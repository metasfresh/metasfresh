/*
 * #%L
 * de-metas-common-externalreference
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

package de.metas.common.externalreference;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.externalsystem.JsonExternalSystemName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class JsonExternalReferenceCreateRequest
{
	@ApiModelProperty(required = true, value = "Name of the external system (GitHub, Everhour etc) to which the referenced external resource belongs.")
	JsonExternalSystemName systemName;

	List<JsonExternalReferenceItem> items;

	@JsonCreator
	@Builder
	public JsonExternalReferenceCreateRequest(
			@JsonProperty("systemName") @NonNull final JsonExternalSystemName systemName,
			@JsonProperty("items") @NonNull @Singular final List<JsonExternalReferenceItem> items)
	{
		this.systemName = systemName;
		this.items = items;
	}
}
