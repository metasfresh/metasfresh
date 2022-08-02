package de.metas.invoicecandidate.modelvalidator;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.api.impl.InvoiceCandBL;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupCompensationChangesHandler;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.location.InvoiceCandidateLocationsUpdater;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.logging.TableRecordMDC;
import de.metas.tax.api.Tax;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_OrderLine;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.isValueChanged;

@Interceptor(I_C_Invoice_Candidate.class)
@Component
public class C_Invoice_Candidate
{
	private static final Logger logger = InvoiceCandidate_Constants.getLogger(C_Invoice_Candidate.class);

	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);

	private final AttachmentEntryService attachmentEntryService;
	private final InvoiceCandidateGroupCompensationChangesHandler groupChangesHandler;
	private final InvoiceCandidateRecordService invoiceCandidateRecordService;
	private final IDocumentLocationBL documentLocationBL;

	public C_Invoice_Candidate(
			@NonNull final InvoiceCandidateRecordService invoiceCandidateRecordService,
			@NonNull final InvoiceCandidateGroupRepository groupsRepo,
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final IDocumentLocationBL documentLocationBL)
	{
		this.invoiceCandidateRecordService = invoiceCandidateRecordService;
		this.groupChangesHandler = InvoiceCandidateGroupCompensationChangesHandler.builder()
				.groupsRepo(groupsRepo)
				.build();
		this.attachmentEntryService = attachmentEntryService;
		this.documentLocationBL = documentLocationBL;
	}

	@ModelChange( //
			timings = ModelValidator.TYPE_BEFORE_CHANGE, //
			ifColumnsChanged = {
					I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override,
					I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override,
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override })
	public void updateInvoiceCandidateDirectly(final I_C_Invoice_Candidate icRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(icRecord))
		{
			final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
			if (invoiceCandBL.isUpdateProcessInProgress())
			{
				logger.debug("Change was performed by scheduler process. No need to update the receord again: {}", icRecord);
				return;
			}

			if (isValueChanged(icRecord, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override))
			{
				invoiceCandidateHandlerBL.setDeliveredData(icRecord);
			}
			final InvoiceCandidate invoiceCandidate = invoiceCandidateRecordService.ofRecord(icRecord);
			invoiceCandidateRecordService.updateRecord(invoiceCandidate, icRecord);
		}
	}

	/**
	 * For the given <code>ic</code>, this method invalidates the invoice candidate<br>
	 * and all other candidates that reference the same record via <code>(AD_Table_ID, Record_ID)</code>, <b>unless</b>
	 * {@link InterfaceWrapperHelper#hasChanges(Object)} returns <code>false</code>. In that case, the method does nothing.
	 * <p>
	 * Note: we invalidate more than just the given candidate, because at least for the case of "split"-candidates we need to do so, in order to update the new and the old candidate. See
	 * {@link InvoiceCandBL#splitCandidate(I_C_Invoice_Candidate)}.
	 */
	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ignoreColumnsChanged = {
					// the following columns already trigger an "immediate update (see method updateInvoiceCandidateDirectly()); no need to invalidate the record
					I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override,
					I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override,
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override,

					// the following columns are "endresults" of invoice candidate updates and never need to trigger an invalidation
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceBeforeDiscount,
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceInUOM_Calc,
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceInUOM,
					I_C_Invoice_Candidate.COLUMNNAME_QtyWithIssues,
					I_C_Invoice_Candidate.COLUMNNAME_QtyWithIssues_Effective,
					I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent,
					I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered,
					I_C_Invoice_Candidate.COLUMNNAME_QtyDeliveredInUOM,
					I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate })
	public void invalidateCandidatesAfterChange(@NonNull final I_C_Invoice_Candidate ic)
	{
		invalidateCandidates0(ic);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void invalidateCandidatesAfterNew(final I_C_Invoice_Candidate ic)
	{
		invalidateCandidates0(ic);
	}

	private void invalidateCandidates0(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		if (invoiceCandBL.isUpdateProcessInProgress())
		{
			logger.debug("Change was performed by scheduler process. No need to invalidate: {}", ic);
			return;
		}

		//
		// Invalidate invoice candidate(s)
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		// Case: the IC is not linked to any particular record => invalidate just this one
		if (ic.getAD_Table_ID() <= 0 || ic.getRecord_ID() <= 0)
		{
			invoiceCandDAO.invalidateCand(ic);
		}
		// Case: the IC is linked to a record => invalidate all ICs (including this one) which links to that record
		else
		{
			invoiceCandDAO.invalidateCandsWithSameTableReference(ic);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE,
			ModelValidator.TYPE_BEFORE_SAVE_TRX }, ifColumnsChanged = {
			I_C_Invoice_Candidate.COLUMNNAME_PriceEntered, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override,
			I_C_Invoice_Candidate.COLUMNNAME_Discount, I_C_Invoice_Candidate.COLUMNNAME_Discount_Override,
			I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID,
			I_C_Invoice_Candidate.COLUMNNAME_PriceActual, I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override,
			I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override })
	public void updateNetAmtToInvoice(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		// first check priceActual
		invoiceCandBL.setPriceActual_Override(ic);
		// note: we don't need to do this "BEFORE_NEW", because a new record doesn't have a "QtyToInvoice" yet.
		// Changed to trigger on every change, not just on overrides, as we need this when running the update process too.
		invoiceCandBL.setNetAmtToInvoice(ic);
	}

	// 03908 : Commented out. After creating candidate, we call invalidateCandidates to invalidate the related ones for recalculation.
	// @ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	// public void invalidateNewCandidate(final I_C_Invoice_Candidate ic)
	// {
	// final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
	// invoiceCandDB.invalidateCand(ic);
	// }

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered,
			I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced,
			I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered,
			I_C_Invoice_Candidate.COLUMNNAME_Processed_Override, // task 08567
	})
	public void updateProcessedFlag(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		invoiceCandBL.updateProcessedFlag(ic);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced })
	public void updateOrderLineQtyInvoiced(final I_C_Invoice_Candidate ic)
	{
		if (ic.getC_OrderLine_ID() > 0)
		{
			final org.compiere.model.I_C_OrderLine ol = ic.getC_OrderLine();
			if (ol.getQtyInvoiced().compareTo(ic.getQtyInvoiced()) != 0)
			{
				// Required to ommit
				// "MOrderLine.set_Value: Column not updateable - QtyInvoiced - NewValue=5.00 - OldValue=0 [62]"
				Check.errorUnless(ol instanceof X_C_OrderLine || Adempiere.isUnitTestMode(), "We need to set QtyInvoiced via the model class, not directly on the PO (class={}).", ol.getClass());
				ol.setQtyInvoiced(ic.getQtyInvoiced());
			}
			InterfaceWrapperHelper.save(ol);

			Check.assume(ol.getQtyInvoiced().compareTo(ic.getQtyInvoiced()) == 0, ic + " should have updated its ol's QtyInVoiced");
		}
	}

	/**
	 * For new invoice candidates, this method sets the <code>C_Order_ID</code>, if the referenced record is either a <code>C_OrderLine_ID</code> or a <code>M_InOutLine_ID</code>.
	 * <p>
	 * Task http://dewiki908/mediawiki/index.php/07242_Error_creating_invoice_from_InOutLine-IC_%28104224060697%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void updateOrderId(final I_C_Invoice_Candidate ic)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		if (adTableDAO.retrieveTableId(org.compiere.model.I_M_InOutLine.Table_Name) == ic.getAD_Table_ID())
		{
			final I_M_InOutLine iol = InterfaceWrapperHelper.create(InterfaceWrapperHelper.getCtx(ic), ic.getRecord_ID(), I_M_InOutLine.class, InterfaceWrapperHelper.getTrxName(ic));
			ic.setC_Order_ID(iol.getM_InOut().getC_Order_ID());
		}
		else if (adTableDAO.retrieveTableId(I_C_OrderLine.Table_Name) == ic.getAD_Table_ID())
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(InterfaceWrapperHelper.getCtx(ic), ic.getRecord_ID(), I_C_OrderLine.class, InterfaceWrapperHelper.getTrxName(ic));
			ic.setC_Order_ID(ol.getC_Order_ID());

		}
	}

	/**
	 * Set the POReference of the C_Order, in case of Sales Orders
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updatePOReference(final I_C_Invoice_Candidate ic)
	{
		Services.get(IInvoiceCandBL.class).updatePOReferenceFromOrder(ic);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateCapturedLocationsAndRenderedAddresses(final I_C_Invoice_Candidate ic)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(ic))
		{
			InvoiceCandidateLocationsUpdater.builder()
					.documentLocationBL(documentLocationBL)
					.record(ic)
					.build()
					.updateAllIfNeeded();
		}
	}

	/**
	 * Configure {@link I_C_Invoice_Candidate#COLUMN_PriceActual_Net_Effective}, depending on the <code>PriceActual</code> and <code>IsTaxIncluded</code> (which if true, is removed).
	 * <p>
	 * Task 08457
	 */
	@ModelChange(//
			timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, //
			ifColumnsChanged = {
					I_C_Invoice_Candidate.COLUMNNAME_PriceActual, //
					I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override, //
					I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded, //
					I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded_Override, //
					I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID, //
					I_C_Invoice_Candidate.COLUMNNAME_C_Tax_Override_ID, //
					I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID //
			})
	public void updatePriceActual_Net_Effective(final I_C_Invoice_Candidate candidate)
	{
		if (candidate.getC_Currency_ID() <= 0)
		{
			return; // the IC is not yet ready for this
		}
		Services.get(IInvoiceCandBL.class).setPriceActualNet(candidate);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_C_Invoice_Candidate.COLUMNNAME_IsError })
	public void resetErrorFields(final I_C_Invoice_Candidate ic)
	{
		if (!ic.isError())
		{
			Services.get(IInvoiceCandBL.class).resetError(ic);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteC_Invoice_Line_Allocs(final I_C_Invoice_Candidate ic)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.create()
				.delete();
	}

	/**
	 * When an invoice candidate is deleted, then also delete all its {@link I_C_InvoiceCandidate_InOutLine}s
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteC_InvoiceCandidate_InOutLines(final I_C_Invoice_Candidate ic)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL
				.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class, ic)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.create()
				.delete();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteC_Invoice_Details(final I_C_Invoice_Candidate ic)
	{
		Services.get(IInvoiceCandDAO.class).deleteInvoiceDetails(ic);
	}

	/**
	 * This method sets {@link I_M_InOutLine#COLUMNNAME_IsInvoiceCandidate} back to <code>N</code> if the given <code>ic</code> references an inOutLine.
	 * <p>
	 * TODO in task 07067: extract this into the listener architecture.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void resetIolIsInvoiceCandidateFlag(final I_C_Invoice_Candidate ic)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		if (ic.getAD_Table_ID() == adTableDAO.retrieveTableId(org.compiere.model.I_M_InOutLine.Table_Name))
		{
			final I_M_InOutLine iol = InterfaceWrapperHelper.create(InterfaceWrapperHelper.getCtx(ic),
																	ic.getRecord_ID(),
																	I_M_InOutLine.class,
																	InterfaceWrapperHelper.getTrxName(ic));

			iol.setIsInvoiceCandidate(false);
			InterfaceWrapperHelper.save(iol);
		}
	}

	/**
	 * After an invoice candidate was deleted, schedule the recreation of it.
	 * <p>
	 * Task http://dewiki908/mediawiki/index.php/09531_C_Invoice_candidate%3A_deleted_ICs_are_not_coming_back_%28107964479343%29
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void scheduleRecreate(final I_C_Invoice_Candidate ic)
	{
		//
		// Skip recreation scheduling if we were asked to avoid that
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		if (invoiceCandDAO.isAvoidRecreate(ic))
		{
			return;
		}

		//
		// Get linked model
		final Object model = TableRecordCacheLocal.getReferencedValue(ic, Object.class);
		if (model == null)
		{
			// Record is missing. It might be because the model was deleted.
			// => nothing to do
			return;
		}

		//
		// Schedule IC generation
		invoiceCandidateHandlerBL.scheduleCreateMissingCandidatesFor(model);
	}

	/**
	 * Resets <code>QtyToInvoice_OverrideFulfilled</code> whenever QtyToInvoice_Override is changed, because:
	 * <ul>
	 * <li>QtyToInvoice_Override is only changed by the user
	 * <li>If the value changes, the current "Fulfilled" make make no sense anymore, and at any rate, the as the user QtyToInvoiceOverride it is fair to assume that he/she did so after reviewing
	 * QtyInvocied and QtyToInvoice and sets the override value to another reasonable value.
	 * </ul>
	 * Note that if <code>QtyToInvoice_Override</code> is change to null, then QtyToInvoice_OverrideFulfilled is reset to <code>null</code>, otherwise to zero. This hopefully makes things more
	 * transparent to the user.
	 */
	@ModelChange( //
			timings = ModelValidator.TYPE_BEFORE_CHANGE, //
			ifColumnsChanged = I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override)
	public void resetQtyToInvoiceFulFilled(final I_C_Invoice_Candidate ic)
	{
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override))
		{
			ic.setQtyToInvoice_OverrideFulfilled(null);
		}
		else
		{
			ic.setQtyToInvoice_OverrideFulfilled(BigDecimal.ZERO);
		}
	}

	/**
	 * Update header aggregation key, unless (=>task 08451) the given <code>id</code> is already processed or a background process (creating, updating or invoicing) is currently in progress.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setHeaderAggregationKey(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final boolean isBackgroundProcessInProcess = invoiceCandBL.isUpdateProcessInProgress();

		if (ic.isProcessed()
				|| invoiceCandBL.extractProcessedOverride(ic).isTrue()
				|| isBackgroundProcessInProcess)
		{
			return; // nothing to do
		}

		if (InterfaceWrapperHelper.isNew(ic) || ic.getC_Invoice_Candidate_ID() <= 0)
		{
			aggregationBL.setHeaderAggregationKey(ic);
			return;
		}

		invoiceCandDAO.invalidateCand(ic);
	}


	/**
	 * In case the correct tax was not found for the invoice candidate and it was set to the Tax_Not_Found placeholder instead, mark the candidate as Error.
	 * <p>
	 * Task 07814
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW })
	public void errorIfTaxNotFound(final I_C_Invoice_Candidate candidate)
	{
		final Tax taxEffective = Services.get(IInvoiceCandBL.class).getTaxEffective(candidate);

		if (taxEffective.isTaxNotFound())
		{
			candidate.setIsError(true);
		}
	}

	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = {
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_GroupCompensationPercentage
			})
	public void handleCompensantionGroupRelatedChanges(final I_C_Invoice_Candidate invoiceCandidate)
	{
		groupChangesHandler.onInvoiceCandidateChanged(invoiceCandidate);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt, I_C_Invoice_Candidate.COLUMNNAME_Processed })
	public void triggerUpdateBPStats(final I_C_Invoice_Candidate ic)
	{
		Services.get(IBPartnerStatisticsUpdater.class)
				.updateBPartnerStatistics(BPartnerStatisticsUpdateRequest.builder()
												  .bpartnerId(ic.getBill_BPartner_ID())
												  .build());
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void shareAttachments(final I_C_Invoice_Candidate ic)
	{
		if (ic.getRecord_ID() <= 0)
		{
			return; // nothing to do
		}

		final TableRecordReference referencedRecord = TableRecordReference.ofReferenced(ic);
		final TableRecordReference icRecord = TableRecordReference.of(ic);

		// Invoke the method after commit to make sure that when we do it, the IC exists "globally"
		// This prevents race conditions in case someone creates e.g. a C_OLCand and then adds an attachment which the ICs are created asynchronously
		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> attachmentEntryService.shareAttachmentLinks(
						ImmutableList.of(referencedRecord),
						ImmutableList.of(icRecord)));
	}
}
