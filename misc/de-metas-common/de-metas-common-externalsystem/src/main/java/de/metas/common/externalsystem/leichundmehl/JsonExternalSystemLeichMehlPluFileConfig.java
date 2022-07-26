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
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JsonExternalSystemLeichMehlPluFileConfig.JsonExternalSystemLeichMehlPluFileConfigBuilder.class)
public class JsonExternalSystemLeichMehlPluFileConfig
{
	@NonNull
	@JsonProperty("targetFieldName")
	String targetFieldName;

	@NonNull
	@JsonProperty("targetFieldType")
	JsonTargetFieldType targetFieldType;

	@NonNull
	@JsonProperty("replacePattern")
	String replacePattern;

	@NonNull
	@JsonProperty("replacement")
	String replacement;

	@NonNull
	@JsonProperty("replacementSource")
	JsonReplacementSource replacementSource;

	@Builder
	@JsonCreator
	public JsonExternalSystemLeichMehlPluFileConfig(
			@JsonProperty("targetFieldName") @NonNull final String targetFieldName,
			@JsonProperty("targetFieldType") @NonNull final JsonTargetFieldType targetFieldType,
			@JsonProperty("replacePattern") @NonNull final String replacePattern,
			@JsonProperty("replacement") @NonNull final String replacement,
			@JsonProperty("replacementSource") @NonNull final JsonReplacementSource replacementSource)
	{
		this.targetFieldName = targetFieldName;
		this.targetFieldType = targetFieldType;
		this.replacePattern = replacePattern;
		this.replacement = replacement;
		this.replacementSource = replacementSource;
	}
}
