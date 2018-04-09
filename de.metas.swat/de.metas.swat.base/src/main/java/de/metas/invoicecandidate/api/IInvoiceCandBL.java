/**
 *
 */
package de.metas.invoicecandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ILoggable;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_AttributeInstance;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IInvoiceCandBL extends ISingletonService
{
	public interface IInvoiceGenerateResult
	{
		int getInvoiceCount();

		List<I_C_Invoice> getC_Invoices();

		int getNotificationCount();

		List<I_AD_Note> getNotifications();

		String getNotificationsWhereClause();

		void addInvoice(I_C_Invoice c_Invoice);

		void addNotifications(List<I_AD_Note> notifications);

		/**
		 * @param ctx context (for translation)
		 * @return result summary (using context language)
		 */
		String getSummary(Properties ctx);
	};

	/**
	 * @return new invoice candidates updater
	 */
	IInvoiceCandInvalidUpdater updateInvalid();

	/**
	 * Start generating invoices
	 *
	 * @return invoice generator
	 */
	IInvoiceGenerator generateInvoices();

	/**
	 * Creates invoices from the given selection.
	 * <p>
	 * <b>IMPORTANT:</b> Candidates with {@link I_C_Invoice_Candidate#isError()} are ignored, even if they are part of the selection!
	 */
	IInvoiceGenerateResult generateInvoicesFromSelection(Properties ctx, int AD_PInstance_ID, boolean ignoreInvoiceSchedule, ILoggable loggable, String trxName);

	/**
	 * Creates <code>de.metas.async</code> work packages from for those invoice candidates that are selected via <code>T_Selection</code> with the given <code>AD_PInstance_ID</code>.
	 *
	 * @param adPInstance the process instance of the process that does the enqueuing.
	 * @param onlyApprovedForInvoicing if true, then enqueue only candidates flagged as {@link I_C_Invoice_Candidate#COLUMNNAME_ApprovalForInvoicing}
	 * @param consolidateApprovedICs <b>ignored, unless <code>onlyApprovedForInvoicing</code> is true </b>. If true then the system will rekey the ICs in question according to the default
	 *            C_Aggregation, assuming that is is the most basic one with the smallest possible number of items and thus the under under which the most ICs have an equal HeaderAggregationKey.
	 * @param ignoreInvoiceSchedule
	 * @param loggable <b>may not be null</b>. Use {@link org.adempiere.util.NullLoggable} if you don't have any other loggable.
	 * @param trxName
	 *
	 * @return the number of enqueued workpackages
	 */
	IInvoiceCandidateEnqueuer enqueueForInvoicing();

	/**
	 * Checks if given invoice candadidate is eligible for invoicing.
	 *
	 * It checks: Processed, IsError, DateToInvoice (if not <code>ignoreInvoiceSchedule</code>).
	 *
	 * NOTE: This method is called both when invoice candidates are enqueued for invoicing and during the actual invoicing.
	 *
	 * @param ic
	 * @param ignoreInvoiceSchedule
	 * @param loggable <b>may not be null</b>. Use {@link org.adempiere.util.NullLoggable} if you don't have any other loggable.
	 * @return true if the invoice candidate is NOT eligible and shall be skipped.
	 */
	boolean isSkipCandidateFromInvoicing(I_C_Invoice_Candidate ic, boolean ignoreInvoiceSchedule, ILoggable loggable);

	IInvoiceGenerateResult generateInvoicesFromQueue(Properties ctx);

	void setNetAmtToInvoice(I_C_Invoice_Candidate ic);

	/**
	 * Calculate actual net price.
	 *
	 * @param ic
	 */
	void setPriceActualNet(I_C_Invoice_Candidate ic);

	/**
	 * Sets the given invoice candidate's {@code PriceActual_Override} value using
	 * <ul>
	 * <li>Discount</li>
	 * <li>Discount_Override</li>
	 * <li>C_Currency_ID</li>
	 * <li>C_OrderLine.PriceEntered.</li>
	 * </ul>
	 *
	 * If the given ic is manual, if it doesn't reference a C_OrderLine or if its {@code Discount} is equal to its {@code Discount_Override}, then the method does nothing.
	 *
	 * @param ic
	 */
	void setPriceActual_Override(I_C_Invoice_Candidate ic);

	BigDecimal getPriceActual(I_C_Invoice_Candidate ic);

	BigDecimal getDiscount(I_C_Invoice_Candidate ic);

	BigDecimal getPriceEntered(I_C_Invoice_Candidate ic);

	boolean isTaxIncluded(I_C_Invoice_Candidate ic);

	/**
	 * Gets amounts precision. Taken from currency, default 2.
	 *
	 * @param ic
	 * @return precision used to calculate amounts
	 */
	int getPrecisionFromCurrency(I_C_Invoice_Candidate ic);

	/**
	 * Invalidates those invoice candidates that reference the given invoice schedule
	 *
	 * @param invoiceSchedule
	 */
	void invalidateForInvoiceSchedule(I_C_InvoiceSchedule invoiceSchedule);

	/**
	 * This method updates certain fields of the given invoice candidate. It's available for invocation from the outside for the case of 'manual' invoice candidates
	 *
	 * @param ctx
	 * @param ic the candidate whose values shall be updated. It is assumed that the candidate has <code>IsManual='Y'</code>.
	 */
	void set_QtyInvoiced_NetAmtInvoiced_Aggregation(Properties ctx, I_C_Invoice_Candidate ic);

	/**
	 *
	 * @param cand
	 * @return true if given candidate is a credit memo (i.e. is manual and price actual < 0)
	 */
	boolean isCreditMemo(I_C_Invoice_Candidate cand);

	BigDecimal calculateNetAmt(I_C_Invoice_Candidate ic);

	BigDecimal calculateNetAmt(BigDecimal qty, BigDecimal price, int currencyPrecision);

	I_C_Invoice_Candidate splitCandidate(I_C_Invoice_Candidate ic, String trxName);

	String getInvoiceRule(I_C_Invoice_Candidate ic);

	Timestamp getDateToInvoice(I_C_Invoice_Candidate ic);

	/**
	 * Determine if the candidate has been changed manually or by the background process.<br>
	 * This information is currently used by {@link de.metas.invoicecandidate.process.C_Invoice_Candidate_Update}.
	 *
	 * Used inside the invalidate code within {@link IInvoiceCandDAO}, to avoid invalidating candidates while the process validates or creates them.
	 *
	 * @param candidate
	 * @return
	 */
	boolean isUpdateProcessInProgress();

	/**
	 * Enables "update in progress" flag and returns an {@link IAutoCloseable} to put it back to off.
	 *
	 * It is important to call this method in any block where we are updating the invoice candidates and we want to avoid them to be invalidated after.
	 *
	 * @return auto closable
	 * @see #isUpdateProcessInProgress()
	 */
	IAutoCloseable setUpdateProcessInProgress();

	/**
	 * Creates initial {@link IInvoiceGenerateResult}
	 *
	 * @param shallStoreInvoices if true, a link to all invoices will be stored; if false then only some counting/aggregation info will be stored
	 * @return initial {@link IInvoiceGenerateResult} instance
	 */
	IInvoiceGenerateResult createInvoiceGenerateResult(boolean shallStoreInvoices);

	/**
	 * Retrieves or creates the invoice line allocation record for the given invoice line and invoice candidate.
	 * <p>
	 * IMPORTANT: as of now we suppose this to be the only way of creating ilas! Please don't create them yourself somewhere in the code.
	 *
	 * @param invoiceCand
	 * @param invoiceLine
	 * @param qtyInvoiced
	 * @param note may be null or empty. Use it to provide a user-friendly note that can be displayed to the customer admin/user
	 * @return returns the invoiceLine allocation that was created or updated never returns <code>null</code>
	 */
	I_C_Invoice_Line_Alloc createUpdateIla(I_C_Invoice_Candidate invoiceCand, I_C_InvoiceLine invoiceLine, BigDecimal qtyInvoiced, String note);

	void handleReversalForInvoice(org.compiere.model.I_C_Invoice invoice);

	/**
	 * Updates/Creates {@link I_C_Invoice_Line_Alloc}s for the case of an invoice (including credit memo) completion. Also makes sure that ICs are created on the fly if they are still missing.
	 *
	 * @param invoice
	 */
	void handleCompleteForInvoice(org.compiere.model.I_C_Invoice invoice);

	/**
	 * Set the {@value I_C_Invoice_Candidate#COLUMN_Processed_Calc} and <code>Processed</code> flags of the given <code>candidate</code>.<br>
	 * <code>Processed_Calc</code> can be overridden by <code>Processed_Override</code>.
	 * If it is not overridden, then the <code>Processed_Calc</code> value is copied into <code>Processed</code>.
	 * <p>
	 * The <code>Processed_Calc</code> shall be set to <code>true</code> if
	 * <ul>
	 * <li>the candidate's {@link I_C_Invoice_Candidate#COLUMN_QtyOrdered QtyOrdered} has the same amount as its {@link I_C_Invoice_Candidate#COLUMN_QtyInvoiced QtyInvoiced} <b>and</b></li>
	 * <li>there is at least one not-reversed {@link I_C_InvoiceLine} allocated to the candidate</li>
	 * </ul>
	 * The second condition is important because we might e.g. have a <code>C_OrderLine</code> with <code>QtyOrdered=0</code>, either because the order was reactivated, or because the user simply
	 * needs to document that a Qty or ZERO was ordered for a certain product. In both case don't we want the candidate to be flagged as processed.
	 * <p>
	 * Note that if <code>Processed_Override</code> is set, then its value shall be copied to <code>Processed</code>, no matter what (issue <a href="https://github.com/metasfresh/metasfresh/issues/243">#243</a>).
	 *
	 * @param candidate
	 */
	void updateProcessedFlag(I_C_Invoice_Candidate candidate);

	/**
	 * Converts the given <code>qty</code> or amount to the given <code>ic</code>'s price UOM.
	 * <p>
	 * E.g. if we have 10 pieces of 0,5kg items priced by kilogram, return 5.
	 * <p>
	 * <b>SIDE-FFECT (gh #428):</b> if the qty can't be converted due to a missing UOM conversion rule,<br>
	 * then return <code>null</code>, set the given <code>ic</code>'s <code>IsError='Y'</code> and append an info-message to the <code>ic</code>'s <code>ErrorMsg</code>.<br>
	 * But don't save the <code>ic</code>.
	 *
	 * @param qty the "raw" Qty in terms of the product UOM
	 * @param ic the invoice candidate whose price UOM, product and product UOM we use for the conversion.
	 *
	 * @return the "price" qty. If the given <code>ic</code>'s product and price UOM is the same or if either product or price UOM is not set, then return the given <code>qty</code>.
	 *
	 * @see org.adempiere.uom.api.IUOMConversionBL#convertFromProductUOM(Properties, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal)
	 */
	BigDecimal convertToPriceUOM(BigDecimal qty, I_C_Invoice_Candidate ic);

	/**
	 * Resets {@link I_C_Invoice_Candidate#COLUMNNAME_IsError} field together with some other depending fields:
	 * <ul>
	 * <li>{@link I_C_Invoice_Candidate#COLUMNNAME_AD_Note_ID}
	 * <li>{@link I_C_Invoice_Candidate#COLUMNNAME_ErrorMsg}
	 * </ul>
	 *
	 * NOTE: this method is NOT saving the invoice candidate
	 *
	 * @param ic invoice candidate
	 */
	void resetError(I_C_Invoice_Candidate ic);

	/**
	 * Flags given invoice candidate as it has errors.
	 *
	 * NOTE: this method is NOT saving the invoice candidate
	 *
	 * @param ic invoice candidate
	 * @param errorMsg error message to be set
	 * @param note error note (optional)
	 */
	void setError(I_C_Invoice_Candidate ic, String errorMsg, I_AD_Note note);

	/**
	 * See {@link #setError(I_C_Invoice_Candidate, String, I_AD_Note)}
	 *
	 * @param ic
	 * @param errorMsg
	 * @param note
	 * @param askForDeleteRegeneration error message will append request to the user asking him/her to delete invoice candidate after problem was fixed and wait for it's regeneration
	 */
	void setError(I_C_Invoice_Candidate ic, String errorMsg, I_AD_Note note, boolean askForDeleteRegeneration);

	void setError(I_C_Invoice_Candidate ic, Throwable e);

	/**
	 * Update invoice candidate's QtyWithIssues and depending cols by taking it from inout lines. Updates
	 * <ul>
	 * <li>QtyWithIssues
	 * <li>QualityDiscountPercent
	 * <li>QualityDiscountPercent_Override to <code>null</code>, if <code>QualityDiscountPercent</code> was changed
	 * <li>IsInDispute to <code>true</code> if <code>QualityDiscountPercent</code> was changed and the new value is <b>not</b> zero.
	 * </ul>
	 *
	 * NOTE: this method is NOT saving the invoice candidate.
	 *
	 * @param ic
	 * @task 06502
	 */
	void updateQtyWithIssues(I_C_Invoice_Candidate ic);

	/**
	 * Retrieve tax override if set, C_Tax otherwise
	 *
	 * @param candidate
	 * @return tax override if set, C_Tax otherwise; never return null
	 */
	I_C_Tax getTaxEffective(I_C_Invoice_Candidate candidate);

	/**
	 * Get quality percent override if set, quality percent otherwise. Never returns <code>null</code>.
	 *
	 * @param candidate
	 * @return
	 */
	BigDecimal getQualityDiscountPercentEffective(I_C_Invoice_Candidate candidate);

	/**
	 * If the given <code>candidate</code> has a currency, it shall return the currency's precision. Otherwise, it shall return <code>2</code> as fallback.
	 *
	 * @param candidate
	 * @return
	 */
	int getCurrencyPrecision(I_C_Invoice_Candidate candidate);

	/**
	 * Update the POReference of a candidate based on the POReference from the order.
	 *
	 * For both sales and purchase orders (purchases added as of https://github.com/metasfresh/metasfresh/issues/292).
	 *
	 * Candidate will not be saved.
	 *
	 * @param candidate
	 */
	void updatePOReferenceFromOrder(I_C_Invoice_Candidate candidate);

	/**
	 * For the given invoice candidate, make sure that itself and all candidates partner are invalidated, <b>if</b> the partner has a certain invoice schedule.
	 *
	 * @param ic
	 */
	void invalidateForPartnerIfInvoiceRuleDemandsIt(I_C_Invoice_Candidate ic);

	/**
	 * @return today date (without time!) to be used by invoicing BLs
	 */
	Timestamp getToday();

	/**
	 * @return current QtyToInvoice_Override or QtyToInvoice
	 */
	BigDecimal getQtyToInvoice(I_C_Invoice_Candidate ic);

	/**
	 * Set the QualityDiscountPercent_Override based on the QualityIssuePercentage from the discount schema If the value does not exist, leave the field on null
	 *
	 * Note: ic not saved
	 *
	 * @param ic
	 * @param instances
	 */
	void setQualityDiscountPercent_Override(I_C_Invoice_Candidate ic, List<I_M_AttributeInstance> instances);

	/**
	 * Precision is take from the current pricelist of the partner. If it is not found, it is taken from the currency as fallback
	 *
	 * @param ic
	 * @return
	 */
	int getPrecisionFromPricelist(I_C_Invoice_Candidate ic);

	/**
	 * Close the given invoice candidate.
	 * Closing an invoice candidate means setting its Processed_Override to Y and invalidating the invoice candidate.
	 * Also close the shipment schedules on which the invoice candidates are based
	 *
	 * @param candidate
	 */
	void closeInvoiceCandidate(I_C_Invoice_Candidate candidate);

	/**
	 * Iterate the candidates to close and close them one by one.
	 *
	 * @param candidatesToClose
	 */
	void closeInvoiceCandidates(Iterator<I_C_Invoice_Candidate> candidatesToClose);

	/**
	 * Find out if invoice candidates with flag IsToCLear are supposed to be closed
	 * The decision is made based on the System Configuration "C_Invoice_Candidate_Close_IsToClear"
	 *
	 * @return the value of the SYS_Config if found, false by default
	 */
	boolean isCloseIfIsToClear();

	/**
	 * Find out if invoice candidates that were partially invoiced are supposed to be closed
	 * The decision is bade based on the System Configuration "C_Invoice_Candidate_Close_PartiallyInvoiced"
	 *
	 * @return the value of the SYS_Config if found, false by default
	 */
	boolean isCloseIfPartiallyInvoiced();

	/**
	 * If the invoice candidates linked to an invoice have Processed_Override on true, the flag must be unset in case of invoice reversal
	 *
	 * @param invoice
	 */
	void candidates_unProcess(I_C_Invoice invoice);

	/**
	 * Close linked invoice candidates if they were partially invoiced
	 * Note: This behavior is determined by the value of the sys config "C_Invoice_Candidate_Close_PartiallyInvoice".
	 * The candidates will be closed only if the sys config is set to 'Y'
	 *
	 * @param invoice
	 */
	void closePartiallyInvoiced_InvoiceCandidates(I_C_Invoice invoice);

	/**
	 * Compute the qty (in stocking UOM) that was ordered but not yet invoiced.<br>
	 * Also account for negative ordered quantities.<br>
	 * The result does not depend on the given ic's invoice rule.
	 */
	BigDecimal computeOpenQty(I_C_Invoice_Candidate ic);

	void markInvoiceCandInDisputeForReceiptLine(I_M_InOutLine inOutLine);

}
