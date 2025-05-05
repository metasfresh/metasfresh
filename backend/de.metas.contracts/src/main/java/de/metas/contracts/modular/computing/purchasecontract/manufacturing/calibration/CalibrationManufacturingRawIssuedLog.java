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

package de.metas.contracts.modular.computing.purchasecontract.manufacturing.calibration;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ProductPriceWithFlags;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingFacadeService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingRawIssued;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
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
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

@Component
public class CalibrationManufacturingRawIssuedLog extends AbstractModularContractLogHandler
{
	private static final AdMessageKey MSG_DESCRIPTION_ISSUE = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue");

	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;
	@NonNull protected final ManufacturingFacadeService manufacturingFacadeService;

	@Getter @NonNull private final String supportedTableName = I_PP_Cost_Collector.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PRODUCTION;
	@Getter @NonNull private final PPCalibrationComputingMethod computingMethod;

	public CalibrationManufacturingRawIssuedLog(@NonNull final ModularContractService modularContractService,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull ManufacturingFacadeService manufacturingFacadeService,
			final @NonNull PPCalibrationComputingMethod computingMethod)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.manufacturingFacadeService = manufacturingFacadeService;
		this.computingMethod = computingMethod;
	}

	@Override
	public boolean applies(@NonNull final CreateLogRequest request)
	{
		return manufacturingFacadeService.getManufacturingRawIssuedIfApplies(request.getRecordRef()).isPresent();
	}

	@Override
	@NonNull
	public final ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		final ManufacturingRawIssued manufacturingRawIssued = manufacturingFacadeService.getManufacturingRawIssued(request.getRecordRef());
		final ProductId productId = request.getProductId();
		final InstantAndOrgId transactionDate = manufacturingRawIssued.getTransactionDate();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, request.getModularContractSettings().getYearAndCalendarId()).orElse(null);
		final String productName = productBL.getProductValueAndName(productId);
		final Quantity qtyIssued =  manufacturingRawIssued.getQtyIssued();
		final Quantity qty = qtyIssued.isPositive() ? qtyIssued.negate() : qtyIssued;
		final String description = msgBL.getBaseLanguageMsg(MSG_DESCRIPTION_ISSUE, qty.abs().toString(), productName);

		final FlatrateTermId contractId = request.getContractId();
		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(contractId);
		final BPartnerId invoicingBPartnerId = BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID());
		final BPartnerId collectionPointBPartnerId = BPartnerId.ofRepoIdOrNull(modularContractRecord.getDropShip_BPartner_ID());
		final ProductPrice price = getContractSpecificPriceWithFlags(request).toProductPrice();

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(contractId)
				.referencedRecord(manufacturingRawIssued.getManufacturingOrderId().toRecordRef())
				.subEntryId(LogSubEntryId.ofCostCollectorId(manufacturingRawIssued.getId()))
				.productId(productId)
				.productName(request.getProductName())
				.invoicingBPartnerId(invoicingBPartnerId)
				.warehouseId(manufacturingRawIssued.getWarehouseId())
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(SOTrx.PURCHASE)
				.quantity(qty)
				.transactionDate(transactionDate.toLocalDateAndOrgId(orgDAO::getTimeZone))
				.year(request.getYearId())
				.description(description)
				.modularContractTypeId(request.getTypeId())
				.configModuleId(request.getConfigId().getModularContractModuleId())
				.collectionPointBPartnerId(collectionPointBPartnerId)
				.invoicingGroupId(invoicingGroupId)
				.isBillable(true)
				.priceActual(price)
				.amount(price.computeAmount(qty, uomConversionBL))
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
		final ManufacturingRawIssued manufacturingRawIssued = manufacturingFacadeService.getManufacturingRawIssued(handleLogsRequest.getTableRecordReference());

		return LogEntryDeleteRequest.builder()
				.referencedModel(manufacturingRawIssued.getManufacturingOrderId().toRecordRef())
				.subEntryId(LogSubEntryId.ofCostCollectorId(manufacturingRawIssued.getId()))
				.flatrateTermId(handleLogsRequest.getContractId())
				.logEntryContractType(getLogEntryContractType())
				.modularContractModuleId(modularContractModuleId)
				.build();
	}

	@NonNull
	public ProductPriceWithFlags getContractSpecificPriceWithFlags(final @NonNull CreateLogRequest request)
	{
		final FlatrateTermId flatrateTermId = request.getContractId();
		final I_C_Flatrate_Term modularContract = flatrateDAO.getById(flatrateTermId);
		final ProductId productId = request.getProductId();
		final UomId stockUOMId = productBL.getStockUOMId(productId);

		final CurrencyId currencyId = CurrencyId.optionalOfRepoId(modularContract.getC_Currency_ID())
				.orElseThrow(() -> new AdempiereException("Currency must be set on the Modular Contract !")
						.appendParametersToMessage()
						.setParameter("ModularContractId", flatrateTermId.getRepoId()));
		final ProductPrice productPrice = ProductPrice.builder()
				.productId(productId)
				.uomId(stockUOMId)
				.money(Money.zero(currencyId))
				.build();

		return ProductPriceWithFlags.ofZero(productPrice);
	}
}
