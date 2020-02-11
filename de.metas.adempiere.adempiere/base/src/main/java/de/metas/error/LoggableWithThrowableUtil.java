package de.metas.error;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Services;
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

/** If a loggable {@link ILoggable#addLog(String, Object...)} last mesParameter is a throwable, this help extracts the throwable and creates an AD_Issue. */
@UtilityClass
public class LoggableWithThrowableUtil
{

	private static final Logger logger = LogManager.getLogger(LoggableWithThrowableUtil.class);

	public FormattedMsgWithAdIssueId extractMsgAndAdIssue(@NonNull final String msg, final Object... msgParameters)
	{
		final IErrorManager errorManager = Services.get(IErrorManager.class);

		final Throwable exception = LoggableWithThrowableUtil.extractThrowable(msgParameters);
		Object[] msgParametersEffective = msgParameters;
		AdIssueId adIssueId = null;
		if (exception != null)
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
