/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.workflow.rest_api.model.CustomApplicationParameter;
import de.metas.workflow.rest_api.model.WorkplaceSettings;
import de.metas.workplace.Workplace;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class JsonApplicationParametersMapper
{
	@Nullable
	public ImmutableMap<CustomApplicationParameter, Object> map(@Nullable final Map<CustomApplicationParameter, Object> applicationParameters)
	{
		if (applicationParameters == null)
		{
			return null;
		}

		return applicationParameters.entrySet()
				.stream()
				.filter(entry -> entry.getValue() != null)
				.map(entry -> map(entry.getKey(), entry.getValue()))
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@NonNull
	private Map.Entry<CustomApplicationParameter, Object> map(@NonNull final CustomApplicationParameter parameterType, @NonNull final Object parameterValue)
	{
		switch (parameterType)
		{
			case WORKPLACE_SETTINGS:
				return new HashMap.SimpleEntry<>(parameterType, map((WorkplaceSettings)parameterValue));
			default:
				return new HashMap.SimpleEntry<>(parameterType, parameterValue);
		}
	}

	private JsonWorkplaceSettings map(@NonNull final WorkplaceSettings settings)
	{
		return JsonWorkplaceSettings.builder()
				.isWorkplaceRequired(settings.isWorkplaceAssignmentRequired())
				.assignedWorkplace(map(settings.getAssignedWorkplace()))
				.build();
	}

	@Nullable
	private JsonWorkplace map(@Nullable final Workplace workplace)
	{
		if (workplace == null)
		{
			return null;
		}

		return JsonWorkplace.builder()
				.name(workplace.getName())
				.id(JsonMetasfreshId.of(workplace.getId().getRepoId()))
				.build();
	}
}
