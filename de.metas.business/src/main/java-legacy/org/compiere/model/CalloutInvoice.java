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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.documentNo.impl.IDocumentNoInfo;
import de.metas.logging.MetasfreshLastError;
import de.metas.tax.api.ITaxBL;

/**
 * Invoice Callouts
 *
 * @author Jorg Janke
 * @version $Id: CalloutInvoice.java,v 1.4 2006/07/30 00:51:03 jjanke Exp $
 */
public class CalloutInvoice extends CalloutEngine
{
	public static final String CTX_EnforcePriceLimit = "EnforcePriceLimit";
	public static final String CTX_DiscountSchema = "DiscountSchema";

	/**
	 * Invoice Header - DocType.
	 * - PaymentRule
	 * - temporary Document
	 * Context:
	 * - DocSubType
	 * - HasCharges
	 * - (re-sets Business Partner info of required)
	 *
	 * @param calloutField
	 * @return error message or {@link #NO_ERROR}
	 */
	public String docType(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(invoice.getC_DocTypeTarget())
				.setOldDocumentNo(invoice.getDocumentNo())
				.setDocumentModel(invoice)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return NO_ERROR;
		}

		// Charges - Set Context
		calloutField.putWindowContext(I_C_DocType.COLUMNNAME_HasCharges, documentNoInfo.isHasChanges());

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			invoice.setDocumentNo(documentNoInfo.getDocumentNo());
		}

		// DocBaseType - Set Context
		final String docBaseType = documentNoInfo.getDocBaseType();
		calloutField.putWindowContext(I_C_DocType.COLUMNNAME_DocBaseType, docBaseType);

		// AP Check & AR Credit Memo
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		if (docBaseType == null)
		{
			// nothing
		}
		if (invoiceBL.isVendorInvoice(docBaseType))
		{
			invoice.setPaymentRule(X_C_Invoice.PAYMENTRULE_Check); // Check
		}
		else if (invoiceBL.isCreditMemo(docBaseType))
		{
			invoice.setPaymentRule(X_C_Invoice.PAYMENTRULE_OnCredit); // OnCredit
		}

		//
		return NO_ERROR;
	}	// docType

	/**
	 * Invoice Header- BPartner.
	 * - M_PriceList_ID (+ Context)
	 * - C_BPartner_Location_ID
	 * - AD_User_ID
	 * - POReference
	 * - SO_Description
	 * - IsDiscountPrinted
	 * - PaymentRule
	 * - C_PaymentTerm_ID
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String bPartner(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);
		final int bPartnerID = invoice.getC_BPartner_ID();
		if (bPartnerID <= 0)
		{
			return NO_ERROR;
		}

		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
				+ " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
				+ " p.SO_Description,p.IsDiscountPrinted,"
				+ " p.SO_CreditLimit, p.SO_CreditLimit-stats."
				+ I_C_BPartner_Stats.COLUMNNAME_SO_CreditUsed
				+ " AS CreditAvailable,"
				+ " l.C_BPartner_Location_ID,c.AD_User_ID,"
				+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID "
				+ "FROM C_BPartner p"
				+ " INNER JOIN "
				+ I_C_BPartner_Stats.Table_Name
				+ " stats ON (p."
				+ I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " = stats."
				+ I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID
				+ ")"
				+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID)"
				+ " LEFT OUTER JOIN C_BPartner_Location l ON (p.C_BPartner_ID=l.C_BPartner_ID AND l.IsBillTo='Y' AND l.IsActive='Y')"
				+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
				+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		// #1

		boolean isSOTrx = invoice.isSOTrx();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, bPartnerID);
			rs = pstmt.executeQuery();
			//
			if (rs.next())
			{
				// PriceList & Currency
				int priceListId = rs.getInt(isSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID");
				if (!rs.wasNull())
				{
					invoice.setM_PriceList_ID(priceListId);
				}
				else
				{	// get default PriceList
					priceListId = calloutField.getGlobalContextAsInt("#M_PriceList_ID");
					if (priceListId > 0)
					{
						invoice.setM_PriceList_ID(priceListId);
					}
				}

				final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

				// PaymentRule
				String paymentRule = rs.getString(isSOTrx ? "PaymentRule" : "PaymentRulePO");
				if (!Check.isEmpty(paymentRule))
				{
					final String docBaseType = invoice.getC_DocType().getDocBaseType();

					// Credits are Payment Term
					if (invoiceBL.isCreditMemo(docBaseType))
					{
						paymentRule = X_C_Invoice.PAYMENTRULE_OnCredit;
					}

					// No Check/Transfer for SO_Trx
					else if (isSOTrx && (X_C_Invoice.PAYMENTRULE_Check.equals(paymentRule)))
					{
						paymentRule = X_C_Invoice.PAYMENTRULE_OnCredit; // Payment Term
					}

					invoice.setPaymentRule(paymentRule);
				}
				// Payment Term
				final int paymentTermId = new Integer(rs.getInt(isSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
				if (!rs.wasNull())
				{
					invoice.setC_PaymentTerm_ID(paymentTermId);
				}

				// Location
				int bpartnerLocationId = rs.getInt("C_BPartner_Location_ID");
				int contactUserId = rs.getInt("AD_User_ID");
				
				// overwritten by InfoBP selection - works only if InfoWindow
				// was used otherwise creates error (uses last value, may belong to different BP)
				if (bPartnerID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int locFromContextId = calloutField.getTabInfoContextAsInt("C_BPartner_Location_ID");
					if (locFromContextId > 0)
					{
						bpartnerLocationId = locFromContextId;
					}

					final int contactFromContextId = calloutField.getTabInfoContextAsInt("AD_User_ID");
					if (contactFromContextId > 0)
					{
						contactUserId = contactFromContextId;
					}
				}
				
				invoice.setC_BPartner_Location_ID(bpartnerLocationId);
				invoice.setAD_User_ID(contactUserId);

				// CreditAvailable
				if (isSOTrx)
				{
					double CreditLimit = rs.getDouble("SO_CreditLimit");
					if (CreditLimit != 0)
					{
						double CreditAvailable = rs.getDouble("CreditAvailable");
						if (!rs.wasNull() && CreditAvailable < 0)
						{
							calloutField.fireDataStatusEEvent("CreditLimitOver",
									DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
									false);
						}
					}
				}

				// PO Reference
				final String poReference = rs.getString("POReference");

				// 08593: don't override or clear a pre-existing POReference, because e.g. a POreference might be already entered by a user,
				// and then the vendor might be set or changed by the same user
				if (!Check.isEmpty(poReference, true) && Check.isEmpty(invoice.getPOReference()))
				{
					invoice.setPOReference(poReference);
				}

				// SO Description
				final String soDescription = rs.getString("SO_Description");
				if (!Check.isEmpty(soDescription))
				{
					invoice.setDescription(soDescription);
				}

				// IsDiscountPrinted
				final boolean isDiscountPrinted = DisplayType.toBoolean(rs.getString("IsDiscountPrinted"));
				invoice.setIsDiscountPrinted(isDiscountPrinted);
				
			}
		}
		catch (SQLException e)
		{
			log.error("bPartner", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return "";
	}	// bPartner

	/**
	 * Set Payment Term.
	 * Payment Term has changed
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String paymentTerm(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_PaymentTerm_ID = (Integer)value;
		int C_Invoice_ID = Env.getContextAsInt(ctx, WindowNo, "C_Invoice_ID");
		if (C_PaymentTerm_ID == null || C_PaymentTerm_ID.intValue() == 0
				|| C_Invoice_ID == 0) 	// not saved yet
			return "";
		//
		MPaymentTerm pt = new MPaymentTerm(ctx, C_PaymentTerm_ID.intValue(), null);
		if (pt.get_ID() == 0)
			return "PaymentTerm not found";

		boolean valid = pt.apply(C_Invoice_ID);
		mTab.setValue("IsPayScheduleValid", valid ? "Y" : "N");

		return "";
	}	// paymentTerm

	/**************************************************************************
	 * Invoice Line - Product.
	 * - reset C_Charge_ID / M_AttributeSetInstance_ID
	 * - PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit
	 * - UOM
	 * Calls Tax
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String product(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() <= 0)
			return "";
		mTab.setValue("C_Charge_ID", null);

		// Set Attribute

		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
				&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
			mTab.setValue("M_AttributeSetInstance_ID", Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
		else
			mTab.setValue("M_AttributeSetInstance_ID", null);

		de.metas.adempiere.model.I_C_InvoiceLine il = InterfaceWrapperHelper.create(mTab, de.metas.adempiere.model.I_C_InvoiceLine.class);

		/***** Price Calculation see also qty ****/
		boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, WindowNo, "C_BPartner_ID");
		final BigDecimal Qty = il.getQtyInvoiced();

		MProductPricing pp = new MProductPricing(M_Product_ID.intValue(), C_BPartner_ID, Qty, IsSOTrx);
		pp.setConvertPriceToContextUOM(false);
		//
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		pp.setM_PriceList_ID(M_PriceList_ID);
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		Timestamp date = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
		pp.setPriceDate(date);
		final int invoiceLineId = il.getC_InvoiceLine_ID();
		if (invoiceLineId > 0)
		{
			final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(ctx, invoiceLineId, I_C_InvoiceLine.class, ITrx.TRXNAME_None);
			pp.setReferencedObject(invoiceLine);
		}

		pp.setManualPrice(il.isManualPrice());

		// task 08908: Make sure the pricing engine knows about the manualPrice Value
		// before the price values are set in the invoice line.
		// In case the price is manual, the values just come from the initial pricing result

		if (!pp.isManualPrice() || mField.getColumnName().equals("M_Product_ID"))
		{
			il.setPriceList(pp.getPriceList());
			il.setPriceLimit(pp.getPriceLimit());
			// metas us1064
			il.setPriceActual(pp.mkPriceStdMinusDiscount());
			// metas us1064 end
			il.setPriceEntered(pp.getPriceStd());
			// mTab.setValue("Discount", pp.getDiscount());
			il.setPrice_UOM_ID(pp.getC_UOM_ID());

			Env.setContext(ctx, WindowNo, CTX_EnforcePriceLimit, pp.isEnforcePriceLimit());
			Env.setContext(ctx, WindowNo, CTX_DiscountSchema, pp.isDiscountSchema());
		}

		// 07216: Correctly set price and product UOM.
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, M_Product_ID, I_M_Product.class, ITrx.TRXNAME_None);

		il.setC_UOM_ID(product.getC_UOM_ID());

		//
		return tax(ctx, WindowNo, mTab, mField, value);
	}	// product

	/**
	 * Invoice Line - Charge.
	 * - updates PriceActual from Charge
	 * - sets PriceLimit, PriceList to zero
	 * Calles tax
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String charge(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Charge_ID = (Integer)value;
		if (C_Charge_ID == null || C_Charge_ID.intValue() == 0)
			return "";

		// No Product defined
		if (mTab.getValue("M_Product_ID") != null)
		{
			mTab.setValue("C_Charge_ID", null);
			return "ChargeExclusively";
		}
		mTab.setValue("M_AttributeSetInstance_ID", null);
		mTab.setValue("S_ResourceAssignment_ID", null);
		mTab.setValue("C_UOM_ID", new Integer(100));	// EA
		mTab.setValue("Price_UOM_ID", new Integer(100)); // 07216: Make sure price UOM is also filled.

		Env.setContext(ctx, WindowNo, CTX_DiscountSchema, false);
		String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Charge_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				mTab.setValue("PriceEntered", rs.getBigDecimal(1));
				mTab.setValue("PriceActual", rs.getBigDecimal(1));
				mTab.setValue("PriceLimit", Env.ZERO);
				mTab.setValue("PriceList", Env.ZERO);
				mTab.setValue("Discount", Env.ZERO);
			}
		}
		catch (SQLException e)
		{
			log.error(sql + e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		return tax(ctx, WindowNo, mTab, mField, value);
	}	// charge

	/**
	 * Invoice Line - Tax.
	 * - basis: Product, Charge, BPartner Location
	 * - sets C_Tax_ID
	 * Calls Amount
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String tax(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String column = mField.getColumnName();
		if (value == null)
			return "";

		// Check Product
		int M_Product_ID = 0;
		if (column.equals("M_Product_ID"))
			M_Product_ID = ((Integer)value).intValue();
		else
			M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int C_Charge_ID = 0;
		if (column.equals("C_Charge_ID"))
			C_Charge_ID = ((Integer)value).intValue();
		else
			C_Charge_ID = Env.getContextAsInt(ctx, WindowNo, "C_Charge_ID");
		log.debug("Product=" + M_Product_ID + ", C_Charge_ID=" + C_Charge_ID);
		if (M_Product_ID == 0 && C_Charge_ID == 0)
			return amt(ctx, WindowNo, mTab, mField, value);	//

		// Check Partner Location
		int shipC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_Location_ID");
		if (shipC_BPartner_Location_ID == 0)
			return amt(ctx, WindowNo, mTab, mField, value);	//
		log.debug("Ship BP_Location=" + shipC_BPartner_Location_ID);
		int billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
		log.debug("Bill BP_Location=" + billC_BPartner_Location_ID);

		// Dates
		Timestamp billDate = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
		log.debug("Bill Date=" + billDate);
		Timestamp shipDate = billDate;
		log.debug("Ship Date=" + shipDate);

		int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");
		log.debug("Org=" + AD_Org_ID);

		int M_Warehouse_ID = Env.getContextAsInt(ctx, "#M_Warehouse_ID");
		log.debug("Warehouse=" + M_Warehouse_ID);

		//
		int C_Tax_ID = Tax.get(ctx, M_Product_ID, C_Charge_ID, billDate, shipDate,
				AD_Org_ID, M_Warehouse_ID, billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
				Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y"));
		log.info("Tax ID=" + C_Tax_ID);
		//
		if (C_Tax_ID == 0)
			mTab.fireDataStatusEEvent(MetasfreshLastError.retrieveError());
		else
			mTab.setValue("C_Tax_ID", new Integer(C_Tax_ID));
		//
		return amt(ctx, WindowNo, mTab, mField, value);
	}	// tax

	/**
	 * Invoice - Amount.
	 * - called from QtyInvoiced, PriceActual
	 * - calculates LineNetAmt
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String amt(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
		{
			return "";
		}

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(mTab, I_C_InvoiceLine.class);
		// instance of de.metas.adempiere.model.I_C_InvoiceLine, needed for columns not existing in the original version
		final de.metas.adempiere.model.I_C_InvoiceLine il = InterfaceWrapperHelper.create(invoiceLine, de.metas.adempiere.model.I_C_InvoiceLine.class);
		final boolean isManualPrice = il.isManualPrice();

		// log.warn("amt - init");
		int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "Price_UOM_ID"); // 07216 : We convert to the price UOM.
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID"); // task 08908: note that there is no such column in C_Invoice or C_InvoiceLine
		int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
		BigDecimal QtyEntered, QtyInvoiced, PriceEntered, PriceActual, PriceLimit, Discount, PriceList;
		// get values
		QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
		QtyInvoiced = (BigDecimal)mTab.getValue("QtyInvoiced");
		log.debug("QtyEntered=" + QtyEntered + ", Invoiced=" + QtyInvoiced + ", UOM=" + C_UOM_To_ID);
		//
		PriceEntered = (BigDecimal)mTab.getValue("PriceEntered");
		PriceActual = (BigDecimal)mTab.getValue("PriceActual");
		// Discount = (BigDecimal)mTab.getValue("Discount");
		PriceLimit = (BigDecimal)mTab.getValue("PriceLimit");
		PriceList = (BigDecimal)mTab.getValue("PriceList");
		log.debug("PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
		log.debug("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual);// + ", Discount=" + Discount);
		// metas: Gutschrift Grund
		mTab.setValue("Line_CreditMemoReason", Env.getContext(ctx, WindowNo, "CreditMemoReason"));
		// metas

		// Qty changed - recalc price
		if ((mField.getColumnName().equals("QtyInvoiced")
				|| mField.getColumnName().equals("QtyEntered")
				|| mField.getColumnName().equals("M_Product_ID"))
				&& !"N".equals(Env.getContext(ctx, WindowNo, CTX_DiscountSchema)))
		{
			// task 08908
			// Do not change a thing if the price is manual, except for the case the product changes
			if (mField.getColumnName().equals("M_Product_ID") || !isManualPrice)
			{
				int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
				if (mField.getColumnName().equals("QtyEntered"))
					QtyInvoiced = MUOMConversion.convertFromProductUOM(ctx, M_Product_ID,
							C_UOM_To_ID, QtyEntered);
				if (QtyInvoiced == null)
					QtyInvoiced = QtyEntered;
				boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
				MProductPricing pp = new MProductPricing(M_Product_ID, C_BPartner_ID, QtyInvoiced, IsSOTrx);
				pp.setM_PriceList_ID(M_PriceList_ID);
				pp.setReferencedObject(invoiceLine); // task 08908: we need to give the pricing context our referenced object, so it can extract the ASI
				final Timestamp date = invoiceLine.getC_Invoice().getDateInvoiced(); // task 08908: we do not have a PLV-ID in C_Invoice or C_InvoiceLine, so we need to get the invoice's date to
 // enable the pricing engine to find the PLV
				pp.setPriceDate(date);
				//
				// /08763: Never convert this in product uom because Prices must always be in PriceUOM
				// PriceEntered = MUOMConversion.convertToProductUOM (ctx, M_Product_ID,
				// C_UOM_To_ID, pp.getPriceStd());
				// if (PriceEntered == null)
				// PriceEntered = pp.getPriceStd();
				// metas us1064
				log.debug("amt - QtyChanged -> PriceActual=" + pp.mkPriceStdMinusDiscount()
						+ ", PriceEntered=" + PriceEntered + ", Discount=" + pp.getDiscount());

				PriceActual = pp.mkPriceStdMinusDiscount();
				mTab.setValue("PriceActual", PriceActual); // 08763 align the behavior to that of order line
				// metas us1064 end
				// mTab.setValue("Discount", pp.getDiscount());
				mTab.setValue("PriceEntered", PriceActual); // 08763 align the behavior to that of order line
				Env.setContext(ctx, WindowNo, CTX_DiscountSchema, pp.isDiscountSchema());
			}
		}
		else if (mField.getColumnName().equals("PriceActual"))
		{
			PriceActual = (BigDecimal)value;
			PriceEntered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			//
			log.debug("amt - PriceActual=" + PriceActual
					+ " -> PriceEntered=" + PriceEntered);
			mTab.setValue("PriceEntered", PriceEntered);

		}
		else if (mField.getColumnName().equals("PriceEntered"))
		{
			PriceEntered = (BigDecimal)value;

			// task 08763: PriceActual = PriceEntered should be OK in invoices. see the task chant and wiki-page for details
			PriceActual = PriceEntered.setScale(StdPrecision, RoundingMode.HALF_UP);
			//
			log.debug("amt - PriceEntered=" + PriceEntered
					+ " -> PriceActual=" + PriceActual);
			mTab.setValue("PriceActual", PriceActual);
		}

		/*
		 * Discount entered - Calculate Actual/Entered
		 * if (mField.getColumnName().equals("Discount"))
		 * {
		 * PriceActual = new BigDecimal ((100.0 - Discount.doubleValue()) / 100.0 * PriceList.doubleValue());
		 * if (PriceActual.scale() > StdPrecision)
		 * PriceActual = PriceActual.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		 * PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
		 * C_UOM_To_ID, PriceActual);
		 * if (PriceEntered == null)
		 * PriceEntered = PriceActual;
		 * mTab.setValue("PriceActual", PriceActual);
		 * mTab.setValue("PriceEntered", PriceEntered);
		 * }
		 * // calculate Discount
		 * else
		 * {
		 * if (PriceList.intValue() == 0)
		 * Discount = Env.ZERO;
		 * else
		 * Discount = new BigDecimal ((PriceList.doubleValue() - PriceActual.doubleValue()) / PriceList.doubleValue() * 100.0);
		 * if (Discount.scale() > 2)
		 * Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
		 * mTab.setValue("Discount", Discount);
		 * }
		 * log.debug("amt = PriceEntered=" + PriceEntered + ", Actual" + PriceActual + ", Discount=" + Discount);
		 * /*
		 */

		// Check PriceLimit
		String epl = Env.getContext(ctx, WindowNo, CTX_EnforcePriceLimit);
		boolean enforce = Env.isSOTrx(ctx, WindowNo) && epl != null && epl.equals("Y");
		if (enforce && Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_OverwritePriceLimit))
			enforce = false;
		// Check Price Limit?
		if (enforce && PriceLimit.doubleValue() != 0.0
				&& PriceActual.compareTo(PriceLimit) < 0
				&& !isManualPrice // do not change anything if the price is manual
		)
		{
			PriceActual = PriceLimit;
			PriceEntered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, PriceLimit);
			if (PriceEntered == null)
				PriceEntered = PriceLimit;
			log.debug("amt =(under) PriceEntered=" + PriceEntered + ", Actual" + PriceLimit);
			mTab.setValue("PriceActual", PriceLimit);
			mTab.setValue("PriceEntered", PriceEntered);
			mTab.fireDataStatusEEvent("UnderLimitPrice", "", false);
			// Repeat Discount calc
			if (PriceList.intValue() != 0)
			{
				Discount = new BigDecimal((PriceList.doubleValue() - PriceActual.doubleValue()) / PriceList.doubleValue() * 100.0);
				if (Discount.scale() > 2)
					Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
				// mTab.setValue ("Discount", Discount);
			}
		}

		// Line Net Amt
		BigDecimal LineNetAmt = QtyInvoiced.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		log.info("amt = LineNetAmt=" + LineNetAmt);
		mTab.setValue("LineNetAmt", LineNetAmt);

		// Calculate Tax Amount for PO
		boolean IsSOTrx = DisplayType.toBoolean(Env.getContext(Env.getCtx(), WindowNo, "IsSOTrx"));
		if (!IsSOTrx)
		{
			BigDecimal TaxAmt = BigDecimal.ZERO; // teo_sarca: [ 1656829 ] Problem when there is not tax selected in vendor invoice
			if (mField.getColumnName().equals("TaxAmt"))
			{
				TaxAmt = (BigDecimal)mTab.getValue("TaxAmt");
			}
			else
			{
				Integer taxID = (Integer)mTab.getValue("C_Tax_ID");
				if (taxID != null && taxID > 0)
				{
					final int C_Tax_ID = taxID.intValue();
					final I_C_Tax tax = new MTax(ctx, C_Tax_ID, null);
					final boolean taxIncluded = isTaxIncluded(invoiceLine);
					TaxAmt = Services.get(ITaxBL.class).calculateTax(tax, LineNetAmt, taxIncluded, StdPrecision);
					mTab.setValue("TaxAmt", TaxAmt);
				}
			}
			// Add it up
			mTab.setValue("LineTotalAmt", LineNetAmt.add(TaxAmt));
		}

		return "";
	}	// amt

	/**
	 * Is Tax Included
	 *
	 * @param invoiceLine
	 * @return tax included (default: false)
	 */
	private boolean isTaxIncluded(final I_C_InvoiceLine invoiceLine)
	{
		return Services.get(IInvoiceBL.class).isTaxIncluded(invoiceLine);
	}	// isTaxIncluded

	/**
	 * Invoice Line - Quantity.
	 * - called from C_UOM_ID, QtyEntered, QtyInvoiced
	 * - enforces qty UOM relationship
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String qty(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(mTab, I_C_InvoiceLine.class);
		// instance of de.metas.adempiere.model.I_C_InvoiceLine, needed for columns not existing in the original version
		final de.metas.adempiere.model.I_C_InvoiceLine il = InterfaceWrapperHelper.create(invoiceLine, de.metas.adempiere.model.I_C_InvoiceLine.class);
		final boolean isManualPrice = il.isManualPrice();

		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		// log.warn("qty - init - M_Product_ID=" + M_Product_ID);
		BigDecimal QtyInvoiced, QtyEntered, PriceActual, PriceEntered;

		// No Product
		if (M_Product_ID == 0)
		{
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			mTab.setValue("QtyInvoiced", QtyEntered);
		}
		// UOM Changed - convert from Entered -> Product
		else if (mField.getColumnName().equals("C_UOM_ID"))
		{
			int C_UOM_To_ID = ((Integer)value).intValue();
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID
						+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyInvoiced = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyEntered);
			if (QtyInvoiced == null)
				QtyInvoiced = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyInvoiced) != 0;

			// do not change anything if manual price
			if (!isManualPrice)
			{
				PriceActual = (BigDecimal)mTab.getValue("PriceActual");
				PriceEntered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
						C_UOM_To_ID, PriceActual);
				if (PriceEntered == null)
					PriceEntered = PriceActual;

				log.debug("qty - UOM=" + C_UOM_To_ID
						+ ", QtyEntered/PriceActual=" + QtyEntered + "/" + PriceActual
						+ " -> " + conversion
						+ " QtyInvoiced/PriceEntered=" + QtyInvoiced + "/" + PriceEntered);
				Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
				mTab.setValue("PriceEntered", PriceEntered);
			}
			mTab.setValue("QtyInvoiced", QtyInvoiced);

		}
		// QtyEntered changed - calculate QtyInvoiced
		else if (mField.getColumnName().equals("QtyEntered"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyEntered = (BigDecimal)value;
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID
						+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyInvoiced = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyEntered);
			if (QtyInvoiced == null)
				QtyInvoiced = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyInvoiced) != 0;
			log.debug("qty - UOM=" + C_UOM_To_ID
					+ ", QtyEntered=" + QtyEntered
					+ " -> " + conversion
					+ " QtyInvoiced=" + QtyInvoiced);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyInvoiced", QtyInvoiced);
		}
		// QtyInvoiced changed - calculate QtyEntered (should not happen)
		else if (mField.getColumnName().equals("QtyInvoiced"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyInvoiced = (BigDecimal)value;
			int precision = MProduct.get(ctx, M_Product_ID).getUOMPrecision();
			BigDecimal QtyInvoiced1 = QtyInvoiced.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (QtyInvoiced.compareTo(QtyInvoiced1) != 0)
			{
				log.debug("Corrected QtyInvoiced Scale "
						+ QtyInvoiced + "->" + QtyInvoiced1);
				QtyInvoiced = QtyInvoiced1;
				mTab.setValue("QtyInvoiced", QtyInvoiced);
			}
			QtyEntered = MUOMConversion.convertFromProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyInvoiced);
			if (QtyEntered == null)
				QtyEntered = QtyInvoiced;
			boolean conversion = QtyInvoiced.compareTo(QtyEntered) != 0;
			log.debug("qty - UOM=" + C_UOM_To_ID
					+ ", QtyInvoiced=" + QtyInvoiced
					+ " -> " + conversion
					+ " QtyEntered=" + QtyEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyEntered", QtyEntered);
		}
		//
		return "";
	}	// qty

	public String priceList(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(mTab, I_C_Invoice.class);
		final I_M_PriceList priceList = invoice.getM_PriceList();
		if (priceList == null || priceList.getM_PriceList_ID() <= 0)
		{
			return NO_ERROR;
		}

		// Tax Included
		invoice.setIsTaxIncluded(priceList.isTaxIncluded());
		// Currency
		invoice.setC_Currency_ID(priceList.getC_Currency_ID());

		return NO_ERROR;
	} // priceList

}	// CalloutInvoice
