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
public class JsonWorkOrderResourceUpsertRequest
{
	@ApiModelProperty(position = 10,
			value = RESOURCE_IDENTIFIER_DOC + "\n"
					+ "Note that `C_Project_WO_Resource.S_Reource_ID` is currently not mandatory!") //
	String resourceIdentifier;

	@ApiModelProperty(hidden = true)
	boolean resourceIdentifierSet;

	@ApiModelProperty(required = true)
	@Setter
	LocalDate assignDateFrom;

	@ApiModelProperty(required = true)
	@Setter
	LocalDate assignDateTo;

	@ApiModelProperty(value = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	Boolean isActive;

	@ApiModelProperty(hidden = true)
	boolean activeSet;

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

	public void setResourceIdentifier(final String resourceIdentifier)
	{
		this.resourceIdentifier = resourceIdentifier;
		this.resourceIdentifierSet = true;
	}

	public void setActive(final Boolean active)
	{
		isActive = active;
		this.activeSet = true;
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