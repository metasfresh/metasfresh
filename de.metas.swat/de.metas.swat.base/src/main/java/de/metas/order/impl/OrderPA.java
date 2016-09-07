package de.metas.order.impl;

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


import static org.adempiere.util.CustomColNames.C_Order_COMPLETE_ORDER_DISCOUNT;
import static org.adempiere.util.CustomColNames.C_Order_DESCRIPTION_BOTTOM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.db.IDatabaseBL;
import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.document.ICopyHandlerBL;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.order.IOrderPA;

public class OrderPA implements IOrderPA
{

	private static final String EXISTS_SCHED = //
	"    AND EXISTS( " //
			+ "      select * from m_shipmentSchedule s where s.C_OrderLine_ID=ol.C_OrderLine_ID" //
			+ "    )";

	public static final String SQL_ORDER_DOCNO = "SELECT * FROM "
			+ I_C_Order.Table_Name + " WHERE "
			+ I_C_Order.COLUMNNAME_DocumentNo + "=?";

	public static final String SQL_ORDER_DOCSTATUS = "SELECT * FROM "
			+ I_C_Order.Table_Name + " WHERE " + I_C_Order.COLUMNNAME_DocStatus
			+ "=? AND AD_Client_ID=? AND AD_Org_ID=?";

	public static final String SQL_OPEN_ORDER_DOCSTATUS = "SELECT o.* FROM "
			+ I_C_Order.Table_Name
			+ " o WHERE o."
			+ I_C_Order.COLUMNNAME_DocStatus
			+ "=? AND o.AD_Client_ID=? AND o.AD_Org_ID=? " //
			+ " AND EXISTS ("
			+ "    select * from C_OrderLine ol WHERE ol.C_Order_ID=o.C_Order_ID AND ol.QtyReserved>0"
			+ ")";

	public static final String SQL_FROM_COMPLETE_ORDER_WITH_CHARGE_ID = "SELECT ol.* "
			+ "FROM C_OrderLine ol "
			+ "       LEFT JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID "
			+ "WHERE o.DocStatus='"
			+ X_C_Order.DOCSTATUS_Completed
			+ "' AND o.C_BPartner_ID=? AND ol.C_Charge_ID=?";

	public static final String SQL_ORDERLINE = //
	"SELECT ol.* FROM "
			+ I_C_OrderLine.Table_Name
			+ " ol "
			+ //
			"LEFT JOIN " + I_C_Order.Table_Name + " o ON o."
			+ I_C_Order.COLUMNNAME_C_Order_ID
			+ "=ol."
			+ I_C_OrderLine.COLUMNNAME_C_Order_ID //
			+ " WHERE o." + I_C_Order.COLUMNNAME_DocumentNo + "=? AND ol."
			+ I_C_OrderLine.COLUMNNAME_Line + "=?";

	public static final String SQL_PO_LINES = //
	"SELECT ol.* " //
			+ " FROM C_OrderLine ol "
			+ " LEFT JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID" //
			+ " WHERE " //
			+ "   o.IsSOTrx='N' AND o.DocStatus in ('"
			+ X_C_Order.DOCSTATUS_Approved
			+ "','"
			+ X_C_Order.DOCSTATUS_Drafted
			+ "','"
			+ X_C_Order.DOCSTATUS_InProgress
			+ "') AND ol.M_Product_ID=? AND (?=0 OR ol.M_Warehouse_ID=?)";

	public static final String SQL_PROD_SCHED = //
	"SELECT ol.* " //
			+ " FROM C_OrderLine ol " //
			+ " WHERE " //
			+ "    ol.M_Product_ID=? AND (?=0 OR ol.M_Warehouse_ID=?)" //
			+ EXISTS_SCHED;

	public static final String SQL_ORDERLINES = //
	"SELECT ol.* " //
			+ " FROM C_OrderLine ol "
			+ " LEFT JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID" //
			+ " WHERE o.DocumentNo=?";

	/**
	 * Selects order lines that have a shipment schedule and a particular shipping destination.
	 */
	public static final String SQL_LOC_SCHED_ORDERLINES = //
	" SELECT ol.* " //
			+ " FROM C_OrderLine ol " //
			+ " WHERE "//
			+ "    ol.C_Bpartner_Location_ID=? AND (?=0 OR ol.M_Shipper_ID=?) " //
			+ EXISTS_SCHED;

	private static final Logger logger = LogManager.getLogger(OrderPA.class);

	@Override
	public MOrder retrieveOrder(final String documentNo, final String trxName)
	{

		if (documentNo == null)
		{
			throw new IllegalArgumentException("'documentNo' may not be null");
		}

		CPreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL_ORDER_DOCNO, trxName);
			pstmt.setString(1, documentNo);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				return new MOrder(Env.getCtx(), rs, trxName);
			}

			logger.debug("There is no order with documentNo '" + documentNo + "'. Returning null.");
			return null;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public MOrder retrieveOrder(final int orderId, final String trxName)
	{

		final MOrder order = new MOrder(Env.getCtx(), orderId, trxName);

		if (order.get_ID() == 0)
		{

			logger.debug("There is no order with id '" + orderId + "'. Returning null.");
			return null;
		}
		return order;

	}

	@Override
	public Collection<I_C_OrderLine> retrieveFromCompleteOrderWithChargeId(
			int partnerId, int chargeId, String trxName)
	{

		CPreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL_FROM_COMPLETE_ORDER_WITH_CHARGE_ID,
					trxName);

			pstmt.setInt(1, partnerId);
			pstmt.setInt(2, chargeId);

			final Collection<I_C_OrderLine> result = new ArrayList<I_C_OrderLine>();
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(InterfaceWrapperHelper.create(new MOrderLine(Env.getCtx(), rs, trxName), I_C_OrderLine.class));
			}
			return result;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public Collection<I_C_OrderLine> retrievePurchaseOrderLines(
			final int productId, final int warehouseId, final String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SQL_PO_LINES,
				trxName);
		return retrieveForProdAndWh(productId, warehouseId, trxName, pstmt);
	}

	private Collection<I_C_OrderLine> retrieveForProdAndWh(final int productId,
			final int warehouseId, final String trxName,
			final PreparedStatement pstmt)
	{

		ResultSet rs = null;

		try
		{
			pstmt.setInt(1, productId);
			pstmt.setInt(2, warehouseId);
			pstmt.setInt(3, warehouseId);

			final Collection<I_C_OrderLine> result = new ArrayList<I_C_OrderLine>();
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(InterfaceWrapperHelper.create(new MOrderLine(Env.getCtx(), rs, trxName), I_C_OrderLine.class));
			}
			return result;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public Collection<I_C_OrderLine> retrieveOrderLinesForProdAndWH(
			final int productId, final int warehouseId, final String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SQL_PROD_SCHED,
				trxName);
		return retrieveForProdAndWh(productId, warehouseId, trxName, pstmt);
	}

	@Override
	public I_C_Order createNewOrder(String trxName)
	{

		return new MOrder(Env.getCtx(), 0, trxName);
	}

	@Override
	public I_C_OrderLine createNewOrderLine(String trxName)
	{
		return InterfaceWrapperHelper.create(new MOrderLine(Env.getCtx(), 0, trxName), I_C_OrderLine.class);
	}

	@Override
	public Collection<I_C_Order> retrieveOpenOrders(final String docStatus,
			final String trxName)
	{

		return retrieveOrdersDocStatus(docStatus, SQL_OPEN_ORDER_DOCSTATUS,
				trxName);
	}

	@Override
	public Collection<I_C_Order> retrieveOrders(final String docStatus,
			final String trxName)
	{

		return retrieveOrdersDocStatus(docStatus, SQL_ORDER_DOCSTATUS, trxName);
	}

	public Collection<I_C_Order> retrieveOrdersDocStatus(
			final String docStatus, final String sql, final String trxName)
	{

		ResultSet rs = null;
		final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);

		final Collection<I_C_Order> result = new ArrayList<I_C_Order>();
		try
		{
			pstmt.setString(1, docStatus);
			pstmt.setInt(2, Env.getAD_Client_ID(Env.getCtx()));
			pstmt.setInt(3, Env.getAD_Org_ID(Env.getCtx()));

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(new MOrder(Env.getCtx(), rs, trxName));
			}
			return result;

		}
		catch (SQLException e)
		{
			MetasfreshLastError.saveError(logger, "Caught SQLException. Msg: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public I_C_OrderLine retrieveOrderLine(final int orderLineId, final String trxName)
	{
		return InterfaceWrapperHelper.create(new MOrderLine(Env.getCtx(), orderLineId, trxName), I_C_OrderLine.class);
	}

	/**
	 * Invokes {@link MOrderLine#MOrderLine(java.util.Properties, ResultSet, String)}.
	 */
	@Override
	public I_C_OrderLine createOrderLine(ResultSet rs, String trxName)
	{
		return InterfaceWrapperHelper.create(new MOrderLine(Env.getCtx(), rs, trxName), I_C_OrderLine.class);
	}

	/**
	 * Invokes {@link MOrder#getLines()}.
	 * 
	 */
	@Override
	public <T extends I_C_OrderLine> List<T>  retrieveOrderLines(final I_C_Order order, final Class<T> clazz)
	{
		final MOrderLine[] lines = ((MOrder)InterfaceWrapperHelper.getPO(order)).getLines(true, "");
		
		final List<T> result = new ArrayList<T>(lines.length);

		for (final MOrderLine currentLine : lines)
		{
			result.add(InterfaceWrapperHelper.create(currentLine, clazz));
		}

		return result;
	}

	@Override
	public Collection<I_C_OrderLine> retrieveOrderLinesForLoc(
			final int partnerLocationId, final int shipperId,
			final String trxName)
	{

		final Object[] params = new Object[] { partnerLocationId, shipperId,
				shipperId };

		final IDatabaseBL db = Services.get(IDatabaseBL.class);

		final List<X_C_OrderLine> xOrderLines = db.retrieveList(
				SQL_LOC_SCHED_ORDERLINES, params, X_C_OrderLine.class, trxName);

		final Collection<I_C_OrderLine> result = new ArrayList<I_C_OrderLine>();

		for (X_C_OrderLine olPO : xOrderLines)
		{
			result.add(InterfaceWrapperHelper.create(olPO, I_C_OrderLine.class));
		}

		return result;
	}

	@Override
	public <T extends I_C_OrderLine> List<T> retrieveOrderLinesForProdAndLoc(
			final Properties ctx, 
			final int productId, 
			final int partnerLocationId,
			final Class<T> clazz,
			final String trxName)
	{

		final String sql = "SELECT ol.* " //
				+ " FROM C_OrderLine ol " //
				+ " WHERE ol.M_Product_ID=? AND ol.C_BPartner_Location_ID=? "
				+ EXISTS_SCHED + "ORDER BY ol.DateOrdered";

		final IDatabaseBL db = Services.get(IDatabaseBL.class);
		final List<MOrderLine> ols = db.retrieveList(sql, new Object[] {
				productId, partnerLocationId }, MOrderLine.class, trxName);

		final ArrayList<T> result = new ArrayList<T>();
		for (final MOrderLine olPO : ols)
		{
			result.add(InterfaceWrapperHelper.create(olPO, clazz));
		}

		return result;
	}

	@Override
	public void reserveStock(final I_C_Order order, final I_C_OrderLine... ols)
	{

		final MOrder mOrder = InterfaceWrapperHelper.getPO(order);
		final MOrderLine[] mOls = new MOrderLine[ols.length];

		for (int i = 0; i < ols.length; i++)
		{
			mOls[i] = InterfaceWrapperHelper.getPO(ols[i]);
		}
		mOrder.reserveStock(null, mOls); // docType=null (i.e. fetch it from order)
	}

	@Override
	public I_C_Order copyOrder(
			final I_C_Order originalOrder,
			final boolean copyLines, 
			final String trxName)
	{

		final MOrder newOrder = new MOrder(Env.getCtx(), 0, trxName);

		Services.get(ICopyHandlerBL.class).copyPreliminaryValues(originalOrder, newOrder);
		
		final IPOService poService = Services.get(IPOService.class);
		poService.copyClientOrg(originalOrder, newOrder);

		newOrder.setDatePromised(Env.getContextAsDate(Env.getCtx(), "#Date"));
		newOrder.setDateOrdered(Env.getContextAsDate(Env.getCtx(), "#Date"));

		newOrder.setAD_OrgTrx_ID(originalOrder.getAD_OrgTrx_ID());
		newOrder.setAD_User_ID(originalOrder.getAD_User_ID());
		newOrder.setC_Activity_ID(originalOrder.getC_Activity_ID());
		newOrder.setC_BPartner_ID(originalOrder.getC_BPartner_ID());
		newOrder.setC_BPartner_Location_ID(originalOrder.getC_BPartner_Location_ID());
		newOrder.setBill_BPartner_ID(originalOrder.getBill_BPartner_ID());
		newOrder.setBill_Location_ID(originalOrder.getBill_Location_ID());
		newOrder.setBill_User_ID(originalOrder.getBill_User_ID());
		newOrder.setC_Campaign_ID(originalOrder.getC_Campaign_ID());
		newOrder.setC_CashLine_ID(originalOrder.getC_CashLine_ID());
		newOrder.setC_Charge_ID(originalOrder.getC_Charge_ID());
		newOrder.setC_ConversionType_ID(originalOrder.getC_ConversionType_ID());
		newOrder.setC_Currency_ID(originalOrder.getC_Currency_ID());
		newOrder.setC_DocType_ID(originalOrder.getC_DocType_ID());
		newOrder.setC_DocTypeTarget_ID(originalOrder.getC_DocTypeTarget_ID());
		newOrder.setC_Payment_ID(originalOrder.getC_Payment_ID());
		newOrder.setC_PaymentTerm_ID(originalOrder.getC_PaymentTerm_ID());
		newOrder.setC_Project_ID(originalOrder.getC_Project_ID());
		newOrder.setChargeAmt(originalOrder.getChargeAmt());
		newOrder.setDescription(originalOrder.getDescription());
		newOrder.setFreightAmt(originalOrder.getFreightAmt());
		newOrder.setFreightCostRule(originalOrder.getFreightCostRule());
		newOrder.setInvoiceRule(originalOrder.getInvoiceRule());
		newOrder.setIsSOTrx(originalOrder.isSOTrx());
		newOrder.setIsTaxIncluded(originalOrder.isTaxIncluded());
		newOrder.setM_PriceList_ID(originalOrder.getM_PriceList_ID());
		newOrder.setM_Shipper_ID(originalOrder.getM_Shipper_ID());
		newOrder.setM_Warehouse_ID(originalOrder.getM_Warehouse_ID());
		newOrder.setPay_BPartner_ID(originalOrder.getPay_BPartner_ID());
		newOrder.setPay_Location_ID(originalOrder.getPay_Location_ID());
		newOrder.setPaymentRule(originalOrder.getPaymentRule());
		newOrder.setPriorityRule(originalOrder.getPriorityRule());
		newOrder.setSalesRep_ID(originalOrder.getSalesRep_ID());
		newOrder.setSendEMail(originalOrder.isSendEMail());
		newOrder.setUser1_ID(originalOrder.getUser1_ID());
		newOrder.setUser2_ID(originalOrder.getUser2_ID());
		newOrder.setVolume(originalOrder.getVolume());
		newOrder.setWeight(originalOrder.getWeight());
		newOrder.setPOReference(originalOrder.getPOReference());

		poService.copyValue(originalOrder, newOrder, C_Order_COMPLETE_ORDER_DISCOUNT);
		poService.copyValue(originalOrder, newOrder, C_Order_DESCRIPTION_BOTTOM);

		poService.save(newOrder, trxName);

		if (copyLines)
		{
			final int linesCopied = newOrder.copyLinesFrom((MOrder)MiscUtils.asPO(originalOrder), false, false);
			logger.debug("Copied " + linesCopied + " form original order");
		}

		Services.get(ICopyHandlerBL.class).copyValues(originalOrder, newOrder);
		
		return newOrder;
	}

	@Override
	public List<MOrderLine> retrieveOpenPurchaseOrderLines(
			final Properties ctx,
			final int productId,
			final int asi,
			final int warehouseId,
			final String trxName)
	{
		return retrieveOpenOrderLines(ctx, productId, asi, warehouseId, false, trxName);
	}
	
	@Override
	public List<MOrderLine> retrieveOpenSalesOrderLines(
			final Properties ctx,
			final int productId,
			final int asi,
			final int warehouseId,
			final String trxName)
	{
		return retrieveOpenOrderLines(ctx, productId, asi, warehouseId, true, trxName);
	}

	private List<MOrderLine> retrieveOpenOrderLines(
			final Properties ctx,
			final int productId,
			final int asi,
			final int warehouseId,
			final boolean soTrx,
			final String trxName)
	{
		final String wc = I_C_OrderLine.COLUMNNAME_M_Product_ID + "=? AND " + I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID + "=? AND " + I_C_OrderLine.COLUMNNAME_M_Warehouse_ID + "=? "
				+ " AND (select IsSOTrx from C_Order o where o.C_Order_ID=C_OrderLine.C_Order_ID)=?"
				+ " AND (select DocStatus from C_Order o where o.C_Order_ID=C_OrderLine.C_Order_ID) IN ('IP','WP','CO')";

		return new Query(ctx, I_C_OrderLine.Table_Name, wc, trxName)
				.setParameters(productId, asi, warehouseId, soTrx)
				.setOnlyActiveRecords(true).setClient_ID()
				.setOrderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.list();
	}

	@Override
	@Cached
	public <T extends I_C_OrderLine> List<T> retrieveOrderLines(
			@CacheCtx final Properties ctx,
			final int orderId,
			final Class<T> clazz,
			final String trxName)
	{
		final String tableName = I_C_OrderLine.Table_Name;
		final String whereClause = I_C_OrderLine.COLUMNNAME_C_Order_ID + "=?";
		final String orderBy = I_C_OrderLine.COLUMNNAME_Line;

		return new Query(ctx, tableName, whereClause, trxName)
				.setParameters(orderId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list(clazz);
	}
}
