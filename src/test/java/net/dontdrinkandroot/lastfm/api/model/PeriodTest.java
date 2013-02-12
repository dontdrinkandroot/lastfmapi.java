package net.dontdrinkandroot.lastfm.api.model;

import org.junit.Assert;
import org.junit.Test;


public class PeriodTest {

	@Test
	public void testFromString() {

		for (Period period : Period.values()) {
			Assert.assertEquals(period, Period.fromString(period.getString()));
		}

		Assert.assertEquals(Period.OVERALL, Period.fromString(null));
		Assert.assertEquals(Period.OVERALL, Period.fromString(""));
		Assert.assertEquals(Period.OVERALL, Period.fromString("asdf"));

		Assert.assertEquals(Period.SEVEN_DAY, Period.fromString("seven_day"));
		Assert.assertEquals(Period.ONE_MONTH, Period.fromString("one_month"));
		Assert.assertEquals(Period.THREE_MONTH, Period.fromString("three_month"));
		Assert.assertEquals(Period.SIX_MONTH, Period.fromString("six_month"));
		Assert.assertEquals(Period.TWELVE_MONTH, Period.fromString("twelve_month"));
	}

}
