package de.metas.contracts.invoicecandidate;

import static de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode.CREATE_CANDIDATES_AND_INVOICES;
import static de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode.CREATE_CANDIDATES;
import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.refund.invoicecandidatehandler.FlatrateTermRefund_Handler;
import de.metas.contracts.subscription.invoicecandidatehandler.FlatrateTermSubscription_Handler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;

import javax.annotation.Nullable;

/**
 * Creates {@link I_C_Invoice_Candidate} from {@link I_C_Flatrate_Term}.
 * Also see {@link ConditionTypeSpecificInvoiceCandidateHandler}.
 */
public class FlatrateTerm_Handler extends AbstractInvoiceCandidateHandler
{
	public final static String SYS_Config_AUTO_INVOICE = "de.metas.contracts.invoicecandidate.ALLOW_AUTO_INVOICE";
	
	private final Map<String, ConditionTypeSpecificInvoiceCandidateHandler> conditionTypeSpecificInvoiceCandidateHandlers;
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public FlatrateTerm_Handler()
	{
		this(ImmutableList.<ConditionTypeSpecificInvoiceCandidateHandler>of(
				new FlatrateTermSubscription_Handler(),
				new FlatrateTermRefund_Handler()));
	}

	public FlatrateTerm_Handler(
			@NonNull final List<ConditionTypeSpecificInvoiceCandidateHandler> conditionTypeSpecificInvoiceCandidateHandlers)
	{
		this.conditionTypeSpecificInvoiceCandidateHandlers = Maps
				.uniqueIndex(
						conditionTypeSpecificInvoiceCandidateHandlers,
						ConditionTypeSpecificInvoiceCandidateHandler::getConditionsType);
	}

	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		final boolean allowAutoInvoice = sysConfigBL.getBooleanValue(SYS_Config_AUTO_INVOICE, false);
		return allowAutoInvoice ? CREATE_CANDIDATES_AND_INVOICES : CREATE_CANDIDATES;
	}
	
	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(@NonNull final Object flatrateTermObj)
	{
		final I_C_Flatrate_Term flatrateTerm = create(flatrateTermObj, I_C_Flatrate_Term.class);
		final Collection<ConditionTypeSpecificInvoiceCandidateHandler> specificHandlers = conditionTypeSpecificInvoiceCandidateHandlers.values();

		for (final ConditionTypeSpecificInvoiceCandidateHandler specificHandler : specificHandlers)
		{
			final CandidatesAutoCreateMode modeForTerm = specificHandler.isMissingInvoiceCandidate(flatrateTerm);
			if (modeForTerm != CandidatesAutoCreateMode.DONT)
			{
				return modeForTerm;
			}
		}
		return CandidatesAutoCreateMode.DONT;
	}
	
	/**
	 * One invocation returns a maximum of <code>limit</code> {@link I_C_Flatrate_Term}s that are completed subscriptions and don't have an invoice candidate referencing them.
	 */
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveAllModelsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		final Collection<ConditionTypeSpecificInvoiceCandidateHandler> specificHandlers = conditionTypeSpecificInvoiceCandidateHandlers.values();
		Iterator<I_C_Flatrate_Term> result = null;

		for (final ConditionTypeSpecificInvoiceCandidateHandler specificHandler : specificHandlers)
		{
			if (result == null)
			{
				result = specificHandler.retrieveTermsWithMissingCandidates(limit);
			}
			else
			{
				result = Iterators.concat(result, specificHandler.retrieveTermsWithMissingCandidates(limit));
			}
		}
		return result;
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final I_C_Flatrate_Term term = request.getModel(I_C_Flatrate_Term.class);

		final I_C_Invoice_Candidate ic = createCandidateForTerm(term);
		return InvoiceCandidateGenerateResult.of(this, ic);
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		HandlerTools.invalidateCandidatesForTerm(model);
	}

	@Override
	public String getSourceTable()
	{
		return I_C_Flatrate_Term.Table_Name;
	}

	@Override
	public int getAD_User_InCharge_ID(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);
		return term.getAD_User_InCharge_ID();
	}

	/**
	 * Returns false, because the user in charge is taken from the C_Flatrate_Term.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	@Nullable
	private I_C_Invoice_Candidate createCandidateForTerm(@NonNull final I_C_Flatrate_Term term)
	{
		if (HandlerTools.isCancelledContract(term))
		{
			return null;
		}

		final I_C_Invoice_Candidate ic = HandlerTools.createIcAndSetCommonFields(term);
		ic.setC_ILCandHandler(getHandlerRecord());

		setOrderedData(ic);

		// note that C_Invoice_Cand.QtyDelivered by InvoiceCandBL, so
		// whatever InvoiceRule set in the conditions should be OK
		ic.setInvoiceRule(term.getC_Flatrate_Conditions().getInvoiceRule());

		getSpecificHandler(term).setSpecificInvoiceCandidateValues(ic, term);
		return ic;
	}

	/**
	 * <ul>
	 * <li>DateOrdered: if the term has a predecessor, then the the predecessor's notice/extend date. Else the term's <code>StartDate</code>.
	 * <li>QtyOrdered: the term's <code>PlannedQtyPerUnit</code>.
	 * <li>C_Order_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setOrderedData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);
		final ConditionTypeSpecificInvoiceCandidateHandler handler = getSpecificHandler(term);

		ic.setDateOrdered(handler.calculateDateOrdered(ic));

		final Quantity calculateQtyOrdered = handler.calculateQtyEntered(ic);

		ic.setQtyEntered(calculateQtyOrdered.toBigDecimal());
		ic.setC_UOM_ID(calculateQtyOrdered.getUomId().getRepoId());

		final ProductId productId = ProductId.ofRepoId(term.getM_Product_ID());

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final Quantity qtyInProductUOM = uomConversionBL.convertToProductUOM(calculateQtyOrdered, productId);
		ic.setQtyOrdered(qtyInProductUOM.toBigDecimal());
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(@NonNull final I_C_Invoice_Candidate ic)
	{
		HandlerTools.setDeliveredData(ic);
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);
		final ConditionTypeSpecificInvoiceCandidateHandler handler = getSpecificHandler(term);

		return handler.calculatePriceAndTax(ic);
	}

	@Override
	public void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		HandlerTools.setBPartnerData(ic);
	}

	@Override
	public void setInvoiceScheduleAndDateToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);
		final ConditionTypeSpecificInvoiceCandidateHandler handler = getSpecificHandler(term);

		final Consumer<I_C_Invoice_Candidate> defaultImplementation = super::setInvoiceScheduleAndDateToInvoice;

		handler.getInvoiceScheduleSetterFunction(defaultImplementation)
				.accept(ic);
	}

	private ConditionTypeSpecificInvoiceCandidateHandler getSpecificHandler(@NonNull final I_C_Flatrate_Term term)
	{
		final ConditionTypeSpecificInvoiceCandidateHandler handlerOrNull = conditionTypeSpecificInvoiceCandidateHandlers.get(term.getType_Conditions());
		return Check.assumeNotNull(handlerOrNull,
								   "The given term's condition-type={} has a not-null ConditionTypeSpecificInvoiceCandidateHandler; term={}",
								   term.getType_Conditions(), term);
	}

}
