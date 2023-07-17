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

import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

@ToString(exclude = { "_ctx", "invoiceCandDAO" })
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

	@Nullable
	private InvoiceCandRecomputeTag _taggedWith = null;
	private int _limit = -1;
	private InvoiceCandidateIdsSelection onlyInvoiceCandidateIds = null;

	//
	// State
	private InvoiceCandRecomputeTag _recomputeTag;

	public InvoiceCandRecomputeTagger(@NonNull final InvoiceCandDAO invoiceCandDAO)
	{
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
		invoiceCandDAO.deleteRecomputeMarkersAndInvalidateCache(this, null /*null means "ALL"*/);
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
		return invoiceCandDAO.fetchInvalidInvoiceCandidates(ctx, recomputeTag, trxName);
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

	private void generateRecomputeTagIfNotSet()
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

	/**
	 * @return recompute tag; never returns null
	 */
	final InvoiceCandRecomputeTag getRecomputeTag()
	{
		Check.assumeNotNull(_recomputeTag, "_recomputeTag not null");
		return _recomputeTag;
	}

	@Override
	public InvoiceCandRecomputeTagger setLockedBy(final ILock lockedBy)
	{
		this._lockedBy = lockedBy;
		return this;
	}

	/* package */ILock getLockedBy()
	{
		return _lockedBy;
	}

	@Override
	public InvoiceCandRecomputeTagger setTaggedWith(@Nullable final InvoiceCandRecomputeTag tag)
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

	/* package */
	@Nullable
	InvoiceCandRecomputeTag getTaggedWith()
	{
		return _taggedWith;
	}

	@Override
	public InvoiceCandRecomputeTagger setLimit(final int limit)
	{
		this._limit = limit;
		return this;
	}

	/* package */int getLimit()
	{
		return _limit;
	}

	@Override
	public void setOnlyInvoiceCandidateIds(@NonNull final InvoiceCandidateIdsSelection onlyInvoiceCandidateIds)
	{
		this.onlyInvoiceCandidateIds = onlyInvoiceCandidateIds;
	}

	@Override
	@Nullable
	public final InvoiceCandidateIdsSelection getOnlyInvoiceCandidateIds()
	{
		return onlyInvoiceCandidateIds;
	}
}
