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
 * How many online calls one run made, and their average round-trip time.
 *
 * <p>Narrower than {@link VATaxIDCheckRunResult} on purpose: only the two numbers
 * {@code VATaxIDCheckRepository} can answer from the rows a run wrote, which the run service folds into
 * that result rather than returning a second, competing summary.
 */
@Value
@Builder
public class VATaxIDCheckCallStats
{
	/** How many {@code VATaxID_CheckLog} rows this run wrote — every one is a call the service was asked. */
	int callCount;

	/**
	 * The average time between {@code RequestDate} and {@code ResponseDate} over the rows that got an
	 * answer, in milliseconds. {@code 0} when {@link #callCount} is {@code 0} (nothing to average) or
	 * when none of the calls ever got an answer.
	 */
	long averageResponseTimeMillis;
}
