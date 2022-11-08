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

import de.metas.common.util.EmptyUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Service
@Primary
public class MicrometerPerformanceMonitoringService implements PerformanceMonitoringService
{
	private final MeterRegistry meterRegistry;
	private static ThreadLocal<PerformanceMonitoringData> perfMonDataTL = new ThreadLocal<>();
	private static String METER_PREFIX = "mf.";

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
		if(perfMonDataTL.get() == null)
		{
			perfMonDataTL.set(new PerformanceMonitoringData());
		}

		final PerformanceMonitoringData perfMonData = perfMonDataTL.get();
		final ArrayList<Tag> tags = new ArrayList<>();

		addTagIfNotNull("name", metadata.getClassName(), tags);
		addTagIfNotNull("action", metadata.getFunctionName(), tags);

		if(!perfMonData.isCalledByMonitoredFunction() && metadata.getType().isAnyRestControllerType())
		{
			perfMonData.setRestControllerData(metadata);
		}
		else if(!perfMonData.isCalledByMonitoredFunction())
		{
			perfMonData.setInitiatorLabelActive(false);
		}

		if(perfMonData.isInitiatorLabelActive())
		{
			addInitiatorTags(perfMonData, metadata, tags);
			perfMonData.getCalledBy().add(perfMonData.getDepth() + 1 ,metadata.getClassName() + " - " + metadata.getFunctionName());

			final Type type = getTypeForMeterName(metadata);

			perfMonData.incrementDepth();
			try
			{
				return recordCallable(callable, tags, METER_PREFIX + type.getCode());
			}
			finally
			{
				perfMonData.decrementDepth();
			}
		}
		else
		{
			appendLabelsToTags(tags, metadata.getLabels());
			return recordCallable(callable, tags, METER_PREFIX + metadata.getType().getCode());
		}
	}

	public void monitor(final long duration, TimeUnit unit, final Metadata metadata)
	{
		if(perfMonDataTL.get() == null)
		{
			perfMonDataTL.set(new PerformanceMonitoringData());
		}

		final PerformanceMonitoringData perfMonData = perfMonDataTL.get();
		final ArrayList<Tag> tags = new ArrayList<>();

		addTagIfNotNull("name", metadata.getClassName(), tags);
		addTagIfNotNull("action", metadata.getFunctionName(), tags);

		if(perfMonData.isInitiatorLabelActive())
		{
			addInitiatorTags(perfMonData, metadata, tags);
			perfMonData.getCalledBy().add(perfMonData.getDepth() + 1, metadata.getClassName() + " - " + metadata.getFunctionName());
			final Type type = getTypeForMeterName(metadata);
			final Timer timer = meterRegistry.timer(METER_PREFIX + type.getCode(), tags);
			timer.record(duration, unit);
		}
		else
		{
			appendLabelsToTags(tags, metadata.getLabels());
			final Timer timer = meterRegistry.timer(METER_PREFIX + metadata.getType().getCode(), tags);
			timer.record(duration, unit);
		}
	}

	private static void appendLabelsToTags(@NonNull final ArrayList<Tag> tags, @NonNull final Map<String, String> labels)
	{
		for (final Entry<String, String> entry : labels.entrySet())
		{
			if (PerformanceMonitoringService.VOLATILE_LABELS.contains(entry.getKey()))
			{
				// Avoid OOME: if we included e.g. the recordId, then every recordId would cause a new meter to be created.
				continue;
			}
			addTagIfNotNull(entry.getKey(), entry.getValue(), tags);
		}
	}
	
	private static void addTagIfNotNull(@Nullable final String name, @Nullable final String value, @NonNull ArrayList<Tag> tags)
	{
		if (!EmptyUtil.isBlank(name) && !EmptyUtil.isBlank(value))
		{
			tags.add(Tag.of(name, value));
		}
	}

	private static void addInitiatorTags(
			@NonNull PerformanceMonitoringData perfMonData,
			@NonNull Metadata metadata,
			@NonNull ArrayList<Tag> tags)
	{
		addTagIfNotNull("depth", String.valueOf(perfMonData.getDepth()), tags);
		addTagIfNotNull("initiator", perfMonData.getInitiator(), tags);
		addTagIfNotNull("window", perfMonData.getInitiatorWindow(), tags);
		addTagIfNotNull("callerName", metadata.getClassName() + " - " + metadata.getFunctionName(), tags);
		addTagIfNotNull("calledBy", perfMonData.getCalledBy().get(perfMonData.getDepth()), tags);
	}

	private Type getTypeForMeterName(Metadata metadata)
	{
		final Type type;
		if(metadata.getType().isAnyRestControllerType())
		{
			type = metadata.getType();
		}
		else
		{
			type = Type.REST_API_PROCESSING;
		}
		return type;
	}

	private <V> V recordCallable(final Callable<V> callable, final ArrayList<Tag> tags, final String name)
	{
		final Timer timer = meterRegistry.timer(name, tags);
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
