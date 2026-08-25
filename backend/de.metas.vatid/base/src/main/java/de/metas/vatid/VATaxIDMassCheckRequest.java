/*
 * #%L
 * metasfresh-vatid-base
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

import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * One run of {@link VATaxIDMassCheckService#run(VATaxIDMassCheckRequest)}: check the header VAT-ID (if any)
 * and every location VAT-ID (if any) of every partner in the {@code T_Selection} keyed by
 * {@link #getPinstanceId()}, throttled by {@link #getMaxChecksPerRun()}.
 *
 * <p>Deliberately carries only process-agnostic values — a selection {@link PInstanceId}, a plain
 * {@code int} — so the same run can be driven from a {@code JavaProcess}, a REST endpoint, or a unit test.
 */
@Value
@Builder
public class VATaxIDMassCheckRequest
{
	/**
	 * Empty/unset or {@code <= 0} means no limit — see
	 * {@link VATaxIDMassCheckService#run(VATaxIDMassCheckRequest)}.
	 */
	int maxChecksPerRun;

	/**
	 * The process run this check belongs to. On a user-triggered run it is ALSO the selection key: the
	 * caller materialised the chosen {@code C_BPartner}s into a {@code T_Selection} under this
	 * {@link PInstanceId} (via {@code IQuery#createSelection}), and the run streams them with
	 * {@code setOnlySelection} — no per-record bind parameter, which is what {@code An I/O error occurred
	 * while sending to the backend} was. Passed through into every {@link VATaxIDCheckRequest#getPinstanceId()}
	 * this run creates too. {@code null} only for the nightly sweep, which builds no selection.
	 */
	@Nullable PInstanceId pinstanceId;

	/**
	 * Whether this is the nightly schedule's selection-less sweep rather than a user-triggered run.
	 * Defaults to {@code false}.
	 *
	 * <p>Changes exactly one thing: whether the unconditional per-partner expansion is additionally filtered
	 * to due targets and re-ordered by attempt time. The nightly run needs that finer staleness filter
	 * because it reaches every VAT-ID system-wide rather than a bounded, user-chosen selection.
	 */
	boolean nightlyRun;
}
