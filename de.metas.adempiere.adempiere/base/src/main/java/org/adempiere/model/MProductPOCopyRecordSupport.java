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


import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_Product_PO;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public class MProductPOCopyRecordSupport extends GeneralCopyRecordSupport
{

	@Override
	public void setSpecialColumnsName(PO to)
	{
		super.setSpecialColumnsName(to);
		if (to.get_ColumnIndex(I_M_Product_PO.COLUMNNAME_VendorProductNo) > 0)
		{
			makeUnique(to, I_M_Product_PO.COLUMNNAME_VendorProductNo);
		}
	}

	@Override
	void makeUnique(PO to, String column)
	{
		if (I_M_Product_PO.COLUMNNAME_VendorProductNo.equals(column))
		{
			to.set_CustomColumn(column,
					getValueWithSuffix(to.get_ValueAsString(column), to.get_ValueAsInt(I_M_Product_PO.COLUMNNAME_C_BPartner_ID)));
		}
		else
		{
			super.makeUnique(to, column);
		}
	}
	
	/**
	 * get latest suffix
	 * 
	 * @param tableName
	 * @param column
	 * @param value
	 * @return
	 */
	private String getValueWithSuffix(String value, int C_BPartner_ID)
	{
		final String whereClause = I_M_Product_PO.COLUMNNAME_VendorProductNo + " LIKE ?"
						   + " AND " + I_M_Product_PO.COLUMNNAME_C_BPartner_ID + " = ?";
		final Query query = new Query(Env.getCtx(), I_M_Product_PO.Table_Name, whereClause, ITrx.TRXNAME_None);
		query.setParameters(new Object[] { value, C_BPartner_ID });
		query.setOrderBy(I_M_Product_PO.COLUMNNAME_VendorProductNo);
		List<PO> list = query.list();
		if (list == null || list.isEmpty())
		{
			return value;
		}
		for (int i = 1; i < 100; i++)
		{
			final String valueNew = value + "_" + i;
			
			boolean found = false;
			for (PO po : list)
			{
				String existentValue = po.get_ValueAsString(I_M_Product_PO.COLUMNNAME_VendorProductNo);
				if (valueNew.equals(existentValue))
				{
					found = true;
					break;
				}
			}
			
			if (!found)
			{
				return valueNew;
			}
		}
		return value;
	}


}
