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
import de.metas.logging.LogManager;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDCheckRequest;
import de.metas.vatid.VATaxIDCheckService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Schedules a {@link VATaxIDCheckService#check(VATaxIDCheckRequest)} to run <b>after</b> the current save
 * commits — the after-commit half of the feature, called from both {@link C_BPartner} and
 * {@link C_BPartner_Location}'s {@code @ModelChange} methods so the two interceptors cannot drift apart
 * in how they schedule, capture or fail.
 *
 * <p>Named {@code Trigger} rather than folded into either interceptor or into
 * {@link VATaxIDCheckService} itself: it owns exactly one concern — deciding <em>when</em> and <em>as
 * whom</em> a check gets scheduled from a save — which is deliberately separate from the two model
 * interceptors (they only know their own table) and from {@link VATaxIDCheckService} (it only knows how
 * to run one check, not when to schedule one).
 */
@Component
@RequiredArgsConstructor
public class VATaxIDCheckTrigger
{
	private static final Logger logger = LogManager.getLogger(VATaxIDCheckTrigger.class);

	@NonNull private final VATaxIDCheckService checkService;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ISessionBL sessionBL = Services.get(ISessionBL.class);

	/**
	 * Schedules the check of {@code vataxIDValue} for the given partner/location once the current save
	 * commits.
	 *
	 * @param bpartnerId the partner the VAT-ID lives on
	 * @param bpartnerLocationId the location the VAT-ID lives on, or {@code null} when it lives on the
	 * partner header
	 * @param vataxIDValue the new value of the {@code VATaxID} column as it was saved
	 */
	public void scheduleCheckAfterCommit(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@Nullable final String vataxIDValue)
	{
		final VATIdentifier vataxID = VATIdentifier.ofNullable(vataxIDValue);
		if (vataxID == null)
		{
			// A cleared VAT-ID (saved as null/blank) has nothing to check — VATaxIDCheckRequest#getVataxID()
			// is @NonNull, so a blank value must never reach it. Not an error: clearing the VAT-ID is a
			// legitimate save (e.g. correcting a wrongly-entered value), not a save that failed validation.
			return;
		}

		// Captured HERE — inside the interceptor's own model-change callback, i.e. still on the thread that
		// is doing the save — and not inside the after-commit Runnable below. Verified against the actual
		// dispatch code rather than assumed: both paths that ever run this Runnable
		// (TrxListenerManager#fireAfterCommit and AutoCommitTrxListenerManager#execute) invoke it
		// synchronously, on the same thread that triggered the commit, so a lazy read inside the Runnable
		// would see the identical Env.getCtx(). Capturing eagerly is still the safer choice: it records
		// unambiguously "the session that performed the save", before any other code gets a chance to run
		// between commit and the Runnable firing, and it does not silently start depending on same-thread
		// dispatch staying true if that ever changes.
		final MFSession currentSession = sessionBL.getCurrentSession(Env.getCtx());
		final Integer adSessionId = currentSession != null ? currentSession.getAD_Session_ID() : null;

		trxManager.runAfterCommit(() -> runCheckSwallowingExceptions(bpartnerId, bpartnerLocationId, vataxID, adSessionId));
	}

	private void runCheckSwallowingExceptions(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@NonNull final VATIdentifier vataxID,
			@Nullable final Integer adSessionId)
	{
		try
		{
			checkService.check(VATaxIDCheckRequest.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(bpartnerLocationId)
					.vataxID(vataxID)
					.adSessionId(adSessionId)
					.build());
		}
		catch (final Exception ex)
		{
			// This catch is not optional and not cargo-cult: on the AUTOCOMMIT path — no ambient transaction
			// when the triggering save ran, e.g. a save outside any explicit trx — ITrxManager#runAfterCommit
			// resolves to AutoCommitTrxListenerManager, whose registerListener() calls this Runnable
			// SYNCHRONOUSLY and wraps-and-rethrows any exception it throws (see
			// AutoCommitTrxListenerManager#execute). Without this catch, a throwing online checker — or a
			// malformed VAT-ID that only this format re-check rejects — would propagate straight back into
			// the caller's save and fail it, even though the row is already committed. On the transactional
			// path this catch is a no-op safety net: TrxListenerManager#fireAfterCommit already fires
			// listeners with OnError.LogAndSkip, discarding a thrown exception itself. Either way, the save
			// must never fail because of what happens here.
			Loggables.addLog("VAT-ID check for bpartnerId={}, bpartnerLocationId={}, VATaxID={} failed after commit: {}",
					bpartnerId, bpartnerLocationId, vataxID.getAsString(), ex.getMessage());
			logger.warn("VAT-ID after-commit check failed for bpartnerId={}, bpartnerLocationId={}, VATaxID={}",
					bpartnerId, bpartnerLocationId, vataxID.getAsString(), ex);
		}
	}
}
