package org.adempiere.ad.housekeeping;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
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
 * Plain unit test: the start-up runner cannot be triggered from a Cucumber scenario or the UI.
 */
public class HouseKeepingServiceTest
{
	private static final UserId OTHER_USER_ID = UserId.ofRepoId(100);

	private List<UserId> seenUserIds;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		seenUserIds = new ArrayList<>();
	}

	private IStartupHouseKeepingTask recordingTask()
	{
		return () -> seenUserIds.add(Env.getLoggedUserIdIfExists(Env.getCtx()).orElse(null));
	}

	private static void run(final IStartupHouseKeepingTask... tasks)
	{
		new HouseKeepingService(Optional.of(ImmutableList.copyOf(tasks))).runStartupHouseKeepingTasks();
	}

	@Test
	public void runsTasksAsSystemUser_andRestoresUnsetUser()
	{
		run(recordingTask());

		assertThat(seenUserIds).containsExactly(UserId.SYSTEM);
		assertThat(Env.getLoggedUserIdIfExists(Env.getCtx())).isEmpty();
	}

	@Test
	public void restoresPreviouslySetUser()
	{
		Env.setLoggedUserId(Env.getCtx(), OTHER_USER_ID);

		run(recordingTask());

		assertThat(seenUserIds).containsExactly(UserId.SYSTEM);
		assertThat(Env.getLoggedUserIdIfExists(Env.getCtx())).contains(OTHER_USER_ID);
	}

	@Test
	public void nextTaskSeesSystemUserAfterAThrowingTask()
	{
		final IStartupHouseKeepingTask throwingTask = () -> {
			throw new RuntimeException("test: task fails on purpose");
		};

		run(throwingTask, recordingTask());

		assertThat(seenUserIds).containsExactly(UserId.SYSTEM);
		assertThat(Env.getLoggedUserIdIfExists(Env.getCtx())).isEmpty();
	}
}
