/*
 * #%L
 * de.metas.util
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

package de.metas.util.time;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntervalUtils
{
	@NonNull
	public static ImmutableList<InstantInterval> intervalDiff(
			@NonNull final ImmutableList<InstantInterval> intervals,
			@NonNull final ImmutableList<InstantInterval> intervalsToRemove)
	{
		if (intervals.isEmpty() || intervalsToRemove.isEmpty())
		{
			return intervals;
		}

		final ImmutableList<AnnotatedPoint> orderedAnnotatedPointList = buildOrderedAnnotatedPointList(intervals, intervalsToRemove);

		return removeGaps(orderedAnnotatedPointList);
	}

	@NonNull
	private static ImmutableList<AnnotatedPoint> buildOrderedAnnotatedPointList(
			@NonNull final List<InstantInterval> intervals,
			@NonNull final List<InstantInterval> intervalsToRemove)
	{
		final List<AnnotatedPoint> annotatedPoints = new ArrayList<>();

		intervals.forEach(interval -> {
			annotatedPoints.add(AnnotatedPoint.of(interval.getFrom(), AnnotatedPoint.PointType.Start));
			annotatedPoints.add(AnnotatedPoint.of(interval.getTo(), AnnotatedPoint.PointType.End));
		});

		intervalsToRemove.forEach(interval -> {
			annotatedPoints.add(AnnotatedPoint.of(interval.getFrom(), AnnotatedPoint.PointType.GapStart));
			annotatedPoints.add(AnnotatedPoint.of(interval.getTo(), AnnotatedPoint.PointType.GapEnd));
		});

		Collections.sort(annotatedPoints);

		return ImmutableList.copyOf(annotatedPoints);
	}

	@NonNull
	private static ImmutableList<InstantInterval> removeGaps(@NonNull final List<AnnotatedPoint> annotatedPointList)
	{
		final List<InstantInterval> result = new ArrayList<>();

		// iterate over the annotatedPointList
		boolean isInterval = false;
		boolean isGap = false;
		Instant intervalStart = null;

		for (final AnnotatedPoint point : annotatedPointList)
		{
			switch (point.type)
			{
				case Start:
					if (!isGap)
					{
						intervalStart = point.value;
					}
					isInterval = true;
					break;
				case End:
					if (!isGap)
					{
						//ignore the null warning as we are on the End case branch
						result.add(InstantInterval.of(intervalStart, point.value));
					}
					isInterval = false;
					break;
				case GapStart:
					if (isInterval)
					{
						result.add(InstantInterval.of(intervalStart, point.value));
					}
					isGap = true;
					break;
				case GapEnd:
					if (isInterval)
					{
						intervalStart = point.value;
					}
					isGap = false;
					break;
			}
		}

		return ImmutableList.copyOf(result);
	}

	@Value
	@Builder
	private static class AnnotatedPoint implements Comparable<AnnotatedPoint>
	{
		@NonNull
		Instant value;

		@NonNull
		PointType type;

		public static AnnotatedPoint of(@NonNull final Instant instant, @NonNull final PointType type)
		{
			return AnnotatedPoint.builder()
					.value(instant)
					.type(type)
					.build();
		}

		@Override
		public int compareTo(@NonNull final AnnotatedPoint other)
		{
			if (other.value.compareTo(this.value) == 0)
			{
				return this.type.ordinal() < other.type.ordinal() ? -1 : 1;
			}
			else
			{
				return this.value.compareTo(other.value);
			}
		}

		// the order is important here, as if multiple points have the same value
		// this is the order in which we deal with them
		public enum PointType
		{
			End, GapEnd, GapStart, Start
		}
	}
}
