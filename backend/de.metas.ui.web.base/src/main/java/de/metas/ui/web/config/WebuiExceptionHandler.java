package de.metas.ui.web.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.EmptyUtil;
import de.metas.logging.LogManager;
import de.metas.ui.web.login.exceptions.NotLoggedInException;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Trace;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * #L%ControllerAdvice
 */

/**
 * Handles all REST API exceptions
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
// Order: IMPORTANT: because we want to call this handler before any other. Else, if it's the last one added, it might be that it will be never called
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebuiExceptionHandler implements ErrorAttributes, HandlerExceptionResolver
{

	private static final Logger logger = LogManager.getLogger(WebuiExceptionHandler.class);

	private static final String REQUEST_ATTR_EXCEPTION = WebuiExceptionHandler.class.getName() + ".ERROR";

	private static final String ATTR_Timestamp = "timestamp";
	private static final String ATTR_Status = "status";
	private static final String ATTR_Error = "error";
	private static final String ATTR_Exception = "exception";
	private static final String ATTR_ExceptionAttributes = "exceptionAttributes";
	private static final String ATTR_Message = "message";
	private static final String ATTR_Stacktrace = "trace";
	private static final String ATTR_Path = "path";
	private static final String ATTR_UserFriendlyError = "userFriendlyError";

	@Value("${de.metas.ui.web.config.WebuiExceptionHandler.logExceptions:true}")
	private boolean logExceptions;

	private static final ImmutableSet<Class<?>> EXCEPTIONS_ExcludeFromLogging = ImmutableSet.of(NotLoggedInException.class);

	private static final ImmutableMap<Class<?>, HttpStatus> EXCEPTION_HTTPSTATUS = ImmutableMap.<Class<?>, HttpStatus>builder()
			.put(org.elasticsearch.client.transport.NoNodeAvailableException.class, HttpStatus.SERVICE_UNAVAILABLE)
			.build();

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.newInstance();
	}

	@java.lang.Override
	public ModelAndView resolveException(final HttpServletRequest request,
										 final HttpServletResponse response,
										 final java.lang.Object handler,
										 @NonNull final Exception ex)
	{
		logExceptionIfNeeded(ex, handler);

		final Throwable cause = AdempiereException.extractCause(ex);
		request.setAttribute(REQUEST_ATTR_EXCEPTION, cause);
		response.setHeader("Cache-Control", "no-cache");

		return null; // don't forward, go with default
	}

	private void logExceptionIfNeeded(final Exception ex, final Object handler)
	{
		if (!logExceptions)
		{
			return;
		}

		if (isExcludeFromLogging(ex))
		{
			logger.debug("Got REST (excluded from logging) exception from handler={}", handler, ex);
		}
		else
		{
			logger.warn("Got REST exception from handler={}", handler, ex);
		}
	}

	private static boolean isExcludeFromLogging(final Throwable ex)
	{
		for (final Class<?> exceptionClass : EXCEPTIONS_ExcludeFromLogging)
		{
			if (exceptionClass.isAssignableFrom(ex.getClass()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public Map<String, Object> getErrorAttributes(final WebRequest requestAttributes, final ErrorAttributeOptions errorAttributeOptions)
	{
		final Map<String, Object> errorAttributes = new LinkedHashMap<>();
		errorAttributes.put(ATTR_Timestamp, ZonedDateTime.now());
		addStatus(errorAttributes, requestAttributes);
		addErrorDetails(errorAttributes, requestAttributes, errorAttributeOptions);
		addPath(errorAttributes, requestAttributes);
		return errorAttributes;
	}

	private void addStatus(final Map<String, Object> errorAttributes, final WebRequest requestAttributes)
	{
		final Integer status = getHttpStatus(requestAttributes);
		if (status == null)
		{
			errorAttributes.put(ATTR_Status, 999);
			errorAttributes.put(ATTR_Error, "None");
		}
		else
		{
			errorAttributes.put(ATTR_Status, status);
			errorAttributes.put(ATTR_Error, getHttpStatusReasonPhase(status));
		}
	}

	@Nullable
	private Integer getHttpStatus(final WebRequest requestAttributes)
	{
		Integer status = null;

		final Throwable error = getError(requestAttributes);
		if (error != null)
		{
			final Class<? extends Throwable> errorClass = error.getClass();
			status = EXCEPTION_HTTPSTATUS
					.entrySet().stream()
					.filter(e -> isErrorMatching(e.getKey(), errorClass))
					.map(e -> e.getValue().value())
					.findFirst()
					.orElse(null);
		}

		//
		// Extract HTTP status from attributes
		if (status == null)
		{
			status = getAttribute(requestAttributes, RequestDispatcher.ERROR_STATUS_CODE);
		}

		return status;
	}

	private static String getHttpStatusReasonPhase(final int statusCode)
	{
		try
		{
			return HttpStatus.valueOf(statusCode).getReasonPhrase();
		}
		catch (final Exception ex)
		{
			// Unable to obtain a reason
			return "Http Status " + statusCode;
		}
	}

	private static boolean isErrorMatching(final Class<?> baseClass, final Class<?> clazz)
	{
		return baseClass.isAssignableFrom(clazz);
	}

	private void addErrorDetails(final Map<String, Object> errorAttributes, final WebRequest requestAttributes, final ErrorAttributeOptions errorAttributeOptions)
	{
		//
		// Get root exception and
		// Set exception related attributes.
		final Throwable rootError = getRootError(requestAttributes);
		if (rootError != null)
		{
			errorAttributes.put(ATTR_Exception, rootError.getClass().getName());

			errorAttributes.put(ATTR_Message, AdempiereException.extractMessage(rootError));
			errorAttributes.put(ATTR_UserFriendlyError, AdempiereException.isUserValidationError(rootError));

			if (errorAttributeOptions.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE) && !isExcludeFromLogging(rootError))
			{
				addStackTrace(errorAttributes, rootError);
			}
		}

		//
		// Set "message" attribute
		if (errorAttributes.get(ATTR_Message) == null && !(rootError instanceof BindingResult))
		{
			final Object message = getAttribute(requestAttributes, RequestDispatcher.ERROR_MESSAGE);
			if (message != null && !EmptyUtil.isEmpty(message))
			{
				errorAttributes.put(ATTR_Message, message);
				errorAttributes.put(ATTR_UserFriendlyError, false);
			}
		}

		//
		// Set "exceptionAttributes" attribute
		if (rootError instanceof AdempiereException)
		{
			final Map<String, Object> exceptionAttributes = ((AdempiereException)rootError).getParameters();
			if (exceptionAttributes != null && !exceptionAttributes.isEmpty())
			{
				final JSONOptions jsonOpts = newJSONOptions();
				final Map<String, Object> jsonExceptionAttributes = exceptionAttributes.entrySet()
						.stream()
						.map(entry -> GuavaCollectors.entry(entry.getKey(), Values.valueToJsonObject(entry.getValue(), jsonOpts, String::valueOf)))
						.collect(GuavaCollectors.toImmutableMap());

				errorAttributes.put(ATTR_ExceptionAttributes, jsonExceptionAttributes);
			}
		}
	}

	private static void addStackTrace(final Map<String, Object> errorAttributes, final Throwable error)
	{
		errorAttributes.put(ATTR_Stacktrace, Trace.toOneLineStackTraceString(error));
	}

	private static void addPath(final Map<String, Object> errorAttributes, final RequestAttributes requestAttributes)
	{
		final String path = getAttribute(requestAttributes, RequestDispatcher.ERROR_REQUEST_URI);
		if (path != null)
		{
			errorAttributes.put(ATTR_Path, path);
		}
	}

	@Override
	public java.lang.Throwable getError(final WebRequest webRequest)
	{
		Throwable exception = (Throwable)webRequest.getAttribute(REQUEST_ATTR_EXCEPTION, RequestAttributes.SCOPE_REQUEST);
		if (exception == null)
		{
			exception = (Throwable)webRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION, RequestAttributes.SCOPE_REQUEST);
		}

		if (exception != null)
		{
			exception = AdempiereException.extractCause(exception);
		}

		return exception;
	}

	@Nullable
	private Throwable getRootError(final WebRequest webRequest)
	{
		final Throwable error = getError(webRequest);
		return error != null ? unboxRootError(error) : null;
	}

	public Throwable unboxRootError(@NonNull final java.lang.Throwable error)
	{
		Throwable rootError = error;
		while (rootError instanceof ServletException)
		{
			final Throwable cause = AdempiereException.extractCause(rootError);
			if (cause == rootError)
			{
				return rootError;
			}
			else
			{
				rootError = cause;
			}
		}
		return rootError;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getAttribute(final RequestAttributes requestAttributes, final String name)
	{
		return (T)requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
