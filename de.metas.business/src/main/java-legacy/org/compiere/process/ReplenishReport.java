/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Chris Farley - northernbrewer                              *
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MStorage;
import org.compiere.model.MWarehouse;
import org.compiere.model.X_T_Replenish;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.ReplenishInterface;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 *	Replenishment Report
 *
 *  @author Jorg Janke
 *  @version $Id: ReplenishReport.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 *
 *  Carlos Ruiz globalqss - integrate bug fixing from Chris Farley
 *    [ 1619517 ] Replenish report fails when no records in m_storage
 */
public class ReplenishReport extends JavaProcess
{
	/** Warehouse				*/
	private int		p_M_Warehouse_ID = 0;
	/**	Optional BPartner		*/
	private int		p_C_BPartner_ID = 0;
	/** Create (POO)Purchse Order or (POR)Requisition or (MMM)Movements */
	private String	p_ReplenishmentCreate = null;
	/** Document Type			*/
	private int		p_C_DocType_ID = 0;
	/** Return Info				*/
	private String	m_info = "";

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("ReplenishmentCreate"))
				p_ReplenishmentCreate = (String)para[i].getParameter();
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("M_Warehouse_ID=" + p_M_Warehouse_ID
			+ ", C_BPartner_ID=" + p_C_BPartner_ID
			+ " - ReplenishmentCreate=" + p_ReplenishmentCreate
			+ ", C_DocType_ID=" + p_C_DocType_ID);
		if (p_ReplenishmentCreate != null && p_C_DocType_ID == 0)
			throw new AdempiereUserError("@FillMandatory@ @C_DocType_ID@");

		MWarehouse wh = MWarehouse.get(getCtx(), p_M_Warehouse_ID);
		if (wh.get_ID() == 0)
			throw new AdempiereSystemError("@FillMandatory@ @M_Warehouse_ID@");
		//
		prepareTable();
		fillTable(wh);
		//
		if (p_ReplenishmentCreate == null)
			return "OK";
		//
		MDocType dt = MDocType.get(getCtx(), p_C_DocType_ID);
		if (!dt.getDocBaseType().equals(p_ReplenishmentCreate))
			throw new AdempiereSystemError("@C_DocType_ID@=" + dt.getName() + " <> " + p_ReplenishmentCreate);
		//
		if (p_ReplenishmentCreate.equals("POO"))
			createPO();
		else if (p_ReplenishmentCreate.equals("POR"))
			createRequisition();
		else if (p_ReplenishmentCreate.equals("MMM"))
			createMovements();
		else if (p_ReplenishmentCreate.equals("DOO"))
			createDO();
		return m_info;
	}	//	doIt

	/**
	 * 	Prepare/Check Replenishment Table
	 */
	private void prepareTable()
	{
		//	Level_Max must be >= Level_Max
		String sql = "UPDATE M_Replenish"
			+ " SET Level_Max = Level_Min "
			+ "WHERE Level_Max < Level_Min";
		int no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Corrected Max_Level=" + no);

		//	Minimum Order should be 1
		sql = "UPDATE M_Product_PO"
			+ " SET Order_Min = 1 "
			+ "WHERE Order_Min IS NULL OR Order_Min < 1";
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Corrected Order Min=" + no);

		//	Pack should be 1
		sql = "UPDATE M_Product_PO"
			+ " SET Order_Pack = 1 "
			+ "WHERE Order_Pack IS NULL OR Order_Pack < 1";
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Corrected Order Pack=" + no);

		//	Set Current Vendor where only one vendor
		sql = "UPDATE M_Product_PO p"
			+ " SET IsCurrentVendor='Y' "
			+ "WHERE IsCurrentVendor<>'Y'"
			+ " AND EXISTS (SELECT pp.M_Product_ID FROM M_Product_PO pp "
				+ "WHERE p.M_Product_ID=pp.M_Product_ID "
				+ "GROUP BY pp.M_Product_ID "
				+ "HAVING COUNT(*) = 1)";
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Corrected CurrentVendor(Y)=" + no);

		//	More then one current vendor
		sql = "UPDATE M_Product_PO p"
			+ " SET IsCurrentVendor='N' "
			+ "WHERE IsCurrentVendor = 'Y'"
			+ " AND EXISTS (SELECT pp.M_Product_ID FROM M_Product_PO pp "
				+ "WHERE p.M_Product_ID=pp.M_Product_ID AND pp.IsCurrentVendor='Y' "
				+ "GROUP BY pp.M_Product_ID "
				+ "HAVING COUNT(*) > 1)";
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Corrected CurrentVendor(N)=" + no);

		//	Just to be sure
		sql = "DELETE FROM T_Replenish WHERE AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Deleted Existing Temp=" + no);
	}	//	prepareTable

	/**
	 * 	Fill Table
	 * 	@param wh warehouse
	 */
	private void fillTable (MWarehouse wh) throws Exception
	{
		String sql = "INSERT INTO T_Replenish "
			+ "(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, AD_Client_ID, AD_Org_ID,"
			+ " ReplenishType, Level_Min, Level_Max,"
			+ " C_BPartner_ID, Order_Min, Order_Pack, QtyToOrder, ReplenishmentCreate) "
			+ "SELECT " + getAD_PInstance_ID()
				+ ", r.M_Warehouse_ID, r.M_Product_ID, r.AD_Client_ID, r.AD_Org_ID,"
			+ " r.ReplenishType, r.Level_Min, r.Level_Max,"
			+ " po.C_BPartner_ID, po.Order_Min, po.Order_Pack, 0, ";
		if (p_ReplenishmentCreate == null)
			sql += "null";
		else
			sql += "'" + p_ReplenishmentCreate + "'";
		sql += " FROM M_Replenish r"
			+ " INNER JOIN M_Product_PO po ON (r.M_Product_ID=po.M_Product_ID) "
			+ "WHERE po.IsCurrentVendor='Y'"	//	Only Current Vendor
			+ " AND r.ReplenishType<>'0'"
			+ " AND po.IsActive='Y' AND r.IsActive='Y'"
			+ " AND r.M_Warehouse_ID=" + p_M_Warehouse_ID;
		if (p_C_BPartner_ID != 0)
			sql += " AND po.C_BPartner_ID=" + p_C_BPartner_ID;
		int no = DB.executeUpdate(sql, get_TrxName());
		log.trace(sql);
		log.debug("Insert (1) #" + no);

		if (p_C_BPartner_ID == 0)
		{
			sql = "INSERT INTO T_Replenish "
				+ "(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, AD_Client_ID, AD_Org_ID,"
				+ " ReplenishType, Level_Min, Level_Max,"
				+ " C_BPartner_ID, Order_Min, Order_Pack, QtyToOrder, ReplenishmentCreate) "
				+ "SELECT " + getAD_PInstance_ID()
				+ ", r.M_Warehouse_ID, r.M_Product_ID, r.AD_Client_ID, r.AD_Org_ID,"
				+ " r.ReplenishType, r.Level_Min, r.Level_Max,"
			    + " 0, 1, 1, 0, ";
			if (p_ReplenishmentCreate == null)
				sql += "null";
			else
				sql += "'" + p_ReplenishmentCreate + "'";
			sql	+= " FROM M_Replenish r "
				+ "WHERE r.ReplenishType<>'0' AND r.IsActive='Y'"
				+ " AND r.M_Warehouse_ID=" + p_M_Warehouse_ID
				+ " AND NOT EXISTS (SELECT * FROM T_Replenish t "
					+ "WHERE r.M_Product_ID=t.M_Product_ID"
					+ " AND AD_PInstance_ID=" + getAD_PInstance_ID() + ")";
			no = DB.executeUpdate(sql, get_TrxName());
			log.debug("Insert (BP) #" + no);
		}

		sql = "UPDATE T_Replenish t SET "
			+ "QtyOnHand = (SELECT COALESCE(SUM(QtyOnHand),0) FROM M_Storage s, M_Locator l WHERE t.M_Product_ID=s.M_Product_ID"
				+ " AND l.M_Locator_ID=s.M_Locator_ID AND l.M_Warehouse_ID=t.M_Warehouse_ID),"
			+ "QtyReserved = (SELECT COALESCE(SUM(QtyReserved),0) FROM M_Storage s, M_Locator l WHERE t.M_Product_ID=s.M_Product_ID"
				+ " AND l.M_Locator_ID=s.M_Locator_ID AND l.M_Warehouse_ID=t.M_Warehouse_ID),"
			+ "QtyOrdered = (SELECT COALESCE(SUM(QtyOrdered),0) FROM M_Storage s, M_Locator l WHERE t.M_Product_ID=s.M_Product_ID"
				+ " AND l.M_Locator_ID=s.M_Locator_ID AND l.M_Warehouse_ID=t.M_Warehouse_ID)";
		if (p_C_DocType_ID != 0)
			sql += ", C_DocType_ID=" + p_C_DocType_ID;
		sql += " WHERE AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Update #" + no);

		//	Delete inactive products and replenishments
		sql = "DELETE FROM T_Replenish r "
			+ "WHERE (EXISTS (SELECT * FROM M_Product p "
				+ "WHERE p.M_Product_ID=r.M_Product_ID AND p.IsActive='N')"
			+ " OR EXISTS (SELECT * FROM M_Replenish rr "
				+ " WHERE rr.M_Product_ID=r.M_Product_ID AND rr.IsActive='N'"
				+ " AND rr.M_Warehouse_ID=" + p_M_Warehouse_ID + " ))"
			+ " AND AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Deleted Inactive=" + no);

		//	Ensure Data consistency
		sql = "UPDATE T_Replenish SET QtyOnHand = 0 WHERE QtyOnHand IS NULL";
		no = DB.executeUpdate(sql, get_TrxName());
		sql = "UPDATE T_Replenish SET QtyReserved = 0 WHERE QtyReserved IS NULL";
		no = DB.executeUpdate(sql, get_TrxName());
		sql = "UPDATE T_Replenish SET QtyOrdered = 0 WHERE QtyOrdered IS NULL";
		no = DB.executeUpdate(sql, get_TrxName());

		//	Set Minimum / Maximum Maintain Level
		//	X_M_Replenish.REPLENISHTYPE_ReorderBelowMinimumLevel
		sql = "UPDATE T_Replenish"
			+ " SET QtyToOrder = CASE WHEN QtyOnHand - QtyReserved + QtyOrdered <= Level_Min "
			+ " THEN Level_Max - QtyOnHand + QtyReserved - QtyOrdered "
			+ " ELSE 0 END "
			+ "WHERE ReplenishType='1'"
			+ " AND AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Update Type-1=" + no);
		//
		//	X_M_Replenish.REPLENISHTYPE_MaintainMaximumLevel
		sql = "UPDATE T_Replenish"
			+ " SET QtyToOrder = Level_Max - QtyOnHand + QtyReserved - QtyOrdered "
			+ "WHERE ReplenishType='2'"
			+ " AND AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Update Type-2=" + no);


		//	Minimum Order Quantity
		sql = "UPDATE T_Replenish"
			+ " SET QtyToOrder = Order_Min "
			+ "WHERE QtyToOrder < Order_Min"
			+ " AND QtyToOrder > 0"
			+ " AND AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Set MinOrderQty=" + no);

		//	Even dividable by Pack
		sql = "UPDATE T_Replenish"
			+ " SET QtyToOrder = QtyToOrder - MOD(QtyToOrder, Order_Pack) + Order_Pack "
			+ "WHERE MOD(QtyToOrder, Order_Pack) <> 0"
			+ " AND QtyToOrder > 0"
			+ " AND AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Set OrderPackQty=" + no);

		//	Source from other warehouse
		if (wh.getM_WarehouseSource_ID() != 0)
		{
			sql = "UPDATE T_Replenish"
				+ " SET M_WarehouseSource_ID=" + wh.getM_WarehouseSource_ID()
				+ " WHERE AD_PInstance_ID=" + getAD_PInstance_ID();
			no = DB.executeUpdate(sql, get_TrxName());
			if (no != 0)
				log.debug("Set Source Warehouse=" + no);
		}
		//	Check Source Warehouse
		sql = "UPDATE T_Replenish"
			+ " SET M_WarehouseSource_ID = NULL "
			+ "WHERE M_Warehouse_ID=M_WarehouseSource_ID"
			+ " AND AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Set same Source Warehouse=" + no);

		//	Custom Replenishment
		String className = wh.getReplenishmentClass();
		if (className != null && className.length() > 0)
		{
			//	Get Replenishment Class
			ReplenishInterface custom = null;
			try
			{
				Class<?> clazz = Class.forName(className);
				custom = (ReplenishInterface)clazz.newInstance();
			}
			catch (Exception e)
			{
				throw new AdempiereUserError("No custom Replenishment class "
						+ className + " - " + e.toString());
			}

			X_T_Replenish[] replenishs = getReplenish("ReplenishType='9'");
			for (int i = 0; i < replenishs.length; i++)
			{
				X_T_Replenish replenish = replenishs[i];
				if (replenish.getReplenishType().equals(X_T_Replenish.REPLENISHTYPE_Custom))
				{
					BigDecimal qto = null;
					try
					{
						qto = custom.getQtyToOrder(wh, replenish);
					}
					catch (Exception e)
					{
						log.error(custom.toString(), e);
					}
					if (qto == null)
						qto = Env.ZERO;
					replenish.setQtyToOrder(qto);
					replenish.save();
				}
			}
		}
		//	Delete rows where nothing to order
		sql = "DELETE FROM T_Replenish "
			+ "WHERE QtyToOrder < 1"
		    + " AND AD_PInstance_ID=" + getAD_PInstance_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.debug("Deleted No QtyToOrder=" + no);
	}	//	fillTable

	/**
	 * 	Create PO's
	 */
	private void createPO()
	{
		int noOrders = 0;
		String info = "";
		//
		MOrder order = null;
		MWarehouse wh = null;
		X_T_Replenish[] replenishs = getReplenish("M_WarehouseSource_ID IS NULL");
		for (int i = 0; i < replenishs.length; i++)
		{
			X_T_Replenish replenish = replenishs[i];
			if (wh == null || wh.getM_Warehouse_ID() != replenish.getM_Warehouse_ID())
				wh = MWarehouse.get(getCtx(), replenish.getM_Warehouse_ID());
			//
			if (order == null
				|| order.getC_BPartner_ID() != replenish.getC_BPartner_ID()
				|| order.getM_Warehouse_ID() != replenish.getM_Warehouse_ID())
			{
				order = new MOrder(getCtx(), 0, get_TrxName());
				order.setIsSOTrx(false);
				order.setC_DocTypeTarget_ID(p_C_DocType_ID);
				MBPartner bp = new MBPartner(getCtx(), replenish.getC_BPartner_ID(), get_TrxName());
				order.setBPartner(bp);
				order.setSalesRep_ID(getAD_User_ID());
				order.setDescription(Msg.getMsg(getCtx(), "Replenishment"));
				//	Set Org/WH
				order.setAD_Org_ID(wh.getAD_Org_ID());
				order.setM_Warehouse_ID(wh.getM_Warehouse_ID());
				if (!order.save())
					return;
				log.debug(order.toString());
				noOrders++;
				info += " - " + order.getDocumentNo();
			}
			MOrderLine line = new MOrderLine (order);
			line.setM_Product_ID(replenish.getM_Product_ID());
			line.setQty(replenish.getQtyToOrder());
			line.setPrice();
			line.save();
		}
		m_info = "#" + noOrders + info;
		log.info(m_info);
	}	//	createPO

	/**
	 * 	Create Requisition
	 */
	private void createRequisition()
	{
		int noReqs = 0;
		String info = "";
		//
		MRequisition requisition = null;
		MWarehouse wh = null;
		X_T_Replenish[] replenishs = getReplenish("M_WarehouseSource_ID IS NULL");
		for (int i = 0; i < replenishs.length; i++)
		{
			X_T_Replenish replenish = replenishs[i];
			if (wh == null || wh.getM_Warehouse_ID() != replenish.getM_Warehouse_ID())
				wh = MWarehouse.get(getCtx(), replenish.getM_Warehouse_ID());
			//
			if (requisition == null
				|| requisition.getM_Warehouse_ID() != replenish.getM_Warehouse_ID())
			{
				requisition = new MRequisition (getCtx(), 0, get_TrxName());
				requisition.setAD_User_ID (getAD_User_ID());
				requisition.setC_DocType_ID(p_C_DocType_ID);
				requisition.setDescription(Msg.getMsg(getCtx(), "Replenishment"));
				//	Set Org/WH
				requisition.setAD_Org_ID(wh.getAD_Org_ID());
				requisition.setM_Warehouse_ID(wh.getM_Warehouse_ID());
				if (!requisition.save())
					return;
				log.debug(requisition.toString());
				noReqs++;
				info += " - " + requisition.getDocumentNo();
			}
			//
			MRequisitionLine line = new MRequisitionLine(requisition);
			line.setM_Product_ID(replenish.getM_Product_ID());
			line.setC_BPartner_ID(replenish.getC_BPartner_ID());
			line.setQty(replenish.getQtyToOrder());
			line.setPrice();
			line.save();
		}
		m_info = "#" + noReqs + info;
		log.info(m_info);
	}	//	createRequisition

	/**
	 * 	Create Inventory Movements
	 */
	private void createMovements()
	{
		int noMoves = 0;
		String info = "";
		//
		MClient client = null;
		MMovement move = null;
		int M_Warehouse_ID = 0;
		int M_WarehouseSource_ID = 0;
		MWarehouse whSource = null;
		MWarehouse wh = null;
		X_T_Replenish[] replenishs = getReplenish("M_WarehouseSource_ID IS NOT NULL");
		for (int i = 0; i < replenishs.length; i++)
		{
			X_T_Replenish replenish = replenishs[i];
			if (whSource == null || whSource.getM_WarehouseSource_ID() != replenish.getM_WarehouseSource_ID())
				whSource = MWarehouse.get(getCtx(), replenish.getM_WarehouseSource_ID());
			if (wh == null || wh.getM_Warehouse_ID() != replenish.getM_Warehouse_ID())
				wh = MWarehouse.get(getCtx(), replenish.getM_Warehouse_ID());
			if (client == null || client.getAD_Client_ID() != whSource.getAD_Client_ID())
				client = MClient.get(getCtx(), whSource.getAD_Client_ID());
			//
			if (move == null
				|| M_WarehouseSource_ID != replenish.getM_WarehouseSource_ID()
				|| M_Warehouse_ID != replenish.getM_Warehouse_ID())
			{
				M_WarehouseSource_ID = replenish.getM_WarehouseSource_ID();
				M_Warehouse_ID = replenish.getM_Warehouse_ID();

				move = new MMovement (getCtx(), 0, get_TrxName());
				move.setC_DocType_ID(p_C_DocType_ID);
				move.setDescription(Msg.getMsg(getCtx(), "Replenishment")
					+ ": " + whSource.getName() + "->" + wh.getName());
				//	Set Org
				move.setAD_Org_ID(whSource.getAD_Org_ID());
				if (!move.save())
					return;
				log.debug(move.toString());
				noMoves++;
				info += " - " + move.getDocumentNo();
			}
			//	To
			int M_LocatorTo_ID = wh.getDefaultLocator().getM_Locator_ID();
			//	From: Look-up Storage
			MProduct product = MProduct.get(getCtx(), replenish.getM_Product_ID());
			String MMPolicy = product.getMMPolicy();
			MStorage[] storages = MStorage.getWarehouse(getCtx(),
				whSource.getM_Warehouse_ID(), replenish.getM_Product_ID(), 0, 0,
				true, null,
				MClient.MMPOLICY_FiFo.equals(MMPolicy), get_TrxName());
			//
			BigDecimal target = replenish.getQtyToOrder();
			for (int j = 0; j < storages.length; j++)
			{
				MStorage storage = storages[j];
				if (storage.getQtyOnHand().signum() <= 0)
					continue;
				BigDecimal moveQty = target;
				if (storage.getQtyOnHand().compareTo(moveQty) < 0)
					moveQty = storage.getQtyOnHand();
				//
				MMovementLine line = new MMovementLine(move);
				line.setM_Product_ID(replenish.getM_Product_ID());
				line.setMovementQty(moveQty);
				if (replenish.getQtyToOrder().compareTo(moveQty) != 0)
					line.setDescription("Total: " + replenish.getQtyToOrder());
				line.setM_Locator_ID(storage.getM_Locator_ID());		//	from
				line.setM_AttributeSetInstance_ID(0 /* storage.getM_AttributeSetInstance_ID() */);
				line.setM_LocatorTo_ID(M_LocatorTo_ID);					//	to
				line.setM_AttributeSetInstanceTo_ID(0 /* storage.getM_AttributeSetInstance_ID() */);
				line.save();
				//
				target = target.subtract(moveQty);
				if (target.signum() == 0)
					break;
			}
		}
		if (replenishs.length == 0)
		{
			m_info = "No Source Warehouse";
			log.warn(m_info);
		}
		else
		{
			m_info = "#" + noMoves + info;
			log.info(m_info);
		}
	}	//	Create Inventory Movements

	/**
	 * 	Create Distribution Order
	 */
	private void createDO() throws Exception
	{
		int noMoves = 0;
		String info = "";
		//
		MClient client = null;
		MDDOrder order = null;
		int M_Warehouse_ID = 0;
		int M_WarehouseSource_ID = 0;
		MWarehouse whSource = null;
		MWarehouse wh = null;
		X_T_Replenish[] replenishs = getReplenishDO("M_WarehouseSource_ID IS NOT NULL");
		for (X_T_Replenish replenish:replenishs)
		{
			if (whSource == null || whSource.getM_WarehouseSource_ID() != replenish.getM_WarehouseSource_ID())
				whSource = MWarehouse.get(getCtx(), replenish.getM_WarehouseSource_ID());
			if (wh == null || wh.getM_Warehouse_ID() != replenish.getM_Warehouse_ID())
				wh = MWarehouse.get(getCtx(), replenish.getM_Warehouse_ID());
			if (client == null || client.getAD_Client_ID() != whSource.getAD_Client_ID())
				client = MClient.get(getCtx(), whSource.getAD_Client_ID());
			//
			if (order == null
				|| M_WarehouseSource_ID != replenish.getM_WarehouseSource_ID()
				|| M_Warehouse_ID != replenish.getM_Warehouse_ID())
			{
				M_WarehouseSource_ID = replenish.getM_WarehouseSource_ID();
				M_Warehouse_ID = replenish.getM_Warehouse_ID();

				order = new MDDOrder (getCtx(), 0, get_TrxName());
				order.setC_DocType_ID(p_C_DocType_ID);
				order.setDescription(Msg.getMsg(getCtx(), "Replenishment")
					+ ": " + whSource.getName() + "->" + wh.getName());
				//	Set Org
				order.setAD_Org_ID(whSource.getAD_Org_ID());
				// Set Org Trx
				MOrg orgTrx = MOrg.get(getCtx(), wh.getAD_Org_ID());
				order.setAD_OrgTrx_ID(orgTrx.getAD_Org_ID());
				int C_BPartner_ID = orgTrx.getLinkedC_BPartner_ID(get_TrxName());
				if (C_BPartner_ID==0)
					throw new AdempiereUserError(Msg.translate(getCtx(), "C_BPartner_ID")+ " @FillMandatory@ ");
				MBPartner bp = new MBPartner(getCtx(),C_BPartner_ID,get_TrxName());
				// Set BPartner Link to Org
				order.setBPartner(bp);
				order.setDateOrdered(new Timestamp(System.currentTimeMillis()));
				//order.setDatePromised(DatePromised);
				order.setDeliveryRule(MDDOrder.DELIVERYRULE_Availability);
				order.setDeliveryViaRule(MDDOrder.DELIVERYVIARULE_Delivery);
				order.setPriorityRule(MDDOrder.PRIORITYRULE_Medium);
				order.setIsInDispute(false);
				order.setIsApproved(false);
				order.setIsDropShip(false);
				order.setIsDelivered(false);
				order.setIsInTransit(false);
				order.setIsPrinted(false);
				order.setIsSelected(false);
				order.setIsSOTrx(false);
				// Warehouse in Transit
				MWarehouse[] whsInTransit  = MWarehouse.getForOrg(getCtx(), whSource.getAD_Org_ID());
				for (MWarehouse whInTransit:whsInTransit)
				{
					if(whInTransit.isInTransit())
					order.setM_Warehouse_ID(whInTransit.getM_Warehouse_ID());
				}
				if (order.getM_Warehouse_ID()==0)
					throw new AdempiereUserError("Warehouse inTransit is @FillMandatory@ ");

				if (!order.save())
					return;
				log.debug(order.toString());
				noMoves++;
				info += " - " + order.getDocumentNo();
			}

			//	To
			int M_LocatorTo_ID = wh.getDefaultLocator().getM_Locator_ID();
			int M_Locator_ID = whSource.getDefaultLocator().getM_Locator_ID();
			if(M_LocatorTo_ID == 0 || M_Locator_ID==0)
			throw new AdempiereUserError(Msg.translate(getCtx(), "M_Locator_ID")+" @FillMandatory@ ");

			//	From: Look-up Storage
			/*MProduct product = MProduct.get(getCtx(), replenish.getM_Product_ID());
			MProductCategory pc = MProductCategory.get(getCtx(), product.getM_Product_Category_ID());
			String MMPolicy = pc.getMMPolicy();
			if (MMPolicy == null || MMPolicy.length() == 0)
				MMPolicy = client.getMMPolicy();
			//
			MStorage[] storages = MStorage.getWarehouse(getCtx(),
				whSource.getM_Warehouse_ID(), replenish.getM_Product_ID(), 0, 0,
				true, null,
				MClient.MMPOLICY_FiFo.equals(MMPolicy), get_TrxName());


			BigDecimal target = replenish.getQtyToOrder();
			for (int j = 0; j < storages.length; j++)
			{
				MStorage storage = storages[j];
				if (storage.getQtyOnHand().signum() <= 0)
					continue;
				BigDecimal moveQty = target;
				if (storage.getQtyOnHand().compareTo(moveQty) < 0)
					moveQty = storage.getQtyOnHand();
				//
				MDDOrderLine line = new MDDOrderLine(order);
				line.setM_Product_ID(replenish.getM_Product_ID());
				line.setQtyEntered(moveQty);
				if (replenish.getQtyToOrder().compareTo(moveQty) != 0)
					line.setDescription("Total: " + replenish.getQtyToOrder());
				line.setM_Locator_ID(storage.getM_Locator_ID());		//	from
				line.setM_AttributeSetInstance_ID(storage.getM_AttributeSetInstance_ID());
				line.setM_LocatorTo_ID(M_LocatorTo_ID);					//	to
				line.setM_AttributeSetInstanceTo_ID(storage.getM_AttributeSetInstance_ID());
				line.setIsInvoiced(false);
				line.save();
				//
				target = target.subtract(moveQty);
				if (target.signum() == 0)
					break;
			}*/

			MDDOrderLine line = new MDDOrderLine(order);
			line.setM_Product_ID(replenish.getM_Product_ID());
			line.setQty(replenish.getQtyToOrder());
			if (replenish.getQtyToOrder().compareTo(replenish.getQtyToOrder()) != 0)
				line.setDescription("Total: " + replenish.getQtyToOrder());
			line.setM_Locator_ID(M_Locator_ID);		//	from
			line.setM_AttributeSetInstance_ID(0);
			line.setM_LocatorTo_ID(M_LocatorTo_ID);					//	to
			line.setM_AttributeSetInstanceTo_ID(0);
			line.setIsInvoiced(false);
			line.save();

		}
		if (replenishs.length == 0)
		{
			m_info = "No Source Warehouse";
			log.warn(m_info);
		}
		else
		{
			m_info = "#" + noMoves + info;
			log.info(m_info);
		}
	}	//	create Distribution Order

	/**
	 * 	Get Replenish Records
	 *	@return replenish
	 */
	private X_T_Replenish[] getReplenish (String where)
	{
		String sql = "SELECT * FROM T_Replenish "
			+ "WHERE AD_PInstance_ID=? AND C_BPartner_ID > 0 ";
		if (where != null && where.length() > 0)
			sql += " AND " + where;
		sql	+= " ORDER BY M_Warehouse_ID, M_WarehouseSource_ID, C_BPartner_ID";
		ArrayList<X_T_Replenish> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getAD_PInstance_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new X_T_Replenish (getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		X_T_Replenish[] retValue = new X_T_Replenish[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getReplenish

	/**
	 * 	Get Replenish Records
	 *	@return replenish
	 */
	private X_T_Replenish[] getReplenishDO (String where)
	{
		String sql = "SELECT * FROM T_Replenish "
			+ "WHERE AD_PInstance_ID=? ";
		if (where != null && where.length() > 0)
			sql += " AND " + where;
		sql	+= " ORDER BY M_Warehouse_ID, M_WarehouseSource_ID, C_BPartner_ID";
		ArrayList<X_T_Replenish> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getAD_PInstance_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new X_T_Replenish (getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		X_T_Replenish[] retValue = new X_T_Replenish[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getReplenish

}	//	Replenish
