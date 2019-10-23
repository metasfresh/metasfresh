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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;

import de.metas.util.ISingletonService;

public interface IReplicationTrxDAO extends ISingletonService
{
	/**
	 * Replication default lookup marker
	 */
	String COLUMNNAME_IsReplicationLookupDefault = "IsReplicationLookupDefault";

	/**
	 * @param ctx
	 * @param replicationTrxName
	 * @param trxName
	 *
	 * @return the existing ReplicationTrx for replicationTrxName.
	 *
	 * @throws DBException if than one record is found
	 */
	I_EXP_ReplicationTrx retrieveReplicationTrxByName(Properties ctx, String replicationTrxName, String trxName);

	/**
	 * Retrieve replication transaction lines.
	 *
	 * Lines are ordered by AD_Table_ID/Record_ID.
	 *
	 * @param rplTrx
	 * @param tableName
	 * @param status
	 * @return
	 */
	Iterator<I_EXP_ReplicationTrxLine> retrieveReplicationTrxLines(final I_EXP_ReplicationTrx rplTrx, final String tableName, final String status);
}
