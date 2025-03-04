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

package de.metas.monitoring.adapter;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Service
@Primary
public class MicrometerPerformanceMonitoringService implements PerformanceMonitoringService
{
	private final MeterRegistry meterRegistry;
	private static final ThreadLocal<PerformanceMonitoringData> perfMonDataTL = new ThreadLocal<>();
	private static final String METER_PREFIX = "mf.";

	public MicrometerPerformanceMonitoringService(
			@NonNull final MeterRegistry meterRegistry)
	{
		this.meterRegistry = meterRegistry;
	}

	@Override
	public <V> V monitor(
			@NonNull final Callable<V> callable,
			@NonNull final PerformanceMonitoringService.Metadata metadata)
	{
		final PerformanceMonitoringData perfMonData = getPerformanceMonitoringData();

		final Metadata dummyHTTPRequestMetadata = perfMonData.isInitiator() && metadata.getType().isAnyRestControllerType()
				? createHTTPRequestPlaceholder(metadata)
				: null;

		try (final IAutoCloseable ignored = perfMonData.addCalledByIfNotNull(dummyHTTPRequestMetadata))
		{
			final ArrayList<Tag> tags = createTags(metadata, perfMonData);
			try (final IAutoCloseable ignored2 = perfMonData.addCalledByIfNotNull(metadata))
			{
				final Type effectiveType = perfMonData.getEffectiveType(metadata);
				final Timer timer = meterRegistry.timer(METER_PREFIX + effectiveType.getCode(), tags);
				try
				{
					return timer.recordCallable(callable);
				}
				catch (final Exception e)
				{
					throw PerformanceMonitoringServiceUtil.asRTE(e);
				}
			}
		}
	}

	private PerformanceMonitoringService.Metadata createHTTPRequestPlaceholder(final PerformanceMonitoringService.Metadata metadata)
	{
		final Type type = metadata.getType();
		final String windowNameAndId = metadata.getWindowNameAndId();
		final Type typeEffective;
		if (type.isAnyRestControllerType())
		{
			typeEffective = type;
		}
		else if (Check.isBlank(windowNameAndId))
		{
			typeEffective = Type.REST_CONTROLLER;
		}
		else
		{
			typeEffective = Type.REST_CONTROLLER_WITH_WINDOW_ID;
		}
		return PerformanceMonitoringService.Metadata.builder()
				.type(typeEffective)
				.className("HTTP")
				.functionName("Request")
				.windowNameAndId(windowNameAndId)
				.isGroupingPlaceholder(true)
				//.labels() // don't copy the labels
				.build();
	}

	@Override
	public void recordElapsedTime(final long duration, final TimeUnit unit, final Metadata metadata)
	{
		final PerformanceMonitoringData perfMonData = getPerformanceMonitoringData();

		final ArrayList<Tag> tags = createTags(metadata, perfMonData);

		try (final IAutoCloseable ignored = perfMonData.addCalledByIfNotNull(metadata))
		{
			final Type effectiveType = perfMonData.getEffectiveType(metadata);
			final Timer timer = meterRegistry.timer(METER_PREFIX + effectiveType.getCode(), tags);
			timer.record(duration, unit);
		}
	}

	private static PerformanceMonitoringData getPerformanceMonitoringData()
	{
		if (perfMonDataTL.get() == null)
		{
			perfMonDataTL.set(new PerformanceMonitoringData());
		}
		return perfMonDataTL.get();
	}

	@NonNull
	private static ArrayList<Tag> createTags(final @NonNull Metadata metadata, final PerformanceMonitoringData perfMonData)
	{
		final ArrayList<Tag> tags = new ArrayList<>();
		addTagIfNotNull("name", metadata.getClassName(), tags);
		addTagIfNotNull("action", metadata.getFunctionName(), tags);

		if (!perfMonData.isInitiator())
		{
			addTagIfNotNull("depth", String.valueOf(perfMonData.getDepth()), tags);
			addTagIfNotNull("initiator", perfMonData.getInitiatorFunctionNameFQ(), tags);
			addTagIfNotNull("window", perfMonData.getInitiatorWindow(), tags);
			addTagIfNotNull("callerName", metadata.getFunctionNameFQ(), tags);
			addTagIfNotNull("calledBy", perfMonData.getLastCalledFunctionNameFQ(), tags);
		}
		else
		{
			for (final Entry<String, String> entry : metadata.getLabels().entrySet())
			{
				if (PerformanceMonitoringService.VOLATILE_LABELS.contains(entry.getKey()))
				{
					// Avoid OOME: if we included e.g. the recordId, then every recordId would cause a new meter to be created.
					continue;
				}
				addTagIfNotNull(entry.getKey(), entry.getValue(), tags);
			}
		}
		return tags;
	}

	private static void addTagIfNotNull(@Nullable final String name, @Nullable final String value, @NonNull final ArrayList<Tag> tags)
	{
		final String nameNorm = StringUtils.trimBlankToNull(name);
		if (nameNorm == null)
		{
			return;
		}

		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			return;
		}

		tags.add(Tag.of(nameNorm, valueNorm));
	}

}
