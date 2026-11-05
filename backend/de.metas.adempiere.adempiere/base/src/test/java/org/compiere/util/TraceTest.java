package org.compiere.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TraceTest
{
	private static StackTraceElement frame(final String className, final String methodName, final int lineNumber)
	{
		return new StackTraceElement(className, methodName, className.substring(className.lastIndexOf('.') + 1) + ".java", lineNumber);
	}

	/**
	 * The Java 8 / Spring Boot 2 call chain of a REST controller: reflection via {@code sun.reflect.*}, servlet via {@code javax.servlet.*}.
	 */
	@Test
	void toOneLineStackTraceString_java8_reflectionAndJavaxServletFramesAreSkipped()
	{
		final StackTraceElement[] stacktrace = {
				frame("de.metas.rest_api.v2.order.sales.SalesOrderRestController", "revertOrder", 241),
				frame("sun.reflect.NativeMethodAccessorImpl", "invoke0", -2),
				frame("sun.reflect.DelegatingMethodAccessorImpl", "invoke", 43),
				frame("java.lang.reflect.Method", "invoke", 498),
				frame("org.springframework.web.servlet.FrameworkServlet", "service", 883),
				frame("javax.servlet.http.HttpServlet", "service", 764),
				frame("de.metas.util.web.WebConfig$ApiNoStoreCacheControlFilter", "doFilter", 80),
		};

		assertThat(Trace.toOneLineStackTraceString(stacktrace))
				.isEqualTo("SalesOrderRestController.revertOrder:241 <~~~ WebConfig$ApiNoStoreCacheControlFilter.doFilter:80");
	}

	/**
	 * The same call chain on JDK 18+ / Spring Boot 3: reflection goes through {@code jdk.internal.reflect.DirectMethodHandleAccessor}
	 * and the servlet API lives in {@code jakarta.servlet.*}. The shortened trace must be the same as on Java 8.
	 */
	@Test
	void toOneLineStackTraceString_jdk18_reflectionAndJakartaServletFramesAreSkipped()
	{
		final StackTraceElement[] stacktrace = {
				frame("de.metas.rest_api.v2.order.sales.SalesOrderRestController", "revertOrder", 241),
				frame("jdk.internal.reflect.DirectMethodHandleAccessor", "invoke", 104),
				frame("java.lang.reflect.Method", "invoke", 580),
				frame("org.springframework.web.servlet.FrameworkServlet", "service", 885),
				frame("jakarta.servlet.http.HttpServlet", "service", 593),
				frame("org.springframework.web.servlet.FrameworkServlet", "service", 885),
				frame("jakarta.servlet.http.HttpServlet", "service", 658),
				frame("org.apache.catalina.core.ApplicationFilterChain", "internalDoFilter", 195),
				frame("de.metas.util.web.WebConfig$ApiNoStoreCacheControlFilter", "doFilter", 80),
		};

		assertThat(Trace.toOneLineStackTraceString(stacktrace))
				.isEqualTo("SalesOrderRestController.revertOrder:241 <~~~ WebConfig$ApiNoStoreCacheControlFilter.doFilter:80");
	}
}
