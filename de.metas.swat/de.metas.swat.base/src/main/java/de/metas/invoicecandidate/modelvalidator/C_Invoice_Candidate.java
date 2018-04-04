package de.metas.invoicecandidate.modelvalidator;

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
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_OrderLine;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.api.impl.InvoiceCandBL;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupCompensationChangesHandler;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.tax.api.ITaxDAO;

@Interceptor(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	private static final transient Logger logger = InvoiceCandidate_Constants.getLogger(C_Invoice_Candidate.class);

	// NOTE: set required=false because atm, for some reason junit tests are failing on jenkins
	@Autowired(required = false)
	private InvoiceCandidateGroupRepository groupsRepo;
	private InvoiceCandidateGroupCompensationChangesHandler groupChangesHandler;

	public C_Invoice_Candidate()
	{
		Adempiere.autowire(this);

		// NOTE: in unit test mode and while running tools like model generators,
		// the groupsRepo is not Autowired because there is no spring context,
		// so we have to instantiate it directly
		if (groupsRepo == null && Adempiere.isUnitTestMode())
		{
			groupsRepo = new InvoiceCandidateGroupRepository(new GroupCompensationLineCreateRequestFactory());
		}

		groupChangesHandler = InvoiceCandidateGroupCompensationChangesHandler.builder()
				.groupsRepo(groupsRepo)
				.build();
	};

	/**
	 * Set QtyToInvoiceInPriceUOM, just to make sure it is up2date.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID,
			I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice,
			I_C_Invoice_Candidate.COLUMNNAME_Price_UOM_ID })
	public void updateQtyToInvoiceInPriceUOM(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		// task 08507: ic.getQtyToInvoice() is already the "effective". Qty even if QtyToInvoice_Override is set, the system will decide what to invoice (e.g. based on RnvoiceRule and QtDdelivered)
		// and update QtyToInvoice accordingly, possibly to a value that is different from QtyToInvoice_Override.
		// final BigDecimal qtyToInvoice = invoiceCandBL.getQtyToInvoice(ic);
		final BigDecimal qtyToInvoiceInPriceUOM = invoiceCandBL.convertToPriceUOM(ic.getQtyToInvoice(), ic);

		ic.setQtyToInvoiceInPriceUOM(qtyToInvoiceInPriceUOM);
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
	 *
	 * @param ic
	 * @task http://dewiki908/mediawiki/index.php/07242_Error_creating_invoice_from_InOutLine-IC_%28104224060697%29
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
	 *
	 * @param ic
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updatePOReference(final I_C_Invoice_Candidate ic)
	{
		Services.get(IInvoiceCandBL.class).updatePOReferenceFromOrder(ic);
	}

	/**
	 * Configure {@link I_C_Invoice_Candidate#COLUMN_PriceActual_Net_Effective}, depending on the <code>PriceActual</code> and <code>IsTaxIncluded</code> (which if true, is removed).
	 *
	 * @param candidate
	 * @task 08457
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = {
			I_C_Invoice_Candidate.COLUMNNAME_PriceActual, I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override, I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded, I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded_Override, I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID, I_C_Invoice_Candidate.COLUMNNAME_C_Tax_Override_ID, I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID })
	public void updatePriceActual_Net_Effective(final I_C_Invoice_Candidate candidate)
	{
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

	/**
	 * For the given <code>ic</code>, this method invalidates the invoice candidate<br>
	 * and all other candidates that reference the same record via <code>(AD_Table_ID, Record_ID)</code>, <b>unless</b>
	 * {@link InterfaceWrapperHelper#hasChanges(Object)} returns <code>false</code>. In that case, the method does nothing.
	 * <p>
	 * Note: we invalidate more than just the given candidate, because at least for the case of "split"-candidates we need to do so, in order to update the new and the old candidate. See
	 * {@link InvoiceCandBL#splitCandidate(I_C_Invoice_Candidate, String)}.
	 *
	 * @param ic
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW })
	public void invalidateCandidates(final I_C_Invoice_Candidate ic)
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
			invoiceCandDAO.invalidateCandsWithSameReference(ic);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteC_Invoice_Line_Allocs(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		for (final I_C_Invoice_Line_Alloc ila : invoiceCandDAO.retrieveIlaForIc(ic))
		{
			InterfaceWrapperHelper.delete(ila);
		}
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
	 *
	 * @param ic
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
	 *
	 * @param ic
	 *
	 * @task http://dewiki908/mediawiki/index.php/09531_C_Invoice_candidate%3A_deleted_ICs_are_not_coming_back_%28107964479343%29
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
		Services.get(IInvoiceCandidateHandlerBL.class).scheduleCreateMissingCandidatesFor(model);
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
	 *
	 * @param ic
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override })
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
	 *
	 * @param ic
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setHeaderAggregationKey(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final boolean isBackgroundProcessInProcess = invoiceCandBL.isUpdateProcessInProgress();
		if (ic.isProcessed()
				|| X_C_Invoice_Candidate.PROCESSED_OVERRIDE_Yes.equals(ic.getProcessed_Override())
				|| isBackgroundProcessInProcess)
		{
			return; // nothing to do
		}

		final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
		aggregationBL.setHeaderAggregationKey(ic);
	}

	/**
	 * In case the correct tax was not found for the invoice candidate and it was set to the Tax_Not_Found placeholder instead, mark the candidate as Error.
	 *
	 * @param candidate
	 * @task 07814
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW })
	public void errorIfTaxNotFound(final I_C_Invoice_Candidate candidate)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);

		final I_C_Tax taxNotFound = Services.get(ITaxDAO.class).retrieveNoTaxFound(ctx);

		final I_C_Tax taxEffective = Services.get(IInvoiceCandBL.class).getTaxEffective(candidate);

		if (taxNotFound.getC_Tax_ID() == taxEffective.getC_Tax_ID())
		{
			candidate.setIsError(true);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice,
			I_C_Invoice_Candidate.COLUMNNAME_GroupCompensationPercentage
	})
	public void handleCompensantionGroupRelatedChanges(final I_C_Invoice_Candidate invoiceCandidate)
	{
		groupChangesHandler.onInvoiceCandidateChanged(invoiceCandidate);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt, I_C_Invoice_Candidate.COLUMNNAME_Processed })
	public void triggerUpdateBPStats(final I_C_Invoice_Candidate ic)
	{
		if (ic.getLineNetAmt().signum() > 0)
		{
			Services.get(IBPartnerStatisticsUpdater.class)
			.updateBPartnerStatistics(BPartnerStatisticsUpdateRequest.builder()
					.bpartnerId(ic.getBill_BPartner_ID())
					.build());

		}
	}

}
