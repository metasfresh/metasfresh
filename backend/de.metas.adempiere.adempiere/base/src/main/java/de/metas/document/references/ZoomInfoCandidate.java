package de.metas.document.references;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.lang.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public final class ZoomInfoCandidate
{
	private static final Logger logger = LogManager.getLogger(ZoomInfoCandidate.class);

	private final String id;
	private final String internalName;
	private final ITranslatableString destinationDisplay;
	private final MQuery query;
	@Getter
	private final AdWindowId adWindowId;
	private final Priority priority;
	private final IntSupplier recordsCountSupplier;

	@Builder
	private ZoomInfoCandidate(
			@NonNull final String id,
			@NonNull final String internalName,
			@NonNull final AdWindowId adWindowId,
			@NonNull final Priority priority,
			@NonNull final MQuery query,
			@NonNull final ITranslatableString destinationDisplay,
			@NonNull final IntSupplier recordsCountSupplier)
	{
		this.id = Check.assumeNotEmpty(id, "id is not empty");
		this.internalName = Check.assumeNotEmpty(internalName, "internalName is not empty");

		this.adWindowId = adWindowId;
		this.priority = priority;

		this.query = query;
		this.destinationDisplay = destinationDisplay;

		this.recordsCountSupplier = recordsCountSupplier;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("internalName", internalName)
				.add("display", destinationDisplay.getDefaultValue())
				.add("adWindowId", adWindowId)
				.toString();
	}

	public void updateRecordsCount()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final int recordsCount = recordsCountSupplier.getAsInt();
		final Duration recordsCountDuration = Duration.ofNanos(stopwatch.stop().elapsed(TimeUnit.NANOSECONDS));
		query.setRecordCount(recordsCount, recordsCountDuration);
		logger.debug("Updated records count for {} in {}", this, stopwatch);
	}

	public Optional<ZoomInfo> evaluate()
	{
		final Map<AdWindowId, Priority> alreadySeenWindowIds = null;
		return evaluate(alreadySeenWindowIds);
	}

	public Optional<ZoomInfo> evaluate(@Nullable final Map<AdWindowId, Priority> alreadySeenWindowIds)
	{
		//
		// Only consider a window already seen if it actually has record count > 0 (task #1062)
		final Priority alreadySeenZoomInfoPriority = alreadySeenWindowIds != null
				? alreadySeenWindowIds.get(adWindowId)
				: null;
		if (alreadySeenZoomInfoPriority != null
				&& alreadySeenZoomInfoPriority.isHigherThan(priority))
		{
			logger.debug("Skipping zoomInfo {} because there is already one for destination '{}'", this, adWindowId);
			return Optional.empty();
		}

		//
		// Compute records count
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final int recordsCount = recordsCountSupplier.getAsInt();
		stopwatch.stop();
		logger.debug("Computed records count for {} in {}", this, stopwatch);

		if (recordsCount <= 0)
		{
			logger.debug("No records found for {}", this);
			return Optional.empty();
		}

		//
		// We got a valid zoom info
		// => accept it
		if (alreadySeenWindowIds != null)
		{
			alreadySeenWindowIds.put(adWindowId, priority);
		}

		final ITranslatableString caption = TranslatableStrings.builder()
				.append(destinationDisplay)
				.append(" (#" + recordsCount + ")")
				.build();

		final Duration recordsCountDuration = Duration.ofNanos(stopwatch.elapsed(TimeUnit.NANOSECONDS));
		final MQuery queryCopy = query.deepCopy();
		queryCopy.setRecordCount(recordsCount, recordsCountDuration);

		return Optional.of(ZoomInfo.builder()
				.id(id)
				.internalName(internalName)
				.adWindowId(adWindowId)
				.priority(priority)
				.caption(caption)
				.query(queryCopy)
				.build());
	}
}
