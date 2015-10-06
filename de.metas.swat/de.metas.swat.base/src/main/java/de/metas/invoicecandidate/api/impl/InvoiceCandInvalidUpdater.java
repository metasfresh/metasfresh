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

import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.NullLoggable;
import org.adempiere.util.Services;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;

/* package*/class InvoiceCandInvalidUpdater implements IInvoiceCandInvalidUpdater
{
	// services
	// private final transient CLogger logger = CLogger.getCLogger(getClass());
	private final transient InvoiceCandBL invoiceCandBL;
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	// Parameters
	private Properties _ctx;
	private String _trxName;
	private Boolean _managedTrx;
	private ILoggable loggable = NullLoggable.instance;
	private Integer _recomputeTag;
	private ILock _lockedBy = ILock.NULL;

	public InvoiceCandInvalidUpdater(final InvoiceCandBL invoiceCandBL)
	{
		super();
		this.invoiceCandBL = invoiceCandBL;
	}

	@Override
	public void updateAll(final int adPInstanceId)
	{
		// Tag invoice candidates scheduled to recompute using given "adPInstanceId" as the recompute tag marker
		_recomputeTag = tagToRecompute()
				.setRecomputeTag(adPInstanceId)
				.tagAll();

		updateTagged();
	}

	@Override
	public void update(Iterable<I_C_Invoice_Candidate> candidates)
	{
		Check.assumeNotNull(candidates, "candidates not null");
		update(candidates.iterator());
	}

	@Override
	public void update(final Iterator<I_C_Invoice_Candidate> candidates)
	{
		// Create a recompute tag only for invoice candidates which are scheduled to recompute and are present in our iterator
		_recomputeTag = tagToRecompute()
				.tag(candidates);

		updateTagged();
	}

	private final IInvoiceCandRecomputeTagger tagToRecompute()
	{
		return invoiceCandDAO.tagToRecompute()
				.setContext(getCtx(), getTrxName())
				.setLockedBy(getLockedBy());
	}

	/** Update all invoice candidates which were tagged with {@link #getRecomputeTag()} */
	private final void updateTagged()
	{
		// Validate invoice candidates tagged with our tag
		invoiceCandBL.updateInvalid(this);

		// Remove from "invoice candidates to recompute" all those which were tagged with our tag
		// because now we consider them valid
		final int recomputeTag = getRecomputeTag();
		final String trxName = getTrxName();
		invoiceCandDAO.deleteRecomputeMarkers(recomputeTag, trxName);
	}

	@Override
	public IInvoiceCandInvalidUpdater setContext(final Properties ctx, final String trxName)
	{
		_ctx = ctx;
		_trxName = trxName;
		return this;
	}

	@Override
	public IInvoiceCandInvalidUpdater setContext(final IContextAware context)
	{
		Check.assumeNotNull(context, "context not null");
		return setContext(context.getCtx(), context.getTrxName());
	}

	/* package */final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	/* package */final String getTrxName()
	{
		return _trxName;
	}

	@Override
	public IInvoiceCandInvalidUpdater setManagedTrx(final boolean managedTrx)
	{
		_managedTrx = managedTrx;
		return this;
	}

	/* package */final boolean isManagedTrx()
	{
		Check.assumeNotNull(_managedTrx, "_managedTrx not null");
		return _managedTrx;
	}

	@Override
	public IInvoiceCandInvalidUpdater setLoggable(final ILoggable loggable)
	{
		Check.assumeNotNull(loggable, "loggable not null");
		this.loggable = loggable;
		return this;
	}

	/* package */ILoggable getLoggable()
	{
		return loggable;
	}

	/* package */int getRecomputeTag()
	{
		Check.assumeNotNull(_recomputeTag, "_recomputeTag not null");
		return _recomputeTag;
	}

	@Override
	public IInvoiceCandInvalidUpdater setLockedBy(final ILock lockedBy)
	{
		this._lockedBy = lockedBy;
		return this;
	}

	/* package */ILock getLockedBy()
	{
		return _lockedBy;
	}
}
