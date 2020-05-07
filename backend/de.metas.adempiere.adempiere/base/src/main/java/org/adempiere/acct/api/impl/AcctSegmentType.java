package org.adempiere.acct.api.impl;

import org.compiere.model.X_C_AcctSchema_Element;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Accounting segment type
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum AcctSegmentType
{
	Client("CL"),
	Organization(X_C_AcctSchema_Element.ELEMENTTYPE_Organization),
	Account(X_C_AcctSchema_Element.ELEMENTTYPE_Account),
	SubAccount(X_C_AcctSchema_Element.ELEMENTTYPE_SubAccount),
	Product(X_C_AcctSchema_Element.ELEMENTTYPE_Product),
	BPartner(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner),
	OrgTrx(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx),
	LocationFrom(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom),
	LocationTo(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo),
	SalesRegion(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion),
	Project(X_C_AcctSchema_Element.ELEMENTTYPE_Project),
	Campaign(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign),
	Activity(X_C_AcctSchema_Element.ELEMENTTYPE_Activity),
	UserList1(X_C_AcctSchema_Element.ELEMENTTYPE_UserList1),
	UserList2(X_C_AcctSchema_Element.ELEMENTTYPE_UserList2),
	UserElement1(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement1),
	UserElement2(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement2), ;

	private final String elementType;

	AcctSegmentType(final String elementType)
	{
		this.elementType = elementType;
	}

	public String getElementType()
	{
		return elementType;
	}
}
