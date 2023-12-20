/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonResponseContactRole
{
	public static final String NAME = "name";
	public static final String UNIQUE_PER_BPARTNER = "uniquePerBPartner";

	@Schema(required = true)
	String name;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isUniquePerBpartner;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonResponseContactRole(
			@JsonProperty(NAME) @NonNull final String name,
			@JsonProperty(UNIQUE_PER_BPARTNER) final boolean isUniquePerBpartner)
	{
		this.name = name;
		this.isUniquePerBpartner = isUniquePerBpartner ? true : null;
	}
}
