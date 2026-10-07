package de.metas.pos;

import lombok.NonNull;
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
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;

/**
 * Repository Tables: C_POS
 * Repository Cluster: POSTerminalRepository, POSTerminalService
 */
@Repository
public class POSTerminalRepository
{
	// arbitrary fixed namespace for this repository's session-scoped Postgres advisory locks (pairs with the
	// terminal's own C_POS_ID as the second key) — reserved so a lock taken here can never collide with the
	// codebase's OTHER advisory-lock user: de.metas.acct.base's product_costs_recreate_all_from_date() (and its
	// siblings run_identity/population_precheck/repost_all_from_date), which key pg_try_advisory_lock/unlock on
	// c_AdvisoryLock_ClassId=26253 (backend/de.metas.acct.base/.../ddl/functions/product_costs_recreate_all_from_date.sql)
	// — nowhere near this namespace's value, so no numeric collision either
	private static final int CROSS_TRX_LOCK_NAMESPACE = 0x504F5354; // "POST" in hex

	/**
	 * Opens a dedicated JDBC connection this method owns for the ENTIRE duration of {@code body}, and hands
	 * {@code body} the two advisory-lock DB primitives bound to that connection: a {@code tryAcquire} that runs
	 * {@code pg_try_advisory_lock} once (returning whether the lock was taken) and a {@code release} that runs
	 * {@code pg_advisory_unlock} once, both keyed on {@code posTerminalId}. Because the connection lives for the
	 * whole {@code body} call, a lock {@code tryAcquire} takes is session-scoped and survives across any number of
	 * separate top-level transactions {@code body} opens and commits internally — not a transaction-scoped row
	 * lock released at some caller's commit.
	 * <p>
	 * The WHEN — the bounded poll/timeout policy that decides how often to call {@code tryAcquire} and when to give
	 * up — lives in the caller, {@link POSTerminalService#runWithCrossTransactionLock}. This repository carries
	 * ONLY the connection lifecycle and the two SQL primitives: no clock, no poll loop, no sleep (a repository
	 * exposes DB access, not timing policy).
	 *
	 * @return whatever {@code body} returns, VERBATIM (including {@code null}).
	 */
	@NonNull
	public <T> T runWithAdvisoryLockConnection(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final BiFunction<BooleanSupplier, Runnable, T> body)
	{
		final Connection lockConnection = DB.createConnection(true /*autoCommit*/, Connection.TRANSACTION_READ_COMMITTED);
		try
		{
			return body.apply(
					() -> tryAdvisoryLock(lockConnection, posTerminalId),
					() -> advisoryUnlock(lockConnection, posTerminalId));
		}
		finally
		{
			DB.close(lockConnection);
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
