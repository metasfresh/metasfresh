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

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * One whole reconciliation run as the operator asked for it: <i>which</i> keys ({@link #getSelection()}), at
 * <i>which</i> date ({@link #getRunDate()}) and under <i>which</i> liveness rule
 * ({@link #getLivenessCutoff()}).
 * <p>
 * This is the payload that crosses the JVM boundary. A real run is enqueued in the webapi and reconciled in the
 * app server (see {@code de.metas.material.dispo.reconcile.async.AtpReconciliationEnqueueService}), so everything
 * the run depends on has to be captured here, at the moment the operator launched it, and carried along - most
 * pointedly the <b>run date</b>: derived in the app server instead, it would silently become "whenever the queue
 * happened to get around to it", which is a different reconciliation point and therefore a different result (a
 * correction candidate absorbs everything dated before it).
 */
@Value
@Builder
public class AtpReconciliationRunRequest
{
	@NonNull AtpKeySelection selection;

	/** The reconciliation point {@code D}: candidates dated after it are not part of the target. */
	@NonNull Instant runDate;

	/**
	 * When set, a candidate dated strictly before this instant is treated as closed regardless of what its source
	 * document says - see {@link AtpTargetCalculator#computeTarget(de.metas.material.cockpit.stock.StockDataRecordIdentifier, Instant, Instant)}.
	 * {@code null} means no candidate is cut off.
	 */
	@Nullable Instant livenessCutoff;
}
