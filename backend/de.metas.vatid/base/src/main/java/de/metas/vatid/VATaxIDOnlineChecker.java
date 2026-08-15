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
 * Checks a VAT-ID against an external validation service. The base half declares only this seam; the
 * implementation lives in the {@code vies} half, so nothing in the base half depends on the transport or on
 * the VIES response format.
 *
 * <p>An implementation reports what the service said and nothing more: {@link VATaxIDStatus#Valid} /
 * {@link VATaxIDStatus#Invalid} when it answered, {@link VATaxIDStatus#NotSupported} for a country outside
 * its scope, {@link VATaxIDStatus#ServiceUnavailable} when it could not answer. Never
 * {@link VATaxIDStatus#RequestSent} (a log-row state, not an outcome) nor {@link VATaxIDStatus#NotChecked}.
 *
 * <p><b>The service-unavailable policy is not the checker's business</b> —
 * {@link VATaxIDConfig#getOnServiceUnavailable()} is applied by the calling service. A checker applying it
 * itself would make an unreachable service indistinguishable from one that answered "invalid", and the two
 * have opposite consequences for a partner's tax certificate.
 *
 * <p>A service-side or transport problem is returned as {@code ServiceUnavailable}, never thrown: a failed
 * check is a recordable outcome that must reach the check log. Throwing is reserved for programming errors.
 */
public interface VATaxIDOnlineChecker
{
	/**
	 * @param vatId  the VAT-ID to check. NOT pre-filtered by the offline format gate:
	 *               {@link VATaxIDValidationUtil} deliberately accepts null and unsupported prefixes.
	 * @param config supplies the service base URL and the requester identity — without the latter the
	 *               service returns an empty request identifier, which is the archivable evidence that a
	 *               check happened at all.
	 * @return the outcome, with {@code requestIdentifier} and {@code rawResponse} populated whenever the
	 *         service supplied them, including on {@code Invalid}.
	 */
	VATaxIDCheckResult check(@NonNull VATIdentifier vatId, @NonNull VATaxIDConfig config);

	/**
	 * The country codes the service currently reports itself unable to answer for. Asked once per run, not
	 * once per VAT-ID: a member state can be down while the service itself is up, and discovering that
	 * per-partner would burn a whole batch marking that country {@code ServiceUnavailable}.
	 *
	 * <p>An empty set means "nothing known to be down" — also what an unreachable availability endpoint
	 * yields, deliberately, so it cannot silently suppress every check. An implementation that learns the
	 * service is down as a whole returns every country it covers, so the caller skips the run entirely.
	 */
	ImmutableSet<String> getUnavailableCountryCodes(@NonNull VATaxIDConfig config);
}
