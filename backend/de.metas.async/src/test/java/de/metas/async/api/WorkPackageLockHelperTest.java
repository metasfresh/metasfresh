package de.metas.async.api;

/*
 * #%L
 * de.metas.async
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.impl.StaticMockedWorkpackageProcessor;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link WorkPackageLockHelper#unlockNoFail(I_C_Queue_WorkPackage)} is called by the {@code QueueProcessorPlanner}
 * when it has claimed a workpackage ({@code LockedAt = now}) but cannot hand it to a queue processor, and by
 * {@code AbstractQueueProcessor} when a processor cannot take a claimed workpackage. In both cases no
 * {@code WorkpackageProcessorTask} runs, so this helper is the only place that can release the claim.
 */
public class WorkPackageLockHelperTest extends QueueProcessorTestBase
{
	@Test
	public void unlockNoFail_clearsLockedAt()
	{
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);
		final I_C_Queue_WorkPackage workPackage = helper.createAndEnqueueWorkpackages(queue, 1, true).get(0);
		workPackage.setLockedAt(SystemTime.asTimestamp());
		InterfaceWrapperHelper.save(workPackage);

		WorkPackageLockHelper.unlockNoFail(workPackage);

		final I_C_Queue_WorkPackage reloaded = InterfaceWrapperHelper.load(workPackage.getC_Queue_WorkPackage_ID(), I_C_Queue_WorkPackage.class);
		assertThat(reloaded.getLockedAt())
				.as("LockedAt must be cleared so the poller can pick the workpackage up again")
				.isNull();
		assertThat(reloaded.isProcessed()).isFalse();
		assertThat(reloaded.isError()).isFalse();
	}
}
