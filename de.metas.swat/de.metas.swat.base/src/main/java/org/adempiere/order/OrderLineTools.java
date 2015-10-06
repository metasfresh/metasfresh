package org.adempiere.order;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.util.Constants;

public final class OrderLineTools {

	private OrderLineTools() {
	}

	/**
	 * 
	 * @param priceList
	 * @param lineDiscount
	 * @param orderDiscount
	 *            <code>null</code> is treated as zero
	 * @param precision
	 * @return
	 */
	public static BigDecimal inactComputePriceActual(final BigDecimal priceList,
			final BigDecimal lineDiscount, final BigDecimal orderDiscount,
			final int precision) {

		final BigDecimal lineDiscountFactor = Constants.HUNDRET.subtract(
				lineDiscount).divide(Constants.HUNDRET);

		final BigDecimal priceIntermediate = priceList.multiply(
				lineDiscountFactor).setScale(precision,
				BigDecimal.ROUND_HALF_UP);

		final BigDecimal orderDiscountFactor = Constants.HUNDRET.subtract(
				zeroIfNull(orderDiscount)).divide(Constants.HUNDRET);

		final BigDecimal newPriceActual = priceIntermediate.multiply(
				orderDiscountFactor).setScale(precision,
				BigDecimal.ROUND_HALF_UP);

		return newPriceActual;
	}

	public static BigDecimal inactComputeOrderLineDiscount(
			final BigDecimal priceList, final BigDecimal priceActual,
			final BigDecimal discountOrder, final int precision) {

		if (priceList.signum() == 0) {
			return BigDecimal.ZERO.setScale(precision);
		}

		final BigDecimal orderDiscountFactor = Constants.HUNDRET.subtract(
				zeroIfNull(discountOrder)).divide(Constants.HUNDRET);

		//
		// priceIntermediate is the price before (or without) the application of
		// discountOrder
		final BigDecimal priceIntermediate = priceActual.divide(
				orderDiscountFactor, precision, BigDecimal.ROUND_HALF_UP);

		final BigDecimal discount = priceList.subtract(priceIntermediate)
				.multiply(Constants.HUNDRET).divide(priceList, precision,
						BigDecimal.ROUND_HALF_UP);

		return discount;
	}

	private static BigDecimal zeroIfNull(final BigDecimal number) {

		if (number == null) {
			return BigDecimal.ZERO;
		}
		return number;
	}

}
