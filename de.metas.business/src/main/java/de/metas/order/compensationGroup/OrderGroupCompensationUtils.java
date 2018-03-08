package de.metas.order.compensationGroup;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class OrderGroupCompensationUtils
{
	public static void assertCompensationLine(final I_C_OrderLine groupOrderLine)
	{
		if (!groupOrderLine.isGroupCompensationLine())
		{
			throw new AdempiereException("Order line " + groupOrderLine.getLine() + " shall be a compensation line");
		}
	}

	public static void assertNotCompensationLine(final I_C_OrderLine groupOrderLine)
	{
		if (groupOrderLine.isGroupCompensationLine())
		{
			throw new AdempiereException("Order line " + groupOrderLine.getLine() + " shall NOT be a compensation line");
		}
	}

	public static void assertInGroup(final I_C_OrderLine orderLine)
	{
		if (!isInGroup(orderLine))
		{
			throw new AdempiereException("Order line " + orderLine.getLine() + " shall be part of a compensation group");
		}
	}

	public static void assertNotInGroup(final I_C_OrderLine orderLine)
	{
		if (isInGroup(orderLine))
		{
			throw new AdempiereException("Order line " + orderLine.getLine() + " shall NOT be part of a compensation group");
		}
	}

	public static boolean isInGroup(final I_C_OrderLine orderLine)
	{
		return orderLine.getC_Order_CompensationGroup_ID() > 0;
	}

	public static boolean isNotInGroup(final I_C_OrderLine orderLine)
	{
		return !isInGroup(orderLine);
	}

	public static BigDecimal adjustAmtByCompensationType(@NonNull final BigDecimal compensationAmt, @NonNull final GroupCompensationType compensationType)
	{
		if (compensationType == GroupCompensationType.Discount)
		{
			return compensationAmt.negate();
		}
		else if (compensationType == GroupCompensationType.Surcharge)
		{
			return compensationAmt;
		}
		else
		{
			throw new AdempiereException("Unknown compensationType: " + compensationType);
		}
	}

	public static boolean isGeneratedCompensationLine(final I_C_OrderLine orderLine)
	{
		return isGeneratedCompensationLine(orderLine.getC_CompensationGroup_SchemaLine_ID());
	}

	public static boolean isGeneratedCompensationLine(final int groupSchemaLineId)
	{
		return groupSchemaLineId > 0;
	}
}
