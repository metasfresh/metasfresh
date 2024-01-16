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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.function.Function;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.RESOURCE_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonWorkOrderResourceUpsertItemRequest
{
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED,
			description = RESOURCE_IDENTIFIER_DOC + "\n"
					+ "Note that `C_Project_WO_Resource.S_Resource_ID` is needed for the calendar view!") //
	@Setter
	String resourceIdentifier;

	LocalDate assignDateFrom;

	@Schema(hidden = true)
	boolean assignDateFromSet;

	LocalDate assignDateTo;

	@Schema(hidden = true)
	boolean assignDateToSet;

	@Schema(description = "If not specified but required (e.g. because a new resource is created), then `true` is assumed")
	Boolean isActive;

	@Schema(hidden = true)
	boolean activeSet;

	Boolean isAllDay;

	@Schema(hidden = true)
	boolean allDaySet;

	String testFacilityGroupName;

	@Schema(hidden = true)
	boolean testFacilityGroupNameSet;

	String externalId;

	@Schema(hidden = true)
	boolean externalIdSet;

	public void setAssignDateFrom(final LocalDate assignDateFrom)
	{
		this.assignDateFrom = assignDateFrom;
		this.assignDateFromSet = true;
	}

	public void setAssignDateTo(final LocalDate assignDateTo)
	{
		this.assignDateTo = assignDateTo;
		this.assignDateToSet = true;
	}
	
	public void setActive(final Boolean active)
	{
		isActive = active;
		this.activeSet = true;
	}

	public void setIsAllDay(final Boolean isAllDay)
	{
		this.isAllDay = isAllDay;
		this.allDaySet = true;
	}

	public void setTestFacilityGroupName(final String testFacilityGroupName)
	{
		this.testFacilityGroupName = testFacilityGroupName;
		this.testFacilityGroupNameSet = true;
	}

	public void setExternalId(final String externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

	@JsonIgnore
	@NonNull
	public <T> T mapResourceIdentifier(@NonNull final Function<String, T> mappingFunction)
	{
		return mappingFunction.apply(resourceIdentifier);
	}
}