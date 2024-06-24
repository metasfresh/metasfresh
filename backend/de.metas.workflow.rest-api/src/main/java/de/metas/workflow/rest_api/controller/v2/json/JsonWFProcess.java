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

package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
public class JsonWFProcess
{
	@NonNull String id;

	@NonNull JsonWFProcessHeaderProperties headerProperties;

	@NonNull List<JsonWFActivity> activities;

	boolean isAllowAbort;

	public static JsonWFProcess of(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFProcessHeaderProperties headerProperties,
			@NonNull final ImmutableMap<WFActivityId, UIComponent> uiComponents,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.id(wfProcess.getId().getAsString())
				.headerProperties(JsonWFProcessHeaderProperties.of(headerProperties, jsonOpts))
				.activities(wfProcess.getActivities()
						.stream()
						.map(activity -> JsonWFActivity.of(
								activity,
								uiComponents.get(activity.getId()),
								jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.isAllowAbort(wfProcess.isAllowAbort())
				.build();
	}

	@JsonIgnore
	public JsonWFActivity getActivityById(@NonNull final String activityId)
	{
		return activities.stream().filter(activity -> activity.getActivityId().equals(activityId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No activity found for id `" + activityId + "` in " + this));
	}
}
