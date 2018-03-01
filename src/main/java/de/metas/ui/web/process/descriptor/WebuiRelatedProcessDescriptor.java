package de.metas.ui.web.process.descriptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.ProcessId;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Webui related process descriptor.
 *
 * NOTE: this is a short living object and it shall not be cached
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public final class WebuiRelatedProcessDescriptor
{
	private final ProcessId processId;
	private final ITranslatableString processCaption;
	private final ITranslatableString processDescription;
	private final boolean quickAction;
	private final boolean defaultQuickAction;
	@NonNull
	private final Supplier<ValueAndDuration<ProcessPreconditionsResolution>> preconditionsResolutionSupplier;

	private final String debugProcessClassname;

	@lombok.Builder
	private WebuiRelatedProcessDescriptor(
			final ProcessId processId,
			final ITranslatableString processCaption,
			final ITranslatableString processDescription,
			final boolean quickAction,
			final boolean defaultQuickAction,
			@NonNull final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier,
			final String debugProcessClassname)
	{
		super();
		this.processId = processId;
		this.processCaption = processCaption;
		this.processDescription = processDescription;
		this.quickAction = quickAction;
		this.defaultQuickAction = defaultQuickAction;

		// Memorize the resolution supplier to make sure it's not invoked more than once because it might be an expensive operation.
		// Also we assume this is a short living instance which was created right before checking
		this.preconditionsResolutionSupplier = ExtendedMemorizingSupplier.of(() -> ValueAndDuration.fromSupplier(preconditionsResolutionSupplier));

		this.debugProcessClassname = debugProcessClassname;
	}

	public ProcessId getProcessId()
	{
		return processId;
	}

	public String getCaption(final String adLanguage)
	{
		final String captionOverride = getPreconditionsResolution().getCaptionOverrideOrNull(adLanguage);
		if (captionOverride != null)
		{
			return captionOverride;
		}

		return processCaption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return processDescription.translate(adLanguage);
	}

	public boolean isQuickAction()
	{
		return quickAction;
	}

	public boolean isDefaultQuickAction()
	{
		return defaultQuickAction;
	}

	private ProcessPreconditionsResolution getPreconditionsResolution()
	{
		return preconditionsResolutionSupplier.get().getValue();
	}

	public Duration getPreconditionsResolutionCalcDuration()
	{
		return preconditionsResolutionSupplier.get().getDuration();
	}

	public boolean isDisabled()
	{
		return getPreconditionsResolution().isRejected();
	}

	public boolean isEnabled()
	{
		final ProcessPreconditionsResolution preconditionsResolution = getPreconditionsResolution();
		return preconditionsResolution.isAccepted();
	}

	public boolean isEnabledOrNotSilent()
	{
		final ProcessPreconditionsResolution preconditionsResolution = getPreconditionsResolution();
		return preconditionsResolution.isAccepted() || !preconditionsResolution.isInternal();
	}

	public String getDisabledReason(final String adLanguage)
	{
		return getPreconditionsResolution().getRejectReason().translate(adLanguage);
	}

	public Map<String, Object> getDebugProperties()
	{
		final ImmutableMap.Builder<String, Object> debugProperties = ImmutableMap.<String, Object> builder();

		if (debugProcessClassname != null)
		{
			debugProperties.put("debug-classname", debugProcessClassname);
		}

		return debugProperties.build();
	}

	@Value
	private static final class ValueAndDuration<T>
	{
		public static <T> ValueAndDuration<T> fromSupplier(final Supplier<T> supplier)
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();
			final T value = supplier.get();
			final Duration duration = Duration.ofNanos(stopwatch.stop().elapsed(TimeUnit.NANOSECONDS));
			return new ValueAndDuration<>(value, duration);
		}

		private final T value;
		private final Duration duration;

		private ValueAndDuration(final T value, final Duration duration)
		{
			this.value = value;
			this.duration = duration;
		}
	}
}
