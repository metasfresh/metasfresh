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

package de.metas.common.externalsystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@Value
@JsonDeserialize(builder = JsonExternalSystemInfo.JsonExternalSystemInfoBuilder.class)
public class JsonExternalSystemInfo
{
	@NonNull
	@JsonProperty("externalSystemConfigId")
	JsonMetasfreshId externalSystemConfigId;

	@NonNull
	@JsonProperty("orgCode")
	String orgCode;

	@NonNull
	@JsonProperty("externalSystemChildConfigValue")
	String externalSystemChildConfigValue;

	@NonNull
	@JsonProperty("externalSystemName")
	JsonExternalSystemName externalSystemName;

	@NonNull
	@JsonProperty("parameters")
	Map<String, String> parameters;

	@Builder
	public JsonExternalSystemInfo(
			@JsonProperty("externalSystemConfigId") final @NonNull JsonMetasfreshId externalSystemConfigId,
			@JsonProperty("externalSystemName") final @NonNull JsonExternalSystemName externalSystemName,
			@JsonProperty("orgCode") final @NonNull String orgCode,
			@JsonProperty("externalSystemChildConfigValue") final @NonNull String externalSystemChildConfigValue,
			@JsonProperty("parameters") final @NonNull Map<String, String> parameters)
	{
		this.externalSystemConfigId = externalSystemConfigId;
		this.externalSystemName = externalSystemName;
		this.orgCode = orgCode;
		this.externalSystemChildConfigValue = externalSystemChildConfigValue;
		this.parameters = parameters;
	}
}
