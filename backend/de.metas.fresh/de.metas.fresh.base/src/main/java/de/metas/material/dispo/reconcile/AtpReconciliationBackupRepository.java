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

import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import lombok.NonNull;

import java.util.List;

/**
 * Persists the pre-change {@code Qty} of every {@code STOCK} candidate a reconciliation run is about to touch, then
 * completes that same row with the after-value once the run's outcome is known - turning it into a durable record
 * of what changed. {@link AtpReconciliationCommand#reconcileAndLog} always calls the two methods in that order.
 */
public interface AtpReconciliationBackupRepository
{
	/**
	 * Persists one row per candidate in {@code stockCandidatesInScope}, carrying its current {@code Qty} as the
	 * recoverable pre-change value. Called before {@link AtpReconciliationCommand} writes anything, so a crash right
	 * after this call still leaves every pre-change value recoverable.
	 */
	void backupBeforeWrite(
			@NonNull String runUuid,
			@NonNull StockDataRecordIdentifier key,
			@NonNull List<Candidate> stockCandidatesInScope);

	/**
	 * Completes the durable record with the after-value of every candidate the run actually changed: updates the
	 * row {@link #backupBeforeWrite} created for a pre-existing candidate, or inserts a fresh one (with no
	 * pre-change value to carry) for a candidate the run itself created.
	 */
	void recordAfterWrite(
			@NonNull String runUuid,
			@NonNull StockDataRecordIdentifier key,
			@NonNull List<AtpReconciliationRunLog.Entry> entries);
}
