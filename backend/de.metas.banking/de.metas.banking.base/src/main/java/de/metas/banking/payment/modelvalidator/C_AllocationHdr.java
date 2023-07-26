package de.metas.banking.payment.modelvalidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

/*
 * #%L
 * de.metas.banking.base
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

import com.google.common.collect.ImmutableSet;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.payment.PaymentId;
import de.metas.util.Services;

@Interceptor(I_C_AllocationHdr.class)
public class C_AllocationHdr
{
	public static final C_AllocationHdr instance = new C_AllocationHdr();
	private final transient Logger logger = LogManager.getLogger(getClass());

	private C_AllocationHdr()
	{
		super();
	}

	/**
	 * After {@link I_C_AllocationHdr} was completed/reversed/voided/reactivated,
	 * update all {@link I_C_PaySelectionLine}s which were not already processed and which are about the invoices from this allocation.
	 * <p>
	 * Task 08972
	 */
	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_VOID,
			ModelValidator.TIMING_AFTER_REACTIVATE })
	public void afterProcessing(final I_C_AllocationHdr allocationHdr)
	{
		final IAllocationDAO allocationsRepo = Services.get(IAllocationDAO.class);

		final List<I_C_AllocationLine> lines = allocationsRepo.retrieveAllLines(allocationHdr);

		final Set<InvoiceId> invoiceIds = extractInvoiceIds(lines);
		updateDraftedPaySelectionLinesForInvoiceIds(invoiceIds);

		final Set<PaymentId> paymentIds = extractPaymentIds(lines);
		invalidateInvoicesAndPayments(invoiceIds, paymentIds);
	}

	private void invalidateInvoicesAndPayments(final Set<InvoiceId> invoiceIds, final Set<PaymentId> paymentIds)
	{
		final ArrayList<CacheInvalidateRequest> requests = new ArrayList<>();
		for (final InvoiceId invoiceId : invoiceIds)
		{
			requests.add(CacheInvalidateRequest.rootRecord(I_C_Invoice.Table_Name, invoiceId));
		}
		for (final PaymentId paymentId : paymentIds)
		{
			requests.add(CacheInvalidateRequest.rootRecord(I_C_Payment.Table_Name, paymentId));
		}

		if (requests.isEmpty())
		{
			return;
		}

		final IModelCacheInvalidationService cacheInvalidationService = Services.get(IModelCacheInvalidationService.class);
		cacheInvalidationService.invalidate(CacheInvalidateMultiRequest.of(requests), ModelCacheInvalidationTiming.CHANGE);
	}

	private static Set<PaymentId> extractPaymentIds(final List<I_C_AllocationLine> lines)
	{
		return lines.stream()
				.map(line -> PaymentId.ofRepoIdOrNull(line.getC_Payment_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static Set<InvoiceId> extractInvoiceIds(final List<I_C_AllocationLine> lines)
	{
		return lines.stream()
				.map(line -> InvoiceId.ofRepoIdOrNull(line.getC_Invoice_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void updateDraftedPaySelectionLinesForInvoiceIds(final Set<InvoiceId> invoiceIds)
	{
		if (invoiceIds.isEmpty())
		{
			return;
		}

		//
		// Retrieve all C_PaySelectionLines which are about invoices from our allocation and which are not already processed.
		// The C_PaySelectionLines will be grouped by C_PaySelection_ID.
		//@formatter:off
		final Collection<List<I_C_PaySelectionLine>> paySelectionLinesGroups =
				Services.get(IPaySelectionDAO.class)
				.queryActivePaySelectionLinesByInvoiceId(invoiceIds)
				.listAndSplit(I_C_PaySelectionLine.class, I_C_PaySelectionLine::getC_PaySelection_ID);
		//@formatter:on

		//
		// Update each C_PaySelectionLines group
		for (final Collection<I_C_PaySelectionLine> paySelectionLines : paySelectionLinesGroups)
		{
			updatePaySelectionLines(paySelectionLines);
		}
	}

	/**
	 * Update all given pay selection lines.
	 * <p>
	 * NOTE: pay selection lines shall ALL be part of the same {@link I_C_PaySelection}.
	 */
	private void updatePaySelectionLines(final Collection<I_C_PaySelectionLine> paySelectionLines)
	{
		// shall not happen
		if (paySelectionLines.isEmpty())
		{
			return;
		}

		// Make sure the C_PaySelection was not already processed.
		// Shall not happen.
		final I_C_PaySelection paySelection = paySelectionLines.iterator().next().getC_PaySelection();
		if (paySelection.isProcessed())
		{
			logger.debug("Skip updating lines because pay selection was already processed: {}", paySelection);
			return;
		}

		//
		// Update all pay selection lines
		final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
		final IPaySelectionUpdater paySelectionUpdater = paySelectionBL.newPaySelectionUpdater();
		paySelectionUpdater
				.setC_PaySelection(paySelection)
				.addPaySelectionLinesToUpdate(paySelectionLines)
				.update();

		logger.debug("Updated {}", paySelectionUpdater.getSummary());
	}
}
