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


import java.util.Collection;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.Query;

import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.service.ICommissionConditionDAO;

public abstract class AbstractCommissionConditionDAO implements ICommissionConditionDAO
{
	@Override
	public I_C_AdvCommissionCondition retrieveForOrphanedSponsors(
			final Properties ctx,
			final I_C_AdvComSystem cAdvComSystem,
			final String trxName)
	{
		assert cAdvComSystem != null : "cAdvComSystem may not be null";

		final ICompositeQueryFilter<I_C_AdvCommissionCondition> filter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_C_AdvCommissionCondition.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AdvCommissionCondition.COLUMNNAME_C_AdvComSystem_ID, cAdvComSystem.getC_AdvComSystem_ID())
				.addEqualsFilter(I_C_AdvCommissionCondition.COLUMNNAME_IsDefaultForOrphandedSponsors, true);

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_AdvCommissionCondition.class)
				.setContext(ctx, trxName)
				.filter(filter)
				.create()
				.firstOnly(I_C_AdvCommissionCondition.class);

		// final String wc = I_C_AdvCommissionCondition.COLUMNNAME_C_AdvComSystem_ID + "=? AND " + I_C_AdvCommissionCondition.COLUMNNAME_IsDefaultForOrphandedSponsors + "='Y'";
		// return new Query(ctx, I_C_AdvCommissionCondition.Table_Name, wc, trxName)
		// .setParameters(cAdvComSystem.getC_AdvComSystem_ID())
		// .setOnlyActiveRecords(true)
		// .firstOnly(I_C_AdvCommissionCondition.class);
	}

	@Override
	public Collection<I_C_AdvCommissionCondition> retrieve(final Properties ctx, final int orgId, final String trxName)
	{
		final String wc = I_C_AdvCommissionCondition.COLUMNNAME_AD_Org_ID + "=?";

		return new Query(ctx, I_C_AdvCommissionCondition.Table_Name, wc, trxName)
				.setClient_ID()
				.setParameters(orgId)
				.setOnlyActiveRecords(true)
				.list(I_C_AdvCommissionCondition.class);
	}

	@Override
	public I_C_AdvCommissionCondition retrieveDefault(final Properties ctx, final I_C_AdvComSystem comSystem, final String trxName)
	{
		assert comSystem != null : "comSystem may not be null";

		final String wc = I_C_AdvCommissionCondition.COLUMNNAME_C_AdvComSystem_ID + "=? AND " + I_C_AdvCommissionCondition.COLUMNNAME_IsDefault + "='Y'";

		return new Query(ctx, I_C_AdvCommissionCondition.Table_Name, wc, trxName)
				.setClient_ID()
				.setParameters(comSystem.getC_AdvComSystem_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_AdvCommissionCondition.COLUMNNAME_Name)
				.first(I_C_AdvCommissionCondition.class);
	}

}
