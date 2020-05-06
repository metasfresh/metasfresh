package de.metas.async.processor;

import de.metas.util.ISingletonService;

/**
 * Class responsible for creating, initializing and configuring of {@link IQueueProcessorsExecutor} implementation.
 * 
 * @author tsa
 * 
 */
public interface IQueueProcessorExecutorService extends ISingletonService
{
	/**
	 * Called to configure and start all {@link IQueueProcessor}.
	 * 
	 * This method can be called many time, but only first time will actually run.
	 * 
	 * If it was successfully executed, the {@link #isInitialized()} will return true.
	 * 
	 * @param delayMillis wait the given number of milliseconds before initializing. Note that for example this delay is supposed to give the adempiereJasper servlet the time it needs to start up
	 *            before jasper-creating package processors kick in.
	 */
	void init(long delayMillis);

	/**
	 * @return true if executor was initialized
	 */
	boolean isInitialized();

	/**
	 * Gets current executor
	 * 
	 * @return current executor
	 * @throws IllegalStateException if the system was not initialized before (by using {@link #init()} method).
	 */
	IQueueProcessorsExecutor getExecutor();

	/**
	 * Removes all queue processors from underlying executor and sets status to not-initialized (i.e. {@link #isInitialized()} returns false).
	 * To initialize, please call {@link #init()}.
	 */
	void removeAllQueueProcessors();

}
