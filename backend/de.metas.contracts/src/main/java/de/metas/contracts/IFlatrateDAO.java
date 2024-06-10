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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.impl.FlatrateTermOverlapCriteria;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.costing.ChargeId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

public interface IFlatrateDAO extends ISingletonService
{
	I_C_Flatrate_Term getById(final int flatrateTermId);

	List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(@NonNull I_C_Flatrate_DataEntry dataEntry);

	/**
	 * Retrieves I_C_Invoice_Clearing_Alloc records that have the given invoiceCand as their <code>C_Invoice_Candidate_ID</code> OR <code>C_Invoice_Cand_ToClear_ID</code>.
	 */
	List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(@NonNull I_C_Invoice_Candidate invoiceCand);

	/**
	 * Like {@link #retrieveClearingAllocs(I_C_Invoice_Candidate)}, but also returns inactive records.
	 */
	List<I_C_Invoice_Clearing_Alloc> retrieveAllClearingAllocs(@NonNull I_C_Invoice_Candidate invoiceCand);

	List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(@NonNull I_C_Flatrate_Term term);

	List<I_C_Flatrate_Matching> retrieveFlatrateMatchings(I_C_Flatrate_Conditions conditions);

	/**
	 * Retrieves the C_Invoice_Clearing_Alloc records with these properties
	 * <ul>
	 * <li>it references the given matching</li>
	 * <li>it's {@link I_C_Invoice_Clearing_Alloc#COLUMNNAME_C_Flatrate_DataEntry_ID} is not yet set</li>
	 * <li><code>C_Invoice_Clearing_Alloc.C_Invoice_Cand_ToClear_ID</code> references an invoice candidate whose <code>C_Invoice_Candidate_ID.DateOrdered</code> value is within the given period</li>
	 * </ul>
	 */
	List<I_C_Invoice_Clearing_Alloc> retrieveOpenClearingAllocs(I_C_Flatrate_DataEntry dataEntry);

	List<I_C_Flatrate_DataEntry> retrieveDataEntries(I_C_Flatrate_Term flatrateTerm, Timestamp date, String dataEntryType, boolean onlyNonSim);

	List<I_C_Flatrate_DataEntry> retrieveDataEntries(I_C_Flatrate_Conditions fc, Timestamp dateOrdered, String typeInvoicingPeriodbased, UomId uomId, boolean onlyNonSim);

	/**
	 * Retrieves the dataEntry that matches the given params and has IsSimulation=N.
	 */
	I_C_Flatrate_DataEntry retrieveDataEntryOrNull(I_C_Flatrate_Term flatrateTerm, I_C_Period period, String dataEntryType, @NonNull UomId uomId);

	I_C_Flatrate_DataEntry retrieveDataEntryOrNull(I_C_Invoice_Candidate ic);

	/**
	 * Retrieved data entries that have the given term and uom, have type = 'IP' and have a period that lies at least partially withing the given dateFrom and dateTo.
	 */
	List<I_C_Flatrate_DataEntry> retrieveInvoicingEntries(I_C_Flatrate_Term term, LocalDateAndOrgId dateFrom, LocalDateAndOrgId dateTo, UomId uomId);

	/**
	 * @param term          mandatory; the term whose data entries are returned
	 * @param dataEntryType optional; if set, then only data entries with the given type are returned
	 * @param uomId         optional; if set, then only data entries with the given uom are returned
	 */
	List<I_C_Flatrate_DataEntry> retrieveDataEntries(I_C_Flatrate_Term term, String dataEntryType, UomId uomId);

	/**
	 * Retrieves from DB the allocation record that references the given invoice candidate (column <code>C_Invoice_Cand_ToClear_ID</code>) and the given data entry. If there is no such record it
	 * returns <code>null</code>.
	 */
	I_C_Invoice_Clearing_Alloc retrieveClearingAllocOrNull(I_C_Invoice_Candidate invoiceCandToClear, I_C_Flatrate_DataEntry dataEntry);

	List<I_C_Flatrate_Term> retrieveTerms(I_C_Flatrate_Conditions flatrateConditions);

	Iterable<I_C_Flatrate_Term> retrieveTerms(@NonNull FlatrateDataId flatrateDataId);

	ImmutableList<I_C_Flatrate_Term> retrieveTermsAsList(@NonNull FlatrateDataId flatrateDataId);

	List<I_C_Flatrate_Term> retrieveTerms(I_C_Flatrate_Data flatrateData);

	List<I_C_Flatrate_Term> retrieveTerms(I_C_BPartner bPartner, I_C_Flatrate_Conditions flatrateConditions);

	I_C_Flatrate_Term getById(@NonNull FlatrateTermId flatrateTermId);

	@NonNull
	ImmutableMap<FlatrateTermId, I_C_Flatrate_Term> getByIds(@NonNull Set<FlatrateTermId> flatrateTermIds);

	/**
	 * This method calls {@link #retrieveTerms(Properties, OrgId, int, Timestamp, int, int, int, String)} using the given invoice candidates values as parameters.
	 */
	List<I_C_Flatrate_Term> retrieveTerms(I_C_Invoice_Candidate ic);

	/**
	 * Note: Terms that have the Type_Conditions FlatFee, HoldingFee or Subscription are *not* returned.
	 */
	List<I_C_Flatrate_Term> retrieveTerms(Properties ctx, @NonNull OrgId orgId, int bill_BPartner_ID, Timestamp dateOrdered, int m_Product_Category_ID, int m_Product_ID, int c_Charge_ID, String trxName);

	/**
	 * Note: Terms that have the Type_Conditions FlatFee, HoldingFee or Subscription are *not* returned.
	 */
	List<I_C_Flatrate_Term> retrieveTerms(TermsQuery query);

	I_C_Flatrate_Conditions getConditionsById(ConditionsId flatrateConditionsId);

	void save(@NonNull I_C_Flatrate_Term flatrateTerm);

	I_C_Invoice_Candidate retrieveInvoiceCandidate(I_C_Flatrate_Term term);

	boolean hasOverlappingTerms(FlatrateTermOverlapCriteria flatrateTermOverlapCriteria);

	Set<FlatrateTermId> retrieveAllRunningSubscriptionIds(
			@NonNull BPartnerId bPartnerId,
			@NonNull Instant date,
			@NonNull OrgId orgId);

	boolean bpartnerHasExistingRunningTerms(@NonNull final I_C_Flatrate_Term flatrateTerm);

	I_C_Flatrate_Term retrieveFirstFlatrateTerm(@NonNull I_C_Invoice invoice);

	boolean isExistsModularOrInterimContract(@NonNull IQueryFilter<I_C_Flatrate_Term> flatrateTermFilter);

	boolean isDefinitiveInvoiceableModularContractExists(@NonNull IQueryFilter<I_C_Flatrate_Term> filter);

	@Value
	@Builder
	class TermsQuery
	{
		@NonNull
		OrgId orgId;

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

	/**
	 * For the given <bold>simulated</bold> dataEntry, this method updates the ActualQty values of all other data Entries that have the same C_Flatrate_Term_ID, C_Period_ID and Type.
	 */
	void updateQtyActualFromDataEntry(I_C_Flatrate_DataEntry dataEntry);

	/**
	 * Retrieves the flatrate term matching the given invoice candidate (or {@code code>}) by using {@link I_C_Flatrate_Matching} Records.<br>
	 * Basically calls {@link #retrieveTerms(Properties, OrgId, int, Timestamp, int, int, int, String)}, but discards all terms that have <code>IsSimulation=Y</code>.
	 *
	 * @return the term or <code>null</code>
	 * @throws AdempiereException if there is more than one non-simulation-term
	 */
	I_C_Flatrate_Term retrieveNonSimTermOrNull(I_C_Invoice_Candidate ic);

	/**
	 * Retrieves invoice candidates that don't have an invoice candidate allocation, but should have. Method to be used when there are already invoice candidates and a term is completed (again).
	 */
	List<I_C_Invoice_Candidate> updateCandidates(I_C_Flatrate_DataEntry dataEntry);

	List<I_C_Flatrate_DataEntry> retrieveEntries(I_C_Flatrate_Conditions fc, I_C_Flatrate_Term term, Timestamp date, String dataEntryType, UomId uomId, boolean onlyNonSim);

	/**
	 * Retrieve all active {@link I_C_Flatrate_Conditions} of context tenant.
	 */
	List<I_C_Flatrate_Conditions> retrieveConditions(Properties ctx);

	@NonNull
	ImmutableMap<ConditionsId, I_C_Flatrate_Conditions> getTermConditionsByIds(@NonNull Set<ConditionsId> conditionsIds);

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
	 * @return the matching entry or <code>null</code>
	 */
	I_C_Flatrate_DataEntry retrieveRefundableDataEntry(
			int bPartner_ID,
			Timestamp movementDate,
			org.compiere.model.I_M_Product product);

	/**
	 * Retrieves a {@link I_C_Flatrate_Data} for the given partner or creates and saves it on the fly. Note that if a record is created, it is also directly set to processed, so the anticipation is
	 * that a term is directly created.
	 */
	I_C_Flatrate_Data retrieveOrCreateFlatrateData(I_C_BPartner bPartner);

	I_C_Flatrate_Term retrieveAncestorFlatrateTerm(I_C_Flatrate_Term contract);

	List<I_C_Invoice> retrieveInvoicesForFlatrateTerm(I_C_Flatrate_Term contract);

	I_C_Flatrate_Conditions getConditionsById(int flatrateConditionsId);

	List<I_C_Flatrate_Term> retrieveTerms(BPartnerId bPartnerId, OrgId orgId, TypeConditions typeConditions);

	@NonNull
	Optional<I_C_Flatrate_Term> getByOrderLineId(@NonNull OrderLineId orderLineId, @NonNull TypeConditions typeConditions);

	@NonNull
	ImmutableList<I_C_Flatrate_Term> getModularFlatrateTermsByQuery(@NonNull ModularFlatrateTermQuery modularFlatrateTermQuery);

	IQuery<I_C_Flatrate_Term> createInterimContractQuery(@NonNull IQueryFilter<I_C_Flatrate_Term> contractFilter);

	Stream<I_C_Flatrate_Term> stream(@NonNull IQueryFilter<I_C_Flatrate_Term> filter);

	ImmutableList<I_C_Flatrate_Term> retrieveRunningTermsForDropShipPartnerAndProductCategory(@NonNull BPartnerId bPartnerId, @NonNull ProductCategoryId productCategoryId);

	@NonNull
	Stream<I_C_Flatrate_Conditions> streamCompletedConditionsBy(@NonNull ModularContractSettingsId modularContractSettingsId);

	ImmutableSet<FlatrateTermId> getReadyForDefinitiveInvoicingModularContractIds(@NonNull IQueryFilter<I_C_Flatrate_Term> queryFilter);

	void prepareForDefinitiveInvoice(@NonNull Collection<FlatrateTermId> contractIds);

	boolean isInvoiceableModularContractExists(@NonNull IQueryFilter<I_C_Flatrate_Term> filter);

	@NonNull
	ImmutableSet<FlatrateTermId> getReadyForFinalInvoicingModularContractIds(@NonNull IQueryFilter<I_C_Flatrate_Term> queryFilter);
}
