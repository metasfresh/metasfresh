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

package de.metas.contracts.modular.impexp;

import de.metas.calendar.standard.YearId;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.X_I_ModCntr_Log;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.sql.ResultSet;
import java.util.Objects;
import java.util.Properties;

public class ModularContractLogImportProcess extends SimpleImportProcessTemplate<I_I_ModCntr_Log>
{
	private final ModularContractService modularContractService = SpringContextHolder.instance.getBean(ModularContractService.class);

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
		final ImportRecordsSelection selection = getImportRecordsSelection();

		// update IDs and validate data
		ModCntrLogImportTableSqlUpdater.updateModularImportTable(selection);

		final int countErrorRecords = ModCntrLogImportTableSqlUpdater.countRecordsWithErrors(selection);
		getResultCollector().setCountImportRecordsWithValidationErrors(countErrorRecords);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_ModCntr_Log.COLUMNNAME_I_ModCntr_Log_ID;
	}

	@Override
	protected I_I_ModCntr_Log retrieveImportRecord(final Properties ctx, final ResultSet rs)
	{
		return new X_I_ModCntr_Log(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(
			@NonNull final IMutable<Object> stateHolder,
			@NonNull final I_I_ModCntr_Log importRecord,
			final boolean isInsertOnly) throws Exception
	{
		final boolean isImportLogRecord = isReadyForImport(importRecord);
		if (!isImportLogRecord)
		{
			return ImportRecordResult.Nothing;
		}

		createImportModCntrLog(importRecord);
		return ImportRecordResult.Inserted;
	}

	private boolean isReadyForImport(@NonNull final I_I_ModCntr_Log importRecord)
	{
		final YearId harvestingYearId = YearId.ofRepoIdOrNull(importRecord.getHarvesting_Year_ID());

		return harvestingYearId != null && Objects.equals(importRecord.getModCntr_Log_DocumentType(), X_I_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_ImportLog)
				&& Objects.equals(importRecord.getI_IsImported(), X_I_ModCntr_Log.I_ISIMPORTED_NotImported);

	}

	private void createImportModCntrLog(@NonNull final I_I_ModCntr_Log record)
	{

		modularContractService.scheduleLogCreation(DocStatusChangedEvent.builder()
														   .tableRecordReference(TableRecordReference.of(record))
														   .modelAction(ModelAction.COMPLETED)
														   .userInChargeId(Env.getLoggedUserId())
														   .build()
		);
	}

}
