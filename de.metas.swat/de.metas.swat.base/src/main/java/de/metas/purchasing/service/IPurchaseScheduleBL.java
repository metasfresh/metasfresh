package de.metas.purchasing.service;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.MRelation;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MOrder;

import de.metas.process.JavaProcess;
import de.metas.purchasing.model.I_M_PurchaseSchedule;
import de.metas.purchasing.model.MMPurchaseSchedule;

public interface IPurchaseScheduleBL extends ISingletonService
{

	/**
	 * Create purchase orders for all schedule entries that have
	 * {@link I_M_PurchaseSchedule#COLUMNNAME_IncludeInPurchase} set to 'Y'.
	 * 
	 * @param processLog
	 *            may be null. If not null, log messages are stored to the instance
	 * 
	 * 
	 * @return a list containing the documentNos of the new POs
	 */
	List<String> createPOs(Properties ctx, JavaProcess processLog, String trxName);

	/**
	 * Retrieves all pruchase schedules with the given productId and warehouseId and calls
	 * {@link #updateStorageData(Properties, I_M_PurchaseSchedule, String)} for each of them.
	 */
	void updateStorageData(Properties ctx, int productId, int warehouseId, String trxName);

	/**
	 * Update the {@link I_M_PurchaseSchedule}'s <code>QtyOnHand</code>, <code>QtyOrdered</code> and
	 * <code>QtyReserved</code> values. The former two are a the sum of all respective values from {@link I_M_Storage}'s
	 * that have the same <code>M_Product_ID</code> and <code>M_Warehouse_ID</code>. The latter one is the sum of all
	 * respective value of <b>completed</b> sales order lines that have the same <code>M_Product_ID</code> and
	 * <code>M_Warehouse_ID</code>.
	 * 
	 * @param purchaseSchedule
	 * @param trxName
	 */
	void updateStorageData(Properties ctx, I_M_PurchaseSchedule purchaseSchedule, String trxName);

	void updateQtyToOrder(
			Properties ctx,
			int productId,
			int warehouseId,
			String trxName);

	void updateQtyToOrder(MMPurchaseSchedule purchaseSchedule);

	/**
	 * Retrieves or creates purchase schedules for the given order's order lines. Note:
	 * <ul>
	 * <li>In the case of retrieval, it might turn out, the the existing schedule is not feasible for its order line
	 * anymore. In that case, the order line is automatically "moved" to another schedule. That other schedule can be a
	 * an existing or a new schedule</li>
	 * <li>The method updates the relation between sales order lines and purchase schedules, but does not set the
	 * purchase schedules members, like QtyResered, QtyToOrder etc</li>
	 * </ul>
	 * 
	 * @param ctx
	 * @param order
	 * @param trxName
	 * @return
	 * @see MRelation
	 */
	Collection<MMPurchaseSchedule> retrieveOrCreateForSO(
			Properties ctx,
			I_C_Order order,
			String trxName);

	/**
	 * Retrieves or creates purchase schedules for the given requisition's lines.
	 * 
	 * @param ctx
	 * @param requisition
	 * @param trxName
	 * @return
	 */
	Collection<MMPurchaseSchedule> retrieveOrCreateForReq(
			Properties ctx,
			I_M_Requisition requisition,
			String trxName);

	Collection<MMPurchaseSchedule> retrieveOrCreateForPO(MOrder order);

}
