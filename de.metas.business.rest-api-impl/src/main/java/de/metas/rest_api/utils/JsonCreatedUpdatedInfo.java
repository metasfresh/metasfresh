package de.metas.rest_api.utils;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.user.UserId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ApiModel(description = "Entity Created/Updated info")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonCreatedUpdatedInfo
{
	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.Integer", //
			value = "This translates to `AD_User_ID`.")
	UserId createdBy;

	ZonedDateTime created;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.Integer", //
			value = "This translates to `AD_User_ID`.")
	UserId updatedBy;

	ZonedDateTime updated;

	@Builder
	@JsonCreator
	private JsonCreatedUpdatedInfo(
			@NonNull @JsonProperty("createdBy") final UserId createdBy,
			@NonNull @JsonProperty("created") final ZonedDateTime created,
			@NonNull @JsonProperty("updatedBy") final UserId updatedBy,
			@NonNull @JsonProperty("updated") final ZonedDateTime updated)
	{
		this.createdBy = createdBy;
		this.created = created;
		this.updatedBy = updatedBy;
		this.updated = updated;
	}
}
