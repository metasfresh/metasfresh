package de.metas.async.api.impl;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.util.time.TimeSource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;

public class AsyncBatchBLTest
{
	/** service under test */
	private AsyncBatchBL asyncBatchBL;

	private Properties ctx;

	private Timestamp now = null; // to be set in tests

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();

		this.asyncBatchBL = (AsyncBatchBL)Services.get(IAsyncBatchBL.class);

		this.now = SystemTime.asTimestamp();
		SystemTime.setTimeSource(new TimeSource()
		{

			@Override
			public long millis()
			{
				Check.assumeNotNull(now, "now shall be configured first");
				return now.getTime();
			}
		});
	}

	@Test
	public void test_Processed()
	{
		
		final Timestamp FirstEnqueued = TimeUtil.addMinutes(now, -5);
		final Timestamp LastEnqueued = TimeUtil.addMinutes(now, -4);
		final Timestamp LastProcessed = TimeUtil.addMinutes(now, -3);
		final int CountEnqueued = 2;
		final int CountProcessed = 2;

		final I_C_Async_Batch asyncBatch = newAsyncBatch();
		asyncBatch.setFirstEnqueued(FirstEnqueued);
		asyncBatch.setLastEnqueued(LastEnqueued);
		asyncBatch.setLastProcessed(LastProcessed);
		
		asyncBatch.setCountEnqueued(CountEnqueued);
		asyncBatch.setCountProcessed(CountProcessed);
		final boolean processedExpected = true;

		Assert.assertEquals("Invalid Processed", processedExpected, asyncBatchBL.checkProcessed(asyncBatch));
	}

	private I_C_Async_Batch newAsyncBatch()
	{
		return InterfaceWrapperHelper.create(ctx, I_C_Async_Batch.class, ITrx.TRXNAME_None);
	}

}
