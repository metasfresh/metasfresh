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

package de.metas.contracts.modular.computing.purchasecontract.manufacturing.coproduct;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingCoReceipt;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingFacadeService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.LogSubEntryId;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

@Component
public class PPCoManufacturingOrderLog extends AbstractModularContractLogHandler
{
	private static final AdMessageKey MSG_DESCRIPTION_RECEIPT = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt");

	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;
	@NonNull protected final ManufacturingFacadeService manufacturingFacadeService;

	@Getter @NonNull private final String supportedTableName = I_PP_Cost_Collector.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PRODUCTION;
	@Getter @NonNull private final PPCoComputingMethod computingMethod;

	public PPCoManufacturingOrderLog(
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ManufacturingFacadeService manufacturingFacadeService,
			@NonNull final ModularContractService modularContractService,
			@NonNull final PPCoComputingMethod computingMethod)
	{
		super(modularContractService);

		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.manufacturingFacadeService = manufacturingFacadeService;
		this.computingMethod = computingMethod;
	}

	@Override
	public boolean applies(final @NonNull CreateLogRequest request)
	{
		return manufacturingFacadeService.getManufacturingCoReceiptIfApplies(request.getRecordRef()).isPresent();
	}

	@Override
	@NonNull
	public final ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		final ManufacturingCoReceipt manufacturingCoReceipt = manufacturingFacadeService.getManufacturingCoReceipt(request.getRecordRef());
		final ProductId productId = request.getProductId();
		final Quantity qtyReceived = manufacturingCoReceipt.getQtyReceived().abs();

		final FlatrateTermId contractId = request.getContractId();
		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(contractId);
		final ProductPrice price = getContractSpecificPriceWithFlags(request).toProductPrice();

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(contractId)
											.referencedRecord(manufacturingCoReceipt.getManufacturingOrderId().toRecordRef())
											.subEntryId(LogSubEntryId.ofCostCollectorId(manufacturingCoReceipt.getId()))
											.productId(productId)
											.productName(request.getProductName())
											.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID()))
											.warehouseId(manufacturingCoReceipt.getWarehouseId())
											.documentType(getLogEntryDocumentType())
											.contractType(getLogEntryContractType())
											.soTrx(SOTrx.PURCHASE)
											.quantity(qtyReceived)
											.transactionDate(manufacturingCoReceipt.getTransactionDate()
																	 .toLocalDateAndOrgId(orgDAO::getTimeZone))
											.year(request.getYearId())
											.description(msgBL.getBaseLanguageMsg(MSG_DESCRIPTION_RECEIPT,
																				  qtyReceived.toString(),
																				  productBL.getProductValueAndName(manufacturingCoReceipt.getCoProductId())))
											.modularContractTypeId(request.getTypeId())
											.configModuleId(request.getConfigId().getModularContractModuleId())
											.collectionPointBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getDropShip_BPartner_ID()))
											.invoicingGroupId(modCntrInvoicingGroupRepository
																	  .getInvoicingGroupIdFor(productId, request.getModularContractSettings().getYearAndCalendarId())
																	  .orElse(null))
											.isBillable(true)
											.priceActual(price)
											.amount(price.computeAmount(qtyReceived, uomConversionBL))
											.build());
	}

	@Override
	@NonNull
	public final ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public final LogEntryDeleteRequest toLogEntryDeleteRequest(@NonNull final HandleLogsRequest handleLogsRequest, @NonNull final ModularContractModuleId modularContractModuleId)
	{
		final ManufacturingCoReceipt manufacturingCoReceipt = manufacturingFacadeService.getManufacturingCoReceipt(handleLogsRequest.getTableRecordReference());

		return LogEntryDeleteRequest.builder()
				.referencedModel(manufacturingCoReceipt.getManufacturingOrderId().toRecordRef())
				.subEntryId(LogSubEntryId.ofCostCollectorId(manufacturingCoReceipt.getId()))
				.flatrateTermId(handleLogsRequest.getContractId())
				.logEntryContractType(getLogEntryContractType())
				.modularContractModuleId(modularContractModuleId)
				.build();
	}
}
