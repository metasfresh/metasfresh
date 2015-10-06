package de.metas.invoicecandidate.api;

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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;

/**
 * Updates {@link I_C_Invoice_Candidate}s which are scheduled to be recomputed.
 * 
 * @author tsa
 *
 */
public interface IInvoiceCandInvalidUpdater
{
	/**
	 * Update given invoice candidates (which were scheduled to be recomputed).
	 *
	 * NOTEs:
	 * <ul>
	 * <li>only those candidates will be updated that were previously invalidated
	 * <li>If some candidates will be updated in database, they need to be refreshed by caller method if caller method wants to use them subsequently (i.e. use
	 * {@link InterfaceWrapperHelper#refresh(Object)}). That's because the implementation won't actually work with the instances from the provided iterator
	 * </ul>
	 * 
	 * @param candidates
	 */
	void update(final Iterator<I_C_Invoice_Candidate> candidates);

	/**
	 * 
	 * @param candidates
	 * @see #update(Iterator)
	 */
	void update(final Iterable<I_C_Invoice_Candidate> candidates);

	/**
	 * Updates all candidates (which were scheduled to be recomputed)
	 *
	 * @param adPInstanceId the "recompute marker"
	 */
	void updateAll(final int adPInstanceId);

	IInvoiceCandInvalidUpdater setContext(final Properties ctx, final String trxName);

	IInvoiceCandInvalidUpdater setContext(IContextAware context);

	/**
	 * @param managedTrx if false, then this updater will commit the given trx between the different work steps. Use managedTrx=true only if you know that there are few candidates to update
	 */
	IInvoiceCandInvalidUpdater setManagedTrx(final boolean managedTrx);

	IInvoiceCandInvalidUpdater setLoggable(final ILoggable loggable);

	/**
	 * @param lockedBy <ul>
	 *            <li>if not null it will consider only those records which are locked by given {@link ILock}
	 *            <li>if null, it will consider only NOT locked records
	 *            </ul>
	 */
	IInvoiceCandInvalidUpdater setLockedBy(ILock lockedBy);
}
