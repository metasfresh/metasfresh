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

package de.metas.contracts.modular.log.process;

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.inout.IInOutDAO;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderDAO;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

public class RecomputeLogRecordsForDocument extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);

	private final ModularContractService modularContractService = SpringContextHolder.instance.getBean(ModularContractService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final String tableName = getTableName();

		if (tableName == null)
		{
			throw new AdempiereException("RecomputeLogRecordsForDocument was not properly setup! TableName is missing!");
		}

		switch (tableName)
		{
			case I_C_Invoice.Table_Name -> recomputeForInvoice();
			case I_C_Order.Table_Name -> recomputeForOrder();
			case I_M_InOut.Table_Name -> recomputeForInOut();
			case I_C_Flatrate_Term.Table_Name -> recomputeForFlatrate();
			case I_M_Inventory.Table_Name -> recomputeForInventory();
			case I_PP_Cost_Collector.Table_Name -> recomputeForCostCollector();
			case I_PP_Order.Table_Name -> recomputeForPPOrder();
			default -> throw new AdempiereException("Process is not supported for table name=" + tableName);
		}

		return MSG_OK;
	}

	private void recomputeForInvoice()
	{
		invoiceDAO.stream(getProcessInfo().getQueryFilterOrElseFalse())
				.map(I_C_Invoice::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
				.forEach(this::recomputeForInvoice);
	}

	private void recomputeForInOut()
	{
		inOutDAO.stream(getProcessInfo().getQueryFilterOrElseFalse())
				//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
				.forEach(this::recomputeForInOut);
	}

	private void recomputeForOrder()
	{
		orderDAO.streamOrders(getProcessInfo().getQueryFilterOrElseFalse())
				.forEach(this::recomputeForOrder);
	}

	private void recomputeForFlatrate()
	{
		flatrateDAO.stream(getProcessInfo().getQueryFilterOrElseFalse())
				.forEach(this::recomputeForFlatrate);
	}

	private void recomputeForInventory()
	{
		inventoryDAO.stream(getProcessInfo().getQueryFilterOrElseFalse())
				.map(I_M_Inventory::getM_Inventory_ID)
				.map(InventoryId::ofRepoId)
				.forEach(this::recomputeForInventory);

	}

	private void recomputeForCostCollector()
	{
		ppCostCollectorDAO.stream(getProcessInfo().getQueryFilterOrElseFalse())
				.forEach(this::recomputeForCostCollector);
	}

	private void recomputeForPPOrder()
	{
		ppOrderDAO.stream(getProcessInfo().getQueryFilterOrElseFalse())
				.map(I_PP_Order::getPP_Order_ID)
				.map(PPOrderId::ofRepoId)
				.forEach(this::recomputeForPPOrder);
	}

	private void recomputeForInvoice(@NonNull final InvoiceId invoiceId)
	{
		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> invoiceDAO
				.retrieveLines(invoiceId)
				.forEach(line -> modularContractService.invokeWithModelForAllContractTypes(line, ModelAction.RECREATE_LOGS)));
	}

	private void recomputeForInOut(@NonNull final I_M_InOut inOut)
	{
		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> inOutDAO
				.retrieveAllLines(inOut)
				.forEach(line -> modularContractService.invokeWithModelForAllContractTypes(line, ModelAction.RECREATE_LOGS)));
	}

	private void recomputeForOrder(@NonNull final I_C_Order order)
	{
		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> orderDAO
				.retrieveOrderLines(order)
				.forEach(line -> modularContractService.invokeWithModelForAllContractTypes(line, ModelAction.RECREATE_LOGS)));
	}

	private void recomputeForFlatrate(@NonNull final I_C_Flatrate_Term term)
	{
		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> modularContractService
				.invokeWithModelForAllContractTypes(term, ModelAction.RECREATE_LOGS));
	}

	private void recomputeForInventory(@NonNull final InventoryId inventoryId)
	{
		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> inventoryDAO
				.retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class)
				.forEach(line -> modularContractService.invokeWithModelForAllContractTypes(line, ModelAction.RECREATE_LOGS)));
	}

	private void recomputeForCostCollector(@NonNull final I_PP_Cost_Collector costCollector)
	{
		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> modularContractService
				.invokeWithModelForAllContractTypes(costCollector, ModelAction.RECREATE_LOGS));
	}

	private void recomputeForPPOrder(@NonNull final PPOrderId ppOrderId)
	{
		//dev-note: one trx per each document, to preserve the results of already successfully recomputed logs
		trxManager.runInNewTrx(() -> ppCostCollectorDAO
				.getReceiptsByOrderId(ppOrderId)
				.forEach(ppCostCollector -> modularContractService.invokeWithModelForAllContractTypes(ppCostCollector,
																									  ModelAction.RECREATE_LOGS)));
	}
}
