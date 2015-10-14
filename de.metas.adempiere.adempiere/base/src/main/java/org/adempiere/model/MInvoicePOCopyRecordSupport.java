/**
 * 
 */
package org.adempiere.model;

/*
 * #%L
 * ADempiere ERP - Base
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

import org.compiere.model.GridTab;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.PO;

/**
 * @author Cristina Ghita, METAS.RO
 * version for copy an invoice
 */
public class MInvoicePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<TableInfoVO> getSuggestedChildren(PO po, GridTab gt)
	{
		List<TableInfoVO> list = super.getSuggestedChildren(po, gt);
		//remove invoice tax from list
		List<TableInfoVO> finalList = new ArrayList<TableInfoVO>();
		for (TableInfoVO childTableInfo : list)
		{
			if (MInvoiceLine.Table_Name.equals(childTableInfo.tableName))
				finalList.add(childTableInfo);
		}
		return finalList;
	}
}
