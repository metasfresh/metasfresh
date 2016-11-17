package org.adempiere.ad.trx.processor.api;

import org.adempiere.util.ILoggable;

/**
 * An {@link ITrxItemProcessorExecutor}'s exception handler which just logs the exception to {@link ILoggable} and does nothing else.
 *
 * @author tsa
 *
 */
public final class LoggableTrxItemExceptionHandler implements ITrxItemExceptionHandler
{
	public static final LoggableTrxItemExceptionHandler instance = new LoggableTrxItemExceptionHandler();

	private LoggableTrxItemExceptionHandler()
	{
		super();
	}

	@Override
	public void onNewChunkError(final Exception e, final Object item)
	{
		ILoggable.THREADLOCAL.getLoggable().addLog("Error while trying to create a new chunk for item: " + item, e);
	}

	@Override
	public void onItemError(final Exception e, final Object item)
	{
		ILoggable.THREADLOCAL.getLoggable().addLog("Error while trying to process item: " + item, e);
	}

	@Override
	public void onCompleteChunkError(final Exception e)
	{
		ILoggable.THREADLOCAL.getLoggable().addLog("Error while completing current chunk", e);
	}

	@Override
	public void onCommitChunkError(final Exception e)
	{
		ILoggable.THREADLOCAL.getLoggable().addLog("Processor failed to commit current chunk => rollback transaction", e);
	}

	@Override
	public void onCancelChunkError(Exception e)
	{
		ILoggable.THREADLOCAL.getLoggable().addLog("Error while cancelling current chunk. Ignored.", e);
	}
}
