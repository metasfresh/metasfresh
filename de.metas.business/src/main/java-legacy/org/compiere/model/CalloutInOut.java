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
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;

/**
 * Shipment/Receipt Callouts
 * 
 * @author Jorg Janke
 * @version $Id: CalloutInOut.java,v 1.7 2006/07/30 00:51:05 jjanke Exp $
 * @author victor.perez@e-evolution.com www.e-evolution.com [ 1867464 ]
 *         http://sourceforge
 *         .net/tracker/index.php?func=detail&aid=1867464&group_id
 *         =176962&atid=879332
 * @author kh http://dewiki908/mediawiki/index.php/Fehlerliste_Integrationstest#B062
 */
//metas: synched with rev. 10203 
public class CalloutInOut extends CalloutEngine {

	public static final String MSG_SERIALNO_QTY_ONE = "CalloutInOut.QtySerNoMustBeOne";

	/**
	 * C_Order - Order Defaults.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return error message or ""
	 */
	public String order(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer C_Order_ID = (Integer) value;
		if (C_Order_ID == null || C_Order_ID.intValue() == 0)
			return "";
		// No Callout Active to fire dependent values
		if (isCalloutActive()) // prevent recursive
			return "";

		// Get Details
		MOrder order = new MOrder(ctx, C_Order_ID.intValue(), null);
		if (order.get_ID() != 0) {
			mTab.setValue("DateOrdered", order.getDateOrdered());
			mTab.setValue("POReference", order.getPOReference());
			mTab.setValue("AD_Org_ID", new Integer(order.getAD_Org_ID()));
			mTab.setValue("AD_OrgTrx_ID", new Integer(order.getAD_OrgTrx_ID()));
			mTab.setValue("C_Activity_ID",
					new Integer(order.getC_Activity_ID()));
			mTab.setValue("C_Campaign_ID",
					new Integer(order.getC_Campaign_ID()));
			mTab.setValue("C_Project_ID", new Integer(order.getC_Project_ID()));
			mTab.setValue("User1_ID", new Integer(order.getUser1_ID()));
			mTab.setValue("User2_ID", new Integer(order.getUser2_ID()));
			
			// 05251 begin: we need to use the advisor; code taken from MInOut(MOrder...) constructor
			final I_M_Warehouse wh =  Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
			Check.assumeNotNull(wh, "IWarehouseAdvisor finds a ware house for {}", order);
			mTab.setValue("M_Warehouse_ID", new Integer(wh.getM_Warehouse_ID()));
			// 05251 end
			
			//
			mTab.setValue("DeliveryRule", order.getDeliveryRule());
			mTab.setValue("DeliveryViaRule", order.getDeliveryViaRule());
			mTab.setValue("M_Shipper_ID", new Integer(order.getM_Shipper_ID()));
			mTab.setValue("FreightCostRule", order.getFreightCostRule());
			mTab.setValue("FreightAmt", order.getFreightAmt());

			mTab.setValue("C_BPartner_ID",
					new Integer(order.getC_BPartner_ID()));

			// [ 1867464 ]
			mTab.setValue("C_BPartner_Location_ID", new Integer(order
					.getC_BPartner_Location_ID()));
			mTab.setValue("AD_User_ID", new Integer(order.getAD_User_ID()));
		}
		return "";
	} // order

	/**
	 * M_RMA - RMA Defaults.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return error message or ""
	 */
	public String rma(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer M_RMA_ID = (Integer) value;
		if (M_RMA_ID == null || M_RMA_ID.intValue() == 0)
			return "";
		// No Callout Active to fire dependent values
		if (isCalloutActive()) // prevent recursive
			return "";

		// Get Details
		MRMA rma = new MRMA(ctx, M_RMA_ID.intValue(), null);
		MInOut originalReceipt = rma.getShipment();
		if (rma.get_ID() != 0) {
			mTab.setValue("DateOrdered", originalReceipt.getDateOrdered());
			mTab.setValue("POReference", originalReceipt.getPOReference());
			mTab.setValue("AD_Org_ID", new Integer(originalReceipt
					.getAD_Org_ID()));
			mTab.setValue("AD_OrgTrx_ID", new Integer(originalReceipt
					.getAD_OrgTrx_ID()));
			mTab.setValue("C_Activity_ID", new Integer(originalReceipt
					.getC_Activity_ID()));
			mTab.setValue("C_Campaign_ID", new Integer(originalReceipt
					.getC_Campaign_ID()));
			mTab.setValue("C_Project_ID", new Integer(originalReceipt
					.getC_Project_ID()));
			mTab.setValue("User1_ID",
					new Integer(originalReceipt.getUser1_ID()));
			mTab.setValue("User2_ID",
					new Integer(originalReceipt.getUser2_ID()));
			mTab.setValue("M_Warehouse_ID", new Integer(originalReceipt
					.getM_Warehouse_ID()));
			//
			mTab.setValue("DeliveryRule", originalReceipt.getDeliveryRule());
			mTab.setValue("DeliveryViaRule", originalReceipt
					.getDeliveryViaRule());
			mTab.setValue("M_Shipper_ID", new Integer(originalReceipt
					.getM_Shipper_ID()));
			mTab.setValue("FreightCostRule", originalReceipt
					.getFreightCostRule());
			mTab.setValue("FreightAmt", originalReceipt.getFreightAmt());

			mTab.setValue("C_BPartner_ID", new Integer(originalReceipt
					.getC_BPartner_ID()));

			// [ 1867464 ]
			mTab.setValue("C_BPartner_Location_ID", new Integer(originalReceipt
					.getC_BPartner_Location_ID()));
			mTab.setValue("AD_User_ID", new Integer(originalReceipt
					.getAD_User_ID()));
		}
		return "";
	} // rma

	/**
	 * InOut - DocType. - sets MovementType - gets DocNo
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return error message or ""
	 */
	public String docType(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer C_DocType_ID = (Integer) value;
		if (C_DocType_ID == null || C_DocType_ID.intValue() == 0)
			return "";

		String sql = "SELECT d.DocBaseType, d.IsDocNoControlled, s.CurrentNext, " // 1..3
				+ "s.AD_Sequence_ID, s.StartNewYear, s.DateColumn, d.IsSOTrx " // 4..7
				+ "FROM C_DocType d "
				+ "LEFT OUTER JOIN AD_Sequence s ON (d.DocNoSequence_ID=s.AD_Sequence_ID) "
				+ "WHERE C_DocType_ID=?"; // 1
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Env.setContext(ctx, WindowNo, "C_DocTypeTarget_ID", C_DocType_ID
					.intValue());
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_DocType_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// Set Movement Type
				String DocBaseType = rs.getString("DocBaseType");
				// BF [2708789] Read IsSOTrx from C_DocType
				String trxFlag = rs.getString(7);
				if (DocBaseType.equals("MMS")) // Material Shipments
				/** solve 1648131 bug vpj-cd e-evolution */
				{
					boolean IsSOTrx = "Y".equals(trxFlag);
					if (IsSOTrx)
						mTab.setValue("MovementType", "C-"); // Customer
																// Shipments
					else
						mTab.setValue("MovementType", "V-"); // Vendor Return

				}
				/** END vpj-cd e-evolution */
				else if (DocBaseType.equals("MMR")) // Material Receipts
				/** solve 1648131 bug vpj-cd e-evolution */
				{
					boolean IsSOTrx = "Y".equals(trxFlag);
					if (IsSOTrx)
						mTab.setValue("MovementType", "C+"); // Customer Return
					else
						mTab.setValue("MovementType", "V+"); // Vendor Receipts
				}
				if (!(trxFlag.equals(mTab.getValue("IsSOTrx"))))
					mTab.setValue("IsSOTrx", trxFlag);
				/** END vpj-cd e-evolution */

				// DocumentNo
				if (rs.getString("IsDocNoControlled").equals("Y")) {
					if ("Y".equals(rs.getString(5))) {
						String dateColumn = rs.getString(6);
						int AD_Sequence_ID = rs.getInt(4);
						mTab
								.setValue("DocumentNo", "<"
										+ MSequence.getPreliminaryNoByYear(
												mTab, AD_Sequence_ID,
												dateColumn, null) + ">");
					} else
						mTab.setValue("DocumentNo", "<"
								+ rs.getString("CurrentNext") + ">");
				}
			}
		} catch (SQLException e) {
			log.error(sql, e);
			return e.getLocalizedMessage();
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return "";
	} // docType

	/**
	 * M_InOut - Defaults for BPartner. - Location - Contact
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return error message or ""
	 */
	public String bpartner(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer C_BPartner_ID = (Integer) value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
			return "";

		//metas: B062: Aenderung fuer die Ermittlung von Preisliste und Anschrift. 
		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
				+ "p.M_PriceList_ID,p.PaymentRule,p.POReference,"
				+ "p.SO_Description,p.IsDiscountPrinted,"
				+ "p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
				+ "l.C_BPartner_Location_ID,c.AD_User_ID,"
				+ "COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID,"
				+ "COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID "
				+ "FROM C_BPartner p"
				+ " JOIN C_BP_Group g ON p.C_BP_Group_ID=g.C_BP_Group_ID"
				+ " LEFT JOIN C_BPartner_Location l ON p.C_BPartner_ID=l.C_BPartner_ID"
				+ " LEFT JOIN AD_User c ON p.C_BPartner_ID=c.C_BPartner_ID "
				+ "WHERE l.IsActive='Y'"
				+ " AND p.C_BPartner_ID=?"; // 1
		//metas: ende

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// [ 1867464 ]
				boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo,
						"IsSOTrx"));
				
				// metas: B062: Abfrage und Zuweisung von Preisliste und Anschrift
				// (aus CalloutOrder Kopiert)
				
				// PriceList (indirect: IsTaxIncluded & Currency)
				Integer ii = new Integer(rs.getInt(IsSOTrx ? "M_PriceList_ID"
						: "PO_PriceList_ID"));
				if (!rs.wasNull())
					mTab.setValue("M_PriceList_ID", ii);
				else { // get default PriceList
					int i = Env.getContextAsInt(ctx, "#M_PriceList_ID");
					if (i != 0)
						mTab.setValue("M_PriceList_ID", new Integer(i));
				}
				int shipTo_ID = rs.getInt("C_BPartner_Location_ID");
				// overwritten by InfoBP selection - works only if InfoWindow
				// was used otherwise creates error (uses last value, may belong
				// to differnt BP)
				if (C_BPartner_ID.toString().equals(
						Env.getContext(ctx, WindowNo, Env.TAB_INFO,
								"C_BPartner_ID"))) {
					String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO,
							"C_BPartner_Location_ID");
					if (loc.length() > 0)
						shipTo_ID = Integer.parseInt(loc);
				}
				if (shipTo_ID == 0)
					mTab.setValue("C_BPartner_Location_ID", null);
				else
					mTab.setValue("C_BPartner_Location_ID", new Integer(
							shipTo_ID));
				//metas: B062 ende
				
				if (!IsSOTrx) {
					// Location
					ii = new Integer(rs
							.getInt("C_BPartner_Location_ID"));
					if (rs.wasNull())
						mTab.setValue("C_BPartner_Location_ID", null);
					else
						mTab.setValue("C_BPartner_Location_ID", ii);
					// Contact
					ii = new Integer(rs.getInt("AD_User_ID"));
					if (rs.wasNull())
						mTab.setValue("AD_User_ID", null);
					else
						mTab.setValue("AD_User_ID", ii);
				}

				// Bugs item #1679818: checking for SOTrx only
				if (IsSOTrx) {
					// CreditAvailable
					double CreditAvailable = rs.getDouble("CreditAvailable");
					if (!rs.wasNull() && CreditAvailable < 0)
						mTab.fireDataStatusEEvent("CreditLimitOver",
								DisplayType.getNumberFormat(DisplayType.Amount)
										.format(CreditAvailable), false);
				}//
			}
		} catch (SQLException e) {
			log.error(sql, e);
			return e.getLocalizedMessage();
		} finally {
			DB.close(rs, pstmt);
		}

		return "";
	} // bpartner

	/**
	 * M_Warehouse. Set Organization and Default Locator
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return error message or ""
	 */
	public String warehouse(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		if (isCalloutActive())
			return "";
		Integer M_Warehouse_ID = (Integer) value;
		if (M_Warehouse_ID == null || M_Warehouse_ID.intValue() == 0)
			return "";

		String sql = "SELECT w.AD_Org_ID, l.M_Locator_ID "
				+ "FROM M_Warehouse w"
				+ " LEFT OUTER JOIN M_Locator l ON (l.M_Warehouse_ID=w.M_Warehouse_ID AND l.IsDefault='Y') "
				+ "WHERE w.M_Warehouse_ID=?"; // 1

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Warehouse_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// Org
				Integer ii = new Integer(rs.getInt(1));
				int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");
				if (AD_Org_ID != ii.intValue())
					mTab.setValue("AD_Org_ID", ii);
				// Locator
				ii = new Integer(rs.getInt(2));
				if (rs.wasNull())
					Env.setContext(ctx, WindowNo, 0, "M_Locator_ID", null);
				else {
					log.info("M_Locator_ID=" + ii);
					Env
							.setContext(ctx, WindowNo, "M_Locator_ID", ii
									.intValue());
				}
			}
		} catch (SQLException e) {
			log.error(sql, e);
			return e.getLocalizedMessage();
		} finally {
			DB.close(rs, pstmt);
		}

		return "";
	} // warehouse

	/**************************************************************************
	 * OrderLine Callout
	 * 
	 * @param ctx
	 *            context
	 * @param WindowNo
	 *            window no
	 * @param mTab
	 *            tab model
	 * @param mField
	 *            field model
	 * @param value
	 *            new value
	 * @return error message or ""
	 */
	public String orderLine(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer C_OrderLine_ID = (Integer) value;
		if (C_OrderLine_ID == null || C_OrderLine_ID.intValue() == 0)
			return "";

		// Get Details
		MOrderLine ol = new MOrderLine(ctx, C_OrderLine_ID.intValue(), null);
		if (ol.get_ID() != 0) {
			if (ol.getC_Charge_ID() > 0 && ol.getM_Product_ID() <= 0) {
				mTab.setValue("C_Charge_ID", new Integer(ol.getC_Charge_ID()));
			} else {
				mTab
						.setValue("M_Product_ID", new Integer(ol
								.getM_Product_ID()));
				mTab.setValue("M_AttributeSetInstance_ID", new Integer(ol
						.getM_AttributeSetInstance_ID()));
			}
			//
			mTab.setValue("C_UOM_ID", new Integer(ol.getC_UOM_ID()));
			BigDecimal MovementQty = ol.getQtyOrdered().subtract(
					ol.getQtyDelivered());
			mTab.setValue("MovementQty", MovementQty);
			BigDecimal QtyEntered = MovementQty;
			if (ol.getQtyEntered().compareTo(ol.getQtyOrdered()) != 0)
				QtyEntered = QtyEntered.multiply(ol.getQtyEntered()).divide(
						ol.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP);
			mTab.setValue("QtyEntered", QtyEntered);
			//
			mTab.setValue("C_Activity_ID", new Integer(ol.getC_Activity_ID()));
			mTab.setValue("C_Campaign_ID", new Integer(ol.getC_Campaign_ID()));
			mTab.setValue("C_Project_ID", new Integer(ol.getC_Project_ID()));
			mTab.setValue("C_ProjectPhase_ID", new Integer(ol
					.getC_ProjectPhase_ID()));
			mTab.setValue("C_ProjectTask_ID", new Integer(ol
					.getC_ProjectTask_ID()));
			mTab.setValue("AD_OrgTrx_ID", new Integer(ol.getAD_OrgTrx_ID()));
			mTab.setValue("User1_ID", new Integer(ol.getUser1_ID()));
			mTab.setValue("User2_ID", new Integer(ol.getUser2_ID()));
		}
		return "";
	} // orderLine

	/**************************************************************************
	 * RMALine Callout
	 * 
	 * @param ctx
	 *            context
	 * @param WindowNo
	 *            window no
	 * @param mTab
	 *            tab model
	 * @param mField
	 *            field model
	 * @param value
	 *            new value
	 * @return error message or ""
	 */
	public String rmaLine(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer M_RMALine_id = (Integer) value;
		if (M_RMALine_id == null || M_RMALine_id.intValue() == 0)
			return "";

		// Get Details
		MRMALine rl = new MRMALine(ctx, M_RMALine_id.intValue(), null);
		if (rl.get_ID() != 0) {
			if (rl.getC_Charge_ID() > 0 && rl.getM_Product_ID() <= 0) {
				mTab.setValue("C_Charge_ID", new Integer(rl.getC_Charge_ID()));
			} else {
				mTab
						.setValue("M_Product_ID", new Integer(rl
								.getM_Product_ID()));
				mTab.setValue("M_AttributeSetInstance_ID", new Integer(rl
						.getM_AttributeSetInstance_ID()));
			}
			//
			mTab.setValue("C_UOM_ID", new Integer(rl.getC_UOM_ID()));
			BigDecimal MovementQty = rl.getQty().subtract(rl.getQtyDelivered());
			mTab.setValue("MovementQty", MovementQty);
			BigDecimal QtyEntered = MovementQty;
			mTab.setValue("QtyEntered", QtyEntered);
			//
			mTab.setValue("C_Activity_ID", new Integer(rl.getC_Activity_ID()));
			mTab.setValue("C_Campaign_ID", new Integer(rl.getC_Campaign_ID()));
			mTab.setValue("C_Project_ID", new Integer(rl.getC_Project_ID()));
			mTab.setValue("C_ProjectPhase_ID", new Integer(rl
					.getC_ProjectPhase_ID()));
			mTab.setValue("C_ProjectTask_ID", new Integer(rl
					.getC_ProjectTask_ID()));
			mTab.setValue("AD_OrgTrx_ID", new Integer(rl.getAD_OrgTrx_ID()));
			mTab.setValue("User1_ID", new Integer(rl.getUser1_ID()));
			mTab.setValue("User2_ID", new Integer(rl.getUser2_ID()));
		}
		return "";
	} // rmaLine

	/**
	 * M_InOutLine - Default UOM/Locator for Product.
	 * 
	 * @param ctx
	 *            context
	 * @param WindowNo
	 *            window no
	 * @param mTab
	 *            tab model
	 * @param mField
	 *            field model
	 * @param value
	 *            new value
	 * @return error message or ""
	 */
	public String product(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		if (isCalloutActive())
			return "";
		Integer M_Product_ID = (Integer) value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";

		// Set Attribute & Locator
		int M_Locator_ID = 0;
		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID
				.intValue()
				&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO,
						"M_AttributeSetInstance_ID") != 0) {
			mTab.setValue("M_AttributeSetInstance_ID", new Integer(Env
					.getContextAsInt(ctx, WindowNo, Env.TAB_INFO,
							"M_AttributeSetInstance_ID")));
			M_Locator_ID = Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO,
					"M_Locator_ID");
			if (M_Locator_ID != 0)
				mTab.setValue("M_Locator_ID", new Integer(M_Locator_ID));
		} else
			mTab.setValue("M_AttributeSetInstance_ID", null);
		//
		int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo,
				"M_Warehouse_ID");
		boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
		if (IsSOTrx) {
			return "";
		}

		// Set UOM/Locator/Qty
		MProduct product = MProduct.get(ctx, M_Product_ID.intValue());
		mTab.setValue("C_UOM_ID", new Integer(product.getC_UOM_ID()));
		BigDecimal QtyEntered = (BigDecimal) mTab.getValue("QtyEntered");
		mTab.setValue("MovementQty", QtyEntered);
		if (M_Locator_ID != 0)
			; // already set
		else if (product.getM_Locator_ID() != 0) {
			MLocator loc = MLocator.get(ctx, product.getM_Locator_ID());
			if (M_Warehouse_ID == loc.getM_Warehouse_ID())
				mTab.setValue("M_Locator_ID", new Integer(product
						.getM_Locator_ID()));
			else
				log.debug("No Locator for M_Product_ID=" + M_Product_ID
						+ " and M_Warehouse_ID=" + M_Warehouse_ID);
		} else
			log.debug("No Locator for M_Product_ID=" + M_Product_ID);
		return "";
	} // product

	/**
	 * InOut Line - Quantity. - called from C_UOM_ID, QtyEntered, MovementQty -
	 * enforces qty UOM relationship
	 * 
	 * @param ctx
	 *            context
	 * @param WindowNo
	 *            window no
	 * @param mTab
	 *            tab model
	 * @param mField
	 *            field model
	 * @param value
	 *            new value
	 * @return error message or ""
	 */
	public String qty(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		if (isCalloutActive() || value == null)
			return "";

		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		// log.warn("qty - init - M_Product_ID=" + M_Product_ID);
		BigDecimal MovementQty, QtyEntered;

		// No Product
		if (M_Product_ID == 0) {
			QtyEntered = (BigDecimal) mTab.getValue("QtyEntered");
			mTab.setValue("MovementQty", QtyEntered);
		}
		// UOM Changed - convert from Entered -> Product
		else if (mField.getColumnName().equals("C_UOM_ID")) {
			int C_UOM_To_ID = ((Integer) value).intValue();
			QtyEntered = (BigDecimal) mTab.getValue("QtyEntered");
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx,
					C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0) {
				log.debug("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID
						+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			MovementQty = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyEntered);
			if (MovementQty == null)
				MovementQty = QtyEntered;
			boolean conversion = QtyEntered.compareTo(MovementQty) != 0;
			log.debug("UOM=" + C_UOM_To_ID + ", QtyEntered=" + QtyEntered
					+ " -> " + conversion + " MovementQty=" + MovementQty);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y"
					: "N");
			mTab.setValue("MovementQty", MovementQty);
		}
		// No UOM defined
		else if (Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID") == 0) {
			QtyEntered = (BigDecimal) mTab.getValue("QtyEntered");
			mTab.setValue("MovementQty", QtyEntered);
		}
		// QtyEntered changed - calculate MovementQty
		else if (mField.getColumnName().equals("QtyEntered")) {
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyEntered = (BigDecimal) value;

			// metas: make sure that MovementQty must be 1 for a product with a
			// serial number.
			final Integer attributeSetInstanceId = (Integer) mTab
					.getValue(I_M_InOutLine.COLUMNNAME_M_AttributeSetInstance_ID);
			if (attributeSetInstanceId != null && attributeSetInstanceId > 0) {
				final MAttributeSetInstance attributeSetInstance = MAttributeSetInstance
						.get(Env.getCtx(), attributeSetInstanceId, 0);
				if (attributeSetInstance != null) {
					final String serNo = attributeSetInstance.getSerNo();
					if (serNo != null && !"".equals(serNo)
							&& QtyEntered.compareTo(BigDecimal.ONE) > 0) {

						Services.get(IClientUI.class).info(WindowNo, null, MSG_SERIALNO_QTY_ONE);
						QtyEntered = BigDecimal.ONE;
						mTab.setValue("QtyEntered", QtyEntered);
					}
				}
			}
			// metas end
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx,
					C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0) {
				log.debug("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID
						+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			MovementQty = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyEntered);
			if (MovementQty == null)
				MovementQty = QtyEntered;
			boolean conversion = QtyEntered.compareTo(MovementQty) != 0;
			log.debug("UOM=" + C_UOM_To_ID + ", QtyEntered=" + QtyEntered
					+ " -> " + conversion + " MovementQty=" + MovementQty);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y"
					: "N");
			mTab.setValue("MovementQty", MovementQty);
		}
		// MovementQty changed - calculate QtyEntered (should not happen)
		else if (mField.getColumnName().equals("MovementQty")) {
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			MovementQty = (BigDecimal) value;
			int precision = MProduct.get(ctx, M_Product_ID).getUOMPrecision();
			BigDecimal MovementQty1 = MovementQty.setScale(precision,
					BigDecimal.ROUND_HALF_UP);
			if (MovementQty.compareTo(MovementQty1) != 0) {
				log.debug("Corrected MovementQty " + MovementQty + "->"
						+ MovementQty1);
				MovementQty = MovementQty1;
				mTab.setValue("MovementQty", MovementQty);
			}
			QtyEntered = MUOMConversion.convertFromProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, MovementQty);
			if (QtyEntered == null)
				QtyEntered = MovementQty;
			boolean conversion = MovementQty.compareTo(QtyEntered) != 0;
			log.debug("UOM=" + C_UOM_To_ID + ", MovementQty=" + MovementQty
					+ " -> " + conversion + " QtyEntered=" + QtyEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y"
					: "N");
			mTab.setValue("QtyEntered", QtyEntered);
		}
		//
		return "";
	} // qty

	/**
	 * M_InOutLine - ASI.
	 * 
	 * @param ctx
	 *            context
	 * @param WindowNo
	 *            window no
	 * @param mTab
	 *            tab model
	 * @param mField
	 *            field model
	 * @param value
	 *            new value
	 * @return error message or ""
	 */
	public String asi(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		if (isCalloutActive())
			return "";
		Integer M_ASI_ID = (Integer) value;
		if (M_ASI_ID == null || M_ASI_ID.intValue() == 0)
			return "";
		//
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo,
				"M_Warehouse_ID");
		int M_Locator_ID = Env.getContextAsInt(ctx, WindowNo, "M_Locator_ID");
		log.debug("M_Product_ID=" + M_Product_ID + ", M_ASI_ID=" + M_ASI_ID
				+ " - M_Warehouse_ID=" + M_Warehouse_ID + ", M_Locator_ID="
				+ M_Locator_ID);
		// Check Selection
		int M_AttributeSetInstance_ID = Env.getContextAsInt(Env.getCtx(),
				WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID");
		if (M_ASI_ID.intValue() == M_AttributeSetInstance_ID) {
			int selectedM_Locator_ID = Env.getContextAsInt(Env.getCtx(),
					WindowNo, Env.TAB_INFO, "M_Locator_ID");
			if (selectedM_Locator_ID != 0) {
				log.debug("Selected M_Locator_ID=" + selectedM_Locator_ID);
				mTab
						.setValue("M_Locator_ID", new Integer(
								selectedM_Locator_ID));
			}
		}

		// metas: make sure that MovementQty must be 1 for a product with a
		// serial number.
		final MAttributeSetInstance attributeSetInstance = MAttributeSetInstance
				.get(Env.getCtx(), M_AttributeSetInstance_ID, 0);
		final BigDecimal qtyEntered = (BigDecimal) mTab
				.getValue(I_M_InOutLine.COLUMNNAME_QtyEntered);
		if (attributeSetInstance != null) {
			final String serNo = attributeSetInstance.getSerNo();
			if (serNo != null
					&& !"".equals(serNo)
					&& (qtyEntered == null || qtyEntered
							.compareTo(BigDecimal.ONE) != 0)) {

				Services.get(IClientUI.class).info(WindowNo, null, MSG_SERIALNO_QTY_ONE);
				mTab.setValue(I_M_InOutLine.COLUMNNAME_QtyEntered,
						BigDecimal.ONE);
			}
		}
		// metas end
		return "";
	} // asi

} // CalloutInOut
