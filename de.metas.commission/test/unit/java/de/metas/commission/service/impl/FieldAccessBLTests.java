package de.metas.commission.service.impl;

import java.math.BigDecimal;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.compiere.model.PO;
import org.junit.Assert;
import org.junit.Test;

import de.metas.interfaces.I_C_OrderLine;

public class FieldAccessBLTests {

	@Mocked
	PO po;

	@Test
	public void retrieveDiscount1() {

		checkWith("90.00", "90.00", "0");
	}

	@Test
	public void retrieveDiscount2() {

		checkWith("100.00", "90.00", "10.00");
	}

	@Test
	public void retrieveDiscount3() {

		checkWith("55.00", "49.50", "10.00");
	}

	@Test
	public void retrieveDiscount4() {

		checkWith("74.50", "40.98", "45.00");
	}

	@Test
	public void retrieveDiscount5() {

		checkWith("32.50", "17.88", "45.00");
	}

	
	private void checkWith(final String priceList, final String priceActual,
			final String discountExp) {

		new NonStrictExpectations() {
			{
				po.get_Value(I_C_OrderLine.COLUMNNAME_PriceList);
				returns(new BigDecimal(priceList));

				po.get_Value(I_C_OrderLine.COLUMNNAME_PriceActual);
				returns(new BigDecimal(priceActual));
			}
		};

		final BigDecimal discount = new FieldAccessBL().getDiscount(po, false);
		Assert.assertEquals(discount, new BigDecimal(discountExp));
	}

	/**
	 * The parameter-po has no pricelist column and "throwEx" is false
	 */
	@Test
	public void inadequatePONoEx() {

		final BigDecimal discount = checkWithInadequaltePO(false);
		Assert.assertNull(discount);
	}

	/**
	 * The parameter-po has no pricelist column and "throwEx" is true
	 */
	@Test(expected = IllegalArgumentException.class)
	public void inadequatePOEx() {

		checkWithInadequaltePO(true);
	}

	private BigDecimal checkWithInadequaltePO(final boolean throwEx) {
		new NonStrictExpectations() {
			{
				po.get_ColumnIndex(I_C_OrderLine.COLUMNNAME_PriceList);
				returns(-1);

				po.get_ColumnIndex(I_C_OrderLine.COLUMNNAME_PriceActual);
				returns(10);
			}
		};

		final BigDecimal discount = new FieldAccessBL()
				.getDiscount(po, throwEx);
		return discount;
	}
}
