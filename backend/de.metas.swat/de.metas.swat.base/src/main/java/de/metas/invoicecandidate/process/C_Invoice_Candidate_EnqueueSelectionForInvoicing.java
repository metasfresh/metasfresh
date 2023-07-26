/**
 *
 */
package de.metas.invoicecandidate.process;

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

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.ProcessPreconditionsResolution.ProcessCaptionMapper;
import de.metas.process.RunOutOfTrx;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.compiere.util.Ini;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Properties;

public class C_Invoice_Candidate_EnqueueSelectionForInvoicing extends JavaProcess implements IProcessPrecondition
{
	private static final String MSG_InvoiceCandidate_PerformEnqueuing = "C_InvoiceCandidate_PerformEnqueuing";
	//
	// Services
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final C_Invoice_Candidate_ProcessCaptionMapperHelper processCaptionMapperHelper = SpringContextHolder.instance.getBean(C_Invoice_Candidate_ProcessCaptionMapperHelper.class);
	// Parameters
	private IInvoicingParams invoicingParams;
	private BigDecimal totalNetAmtToInvoiceChecksum;

	private int selectionCount = 0;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final IQueryFilter<I_C_Invoice_Candidate> selectionFilter = context.getQueryFilter(I_C_Invoice_Candidate.class);

		return ProcessPreconditionsResolution.accept()
				.withCaptionMapper(processCaptionMapper(selectionFilter));
	}

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		// Display process logs only if the process failed.
		// NOTE: we do that because this process is called from window Gear and user shall only see how many ICs where enqueued, in status line,
		// and no popup shall be displayed.
		setShowProcessLogs(ShowProcessLogs.OnError);

		final IParams params = getParameterAsIParams();
		this.invoicingParams = new InvoicingParams(params);

		//
		// Create and check invoice candidate selection
		selectionCount = createSelection();
		if (selectionCount <= 0)
		{
			final Properties ctx = getCtx();
			throw new AdempiereException(msgBL.getMsg(ctx, IInvoiceCandidateEnqueuer.MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P));
		}

		//
		// Ask user if we shall enqueue the invoice candidates.
		checkPerformEnqueuing();
	}

	/**
	 * Before enqueuing the candidates, check how many partners they have.
	 * In case there are more that one partner, ask the user if he really wants to invoice for so many partners.
	 *
	 * @task 08961
	 * @throws ProcessCanceledException if user canceled
	 */
	private void checkPerformEnqueuing() throws ProcessCanceledException
	{
		final int bpartnerCount = countBPartners();
		if (bpartnerCount <= 1)
		{
			return;
		}
		final boolean performEnqueuing;
		if (Ini.isSwingClient())
		{
			performEnqueuing = Services.get(IClientUI.class).ask()
					.setParentWindowNo(getProcessInfo().getWindowNo())
					.setAD_Message(MSG_InvoiceCandidate_PerformEnqueuing, selectionCount, bpartnerCount)
					.setDefaultAnswer(false)
					.getAnswer();
		}
		else
		{
			performEnqueuing = true;
		}

		// if the enqueuing was not accepted by the user, do nothing
		if (!performEnqueuing)
		{
			throw new ProcessCanceledException();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final PInstanceId pinstanceId = getPinstanceId();

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setInvoicingParams(invoicingParams)
				.setFailIfNothingEnqueued(true) // If no workpackages were created, display error message that no selection was made (07666)
				.setTotalNetAmtToInvoiceChecksum(totalNetAmtToInvoiceChecksum)
				// .setFailOnChanges(true) // NOTE: use the standard settings (which will fallback on SysConfig)
				//
				.enqueueSelection(pinstanceId);

		return enqueueResult.getSummaryTranslated(getCtx());
	}

	/**
	 * @return count of selected items
	 */
	private int createSelection()
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = createICQueryBuilder();

		//
		// Create selection and return how many items were added
		final PInstanceId adPInstanceId = getPinstanceId();
		Check.assumeNotNull(adPInstanceId, "adPInstanceId is not null");

		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		final int selectionCount = queryBuilder
				.create()
				.setRequiredAccess(Access.READ) // 04471: enqueue only those records on which user has access to
				.createSelection(adPInstanceId);

		return selectionCount;
	}

	private int countBPartners()
	{
		return createICQueryBuilder()
				.create()
				.listDistinct(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID)
				.size();

	}

	private IQueryBuilder<I_C_Invoice_Candidate> createICQueryBuilder()
	{
		// Get the user selection filter (i.e. what user filtered in his window)
		final IQueryFilter<I_C_Invoice_Candidate> userSelectionFilter;
		if (Ini.isSwingClient())
		{
			// In case of Swing, preserve the old functionality, i.e. if no where clause then select all
			userSelectionFilter = getProcessInfo().getQueryFilterOrElseFalse();
		}
		else
		{
			userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);
			if (userSelectionFilter == null)
			{
				throw new AdempiereException("@NoSelection@");
			}
		}

		return createICQueryBuilder(userSelectionFilter);
	}

	private IQueryBuilder<I_C_Invoice_Candidate> createICQueryBuilder(final IQueryFilter<I_C_Invoice_Candidate> userSelectionFilter)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, getCtx(), ITrx.TRXNAME_None)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				//
				// NOTE: we are not filtering by IsToClear, Processed etc
				// because we want to allow the enqueuer do do that
				// because enqueuer will also log an message about why it was excluded (=> transparant for user)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsToClear, false)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false) not filtering by processed, because the IC might be invalid (08343)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsError, false)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsInDispute, false)
				//
				;

		//
		// Consider only approved invoices (if we were asked to do so)
		if (invoicingParams != null && invoicingParams.isOnlyApprovedForInvoicing())
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing, true);
		}

		return queryBuilder;
	}

	@Nullable
	private ProcessCaptionMapper processCaptionMapper(final IQueryFilter<I_C_Invoice_Candidate> selectionFilter)
	{
		final IQuery<I_C_Invoice_Candidate> query = prepareNetAmountsToInvoiceForSelectionQuery(selectionFilter);
		return processCaptionMapperHelper.getProcessCaptionMapperForNetAmountsFromQuery(query);
	}

	private IQuery<I_C_Invoice_Candidate> prepareNetAmountsToInvoiceForSelectionQuery(final IQueryFilter<I_C_Invoice_Candidate> selectionFilter)
	{
		return createICQueryBuilder(selectionFilter)
				.addNotNull(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID)
				.create();
	}

}
