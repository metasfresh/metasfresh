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
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.model.X_I_ModCntr_Log;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.contracts.modular.settings.ModularContractType;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.contracts.modular.settings.ModuleConfigId;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class ModularContractLogImportProcess extends SimpleImportProcessTemplate<I_I_ModCntr_Log>
{
	private final ModularContractLogDAO modularContractLogDAO = SpringContextHolder.instance.getBean(ModularContractLogDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final int importModCntrLogTableID = Services.get(IADTableDAO.class).retrieveTableId(I_I_ModCntr_Log.Table_Name);

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
		final boolean isImportLogRecord = isImportLog(importRecord);
		if (!isImportLogRecord)
		{
			return ImportRecordResult.Nothing;
		}

		final Optional<LogEntryCreateRequest> logEntryCreateRequest = getLogEntryCreateRequestFrom(importRecord);
		if (logEntryCreateRequest.isPresent())
		{
			modularContractLogDAO.create(logEntryCreateRequest.get());
			return ImportRecordResult.Inserted;
		}

		return ImportRecordResult.Nothing;
	}

	private Optional<LogEntryCreateRequest> getLogEntryCreateRequestFrom(@NonNull final I_I_ModCntr_Log record)
	{
		final YearId harvestingYearId = YearId.ofRepoIdOrNull(record.getHarvesting_Year_ID());
		final Optional<ModuleConfig> config = getModuleConfigFrom(record);

		if (Objects.isNull(harvestingYearId) || config.isEmpty())
		{
			throw new AdempiereException("No Contract Config (Contract Settings / Contract Module) Match");
		}

		final ProductPrice productPrice = getPriceActual(record);
		return Optional.of(LogEntryCreateRequest.builder()
								   //
								   .referencedRecord(TableRecordReference.of(importModCntrLogTableID, record.getI_ModCntr_Log_ID()))
								   .configId(config.get().getId())
								   .year(harvestingYearId)
								   .transactionDate(LocalDateAndOrgId.ofTimestamp(record.getDateTrx(), OrgId.ofRepoId(record.getAD_Org_ID()), orgDAO::getTimeZone))
								   .soTrx(SOTrx.ofBoolean(record.isSOTrx()))
								   .documentType(LogEntryDocumentType.IMPORT_LOG) // dev note : all imported logs should have ImportLog as doctype
								   .contractType(LogEntryContractType.MODULAR_CONTRACT)
								   //
								   .contractId(FlatrateTermId.ofRepoIdOrNull(record.getC_Flatrate_Term_ID()))
								   .collectionPointBPartnerId(BPartnerId.ofRepoIdOrNull(record.getCollectionPoint_BPartner_ID()))
								   .producerBPartnerId(BPartnerId.ofRepoIdOrNull(record.getProducer_BPartner_ID()))
								   .invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(record.getBill_BPartner_ID()))
								   .invoicingGroupId(InvoicingGroupId.ofRepoIdOrNull(record.getModCntr_InvoicingGroup_ID()))
								   .productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
								   .quantity(Quantity.ofNullable(record.getQty(), uomDAO.getById(record.getC_UOM_ID())))
								   .priceActual(productPrice)
								   .amount(Money.ofOrNull(record.getAmount(), CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID())))
								   .warehouseId(WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()))
								   .processed(false)
								   .description(record.getDescription())
								   //
								   .build());
	}

	private boolean isImportLog(@NonNull final I_I_ModCntr_Log importRecord)
	{
		return Objects.equals(importRecord.getModCntr_Log_DocumentType(), X_I_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_ImportLog)
				&& Objects.equals(importRecord.getI_IsImported(), "N");
	}

	@NonNull
	private Optional<ModuleConfig> getModuleConfigFrom(@NonNull final I_I_ModCntr_Log importRecord)
	{
		final I_ModCntr_Module module = importRecord.getModCntr_Module();
		if (Objects.isNull(module))
		{
			return Optional.empty();
		}

		final I_ModCntr_Settings settings = module.getModCntr_Settings();
		final I_ModCntr_Type modCntrType = module.getModCntr_Type();
		final ModularContractSettingsId settingsId = ModularContractSettingsId.ofRepoId(settings.getModCntr_Settings_ID());
		return Optional.of(ModuleConfig.builder()
								   .id(ModuleConfigId.ofRepoId(settingsId, module.getModCntr_Module_ID()))
								   .name(module.getName())
								   .productId(ProductId.ofRepoId(module.getM_Product_ID()))
								   .seqNo(SeqNo.ofInt(module.getSeqNo()))
								   .invoicingGroup(module.getInvoicingGroup())
								   .modularContractType(ModularContractType.builder()
																.id(ModularContractTypeId.ofRepoId(modCntrType.getModCntr_Type_ID()))
																.value(modCntrType.getValue())
																.name(modCntrType.getName())
																.build())
								   .build());

	}

	@Nullable
	private ProductPrice getPriceActual(@NonNull final I_I_ModCntr_Log importRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(importRecord.getC_Currency_ID());
		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());

		final BigDecimal priceActual = importRecord.getPriceActual();
		final UomId productUomId = UomId.ofRepoId(importRecord.getPrice_UOM_ID());  // Price_UOM_ID already provided in thr excel template

		return ProductPrice.builder()
				.productId(productId)
				.uomId(productUomId)
				.money(Money.of(priceActual, currencyId))
				.build();
	}

}
