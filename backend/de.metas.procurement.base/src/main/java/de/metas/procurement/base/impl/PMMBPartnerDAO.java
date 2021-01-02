package de.metas.procurement.base.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import de.metas.procurement.base.IPMMBPartnerDAO;
import de.metas.procurement.base.model.I_AD_User;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
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

public class PMMBPartnerDAO implements IPMMBPartnerDAO
{
	@Override
	public List<I_C_BPartner> retrieveAllPartnersWithProcurementUsers()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsMFProcurementUser, true)
				.andCollect(I_AD_User.COLUMN_C_BPartner_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}
}
