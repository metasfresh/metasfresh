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

package de.metas.contracts.modular.computing.purchasecontract.sales.processed;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.LogSubEntryId;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessedSalesManufacturingOrderLog implements IModularContractLogHandler
{
	private static final AdMessageKey MSG_DESCRIPTION_RECEIPT = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt");

	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final ManufacturingFacadeService manufacturingFacadeService;
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;
	@NonNull private final ModularContractService modularContractService;

	@Getter @NonNull private final ProcessedSalesComputingMethod computingMethod;
	@Getter @NonNull private final String supportedTableName = I_PP_Cost_Collector.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PRODUCTION;

	public boolean applies(@NonNull final CreateLogRequest request)
	{
		final ManufacturingReceipt manufacturingReceipt = manufacturingFacadeService.getManufacturingReceiptIfApplies(request.getRecordRef()).orElse(null);
		return manufacturingReceipt != null
				&& ProductId.equals(manufacturingReceipt.getProductId(), request.getProductId());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final ManufacturingReceipt manufacturingReceipt = manufacturingFacadeService.getManufacturingReceipt(createLogRequest.getRecordRef());
		final ProductId productId = manufacturingReceipt.getProductId();
		final InstantAndOrgId transactionDate = manufacturingReceipt.getTransactionDate();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, createLogRequest.getModularContractSettings().getYearAndCalendarId()).orElse(null);
		final String productName = productBL.getProductValueAndName(productId);
		final Quantity qty = manufacturingReceipt.getQtyReceived();
		final String description = msgBL.getBaseLanguageMsg(MSG_DESCRIPTION_RECEIPT, qty.abs().toString(), productName);

		final FlatrateTermId contractId = createLogRequest.getContractId();
		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(contractId);
		final BPartnerId invoicingBPartnerId = BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID());
		final BPartnerId collectionPointBPartnerId = BPartnerId.ofRepoIdOrNull(modularContractRecord.getDropShip_BPartner_ID());
		final ProductPrice price = modularContractService.getContractSpecificPrice(createLogRequest.getModularContractModuleId(), contractId);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(contractId)
				.referencedRecord(manufacturingReceipt.getManufacturingOrderId().toRecordRef())
				.subEntryId(LogSubEntryId.ofCostCollectorId(manufacturingReceipt.getId()))
				.productId(productId)
				.productName(createLogRequest.getProductName())
				.invoicingBPartnerId(invoicingBPartnerId)
				.warehouseId(manufacturingReceipt.getWarehouseId())
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(SOTrx.PURCHASE)
				.quantity(qty)
				.transactionDate(transactionDate.toLocalDateAndOrgId(orgDAO::getTimeZone))
				.year(createLogRequest.getYearId())
				.description(description)
				.modularContractTypeId(createLogRequest.getTypeId())
				.configId(createLogRequest.getConfigId())
				.collectionPointBPartnerId(collectionPointBPartnerId)
				.invoicingGroupId(invoicingGroupId)
				.isBillable(true)
				.priceActual(price)
				.amount(price.computeAmount(qty, uomConversionBL))
				.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public LogEntryDeleteRequest toLogEntryDeleteRequest(@NonNull final HandleLogsRequest handleLogsRequest)
	{
		final ManufacturingReceipt manufacturingReceipt = manufacturingFacadeService.getManufacturingReceipt(handleLogsRequest.getTableRecordReference());

		return LogEntryDeleteRequest.builder()
				.referencedModel(manufacturingReceipt.getManufacturingOrderId().toRecordRef())
				.subEntryId(LogSubEntryId.ofCostCollectorId(manufacturingReceipt.getId()))
				.flatrateTermId(handleLogsRequest.getContractId())
				.logEntryContractType(getLogEntryContractType())
				.build();
	}
}
