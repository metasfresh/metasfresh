package de.metas.async.api.impl;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.impl.CheckProcessedAsynBatchWorkpackageProcessor;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;

public class AsyncBatchBLTest
{
	/**
	 * service under test
	 */
	private AsyncBatchBL asyncBatchBL;

	private Properties ctx;

	private Timestamp now = null; // to be set in tests

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();

		this.asyncBatchBL = new AsyncBatchBL();
		this.asyncBatchBL.setUseMetasfreshSystemTime(true);

		this.now = de.metas.common.util.time.SystemTime.asTimestamp();
		SystemTime.setFixedTimeSource(TimeUtil.asZonedDateTime(now));
	}

	@Test
	public void givenNotEnoughTimePassedSinceLastEnqueued_whenGetTimeUntilProcessedRecheck_thenReturnTimeToWait()
	{
		//given
		final Timestamp FirstEnqueued = TimeUtil.addMinutes(now, -5);
		final Timestamp LastEnqueued = TimeUtil.addMinutes(now, -4);
		final Timestamp LastProcessed = TimeUtil.addMinutes(now, +1);

		final I_C_Async_Batch asyncBatch = newAsyncBatch();
		asyncBatch.setFirstEnqueued(FirstEnqueued);
		asyncBatch.setLastEnqueued(LastEnqueued);
		asyncBatch.setLastProcessed(LastProcessed);
		InterfaceWrapperHelper.save(asyncBatch);

		//when
		final Duration timeToWait = asyncBatchBL.getTimeUntilProcessedRecheck(asyncBatch);

		//then
		final long expected = TimeUtil.getMillisBetween(now, TimeUtil.addMillis(LastProcessed, 1));
		Assertions.assertThat(timeToWait.toMillis()).isEqualTo(expected);
	}

	private I_C_Async_Batch newAsyncBatch()
	{
		return InterfaceWrapperHelper.create(ctx, I_C_Async_Batch.class, ITrx.TRXNAME_None);
	}

	@Nested
	class EnqueueAsyncBatch
	{
		@Test
		public void nonConsumerType_skipsCheckProcessedWorkPackage()
		{
			// given: a C_Async_Batch_Type that nobody consumes (IsCheckProcessed='N', no boilerplate)
			final I_C_Async_Batch_Type asyncBatchType = newAsyncBatchType();
			asyncBatchType.setIsCheckProcessed(false);
			asyncBatchType.setAD_BoilerPlate_ID(0);
			InterfaceWrapperHelper.save(asyncBatchType);

			final AsyncBatchId asyncBatchId = createAsyncBatchOfType(asyncBatchType);

			// when
			asyncBatchBL.enqueueAsyncBatch(asyncBatchId);

			// then: AC1 - the gate must SKIP creating the CheckProcessedAsynBatch work-package
			Assertions.assertThat(countCheckProcessedWorkPackages()).isZero();
		}

		@Test
		public void consumerType_createsCheckProcessedWorkPackage()
		{
			// given: a C_Async_Batch_Type that IS a consumer of the Processed flag (IsCheckProcessed='Y')
			final I_C_Async_Batch_Type asyncBatchType = newAsyncBatchType();
			asyncBatchType.setIsCheckProcessed(true);
			asyncBatchType.setAD_BoilerPlate_ID(0);
			InterfaceWrapperHelper.save(asyncBatchType);

			final AsyncBatchId asyncBatchId = createAsyncBatchOfType(asyncBatchType);

			// when
			asyncBatchBL.enqueueAsyncBatch(asyncBatchId);

			// then: AC2 - the gate must CREATE exactly one CheckProcessedAsynBatch work-package
			Assertions.assertThat(countCheckProcessedWorkPackages()).isEqualTo(1);
		}

		@Test
		public void boilerplateType_createsCheckProcessedWorkPackage()
		{
			// given: IsCheckProcessed='N' but AD_BoilerPlate_ID>0 -> boilerplate is also a consumer of the Processed flag
			final I_C_Async_Batch_Type asyncBatchType = newAsyncBatchType();
			asyncBatchType.setIsCheckProcessed(false);
			asyncBatchType.setAD_BoilerPlate_ID(1_000_000);
			InterfaceWrapperHelper.save(asyncBatchType);

			final AsyncBatchId asyncBatchId = createAsyncBatchOfType(asyncBatchType);

			// when
			asyncBatchBL.enqueueAsyncBatch(asyncBatchId);

			// then
			Assertions.assertThat(countCheckProcessedWorkPackages()).isEqualTo(1);
		}

		@Test
		public void nullBatchRecord_doesNotThrowNPE_andEnqueues()
		{
			// given: the DAO returns null for the id (the production "record not found out-of-trx" path;
			// the in-memory POJOLookupMap would instead throw, so we register a DAO that returns null).
			final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(999_999);
			Services.registerService(IAsyncBatchDAO.class, new AsyncBatchDAO()
			{
				@Override
				public I_C_Async_Batch retrieveAsyncBatchRecordOutOfTrx(final AsyncBatchId id)
				{
					return null;
				}
			});
			final AsyncBatchBL blWithNullReturningDao = new AsyncBatchBL();
			blWithNullReturningDao.setUseMetasfreshSystemTime(true);

			// then: the null-safe overload yields empty rather than NPE...
			Assertions.assertThat(blWithNullReturningDao.getAsyncBatchType(asyncBatchId)).isEmpty();

			// ...and the enqueue gate must not NPE (defaults to enqueuing the CheckProcessed WP)
			Assertions.assertThatCode(() -> blWithNullReturningDao.enqueueAsyncBatch(asyncBatchId))
					.doesNotThrowAnyException();
			Assertions.assertThat(countCheckProcessedWorkPackages()).isEqualTo(1);

			// ...and the CheckProcessed processWorkPackage chain (updateProcessedOutOfTrx -> keepAliveTimeExpired)
			// must not NPE either. updateProcessedOutOfTrx runs FIRST, so it must handle the null record:
			// a vanished batch -> "processed" so the checker WP completes rather than looping forever.
			Assertions.assertThat(blWithNullReturningDao.updateProcessedOutOfTrx(asyncBatchId)).isTrue();
			Assertions.assertThat(blWithNullReturningDao.keepAliveTimeExpired(asyncBatchId)).isFalse();
			Assertions.assertThatCode(() -> blWithNullReturningDao.increaseProcessed(newAsyncBatchWorkPackage(asyncBatchId)))
					.doesNotThrowAnyException();

			// ...and the enqueued-count updaters (increaseEnqueued/decreaseEnqueued -> setAsyncBatchCountEnqueued)
			// must not NPE on the null out-of-trx record path either.
			Assertions.assertThatCode(() -> blWithNullReturningDao.increaseEnqueued(newAsyncBatchWorkPackage(asyncBatchId)))
					.doesNotThrowAnyException();
			Assertions.assertThatCode(() -> blWithNullReturningDao.decreaseEnqueued(newAsyncBatchWorkPackage(asyncBatchId)))
					.doesNotThrowAnyException();
		}
	}

	private I_C_Queue_WorkPackage newAsyncBatchWorkPackage(final AsyncBatchId asyncBatchId)
	{
		final I_C_Queue_WorkPackage workPackage = InterfaceWrapperHelper.create(ctx, I_C_Queue_WorkPackage.class, ITrx.TRXNAME_None);
		workPackage.setC_Async_Batch_ID(asyncBatchId.getRepoId());
		InterfaceWrapperHelper.save(workPackage);
		return workPackage;
	}

	private I_C_Async_Batch_Type newAsyncBatchType()
	{
		final I_C_Async_Batch_Type asyncBatchType = InterfaceWrapperHelper.create(ctx, I_C_Async_Batch_Type.class, ITrx.TRXNAME_None);
		asyncBatchType.setInternalName(getClass().getSimpleName() + "_" + UUID.randomUUID());
		return asyncBatchType;
	}

	private AsyncBatchId createAsyncBatchOfType(final I_C_Async_Batch_Type asyncBatchType)
	{
		final I_C_Async_Batch asyncBatch = newAsyncBatch();
		asyncBatch.setC_Async_Batch_Type_ID(asyncBatchType.getC_Async_Batch_Type_ID());
		InterfaceWrapperHelper.save(asyncBatch);

		return AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID());
	}

	/**
	 * Counts the real {@link I_C_Queue_WorkPackage} rows enqueued for {@link CheckProcessedAsynBatchWorkpackageProcessor} -
	 * i.e. the actual side effect that {@link AsyncBatchBL#enqueueAsyncBatch(AsyncBatchId)} is supposed to gate.
	 */
	private long countCheckProcessedWorkPackages()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Queue_WorkPackage.class)
				.create()
				.stream()
				.filter(workPackage -> workPackage.getC_Queue_PackageProcessor() != null
						&& CheckProcessedAsynBatchWorkpackageProcessor.class.getCanonicalName().equals(workPackage.getC_Queue_PackageProcessor().getClassname()))
				.count();
	}
}
