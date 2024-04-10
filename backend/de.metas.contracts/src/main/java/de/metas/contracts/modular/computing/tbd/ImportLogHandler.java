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

package de.metas.contracts.modular.computing.tbd;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.computing.IModularContractComputingMethodHandler;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class ImportLogHandler implements IModularContractLogHandler
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull
	private final ImportLogModularContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final IModularContractLogHandler.HandleLogsRequest request)
	{
		return switch (request.getModelAction())
		{
			case COMPLETED -> LogAction.CREATE;
			case REVERSED -> LogAction.REVERSE;
			case RECREATE_LOGS -> LogAction.RECOMPUTE;
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		};
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
		final int logId = createLogRequest.getHandleLogsRequest().getTableRecordReference().getRecordIdAssumingTableName(I_I_ModCntr_Log.Table_Name);
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
				.configId(createLogRequest.getConfigId())
				.year(harvestingYearId)
				.transactionDate(LocalDateAndOrgId.ofTimestamp(record.getDateTrx(), OrgId.ofRepoId(record.getAD_Org_ID()), orgDAO::getTimeZone))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.documentType(LogEntryDocumentType.IMPORT_LOG)
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
				.modularContractTypeId(createLogRequest.getTypeId())
				.processed(false)
				.description(record.getDescription())
				//
				.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final IModularContractLogHandler.HandleLogsRequest handleLogsRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull IModularContractComputingMethodHandler getComputingMethod()
	{
		return contractHandler;
	}

	@Override
	public @NonNull Optional<ProductId> getProductId(final @NonNull HandleLogsRequest handleLogsRequest)
	{
		final int logId = handleLogsRequest.getTableRecordReference().getRecordIdAssumingTableName(I_I_ModCntr_Log.Table_Name);
		final I_I_ModCntr_Log record = InterfaceWrapperHelper.load(logId, I_I_ModCntr_Log.class);
		return Optional.of(record.getM_Product_ID())
				.map(ProductId::ofRepoId);
	}

	@Override
	public @NonNull String getSupportedTableName()
	{
		return I_I_ModCntr_Log.Table_Name;
	}

}
