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
 * ({@link DB_PostgreSQL#tryAcquireBusyConnectionsLogSlot(long, long)}).
 * <p>
 * The guard exists so that, under connection-pool saturation, the expensive {@code getStatus()} dump +
 * {@code Runtime.runFinalization()} + WARN block fires at most once per the configured interval instead of on every
 * single connection checkout (which previously produced a multi-GB/day log + GC storm).
 */
class DB_PostgreSQL_BusyConnectionsLogThrottleTest
{
	/**
	 * Interval the tests drive the guard with. Passed explicitly into {@code tryAcquireBusyConnectionsLogSlot} so the
	 * test is deterministic regardless of any {@code db.postgresql.busyConnectionsLogIntervalMillis} system property
	 * configured in the runtime - i.e. the test does NOT depend on the production default.
	 */
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
		assertThat(db.tryAcquireBusyConnectionsLogSlot(1_000_000L, INTERVAL_MILLIS)).isTrue();
	}

	@Test
	void secondCall_withinInterval_isSuppressed()
	{
		final long t0 = 1_000_000L;
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0, INTERVAL_MILLIS)).isTrue();

		// just before the interval elapses -> still suppressed
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + INTERVAL_MILLIS - 1, INTERVAL_MILLIS)).isFalse();
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + 1, INTERVAL_MILLIS)).isFalse();
	}

	@Test
	void call_afterIntervalElapsed_isAllowedAgain()
	{
		final long t0 = 1_000_000L;
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0, INTERVAL_MILLIS)).isTrue();
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + 10, INTERVAL_MILLIS)).isFalse();

		// exactly at the interval boundary -> allowed again
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + INTERVAL_MILLIS, INTERVAL_MILLIS)).isTrue();

		// and then suppressed again relative to the new last-run timestamp
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + INTERVAL_MILLIS + 5, INTERVAL_MILLIS)).isFalse();
	}

	@Test
	void manyCallsWithinInterval_allowExactlyOnce()
	{
		final long t0 = 5_000_000L;
		int allowed = 0;
		for (int i = 0; i < 10_000; i++)
		{
			// all calls land strictly within the first interval window
			if (db.tryAcquireBusyConnectionsLogSlot(t0 + (i % (INTERVAL_MILLIS / 2)), INTERVAL_MILLIS))
			{
				allowed++;
			}
		}
		assertThat(allowed).as("only the very first call within the interval window should be allowed").isEqualTo(1);
	}

	@Test
	void nonPositiveInterval_isClampedToDefault_soThrottleStaysEnabled()
	{
		// A misconfigured interval of 0 (or negative) must NOT disable the throttle: it is clamped to the 60s default.
		final long t0 = 2_000_000L;

		// interval=0 -> clamped to default; first call claims the slot
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0, 0L)).isTrue();

		// a call well within the default interval is still suppressed (proves the throttle was NOT disabled)
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + 5, 0L)).isFalse();
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + 1_000, -5L)).isFalse();

		// once the default interval elapses it is allowed again
		assertThat(db.tryAcquireBusyConnectionsLogSlot(t0 + INTERVAL_MILLIS, 0L)).isTrue();
	}
}
