/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.v2.tablerecordref.JsonTableRecordReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Schema(description = "Logs")
@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonPInstanceLog.JsonPInstanceLogBuilder.class)
public class JsonPInstanceLog
{
	@NonNull
	@JsonProperty("message")
	String message;

	@Nullable
	@JsonProperty("tableRecordRef")
	JsonTableRecordReference tableRecordReference;

	@JsonCreator
	@Builder
	private JsonPInstanceLog(
			@JsonProperty("message") @NonNull final String message,
			@JsonProperty("tableRecordRef") @Nullable final JsonTableRecordReference tableRecordRef)
	{
		this.message = message;
		this.tableRecordReference = tableRecordRef;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonPInstanceLogBuilder
	{
	}
}

