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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.logging.LoggingHelper;
import org.compiere.model.Null;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import ch.qos.logback.classic.Level;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.logging.MetasfreshLastError;
import lombok.NonNull;

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

	/**
	 * If enabled, the language used to translate the error message is captured when the exception is constructed.
	 *
	 * If is NOT enabled, the language used to translate the error message is acquired when the message is translated.
	 */
	public static final void enableCaptureLanguageOnConstructionTime()
	{
		AdempiereException.captureLanguageOnConstructionTime = true;
	}

	private static boolean captureLanguageOnConstructionTime = false;

	private boolean parseTranslation = true;
	/** Build message but not translated */
	private String _messageBuilt = null;
	private final String adLanguage;
	private ValueNamePair _translatedLocalizedMessage = null;

	private Integer adIssueId = null;
	private boolean userNotified = false;

	private Map<String, Object> parameters = null;
	private boolean appendParametersToMessage = false;

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
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
	}

	public AdempiereException(final String adLanguage, final String adMessage, final Object[] params)
	{
		super(Services.get(IMsgBL.class).getMsg(adLanguage, adMessage, params));
		this.adLanguage = captureLanguageOnConstructionTime ? adLanguage : null;
		setParameter("AD_Language", adLanguage);
		setParameter("AD_Message", adMessage);
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
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AdempiereException(final String message, final Throwable cause)
	{
		super(message, cause);
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
	}

	private final String getOriginalLocalizedMessage()
	{
		return super.getLocalizedMessage();
	}

	@Override
	public final String getLocalizedMessage()
	{
		if (!parseTranslation)
		{
			return getOriginalLocalizedMessage();
		}

		if (!Language.isBaseLanguageSet())
		{
			return getOriginalLocalizedMessage();
		}

		try
		{
			final String adLanguage = getADLanguage();
			return translateOriginalLocalizedMessage(adLanguage);
		}
		catch (final Throwable ex)
		{
			// don't fail while building the actual exception
			ex.printStackTrace();
			return getOriginalLocalizedMessage();
		}
	}

	// NOTE: i think we don't have to synchronize this method because usually is called by same thread
	// NOTE: it's OK to cache by last used language because in 99% of the cases only one language is used.
	private String translateOriginalLocalizedMessage(final String adLanguage)
	{
		ValueNamePair translatedLocalizedMessage = this._translatedLocalizedMessage;
		if (translatedLocalizedMessage == null || !translatedLocalizedMessage.getValue().equals(adLanguage))
		{
			final String msg = getOriginalLocalizedMessage();
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			final String msgTrl = msgBL.parseTranslation(adLanguage, msg);
			this._translatedLocalizedMessage = translatedLocalizedMessage = ValueNamePair.of(adLanguage, msgTrl);
		}
		return translatedLocalizedMessage.getName();
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
		final StringBuilder message = new StringBuilder();
		message.append(getOriginalMessage());
		if (appendParametersToMessage)
		{
			appendParameters(message);
		}
		return message.toString();
	}

	/**
	 * Reset the build message. Next time when the message is needed, it will be re-builded first ({@link #buildMessage()}).
	 *
	 * Call this method from each setter which would change your message.
	 */
	protected final void resetMessageBuilt()
	{
		this._messageBuilt = null;
		this._translatedLocalizedMessage = null;
	}

	protected final String getADLanguage()
	{
		return adLanguage != null ? adLanguage : Env.getAD_Language();
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

	public final boolean isUserNotified()
	{
		return userNotified;
	}

	public final AdempiereException markUserNotified()
	{
		userNotified = true;
		return this;
	}

	/**
	 * Sets parameter.
	 *
	 * @param name parameter name
	 * @param value parameter value or <code>null</code> if you want the parameter to be removed.
	 */
	@OverridingMethodsMustInvokeSuper
	public AdempiereException setParameter(@NonNull final String name, @Nullable final Object value)
	{
		if (parameters == null)
		{
			parameters = new LinkedHashMap<>();
		}

		parameters.put(name, Null.box(value));
		resetMessageBuilt();

		return this;
	}

	public final boolean hasParameter(@NonNull final String name)
	{
		return parameters == null ? false : parameters.containsKey(name);
	}

	public final Map<String, Object> getParameters()
	{
		if (parameters == null)
		{
			return ImmutableMap.of();
		}
		return ImmutableMap.copyOf(parameters);
	}

	/**
	 * Ask the exception to also include the parameters in it's message.
	 */
	@OverridingMethodsMustInvokeSuper
	public AdempiereException appendParametersToMessage()
	{
		if (!appendParametersToMessage)
		{
			appendParametersToMessage = true;
			resetMessageBuilt();
		}
		return this;
	}

	/**
	 * Utility method that can be used by both external callers and subclasses'
	 * {@link AdempiereException#buildMessage()} or
	 * {@link #getMessage()} methods to create a string from this instance's parameters.
	 *
	 * Note: as of now, this method is final by intention; if you need the returned string to be customized, I suggest to not override this method somewhere,
	 * but instead add another method that can take a format string as parameter.
	 *
	 * @return an empty sting if this instance has no parameters or otherwise something like
	 *
	 *         <pre>
	 * Additional parameters:
	 * name1: value1
	 * name2: value2
	 *         </pre>
	 */
	protected final String buildParametersString()
	{
		final Map<String, Object> parameters = getParameters();
		if (parameters.isEmpty())
		{
			return "";
		}

		final StringBuilder message = new StringBuilder();
		message.append("Additional parameters:");
		for (final Map.Entry<String, Object> paramName2Value : parameters.entrySet())
		{
			message.append("\n ").append(paramName2Value.getKey()).append(": ").append(paramName2Value.getValue());
		}

		return message.toString();
	}

	/**
	 * Utility method to convert parameters to string and append them to given <code>message</code>
	 *
	 * @see #buildParametersString()
	 */
	protected final void appendParameters(final StringBuilder message)
	{
		final String parametersStr = buildParametersString();
		if (Check.isEmpty(parametersStr, true))
		{
			return;
		}

		if (message.length() > 0)
		{
			message.append("\n");
		}
		message.append(parametersStr);
	}
}
