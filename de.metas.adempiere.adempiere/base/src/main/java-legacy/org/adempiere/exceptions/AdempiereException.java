/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.adempiere.exceptions;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.logging.LoggingHelper;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.MetasfreshLastError;

/**
 * Any exception that occurs inside the Adempiere core
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class AdempiereException extends RuntimeException
		implements IIssueReportableAware
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1813037338765245293L;

	/**
	 * Wraps given <code>throwable</code> as {@link AdempiereException}, if it's not already an {@link AdempiereException}.<br>
	 * Note that this method also tries to pick the most specific adempiere exception (work in progress).
	 *
	 * @param throwable
	 * @return {@link AdempiereException} or <code>null</code> if the throwable was null.
	 */
	public static AdempiereException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}

		final Throwable cause = extractCause(throwable);

		if (cause instanceof AdempiereException)
		{
			return (AdempiereException)cause;
		}

		if (cause instanceof SQLException)
		{
			return DBException.wrapIfNeeded(cause);
		}

		if (cause != throwable)
		{
			return wrapIfNeeded(cause);
		}

		// default
		return new AdempiereException(extractMessage(cause), cause);
	}

	/**
	 * Extracts throwable message.
	 *
	 * @param throwable
	 * @return message; never return null
	 */
	public static final String extractMessage(final Throwable throwable)
	{
		// guard against NPE, shall not happen
		if (throwable == null)
		{
			return "null";
		}

		String message = throwable.getLocalizedMessage();

		// If throwable message is null or it's very short then it's better to use throwable.toString()
		if (message == null || message.length() < 4)
		{
			message = throwable.toString();
		}

		return message;
	}

	/**
	 * Extract cause exception from those exceptions which are only about wrapping the real cause (e.g. ExecutionException, InvocationTargetException).
	 *
	 * @param throwable
	 * @return cause or throwable; never returns null
	 */
	public static final Throwable extractCause(final Throwable throwable)
	{
		final Throwable cause = throwable.getCause();
		if (cause == null)
		{
			return throwable;
		}

		if (throwable instanceof ExecutionException)
		{
			return cause;
		}
		if (throwable instanceof com.google.common.util.concurrent.UncheckedExecutionException)
		{
			return cause;
		}

		if (throwable instanceof InvocationTargetException)
		{
			return cause;
		}

		return throwable;
	}

	/**
	 * Convenient method to suppress a given exception if there is an already main exception which is currently thrown.
	 *
	 * @param exceptionToSuppress
	 * @param mainException
	 * @throws AdempiereException if mainException was null. It will actually be the exceptionToSuppress, wrapped to AdempiereException if it was needed.
	 */
	public static final void suppressOrThrow(final Throwable exceptionToSuppress, final Throwable mainException)
	{
		if (mainException == null)
		{
			throw wrapIfNeeded(exceptionToSuppress);
		}
		else
		{
			mainException.addSuppressed(exceptionToSuppress);
		}
	}

	private boolean parseTranslation = true;
	private String _messageBuilt = null;

	private Integer adIssueId = null;

	/**
	 * Default Constructor (saved logger error will be used as message)
	 */
	public AdempiereException()
	{
		this(getMessageFromLogger());
	}

	/**
	 * @param message
	 */
	public AdempiereException(final String message)
	{
		super(message);
	}

	public AdempiereException(final String language, final String adMessage, final Object[] params)
	{
		super(Services.get(IMsgBL.class).getMsg(language, adMessage, params));
	}

	public AdempiereException(final Properties ctx, final String adMessage, final Object[] params)
	{
		this(Env.getAD_Language(ctx), adMessage, params);
	}

	public AdempiereException(final String adMessage, final Object[] params)
	{
		this(Env.getCtx(), adMessage, params);
	}

	/**
	 * @param cause
	 */
	public AdempiereException(final Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AdempiereException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	@Override
	public final String getLocalizedMessage()
	{
		String msg = super.getLocalizedMessage();

		if (parseTranslation)
		{
			if (Language.isBaseLanguageSet())
			{
				try
				{
					msg = Services.get(IMsgBL.class).parseTranslation(getCtx(), msg);
				}
				catch (Throwable e)
				{
					// don't fail while building the actual exception
					addSuppressed(e);
					// e.printStackTrace();
				}
			}
		}

		return msg;
	}

	@Override
	public final String getMessage()
	{
		if (_messageBuilt == null)
		{
			_messageBuilt = buildMessage();
		}
		return _messageBuilt;
	}

	/**
	 * Gets original message
	 *
	 * @return original message
	 */
	protected final String getOriginalMessage()
	{
		return super.getMessage();
	}

	/**
	 * Build error message (if needed) and return it.
	 *
	 * By default this method is returning initial message, but extending classes could override it.
	 *
	 * WARNING: to avoid recursion, please never ever call {@link #getMessage()} or {@link #getLocalizedMessage()} but
	 * <ul>
	 * <li>call {@link #getOriginalMessage()}
	 * <li>or store the error message in a separate field and use it</li>
	 *
	 * @return built detail message
	 */
	protected String buildMessage()
	{
		return super.getMessage();
	}

	/**
	 * Reset the build message. Next time when the message is needed, it will be re-builded first ({@link #buildMessage()}).
	 *
	 * Call this method from each setter which would change your message.
	 */
	protected final void resetMessageBuilt()
	{
		this._messageBuilt = null;
	}

	protected Properties getCtx()
	{
		return Env.getCtx();
	}

	/**
	 * @return error message from logger
	 * @see MetasfreshLastError#retrieveError()
	 */
	private static String getMessageFromLogger()
	{
		//
		// Check last error
		final org.compiere.util.ValueNamePair err = MetasfreshLastError.retrieveError();
		String msg = null;
		if (err != null)
		{
			msg = err.getName();
		}

		//
		// Check last exception
		if (msg == null)
		{
			final Throwable ex = MetasfreshLastError.retrieveException();
			if (ex != null)
			{
				msg = ex.getLocalizedMessage();
			}
		}

		//
		// Fallback: no last error found => use Unknown error message
		if (msg == null)
		{
			msg = "UnknownError";
		}

		return msg;
	}

	/**
	 * Convenient method to throw this exception or just log it as {@link Level#ERROR}.
	 *
	 * @param throwIt <code>true</code> if the exception shall be thrown
	 * @param logger
	 * @return always returns <code>false</code>.
	 */
	public final boolean throwOrLogSevere(final boolean throwIt, final Logger logger)
	{
		return throwOrLog(throwIt, Level.ERROR, logger);
	}

	/**
	 * Convenient method to throw this exception or just log it as {@link Level#WARN}.
	 *
	 * @param throwIt <code>true</code> if the exception shall be thrown
	 * @param logger
	 * @return always returns <code>false</code>.
	 */
	public final boolean throwOrLogWarning(final boolean throwIt, final Logger logger)
	{
		return throwOrLog(throwIt, Level.WARN, logger);
	}

	/**
	 * Convenient method to throw this exception if developer mode is enabled or just log it as {@link Level#WARNING}.
	 *
	 * @param logger
	 * @return always returns <code>false</code>.
	 */
	public final boolean throwIfDeveloperModeOrLogWarningElse(final Logger logger)
	{
		final boolean throwIt = Services.get(IDeveloperModeBL.class).isEnabled();
		return throwOrLog(throwIt, Level.WARN, logger);
	}

	private final boolean throwOrLog(final boolean throwIt, final Level logLevel, final Logger logger)
	{
		if (throwIt)
		{
			throw this;
		}
		else if (logger != null)
		{
			LoggingHelper.log(logger, logLevel, "this is logged, no Exception thrown (throwIt=false, logger!=null):", this);
			LoggingHelper.log(logger, logLevel, getLocalizedMessage(), this);
			return false;
		}
		else
		{
			System.err.println(this.getClass().getSimpleName() + "throwOrLog: this is written to std-err, no Exception thrown (throwIt=false, logger=null):");
			this.printStackTrace();
			return false;
		}
	}

	/**
	 * If developer mode is active, it logs a warning with given exception.
	 *
	 * If the developer mode is not active, this method does nothing
	 *
	 * @param logger
	 * @param exceptionSupplier {@link AdempiereException} supplier
	 */
	public static final void logWarningIfDeveloperMode(final Logger logger, Supplier<? extends AdempiereException> exceptionSupplier)
	{
		if (!Services.get(IDeveloperModeBL.class).isEnabled())
		{
			return;
		}

		final boolean throwIt = false;
		final AdempiereException exception = exceptionSupplier.get();
		exception.throwOrLog(throwIt, Level.WARN, logger);
	}

	/**
	 * Sets if {@link #getLocalizedMessage()} shall parse the translations.
	 *
	 * @param parseTranslation
	 */
	protected final void setParseTranslation(final boolean parseTranslation)
	{
		this.parseTranslation = parseTranslation;
	}

	@Override
	public final void markIssueReported(final int adIssueId)
	{
		this.adIssueId = (adIssueId <= 0 ? 0 : adIssueId);
	}

	@Override
	public final boolean isIssueReported()
	{
		// NOTE: we consider it as issue reported even if the AD_Issue_ID <= 0
		return adIssueId != null;
	}
}
