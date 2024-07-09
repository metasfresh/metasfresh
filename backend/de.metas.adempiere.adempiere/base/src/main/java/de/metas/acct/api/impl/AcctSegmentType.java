package de.metas.acct.api.impl;

import de.metas.acct.api.AcctSchemaElementType;
import org.compiere.model.I_C_ValidCombination;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Accounting segment type
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public enum AcctSegmentType
{
	Client(null),
	Organization(AcctSchemaElementType.Organization),
	Account(AcctSchemaElementType.Account),
	SubAccount(AcctSchemaElementType.SubAccount),
	Product(AcctSchemaElementType.Product),
	BPartner(AcctSchemaElementType.BPartner),
	OrgTrx(AcctSchemaElementType.OrgTrx),
	LocationFrom(AcctSchemaElementType.LocationFrom),
	LocationTo(AcctSchemaElementType.LocationTo),
	SalesRegion(AcctSchemaElementType.SalesRegion),
	Project(AcctSchemaElementType.Project),
	Campaign(AcctSchemaElementType.Campaign),
	Activity(AcctSchemaElementType.Activity),
	UserList1(AcctSchemaElementType.UserList1),
	UserList2(AcctSchemaElementType.UserList2),
	UserElement1(AcctSchemaElementType.UserElement1),
	UserElement2(AcctSchemaElementType.UserElement2),
	UserElementString1(AcctSchemaElementType.UserElementString1),
	UserElementString2(AcctSchemaElementType.UserElementString2),
	UserElementString3(AcctSchemaElementType.UserElementString3),
	UserElementString4(AcctSchemaElementType.UserElementString4),
	UserElementString5(AcctSchemaElementType.UserElementString5),
	UserElementString6(AcctSchemaElementType.UserElementString6),
	UserElementString7(AcctSchemaElementType.UserElementString7),
	UserElementNumber1(AcctSchemaElementType.UserElementNumber1),
	UserElementNumber2(AcctSchemaElementType.UserElementNumber2),
	SalesOrder(AcctSchemaElementType.SalesOrder),
	SectionCode(AcctSchemaElementType.SectionCode),
	UserElementDate1(AcctSchemaElementType.UserElementDate1),
	UserElementDate2(AcctSchemaElementType.UserElementDate2),
	HarvestingCalendar(AcctSchemaElementType.HarvestingCalendar),
	HarvestingYear(AcctSchemaElementType.HarvestingYear);

	AcctSegmentType(@SuppressWarnings("unused") final AcctSchemaElementType elementType)
	{
	}
}
