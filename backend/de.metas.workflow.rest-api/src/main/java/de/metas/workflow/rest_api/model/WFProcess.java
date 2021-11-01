/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.i18n.ITranslatableString;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Objects;
import java.util.function.UnaryOperator;

public final class WFProcess
{
	@Getter
	@NonNull private final WFProcessId id;

	@Getter
	@NonNull private final UserId invokerId;

	@Getter
	@NonNull private final ITranslatableString caption;

	@NonNull private final WFProcessStatus status;

	@NonNull private final Object document;

	@Getter
	@NonNull private final ImmutableList<WFActivity> activities;
	@NonNull private final ImmutableMap<WFActivityId, WFActivity> activitiesById;

	@Builder(toBuilder = true)
	private WFProcess(
			@NonNull final WFProcessId id,
			@NonNull final UserId invokerId,
			@NonNull final ITranslatableString caption,
			@NonNull final Object document,
			@NonNull final ImmutableList<WFActivity> activities)
	{
		this.id = id;
		this.invokerId = invokerId;
		this.caption = caption;
		this.document = document;
		this.activities = activities;

		this.activitiesById = Maps.uniqueIndex(this.activities, WFActivity::getId);
		this.status = computeStatusFromActivities(this.activities);
	}

	private static WFProcessStatus computeStatusFromActivities(@NonNull final ImmutableList<WFActivity> activities)
	{
		final ImmutableSet<WFActivityStatus> activityStatuses = activities
				.stream()
				.map(WFActivity::getStatus)
				.collect(ImmutableSet.toImmutableSet());

		if (activityStatuses.isEmpty())
		{
			// shall never happen
			return WFProcessStatus.COMPLETED;
		}
		else if (activityStatuses.size() == 1)
		{
			final WFActivityStatus activityStatus = activityStatuses.iterator().next();
			switch (activityStatus)
			{
				case NOT_STARTED:
					return WFProcessStatus.NOT_STARTED;
				case IN_PROGRESS:
					return WFProcessStatus.IN_PROGRESS;
				case COMPLETED:
					return WFProcessStatus.COMPLETED;
				default:
					throw new AdempiereException("Unknown activity status: " + activityStatus);
			}
		}
		else
		{
			return WFProcessStatus.IN_PROGRESS;
		}
	}

	public WFProcess assertHasAccess(@NonNull final UserId userId)
	{
		if (!hasAccess(userId))
		{
			throw new AdempiereException("User does not have access");
		}

		return this;
	}

	public boolean hasAccess(@NonNull final UserId userId)
	{
		return UserId.equals(getInvokerId(), userId);
	}

	public <T> T getDocumentAs(@NonNull final Class<T> type)
	{
		return type.cast(document);
	}

	public <T> WFProcess mapDocument(@NonNull final UnaryOperator<T> remappingFunction)
	{
		//noinspection unchecked
		final T document = (T)this.document;
		final T documentNew = remappingFunction.apply(document);
		return !Objects.equals(document, documentNew)
				? toBuilder().document(documentNew).build()
				: this;
	}

	public WFActivity getActivityById(@NonNull final WFActivityId id)
	{
		final WFActivity wfActivity = activitiesById.get(id);
		if (wfActivity == null)
		{
			throw new AdempiereException("No activity found for " + id + " in " + this);
		}
		return wfActivity;
	}

	public WFProcess withChangedActivityStatus(
			@NonNull final WFActivityId wfActivityId,
			@NonNull final WFActivityStatus newActivityStatus)
	{
		return withChangedActivityById(wfActivityId, wfActivity -> wfActivity.withStatus(newActivityStatus));
	}

	private WFProcess withChangedActivityById(@NonNull final WFActivityId wfActivityId, @NonNull final UnaryOperator<WFActivity> remappingFunction)
	{
		return withChangedActivities(wfActivity -> wfActivity.getId().equals(wfActivityId)
				? remappingFunction.apply(wfActivity)
				: wfActivity);
	}

	private WFProcess withChangedActivities(@NonNull final UnaryOperator<WFActivity> remappingFunction)
	{
		final ImmutableList<WFActivity> activitiesNew = CollectionUtils.map(this.activities, remappingFunction);
		return !Objects.equals(this.activities, activitiesNew)
				? toBuilder().activities(activitiesNew).build()
				: this;
	}
}
