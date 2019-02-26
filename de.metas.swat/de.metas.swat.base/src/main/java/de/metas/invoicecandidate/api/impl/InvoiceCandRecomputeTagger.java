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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

@ToString(exclude= {"_ctx", "invoiceCandDAO"})
/*package*/class InvoiceCandRecomputeTagger implements IInvoiceCandRecomputeTagger
{
	// services
	private final transient InvoiceCandDAO invoiceCandDAO;

	//
	// Parameters
	private Properties _ctx;
	private String _trxName;
	private InvoiceCandRecomputeTag _recomputeTagToUseForTagging;
	private ILock _lockedBy = null;
	private InvoiceCandRecomputeTag _taggedWith = null;
	private int _limit = -1;
	private Set<Integer> onlyC_Invoice_Candidate_IDs = null;

	//
	// State
	private InvoiceCandRecomputeTag _recomputeTag;

	public InvoiceCandRecomputeTagger(final InvoiceCandDAO invoiceCandDAO)
	{
		super();
		Check.assumeNotNull(invoiceCandDAO, "invoiceCandDAO not null");
		this.invoiceCandDAO = invoiceCandDAO;
	}

	@Override
	public InvoiceCandRecomputeTag tag()
	{
		generateRecomputeTagIfNotSet();

		invoiceCandDAO.tagToRecompute(this);

		return getRecomputeTag();
	}

	@Override
	public int countToBeTagged()
	{
		return invoiceCandDAO.countToBeTagged(this);
	}

	@Override
	public void deleteAllTaggedAndInvalidateCache()
	{
		final Collection<Integer> onlyInvoiceCandidateIds = null; // i.e. ALL
		invoiceCandDAO.deleteRecomputeMarkersAndInvalidateCache(this, onlyInvoiceCandidateIds);
	}

	@Override
	public void deleteTaggedAndInvalidateCache(final Collection<Integer> invoiceCandidateIds)
	{
		if (Check.isEmpty(invoiceCandidateIds))
		{
			return;
		}
		invoiceCandDAO.deleteRecomputeMarkersAndInvalidateCache(this, invoiceCandidateIds);
	}

	@Override
	public int untag()
	{
		return invoiceCandDAO.untag(this);
	}

	@Override
	public Iterator<I_C_Invoice_Candidate> retrieveInvoiceCandidates()
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();
		final InvoiceCandRecomputeTag recomputeTag = getRecomputeTag();
		final Iterator<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.fetchInvalidInvoiceCandidates(ctx, recomputeTag, trxName);
		return invoiceCandidates;
	}

	@Override
	public IInvoiceCandRecomputeTagger setContext(final Properties ctx, final String trxName)
	{
		this._ctx = ctx;
		this._trxName = trxName;
		return this;
	}

	@Override
	public final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	String getTrxName()
	{
		return _trxName;
	}

	@Override
	public IInvoiceCandRecomputeTagger setRecomputeTag(final InvoiceCandRecomputeTag recomputeTag)
	{
		this._recomputeTagToUseForTagging = recomputeTag;
		return this;
	}

	private final void generateRecomputeTagIfNotSet()
	{
		// Do nothing if the recompute tag was already generated
		if (!InvoiceCandRecomputeTag.isNull(_recomputeTag))
		{
			return;
		}

		// Use the recompute tag which was suggested
		if (!InvoiceCandRecomputeTag.isNull(_recomputeTagToUseForTagging))
		{
			_recomputeTag = _recomputeTagToUseForTagging;
			return;
		}

		// Generate a new recompute tag
		_recomputeTag = invoiceCandDAO.generateNewRecomputeTag();
	}

	/** @return recompute tag; never returns null */
	final InvoiceCandRecomputeTag getRecomputeTag()
	{
		Check.assumeNotNull(_recomputeTag, "_recomputeTag not null");
		return _recomputeTag;
	}

	@Override
	public InvoiceCandRecomputeTagger setLockedBy(ILock lockedBy)
	{
		this._lockedBy = lockedBy;
		return this;
	}

	/* package */ILock getLockedBy()
	{
		return _lockedBy;
	}

	@Override
	public InvoiceCandRecomputeTagger setTaggedWith(final InvoiceCandRecomputeTag tag)
	{
		_taggedWith = tag;
		return this;
	}

	@Override
	public InvoiceCandRecomputeTagger setTaggedWithNoTag()
	{
		return setTaggedWith(InvoiceCandRecomputeTag.NULL);
	}

	@Override
	public IInvoiceCandRecomputeTagger setTaggedWithAnyTag()
	{
		return setTaggedWith(null);
	}


	/* package */InvoiceCandRecomputeTag getTaggedWith()
	{
		return _taggedWith;
	}

	@Override
	public InvoiceCandRecomputeTagger setLimit(int limit)
	{
		this._limit = limit;
		return this;
	}

	/* package */int getLimit()
	{
		return _limit;
	}

	@Override
	public InvoiceCandRecomputeTagger setOnlyC_Invoice_Candidates(@NonNull final Iterator<? extends I_C_Invoice_Candidate> invoiceCandidates)
	{
		final Set<Integer> invoiceCandidateIds = new HashSet<>();
		while (invoiceCandidates.hasNext())
		{
			final I_C_Invoice_Candidate ic = invoiceCandidates.next();
			final int invoiceCandidateId = ic.getC_Invoice_Candidate_ID();
			if (invoiceCandidateId <= 0)
			{
				continue;
			}

			invoiceCandidateIds.add(invoiceCandidateId);
		}

		this.onlyC_Invoice_Candidate_IDs = ImmutableSet.copyOf(invoiceCandidateIds);

		return this;
	}

	@Override
	public InvoiceCandRecomputeTagger setOnlyC_Invoice_Candidates(@NonNull final Iterable<? extends I_C_Invoice_Candidate> invoiceCandidates)
	{
		return setOnlyC_Invoice_Candidates(invoiceCandidates.iterator());
	}

	@Override
	public boolean isOnlyC_Invoice_Candidate_IDs()
	{
		return onlyC_Invoice_Candidate_IDs != null;
	}

	final Set<Integer> getOnlyC_Invoice_Candidate_IDs()
	{
		return onlyC_Invoice_Candidate_IDs;
	}
}
