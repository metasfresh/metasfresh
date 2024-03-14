package org.adempiere.exceptions;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import de.metas.error.AdIssueId;
import de.metas.error.IssueCategory;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.logging.LoggingHelper;
import org.compiere.model.Null;
import org.compiere.util.Env;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

/**
 * Any exception that occurs inside the Adempiere core
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class AdempiereException extends RuntimeException
		implements IIssueReportableAware
{
	public static final AdMessageKey MSG_NoLines = AdMessageKey.of("NoLines");

	/**
	 * Wraps given <code>throwable</code> as {@link AdempiereException}, if it's not already an {@link AdempiereException}.<br>
	 * Note that this method also tries to pick the most specific adempiere exception (work in progress).
	 *
	 * @return {@link AdempiereException} or <code>null</code> if the throwable was null.
	 */
	@Nullable
	@Contract("!null -> !null")
	public static AdempiereException wrapIfNeeded(@Nullable final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}
		if (throwable instanceof AdempiereException)
		{
			return (AdempiereException)throwable;
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
		return new AdempiereException(throwable.getClass().getSimpleName() + ": " + extractMessage(cause), cause);
	}

	/**
	 * Extracts throwable message.
	 *
	 * @return message; never return null
	 */
	public static String extractMessage(@Nullable final Throwable throwable)
	{
		// guard against NPE, shall not happen
		if (throwable == null)
		{
			return "null";
		}

		if (throwable instanceof NullPointerException)
		{
			return throwable.toString();
		}
		else
		{
			String message = throwable.getLocalizedMessage();

			// If throwable message is null or it's very short then it's better to use throwable.toString()
			if (message == null || message.length() < 4)
			{
				message = throwable.toString();
			}

			return message;
		}
	}

	public static ITranslatableString extractMessageTrl(final Throwable throwable)
	{
		if (throwable instanceof AdempiereException)
		{
			final AdempiereException ex = (AdempiereException)throwable;
			return ex.getMessageBuilt();
		}

		return TranslatableStrings.constant(extractMessage(throwable));
	}

	public static Map<String, Object> extractParameters(final Throwable throwable)
	{
		if (throwable instanceof AdempiereException)
		{
			return ((AdempiereException)throwable).getParameters();
		}
		else
		{
			return ImmutableMap.of();
		}
	}

	/**
	 * Extract cause exception from those exceptions which are only about wrapping the real cause (e.g. ExecutionException, InvocationTargetException).
	 *
	 * @return cause or throwable; never returns null
	 */
	public static Throwable extractCause(final Throwable throwable)
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

		if (cause instanceof NullPointerException)
		{
			return cause;
		}
		if (cause instanceof IllegalArgumentException)
		{
			return cause;
		}
		if (cause instanceof IllegalStateException)
		{
			return cause;
		}

		if (throwable instanceof InvocationTargetException)
		{
			return cause;
		}

		if (throwable instanceof CalloutExecutionException)
		{
			return cause;
		}

		return throwable;
	}

	/**
	 * Convenient method to suppress a given exception if there is an already main exception which is currently thrown.
	 *
	 * @throws AdempiereException if mainException was null. It will actually be the exceptionToSuppress, wrapped to AdempiereException if it was needed.
	 */
	public static void suppressOrThrow(final Throwable exceptionToSuppress, final Throwable mainException)
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
	 * <p>
	 * If is NOT enabled, the language used to translate the error message is acquired when the message is translated.
	 */
	public static void enableCaptureLanguageOnConstructionTime()
	{
		AdempiereException.captureLanguageOnConstructionTime = true;
	}

	/**
	 * Tells if a throwable passsing thourgh trx-manager shall be logged there or not.
	 * We currently have one exception where whe know that it needs not to be logged and can clutter the whole output when it is logged.
	 */
	public static boolean isThrowableLoggedInTrxManager(@NonNull final Throwable t)
	{
		if (t instanceof AdempiereException)
		{
			return ((AdempiereException)t).isLoggedInTrxManager();
		}
		return true;
	}

	@VisibleForTesting
	static final String PARAMETER_RecordRef = "recordRef";
	@VisibleForTesting
	static final String PARAMETER_IssueCategory = "issueCategory";

	private static boolean captureLanguageOnConstructionTime = false;

	/**
	 * In future this might become a "real" aphanumerical error-code.
	 * But right now, I'm actually starting it so that we can verify in a language-independent way whether particular exceptions were thrown.
	 */
	@Getter
	private final String errorCode;

	private final ITranslatableString messageTrl;
	/**
	 * Build message but not translated
	 */
	@Nullable
	private ITranslatableString _messageBuilt = null;
	private final String adLanguage;

	private AdIssueId adIssueId = null;
	private boolean userNotified = false;
	private boolean userValidationError;

	private Map<String, Object> parameters = null;
	private final Map<String, String> mdcContextMap;

	private boolean appendParametersToMessage = false;

	public AdempiereException(final String message)
	{
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
		this.messageTrl = TranslatableStrings.parse(message);
		this.userValidationError = TranslatableStrings.isPossibleTranslatableString(message);
		this.mdcContextMap = captureMDCContextMap();
		this.errorCode = null;
	}

	public AdempiereException(@NonNull final ITranslatableString message)
	{
		// when this constructor is called, usually we have nice error messages,
		// so we can consider those user-friendly errors
		this(message, true, null);
	}

	protected AdempiereException(@NonNull final ITranslatableString message, final boolean userValidationError)
	{
		this(message, userValidationError, null);
	}

	private AdempiereException(
			@NonNull final ITranslatableString message,
			final boolean userValidationError,
			@Nullable final String errorCode)
	{
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
		this.messageTrl = message;
		this.userValidationError = userValidationError;
		this.mdcContextMap = captureMDCContextMap();
		this.errorCode = errorCode;
	}

	public AdempiereException(@NonNull final AdMessageKey messageKey)
	{
		this(TranslatableStrings.adMessage(messageKey), true, messageKey.toAD_Message());
	}

	public AdempiereException(final String adLanguage, @NonNull final AdMessageKey adMessage, final Object... params)
	{
		this.messageTrl = TranslatableStrings.adMessage(adMessage, params);
		this.adLanguage = captureLanguageOnConstructionTime ? adLanguage : null;
		this.userValidationError = true;
		this.mdcContextMap = captureMDCContextMap();

		setParameter("AD_Language", this.adLanguage);
		setParameter("AD_Message", adMessage);

		this.errorCode = adMessage.toAD_Message();
	}

	public AdempiereException(final AdMessageKey adMessage, final Object... params)
	{
		this(Env.getAD_Language(), adMessage, params);
	}

	public AdempiereException(@Nullable final Throwable cause)
	{
		super(cause);
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
		this.messageTrl = TranslatableStrings.empty();
		this.userValidationError = false;
		this.mdcContextMap = captureMDCContextMap();

		this.errorCode = null;
	}

	public AdempiereException(final String plainMessage, @Nullable final Throwable cause)
	{
		super(cause);
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
		this.messageTrl = TranslatableStrings.constant(plainMessage);
		this.userValidationError = false;
		this.mdcContextMap = captureMDCContextMap();

		this.errorCode = null;
	}

	public AdempiereException(@NonNull final ITranslatableString message, @Nullable final Throwable cause)
	{
		super(cause);
		this.adLanguage = captureLanguageOnConstructionTime ? Env.getAD_Language() : null;
		this.messageTrl = message;
		this.userValidationError = true;
		this.mdcContextMap = captureMDCContextMap();

		this.errorCode = null;
	}

	public static AdempiereException noLines() {return new AdempiereException(MSG_NoLines);}

	public static AdempiereException newWithTranslatableMessage(@Nullable final String translatableMessage) {return new AdempiereException(TranslatableStrings.parse(translatableMessage));}

	public static AdempiereException newWithPlainMessage(@Nullable final String plainMessage) {return new AdempiereException(TranslatableStrings.constant(plainMessage), false);}

	private static Map<String, String> captureMDCContextMap()
	{
		final Map<String, String> map = MDC.getCopyOfContextMap();
		return map != null && !map.isEmpty()
				? map
				: ImmutableMap.of();
	}

	protected final ITranslatableString getOriginalMessage()
	{
		return messageTrl;
	}

	@Override
	public final String getLocalizedMessage()
	{
		final ITranslatableString message = getMessageBuilt();

		if (!Language.isBaseLanguageSet())
		{
			return message.getDefaultValue();
		}

		try
		{
			final String adLanguage = getADLanguage();
			return message.translate(adLanguage);
		}
		catch (final Throwable ex)
		{
			// don't fail while building the actual exception
			ex.printStackTrace();
			return message.getDefaultValue();
		}
	}

	@Override
	public final String getMessage()
	{
		// always return the localized string,
		// else those APIs which are using getMessage() will fetch the not so nice text message.
		return getLocalizedMessage();
	}

	/**
	 * Reset the build message. Next time when the message is needed, it will be re-builded first ({@link #buildMessage()}).
	 * <p>
	 * Call this method from each setter which would change your message.
	 */
	protected final void resetMessageBuilt()
	{
		this._messageBuilt = null;
	}

	private ITranslatableString getMessageBuilt()
	{
		ITranslatableString messageBuilt = _messageBuilt;
		if (messageBuilt == null)
		{
			_messageBuilt = messageBuilt = buildMessage();
		}
		return messageBuilt;
	}

	/**
	 * Build error message (if needed) and return it.
	 * <p>
	 * By default this method is returning initial message, but extending classes could override it.
	 * <p>
	 * WARNING: to avoid recursion, please never ever call {@link #getMessage()} or {@link #getLocalizedMessage()} but
	 * <ul>
	 * <li>call {@link #getOriginalMessage()}
	 * <li>or store the error message in a separate field and use it</li>
	 *
	 * @return built detail message
	 */
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();
		message.append(getOriginalMessage());
		if (appendParametersToMessage)
		{
			appendParameters(message);
			final Throwable cause = getCause();
			if (cause instanceof AdempiereException)
			{
				final AdempiereException metasfreshCause = (AdempiereException)cause;
				if (metasfreshCause.appendParametersToMessage) // also append the cause's parameters
				{
					metasfreshCause.appendParameters(message);
				}
			}
		}
		return message.build();
	}

	protected final String getADLanguage()
	{
		return coalesceSuppliers(() -> adLanguage, Env::getAD_Language);
	}

	/**
	 * Convenient method to throw this exception or just log it as {@link Level#ERROR}.
	 *
	 * @param throwIt <code>true</code> if the exception shall be thrown
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
	 */
	public final void throwOrLogWarning(final boolean throwIt, final Logger logger)
	{
		throwOrLog(throwIt, Level.WARN, logger);
	}

	/**
	 * Convenient method to throw this exception if developer mode is enabled or just log it as {@link Level#WARN}.
	 *
	 * @return always returns <code>false</code>.
	 */
	public final boolean throwIfDeveloperModeOrLogWarningElse(final Logger logger)
	{
		final boolean throwIt = Services.get(IDeveloperModeBL.class).isEnabled();
		return throwOrLog(throwIt, Level.WARN, logger);
	}

	private boolean throwOrLog(final boolean throwIt, final Level logLevel, final Logger logger)
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
	 * <p>
	 * If the developer mode is not active, this method does nothing
	 *
	 * @param exceptionSupplier {@link AdempiereException} supplier
	 */
	public static void logWarningIfDeveloperMode(final Logger logger, final Supplier<? extends AdempiereException> exceptionSupplier)
	{
		if (!Services.get(IDeveloperModeBL.class).isEnabled())
		{
			return;
		}

		final boolean throwIt = false;
		final AdempiereException exception = exceptionSupplier.get();
		exception.throwOrLog(throwIt, Level.WARN, logger);
	}

	@Override
	public final void markIssueReported(final AdIssueId adIssueId)
	{
		this.adIssueId = adIssueId;
	}

	@Override
	public final boolean isIssueReported()
	{
		return adIssueId != null;
	}

	@Override
	public AdIssueId getAdIssueId()
	{
		return adIssueId;
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

	@OverridingMethodsMustInvokeSuper
	public AdempiereException setRecord(@NonNull final TableRecordReference recordRef)
	{
		return setParameter(PARAMETER_RecordRef, recordRef);
	}

	@Nullable
	public final TableRecordReference getRecord()
	{
		final Object recordRefObj = getParameter(PARAMETER_RecordRef);
		return recordRefObj instanceof TableRecordReference
				? (TableRecordReference)recordRefObj
				: null;
	}

	public void setIssueCategory(@NonNull final IssueCategory issueCategory)
	{
		setParameter(PARAMETER_IssueCategory, issueCategory);
	}

	@NonNull
	public IssueCategory getIssueCategory()
	{
		final Object issueCategoryObj = getParameter(PARAMETER_IssueCategory);
		return issueCategoryObj instanceof IssueCategory
				? (IssueCategory)issueCategoryObj
				: IssueCategory.OTHER;
	}

	/**
	 * Sets parameter.
	 *
	 * @param name  parameter name
	 * @param value parameter value; {@code null} is added as {@link Null}
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

	@OverridingMethodsMustInvokeSuper
	public <T extends Enum<?>> AdempiereException setParameter(@NonNull final T enumValue)
	{
		final String parameterName = buildParameterName(enumValue);
		return setParameter(parameterName, enumValue);
	}

	public AdempiereException setParameters(@Nullable final Map<String, ?> params)
	{
		if (params != null && !params.isEmpty())
		{
			params.forEach(this::setParameter);
		}
		return this;
	}

	@OverridingMethodsMustInvokeSuper
	public <T extends Enum<?>> boolean hasParameter(@NonNull final T enumValue)
	{
		final String parameterName = buildParameterName(enumValue);
		return enumValue.equals(getParameter(parameterName));
	}

	private static <T extends Enum<?>> String buildParameterName(@NonNull final T enumValue)
	{
		return enumValue.getClass().getSimpleName();
	}

	public final boolean hasParameter(@NonNull final String name)
	{
		return parameters != null && parameters.containsKey(name);
	}

	@Nullable
	public final Object getParameter(@NonNull final String name)
	{
		return parameters != null ? Null.unbox(parameters.get(name)) : null;
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
	@NonNull
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
	 * <p>
	 * Note: as of now, this method is final by intention; if you need the returned string to be customized, I suggest to not override this method somewhere,
	 * but instead add another method that can take a format string as parameter.
	 *
	 * @return an empty string if this instance has no parameters or otherwise something like
	 *
	 * <pre>
	 * Additional parameters:
	 * name1: value1
	 * name2: value2
	 *         </pre>
	 */
	protected final ITranslatableString buildParametersString()
	{
		final Map<String, Object> parameters = getParameters();
		if (parameters.isEmpty())
		{
			return TranslatableStrings.empty();
		}

		final TranslatableStringBuilder message = TranslatableStrings.builder();
		message.append("Additional parameters:");
		for (final Map.Entry<String, Object> paramName2Value : parameters.entrySet())
		{
			message.append("\n ").append(paramName2Value.getKey()).append(": ").appendObj(paramName2Value.getValue());
		}

		return message.build();
	}

	/**
	 * Utility method to convert parameters to string and append them to given <code>message</code>
	 *
	 * @see #buildParametersString()
	 */
	protected final void appendParameters(final TranslatableStringBuilder message)
	{
		final ITranslatableString parametersStr = buildParametersString();
		if (TranslatableStrings.isBlank(parametersStr))
		{
			return;
		}

		if (!message.isEmpty())
		{
			message.append("\n");
		}
		message.append(parametersStr);
	}

	public String getMDC(@NonNull final String name)
	{
		return mdcContextMap.get(name);
	}

	/**
	 * Marks this exception as user validation error.
	 * In case an exception is a user validation error, the framework assumes the message is user friendly and can be displayed directly.
	 * More, the webui auto-saving will not hide/ignore this error put it will propagate it directly to user.
	 */
	@OverridingMethodsMustInvokeSuper
	public AdempiereException markAsUserValidationError()
	{
		userValidationError = true;
		return this;
	}

	public final boolean isUserValidationError()
	{
		return userValidationError;
	}

	public static boolean isUserValidationError(final Throwable ex)
	{
		return (ex instanceof AdempiereException) && ((AdempiereException)ex).isUserValidationError();
	}

	/**
	 * Fluent version of {@link #addSuppressed(Throwable)}
	 */
	public AdempiereException suppressing(@NonNull final Throwable exception)
	{
		addSuppressed(exception);
		return this;
	}

	/**
	 * Override with a method returning false if your exception is more of a signal than an error
	 * and shall not clutter the log when it is caught and rethrown by the transaction manager.
	 * <p/>
	 * To be invoked by {@link AdempiereException#isThrowableLoggedInTrxManager(Throwable)}.
	 */
	protected boolean isLoggedInTrxManager()
	{
		return true;
	}
}
