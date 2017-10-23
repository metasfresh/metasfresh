package de.metas.order.compensationGroup;

import org.adempiere.util.Check;
import org.compiere.model.I_C_OrderLine;

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

// @UtilityClass
public class OrderGroupCompensationUtils
{
	public static void assertNotCompensationLine(final I_C_OrderLine groupOrderLine)
	{
		Check.assume(!groupOrderLine.isGroupCompensationLine(), "Order line {} shall not be a compensation line", groupOrderLine);
	}

	public static void assertNotInGroup(final I_C_OrderLine orderLine)
	{
		Check.assume(orderLine.getGroupNo() <= 0, "Order line {} shall not have any discount group", orderLine);
	}
}
