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

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
public class JsonWorkOrderResourceRequest
{
	@Getter
	JsonMetasfreshId woResourceId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean woResourceIdSet;

	@ApiModelProperty(required = true)
	String orgCode;

	@ApiModelProperty(required = true)
	JsonMetasfreshId stepId;

	@ApiModelProperty(required = true)
	LocalDate assignDateFrom;

	@ApiModelProperty(required = true)
	LocalDate assignDateTo;

	@Getter
	Boolean isActive;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean activeSet;

	@Getter
	JsonMetasfreshId resourceId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean resourceIdSet;

	@Getter
	Boolean isAllDay;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean allDaySet;

	@ApiModelProperty(required = true)
	SyncAdvise syncAdvise;

	public void setWoResourceId(final JsonMetasfreshId woResourceId)
	{
		this.woResourceId = woResourceId;
		this.woResourceIdSet = true;
	}

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
	}

	public void setStepId(final JsonMetasfreshId stepId)
	{
		this.stepId = stepId;
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
		isAllDay = allDay;
		this.allDaySet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
	}

	@NonNull
	public String getOrgCode()
	{
		return orgCode;
	}

	@NonNull
	public JsonMetasfreshId getStepId()
	{
		return stepId;
	}

	@NonNull
	public LocalDate getAssignDateFrom()
	{
		return assignDateFrom;
	}

	@NonNull
	public LocalDate getAssignDateTo()
	{
		return assignDateTo;
	}

	@NonNull
	public SyncAdvise getSyncAdvise()
	{
		return syncAdvise;
	}
}