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

package de.metas.contracts.modular.computing.tbd.purchasecontract.pforderline;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper;
import de.metas.document.DocTypeId;
import de.metas.i18n.ExplainedOptional;
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
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
class SalesOrderLineProFormaPOLogHandler extends AbstractModularContractLogHandler
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@Getter @NonNull private final SalesOrderLineProFormaPOModularContractHandler computingMethod;
	@Getter @NonNull private final String supportedTableName = I_C_OrderLine.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PRO_FORMA_SO;

	public SalesOrderLineProFormaPOLogHandler(@NonNull final ModularContractService modularContractService,
			final @NonNull SalesOrderLineProFormaPOModularContractHandler computingMethod)
	{
		super(modularContractService);
		this.computingMethod = computingMethod;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));

		final UomId uomId = UomId.ofRepoId(orderLine.getC_UOM_ID());
		final Quantity quantity = Quantitys.of(orderLine.getQtyEntered(), uomId);

		final I_C_Order orderRecord = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		final BPartnerId warehousePartnerId = warehouseBL.getBPartnerId(WarehouseId.ofRepoId(orderRecord.getM_Warehouse_ID()));
		final BPartnerId billBPartnerId = BPartnerId.ofRepoId(orderRecord.getBill_BPartner_ID());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final DocTypeId docTypeId = DocTypeId.ofRepoId(orderRecord.getC_DocType_ID());
		final String description = ModularContractLogHandlerHelper.getOnCompleteDescription(docTypeId, productId, quantity);

		final Money amount = Money.of(orderLine.getLineNetAmt(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.referencedRecord(TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID()))
											.contractId(createLogRequest.getContractId())
											.collectionPointBPartnerId(warehousePartnerId)
											.producerBPartnerId(bPartnerId)
											.invoicingBPartnerId(billBPartnerId)
											.warehouseId(WarehouseId.ofRepoId(orderRecord.getM_Warehouse_ID()))
											.productId(productId)
											.productName(createLogRequest.getProductName())
											.documentType(getLogEntryDocumentType())
											.contractType(LogEntryContractType.MODULAR_CONTRACT)
											.soTrx(SOTrx.PURCHASE)
											.processed(false)
											.quantity(quantity)
											.transactionDate(LocalDateAndOrgId.ofTimestamp(orderRecord.getDateOrdered(),
																						   OrgId.ofRepoId(orderLine.getAD_Org_ID()),
																						   orgDAO::getTimeZone))
											.year(createLogRequest.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(createLogRequest.getTypeId())
											.amount(amount)
											.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
											.priceActual(orderLineBL.getPriceActual(orderLine))
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		throw new AdempiereException(MSG_ERROR_DOC_ACTION_UNSUPPORTED);
	}
}
