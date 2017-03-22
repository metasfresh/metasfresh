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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.I_M_ProductGroup;

public interface IInvoiceCandDAO extends ISingletonService
{
	/**
	 * @param ctx
	 * @param AD_PInstance_ID
	 * @param trxName
	 *
	 * @return invoice candidate iterator ordered by {@link I_C_Invoice_Candidate#COLUMNNAME_HeaderAggregationKey}
	 * @see #retrieveInvoiceCandidates(IQueryBuilder)
	 */
	Iterator<I_C_Invoice_Candidate> retrieveIcForSelection(Properties ctx, int AD_PInstance_ID, String trxName);

	/**
	 *
	 * @param queryBuilder
	 * @return invoice candidate iterator ordered by {@link I_C_Invoice_Candidate#COLUMNNAME_HeaderAggregationKey}
	 */
	<T extends I_C_Invoice_Candidate> Iterator<T> retrieveInvoiceCandidates(IQueryBuilder<T> queryBuilder);

	List<I_C_Invoice_Candidate> retrieveIcForIl(I_C_InvoiceLine invoiceLine);

	List<I_C_Invoice_Candidate> fetchInvoiceCandidates(Properties ctx, String tableName, int recordId, String trxName);

	/**
	 * Returns those invoice candidates that have been tagged to be recomputed/updated by the given <code>recomputeTag</code>.
	 *
	 * This method ALWAYS return non-manual candidates first in the list.
	 *
	 * @param recomputeTag
	 * @param trxName
	 * @return
	 */
	Iterator<I_C_Invoice_Candidate> fetchInvalidInvoiceCandidates(Properties ctx, InvoiceCandRecomputeTag recomputeTag, String trxName);

	/**
	 * @return new unique recompute tag
	 */
	InvoiceCandRecomputeTag generateNewRecomputeTag();

	/**
	 * @return new tagger used to tag invoice candidates which were scheduled to be recomputed.
	 */
	IInvoiceCandRecomputeTagger tagToRecompute();

	boolean hasInvalidInvoiceCandidatesForTag(final InvoiceCandRecomputeTag tag);

	List<I_C_InvoiceLine> retrieveIlForIc(I_C_Invoice_Candidate invoiceCand);

	List<I_C_Invoice_Line_Alloc> retrieveIlaForIc(I_C_Invoice_Candidate invoiceCand);

	List<I_C_Invoice_Line_Alloc> retrieveIlaForIl(I_C_InvoiceLine il);

	I_C_Invoice_Line_Alloc retrieveIlaForIcAndIl(I_C_Invoice_Candidate invoiceCand, org.compiere.model.I_C_InvoiceLine invoiceLine);

	List<I_C_Invoice_Candidate> retrieveForBillPartner(I_C_BPartner bpartner);

	/**
	 * Loads those invoice candidates
	 * <ul>
	 * <li>whose Bill_BPartner references he given invoiceSchedule and
	 * <li>that have their InvoiceRule_Override/InvoiceRule_Override set to 'S'
	 *
	 * @param invoiceSchedule
	 * @return
	 */
	List<I_C_Invoice_Candidate> retrieveForInvoiceSchedule(I_C_InvoiceSchedule invoiceSchedule);

	/**
	 * Returns all ICs that have the given <code>headerAggregationKey</code>.
	 *
	 * @param ctx
	 * @param headerAggregationKey
	 * @param trxName
	 * @return
	 */
	Iterator<I_C_Invoice_Candidate> retrieveForHeaderAggregationKey(Properties ctx, String headerAggregationKey, String trxName);

	/**
	 * Invalidates the invoice candidates identified by given query.
	 *
	 * @param icQueryBuilder
	 */
	void invalidateCandsFor(IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder);

	/**
	 * Invalidates the invoice candidates identified by given query.
	 *
	 * @param icQuery
	 */
	void invalidateCandsFor(IQuery<I_C_Invoice_Candidate> icQuery);

	/**
	 * Invalidates just the given candidate. If the given <code>ic</code> has an IC <= 0, the method does nothing.
	 *
	 * @param ic
	 */
	void invalidateCand(I_C_Invoice_Candidate ic);

	/**
	 * Invalidates the given collection of invoice candidates. Note that for more than one candidate, this method is more efficient than repeated calls of
	 * {@link #invalidateCand(I_C_Invoice_Candidate)}
	 *
	 * @param ics
	 * @param trxName
	 */
	void invalidateCands(Collection<I_C_Invoice_Candidate> ics, String trxName);

	void invalidateAllCands(Properties ctx, String get_TrxName);

	/**
	 * Invalidates all candidates that have the same <code>(AD_Table_ID, Record_ID)</code> reference.
	 *
	 * @param ic
	 * @throws AdempiereException if the invoice candidate does not have the AD_Table_ID/Record_ID set
	 */
	void invalidateCandsWithSameReference(I_C_Invoice_Candidate ic);

	void invalidateCandsForProductGroup(I_M_ProductGroup pg);

	/**
	 * Invalidates all ICs that have the given <code>headerAggregationKey</code>.
	 *
	 * @param ctx
	 * @param headerAggregationKey
	 * @param trxName
	 */
	void invalidateCandsForHeaderAggregationKey(Properties ctx, String headerAggregationKey, String trxName);

	/**
	 * Invalidates all ICs that have the given <code>Bill_BPartner_ID</code> and have their effective invoice rule set to <code>KundenintervallNachLieferung</code>.
	 *
	 * @param bpartner
	 */
	void invalidateCandsForBPartnerInvoiceRule(I_C_BPartner bpartner);

	/**
	 * Invalidates all ICs that have the given <code>Bill_BPartner_ID</code>.
	 *
	 * @param bpartner
	 */
	void invalidateCandsForBPartner(I_C_BPartner bpartner);

	// @Deprecated
	// void invalidateCandsForBPartnerAllowConsolidate(I_C_BPartner bPartner);

	/**
	 * Loads the invoice candidates whose <code>AD_Table_ID</code> and <code>Record_ID</code> columns match the given model.
	 */
	List<I_C_Invoice_Candidate> retrieveReferencing(Object model);

	/**
	 * Updates <code>dateInvoiced</code> of candidates from selection.
	 *
	 * @param dateInvoiced new value to be set
	 * @param AD_Pinstance_ID id of the <code>T_Selection</code> containing the candidates that shall be updated.
	 * @param trxName
	 */
	void updateDateInvoiced(Timestamp dateInvoiced, int AD_Pinstance_ID, String trxName);

	/**
	 * Similar to {@link #updateDateInvoiced(Timestamp, int, String)}, but updates the <code>DateAcct</code> column
	 *
	 * @param p_DateInvoiced
	 * @param ad_PInstance_ID
	 * @param trxName
	 * @task 08437
	 */
	void updateDateAcct(Timestamp dateAcct, int ad_PInstance_ID, String trxName);

	/**
	 * Similar to {@link #updateDateInvoiced(Timestamp, int, String)}, but updates the <code>POReference</code> column
	 *
	 * @param poReference
	 * @param ad_PInstance_ID
	 * @param trxName
	 */
	void updatePOReference(String poReference, int ad_PInstance_ID, String trxName);

	/**
	 * Mass-update a given invoice candidate column.
	 *
	 * If there were any changes, those invoice candidates will be invalidated.
	 *
	 * @param invoiceCandidateColumnName {@link I_C_Invoice_Candidate}'s column to update
	 * @param value value to set (you can also use {@link ModelColumnNameValue})
	 * @param updateOnlyIfNull if true then it will update only if column value is null (not set)
	 * @param selectionId invoice candidates selection (AD_PInstance_ID)
	 * @param trxName
	 */
	<ValueType> void updateColumnForSelection(String invoiceCandidateColumnName, ValueType value, boolean updateOnlyIfNull, int selectionId, String trxName);

	/**
	 * Gets the sum of all {@link I_C_Invoice_Candidate#COLUMNNAME_NetAmtToInvoice} values of the invoice candidates that have the given bPartner and are invoiceable before or at the given date. The
	 * amounts are converted to the currency which is set in the accounting schema of the bPartner's clients AD_ClientInfo.
	 *
	 */
	BigDecimal retrieveInvoicableAmount(I_C_BPartner billBPartner, Timestamp date);

	BigDecimal retrieveInvoicableAmount(Properties ctx, IInvoiceCandidateQuery query, int targetCurrencyId, int adClientId, int adOrgId, String amountColumnName, String trxName);

	/**
	 * Creates a new {@link IInvoiceCandidateQuery} instance
	 *
	 * @return
	 */
	IInvoiceCandidateQuery newInvoiceCandidateQuery();

	List<I_M_InOutLine> retrieveInOutLines(Properties ctx, int C_OrderLine_ID, String trxName);

	/**
	 * Retrieve all invoices which have an invoice candidate for given tableName/recordId. We can select unpaid invoices only or all invoices.
	 *
	 * @param ctx
	 * @param tableName
	 * @param recordId
	 * @param onlyUnpaid
	 * @param trxName
	 * @return map of C_Invoice_ID to {@link I_C_Invoice} objects
	 */
	<T extends org.compiere.model.I_C_Invoice> Map<Integer, T> retrieveInvoices(Properties ctx, String tableName, int recordId, Class<T> clazz, boolean onlyUnpaid, String trxName);

	List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInvoiceCandidate(I_C_Invoice_Candidate invoiceCandidate);

	/**
	 *
	 * @param invoiceCandidate
	 * @return also returns inactive records (intended use is for deletion)
	 */
	List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInvoiceCandidateInclInactive(I_C_Invoice_Candidate invoiceCandidate);

	/**
	 *
	 * @param inOutLine
	 * @return also returns inactive records (intended use is for deletion)
	 */
	List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInOutLineInclInactive(I_M_InOutLine inOutLine);

	/**
	 * Retrieves those invoice candidates that belong to the given <code>inOutLine</code>.
	 *
	 * @param inOutLine
	 * @see #retrieveInvoiceCandidatesForInOutLineQuery(I_M_InOutLine)
	 */
	List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInOutLine(I_M_InOutLine inOutLine);

	/**
	 * Retrieves those invoice candidates that belong to the given <code>inOutLine</code> by:
	 * <ul>
	 * <li>referencing it directly via <code>(AD_Table_ID, Record_ID)</code>
	 * <li>referencing it directly via inout line's order line (if any)
	 * <li>or by referencing the inOutLine's order line record.
	 * </ul>
	 *
	 * @param inOutLine
	 */
	IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInOutLineQuery(I_M_InOutLine inoutLine);

	List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForOrderLine(I_C_OrderLine orderLine);

	/**
	 * Return the active <code>M_InOutLine</code>s for the given invoice candidate.
	 * <p>
	 * <b>Important:</b> do not filter by the lines' <code>M_InOut.DocStatus</code>, i.e. also reversed lines are returned by this.
	 * <p>
	 * FIXME debug to see why c_invoicecandidate_inoutline have duplicates and take the inoutlines from there for now takes it via orderline.
	 *
	 * @param ic
	 * @param clazz
	 * @return
	 */
	<T extends org.compiere.model.I_M_InOutLine> List<T> retrieveInOutLinesForCandidate(I_C_Invoice_Candidate ic, Class<T> clazz);

	/**
	 * Checks if there exists an allocation between given invoice candidate and given receipt/shipment line.
	 *
	 * @param ic invoice candidate
	 * @param iol receipt/shipment line
	 * @return true if an allocation exists
	 * @see I_C_InvoiceCandidate_InOutLine
	 */
	boolean existsInvoiceCandidateInOutLinesForInvoiceCandidate(I_C_Invoice_Candidate ic, I_M_InOutLine iol);

	/**
	 * Checks if the given <code>ic</code> is referenced by a <code>C_Invoice_Candidate_Recompute</code> record. The check is made within the ic's transaction.<br>
	 * Please use this method instead of calling the SQL-column based {@link I_C_Invoice_Candidate#isToRecompute()}.
	 *
	 * @param ic
	 * @return
	 */
	boolean isToRecompute(I_C_Invoice_Candidate ic);

	/**
	 * Save given invoice candidate.
	 *
	 * If there were any errors encountered while saving, this method will save the errors fields directly in database.
	 *
	 * @param invoiceCandidate
	 */
	void save(I_C_Invoice_Candidate invoiceCandidate);

	/**
	 * Return all invoice candidates that have Processed='N'
	 *
	 * @param contextAware
	 * @return
	 */
	Iterator<I_C_Invoice_Candidate> retrieveNonProcessed(IContextAware contextAware);

	/**
	 * Invalidate all ICs that have the given <code>aggregation</code> as either their <code>HeaderAggregationKeyBuilder_ID</code> or <code>LineAggregationKeyBuilder_ID</code>.
	 *
	 * @param aggregation
	 */
	void invalidateCandsForAggregationBuilder(I_C_Aggregation aggregation);

	int deleteInvoiceDetails(I_C_Invoice_Candidate ic);

	/**
	 * Delete given invoice candidate AND it will advice the framework to avoid scheduling a re-create.
	 *
	 * @param ic
	 */
	void deleteAndAvoidRecreateScheduling(I_C_Invoice_Candidate ic);

	/**
	 * @param ic invoice candidate
	 * @return true if re-create scheduling shall be avoided for given invoice candidate
	 */
	boolean isAvoidRecreate(I_C_Invoice_Candidate ic);

	List<I_C_Invoice_Detail> retrieveInvoiceDetails(I_C_Invoice_Candidate ic);

	/**
	 * Add default filter for retrieving invoice candidates.
	 *
	 * Default filters until now:
	 * <li>Only retrieve invoice candidates the user and role have access to
	 *
	 * To be kept in sync with {@link #getSQLDefaultFilter(Properties)}
	 *
	 * @param queryBuilder
	 * @return
	 */
	IQueryBuilder<I_C_Invoice_Candidate> applyDefaultFilter(IQueryBuilder<I_C_Invoice_Candidate> queryBuilder);

	/**
	 * Return the default filter to be applied for retrieving invoice candidates, in String format.<br>
	 * This string is to be used in the hard-coded sql queries, in where clauses.<br>
	 * Note that this string does not start with "AND", but directly with the condition.<br>
	 *
	 * Default filters until now:
	 * <li>Only retrieve invoice candidates the user and role have access to.
	 *
	 * To be kept in sync with {{@link #applyDefaultFilter(IQueryBuilder)}
	 *
	 * @param ctx
	 * @return
	 */
	String getSQLDefaultFilter(Properties ctx);
}
