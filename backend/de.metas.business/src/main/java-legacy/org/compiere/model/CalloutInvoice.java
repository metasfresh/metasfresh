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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.IDocTypeDAO;
import de.metas.document.location.adapter.DocumentLocationAdaptersRegistry;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.location.CountryId;
import de.metas.logging.MetasfreshLastError;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.security.IUserRolePermissions;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.LegacyUOMConversionUtils;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

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

	protected static final String SYS_Config_C_Invoice_SOTrx_OnlyAllowBillToDefault_Contact = "C_Invoice.SOTrx_OnlyAllowBillToDefault_Contact";

	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final DocumentLocationAdaptersRegistry documentLocationAdaptersRegistry = SpringContextHolder.instance.getBean(DocumentLocationAdaptersRegistry.class);
	
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
	 */
	public String bPartner(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);
		final int bPartnerID = invoice.getC_BPartner_ID();
		if (bPartnerID <= 0)
		{
			return NO_ERROR;
		}

		final boolean isAllowOnlyBillToDefault_Contact = Services.get(ISysConfigBL.class)
				.getBooleanValue(SYS_Config_C_Invoice_SOTrx_OnlyAllowBillToDefault_Contact, false);

		final boolean isSOTrx = invoice.isSOTrx();

		final StringBuilder sql = new StringBuilder().append("SELECT p.AD_Language,p.C_PaymentTerm_ID,"
																	 + " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
																	 + " p.SO_Description,p.IsDiscountPrinted, "
																	 + " stats." + I_C_BPartner_Stats.COLUMNNAME_SO_CreditUsed + ", "
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
																	 + " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) ");

		if (isAllowOnlyBillToDefault_Contact && isSOTrx)
		{
			sql.append(" AND c." + I_AD_User.COLUMNNAME_IsBillToContact_Default + " = 'Y'");
		}
		sql.append(
				" WHERE p.C_BPartner_ID=? AND p.IsActive='Y' "
						+ " ORDER BY c." + I_AD_User.COLUMNNAME_IsBillToContact_Default + " DESC NULLS FIRST"
						+ ";	");        // #1

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
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
				{    // get default PriceList
					priceListId = calloutField.getGlobalContextAsInt("#M_PriceList_ID");
					if (priceListId > 0)
					{
						invoice.setM_PriceList_ID(priceListId);
					}
				}

				final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

				// PaymentRule
				PaymentRule paymentRule = PaymentRule.ofNullableCode(rs.getString(isSOTrx ? "PaymentRule" : "PaymentRulePO"));
				if (paymentRule != null)
				{
					final int docTypeId = firstGreaterThanZero(invoice.getC_DocType_ID(), invoice.getC_DocTypeTarget_ID());
					if (docTypeId > 0)
					{
						final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
						final I_C_DocType invoiceDocType = docTypeDAO.getRecordById(docTypeId);

						final String docBaseType = invoiceDocType.getDocBaseType();

						// Credits are Payment Term
						if (invoiceBL.isCreditMemo(docBaseType))
						{
							paymentRule = PaymentRule.OnCredit;
						}

						// No Check/Transfer for SO_Trx
						else if (isSOTrx && paymentRule.isCheck())
						{
							paymentRule = PaymentRule.OnCredit;
						}

						invoice.setPaymentRule(paymentRule.getCode());
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
				BPartnerContactId contactUserId = BPartnerContactId.ofRepoIdOrNull(bPartnerID, rs.getInt("AD_User_ID"));

				// overwritten by InfoBP selection - works only if InfoWindow
				// was used otherwise creates error (uses last value, may belong to different BP)
				if (bPartnerID == calloutField.getTabInfoContextAsInt("C_BPartner_ID"))
				{
					final int locFromContextId = calloutField.getTabInfoContextAsInt("C_BPartner_Location_ID");
					if (locFromContextId > 0)
					{
						bpartnerLocationId = locFromContextId;
					}

					final BPartnerContactId contactFromContextId = BPartnerContactId.ofRepoIdOrNull(bPartnerID, calloutField.getTabInfoContextAsInt("AD_User_ID"));
					if (contactFromContextId != null)
					{
						contactUserId = contactFromContextId;
					}
				}

				invoice.setC_BPartner_Location_ID(bpartnerLocationId);
				invoice.setAD_User_ID(BPartnerContactId.toRepoId(contactUserId));

				// CreditAvailable
				if (isSOTrx)
				{
					final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
					final BigDecimal CreditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bPartnerID, invoice.getDateInvoiced());
					if (CreditLimit.signum() > 0)
					{
						final double creditUsed = rs.getDouble("SO_CreditUsed");
						final BigDecimal CreditAvailable = CreditLimit.subtract(BigDecimal.valueOf(creditUsed));
						if (!rs.wasNull() && CreditAvailable.signum() < 0)
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

				// #4185: take description from doctype, if exists
				invoiceBL.updateDescriptionFromDocTypeTargetId(invoice, rs.getString("SO_Description"), null);

				// IsDiscountPrinted
				final boolean isDiscountPrinted = DisplayType.toBoolean(rs.getString("IsDiscountPrinted"));
				invoice.setIsDiscountPrinted(isDiscountPrinted);

			}
		}
		catch (final SQLException e)
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
	}    // bPartner

	/**
	 * Set Payment Term.
	 * Payment Term has changed
	 */
	public String paymentTerm(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);

		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(invoice.getC_PaymentTerm_ID());
		if (paymentTermId == null)
		{
			// nothing to do
			return NO_ERROR;
		}

		final I_C_PaymentTerm paymentTerm = Services.get(IPaymentTermRepository.class).getRecordById(paymentTermId);

		// TODO: Fix in next step (refactoring: Move the apply method from MPaymentTerm to a BL)
		final MPaymentTerm pt = InterfaceWrapperHelper.getPO(paymentTerm);

		final boolean valid = pt.apply(invoice);
		invoice.setIsPayScheduleValid(valid);

		return NO_ERROR;
	}    // paymentTerm

	/**************************************************************************
	 * Invoice Line - Product.
	 * - reset C_Charge_ID / M_AttributeSetInstance_ID
	 * - PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit
	 * - UOM
	 * Calls Tax
	 */
	public String product(@NonNull final ICalloutField calloutField)
	{
		final I_C_InvoiceLine invoiceLine = calloutField.getModel(I_C_InvoiceLine.class);
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();

		final int productID = invoiceLine.getM_Product_ID();

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
		final boolean isSOTrx = invoice.isSOTrx();

		final int bpartnerID = invoice.getC_BPartner_ID();
			
		final BigDecimal qty = invoiceLine.getQtyInvoiced();
		final CountryId countryId = extractCountryIdOrNull(invoice);

		// TODO: Refactoring here in step 2: Use IPricingBL instead
		final MProductPricing pp = new MProductPricing(OrgId.ofRepoId(invoice.getAD_Org_ID()),
													   productID,
													   bpartnerID,
													   countryId,
													   qty,
													   isSOTrx);
		pp.setConvertPriceToContextUOM(false);

		//
		final int priceList_ID = invoice.getM_PriceList_ID();
		pp.setM_PriceList_ID(priceList_ID);

		final int priceListVersionID = calloutField.getTabInfoContextAsInt("M_PriceList_Version_ID");
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
		final UomId uomId = Services.get(IProductBL.class).getStockUOMId(productID);
		invoiceLine.setC_UOM_ID(uomId.getRepoId());

		//
		return tax(calloutField);
	}    // product

	/**
	 * Invoice Line - Charge.
	 * - updates PriceActual from Charge
	 * - sets PriceLimit, PriceList to zero
	 * Calles tax
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
			invoiceLine.setC_Charge_ID(-1);
			return "ChargeExclusively";
		}

		invoiceLine.setM_AttributeSetInstance(null);
		invoiceLine.setS_ResourceAssignment_ID(-1);
		invoiceLine.setC_UOM_ID(UomId.EACH.getRepoId()); // EA

		invoiceLine.setPrice_UOM_ID(UomId.EACH.getRepoId()); // 07216: Make sure price UOM is also filled.

		calloutField.putContext(CTX_DiscountSchema, false);

		final String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
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
		catch (final SQLException e)
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
	}    // charge

	/**
	 * Invoice Line - Tax.
	 * - basis: Product, Charge, BPartner Location
	 * - sets C_Tax_ID
	 * Calls Amount
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
		final BPartnerLocationAndCaptureId shipBPartnerLocationID = InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice).getBPartnerLocationAndCaptureIdIfExists().orElse(null);
		if (shipBPartnerLocationID == null)
		{
			return amt(calloutField);    //
		}

		log.debug("Ship BP_Location={}", shipBPartnerLocationID);

		final BPartnerLocationAndCaptureId billBPartnerLocationID = shipBPartnerLocationID;
		log.debug("Bill BP_Location={}", billBPartnerLocationID);

		// Dates

		final Timestamp billDate = invoice.getDateInvoiced();
		log.debug("Bill Date=" + billDate);

		final Timestamp shipDate = billDate;
		log.debug("Ship Date=" + shipDate);

		final int orgID = invoiceLine.getAD_Org_ID();
		log.debug("Org=" + orgID);

		final int warehouseID;
		if (invoice.getM_Warehouse_ID() > 0)
		{
			warehouseID = invoice.getM_Warehouse_ID();

		}
		else
		{
			warehouseID = calloutField.getGlobalContextAsInt("#M_Warehouse_ID");
		}

		log.debug("Warehouse={}", warehouseID);

		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(invoiceLine.getC_VAT_Code_ID());

		//
		final int taxID = Tax.get(ctx, productID, chargeID, billDate,
								  shipDate, orgID, warehouseID,
								  billBPartnerLocationID,
								  shipBPartnerLocationID,
								  invoice.isSOTrx(),
				vatCodeId);

		log.debug("Tax ID={}", taxID);

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
	}    // tax

	/**
	 * Invoice - Amount.
	 * - called from QtyInvoiced, PriceActual
	 * - calculates LineNetAmt
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
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(invoice.getM_PriceList_ID());
		// final int priceListVersionID = calloutField.getTabInfoContextAsInt("M_PriceList_Version_ID"); // task 08908: note that there is no such column in C_Invoice or C_InvoiceLine
		final CurrencyPrecision pricePrecision = Services.get(IInvoiceBL.class).getPricePrecision(invoice);

		// get values
		final BigDecimal qtyEntered = invoiceLine.getQtyEntered();
		BigDecimal qtyInvoiced = invoiceLine.getQtyInvoiced();

		log.debug("QtyEntered=" + qtyEntered + ", Invoiced=" + qtyInvoiced + ", UOM=" + uomToID);

		//

		BigDecimal priceEntered = invoiceLine.getPriceEntered();
		BigDecimal priceActual = invoiceLine.getPriceActual();
		// final BigDecimal Discount = invoiceLine.getDiscount();
		final BigDecimal priceLimit = invoiceLine.getPriceLimit();
		final BigDecimal priceList = invoiceLine.getPriceList();

		log.debug("PriceList=" + priceList + ", Limit=" + priceLimit + ", Precision=" + pricePrecision);
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
					qtyInvoiced = LegacyUOMConversionUtils.convertFromProductUOM(ctx, productID, uomToID, qtyEntered);
				}

				if (qtyInvoiced == null)
				{
					qtyInvoiced = qtyEntered;
				}

				// TODO: PricingBL
				final CountryId countryId = extractCountryIdOrNull(invoice);

				final MProductPricing pp = new MProductPricing(
						OrgId.ofRepoId(invoice.getAD_Org_ID()),
						productID,
						bPartnerID,
						countryId,
						qtyInvoiced,
						isSOTrx);
				pp.setPriceListId(priceListId);
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
				final BigDecimal priceStd = pp.getPriceStd();
				final Percent discount = pp.getDiscount();
				final BigDecimal priceStdMinusDiscount = pp.mkPriceStdMinusDiscount();
				log.debug("amt - QtyChanged -> PriceActual=" + priceStdMinusDiscount
								  + ", PriceEntered=" + priceEntered + ", Discount=" + discount);

				priceActual = priceStdMinusDiscount;
				invoiceLine.setPriceActual(priceActual); // 08763 align the behavior to that of order line
				invoiceLine.setDiscount(Percent.toBigDecimalOrNull(discount));	
				invoiceLine.setPriceEntered(priceStd); // 08763 align the behavior to that of order line

				calloutField.putContext(CTX_DiscountSchema, pp.isDiscountSchema());
			}
		}
		else if (columnName.equals("PriceActual"))
		{
			priceActual = (BigDecimal)value;
			priceEntered = LegacyUOMConversionUtils.convertToProductUOM(ctx, productID, uomToID, priceActual);

			if (priceEntered == null)
			{
				priceEntered = priceActual;
			}
			//
			log.debug("amt - PriceActual=" + priceActual
							  + " -> PriceEntered=" + priceEntered);

			invoiceLine.setPriceEntered(priceEntered);
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
		{
			enforce = false;
		}
		// Check Price Limit?
		if (enforce && priceLimit.doubleValue() != 0.0
				&& priceActual.compareTo(priceLimit) < 0
				&& !isManualPrice // do not change anything if the price is manual
		)
		{
			priceActual = priceLimit;
			priceEntered = LegacyUOMConversionUtils.convertToProductUOM(ctx, productID, uomToID, priceLimit);

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
		final CurrencyPrecision netPrecision = Services.get(IPriceListBL.class).getAmountPrecision(priceListId);
		BigDecimal lineNetAmt = netPrecision.roundIfNeeded(qtyInvoiced.multiply(priceActual));
		log.debug("amt = LineNetAmt={}", lineNetAmt);

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
				final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
				final de.metas.tax.api.Tax tax = taxDAO.getTaxByIdOrNull(invoiceLine.getC_Tax_ID());

				if (tax != null)
				{

					final boolean taxIncluded = isTaxIncluded(invoiceLine);
					taxAmt = tax.calculateTax(lineNetAmt, taxIncluded, pricePrecision.toInt()).getTaxAmount();
					invoiceLine.setTaxAmt(taxAmt);
				}
			}
			// Add it up
			invoiceLine.setLineNetAmt(lineNetAmt.add(taxAmt));
		}

		return "";
	}    // amt

	@Nullable
	private CountryId extractCountryIdOrNull(@NonNull final I_C_Invoice invoice)
	{
		final CountryId countryId;
		if (invoice.getC_BPartner_Location_ID() > 0)
		{
			final BPartnerLocationAndCaptureId bpLocationId =
					documentLocationAdaptersRegistry.getDocumentLocationAdapterIfHandled(invoice).get() // we know that invoice is handeled
							.getBPartnerLocationAndCaptureId();
			countryId = bPartnerBL.getCountryId(bpLocationId);
		}
		else
		{
			countryId = null;
		}
		return countryId;
	}

	/**
	 * Is Tax Included
	 *
	 * @return tax included (default: false)
	 */
	private boolean isTaxIncluded(final I_C_InvoiceLine invoiceLine)
	{
		return Services.get(IInvoiceBL.class).isTaxIncluded(invoiceLine);
	}    // isTaxIncluded

	/**
	 * Invoice Line - Quantity.
	 * - called from C_UOM_ID, QtyEntered, QtyInvoiced
	 * - enforces qty UOM relationship
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

		final int productID = invoiceLine.getM_Product_ID();

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
			final BigDecimal qtyEnteredScaled = qtyEntered.setScale(MUOM.getPrecision(ctx, uomToID), BigDecimal.ROUND_HALF_UP);

			if (qtyEntered.compareTo(qtyEnteredScaled) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + uomToID
								  + "; QtyEntered=" + qtyEntered + "->" + qtyEnteredScaled);
				qtyEntered = qtyEnteredScaled;
				invoiceLine.setQtyEntered(qtyEntered);
			}

			qtyInvoiced = LegacyUOMConversionUtils.convertToProductUOM(ctx, productID, uomToID, qtyEntered);
			if (qtyInvoiced == null)
			{
				qtyInvoiced = qtyEntered;
			}

			final boolean conversion = qtyEntered.compareTo(qtyInvoiced) != 0;

			// do not change anything if manual price
			if (!isManualPrice)
			{
				priceActual = invoiceLine.getPriceActual();

				priceEntered = LegacyUOMConversionUtils.convertToProductUOM(ctx, productID, uomToID, priceActual);

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

			final BigDecimal qtyEnteredScaled = qtyEntered.setScale(MUOM.getPrecision(ctx, uomToID), BigDecimal.ROUND_HALF_UP);
			if (qtyEntered.compareTo(qtyEnteredScaled) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM=" + uomToID
								  + "; QtyEntered=" + qtyEntered + "->" + qtyEnteredScaled);
				qtyEntered = qtyEnteredScaled;
				invoiceLine.setQtyEntered(qtyEntered);

			}
			qtyInvoiced = LegacyUOMConversionUtils.convertToProductUOM(ctx, productID, uomToID, qtyEntered);

			if (qtyInvoiced == null)
			{
				qtyInvoiced = qtyEntered;
			}

			final boolean conversion = qtyEntered.compareTo(qtyInvoiced) != 0;
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

			final int precision = MProduct.get(ctx, productID).getUOMPrecision();

			final BigDecimal qtyInvoicedScaled = qtyInvoiced.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (qtyInvoiced.compareTo(qtyInvoicedScaled) != 0)
			{
				log.debug("Corrected QtyInvoiced Scale "
								  + qtyInvoiced + "->" + qtyInvoicedScaled);

				qtyInvoiced = qtyInvoicedScaled;
				invoiceLine.setQtyInvoiced(qtyInvoiced);

			}
			qtyEntered = LegacyUOMConversionUtils.convertFromProductUOM(ctx, productID, uomToID, qtyInvoiced);
			if (qtyEntered == null)
			{
				qtyEntered = qtyInvoiced;
			}

			final boolean conversion = qtyInvoiced.compareTo(qtyEntered) != 0;

			log.debug("qty - UOM=" + uomToID
							  + ", QtyInvoiced=" + qtyInvoiced
							  + " -> " + conversion
							  + " QtyEntered=" + qtyEntered);
			setUOMConversion(calloutField, conversion);

			invoiceLine.setQtyEntered(qtyEntered);
		}
		//
		return "";
	}    // qty

	private static void setUOMConversion(final ICalloutField calloutField, final boolean conversion)
	{
		calloutField.putContext(CTX_UOMConversion, conversion);
	}

	public String priceList(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);

		if (invoice.getM_PriceList_ID() <= 0)
		{
			return NO_ERROR;
		}

		final I_M_PriceList priceList = Services.get(IPriceListDAO.class).getById(invoice.getM_PriceList_ID());

		// Tax Included
		invoice.setIsTaxIncluded(priceList.isTaxIncluded());
		// Currency
		invoice.setC_Currency_ID(priceList.getC_Currency_ID());

		return NO_ERROR;
	} // priceList

}    // CalloutInvoice
