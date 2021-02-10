/*
 * #%L
 * de.metas.business.rest-api
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

package de.metas.rest_api.bpartner.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
@ApiModel
public class JsonRequestBPRelationsUpsert
{

	@ApiModelProperty(position = 10)
	String orgCode;

	@ApiModelProperty(position = 20)
	String locationIdentifier;

	@ApiModelProperty(position = 30)
	List<JsonRequestBPRelationTarget> relatesTo;

	@JsonCreator
	@Builder(toBuilder = true)
	public JsonRequestBPRelationsUpsert(
			@NonNull @JsonProperty("orgCode") final String orgCode,
			@Nullable @JsonProperty("locationIdentifier") final String locationIdentifier,
			@JsonProperty("relatesTo") final List<JsonRequestBPRelationTarget> relatesTo)
	{
		this.orgCode = orgCode;
		this.locationIdentifier = locationIdentifier;
		this.relatesTo = coalesce(relatesTo, ImmutableList.of());
	}

}
