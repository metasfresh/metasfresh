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

package de.metas.contracts.modular.computing.purchasecontract.addedvalue.interim;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.impl.AbstractPurchaseContractHandler;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Component;

@Component
@Getter
public class InterimContractLog extends AbstractPurchaseContractHandler
{

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@NonNull private final AVInterimComputingMethod computingMethod;
	@NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.CONTRACT_PREFINANCING;
	@NonNull private final LogEntryContractType logEntryContractType = LogEntryContractType.MODULAR_CONTRACT;
	private final ModularContractLogService modularContractLogService;

	public InterimContractLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final AVInterimComputingMethod computingMethod, final ModularContractLogService modularContractLogService)
	{
		super(modularContractService, modCntrInvoicingGroupRepository);
		this.computingMethod = computingMethod;
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.modularContractLogService = modularContractLogService;
	}
	@Override
	/*
	  Do not update the price for this log since it is the price from the interim contract itself
	 */
	public boolean isSuitableForPriceUpdate()
	{
		return false;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest request)
	{
		final TableRecordReference tableRecordReference = request.getRecordRef();
		final FlatrateTermId interimContractId = FlatrateTermId.ofRepoId(tableRecordReference.getRecordIdAssumingTableName(getSupportedTableName()));
		final I_C_Flatrate_Term interimContractRecord = flatrateBL.getById(interimContractId);

		final FlatrateTermId modularContractId = FlatrateTermId.ofRepoId(interimContractRecord.getModular_Flatrate_Term_ID());
		final I_C_Flatrate_Term modularContractRecord = flatrateBL.getById(modularContractId);

		final ProductId contractProductId = ProductId.ofRepoId(modularContractRecord.getM_Product_ID());

		final OrderId orderId = OrderId.ofRepoId(modularContractRecord.getC_Order_Term_ID());

		final I_C_Order order = orderBL.getById(orderId);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		final I_M_Warehouse warehouseRecord = warehouseBL.getById(warehouseId);

		final Quantity quantity = Quantitys.of(modularContractRecord.getPlannedQtyPerUnit(),
											   UomId.ofRepoIdOrNull(modularContractRecord.getC_UOM_ID()),
											   contractProductId);

		final String productName = productBL.getProductValueAndName(contractProductId);

		final String description = msgBL.getBaseLanguageMsg(MSG_ON_INTERIM_COMPLETE_DESCRIPTION, productName, quantity);

		final BPartnerId billBPartnerId = BPartnerId.ofRepoId(modularContractRecord.getBill_BPartner_ID());

		final ProductPrice priceActual = flatrateBL.extractPriceActual(interimContractRecord);
		Check.assumeNotNull(priceActual, "The interim contract must have a price.");

		final Money amount = quantity != null
				? priceActual.computeAmount(quantity)
				: null;

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(interimContractRecord.getStartDate(),
																				OrgId.ofRepoId(interimContractRecord.getAD_Org_ID()),
																				orgDAO::getTimeZone);

		final YearAndCalendarId yearAndCalendarId = request.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(contractProductId, yearAndCalendarId)
				.orElse(null);

		final ProductPrice productPrice = ProductPrice.builder()
				.productId(contractProductId)
				.money(priceActual.toMoney())
				.uomId(priceActual.getUomId())
				.build();

		final boolean interimContractLogExists = modularContractLogService.anyMatch(ModularContractLogQuery.builder()
																							.flatrateTermId(modularContractId)
																							.billable(true)
																							.computingMethodType(ComputingMethodType.AddValueOnInterim)
																							.logEntryDocumentType(getLogEntryDocumentType())
																							.build());
		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(modularContractId)
											.productId(contractProductId)
											.initialProductId(contractProductId)
											.productName(request.getProductName())
											.referencedRecord(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, interimContractId))
											.producerBPartnerId(billBPartnerId)
											.invoicingBPartnerId(billBPartnerId)
											.collectionPointBPartnerId(BPartnerId.ofRepoId(warehouseRecord.getC_BPartner_ID()))
											.warehouseId(warehouseId)
											.documentType(getLogEntryDocumentType())
											.contractType(getLogEntryContractType())
											.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
											.processed(false)
											.quantity(quantity)
											.transactionDate(transactionDate)
											.year(yearAndCalendarId.yearId())
											.description(description)
											.modularContractTypeId(request.getTypeId())
											.configModuleId(request.getConfigId().getModularContractModuleId())
											.priceActual(productPrice)
											.amount(amount)
											.invoicingGroupId(invoicingGroupId)
											.isBillable(!interimContractLogExists)
											.build());
	}

}
