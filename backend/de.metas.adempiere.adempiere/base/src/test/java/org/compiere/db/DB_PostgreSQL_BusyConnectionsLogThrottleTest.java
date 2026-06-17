/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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
package org.compiere.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the busy-connections log+finalization rate-limit guard
 * ({@link DB_PostgreSQL#tryAcquireBusyConnectionsLogSlot(long)}).
 * <p>
 * The guard exists so that, under connection-pool saturation, the expensive {@code getStatus()} dump +
 * {@code Runtime.runFinalization()} + WARN block fires at most once per the configured interval instead of on every
 * single connection checkout (which previously produced a multi-GB/day log + GC storm).
 */
class DB_PostgreSQL_BusyConnectionsLogThrottleTest
{
	/** Default interval the production code uses (60s) unless overridden via system property. */
	private static final long INTERVAL_MILLIS = 60_000L;

	private DB_PostgreSQL db;

	@BeforeEach
	void setUp()
	{
		db = new DB_PostgreSQL();
	}

	@Test
	void firstCall_isAllowed()
	{
		assertThat(db.tryAcquireBusyConnectionsLogSlot(1_000_000L)).isTrue();
	}

	@Test
	void secondCall_withinInterval_isSuppressed()
	{
		final long t0 = 1_000_000L;
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0)).isTrue();

		// just before the interval elapses -> still suppressed
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + INTERVAL_MILLIS - 1)).isFalse();
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + 1)).isFalse();
	}

	@Test
	void call_afterIntervalElapsed_isAllowedAgain()
	{
		final long t0 = 1_000_000L;
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0)).isTrue();
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + 10)).isFalse();

		// exactly at the interval boundary -> allowed again
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + INTERVAL_MILLIS)).isTrue();

		// and then suppressed again relative to the new last-run timestamp
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + INTERVAL_MILLIS + 5)).isFalse();
	}

	@Test
	void manyCallsWithinInterval_allowExactlyOnce()
	{
		final long t0 = 5_000_000L;
		int allowed = 0;
		for (int i = 0; i < 10_000; i++)
		{
			// all calls land strictly within the first interval window
			if (db.tryAcquireBusyConnectionsLogSlot(t0 + (i % (INTERVAL_MILLIS / 2))))
			{
				allowed++;
			}
		}
		assertThat(allowed).as("only the very first call within the interval window should be allowed").isEqualTo(1);
	}
}
