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
 * The outcome of a VAT-ID check, used to complete a {@code VATaxID_CheckLog} row that is at
 * {@link VATaxIDStatus#RequestSent} — see
 * {@link VATaxIDCheckRepository#completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)}.
 *
 * <p>{@link #getStatus()} must be a final status, i.e. anything other than
 * {@link VATaxIDStatus#RequestSent} — that value only ever exists on the row being completed, not as
 * an outcome to write.
 */
@Value
@Builder
public class VATaxIDCheckResult
{
	@NonNull VATaxIDStatus status;

	@Nullable String requestIdentifier;

	@Nullable String rawResponse;
}
