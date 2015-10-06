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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.util.Check;

import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;

/*package*/class InvoiceCandRecomputeTagger implements IInvoiceCandRecomputeTagger
{
	// services
	private final InvoiceCandDAO invoiceCandDAO;

	private Properties _ctx;
	private String _trxName;
	private Integer _recomputeTag;
	private ILock _lockedBy = null;

	public InvoiceCandRecomputeTagger(final InvoiceCandDAO invoiceCandDAO)
	{
		super();
		Check.assumeNotNull(invoiceCandDAO, "invoiceCandDAO not null");
		this.invoiceCandDAO = invoiceCandDAO;
	}

	@Override
	public int tag(final Iterator<I_C_Invoice_Candidate> candidates)
	{
		Check.assumeNotNull(candidates, "candidates not null");

		generateRecomputeTagIfNotSet();

		while (candidates.hasNext())
		{
			invoiceCandDAO.tagToRecomputeChunk(this, candidates);
		}

		return getRecomputeTag();
	}

	@Override
	public int tagAll()
	{
		generateRecomputeTagIfNotSet();

		invoiceCandDAO.tagAllToRecompute(this);

		return getRecomputeTag();
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
	public IInvoiceCandRecomputeTagger setRecomputeTag(final int adPInstanceId)
	{
		this._recomputeTag = adPInstanceId;
		return this;
	}

	private final void generateRecomputeTagIfNotSet()
	{
		if (_recomputeTag != null)
		{
			return;
		}
		_recomputeTag = invoiceCandDAO.nextRecomputeId();
	}

	int getRecomputeTag()
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
}
