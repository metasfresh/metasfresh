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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_Sponsor_SalesRep;

public class PlainSponsorDAO extends AbstractSponsorDAO
{
	// public static final String WHERE_SALESREP =
	// I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND "
	// + I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType + "='" + X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP + "'";

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSSRsAtDate(final Properties ctx, final I_C_Sponsor sponsor, final Timestamp date, final String cSponsorSalesRepType, final String trxName)
	{
		final int sponsorId = sponsor.getC_Sponsor_ID();
		Check.assume(sponsorId > 0, "Param " + sponsor + " has C_Sponsor_ID>0");
		Check.assume(date != null, "Param 'date' is not null");

		final ICompositeQueryFilter<I_C_Sponsor_SalesRep> salesRepFilter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_C_Sponsor_SalesRep.class) 
				.addFilter(ActiveRecordQueryFilter.getInstance(I_C_Sponsor_SalesRep.class))
				.addEqualsFilter(I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID, sponsorId)
				.addEqualsFilter(I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType, X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP);

		if (X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie.equals(cSponsorSalesRepType))
		{
			salesRepFilter.addEqualsFilter(I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType, X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie);
		}
		else if (X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP.equals(cSponsorSalesRepType))
		{
			salesRepFilter.addEqualsFilter(I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType, X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP);
		}

		final IQueryFilter<I_C_Sponsor_SalesRep> filter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_C_Sponsor_SalesRep.class)
				.addFilter(salesRepFilter)
				//
				.addCompareFilter(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, Operator.LessOrEqual, date)
				.addCompareFilter(I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo, Operator.GreatherOrEqual, date);

		final IQueryBuilder<I_C_Sponsor_SalesRep> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Sponsor_SalesRep.class)
				.setContext(ctx, trxName)
				.filter(filter);
		queryBuilder.orderBy().addColumn(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom);

		return queryBuilder
				.create()
				.list(I_C_Sponsor_SalesRep.class);
	}
}
