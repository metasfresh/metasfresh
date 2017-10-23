package de.metas.order.compensationGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class OrderGroupCompensationPercentageCalculator
{
	public static final OrderGroupCompensationPercentageCalculator of(final Collection<I_C_OrderLine> groupOrderLines)
	{
		return new OrderGroupCompensationPercentageCalculator(groupOrderLines);
	}

	private final List<I_C_OrderLine> groupOrderLines;

	private OrderGroupCompensationPercentageCalculator(@NonNull final Collection<I_C_OrderLine> groupOrderLines)
	{
		this.groupOrderLines = groupOrderLines.stream()
				.peek(OrderGroupCompensationUtils::assertNotCompensationLine)
				.collect(ImmutableList.toImmutableList());

		Check.assumeNotEmpty(this.groupOrderLines, "groupOrderLines is not empty");
	}

	public BigDecimal calculateCompensationByPercent(final BigDecimal compensationPerc, final int precision)
	{
		if (compensationPerc.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		return groupOrderLines.stream()
				.map(ol -> calculateCompensationByPercent(compensationPerc, precision, ol))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal calculateCompensationByPercent(final BigDecimal compensationPerc, final int precision, final I_C_OrderLine groupOrderLine)
	{
		final BigDecimal lineNetAmt = groupOrderLine.getLineNetAmt();
		if (lineNetAmt.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		BigDecimal groupDiscountAmt = lineNetAmt.multiply(compensationPerc);
		if (groupDiscountAmt.scale() > precision)
		{
			groupDiscountAmt = groupDiscountAmt.setScale(precision, RoundingMode.HALF_UP);
		}

		return groupDiscountAmt;
	}

}
