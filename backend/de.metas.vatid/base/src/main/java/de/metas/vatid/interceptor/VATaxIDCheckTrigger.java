/*
 * #%L
 * de.metas.vatid
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.vatid.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.tax.api.VATIdentifier;
import de.metas.vatid.async.VATaxIDCheckWorkpackageProcessor;
import lombok.NonNull;
import org.adempiere.ad.session.AdSessionId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Schedules the online VAT-ID check to run <b>after</b> the current save
 * commits, called from both {@link C_BPartner} and {@link C_BPartner_Location} so the two interceptors
 * cannot drift apart in how they schedule, capture or fail.
 *
 * <p>Kept separate from both: it owns only <em>when</em> and <em>as whom</em> a check is scheduled from a
 * save — the interceptors know just their own table, and {@code VATaxIDCheckService} knows just how to run
 * one check.
 *
 * <p>Deliberately does not read {@code Env.getCtx()} itself — a shared {@code @Component} reading ambient
 * thread-local context is what the service-injection rule forbids. Each interceptor resolves the acting
 * {@code AD_Session_ID} and passes it in.
 */
@Component
public class VATaxIDCheckTrigger
{
	/**
	 * Schedules the check of {@code vataxIDValue} for the given partner/location once the current save
	 * commits.
	 *
	 * @param bpartnerId the partner the VAT-ID lives on
	 * @param bpartnerLocationId the location the VAT-ID lives on, or {@code null} when it lives on the
	 * partner header
	 * @param vataxIDValue the new value of the {@code VATaxID} column as it was saved
	 * @param adSessionId the acting session, already resolved by the caller — see the class javadoc for why
	 * this trigger never resolves it itself
	 */
	public void scheduleCheckAfterCommit(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@Nullable final String vataxIDValue,
			@NonNull final AdSessionId adSessionId)
	{
		final VATIdentifier vataxID = VATIdentifier.ofNullable(vataxIDValue);
		if (vataxID == null)
		{
			// A cleared VAT-ID (saved as null/blank) has nothing to check — VATaxIDCheckRequest#getVataxID()
			// is @NonNull, so a blank value must never reach it. Not an error: clearing the VAT-ID is a
			// legitimate save (e.g. correcting a wrongly-entered value), not a save that failed validation.
			return;
		}

		// Enqueued rather than run here. bindToThreadInheritedTrx means the work package materialises only
		// if this save actually commits, so the previous runAfterCommit guarantee is kept while the wait on
		// a third party moves off the saving thread.
		VATaxIDCheckWorkpackageProcessor.enqueueOnTrxCommit(bpartnerId, bpartnerLocationId, vataxID, adSessionId);
	}
}
