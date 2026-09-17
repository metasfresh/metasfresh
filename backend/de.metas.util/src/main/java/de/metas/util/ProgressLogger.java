package de.metas.util;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

/**
 * Please use {@link ILoggable#newProgress()}, {@link ILoggable#prepareProgress()}
 */
@ToString(of = { "countItemsProcessed" })
public class ProgressLogger
{
	@NonNull private final ILoggable loggable;
	private final int maxItemsToLog;
	@Getter private int countItemsProcessed;

	@Builder(access = AccessLevel.PACKAGE)
	private ProgressLogger(
			@NonNull ILoggable loggable,
			@Nullable final Integer maxItemsToLog)
	{
		this.loggable = loggable;
		this.maxItemsToLog = maxItemsToLog != null && maxItemsToLog >= 0 ? maxItemsToLog : -1;
	}

	// declared just to make sure is public/accessible
	public static class ProgressLoggerBuilder
	{
		public ProgressLoggerBuilder() {}
	}

	public void itemProcessed(@Nullable Object item)
	{
		itemProcessed("{}", item);
	}

	public void itemProcessed(@NonNull String msg, Object... msgParameters)
	{
		countItemsProcessed++;

		if (maxItemsToLog < 0 || countItemsProcessed <= maxItemsToLog)
		{
			loggable.addLog(msg, msgParameters);
		}
	}

	public void done(@NonNull final String message)
	{
		loggable.addLog(message, countItemsProcessed);
	}
}
