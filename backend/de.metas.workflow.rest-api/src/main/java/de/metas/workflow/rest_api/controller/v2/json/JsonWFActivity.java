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
import com.google.common.collect.ImmutableMap;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
public class JsonWFActivity
{
	@NonNull String activityId;
	@NonNull String caption;
	@NonNull String componentType;

	@NonNull WFActivityStatus status;

	@Nullable Boolean isAlwaysAvailableToUser;
	@Nullable String userInstructions;

	@Builder.Default
	@NonNull Map<String, Object> componentProps = ImmutableMap.of();

	static JsonWFActivity of(
			@NonNull final WFActivity activity,
			@NonNull final UIComponent uiComponent,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return builder()
				.activityId(activity.getId().getAsString())
				.caption(activity.getCaption().translate(adLanguage))
				.componentType(uiComponent.getType().getAsString())
				.status(activity.getStatus())
				.isAlwaysAvailableToUser(uiComponent.getAlwaysAvailableToUser().toBooleanObject())
				.userInstructions(activity.getUserInstructions())
				.componentProps(uiComponent.getProperties().toJson(jsonOpts::convertValueToJson))
				.build();
	}

}
