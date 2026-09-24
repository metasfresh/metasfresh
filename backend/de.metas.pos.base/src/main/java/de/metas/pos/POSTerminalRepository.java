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
import java.sql.SQLException;
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
	// terminal's own C_POS_ID as the second key) — reserved so a lock taken here can never collide with an
	// unrelated advisory lock keyed on the same raw id elsewhere; nothing else in this codebase uses pg_advisory_lock
	// today (verified by a repo-wide grep), but the namespace still documents the intent for the next user of one
	private static final int CROSS_TRX_LOCK_NAMESPACE = 0x504F5354; // "POST" in hex

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
	 */
	@NonNull
	public <T> T runWithCrossTransactionLock(@NonNull final POSTerminalId posTerminalId, @NonNull final Supplier<T> action)
	{
		final Connection lockConnection = DB.createConnection(true /*autoCommit*/, Connection.TRANSACTION_READ_COMMITTED);
		try
		{
			advisoryLock(lockConnection, posTerminalId, true);
			return action.get();
		}
		finally
		{
			try
			{
				advisoryLock(lockConnection, posTerminalId, false);
			}
			finally
			{
				DB.close(lockConnection);
			}
		}
	}

	private void advisoryLock(@NonNull final Connection connection, @NonNull final POSTerminalId posTerminalId, final boolean lock)
	{
		final String sql = lock ? "SELECT pg_advisory_lock(?, ?)" : "SELECT pg_advisory_unlock(?, ?)";
		try (final PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setInt(1, CROSS_TRX_LOCK_NAMESPACE);
			statement.setInt(2, posTerminalId.getRepoId());
			statement.execute();
		}
		catch (final SQLException ex)
		{
			throw new AdempiereException("Failed to " + (lock ? "acquire" : "release") + " the cross-transaction POS terminal lock", ex)
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
