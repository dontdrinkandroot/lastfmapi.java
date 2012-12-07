/**
 * Copyright (C) 2012 Philip W. Sorst <philip@sorst.net>
 * and individual contributors as indicated
 * by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.dontdrinkandroot.lastfm.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.utils.lang.StringUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.ccil.cowan.tagsoup.Parser;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;


public class CheckImplementationStatus {

	private static boolean removeNamespaces = true;

	private static Parser xmlReader;

	private static SAXReader saxReader;


	/**
	 * Removes namespaces if removeNamespaces is true
	 */
	@SuppressWarnings("unchecked")
	public static void fixNamespaces(final Document doc) {

		final Element root = doc.getRootElement();
		if (CheckImplementationStatus.removeNamespaces && root.getNamespace() != Namespace.NO_NAMESPACE) {
			CheckImplementationStatus.removeNamespaces(root.content());
		}
	}


	/**
	 * Puts the namespaces back to the original root if removeNamespaces is true
	 */
	@SuppressWarnings("unchecked")
	public static void unfixNamespaces(final Document doc, final Namespace original) {

		final Element root = doc.getRootElement();
		if (CheckImplementationStatus.removeNamespaces && original != null) {
			CheckImplementationStatus.setNamespaces(root.content(), original);
		}
	}


	/**
	 * Sets the namespace of the element to the given namespace
	 */
	public static void setNamespace(final Element elem, final Namespace ns) {

		elem.setQName(QName.get(elem.getName(), ns, elem.getQualifiedName()));
	}


	/**
	 * Recursively removes the namespace of the element and all its children: sets to
	 * Namespace.NO_NAMESPACE
	 */
	public static void removeNamespaces(final Element elem) {

		CheckImplementationStatus.setNamespaces(elem, Namespace.NO_NAMESPACE);
	}


	/**
	 * Recursively removes the namespace of the list and all its children: sets to
	 * Namespace.NO_NAMESPACE
	 */
	public static void removeNamespaces(final List<Node> l) {

		CheckImplementationStatus.setNamespaces(l, Namespace.NO_NAMESPACE);
	}


	/**
	 * Recursively sets the namespace of the element and all its children.
	 */
	@SuppressWarnings("unchecked")
	public static void setNamespaces(final Element elem, final Namespace ns) {

		CheckImplementationStatus.setNamespace(elem, ns);
		CheckImplementationStatus.setNamespaces(elem.content(), ns);
	}


	/**
	 * Recursively sets the namespace of the List and all children if the current namespace is match
	 */
	public static void setNamespaces(final List<Node> l, final Namespace ns) {

		Node n = null;
		for (int i = 0; i < l.size(); i++) {
			n = l.get(i);
			if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
				((Attribute) n).setNamespace(ns);
			}
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				CheckImplementationStatus.setNamespaces((Element) n, ns);
			}
		}
	}


	// private static String loadIntoString(final URL url) throws IOException {
	// final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	// String text = "";
	// String line = null;
	// while ((line = br.readLine()) != null) {
	// text += line;
	// }
	// br.close();
	// return text;
	//
	// }

	// private static Pattern paramsPattern = Pattern.compile("<h2>Params</h2>(.*)<h2>Auth</h2>",
	// Pattern.DOTALL);
	//
	// private static Pattern paramPattern = Pattern.compile(
	// "<span class=\"param\">(.*?)</span>\\s(\\((.*?)\\))?\\s(:\\s(.*?))?<br />",
	// Pattern.DOTALL);
	//
	// private static Pattern descriptionPattern = Pattern.compile(
	// "<div class=\"wsdescription\">(.*?)</div>", Pattern.DOTALL);
	//
	// private static Pattern postPattern = Pattern.compile(
	// "<h2>Auth</h2>(.*?)POST(.*?)<h2>SampleResponse</h2>", Pattern.DOTALL);

	public static void main(final String[] args) throws DocumentException, IOException {

		CheckImplementationStatus.xmlReader = new Parser();
		CheckImplementationStatus.saxReader = new SAXReader(CheckImplementationStatus.xmlReader);

		final String packagePrefix = "net.dontdrinkandroot.lastfm.api.model.";

		final Map<String, Map<String, URL>> packages = CheckImplementationStatus.parseOverview();

		final StringBuffer html = new StringBuffer();
		html.append("<html>\n");
		html.append("<head>\n");
		html.append("<title>Implementation Status</title>\n");
		html.append("</head>\n");
		html.append("<body>\n");
		html.append("<h1>Implementation Status</h1>\n");

		final StringBuffer wiki = new StringBuffer();

		int numImplemented = 0;
		int numTested = 0;
		int numMethods = 0;

		final List<String> packageList = new ArrayList<String>(packages.keySet());
		Collections.sort(packageList);

		for (final String pkg : packageList) {

			System.out.println("Parsing " + pkg);
			html.append("<h2>" + pkg + "</h2>\n");
			wiki.append("\n===== " + pkg + " =====\n\n");

			Class<?> modelClass = null;
			final String className = packagePrefix + pkg;
			try {
				modelClass = Class.forName(className);
				System.out.println("\tClass " + modelClass.getName() + " exists");
			} catch (final ClassNotFoundException e) {
				// e.printStackTrace();
				System.out.println("\t" + className + ": DOES NOT exist");
			}

			Class<?> testClass = null;
			final String testClassName = packagePrefix + pkg + "Test";
			try {
				testClass = Class.forName(testClassName);
				System.out.println("\tTestClass " + testClass.getName() + " exists");
			} catch (final ClassNotFoundException e) {
				// e.printStackTrace();
				System.out.println("\t" + testClassName + ": TestClass for DOES NOT exist");
			}

			final List<String> methods = new ArrayList<String>(packages.get(pkg).keySet());
			Collections.sort(methods);

			final Method[] classMethods = modelClass.getMethods();
			final Method[] testMethods = testClass.getMethods();

			html.append("<table>\n");
			html.append("<tr><th>Method</th><th>Implemented</th><th>Tested</th></tr>\n");
			wiki.append("^ Method  ^ Implemented  ^ Tested  ^\n");

			numMethods += methods.size();

			for (final String method : methods) {

				System.out.println("\t\t parsing " + method);

				html.append("<tr>\n");
				html.append("<td>" + method + "</td>\n");
				wiki.append("| " + method + "  ");

				boolean classMethodFound = false;
				for (final Method classMethod : classMethods) {
					if (classMethod.getName().equals(method)) {
						classMethodFound = true;
						break;
					}
				}

				if (classMethodFound) {
					System.out.println("\t\t\tMethod " + method + " found");
					html.append("<td style=\"background-color: green\">true</td>\n");
					wiki.append("| yes  ");
					numImplemented++;
				} else {
					System.out.println("\t\t\t" + method + " NOT found");
					html.append("<td style=\"background-color: red\">false</td>\n");
					wiki.append("| **no**  ");
				}

				boolean testMethodFound = false;
				final String testMethodName = "test" + StringUtils.capitalize(method);
				for (final Method testMethod : testMethods) {
					if (testMethod.getName().equals(testMethodName)) {
						testMethodFound = true;
						break;
					}
				}

				if (testMethodFound) {
					System.out.println("\t\t\tTestMethod " + method + " found");
					html.append("<td style=\"background-color: green\">true</td>\n");
					wiki.append("| yes  |\n");
					numTested++;
				} else {
					System.out.println("\t\t\t" + testMethodName + " NOT found");
					html.append("<td style=\"background-color: red\">false</td>\n");
					wiki.append("| **no**  |\n");
				}

				html.append("</tr>\n");

			}

			html.append("</table>\n");

			// for (String methodName : methods) {
			// URL url = pkg.getValue().get(methodName);
			// System.out.println("PARSING: " + pkg.getKey() + "." + methodName + ": " + url);
			// String html = loadIntoString(url);
			// String description = null;
			// Matcher descMatcher = descriptionPattern.matcher(html);
			// if (descMatcher.find()) {
			// description = descMatcher.group(1).trim();
			// }
			// boolean post = false;
			// Matcher postMatcher = postPattern.matcher(html);
			// if (postMatcher.find()) {
			// post = true;
			// }
			// Matcher paramsMatcher = paramsPattern.matcher(html);
			// List<String[]> params = new ArrayList<String[]>();
			// boolean authenticated = false;
			// if (paramsMatcher.find()) {
			// String paramsString = paramsMatcher.group(1);
			// Matcher paramMatcher = paramPattern.matcher(paramsString);
			// while (paramMatcher.find()) {
			// String[] param = new String[3];
			// param[0] = paramMatcher.group(1);
			// param[1] = paramMatcher.group(3);
			// param[2] = paramMatcher.group(5);
			// // System.out.println(paramMatcher.group(1) + "|" + paramMatcher.group(3) + "|" +
			// paramMatcher.group(5));
			// if (param[0].equals("")) {
			// /* DO NOTHING */
			// } else if (param[0].equals("api_key")) {
			// /* DO NOTHING */
			// } else if (param[0].equals("api_sig")) {
			// authenticated = true;
			// } else {
			// params.add(param);
			// }
			// }
			// }
			// }
			// count++;
			//
		}

		html.append("<hr />");
		html.append("<p>"
				+ numImplemented
				+ "/"
				+ numMethods
				+ " implemented ("
				+ numImplemented
				* 100
				/ numMethods
				+ "%)</p>");
		html.append("<p>" + numTested + "/" + numMethods + " tested (" + numTested * 100 / numMethods + "%)</p>");

		html.append("</body>\n");
		html.append("</html>\n");

		FileOutputStream out = new FileOutputStream(new File(FileUtils.getTempDirectory(), "apistatus.html"));
		IOUtils.write(html, out);
		IOUtils.closeQuietly(out);

		out = new FileOutputStream(new File(FileUtils.getTempDirectory(), "apistatus.wiki.txt"));
		IOUtils.write(wiki, out);
		IOUtils.closeQuietly(out);
	}


	// private static String createMethod(
	// final String pkg,
	// final String name,
	// final String description,
	// final List<String[]> params,
	// final boolean authenticated,
	// final boolean post,
	// final URL url) {
	//
	// final StringBuffer sb = new StringBuffer();
	// sb.append("\t/**\n");
	// sb.append("\t * " + description + "\n");
	// for (final String[] param : params) {
	// sb.append("\t * @param " + param[0] + " " + param[2] + " (" + param[1] + ").\n");
	// }
	// sb.append("\t * @return \n");
	// sb.append("\t */\n");
	// if (authenticated) {
	// if (post) {
	// sb.append("\tpublic static PostQuery<Serializable> " + name + "(\n");
	// } else {
	// sb.append("\tpublic static AuthenticatedGetQuery<Serializable> " + name + "(\n");
	// }
	// } else {
	// sb.append("\tpublic static UnauthenticatedGetQuery<Serializable> " + name + "(\n");
	// }
	// int count = 0;
	// for (final String[] param : params) {
	// if (count < params.size() - 1) {
	// sb.append("\t\tfinal Object " + param[0] + ",\n");
	// } else {
	// sb.append("\t\tfinal Object " + param[0] + "\n");
	// }
	// count++;
	// }
	// sb.append("\t) {\n");
	// sb.append("\t\t//TODO: implement\n");
	// sb.append("\t\tif (1 == 1) throw new RuntimeException(\"Not implemented\");\n");
	// if (authenticated) {
	// if (post) {
	// sb.append("\t\tPostQuery<Serializable> query\n");
	// sb.append("\t\t\t = new AbstractPostQuery<Serializable>(\"" + pkg.toLowerCase() + "." + name
	// + "\") {\n");
	// } else {
	// sb.append("\t\tAuthenticatedGetQuery<Serializable> query\n");
	// sb.append("\t\t\t = new AbstractAuthenticatedGetQuery<Serializable>(\"" + pkg.toLowerCase() +
	// "."
	// + name + "\") {\n");
	// }
	// } else {
	// sb.append("\t\tUnauthenticatedGetQuery<Serializable> query\n");
	// sb.append("\t\t\t = new AbstractUnauthenticatedGetQuery<Serializable>(\"" + pkg.toLowerCase()
	// + "." + name
	// + "\") {\n");
	// }
	// sb.append("\t\t\t\t@Override\n");
	// sb.append("\t\t\t\tpublic Serializable parse(final Element root) {\n");
	// sb.append("\t\t\t\t\treturn null;\n");
	// sb.append("\t\t\t\t}\n");
	// sb.append("\t\t};\n");
	// for (final String[] param : params) {
	// sb.append("\t\tquery.addParameter(\"" + param[0] + "\", " + param[0] + ");\n");
	// }
	// sb.append("\t\treturn query;\n");
	// sb.append("\t}\n");
	// sb.append("\n");
	// sb.append("\n");
	// return sb.toString();
	// }

	// private static String createTestMethod(
	// final String pkg,
	// final String name,
	// final String description,
	// final List<String[]> params,
	// final boolean authenticated,
	// final boolean post,
	// final URL url) {
	//
	// final StringBuffer sb = new StringBuffer();
	// sb.append("\t/**\n");
	// sb.append("\t * Test {@link " + pkg + "#" + name + "}.\n");
	// sb.append("\t */\n");
	// sb.append("\t@Test\n");
	// sb.append("\tpublic final void test" + pkg + StringUtils.capitalize(name)
	// + "() throws LastfmWebServicesException {\n");
	// if (authenticated) {
	// if (post) {
	// sb.append("\t\tObject result = getWs().execute(");
	// } else {
	// sb.append("\t\tObject result = getWs().fetch(");
	// }
	// } else {
	// sb.append("\t\tObject result = getWs().fetch(");
	// }
	// sb.append(pkg + "." + name + "(");
	// int count = 0;
	// for (final String[] param : params) {
	// if (count < params.size() - 1) {
	// sb.append("null, ");
	// } else {
	// sb.append("null");
	// }
	// count++;
	// }
	// sb.append(")");
	// sb.append(");\n");
	// sb.append("\t};");
	// sb.append("\n");
	// sb.append("\n");
	//
	// return sb.toString();
	// }

	@SuppressWarnings("unchecked")
	private static Map<String, Map<String, URL>> parseOverview() throws MalformedURLException, DocumentException {

		System.out.println("PARSING OVERVIEW");
		final Map<String, Map<String, URL>> packages = new HashMap<String, Map<String, URL>>();
		final Document doc =
				CheckImplementationStatus.saxReader.read(new URL("http://www.last.fm/api/show?service=302"));
		CheckImplementationStatus.fixNamespaces(doc);
		/* Find methods and links */
		final List<Element> container =
				doc.createXPath("//h2[text()='API Methods']/following-sibling::*").selectNodes(doc);
		String pkgName = "";
		for (final Element packageWrapper : container) {

			for (final Element packageElement : (List<Element>) packageWrapper.elements()) {

				if (packageElement.attributeValue("class") != null
						&& packageElement.attributeValue("class").equals("package")) {

					pkgName = packageElement.element("h3").getText();
					packages.put(pkgName, new HashMap<String, URL>());

					Element listElement = packageElement.element("ul");
					List<Element> listItems = listElement.elements("li");
					for (Element listItem : listItems) {

						final Element link = listItem.element("a");
						final String href = "http://last.fm" + link.attributeValue("href");
						final String method = link.getText().split("\\.")[1];
						final URL methodUrl = new URL(href);
						packages.get(pkgName).put(method, methodUrl);
					}

				}
			}
		}

		return packages;
	}

}
