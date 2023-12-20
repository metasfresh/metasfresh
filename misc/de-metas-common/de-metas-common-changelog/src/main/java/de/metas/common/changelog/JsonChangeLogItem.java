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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonChangeLogItem
{
	@Schema
	String fieldName;

	@Schema
	Long updatedMillis;

	@Schema(description = "Might be `null` if no `#AD_User_ID` was in the application context while the record was updated")
	JsonMetasfreshId updatedBy;

	@Schema
	String oldValue;

	@Schema
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
