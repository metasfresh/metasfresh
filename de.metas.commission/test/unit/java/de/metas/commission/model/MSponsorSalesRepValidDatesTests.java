package de.metas.commission.model;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mocked;
import mockit.Mockit;
import mockit.NonStrictExpectations;

import org.adempiere.util.MiscUtils;
import org.slf4j.Logger;
import de.metas.logging.CLogMgt;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.metas.adempiere.test.POTest;

@RunWith(value = Parameterized.class)
public class MSponsorSalesRepValidDatesTests {

	@Mocked(methods = "beforeSave", inverse = true)
	MCSponsorSalesRep sponsorSalesRep;

	final Object validFrom;
	final Object validTo;
	final String msg;
	final boolean expected;

	public MSponsorSalesRepValidDatesTests(String validFrom, String validTo,
			String msg, boolean expected) {

		this.validFrom = validFrom;
		this.validTo = validTo;
		this.msg = msg;
		this.expected = expected;

	}

	@Test
	public void validFromValidTo() {

		setupMock(validFrom, validTo);
		
		assertEquals(msg, sponsorSalesRep.beforeSave(true), expected);
		assertEquals(msg, sponsorSalesRep.beforeSave(false), expected);
	}

	/**
	 * Test parameters from the check of validFrom and validTo values in
	 * {@link MCSponsorSalesRep#beforeSave(boolean)}.
	 * 
	 * @return
	 */
	@Parameters
	public static Collection<Object[]> validFromValidToParams() {

		Object[][] data = new Object[][] {
			{ "1899-11-02", "9999-12-31", "validFrom before min value", false },
			{ "2009-11-02", "2009-11-01", "validFrom after validTo", false },
			{ "2009-11-02", "10000-11-01", "validTo after max value", false },
			{ "2009-11-02", "2010-11-15", "validFrom and validTo OK", true } 
		};

		return Arrays.asList(data);
	}

	private void setupMock(final Object validFrom, final Object validTo) {

		final Timestamp validFromToUse = mkTimestamp(validFrom);
		final Timestamp validToToUse = mkTimestamp(validTo);

		POTest.recordGenericExpectations(sponsorSalesRep, 10);

		new NonStrictExpectations() 
		{
			Logger logger;
			{
				MCSponsorSalesRep.getLogger(); result = logger;
				
				sponsorSalesRep.getC_Sponsor_ID();
				returns(20);

				sponsorSalesRep.getValidFrom();
				returns(validFromToUse);

				sponsorSalesRep.getValidTo();
				returns(validToToUse);
			}
		};
	}

	private Timestamp mkTimestamp(final Object validObj) {

		final Timestamp validTimestamp;

		if (validObj instanceof String)
		{
			validTimestamp = MiscUtils.toTimeStamp((String)validObj);
		}
		else
		{
			validTimestamp = (Timestamp)validObj;
		}
		return validTimestamp;
	}

	@BeforeClass
	public static void beforeClass() {

		Mockit.setUpMocks();
	}
}
