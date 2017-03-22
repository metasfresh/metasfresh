package de.metas.adempiere.service;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.util.ISingletonService;

import de.metas.process.JavaProcess;

public interface ISweepTableBL extends ISingletonService
{

	/**
	 * Method attempts to remove all records of the given table, that have the AD_Client_ID of <code>ctx</code>, together
	 * with records that reference them directly or indirectly.
	 * 
	 * @param ctx
	 * @param tableName
	 *            the name of the table whose records need to be swept.
	 * @param targetClientId
	 *            if the given number is <=0, the method tried to delete the records. If the value is >0, the method
	 *            updated all records' AD_Client_ID to the given value.
	 * @param process
	 *            may be null. If a process is given, it's {@link JavaProcess#addLog(String)} method is used to log
	 *            important messages.
	 * @param trxName
	 * @return
	 */
	boolean sweepTable(Properties ctx, String tableName, int targetClientId, JavaProcess process, String trxName);

	/**
	 * Method attempts to remove all records of the given table, that have the AD_Client_ID of <code>ctx</code> and
	 * match the given where clause, together with records that reference them directly or indirectly.
	 * 
	 * @param ctx
	 * @param tableName
	 *            the name of the table whose records need to be swept.
	 * @param whereClause
	 *            start the sweeping not with all records of <code>tableName</code>, but only with those that match this
	 *            where clause
	 * @param targetClientId
	 *            if the given number is <=0, the method tried to delete the records. If the value is >0, the method
	 *            updated all records' AD_Client_ID to the given value.
	 * @param process
	 *            may be null. If a process is given, it's {@link JavaProcess#addLog(String)} method is used to log
	 *            important messages.
	 * @param trxName
	 * @return
	 */
	boolean sweepTable(Properties ctx, String tableName, String whereClause, int targetClientId, JavaProcess process, String trxName);

	/**
	 * Method attempts to remove all records of the given table, that have the AD_Client_ID of <code>ctx</code> and
	 * have the given IDs, together with records that reference them directly or indirectly.
	 * 
	 * @param ctx
	 * @param tableName
	 *            the name of the table whose records need to be swept.
	 * @param initalIds
	 *            start the sweeping not with all records of <code>tableName</code>, but only with the given IDs
	 * @param targetClientId
	 *            if the given number is <=0, the method tried to delete the records. If the value is >0, the method
	 *            updated all records' AD_Client_ID to the given value.
	 * @param process
	 *            may be null. If a process is given, it's {@link JavaProcess#addLog(String)} method is used to log
	 *            important messages.
	 * @param trxName
	 * @return
	 */
	boolean sweepTable(Properties ctx, String tableName, Collection<Integer> initalIds, int targetClientId, JavaProcess process, String trxName);

}
