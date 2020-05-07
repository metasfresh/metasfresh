package de.metas.async.processor;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;

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
