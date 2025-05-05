package de.metas.invoicecandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.CompareToBuilder;

import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Check;
import de.metas.util.collections.IdentityHashSet;
import lombok.NonNull;

public class InvoiceCandAggregateImpl implements IInvoiceCandAggregate
{

	private final Map<Integer, I_C_Invoice_Candidate> candIDs2Cands = new HashMap<>();

	private static final Comparator<I_C_Invoice_Candidate> invoiceCandComparator =
			(ic1, ic2) -> {
				final int ic1EffectiveLine = ic1.getLine() == 0 ? Integer.MAX_VALUE : ic1.getLine();
				final int ic2EffectiveLine = ic2.getLine() == 0 ? Integer.MAX_VALUE : ic2.getLine();

				return new CompareToBuilder()
						.append(ic1EffectiveLine, ic2EffectiveLine)
						.append(ic1.getC_Invoice_Candidate_ID(), ic2.getC_Invoice_Candidate_ID())
						.build();
			};

	/**
	 * Sorted set of all candidates that are added to this aggregation. They are ordered by <code>Line</code> and, if the line is identical, by <code>C_Invoice_Candidate_ID</code>.
	 * If the line is 0, then they shall be ordered to the end.
	 */
	private final Set<I_C_Invoice_Candidate> allCands = new TreeSet<>(invoiceCandComparator);

	private final Set<IInvoiceLineRW> allLines = new IdentityHashSet<>();

	private final Map<IInvoiceLineRW, List<Integer>> line2candidates = new IdentityHashMap<>();

	private final Map<Integer, List<IInvoiceLineRW>> candidateId2lines = new HashMap<>();

	private final Map<Integer, Map<IInvoiceLineRW, StockQtyAndUOMQty>> candIdAndLine2AllocatedQty = new HashMap<>();

	/**
	 * Returns all candidates that were added, ordered by line (1, 2, ... 10, 20, ... , 0), i.e. null/not-set last.
	 */
	@Override
	public Collection<I_C_Invoice_Candidate> getAllCands()
	{
		return allCands;
	}

	@Override
	public List<I_C_Invoice_Candidate> getCandsFor(final IInvoiceLineRW il)
	{
		final List<I_C_Invoice_Candidate> result = new ArrayList<>();
		for (final int candId : getCandIdsFor(il))
		{
			result.add(candIDs2Cands.get(candId));
		}
		return result;
	}

	private List<Integer> getCandIdsFor(final IInvoiceLineRW il)
	{
		return line2candidates.get(il);
	}

	@Override
	public Collection<IInvoiceLineRW> getAllLines()
	{
		return allLines;
	}

	@Override
	public List<IInvoiceLineRW> getLinesFor(final I_C_Invoice_Candidate ic)
	{
		final boolean mandatory = true;
		return getLinesFor(ic, ic.getC_Invoice_Candidate_ID(), mandatory);
	}

	@Override
	public List<IInvoiceLineRW> getLinesForOrEmpty(final I_C_Invoice_Candidate ic)
	{
		final boolean mandatory = false;
		return getLinesFor(ic, ic.getC_Invoice_Candidate_ID(), mandatory);
	}

	/**
	 * This method does the actual work for {@link #getLinesFor(I_C_Invoice_Candidate)}.
	 * <p>
	 * Note that this method with the extra 'icId' parameter is here because I was not able to write unit tests with mocks of I_C_Invoice_Candidate that returned the correct C_Invoice_Candidate_ID
	 * value. In the end I decided to give the C_Invoice_Candidate_ID value as another parameter.
	 *
	 * @param icId the value that would be returned by <code>ic.C_Invoice_Candidate_ID()</code> if the method was called with a proper <code>I_C_Invoice_Candidate</code>.
	 * @param mandatory if true an {@link IllegalArgumentException} is no {@link IInvoiceLineRW} were matched
	 */
	List<IInvoiceLineRW> getLinesFor(final I_C_Invoice_Candidate ic, final int icId, final boolean mandatory)
	{
		Check.assume(ic != null, "Param 'ic' is not null");
		Check.assume(icId > 0, "Param 'ic' has C_Invoice_Candidate_ID>0");

		final List<IInvoiceLineRW> invoiceLines = candidateId2lines.get(icId);
		if (invoiceLines == null)
		{
			if (mandatory)
			{
				throw new IllegalArgumentException(ic + " has not been added to this instance");
			}
			else
			{
				return Collections.emptyList();
			}
		}
		return new ArrayList<>(invoiceLines);
	}

	@Override
	public final boolean addAssociationIfNotExists(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final IInvoiceLineRW il,
			@NonNull final StockQtyAndUOMQty qtysInvoiced)
	{
		// Check if association already exists
		if (isAssociated(ic, il))
		{
			return false;
		}

		// Add association
		addAssociation(ic, il, qtysInvoiced);
		return true;
	}


	@Override
	public void addAssociation(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final IInvoiceLineRW il,
			@NonNull final StockQtyAndUOMQty qtysInvoice)
	{
		addAssociation(ic, ic.getC_Invoice_Candidate_ID(), il, qtysInvoice);
	}

	/**
	 * This method does the actual work for {@link #addAssociation(I_C_Invoice_Candidate, IInvoiceLineRW, StockQtyAndUOMQty)}.
	 * <p>
	 * Note that this method with the extra 'icId' parameter is here because I was not able to write unit tests with mocks of I_C_Invoice_Candidate that returned the correct C_Invoice_Candidate_ID
	 * value. In the end I decided to give the C_Invoice_Candidate_ID value as another parameter.
	 *
	 * @param icId the value that would be returned by <code>ic.C_Invoice_Candidate_ID()</code> if the method was called with a proper <code>I_C_Invoice_Candidate</code>.
	 * @see IInvoiceCandAggregate#addAssociation(I_C_Invoice_Candidate, IInvoiceLineRW, StockQtyAndUOMQty)
	 */
	private void addAssociation(
			@NonNull final I_C_Invoice_Candidate ic,
			final int icId,
			@NonNull final IInvoiceLineRW il,
			@NonNull final StockQtyAndUOMQty allocatedQty)
	{
		Check.assume(icId > 0, "Param 'ic' has C_Invoice_Candidate_ID>0");

		Check.assume(!isAssociated(icId, il), ic + " with ID=" + icId + " is not yet associated with " + il);

		final List<Integer> candidateIds = line2candidates.computeIfAbsent(il, k -> new ArrayList<>());
		candidateIds.add(icId);

		candIDs2Cands.put(icId, ic);
		allCands.add(ic);

		final List<IInvoiceLineRW> invoiceLines = candidateId2lines.computeIfAbsent(icId, k -> new ArrayList<>());

		if (!invoiceLines.contains(il))
		{
			invoiceLines.add(il);
		}

		allLines.add(il);

		Map<IInvoiceLineRW, StockQtyAndUOMQty> il2Qty = candIdAndLine2AllocatedQty.get(icId);
		if (il2Qty == null)
		{
			// it's important to have an IdentityHashMap, because we don't guarantee that IInvoiceLineRW is immutable!
			il2Qty = new IdentityHashMap<>();
			candIdAndLine2AllocatedQty.put(icId, il2Qty);
		}
		il2Qty.put(il, allocatedQty);
	}

	@Override
	public final void addAllocatedQty(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final IInvoiceLineRW il,
			final StockQtyAndUOMQty qtyAllocatedToAdd)
	{
		final int icId = ic.getC_Invoice_Candidate_ID();
		Check.assume(icId > 0, "Param 'ic' has C_Invoice_Candidate_ID>0");
		Check.assumeNotNull(qtyAllocatedToAdd, "qtyAllocatedToAdd not null");

		final Map<IInvoiceLineRW, StockQtyAndUOMQty> il2Qty = candIdAndLine2AllocatedQty.get(icId);
		Check.assumeNotNull(il2Qty, "{} has no associations to invoice lines yet", ic);

		final StockQtyAndUOMQty qtyAllocatedOld = il2Qty.get(il);
		Check.assumeNotNull(qtyAllocatedOld, "{} has no associations to {}", il, ic);

		final StockQtyAndUOMQty qtyAllocatedNew = qtyAllocatedOld.add(qtyAllocatedToAdd);
		il2Qty.put(il, qtyAllocatedNew);
	}

	@Override
	public boolean isAssociated(
			final I_C_Invoice_Candidate ic,
			final IInvoiceLineRW il)
	{
		return isAssociated(ic.getC_Invoice_Candidate_ID(), il);
	}

	private boolean isAssociated(
			final int icId,
			final IInvoiceLineRW il)
	{
		return candIdAndLine2AllocatedQty.containsKey(icId) && candIdAndLine2AllocatedQty.get(icId).containsKey(il);
	}

	@Override
	public StockQtyAndUOMQty getAllocatedQty(@NonNull final I_C_Invoice_Candidate ic, @NonNull final IInvoiceLineRW il)
	{
		return getAllocatedQty(ic, ic.getC_Invoice_Candidate_ID(), il);
	}

	/**
	 * This method does the actual work for {@link #getAllocatedQty(I_C_Invoice_Candidate, IInvoiceLineRW)}. For an explanation of why the method is here, see
	 * {@link #addAssociation(I_C_Invoice_Candidate, int, IInvoiceLineRW, StockQtyAndUOMQty)}.
	 */
	StockQtyAndUOMQty getAllocatedQty(final I_C_Invoice_Candidate ic, final int icId, final IInvoiceLineRW il)
	{
		Check.assume(isAssociated(icId, il), ic + " with ID=" + icId + " is associated with " + il);

		return candIdAndLine2AllocatedQty.get(icId).get(il);
	}

	@Override
	public String toString()
	{
		return "InvoiceCandAggregateImpl [allLines=" + allLines + "]";
	}

	@Override
	public void negateLineAmounts()
	{
		for (final IInvoiceLineRW line : getAllLines())
		{
			line.negateAmounts();
		}
	}
}
