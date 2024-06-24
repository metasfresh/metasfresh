package de.metas.invoice.service;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.document.ICopyHandler;
import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocCopyHandler;
import de.metas.document.IDocLineCopyHandler;
import de.metas.invoice.BPartnerInvoicingInfo;
import de.metas.invoice.InvoiceCreditContext;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.impl.AdjustmentChargeCreateRequest;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.payment.PaymentRule;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.X_C_DocType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public interface IInvoiceBL extends ISingletonService
{
	I_C_Invoice getById(final InvoiceId invoiceId);

	/**
	 * Copies a given invoice
	 *
	 * @param from the copy source
	 * @param isSOTrx parameter is set as the copy's <code>IsSOtrx</code> value
	 * @param isCounterpart if <code>true</code>, then the copy shall be the counter document of <code>from</code>
	 * @param setOrderRef if true, then the copy shall reference the same C_Order that <code>from</code> references
	 * @param isSetLineInvoiceRef if true, then the copy shall reference the <code>from</code> C_Invoice
	 * @param isCopyLines if true, the invoice lines are also copied using {@link #copyLinesFrom(I_C_Invoice, I_C_Invoice, boolean, boolean, boolean, IDocLineCopyHandler)}
	 */
	org.compiere.model.I_C_Invoice copyFrom(
			org.compiere.model.I_C_Invoice from,
			Timestamp dateDoc,
			int C_DocTypeTarget_ID,
			boolean isSOTrx,
			boolean isCounterpart,
			boolean setOrderRef,
			boolean isSetLineInvoiceRef,
			boolean isCopyLines);


	/**
	 * Load and iterate the invoice lines from the given <code>fromInvoice</code> and create new invoice lines for the given <code>toInvoice</code>.
	 *
	 * @param counter if <code>true</code>, then
	 *            <ul>
	 *            <li>The <code>C_InvoiceLine.Ref_InvoiceLine_ID</code> values of both the existing and new invoice line are set to the ID of their counterpart (independent of the setInvoiceRef
	 *            parameter's value)
	 *            <li>the invoice lines of toInvoice are created using toInvoice's context, because it may have a different org etc
	 *            <li>if the respective source invoice line references an order line and that order line has a <code>Ref_OrderLine_ID</code> reference, then set that reference as the new invoice
	 *            lines's <code>C_OrderLine_ID</code>. Same for inout line and <code>Ref_InOutLine_ID</code>. This is old, code and probably assumes that <code>Ref_OrderLine_ID</code> and
	 *            <code>Ref_InOutLine_ID</code> are the analog counter docs to the newly created counter invoice line.
	 *            </ul>
	 * @param setOrderRef if <code>true</code> then set the new invoice line's <code>C_OrderLine_ID</code> to the value of the old invoice line. Otherwise, set the new line's ref to 0. <br>
	 *            <b>Important</b>: the value set as consequence of param counter = true will take precedence!
	 * @param setInvoiceRef if <code>true</code>, then set <code>C_InvoiceLine.Ref_InvoiceLine_ID</code> as described for the counter parameter (do this independent of the counter parameter's value).
	 * @param docLineCopyHandler allows copying of fields to be customized per implementation. This is e.g. used by {@link #creditInvoice(de.metas.adempiere.model.I_C_Invoice, InvoiceCreditContext)}.
	 *            May be <code>null</code>.
	 * @see #copyFrom(I_C_Invoice, Timestamp, int, boolean, boolean, boolean, boolean, boolean)
	 */
	int copyLinesFrom(I_C_Invoice fromInvoice, I_C_Invoice toInvoice, boolean counter, boolean setOrderRef, boolean setInvoiceRef,
			IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> docLineCopyHandler);

	String getSummary(I_C_Invoice invoice);

	default boolean isVendorInvoice(final String docBaseType)
	{
		return X_C_DocType.DOCBASETYPE_APInvoice.equals(docBaseType)
				|| X_C_DocType.DOCBASETYPE_APCreditMemo.equals(docBaseType);
	}

	/**
	 * @param invoice the invoice to check
	 * @return true if the given invoice is an Invoice (API or ARI)
	 */
	boolean isInvoice(@NonNull I_C_Invoice invoice);

	/**
	 * @return true if the given invoice is a CreditMemo (APC or ARC)
	 */
	boolean isCreditMemo(I_C_Invoice invoice);

	/**
	 * @return true if the given invoice DocBaseType is a CreditMemo (APC or ARC)
	 */
	boolean isCreditMemo(String docBaseType);

	/**
	 * @return <code>true</code> if the given invoice is the reversal of another invoice.
	 */
	boolean isReversal(I_C_Invoice invoice);

	/**
	 * @return true if the given invoice is a AR CreditMemo (ARC)
	 */
	boolean isARCreditMemo(I_C_Invoice invoice);

	/**
	 * Writes off the given openAmt from the given invoice.
	 *
	 * @param openAmt open amount (not absolute, the value is relative to IsSOTrx sign)
	 */
	void writeOffInvoice(I_C_Invoice invoice, BigDecimal openAmt, String description);

	/**
	 * Create a credit memo for the given invoice.
	 * <p>
	 * <b>Special handling for partially paid invoices:</b> If the given invoice to credit is already partially handled, then apply this behavior:
	 * <ul>
	 * <li>The credit memo will have one line per invoice line, each line having qty 1. We set them to 1, because the taxes are aggregated on the invoice's, not the lines' level and the credit memo
	 * needs to have the correct partial tax amounts. So if we tried to get integer qtyInvoiced, they would not be accurate. Even with non-int QtyInvoiced there would be rounding problems.
	 * <li>The credit memo's <code>GrandTotal</code> will be the given invoice's open amount.</il>
	 * <li>If the given invoice has already been partially paid/allocated, then the credit memo lines will only have the the remaining fractions of the invoice's original <code>LineNetAmt</code>
	 * values.
	 * <li>The created credit memo will always have <code>IsTaxIncluded='Y'</code>
	 * </ul>
	 * <p>
	 * Depending in the <code>completeAndAllocate</code> parameter, the credit memo will also be allocated against the invoice, so that both have <code>IsPaid='Y'</code>.
	 *
	 * @param invoice the invoice to be credited. May not be fully paid/allocated and may not be a credit memo itself
	 * @return the created credit memo
	 * @throws AdempiereException if
	 *             <ul>
	 *             <li>the given invoice is <code>null</code> or</li>
	 *             <li>the given C_Charge_ID doesn't have a valid C_Charge or</li>
	 *             <li>the given invoice is a credit memo or</li>
	 *             <li>the given
	 *             invoice is already fully paid or</li>
	 *             <li>the credit memo line's C_Tax is not tax exempt (due to the charge's tax category and/or the invoice BPartner's location)</li>
	 *             </ul>
	 */
	de.metas.adempiere.model.I_C_Invoice creditInvoice(de.metas.adempiere.model.I_C_Invoice invoice, InvoiceCreditContext creditCtx);

	/**
	 * Creates a new invoice line for the given invoice. Note that the new line is not saved.
	 */
	I_C_InvoiceLine createLine(I_C_Invoice invoice);

	/**
	 * Test Allocation (and set paid flag)
	 *
	 * @param invoice the invoice to be checked
	 * @param ignoreProcessed if true, then the change will be done even if the given <code>invoice</code> currently still have <code>Processed='N'</code>.
	 * @return true if the isPaid value was changed
	 */
	boolean testAllocation(I_C_Invoice invoice, boolean ignoreProcessed);

	/**
	 * @param docTypeTargetId invoice's document type
	 * @param dateInvoiced may be <code>null</code>
	 * @param dateAcct may be <code>null</code> (see task 08438)
	 * @return created invoice
	 */
	de.metas.adempiere.model.I_C_Invoice createInvoiceFromOrder(
			org.compiere.model.I_C_Order order,
			DocTypeId docTypeTargetId,
			LocalDate dateInvoiced,
			LocalDate dateAcct);

	void setFromOrder(I_C_Invoice invoice, I_C_Order order);

	void updateFromBPartner(I_C_Invoice invoice);

	BPartnerInvoicingInfo getBPartnerInvoicingInfo(
			@NonNull BPartnerId bpartnerId,
			@NonNull SOTrx soTrx,
			@NonNull ZonedDateTime date);

	/**
	 * Sets Target Document Type and IsSOTrx.
	 *
	 * @return true if document type found and set
	 */
	boolean setDocTypeTargetId(I_C_Invoice invoice, InvoiceDocBaseType docBaseType);

	/**
	 * Set Target Document Type based on SO flag AP/AP Invoice
	 */
	void setDocTypeTargetIdIfNotSet(I_C_Invoice invoice);

	void setDocTypeTargetIdAndUpdateDescription(I_C_Invoice invoice, int docTypeId);

	/**
	 * Sort and then renumber all invoice lines.
	 *
	 * @param step start and step
	 */
	void renumberLines(de.metas.adempiere.model.I_C_Invoice invoice, int step);

	/**
	 * Similar to {@link #renumberLines(de.metas.adempiere.model.I_C_Invoice, int)}, but in addition, leave alone lines which were flagged using {@link #setHasFixedLineNumber(I_C_InvoiceLine, boolean)}
	 * and don't assign their <code>Line</code> value to any other line.
	 */
	void renumberLines(List<I_C_InvoiceLine> lines, int step);

	/**
	 * Use {@link org.adempiere.ad.persistence.ModelDynAttributeAccessor ModelDynAttributeAccessor} to flag lines whose <code>C_InvoiceLine.Line</code> value shall not be changed by
	 * {@link #renumberLines(List, int)}.
	 */
	void setHasFixedLineNumber(I_C_InvoiceLine line, boolean value);

	/**
	 * Updates the given invoice line's <code>M_Product_ID</code>, <code>C_UOM_ID</code> and <code>M_AttributeSetInstance_ID</code>.<br>
	 * Product ID and UOM ID are set to the product and it's respective UOM or to 0 if the given <code>productId</code> is 0. The ASI ID is always set to 0.
	 * <p>
	 * Important note: what we do <b>not</b> set there is the price UOM because that one is set only together with the price.
	 */
	void setProductAndUOM(I_C_InvoiceLine invoiceLine, ProductId productId);

	/**
	 * Set the given invoiceline's QtyInvoiced, QtyEntered and QtyInvoicedInPriceUOM.
	 * This method assumes that the given invoice Line has a product (with an UOM) and a C_UOM and Price_UOM set.
	 */
	void setQtys(I_C_InvoiceLine invoiceLine, StockQtyAndUOMQty qtyInvoiced);

	void setLineNetAmt(I_C_InvoiceLine invoiceLine);

	/**
	 * Calculate Tax Amt. Assumes Line Net is calculated
	 */
	void setTaxAmt(I_C_InvoiceLine invoiceLine);

	I_C_DocType getC_DocType(I_C_Invoice invoice);

	/**
	 * @return true if invoice's DocStatus is COmpleted, CLosed or REversed.
	 */
	boolean isComplete(org.compiere.model.I_C_Invoice invoice);

	CurrencyPrecision getPricePrecision(org.compiere.model.I_C_Invoice invoice);

	CurrencyPrecision getPricePrecision(org.compiere.model.I_C_InvoiceLine invoiceLine);

	CurrencyPrecision getAmountPrecision(org.compiere.model.I_C_Invoice invoice);

	CurrencyPrecision getAmountPrecision(org.compiere.model.I_C_InvoiceLine invoiceLine);

	CurrencyPrecision getTaxPrecision(org.compiere.model.I_C_Invoice invoice);

	CurrencyPrecision getTaxPrecision(org.compiere.model.I_C_InvoiceLine invoiceLine);

	/**
	 * Creates a copy of given Invoice with C_DocType "Nachbelastung" (Adjustment Charge). The button is active just for 'ARI' docbasetypes. There can be more types of Adjustment Charges, with
	 * different DocSubTypes. For example we have: "Nachbelastung - Mengendifferenz" which copies the Invoice but sets the product prices readOnly. "Nachbelastung - Preisdifferenz" which copies the
	 * Invoice but sets the quantity read only.
	 *
	 * @return adjustmentCharge {@link de.metas.adempiere.model.I_C_Invoice}
	 */
	de.metas.adempiere.model.I_C_Invoice adjustmentCharge(AdjustmentChargeCreateRequest adjustmentChargeCreateRequest);

	/**
	 * Updates {@link I_C_InvoiceLine}'s {@link I_C_InvoiceLine#COLUMNNAME_IsPriceReadOnly IsPriceReadOnly}, {@link I_C_InvoiceLine#COLUMNNAME_IsQtyReadOnly IsQtyReadOnly} and
	 * {@link I_C_InvoiceLine#COLUMNNAME_IsOrderLineReadOnly IsOrderLineReadOnly} flags according to the invoice's {@link I_C_Invoice#COLUMNNAME_C_DocTypeTarget_ID}.<br>
	 * <br>
	 * If the target doctype is empty or if the if has a subtype other than {@link de.metas.adempiere.model.I_C_Invoice#DOC_SUBTYPE_ARI_AP},
	 * {@link de.metas.adempiere.model.I_C_Invoice#DOC_SUBTYPE_ARI_AQ}, {@link de.metas.adempiere.model.I_C_Invoice#DOC_SUBTYPE_ARC_CQ} or
	 * {@link de.metas.adempiere.model.I_C_Invoice#DOC_SUBTYPE_ARC_CR}, then both <code>IsPriceReadOnly</code> and <code>IsQtyReadOnly</code> are set to <code>false</code>.<br>
	 * <br>
	 * If the document subtype is {@code DOC_SUBTYPE_ARI_AP} or {@code DOC_SUBTYPE_ARC_CR}, then the qty shall be read-only.<br>
	 * If the document subtype is {@code DOC_SUBTYPE_ARI_AQ} or {@code DOC_SUBTYPE_ARC_CQ}, then the price shall be read-only.<br>
	 * IF (and only if!, as of now) the the document subtype is {@code DOC_SUBTYPE_ARC_CS}, then the order line shall be editable.
	 *
	 * @param invoice may not be null
	 * @param invoiceLines optional, the lines to update; if <code>null</code> or empty, then all invoice lines are updated <b>and saved</b>. Otherwise, the given lines are only updated, but not
	 *            saved.
	 */
	void updateInvoiceLineIsReadOnlyFlags(de.metas.adempiere.model.I_C_Invoice invoice, I_C_InvoiceLine... invoiceLines);

	/**
	 * If the given <code>invoiceLine</code> references a <code>C_Charge</code>, then the method returns that charge's tax category. Otherwise, it uses the pricing APO to get the tax category that is
	 * referenced from the invoice line's pricing data (i.e. <code>M_ProductPrice</code> record).
	 */
	TaxCategoryId getTaxCategoryId(I_C_InvoiceLine invoiceLine);

	/**
	 * Basically this method delegated to {@link ICopyHandlerBL#registerCopyHandler(Class, IQueryFilter, ICopyHandler)}, but makes sure that the correct types are used.
	 */
	void registerCopyHandler(
			IQueryFilter<ImmutablePair<I_C_Invoice, I_C_Invoice>> filter,
			IDocCopyHandler<I_C_Invoice, org.compiere.model.I_C_InvoiceLine> copyHandler);

	/**
	 * Basically this method delegates to {@link ICopyHandlerBL#registerCopyHandler(Class, IQueryFilter, ICopyHandler)}, but makes sure that the correct types are used.
	 * If this proves to be usefull, we can add similar methods e.g. to <code>IOrderBL</code>.
	 */
	void registerLineCopyHandler(
			IQueryFilter<ImmutablePair<org.compiere.model.I_C_InvoiceLine, org.compiere.model.I_C_InvoiceLine>> filter,
			IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> copyhandler);

	/**
	 * Calls {@link #isTaxIncluded(I_C_Invoice, Tax)} for the given <code>invoiceLine</code>'s <code>C_Invoice</code> and <code>C_Tax</code>.
	 */
	boolean isTaxIncluded(org.compiere.model.I_C_InvoiceLine invoiceLine);

	/**
	 * Is Tax Included in Amount.
	 *
	 * @return if the given <code>tax</code> is not <code>null</code> and if is has {@link I_C_Tax#isWholeTax()} equals <code>true</code>, then true is returned. Otherwise, the given invoice's
	 */
	boolean isTaxIncluded(I_C_Invoice invoice, Tax tax);

	/**
	 * Supposed to be called if an invoice is reversed. Iterate the given invoice's lines, iterate each line's <code>M_MatchInv</code> and create a reversal M_Matchinv that references the respective
	 * reversal invoice line.
	 */
	void handleReversalForInvoice(I_C_Invoice invoice);

	/**
	 * Allocate parent invoice against it's credit memo
	 */
	void allocateCreditMemo(de.metas.adempiere.model.I_C_Invoice invoice, de.metas.adempiere.model.I_C_Invoice creditMemo, BigDecimal openAmt);

	/**
	 * Decide if the given invoice is an Adjustment Charge
	 */
	boolean isAdjustmentCharge(I_C_Invoice invoice);

	/**
	 * Decide if the given doctype is of an Adjustment Charge
	 */
	boolean isAdjustmentCharge(I_C_DocType docType);

	/**
	 * Value set in the system configuration "de.metas.invoice.C_Invoice_PaymentRule".
	 *
	 * @return the value if set, {@code X_C_Invoice.PAYMENTRULE_OnCredit} otherwise
	 */
	PaymentRule getDefaultPaymentRule();

	void updateDescriptionFromDocTypeTargetId(I_C_Invoice invoice, String defaultDescription, String defaultDocumentNote);

	/**
	 * If a line has C_UOM_ID or Price_UOM_ID empty:
	 * <p>
	 * - set C_UOM_ID to M_Product.C_UOM_ID
	 * <p>
	 * - set Price_UOM_ID to C_InvoiceLine.C_UOM_ID
	 */
	void ensureUOMsAreNotNull(@NonNull InvoiceId invoiceId);

	/**
	 * @param discountAmt - the value is not AP corrected. The correction is done inside this function
	 */
	void discountInvoice(@NonNull I_C_Invoice invoice, @NonNull Amount discountAmt, @NonNull Timestamp date);

	void setInvoiceLineTaxes(@NonNull de.metas.adempiere.model.I_C_Invoice invoice);

	CountryId getFromCountryId(@NonNull I_C_Invoice invoice, @NonNull org.compiere.model.I_C_InvoiceLine invoiceLine);

	String getLocationEmail(InvoiceId invoiceId);
}
