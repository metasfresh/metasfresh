package de.metas.contracts.subscription.invoicecandidatehandler;

import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;

import java.math.BigDecimal;
import java.util.Iterator;

public class FlatrateTermSubscription_Handler implements ConditionTypeSpecificInvoiceCandidateHandler
{

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		return Services.get(IContractsDAO.class)
				.retrieveSubscriptionTermsWithMissingCandidates(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription, limit)
				.iterator();
	}

	@Override
	@NonNull
	public CandidatesAutoCreateMode isMissingInvoiceCandidate(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final boolean anyMissing = Services.get(IContractsDAO.class)
				.createTermWithMissingCandidateQueryBuilder(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription, true /* ignoreDateFilters */)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, flatrateTerm.getC_Flatrate_Term_ID())
				.create()
				.anyMatch();
		if (!anyMissing)
		{
			return CandidatesAutoCreateMode.DONT;
		}

		return isEligibleForInvoiceAutoCreation(flatrateTerm)
				? CandidatesAutoCreateMode.CREATE_CANDIDATES_AND_INVOICES
				: CandidatesAutoCreateMode.CREATE_CANDIDATES;

	}

	private boolean isEligibleForInvoiceAutoCreation(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final boolean autoInvoiceFlatrateTerm = orgDAO.isAutoInvoiceFlatrateTerm(OrgId.ofRepoId(flatrateTerm.getAD_Org_ID()));
		if (!autoInvoiceFlatrateTerm)
		{
			return false; // nothing to do
		}

		if (flatrateDAO.bpartnerHasExistingRunningTerms(flatrateTerm))
		{
			return false; // there are already running terms for this partner. Nothing to do
		}
		return true;
	}

	@Override
	public void setSpecificInvoiceCandidateValues(
			@NonNull final I_C_Invoice_Candidate icRecord,
			@NonNull final I_C_Flatrate_Term term)
	{
		final PriceAndTax priceAndTax = calculatePriceAndTax(icRecord);
		IInvoiceCandInvalidUpdater.updatePriceAndTax(icRecord, priceAndTax);

		// 05265
		icRecord.setIsSOTrx(true);

		final BigDecimal qty = Services.get(IContractsDAO.class).retrieveSubscriptionProgressQtyForTerm(term);
		icRecord.setQtyOrdered(qty);
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
		return Quantitys.create(term.getPlannedQtyPerUnit(), uomId);
	}

}
