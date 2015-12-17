/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.annotation.concurrent.ThreadSafe;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.compiere.model.MSession;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * Adempiere Logger. <br>
 * Log levels can be configured with <code>AD_SysConfig</code> (see {@link ISysConfigBL}) using either
 * <p>
 * <code>CLogger.Level."loggername"</code><br>
 * or<br>
 * <code>CLogger.Level."hostname"."loggername"</code> where hostname is the host's name as it appears in <code>AD_Session.Remote_Host</code>. There are some restrictions though:
 * <ul>
 * <li>Loggers that are initialized too early before there is an {@link Env#getCtx()} and an {@link MSession#get()} won't get their log level customized.
 * <li>The whole feature is disabled in unit test mode (i.e. if {@link Adempiere#isUnitTestMode()} returns <code>true</code>).
 * </ul>
 *
 * @author Jorg Janke
 * @author tsa
 * @author ts
 */
public class CLogger extends Logger implements Serializable
{
	private static final long serialVersionUID = 6492376264463028357L;

	/* package */static final String LOGGERNAME_DEFAULT = "org.adempiere.default";
	private static final String LASTERRORINSTANCE_CTXKEY = LastErrorsInstance.class.getName();
	
	/* package */static final String LOGGERNAME_MODULE_PREFIX = "module:";

	private static final ILoggerCustomizer loggerCustomizer = SysConfigLoggerCustomizer.instance;

	public static synchronized CLogger getCLogger(final String className)
	{
		// do the actual work of getting the logger
		final CLogger logger = getCLogger0(className);

		// Customize the logger if needed
		loggerCustomizer.customize(logger);

		return logger;
	}

	/**
	 * Get Logger
	 *
	 * @param className class name
	 * @return Logger
	 */
	private static synchronized CLogger getCLogger0(String className)
	{
		final LogManager manager = LogManager.getLogManager();
		if (className == null)
		{
			className = "";
		}

		final Logger result = manager.getLogger(className);
		if (result != null && result instanceof CLogger)
		{
			return (CLogger)result;
		}
		//
		final String resourceBundleName = null; // No resource bundle
		final CLogger newLogger = new CLogger(className, resourceBundleName);
		newLogger.setLevel(CLogMgt.getLevel());
		manager.addLogger(newLogger);
		return newLogger;
	}	// getLogger

	/**
	 * Get Logger
	 *
	 * @param clazz class name
	 * @return Logger
	 */
	public static CLogger getCLogger(final Class<?> clazz)
	{
		if (clazz == null)
		{
			return get();
		}
		return getCLogger(clazz.getName());
	}	// getLogger
	
	public static final String createModuleLoggerName(final String moduleName)
	{
		Check.assumeNotEmpty(moduleName, "moduleName not empty");
		return LOGGERNAME_MODULE_PREFIX + moduleName.trim();
	}
	
	/**
	 * Get default Adempiere Logger.
	 * Need to be used in serialized objects
	 *
	 * @return logger
	 */
	public static CLogger get()
	{
		return defaultLoggerSupplier.get();
	}

	/** Default Logger */
	private static final Supplier<CLogger> defaultLoggerSupplier = Suppliers.memoize(new Supplier<CLogger>()
	{

		@Override
		public CLogger get()
		{
			return getCLogger(LOGGERNAME_DEFAULT);
		}
	});

	/**************************************************************************
	 * Standard constructor
	 *
	 * @param name logger name
	 * @param resourceBundleName optional resource bundle (ignored)
	 */
	private CLogger(final String name, final String resourceBundleName)
	{
		super(name, resourceBundleName);
		// setLevel(Level.ALL);
	}	// CLogger

	private final static LastErrorsInstance getLastErrorsInstance()
	{
		final Properties ctx = Env.getCtx();
		return Env.get(ctx, LASTERRORINSTANCE_CTXKEY, LastErrorsInstance.supplier);
	}

	/**
	 * Set and issue Error and save as ValueNamePair
	 *
	 * @param AD_Message message key
	 * @param ex exception
	 */
	public void saveError(final String AD_Message, final Throwable ex)
	{
		final boolean issueError = true;
		saveError(AD_Message, ex.getLocalizedMessage(), ex, issueError);
	}   // saveError

	/**
	 * Set Error and save as ValueNamePair
	 *
	 * @param AD_Message message key
	 * @param message clear text message
	 * @param issueError print error message (default true)
	 */
	public void saveError(final String AD_Message, final String message, final boolean issueError)
	{
		final Throwable exception = null;
		saveError(AD_Message, message, exception, issueError);
	}   // saveError

	private final void saveError(final String AD_Message, final String message, final Throwable exception, final boolean issueError)
	{
		final ValueNamePair lastError = new ValueNamePair(AD_Message, message);

		final LastErrorsInstance lastErrors = getLastErrorsInstance();
		lastErrors.setLastError(lastError);
		if (exception != null)
		{
			lastErrors.setLastException(exception);
		}

		// print it and also report issue
		// NOTE: see org.compiere.util.CLogErrorBuffer.publish(LogRecord) - there the issue is reported
		if (issueError)
		{
			log(Level.SEVERE, AD_Message + " - " + message, exception);
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
	}   // retrieveError

	/**
	 * Save Warning as ValueNamePair.
	 *
	 * @param AD_Message message key
	 * @param message clear text message
	 */
	public void saveWarning(final String AD_Message, final String message)
	{
		final ValueNamePair lastWarning = new ValueNamePair(AD_Message, message);
		getLastErrorsInstance().setLastWarning(lastWarning);

		// print it
		if (true)
		{
			warning(AD_Message + " - " + message);
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
	public void saveInfo(final String AD_Message, final String message)
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

	/**
	 * Get root cause
	 *
	 * @param t
	 * @return Throwable
	 */
	public static Throwable getRootCause(final Throwable t)
	{
		Throwable cause = t;
		while (cause.getCause() != null)
		{
			cause = cause.getCause();
		}
		return cause;
	}
	
	private Level maxLevel = Level.OFF;

	/**
	 * Sets the maximum level that this logger allows to be set by {@link #setLevel(Level)}.
	 * 
	 * NOTE: the Levels ordering is from SEVERE (highest value) to FINEST  (lowest value).
	 * So for example, if you set the maximum level to be INFO, then calling {@link #setLevel(Level)} with WARNING it will actually call the super {@link #setLevel(Level)} with INFO,
	 * while calling {@link #setLevel(Level)} with FINE will call super {@link #setLevel(Level)} with FINE.
	 * 
	 * @param maxLevel
	 */
	public void setMaxLevel(final Level maxLevel)
	{
		this.maxLevel = maxLevel;
	}
	
	/**
	 * 
	 * @return maximum level to be allowed for logging
	 */
	public Level getMaxLevel()
	{
		return this.maxLevel;
	}
	
	@Override
	public void setLevel(final Level newLevel) throws SecurityException
	{
		Level newLevelActual = newLevel;
		
		//
		// Make sure requested level is not higher then maximum allowed level.
		// NOTE: e.g. OFF > SEVERE > WARNING > INFO > FINE > ALL
		final Level maxLevel = getMaxLevel();
		if (maxLevel != null && newLevel != null
				&& newLevel.intValue() > maxLevel.intValue())
		{
			newLevelActual = maxLevel;
		}
		
		super.setLevel(newLevelActual);
	}
	
	/**
	 * Advice the logging system to not report AD_Issues for this logger.
	 * @return
	 */
	public CLogger setSkipIssueReporting()
	{
		CLogMgt.getErrorBuffer().skipIssueReportingForLoggerName(getName());
		return this;
	}
	
	public void log(final Level level, final String msg, final Object param1, final Object param2)
	{
		if (!isLoggable(level))
		{
			return;
		}
		log(level, msg, new Object[] { param1, param2 });
	}

	public void log(final Level level, final String msg, final Object param1, final Object param2, final Object param3)
	{
		if (!isLoggable(level))
		{
			return;
		}
		log(level, msg, new Object[] { param1, param2, param3 });
	}

	public void log(final Level level, final String msg, final Object param1, final Object param2, final Object param3, final Object param4)
	{
		if (!isLoggable(level))
		{
			return;
		}
		log(level, msg, new Object[] { param1, param2, param3, param4 });
	}

	/**
	 * Holds last error/exception, warning and info.
	 *
	 * @author tsa
	 */
	@ThreadSafe
	private static class LastErrorsInstance
	{
		public static final transient Supplier<LastErrorsInstance> supplier = new Supplier<LastErrorsInstance>()
		{

			@Override
			public LastErrorsInstance get()
			{
				return new LastErrorsInstance();
			}
		};

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
}	// CLogger
