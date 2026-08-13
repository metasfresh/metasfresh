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
import org.adempiere.ad.trx.api.ITrxManager;
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
 *
 * <p>Deliberately does <b>not</b> read {@code Env.getCtx()} itself: a shared {@code @Component} reading
 * ambient thread-local context is exactly what the service-injection rule on {@code Env.get*} forbids.
 * The caller — each interceptor's {@code @ModelChange} method, the actual near-user save-time boundary —
 * resolves the acting {@code AD_Session_ID} and passes it in as a plain {@code Integer}.
 */
@Component
@RequiredArgsConstructor
public class VATaxIDCheckTrigger
{
	private static final Logger logger = LogManager.getLogger(VATaxIDCheckTrigger.class);

	@NonNull private final VATaxIDCheckService checkService;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Schedules the check of {@code vataxIDValue} for the given partner/location once the current save
	 * commits.
	 *
	 * @param bpartnerId the partner the VAT-ID lives on
	 * @param bpartnerLocationId the location the VAT-ID lives on, or {@code null} when it lives on the
	 * partner header
	 * @param vataxIDValue the new value of the {@code VATaxID} column as it was saved
	 * @param adSessionId the acting {@code AD_Session_ID}, already resolved by the caller (or {@code null}
	 * if there is none) — see the class javadoc for why this trigger never resolves it itself
	 */
	public void scheduleCheckAfterCommit(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@Nullable final String vataxIDValue,
			@Nullable final Integer adSessionId)
	{
		final VATIdentifier vataxID = VATIdentifier.ofNullable(vataxIDValue);
		if (vataxID == null)
		{
			// A cleared VAT-ID (saved as null/blank) has nothing to check — VATaxIDCheckRequest#getVataxID()
			// is @NonNull, so a blank value must never reach it. Not an error: clearing the VAT-ID is a
			// legitimate save (e.g. correcting a wrongly-entered value), not a save that failed validation.
			return;
		}

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
			// Without this catch, a throwing online checker — or a
			// malformed VAT-ID that only this format re-check rejects — would propagate straight back into
			// the caller's save and fail it, even though the row is already committed.
			Loggables.withWarnLoggerToo(logger)
					.addLog("VAT-ID check for bpartnerId={}, bpartnerLocationId={}, VATaxID={} failed after commit: {}",
							bpartnerId, bpartnerLocationId, vataxID.getAsString(), ex.getMessage());
		}
	}
}
