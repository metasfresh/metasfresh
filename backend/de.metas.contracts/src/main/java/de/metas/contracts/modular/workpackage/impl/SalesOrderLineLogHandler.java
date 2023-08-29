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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SalesOrderLineLogHandler implements IModularContractLogHandler<I_C_OrderLine>
{
	private static final AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description");
	private static final AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnReverse.Description");

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	private final ModularContractLogDAO contractLogDAO;
	private final SalesOrderLineModularContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_C_OrderLine> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case REVERSED, REACTIVATED, VOIDED -> LogAction.REVERSE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_C_OrderLine> request)
	{
		final I_C_OrderLine orderLine = request.getHandleLogsRequest().getModel();

		final I_C_Flatrate_Term contract = flatrateBL.getById(request.getContractId());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(contract.getBill_BPartner_ID());

		final UomId uomId = UomId.ofRepoId(orderLine.getC_UOM_ID());
		final Quantity quantity = Quantitys.create(orderLine.getQtyEntered(), uomId);

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		final String description = msgBL.getMsg(MSG_ON_COMPLETE_DESCRIPTION, ImmutableList.of(String.valueOf(productId.getRepoId()), quantity.toString()));

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.referencedRecord(TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID()))
											.contractId(request.getContractId())
											.collectionPointBPartnerId(bPartnerId)
											.producerBPartnerId(bPartnerId)
											.invoicingBPartnerId(bPartnerId)
											.warehouseId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()))
											.productId(productId)
											.documentType(LogEntryDocumentType.SALES_ORDER)
											.contractType(LogEntryContractType.MODULAR_CONTRACT)
											.soTrx(SOTrx.PURCHASE)
											.processed(false)
											.quantity(quantity)
											.transactionDate(LocalDateAndOrgId.ofTimestamp(order.getDateOrdered(),
																						   OrgId.ofRepoId(orderLine.getAD_Org_ID()),
																						   orgDAO::getTimeZone))
											.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(request.getTypeId())
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(
			@NonNull final HandleLogsRequest<I_C_OrderLine> handleLogsRequest,
			@NonNull final FlatrateTermId contractId)
	{
		final I_C_OrderLine orderLine = handleLogsRequest.getModel();

		final TableRecordReference orderLineRef = TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID());

		final Quantity quantity = contractLogDAO.retrieveQuantityFromExistingLog(ModularContractLogQuery.builder()
																						 .flatrateTermId(contractId)
																						 .referenceSet(TableRecordReferenceSet.of(orderLineRef))
																						 .build());

		final String description = msgBL.getMsg(MSG_ON_REVERSE_DESCRIPTION, ImmutableList.of(String.valueOf(orderLine.getM_Product_ID()), quantity.toString()));

		return ExplainedOptional.of(
				LogEntryReverseRequest.builder()
						.referencedModel(orderLineRef)
						.flatrateTermId(contractId)
						.description(description)
						.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
						.build());
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_C_OrderLine> getModularContractTypeHandler()
	{
		return contractHandler;
	}
}
