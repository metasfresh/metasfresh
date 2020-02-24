package de.metas.monitoring.adapter;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2020 metas GmbH
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

public interface PerformanceMonitoringService
{
	/** invoke the given {@code callable} as a span. */
	<V> Result<V> monitorSpan(Callable<V> callable, SpanMetadata request);

	default <V> Result<V> monitorSpan(
			@NonNull final Runnable runnable,
			@NonNull final SpanMetadata request)
	{
		return monitorSpan(
				() -> {
					runnable.run();
					return null;
				},
				request);
	}

	@Value
	@Builder
	public static class SpanMetadata
	{
		@NonNull
		String name;

		@NonNull
		String type;

		@Nullable
		String subType;

		@Nullable
		String action;

		@Singular
		Map<String, String> labels;
	}

	<V> Result<V> monitorTransaction(Callable<V> callable, TransactionMetadata request);

	default <V> Result<V> monitorTransaction(
			@NonNull final Runnable runnable,
			@NonNull final TransactionMetadata request)
	{
		return monitorTransaction(
				() -> {
					runnable.run();
					return null;
				},
				request);
	}

	@Value
	@Builder
	public static class TransactionMetadata
	{
		@NonNull
		String name;

		@Singular
		Map<String, String> labels;

		@NonNull
		Type type;
	}

	public enum Type
	{
		MODEL_INTERCEPTOR("modelInterceptor"),

		MODEL_CHANGE("modelChange"),

		DOC_VALIDATE("docValidate"),

		DOC_ACTION("docAction"),

		ASYNC_WORKPACKAGE("asyncWorkPackage");

		private Type(String code)
		{
			this.code = code;
		}

		@Getter
		private String code;
	}

	@Value
	public static class Result<V>
	{
		public static <V> Result<V> ofResult(final V result)
		{
			return new Result<V>(result, null);
		}

		public static <V> Result<V> ofException(@NonNull final Exception exception)
		{
			return new Result<V>(null, exception);
		}

		V callableResult;

		Exception exception;

		private Result(
				@Nullable final V callableResult,
				@Nullable final Exception exception)
		{
			this.callableResult = callableResult;
			this.exception = exception;
		}
	}
}
