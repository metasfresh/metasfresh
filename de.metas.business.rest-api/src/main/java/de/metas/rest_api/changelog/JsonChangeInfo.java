package de.metas.rest_api.changelog;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.MetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
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

@Value
public class JsonChangeInfo
{
	@ApiModelProperty(position = 10)
	Long createdMillis;

	@ApiModelProperty(value = "Might be empty if no `#AD_User_ID` was in the application context while the record was saved", dataType = "java.lang.Integer", position = 20)
	@JsonInclude(Include.NON_NULL)
	MetasfreshId createdBy;

	@ApiModelProperty(position = 30)
	@JsonInclude(Include.NON_NULL)
	Long lastUpdatedMillis;

	@ApiModelProperty(allowEmptyValue = true, dataType = "java.lang.Integer", position = 30)
	@JsonInclude(Include.NON_NULL)
	MetasfreshId lastUpdatedBy;

	@JsonInclude(Include.NON_EMPTY)
	List<JsonChangeLogItem> changeLogs;

	@Builder
	@JsonCreator
	private JsonChangeInfo(
			@JsonProperty("createdMillis") @NonNull Long createdMillis,
			@JsonProperty("createdBy") @Nullable MetasfreshId createdBy,
			@JsonProperty("lastUpdatedMillis") @Nullable Long lastUpdatedMillis,
			@JsonProperty("lastUpdatedBy") @Nullable MetasfreshId lastUpdatedBy,
			@JsonProperty("changeLog") @Singular List<JsonChangeLogItem> changeLogs)
	{
		this.createdMillis = createdMillis;
		this.createdBy = createdBy;
		this.lastUpdatedMillis = lastUpdatedMillis;
		this.lastUpdatedBy = lastUpdatedBy;
		this.changeLogs = changeLogs;
	}
}
