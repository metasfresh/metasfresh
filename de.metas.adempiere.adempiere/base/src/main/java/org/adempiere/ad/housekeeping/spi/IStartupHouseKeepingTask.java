package org.adempiere.ad.housekeeping.spi;

import de.metas.util.ILoggable;
import de.metas.util.Loggables;

@FunctionalInterface
public interface IStartupHouseKeepingTask
{
	/**
	 * Execute the housekeeping-task. Please log to an {@link ILoggable} obtained via {@link Loggables#get()}. The housekeeping engine provides one.
	 */
	void executeTask();
}
