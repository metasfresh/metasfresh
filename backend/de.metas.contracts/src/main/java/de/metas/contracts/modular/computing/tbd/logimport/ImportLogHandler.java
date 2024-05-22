/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing.tbd.logimport;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.ExplainedOptional;
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
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
class ImportLogHandler extends AbstractModularContractLogHandler
{
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@Getter @NonNull private final ImportLogModularContractHandler computingMethod;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.IMPORT_LOG;

	public ImportLogHandler(@NonNull final ModularContractService modularContractService,
			final @NonNull ImportLogModularContractHandler computingMethod)
	{
		super(modularContractService);
		this.computingMethod = computingMethod;
	}

	@Override
	public @NonNull String getSupportedTableName()
	{
		return I_I_ModCntr_Log.Table_Name;
	}

	@Nullable
	private static ProductPrice getPriceActual(@NonNull final I_I_ModCntr_Log importRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(importRecord.getC_Currency_ID());
		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());

		final BigDecimal priceActual = importRecord.getPriceActual();
		final UomId productUomId = UomId.ofRepoId(importRecord.getPrice_UOM_ID());

		return ProductPrice.builder()
				.productId(productId)
				.uomId(productUomId)
				.money(Money.of(priceActual, currencyId))
				.build();
	}

	@Override
	@NonNull
	public ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final CreateLogRequest createLogRequest)
	{
		final int logId = createLogRequest.getRecordRef().getRecordIdAssumingTableName(getSupportedTableName());
		final I_I_ModCntr_Log record = InterfaceWrapperHelper.load(logId, I_I_ModCntr_Log.class);
		final YearId harvestingYearId = YearId.ofRepoIdOrNull(record.getHarvesting_Year_ID());

		if (harvestingYearId == null)
		{
			throw new AdempiereException("No Contract Config (Contract Settings / Contract Module) Match");
		}

		final ProductPrice productPrice = getPriceActual(record);
		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				//
				.referencedRecord(TableRecordReference.of(I_I_ModCntr_Log.Table_Name, record.getI_ModCntr_Log_ID()))
				.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
				.year(harvestingYearId)
				.transactionDate(LocalDateAndOrgId.ofTimestamp(record.getDateTrx(), OrgId.ofRepoId(record.getAD_Org_ID()), orgDAO::getTimeZone))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.documentType(getLogEntryDocumentType())
				.contractType(LogEntryContractType.MODULAR_CONTRACT)
				//
				.contractId(FlatrateTermId.ofRepoIdOrNull(record.getC_Flatrate_Term_ID()))
				.collectionPointBPartnerId(BPartnerId.ofRepoIdOrNull(record.getCollectionPoint_BPartner_ID()))
				.producerBPartnerId(BPartnerId.ofRepoIdOrNull(record.getProducer_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(record.getBill_BPartner_ID()))
				.invoicingGroupId(InvoicingGroupId.ofRepoIdOrNull(record.getModCntr_InvoicingGroup_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.productName(createLogRequest.getProductName())
				.quantity(Quantity.ofNullable(record.getQty(), uomDAO.getById(record.getC_UOM_ID())))
				.priceActual(productPrice)
				.amount(Money.ofOrNull(record.getAmount(), CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID())))
				.warehouseId(WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()))
				.modularContractTypeId(createLogRequest.getTypeId())
				.processed(false)
				.description(record.getDescription())
				//
				.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final IModularContractLogHandler.CreateLogRequest createLogRequest)
	{
		throw new UnsupportedOperationException();
	}
}
