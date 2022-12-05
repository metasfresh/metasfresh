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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.STEP_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonWorkOrderStepUpsertItemRequest
{
	@ApiModelProperty(position = 10,
			required = true,
			value = STEP_IDENTIFIER_DOC) //
	@Setter
	String identifier;

	@ApiModelProperty(required = true)
	@Setter
	String name;

	String description;

	@ApiModelProperty(hidden = true)
	boolean descriptionSet;

	Integer seqNo;

	@ApiModelProperty(hidden = true)
	boolean seqNoSet;

	@ApiModelProperty(required = true)
	LocalDate dateStart;

	@ApiModelProperty(hidden = true)
	boolean dateStartSet;

	@ApiModelProperty(required = true)
	LocalDate dateEnd;

	@ApiModelProperty(hidden = true)
	boolean dateEndSet;

	LocalDate woPartialReportDate;

	@ApiModelProperty(hidden = true)
	boolean woPartialReportDateSet;

	private Integer woPlannedResourceDurationHours;

	@ApiModelProperty(hidden = true)
	boolean woPlannedResourceDurationHoursSet;

	LocalDate deliveryDate;

	@ApiModelProperty(hidden = true)
	boolean deliveryDateSet;

	LocalDate woTargetStartDate;

	@ApiModelProperty(hidden = true)
	boolean woTargetStartDateSet;

	LocalDate woTargetEndDate;

	@ApiModelProperty(hidden = true)
	boolean woTargetEndDateSet;

	Integer woPlannedPersonDurationHours;

	@ApiModelProperty(hidden = true)
	boolean woPlannedPersonDurationHoursSet;

	JsonWOStepStatus woStepStatus;

	@ApiModelProperty(hidden = true)
	boolean woStepStatusSet;

	LocalDate woFindingsReleasedDate;

	@ApiModelProperty(hidden = true)
	boolean woFindingsReleasedDateSet;

	LocalDate woFindingsCreatedDate;

	@ApiModelProperty(hidden = true)
	boolean woFindingsCreatedDateSet;

	String externalId;

	@ApiModelProperty(hidden = true)
	boolean externalIdSet;

	@ApiModelProperty("Optional resource allocations that reference to this step")
	List<JsonWorkOrderResourceUpsertItemRequest> resources = ImmutableList.of();

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setSeqNo(final Integer seqNo)
	{
		this.seqNo = seqNo;
		this.seqNoSet = true;
	}

	public void setDateStart(final LocalDate dateStart)
	{
		this.dateStart = dateStart;
		this.dateStartSet = true;
	}

	public void setDateEnd(final LocalDate dateEnd)
	{
		this.dateEnd = dateEnd;
		this.dateEndSet = true;
	}

	public void setWoPartialReportDate(final LocalDate woPartialReportDate)
	{
		this.woPartialReportDate = woPartialReportDate;
		this.woPartialReportDateSet = true;
	}

	public void setWoPlannedResourceDurationHours(final Integer woPlannedResourceDurationHours)
	{
		this.woPlannedResourceDurationHours = woPlannedResourceDurationHours;
		this.woPlannedResourceDurationHoursSet = true;
	}

	public void setDeliveryDate(final LocalDate deliveryDate)
	{
		this.deliveryDate = deliveryDate;
		this.deliveryDateSet = true;
	}

	public void setWoTargetStartDate(final LocalDate woTargetStartDate)
	{
		this.woTargetStartDate = woTargetStartDate;
		this.woTargetStartDateSet = true;
	}

	public void setWoTargetEndDate(final LocalDate woTargetEndDate)
	{
		this.woTargetEndDate = woTargetEndDate;
		this.woTargetEndDateSet = true;
	}

	public void setWoPlannedPersonDurationHours(final Integer woPlannedPersonDurationHours)
	{
		this.woPlannedPersonDurationHours = woPlannedPersonDurationHours;
		this.woPlannedPersonDurationHoursSet = true;
	}

	public void setWoStepStatus(final JsonWOStepStatus woStepStatus)
	{
		this.woStepStatus = woStepStatus;
		this.woStepStatusSet = true;
	}

	public void setWoFindingsReleasedDate(final LocalDate woFindingsReleasedDate)
	{
		this.woFindingsReleasedDate = woFindingsReleasedDate;
		this.woFindingsReleasedDateSet = true;
	}

	public void setWoFindingsCreatedDate(final LocalDate woFindingsCreatedDate)
	{
		this.woFindingsCreatedDate = woFindingsCreatedDate;
		this.woFindingsCreatedDateSet = true;
	}

	public void setExternalId(final String externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

	public void setResources(final List<JsonWorkOrderResourceUpsertItemRequest> resources)
	{
		this.resources = CoalesceUtil.coalesceNotNull(resources, ImmutableList.of());
	}

	@JsonIgnore
	@NonNull
	public <T> T mapStepIdentifier(@NonNull final Function<String, T> mappingFunction)
	{
		return mappingFunction.apply(identifier);
	}
}