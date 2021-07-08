package org.adempiere.ad.dao.housekeeping.spi.impl;

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

import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.dao.selection.model.I_T_Query_Selection;
import de.metas.dao.selection.model.I_T_Query_Selection_ToDelete;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Clears several temporary tables.
 */
@Component
public class ClearTemporaryTables implements IStartupHouseKeepingTask
{
	private static final Logger logger = LogManager.getLogger(ClearTemporaryTables.class);

	@Override
	public void executeTask()
	{
		truncateTable("T_Selection");
		truncateTable("T_Selection2");

		// handeled via QuerySelectionToDeleteHelper
		// truncateTable(I_T_Query_Selection.Table_Name);
		// truncateTable(I_T_Query_Selection_ToDelete.Table_Name);
	}

	private void truncateTable(@NonNull final String tableName)
	{
		try
		{
			DB.executeUpdateEx("TRUNCATE TABLE " + tableName, ITrx.TRXNAME_None);
			Loggables.withLogger(logger, Level.DEBUG).addLog("Truncated table {}", tableName);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed truncating {}", tableName, ex);
		}
	}

}
