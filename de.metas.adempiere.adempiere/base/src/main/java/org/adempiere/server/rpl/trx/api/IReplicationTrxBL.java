package org.adempiere.server.rpl.trx.api;

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

import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;

import de.metas.util.ISingletonService;

public interface IReplicationTrxBL extends ISingletonService
{
	/**
	 * <b>NOTE:</b> Assume selected <code>tableName</code> not ignored.<br>
	 * <br>
	 * Get (if available), or create {@link I_EXP_ReplicationTrx} for <code>replicationTrxName</code>.<br>
	 * Create new (empty) {@link I_EXP_ReplicationTrxLine}, and bind it to the selected {@link I_EXP_ReplicationTrx}.
	 *
	 * @param ctx
	 * @param tableName
	 * @param replicationTrxName
	 * @param trxName
	 * @return the new {@link I_EXP_ReplicationTrxLine}
	 */
	I_EXP_ReplicationTrxLine createAndMatchVoidReplicationTrxLine(Properties ctx, String tableName, String replicationTrxName, String trxName);

	/**
	 * Add table to ignore list. Ignored tables will not be included in transactions.
	 *
	 * @param tableName
	 */
	void addTableToIgnoreList(String tableName);

	/**
	 * @param tableName
	 * @return true if table is ignored, false otherwise
	 */
	boolean isTableIgnored(String tableName);
}
