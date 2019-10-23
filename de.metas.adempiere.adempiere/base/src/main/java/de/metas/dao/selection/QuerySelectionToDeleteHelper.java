package de.metas.dao.selection;

import java.util.Set;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.dao.selection.model.I_T_Query_Selection;
import de.metas.dao.selection.model.I_T_Query_Selection_Pagination;
import de.metas.dao.selection.model.I_T_Query_Selection_ToDelete;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.lang.UIDStringUtil;
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

	/** Inserts the uuids in a hardcoded way, with one DB statement per 1000 uuids */
	private static void scheduleDeleteSelectionsNow(@NonNull final Set<String> uuids)
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
		Services
				.get(ITrxManager.class)
				.runInNewTrx(trxName -> deleteScheduledSelections0());
	}

	private static void deleteScheduledSelections0()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final PlainContextAware inheritedTrx = PlainContextAware.newWithThreadInheritedTrx();

		// Tag scheduled IDs
		final String executorId = UIDStringUtil.createRandomUUID();
		{
			final ICompositeQueryUpdater<I_T_Query_Selection_ToDelete> updater = queryBL
					.createCompositeQueryUpdater(I_T_Query_Selection_ToDelete.class)
					.addSetColumnValue(I_T_Query_Selection_ToDelete.COLUMNNAME_Executor_UUID, executorId);

			final int count = queryBL
					.createQueryBuilder(I_T_Query_Selection_ToDelete.class, inheritedTrx)
					.addEqualsFilter(I_T_Query_Selection_ToDelete.COLUMNNAME_Executor_UUID, null)
					.create()
					.updateDirectly(updater);

			if (count <= 0)
			{
				return;
			}
			logger.trace("Tagged {} selectionIds to be deleted", count);
		}

		final IQuery<I_T_Query_Selection_ToDelete> selectionToDeleteQuery = queryBL
				.createQueryBuilder(I_T_Query_Selection_ToDelete.class, inheritedTrx)
				.addEqualsFilter(I_T_Query_Selection_ToDelete.COLUMNNAME_Executor_UUID, executorId)
				.create();

		// Delete from T_Query_Selection
		{
			final int count = queryBL.createQueryBuilder(I_T_Query_Selection.class)
					.addInSubQueryFilter(
							I_T_Query_Selection.COLUMN_UUID,
							I_T_Query_Selection_ToDelete.COLUMN_UUID,
							selectionToDeleteQuery)
					.create()
					.deleteDirectly();
			logger.trace("Deleted {} rows from {}", count, I_T_Query_Selection.Table_Name);
		}

		// Delete from T_Query_Selection_Pagination
		{
			final int count = queryBL.createQueryBuilder(I_T_Query_Selection_Pagination.class)
					.addInSubQueryFilter(
							I_T_Query_Selection_Pagination.COLUMN_UUID,
							I_T_Query_Selection_ToDelete.COLUMN_UUID,
							selectionToDeleteQuery)
					.create()
					.deleteDirectly();
			logger.trace("Deleted {} rows from {}", count, I_T_Query_Selection.Table_Name);
		}

		// Delete scheduled IDs
		{
			final int count = selectionToDeleteQuery.deleteDirectly();
			logger.trace("Deleted {} rows from {}", count, I_T_Query_Selection_ToDelete.Table_Name);
		}
	}
}
