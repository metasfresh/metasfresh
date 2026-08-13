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

package de.metas.vatid;

import com.google.common.collect.ImmutableSet;
import de.metas.tax.api.VATIdentifier;
import lombok.NonNull;

/**
 * Checks a VAT-ID against an external validation service.
 *
 * <p>The base half declares only this seam; the implementation lives in the {@code vies} half, so
 * nothing in the base half — interceptors, the check service, tax determination — depends on the
 * transport or on the VIES response format.
 *
 * <h2>What an implementation reports, and what it must NOT decide</h2>
 *
 * An implementation reports <em>what the service said</em> and nothing more:
 *
 * <ul>
 *     <li>{@link VATaxIDStatus#Valid} / {@link VATaxIDStatus#Invalid} — the service answered.</li>
 *     <li>{@link VATaxIDStatus#NotSupported} — the VAT-ID's country is outside the service's scope,
 *         so no statement about validity is available.</li>
 *     <li>{@link VATaxIDStatus#ServiceUnavailable} — the service could not answer: a member state
 *         reported as unavailable, a transport failure, a timeout, an unusable response.</li>
 * </ul>
 *
 * It must never return {@link VATaxIDStatus#RequestSent} (that value exists only on the log row
 * being completed, never as an outcome) nor {@link VATaxIDStatus#NotChecked} (the absence of a
 * check, not the result of one).
 *
 * <p><b>The service-unavailable POLICY is not the checker's business.</b>
 * {@link VATaxIDConfig#getOnServiceUnavailable()} decides whether an unreachable service is recorded
 * as {@code ServiceUnavailable} or treated as {@code Invalid}; applying that belongs to the calling
 * service, which owns the config. A checker that applied it itself would make an unreachable service
 * indistinguishable from a service that answered "invalid" — and the two have opposite consequences
 * for a partner's tax certificate.
 *
 * <h2>Failure mode</h2>
 *
 * An implementation does not throw for a service-side or transport problem: it returns
 * {@code ServiceUnavailable}, because a failed check is a recordable outcome that must reach the
 * check log as evidence. Throwing is reserved for programming errors (a null argument, a
 * misconfigured base URL that cannot form a request at all).
 */
public interface VATaxIDOnlineChecker
{
	/**
	 * @param vatId  the VAT-ID to check. A caller is expected to have a VAT-ID in hand at all — there is
	 *               nothing to check otherwise. Note this is NOT the offline format gate:
	 *               {@link VATaxIDValidationUtil} deliberately <em>accepts</em> null and any
	 *               unsupported-prefix value, so it neither filters for nor guarantees anything here.
	 * @param config the organisation's configuration, supplying the service base URL and the
	 *               requester identity. The requester fields matter beyond authentication: without
	 *               them the service returns an empty request identifier, and that identifier is the
	 *               archivable evidence a check happened at all.
	 * @return the outcome, with {@code requestIdentifier} and {@code rawResponse} populated whenever
	 *         the service supplied them — including on an {@code Invalid} answer, where the raw
	 *         response is the evidence for removing a tax certificate.
	 */
	VATaxIDCheckResult check(@NonNull VATIdentifier vatId, @NonNull VATaxIDConfig config);

	/**
	 * The country codes the service currently reports itself unable to answer for.
	 *
	 * <p>Separate from {@link #check(VATIdentifier, VATaxIDConfig)} on purpose, and asked <em>once per
	 * run</em> rather than once per VAT-ID: a member state can be down while the service itself is up,
	 * and a batch that discovers this per-partner would burn its whole budget marking that country's
	 * partners {@code ServiceUnavailable}. Callers consult this first and skip the affected countries,
	 * leaving their stored status untouched.
	 *
	 * <p>An empty set means "nothing known to be down" — which is also what a caller gets when the
	 * availability endpoint itself cannot be reached. That is deliberate: an unreachable availability
	 * endpoint must not silently suppress every check, and a genuinely-down member state will surface
	 * again as a {@code ServiceUnavailable} outcome on the individual check.
	 *
	 * <p>Conversely, an implementation that learns the service is down <em>as a whole</em> returns every
	 * country it covers, so the caller skips the run entirely rather than rediscovering the outage once
	 * per VAT-ID.
	 */
	ImmutableSet<String> getUnavailableCountryCodes(@NonNull VATaxIDConfig config);
}
