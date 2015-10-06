package org.adempiere.server.rpl.trx.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.concurrent.CopyOnWriteArraySet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.api.IReplicationTrxBL;
import org.adempiere.server.rpl.trx.api.IReplicationTrxDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnable;

public class ReplicationTrxBL implements IReplicationTrxBL
{
	private final CopyOnWriteArraySet<String> excludeTableNames = new CopyOnWriteArraySet<String>();

	private int getCreateReplicationTrx(final Properties ctx, final String replicationTrxName, final String trxName_NOTUSED_YET)
	{
		//
		// FIXME Do not run out of transaction! (this creates the risk of having empty transactions)
		//
		final I_EXP_ReplicationTrx replicationTrx = Services.get(IReplicationTrxDAO.class).retrieveReplicationTrxByName(ctx, replicationTrxName, ITrx.TRXNAME_None);
		if (replicationTrx != null)
		{
			return replicationTrx.getEXP_ReplicationTrx_ID();
		}

		//
		// FIXME Do not run out of transaction! (this creates the risk of having empty transactions)
		//
		final I_EXP_ReplicationTrx[] result = new I_EXP_ReplicationTrx[] { null };
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final I_EXP_ReplicationTrx replicationTrxNew = InterfaceWrapperHelper.create(ctx, I_EXP_ReplicationTrx.class, localTrxName);
				replicationTrxNew.setName(replicationTrxName);
				InterfaceWrapperHelper.save(replicationTrxNew);

				result[0] = replicationTrxNew;
			}
		});

		return result[0].getEXP_ReplicationTrx_ID();
	}

	@Override
	public I_EXP_ReplicationTrxLine createAndMatchVoidReplicationTrxLine(final Properties ctx, final String tableName, final String replicationTrxName, final String trxName)
	{
		Check.assume(!isTableIgnored(tableName), "tableName not ignored");

		final int replicationTrxId = getCreateReplicationTrx(ctx, replicationTrxName, trxName);

		//
		// Create a new ReplicationTrxLine
		final I_EXP_ReplicationTrxLine replicationTrxLine = InterfaceWrapperHelper.create(ctx, I_EXP_ReplicationTrxLine.class, trxName);
		replicationTrxLine.setEXP_ReplicationTrx_ID(replicationTrxId);
		return replicationTrxLine;
	}

	@Override
	public void addTableToIgnoreList(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		excludeTableNames.add(tableName);
	}

	@Override
	public boolean isTableIgnored(final String tableName)
	{
		return excludeTableNames.contains(tableName);
	}
}
