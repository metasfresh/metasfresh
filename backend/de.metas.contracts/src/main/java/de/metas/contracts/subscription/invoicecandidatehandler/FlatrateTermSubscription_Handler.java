package de.metas.contracts.subscription.invoicecandidatehandler;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.function.Consumer;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.contracts.IContractsDAO;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

public class FlatrateTermSubscription_Handler implements ConditionTypeSpecificInvoiceCandidateHandler
{
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(final int limit)
	{
		return Services.get(IContractsDAO.class)
				.retrieveSubscriptionTermsWithMissingCandidates(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription, limit)
				.iterator();
	}

	@Override
	public boolean isMissingInvoiceCandidate(final I_C_Flatrate_Term flatrateTerm)
	{
		return Services.get(IContractsDAO.class)
				.createTermWithMissingCandidateQueryBuilder(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription, true /* ignoreDateFilters */)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, flatrateTerm.getC_Flatrate_Term_ID())
				.create()
				.anyMatch();
	}

	@Override
	public void setSpecificInvoiceCandidateValues(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final I_C_Flatrate_Term term)
	{
		ic.setPriceActual(term.getPriceActual()); // TODO document and make sure there is the correct value
		ic.setPrice_UOM_ID(term.getC_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		ic.setPriceEntered(term.getPriceActual()); // cg : task 04917 -- same as price actual

		final boolean isSOTrx = true;

		// 05265
		ic.setIsSOTrx(isSOTrx);

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(term.getC_TaxCategory_ID());

		final BigDecimal qty = Services.get(IContractsDAO.class).retrieveSubscriptionProgressQtyForTerm(term);
		ic.setQtyOrdered(qty);

		final int taxId = Services.get(ITaxBL.class).getTax(
				Env.getCtx(),
				term,
				taxCategoryId,
				term.getM_Product_ID(),
				ic.getDateOrdered(), // shipDate
				OrgId.ofRepoId(term.getAD_Org_ID()),
				(WarehouseId)null,
				CoalesceUtil.firstGreaterThanZero(term.getDropShip_Location_ID(), term.getBill_Location_ID()), // ship location id
				isSOTrx);
		ic.setC_Tax_ID(taxId);
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);
		return PriceAndTax.builder()
				.priceActual(term.getPriceActual())
				.priceUOMId(UomId.ofRepoId(term.getC_UOM_ID())) // 07090: when setting a priceActual, we also need to specify a PriceUOM
				.build();
	}

	@Override
	public String getConditionsType()
	{
		return X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription;
	}

	/**
	 * Set the quantity from the term.
	 */
	@Override
	public Quantity calculateQtyEntered(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final UomId uomId = HandlerTools.retrieveUomId(icRecord);

		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(icRecord);
		return new Quantity(
				term.getPlannedQtyPerUnit(),
				loadOutOfTrx(uomId, I_C_UOM.class));
	}

	@Override
	public Consumer<I_C_Invoice_Candidate> getInvoiceScheduleSetterFunction(@NonNull final Consumer<I_C_Invoice_Candidate> defaultImplementation)
	{
		return defaultImplementation;
	}

	@Override
	public Timestamp calculateDateOrdered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(invoiceCandidateRecord);

		final Timestamp dateOrdered;
		final boolean termHasAPredecessor = Services.get(IContractsDAO.class).termHasAPredecessor(term);
		if (!termHasAPredecessor)
		{
			dateOrdered = term.getStartDate();
		}
		// If the term was extended (meaning that there is a predecessor term),
		// C_Invoice_Candidate.DateOrdered should rather be the StartDate minus TermOfNoticeDuration/TermOfNoticeUnit
		else
		{
			final Timestamp firstDayOfNewTerm = getGetExtentionDateOfNewTerm(term);
			dateOrdered = firstDayOfNewTerm;
		}
		return dateOrdered;
	}

	/**
	 * For the given <code>term</code> and its <code>C_Flatrate_Transition</code> record, this method returns the term's start date minus the period specified by <code>TermOfNoticeDuration</code> and
	 * <code>TermOfNoticeUnit</code>.
	 */
	private static Timestamp getGetExtentionDateOfNewTerm(@NonNull final I_C_Flatrate_Term term)
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

		return minimumDateToStart;
	}
}
