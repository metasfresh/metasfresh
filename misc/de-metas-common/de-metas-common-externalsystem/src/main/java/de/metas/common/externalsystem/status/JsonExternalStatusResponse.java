/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@JsonDeserialize(builder = JsonExternalStatusResponse.JsonExternalStatusResponseBuilder.class)
public class JsonExternalStatusResponse
{
	@NonNull
	@JsonProperty("statusInfoList")
	List<JsonExternalStatusResponseItem> externalStatusResponses;

	@Builder
	public JsonExternalStatusResponse(@JsonProperty("statusInfoList") @NonNull final List<JsonExternalStatusResponseItem> externalStatusResponses)
	{
		this.externalStatusResponses = externalStatusResponses;
	}
}
