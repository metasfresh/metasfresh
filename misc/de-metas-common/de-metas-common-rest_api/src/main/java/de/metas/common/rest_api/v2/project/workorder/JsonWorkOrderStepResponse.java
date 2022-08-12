/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.rest_api.v2.project.workorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonWorkOrderStepResponse
{
	@NonNull
	@JsonProperty("stepId")
	JsonMetasfreshId stepId;

	@NonNull
	@JsonProperty("name")
	String name;

	@NonNull
	@JsonProperty("projectId")
	JsonMetasfreshId projectId;

	@Nullable
	@JsonProperty("description")
	String description;

	@NonNull
	@JsonProperty("seqNo")
	Integer seqNo;

	@Nullable
	@JsonProperty("dateStart")
	String dateStart;

	@Nullable
	@JsonProperty("dateEnd")
	String dateEnd;

	@Nullable
	@JsonProperty("externalId")
	String externalId;

	@Nullable
	@JsonProperty("resources")
	List<JsonWorkOrderResourceResponse> resources;
	
	@JsonCreator
	@Builder
	public JsonWorkOrderStepResponse(
			@NonNull @JsonProperty("stepId") final JsonMetasfreshId stepId,
			@NonNull @JsonProperty("name") final String name,
			@NonNull @JsonProperty("projectId") final JsonMetasfreshId projectId,
			@Nullable @JsonProperty("description") final String description,
			@NonNull @JsonProperty("seqNo") final Integer seqNo,
			@Nullable @JsonProperty("dateStart") final String dateStart,
			@Nullable @JsonProperty("dateEnd") final String dateEnd,
			@Nullable @JsonProperty("externalId") final String externalId,
			@Singular @JsonProperty("resources") final List<JsonWorkOrderResourceResponse> resources
	)
	{
		this.stepId = stepId;
		this.name = name;
		this.projectId = projectId;
		this.description = description;
		this.seqNo = seqNo;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.externalId = externalId;
		this.resources = resources;
	}
}
