package de.metas.error;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/** If a loggable {@link ILoggable#addLog(String, Object...)} last {@code msgParameter} is a throwable, this helper extracts the throwable and creates an AD_Issue. */
@UtilityClass
public class LoggableWithThrowableUtil
{

	private static final Logger logger = LogManager.getLogger(LoggableWithThrowableUtil.class);

	private static final ThreadLocal<Boolean> adIssueCreationSuppressed = ThreadLocal.withInitial(() -> Boolean.FALSE);

	/**
	 * While the returned {@link IAutoCloseable} is open, this class will NOT turn a logged {@link Throwable} into an
	 * AD_Issue on the current thread; it only logs it.
	 * <p>
	 * Needed because persisting an AD_Issue is itself a database write that runs inside the transaction manager. If
	 * that write fails, the transaction manager logs the failure through the ambient {@link ILoggable} — and if that
	 * loggable routes throwables back into AD_Issue creation, each failed attempt creates another AD_Issue whose save
	 * fails the same way. The recursion is unbounded and every level carries the stacktrace of the level below it, so
	 * the strings grow until the JVM dies with an OutOfMemoryError.
	 * <p>
	 * {@code de.metas.error.impl.ErrorManager} opens this region around issue creation, which bounds the depth at one.
	 */
	public IAutoCloseable suppressAdIssueCreation()
	{
		final Boolean previous = adIssueCreationSuppressed.get();
		adIssueCreationSuppressed.set(Boolean.TRUE);
		return () -> adIssueCreationSuppressed.set(previous);
	}

	public boolean isAdIssueCreationSuppressed()
	{
		return Boolean.TRUE.equals(adIssueCreationSuppressed.get());
	}

	public FormattedMsgWithAdIssueId extractMsgAndAdIssue(@NonNull final String msg, final Object... msgParameters)
	{
		final IErrorManager errorManager = Services.get(IErrorManager.class);

		final Throwable exception = LoggableWithThrowableUtil.extractThrowable(msgParameters);
		Object[] msgParametersEffective = msgParameters;
		AdIssueId adIssueId = null;
		if (exception != null && isAdIssueCreationSuppressed())
		{
			// We are already creating an AD_Issue on this thread and something failed while doing so.
			// Creating another AD_Issue for that failure would recurse into the very code that is failing.
			logger.warn("Failed while creating an AD_Issue; logging the nested exception instead of creating another AD_Issue.", exception);
			msgParametersEffective = LoggableWithThrowableUtil.removeLastElement(msgParameters);
		}
		else if (exception != null)
		{
			try
			{
				adIssueId = errorManager.createIssue(exception);
				msgParametersEffective = LoggableWithThrowableUtil.removeLastElement(msgParameters);
			}
			catch (final Exception createIssueException)
			{
				createIssueException.addSuppressed(exception);
				logger.warn("Failed creating AD_Issue for exception: Skip creating the AD_Issue.", createIssueException);
			}
		}

		//
		String messageFormatted;
		try
		{
			messageFormatted = StringUtils.formatMessage(msg, msgParametersEffective);
		}
		catch (final Exception formatMessageException)
		{
			logger.warn("Failed creating log entry for msg={} and msgParametes={}. Creating a fallback one instead",
					msg, msgParametersEffective, formatMessageException);

			messageFormatted = (msg != null ? msg : "")
					+ (msgParameters != null && msgParameters.length > 0 ? " -- parameters: " + Arrays.asList(msgParameters) : "");
		}

		return new FormattedMsgWithAdIssueId(messageFormatted, Optional.ofNullable(adIssueId));
	}

	private Throwable extractThrowable(final Object[] msgParameters)
	{
		if (msgParameters == null || msgParameters.length == 0)
		{
			return null;
		}

		final Object lastEntry = msgParameters[msgParameters.length - 1];
		return lastEntry instanceof Throwable
				? (Throwable)lastEntry
				: null;
	}

	private Object[] removeLastElement(final Object[] msgParameters)
	{
		if (msgParameters == null || msgParameters.length == 0)
		{
			return msgParameters;
		}
		final int newLen = msgParameters.length - 1;
		final Object[] msgParametersNew = new Object[newLen];
		System.arraycopy(msgParameters, 0, msgParametersNew, 0, newLen);
		return msgParametersNew;
	}

	@Value
	public static class FormattedMsgWithAdIssueId
	{
		String formattedMessage;

		Optional<AdIssueId> adIsueId;
	}

}
