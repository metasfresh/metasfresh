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

import java.time.Instant;

/**
 * What is known about one VAT-ID value from a single {@code VATaxID_CheckLog} row: the status, when it was
 * obtained, and which row is the evidence.
 *
 * <p>These three and no more — exactly what the de-duplication lookup and the denormalised parent columns
 * need.
 */
@Value
@Builder
public class VATaxIDLastCheck
{
	@NonNull VATaxIDCheckLogId checkLogId;

	@NonNull VATaxIDStatus status;

	/**
	 * When the outcome was learned: the row's {@code ResponseDate}, falling back to its {@code RequestDate}
	 * for a row that carries none.
	 */
	@NonNull Instant checkedAt;
}
