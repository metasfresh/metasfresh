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

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.X_I_ModCntr_Log;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ImportModularContractLog extends SimpleImportProcessTemplate<I_I_ModCntr_Log>
{
	private static final Logger logger = LogManager.getLogger(ImportModularContractLog.class);

	private final ModularContractLogDAO modularContractLogDAO = SpringContextHolder.instance.getBean(ModularContractLogDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

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
	protected I_I_ModCntr_Log retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_ModCntr_Log(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(
			@NonNull final IMutable<Object> stateHolder,
			@NonNull final I_I_ModCntr_Log importRecord,
			final boolean isInsertOnly) throws Exception
	{
		final LogEntryCreateRequest logEntryCreateRequest = getLogEntryCreateRequestFrom(importRecord);

		// create log entry
		modularContractLogDAO.create(logEntryCreateRequest);

		return ImportRecordResult.Inserted;
	}

	private LogEntryCreateRequest getLogEntryCreateRequestFrom(@NonNull final I_I_ModCntr_Log record)
	{
		return LogEntryCreateRequest.builder()
				.referencedRecord(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.contractId(FlatrateTermId.ofRepoId(record.getC_Flatrate_Term_ID()))
				.documentType(LogEntryDocumentType.IMPORT_LOG)
				.transactionDate(LocalDateAndOrgId.ofTimestamp(record.getDateTrx(), OrgId.ofRepoId(record.getAD_Org_ID()), orgDAO::getTimeZone))
				.collectionPointBPartnerId(BPartnerId.ofRepoId(record.getCollectionPoint_BPartner_ID()))
				.producerBPartnerId(BPartnerId.ofRepoId(record.getProducer_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoId(record.getBill_BPartner_ID()))
				.productId(ProductId.ofRepoId(record.getRecord_ID()))
				.quantity(Quantity.ofNullable(record.getQty(), uomDAO.getById(record.getC_UOM_ID())))
				.amount(Money.ofOrNull(record.getAmount(), CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID())))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.year(YearId.ofRepoId(record.getHarvesting_Year_ID()))
				.processed(false)
				.build();
	}

}
