/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.BPartnerNoBillToAddressException;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.documentNo.impl.IDocumentNoInfo;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.MetasfreshLastError;
import de.metas.product.IProductBL;

/**
 * Order Callouts. metas 24.09.2008: Aenderungen durchgefuehrt um das Verhalten bei der Auswahl von Liefer- und Rechnungsadressen (sowie Geschaeftspartnern) zu beeinflussen. So werden jetzt im Feld
 * Lieferadressen nur die Adressen angezeigt, die auch als solche in der Location ausgewiesen sind. Ebenso bei den Rechnungsadressen.
 * <li>Contains proposed <a
 * href="https://sourceforge.net/tracker/index.php?func=detail&aid=1883543&group_id=176962&atid=879332" >BF_1883543</a></li>
 *
 * @author Jorg Janke
 * @author Mark Ostermann
 */
// metas: synched with rev 9749
public class CalloutOrder extends CalloutEngine
{
	private static final String CTX_EnforcePriceLimit = "EnforcePriceLimit";
	private static final String CTX_UOMConversion = "UOMConversion";

	private static final String MSG_CreditLimitOver = "CreditLimitOver";
	private static final String MSG_UnderLimitPrice = "UnderLimitPrice";

	/** Debug Steps */
	private boolean steps = false;

	// FIXME: QtyAvailable field does not exist. Pls check and drop following code.
	// public static final String COLNAME_QTY_AVAIL = "QtyAvailable";

	/**
	 * Order Header Change - DocType. - InvoiceRuld/DeliveryRule/PaymentRule - temporary Document Context: - DocSubType - HasCharges - (re-sets Business Partner info of required)
	 */
	public String docType(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(order.getC_DocTypeTarget())
				.setOldDocType_ID(order.getC_DocType_ID())
				.setOldDocumentNo(order.getDocumentNo())
				.setDocumentModel(order)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return NO_ERROR;
		}

		// Set Context: Document Sub Type for Sales Orders
		final String docSubType = documentNoInfo.getDocSubType();
		order.setOrderType(docSubType);

		// No Drop Ship other than Standard
		final boolean dataCopy = calloutField.isRecordCopyingMode(); // 05291
		if (!MOrder.DocSubType_Standard.equals(docSubType) && !dataCopy)
		{
			order.setIsDropShip(false);
		}

		//
		// Delivery Rule
		if (MOrder.DocSubType_POS.equals(docSubType))
		{
			order.setDeliveryRule(X_C_Order.DELIVERYRULE_Force);
		}
		// NOTE: Don't override default configured DeliveryRule (see task 09250)
		// else
		// {
		// if (DocSubType.equals(MOrder.DocSubType_Prepay))
		// {
		// order.setDeliveryRule(X_C_Order.DELIVERYRULE_CompleteOrder);
		// }
		// else
		// {
		// order.setDeliveryRule(X_C_Order.DELIVERYRULE_Availability);
		// }
		// }

		// Invoice Rule
		if (MOrder.DocSubType_POS.equals(docSubType)
				|| MOrder.DocSubType_Prepay.equals(docSubType)
				|| MOrder.DocSubType_OnCredit.equals(docSubType))
		{
			order.setInvoiceRule(X_C_Order.INVOICERULE_Immediate);
		}
		else
		{
			order.setInvoiceRule(X_C_Order.INVOICERULE_AfterDelivery);
		}

		// Payment Rule - POS Order
		if (MOrder.DocSubType_POS.equals(docSubType))
		{
			order.setPaymentRule(X_C_Order.PAYMENTRULE_Cash);
		}
		else
		{
			order.setPaymentRule(X_C_Order.PAYMENTRULE_OnCredit);
		}

		// Set Context:
		calloutField.putContext(I_C_DocType.COLUMNNAME_HasCharges, documentNoInfo.isHasChanges());

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			order.setDocumentNo(documentNoInfo.getDocumentNo());
		}

		//
		// When BPartner is changed, the Rules are not set if
		// it is a POS or Credit Order (i.e. defaults from Standard
		// BPartner)
		// This re-reads the Rules and applies them.
		if (MOrder.DocSubType_POS.equals(docSubType)
				|| MOrder.DocSubType_Prepay.equals(docSubType))  // not
		{
			// for POS/PrePay
			;
		}
		else
		{
			final I_C_BPartner bpartner = order.getC_BPartner();
			if (bpartner != null && bpartner.getC_BPartner_ID() > 0)
			{
				final boolean IsSOTrx = documentNoInfo.isSOTrx();

				// PaymentRule
				{
					String paymentRule = IsSOTrx ? bpartner.getPaymentRule() : bpartner.getPaymentRulePO();
					if (paymentRule != null && paymentRule.length() != 0)
					{
						if (IsSOTrx
								// No Cash/Check/Transfer:
								&& (X_C_Order.PAYMENTRULE_Cash.equals(paymentRule)
										|| X_C_Order.PAYMENTRULE_Check.equals(paymentRule)
										|| "U".equals(paymentRule) // FIXME: we no longer have this PaymentRule... so drop it from here
								))
						{
							// for SO_Trx
							paymentRule = X_C_Order.PAYMENTRULE_OnCredit; // Payment Term
						}
						if (!IsSOTrx && (X_C_Order.PAYMENTRULE_Cash.equals(paymentRule)))  // No Cash for PO_Trx
						{
							paymentRule = X_C_Order.PAYMENTRULE_OnCredit; // Payment Term
						}
						order.setPaymentRule(paymentRule);
					}
				}

				// Payment Term
				{
					final int paymentTermId = IsSOTrx ? bpartner.getC_PaymentTerm_ID() : bpartner.getPO_PaymentTerm_ID();
					if (paymentTermId > 0)
					{
						order.setC_PaymentTerm_ID(paymentTermId);
					}
				}

				// InvoiceRule
				{
					final String invoiceRule = bpartner.getInvoiceRule();
					if (invoiceRule != null && invoiceRule.length() != 0)
					{
						order.setInvoiceRule(invoiceRule);
					}
				}

				// DeliveryRule
				{
					final String deliveryRule = bpartner.getDeliveryRule();
					if (deliveryRule != null && deliveryRule.length() != 0)
					{
						order.setDeliveryRule(deliveryRule);
					}
				}

				// FreightCostRule
				{
					final String freightCostRule = bpartner.getFreightCostRule();
					if (freightCostRule != null && freightCostRule.length() != 0)
					{
						order.setFreightCostRule(freightCostRule);
					}
				}

				// DeliveryViaRule
				{
					final String deliveryViaRule = bpartner.getDeliveryViaRule();
					if (deliveryViaRule != null && deliveryViaRule.length() != 0)
					{
						order.setDeliveryViaRule(deliveryViaRule);
					}
				}
			}
		}

		//
		return NO_ERROR;
	}

	/**
	 * Order Header - BPartner. - M_PriceList_ID (+ Context) - C_BPartner_Location_ID - Bill_BPartner_ID/Bill_Location_ID - AD_User_ID - POReference - SO_Description - IsDiscountPrinted -
	 * InvoiceRule/DeliveryRule/PaymentRule/FreightCost/DeliveryViaRule - C_PaymentTerm_ID
	 *
	 * @param ctx Context
	 * @param WindowNo current Window No
	 * @param mTab Model Tab
	 * @param mField Model Field
	 * @param value The new value
	 * @return Error message or ""
	 */
	public String bPartnerLocation(final ICalloutField calloutField)
	{
		// FIXME !!!

		// 05291: In case current record is on dataNew phase with Copy option set
		// then just don't update the Bill fields but let them copy from original record
		if (calloutField.isRecordCopyingMode())
		{
			return NO_ERROR;
		}

		final I_C_Order order = calloutField.getModel(I_C_Order.class);

		if (order == null)
		{
			return NO_ERROR; // nothing to do
		}

		if (order.getC_BPartner() == null)
		{
			return NO_ERROR; // nothing to do
		}

		final IOrderBL orderBL = Services.get(IOrderBL.class);

		if (null == order.getC_BPartner_Location())
		{
			orderBL.setBPLocation(order, order.getC_BPartner());
		}

		if (!orderBL.setBillLocation(order))
		{
			throw new BPartnerNoBillToAddressException(order.getC_BPartner());
		}

		return NO_ERROR;
	}

	/**
	 * Order Header - BPartner. - M_PriceList_ID (+ Context) - C_BPartner_Location_ID - Bill_BPartner_ID/Bill_Location_ID - AD_User_ID - POReference - SO_Description - IsDiscountPrinted -
	 * InvoiceRule/DeliveryRule/PaymentRule/FreightCost/DeliveryViaRule - C_PaymentTerm_ID
	 *
	 * @param ctx Context
	 * @param WindowNo current Window No
	 * @param mTab Model Tab
	 * @param mField Model Field
	 * @param value The new value
	 * @return Error message or ""
	 */
	public String bPartner(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final int C_BPartner_ID = order.getC_BPartner_ID();
		if (C_BPartner_ID <= 0)
		{
			return NO_ERROR;
		}
		final boolean IsSOTrx = order.isSOTrx();
		// #928: Make sure the user is of the right SOTrx value and it is set as default for that SOTrx value.
		final String userFlag = IsSOTrx ? I_AD_User.COLUMNNAME_IsSalesContact : I_AD_User.COLUMNNAME_IsPurchaseContact;

		final String defaultUserFlag = IsSOTrx ? I_AD_User.COLUMNNAME_IsSalesContact_Default : I_AD_User.COLUMNNAME_IsPurchaseContact_Default;

		// task FRESH-152: Joining with the BPartner Stats.
		// will use the table and column names so if somebody wants to know the references of the stats table, he will also get here

		final String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
				+ " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
				+ " p.SO_Description,p.IsDiscountPrinted,"
				+ " p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
				+ " p.SO_CreditLimit, p.SO_CreditLimit-stats."
				+ I_C_BPartner_Stats.COLUMNNAME_SO_CreditUsed
				+ " AS CreditAvailable,"
				+ " lship.C_BPartner_Location_ID,c.AD_User_ID,"
				+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID,"
				+ " lbill.C_BPartner_Location_ID AS Bill_Location_ID, stats."
				+ I_C_BPartner_Stats.COLUMNNAME_SOCreditStatus
				+ ", "
				+ " p.SalesRep_ID, p.SO_DocTypeTarget_ID "
				+ "FROM C_BPartner p"

				+ " INNER JOIN "
				+ I_C_BPartner_Stats.Table_Name
				+ " stats ON (p."
				+ I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " = stats."
				+ I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID
				+ ")"
				+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID)"
				+ " LEFT OUTER JOIN C_BPartner_Location lbill ON (p.C_BPartner_ID=lbill.C_BPartner_ID AND lbill.IsBillTo='Y' AND lbill.IsActive='Y')"
				+ " LEFT OUTER JOIN C_BPartner_Location lship ON (p.C_BPartner_ID=lship.C_BPartner_ID AND lship.IsShipTo='Y' AND lship.IsActive='Y')"
				+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) AND c.IsActive = 'Y' "
				// #928
				// Only join with Users that have the right SOTrx value (Sales Contact for SO and PurchaseContact for PO) and are set as default for that SOTrxValue
				+ " AND c." + userFlag + "= 'Y'"
				+ " AND c." + defaultUserFlag + " = 'Y' "
				+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'"
				// metas (2009 0027 G1): making sure that the default billTo
				// and shipTo location is used
				+ " ORDER BY lbill." + I_C_BPartner_Location.COLUMNNAME_IsBillTo + " DESC"
				+ " , lship." + I_C_BPartner_Location.COLUMNNAME_IsShipTo + " DESC"
				// metas end
				// 08578 take default users first.
				// #928: The IsDefaultContact is no longer important
				// + " , c." + I_AD_User.COLUMNNAME_IsDefaultContact + " DESC"
				+ " , c." + I_AD_User.COLUMNNAME_AD_User_ID + " ASC ";; // #1

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, C_BPartner_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				// metas: Auftragsart aus Kunde
				Integer docTypeTargetId = rs.getInt("SO_DocTypeTarget_ID");
				if (IsSOTrx && docTypeTargetId > 0)
				{
					order.setC_DocTypeTarget_ID(docTypeTargetId);
				}

				// Sales Rep - If BP has a default SalesRep then default it
				Integer salesRepId = rs.getInt("SalesRep_ID");
				if (IsSOTrx && salesRepId != 0)
				{
					order.setSalesRep_ID(salesRepId);
				}
				/*
				 * // PriceList (indirect: IsTaxIncluded & Currency) Integer ii = rs.getInt(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID"); if (!rs.wasNull())
				 * mTab.setValue("M_PriceList_ID", ii); else { // get default PriceList int i = Env.getContextAsInt(ctx, "#M_PriceList_ID"); if (i != 0) mTab.setValue("M_PriceList_ID", new
				 * Integer(i)); }
				 */
				int shipTo_ID = rs.getInt("C_BPartner_Location_ID");
				// overwritten by InfoBP selection - works only if InfoWindow
				// was used otherwise creates error (uses last value, may belong
				// to different BP)
				if (C_BPartner_ID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int locFromContextId = calloutField.getTabInfoContextAsInt("C_BPartner_Location_ID");
					if (locFromContextId > 0)
					{
						// metas: before using the location we need to make sure
						// that it is a shipping location
						if (new MBPartnerLocation(calloutField.getCtx(), locFromContextId, null).isShipTo())
						{
							shipTo_ID = locFromContextId;
						}
						// metas end
					}
				}
				order.setC_BPartner_Location_ID(shipTo_ID <= 0 ? -1 : shipTo_ID);

				// metas (2009 0027 G1): setting billTo location. Why has it
				// been selected above when it isn't used?
				final int billTo_ID = rs.getInt("Bill_Location_ID");
				if (billTo_ID <= 0)
				{
					order.setBill_Location(null);
				}
				else
				{
					order.setBill_Location_ID(billTo_ID);
				}
				// metas: end

				// metas: Einkaufsgenossenschaft
				// TODO auskommentiert, weil Aufruf macht nur aus C_OrderLine heraus Sinn
				// z. Zt. Aufruf des calcLocation-Nachfolgers via tax (u.a. aus
				// C_OrderLine.C_BPartnerLocation)
				// calcLocation(ctx, WindowNo, mTab, mField, value);

				// Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				if (C_BPartner_ID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int tabInfoContactId = calloutField.getTabInfoContextAsInt("AD_User_ID");
					if (tabInfoContactId > 0)
						contID = tabInfoContactId;
				}
				if (contID <= 0)
					order.setAD_User_ID(-1);
				else
				{
					order.setAD_User_ID(contID);
					order.setBill_User_ID(contID);
				}

				// CreditAvailable
				if (IsSOTrx)
				{
					if (isChkCreditLimit(rs, true))
					{
						double CreditAvailable = rs.getDouble("CreditAvailable");
						if (!rs.wasNull() && CreditAvailable < 0)
						{
							calloutField.fireDataStatusEEvent(MSG_CreditLimitOver, DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable), false);
						}
					}
				}

				// PO Reference
				String s = rs.getString("POReference");
				if (s != null && s.length() != 0)
					order.setPOReference(s);
				// should not be reset to null if we entered already value!
				// VHARCQ, accepted YS makes sense that way
				// TODO: should get checked and removed if no longer needed!
				/*
				 * else mTab.setValue("POReference", null);
				 */

				// SO Description
				s = rs.getString("SO_Description");
				if (s != null && s.trim().length() != 0)
					order.setDescription(s);
				// IsDiscountPrinted
				s = rs.getString("IsDiscountPrinted");
				order.setIsDiscountPrinted(DisplayType.toBoolean(s));

				// Defaults, if not Walkin Receipt or Walkin Invoice
				final String OrderType = order.getOrderType();
				order.setInvoiceRule(X_C_Order.INVOICERULE_AfterDelivery);
				// mTab.setValue("DeliveryRule", X_C_Order.DELIVERYRULE_Availability); // nop, shall use standard defaults (see task 09250)
				order.setPaymentRule(X_C_Order.PAYMENTRULE_OnCredit);
				if (MOrder.DocSubType_Prepay.equals(OrderType))
				{
					order.setInvoiceRule(X_C_Order.INVOICERULE_Immediate);
					// mTab.setValue("DeliveryRule", X_C_Order.DELIVERYRULE_Availability); // nop, shall use standard defaults (see task 09250)
				}
				else if (MOrder.DocSubType_POS.equals(OrderType))  // for POS
				{
					order.setPaymentRule(X_C_Order.PAYMENTRULE_Cash);
				}
				else
				{
					// PaymentRule
					s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
					if (s != null && s.length() != 0)
					{
						if (s.equals("B"))  // No Cache in Non POS
							s = "P";
						if (IsSOTrx && (s.equals("S") || s.equals("U")))  // No Check/Transfer for SO_Trx
							s = "P"; // Payment Term
						order.setPaymentRule(s);
					}
					// Payment Term
					Integer ii = rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID");
					if (!rs.wasNull())
						order.setC_PaymentTerm_ID(ii);
					// InvoiceRule
					s = rs.getString("InvoiceRule");
					if (s != null && s.length() != 0)
						order.setInvoiceRule(s);
					// DeliveryRule
					s = rs.getString("DeliveryRule");
					if (s != null && s.length() != 0)
						order.setDeliveryRule(s);
					// FreightCostRule
					s = rs.getString("FreightCostRule");
					if (s != null && s.length() != 0)
						order.setFreightCostRule(s);
					// DeliveryViaRule
					s = rs.getString("DeliveryViaRule");
					if (s != null && s.length() != 0)
					{
						if (IsSOTrx)  // task: 06914: for purchase orders, we use another C_BPartner column
						{
							order.setDeliveryViaRule(s);
						}
					}

				}
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return NO_ERROR;
	} // bPartner

	/**
	 * Order Header - Invoice BPartner. - M_PriceList_ID (+ Context) - Bill_Location_ID - Bill_User_ID - POReference - SO_Description - IsDiscountPrinted - InvoiceRule/PaymentRule - C_PaymentTerm_ID
	 *
	 * @param ctx Context
	 * @param WindowNo current Window No
	 * @param mTab Model Tab
	 * @param mField Model Field
	 * @param value The new value
	 * @return Error message or ""
	 */
	public String bPartnerBill(final ICalloutField calloutField)
	{
		if (isCalloutActive())
			return NO_ERROR;
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final int bill_BPartner_ID = order.getBill_BPartner_ID();
		if (bill_BPartner_ID <= 0)
			return NO_ERROR;

		// #928: Make sure the user is of the right SOTrx value and it is set as default for that SOTrx value.

		final boolean isSOTrx = order.isSOTrx();
		final String userFlag = isSOTrx ? I_AD_User.COLUMNNAME_IsSalesContact : I_AD_User.COLUMNNAME_IsPurchaseContact;

		final String defaultUserFlag = isSOTrx ? I_AD_User.COLUMNNAME_IsSalesContact_Default : I_AD_User.COLUMNNAME_IsPurchaseContact_Default;

		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
				+ "p.M_PriceList_ID,p.PaymentRule,p.POReference,"
				+ "p.SO_Description,p.IsDiscountPrinted,"
				+ "p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
				+ "p.SO_CreditLimit, p.SO_CreditLimit-stats."

				+ I_C_BPartner_Stats.COLUMNNAME_SO_CreditUsed
				+ " AS CreditAvailable,"
				+ "c.AD_User_ID,"
				+ "p.PO_PriceList_ID, p.PaymentRulePO, p.PO_PaymentTerm_ID,"
				+ "lbill.C_BPartner_Location_ID AS Bill_Location_ID "
				+ "FROM C_BPartner p"

				+ " INNER JOIN "
				+ I_C_BPartner_Stats.Table_Name
				+ " stats ON (p."
				+ I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " = stats."
				+ I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID
				+ ")"
				+ " LEFT OUTER JOIN C_BPartner_Location lbill ON (p.C_BPartner_ID=lbill.C_BPartner_ID AND lbill.IsBillTo='Y' AND lbill.IsActive='Y')"
				+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
				// #928
				// Only join with Users that have the right SOTrx value (Sales Contact for SO and PurchaseContact for PO) and are set as default for that SOTrxValue
				+ " AND c." + userFlag + "= 'Y'"
				+ " AND c." + defaultUserFlag + " = 'Y' "
				+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'"
				// metas: (2009 0027 G1): making sure that the default billTo
				// location is used
				+ " ORDER BY " + I_C_BPartner_Location.COLUMNNAME_IsBillToDefault + " DESC"
		// metas end
		; // #1

		boolean IsSOTrx = order.isSOTrx();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, bill_BPartner_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				// PriceList (indirect: IsTaxIncluded & Currency)
				final int priceListId = rs.getInt(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID");
				if (!rs.wasNull())
				{
					order.setM_PriceList_ID(priceListId);
				}
				else
				{ // get default PriceList
					int i = calloutField.getGlobalContextAsInt("#M_PriceList_ID");
					if (i > 0)
						order.setM_PriceList_ID(i);
				}

				int bill_Location_ID = rs.getInt("Bill_Location_ID");
				// overwritten by InfoBP selection - works only if InfoWindow
				// was used otherwise creates error (uses last value, may belong
				// to differnt BP)
				if (bill_BPartner_ID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int tabInfoBPartnerLocationId = calloutField.getTabInfoContextAsInt("C_BPartner_Location_ID");
					if (tabInfoBPartnerLocationId > 0)
						bill_Location_ID = tabInfoBPartnerLocationId;
				}
				if (bill_Location_ID <= 0)
					order.setBill_Location_ID(-1);
				else
					order.setBill_Location_ID(bill_Location_ID);

				// Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				if (bill_BPartner_ID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int tabInfoContactId = calloutField.getTabInfoContextAsInt("AD_User_ID");
					if (tabInfoContactId > 0)
						contID = tabInfoContactId;
				}
				if (contID <= 0)
					order.setBill_User(null);
				else
					order.setBill_User_ID(contID);

				// CreditAvailable
				if (IsSOTrx)
				{
					if (isChkCreditLimit(rs, false))
					{
						double CreditAvailable = rs.getDouble("CreditAvailable");
						if (!rs.wasNull() && CreditAvailable < 0)
							calloutField.fireDataStatusEEvent(MSG_CreditLimitOver, DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable), false);
					}
				}

				// PO Reference
				String s = rs.getString("POReference");
				if (s != null && s.length() != 0)
					order.setPOReference(s);
				else
					order.setPOReference(null);
				// SO Description
				s = rs.getString("SO_Description");
				if (s != null && s.trim().length() != 0)
					order.setDescription(s);
				// IsDiscountPrinted
				order.setIsDiscountPrinted(DisplayType.toBoolean(rs.getString("IsDiscountPrinted")));

				// Defaults, if not Walkin Receipt or Walkin Invoice
				String OrderType = order.getOrderType();
				order.setInvoiceRule(X_C_Order.INVOICERULE_AfterDelivery);
				order.setPaymentRule(X_C_Order.PAYMENTRULE_OnCredit);
				if (MOrder.DocSubType_Prepay.equals(OrderType))
					order.setInvoiceRule(X_C_Order.INVOICERULE_Immediate);
				else if (MOrder.DocSubType_POS.equals(OrderType))  // for POS
					order.setPaymentRule(X_C_Order.PAYMENTRULE_Cash);
				else
				{
					// PaymentRule
					s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
					if (s != null && s.length() != 0)
					{
						if (s.equals("B"))  // No Cache in Non POS
							s = "P";
						if (IsSOTrx && (s.equals("S") || s.equals("U"))) // No
							s = "P"; // Payment Term
						order.setPaymentRule(s);
					}
					// Payment Term
					final int paymentTermId = rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID");
					if (!rs.wasNull())
						order.setC_PaymentTerm_ID(paymentTermId);
					// InvoiceRule
					s = rs.getString("InvoiceRule");
					if (s != null && s.length() != 0)
						order.setInvoiceRule(s);
				}
			}
		}
		catch (SQLException e)
		{
			log.error("bPartnerBill", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return NO_ERROR;
	} // bPartnerBill

	/**
	 * Order Header - PriceList. (used also in Invoice) - C_Currency_ID - IsTaxIncluded Window Context: - EnforcePriceLimit - StdPrecision - M_PriceList_Version_ID
	 */
	public String priceList(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final I_M_PriceList priceList = order.getM_PriceList();
		if (priceList == null || priceList.getM_PriceList_ID() <= 0)
		{
			return NO_ERROR;
		}

		// Tax Included
		order.setIsTaxIncluded(priceList.isTaxIncluded());
		// Price Limit Enforce
		calloutField.putContext(CTX_EnforcePriceLimit, priceList.isEnforcePriceLimit());
		// Currency
		order.setC_Currency_ID(priceList.getC_Currency_ID());

		return NO_ERROR;
	} // priceList

	/*************************************************************************
	 * Order Line - Product. - reset C_Charge_ID / M_AttributeSetInstance_ID - PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit - UOM Calls Tax
	 */
	public String product(final ICalloutField calloutField)
	{
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final int M_Product_ID = orderLine.getM_Product_ID();
		if (M_Product_ID <= 0)
			return NO_ERROR;
		if (steps)
			log.warn("init");

		//
		// Charge: reset
		orderLine.setC_Charge(null);

		//
		// UOMs: reset them to avoid UOM conversion errors between previous UOM and current product's UOMs (see FRESH-936 #69)
		final I_M_Product product = orderLine.getM_Product();
		orderLine.setC_UOM(Services.get(IProductBL.class).getStockingUOM(product));
		orderLine.setPrice_UOM(null); // reset; will be set when we update pricing

		// Set Attribute
		if (calloutField.getTabInfoContextAsInt("M_Product_ID") == M_Product_ID
				&& calloutField.getTabInfoContextAsInt("M_AttributeSetInstance_ID") > 0)
			orderLine.setM_AttributeSetInstance_ID(calloutField.getTabInfoContextAsInt("M_AttributeSetInstance_ID"));
		else
			orderLine.setM_AttributeSetInstance(null);

		/***** Price Calculation see also qty ****/
		updatePrices(orderLine); // metas

		orderLine.setQtyOrdered(orderLine.getQtyEntered());

		if (orderLine.getC_Order().isSOTrx())
		{
			if (Services.get(IProductBL.class).isStocked(product))
			{
				BigDecimal QtyOrdered = orderLine.getQtyOrdered();
				int M_Warehouse_ID = orderLine.getM_Warehouse_ID();
				if (M_Warehouse_ID <= 0)
				{
					M_Warehouse_ID = orderLine.getC_Order().getM_Warehouse_ID();
				}
				int M_AttributeSetInstance_ID = orderLine.getM_AttributeSetInstance_ID();
				BigDecimal available = MStorage.getQtyAvailable(M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, null);

				// FIXME: QtyAvailable field does not exist. Pls check and drop following code.
				// // metas: if we have the respective field, display the available qty
				// GridField fieldQtyAvailable = mTab.getField(COLNAME_QTY_AVAIL);
				// if (fieldQtyAvailable != null)
				// {
				// mTab.setValue(COLNAME_QTY_AVAIL, available);
				// }

				if (available == null)
					available = BigDecimal.ZERO;
				if (available.signum() == 0)
				{
					// metas: disable user message
					// mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
				}
				else if (available.compareTo(QtyOrdered) < 0)
				{
					// metas: disable user message
					// mTab.fireDataStatusEEvent ("InsufficientQtyAvailable",
					// available.toString(), false);
				}
				else
				{
					int C_OrderLine_ID = orderLine.getC_OrderLine_ID();
					if (C_OrderLine_ID <= 0)
						C_OrderLine_ID = 0;
					BigDecimal notReserved = MOrderLine.getNotReserved(calloutField.getCtx(),
							M_Warehouse_ID, M_Product_ID,
							M_AttributeSetInstance_ID, C_OrderLine_ID);
					if (notReserved == null)
						notReserved = BigDecimal.ZERO;
					BigDecimal total = available.subtract(notReserved);
					if (total.compareTo(QtyOrdered) < 0)
					{
						// metas: don't show warning
						// String info = Msg.parseTranslation(ctx,
						// "@QtyAvailable@=" + available
						// + " - @QtyNotReserved@=" + notReserved
						// + " = " + total);
						// mTab.fireDataStatusEEvent("InsufficientQtyAvailable",
						// info, false);
					}
				}
			}
		}
		// metas (2008 0030 AP47)
		handleIndividualDescription(orderLine);
		//
		if (steps)
			log.warn("fini");
		return tax(calloutField);
	} // product

	/**
	 * Order Line - Charge. - updates PriceActual from Charge - sets PriceLimit, PriceList to zero Calles tax
	 */
	public String charge(final ICalloutField calloutField)
	{
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final int C_Charge_ID = orderLine.getC_Charge_ID();
		if (C_Charge_ID <= 0)
			return NO_ERROR;
		// No Product defined
		if (orderLine.getM_Product_ID() > 0)
		{
			orderLine.setC_Charge(null);
			return "ChargeExclusively";
		}
		orderLine.setM_AttributeSetInstance(null);
		orderLine.setS_ResourceAssignment_ID(-1);
		orderLine.setC_UOM_ID(IUOMDAO.C_UOM_ID_Each); // EA

		String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, C_Charge_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				orderLine.setPriceEntered(rs.getBigDecimal(1));
				orderLine.setPriceActual(rs.getBigDecimal(1));
				orderLine.setPriceLimit(BigDecimal.ZERO);
				orderLine.setPriceList(BigDecimal.ZERO);
				orderLine.setDiscount(BigDecimal.ZERO);
				orderLine.setPriceStd(BigDecimal.ZERO); // metas

				// metas: also displaying PLV-ID
				orderLine.setM_PriceList_Version(null);
				// metas: end
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		return tax(calloutField);
	} // charge

	/**
	 * Order Line - Tax. - basis: Product, Charge, BPartner Location - sets C_Tax_ID Calles Amount
	 */
	public String tax(final ICalloutField calloutField)
	{
		final Properties ctx = calloutField.getCtx();
		final I_C_OrderLine ol = calloutField.getModel(I_C_OrderLine.class);
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		orderLineBL.setPricesIfNotIgnored(ctx, ol,
				false,  // usePriceUOM
				null);

		final Object value = calloutField.getValue();
		if (value == null)
			return NO_ERROR;
		if (steps)
			log.warn("init");

		// Check Product
		final int M_Product_ID = ol.getM_Product_ID();
		final int C_Charge_ID = ol.getC_Charge_ID();
		log.debug("Product={}, C_Charge_ID={}", M_Product_ID, C_Charge_ID);
		if (M_Product_ID <= 0 && C_Charge_ID <= 0)
			return amt(calloutField); //

		// Check Partner Location
		final I_C_Order order = ol.getC_Order();
		int shipC_BPartner_Location_ID = ol.getC_BPartner_Location_ID();
		if (shipC_BPartner_Location_ID <= 0)
			shipC_BPartner_Location_ID = order.getC_BPartner_Location_ID();
		if (shipC_BPartner_Location_ID <= 0)
			return amt(calloutField); //
		log.debug("Ship BP_Location={}", shipC_BPartner_Location_ID);

		//
		Timestamp billDate = ol.getDateOrdered();
		if (billDate == null)
			billDate = order.getDateOrdered();
		log.debug("Bill Date={}", billDate);

		Timestamp shipDate = ol.getDatePromised();
		if (shipDate == null)
			shipDate = order.getDatePromised();
		log.debug("Ship Date={}", shipDate);

		int AD_Org_ID = ol.getAD_Org_ID();
		log.debug("Org={}", AD_Org_ID);

		int M_Warehouse_ID = ol.getM_Warehouse_ID();
		if (M_Warehouse_ID <= 0)
			M_Warehouse_ID = order.getM_Warehouse_ID();
		log.debug("Warehouse={}", M_Warehouse_ID);

		int billC_BPartner_Location_ID = order.getBill_Location_ID();
		if (billC_BPartner_Location_ID <= 0)
			billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
		log.debug("Bill BP_Location={}", billC_BPartner_Location_ID);

		//
		int C_Tax_ID = Tax.get(ctx, M_Product_ID, C_Charge_ID, billDate,
				shipDate, AD_Org_ID, M_Warehouse_ID,
				billC_BPartner_Location_ID, shipC_BPartner_Location_ID, order.isSOTrx());
		log.trace("Tax ID={}", C_Tax_ID);
		//
		if (C_Tax_ID <= 0)
			calloutField.fireDataStatusEEvent(MetasfreshLastError.retrieveError());
		else
			ol.setC_Tax_ID(C_Tax_ID);
		//
		if (steps)
			log.warn("fini");

		return amt(calloutField);
	} // tax

	/**
	 * Order Line - Amount. - called from QtyOrdered, Discount and PriceActual - calculates Discount or Actual Amount - calculates LineNetAmt - enforces PriceLimit
	 */
	public String amt(final ICalloutField calloutField)
	{
		final Object value = calloutField.getValue();
		if (isCalloutActive() || value == null)
		{
			return NO_ERROR;
		}

		if (steps)
			log.warn("init");

		final Properties ctx = calloutField.getCtx();
		final String changedColumnName = calloutField.getColumnName();
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final I_C_Order order = orderLine.getC_Order();

		final int C_UOM_To_ID = orderLine.getPrice_UOM_ID();
		final int M_Product_ID = orderLine.getM_Product_ID();
		final int M_PriceList_ID = order.getM_PriceList_ID();
		// final int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		final int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
		// get values
		final BigDecimal QtyEntered = orderLine.getQtyEntered();
		final BigDecimal QtyOrdered = orderLine.getQtyOrdered();
		log.debug("QtyEntered={}, Ordered={}, UOM={}", QtyEntered, QtyOrdered, C_UOM_To_ID);
		//
		BigDecimal PriceEntered = orderLine.getPriceEntered();
		BigDecimal PriceActual = orderLine.getPriceActual();
		BigDecimal Discount = orderLine.getDiscount();
		BigDecimal PriceLimit = orderLine.getPriceLimit();
		BigDecimal PriceList = orderLine.getPriceList();
		log.debug("PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
		log.debug("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

		// Qty changed - recalc price
		if (I_C_OrderLine.COLUMNNAME_QtyOrdered.equals(changedColumnName)
				|| I_C_OrderLine.COLUMNNAME_QtyEntered.equals(changedColumnName)
				|| I_C_OrderLine.COLUMNNAME_M_Product_ID.equals(changedColumnName))
		// && !"N".equals(Env.getContext(ctx, WindowNo, CTX_DiscountSchema))
		{
			updatePrices(orderLine);
			PriceEntered = orderLine.getPriceEntered();
			PriceActual = orderLine.getPriceActual();
			Discount = orderLine.getDiscount();
			PriceLimit = orderLine.getPriceLimit();
			PriceList = orderLine.getPriceList();
		}
		else if (I_C_OrderLine.COLUMNNAME_PriceActual.equals(changedColumnName))
		{
			PriceActual = (BigDecimal)value;
			PriceEntered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID, C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
		}
		else if (I_C_OrderLine.COLUMNNAME_PriceEntered.equals(changedColumnName))
		{
			Services.get(IOrderLineBL.class).calculatePriceActual(orderLine, -1); // precision=-1, preserving old behavior (->called method shall find out itself)
			PriceEntered = orderLine.getPriceEntered();
			PriceActual = orderLine.getPriceActual();
		}

		// metas: *** Discount veraendert? ***
		// -> PriceActual
		// -> LineNetAmt
		if (I_C_OrderLine.COLUMNNAME_Discount.equals(changedColumnName))
		{
			// metas
			// if (PriceList.doubleValue() != 0)
			// PriceActual = new BigDecimal((100.0 - Discount.doubleValue())
			// / 100.0 * PriceList.doubleValue());
			// metas ende
			if (PriceEntered.doubleValue() != 0)
				PriceActual = new BigDecimal((100.0 - Discount.doubleValue()) / 100.0 * PriceEntered.doubleValue());

			if (PriceActual.scale() > StdPrecision)
				PriceActual = PriceActual.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			// metas
			// PriceEntered = MUOMConversion.convertProductFrom(ctx, M_Product_ID,
			// C_UOM_To_ID, PriceActual);
			// metas ende

			// metas us1064
			// if (PriceEntered == null)
			// PriceEntered = PriceActual;
			orderLine.setPriceEntered(PriceEntered);
			// mTab.setValue("PriceActual", PriceActual);
			// metas us1064 end
		}
		// calculate Discount
		else
		{
			if (PriceList.intValue() == 0)
				Discount = BigDecimal.ZERO;
			// metas
			// else
			// Discount = new BigDecimal(
			// (PriceList.doubleValue() - PriceActual.doubleValue())
			// / PriceList.doubleValue() * 100.0);
			if (Discount.scale() > 2)
				Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
			// metas
			// mTab.setValue("Discount", Discount);
			// Discount = new BigDecimal(0);
			// mTab.setValue("Discount", Discount);
		}
		log.debug("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

		// Check Price Limit?
		if (isEnforcePriceLimit(calloutField, order.isSOTrx())
				&& PriceLimit.signum() != 0 && PriceActual.compareTo(PriceLimit) < 0)
		{
			PriceActual = PriceLimit;
			PriceEntered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID, C_UOM_To_ID, PriceLimit);
			if (PriceEntered == null)
				PriceEntered = PriceLimit;
			log.debug("(under) PriceEntered=" + PriceEntered + ", Actual" + PriceLimit);
			orderLine.setPriceActual(PriceActual);
			// 07090: this (complicated, legacy) is just about updating price amounts, not priceUOM -> not touching the price UOM here
			orderLine.setPriceEntered(PriceEntered);
			calloutField.fireDataStatusEEvent(MSG_UnderLimitPrice, "", false);

			// Repeat Discount calc
			if (PriceList.intValue() != 0)
			{
				Discount = new BigDecimal(
						(PriceList.doubleValue() - PriceActual.doubleValue())
								/ PriceList.doubleValue() * 100.0);
				if (Discount.scale() > 2)
					Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
				orderLine.setDiscount(Discount);
			}
		}

		orderLine.setPriceActual(PriceActual);
		// 07090: this (complicated, legacy) is just about updating price amounts, not priceUOM -> not touching the price UOM here
		// Make sure we use the converted quantity for calculation, since the priceActual is for the price's UOM, while the quantity ordered is for the product's UOM.

		// Line Net Amt
		final BigDecimal qtyEnteredInPriceUOM = Services.get(IOrderLineBL.class).convertQtyEnteredToPriceUOM(orderLine);

		BigDecimal LineNetAmt = qtyEnteredInPriceUOM.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		orderLine.setLineNetAmt(LineNetAmt);

		Services.get(IOrderLineBL.class).setTaxAmtInfoIfNotIgnored(ctx, orderLine, ITrx.TRXNAME_None);

		//
		return NO_ERROR;
	} // amt

	/**
	 * Order Line - Quantity. - called from C_UOM_ID, QtyEntered, QtyOrdered - enforces qty UOM relationship
	 */
	public String qty(final ICalloutField calloutField)
	{
		if (isCalloutActive() || calloutField.getValue() == null)
			return NO_ERROR;

		final String columnName = calloutField.getColumnName();
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final int M_Product_ID = orderLine.getM_Product_ID();
		if (steps)
			log.warn("init - M_Product_ID=" + M_Product_ID + " - ");

		// No Product
		if (M_Product_ID <= 0)
		{
			final BigDecimal QtyEntered = orderLine.getQtyEntered();
			BigDecimal QtyOrdered = QtyEntered;
			orderLine.setQtyOrdered(QtyOrdered);
			setUOMConversion(calloutField, false);
		}
		else if (I_C_OrderLine.COLUMNNAME_C_UOM_ID.equals(columnName))
		{
			final I_C_OrderLine orderLineOld = calloutField.getModelBeforeChanges(I_C_OrderLine.class);
			final I_C_UOM uomFrom = orderLineOld.getC_UOM();
			final I_C_UOM uomTo = orderLine.getC_UOM();
			BigDecimal QtyEntered = orderLine.getQtyEntered();
			final IUOMConversionContext uomConverter = IUOMConversionContext.of(orderLine.getM_Product());

			BigDecimal QtyEntered1 = uomConverter.roundToUOMPrecisionIfPossible(QtyEntered, uomTo);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM={} {}; QtyEntered={}->{}", uomTo, QtyEntered, QtyEntered1);
				QtyEntered = QtyEntered1;
				orderLine.setQtyEntered(QtyEntered);
			}

			BigDecimal QtyOrdered = uomConverter.convertQty(QtyEntered1, uomFrom, uomTo);
			if (QtyOrdered == null)
			{
				QtyOrdered = QtyEntered;
			}
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			// PriceActual = (BigDecimal)mTab.getValue("PriceActual");
			// metas us1064
			// PriceEntered = MUOMConversion.convertProductFrom(ctx, M_Product_ID, C_UOM_To_ID, PriceActual);
			// if (PriceEntered == null)
			// PriceEntered = PriceActual;
			// log.debug("UOM=" + C_UOM_To_ID + ", QtyEntered/PriceActual="
			// + QtyEntered + "/" + PriceActual + " -> " + conversion
			// + " QtyOrdered/PriceEntered=" + QtyOrdered + "/"
			// + PriceEntered);
			// metas us1064 end
			setUOMConversion(calloutField, conversion);
			orderLine.setQtyOrdered(QtyOrdered);
			// metas us1064
			// mTab.setValue("PriceEntered", PriceEntered);
			// metas us1064 end
		}
		// QtyEntered changed - calculate QtyOrdered
		else if (I_C_OrderLine.COLUMNNAME_QtyEntered.equals(columnName))
		{
			final int C_UOM_To_ID = orderLine.getC_UOM_ID();
			BigDecimal QtyEntered = orderLine.getQtyEntered();
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(calloutField.getCtx(), C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID + "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
				QtyEntered = QtyEntered1;
				orderLine.setQtyEntered(QtyEntered);
			}
			BigDecimal QtyOrdered = MUOMConversion.convertToProductUOM(calloutField.getCtx(), M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			log.debug("UOM=" + C_UOM_To_ID + ", QtyEntered=" + QtyEntered + " -> " + conversion + " QtyOrdered=" + QtyOrdered);
			setUOMConversion(calloutField, conversion);
			orderLine.setQtyOrdered(QtyOrdered);
		}
		// QtyOrdered changed - calculate QtyEntered (should not happen)
		else if (I_C_OrderLine.COLUMNNAME_QtyOrdered.equals(columnName))
		{
			int C_UOM_To_ID = orderLine.getC_UOM_ID();
			BigDecimal QtyOrdered = orderLine.getQtyOrdered();
			int precision = MProduct.get(calloutField.getCtx(), M_Product_ID).getUOMPrecision();
			BigDecimal QtyOrdered1 = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (QtyOrdered.compareTo(QtyOrdered1) != 0)
			{
				log.debug("Corrected QtyOrdered Scale " + QtyOrdered + "->" + QtyOrdered1);
				QtyOrdered = QtyOrdered1;
				orderLine.setQtyOrdered(QtyOrdered);
			}
			BigDecimal QtyEntered = MUOMConversion.convertFromProductUOM(calloutField.getCtx(), M_Product_ID, C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
				QtyEntered = QtyOrdered;
			boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			log.debug("UOM=" + C_UOM_To_ID + ", QtyOrdered=" + QtyOrdered + " -> " + conversion + " QtyEntered=" + QtyEntered);
			setUOMConversion(calloutField, conversion);
			orderLine.setQtyEntered(QtyEntered);
		}

		// Storage
		final BigDecimal QtyOrdered = orderLine.getQtyOrdered();
		if (M_Product_ID > 0 && orderLine.getC_Order().isSOTrx()
				&& QtyOrdered.signum() > 0)  // no negative (returns)
		{
			I_M_Product product = orderLine.getM_Product();
			if (Services.get(IProductBL.class).isStocked(product))
			{
				int M_Warehouse_ID = orderLine.getM_Warehouse_ID();
				int M_AttributeSetInstance_ID = orderLine.getM_AttributeSetInstance_ID();
				BigDecimal available = MStorage.getQtyAvailable(M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
				if (available == null)
					available = BigDecimal.ZERO;

				// FIXME: QtyAvailable field does not exist. Pls check and drop following code.
				// // metas: if we have the respective field, display the available qty
				// GridField fieldQtyAvailable = mTab.getField(COLNAME_QTY_AVAIL);
				// if (fieldQtyAvailable != null)
				// {
				// mTab.setValue(COLNAME_QTY_AVAIL, available);
				// }

				if (available.signum() == 0)
				{
					// metas: disabling the warnings about insufficient qty
					// mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
				}
				else if (available.compareTo(QtyOrdered) < 0)
				{
					// metas: disabling the warnings about insufficient qty
					// mTab.fireDataStatusEEvent ("InsufficientQtyAvailable",
					// available.toString(), false);
				}
				else
				{
					int C_OrderLine_ID = orderLine.getC_OrderLine_ID();
					if (C_OrderLine_ID <= 0)
						C_OrderLine_ID = 0;
					BigDecimal notReserved = MOrderLine.getNotReserved(calloutField.getCtx(),
							M_Warehouse_ID, M_Product_ID,
							M_AttributeSetInstance_ID, C_OrderLine_ID);
					if (notReserved == null)
						notReserved = BigDecimal.ZERO;
					BigDecimal total = available.subtract(notReserved);
					if (total.compareTo(QtyOrdered) < 0)
					{
						// metas: disabling the warnings about insuffizient qty
						// String info = Msg.parseTranslation(ctx,
						// "@QtyAvailable@=" + available
						// + " - @QtyNotReserved@=" + notReserved + " = " +
						// total);
						// mTab.fireDataStatusEEvent
						// ("InsufficientQtyAvailable",
						// info, false);
					}
				}
			}
		}
		//
		return NO_ERROR;
	} // qty

	private static final void setUOMConversion(final ICalloutField calloutField, final boolean conversion)
	{
		calloutField.putContext(CTX_UOMConversion, conversion);
	}

	public String isIndividualDescription(final ICalloutField calloutField)
	{
		if (isCalloutActive() || calloutField.getValue() == null)
		{
			return NO_ERROR;
		}

		if (!I_C_OrderLine.COLUMNNAME_IsIndividualDescription.equals(calloutField.getColumnName()))
		{
			log.warn("Callout method 'CalloutOrder.isIndiviualDescription' has been call with field " + calloutField.getColumnName());
			return NO_ERROR;
		}
		handleIndividualDescription(calloutField.getModel(I_C_OrderLine.class));
		return NO_ERROR;
	}

	/**
	 * Evaluates the fields {@link I_C_OrderLine#COLUMNNAME_M_Product_ID} and {@link I_C_OrderLine#COLUMNNAME_IsIndividualDescription}.
	 * 
	 * If Both are set and isIndividualDescription is true the product's description is copied into the order line's {@link I_C_OrderLine#COLUMNNAME_ProductDescription} field.
	 */
	private void handleIndividualDescription(final I_C_OrderLine ol)
	{
		if (!ol.isIndividualDescription())
		{
			ol.setProductDescription("");
			return;
		}
		if (ol.getM_Product_ID() <= 0)
		{
			// no product has been selected yet -> nothing to do
			return;
		}

		final I_M_Product product = ol.getM_Product();
		ol.setProductDescription(product.getDescription());

		return;
	}

	/**
	 * Decides (using the given <code>rs</code> whether the business partner's credit limit should be checked.
	 *
	 * @param rs the result set contains the data sued fpr the decision
	 * @param evalCreditstatus if <code>true</code>, the result set's column <code>"SOCreditStatus"</code> is also used for the decision
	 * @return
	 * @throws SQLException if one is thrown while accessing the result set (in particular if evalCreditstatus is <code>true</code>, but the result set doesn't contain<code>"SOCreditStatus"</code>).
	 */
	private boolean isChkCreditLimit(final ResultSet rs, final boolean evalCreditstatus) throws SQLException
	{
		final double creditLimit = rs.getDouble("SO_CreditLimit");
		boolean dontCheck = true;
		if (evalCreditstatus)
		{
			String creditStatus = rs.getString("SOCreditStatus");
			dontCheck = creditLimit == 0 || X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(creditStatus);
		}
		else
		{
			dontCheck = creditLimit == 0;
		}
		return !dontCheck;
	}

	// metas
	public static void updatePrices(final I_C_OrderLine orderLine)
	{
		Services.get(IOrderLineBL.class).updatePrices(orderLine);
	}

	private static interface DropShipPartnerAware
	{
		public int getDropShip_BPartner_ID();

		public void setDropShip_Location_ID(int DropShip_Location_ID);

		public void setDropShip_Location(org.compiere.model.I_C_BPartner_Location DropShip_Location);

		public void setDropShip_User_ID(int DropShip_User_ID);

		public void setDropShip_User(org.compiere.model.I_AD_User DropShip_User);
	}

	public String deliveryToBPartnerID(final ICalloutField calloutField)
	{
		final DropShipPartnerAware dropShipAware = calloutField.getModel(DropShipPartnerAware.class);
		final int dropShipBPartnerId = dropShipAware.getDropShip_BPartner_ID();
		if (dropShipBPartnerId <= 0)
			return NO_ERROR;
		String sql = "SELECT lship.C_BPartner_Location_ID,c.AD_User_ID "
				+ " FROM C_BPartner p"
				+ " LEFT OUTER JOIN C_BPartner_Location lship ON (p.C_BPartner_ID=lship.C_BPartner_ID AND lship.IsShipTo='Y' AND lship.IsActive='Y')"
				+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
				+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'"
				+ " ORDER BY lship." + I_C_BPartner_Location.COLUMNNAME_IsShipToDefault
				+ " DESC"
		// metas end
		; // #1

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, dropShipBPartnerId);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				int shipTo_ID = rs.getInt("C_BPartner_Location_ID");
				if (dropShipBPartnerId == calloutField.getTabInfoContextAsInt("DropShip_BPartner_ID"))
				{
					final int locFromContextId = calloutField.getTabInfoContextAsInt("DropShip_Location_ID");
					if (locFromContextId > 0)
					{
						// metas: before using the location we need to make sure
						// that it is a shipping location
						if (new MBPartnerLocation(calloutField.getCtx(), locFromContextId, null).isShipTo())
						{
							shipTo_ID = locFromContextId;
						}
					}
				}
				if (shipTo_ID <= 0)
					dropShipAware.setDropShip_Location(null);
				else
					dropShipAware.setDropShip_Location_ID(shipTo_ID);

				int contID = rs.getInt("AD_User_ID");
				if (dropShipBPartnerId == calloutField.getTabInfoContextAsInt("DropShip_BPartner_ID"))
				{
					final int tabInfoContactId = calloutField.getTabInfoContextAsInt("DropShip_User_ID");
					if (tabInfoContactId > 0)
						contID = tabInfoContactId;
				}
				if (contID <= 0)
					dropShipAware.setDropShip_User(null);
				else
				{
					dropShipAware.setDropShip_User_ID(contID);
				}

			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return NO_ERROR;
	}

	// 01717
	public String DropShipPartner(final ICalloutField calloutField)
	{
		if (calloutField.isRecordCopyingMode())
		{
			// 05291: In case current record is on dataNew phase with Copy option set
			// then just don't update the DropShip fields, take them as they were copied
			return NO_ERROR;
		}

		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		if (order.isSOTrx())
		{
			if (order.isDropShip())
			{
				order.setDropShip_BPartner_ID(order.getC_BPartner_ID());
				order.setDropShip_Location_ID(order.getC_BPartner_Location_ID());
				order.setDropShip_User_ID(order.getAD_User_ID());
			}
			else
			{
				order.setDropShip_BPartner_ID(-1);
				order.setDropShip_Location_ID(-1);
				order.setDropShip_User_ID(-1);
			}
		}
		else
		{
			if (!order.isDropShip())
			{
				final I_AD_Org org = order.getAD_Org();
				final I_C_BPartner linkedBPartner = Services.get(IBPartnerOrgBL.class).retrieveLinkedBPartner(org);
				if (null != linkedBPartner)
				{
					order.setDropShip_BPartner_ID(linkedBPartner.getC_BPartner_ID());
				}
				if (null != order.getM_Warehouse())
				{
					order.setDropShip_Location_ID(order.getM_Warehouse().getC_BPartner_Location_ID());
				}
				order.setDropShip_User_ID(-1);

			}
		}
		return NO_ERROR;
	}

	private static boolean isEnforcePriceLimit(final ICalloutField calloutField, final boolean isSOTrx)
	{
		// We enforce PriceLimit only for sales orders
		if (!isSOTrx)
		{
			return false;
		}

		final boolean epl = calloutField.getContextAsBoolean(CTX_EnforcePriceLimit);
		if (!epl)
		{
			return false;
		}

		// User role allows us to overwrite PriceLimit, so we are not enforcing it
		if (Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_OverwritePriceLimit))
		{
			return false;
		}

		return true;
	}

	public String attributeSetInstance(final ICalloutField calloutField)
	{
		// 05118 : Also update the prices
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		updatePrices(orderLine);

		return NO_ERROR;
	}
} // CalloutOrder
