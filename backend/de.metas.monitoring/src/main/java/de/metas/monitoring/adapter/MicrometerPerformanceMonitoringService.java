/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2021 metas GmbH
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
import org.adempiere.util.lang.IAutoCloseable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Primary
public class MicrometerPerformanceMonitoringService implements PerformanceMonitoringService
{
	private final MeterRegistry meterRegistry;
	private final ThreadLocal<Integer> step = ThreadLocal.withInitial(() -> 1);
	private final ThreadLocal<Integer> depth = ThreadLocal.withInitial(() -> 0);
	private final AtomicInteger correlationIdProvider = new AtomicInteger();
	private final ThreadLocal<Integer> correlationId = ThreadLocal.withInitial(() -> 0);
	private final ThreadLocal<Boolean> isCorrelationActive = ThreadLocal.withInitial(() -> false);
	private final ThreadLocal<Boolean> isCorrelationLocked = ThreadLocal.withInitial(() -> false);
	private final ThreadLocal<String[]> correlationTriggers = ThreadLocal.withInitial(this::getCorrelationTriggers);
	private final ThreadLocal<String> initiator = ThreadLocal.withInitial(() -> "");

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
		final ArrayList<Tag> tags = createTagsFromLabels(metadata.getLabels());

		mkTagIfNotNull("Name", metadata.getName()).ifPresent(tags::add);
		// if(metadata.getSubType() != null)
		// {
		// 	mkTagIfNotNull("SubType", metadata.getSubType().getCode()).ifPresent(tags::add);
		// }
		mkTagIfNotNull("Action", metadata.getAction()).ifPresent(tags::add);

		if(isCorrelationTrigger(metadata.getType().getCode()) && !isCorrelationActive.get())
		{
			isCorrelationActive.set(true);
			correlationId.set(correlationIdProvider.getAndIncrement());
		}
		if(isCorrelationActive.get())
		{
			addAdditionalTags(tags);
		}

		if(depth.get() == 0)
		{
			if(metadata.getAction() != null)
			{
				initiator.set(metadata.getType().getCode() + " - " + metadata.getAction());
			}
			else
			{
				initiator.set(metadata.getType().getCode());
			}
		}
		mkTagIfNotNull("Initiator", initiator.get()).ifPresent(tags::add);


		depth.set(depth.get() + 1);
		try(final IAutoCloseable ignored = this.reduceDepth())
		{
			if(isCorrelationTrigger(metadata.getType().getCode()) && !isCorrelationLocked.get()){
				try(final IAutoCloseable ignored2 = this.endCorrelation())
				{
					isCorrelationLocked.set(true);
					return recordCallable(callable, tags, "mf." + metadata.getType().getCode());
				}
			}
			return recordCallable(callable, tags, "mf." + metadata.getType().getCode());
		}
	}

	private ArrayList<Tag> createTagsFromLabels(@NonNull final Map<String, String> labels)
	{
		final ArrayList<Tag> tags = new ArrayList<>();
		for (final Entry<String, String> entry : labels.entrySet())
		{
			if (PerformanceMonitoringService.VOLATILE_LABELS.contains(entry.getKey()))
			{
				// Avoid OOME: if we included e.g. the recordId, then every recordId would cause a new meter to be created.
				continue;
			}
			mkTagIfNotNull(entry.getKey(), entry.getValue()).ifPresent(tags::add);
		}
		return tags;
	}
	
	private Optional<Tag> mkTagIfNotNull(@Nullable final String name, @Nullable final String value)
	{
		if (EmptyUtil.isBlank(name) || EmptyUtil.isBlank(value))
		{
			return Optional.empty();
		}
		return Optional.of(Tag.of(name, value));
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

	private void addAdditionalTags(ArrayList<Tag> tags)
	{
		// mkTagIfNotNull("ThreadId", String.valueOf(Thread.currentThread().getId())).ifPresent(tags::add);
		mkTagIfNotNull("Step", String.valueOf(step.get())).ifPresent(tags::add);
		step.set(step.get() + 1);
		mkTagIfNotNull("Depth", String.valueOf(depth.get())).ifPresent(tags::add);
		mkTagIfNotNull("CorrelationId", String.valueOf(correlationId.get())).ifPresent(tags::add);
	}

	private IAutoCloseable reduceDepth()
	{
		return () -> {
			depth.set(depth.get() - 1);
		};
	}

	private Boolean isCorrelationTrigger(String type)
	{
		return Arrays.asList(correlationTriggers.get()).contains(type);
	}

	private String[] getCorrelationTriggers()
	{
		final String[] correlationTriggers = new String[] { "" };
		return correlationTriggers;
	}

	private IAutoCloseable endCorrelation()
	{
		return () -> {
			isCorrelationActive.set(false);
			isCorrelationLocked.set(false);
			step.set(1);
		};
	}
}
