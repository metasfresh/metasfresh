package de.metas.ui.web.process.descriptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_AD_Process;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.logging.TableRecordMDC;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.process.ProcessId;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
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
	@Getter
	private final ProcessId processId;

	@Getter
	private final InternalName internalName;

	private final ITranslatableString processCaption;
	private final ITranslatableString processDescription;

	@Getter
	private final ImmutableSet<DisplayPlace> displayPlaces;

	@Getter
	private final boolean defaultQuickAction;

	@Getter
	private final String shortcut;

	@NonNull
	private final Supplier<ValueAndDuration<ProcessPreconditionsResolution>> preconditionsResolutionSupplier;

	private final String debugProcessClassname;

	@Getter
	private final int sortNo;

	@lombok.Builder
	private WebuiRelatedProcessDescriptor(
			final ProcessId processId,
			final InternalName internalName,
			final ITranslatableString processCaption,
			final ITranslatableString processDescription,
			@NonNull @Singular final ImmutableSet<DisplayPlace> displayPlaces,
			final boolean defaultQuickAction,
			final String shortcut,
			@NonNull final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier,
			final int sortNo,
			final String debugProcessClassname)
	{
		this.processId = processId;
		this.internalName = internalName;
		this.processCaption = processCaption;
		this.processDescription = processDescription;
		this.displayPlaces = displayPlaces;
		this.defaultQuickAction = defaultQuickAction && displayPlaces.contains(DisplayPlace.ViewQuickActions);

		this.shortcut = shortcut;

		// Memorize the resolution supplier to make sure it's not invoked more than once because it might be an expensive operation.
		// Also we assume this is a short living instance which was created right before checking
		this.preconditionsResolutionSupplier = ExtendedMemorizingSupplier.of(() -> ValueAndDuration.fromSupplier(preconditionsResolutionSupplier));

		this.sortNo = sortNo > 0 ? sortNo : 0;

		this.debugProcessClassname = debugProcessClassname;

	}

	public String getCaption(final String adLanguage)
	{
		return getPreconditionsResolution().computeCaption(processCaption, adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return processDescription.translate(adLanguage);
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
		try (final MDCCloseable processMDC = TableRecordMDC.putTableRecordReference(I_AD_Process.Table_Name, processId == null ? null : processId.toAdProcessIdOrNull()))
		{
			final ProcessPreconditionsResolution preconditionsResolution = getPreconditionsResolution();
			return preconditionsResolution.isAccepted() || !preconditionsResolution.isInternal();
		}
	}

	public boolean isInternal()
	{
		final ProcessPreconditionsResolution preconditionsResolution = getPreconditionsResolution();
		return preconditionsResolution.isInternal();
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

	public boolean isDisplayedOn(@NonNull final DisplayPlace displayPlace)
	{
		return getDisplayPlaces().contains(displayPlace);
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
