/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.project.workorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonWorkOrderObjectsUnderTestResponse
{
	@NonNull
	@JsonProperty("objectUnderTestId")
	JsonMetasfreshId objectUnderTestId;

	@NonNull
	@JsonProperty("numberOfObjectsUnderTest")
	Integer numberOfObjectsUnderTest;

	@NonNull
	@JsonProperty("externalId")
	JsonMetasfreshId externalId;

	@Nullable
	@JsonProperty("woDeliveryNote")
	String woDeliveryNote;

	@Nullable
	@JsonProperty("woManufacturer")
	String woManufacturer;

	@Nullable
	@JsonProperty("woObjectType")
	String woObjectType;

	@Nullable
	@JsonProperty("woObjectName")
	String woObjectName;

	@Nullable
	@JsonProperty("woObjectWhereabouts")
	String woObjectWhereabouts;

	@Builder
	@JsonCreator
	public JsonWorkOrderObjectsUnderTestResponse(
			@NonNull @JsonProperty("objectUnderTestId") final JsonMetasfreshId objectUnderTestId,
			@NonNull @JsonProperty("numberOfObjectsUnderTest")final Integer numberOfObjectsUnderTest,
			@NonNull @JsonProperty("externalId") final JsonMetasfreshId externalId,
			@Nullable @JsonProperty("woDeliveryNote") final String woDeliveryNote,
			@Nullable @JsonProperty("woManufacturer") final String woManufacturer,
			@Nullable @JsonProperty("woObjectType") final String woObjectType,
			@Nullable @JsonProperty("woObjectName") final String woObjectName,
			@Nullable @JsonProperty("woObjectWhereabouts") final String woObjectWhereabouts)
	{
		this.objectUnderTestId = objectUnderTestId;
		this.numberOfObjectsUnderTest = numberOfObjectsUnderTest;
		this.externalId = externalId;
		this.woDeliveryNote = woDeliveryNote;
		this.woManufacturer = woManufacturer;
		this.woObjectType = woObjectType;
		this.woObjectName = woObjectName;
		this.woObjectWhereabouts = woObjectWhereabouts;
	}
}
