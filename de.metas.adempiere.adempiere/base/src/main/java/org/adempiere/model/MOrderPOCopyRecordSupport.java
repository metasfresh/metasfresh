package org.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.ArrayList;
import java.util.List;

import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.PO;

import com.google.common.collect.ImmutableList;

/**
 * @author Cristina Ghita, METAS.RO
 *         version for copy an order
 */
public class MOrderPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	private static final List<String> COLUMNNAMES_ToCopyDirectly = ImmutableList.of(
			I_C_Order.COLUMNNAME_PreparationDate // task 09000
			);

	@Override
	public List<TableInfoVO> getSuggestedChildren(final PO po, final GridTab gridTab)
	{
		final List<TableInfoVO> list = super.getSuggestedChildren(po, gridTab);
		// remove order tax from list
		final List<TableInfoVO> finalList = new ArrayList<TableInfoVO>();
		for (final TableInfoVO childTableInfo : list)
		{
			if (I_C_OrderLine.Table_Name.equals(childTableInfo.tableName))
			{
				finalList.add(childTableInfo);
			}
		}
		return finalList;
	}

	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		if (COLUMNNAMES_ToCopyDirectly.contains(columnName))
		{
			return from.get_Value(columnName);
		}

		//
		// Fallback to super:
		return super.getValueToCopy(to, from, columnName);
	}

	@Override
	public Object getValueToCopy(final GridField gridField)
	{
		if (COLUMNNAMES_ToCopyDirectly.contains(gridField.getColumnName()))
		{
			return gridField.getValue();
		}

		//
		// Fallback to super:
		return super.getValueToCopy(gridField);
	}

}
