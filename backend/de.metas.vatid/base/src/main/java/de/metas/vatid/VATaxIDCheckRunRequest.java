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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * One run of {@link VATaxIDCheckRunService#run(VATaxIDCheckRunRequest)}: check the header VAT-ID (if any)
 * and every location VAT-ID (if any) of every partner in {@link #getSelectedBPartnerIds()} — see that
 * method's javadoc for the combined selection it builds from this list and the throttling
 * {@link #getMaxChecksPerRun()} applies to it.
 *
 * <p>Deliberately carries only process-agnostic values — typed ids, a plain {@code int}, an optional
 * {@link PInstanceId} — so the same run can be driven from a {@code JavaProcess}, a REST endpoint, or a
 * unit test, none of which need a {@code JavaProcess} to exist.
 */
@Value
@Builder
public class VATaxIDCheckRunRequest
{
	/**
	 * Ordered by {@code C_BPartner_ID} ascending — that order is what makes a throttled run's processed
	 * prefix deterministic and reproducible (see {@link VATaxIDCheckRunService#run(VATaxIDCheckRunRequest)}).
	 * The caller, not this class, is responsible for that ordering.
	 */
	@NonNull ImmutableList<BPartnerId> selectedBPartnerIds;

	/**
	 * Empty/unset or {@code <= 0} means no limit — see
	 * {@link VATaxIDCheckRunService#run(VATaxIDCheckRunRequest)}.
	 */
	int maxChecksPerRun;

	/**
	 * The process run this check belongs to, if any — passed straight through into every
	 * {@link VATaxIDCheckRequest#getPinstanceId()} this run creates. {@code null} for a run triggered
	 * outside a process (e.g. a REST call or a unit test).
	 */
	@Nullable PInstanceId pinstanceId;

	/**
	 * Whether this run is the nightly schedule's own, selection-less sweep — as opposed to a user-triggered
	 * manual/selection run. Defaults to {@code false} (a manual/selection run) when left unset.
	 *
	 * <p>Changes exactly one thing in {@link VATaxIDCheckRunService#run(VATaxIDCheckRunRequest)}: whether
	 * the unconditional per-partner expansion (every selected partner's header plus ALL of its
	 * VAT-ID-bearing locations — the documented, deliberate "selecting a partner also covers its locations"
	 * behaviour) is additionally filtered down to due targets only, and re-ordered by attempt time. A
	 * manual/selection run keeps the unconditional expansion; the nightly run needs the finer-grained
	 * staleness filter because it reaches every VAT-ID system-wide rather than a bounded, user-chosen
	 * selection — see {@link VATaxIDCheckRunService}'s class javadoc, "Attempt vs success", for why the two
	 * paths must diverge here.
	 */
	boolean nightlyRun;
}
