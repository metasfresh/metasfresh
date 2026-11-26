package de.metas.cucumber.stepdefs.match_po;

import de.metas.logging.LogManager;
import de.metas.order.MatchPOId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_CostDetail;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Stands in for the async posting of an M_MatchPO ({@code DocLine_MatchPO.createCostDetails}), which inserts an M_CostDetail for the M_MatchPO
 * in its own transaction and commits it. Here that transaction is a separate JDBC connection whose commit the test controls.
 * <p>
 * FK {@code MMatchPO_MCostDetail} is {@code DEFERRABLE INITIALLY DEFERRED}, so the insert itself takes no lock on the M_MatchPO row;
 * only the commit-time FK check does ({@code FOR KEY SHARE}).
 */
final class ConcurrentMatchPOCostDetailCommit implements AutoCloseable
{
	private static final Logger logger = LogManager.getLogger(ConcurrentMatchPOCostDetailCommit.class);
	private static final int TIMEOUT_SECONDS = 30;

	@NonNull private final Connection connection;
	private final int backendPid;
	private final int costDetailId;
	@NonNull private final ExecutorService committer = Executors.newSingleThreadExecutor();
	@Nullable private Future<?> commitFuture;

	private ConcurrentMatchPOCostDetailCommit(@NonNull final Connection connection, final int backendPid, final int costDetailId)
	{
		this.connection = connection;
		this.backendPid = backendPid;
		this.costDetailId = costDetailId;
	}

	/**
	 * Inserts, without committing, a copy of the M_MatchPO's existing (committed) M_CostDetail.
	 */
	static ConcurrentMatchPOCostDetailCommit insertUncommitted(@NonNull final MatchPOId matchPOId)
	{
		final Connection connection = DB.createConnection(false, Connection.TRANSACTION_READ_COMMITTED);
		try
		{
			final int backendPid = selectInt(connection, "SELECT pg_backend_pid()");
			final int clientId = selectInt(connection, "SELECT AD_Client_ID FROM M_CostDetail WHERE M_MatchPO_ID=? ORDER BY M_CostDetail_ID LIMIT 1", matchPOId.getRepoId());
			final int costDetailId = DB.getNextID(clientId, I_M_CostDetail.Table_Name);

			// Copy via a temp table: same table, so SELECT * keeps the column order; M_CostDetail has no unique index besides its PK.
			execute(connection, "CREATE TEMPORARY TABLE tmp_concurrent_matchpo_costdetail ON COMMIT DROP AS"
					+ " SELECT * FROM M_CostDetail WHERE M_MatchPO_ID=? ORDER BY M_CostDetail_ID LIMIT 1", matchPOId.getRepoId());
			execute(connection, "UPDATE tmp_concurrent_matchpo_costdetail SET M_CostDetail_ID=?", costDetailId);
			final int inserted = execute(connection, "INSERT INTO M_CostDetail SELECT * FROM tmp_concurrent_matchpo_costdetail");
			if (inserted != 1)
			{
				throw new AdempiereException("Expected to insert exactly one M_CostDetail, but inserted " + inserted);
			}

			logger.info("Inserted uncommitted M_CostDetail_ID={} for M_MatchPO_ID={} on backend pid={}", costDetailId, matchPOId.getRepoId(), backendPid);
			return new ConcurrentMatchPOCostDetailCommit(connection, backendPid, costDetailId);
		}
		catch (final SQLException | RuntimeException e)
		{
			rollbackAndClose(connection);
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	/**
	 * Starts the commit and returns as soon as it either completed or is blocked by the transaction {@code deletingTrxName}.
	 */
	void commitAndWaitUntilDoneOrBlockedBy(@NonNull final String deletingTrxName)
	{
		final int deletingBackendPid = DB.getSQLValueEx(deletingTrxName, "SELECT pg_backend_pid()");

		commitFuture = committer.submit(() -> {
			connection.commit();
			return null;
		});

		final long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(TIMEOUT_SECONDS);
		while (!commitFuture.isDone())
		{
			final boolean blockedByDeletingTrx = DB.getSQLValueEx(
					ITrx.TRXNAME_None,
					"SELECT COUNT(1) FROM unnest(pg_blocking_pids(?)) AS blocking_pid WHERE blocking_pid=?",
					backendPid, deletingBackendPid) > 0;
			if (blockedByDeletingTrx)
			{
				logger.info("Commit of M_CostDetail_ID={} is blocked by the deleting trx {} (backend pid={})", costDetailId, deletingTrxName, deletingBackendPid);
				return;
			}
			if (System.currentTimeMillis() > deadline)
			{
				throw new AdempiereException("Commit of M_CostDetail_ID=" + costDetailId + " neither completed nor got blocked by " + deletingTrxName + " within " + TIMEOUT_SECONDS + "s");
			}
			sleepMillis(20);
		}
		logger.info("Commit of M_CostDetail_ID={} completed before the M_MatchPO was deleted", costDetailId);
	}

	/**
	 * Waits until the commit started by {@link #commitAndWaitUntilDoneOrBlockedBy(String)} is over.
	 *
	 * @return the error that made the commit fail, or {@code null} if the commit succeeded
	 */
	@Nullable
	Throwable awaitCommitResult()
	{
		if (commitFuture == null)
		{
			throw new AdempiereException("Commit of M_CostDetail_ID=" + costDetailId + " was never started");
		}
		try
		{
			commitFuture.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
			return null;
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
			throw new AdempiereException("Commit of M_CostDetail_ID=" + costDetailId + " did not finish within " + TIMEOUT_SECONDS + "s", e);
		}
	}

	int getCostDetailId() {return costDetailId;}

	@Override
	public void close()
	{
		if (commitFuture != null && !commitFuture.isDone())
		{
			try
			{
				commitFuture.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
			}
			catch (final Exception e)
			{
				logger.info("Ignoring commit outcome while closing", e);
			}
		}
		committer.shutdownNow();
		rollbackAndClose(connection);
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

	private static int selectInt(@NonNull final Connection connection, @NonNull final String sql, final Object... params) throws SQLException
	{
		try (final PreparedStatement pstmt = prepare(connection, sql, params);
				final ResultSet rs = pstmt.executeQuery())
		{
			if (!rs.next())
			{
				throw new AdempiereException("No row returned by: " + sql);
			}
			return rs.getInt(1);
		}
	}

	private static int execute(@NonNull final Connection connection, @NonNull final String sql, final Object... params) throws SQLException
	{
		try (final PreparedStatement pstmt = prepare(connection, sql, params))
		{
			return pstmt.executeUpdate();
		}
	}

	private static PreparedStatement prepare(@NonNull final Connection connection, @NonNull final String sql, final Object... params) throws SQLException
	{
		final PreparedStatement pstmt = connection.prepareStatement(sql);
		try
		{
			for (int i = 0; i < params.length; i++)
			{
				pstmt.setObject(i + 1, params[i]);
			}
			return pstmt;
		}
		catch (final SQLException | RuntimeException e)
		{
			DB.close(pstmt);
			throw e;
		}
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
