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

package de.metas.contracts.modular.computing.purchasecontract.averageonshippedqty;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.purchasecontract.storagecost.StorageCostComputingMethod;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class UEN1ShipmentLineLog extends AbstractUENShipmentLineLog
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@NonNull
	private final ModularContractService modularContractService;
	@NonNull
	private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Getter @NonNull protected final StorageCostComputingMethod computingMethod;

	public UEN1ShipmentLineLog(@NonNull final ModularContractService modularContractService,
							   final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
							   final @NonNull StorageCostComputingMethod computingMethod)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.computingMethod = computingMethod;
		this.modularContractService = modularContractService;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(getInOutLineId(recordRef));

		final I_M_InOut inOutRecord = inOutBL.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(createLogRequest.getContractId());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID());
		final BigDecimal uen1 = inOutLineRecord.getUserElementNumber2();
		final String columName = "UEN1";

		final Quantity quantity = inOutBL.getQtyEntered(inOutLineRecord);
		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final LocalDateAndOrgId transactionDate = extractTransactionDate(inOutRecord);

		final ContractSpecificScalePriceRequest contractSpecificScalePriceRequest = ContractSpecificScalePriceRequest.builder()
				.modularContractModuleId(createLogRequest.getModularContractModuleId())
				.flatrateTermId(createLogRequest.getContractId())
				.columnName(columName)
				.value(uen1)
				.build();

		final ProductPrice contractSpecificPrice = modularContractService.getContractSpecificScalePrice(contractSpecificScalePriceRequest);

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(createLogRequest.getContractId())
				.productId(createLogRequest.getProductId())
				.productName(createLogRequest.getProductName())
				.initialProductId(productId)
				.referencedRecord(recordRef)
				.collectionPointBPartnerId(bpartnerId)
				.producerBPartnerId(bpartnerId)
				.invoicingBPartnerId(bpartnerId)
				.warehouseId(WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID()))
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(SOTrx.PURCHASE)
				.processed(false)
				.isBillable(true)
				.quantity(quantity)
				.amount(calculateAmount(quantity, contractSpecificPrice, uomConversionBL))
				.transactionDate(transactionDate)
				.priceActual(contractSpecificPrice)
				.year(yearAndCalendarId.yearId())
				.description(msgBL.getBaseLanguageMsg(MSG_INFO_SHIPMENT_COMPLETED, productName, quantity.abs()))
				.modularContractTypeId(createLogRequest.getTypeId())
				.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
				.invoicingGroupId(invoicingGroupId)
				.build());
	}
}
