package de.metas.monitoring.adapter.apm;

import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringServiceUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.Callable;

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

@Service
@Primary
public class MicrometerPerformanceMonitoringService implements PerformanceMonitoringService
{
	private final MeterRegistry meterRegistry;
	private final Optional<APMPerformanceMonitoringService> apmPerformanceMonitoringService;

	public MicrometerPerformanceMonitoringService(
			@NonNull Optional<APMPerformanceMonitoringService> apmPerformanceMonitoringService,
			@NonNull final MeterRegistry meterRegistry)
	{
		this.apmPerformanceMonitoringService = apmPerformanceMonitoringService;
		this.meterRegistry = meterRegistry;
	}

	@Override
	public <V> V monitorTransaction(
			@NonNull final Callable<V> callable,
			@NonNull final TransactionMetadata metadata)
	{
		final ArrayList<Tag> tags = createTagsFromLabels(metadata.getLabels());
		tags.add(Tag.of("Name", metadata.getName()));

		final Callable<V> callableToUse;
		if (apmPerformanceMonitoringService.isPresent())
		{
			callableToUse = () -> apmPerformanceMonitoringService.get().monitorTransaction(callable, metadata);
		}
		else
		{
			callableToUse = callable;
		}
		return recordCallable(callableToUse, tags, "mf." + metadata.getType().getCode());
	}

	private ArrayList<Tag> createTagsFromLabels(final Map<String, String> labels)
	{
		final ArrayList<Tag> tags = new ArrayList<>();
		for (final Entry<String, String> entry : labels.entrySet())
		{
			tags.add(Tag.of(entry.getKey(), entry.getValue()));
		}
		return tags;
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

	@Override
	public <V> V monitorSpan(
			@NonNull final Callable<V> callable,
			@NonNull final SpanMetadata metadata)
	{
		final ArrayList<Tag> tags = createTagsFromLabels(metadata.getLabels());
		tags.add(Tag.of("Name", metadata.getName()));
		tags.add(Tag.of("SubType", metadata.getSubType()));
		tags.add(Tag.of("Action", metadata.getAction()));

		final Callable<V> callableToUse;
		if (apmPerformanceMonitoringService.isPresent())
		{
			callableToUse = () -> apmPerformanceMonitoringService.get().monitorSpan(callable, metadata);
		}
		else
		{
			callableToUse = callable;
		}
		
		return recordCallable(callableToUse, tags, "mf." + metadata.getType());
	}
}
