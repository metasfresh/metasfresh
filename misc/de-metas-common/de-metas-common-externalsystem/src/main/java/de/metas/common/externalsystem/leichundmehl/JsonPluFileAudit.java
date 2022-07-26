/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem.leichundmehl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JsonPluFileAudit.JsonPluFileAuditBuilder.class)
public class JsonPluFileAudit
{
	@NonNull
	@JsonProperty("fileName")
	String fileName;

	@NonNull
	@JsonProperty("missingKeys")
	List<String> missingKeys;

	@NonNull
	@JsonProperty("processedKeys")
	List<JsonProcessedKeys> processedKeys;

	@Builder
	@JsonCreator
	public JsonPluFileAudit(
			@JsonProperty("fileName") @NonNull final String fileName,
			@JsonProperty("missingKeys") @NonNull @Singular final List<String> missingKeys,
			@JsonProperty("processedKeys") @NonNull @Singular final List<JsonProcessedKeys> processedKeys)
	{
		this.fileName = fileName;
		this.missingKeys = missingKeys;
		this.processedKeys = processedKeys;
	}
}
