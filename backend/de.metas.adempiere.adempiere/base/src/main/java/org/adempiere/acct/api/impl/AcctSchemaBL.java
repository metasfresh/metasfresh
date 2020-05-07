package org.adempiere.acct.api.impl;

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


import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MColumn;
import org.compiere.model.X_C_AcctSchema_Element;

public class AcctSchemaBL implements IAcctSchemaBL
{

	@Override
	public int getDefaultValue(final I_C_AcctSchema_Element ase)
	{
		final String elementType = ase.getElementType();
		final int defaultValue;

		if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Organization))
		{
			defaultValue = ase.getOrg_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Account))
		{
			defaultValue = ase.getC_ElementValue_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner))
		{
			defaultValue = ase.getC_BPartner_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Product))
		{
			defaultValue = ase.getM_Product_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Activity))
		{
			defaultValue = ase.getC_Activity_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom))
		{
			defaultValue = ase.getC_Location_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo))
		{
			defaultValue = ase.getC_Location_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign))
		{
			defaultValue = ase.getC_Campaign_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx))
		{
			defaultValue = ase.getOrg_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Project))
		{
			defaultValue = ase.getC_Project_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion))
		{
			defaultValue = ase.getC_SalesRegion_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList1))
		{
			defaultValue = ase.getC_ElementValue_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList2))
		{
			defaultValue = ase.getC_ElementValue_ID();
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement1))
		{
			defaultValue = 0;
		}
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement2))
		{
			defaultValue = 0;
		}
		else
		{
			defaultValue = 0;
		}
		return defaultValue;
	}

	/**
	 * Get Column Name of ELEMENTTYPE
	 * 
	 * @param elementType ELEMENTTYPE
	 * @return column name or "" if not found
	 */
	@Override
	public String getColumnName(String elementType)
	{
		if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Organization))
			return "AD_Org_ID";
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Account))
			return I_C_ValidCombination.COLUMNNAME_Account_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner))
			return I_C_ValidCombination.COLUMNNAME_C_BPartner_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Product))
			return I_C_ValidCombination.COLUMNNAME_M_Product_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Activity))
			return I_C_ValidCombination.COLUMNNAME_C_Activity_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom))
			return I_C_ValidCombination.COLUMNNAME_C_LocFrom_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo))
			return I_C_ValidCombination.COLUMNNAME_C_LocTo_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign))
			return I_C_ValidCombination.COLUMNNAME_C_Campaign_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx))
			return I_C_ValidCombination.COLUMNNAME_AD_OrgTrx_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Project))
			return I_C_ValidCombination.COLUMNNAME_C_Project_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion))
			return I_C_ValidCombination.COLUMNNAME_C_SalesRegion_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList1))
			return I_C_ValidCombination.COLUMNNAME_User1_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList2))
			return I_C_ValidCombination.COLUMNNAME_User2_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement1))
			return I_C_ValidCombination.COLUMNNAME_UserElement1_ID;
		else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement2))
			return I_C_ValidCombination.COLUMNNAME_UserElement2_ID;
		//
		return "";
	}   // getColumnName

	@Override
	public String getColumnName(final I_C_AcctSchema_Element ase)
	{
		return getColumnName(ase.getElementType());
	}

	@Override
	public String getDisplayColumnName(final I_C_AcctSchema_Element ase)
	{
		String et = ase.getElementType();
		if (X_C_AcctSchema_Element.ELEMENTTYPE_UserElement1.equals(et) || X_C_AcctSchema_Element.ELEMENTTYPE_UserElement2.equals(et))
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(ase);
			return MColumn.getColumnName(ctx, ase.getAD_Column_ID());
		}
		return getColumnName(et);
	}	// getDisplayColumnName

}
