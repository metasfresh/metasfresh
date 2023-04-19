/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.monitoring.aspect;

import de.metas.monitoring.adapter.MicrometerPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.annotation.Monitor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;

@Aspect
@Component
public class MonitorAspect
{
	private final MicrometerPerformanceMonitoringService service;
	private static final String PERF_MON_SYSCONFIG_NAME = "de.metas.monitoring.annotation.enable";
	private static final boolean SYS_CONFIG_DEFAULT_VALUE = false;
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);


	public MonitorAspect(@NonNull final MicrometerPerformanceMonitoringService service)
	{
		this.service = service;
	}

	@Around("execution(* *(..)) && @annotation(de.metas.monitoring.annotation.Monitor)")
	public Object monitorMethod(ProceedingJoinPoint pjp) throws Throwable
	{
		final boolean perfMonIsActive = sysConfigBL.getBooleanValue(PERF_MON_SYSCONFIG_NAME, SYS_CONFIG_DEFAULT_VALUE);
		if(!perfMonIsActive)
		{
			return pjp.proceed();
		}

		final Callable<?> callable = wrapAsCallable(pjp);
		final PerformanceMonitoringService.Metadata metadata;

		final Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		final Monitor monitorAnnotation = method.getAnnotation(Monitor.class);

		if(monitorAnnotation.type() == PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
		{
			metadata = PerformanceMonitoringService.Metadata.builder()
					.className(pjp.getTarget().getClass().getSimpleName())
					.type(monitorAnnotation.type())
					.functionName(method.getName())
					.windowNameAndId(getWindowNameAndId())
					.build();
		}
		else
		{
			metadata = PerformanceMonitoringService.Metadata.builder()
					.className(pjp.getTarget().getClass().getSimpleName())
					.type(monitorAnnotation.type())
					.functionName(method.getName())
					.build();
		}

		return service.monitor(callable, metadata);

	}

	private Callable<Object> wrapAsCallable(final ProceedingJoinPoint pjp)
	{
		final Callable<Object> callable = new Callable<Object>()
		{

			@Override
			public Object call() throws Exception
			{
				try
				{
					return pjp.proceed();
				}
				catch (final Exception e)
				{
					throw e;
				}
				catch (final Throwable t)
				{
					throw AdempiereException.wrapIfNeeded(t);
				}
			}

			@Override
			public String toString()
			{
				return pjp.getTarget().toString();
			}
		};

		return callable;
	}

	private String getWindowNameAndId(){
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		final Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		final String windowId = (String)pathVariables.get("windowId");
		String windowName;
		try
		{
			windowName = (adWindowDAO.retrieveWindowName(AdWindowId.ofRepoId(Integer.parseInt(windowId)))).getDefaultValue();
		}
		catch (final NumberFormatException nfe)
		{
			windowName = "unknown";
		}
		return windowName + " (" + windowId + ")";
	}
}
