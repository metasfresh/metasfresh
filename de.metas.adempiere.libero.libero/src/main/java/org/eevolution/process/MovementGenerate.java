/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Movement;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MClient;
import org.compiere.model.MLocator;
import org.compiere.model.MMovementLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MStorage;
import org.compiere.process.DocAction;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderMovementBuilder;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;

import de.metas.document.engine.IDocActionBL;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;
import de.metas.product.IProductBL;



/**
 *	Generate Movement
 *	Manual or Automatic
 *	
 *  @author Victor Perez www.e-evolution.com
 *  @version $Id: MovementGenerate.java,v 1.0 
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
@Deprecated // please review before considering to re-use
public class MovementGenerate extends SvrProcess
{
	/**	Manual Selection		*/
	private boolean 	p_Selection = false;
	/** Warehouse				*/
	private int			p_M_Warehouse_ID = 0;
	/** BPartner				*/
	private int			p_C_BPartner_ID = 0;
	/** Promise Date			*/
	private Timestamp	p_DatePromised = null;
	/** Include Orders w. unconfirmed Shipments	*/
	private boolean		p_IsUnconfirmedInOut = false;
	/** DocAction				*/
	private String		p_docAction = DocAction.ACTION_Complete;
	/** Consolidate				*/
	private boolean		p_ConsolidateDocument = true;
    /** Shipment Date                       */
	private Timestamp       p_DateShipped = null;
	
	/**	The current Shipment	*/
	private I_M_Movement		m_movement = null;
	/** Numner of Shipments		*/
	private int			m_created = 0;
	/**	Line Number				*/
	private int			m_line = 0;
	/** Movement Date			*/
	private Timestamp	m_movementDate = null;
	/**	Last BP Location		*/
	private int			m_lastC_BPartner_Location_ID = -1;

	/** The Query sql			*/
	private String 		m_sql = null;

	
	/** Storages temp space				*/
	private HashMap<SParameter,MStorage[]> m_map = new HashMap<SParameter,MStorage[]>();
	/** Last Parameter					*/
	private SParameter		m_lastPP = null;
	/** Last Storage					*/
	private MStorage[]		m_lastStorages = null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		for (ProcessInfoParameter para: getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = para.getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para.getParameterAsInt();
			else if (name.equals("DatePromised"))
				p_DatePromised = (Timestamp)para.getParameter();
			else if (name.equals("Selection"))
				p_Selection = "Y".equals(para.getParameter());
			else if (name.equals("IsUnconfirmedInOut"))
				p_IsUnconfirmedInOut = "Y".equals(para.getParameter());
			else if (name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = "Y".equals(para.getParameter());
			else if (name.equals("DocAction"))
				p_docAction = (String)para.getParameter();
			else if (name.equals("MovementDate"))
                p_DateShipped = (Timestamp)para.getParameter();
			else
				log.error("Unknown Parameter: " + name);
			
			//  juddm - added ability to specify a shipment date from Generate Shipments
			if (p_DateShipped == null) {
				m_movementDate = Env.getContextAsDate(getCtx(), "#Date");
				if (m_movementDate == null)
					m_movementDate = new Timestamp(System.currentTimeMillis());
			} else
				m_movementDate = p_DateShipped;
			//	DocAction check
			if (!DocAction.ACTION_Complete.equals(p_docAction))
				p_docAction = DocAction.ACTION_Prepare;
		}
	}	//	prepare

	/**
	 * 	Generate Movemements
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt () throws Exception
	{
		log.info("Selection=" + p_Selection
			+ ", M_Warehouse_ID=" + p_M_Warehouse_ID 
			+ ", C_BPartner_ID=" + p_C_BPartner_ID 
			+ ", Consolidate=" + p_ConsolidateDocument
			+ ", IsUnconfirmed=" + p_IsUnconfirmedInOut
			+ ", Movement=" + m_movementDate);
		
		if (p_M_Warehouse_ID == 0)
			throw new AdempiereUserError("@NotFound@ @M_Warehouse_ID@");
		
		if (p_Selection)	//	VInOutGen
		{
			m_sql = "SELECT DD_Order.* FROM DD_Order, T_Selection "
				+ "WHERE DD_Order.DocStatus='CO' AND DD_Order.IsSOTrx='Y' AND DD_Order.AD_Client_ID=? "
				+ "AND DD_Order.DD_Order_ID = T_Selection.T_Selection_ID " 
				+ "AND T_Selection.AD_PInstance_ID=? ";
		}
		else
		{
			m_sql = "SELECT * FROM DD_Order o "
				+ "WHERE DocStatus='CO' AND IsSOTrx='Y'"
				//	No Offer,POS
				+ " AND o.C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType "
					+ "WHERE DocBaseType='DOO')"
				+ "	AND o.IsDropShip='N'"
				//	No Manual
				+ " AND o.DeliveryRule<>'M'"
				//	Open Order Lines with Warehouse
				+ " AND EXISTS (SELECT * FROM DD_OrderLine ol "
					+ "WHERE ol.M_Warehouse_ID=?";					//	#1
			if (p_DatePromised != null)
				m_sql += " AND TRUNC(ol.DatePromised)<=?";		//	#2
			m_sql += " AND o.DD_Order_ID=ol.DD_Order_ID AND ol.QtyOrdered<>ol.QtyIntransit)";
			//
			if (p_C_BPartner_ID != 0)
				m_sql += " AND o.C_BPartner_ID=?";					//	#3
		}
		m_sql += " ORDER BY M_Warehouse_ID, PriorityRule, M_Shipper_ID, C_BPartner_ID, C_BPartner_Location_ID, DD_Order_ID";
	//	m_sql += " FOR UPDATE";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (m_sql, get_TrxName());
			int index = 1;
			if (p_Selection)
			{
				pstmt.setInt(index++, Env.getAD_Client_ID(getCtx()));
				pstmt.setInt(index++, getAD_PInstance_ID());
			}
			else	
			{
				pstmt.setInt(index++, p_M_Warehouse_ID);
				if (p_DatePromised != null)
					pstmt.setTimestamp(index++, p_DatePromised);
				if (p_C_BPartner_ID != 0)
					pstmt.setInt(index++, p_C_BPartner_ID);
			}
		}
		catch (Exception e)
		{
			log.error(m_sql, e);
		}
		return generate(pstmt);
	}	//	doIt
	
	/**
	 * 	Generate Shipments
	 * 	@param pstmt Order Query
	 *	@return info
	 */
	private String generate (PreparedStatement pstmt)
	{
		MClient client = MClient.get(getCtx());
		try
		{
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())		//	Order
			{
				MDDOrder order = new MDDOrder (getCtx(), rs, get_TrxName());
				//	New Header different Shipper, Shipment Location
				if (!p_ConsolidateDocument 
					|| (m_movement != null 
					&& (m_movement.getC_BPartner_Location_ID() != order.getC_BPartner_Location_ID()
					|| m_movement.getM_Shipper_ID() != order.getM_Shipper_ID() )))
				{
					completeMovement();
				}
				log.debug("check: " + order + " - DeliveryRule=" + order.getDeliveryRule());
				//
				Timestamp minGuaranteeDate = m_movementDate;
				boolean completeOrder = MDDOrder.DELIVERYRULE_CompleteOrder.equals(order.getDeliveryRule());
				//	OrderLine WHERE
				String where = " " + p_M_Warehouse_ID + " IN (SELECT l.M_Warehouse_ID FROM M_Locator l WHERE l.M_Locator_ID=M_Locator_ID) ";
				if (p_DatePromised != null)
					where += " AND (TRUNC(DatePromised)<=" + DB.TO_DATE(p_DatePromised, true)
						+ " OR DatePromised IS NULL)";		
				//	Exclude Auto Delivery if not Force
				if (!MDDOrder.DELIVERYRULE_Force.equals(order.getDeliveryRule()))
					where += " AND (DD_OrderLine.M_Product_ID IS NULL"
						+ " OR EXISTS (SELECT * FROM M_Product p "
						+ "WHERE DD_OrderLine.M_Product_ID=p.M_Product_ID"
						+ " AND IsExcludeAutoDelivery='N'))";
				//	Exclude Unconfirmed
				if (!p_IsUnconfirmedInOut)
					where += " AND NOT EXISTS (SELECT * FROM M_MovementLine iol"
							+ " INNER JOIN M_Movement io ON (iol.M_Movement_ID=io.M_Movement_ID) "
								+ "WHERE iol.DD_OrderLine_ID=DD_OrderLine.DD_OrderLine_ID AND io.DocStatus IN ('IP','WC'))";
				//	Deadlock Prevention - Order by M_Product_ID
				MDDOrderLine[] lines = order.getLines (where, "M_Product_ID");
				for (int i = 0; i < lines.length; i++)
				{
					MDDOrderLine line = lines[i];
					MLocator l = new MLocator(getCtx(),line.getM_Locator_ID(), get_TrxName());
					if (l.getM_Warehouse_ID() != p_M_Warehouse_ID)
						continue;
					log.debug("check: " + line);
					BigDecimal onHand = Env.ZERO;
					//BigDecimal toDeliver = line.getQtyOrdered()
					BigDecimal toDeliver = line.getConfirmedQty()
						.subtract(line.getQtyDelivered());
					MProduct product = line.getProduct();
					//	Nothing to Deliver
					if (product != null && toDeliver.signum() == 0)
						continue;
					
					// or it's a charge - Bug#: 1603966 
					if (line.getC_Charge_ID()!=0 && toDeliver.signum() == 0)
						continue;
					
					//	Check / adjust for confirmations
					BigDecimal unconfirmedShippedQty = Env.ZERO;
					if (p_IsUnconfirmedInOut && product != null && toDeliver.signum() != 0)
					{
						String where2 = "EXISTS (SELECT * FROM M_Movement io WHERE io.M_Movement_ID=M_MovementLine.M_Movement_ID AND io.DocStatus IN ('IP','WC'))";
						MMovementLine[] iols = MMovementLine.getOfOrderLine(getCtx(), 
							line.getDD_OrderLine_ID(), where2, null);
						for (int j = 0; j < iols.length; j++) 
							unconfirmedShippedQty = unconfirmedShippedQty.add(iols[j].getMovementQty());
						String logInfo = "Unconfirmed Qty=" + unconfirmedShippedQty 
							+ " - ToDeliver=" + toDeliver + "->";					
						toDeliver = toDeliver.subtract(unconfirmedShippedQty);
						logInfo += toDeliver;
						if (toDeliver.signum() < 0)
						{
							toDeliver = Env.ZERO;
							logInfo += " (set to 0)";
						}
						//	Adjust On Hand
						onHand = onHand.subtract(unconfirmedShippedQty);
						log.debug(logInfo);					
					}
					
					//	Comments & lines w/o product & services
					if ((product == null || !Services.get(IProductBL.class).isStocked(product))
						&& (line.getQtyOrdered().signum() == 0 	//	comments
							|| toDeliver.signum() != 0))		//	lines w/o product
					{
						if (!MDDOrder.DELIVERYRULE_CompleteOrder.equals(order.getDeliveryRule()))	//	printed later
							createLine (order, line, toDeliver, null, false);
						continue;
					}

					//	Stored Product
					MProductCategory pc = MProductCategory.get(order.getCtx(), product.getM_Product_Category_ID());
					String MMPolicy = pc.getMMPolicy();
					if (MMPolicy == null || MMPolicy.length() == 0)
						MMPolicy = client.getMMPolicy();
					//
					MStorage[] storages = getStorages(l.getM_Warehouse_ID(), 
						line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
						product.getM_AttributeSet_ID(),
						line.getM_AttributeSetInstance_ID()==0, minGuaranteeDate, 
						MClient.MMPOLICY_FiFo.equals(MMPolicy)); 
					
					for (int j = 0; j < storages.length; j++)
					{
						MStorage storage = storages[j];
						onHand = onHand.add(storage.getQtyOnHand());
					}
					boolean fullLine = onHand.compareTo(toDeliver) >= 0
						|| toDeliver.signum() < 0;
					
					//	Complete Order
					if (completeOrder && !fullLine)
					{
						log.debug("Failed CompleteOrder - OnHand=" + onHand 
							+ " (Unconfirmed=" + unconfirmedShippedQty
							+ "), ToDeliver=" + toDeliver + " - " + line);
						completeOrder = false;
						break;
					}
					//	Complete Line
					else if (fullLine && MDDOrder.DELIVERYRULE_CompleteLine.equals(order.getDeliveryRule()))
					{
						log.debug("CompleteLine - OnHand=" + onHand 
							+ " (Unconfirmed=" + unconfirmedShippedQty
							+ ", ToDeliver=" + toDeliver + " - " + line);
						//	
						createLine (order, line, toDeliver, storages, false);
					}
					//	Availability
					else if (MDDOrder.DELIVERYRULE_Availability.equals(order.getDeliveryRule())
						&& (onHand.signum() > 0
							|| toDeliver.signum() < 0))
					{
						BigDecimal deliver = toDeliver;
						if (deliver.compareTo(onHand) > 0)
							deliver = onHand;
						log.debug("Available - OnHand=" + onHand 
							+ " (Unconfirmed=" + unconfirmedShippedQty
							+ "), ToDeliver=" + toDeliver 
							+ ", Delivering=" + deliver + " - " + line);
						//	
						createLine (order, line, deliver, storages, false);
					}
					//	Force
					else if (MDDOrder.DELIVERYRULE_Force.equals(order.getDeliveryRule()))
					{
						BigDecimal deliver = toDeliver;
						log.debug("Force - OnHand=" + onHand 
							+ " (Unconfirmed=" + unconfirmedShippedQty
							+ "), ToDeliver=" + toDeliver 
							+ ", Delivering=" + deliver + " - " + line);
						//	
						createLine (order, line, deliver, storages, true);
					}
					//	Manual
					else if (MDDOrder.DELIVERYRULE_Manual.equals(order.getDeliveryRule()))
						log.debug("Manual - OnHand=" + onHand 
							+ " (Unconfirmed=" + unconfirmedShippedQty
							+ ") - " + line);
					else
						log.debug("Failed: " + order.getDeliveryRule() + " - OnHand=" + onHand 
							+ " (Unconfirmed=" + unconfirmedShippedQty
							+ "), ToDeliver=" + toDeliver + " - " + line);
				}	//	for all order lines
				
				//	Complete Order successful
				if (completeOrder && MDDOrder.DELIVERYRULE_CompleteOrder.equals(order.getDeliveryRule()))
				{
					for (int i = 0; i < lines.length; i++)
					{
						MDDOrderLine line = lines[i];
						MLocator l = new MLocator(getCtx(),line.getM_Locator_ID(), get_TrxName());
						if (l.getM_Warehouse_ID() != p_M_Warehouse_ID)
							continue;
						MProduct product = line.getProduct();
						BigDecimal toDeliver = line.getQtyOrdered().subtract(line.getQtyDelivered());
						//
						MStorage[] storages = null;
						if (product != null && Services.get(IProductBL.class).isStocked(product))
						{
							MProductCategory pc = MProductCategory.get(order.getCtx(), product.getM_Product_Category_ID());
							String MMPolicy = pc.getMMPolicy();
							if (MMPolicy == null || MMPolicy.length() == 0)
								MMPolicy = client.getMMPolicy();
							//
							storages = getStorages(l.getM_Warehouse_ID(), 
								line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
								product.getM_AttributeSet_ID(),
								line.getM_AttributeSetInstance_ID()==0, minGuaranteeDate, 
								MClient.MMPOLICY_FiFo.equals(MMPolicy));
						}
						//	
						createLine (order, line, toDeliver, storages, false);
					}
				}
				m_line += 1000;
			}	//	while order
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(m_sql, e);
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
		completeMovement();
		return "@Created@ = " + m_created;
	}	//	generate
	
	
	
	/**************************************************************************
	 * 	Create Line
	 *	@param Distribution order order
	 *	@param orderLine line
	 *	@param qty qty
	 *	@param storages storage info
	 *	@param force force delivery
	 */
	private void createLine (MDDOrder order, MDDOrderLine orderLine, BigDecimal qty, 
		MStorage[] storages, boolean force)
	{
		//	Complete last Shipment - can have multiple shipments
		if (m_lastC_BPartner_Location_ID != order.getC_BPartner_Location_ID() )
			completeMovement();
		m_lastC_BPartner_Location_ID = order.getC_BPartner_Location_ID();
		//	Create New Shipment
		if (m_movement == null)
		{
			m_movement = createMovement(order, m_movementDate);
			//m_movement.setM_Warehouse_ID(orderLine.getM_Warehouse_ID());	//	sets Org too
			m_movement.setIsInTransit(true);
			m_movement.setDD_Order_ID(order.getDD_Order_ID());
			if (order.getC_BPartner_ID() != order.getC_BPartner_ID())
				m_movement.setC_BPartner_ID(order.getC_BPartner_ID());
			if (order.getC_BPartner_Location_ID() != order.getC_BPartner_Location_ID())
				m_movement.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
			InterfaceWrapperHelper.save(m_movement);
		}
		//	Non Inventory Lines
		if (storages == null)
		{
			MMovementLine line = new MMovementLine (m_movement);
			line.setOrderLine(orderLine, Env.ZERO, false);
			line.setMovementQty(qty);	//	Correct UOM
			if (orderLine.getQtyEntered().compareTo(orderLine.getQtyOrdered()) != 0)
				line.setMovementQty(qty
					.multiply(orderLine.getQtyEntered())
					.divide(orderLine.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP));
			line.setLine(m_line + orderLine.getLine());
			if (!line.save())
				throw new IllegalStateException("Could not create Shipment Line");
			log.debug(line.toString());
			return;
		}
		
		//	Product
		MProduct product = orderLine.getProduct();
		boolean linePerASI = false;
		if (product.getM_AttributeSet_ID() != 0)
		{
			MAttributeSet mas = MAttributeSet.get(getCtx(), product.getM_AttributeSet_ID());
			linePerASI = mas.isInstanceAttribute();
		}
		
		//	Inventory Lines
		ArrayList<MMovementLine> list = new ArrayList<MMovementLine>();
		BigDecimal toDeliver = qty;
		for (int i = 0; i < storages.length; i++)
		{
			MStorage storage = storages[i];
			BigDecimal deliver = toDeliver;
			//	Not enough On Hand
			if (deliver.compareTo(storage.getQtyOnHand()) > 0 
				&& storage.getQtyOnHand().signum() >= 0)		//	positive storage
			{
				if (!force	//	Adjust to OnHand Qty  
					|| (force && i+1 != storages.length))	//	if force not on last location
					deliver = storage.getQtyOnHand();
			}
			if (deliver.signum() == 0)	//	zero deliver
				continue;
			int M_Locator_ID = storage.getM_Locator_ID();
			//
			MMovementLine line = null;
			if (!linePerASI)	//	find line with Locator
			{
				for (int ll = 0; ll < list.size(); ll++)
				{
					MMovementLine test = (MMovementLine)list.get(ll);
					if (test.getM_Locator_ID() == M_Locator_ID)
					{
						line = test;
						break;
					}
				}
			}
			if (line == null)	//	new line
			{
				line = new MMovementLine (m_movement);
				line.setOrderLine(orderLine,  deliver , false);
				line.setMovementQty(deliver);
				list.add(line);
			}
			else				//	existing line
				line.setMovementQty(line.getMovementQty().add(deliver));
			if (orderLine.getQtyEntered().compareTo(orderLine.getQtyOrdered()) != 0)
				line.setMovementQty(line.getMovementQty().multiply(orderLine.getQtyEntered())
					.divide(orderLine.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP));
			line.setLine(m_line + orderLine.getLine());
			if (linePerASI)
				line.setM_AttributeSetInstance_ID(storage.getM_AttributeSetInstance_ID());
			if (!line.save())
				throw new IllegalStateException("Could not create Shipment Line");
			log.debug("ToDeliver=" + qty + "/" + deliver + " - " + line);
			toDeliver = toDeliver.subtract(deliver);
			//	Temp adjustment
			storage.setQtyOnHand(storage.getQtyOnHand().subtract(deliver));
			//
			if (toDeliver.signum() == 0)
				break;
		}		
		if (toDeliver.signum() != 0)
			throw new IllegalStateException("Not All Delivered - Remainder=" + toDeliver);
	}	//	createLine
	
	private static I_M_Movement createMovement(MDDOrder order, Timestamp movementDate)
	{
		final Timestamp movementDateActual;
		if (movementDate != null)
		{
			movementDateActual = movementDate;
		}
		else
		{
			//	Default - Today
			movementDateActual = SystemTime.asDayTimestamp();
		}
		
		final IDDOrderMovementBuilder movementBuilder = Services.get(IDDOrderBL.class).createMovementBuilder();
		movementBuilder.setDD_Order(order);
		movementBuilder.setMovementDate(movementDateActual);
		final I_M_Movement movement = movementBuilder.getCreateMovementHeader();
		return movement;
	}

	
	/**
	 * 	Get Storages
	 *	@param M_Warehouse_ID
	 *	@param M_Product_ID
	 *	@param M_AttributeSetInstance_ID
	 *	@param M_AttributeSet_ID
	 *	@param allAttributeInstances
	 *	@param minGuaranteeDate
	 *	@param FiFo
	 *	@return storages
	 */
	private MStorage[] getStorages(int M_Warehouse_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID, int M_AttributeSet_ID,
		boolean allAttributeInstances, Timestamp minGuaranteeDate,
		boolean FiFo)
	{
		m_lastPP = new SParameter(M_Warehouse_ID, 
			M_Product_ID, M_AttributeSetInstance_ID, M_AttributeSet_ID,
			allAttributeInstances, minGuaranteeDate, FiFo);
		//
		m_lastStorages = m_map.get(m_lastPP); 
		
		if (m_lastStorages == null)
		{
			m_lastStorages = MStorage.getWarehouse(getCtx(), 
				M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
				M_AttributeSet_ID, allAttributeInstances, minGuaranteeDate, 
				FiFo, get_TrxName());
			m_map.put(m_lastPP, m_lastStorages);
		}
		return m_lastStorages;
	}	//	getStorages
	
	
	/**
	 * 	Complete Shipment
	 */
	private void completeMovement()
	{
		if (m_movement != null)
		{
			//	Fails if there is a confirmation
			if (!Services.get(IDocActionBL.class).processIt(m_movement, p_docAction))
				log.warn("Failed: " + m_movement);
			InterfaceWrapperHelper.save(m_movement);
			//
			addLog(m_movement.getM_Movement_ID(), m_movement.getMovementDate(), null, m_movement.getDocumentNo());
			m_created++;
			m_map = new HashMap<SParameter,MStorage[]>();
			if (m_lastPP != null && m_lastStorages != null)
				m_map.put(m_lastPP, m_lastStorages);
		}
		m_movement = null;
		m_line = 0;
	}	//	completeOrder
	
	/**
	 * 	MovementGenerate Parameter
	 */
	class SParameter
	{
		/**
		 * 	Parameter
		 *	@param p_Warehouse_ID warehouse
		 *	@param p_Product_ID 
		 *	@param p_AttributeSetInstance_ID 
		 *	@param p_AttributeSet_ID
		 *	@param p_allAttributeInstances 
		 *	@param p_minGuaranteeDate
		 *	@param p_FiFo
		 */
		protected SParameter (int p_Warehouse_ID, 
			int p_Product_ID, int p_AttributeSetInstance_ID, int p_AttributeSet_ID,
			boolean p_allAttributeInstances, Timestamp p_minGuaranteeDate,
			boolean p_FiFo)
		{
			this.M_Warehouse_ID = p_Warehouse_ID;
			this.M_Product_ID = p_Product_ID;
			this.M_AttributeSetInstance_ID = p_AttributeSetInstance_ID; 
			this.M_AttributeSet_ID = p_AttributeSet_ID;
			this.allAttributeInstances = p_allAttributeInstances;
			this.minGuaranteeDate = p_minGuaranteeDate;
			this.FiFo = p_FiFo;	
		}
		/** Warehouse		*/
		public int M_Warehouse_ID;
		/** Product			*/
		public int M_Product_ID;
		/** ASI				*/
		public int M_AttributeSetInstance_ID;
		/** AS				*/
		public int M_AttributeSet_ID;
		/** All instances	*/
		public boolean allAttributeInstances;
		/** Mon Guarantee Date	*/
		public Timestamp minGuaranteeDate;
		/** FiFo			*/
		public boolean FiFo;

		/**
		 * 	Equals
		 *	@param obj
		 *	@return true if equal
		 */
		public boolean equals (Object obj)
		{
			if (obj != null && obj instanceof SParameter)
			{
				SParameter cmp = (SParameter)obj;
				boolean eq = cmp.M_Warehouse_ID == M_Warehouse_ID
					&& cmp.M_Product_ID == M_Product_ID
					&& cmp.M_AttributeSetInstance_ID == M_AttributeSetInstance_ID
					&& cmp.M_AttributeSet_ID == M_AttributeSet_ID
					&& cmp.allAttributeInstances == allAttributeInstances
					&& cmp.FiFo == FiFo;
				if (eq)
				{
					if (cmp.minGuaranteeDate == null && minGuaranteeDate == null)
						;
					else if (cmp.minGuaranteeDate != null && minGuaranteeDate != null
						&& cmp.minGuaranteeDate.equals(minGuaranteeDate))
						;
					else
						eq = false;
				}
				return eq;
			}
			return false;
		}	//	equals
		
		/**
		 * 	hashCode
		 *	@return hash code
		 */
		public int hashCode ()
		{
			long hash = M_Warehouse_ID
				+ (M_Product_ID * 2)
				+ (M_AttributeSetInstance_ID * 3)
				+ (M_AttributeSet_ID * 4);

			if (allAttributeInstances)
				hash *= -1;
			if (FiFo);	
				hash *= -2;
			if (hash < 0)
				hash = -hash + 7;
			while (hash > Integer.MAX_VALUE)
				hash -= Integer.MAX_VALUE;
			//
			if (minGuaranteeDate != null)
			{
				hash += minGuaranteeDate.hashCode();
				while (hash > Integer.MAX_VALUE)
					hash -= Integer.MAX_VALUE;
			}
				
			return (int)hash;
		}	//	hashCode
		
	}	//	Parameter
	
}	//	MovementGenerate
