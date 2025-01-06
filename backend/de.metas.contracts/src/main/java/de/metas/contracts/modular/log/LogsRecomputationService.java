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

package de.metas.contracts.modular.log;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutQuery;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineQuery;
import de.metas.shippingnotification.ShippingNotificationLine;
import de.metas.shippingnotification.ShippingNotificationQuery;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StreamUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.util.Env;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogsRecomputationService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);

	@NonNull
	private final ShippingNotificationService shippingNotificationService;
	@NonNull
	private final ModularContractService modularContractService;
	@NonNull
	private final ModularContractLogDAO modularContractLogDAO;

	public void recomputeAllFinalInvoiceRelatedLogsLinkedTo(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term flatrateTerm = flatrateBL.getById(flatrateTermId);

		final OrderId flatrateTermOrderId = OrderId.ofRepoId(flatrateTerm.getC_Order_Term_ID());
		inOutDAO.retrieveByQuery(InOutQuery.builder()
										 .orderId(flatrateTermOrderId)
										 .build())
						.forEach(this::recomputeForInOut);

		if(flatrateTerm.isSOTrx())
		{
			shippingNotificationService.getByQuery(ShippingNotificationQuery.builder()
														   .orderId(flatrateTermOrderId)
														   .build())
					.forEach(shippingNotification -> recomputeForShippingNotificationLines(shippingNotification.getLines()));
		}
		else
		{
			final Set<OrderId> salesOrderIds = orderDAO.streamOrderLines(OrderLineQuery.builder().modularPurchaseContractId(flatrateTermId).build())
					.map(I_C_OrderLine::getC_Order_ID)
					.map(OrderId::ofRepoId)
					.collect(Collectors.toSet());

			inOutDAO.retrieveByQuery(InOutQuery.builder()
											 .orderIds(salesOrderIds)
											 .build())
					.forEach(this::recomputeForInOut);

			shippingNotificationService.getByQuery(ShippingNotificationQuery.builder()
														   .orderIds(salesOrderIds)
														   .build())
					.forEach(shippingNotification -> recomputeForShippingNotificationLines(shippingNotification.getLines()));

			ppOrderDAO.streamManufacturingOrders(ManufacturingOrderQuery.builder()
														 .modularFlatrateTermId(flatrateTermId)
														 .build())
					.map(I_PP_Order::getPP_Order_ID)
					.map(PPOrderId::ofRepoId)
					.forEach(this::recomputeForPPOrder);

			recomputeForFlatrate(flatrateTerm);
		}
	}

	private void recomputeForShippingNotificationLines(@NonNull final List<ShippingNotificationLine> shippingNotificationLinesList)
	{
		shippingNotificationLinesList.forEach(line -> modularContractService.scheduleLogCreation(
				DocStatusChangedEvent.builder()
						.tableRecordReference(TableRecordReference.of(I_M_Shipping_NotificationLine.Table_Name,
																	  Check.assumeNotNull(line.getId(), "ShippingNotificationLineId shouldn't be null")))
						.modelAction(ModelAction.RECREATE_LOGS)
						.userInChargeId(Env.getLoggedUserId())
						.build())
		);
	}

	private void recomputeForInOut(@NonNull final I_M_InOut inOut)
	{
		inOutDAO.retrieveAllLines(inOut)
				.forEach(line -> modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(line))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build())
		);
	}

	private void recomputeForPPOrder(@NonNull final PPOrderId ppOrderId)
	{
		ppCostCollectorDAO.getByOrderId(ppOrderId)
				.forEach(ppCostCollector -> modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(ppCostCollector))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build())
		);
	}

	private void recomputeForFlatrate(@NonNull final I_C_Flatrate_Term term)
	{
		modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(term))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build()
		);
	}


	public void recomputeLogs(@NonNull final IQueryFilter<I_ModCntr_Log> filter)
	{
		final Iterator<I_ModCntr_Log> logsIterator = modularContractLogDAO.getLogsIteratorOrderedByRecordRef(filter);

		TableRecordReference currentRecordReference = null;
		while (logsIterator.hasNext())
		{
			final I_ModCntr_Log logEntry = logsIterator.next();
			final TableRecordReference logRecordRef = TableRecordReference.of(logEntry.getAD_Table_ID(), logEntry.getRecord_ID());

			if (currentRecordReference == null)
			{
				currentRecordReference = TableRecordReference.of(logEntry.getAD_Table_ID(), logEntry.getRecord_ID());
			}

			if (!logRecordRef.equals(currentRecordReference) || !logsIterator.hasNext())
			{
				recomputeForRecord(currentRecordReference);
				currentRecordReference = logRecordRef;
			}
		}
	}

	public void recomputeForInvoice(@NonNull final IQueryFilter<I_C_Invoice> filter)
	{
		invoiceDAO.stream(filter)
				.map(I_C_Invoice::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.forEach(this::recomputeForInvoice);
	}

	public void recomputeForInOut(@NonNull final IQueryFilter<I_M_InOut> filter)
	{
		inOutDAO.stream(filter)
				.forEach(this::recomputeForInOutInNewTrx);
	}

	public void recomputeForOrder(@NonNull final IQueryFilter<I_C_Order> filter)
	{
		orderDAO.streamOrders(filter)
				.forEach(this::recomputeForOrder);
	}

	public void recomputeForFlatrateInNewTrx(@NonNull final IQueryFilter<I_C_Flatrate_Term> filter)
	{
		flatrateBL.stream(filter)
				.forEach(this::recomputeForFlatrateInNewTrx);
	}

	public void recomputeForInventory(@NonNull final IQueryFilter<I_M_Inventory> filter)
	{
		inventoryDAO.stream(filter)
				.map(I_M_Inventory::getM_Inventory_ID)
				.map(InventoryId::ofRepoId)
				.forEach(this::recomputeForInventory);

	}

	public void recomputeForCostCollector(@NonNull final IQueryFilter<I_PP_Cost_Collector> filter)
	{
		ppCostCollectorDAO.stream(filter)
				.forEach(this::recomputeForCostCollector);
	}

	public void recomputeForPPOrder(@NonNull final IQueryFilter<I_PP_Order> filter)
	{
		ppOrderDAO.stream(filter)
				.map(I_PP_Order::getPP_Order_ID)
				.map(PPOrderId::ofRepoId)
				.forEach(this::recomputeForPPOrderInNewTrx);
	}

	public void recomputeForShippingNotification(@NonNull final IQueryFilter<I_M_Shipping_Notification> filter)
	{
		StreamUtils.dice(shippingNotificationService.streamIds(filter), 100)
				.map(shippingNotificationService::getLines)
				.map(Map::entrySet)
				.flatMap(Set::stream)
				.map(Map.Entry::getValue)
				.forEach(this::recomputeForShippingNotification);
	}

	private void recomputeForInvoice(@NonNull final InvoiceId invoiceId)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> invoiceDAO
				.retrieveLines(invoiceId)
				.forEach(line -> modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(line))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build()))
		);
	}

	private void recomputeForInOutInNewTrx(@NonNull final I_M_InOut inOut)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> inOutDAO
				.retrieveAllLines(inOut)
				.forEach(line -> modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(line))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build()))
		);
	}

	private void recomputeForOrder(@NonNull final I_C_Order order)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> orderDAO
				.retrieveOrderLines(order)
				.forEach(line -> modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(line))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build()))
		);
	}

	private void recomputeForFlatrateInNewTrx(@NonNull final I_C_Flatrate_Term term)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> modularContractService
				.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(term))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build())
				);
	}

	private void recomputeForInventory(@NonNull final InventoryId inventoryId)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> inventoryDAO
				.retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class)
				.forEach(line -> modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(line))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build()))
				);
	}

	private void recomputeForCostCollector(@NonNull final I_PP_Cost_Collector costCollector)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> modularContractService
				.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(costCollector))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build())
				);
	}

	private void recomputeForPPOrderInNewTrx(@NonNull final PPOrderId ppOrderId)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> ppCostCollectorDAO
				.getByOrderId(ppOrderId)
				.forEach(ppCostCollector -> modularContractService.scheduleLogCreation(
						DocStatusChangedEvent.builder()
								.tableRecordReference(TableRecordReference.of(ppCostCollector))
								.modelAction(ModelAction.RECREATE_LOGS)
								.userInChargeId(Env.getLoggedUserId())
								.build()))
		);
	}

	private void recomputeForRecord(@NonNull final TableRecordReference recordRef)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		switch (recordRef.getTableName())
		{
			case I_PP_Order.Table_Name -> recomputeForPPOrderInNewTrx(recordRef.getIdAssumingTableName(I_PP_Order.Table_Name, PPOrderId::ofRepoId));
			default -> trxManager.runInNewTrx(() -> modularContractService
					.scheduleLogCreation(
							DocStatusChangedEvent.builder()
									.tableRecordReference(recordRef)
									.modelAction(ModelAction.RECREATE_LOGS)
									.userInChargeId(Env.getLoggedUserId())
									.build())
				);
		}
	}

	private void recomputeForShippingNotification(@NonNull final List<I_M_Shipping_NotificationLine> shippingNotificationLinesList)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> shippingNotificationLinesList
				.forEach(line -> modularContractService.scheduleLogCreation(
						 DocStatusChangedEvent.builder()
								 .tableRecordReference(TableRecordReference.of(line))
								 .modelAction(ModelAction.RECREATE_LOGS)
								 .userInChargeId(Env.getLoggedUserId())
								 .build()))
				);
	}
}
