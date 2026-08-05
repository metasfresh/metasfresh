/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.currencyconversion;

import de.pentabyte.springfox.ApiEnum;

/**
 * Top-level aggregate outcome of a conversion-rate batch upsert, computed over the per-item outcomes
 * ({@link JsonResponseConversionRateUpsertItem.SyncOutcome}). An item counts as "failed" iff its per-item
 * outcome is {@code ERROR} ({@code NOTHING_DONE} counts as applied, not failed).
 * <ul>
 *     <li>{@link #SUCCESS} — no item failed (including the degenerate empty batch); HTTP 200.</li>
 *     <li>{@link #PARTIAL_SUCCESS} — some items failed but not all; HTTP 200.</li>
 *     <li>{@link #ERROR} — every item failed (non-empty batch); HTTP 422.</li>
 * </ul>
 */
public enum BatchSyncOutcome
{
	@ApiEnum("No record failed; the batch fully applied (also the degenerate empty batch).")
	SUCCESS,

	@ApiEnum("Some records applied and at least one failed; the failed records carry a per-item ERROR outcome.")
	PARTIAL_SUCCESS,

	@ApiEnum("No record was applied; every record failed (the response reports the per-record outcomes).")
	ERROR
}
