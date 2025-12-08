package de.metas.monitoring.adapter;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
				() -> {
					runnable.run();
					return null;
				},
				metadata);
	}

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

	}

	enum Type
	{
		MODEL_INTERCEPTOR("modelInterceptor"),

		DOC_ACTION("docAction"),

		ASYNC_WORKPACKAGE("asyncWorkPackage"),

		SCHEDULER("scheduler"),

		EVENTBUS_REMOTE_ENDPOINT("eventbus-remote-endpoint"),

		REST_CONTROLLER("rest-controller"),

		REST_CONTROLLER_WITH_WINDOW_ID("rest-controller-with-windowId"),

		PO("po"),

		DB("db");

		Type(final String code)
		{
			this.code = code;
		}

		public boolean isAnyRestControllerType()
		{
			return this == Type.REST_CONTROLLER || this == Type.REST_CONTROLLER_WITH_WINDOW_ID;
		}

		@Getter
		private final String code;
	}
}
