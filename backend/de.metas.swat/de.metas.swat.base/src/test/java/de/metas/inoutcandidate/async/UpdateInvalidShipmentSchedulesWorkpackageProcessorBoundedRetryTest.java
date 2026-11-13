package de.metas.inoutcandidate.async;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidResult;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleUpdater;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateRepository;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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
 * A recompute batch that fails releases its flags and enqueues a retry pass, but only a bounded number of times: a schedule
 * that fails every batch ("poison schedule") must not keep the queue busy forever.
 */
class UpdateInvalidShipmentSchedulesWorkpackageProcessorBoundedRetryTest
{
	private IWorkPackageQueue queue;
	private ShipmentScheduleInvalidateRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(Env.getCtx(), UpdateInvalidShipmentSchedulesWorkpackageProcessor.class);

		repository = spy(new ShipmentScheduleInvalidateRepository());
		Services.registerService(IShipmentScheduleInvalidateRepository.class, repository);
	}

	private static List<I_C_Queue_WorkPackage> recomputeWorkpackages()
	{
		return POJOLookupMap.get().getRecords(I_C_Queue_WorkPackage.class).stream()
				.filter(wp -> UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getName().equals(wp.getC_Queue_PackageProcessor().getClassname()))
				.collect(Collectors.toList());
	}

	private static Optional<I_C_Queue_WorkPackage> nextPendingRecomputeWorkpackage()
	{
		return recomputeWorkpackages().stream()
				.filter(wp -> !wp.isProcessed() && !wp.isError())
				.findFirst();
	}

	private static int retryGenerationOf(final I_C_Queue_WorkPackage workpackage)
	{
		return Services.get(IWorkpackageParamDAO.class)
				.retrieveWorkpackageParams(workpackage)
				.getParameterAsInt(UpdateInvalidShipmentSchedulesWorkpackageProcessor.PARAM_RecomputeRetryGeneration, 0);
	}

	/** what WorkpackageProcessorTask does before invoking the processor */
	private static UpdateInvalidShipmentSchedulesWorkpackageProcessor newProcessorFor(final I_C_Queue_WorkPackage workpackage)
	{
		final UpdateInvalidShipmentSchedulesWorkpackageProcessor processor = new UpdateInvalidShipmentSchedulesWorkpackageProcessor();
		processor.setParameters(Services.get(IWorkpackageParamDAO.class).retrieveWorkpackageParams(workpackage));
		processor.setC_Queue_WorkPackage(workpackage);
		return processor;
	}

	/**
	 * Every batch fails (the real updater, whose schedule retrieval throws) and leaves its tagged flag behind, which the release untags.
	 * The workpackages are run one after another, as the async framework would: each failed one is marked IsError and never polled again.
	 */
	@Test
	void batchThatAlwaysFails_retryPassesAreBoundedByCap_andTheChainEnds()
	{
		final IShipmentSchedulePA shipmentSchedulePA = mock(IShipmentSchedulePA.class);
		when(shipmentSchedulePA.retrieveInvalid(any(), any())).thenThrow(new AdempiereException("poison schedule"));
		Services.registerService(IShipmentSchedulePA.class, shipmentSchedulePA);
		doReturn(1).when(repository).releaseRecomputeMarkerOutOfTrx(any()); // the failed batch's flag is released every time
		Services.registerService(IShipmentScheduleUpdater.class, ShipmentScheduleUpdater.newInstanceForUnitTesting());

		queue.newWorkPackage().buildAndEnqueue(); // the normal pass of a business change

		final int maxIterations = 20;
		int processed = 0;
		for (Optional<I_C_Queue_WorkPackage> next = nextPendingRecomputeWorkpackage(); next.isPresent(); next = nextPendingRecomputeWorkpackage())
		{
			assertThat(processed).as("the chain must end").isLessThan(maxIterations);
			final I_C_Queue_WorkPackage workpackage = next.get();

			// while it runs, the workpackage is locked and not counted by the queue size; after the failure it is IsError and never polled again
			workpackage.setIsError(true);
			InterfaceWrapperHelper.save(workpackage, ITrx.TRXNAME_None);

			assertThatThrownBy(() -> newProcessorFor(workpackage).processWorkPackage(workpackage, ITrx.TRXNAME_None))
					.hasMessageContaining("poison schedule");
			processed++;
		}

		final int cap = UpdateInvalidShipmentSchedulesWorkpackageProcessor.DEFAULT_MaxRetriesAfterFailedBatch;
		final List<Integer> generations = recomputeWorkpackages().stream().map(UpdateInvalidShipmentSchedulesWorkpackageProcessorBoundedRetryTest::retryGenerationOf).collect(Collectors.toList());
		assertThat(generations).as("the normal pass, then one retry pass per generation up to the cap").containsExactly(0, 1, 2, 3);
		assertThat(generations.stream().filter(generation -> generation > 0).count()).isLessThanOrEqualTo(cap);
		assertThat(processed).isEqualTo(cap + 1);
	}

	/** a retry pass that succeeds but leaves backlog hands its generation to the follow-up, so a later failure in that chain cannot restart the count */
	@Test
	void retryPass_limitReached_followUpKeepsGeneration()
	{
		final IShipmentScheduleUpdater updater = mock(IShipmentScheduleUpdater.class);
		when(updater.updateShipmentSchedules(any(ShipmentScheduleUpdateInvalidRequest.class)))
				.thenReturn(ShipmentScheduleUpdateInvalidResult.builder().updatedCount(1).limitReached(true).build());
		Services.registerService(IShipmentScheduleUpdater.class, updater);

		final I_C_Queue_WorkPackage retryPass = queue.newWorkPackage()
				.parameter(UpdateInvalidShipmentSchedulesWorkpackageProcessor.PARAM_RecomputeRetryGeneration, 2)
				.buildAndEnqueue();

		newProcessorFor(retryPass).processWorkPackage(retryPass, ITrx.TRXNAME_None);

		final List<I_C_Queue_WorkPackage> followUps = recomputeWorkpackages().stream()
				.filter(wp -> wp.getC_Queue_WorkPackage_ID() != retryPass.getC_Queue_WorkPackage_ID())
				.collect(Collectors.toList());
		assertThat(followUps).hasSize(1);
		assertThat(retryGenerationOf(followUps.get(0))).isEqualTo(2);
	}

	/** the processor hands the workpackage's generation to the updater */
	@Test
	void retryPass_passesGenerationToUpdater()
	{
		final IShipmentScheduleUpdater updater = mock(IShipmentScheduleUpdater.class);
		final List<ShipmentScheduleUpdateInvalidRequest> requests = new java.util.ArrayList<>();
		when(updater.updateShipmentSchedules(any(ShipmentScheduleUpdateInvalidRequest.class))).thenAnswer(invocation -> {
			requests.add(invocation.getArgument(0));
			return ShipmentScheduleUpdateInvalidResult.builder().updatedCount(0).limitReached(false).build();
		});
		Services.registerService(IShipmentScheduleUpdater.class, updater);

		final I_C_Queue_WorkPackage retryPass = queue.newWorkPackage()
				.parameter(UpdateInvalidShipmentSchedulesWorkpackageProcessor.PARAM_RecomputeRetryGeneration, 1)
				.buildAndEnqueue();
		final I_C_Queue_WorkPackage normalPass = queue.newWorkPackage().buildAndEnqueue();

		newProcessorFor(retryPass).processWorkPackage(retryPass, ITrx.TRXNAME_None);
		newProcessorFor(normalPass).processWorkPackage(normalPass, ITrx.TRXNAME_None);

		assertThat(requests).extracting(ShipmentScheduleUpdateInvalidRequest::getRecomputeRetryGeneration).containsExactly(1, 0);
	}
}
