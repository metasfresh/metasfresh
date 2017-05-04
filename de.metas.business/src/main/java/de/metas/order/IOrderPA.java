package de.metas.order;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_Order;

import de.metas.adempiere.service.IOrderDAO;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated Please use {@link IOrderDAO}
 */
@Deprecated
public interface IOrderPA extends ISingletonService
{

	I_C_Order createNewOrder(String trxName);

	I_C_Order copyOrder(I_C_Order oldOrder, boolean copyLines, String trxName);

	I_C_OrderLine createNewOrderLine(String trxName);

	I_C_OrderLine createOrderLine(ResultSet rs, String trxName);

	Collection<I_C_OrderLine> retrieveFromCompleteOrderWithChargeId(int bPartnerId, int chargeId, String trxName);

	Collection<I_C_Order> retrieveOpenOrders(String docStatus, String trxName);

	I_C_Order retrieveOrder(int orderId, String trxName);

	I_C_Order retrieveOrder(String documentNo, String trxName);

	I_C_OrderLine retrieveOrderLine(int orderLineId, String trxName);

	/**
	 * Loads the lines of a given order from DB
	 * 
	 * @param order
	 * @param clazz
	 * @return
	 */
	<T extends I_C_OrderLine> List<T> retrieveOrderLines(I_C_Order order, Class<T> clazz);

	Collection<I_C_OrderLine> retrieveOrderLinesForLoc(int bPartnerLocationId,
			int shipperId, String trxName);

	Collection<I_C_Order> retrieveOrders(String docStatus, String trxName);

	/**
	 * Retrieves order lines from purchase orders for the given product and warehouse. Only orders with a document status in {@link X_C_Order#DOCSTATUS_Approved}, {@link X_C_Order#DOCSTATUS_Drafted}
	 * or {@link X_C_Order#DOCSTATUS_InProgress} are considered.
	 * 
	 * @param productId
	 * @param warehouseId
	 * @param trxName
	 * @return
	 */
	Collection<I_C_OrderLine> retrievePurchaseOrderLines(int productId,
			int warehouseId, String trxName);

	/**
	 * Retrieves order lines from sales orders for the given product and warehouse. Orders with a document status in {@link X_C_Order#DOCSTATUS_InProgress} are <b>ignored</b>.
	 * 
	 * @param productId
	 * @param warehouseId may be null. In that case all order lines with the given productId are returned (regardless of their warehouse)
	 * @param trxName
	 * @return
	 */
	Collection<I_C_OrderLine> retrieveOrderLinesForProdAndWH(int productId,
			int warehouseId, String trxName);

	/**
	 * Retrieves order lines from sales orders for the given product and shipping location ( {@link I_C_OrderLine#COLUMNNAME_C_BPartner_Location_ID} ). Lines are ordered by
	 * {@link I_C_OrderLine#COLUMNNAME_DateOrdered}.
	 * 
	 * @param productId
	 * @param bPartnerLocationId
	 * @return
	 */
	<T extends I_C_OrderLine> List<T> retrieveOrderLinesForProdAndLoc(
			Properties ctx,
			int productId,
			int bPartnerLocationId,
			Class<T> clazz,
			String trxName);

	/**
	 * No trxName required. Implementation uses <code>order</code>'s internal trxName.
	 * <p>
	 * FIXME: move this method to a "BL" service API
	 * 
	 * @param order
	 * @param ols
	 */
	void reserveStock(I_C_Order order, I_C_OrderLine... ols);

	List<MOrderLine> retrieveOpenPurchaseOrderLines(
			Properties ctx,
			int productId,
			int asi,
			int warehouseId,
			String trxName);

	List<MOrderLine> retrieveOpenSalesOrderLines(
			Properties ctx,
			int productId,
			int asi,
			int warehouseId,
			String trxName);

	/**
	 * Loads all active order line what have the given order id. Doesn't load the order itself.
	 * 
	 * @param ctx
	 * @param orderId
	 * @param getTrxName
	 * @return
	 */
	<T extends I_C_OrderLine> List<T> retrieveOrderLines(Properties ctx, int orderId, Class<T> clazz, String getTrxName);
}
