/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.references.related_documents;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Priority;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import lombok.experimental.Delegate;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Value
public class ZoomInfoCandidateGroup
{
	private static final Logger logger = LogManager.getLogger(ZoomInfoCandidateGroup.class);

	ImmutableList<ZoomInfoCandidate> candidates;
	AdWindowId targetWindowId;

	@Builder
	private ZoomInfoCandidateGroup(
			@NonNull @Singular final ImmutableList<ZoomInfoCandidate> candidates)
	{
		Check.assumeNotEmpty(candidates, "candidates");
		this.candidates = candidates;

		targetWindowId = CollectionUtils.extractSingleElement(candidates, ZoomInfoCandidate::getTargetWindowId);
	}

	public static ZoomInfoCandidateGroup of(@NonNull ZoomInfoCandidate candidate)
	{
		return builder().candidate(candidate).build();
	}

	public boolean isZoomInfoIdMatching(@NonNull final ZoomInfoId zoomInfoId)
	{
		return candidates.stream().anyMatch(candidate -> candidate.isZoomInfoIdMatching(zoomInfoId));
	}

	public Stream<ZoomInfo> evaluateAndStream(@Nullable final ZoomTargetWindowEvaluationContext context)
	{
		final ImmutableList<ZoomInfoCandidateWithRecordsCount> candidatesWithRecordCount = candidates
				.stream()
				.map(candidate -> evaluateOrNull(candidate, context))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		if (candidatesWithRecordCount.isEmpty())
		{
			return Stream.empty();
		}
		else if (candidatesWithRecordCount.size() == 1)
		{
			final ZoomInfo zoomInfo = toZoomInfo(candidatesWithRecordCount.get(0).withAppendFilterByFieldCaption(false));
			return Stream.of(zoomInfo);
		}
		else
		{
			return candidatesWithRecordCount.stream()
					.map(candidateWithRecordsCount -> candidateWithRecordsCount.withAppendFilterByFieldCaption(true))
					.map(this::toZoomInfo);
		}
	}

	private ZoomInfoCandidateWithRecordsCount evaluateOrNull(
			@NonNull final ZoomInfoCandidate candidate,
			@Nullable final ZoomTargetWindowEvaluationContext context)
	{
		// Apply onlyZoomInfoId
		if (context != null && context.getOnlyZoomInfoId() != null && !ZoomInfoId.equals(context.getOnlyZoomInfoId(), candidate.getId()))
		{
			return null;
		}

		//
		// Only consider a window already seen if it actually has record count > 0 (task #1062)
		final Priority alreadySeenZoomInfoPriority = context != null
				? context.getPriorityOrNull(candidate.getTargetWindow())
				: null;
		if (alreadySeenZoomInfoPriority != null
				&& alreadySeenZoomInfoPriority.isHigherThan(candidate.getPriority()))
		{
			logger.debug("Skipping zoomInfo {} because there is already one for destination '{}'", this, candidate.getTargetWindow());
			return null;
		}

		//
		// Compute records count
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final int recordsCount = candidate.getRecordsCountSupplier().getRecordsCount();
		stopwatch.stop();
		logger.debug("Computed records count for {} in {}", this, stopwatch);
		if (recordsCount <= 0)
		{
			logger.debug("No records found for {}", this);
			return null;
		}
		final Duration recordsCountDuration = Duration.ofNanos(stopwatch.elapsed(TimeUnit.NANOSECONDS));

		if (context != null)
		{
			context.putWindow(candidate.getTargetWindow(), candidate.getPriority());
		}

		return ZoomInfoCandidateWithRecordsCount.builder()
				.candidate(candidate)
				.recordsCount(recordsCount)
				.recordsCountDuration(recordsCountDuration)
				.build();
	}

	private ZoomInfo toZoomInfo(@NonNull final ZoomInfoCandidateWithRecordsCount candidateWithRecordsCount)
	{
		final ZoomInfoCandidate candidate = candidateWithRecordsCount.getCandidate();
		final int recordsCount = candidateWithRecordsCount.getRecordsCount();
		final Duration recordsCountDuration = candidateWithRecordsCount.getRecordsCountDuration();

		final TranslatableStringBuilder captionBuilder = TranslatableStrings.builder()
				.append(candidate.getWindowCaption());
		if (candidateWithRecordsCount.isAppendFilterByFieldCaption()
				&& !TranslatableStrings.isBlank(candidate.getFilterByFieldCaption())
				&& !Objects.equals(candidate.getWindowCaption(), candidate.getFilterByFieldCaption()))
		{
			captionBuilder.append(" / ").append(candidate.getFilterByFieldCaption());
		}

		final ITranslatableString caption = captionBuilder
				.append(" (#").append(recordsCount).append(")")
				.build();

		final MQuery queryCopy = candidate.getQuery().deepCopy();
		queryCopy.setRecordCount(recordsCount, recordsCountDuration);

		return ZoomInfo.builder()
				.id(candidate.getId())
				.internalName(candidate.getInternalName())
				.targetWindow(candidate.getTargetWindow())
				.priority(candidate.getPriority())
				.caption(caption)
				.filterByFieldCaption(candidate.getFilterByFieldCaption())
				.query(queryCopy)
				.build();
	}

	@Value
	@Builder
	private static class ZoomInfoCandidateWithRecordsCount
	{
		@Delegate
		@NonNull ZoomInfoCandidate candidate;

		int recordsCount;
		Duration recordsCountDuration;

		@With
		boolean appendFilterByFieldCaption;
	}

}
