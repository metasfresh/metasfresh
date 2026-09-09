package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.material.dispo.commons.candidate.CandidateId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * The audit trail of one {@link AtpReconciliationCommand#reconcileAndLog} run: which {@code STOCK} candidates it
 * changed, and what {@code Qty} each one carried immediately before the run touched it.
 * <p>
 * That before-value <b>is</b> the backup: nothing this run changes is ever lost, because the value it replaced is
 * captured here - read from the store before {@link AtpReconciliationCommand#reconcile} writes anything - rather
 * than only being derivable after the fact from the new, already-overwritten state. This in-process object does
 * not itself outlive the run, though: {@link #getRunUuid()} is the key under which
 * {@link AtpReconciliationBackupRepository} persists the same before/after values durably, so they stay recoverable
 * after the process that ran this reconciliation has ended.
 * <p>
 * A candidate absent from {@link #getEntries()} was not touched by the run - either the run wrote nothing at all
 * (a dry run, or a zero delta - see {@link AtpReconciliationCommand#reconcile}), or the candidate falls outside the
 * general {@code STOCK} chain from the run's date onward, the only place a reconciliation ever writes.
 */
@Value
public class AtpReconciliationRunLog
{
	@NonNull AtpDivergence divergence;
	@NonNull ImmutableList<Entry> entries;

	/**
	 * Groups this run's persisted {@link AtpReconciliationBackupRepository} rows - {@code null} when nothing was
	 * written (a dry run, or a zero delta), since then there is nothing to look up.
	 */
	@Nullable String runUuid;

	public static AtpReconciliationRunLog empty(@NonNull final AtpDivergence divergence)
	{
		return new AtpReconciliationRunLog(divergence, ImmutableList.of(), null);
	}

	public boolean isEmpty()
	{
		return entries.isEmpty();
	}

	/**
	 * One {@code STOCK} candidate this run changed.
	 */
	@Value
	public static class Entry
	{
		@NonNull CandidateId candidateId;
		@NonNull Instant date;

		/**
		 * The candidate's {@code Qty} immediately before this run touched it - the backed-up value - or
		 * {@code null} when the run itself created this candidate, i.e. there was no earlier value to back up.
		 */
		@Nullable BigDecimal qtyBefore;

		@NonNull BigDecimal qtyAfter;

		public boolean isNewCandidate()
		{
			return qtyBefore == null;
		}
	}
}
