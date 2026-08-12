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

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Immutable per-organisation VAT-ID check configuration, read from a single active {@code VATaxID_Config}
 * record (see {@code de.metas.vatid.VATaxIDConfigRepository}).
 *
 * <p>Field meanings:
 * <ul>
 *   <li>{@link #isFormatCheckEnabled()} — run the local structure + check-digit validation.</li>
 *   <li>{@link #isVIESCheckEnabled()} — run the online VIES check.</li>
 *   <li>{@link #getRestApiBaseURL()} — the VIES endpoint.</li>
 *   <li>{@link #getRequesterMemberStateCode()} / {@link #getRequesterNumber()} — our own VAT-ID, sent so
 *       VIES returns the consultation number.</li>
 *   <li>{@link #getRecheckAfterDays()} — how long a successful result stays good.</li>
 *   <li>{@link #getOnServiceUnavailable()} — what an unreachable service means once the last result is
 *       older than {@link #getRecheckAfterDays()}.</li>
 * </ul>
 */
@Value
@Builder
public class VATaxIDConfig
{
	@NonNull VATaxIDConfigId id;

	boolean formatCheckEnabled;
	boolean viesCheckEnabled;

	@Nullable String restApiBaseURL;
	@Nullable String requesterMemberStateCode;
	@Nullable String requesterNumber;

	int recheckAfterDays;

	@NonNull VATaxIDOnServiceUnavailableAction onServiceUnavailable;
}
