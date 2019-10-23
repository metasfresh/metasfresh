package org.adempiere.server.rpl.trx.api.impl;

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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.api.IReplicationTrxDAO;

import de.metas.util.Services;

public class ReplicationTrxDAO implements IReplicationTrxDAO
{
	@Override
	public I_EXP_ReplicationTrx retrieveReplicationTrxByName(final Properties ctx, final String replicationTrxName, final String trxName)
	{
		//
		// Build query to filter by name
		final IQueryBuilder<I_EXP_ReplicationTrx> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_EXP_ReplicationTrx.class, ctx, trxName);
		queryBuilder.filter(new EqualsQueryFilter<I_EXP_ReplicationTrx>(I_EXP_ReplicationTrx.COLUMNNAME_Name, replicationTrxName));
		//
		// Retrieve first match, or throw exception if multiple transactions were found for the same name (UNIQUE INDEX)
		return queryBuilder.create()
				.firstOnly(I_EXP_ReplicationTrx.class);
	}

	@Override
	public Iterator<I_EXP_ReplicationTrxLine> retrieveReplicationTrxLines(final I_EXP_ReplicationTrx rplTrx, final String tableName, final String status)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(rplTrx);
		final String trxName = InterfaceWrapperHelper.getTrxName(rplTrx);

		final int rplTrxID = rplTrx.getEXP_ReplicationTrx_ID();
		final int tableID = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		final IQueryBuilder<I_EXP_ReplicationTrxLine> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_EXP_ReplicationTrxLine.class, ctx, trxName);

		final ICompositeQueryFilter<I_EXP_ReplicationTrxLine> filters = queryBuilder.getCompositeFilter();

		filters.addEqualsFilter(I_EXP_ReplicationTrxLine.COLUMNNAME_EXP_ReplicationTrx_ID, rplTrxID);
		filters.addEqualsFilter(I_EXP_ReplicationTrxLine.COLUMNNAME_AD_Table_ID, tableID);
		filters.addEqualsFilter(I_EXP_ReplicationTrxLine.COLUMNNAME_ReplicationTrxStatus, status);
		filters.addOnlyActiveRecordsFilter();

		return queryBuilder.create().list(I_EXP_ReplicationTrxLine.class)
				.iterator();
	}
}
