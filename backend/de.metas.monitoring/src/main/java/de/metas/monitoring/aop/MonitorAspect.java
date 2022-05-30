/*
 * #%L
 * de.metas.monitoring
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

package de.metas.monitoring.aop;

import de.metas.monitoring.adapter.MicrometerPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.annotation.Monitor;
import lombok.NonNull;
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
	public MonitorAspect(@NonNull final MicrometerPerformanceMonitoringService service)
	{
		this.service = service;
	}

	@Around("@annotation(de.metas.monitoring.annotation.Monitor)")
	public Object monitorMethod(ProceedingJoinPoint pjp) throws Throwable
	{
		final PerformanceMonitoringService.Metadata metadata;
		final Callable callable = getCallableFromProceedingJoinPoint( pjp );

		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		Monitor monitor = method.getAnnotation(Monitor.class);
		// if (monitor == null) {
		// 	method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
		// 	monitor = method.getAnnotation(Monitor.class);
		// }

		if(monitor.type() == PerformanceMonitoringService.Type.REST_CONTROLLER)
		{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			String windowIdStr = (String)pathVariables.get("windowId");

			metadata = PerformanceMonitoringService.Metadata.builder()
					.name(pjp.getTarget().getClass().getSimpleName())
					.type(monitor.type())
					.action(method.getName())
					.windowIdStr(windowIdStr)
					.build();
		}
		else
		{
			metadata = PerformanceMonitoringService.Metadata.builder()
					.name(pjp.getTarget().getClass().getSimpleName())
					.type(monitor.type())
					.action(method.getName())
					.build();
		}

		return service.monitor(callable, metadata);

	}

	private Callable<Object> getCallableFromProceedingJoinPoint(final ProceedingJoinPoint pjp)
	{
		Callable<Object> callable = new Callable<Object>()
		{

			@Override
			public Object call() throws Exception
			{
				try
				{
					return pjp.proceed();
				}
				catch (Exception e)
				{
					throw e;
				}
				catch (Throwable t)
				{
					throw new RuntimeException(t);
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


}
