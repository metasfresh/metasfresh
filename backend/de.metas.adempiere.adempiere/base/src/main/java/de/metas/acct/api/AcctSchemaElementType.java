package de.metas.acct.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.X_C_AcctSchema_Element;

import java.util.Arrays;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum AcctSchemaElementType
{
	Organization(X_C_AcctSchema_Element.ELEMENTTYPE_Organization, I_C_ValidCombination.COLUMNNAME_AD_Org_ID), //
	Account(X_C_AcctSchema_Element.ELEMENTTYPE_Account, I_C_ValidCombination.COLUMNNAME_Account_ID), //
	Product(X_C_AcctSchema_Element.ELEMENTTYPE_Product, I_C_ValidCombination.COLUMNNAME_M_Product_ID), //
	BPartner(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner, I_C_ValidCombination.COLUMNNAME_C_BPartner_ID), //
	OrgTrx(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx, I_C_ValidCombination.COLUMNNAME_AD_OrgTrx_ID), //
	LocationFrom(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom, I_C_ValidCombination.COLUMNNAME_C_LocFrom_ID), //
	LocationTo(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo, I_C_ValidCombination.COLUMNNAME_C_LocTo_ID), //
	SalesRegion(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion, I_C_ValidCombination.COLUMNNAME_C_SalesRegion_ID), //
	Project(X_C_AcctSchema_Element.ELEMENTTYPE_Project, I_C_ValidCombination.COLUMNNAME_C_Project_ID), //
	Campaign(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign, I_C_ValidCombination.COLUMNNAME_C_Campaign_ID), //
	UserList1(X_C_AcctSchema_Element.ELEMENTTYPE_UserList1, I_C_ValidCombination.COLUMNNAME_User1_ID), //
	UserList2(X_C_AcctSchema_Element.ELEMENTTYPE_UserList2, I_C_ValidCombination.COLUMNNAME_User2_ID), //
	Activity(X_C_AcctSchema_Element.ELEMENTTYPE_Activity, I_C_ValidCombination.COLUMNNAME_C_Activity_ID), //
	SubAccount(X_C_AcctSchema_Element.ELEMENTTYPE_SubAccount, I_C_ValidCombination.COLUMNNAME_C_SubAcct_ID), //
	UserElement1(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement1, I_C_ValidCombination.COLUMNNAME_UserElement1_ID), //
	UserElement2(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement2, I_C_ValidCombination.COLUMNNAME_UserElement2_ID),//
	UserElementString1(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementString1, I_C_ValidCombination.COLUMNNAME_UserElementString1),//
	UserElementString2(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementString2, I_C_ValidCombination.COLUMNNAME_UserElementString2),//
	UserElementString3(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementString3, I_C_ValidCombination.COLUMNNAME_UserElementString3),//
	UserElementString4(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementString4, I_C_ValidCombination.COLUMNNAME_UserElementString4),//
	UserElementString5(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementString5, I_C_ValidCombination.COLUMNNAME_UserElementString5),//
	UserElementString6(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementString6, I_C_ValidCombination.COLUMNNAME_UserElementString6),//
	UserElementString7(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementString7, I_C_ValidCombination.COLUMNNAME_UserElementString7),//
	UserElementNumber1(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementNumber1, I_C_ValidCombination.COLUMNNAME_UserElementNumber1),//
	UserElementNumber2(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementNumber2, I_C_ValidCombination.COLUMNNAME_UserElementNumber2),//
	SalesOrder(X_C_AcctSchema_Element.ELEMENTTYPE_SalesOrder, I_C_ValidCombination.COLUMNNAME_C_OrderSO_ID),//
	SectionCode(X_C_AcctSchema_Element.ELEMENTTYPE_SectionCode, I_C_ValidCombination.COLUMNNAME_M_SectionCode_ID),
	UserElementDate1(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementDate1, I_C_ValidCombination.COLUMNNAME_UserElementDate1),//
	UserElementDate2(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementDate2, I_C_ValidCombination.COLUMNNAME_UserElementDate2),//
	HarvestingCalendar(X_C_AcctSchema_Element.ELEMENTTYPE_HarvestingCalendar, I_C_ValidCombination.COLUMNNAME_C_Harvesting_Calendar_ID), //
	HarvestingYear(X_C_AcctSchema_Element.ELEMENTTYPE_HarvestingYear, I_C_ValidCombination.COLUMNNAME_Harvesting_Year_ID)
	;

	/**
	 * ElementType AD_Reference_ID=181
	 * Reference name: C_AcctSchema ElementType
	 */
	public static final int AD_REFERENCE_ID = X_C_AcctSchema_Element.ELEMENTTYPE_AD_Reference_ID;

	@Getter
	private final String code;
	@Getter
	private final String columnName;

	AcctSchemaElementType(@NonNull final String code, @NonNull final String columnName)
	{
		this.code = code;
		this.columnName = columnName;
	}

	public static AcctSchemaElementType ofCode(@NonNull final String code)
	{
		final AcctSchemaElementType type = ofCodeOrNull(code);
		if (type == null)
		{
			throw new AdempiereException("No " + AcctSchemaElementType.class + " found for code: " + code);
		}
		return type;
	}

	public static AcctSchemaElementType ofCodeOrNull(@NonNull final String code)
	{
		return typesByCode.get(code);
	}

	private static final ImmutableMap<String, AcctSchemaElementType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), AcctSchemaElementType::getCode);

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isDeletable()
	{
		// Acct Schema Elements "Account" and "Org" should be mandatory - teo_sarca BF [ 1795817 ]
		return this != Account && this != Organization;
	}

	public boolean isUserDefinedElements()
	{
		return this == AcctSchemaElementType.UserList1
				|| this == AcctSchemaElementType.UserList2
				|| this == AcctSchemaElementType.UserElement1
				|| this == AcctSchemaElementType.UserElement2;

	}
}
