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
 * record by {@link VATaxIDConfigRepository}.
 *
 * <p>The two requester fields are our own VAT-ID, sent so VIES returns a consultation number;
 * {@link #getOnServiceUnavailable()} applies only once the last result is older than
 * {@link #getRecheckAfterDays()}.
 */
@Value
@Builder
public class VATaxIDConfig
{
	/**
	 * {@code null} on the synthetic configuration an organisation without a {@code VATaxID_Config} record
	 * effectively has — there is no record to point at. Every configuration read from an actual record
	 * carries its id; see {@code VATaxIDCheckService}, the single place that resolves that default.
	 */
	@Nullable VATaxIDConfigId id;

	boolean formatCheckEnabled;
	boolean viesCheckEnabled;

	@Nullable String restApiBaseURL;
	@Nullable String requesterMemberStateCode;
	@Nullable String requesterNumber;

	int recheckAfterDays;

	@NonNull VATaxIDOnServiceUnavailableAction onServiceUnavailable;
}
