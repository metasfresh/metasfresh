package org.adempiere.acct.api;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_AcctSchema_Element;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;

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
	Organization(X_C_AcctSchema_Element.ELEMENTTYPE_Organization), //
	Account(X_C_AcctSchema_Element.ELEMENTTYPE_Account), //
	Product(X_C_AcctSchema_Element.ELEMENTTYPE_Product), //
	BPartner(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner), //
	OrgTrx(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx), //
	LocationFrom(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom), //
	LocationTo(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo), //
	SalesRegion(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion), //
	Project(X_C_AcctSchema_Element.ELEMENTTYPE_Project), //
	Campaign(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign), //
	UserList1(X_C_AcctSchema_Element.ELEMENTTYPE_UserList1), //
	UserList2(X_C_AcctSchema_Element.ELEMENTTYPE_UserList2), //
	Activity(X_C_AcctSchema_Element.ELEMENTTYPE_Activity), //
	SubAccount(X_C_AcctSchema_Element.ELEMENTTYPE_SubAccount), //
	UserElement1(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement1), //
	UserElement2(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement2) //
	;

	/**
	 * ElementType AD_Reference_ID=181
	 * Reference name: C_AcctSchema ElementType
	 */
	public static final int AD_REFERENCE_ID = 181; // X_C_AcctSchema_Element.ELEMENTTYPE_AD_Reference_ID;

	@Getter
	private final String code;

	AcctSchemaElementType(final String code)
	{
		this.code = code;
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
}
