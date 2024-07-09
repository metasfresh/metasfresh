package de.metas.contracts.subscription.invoicecandidatehandler;

import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

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
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(term.getC_TaxCategory_ID());
		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(firstGreaterThanZero(ic.getC_VAT_Code_Override_ID(), ic.getC_VAT_Code_ID()));

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				term,
				taxCategoryId,
				term.getM_Product_ID(),
				ic.getDateOrdered(), // shipDate
				OrgId.ofRepoId(term.getAD_Org_ID()),
				(WarehouseId)null,
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
		return Quantitys.of(term.getPlannedQtyPerUnit(), uomId);
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
