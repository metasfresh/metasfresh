/*
 * #%L
 * de-metas-common-changelog
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

package de.metas.common.changelog;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonChangeLogItem
{
	@ApiModelProperty(position = 10)
	String fieldName;

	@ApiModelProperty(position = 20)
	Long updatedMillis;

	@ApiModelProperty(value = "Might be `null` if no `#AD_User_ID` was in the application context while the record was updated", //
			dataType = "java.lang.Integer", position = 30)
	JsonMetasfreshId updatedBy;

	@ApiModelProperty(position = 40)
	String oldValue;

	@ApiModelProperty(position = 50)
	String newValue;

	@Builder
	@JsonCreator
	private JsonChangeLogItem(
			@JsonProperty("fieldName") @NonNull String fieldName,
			@JsonProperty("updatedMillis") @Nullable Long updatedMillis,
			@JsonProperty("updatedBy") @Nullable JsonMetasfreshId updatedBy,
			@JsonProperty("oldValue") @NonNull String oldValue,
			@JsonProperty("newValue") @NonNull String newValue)
	{
		this.fieldName = fieldName;
		this.updatedMillis = updatedMillis;
		this.updatedBy = updatedBy;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
}
