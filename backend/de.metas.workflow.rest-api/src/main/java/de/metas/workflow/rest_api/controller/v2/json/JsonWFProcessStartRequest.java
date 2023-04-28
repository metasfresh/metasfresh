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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Map;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = JsonWFProcessStartRequest.JsonWFProcessStartRequestBuilder.class)
@Value
@Builder
public class JsonWFProcessStartRequest
{
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonWFProcessStartRequestBuilder {}

	public static final String PARAM_ApplicationId = "applicationId";

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@NonNull Map<String, Object> wfParameters;

	public MobileApplicationId getApplicationId()
	{
		final Object idObj = wfParameters.get(PARAM_ApplicationId);
		if (idObj == null)
		{
			throw new AdempiereException("No `" + PARAM_ApplicationId + "` found in " + this);
		}
		return MobileApplicationId.ofString(idObj.toString());
	}
}
