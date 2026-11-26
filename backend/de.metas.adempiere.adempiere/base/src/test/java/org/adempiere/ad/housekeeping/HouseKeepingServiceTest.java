package org.adempiere.ad.housekeeping;

import com.google.common.collect.ImmutableList;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Start-up tasks must run as the System user (AD_User_ID=0) and the caller's user must be restored afterwards.
 */
public class HouseKeepingServiceTest
{
	private static final int SYSTEM_USER_ID = 0;
	private static final int OTHER_USER_ID = 100;

	private List<Integer> seenUserIds;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		seenUserIds = new ArrayList<>();
	}

	private IStartupHouseKeepingTask recordingTask()
	{
		return () -> seenUserIds.add(Env.getAD_User_ID(Env.getCtx()));
	}

	private static void run(final IStartupHouseKeepingTask... tasks)
	{
		new HouseKeepingService(Optional.of(ImmutableList.copyOf(tasks))).runStartupHouseKeepingTasks();
	}

	@Test
	public void runsTasksAsSystemUser_andRestoresUnsetUser()
	{
		run(recordingTask());

		assertThat(seenUserIds).containsExactly(SYSTEM_USER_ID);
		assertThat(Env.getAD_User_ID(Env.getCtx())).isEqualTo(-1);
	}

	@Test
	public void restoresPreviouslySetUser()
	{
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, OTHER_USER_ID);

		run(recordingTask());

		assertThat(seenUserIds).containsExactly(SYSTEM_USER_ID);
		assertThat(Env.getAD_User_ID(Env.getCtx())).isEqualTo(OTHER_USER_ID);
	}

	@Test
	public void nextTaskSeesSystemUserAfterAThrowingTask()
	{
		final IStartupHouseKeepingTask throwingTask = () -> {
			throw new RuntimeException("test: task fails on purpose");
		};

		run(throwingTask, recordingTask());

		assertThat(seenUserIds).containsExactly(SYSTEM_USER_ID);
		assertThat(Env.getAD_User_ID(Env.getCtx())).isEqualTo(-1);
	}
}
