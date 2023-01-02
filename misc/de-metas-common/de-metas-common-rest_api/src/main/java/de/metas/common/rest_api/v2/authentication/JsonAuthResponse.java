/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.common.rest_api.v2.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.v2.i18n.JsonMessages;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@JsonDeserialize(builder = JsonAuthResponse.JsonAuthResponseBuilder.class)
public class JsonAuthResponse
{
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonAuthResponseBuilder {}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String token;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String language;

 	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Integer userId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String error;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Map<String, Object> messages;

	public static JsonAuthResponse.JsonAuthResponseBuilder ok(@NonNull final String token)
	{
		return builder().token(token);
	}

	public static JsonAuthResponse error(@NonNull final String error)
	{
		return builder().error(error).build();
	}
}
