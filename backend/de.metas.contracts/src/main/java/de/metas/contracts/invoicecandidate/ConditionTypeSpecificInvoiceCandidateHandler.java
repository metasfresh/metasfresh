package de.metas.contracts.invoicecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.lang.SOTrx;
import de.metas.lock.api.LockOwner;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface ConditionTypeSpecificInvoiceCandidateHandler
{
	String getConditionsType();

	default boolean isHandlerFor(@NonNull final I_C_Flatrate_Term term)
	{
		return true;
	}

	Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(@NonNull QueryLimit limit);

	default void setSpecificInvoiceCandidateValues(final I_C_Invoice_Candidate ic, final I_C_Flatrate_Term term)
	{
		//NOOP
	}

	Quantity calculateQtyEntered(I_C_Invoice_Candidate invoiceCandidateRecord);

	default Instant calculateDateOrdered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(invoiceCandidateRecord);

		final Instant dateOrdered;
		final boolean termHasAPredecessor = Services.get(IContractsDAO.class).termHasAPredecessor(term);
		if (!termHasAPredecessor)
		{
			dateOrdered = TimeUtil.asInstant(term.getStartDate());
		}
		// If the term was extended (meaning that there is a predecessor term),
		// C_Invoice_Candidate.DateOrdered should rather be the StartDate minus TermOfNoticeDuration/TermOfNoticeUnit
		else
		{
			final Instant firstDayOfNewTerm = getExtentionDateOfNewTerm(term);
			dateOrdered = firstDayOfNewTerm;
		}
		return dateOrdered;
	}

	/**
	 * For the given <code>term</code> and its <code>C_Flatrate_Transition</code> record, this method returns the term's start date minus the period specified by <code>TermOfNoticeDuration</code> and
	 * <code>TermOfNoticeUnit</code>.
	 */
	default Instant getExtentionDateOfNewTerm(@NonNull final I_C_Flatrate_Term term)
	{
		final Timestamp startDateOfTerm = term.getStartDate();

		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition transition = conditions.getC_Flatrate_Transition();
		final String termOfNoticeUnit = transition.getTermOfNoticeUnit();
		final int termOfNotice = transition.getTermOfNotice();

		final Timestamp minimumDateToStart;
		if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE.equals(termOfNoticeUnit))
		{
			minimumDateToStart = TimeUtil.addMonths(startDateOfTerm, termOfNotice * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN.equals(termOfNoticeUnit))
		{
			minimumDateToStart = TimeUtil.addWeeks(startDateOfTerm, termOfNotice * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_TagE.equals(termOfNoticeUnit))
		{
			minimumDateToStart = TimeUtil.addDays(startDateOfTerm, termOfNotice * -1);
		}
		else
		{
			Check.assume(false, "TermOfNoticeDuration " + transition.getTermOfNoticeUnit() + " doesn't exist");
			minimumDateToStart = null; // code won't be reached
		}

		return TimeUtil.asInstantNonNull(minimumDateToStart);
	}

	default PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(term.getC_TaxCategory_ID());
		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(CoalesceUtil.firstGreaterThanZero(ic.getC_VAT_Code_Override_ID(), ic.getC_VAT_Code_ID()));

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				term,
				taxCategoryId,
				term.getM_Product_ID(),
				ic.getDateOrdered(), // shipDate
				OrgId.ofRepoId(term.getAD_Org_ID()),
				null,
				CoalesceUtil.coalesceSuppliersNotNull(
						() -> ContractLocationHelper.extractDropshipLocationId(term),
						() -> ContractLocationHelper.extractBillToLocationId(term)),
				SOTrx.ofBoolean(ic.isSOTrx()),
				vatCodeId);

		return PriceAndTax.builder()
				.pricingSystemId(PricingSystemId.ofRepoId(term.getM_PricingSystem_ID()))
				.priceActual(term.getPriceActual())
				.priceEntered(term.getPriceActual()) // cg : task 04917 -- same as price actual
				.priceUOMId(UomId.ofRepoId(term.getC_UOM_ID())) // 07090: when setting a priceActual, we also need to specify a PriceUOM
				.taxCategoryId(TaxCategoryId.ofRepoId(term.getC_TaxCategory_ID()))
				.taxId(taxId)
				.taxIncluded(term.isTaxIncluded())
				.currencyId(CurrencyId.ofRepoIdOrNull(term.getC_Currency_ID()))
				.build();
	}

	default Consumer<I_C_Invoice_Candidate> getInvoiceScheduleSetterFunction(final Consumer<I_C_Invoice_Candidate> defaultImplementation)
	{
		return defaultImplementation;
	}

	@NonNull CandidatesAutoCreateMode isMissingInvoiceCandidate(@NonNull I_C_Flatrate_Term flatrateTerm);

	default List<I_C_Invoice_Candidate> createInvoiceCandidates(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final LockOwner lockOwner)
	{
		return Collections.singletonList(HandlerTools.createIcAndSetCommonFields(term));
	}

	default void postSave(@NonNull final I_C_Invoice_Candidate ic)
	{
		//NOOP
	}

	@NonNull
	default ImmutableList<Object> getRecordsToLock(@NonNull final I_C_Flatrate_Term term)
	{
		return ImmutableList.of(term);
	}
}
