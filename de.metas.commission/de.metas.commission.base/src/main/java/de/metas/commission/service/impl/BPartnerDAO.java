package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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

import org.compiere.model.Query;

import de.metas.commission.interfaces.I_AD_User;
import de.metas.commission.interfaces.I_C_BPartner_Location;
import de.metas.commission.service.IBPartnerDAO;

public class BPartnerDAO implements IBPartnerDAO
{
	@Override
	public I_AD_User retrieveCommissionContact(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final String tableName = org.compiere.model.I_AD_User.Table_Name;

		final String whereClause = org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID + "=? AND "
				+ I_AD_User.COLUMNNAME_IS_COMMISSION + "='Y'";

		final Object[] parameters = { bPartnerId };

		return new Query(ctx, tableName, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.firstOnly(I_AD_User.class);
	}

	@Override
	public I_C_BPartner_Location retrieveCommissionToLocation(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final String tableName = org.compiere.model.I_C_BPartner_Location.Table_Name;

		final String whereClause = org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + "=? AND "
				+ I_C_BPartner_Location.COLUMNNAME_IsCommissionTo + "='Y'";

		final Object[] parameters = { bPartnerId };
		final String orderBy = I_C_BPartner_Location.COLUMNNAME_IsCommissionToDefault + " DESC";

		return new Query(ctx, tableName, whereClause, trxName).setParameters(
				parameters).setOnlyActiveRecords(true).setOrderBy(orderBy)
				.first(I_C_BPartner_Location.class);
	}
}
