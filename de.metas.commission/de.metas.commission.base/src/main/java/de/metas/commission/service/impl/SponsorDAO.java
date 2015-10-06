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

import org.adempiere.util.Check;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_Sponsor_SalesRep;

public class SponsorDAO extends AbstractSponsorDAO
{

	
	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSSRsAtDate(final Properties ctx, final I_C_Sponsor sponsor, final Timestamp date, final String cSponsorSalesRepType, final String trxName)
	{
		Check.assume(sponsor.getC_Sponsor_ID() > 0, "Param " + sponsor + " has C_Sponsor_ID>0");
		Check.assume(date != null, "Param 'date' is not null");
		
		return retrieveSSRsAtDatel(ctx, sponsor.getC_Sponsor_ID(), date, cSponsorSalesRepType, trxName);
	}

	@Cached
	/* package */ List<I_C_Sponsor_SalesRep> retrieveSSRsAtDatel(@CacheCtx final Properties ctx, 
			final int sponsorId, 
			final Timestamp date, 
			final String cSponsorSalesRepType, 
			@CacheTrx final String trxName)
	{
		final StringBuilder wc = new StringBuilder();
		if (X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie.equals(cSponsorSalesRepType))
		{
			wc.append(AbstractSponsorDAO.WHERE_PARENT_SPONSOR + " AND ");
		}
		else if (X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP.equals(cSponsorSalesRepType))
		{
			wc.append(AbstractSponsorDAO.WHERE_SALESREP + " AND ");
		}
		else if (Check.isEmpty(cSponsorSalesRepType))
		{
			wc.append(I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND ");
		}

		wc.append(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=?");

		final Object[] parameters = { sponsorId, date, date };

		final String orderBy = I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom;

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, wc.toString(), trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_Sponsor_SalesRep.class);
	}

	// @Override
	// @Cached
	// public List<I_C_Sponsor_SalesRep> retrieveParentLinks(
	// final @CacheCtx Properties ctx,
	// final int sponsorID,
	// final Timestamp validFrom,
	// final Timestamp validTo,
	// final String trxName)
	// {
	// final String whereClause =
	// I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND " //
	// + I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID + ">0 AND (" //
	// + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + ">=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=?" +
	// " OR "
	// + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=?" + ")";
	//
	// final Object[] parameters = { sponsorID,
	// validFrom, validTo, validFrom, validFrom };
	//
	// final String orderBy = I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom;
	//
	// return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
	// .setParameters(parameters)
	// .setOnlyActiveRecords(true)
	// .setOrderBy(orderBy)
	// .list(I_C_Sponsor_SalesRep.class);
	// }

}
