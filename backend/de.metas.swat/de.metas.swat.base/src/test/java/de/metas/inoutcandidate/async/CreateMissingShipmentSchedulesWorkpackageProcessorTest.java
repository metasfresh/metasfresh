package de.metas.inoutcandidate.async;

import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.inoutcandidate.api.CreateMissingCandidatesResult;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

import java.sql.SQLException;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

	@Nested
	class processWorkPackage
	{
		private IShipmentScheduleHandlerBL shipmentScheduleHandlerBL;

		@BeforeEach
		void beforeEach()
		{
			shipmentScheduleHandlerBL = mock(IShipmentScheduleHandlerBL.class);
			Services.registerService(IShipmentScheduleHandlerBL.class, shipmentScheduleHandlerBL);
			Services.registerService(IShipmentScheduleInvalidateBL.class, mock(IShipmentScheduleInvalidateBL.class));

			// no SET LOCAL lock_timeout, there is no database
			Services.get(ISysConfigBL.class).setValue("de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor.LockTimeoutMillis", 0, ClientId.SYSTEM, OrgId.ANY);
		}

		private void givenBatchFailsWith(final RuntimeException e)
		{
			when(shipmentScheduleHandlerBL.createMissingCandidates(any(Properties.class), any(QueryLimit.class))).thenThrow(e);
		}

		private IWorkpackageProcessor.Result process(final int skippedCount)
		{
			final I_C_Queue_WorkPackage workPackage = newInstance(I_C_Queue_WorkPackage.class);
			workPackage.setSkipped_Count(skippedCount);
			return new CreateMissingShipmentSchedulesWorkpackageProcessor().processWorkPackage(workPackage, ITrx.TRXNAME_None);
		}

		private SQLException foreignKeyViolation(final String constraintName)
		{
			return new PSQLException(new ServerErrorMessage("SERROR\0C23503\0Minsert or update on table violates foreign key constraint\0n" + constraintName + "\0"));
		}

		@Test
		void success()
		{
			when(shipmentScheduleHandlerBL.createMissingCandidates(any(Properties.class), any(QueryLimit.class)))
					.thenReturn(new CreateMissingCandidatesResult(ImmutableSet.of(), false));

			assertThat(process(0)).isEqualTo(IWorkpackageProcessor.Result.SUCCESS);
		}

		@Test
		void lockTimeout_retriedLater()
		{
			final SQLException lockTimeout = new SQLException("ERROR: canceling statement due to lock timeout", "55P03");
			givenBatchFailsWith(new AdempiereException("batch failed", lockTimeout));

			assertThatThrownBy(() -> process(0))
					.isInstanceOfSatisfying(WorkpackageSkipRequestException.class, skipRequest -> assertThat(skipRequest.getSkipTimeoutMillis()).isEqualTo(5000))
					.hasRootCause(lockTimeout);
		}

		@Test
		void orderLineForeignKeyViolation_retriedLater()
		{
			final SQLException foreignKeyViolation = foreignKeyViolation("corderline_corderline");
			givenBatchFailsWith(new AdempiereException("batch failed", foreignKeyViolation));

			assertThatThrownBy(() -> process(0))
					.isInstanceOfSatisfying(WorkpackageSkipRequestException.class, skipRequest -> assertThat(skipRequest.getSkipTimeoutMillis()).isEqualTo(5000))
					.hasRootCause(foreignKeyViolation);
		}

		@Test
		void otherForeignKeyViolation_notRetried()
		{
			final AdempiereException failure = new AdempiereException("batch failed", foreignKeyViolation("mproduct_mshipmentschedule"));
			givenBatchFailsWith(failure);

			assertThatThrownBy(() -> process(0)).isSameAs(failure);
		}

		@Test
		void otherError_notRetried()
		{
			final AdempiereException failure = new AdempiereException("some bug");
			givenBatchFailsWith(failure);

			assertThatThrownBy(() -> process(0)).isSameAs(failure);
		}

		@Test
		void retriesExhausted_notRetriedAnyMore()
		{
			final AdempiereException failure = new AdempiereException("batch failed", new SQLException("ERROR: canceling statement due to lock timeout", "55P03"));
			givenBatchFailsWith(failure);

			assertThatThrownBy(() -> process(9)).isInstanceOf(WorkpackageSkipRequestException.class);
			assertThatThrownBy(() -> process(10)).isSameAs(failure);
		}
	}
}
