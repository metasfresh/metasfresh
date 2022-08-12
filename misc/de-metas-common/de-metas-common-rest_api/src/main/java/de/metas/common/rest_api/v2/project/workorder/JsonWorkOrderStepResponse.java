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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("dateStart")
	LocalDate dateStart;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("dateEnd")
	LocalDate dateEnd;

	@Nullable
	@JsonProperty("externalId")
	JsonMetasfreshId externalId;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("woPartialReportDate")
	LocalDate woPartialReportDate;

	@Nullable
	@JsonProperty("woPlannedResourceDurationHours")
	Integer woPlannedResourceDurationHours;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("deliveryDate")
	LocalDate deliveryDate;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("woTargetStartDate")
	LocalDate woTargetStartDate;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("woTargetEndDate")
	LocalDate woTargetEndDate;

	@Nullable
	@JsonProperty("woPlannedPersonDurationHours")
	Integer woPlannedPersonDurationHours;

	@Nullable
	@JsonProperty("woStepStatus")
	JsonWOStepStatus woStepStatus;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("woFindingsReleasedDate")
	LocalDate woFindingsReleasedDate;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("woFindingsCreatedDate")
	LocalDate woFindingsCreatedDate;

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
			@NonNull @JsonProperty("seqNo") final Integer seqNo,
			@Nullable @JsonProperty("description") final String description,
			@Nullable @JsonProperty("dateStart") final LocalDate dateStart,
			@Nullable @JsonProperty("dateEnd") final LocalDate dateEnd,
			@Nullable @JsonProperty("externalId") final JsonMetasfreshId externalId,
			@Nullable @JsonProperty("woPartialReportDate") final LocalDate woPartialReportDate,
			@Nullable @JsonProperty("woPlannedResourceDurationHours") final Integer woPlannedResourceDurationHours,
			@Nullable @JsonProperty("deliveryDate") final LocalDate deliveryDate,
			@Nullable @JsonProperty("woTargetStartDate") final LocalDate woTargetStartDate,
			@Nullable @JsonProperty("woTargetEndDate") final LocalDate woTargetEndDate,
			@Nullable @JsonProperty("woPlannedPersonDurationHours") final Integer woPlannedPersonDurationHours,
			@Nullable @JsonProperty("woStepStatus") final JsonWOStepStatus woStepStatus,
			@Nullable @JsonProperty("woFindingsReleasedDate") final LocalDate woFindingsReleasedDate,
			@Nullable @JsonProperty("woFindingsCreatedDate") final LocalDate woFindingsCreatedDate,
			@Nullable @JsonProperty("resources") final List<JsonWorkOrderResourceResponse> resources
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
		this.woPartialReportDate = woPartialReportDate;
		this.woPlannedResourceDurationHours = woPlannedResourceDurationHours;
		this.deliveryDate = deliveryDate;
		this.woTargetStartDate = woTargetStartDate;
		this.woTargetEndDate = woTargetEndDate;
		this.woPlannedPersonDurationHours = woPlannedPersonDurationHours;
		this.woStepStatus = woStepStatus;
		this.woFindingsReleasedDate = woFindingsReleasedDate;
		this.woFindingsCreatedDate = woFindingsCreatedDate;
		this.resources = resources;
	}
}
