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

package de.metas.contracts.modular.computing.purchasecontract.receipt;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PurchaseOrderLineLog implements IModularContractLogHandler
{
	private static final AdMessageKey MSG_INFO_PO_COMPLETED = AdMessageKey.of("de.metas.contracts.modular.workpackage.impl.PurchaseOrderLineLogHandler.OnComplete.Description");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull
	private final ReceiptComputingMethod computingMethod;
	@NonNull
	private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Override
	public @NonNull String getSupportedTableName()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(orderLine.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(orderLine.getQtyEntered(), uomId);
		final Money amount = Money.of(orderLine.getLineNetAmt(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(order.getDateOrdered(),
																				OrgId.ofRepoId(orderLine.getAD_Org_ID()),
																				orgDAO::getTimeZone);

		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, transactionDate.toInstant(orgDAO::getTimeZone))
				.orElse(null);

		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_INFO_PO_COMPLETED, productName, quantity.abs().toString());

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(createLogRequest.getContractId())
											.productId(productId)
											.productName(createLogRequest.getProductName())
											.referencedRecord(recordRef)
											.producerBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
											.invoicingBPartnerId(BPartnerId.ofRepoId(order.getBill_BPartner_ID()))
											.collectionPointBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
											.warehouseId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()))
											.documentType(LogEntryDocumentType.PURCHASE_ORDER)
											.contractType(LogEntryContractType.MODULAR_CONTRACT)
											.soTrx(SOTrx.PURCHASE)
											.processed(false)
											.quantity(quantity)
											.amount(amount)
											.transactionDate(transactionDate)
											.year(createLogRequest.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(createLogRequest.getTypeId())
											.configId(createLogRequest.getConfigId())
											.priceActual(orderLineBL.getPriceActual(orderLine))
											.invoicingGroupId(invoicingGroupId)
											.isBillable(false)
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		return ExplainedOptional.of(LogEntryReverseRequest.builder()
											.referencedModel(createLogRequest.getRecordRef())
											.flatrateTermId(createLogRequest.getContractId())
											.description(null)
											.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
											.contractModuleId(createLogRequest.getModuleConfig().getId().getModularContractModuleId())
											.build());
	}

	@Override
	public @NonNull IComputingMethodHandler getComputingMethod()
	{
		return computingMethod;
	}
}
