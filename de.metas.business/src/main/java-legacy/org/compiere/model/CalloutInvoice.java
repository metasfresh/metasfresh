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
import org.adempiere.uom.api.IUOMDAO;
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
	private static final String CTX_UOMConversion = "UOMConversion";

	private static final String MSG_UnderLimitPrice = "UnderLimitPrice";

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
		else if (invoiceBL.isVendorInvoice(docBaseType))
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
				
				if (!Check.isEmpty(paymentRule)) {
					
					final I_C_DocType invoiceDocType = invoice.getC_DocType() == null ? invoice.getC_DocTypeTarget()
							: invoice.getC_DocType();

					if (invoiceDocType != null) {

						final String docBaseType = invoiceDocType.getDocBaseType();

						// Credits are Payment Term
						if (invoiceBL.isCreditMemo(docBaseType)) {
							paymentRule = X_C_Invoice.PAYMENTRULE_OnCredit;
						}

						// No Check/Transfer for SO_Trx
						else if (isSOTrx && (X_C_Invoice.PAYMENTRULE_Check.equals(paymentRule))) {
							paymentRule = X_C_Invoice.PAYMENTRULE_OnCredit; // Payment
																			// Term
						}

						invoice.setPaymentRule(paymentRule);
					}
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
	public String paymentTerm(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);

		final I_C_PaymentTerm paymentTerm = invoice.getC_PaymentTerm();

		if (paymentTerm == null)
		{
			// nothing to do
			return NO_ERROR;
		}

		// TODO: Fix in next step (refactoring: Move the apply method from MPaymentTerm to a BL)
		MPaymentTerm pt = InterfaceWrapperHelper.getPO(paymentTerm);

		final boolean valid = pt.apply(invoice.getC_Invoice_ID());
		invoice.setIsPayScheduleValid(valid);

		return NO_ERROR;
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
	public String product(final ICalloutField calloutField)
	{
		final I_C_InvoiceLine invoiceLine = calloutField.getModel(I_C_InvoiceLine.class);
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();

		final I_M_Product product = invoiceLine.getM_Product();
		final int productID = product.getM_Product_ID();

		if (productID <= 0)
		{
			// nothing to do
			return NO_ERROR;
		}

		// a line with product does not have charge
		invoiceLine.setC_Charge_ID(-1);

		final int asiID = calloutField.getTabInfoContextAsInt("M_AttributeSetInstance_ID");

		// Set Attribute
		if (productID == calloutField.getTabInfoContextAsInt("M_Product_ID")
				&& asiID > 0)
		{
			invoiceLine.setM_AttributeSetInstance_ID(asiID);
		}
		else
		{
			invoiceLine.setM_AttributeSetInstance_ID(-1);
		}

		/***** Price Calculation see also qty ****/
		boolean isSOTrx = invoice.isSOTrx();

		final int bpartnerID = invoice.getC_BPartner_ID();

		final BigDecimal qty = invoiceLine.getQtyInvoiced();

		// TODO: Refactoring here in step 2: Use IPricingBL instead
		MProductPricing pp = new MProductPricing(productID, bpartnerID, qty, isSOTrx);
		pp.setConvertPriceToContextUOM(false);

		//
		final int priceList_ID = invoice.getM_PriceList_ID();
		pp.setM_PriceList_ID(priceList_ID);

		int priceListVersionID = calloutField.getTabInfoContextAsInt("M_PriceList_Version_ID");
		pp.setM_PriceList_Version_ID(priceListVersionID);

		final Timestamp dateInvoiced = invoice.getDateInvoiced();
		pp.setPriceDate(dateInvoiced);

		pp.setReferencedObject(invoiceLine);

		final de.metas.adempiere.model.I_C_InvoiceLine adInvoiceLine = InterfaceWrapperHelper.create(invoiceLine, de.metas.adempiere.model.I_C_InvoiceLine.class);
		pp.setManualPrice(adInvoiceLine.isManualPrice());

		// task 08908: Make sure the pricing engine knows about the manualPrice Value
		// before the price values are set in the invoice line.
		// In case the price is manual, the values just come from the initial pricing result

		final String columnName = calloutField.getColumnName();

		if (!pp.isManualPrice() || columnName.equals("M_Product_ID"))
		{
			invoiceLine.setPriceList(pp.getPriceList());
			invoiceLine.setPriceLimit(pp.getPriceLimit());
			// metas us1064
			invoiceLine.setPriceActual(pp.mkPriceStdMinusDiscount());
			// metas us1064 end
			invoiceLine.setPriceEntered(pp.getPriceStd());
			// mTab.setValue("Discount", pp.getDiscount());
			adInvoiceLine.setPrice_UOM_ID(pp.getC_UOM_ID());

			calloutField.putWindowContext(CTX_EnforcePriceLimit, pp.isEnforcePriceLimit());
			calloutField.putWindowContext(CTX_DiscountSchema, pp.isDiscountSchema());
		}

		// 07216: Correctly set price and product UOM.
		invoiceLine.setC_UOM_ID(product.getC_UOM_ID());

		//
		return tax(calloutField);
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
	public String charge(final ICalloutField calloutField)
	{
		final de.metas.adempiere.model.I_C_InvoiceLine invoiceLine = calloutField.getModel(de.metas.adempiere.model.I_C_InvoiceLine.class);

		final int chargeID = invoiceLine.getC_Charge_ID();

		if (chargeID <= 0)
		{
			return NO_ERROR;
		}

		// No Product defined
		if (invoiceLine.getM_Product_ID() > 0)
		{
			invoiceLine.setC_Charge(null);
			return "ChargeExclusively";
		}

		invoiceLine.setM_AttributeSetInstance(null);
		invoiceLine.setS_ResourceAssignment_ID(-1);
		invoiceLine.setC_UOM_ID(IUOMDAO.C_UOM_ID_Each); // EA

		invoiceLine.setPrice_UOM_ID(IUOMDAO.C_UOM_ID_Each); // 07216: Make sure price UOM is also filled.

		calloutField.putContext(CTX_DiscountSchema, false);

		String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, chargeID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				invoiceLine.setPriceEntered(rs.getBigDecimal(1));
				invoiceLine.setPriceActual(rs.getBigDecimal(1));
				invoiceLine.setPriceLimit(BigDecimal.ZERO);
				invoiceLine.setPriceList(BigDecimal.ZERO);
				invoiceLine.setDiscount(BigDecimal.ZERO);

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
		return tax(calloutField);
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
	public String tax(final ICalloutField calloutField)
	{
		final Properties ctx = calloutField.getCtx();
		final String columnName = calloutField.getColumnName();

		final Object value = calloutField.getValue();
		if (value == null)
		{
			return NO_ERROR;
		}

		final I_C_InvoiceLine invoiceLine = calloutField.getModel(I_C_InvoiceLine.class);
		final I_C_Invoice invoice = invoiceLine.getC_Invoice(); // header

		// Check Product
		final int productID = invoiceLine.getM_Product_ID();
		final int chargeID = invoiceLine.getC_Charge_ID();

		log.debug("Product=" + productID + ", C_Charge_ID=" + chargeID);
		if (productID <= 0 && chargeID <= 0)
		{
			return amt(calloutField); //
		}

		// Check Partner Location
		int shipBPartnerLocationID = invoice.getC_BPartner_Location_ID();
		if (shipBPartnerLocationID <= 0)
		{
			return amt(calloutField);	//
		}

		log.debug("Ship BP_Location=" + shipBPartnerLocationID);

		int billBPartnerLocationID = shipBPartnerLocationID;
		log.debug("Bill BP_Location=" + billBPartnerLocationID);

		// Dates

		final Timestamp billDate = invoice.getDateInvoiced();
		log.debug("Bill Date=" + billDate);

		final Timestamp shipDate = billDate;
		log.debug("Ship Date=" + shipDate);

		final int orgID = invoiceLine.getAD_Org_ID();
		log.debug("Org=" + orgID);

		final int warehouseID = calloutField.getGlobalContextAsInt("#M_Warehouse_ID");
		log.debug("Warehouse=" + warehouseID);

		//
		final int taxID = Tax.get(ctx, productID, chargeID, billDate,
				shipDate, orgID, warehouseID,
				billBPartnerLocationID, shipBPartnerLocationID, invoice.isSOTrx());

		log.info("Tax ID=" + taxID);

		//
		if (taxID <= 0)
		{
			calloutField.fireDataStatusEEvent(MetasfreshLastError.retrieveError());
		}
		else
		{
			invoiceLine.setC_Tax_ID(taxID);
		}
		//
		return amt(calloutField);
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
	public String amt(final ICalloutField calloutField)
	{
		final Object value = calloutField.getValue();
		if (isCalloutActive() || value == null)
		{
			return NO_ERROR;
		}

		final Properties ctx = calloutField.getCtx();

		final de.metas.adempiere.model.I_C_InvoiceLine invoiceLine = calloutField.getModel(de.metas.adempiere.model.I_C_InvoiceLine.class);
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();
		final boolean isSOTrx = invoice.isSOTrx();

		// instance of de.metas.adempiere.model.I_C_InvoiceLine, needed for columns not existing in the original version
		// final de.metas.adempiere.model.I_C_InvoiceLine il = InterfaceWrapperHelper.create(invoiceLine, de.metas.adempiere.model.I_C_InvoiceLine.class);
		final boolean isManualPrice = invoiceLine.isManualPrice();

		// log.warn("amt - init");
		final int uomToID = invoiceLine.getPrice_UOM_ID(); // 07216 : We convert to the price UOM.
		final int productID = invoiceLine.getM_Product_ID();
		final int priceListID = invoice.getM_PriceList_ID();
		final int priceListVersionID = calloutField.getTabInfoContextAsInt("M_PriceList_Version_ID"); // task 08908: note that there is no such column in C_Invoice or C_InvoiceLine
		final int stdPrecision = MPriceList.getStandardPrecision(ctx, priceListID);

		// get values
		final BigDecimal qtyEntered = invoiceLine.getQtyEntered();
		BigDecimal qtyInvoiced = invoiceLine.getQtyInvoiced();

		log.debug("QtyEntered=" + qtyEntered + ", Invoiced=" + qtyInvoiced + ", UOM=" + uomToID);

		//

		BigDecimal priceEntered = invoiceLine.getPriceEntered();
		BigDecimal priceActual = invoiceLine.getPriceActual();
		// final BigDecimal Discount = invoiceLine.getDiscount();
		BigDecimal priceLimit = invoiceLine.getPriceLimit();
		BigDecimal priceList = invoiceLine.getPriceList();

		log.debug("PriceList=" + priceList + ", Limit=" + priceLimit + ", Precision=" + stdPrecision);
		log.debug("PriceEntered=" + priceEntered + ", Actual=" + priceActual); // + ", Discount=" + Discount);

		// metas: Gutschrift Grund
		invoiceLine.setLine_CreditMemoReason(invoice.getCreditMemoReason());
		// metas

		// TODO: Use UpdatePrices HERE

		final String columnName = calloutField.getColumnName();
		final boolean isDiscountSchema = calloutField.getContextAsBoolean(CTX_DiscountSchema);

		// Qty changed - recalc price
		if ((columnName.equals("QtyInvoiced")
				|| columnName.equals("QtyEntered")
				|| columnName.equals("M_Product_ID"))
				&& !isDiscountSchema)
		{
			// task 08908
			// Do not change a thing if the price is manual, except for the case the product changes
			if (columnName.equals("M_Product_ID") || !isManualPrice)
			{
				final int bPartnerID = invoice.getC_BPartner_ID();

				if (columnName.equals("QtyEntered"))
				{
					qtyInvoiced = MUOMConversion.convertFromProductUOM(ctx, productID,
							uomToID, qtyEntered);
				}

				if (qtyInvoiced == null)
				{
					qtyInvoiced = qtyEntered;
				}

				// TODO: PricingBL
				MProductPricing pp = new MProductPricing(productID, bPartnerID, qtyInvoiced, isSOTrx);
				pp.setM_PriceList_ID(priceListID);
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
						+ ", PriceEntered=" + priceEntered + ", Discount=" + pp.getDiscount());

				priceActual = pp.mkPriceStdMinusDiscount();
				invoiceLine.setPriceActual(priceActual); // 08763 align the behavior to that of order line
				// metas us1064 end
				// mTab.setValue("Discount", pp.getDiscount());
				invoiceLine.setPriceEntered(priceActual); // 08763 align the behavior to that of order line

				calloutField.putContext(CTX_DiscountSchema, pp.isDiscountSchema());
			}
		}
		else if (columnName.equals("PriceActual"))
		{
			priceActual = (BigDecimal)value;
			priceEntered = MUOMConversion.convertToProductUOM(ctx, productID,
					uomToID, priceActual);

			if (priceEntered == null)
			{
				priceEntered = priceActual;
			}
			//
			log.debug("amt - PriceActual=" + priceActual
					+ " -> PriceEntered=" + priceEntered);

			invoiceLine.setPriceEntered(priceEntered);

		}
		else if (columnName.equals("PriceEntered"))
		{
			priceEntered = (BigDecimal)value;

			// task 08763: PriceActual = PriceEntered should be OK in invoices. see the task chant and wiki-page for details
			priceActual = priceEntered.setScale(stdPrecision, RoundingMode.HALF_UP);
			//
			log.debug("amt - PriceEntered=" + priceEntered
					+ " -> PriceActual=" + priceActual);

			invoiceLine.setPriceActual(priceActual);
		}

		/*
		 * Discount entered - Calculate Actual/Entered
		 * if (columnName.equals("Discount"))
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
		final boolean enforcePriceLimit = calloutField.getContextAsBoolean(CTX_EnforcePriceLimit);

		boolean enforce = enforcePriceLimit && isSOTrx;

		if (enforce && Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_OverwritePriceLimit))
			enforce = false;
		// Check Price Limit?
		if (enforce && priceLimit.doubleValue() != 0.0
				&& priceActual.compareTo(priceLimit) < 0
				&& !isManualPrice // do not change anything if the price is manual
		)
		{
			priceActual = priceLimit;
			priceEntered = MUOMConversion.convertToProductUOM(ctx, productID,
					uomToID, priceLimit);

			if (priceEntered == null)
			{
				priceEntered = priceLimit;
			}
			log.debug("amt =(under) PriceEntered=" + priceEntered + ", Actual" + priceLimit);

			invoiceLine.setPriceActual(priceLimit);
			invoiceLine.setPriceEntered(priceEntered);
			calloutField.fireDataStatusEEvent(MSG_UnderLimitPrice, "", false);

			// Repeat Discount calc
			// if (priceList.intValue() != 0)
			// {
			// discount = new BigDecimal((priceList.doubleValue() - priceActual.doubleValue()) / priceList.doubleValue() * 100.0);
			// if (Discount.scale() > 2)
			// Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
			// // mTab.setValue ("Discount", Discount);
			// }
		}

		// Line Net Amt
		BigDecimal lineNetAmt = qtyInvoiced.multiply(priceActual);
		if (lineNetAmt.scale() > stdPrecision)
		{
			lineNetAmt = lineNetAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
		}
		log.info("amt = LineNetAmt=" + lineNetAmt);

		invoiceLine.setLineNetAmt(lineNetAmt);

		// Calculate Tax Amount for PO

		// TODO: Use BL for this
		if (!isSOTrx)
		{
			BigDecimal taxAmt = BigDecimal.ZERO; // teo_sarca: [ 1656829 ] Problem when there is not tax selected in vendor invoice
			if (columnName.equals("TaxAmt"))
			{
				taxAmt = invoiceLine.getTaxAmt(); // (BigDecimal)mTab.getValue("TaxAmt");
			}
			else
			{
				final I_C_Tax tax = invoiceLine.getC_Tax();

				if (tax != null)
				{

					final boolean taxIncluded = isTaxIncluded(invoiceLine);
					taxAmt = Services.get(ITaxBL.class).calculateTax(tax, lineNetAmt, taxIncluded, stdPrecision);
					invoiceLine.setTaxAmt(taxAmt);
				}
			}
			// Add it up
			invoiceLine.setLineNetAmt(lineNetAmt.add(taxAmt));
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
	public String qty(final ICalloutField calloutField)
	{
		if (isCalloutActive() || calloutField.getValue() == null)
		{
			return NO_ERROR;
		}

		final String columnName = calloutField.getColumnName();

		final I_C_InvoiceLine invoiceLine = calloutField.getModel(I_C_InvoiceLine.class);

		// instance of de.metas.adempiere.model.I_C_InvoiceLine, needed for columns not existing in the original version
		final de.metas.adempiere.model.I_C_InvoiceLine il = InterfaceWrapperHelper.create(invoiceLine, de.metas.adempiere.model.I_C_InvoiceLine.class);
		final boolean isManualPrice = il.isManualPrice();

		int productID = invoiceLine.getM_Product_ID();

		// log.warn("qty - init - M_Product_ID=" + M_Product_ID);
		BigDecimal qtyInvoiced, qtyEntered, priceActual, priceEntered;

		final Properties ctx = calloutField.getCtx();
		// No Product
		if (productID <= 0)
		{
			qtyEntered = invoiceLine.getQtyEntered();
			invoiceLine.setQtyInvoiced(qtyEntered);
		}

		// UOM Changed - convert from Entered -> Product
		else if (columnName.equals("C_UOM_ID"))
		{
			final int uomToID = invoiceLine.getC_UOM_ID();

			qtyEntered = invoiceLine.getQtyEntered();
			BigDecimal qtyEnteredScaled = qtyEntered.setScale(MUOM.getPrecision(ctx, uomToID), BigDecimal.ROUND_HALF_UP);

			if (qtyEntered.compareTo(qtyEnteredScaled) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + uomToID
						+ "; QtyEntered=" + qtyEntered + "->" + qtyEnteredScaled);
				qtyEntered = qtyEnteredScaled;
				invoiceLine.setQtyEntered(qtyEntered);
			}

			qtyInvoiced = MUOMConversion.convertToProductUOM(ctx, productID,
					uomToID, qtyEntered);
			if (qtyInvoiced == null)
			{
				qtyInvoiced = qtyEntered;
			}

			boolean conversion = qtyEntered.compareTo(qtyInvoiced) != 0;

			// do not change anything if manual price
			if (!isManualPrice)
			{
				priceActual = invoiceLine.getPriceActual();

				priceEntered = MUOMConversion.convertToProductUOM(ctx, productID,
						uomToID, priceActual);

				if (priceEntered == null)
				{
					priceEntered = priceActual;
				}

				log.debug("qty - UOM=" + uomToID
						+ ", QtyEntered/PriceActual=" + qtyEntered + "/" + priceActual
						+ " -> " + conversion
						+ " QtyInvoiced/PriceEntered=" + qtyInvoiced + "/" + priceEntered);

				setUOMConversion(calloutField, conversion);
				invoiceLine.setPriceEntered(priceEntered);
			}

			invoiceLine.setQtyInvoiced(qtyInvoiced);

		}
		// QtyEntered changed - calculate QtyInvoiced
		else if (columnName.equals("QtyEntered"))
		{
			final int uomToID = invoiceLine.getC_UOM_ID();

			qtyEntered = invoiceLine.getQtyEntered();

			BigDecimal qtyEnteredScaled = qtyEntered.setScale(MUOM.getPrecision(ctx, uomToID), BigDecimal.ROUND_HALF_UP);
			if (qtyEntered.compareTo(qtyEnteredScaled) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + uomToID
						+ "; QtyEntered=" + qtyEntered + "->" + qtyEnteredScaled);
				qtyEntered = qtyEnteredScaled;
				invoiceLine.setQtyEntered(qtyEntered);

			}
			qtyInvoiced = MUOMConversion.convertToProductUOM(ctx, productID,
					uomToID, qtyEntered);

			if (qtyInvoiced == null)
			{
				qtyInvoiced = qtyEntered;
			}

			boolean conversion = qtyEntered.compareTo(qtyInvoiced) != 0;
			log.debug("qty - UOM=" + uomToID
					+ ", QtyEntered=" + qtyEntered
					+ " -> " + conversion
					+ " QtyInvoiced=" + qtyInvoiced);

			setUOMConversion(calloutField, conversion);
			invoiceLine.setQtyInvoiced(qtyInvoiced);
		}
		// QtyInvoiced changed - calculate QtyEntered (should not happen)
		else if (columnName.equals("QtyInvoiced"))
		{
			final int uomToID = invoiceLine.getC_UOM_ID();

			qtyInvoiced = invoiceLine.getQtyInvoiced();

			int precision = MProduct.get(ctx, productID).getUOMPrecision();

			final BigDecimal qtyInvoicedScaled = qtyInvoiced.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (qtyInvoiced.compareTo(qtyInvoicedScaled) != 0)
			{
				log.debug("Corrected QtyInvoiced Scale "
						+ qtyInvoiced + "->" + qtyInvoicedScaled);

				qtyInvoiced = qtyInvoicedScaled;
				invoiceLine.setQtyInvoiced(qtyInvoiced);

			}
			qtyEntered = MUOMConversion.convertFromProductUOM(ctx, productID,
					uomToID, qtyInvoiced);
			if (qtyEntered == null)
			{
				qtyEntered = qtyInvoiced;
			}

			boolean conversion = qtyInvoiced.compareTo(qtyEntered) != 0;

			log.debug("qty - UOM=" + uomToID
					+ ", QtyInvoiced=" + qtyInvoiced
					+ " -> " + conversion
					+ " QtyEntered=" + qtyEntered);
			setUOMConversion(calloutField, conversion);

			invoiceLine.setQtyEntered(qtyEntered);
		}
		//
		return "";
	}	// qty

	private static final void setUOMConversion(final ICalloutField calloutField, final boolean conversion)
	{
		calloutField.putContext(CTX_UOMConversion, conversion);
	}

	public String priceList(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);

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
