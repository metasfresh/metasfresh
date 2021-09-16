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
import de.metas.workflow.WFState;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;

public final class WFProcess
{
	@Getter
	@NonNull private final WFProcessId id;

	@Getter
	@NonNull private final UserId invokerId;

	@Getter
	@NonNull private final ITranslatableString caption;

	@NonNull private WFState status = WFState.NotStarted;

	@NonNull private final WFProcessDocumentHolder documentHolder;

	@Getter
	@NonNull private final ImmutableList<WFActivity> activitiesInOrder;
	@NonNull private final ImmutableMap<WFActivityId, WFActivity> activitiesById;

	@Builder
	private WFProcess(
			@NonNull final WFProcessId id,
			@NonNull final UserId invokerId,
			@NonNull final ITranslatableString caption,
			@NonNull final WFProcessDocumentHolder documentHolder,
			@NonNull @Singular final ImmutableList<WFActivity> activities)
	{
		this.id = id;
		this.invokerId = invokerId;
		this.caption = caption;
		this.documentHolder = documentHolder;
		this.activitiesInOrder = activities;
		this.activitiesById = Maps.uniqueIndex(activities, WFActivity::getId);
	}

	public void assertHasAccess(@NonNull final UserId userId)
	{
		if (!UserId.equals(getInvokerId(), userId))
		{
			throw new AdempiereException("User does not have access");
		}
	}

	public <T> T getDocumentAs(@NonNull final Class<T> type)
	{
		return documentHolder.getDocumentAs(type);
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

	public synchronized void setActivityStatus(@NonNull final WFActivityId wfActivityId, @NonNull final WFState newActivityStatus)
	{
		final WFActivity wfActivity = getActivityById(wfActivityId);
		final WFState oldActivityStatus = wfActivity.getStatus();
		if (!newActivityStatus.equals(oldActivityStatus))
		{
			wfActivity._setStatus(newActivityStatus);
			updateStatusFromActivities();
		}
	}

	public synchronized void updateStatusFromActivities()
	{
		this.status = computeStatusFromActivities();
	}

	private WFState computeStatusFromActivities()
	{
		final ImmutableSet<WFState> activityStatuses = getActivitiesInOrder()
				.stream()
				.map(WFActivity::getStatus)
				.collect(ImmutableSet.toImmutableSet());

		if (activityStatuses.isEmpty())
		{
			// shall never happen
			return WFState.Completed;
		}
		else if (activityStatuses.size() == 1)
		{
			return activityStatuses.iterator().next();
		}
		else
		{
			return WFState.Suspended;
		}
	}

}
