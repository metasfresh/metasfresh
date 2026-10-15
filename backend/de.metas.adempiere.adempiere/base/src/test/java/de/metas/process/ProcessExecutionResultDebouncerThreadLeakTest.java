package de.metas.process;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.model.X_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Replays a scheduled AD_Scheduler run (Scheduler#runProcess -> ProcessExecutor#executeSync):
 * every run that writes process logs starts one ProcessExecutionResult.Debouncer thread;
 * ProcessExecutor's unlock -> unlockAndSaveResult -> syncLogsToDB must shut it down.
 */
class ProcessExecutionResultDebouncerThreadLeakTest
{
	private static final String DEBOUNCER_THREAD_PREFIX = ProcessExecutionResult.class.getName() + ".Debouncer-";
	private static final int RUNS = 5;
	private static final long MAX_WAIT_AFTER_LAST_RUN_MS = 5_000; // Debouncer delay is 1 s; idle timeout (60 s) must NOT be what saves us

	/**
	 * ids of Debouncer threads alive before the test started — excluded from all assertions (other tests in the same JVM).
	 */
	private static Set<Long> preExistingThreadIds = ImmutableSet.of();
	/**
	 * Debouncer threads observed by the process itself, after it wrote its logs.
	 */
	private static final Set<Long> threadIdsSeenDuringRuns = ConcurrentHashMap.newKeySet();

	public static class LogWritingProcess extends JavaProcess
	{
		@Override
		protected String doIt()
		{
			for (int i = 1; i <= 3; i++)
			{
				addLog(0, SystemTime.asTimestamp(), null, "scheduled-run log line " + i);
			}
			threadIdsSeenDuringRuns.addAll(newDebouncerThreadIds());
			return MSG_OK;
		}
	}

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		final PlainTrxManager trxManager = (PlainTrxManager)Services.get(ITrxManager.class);
		trxManager.setFailCommitIfTrxNotStarted(false);
		trxManager.setFailRollbackIfTrxNotStarted(false);
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(100));

		// ProcessExecutor checks the role's process access; permissions are not the invariant under test
		final IUserRolePermissions permissions = Mockito.mock(IUserRolePermissions.class);
		Mockito.doReturn(RoleId.SYSTEM).when(permissions).getRoleId();
		final IUserRolePermissionsDAO permissionsDAO = Mockito.mock(IUserRolePermissionsDAO.class);
		Mockito.doReturn(permissions).when(permissionsDAO).getUserRolePermissions(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
		Services.registerService(IUserRolePermissionsDAO.class, permissionsDAO);

		preExistingThreadIds = debouncerThreadIds();
		threadIdsSeenDuringRuns.clear();
	}

	@Test
	void processRunsThroughProcessExecutor_leaveNoDebouncerThreadAlive() throws Exception
	{
		final I_AD_Process adProcess = newInstanceOutOfTrx(I_AD_Process.class);
		adProcess.setValue(LogWritingProcess.class.getSimpleName());
		adProcess.setName(LogWritingProcess.class.getSimpleName());
		adProcess.setType(X_AD_Process.TYPE_Java);
		adProcess.setClassname(LogWritingProcess.class.getName());
		saveRecord(adProcess);

		for (int run = 1; run <= RUNS; run++)
		{
			final ProcessExecutionResult result = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(adProcess.getAD_Process_ID())
					.setRoleId(RoleId.SYSTEM) // a scheduler run uses the scheduler's role; SYSTEM skips the per-process access check
					.setTitle("DebouncerLeak run " + run)
					.buildAndPrepareExecution()
					.onErrorThrowException()
					.executeSync()
					.getResult();
			assertThat(result.isError()).as("run %s error: %s", run, result.getSummary()).isFalse();
		}

		// guard against a vacuous pass: the runs really started Debouncer threads
		assertThat(threadIdsSeenDuringRuns).as("Debouncer threads started by the process runs").isNotEmpty();

		final long deadline = System.currentTimeMillis() + MAX_WAIT_AFTER_LAST_RUN_MS;
		Set<Long> stillAlive = newDebouncerThreadIds();
		while (!stillAlive.isEmpty() && System.currentTimeMillis() < deadline)
		{
			//noinspection BusyWait
			Thread.sleep(100);
			stillAlive = newDebouncerThreadIds();
		}
		assertThat(stillAlive)
				.as("ProcessExecutionResult.Debouncer threads still alive %s ms after the last run (leak: one thread per process run)", MAX_WAIT_AFTER_LAST_RUN_MS)
				.isEmpty();
	}

	private static Set<Long> newDebouncerThreadIds()
	{
		return debouncerThreadIds().stream().filter(id -> !preExistingThreadIds.contains(id)).collect(Collectors.toSet());
	}

	private static Set<Long> debouncerThreadIds()
	{
		return Thread.getAllStackTraces().keySet().stream()
				.filter(Thread::isAlive)
				.filter(t -> t.getName().startsWith(DEBOUNCER_THREAD_PREFIX))
				.map(Thread::getId)
				.collect(Collectors.toSet());
	}
}
