package de.metas.logging;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.concurrent.ThreadSafe;

import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Last error/warning/info holder.
 * 
 * NOTE: in future we will remove this class, because the feature is legacy.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MetasfreshLastError
{
	public static MetasfreshLastError get()
	{
		return instance;
	}

	private static final transient MetasfreshLastError instance = new MetasfreshLastError();

	private static final String LASTERRORINSTANCE_CTXKEY = LastErrorsInstance.class.getName();

	/**
	 * Set and issue Error and save as ValueNamePair
	 *
	 * @param AD_Message message key
	 * @param ex exception
	 */
	public static void saveError(final Logger logger, final String AD_Message, final Throwable ex)
	{
		final boolean issueError = true;
		saveError(logger, AD_Message, ex.getLocalizedMessage(), ex, issueError);
	}

	/**
	 * Set Error and save as ValueNamePair
	 *
	 * @param AD_Message message key
	 * @param message clear text message
	 * @param issueError print error message (default true)
	 */
	public static void saveError(final Logger logger, final String AD_Message, final String message, final boolean issueError)
	{
		final Throwable exception = null;
		saveError(logger, AD_Message, message, exception, issueError);
	}   // saveError

	private static final void saveError(final Logger logger, final String AD_Message, final String message, final Throwable exception, final boolean issueError)
	{
		final ValueNamePair lastError = new ValueNamePair(AD_Message, message);

		final LastErrorsInstance lastErrors = getLastErrorsInstance();
		lastErrors.setLastError(lastError);
		if (exception != null)
		{
			lastErrors.setLastException(exception);
		}

		// print it and also report issue (so we will have an AD_Issue record)
		if (issueError)
		{
			logger.error(AD_Message + " - " + message, exception);
		}
	}   // saveError

	/**
	 * Get Error from Stack
	 *
	 * @return AD_Message as Value and Message as String
	 */
	public static ValueNamePair retrieveError()
	{
		final ValueNamePair vp = getLastErrorsInstance().getLastErrorAndReset();
		return vp;
	}   // retrieveError

	/**
	 * Get Error message from stack
	 *
	 * @param defaultMsg default message (used when there are no errors on stack)
	 * @return error message, or defaultMsg if there is not error message saved
	 * @see #retrieveError()
	 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
	 */
	public static String retrieveErrorString(final String defaultMsg)
	{
		final ValueNamePair vp = retrieveError();
		if (vp == null)
		{
			return defaultMsg;
		}
		return vp.getName();
	}

	/**
	 * Get Error from Stack
	 *
	 * @return last exception
	 */
	public static Throwable retrieveException()
	{
		final Throwable ex = getLastErrorsInstance().getLastExceptionAndReset();
		return ex;
	}

	/**
	 * Save Warning as ValueNamePair.
	 *
	 * @param AD_Message message key
	 * @param message clear text message
	 */
	public static void saveWarning(final Logger logger, final String AD_Message, final String message)
	{
		final ValueNamePair lastWarning = new ValueNamePair(AD_Message, message);
		getLastErrorsInstance().setLastWarning(lastWarning);

		// print it
		if (true)
		{
			logger.warn(AD_Message + " - " + message);
		}
	}   // saveWarning

	/**
	 * Get Warning from Stack
	 *
	 * @return AD_Message as Value and Message as String
	 */
	public static ValueNamePair retrieveWarning()
	{
		final ValueNamePair lastWarning = getLastErrorsInstance().getLastWarningAndReset();
		return lastWarning;
	}   // retrieveWarning

	/**
	 * Save Info as ValueNamePair
	 *
	 * @param AD_Message message key
	 * @param message clear text message
	 */
	public static void saveInfo(final String AD_Message, final String message)
	{
		final ValueNamePair lastInfo = new ValueNamePair(AD_Message, message);
		getLastErrorsInstance().setLastInfo(lastInfo);
	}   // saveInfo

	/**
	 * Get Info from Stack
	 *
	 * @return AD_Message as Value and Message as String
	 */
	public static ValueNamePair retrieveInfo()
	{
		final ValueNamePair lastInfo = getLastErrorsInstance().getLastInfoAndReset();
		return lastInfo;
	}

	/**
	 * Reset Saved Messages/Errors/Info
	 */
	public static void resetLast()
	{
		getLastErrorsInstance().reset();
	}

	private final static LastErrorsInstance getLastErrorsInstance()
	{
		final Properties ctx = Env.getCtx();
		return Env.get(ctx, LASTERRORINSTANCE_CTXKEY, LastErrorsInstance::new);
	}

	/**
	 * Holds last error/exception, warning and info.
	 *
	 * @author tsa
	 */
	@ThreadSafe
	@SuppressWarnings("serial")
	private static class LastErrorsInstance implements Serializable
	{
		private ValueNamePair lastError;
		private Throwable lastException;
		private ValueNamePair lastWarning;
		private ValueNamePair lastInfo;

		private LastErrorsInstance()
		{
			super();
		}

		@Override
		public synchronized String toString()
		{
			final StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("[");
			sb.append("lastError=").append(lastError);
			sb.append(", lastException=").append(lastException);
			sb.append(", lastWarning=").append(lastWarning);
			sb.append(", lastInfo=").append(lastInfo);
			sb.append("]");
			return sb.toString();
		}

		public synchronized ValueNamePair getLastErrorAndReset()
		{
			final ValueNamePair lastErrorToReturn = lastError;
			lastError = null;
			return lastErrorToReturn;
		}

		public synchronized void setLastError(final ValueNamePair lastError)
		{
			this.lastError = lastError;
		}

		public synchronized Throwable getLastExceptionAndReset()
		{
			final Throwable lastExceptionAndClear = lastException;
			lastException = null;
			return lastExceptionAndClear;
		}

		public synchronized void setLastException(final Throwable lastException)
		{
			this.lastException = lastException;
		}

		public synchronized ValueNamePair getLastWarningAndReset()
		{
			final ValueNamePair lastWarningToReturn = lastWarning;
			lastWarning = null;
			return lastWarningToReturn;
		}

		public synchronized void setLastWarning(final ValueNamePair lastWarning)
		{
			this.lastWarning = lastWarning;
		}

		public synchronized ValueNamePair getLastInfoAndReset()
		{
			final ValueNamePair lastInfoToReturn = lastInfo;
			lastInfo = null;
			return lastInfoToReturn;
		}

		public synchronized void setLastInfo(final ValueNamePair lastInfo)
		{
			this.lastInfo = lastInfo;
		}

		public synchronized void reset()
		{
			lastError = null;
			lastException = null;
			lastWarning = null;
			lastInfo = null;
		}
	}

}
