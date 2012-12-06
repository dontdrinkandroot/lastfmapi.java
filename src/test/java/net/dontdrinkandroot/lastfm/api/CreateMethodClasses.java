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
//package net.dontdrinkandroot.lastfm.api;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang.StringUtils;
//import org.ccil.cowan.tagsoup.Parser;
//import org.dom4j.Attribute;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.Namespace;
//import org.dom4j.Node;
//import org.dom4j.QName;
//import org.dom4j.io.SAXReader;
//
//public class CreateMethodClasses {
//
//	private static boolean removeNamespaces = true;
//	private static Parser xmlReader;
//	private static SAXReader saxReader;
//	
//	/**
//	 *Removes namespaces if removeNamespaces is true
//	 */
//	public static void fixNamespaces(Document doc) {
//		Element root = doc.getRootElement();
//		if (removeNamespaces && root.getNamespace() != Namespace.NO_NAMESPACE)
//			removeNamespaces(root.content());
//	}
//
//	/**
//	 *Puts the namespaces back to the original root if removeNamespaces is true
//	 */
//	public static void unfixNamespaces(Document doc, Namespace original) {
//		Element root = doc.getRootElement();
//		if (removeNamespaces && original != null)
//			setNamespaces(root.content(), original);
//	}
//
//	/**
//	 *Sets the namespace of the element to the given namespace
//	 */
//	public static void setNamespace(Element elem, Namespace ns) {
//		elem.setQName(QName.get(elem.getName(), ns, elem.getQualifiedName()));
//	}
//
//	/**
//	 *Recursively removes the namespace of the element and all its children:
//	 * sets to Namespace.NO_NAMESPACE
//	 */
//	public static void removeNamespaces(Element elem) {
//		setNamespaces(elem, Namespace.NO_NAMESPACE);
//	}
//
//	/**
//	 *Recursively removes the namespace of the list and all its children: sets
//	 * to Namespace.NO_NAMESPACE
//	 */
//	public static void removeNamespaces(List l) {
//		setNamespaces(l, Namespace.NO_NAMESPACE);
//	}
//
//	/**
//	 *Recursively sets the namespace of the element and all its children.
//	 */
//	public static void setNamespaces(Element elem, Namespace ns) {
//		setNamespace(elem, ns);
//		setNamespaces(elem.content(), ns);
//	}
//
//	/**
//	 *Recursively sets the namespace of the List and all children if the
//	 * current namespace is match
//	 */
//	public static void setNamespaces(List l, Namespace ns) {
//		Node n = null;
//		for (int i = 0; i < l.size(); i++) {
//			n = (Node) l.get(i);
//			if (n.getNodeType() == Node.ATTRIBUTE_NODE)
//				((Attribute) n).setNamespace(ns);
//			if (n.getNodeType() == Node.ELEMENT_NODE)
//				setNamespaces((Element) n, ns);
//		}
//	}
//	
//	private static String loadIntoString(URL url) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//		String text = "";
//		String line = null;
//		while ((line = br.readLine()) != null) {
//			text += line;
//		}
//		br.close();
//		return text;
//		
//	}
//	
//	private static Pattern paramsPattern = Pattern.compile(
//			"<h2>Params</h2>(.*)<h2>Auth</h2>",
//			Pattern.DOTALL
//		);
//	
//	private static Pattern paramPattern = Pattern.compile(
//			"<span class=\"param\">(.*?)</span>\\s(\\((.*?)\\))?\\s(:\\s(.*?))?<br />",
//			Pattern.DOTALL
//		);
//	
//	private static Pattern descriptionPattern = Pattern.compile(
//			"<div class=\"wsdescription\">(.*?)</div>",
//			Pattern.DOTALL
//		);
//	
//	private static Pattern postPattern = Pattern.compile(
//			"<h2>Auth</h2>(.*?)POST(.*?)<h2>SampleResponse</h2>",
//			Pattern.DOTALL
//		);
//	
//	
//
//	public static void main(String[] args) throws DocumentException, IOException {
//		xmlReader = new Parser();
//		saxReader = new SAXReader(xmlReader);
//		
//		Map<String, Map<String, URL>> packages = parseOverview();
//		
//		int count = 0;
//		for (Entry<String, Map<String, URL>> pkg : packages.entrySet()) {
//			
////			if (count > 0) break;
//			
//			StringBuffer testBuffer = new StringBuffer();
//			testBuffer.append("package net.dontdrinkandroot.lastfm.api.model;\n");
//			testBuffer.append("\n");
//			testBuffer.append("import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;\n");
//			testBuffer.append("\n");
//			testBuffer.append("import org.junit.Test;\n");
//			testBuffer.append("\n");
//			testBuffer.append("public class " + pkg.getKey() + "Test extends AbstractModelTest {\n");
//			testBuffer.append("\n");
//			
//			StringBuffer classBuffer = new StringBuffer();
//			classBuffer.append("package net.dontdrinkandroot.lastfm.api.model;\n");
//			classBuffer.append("import java.io.Serializable;\n");
//			classBuffer.append("\n");
//			classBuffer.append("import org.dom4j.Element;\n");
//			classBuffer.append("\n");
//			classBuffer.append("import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;\n");
//			classBuffer.append("import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;\n");
//			classBuffer.append("import net.dontdrinkandroot.lastfm.api.queries.impl.AbstractAuthenticatedGetQuery;\n");
//			classBuffer.append("import net.dontdrinkandroot.lastfm.api.queries.impl.AbstractUnauthenticatedGetQuery;\n");
//			classBuffer.append("\n");
//			classBuffer.append("public class " + pkg.getKey() + " {\n");
//			classBuffer.append("\n");
//			
//			List<String> methods = new ArrayList<String>(pkg.getValue().keySet());
//			Collections.sort(methods);
//			for (String methodName : methods) {
//				URL url = pkg.getValue().get(methodName);
////			for (Entry<String, URL> method : pkg.getValue().entrySet()) {
//				System.out.println("PARSING: " + pkg.getKey() + "." + methodName + ": " + url);
//				String html = loadIntoString(url);
//				String description = null;
//				Matcher descMatcher = descriptionPattern.matcher(html);
//				if (descMatcher.find()) {
//					description = descMatcher.group(1).trim();
//				}
//				boolean post = false;
//				Matcher postMatcher = postPattern.matcher(html);
//				if (postMatcher.find()) {
//					post = true;
//				}
//				Matcher paramsMatcher = paramsPattern.matcher(html);
//				List<String[]> params = new ArrayList<String[]>();
//				boolean authenticated = false;
//				if (paramsMatcher.find()) {
//					String paramsString = paramsMatcher.group(1);
//					Matcher paramMatcher = paramPattern.matcher(paramsString);
//					while (paramMatcher.find()) {
//						String[] param = new String[3];
//						param[0] = paramMatcher.group(1);
//						param[1] = paramMatcher.group(3);
//						param[2] = paramMatcher.group(5);
////						System.out.println(paramMatcher.group(1) + "|" + paramMatcher.group(3) + "|" + paramMatcher.group(5));
//						if (param[0].equals("")) {
//							/* DO NOTHING */
//						} else if (param[0].equals("api_key")) {
//							/* DO NOTHING */
//						} else if (param[0].equals("api_sig")) {
//							authenticated = true;
//						} else {
//							params.add(param);
//						}
//					}
//				}
//				classBuffer.append(createMethod(pkg.getKey(), methodName, description, params, authenticated, post, url));
//				testBuffer.append(createTestMethod(pkg.getKey(), methodName, description, params, authenticated, post, url));
//			}
//			count++;
//			
//			classBuffer.append("}\n");
//			testBuffer.append("}\n");
////			System.out.println(sb);
//			
//			BufferedWriter bw = new BufferedWriter(
//					new FileWriter(
//							new File(
//									"/home/sorst/eclipse/java/lastfmapi.trunk/src/main/java/net/dontdrinkandroot/lastfm/api/model/"
//									+ pkg.getKey() + ".java"
//								)
//						)
//				);
//			bw.write(classBuffer.toString());
//			
//			bw.close();
//			
//			bw = new BufferedWriter(
//					new FileWriter(
//							new File(
//									"/home/sorst/eclipse/java/lastfmapi.trunk/src/test/java/net/dontdrinkandroot/lastfm/api/model/"
//									+ pkg.getKey() + "Test.java"
//								)
//						)
//				);
//			bw.write(testBuffer.toString());
//			
//			bw.close();
//			
//		}
//	}
//	
//
//
//
//	private static String createMethod(
//			final String pkg,
//			final String name,
//			final String description,
//			final List<String[]> params,
//			final boolean authenticated,
//			final boolean post,
//			final URL url
//	) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("\t/**\n");
//		sb.append("\t * " + description + "\n");
//		for (String[] param : params) {
//			sb.append("\t * @param " + param[0] + " " + param[2] + " (" + param[1] + ").\n");
//		}
//		sb.append("\t * @return \n");
//		sb.append("\t */\n");
//		if (authenticated) {
//			if (post) {
//				sb.append("\tpublic static PostQuery<Serializable> " + name + "(\n");
//			} else {
//				sb.append("\tpublic static AuthenticatedGetQuery<Serializable> " + name + "(\n");
//			}
//		} else {
//			sb.append("\tpublic static UnauthenticatedGetQuery<Serializable> " + name + "(\n");
//		}
//		int count = 0;
//		for (String[] param : params) {
//			if (count < (params.size() - 1)) {
//				sb.append("\t\tfinal Object " + param[0] + ",\n");
//			} else {
//				sb.append("\t\tfinal Object " + param[0] + "\n");
//			}
//			count++;
//		}
//		sb.append("\t) {\n");
//		sb.append("\t\t//TODO: implement\n");
//		sb.append("\t\tif (1 == 1) throw new RuntimeException(\"Not implemented\");\n");
//		if (authenticated) {
//			if (post) {
//				sb.append("\t\tPostQuery<Serializable> query\n");
//				sb.append("\t\t\t = new AbstractPostQuery<Serializable>(\"" + pkg.toLowerCase() + "." + name + "\") {\n");
//			} else {
//				sb.append("\t\tAuthenticatedGetQuery<Serializable> query\n");
//				sb.append("\t\t\t = new AbstractAuthenticatedGetQuery<Serializable>(\"" + pkg.toLowerCase() + "." + name + "\") {\n");
//			}
//		} else {
//			sb.append("\t\tUnauthenticatedGetQuery<Serializable> query\n");
//			sb.append("\t\t\t = new AbstractUnauthenticatedGetQuery<Serializable>(\"" + pkg.toLowerCase() + "." + name + "\") {\n");
//		}
//		sb.append("\t\t\t\t@Override\n");
//		sb.append("\t\t\t\tpublic Serializable parse(final Element root) {\n");
//		sb.append("\t\t\t\t\treturn null;\n");
//		sb.append("\t\t\t\t}\n");
//		sb.append("\t\t};\n");
//		for (String[] param : params) {
//			sb.append("\t\tquery.addParameter(\"" + param[0] + "\", " + param[0] + ");\n");
//		}
//		sb.append("\t\treturn query;\n");
//		sb.append("\t}\n");
//		sb.append("\n");
//		sb.append("\n");
//		return sb.toString();
//	}
//	
//	
//	private static String createTestMethod(
//			final String pkg,
//			final String name,
//			final String description,
//			final List<String[]> params,
//			final boolean authenticated,
//			final boolean post,
//			final URL url
//	) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("\t/**\n");
//		sb.append("\t * Test {@link " + pkg + "#"  + name + "}.\n");
//		sb.append("\t */\n");
//		sb.append("\t@Test\n");
//		sb.append("\tpublic final void test" + pkg + StringUtils.capitalize(name) + "() throws LastfmWebServicesException {\n");
//		if (authenticated) {
//			if (post) {
//				sb.append("\t\tObject result = getWs().execute(");
//			} else {
//				sb.append("\t\tObject result = getWs().fetch(");
//			}
//		} else {
//			sb.append("\t\tObject result = getWs().fetch(");
//		}
//		sb.append(pkg + "." + name + "(");
//		int count = 0;
//		for (String[] param : params) {
//			if (count < (params.size() - 1)) {
//				sb.append("null, ");
//			} else {
//				sb.append("null");
//			}
//			count++;
//		}
//		sb.append(")");
//		sb.append(");\n");
//		sb.append("\t};");
//		sb.append("\n");
//		sb.append("\n");
//		
//		return sb.toString();
//	}
//	
//
//	private static Map<String, Map<String, URL>> parseOverview() throws MalformedURLException, DocumentException {
//		System.out.println("PARSING OVERVIEW");
//		Map<String, Map<String, URL>> packages = new HashMap<String, Map<String, URL>>();
//		Document doc = saxReader.read(new URL("http://www.last.fm/api/show?service=302"));
//		fixNamespaces(doc);
//		/* Find methods and links */
//		List<Element> container =
//			(List<Element>) doc.createXPath(
//				"//h2[text()='API Methods']/following-sibling::*"
//			).selectNodes(doc);
//		String pkgName = "";
//		for (Element packageWrapper : (List<Element>) container) {
//			for (Element packageElement : (List<Element>) packageWrapper.elements()) {
//				if (
//						packageElement.attributeValue("class") != null
//						&& packageElement.attributeValue("class").equals("package")
//				) {
//					pkgName = packageElement.element("b").getText();
//					packages.put(pkgName, new HashMap<String, URL>());
//				} else {
//					Element link = packageElement.element("a");
//					String href = "http://last.fm" + link.attributeValue("href");
//					String method = link.getText().split("\\.")[1];
//					URL methodUrl = new URL(href);
//					packages.get(pkgName).put(method, methodUrl);
//				}
//			}
//		}
//		return packages;
//	}
//
//}
