/*
 * #%L
 * de-metas-common-externalreference
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

package de.metas.common.externalreference.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.externalsystem.JsonExternalSystemName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonSingleExternalReferenceCreateReq
{
	@Schema(required = true, description = "Name of the external system (GitHub, Everhour etc) to which the referenced external resource belongs.")
	JsonExternalSystemName systemName;

	JsonExternalReferenceRequestItem externalReferenceItem;

	@JsonCreator
	@Builder
	public JsonSingleExternalReferenceCreateReq(
			@JsonProperty("systemName") @NonNull final JsonExternalSystemName systemName,
			@JsonProperty("externalReferenceItem") @NonNull final JsonExternalReferenceRequestItem externalReferenceItem)
	{
		this.systemName = systemName;
		this.externalReferenceItem = externalReferenceItem;
	}
}
