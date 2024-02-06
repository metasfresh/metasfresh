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

package de.metas.common.rest_api.v2.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Value
@Builder
@JsonDeserialize(builder = JsonExternalReferenceTarget.JsonExternalReferenceTargetBuilder.class)
public class JsonExternalReferenceTarget
{
	@Schema(requiredMode= REQUIRED)
	@NonNull
	@JsonProperty("externalReferenceType")
	String externalReferenceType;

	@Schema(requiredMode= REQUIRED)
	@NonNull
	@JsonProperty("externalReferenceIdentifier")
	String externalReferenceIdentifier;

	@NonNull
	public static JsonExternalReferenceTarget ofTypeAndId(@NonNull final String externalReferenceType, @NonNull final String externalReferenceIdentifier)
	{
		return new JsonExternalReferenceTarget(externalReferenceType, externalReferenceIdentifier);
	}
}
