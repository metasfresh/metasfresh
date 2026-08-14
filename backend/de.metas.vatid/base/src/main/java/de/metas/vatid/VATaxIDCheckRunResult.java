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
import lombok.Value;

/**
 * What {@link VATaxIDCheckRunService#run(VATaxIDCheckRunRequest)} did with one
 * {@link VATaxIDCheckRunRequest}: how much of the combined partner+location selection it actually checked,
 * how much it left untouched because {@link VATaxIDCheckRunRequest#getMaxChecksPerRun()} throttled the run,
 * and — the AC16 run summary — how many online calls it actually made and their average response time (see
 * {@link VATaxIDCheckCallStats}, which this extends rather than duplicating).
 */
@Value
@Builder
public class VATaxIDCheckRunResult
{
	int checkedCount;

	int pendingCount;

	/** How many online calls this run made — see {@link VATaxIDCheckCallStats#getCallCount()}. */
	int callCount;

	/** See {@link VATaxIDCheckCallStats#getAverageResponseTimeMillis()}. */
	long averageResponseTimeMillis;
}
