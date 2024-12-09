package de.metas.monitoring.adapter;

<<<<<<< HEAD
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

<<<<<<< HEAD
=======
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public static final String LABEL_RECORD_ID = "recordId";
	public static final String LABEL_EXTERNAL_HEADER_ID = "externalHeaderId";
	public static final String LABEL_EXTERNAL_LINE_ID = "externalLineId";
	public static final String LABEL_WORKPACKAGE_ID = "de.metas.async.C_Queue_WorkPackage_ID";
	public static final Set<String> VOLATILE_LABELS = ImmutableSet.of(LABEL_RECORD_ID, LABEL_EXTERNAL_LINE_ID, LABEL_EXTERNAL_HEADER_ID, LABEL_WORKPACKAGE_ID);
	
	/** Invoke the given {@code callable} as a span. Capture exception and re-throw it, wrapped as RuntimeException if required. */
	<V> V monitorSpan(Callable<V> callable, SpanMetadata metadata);

	default void monitorSpan(final Runnable runnable, final SpanMetadata metadata)
	{
		monitorSpan(
=======
	String LABEL_RECORD_ID = "recordId";
	String LABEL_EXTERNAL_HEADER_ID = "externalHeaderId";
	String LABEL_EXTERNAL_LINE_ID = "externalLineId";
	String LABEL_WORKPACKAGE_ID = "de.metas.async.C_Queue_WorkPackage_ID";
	ImmutableSet<String> VOLATILE_LABELS = ImmutableSet.of(LABEL_RECORD_ID, LABEL_EXTERNAL_LINE_ID, LABEL_EXTERNAL_HEADER_ID, LABEL_WORKPACKAGE_ID);

	/**
	 * Invoke the given {@code callable} as a span. Capture exception and re-throw it, wrapped as RuntimeException if required.
	 */
	<V> V monitor(Callable<V> callable, Metadata metadata);

	default void monitor(final Runnable runnable, final Metadata metadata)
	{
		monitor(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				() -> {
					runnable.run();
					return null;
				},
				metadata);
	}
<<<<<<< HEAD
	
	@Value
	@Builder
	class SpanMetadata
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

		@Nullable
		BiConsumer<String, String> distributedHeadersInjector;
	}

	/** Invoke the given {@code callable} as a transaction. Capture exception and re-throw it, wrapped as RuntimeException if required. */
	<V> V monitorTransaction(Callable<V> callable, TransactionMetadata request);

	default void monitorTransaction(
			@NonNull final Runnable runnable,
			@NonNull final TransactionMetadata request)
	{
		monitorTransaction(
				() -> {
					runnable.run();
					return null;
				},
				request);
	}

	@Value
	@Builder
	class TransactionMetadata
	{
		@NonNull
		String name;

		@Singular
		Map<String, String> labels;

		@NonNull
		Type type;

		@Singular
		Map<String, String> distributedTransactionHeaders;
=======

	void recordElapsedTime(final long duration, TimeUnit unit, final Metadata metadata);

	@Value
	@Builder
	class Metadata
	{
		@NonNull
		Type type;

		@NonNull
		String className;

		@NonNull
		String functionName;

		@Nullable
		String windowNameAndId;

		boolean isGroupingPlaceholder;

		@Singular
		Map<String, String> labels;

		public String getFunctionNameFQ() {return className + " - " + functionName;}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	enum Type
	{
		MODEL_INTERCEPTOR("modelInterceptor"),

		DOC_ACTION("docAction"),

		ASYNC_WORKPACKAGE("asyncWorkPackage"),

		SCHEDULER("scheduler"),

<<<<<<< HEAD
		REST_API_PROCESSING("rest-API"),

		CACHE_OPERATION("cache-operation"),

		EVENTBUS_REMOTE_ENDPOINT("eventbus-remote-endpoint");
=======
		EVENTBUS_REMOTE_ENDPOINT("eventbus-remote-endpoint"),

		REST_CONTROLLER("rest-controller"),

		REST_CONTROLLER_WITH_WINDOW_ID("rest-controller-with-windowId"),

		PO("po"),

		DB("db");
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		Type(final String code)
		{
			this.code = code;
		}

<<<<<<< HEAD
		@Getter
		private final String code;
	}

	enum SubType
	{
		MODEL_CHANGE("modelChange"),

		DOC_VALIDATE("docValidate"),

		CACHE_INVALIDATE("cache-invalidate"),

		ADD_TO_CACHE("addToCache"),

		EVENT_SEND("event-send"),

		EVENT_RECEIVE("event-receive");

		SubType(final String code)
		{
			this.code = code;
=======
		public boolean isAnyRestControllerType()
		{
			return this == Type.REST_CONTROLLER || this == Type.REST_CONTROLLER_WITH_WINDOW_ID;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		@Getter
		private final String code;
	}
}
