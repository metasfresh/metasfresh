package de.metas.pos;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ForUpdate;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_POS;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Repository Tables: C_POS
 * Repository Cluster: POSTerminalRepository, POSTerminalService
 */
@Repository
public class POSTerminalRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// arbitrary fixed namespace for this repository's session-scoped Postgres advisory locks (pairs with the
	// terminal's own C_POS_ID as the second key) — reserved so a lock taken here can never collide with the
	// codebase's OTHER advisory-lock user: de.metas.acct.base's product_costs_recreate_all_from_date() (and its
	// siblings run_identity/population_precheck/repost_all_from_date), which key pg_try_advisory_lock/unlock on
	// c_AdvisoryLock_ClassId=26253 (backend/de.metas.acct.base/.../ddl/functions/product_costs_recreate_all_from_date.sql,
	// from merged commit 59cd28bc933) — nowhere near this namespace's value, so no numeric collision either
	private static final int CROSS_TRX_LOCK_NAMESPACE = 0x504F5354; // "POST" in hex

	// how often a caller blocked waiting for the lock re-polls pg_try_advisory_lock; short enough that the
	// caller-supplied timeout (de.metas.pos.Return.LockTimeoutMillis, read by POSTerminalService) is honored
	// closely, long enough not to hammer the DB with a tight spin loop
	private static final long POLL_INTERVAL_MILLIS = 250;

	/**
	 * Locks the terminal's {@code C_POS} row for the rest of the caller's transaction, serializing two concurrent
	 * callers against the SAME terminal (e.g. two in-flight requests carrying the same idempotency key) — the
	 * second blocks here until the first commits, by which point its result already exists for the second to find.
	 */
	@NonNull
	public I_C_POS lockForUpdate(@NonNull final POSTerminalId posTerminalId)
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addEqualsFilter(I_C_POS.COLUMNNAME_C_POS_ID, posTerminalId)
				.create()
				.setForUpdate(ForUpdate.FOR_UPDATE)
				.firstOnlyNotNull(I_C_POS.class);
	}

	/**
	 * Runs {@code action} while holding a session-scoped Postgres advisory lock keyed on the given POS terminal, on
	 * a dedicated JDBC connection this method owns for the ENTIRE duration of {@code action}. Unlike
	 * {@link #lockForUpdate} (a row lock released at the caller's transaction commit), this lock survives across
	 * any number of separate top-level transactions {@code action} opens and commits internally — for a flow that
	 * spans more than one transaction end to end and needs the SAME terminal serialized for its full duration, not
	 * just for one transaction of it.
	 * <p>
	 * BOUNDED wait, not a blocking {@code pg_advisory_lock}: acquires with {@code pg_try_advisory_lock}, polled every
	 * {@link #POLL_INTERVAL_MILLIS} until either it succeeds or {@code timeoutMillis} elapses. An unbounded blocking
	 * wait here would let a single stuck caller (e.g. a slow/hung async workpackage inside {@code action}) freeze
	 * every OTHER caller for that terminal indefinitely; {@code product_costs_recreate_all_from_date()} in
	 * de.metas.acct.base (see the {@link #CROSS_TRX_LOCK_NAMESPACE} comment) sets the precedent of never blocking
	 * indefinitely on an advisory lock in this codebase — that function fails immediately on a single
	 * {@code pg_try_advisory_lock} miss, whereas this caller can tolerate a short, bounded wait (the timeout is
	 * caller-supplied so it can be tuned per use), so it polls instead of failing on the very first miss.
	 *
	 * @throws RuntimeException the one {@code onTimeout} supplies, if the lock could not be acquired within
	 * {@code timeoutMillis} — the caller decides what that means (e.g. a user-facing "till busy" rejection);
	 * this repository method carries no such policy itself. On success, returns whatever {@code action} itself
	 * returns, VERBATIM — including {@code null}, if {@code action} is a {@code Void}/{@code Runnable}-shaped
	 * action — with NO wrapping in between, so "timed out" and "action's own result" can never be confused
	 * (unlike an {@code Optional<T>}-returning design, where {@code action} legitimately returning {@code null}
	 * is indistinguishable from a timeout).
	 */
	@NonNull
	public <T> T runWithCrossTransactionLock(
			@NonNull final POSTerminalId posTerminalId,
			final long timeoutMillis,
			@NonNull final Supplier<T> action,
			@NonNull final Supplier<? extends RuntimeException> onTimeout)
	{
		final Connection lockConnection = DB.createConnection(true /*autoCommit*/, Connection.TRANSACTION_READ_COMMITTED);
		try
		{
			return runWithBoundedAcquire(
					() -> tryAdvisoryLock(lockConnection, posTerminalId),
					() -> advisoryUnlock(lockConnection, posTerminalId),
					timeoutMillis,
					POLL_INTERVAL_MILLIS,
					action,
					onTimeout);
		}
		finally
		{
			DB.close(lockConnection);
		}
	}

	/**
	 * The generic "poll a bounded number of times to acquire, then run-and-return-verbatim or throw" algorithm,
	 * factored out of {@link #runWithCrossTransactionLock} so it can be unit-tested directly — with fake
	 * {@code tryAcquire}/{@code release} suppliers — without a real DB connection, which
	 * {@link #runWithCrossTransactionLock} itself cannot be exercised without (it needs a real Postgres session
	 * for {@code pg_try_advisory_lock}/{@code pg_advisory_unlock}). Package-private + static: pure algorithm, no
	 * instance state, no DB/AD-context dependency at all.
	 */
	@NonNull
	static <T> T runWithBoundedAcquire(
			@NonNull final BooleanSupplier tryAcquire,
			@NonNull final Runnable release,
			final long timeoutMillis,
			final long pollIntervalMillis,
			@NonNull final Supplier<T> action,
			@NonNull final Supplier<? extends RuntimeException> onTimeout)
	{
		boolean locked = false;
		try
		{
			final long deadline = System.currentTimeMillis() + timeoutMillis;
			while (!(locked = tryAcquire.getAsBoolean()))
			{
				if (System.currentTimeMillis() >= deadline)
				{
					throw onTimeout.get();
				}
				sleepQuietly(pollIntervalMillis);
			}

			return action.get();
		}
		finally
		{
			if (locked)
			{
				release.run();
			}
		}
	}

	private boolean tryAdvisoryLock(@NonNull final Connection connection, @NonNull final POSTerminalId posTerminalId)
	{
		try (final PreparedStatement statement = connection.prepareStatement("SELECT pg_try_advisory_lock(?, ?)"))
		{
			statement.setInt(1, CROSS_TRX_LOCK_NAMESPACE);
			statement.setInt(2, posTerminalId.getRepoId());
			try (final ResultSet resultSet = statement.executeQuery())
			{
				resultSet.next();
				return resultSet.getBoolean(1);
			}
		}
		catch (final SQLException ex)
		{
			throw new AdempiereException("Failed to try-acquire the cross-transaction POS terminal lock", ex)
					.setParameter("C_POS_ID", posTerminalId);
		}
	}

	private void advisoryUnlock(@NonNull final Connection connection, @NonNull final POSTerminalId posTerminalId)
	{
		try (final PreparedStatement statement = connection.prepareStatement("SELECT pg_advisory_unlock(?, ?)"))
		{
			statement.setInt(1, CROSS_TRX_LOCK_NAMESPACE);
			statement.setInt(2, posTerminalId.getRepoId());
			statement.execute();
		}
		catch (final SQLException ex)
		{
			throw new AdempiereException("Failed to release the cross-transaction POS terminal lock", ex)
					.setParameter("C_POS_ID", posTerminalId);
		}
	}

	private static void sleepQuietly(final long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (final InterruptedException ex)
		{
			Thread.currentThread().interrupt();
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	@NonNull
	public POSTerminalId createPOSTerminal(@NonNull final POSTerminalCreateRequest request)
	{
		final I_C_POS record = InterfaceWrapperHelper.newInstance(I_C_POS.class);
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setName(request.getName());
		record.setIsActive(true);
		record.setIsModifyPrice(false);
		record.setCashLastBalance(BigDecimal.ZERO);
		record.setC_BPartnerCashTrx_ID(request.getWalkInCustomerId().getRepoId());
		record.setC_BP_BankAccount_ID(request.getCashbookId().getRepoId());
		record.setC_DocTypeOrder_ID(request.getSalesOrderDocTypeId().getRepoId());
		record.setM_PriceList_ID(request.getPriceListId().getRepoId());
		record.setM_Warehouse_ID(request.getShipFromWarehouseId().getRepoId());
		InterfaceWrapperHelper.saveRecord(record);

		return POSTerminalId.ofRepoId(record.getC_POS_ID());
	}
}
