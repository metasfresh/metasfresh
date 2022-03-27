package de.metas.async.api.impl;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Properties;

public class AsyncBatchBLTest
{
	/**
	 * service under test
	 */
	private AsyncBatchBL asyncBatchBL;

	private Properties ctx;

	private Timestamp now = null; // to be set in tests

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();

		this.asyncBatchBL = (AsyncBatchBL)Services.get(IAsyncBatchBL.class);

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
		Assert.assertEquals(TimeUtil.getMillisBetween(now, TimeUtil.addMillis(LastProcessed, 1)), timeToWait.toMillis());
	}

	private I_C_Async_Batch newAsyncBatch()
	{
		return InterfaceWrapperHelper.create(ctx, I_C_Async_Batch.class, ITrx.TRXNAME_None);
	}
}
