package de.metas.monitoring.adapter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;
import com.google.common.collect.ImmutableSet;
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
	public static final String LABEL_RECORD_ID = "recordId";
	public static final String LABEL_EXTERNAL_HEADER_ID = "externalHeaderId";
	public static final String LABEL_EXTERNAL_LINE_ID = "externalLineId";
	public static final String LABEL_WORKPACKAGE_ID = "de.metas.async.C_Queue_WorkPackage_ID";
	public static final Set<String> VOLATILE_LABELS = ImmutableSet.of(LABEL_RECORD_ID, LABEL_EXTERNAL_LINE_ID, LABEL_EXTERNAL_HEADER_ID, LABEL_WORKPACKAGE_ID);
	
	/** Invoke the given {@code callable} as a span. Capture exception and re-throw it, wrapped as RuntimeException if required. */
	<V> V monitor(Callable<V> callable, Metadata metadata);

	default void monitor(final Runnable runnable, final Metadata metadata)
	{
		monitor(
				() -> {
					runnable.run();
					return null;
				},
				metadata);
	}
	
	@Value
	@Builder
	class Metadata
	{
		@NonNull
		String name;

		@NonNull
		Type type;

		@Nullable
		SubType subType;

		@Nullable
		String action;

		@Nullable
		String windowNameAndId;

		@Singular
		Map<String, String> labels;
	}

	enum Type
	{
		MODEL_INTERCEPTOR("modelInterceptor"),

		DOC_ACTION("docAction"),

		ASYNC_WORKPACKAGE("asyncWorkPackage"),

		SCHEDULER("scheduler"),

		REST_API_PROCESSING("rest-API-processing"),

		EVENTBUS_REMOTE_ENDPOINT("eventbus-remote-endpoint"),

		REST_CONTROLLER("rest-controller"),

		REST_CONTROLLER_WITH_WINDOW_ID("rest-controller-with-windowId"),

		PO("po");

		Type(final String code)
		{
			this.code = code;
		}

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
		}

		@Getter
		private final String code;
	}
}
