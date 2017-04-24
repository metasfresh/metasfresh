package org.compiere.model;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ElementTranslationBL
{
	protected boolean updateTransations (final int elementId, final String adLanguage, final String newName)
	{
		//	Update Columns, Fields, Parameters, Print Info
		
			StringBuffer sql = new StringBuffer();
			int no = 0;
			
			{
				//	Column
				sql = new StringBuffer("UPDATE AD_Column_Trl SET Name=")
					.append(DB.TO_STRING(newName))
					
					.append(" WHERE AD_Element_ID=").append(elementId)
					.append(" AND AD_Language=").append(DB.TO_STRING(adLanguage));
				no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_None);
			

				//	Parameter 
				sql = new StringBuffer("UPDATE AD_Process_Para SET ColumnName=")
					.append(DB.TO_STRING(getColumnName()))
					.append(", Name=").append(DB.TO_STRING(getName()))
					.append(", Description=").append(DB.TO_STRING(getDescription()))
					.append(", Help=").append(DB.TO_STRING(getHelp()))
					.append(", AD_Element_ID=").append(get_ID())
					.append(" WHERE UPPER(ColumnName)=")
					.append(DB.TO_STRING(getColumnName().toUpperCase()))
					.append(" AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL");
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				
				sql = new StringBuffer("UPDATE AD_Process_Para SET ColumnName=")
					.append(DB.TO_STRING(getColumnName()))
					.append(", Name=").append(DB.TO_STRING(getName()))
					.append(", Description=").append(DB.TO_STRING(getDescription()))
					.append(", Help=").append(DB.TO_STRING(getHelp()))
					.append(" WHERE AD_Element_ID=").append(get_ID())
					.append(" AND IsCentrallyMaintained='Y'");
				no += DB.executeUpdate(sql.toString(), get_TrxName());
				log.debug("Parameters updated #" + no);
			}
			
			if (   is_ValueChanged(M_Element.COLUMNNAME_Name)
				|| is_ValueChanged(M_Element.COLUMNNAME_Description)
				|| is_ValueChanged(M_Element.COLUMNNAME_Help)
				) {
				//	Field
				sql = new StringBuffer("UPDATE AD_Field SET Name=")
					.append(DB.TO_STRING(getName()))
					.append(", Description=").append(DB.TO_STRING(getDescription()))
					.append(", Help=").append(DB.TO_STRING(getHelp()))
					.append(" WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=")
					.append(get_ID())
					.append(") AND IsCentrallyMaintained='Y'");
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.debug("Fields updated #" + no);
				
				// Info Column - update Name, Description, Help - doesn't have IsCentrallyMaintained currently
				// no = DB.executeUpdate(sql.toString(), get_TrxName());
				// log.debug("InfoColumn updated #" + no);
			}
			
			if (   is_ValueChanged(M_Element.COLUMNNAME_PrintName)
				|| is_ValueChanged(M_Element.COLUMNNAME_Name)
				) {
				//	Print Info
				sql = new StringBuffer("UPDATE AD_PrintFormatItem pi SET PrintName=")
					.append(DB.TO_STRING(getPrintName()))
					.append(", Name=").append(DB.TO_STRING(getName()))
					.append(" WHERE IsCentrallyMaintained='Y'")	
					.append(" AND EXISTS (SELECT * FROM AD_Column c ")
						.append("WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=")
						.append(get_ID()).append(")");
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.debug("PrintFormatItem updated #" + no);
			}
			
		}return success;
}	// afterSave
}
