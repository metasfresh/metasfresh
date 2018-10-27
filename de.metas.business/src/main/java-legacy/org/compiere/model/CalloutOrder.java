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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.uom.api.UOMConversionContext;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.exceptions.BPartnerNoBillToAddressException;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.MetasfreshLastError;
import de.metas.order.DeliveryRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.order.PriceAndDiscount;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.service.IPriceListBL;
import de.metas.product.IProductBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	private static final String CTX_UOMConversion = "UOMConversion";

	private static final String MSG_CreditLimitOver = "CreditLimitOver";
	private static final String MSG_UnderLimitPrice = "UnderLimitPrice";

	/**
	 * C_Order.C_DocTypeTarget_ID changed: - InvoiceRuld/DeliveryRule/PaymentRule - temporary Document Context: - DocSubType - HasCharges - (re-sets Business Partner info of required)
	 */
	public String docType(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);

		final I_C_Order oldOrder = InterfaceWrapperHelper.createOld(order, I_C_Order.class);

		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(order.getC_DocTypeTarget())
				.setOldDocType_ID(oldOrder.getC_DocTypeTarget_ID())
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
			order.setDeliveryRule(DeliveryRule.FORCE.getCode());
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
		Services.get(IOrderBL.class).updateDescriptionFromDocTypeTargetId(order);

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
				+ " lship.C_BPartner_Location_ID,c.AD_User_ID,"
				+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID,"
				+ " lbill.C_BPartner_Location_ID AS Bill_Location_ID, "
				+ " p.SalesRep_ID, p.SO_DocTypeTarget_ID "
				+ " FROM C_BPartner p"
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
				+ " , c." + I_AD_User.COLUMNNAME_AD_User_ID + " ASC "; // #1
		final Object[] sqlParams = new Object[] { C_BPartner_ID };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				// metas: Auftragsart aus Kunde
				final Integer docTypeTargetId = rs.getInt("SO_DocTypeTarget_ID");
				if (IsSOTrx && docTypeTargetId > 0)
				{
					Services.get(IOrderBL.class).setDocTypeTargetIdAndUpdateDescription(order, docTypeTargetId);
				}

				// Sales Rep - If BP has a default SalesRep then default it
				final Integer salesRepId = rs.getInt("SalesRep_ID");
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
					{
						contID = tabInfoContactId;
					}
				}
				if (contID <= 0)
				{
					order.setAD_User_ID(-1);
				}
				else
				{
					order.setAD_User_ID(contID);
					order.setBill_User_ID(contID);
				}

				// CreditAvailable
				if (IsSOTrx)
				{
					checkCreditLimit(calloutField, order);
				}

				// PO Reference
				String s = rs.getString("POReference");
				if (s != null && s.length() != 0)
				{
					order.setPOReference(s);
					// should not be reset to null if we entered already value!
					// VHARCQ, accepted YS makes sense that way
					// TODO: should get checked and removed if no longer needed!
					/*
					 * else mTab.setValue("POReference", null);
					 */
				}

				// SO Description
				s = rs.getString("SO_Description");
				if (s != null && s.trim().length() != 0)
				{
					order.setDescription(s);
				}
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
						if ("B".equals(s))
						{
							s = "P";
						}
						if (IsSOTrx && ("S".equals(s) || "U".equals(s)))
						{
							s = "P"; // Payment Term
						}
						order.setPaymentRule(s);
					}
					// Payment Term
					final Integer ii = rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID");
					if (!rs.wasNull())
					{
						order.setC_PaymentTerm_ID(ii);
					}
					// InvoiceRule
					s = rs.getString("InvoiceRule");
					if (s != null && s.length() != 0)
					{
						order.setInvoiceRule(s);
					}
					// DeliveryRule
					s = rs.getString("DeliveryRule");
					if (s != null && s.length() != 0)
					{
						order.setDeliveryRule(s);
					}
					// FreightCostRule
					s = rs.getString("FreightCostRule");
					if (s != null && s.length() != 0)
					{
						order.setFreightCostRule(s);
					}
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
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return NO_ERROR;
	} // bPartner

	private void checkCreditLimit(
			@NonNull final ICalloutField calloutField,
			@NonNull final I_C_Order order)
	{
		final I_C_BPartner bPartner = order.getC_BPartner();
		final BPartnerStats bPartnerStats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(bPartner);

		final CreditLimitRequest creditLimitRequest = CreditLimitRequest.builder()
				.bpartnerId(bPartner.getC_BPartner_ID())
				.creditStatus(bPartnerStats.getSOCreditStatus())
				.evalCreditstatus(true)
				.evaluationDate(order.getDateOrdered())
				.build();

		if (!isChkCreditLimit(creditLimitRequest))
		{
			return;
		}

		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bPartner.getC_BPartner_ID(), order.getDateOrdered());

		final BigDecimal CreditAvailable = creditLimit.subtract(bPartnerStats.getSOCreditUsed());
		if (CreditAvailable.signum() < 0)
		{
			calloutField.fireDataStatusEEvent(
					MSG_CreditLimitOver,
					DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
					false);
		}
	}

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
		{
			return NO_ERROR;
		}
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final int bill_BPartner_ID = order.getBill_BPartner_ID();
		if (bill_BPartner_ID <= 0)
		{
			return NO_ERROR;
		}

		// #928: Make sure the user is of the right SOTrx value and it is set as default for that SOTrx value.

		final boolean isSOTrx = order.isSOTrx();
		final String userFlag = isSOTrx ? I_AD_User.COLUMNNAME_IsSalesContact : I_AD_User.COLUMNNAME_IsPurchaseContact;

		final String defaultUserFlag = isSOTrx ? I_AD_User.COLUMNNAME_IsSalesContact_Default : I_AD_User.COLUMNNAME_IsPurchaseContact_Default;

		final String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
				+ "p.M_PriceList_ID,p.PaymentRule,p.POReference,"
				+ "p.SO_Description,p.IsDiscountPrinted,"
				+ "p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
				+ "stats." + I_C_BPartner_Stats.COLUMNNAME_SO_CreditUsed + ", "
				+ "stats." + I_C_BPartner_Stats.COLUMNNAME_SOCreditStatus + ", "
				+ "c.AD_User_ID,"
				+ "p.PO_PriceList_ID, p.PaymentRulePO, p.PO_PaymentTerm_ID,"
				+ "lbill.C_BPartner_Location_ID AS Bill_Location_ID "
				+ "FROM C_BPartner p"

				+ " INNER JOIN " + I_C_BPartner_Stats.Table_Name + " stats ON (p." + I_C_BPartner.COLUMNNAME_C_BPartner_ID + " = stats." + I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID + ")"
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
		final Object[] sqlParams = new Object[] { bill_BPartner_ID };

		final boolean IsSOTrx = order.isSOTrx();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				// #4463 don't change the pricelist of a sales order it its bill partner was changed.
				if (!order.isSOTrx())
				{
					// PriceList (indirect: IsTaxIncluded & Currency)
					final int priceListId = rs.getInt(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID");
					if (!rs.wasNull())
					{
						order.setM_PriceList_ID(priceListId);
					}
					else
					{ // get default PriceList
						final int i = calloutField.getGlobalContextAsInt("#M_PriceList_ID");
						if (i > 0)
						{
							order.setM_PriceList_ID(i);
						}
					}
				}

				int bill_Location_ID = rs.getInt("Bill_Location_ID");
				// overwritten by InfoBP selection - works only if InfoWindow
				// was used otherwise creates error (uses last value, may belong
				// to differnt BP)
				if (bill_BPartner_ID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int tabInfoBPartnerLocationId = calloutField.getTabInfoContextAsInt("C_BPartner_Location_ID");
					if (tabInfoBPartnerLocationId > 0)
					{
						bill_Location_ID = tabInfoBPartnerLocationId;
					}
				}
				if (bill_Location_ID <= 0)
				{
					order.setBill_Location_ID(-1);
				}
				else
				{
					order.setBill_Location_ID(bill_Location_ID);
				}

				// Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				if (bill_BPartner_ID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int tabInfoContactId = calloutField.getTabInfoContextAsInt("AD_User_ID");
					if (tabInfoContactId > 0)
					{
						contID = tabInfoContactId;
					}
				}
				if (contID <= 0)
				{
					order.setBill_User(null);
				}
				else
				{
					order.setBill_User_ID(contID);
				}

				// CreditAvailable
				if (IsSOTrx)
				{
					final String creditStatus = rs.getString(I_C_BPartner_Stats.COLUMNNAME_SOCreditStatus);
					final CreditLimitRequest creditLimitRequest = CreditLimitRequest.builder()
							.bpartnerId(bill_BPartner_ID)
							.creditStatus(creditStatus)
							.evalCreditstatus(false)
							.evaluationDate(order.getDateOrdered())
							.build();
					if (isChkCreditLimit(creditLimitRequest))
					{
						final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
						final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bill_BPartner_ID, order.getDateOrdered());
						final BigDecimal creditUsed = Util.coalesce(rs.getBigDecimal(I_C_BPartner_Stats.COLUMNNAME_SO_CreditUsed), BigDecimal.ZERO);
						final BigDecimal creditAvailable = creditLimit.subtract(creditUsed);
						if (creditAvailable.signum() < 0)
						{
							calloutField.fireDataStatusEEvent(MSG_CreditLimitOver, DisplayType.getNumberFormat(DisplayType.Amount).format(creditAvailable), false);
						}
					}
				}

				// PO Reference
				String s = rs.getString("POReference");
				if (s != null && s.length() != 0)
				{
					order.setPOReference(s);
				}
				else
				{
					order.setPOReference(null);
				}
				// SO Description
				s = rs.getString("SO_Description");
				if (s != null && s.trim().length() != 0)
				{
					order.setDescription(s);
				}
				// IsDiscountPrinted
				order.setIsDiscountPrinted(DisplayType.toBoolean(rs.getString("IsDiscountPrinted")));

				// Defaults, if not Walkin Receipt or Walkin Invoice
				final String OrderType = order.getOrderType();
				order.setInvoiceRule(X_C_Order.INVOICERULE_AfterDelivery);
				order.setPaymentRule(X_C_Order.PAYMENTRULE_OnCredit);
				if (MOrder.DocSubType_Prepay.equals(OrderType))
				{
					order.setInvoiceRule(X_C_Order.INVOICERULE_Immediate);
				}
				else if (MOrder.DocSubType_POS.equals(OrderType))
				{
					order.setPaymentRule(X_C_Order.PAYMENTRULE_Cash);
				}
				else
				{
					// PaymentRule
					s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
					if (s != null && s.length() != 0)
					{
						if ("B".equals(s))
						{
							s = "P";
						}
						if (IsSOTrx && ("S".equals(s) || "U".equals(s)))
						{
							s = "P"; // Payment Term
						}
						order.setPaymentRule(s);
					}
					// Payment Term
					final int paymentTermId = rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID");
					if (!rs.wasNull())
					{
						order.setC_PaymentTerm_ID(paymentTermId);
					}
					// InvoiceRule
					s = rs.getString("InvoiceRule");
					if (s != null && s.length() != 0)
					{
						order.setInvoiceRule(s);
					}
				}
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
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

		order.setIsTaxIncluded(priceList.isTaxIncluded());
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
		{
			return NO_ERROR;
		}

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
		{
			orderLine.setM_AttributeSetInstance_ID(calloutField.getTabInfoContextAsInt("M_AttributeSetInstance_ID"));
		}
		else
		{
			orderLine.setM_AttributeSetInstance(null);
		}

		/***** Price Calculation see also qty ****/
		Services.get(IOrderLineBL.class).updatePrices(orderLine);

		orderLine.setQtyOrdered(orderLine.getQtyEntered());

		handleIndividualDescription(orderLine);

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
		{
			return NO_ERROR;
		}
		// No Product defined
		if (orderLine.getM_Product_ID() > 0)
		{
			orderLine.setC_Charge(null);
			throw new AdempiereException("ChargeExclusively");
		}
		orderLine.setM_AttributeSetInstance(null);
		orderLine.setS_ResourceAssignment_ID(-1);
		orderLine.setC_UOM_ID(IUOMDAO.C_UOM_ID_Each); // EA

		final String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
		final Object[] sqlParams = new Object[] { C_Charge_ID };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
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
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
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

		orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
				.orderLine(ol)
				.resultUOM(ResultUOM.CONTEXT_UOM)
				.updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(true)
				.updateLineNetAmt(true)
				.build());

		final Object value = calloutField.getValue();
		if (value == null)
		{
			return NO_ERROR;
		}

		// Check Product
		final int M_Product_ID = ol.getM_Product_ID();
		final int C_Charge_ID = ol.getC_Charge_ID();
		log.debug("Product={}, C_Charge_ID={}", M_Product_ID, C_Charge_ID);
		if (M_Product_ID <= 0 && C_Charge_ID <= 0)
		{
			return amt(calloutField); //
		}

		// Check Partner Location
		final I_C_Order order = ol.getC_Order();
		int shipC_BPartner_Location_ID = ol.getC_BPartner_Location_ID();
		if (shipC_BPartner_Location_ID <= 0)
		{
			shipC_BPartner_Location_ID = order.getC_BPartner_Location_ID();
		}
		if (shipC_BPartner_Location_ID <= 0)
		{
			return amt(calloutField); //
		}
		log.debug("Ship BP_Location={}", shipC_BPartner_Location_ID);

		//
		Timestamp billDate = ol.getDateOrdered();
		if (billDate == null)
		{
			billDate = order.getDateOrdered();
		}
		log.debug("Bill Date={}", billDate);

		Timestamp shipDate = ol.getDatePromised();
		if (shipDate == null)
		{
			shipDate = order.getDatePromised();
		}
		log.debug("Ship Date={}", shipDate);

		final int AD_Org_ID = ol.getAD_Org_ID();
		log.debug("Org={}", AD_Org_ID);

		int M_Warehouse_ID = ol.getM_Warehouse_ID();
		if (M_Warehouse_ID <= 0)
		{
			M_Warehouse_ID = order.getM_Warehouse_ID();
		}
		log.debug("Warehouse={}", M_Warehouse_ID);

		int billC_BPartner_Location_ID = order.getBill_Location_ID();
		if (billC_BPartner_Location_ID <= 0)
		{
			billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
		}
		log.debug("Bill BP_Location={}", billC_BPartner_Location_ID);

		//
		final int C_Tax_ID = Tax.get(ctx, M_Product_ID, C_Charge_ID, billDate,
				shipDate, AD_Org_ID, M_Warehouse_ID,
				billC_BPartner_Location_ID, shipC_BPartner_Location_ID, order.isSOTrx());
		log.trace("Tax ID={}", C_Tax_ID);
		//
		if (C_Tax_ID <= 0)
		{
			calloutField.fireDataStatusEEvent(MetasfreshLastError.retrieveError());
		}
		else
		{
			ol.setC_Tax_ID(C_Tax_ID);
		}

		return amt(calloutField);
	} // tax

	/**
	 * Order Line - Amount. - calculates Discount or Actual Amount - calculates LineNetAmt - enforces PriceLimit
	 *
	 * Triggered by: C_UOM_ID, Discount, PriceActual, PriceEntered, PriceList, QtyOrdered, S_ResourceAssignment_ID
	 */
	public String amt(final ICalloutField calloutField)
	{
		if (isCalloutActive() || calloutField.getValue() == null)
		{
			return NO_ERROR;
		}

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		final Properties ctx = calloutField.getCtx();
		final String changedColumnName = calloutField.getColumnName();
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final I_C_Order order = orderLine.getC_Order();

		final int priceUOMId = orderLine.getPrice_UOM_ID();
		final int productId = orderLine.getM_Product_ID();
		final int pricePrecision = Services.get(IPriceListBL.class).getPricePrecision(order.getM_PriceList_ID());

		//
		PriceAndDiscount priceAndDiscount;

		// Qty changed - recalc price
		if (I_C_OrderLine.COLUMNNAME_QtyOrdered.equals(changedColumnName))
		{
			orderLineBL.updatePrices(OrderLinePriceUpdateRequest.ofOrderLine(orderLine)
					.toBuilder()
					.applyPriceLimitRestrictions(false)
					.build());

			priceAndDiscount = PriceAndDiscount.of(orderLine, pricePrecision);
		}
		else if (I_C_OrderLine.COLUMNNAME_PriceActual.equals(changedColumnName))
		{
			final BigDecimal priceActual = orderLine.getPriceActual();
			BigDecimal priceEntered = MUOMConversion.convertToProductUOM(ctx, productId, priceUOMId, priceActual);
			if (priceEntered == null)
			{
				priceEntered = priceActual;
			}

			priceAndDiscount = PriceAndDiscount.builder()
					.precision(pricePrecision)
					.priceEntered(priceEntered)
					.priceActual(priceActual)
					.discount(Percent.of(orderLine.getDiscount()))
					.build();
		}
		else if (I_C_OrderLine.COLUMNNAME_PriceEntered.equals(changedColumnName))
		{
			priceAndDiscount = PriceAndDiscount.of(orderLine, pricePrecision)
					.updatePriceActual();
		}
		else if (I_C_OrderLine.COLUMNNAME_Discount.equals(changedColumnName))
		{
			priceAndDiscount = PriceAndDiscount.of(orderLine, pricePrecision)
					.updatePriceActualIfPriceEnteredIsNotZero();
		}
		// C_UOM_ID, PriceList, S_ResourceAssignment_ID
		else
		{
			priceAndDiscount = PriceAndDiscount.of(orderLine, pricePrecision);
		}

		//
		// Check PriceActual and enforce PriceLimit.
		// Also, update Discount or PriceEntered if needed.
		if (isEnforcePriceLimit(orderLine, order.isSOTrx()))
		{
			priceAndDiscount.applyTo(orderLine);
			final PriceLimitRuleResult priceLimitResult = orderLineBL.computePriceLimit(orderLine);
			priceAndDiscount = priceAndDiscount.enforcePriceLimit(priceLimitResult);
		}

		//
		// Update order line
		priceAndDiscount.applyTo(orderLine);
		orderLineBL.updateLineNetAmt(orderLine);
		orderLineBL.setTaxAmtInfo(orderLine);

		if (!Check.isEmpty(priceAndDiscount.getPriceLimitEnforceExplanation(), true))
		{
			calloutField.fireDataStatusEEvent(MSG_UnderLimitPrice, priceAndDiscount.getPriceLimitEnforceExplanation(), /* isError */false);
		}

		//
		return NO_ERROR;
	} // amt

	/**
	 * Order Line - Quantity. - called from C_UOM_ID, QtyEntered, QtyOrdered - enforces qty UOM relationship
	 */
	public String qty(final ICalloutField calloutField)
	{
		if (isCalloutActive() || calloutField.getValue() == null)
		{
			return NO_ERROR;
		}

		final String columnName = calloutField.getColumnName();
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final int M_Product_ID = orderLine.getM_Product_ID();

		// No Product
		if (M_Product_ID <= 0)
		{
			final BigDecimal QtyEntered = orderLine.getQtyEntered();
			final BigDecimal QtyOrdered = QtyEntered;
			orderLine.setQtyOrdered(QtyOrdered);
			setUOMConversion(calloutField, false);
		}
		else if (I_C_OrderLine.COLUMNNAME_C_UOM_ID.equals(columnName))
		{
			final I_C_OrderLine orderLineOld = calloutField.getModelBeforeChanges(I_C_OrderLine.class);
			final I_C_UOM uomFrom = orderLineOld.getC_UOM();
			final I_C_UOM uomTo = orderLine.getC_UOM();
			BigDecimal QtyEntered = orderLine.getQtyEntered();
			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
			final UOMConversionContext uomConversionCtx = UOMConversionContext.of(orderLine.getM_Product_ID());

			final BigDecimal QtyEntered1 = uomConversionBL.roundToUOMPrecisionIfPossible(QtyEntered, uomTo);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM={} {}; QtyEntered={}->{}", uomTo, QtyEntered, QtyEntered1);
				QtyEntered = QtyEntered1;
				orderLine.setQtyEntered(QtyEntered);
			}

			BigDecimal QtyOrdered = uomConversionBL.convertQty(uomConversionCtx, QtyEntered1, uomFrom, uomTo);
			if (QtyOrdered == null)
			{
				QtyOrdered = QtyEntered;
			}
			final boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
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
			final BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(calloutField.getCtx(), C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID + "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
				QtyEntered = QtyEntered1;
				orderLine.setQtyEntered(QtyEntered);
			}
			BigDecimal QtyOrdered = MUOMConversion.convertToProductUOM(calloutField.getCtx(), M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
			{
				QtyOrdered = QtyEntered;
			}
			final boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			log.debug("UOM=" + C_UOM_To_ID + ", QtyEntered=" + QtyEntered + " -> " + conversion + " QtyOrdered=" + QtyOrdered);
			setUOMConversion(calloutField, conversion);
			orderLine.setQtyOrdered(QtyOrdered);
		}
		// QtyOrdered changed - calculate QtyEntered (should not happen)
		else if (I_C_OrderLine.COLUMNNAME_QtyOrdered.equals(columnName))
		{
			final int C_UOM_To_ID = orderLine.getC_UOM_ID();
			BigDecimal QtyOrdered = orderLine.getQtyOrdered();
			final int precision = MProduct.get(calloutField.getCtx(), M_Product_ID).getUOMPrecision();
			final BigDecimal QtyOrdered1 = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (QtyOrdered.compareTo(QtyOrdered1) != 0)
			{
				log.debug("Corrected QtyOrdered Scale " + QtyOrdered + "->" + QtyOrdered1);
				QtyOrdered = QtyOrdered1;
				orderLine.setQtyOrdered(QtyOrdered);
			}
			BigDecimal QtyEntered = MUOMConversion.convertFromProductUOM(calloutField.getCtx(), M_Product_ID, C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
			{
				QtyEntered = QtyOrdered;
			}
			final boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			log.debug("UOM=" + C_UOM_To_ID + ", QtyOrdered=" + QtyOrdered + " -> " + conversion + " QtyEntered=" + QtyEntered);
			setUOMConversion(calloutField, conversion);
			orderLine.setQtyEntered(QtyEntered);
		}

		// Storage
		final BigDecimal QtyOrdered = orderLine.getQtyOrdered();
		if (M_Product_ID > 0 && orderLine.getC_Order().isSOTrx()
				&& QtyOrdered.signum() > 0)  // no negative (returns)
		{
			final I_M_Product product = orderLine.getM_Product();
			if (Services.get(IProductBL.class).isStocked(product))
			{
				final int M_Warehouse_ID = orderLine.getM_Warehouse_ID();
				final int M_AttributeSetInstance_ID = orderLine.getM_AttributeSetInstance_ID();
				BigDecimal available = MStorage.getQtyAvailable(M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
				if (available == null)
				{
					available = BigDecimal.ZERO;
				}

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
					{
						C_OrderLine_ID = 0;
					}
					BigDecimal notReserved = MOrderLine.getNotReserved(calloutField.getCtx(),
							M_Warehouse_ID, M_Product_ID,
							M_AttributeSetInstance_ID, C_OrderLine_ID);
					if (notReserved == null)
					{
						notReserved = BigDecimal.ZERO;
					}
					final BigDecimal total = available.subtract(notReserved);
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

	@Builder
	@Value
	private static class CreditLimitRequest
	{
		final int bpartnerId;
		@NonNull
		final String creditStatus;
		final boolean evalCreditstatus;
		@NonNull
		final Timestamp evaluationDate;
	}

	/**
	 * Decides whether the business partner's credit limit should be checked.
	 *
	 * @param bpartnerId
	 * @param creditStatus
	 * @param evalCreditstatus if <code>true</code>, the result set's column <code>"SOCreditStatus"</code> is also used for the decision
	 * @return
	 */
	private boolean isChkCreditLimit(@NonNull final CreditLimitRequest creditlimitrequest)
	{
		final int bpartnerId = creditlimitrequest.getBpartnerId();
		final String creditStatus = creditlimitrequest.getCreditStatus();
		final boolean evalCreditstatus = creditlimitrequest.isEvalCreditstatus();
		final Timestamp evaluationDate = creditlimitrequest.getEvaluationDate();

		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpartnerId, evaluationDate);
		boolean dontCheck = true;
		if (evalCreditstatus)
		{
			dontCheck = creditLimit.signum() == 0 || X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(creditStatus);
		}
		else
		{
			dontCheck = creditLimit.signum() == 0;
		}
		return !dontCheck;
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
		{
			return NO_ERROR;
		}
		final String sql = "SELECT lship.C_BPartner_Location_ID,c.AD_User_ID "
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
				{
					dropShipAware.setDropShip_Location(null);
				}
				else
				{
					dropShipAware.setDropShip_Location_ID(shipTo_ID);
				}

				int contID = rs.getInt("AD_User_ID");
				if (dropShipBPartnerId == calloutField.getTabInfoContextAsInt("DropShip_BPartner_ID"))
				{
					final int tabInfoContactId = calloutField.getTabInfoContextAsInt("DropShip_User_ID");
					if (tabInfoContactId > 0)
					{
						contID = tabInfoContactId;
					}
				}
				if (contID <= 0)
				{
					dropShipAware.setDropShip_User(null);
				}
				else
				{
					dropShipAware.setDropShip_User_ID(contID);
				}

			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
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

	private static boolean isEnforcePriceLimit(final I_C_OrderLine orderLine, final boolean isSOTrx)
	{
		// We enforce PriceLimit only for sales orders
		if (!isSOTrx)
		{
			return false;
		}

		if (!orderLine.isEnforcePriceLimit())
		{
			return false;
		}

		return true;
	}

	public String attributeSetInstance(final ICalloutField calloutField)
	{
		// 05118 : Also update the prices
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		Services.get(IOrderLineBL.class).updatePrices(orderLine);

		return NO_ERROR;
	}
} // CalloutOrder
