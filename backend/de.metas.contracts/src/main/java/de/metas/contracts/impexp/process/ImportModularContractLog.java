/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.impexp.process;

import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.util.lang.IMutable;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ImportModularContractLog extends SimpleImportProcessTemplate<I_I_ModCntr_Log>
{
	private static final Logger logger = LogManager.getLogger(ImportModularContractLog.class);

	@Override
	public Class<I_I_ModCntr_Log> getImportModelClass()
	{
		return I_I_ModCntr_Log.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_ModCntr_Log.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_ModCntr_Module.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{

	}

	@Override
	protected String getImportOrderBySql()
	{
		return null;
	}

	@Override
	protected I_I_ModCntr_Log retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return null;
	}

	@Override
	protected ImportRecordResult importRecord(final @NonNull IMutable<Object> stateHolder, final @NonNull I_I_ModCntr_Log importRecord, final boolean isInsertOnly) throws Exception
	{
		return null;
	}

}
