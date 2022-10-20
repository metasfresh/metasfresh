/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.monitoring.micrometer;

import de.metas.common.util.EmptyUtil;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringServiceUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.Callable;

@Service
@Primary
public class MicrometerPerformanceMonitoringService implements PerformanceMonitoringService
{
	private final MeterRegistry meterRegistry;
	private final ThreadLocal<PerformanceMonitoringData> perfMonDataTL = ThreadLocal.withInitial(() -> new PerformanceMonitoringData());

	@Value("${performance.monitoring.enable:false}")
	private Boolean perfMonEnvVar;

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
		if(perfMonEnvVar != false)
		{
			try
			{
				return callable.call();
			}
			catch (Exception e)
			{
				throw PerformanceMonitoringServiceUtil.asRTE(e);
			}
		}

		PerformanceMonitoringData perfMonData = perfMonDataTL.get();

		final ArrayList<Tag> tags = createTagsFromLabels(metadata.getLabels());

		mkTagIfNotNull("name", metadata.getName()).ifPresent(tags::add);
		mkTagIfNotNull("action", metadata.getAction()).ifPresent(tags::add);
		mkTagIfNotNull("depth", String.valueOf(perfMonData.getDepth())).ifPresent(tags::add);

		if(perfMonData.getDepth() == 0
				&& metadata.getType() == Type.REST_CONTROLLER_WITH_WINDOW_ID
				&& metadata.getAction() != null
				&& metadata.getWindowNameAndId() != null)
		{
			perfMonData.setIsInitiatorLabelActive(true);
			perfMonData.setInitiator(metadata.getName() + " - " + metadata.getAction());
			perfMonData.setInitiatorWindow(metadata.getWindowNameAndId());
			perfMonData.getCalledBy().add(0, "HTTP Request");

		}
		else if(perfMonData.getDepth() == 0
				&& metadata.getType() == Type.REST_CONTROLLER
				&& metadata.getAction() != null)
		{
			perfMonData.setIsInitiatorLabelActive(true);
			perfMonData.setInitiator(metadata.getName() + " - " + metadata.getAction());
			perfMonData.setInitiatorWindow(null);
			perfMonData.getCalledBy().add(0, "HTTP Request");
		}
		else if(perfMonData.getDepth() == 0)
		{
			perfMonData.setIsInitiatorLabelActive(false);
		}

		if(perfMonData.getIsInitiatorLabelActive())
		{
			mkTagIfNotNull("initiator", perfMonData.getInitiator()).ifPresent(tags::add);
			mkTagIfNotNull("window", perfMonData.getInitiatorWindow()).ifPresent(tags::add);
			mkTagIfNotNull("callerName", metadata.getName() + " - " + metadata.getAction()).ifPresent(tags::add);
			mkTagIfNotNull("calledBy", perfMonData.getCalledBy().get(perfMonData.getDepth())).ifPresent(tags::add);
			perfMonData.getCalledBy().add(perfMonData.getDepth() + 1 ,metadata.getName() + " - " + metadata.getAction());

			perfMonData.setDepth(perfMonData.getDepth() + 1);
			try
			{
				return recordCallable(callable, tags, "mf." + metadata.getType().getCode());
			}
			finally
			{
				perfMonData.setDepth(perfMonData.getDepth() - 1);
			}
		}

		try
		{
			return callable.call();
		}
		catch (Exception e)
		{
			throw PerformanceMonitoringServiceUtil.asRTE(e);
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
}
