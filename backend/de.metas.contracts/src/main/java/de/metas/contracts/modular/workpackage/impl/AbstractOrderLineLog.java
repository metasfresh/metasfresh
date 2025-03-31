package de.metas.contracts.modular.workpackage.impl;/*
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

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper;
import de.metas.document.DocTypeId;
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
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;

public abstract class AbstractOrderLineLog extends AbstractModularContractLogHandler
{
	private static final AdMessageKey MSG_PO_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.workpackage.impl.PurchaseOrderLineLogHandler.OnComplete.Description");
	private static final AdMessageKey MSG_SO_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.workpackage.impl.SOLineForSOLogHandler.OnComplete.Description");

	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;
	@NonNull private final ModularContractLogDAO modularContractLogDAO;

	@Getter @NonNull private final String supportedTableName = I_C_OrderLine.Table_Name;

	public AbstractOrderLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ModularContractLogDAO modularContractLogDAO)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.modularContractLogDAO = modularContractLogDAO;
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

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		final String productName = productBL.getProductValueAndName(productId);
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		final AdMessageKey msgToUse = soTrx.isSales() ? MSG_SO_ON_COMPLETE_DESCRIPTION : MSG_PO_ON_COMPLETE_DESCRIPTION;
		final String description = msgBL.getBaseLanguageMsg(msgToUse, productName, quantity.abs().toString());

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(createLogRequest.getContractId())
				.productId(productId)
				.productName(createLogRequest.getProductName())
				.referencedRecord(recordRef)
				.producerBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoId(order.getBill_BPartner_ID()))
				.collectionPointBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()))
				.documentType(getLogEntryDocumentType())
				.contractType(LogEntryContractType.MODULAR_CONTRACT)
				.soTrx(soTrx)
				.processed(false)
				.quantity(quantity)
				.amount(amount)
				.transactionDate(transactionDate)
				.year(yearAndCalendarId.yearId())
				.description(description)
				.modularContractTypeId(createLogRequest.getTypeId())
				.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
				.priceActual(orderLineBL.getPriceActual(orderLine))
				.invoicingGroupId(invoicingGroupId)
				.isBillable(false)
				.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(final @NonNull IModularContractLogHandler.CreateLogRequest request)
	{
		final TableRecordReference orderLineRef = request.getRecordRef();
		final I_C_OrderLine orderLineRecord = orderBL.getOrderLineById(orderLineRef.getIdAssumingTableName(getSupportedTableName(), OrderLineId::ofRepoId));
		final I_C_Order orderRecord = orderBL.getById(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()));

		final Quantity quantity = modularContractLogDAO.retrieveQuantityFromExistingLog(
				ModularContractLogQuery.builder()
						.flatrateTermId(request.getContractId())
						.referenceSet(TableRecordReferenceSet.of(request.getRecordRef()))
						.contractType(getLogEntryContractType())
						.build());

		final ProductId productId = ProductId.ofRepoId(orderLineRecord.getM_Product_ID());
		final DocTypeId docTypeId = DocTypeId.ofRepoId(orderRecord.getC_DocType_ID());
		final String description = ModularContractLogHandlerHelper.getOnReverseDescription(docTypeId, productId, quantity);

		return ExplainedOptional.of(LogEntryReverseRequest.builder()
				.referencedModel(request.getRecordRef())
				.flatrateTermId(request.getContractId())
				.description(description)
				.logEntryContractType(getLogEntryContractType())
				.contractModuleId(request.getModularContractModuleId())
				.build());
	}
}
