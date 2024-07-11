/**
 *
 */
package de.metas.impexp.processing;

import de.metas.impexp.DataImportRunId;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class DBFunctionHelper
{
	private static final transient Logger logger = LogManager.getLogger(DBFunctionHelper.class);

	/**
	 * Used to call import DB functions using c_dataimport_id and record_id as parameters
	 */
	public static void doDBFunctionCall(
			@NonNull final DBFunction function,
			@Nullable final DataImportConfigId dataImportConfigId,
			final int recordId)
	{
		final String sql = new StringBuilder()
				.append("SELECT ")
				.append(function.getSchema())
				.append(".")
				.append(function.getName())
				.append("(?,?)")
				.toString();

		final Object[] sqlParams = new Object[] {
				DataImportConfigId.toRepoId(dataImportConfigId),
				recordId };

		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
		logger.debug("\nExecuted {} with params: {}", function, sqlParams);
	}

	/**
	 * Used to call import DB functions using c_dataimport_run_id
	 */
	public static void doDBFunctionCall(
			@NonNull final DBFunction function,
			@Nullable final DataImportRunId dataImportRunId)
	{
		final String sql = new StringBuilder()
				.append("SELECT ")
				.append(function.getSchema())
				.append(".")
				.append(function.getName())
				.append("(?)")
				.toString();

		final Object[] sqlParams = new Object[] {
				DataImportRunId.toRepoId(dataImportRunId) };

		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
		logger.debug("\nExecuted {} with params: {}", function, sqlParams);
	}
}
