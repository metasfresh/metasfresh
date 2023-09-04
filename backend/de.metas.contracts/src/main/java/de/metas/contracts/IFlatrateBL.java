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
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.FlatrateTermRequest.FlatrateTermBillPartnerRequest;
import de.metas.contracts.FlatrateTermRequest.FlatrateTermPriceRequest;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.process.PInstanceId;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;


public interface IFlatrateBL extends ISingletonService
{
	I_C_Flatrate_Conditions getConditionsById(ConditionsId flatrateConditionsId);

	List<I_C_Flatrate_Term> retrieveTerms(
			I_C_BPartner bpartner,
			I_C_Flatrate_Conditions flatrateConditions);

	void save(@NonNull I_C_Flatrate_Term flatrateTerm);

	String beforeCompleteDataEntry(I_C_Flatrate_DataEntry dataEntry);

	/**
	 * Validates the pricing for the given term, e.g. if the flatrate product associated with the term has a price during the term's runtime.
	 *
	 * @throws AdempiereException if the pricing is not OK
	 */
	void validatePricing(I_C_Flatrate_Term fc);

	/**
	 * Retrieves data entries with the given term and uom and whose periods are between the given periodFrom and periodTo (inclusive!). Checks if they have been completed and fully invoiced
	 *
	 * @param errors the method adds a user friendly error message to this list for every data entry that is not yet ready.
	 * @return the retrieved data entries or <code>null</code> if they are not yet completed or fully invoiced.
	 */
	List<I_C_Flatrate_DataEntry> retrieveAndCheckInvoicingEntries(
			I_C_Flatrate_Term flatrateTerm,
			LocalDateAndOrgId startDate,
			LocalDateAndOrgId endDate,
			I_C_UOM uom,
			List<String> errors);

	/**
	 * @param flatrateTerm the term under which the entries are created
	 */
	void createDataEntriesForTerm(I_C_Flatrate_Term flatrateTerm);

	/**
	 * Updates various fields of the given entry, all based of the entry's current Qty_Reported and ActualQty values
	 */
	void updateEntry(I_C_Flatrate_DataEntry dataEntry);

	void updateFlatrateTermProductAndPrice(@NonNull FlatrateTermPriceRequest request);

	void updateFlatrateTermBillBPartner(FlatrateTermBillPartnerRequest request);

	I_C_Flatrate_Term getById(@NonNull FlatrateTermId flatrateTermId);

	ImmutableList<I_C_Flatrate_Term> retrieveNextFlatrateTerms(@NonNull I_C_Flatrate_Term term);

	boolean isModularContractInProgress(@NonNull ModularFlatrateTermQuery query);

	@NonNull Stream<FlatrateTermId> streamModularFlatrateTermIdsByQuery(@NonNull ModularFlatrateTermQuery query);

	FlatrateTermId getInterimContractIdByModularContractIdAndDate(@NonNull FlatrateTermId modularFlatrateTermId, @NonNull Instant date);

	/**
	 * term to extend
	 * forceExtend - will create a new term, even if the given <code>term</code> has <code>IsAutoRenew='N'</code>
	 * forceComplete - will complete a new term (if one has been created), even if it has <code>IsAutoComplete='N'</code>
	 */
	@Builder(toBuilder = true)
	@Getter
	class ContractExtendingRequest
	{
		private @NonNull final I_C_Flatrate_Term contract;
		private final boolean forceExtend;
		private final Boolean forceComplete;
		private final Timestamp nextTermStartDate;
		private final PInstanceId AD_PInstance_ID;
	}

	/**
	 * Create a new flatrate term using the given term as template. The new term's C_Year will be the year after the
	 * given term's C_Year.
	 */
	void extendContractAndNotifyUser(ContractExtendingRequest context);

	/**
	 * Updates the <code>NoticeDate</code> and <code>EndDate</code> dates of the given term, using the term's values such as <code>StartDate</code>, as well as the {@link I_C_Flatrate_Transition}
	 * associated with the term.
	 *
	 * It is assume that the term is not completed.
	 */
	void updateNoticeDateAndEndDate(I_C_Flatrate_Term term);

	I_C_DocType getDocTypeFor(I_C_Flatrate_Term term);

	WarehouseId getWarehouseId(I_C_Flatrate_Term term);

	/**
	 * Copy relevant columns from {@link I_C_Flatrate_Conditions} to given <code>term</code>.
	 */
	void updateFromConditions(I_C_Flatrate_Term term);

	/**
	 * Updates the corresponding {@link I_C_Flatrate_DataEntry#COLUMNNAME_ActualQty} when a M_InOutLine is added/changed/deleted.
	 *
	 * @param product used (together with <code>inOutLine</code>) to look up the data entry record to update.
	 * @param qty the qty to add to or remove from the data entry's <code>ActualQty</code> value
	 * @param inOutLine this inout line's header (M_InOut) is used to get the C_BPartner_ID and MovementDate, which in turn are used to get the {@link I_C_Flatrate_DataEntry} which shall be updates.
	 *            If there is no such entry, the method does nothing.
	 * @param substract if <code>true</code> then the given <code>qty</code> is added, otherwise it is subtracted.
	 */
	void updateFlatrateDataEntryQty(I_M_Product product, BigDecimal qty, I_M_InOutLine inOutLine, boolean substract);

	/**
	 * Create a new flatrate term for the given partner. If necessary, also create a {@link I_C_Flatrate_Data} record for the term.<br>
	 * <p>
	 * <b>IMPORTANT:</b> depending on the conditions type, this method may or may not work for you. It does for <code>flatrateConditions</code> which have the type <code>Refundable</code>.
	 * <p>
	 * Note: obtain a {@link de.metas.util.ILoggable} and log to it:
	 * <ul>
	 * <li>the bpartner's value</li>
	 * <li>whether a term was created</li>
	 * <li>if no term was created, it logs the reason</li>
	 * </ul>
	 * Note that as of now, the log messages are non-localized EN strings.
	 *
	 * @return the newly created and completed term; never returns <code>null</code>
	 * @throws AdempiereException in case of any error
	 */
	I_C_Flatrate_Term createTerm(CreateFlatrateTermRequest createFlatrateTermRequest);

	/**
	 * Complete given contract.
	 */
	void complete(I_C_Flatrate_Term term);

	/**
	 * Void given contract
	 */
	void voidIt(I_C_Flatrate_Term term);

	/**
	 * Return {@code true} if the given term (by virtue of its conditions-type) does not define a set of invoice candidates that "belong" to it.
	 * Examples for ICs that belong to a contract in this sense are e.g. ICs that shall trigger a refund, a commission payment or are empties (e.g. empty pallets).
	 * In all this cases, a completed term matches a set of ICs and for every given ICs, we need to make sure that max. one term matches it.
	 *
	 * Also see {@link #hasOverlappingTerms(I_C_Flatrate_Term)}.
	 */
	boolean isAllowedToOverlapWithOtherTerms(@NonNull final I_C_Flatrate_Term term);

	/**
	 * Check if there are terms for the same bPartner and org that have a time period overlapping with the given term and match with the same product or product category.
	 * <p>
	 * Note that overlapping need to be prevented for those types of terms (like refund contracts or refundable contracts) to which newly created invoice candidates need to be mapped.
	 * Overlapping is no problem for subscription contracts.
	 *
	 * Also see {@link #isAllowedToOverlapWithOtherTerms(I_C_Flatrate_Term)}.
	 */
	boolean hasOverlappingTerms(final I_C_Flatrate_Term term);

	/**
	 * Complete the given flatrate term only if it respects some conditions:
	 * 1) Has not already completed overlapping terms (until now this is the only condition. Implement here if more become needed)
	 */
	void completeIfValid(I_C_Flatrate_Term term);

	/**
	 * Return the initial contract, looping back through contracts
	 */
	I_C_Flatrate_Term getInitialFlatrateTerm(I_C_Flatrate_Term term);


	void ensureOneContractOfGivenType(I_C_Flatrate_Term term,TypeConditions targetConditions);

	/**
	 * @return {@code true} if there is at lease one term that references the given <code>ol</code> via its <code>C_OrderLine_Term_ID</code> column.
	 */
	boolean existsTermForOrderLine(I_C_OrderLine ol);

	boolean isModularContract(ConditionsId conditionsId);

	I_C_Flatrate_Term createContractForOrderLine(I_C_OrderLine orderLine);

	boolean isModularContract(@NonNull FlatrateTermId flatrateTermId);

	/**
	 * Extend the C_Flatrate_Conditions to the new year
	 */
	ConditionsId cloneConditionsToNewYear(ConditionsId conditionsId, YearId yearId);

	/**
	 * Check if the provided contract is extendable (Not a Modular Contract,...)
	 */
	boolean isExtendableContract(I_C_Flatrate_Term contract);

	Stream<I_C_Flatrate_Term> streamModularFlatrateTermsByQuery(ModularFlatrateTermQuery modularFlatrateTermQuery);
}
