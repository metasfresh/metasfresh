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


import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverDAO;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;

import de.metas.util.Check;
import de.metas.util.Services;

public class ReplicationIssueSolverDAO implements IReplicationIssueSolverDAO
{
	@Override
	public IReplicationIssueAware retrieveReplicationIssueAware(final I_EXP_ReplicationTrxLine trxLine)
	{
		Check.assumeNotNull(trxLine, "trxLine not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(trxLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(trxLine);

		final int adTableId = trxLine.getAD_Table_ID();
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		final int recordId = trxLine.getRecord_ID();

		final IReplicationIssueAware issueAware = InterfaceWrapperHelper.create(ctx, tableName, recordId, IReplicationIssueAware.class, trxName);
		return issueAware;
	}
}
