package de.metas.contracts;

/*
 * #%L
 * de.metas.contracts
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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.costing.ChargeId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

public interface IFlatrateDAO extends ISingletonService
{
	I_C_Flatrate_Term getById(final int flatrateTermId);

	List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(I_C_Flatrate_DataEntry dataEntry);

	/**
	 * Retrieves I_C_Invoice_Clearing_Alloc records that have the given invoiceCand as their <code>C_Invoice_Candidate_ID</code> OR <code>C_Invoice_Cand_ToClear_ID</code>.
	 *
	 * @param invoiceCand
	 * @return
	 */
	List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(I_C_Invoice_Candidate invoiceCand);

	/**
	 * Like {@link #retrieveClearingAllocs(I_C_Invoice_Candidate)}, but also returns inactive records.
	 *
	 * @param invoiceCand
	 * @return
	 */
	List<I_C_Invoice_Clearing_Alloc> retrieveAllClearingAllocs(I_C_Invoice_Candidate invoiceCand);

	List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(I_C_Flatrate_Term term);

	List<I_C_Flatrate_Matching> retrieveFlatrateMatchings(I_C_Flatrate_Conditions conditions);

	/**
	 * Retrieves the C_Invoice_Clearing_Alloc records with these properties
	 * <ul>
	 * <li>it references the given matching</li>
	 * <li>it's {@link I_C_Invoice_Clearing_Alloc#COLUMNNAME_C_Flatrate_DataEntry_ID} is not yet set</li>
	 * <li><code>C_Invoice_Clearing_Alloc.C_Invoice_Cand_ToClear_ID</code> references an invoice candidate whose <code>C_Invoice_Candidate_ID.DateOrdered</code> value is within the given period</li>
	 * </ul>
	 *
	 * @param period
	 * @return
	 */
	List<I_C_Invoice_Clearing_Alloc> retrieveOpenClearingAllocs(I_C_Flatrate_DataEntry dataEntry);

	List<I_C_Flatrate_DataEntry> retrieveDataEntries(I_C_Flatrate_Term flatrateTerm, Timestamp date, String dataEntryType, boolean onlyNonSim);

	/**
	 *
	 * @param fc
	 * @param dateOrdered
	 * @param typeInvoicingPeriodbased
	 * @param c_UOM
	 * @param onlyNonSim
	 * @return
	 */
	List<I_C_Flatrate_DataEntry> retrieveDataEntries(I_C_Flatrate_Conditions fc, Timestamp dateOrdered, String typeInvoicingPeriodbased, UomId uomId, boolean onlyNonSim);

	/**
	 * Retrieves the dataEntry that matches the given params and has IsSimulation=N.
	 *
	 * @param flatrateTerm
	 * @param period
	 * @param dataEntryType
	 * @param uom
	 * @return
	 */
	I_C_Flatrate_DataEntry retrieveDataEntryOrNull(I_C_Flatrate_Term flatrateTerm, I_C_Period period, String dataEntryType, I_C_UOM uom);

	I_C_Flatrate_DataEntry retrieveDataEntryOrNull(I_C_Invoice_Candidate ic);

	/**
	 * Retrieved data entries that have the given term and uom, have type = 'IP' and have a period that lies at least partially withing the given dateFrom and dateTo.
	 *
	 * @param term
	 * @param dateFrom entries to return must have
	 * @param dateTo
	 * @param uomId
	 * @return
	 */
	List<I_C_Flatrate_DataEntry> retrieveInvoicingEntries(I_C_Flatrate_Term term, Timestamp dateFrom, Timestamp dateTo, UomId uomId);

	/**
	 *
	 * @param term mandatory; the term whose data entries are returned
	 * @param dataEntryType optional; if set, then only data entries with the given type are returned
	 * @param uomId optional; if set, then only data entries with the given uom are returned
	 * @return
	 */
	List<I_C_Flatrate_DataEntry> retrieveDataEntries(I_C_Flatrate_Term term, String dataEntryType, UomId uomId);

	/**
	 * Retrieves from DB the allocation record that references the given invoice candidate (column <code>C_Invoice_Cand_ToClear_ID</code>) and the given data entry. If there is no such record it
	 * returns <code>null</code>.
	 *
	 * @param invoiceCandToClear
	 * @param dataEntry
	 * @return
	 */
	I_C_Invoice_Clearing_Alloc retrieveClearingAllocOrNull(I_C_Invoice_Candidate invoiceCandToClear, I_C_Flatrate_DataEntry dataEntry);

	List<I_C_Flatrate_Term> retrieveTerms(I_C_Flatrate_Conditions flatrateConditions);

	List<I_C_Flatrate_Term> retrieveTerms(I_C_Flatrate_Data flatrateData);

	List<I_C_Flatrate_Term> retrieveTerms(I_C_BPartner bPartner, I_C_Flatrate_Conditions flatrateConditions);

	/**
	 * This method calls {@link #retrieveTerms(Properties, int, Timestamp, int, int, int, String)} using the given invoice candidates values as parameters.
	 */
	List<I_C_Flatrate_Term> retrieveTerms(I_C_Invoice_Candidate ic);

	List<I_C_Flatrate_Term> retrieveTerms(Properties ctx, int bill_BPartner_ID, Timestamp dateOrdered, int m_Product_Category_ID, int m_Product_ID, int c_Charge_ID, String trxName);

	List<I_C_Flatrate_Term> retrieveTerms(TermsQuery query);

	@Value
	@Builder
	public static class TermsQuery
	{
		@Singular
		List<BPartnerId> billPartnerIds;

		@NonNull
		LocalDate dateOrdered;

		@Nullable
		ProductCategoryId productCategoryId;

		@Nullable
		ProductId productId;

		@Nullable
		ChargeId chargeId;
	}

	List<I_C_Flatrate_Term> retrieveTerms(Collection<FlatrateTermId> flatrateTermIds);

	default I_C_Flatrate_Term retrieveTerm(@NonNull final FlatrateTermId flatrateTermId)
	{
		return CollectionUtils.singleElement(retrieveTerms(ImmutableList.of(flatrateTermId)));
	}

	List<I_M_Product> retrieveHoldingFeeProducts(I_C_Flatrate_Conditions c_Flatrate_Conditions);

	List<I_C_UOM> retrieveUOMs(Properties ctx, I_C_Flatrate_Term flatrateTerm, String trxName);

	/**
	 * For the given <bold>simulated</bold> dataEntry, this method updates the ActualQty values of all other data Entries that have the same C_Flatrate_Term_ID, C_Period_ID and Type.
	 *
	 * @param dataEntry
	 */
	void updateQtyActualFromDataEntry(I_C_Flatrate_DataEntry dataEntry);

	/**
	 * Retrieves the flatrate term matching the given invoice candidate or <code>null</code>.<br>
	 * Basically calls {@link #retrieveTerms(Properties, int, Timestamp, int, int, int, String)}, but discards all terms that have <code>IsSimulation=Y</code>.
	 *
	 * @return the term or <code>null</code>
	 * @throws AdempiereException if there is more than one non-simulation-term
	 */
	I_C_Flatrate_Term retrieveNonSimTermOrNull(I_C_Invoice_Candidate ic);

	/**
	 * Retrieves invoice candidates that don't have an invoice candidate allocation, but should have. Method to be used when there are already invoice candidates and a term is completed (again).
	 *
	 * @param dataEntry
	 * @return
	 */
	List<I_C_Invoice_Candidate> updateCandidates(I_C_Flatrate_DataEntry dataEntry);

	List<I_C_Flatrate_DataEntry> retrieveEntries(I_C_Flatrate_Conditions fc, I_C_Flatrate_Term term, Timestamp date, String dataEntryType, UomId uomId, boolean onlyNonSim);

	/**
	 * Retrieve all active {@link I_C_Flatrate_Conditions} of context tenant.
	 *
	 * @param ctx
	 * @return
	 */
	List<I_C_Flatrate_Conditions> retrieveConditions(Properties ctx);

	int getFlatrateConditionsIdByName(String name);

	List<I_C_Flatrate_Transition> retrieveTransitionsForCalendar(I_C_Calendar calendar);

	List<I_C_Flatrate_DataEntry> retrieveDataEntriesForProduct(org.compiere.model.I_M_Product product);

	/**
	 * Retrieve data entry for a particular BP, movement date and product. More detailed, the conditions are: the selected data entry
	 * <ul>
	 * <li>belongs to a "refundable" contract which has the given <code>bPartner_ID</code> as <code>DropShip_BPartner</code> or (if the former is <code>null</code>) as <code>Bill_BPartner</code></li>
	 * <li>has a <code>C_Period</code> that covers the given <code>movementDate</code></li>
	 * <li>references the the given <code>product</code> with its <code>M_Product_DataEntry_ID</code> column</li>
	 * </ul>
	 *
	 * @param bPartner_ID
	 * @param movementDate
	 * @param product
	 * @return the matching entry or <code>null</code>
	 */
	I_C_Flatrate_DataEntry retrieveRefundableDataEntry(
			int bPartner_ID,
			Timestamp movementDate,
			org.compiere.model.I_M_Product product);

	/**
	 * Retrieves a {@link I_C_Flatrate_Data} for the given partner or creates and saves it on the fly. Note that if a record is created, it is also directly set to processed, so the anticipation is
	 * that a term is directly created.
	 *
	 * @param bPartner
	 * @return
	 */
	I_C_Flatrate_Data retriveOrCreateFlatrateData(I_C_BPartner bPartner);

	I_C_Flatrate_Term retrieveAncestorFlatrateTerm(I_C_Flatrate_Term contract);

	List<I_C_Invoice> retrieveInvoicesForFlatrateTerm(I_C_Flatrate_Term contract);
}
