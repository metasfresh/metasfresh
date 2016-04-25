package de.metas.commission.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import mockit.Expectations;
import mockit.Mocked;

import org.compiere.model.I_C_Currency;
import org.junit.Test;

public class CommissionPayrollBLTests {

	@Mocked
	I_C_Currency currency;

	/**
	 * Compute the payroll-amt using currency (precision), points and factor
	 */
	@Test
	public void computeAmt() {

		final BigDecimal commissionPoints = new BigDecimal("6.235");

		final BigDecimal factor = new BigDecimal("1");

		new Expectations() {
			{
				currency.getStdPrecision(); returns(2);
			}
		};

		final BigDecimal result = new CommissionPayrollBL().computeAmt(
				commissionPoints, factor, currency);

		assertEquals(new BigDecimal("6.24"), result);
		
	}

}
