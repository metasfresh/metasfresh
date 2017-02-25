package de.metas.ui.web.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.login.exceptions.NotLoggedInException;

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
 * #L%
 */

/**
 * Handles all REST API exceptions
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on {@link org.springframework.boot.autoconfigure.web.DefaultErrorAttributes}
 */
@Component
// Order: IMPORTANT: because we want to call this handler before any other. Else, if it's the last one added, it might be that it will be never called
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebuiExceptionHandler implements ErrorAttributes, HandlerExceptionResolver
{

	private static final transient Logger logger = LogManager.getLogger(WebuiExceptionHandler.class);

	private static final String REQUEST_ATTR_EXCEPTION = WebuiExceptionHandler.class.getName() + ".ERROR";

	private static final String ATTR_Timestamp = "timestamp";
	private static final String ATTR_Status = "status";
	private static final String ATTR_Error = "error";
	private static final String ATTR_Exception = "exception";
	private static final String ATTR_Message = "message";
	private static final String ATTR_Stacktrace = "trace";
	private static final String ATTR_Path = "path";

	@Value("${de.metas.ui.web.config.WebuiExceptionHandler.logExceptions:true}")
	private boolean logExceptions;

	private final Set<Class<?>> EXCEPTIONS_ExcludeFromLogging = ImmutableSet.of(NotLoggedInException.class);

	private final Map<Class<?>, HttpStatus> EXCEPTION_HTTPSTATUS = ImmutableMap.<Class<?>, HttpStatus> builder()
			.build();

	@Override
	public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex)
	{
		logExceptionIfNeeded(ex, handler);
		request.setAttribute(REQUEST_ATTR_EXCEPTION, ex);
		return null;
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

	private final boolean isExcludeFromLogging(final Throwable ex)
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
	public Map<String, Object> getErrorAttributes(final RequestAttributes requestAttributes, final boolean includeStackTrace)
	{
		final Map<String, Object> errorAttributes = new LinkedHashMap<>();
		errorAttributes.put(ATTR_Timestamp, new Date());
		addStatus(errorAttributes, requestAttributes);
		addErrorDetails(errorAttributes, requestAttributes, includeStackTrace);
		addPath(errorAttributes, requestAttributes);
		return errorAttributes;
	}

	private void addStatus(final Map<String, Object> errorAttributes, final RequestAttributes requestAttributes)
	{
		Integer status = null;

		//
		// Extract HTTP status from EXCEPTION_HTTPSTATUS map
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

		if (status == null)
		{
			errorAttributes.put(ATTR_Status, 999);
			errorAttributes.put(ATTR_Error, "None");
			return;
		}
		errorAttributes.put(ATTR_Status, status);
		try
		{
			errorAttributes.put(ATTR_Error, HttpStatus.valueOf(status).getReasonPhrase());
		}
		catch (final Exception ex)
		{
			// Unable to obtain a reason
			errorAttributes.put(ATTR_Error, "Http Status " + status);
		}
	}
	
	private final boolean isErrorMatching(final Class<?> baseClass, final Class<?> clazz)
	{
		return baseClass.isAssignableFrom(clazz);
	}

	private void addErrorDetails(final Map<String, Object> errorAttributes, final RequestAttributes requestAttributes, final boolean includeStackTrace)
	{
		Throwable error = getError(requestAttributes);
		if (error != null)
		{
			while (error instanceof ServletException && error.getCause() != null)
			{
				error = ((ServletException)error).getCause();
			}
			errorAttributes.put(ATTR_Exception, error.getClass().getName());
			addErrorMessage(errorAttributes, error);
			if (includeStackTrace && !isExcludeFromLogging(error))
			{
				addStackTrace(errorAttributes, error);
			}
		}

		final Object message = getAttribute(requestAttributes, RequestDispatcher.ERROR_MESSAGE);
		if ((!StringUtils.isEmpty(message) || errorAttributes.get(ATTR_Message) == null)
				&& !(error instanceof BindingResult))
		{
			errorAttributes.put(ATTR_Message, StringUtils.isEmpty(message) ? "No message available" : message);
		}
	}

	private void addErrorMessage(final Map<String, Object> errorAttributes, final Throwable error)
	{
		errorAttributes.put(ATTR_Message, error.getLocalizedMessage());
	}

	private void addStackTrace(final Map<String, Object> errorAttributes, final Throwable error)
	{
		final StringWriter stackTrace = new StringWriter();
		error.printStackTrace(new PrintWriter(stackTrace));
		stackTrace.flush();
		errorAttributes.put(ATTR_Stacktrace, stackTrace.toString());
	}

	private void addPath(final Map<String, Object> errorAttributes, final RequestAttributes requestAttributes)
	{
		final String path = getAttribute(requestAttributes, RequestDispatcher.ERROR_REQUEST_URI);
		if (path != null)
		{
			errorAttributes.put(ATTR_Path, path);
		}
	}

	@Override
	public Throwable getError(final RequestAttributes requestAttributes)
	{
		Throwable exception = getAttribute(requestAttributes, REQUEST_ATTR_EXCEPTION);
		if (exception == null)
		{
			exception = getAttribute(requestAttributes, RequestDispatcher.ERROR_EXCEPTION);
		}
		return exception;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getAttribute(final RequestAttributes requestAttributes, final String name)
	{
		return (T)requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
