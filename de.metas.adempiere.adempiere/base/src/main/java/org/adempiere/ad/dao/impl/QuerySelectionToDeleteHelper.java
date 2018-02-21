package org.adempiere.ad.dao.impl;

import java.util.Set;
import java.util.UUID;

import org.adempiere.ad.dao.model.I_T_Query_Selection;
import org.adempiere.ad.dao.model.I_T_Query_Selection_ToDelete;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class QuerySelectionToDeleteHelper
{
	private static final Logger logger = LogManager.getLogger(QuerySelectionToDeleteHelper.class);

	public static void scheduleDeleteSelectionNoFail(@NonNull final String uuid, final String trxName)
	{
		try
		{
			scheduleDeleteSelections(ImmutableSet.of(uuid), trxName);
		}
		catch (final Throwable ex)
		{
			logger.warn("Failed scheduling query selection {} to be deleted", uuid, ex);
		}
	}

	public static void scheduleDeleteSelection(@NonNull final String uuid, final String trxName)
	{
		scheduleDeleteSelections(ImmutableSet.of(uuid), trxName);
	}

	public static void scheduleDeleteSelections(final Set<String> uuids, final String trxName)
	{
		if (uuids.isEmpty())
		{
			return;
		}

		Services.get(ITrxManager.class)
				.getTrxListenerManagerOrAutoCommit(trxName)
				.newEventListener(TrxEventTiming.AFTER_CLOSE)
				.registerHandlingMethod(trx -> scheduleDeleteSelectionsNow(uuids));
	}

	private static void scheduleDeleteSelectionsNow(final Set<String> uuids)
	{
		if (uuids.isEmpty())
		{
			return;
		}

		final String sqlInsertInto = "INSERT INTO " + I_T_Query_Selection_ToDelete.Table_Name + "("
				+ I_T_Query_Selection_ToDelete.COLUMNNAME_UUID
				+ ")";

		StringBuilder sql = new StringBuilder();
		sql.append(sqlInsertInto);
		int counter = 0;
		for (final String uuid : uuids)
		{
			counter++;
			if (counter > 1)
			{
				sql.append(" UNION ");
			}
			sql.append("SELECT ");
			sql.append(DB.TO_STRING(uuid));

			if (counter >= 1000)
			{
				DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
				sql = new StringBuilder();
				sql.append(sqlInsertInto);
				counter = 0;
			}
		}
		if (counter > 0)
		{
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
		}

		logger.debug("{} query selections scheduled to be deleted");
	}

	public static void deleteScheduledSelectionsNoFail()
	{
		try
		{
			deleteScheduledSelections();
		}
		catch (final Throwable ex)
		{
			logger.warn("Failed deleting scheduled query selections. Ignored", ex);
		}
	}

	public static void deleteScheduledSelections()
	{
		//
		// Tag scheduled IDs
		final String executorId = UUID.randomUUID().toString();
		{
			final String sql = "UPDATE " + I_T_Query_Selection_ToDelete.Table_Name + " SET "
					+ I_T_Query_Selection_ToDelete.COLUMNNAME_Executor_UUID + "=?"
					+ " WHERE " + I_T_Query_Selection_ToDelete.COLUMNNAME_Executor_UUID + " IS NULL";
			final int count = DB.executeUpdateEx(sql, new Object[] { executorId }, ITrx.TRXNAME_None);
			if (count <= 0)
			{
				return;
			}

			logger.trace("Tagged {} selectionIds to be deleted", count);
		}

		//
		// Delete from T_Query_Selection
		{
			final String sql = "DELETE FROM " + I_T_Query_Selection.Table_Name + " t "
					+ "\n WHERE EXISTS (SELECT 1 FROM " + I_T_Query_Selection_ToDelete.Table_Name + " s "
					+ "\n       WHERE "
					+ "\n           s." + I_T_Query_Selection_ToDelete.COLUMNNAME_UUID + "=t." + I_T_Query_Selection.COLUMNNAME_UUID
					+ "\n           AND s." + I_T_Query_Selection_ToDelete.COLUMNNAME_Executor_UUID + "=?"
					+ "\n )";
			final int count = DB.executeUpdateEx(sql, new Object[] { executorId }, ITrx.TRXNAME_None);
			logger.trace("Deleted {} rows from {}", count, I_T_Query_Selection.Table_Name);
		}

		//
		// Delete scheduled IDs
		{
			final String sql = "DELETE FROM " + I_T_Query_Selection_ToDelete.Table_Name
					+ " WHERE " + I_T_Query_Selection_ToDelete.COLUMNNAME_Executor_UUID + "=?";
			final int count = DB.executeUpdateEx(sql, new Object[] { executorId }, ITrx.TRXNAME_None);
			logger.trace("Deleted {} rows from {}", count, I_T_Query_Selection_ToDelete.Table_Name);
		}
	}
}
