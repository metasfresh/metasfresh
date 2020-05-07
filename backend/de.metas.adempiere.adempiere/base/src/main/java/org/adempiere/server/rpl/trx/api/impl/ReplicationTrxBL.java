package org.adempiere.server.rpl.trx.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.PlainContextAware;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.api.IReplicationTrxBL;
import org.adempiere.server.rpl.trx.api.IReplicationTrxDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

public class ReplicationTrxBL implements IReplicationTrxBL
{
	private final CopyOnWriteArraySet<String> excludeTableNames = new CopyOnWriteArraySet<>();

	private int getCreateReplicationTrx(final Properties ctx, final String replicationTrxName, final String trxName)
	{
		final I_EXP_ReplicationTrx replicationTrx = Services.get(IReplicationTrxDAO.class).retrieveReplicationTrxByName(ctx, replicationTrxName, ITrx.TRXNAME_None);
		if (replicationTrx != null)
		{
			return replicationTrx.getEXP_ReplicationTrx_ID();
		}

		final I_EXP_ReplicationTrx replicationTrxNew = newInstance(
				I_EXP_ReplicationTrx.class,
				PlainContextAware.newWithTrxName(ctx, trxName));

		replicationTrxNew.setName(replicationTrxName);
		save(replicationTrxNew);

		return replicationTrxNew.getEXP_ReplicationTrx_ID();
	}

	@Override
	public I_EXP_ReplicationTrxLine createAndMatchVoidReplicationTrxLine(final Properties ctx, final String tableName, final String replicationTrxName, final String trxName)
	{
		Check.assume(!isTableIgnored(tableName), "tableName not ignored");

		final int replicationTrxId = getCreateReplicationTrx(ctx, replicationTrxName, trxName);

		//
		// Create a new ReplicationTrxLine
		final I_EXP_ReplicationTrxLine replicationTrxLine = newInstance(
				I_EXP_ReplicationTrxLine.class,
				PlainContextAware.newWithTrxName(ctx, trxName));
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
