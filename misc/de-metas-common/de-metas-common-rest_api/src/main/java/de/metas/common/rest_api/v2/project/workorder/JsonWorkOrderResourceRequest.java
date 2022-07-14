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

import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.RESOURCE_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonWorkOrderResourceRequest
{
	@ApiModelProperty(position = 10,
			required = true,
			value = RESOURCE_IDENTIFIER_DOC) //
	@Setter
	String resourceIdentifier;

	@ApiModelProperty(required = true)
	String orgCode;

	@ApiModelProperty(required = true)
	LocalDate assignDateFrom;

	@ApiModelProperty(required = true)
	LocalDate assignDateTo;
	
	@ApiModelProperty(value = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	Boolean isActive;

	@ApiModelProperty(hidden = true)
	boolean activeSet;

	JsonMetasfreshId resourceId;
	@ApiModelProperty(hidden = true)
	boolean resourceIdSet;

	Boolean isAllDay;
	@ApiModelProperty(hidden = true)
	boolean allDaySet;

	BigDecimal duration;
	@ApiModelProperty(hidden = true)
	boolean durationSet;

	// TODO: turn into enum
	String durationUnit;
	@ApiModelProperty(hidden = true)
	boolean durationUnitSet;

	String testFacilityGroupName;
	@ApiModelProperty(hidden = true)
	boolean testFacilityGroupNameSet;

	@Setter
	@ApiModelProperty(required = true)
	JsonExternalId externalId;
	
	@Setter
	@ApiModelProperty(required = true)
	SyncAdvise syncAdvise;

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
	}

	public void setAssignDateFrom(final LocalDate assignDateFrom)
	{
		this.assignDateFrom = assignDateFrom;
	}

	public void setAssignDateTo(final LocalDate assignDateTo)
	{
		this.assignDateTo = assignDateTo;
	}

	public void setActive(final Boolean active)
	{
		isActive = active;
		this.activeSet = true;
	}

	public void setResourceId(final JsonMetasfreshId resourceId)
	{
		this.resourceId = resourceId;
		this.resourceIdSet = true;
	}

	public void setAllDay(final Boolean allDay)
	{
		this.isAllDay = allDay;
		this.allDaySet = true;
	}

	public void setDuration(final BigDecimal duration)
	{
		this.duration = duration;
		this.durationSet = true;
	}

	public void setDurationUnit(final String durationUnit)
	{
		this.durationUnit = durationUnit;
		this.durationUnitSet = true;
	}

	public void setTestFacilityGroupName(final String testFacilityGroupName)
	{
		this.testFacilityGroupName = testFacilityGroupName;
		this.testFacilityGroupNameSet = true;
	}
}