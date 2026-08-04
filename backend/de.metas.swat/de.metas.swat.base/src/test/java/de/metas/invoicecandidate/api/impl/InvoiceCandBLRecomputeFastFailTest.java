/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.invoicecandidate.api.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers the purely-additive fast-fail on a definitely-errored async recompute, added to
 * {@link InvoiceCandBL#isSelectionUpdated_failFastOnRecomputeError(InvoiceCandidateIdsSelection)} and its detection
 * seam {@link IInvoiceCandDAO#getFailedRecomputeErrorMessage(InvoiceCandidateIdsSelection)}.
 */
public class InvoiceCandBLRecomputeFastFailTest
{
	private static final int ASYNC_BATCH_ID = 555;
	private static final String ERROR_MSG = "boom - transaction poisoned in UpdateInvalidInvoiceCandidatesWorkpackageProcessor";

	private IInvoiceCandDAO invoiceCandDAO;
	private InvoiceCandBL invoiceCandBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		invoiceCandBL = new InvoiceCandBL();
	}

	private InvoiceCandidateId createInvoiceCandidate(final int asyncBatchId)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		if (asyncBatchId > 0)
		{
			ic.setC_Async_Batch_ID(asyncBatchId);
		}
		saveRecord(ic);
		return InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID());
	}

	/** Marks the given IC invalid, i.e. makes {@code hasInvalidInvoiceCandidatesForSelection} return true. */
	private void tagInvalid(final InvoiceCandidateId invoiceCandidateId, final int asyncBatchId)
	{
		final I_C_Invoice_Candidate_Recompute recompute = newInstance(I_C_Invoice_Candidate_Recompute.class);
		recompute.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		if (asyncBatchId > 0)
		{
			recompute.setC_Async_Batch_ID(asyncBatchId);
		}
		saveRecord(recompute);
	}

	private void createWorkPackage(final int asyncBatchId, final boolean isError, @Nullable final String errorMsg)
	{
		final I_C_Queue_WorkPackage wp = newInstance(I_C_Queue_WorkPackage.class);
		wp.setC_Async_Batch_ID(asyncBatchId);
		wp.setIsError(isError);
		wp.setErrorMsg(errorMsg);
		saveRecord(wp);
	}

	@Test
	public void getFailedRecomputeErrorMessage_erroredWorkPackage_returnsErrorMsg()
	{
		final InvoiceCandidateId icId = createInvoiceCandidate(ASYNC_BATCH_ID);
		tagInvalid(icId, ASYNC_BATCH_ID);
		createWorkPackage(ASYNC_BATCH_ID, true, ERROR_MSG);

		final Optional<String> result = invoiceCandDAO.getFailedRecomputeErrorMessage(
				InvoiceCandidateIdsSelection.ofIdsSet(java.util.Collections.singleton(icId)));

		assertThat(result).isPresent();
		assertThat(result.get())
				.contains(ERROR_MSG)
				.contains(String.valueOf(icId.getRepoId()));
	}

	@Test
	public void getFailedRecomputeErrorMessage_noErroredWorkPackage_empty()
	{
		final InvoiceCandidateId icId = createInvoiceCandidate(ASYNC_BATCH_ID);
		tagInvalid(icId, ASYNC_BATCH_ID);
		// a non-errored WP for the batch: must NOT trigger a fast-fail
		createWorkPackage(ASYNC_BATCH_ID, false, null);

		final Optional<String> result = invoiceCandDAO.getFailedRecomputeErrorMessage(
				InvoiceCandidateIdsSelection.ofIdsSet(java.util.Collections.singleton(icId)));

		assertThat(result).isEmpty();
	}

	@Test
	public void getFailedRecomputeErrorMessage_icHasNoAsyncBatch_empty()
	{
		final InvoiceCandidateId icId = createInvoiceCandidate(0 /*no async batch*/);
		tagInvalid(icId, 0);
		// even an errored WP under an unrelated batch must NOT be attributed to this selection
		createWorkPackage(ASYNC_BATCH_ID, true, ERROR_MSG);

		final Optional<String> result = invoiceCandDAO.getFailedRecomputeErrorMessage(
				InvoiceCandidateIdsSelection.ofIdsSet(java.util.Collections.singleton(icId)));

		assertThat(result).isEmpty();
	}

	@Test
	public void isSelectionUpdated_stillInvalidAndErrored_throws()
	{
		final InvoiceCandidateId icId = createInvoiceCandidate(ASYNC_BATCH_ID);
		tagInvalid(icId, ASYNC_BATCH_ID);
		createWorkPackage(ASYNC_BATCH_ID, true, ERROR_MSG);

		final InvoiceCandidateIdsSelection selection = InvoiceCandidateIdsSelection.ofIdsSet(java.util.Collections.singleton(icId));

		assertThatThrownBy(() -> invoiceCandBL.isSelectionUpdated_failFastOnRecomputeError(selection))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	public void isSelectionUpdated_stillInvalidButNoError_keepsWaiting()
	{
		final InvoiceCandidateId icId = createInvoiceCandidate(ASYNC_BATCH_ID);
		tagInvalid(icId, ASYNC_BATCH_ID);
		// no errored WP -> must keep waiting (return false), NOT throw

		final InvoiceCandidateIdsSelection selection = InvoiceCandidateIdsSelection.ofIdsSet(java.util.Collections.singleton(icId));

		assertThat(invoiceCandBL.isSelectionUpdated_failFastOnRecomputeError(selection)).isFalse();
	}

	@Test
	public void isSelectionUpdated_noInvalidIcs_done()
	{
		final InvoiceCandidateId icId = createInvoiceCandidate(ASYNC_BATCH_ID);
		// no recompute tag -> IC is valid -> done

		final InvoiceCandidateIdsSelection selection = InvoiceCandidateIdsSelection.ofIdsSet(java.util.Collections.singleton(icId));

		assertThat(invoiceCandBL.isSelectionUpdated_failFastOnRecomputeError(selection)).isTrue();
	}
}
