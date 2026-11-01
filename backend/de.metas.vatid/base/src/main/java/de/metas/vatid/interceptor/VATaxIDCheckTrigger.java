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
import de.metas.organization.OrgId;
import de.metas.tax.api.VATIdentifier;
import de.metas.vatid.VATaxIDCheckService;
import de.metas.vatid.async.VATaxIDCheckWorkpackageProcessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
 * {@code AD_Session_ID} and passes it in, and likewise the {@code AD_Org_ID} of the record being saved.
 *
 * <p><b>The organisation's {@code IsVIESCheckEnabled} is consulted here, at ENQUEUE time.</b> That is the
 * moment the save happens, and the policy in force when the user saves is the one that should govern that
 * save — the property the synchronous predecessor had for free, and that moving the check into a work
 * package would otherwise lose: {@code VATaxIDCheckService} re-reads the same flag when the package is
 * PROCESSED, which can be much later and under a configuration nobody had switched on yet at save time.
 * It also stops the shipped default (VIES off, see migration {@code 5819340}) from queueing one guaranteed
 * no-op work package per VAT-ID save on every installation.
 *
 * <p>{@code VATaxIDCheckService#check} keeps its own gate, and must: the {@code C_BPartner_VATaxID_Check}
 * process does not come through here at all, so that gate is the only one on the process path — and on this
 * path a second reading costs nothing.
 *
 * <p><b>Residual disagreement between the two readings, accepted.</b> The flag is read here and read again
 * when the package is processed, and the two can differ. Because BOTH gates must pass, that can only ever
 * SUPPRESS a check, never add one: a configuration switched off in between makes
 * {@code VATaxIDCheckService#check} return before it writes anything or calls the service, so the queued
 * package becomes a silent no-op — no online call, no {@code VATaxID_CheckLog} row, the stored status
 * untouched. What is lost in that window is a check the save-time policy had authorised; what cannot happen
 * is a check it had declined, which is the direction that matters and the one this class exists to close.
 * Making the enqueue-time verdict final — carrying it inside the work package and having the processor
 * refuse to re-read — would buy that window at the price of a process path with no gate at all, and of
 * checks that ignore a configuration switched off while they were queued.
 */
@Component
@RequiredArgsConstructor
public class VATaxIDCheckTrigger
{
	@NonNull private final VATaxIDCheckService checkService;

	/**
	 * Schedules the check of {@code vataxIDValue} for the given partner/location once the current save
	 * commits.
	 *
	 * @param orgId the organisation of the record being saved, resolved by the caller from that record —
	 * see the class javadoc for why this trigger reads no ambient context of its own
	 * @param bpartnerId the partner the VAT-ID lives on
	 * @param bpartnerLocationId the location the VAT-ID lives on, or {@code null} when it lives on the
	 * partner header
	 * @param vataxIDValue the new value of the {@code VATaxID} column as it was saved
	 * @param adSessionId the acting session, already resolved by the caller — see the class javadoc for why
	 * this trigger never resolves it itself
	 */
	public void scheduleCheckAfterCommit(
			@NonNull final OrgId orgId,
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

		if (!checkService.isViesCheckEnabled(orgId))
		{
			// The organisation has the online check switched off, so this save is not to be checked at all
			// — see the class javadoc for why that verdict is reached here and not left to the processor.
			return;
		}

		// Enqueued rather than run here. bindToThreadInheritedTrx means the work package materialises only
		// if this save actually commits, so the previous runAfterCommit guarantee is kept while the wait on
		// a third party moves off the saving thread.
		VATaxIDCheckWorkpackageProcessor.enqueueOnTrxCommit(bpartnerId, bpartnerLocationId, vataxID, adSessionId);
	}
}
