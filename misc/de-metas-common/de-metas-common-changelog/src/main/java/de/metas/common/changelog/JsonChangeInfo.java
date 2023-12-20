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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonChangeInfo
{
	@Schema
	Long createdMillis;

	@Schema(description = "Might be `null` if no `#AD_User_ID` was in the application context while the record was created")
	JsonMetasfreshId createdBy;

	@Schema
	Long lastUpdatedMillis;

	@Schema(description = "Might be `null` if no `#AD_User_ID` was in the application context while the record was updated",
			nullable = true)
	JsonMetasfreshId lastUpdatedBy;

	@JsonInclude(Include.NON_EMPTY)
	List<JsonChangeLogItem> changeLogs;

	@Builder
	@JsonCreator
	private JsonChangeInfo(
			@JsonProperty("createdMillis") @NonNull Long createdMillis,
			@JsonProperty("createdBy") @Nullable JsonMetasfreshId createdBy,
			@JsonProperty("lastUpdatedMillis") @Nullable Long lastUpdatedMillis,
			@JsonProperty("lastUpdatedBy") @Nullable JsonMetasfreshId lastUpdatedBy,
			@JsonProperty("changeLog") @Singular List<JsonChangeLogItem> changeLogs)
	{
		this.createdMillis = createdMillis;
		this.createdBy = createdBy;
		this.lastUpdatedMillis = lastUpdatedMillis;
		this.lastUpdatedBy = lastUpdatedBy;
		this.changeLogs = changeLogs;
	}
}
