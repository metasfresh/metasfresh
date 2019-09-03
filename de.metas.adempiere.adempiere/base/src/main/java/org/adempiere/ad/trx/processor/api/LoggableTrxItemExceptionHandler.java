package org.adempiere.ad.trx.processor.api;

import de.metas.util.ILoggable;
import de.metas.util.Loggables;

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
	public void onNewChunkError(final Throwable e, final Object item)
	{
		Loggables.addLog("Error while trying to create a new chunk for item={}; exception={}", item, e);
	}

	@Override
	public void onItemError(final Throwable e, final Object item)
	{
		Loggables.addLog("Error while trying to process item={}; exception={}", item, e);
	}

	@Override
	public void onCompleteChunkError(final Throwable e)
	{
		Loggables.addLog("Error while completing current chunk; exception={}", e);
	}

	@Override
	public void afterCompleteChunkError(final Throwable e)
	{
		// nothing to do.
		// error was already logged by onCompleteChunkError
	}

	@Override
	public void onCommitChunkError(final Throwable e)
	{
		Loggables.addLog("Processor failed to commit current chunk => rollback transaction; exception={}", e);
	}

	@Override
	public void onCancelChunkError(final Throwable e)
	{
		Loggables.addLog("Error while cancelling current chunk. Ignored; exception={}", e);
	}
}
