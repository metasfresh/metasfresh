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
public class JsonWorkOrderStepRequest
{
	@Getter
	JsonMetasfreshId stepId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean stepIdSet;

	@ApiModelProperty(required = true)
	String name;

	@Getter
	String description;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean descriptionSet;

	@Getter
	Integer seqNo;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean seqNoSet;

	@Getter
	LocalDate dateStart;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean dateStartSet;

	@Getter
	LocalDate dateEnd;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean dateEndSet;

	@ApiModelProperty(required = true)
	SyncAdvise syncAdvise;

	public void setStepId(final JsonMetasfreshId stepId)
	{
		this.stepId = stepId;
		this.stepIdSet = true;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

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

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
	}

	@NonNull
	public String getName()
	{
		return name;
	}

	@NonNull
	public SyncAdvise getSyncAdvise()
	{
		return syncAdvise;
	}
}