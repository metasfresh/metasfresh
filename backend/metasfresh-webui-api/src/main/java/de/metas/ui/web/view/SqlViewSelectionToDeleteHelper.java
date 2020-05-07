package de.metas.ui.web.view;

import java.util.Set;
import java.util.UUID;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelectionLine;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection_ToDelete;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
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
public class SqlViewSelectionToDeleteHelper
{
	private static final Logger logger = LogManager.getLogger(SqlViewSelectionToDeleteHelper.class);

	public static void scheduleDeleteSelections(@NonNull final Set<String> selectionIds)
	{
		if (selectionIds.isEmpty())
		{
			return;
		}

		final String sqlInsertInto = "INSERT INTO " + I_T_WEBUI_ViewSelection_ToDelete.Table_Name + "("
				+ I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_View_UUID
				+ ") VALUES ";

		SqlAndParams.Builder sqlBuilder = null;
		for (final String selectionId : selectionIds)
		{
			if (sqlBuilder == null)
			{
				sqlBuilder = SqlAndParams.builder()
						.append(sqlInsertInto);
			}

			sqlBuilder.appendIfHasParameters(", ").append("(?)", selectionId);

			if (sqlBuilder.getParametersCount() >= 1000)
			{
				final SqlAndParams sql = sqlBuilder.build();
				DB.executeUpdateEx(sql.getSql(), sql.getSqlParamsArray(), ITrx.TRXNAME_None);
				sqlBuilder = null;
			}
		}

		if (sqlBuilder != null)
		{
			final SqlAndParams sql = sqlBuilder.build();
			DB.executeUpdateEx(sql.getSql(), sql.getSqlParamsArray(), ITrx.TRXNAME_None);
			sqlBuilder = null;
		}

		logger.debug("{} view selections scheduled to be deleted");
	}

	public static void deleteScheduledSelectionsNoFail()
	{
		try
		{
			deleteScheduledSelections();
		}
		catch (final Throwable ex)
		{
			logger.warn("Failed deleting scheduled view selections. Ignored", ex);
		}
	}

	public static void deleteScheduledSelections()
	{
		//
		// Tag scheduled IDs
		final String executorId = UUID.randomUUID().toString();
		{
			final String sql = "UPDATE " + I_T_WEBUI_ViewSelection_ToDelete.Table_Name + " SET "
					+ I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_Executor_UUID + "=?"
					+ " WHERE " + I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_Executor_UUID + " IS NULL";
			final int count = DB.executeUpdateEx(sql, new Object[] { executorId }, ITrx.TRXNAME_None);
			if (count <= 0)
			{
				return;
			}

			logger.trace("Tagged {} selectionIds to be deleted", count);
		}

		//
		// Delete from T_WEBUI_ViewSelectionLine
		{
			final String sql = "DELETE FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name + " t "
					+ "\n WHERE EXISTS (SELECT 1 FROM " + I_T_WEBUI_ViewSelection_ToDelete.Table_Name + " s "
					+ "\n       WHERE "
					+ "\n           s." + I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_View_UUID + "=t." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
					+ "\n           AND s." + I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_Executor_UUID + "=?"
					+ "\n )";
			final int count = DB.executeUpdateEx(sql, new Object[] { executorId }, ITrx.TRXNAME_None);
			logger.trace("Deleted {} rows from {}", count, I_T_WEBUI_ViewSelectionLine.Table_Name);
		}

		//
		// Delete from T_WEBUI_ViewSelection
		{
			final String sql = "DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name + " t "
					+ "\n WHERE EXISTS (SELECT 1 FROM " + I_T_WEBUI_ViewSelection_ToDelete.Table_Name + " s "
					+ "\n       WHERE "
					+ "\n           s." + I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_View_UUID + "=t." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
					+ "\n           AND s." + I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_Executor_UUID + "=?"
					+ "\n )";
			final int count = DB.executeUpdateEx(sql, new Object[] { executorId }, ITrx.TRXNAME_None);
			logger.trace("Deleted {} rows from {}", count, I_T_WEBUI_ViewSelection.Table_Name);
		}

		//
		// Delete scheduled IDs
		{
			final String sql = "DELETE FROM " + I_T_WEBUI_ViewSelection_ToDelete.Table_Name
					+ " WHERE " + I_T_WEBUI_ViewSelection_ToDelete.COLUMNNAME_Executor_UUID + "=?";
			final int count = DB.executeUpdateEx(sql, new Object[] { executorId }, ITrx.TRXNAME_None);
			logger.trace("Deleted {} rows from {}", count, I_T_WEBUI_ViewSelection_ToDelete.Table_Name);
		}

	}
}
