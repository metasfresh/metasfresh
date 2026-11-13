/*
 * #%L
 * de.metas.cucumber
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
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.shipmentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Records in which order a writer locks a given set of shipment schedules.
 * <p>
 * Two writers that update several shipment schedules each in one transaction deadlock if they lock them in opposite order
 * (e.g. an order's reactivation and the shipment schedule recompute pass). Both locking them in ascending {@code M_ShipmentSchedule_ID}
 * order rules that out.
 * <p>
 * The probe locks each of the shipment schedules in a transaction of its own, then starts the writer in another thread. Whenever the
 * writer waits for one of these locks, the probe records that shipment schedule and releases its lock, so the writer gets it and goes
 * on to its next one. This yields the order in which the writer acquires the locks, without changing it.
 */
final class ShipmentScheduleRowLockProbe
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleRowLockProbe.class);
	private static final int TIMEOUT_SECONDS = 60;
	private static final String SQL_LOCK = "SELECT M_ShipmentSchedule_ID FROM M_ShipmentSchedule WHERE M_ShipmentSchedule_ID=? FOR NO KEY UPDATE";

	/**
	 * @param writer runs in another thread; returns the error it ended with, or {@code null}
	 */
	static Result recordLockOrder(@NonNull final List<Integer> shipmentScheduleIds, @NonNull final Callable<Throwable> writer)
	{
		final Map<Integer, Connection> blockerByPid = new LinkedHashMap<>();
		final Map<Integer, Integer> shipmentScheduleIdByPid = new HashMap<>();
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		try
		{
			for (final int shipmentScheduleId : shipmentScheduleIds)
			{
				final Connection blocker = DB.createConnection(false, Connection.TRANSACTION_READ_COMMITTED);
				final int pid = selectInt(blocker, "SELECT pg_backend_pid()");
				blockerByPid.put(pid, blocker);
				selectInt(blocker, SQL_LOCK, shipmentScheduleId);
				shipmentScheduleIdByPid.put(pid, shipmentScheduleId);
			}
			logger.info("Locked M_ShipmentSchedule_IDs {} on backends {}", shipmentScheduleIds, shipmentScheduleIdByPid);

			final Future<Throwable> writerOutcome = executor.submit(writer);

			final ImmutableList.Builder<Integer> lockOrder = ImmutableList.builder();
			int writerPid = -1;
			final long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(TIMEOUT_SECONDS);
			while (!blockerByPid.isEmpty() && !writerOutcome.isDone())
			{
				final int blockingPid = retrieveBlockingPid(writerPid, blockerByPid.keySet());
				if (blockingPid > 0)
				{
					if (writerPid <= 0)
					{
						writerPid = retrieveBlockedPid(blockingPid);
					}
					final int shipmentScheduleId = shipmentScheduleIdByPid.get(blockingPid);
					logger.info("The writer (backend pid={}) waits for M_ShipmentSchedule_ID={}", writerPid, shipmentScheduleId);
					lockOrder.add(shipmentScheduleId);
					rollbackAndClose(blockerByPid.remove(blockingPid));
				}
				else if (System.currentTimeMillis() > deadline)
				{
					throw new AdempiereException("The writer neither finished nor waited for one of the locked shipment schedules within " + TIMEOUT_SECONDS + "s");
				}
				else
				{
					sleepMillis(20);
				}
			}

			blockerByPid.values().forEach(ShipmentScheduleRowLockProbe::rollbackAndClose);
			blockerByPid.clear();

			return new Result(lockOrder.build(), awaitOutcome(writerOutcome));
		}
		catch (final SQLException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		finally
		{
			blockerByPid.values().forEach(ShipmentScheduleRowLockProbe::rollbackAndClose);
			executor.shutdownNow();
		}
	}

	static final class Result
	{
		/** the shipment schedules in the order in which the writer locked them */
		@NonNull final ImmutableList<Integer> lockOrder;
		@Nullable final Throwable writerFailure;

		private Result(@NonNull final ImmutableList<Integer> lockOrder, @Nullable final Throwable writerFailure)
		{
			this.lockOrder = lockOrder;
			this.writerFailure = writerFailure;
		}
	}

	/**
	 * @param writerPid the writer's backend pid, or {@code -1} while it is not known yet (then any backend except the blockers counts)
	 * @return the pid of the blocker the writer waits for, or {@code -1}
	 */
	private static int retrieveBlockingPid(final int writerPid, @NonNull final Set<Integer> blockerPids)
	{
		final String blockerPidsArray = blockerPids.stream().map(String::valueOf).collect(Collectors.joining(",", "{", "}"));
		return DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				"SELECT COALESCE(MIN(b.blocking_pid), -1)"
						+ " FROM pg_stat_activity a, unnest(pg_blocking_pids(a.pid)) AS b(blocking_pid)"
						+ " WHERE b.blocking_pid = ANY(?::int[])"
						+ " AND NOT (a.pid = ANY(?::int[]))"
						+ " AND (? <= 0 OR a.pid = ?)",
				blockerPidsArray, blockerPidsArray, writerPid, writerPid);
	}

	private static int retrieveBlockedPid(final int blockingPid)
	{
		return DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				"SELECT COALESCE(MIN(pid), -1) FROM pg_stat_activity WHERE ?=ANY(pg_blocking_pids(pid))",
				blockingPid);
	}

	@Nullable
	private static Throwable awaitOutcome(@NonNull final Future<Throwable> writerOutcome)
	{
		try
		{
			return writerOutcome.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		}
		catch (final ExecutionException e)
		{
			return e.getCause();
		}
		catch (final InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw AdempiereException.wrapIfNeeded(e);
		}
		catch (final TimeoutException e)
		{
			throw new AdempiereException("The writer did not finish within " + TIMEOUT_SECONDS + "s", e);
		}
	}

	private static int selectInt(@NonNull final Connection connection, @NonNull final String sql, final Object... params) throws SQLException
	{
		try (final PreparedStatement pstmt = connection.prepareStatement(sql))
		{
			for (int i = 0; i < params.length; i++)
			{
				pstmt.setObject(i + 1, params[i]);
			}
			try (final ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
				{
					throw new AdempiereException("No row returned by: " + sql);
				}
				return rs.getInt(1);
			}
		}
	}

	private static void rollbackAndClose(@NonNull final Connection connection)
	{
		try
		{
			connection.rollback();
			connection.setAutoCommit(true); // don't hand the connection back to the pool with autoCommit=false
		}
		catch (final SQLException e)
		{
			logger.info("Ignoring rollback failure", e);
		}
		DB.close(connection);
	}

	private static void sleepMillis(final long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (final InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
