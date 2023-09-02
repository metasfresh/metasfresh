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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.ExplainedOptional;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PurchaseOrderLineLogHandler implements IModularContractLogHandler<I_C_OrderLine>
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final PurchaseOrderLineModularContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_C_OrderLine> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case REACTIVATED, REVERSED, VOIDED -> LogAction.REVERSE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_C_OrderLine> request)
	{
		final I_C_OrderLine orderLine = request.getHandleLogsRequest().getModel();

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(orderLine.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(orderLine.getQtyEntered(), uomId);
		final Money amount = Money.of(orderLine.getLineNetAmt(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(request.getContractId())
											.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
											.referencedRecord(TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID()))
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
											.transactionDate(LocalDateAndOrgId.ofTimestamp(order.getDateOrdered(),
																						   OrgId.ofRepoId(orderLine.getAD_Org_ID()),
																						   orgDAO::getTimeZone))
											.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(null)
											.modularContractTypeId(request.getTypeId())
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(
			@NonNull final HandleLogsRequest<I_C_OrderLine> handleLogsRequest,
			@NonNull final FlatrateTermId contractId)
	{
		return ExplainedOptional.of(LogEntryReverseRequest.builder()
											.referencedModel(TableRecordReference.of(I_C_OrderLine.Table_Name, handleLogsRequest.getModel().getC_OrderLine_ID()))
											.flatrateTermId(contractId)
											.description(null)
											.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
											.build());
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_C_OrderLine> getModularContractTypeHandler()
	{
		return contractHandler;
	}
}
