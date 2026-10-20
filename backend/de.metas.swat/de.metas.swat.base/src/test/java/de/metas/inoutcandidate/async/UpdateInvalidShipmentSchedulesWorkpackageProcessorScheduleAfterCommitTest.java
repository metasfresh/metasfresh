package de.metas.inoutcandidate.async;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * The "skip if more than one recompute workpackage is already queued" decision must be taken when the flag is visible to those
 * workpackages: for a request bound to a transaction that is after the commit, not while the flag is still uncommitted.
 * Otherwise the queued workpackages can run (and see nothing) before the commit, and the committed flag is left without a workpackage.
 */
class UpdateInvalidShipmentSchedulesWorkpackageProcessorScheduleAfterCommitTest
{
	private ITrxManager trxManager;
	private IWorkPackageQueue queue;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(Env.getCtx(), UpdateInvalidShipmentSchedulesWorkpackageProcessor.class);
	}

	private List<I_C_Queue_WorkPackage> enqueueTwoReadyWorkpackages()
	{
		final List<I_C_Queue_WorkPackage> workpackages = Arrays.asList(
				queue.newWorkPackage().buildAndEnqueue(),
				queue.newWorkPackage().buildAndEnqueue());
		assertThat(queue.size()).as("setup: two processable recompute workpackages are queued").isEqualTo(2);
		return workpackages;
	}

	private static void markProcessed(final List<I_C_Queue_WorkPackage> workpackages)
	{
		for (final I_C_Queue_WorkPackage workpackage : workpackages)
		{
			workpackage.setProcessed(true);
			InterfaceWrapperHelper.save(workpackage, ITrx.TRXNAME_None);
		}
	}

	private static List<I_C_Queue_WorkPackage> pendingRecomputeWorkpackages()
	{
		return POJOLookupMap.get().getRecords(I_C_Queue_WorkPackage.class).stream()
				.filter(wp -> UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getName().equals(wp.getC_Queue_PackageProcessor().getClassname()))
				.filter(wp -> !wp.isProcessed())
				.collect(Collectors.toList());
	}

	private static ShipmentSchedulesUpdateSchedulerRequest request(final String trxName)
	{
		return ShipmentSchedulesUpdateSchedulerRequest.builder()
				.ctx(Env.getCtx())
				.trxName(trxName)
				.build();
	}

	@Test
	void trxBound_queuedWorkpackagesRunBeforeCommit_workpackageIsEnqueuedAfterCommit()
	{
		final List<I_C_Queue_WorkPackage> queued = enqueueTwoReadyWorkpackages();

		trxManager.runInThreadInheritedTrx(() -> {
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(request(ITrx.TRXNAME_ThreadInherited));

			// both queued workpackages run while the flag is still uncommitted, so they cannot see it
			markProcessed(queued);
		});

		assertThat(pendingRecomputeWorkpackages())
				.as("after the commit no workpackage is pending that could see the flag, so one must be enqueued")
				.hasSize(1);
	}

	@Test
	void trxBound_queuedWorkpackagesStillPendingAtCommit_noAdditionalWorkpackage()
	{
		final List<I_C_Queue_WorkPackage> queued = enqueueTwoReadyWorkpackages();

		trxManager.runInThreadInheritedTrx(() -> UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(request(ITrx.TRXNAME_ThreadInherited)));

		assertThat(pendingRecomputeWorkpackages())
				.as("the two workpackages queued at commit start after it and see the flag")
				.containsExactlyInAnyOrderElementsOf(queued);
	}

	@Test
	void noTrx_twoWorkpackagesQueued_noAdditionalWorkpackage()
	{
		final List<I_C_Queue_WorkPackage> queued = enqueueTwoReadyWorkpackages();

		UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(request(ITrx.TRXNAME_None));

		assertThat(pendingRecomputeWorkpackages()).containsExactlyInAnyOrderElementsOf(queued);
	}

	@Test
	void noTrx_nothingQueued_workpackageIsEnqueued()
	{
		UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(request(ITrx.TRXNAME_None));

		assertThat(pendingRecomputeWorkpackages()).hasSize(1);
	}
}
