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

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * Helper used to tag {@link I_C_Invoice_Candidate} scheduled to recompute with a given tag or a newly generated tag.
 * 
 * The tag is later used to distinguish these records from other's that were inserted meanwhile.
 * 
 * @author tsa
 *
 */
public interface IInvoiceCandRecomputeTagger
{
	/**
	 * Tag all invoice candidates which were scheduled to be recomputed.
	 * 
	 * If the recompute tag is not set, a new one will be generated.
	 * 
	 * @return recompute tag
	 */
	InvoiceCandRecomputeTag tag();

	/**
	 * Un-tag all those which were previously tagged by {@link #tag()} and which were not already deleted.
	 * 
	 * @return how many candidates were un-tagged.
	 */
	int untag();

	/**
	 * 
	 * @return how many invoice candidates will be tagged by {@link #tag()} method.
	 */
	int countToBeTagged();

	/**
	 * Delete all invoice candidates schedulers for recompute of current tag.
	 */
	void deleteAllTaggedAndInvalidateCache();

	/**
	 * Delete given invoice candidates which were scheduled for recompute and are tagged with current tag.
	 */
	void deleteTaggedAndInvalidateCache(Collection<Integer> invoiceCandidateIds);

	/**
	 * Retrieves tagged invoice candidates.
	 * 
	 * This method assumes {@link #tag()} was called before.
	 */
	Iterator<I_C_Invoice_Candidate> retrieveInvoiceCandidates();

	/** @param recomputeTag recompute tag to be used */
	IInvoiceCandRecomputeTagger setRecomputeTag(final InvoiceCandRecomputeTag recomputeTag);

	IInvoiceCandRecomputeTagger setContext(final Properties ctx, final String trxName);

	Properties getCtx();

	/**
	 * @param lockedBy <ul>
	 *            <li>if not null it will consider only those records which are locked by given {@link ILock}
	 *            <li>if null, it will consider only NOT locked records
	 *            </ul>
	 */
	IInvoiceCandRecomputeTagger setLockedBy(ILock lockedBy);

	/**
	 * Consider only those invoice candidates which were tagged with given tag.
	 */
	IInvoiceCandRecomputeTagger setTaggedWith(InvoiceCandRecomputeTag tag);

	/**
	 * Consider only those invoice candidates which were not tagged.
	 */
	IInvoiceCandRecomputeTagger setTaggedWithNoTag();

	/**
	 * Consider any invalid invoice candidate, no matter if they are tagged or not.
	 *
	 * NOTE:
	 * <ul>
	 * <li>this is the default behavior if no setTaggedWith methods are called.
	 * </ul>
	 */
	IInvoiceCandRecomputeTagger setTaggedWithAnyTag();

	/**
	 * Sets maximum number of invoice candidates to tag.
	 */
	IInvoiceCandRecomputeTagger setLimit(int limit);

	void setOnlyInvoiceCandidateIds(@NonNull InvoiceCandidateIdsSelection onlyInvoiceCandidateIds);

	@Nullable
	InvoiceCandidateIdsSelection getOnlyInvoiceCandidateIds();
}
