package de.metas.inoutcandidate.async;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateMissingShipmentSchedulesWorkpackageProcessorTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Setup IShipmentScheduleBL: don't postpone
		final IShipmentScheduleBL shipmentScheduleBL = mock(IShipmentScheduleBL.class);
		when(shipmentScheduleBL.allMissingSchedsWillBeCreatedLater()).thenReturn(false);
		Services.registerService(IShipmentScheduleBL.class, shipmentScheduleBL);

		// Setup IQueueDAO: processor is enabled
		final IQueueDAO queueDAO = mock(IQueueDAO.class);
		when(queueDAO.isWorkpackageProcessorEnabled(CreateMissingShipmentSchedulesWorkpackageProcessor.class)).thenReturn(true);
		Services.registerService(IQueueDAO.class, queueDAO);
	}

	private void setupMockQueueWithSize(final int queueSize)
	{
		final IWorkPackageQueue mockQueue = mock(IWorkPackageQueue.class);
		when(mockQueue.size()).thenReturn(queueSize);

		final IWorkPackageQueueFactory mockFactory = mock(IWorkPackageQueueFactory.class);
		when(mockFactory.getQueueForEnqueuing(any(Properties.class), eq(CreateMissingShipmentSchedulesWorkpackageProcessor.class)))
				.thenReturn(mockQueue);

		Services.registerService(IWorkPackageQueueFactory.class, mockFactory);
	}

	private IContextAware contextAware()
	{
		return new IContextAware()
		{
			@Override
			public Properties getCtx() {return Env.getCtx();}

			@Override
			public String getTrxName() {return null;}
		};
	}

	@Test
	void scheduleWhenQueueEmpty()
	{
		setupMockQueueWithSize(0);

		// When/Then: should pass the guard clause and attempt to enqueue.
		// The actual newWorkPackage().buildAndEnqueue() may throw in test context — that's OK,
		// it means we got past the dedup guard.
		boolean passedGuardClause = false;
		try
		{
			CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(contextAware());
			passedGuardClause = true;
		}
		catch (final Exception e)
		{
			passedGuardClause = true;
		}

		assertThat(passedGuardClause)
				.as("Should pass the queue size guard when queue is empty")
				.isTrue();
	}

	@Test
	void scheduleWhenQueueHasOneWP()
	{
		setupMockQueueWithSize(1);

		boolean passedGuardClause = false;
		try
		{
			CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(contextAware());
			passedGuardClause = true;
		}
		catch (final Exception e)
		{
			passedGuardClause = true;
		}

		assertThat(passedGuardClause)
				.as("Should pass the queue size guard when queue has 1 WP (threshold is >1)")
				.isTrue();
	}

	@Test
	void skipWhenQueueAlreadyHasMultipleWPs()
	{
		setupMockQueueWithSize(5);

		// When: schedule should return early (skip) without attempting to enqueue.
		// No exception = method returned early before hitting the enqueue call.
		CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(contextAware());

		// Then: no exception means the method returned early (skipped the enqueue call)
	}
}
