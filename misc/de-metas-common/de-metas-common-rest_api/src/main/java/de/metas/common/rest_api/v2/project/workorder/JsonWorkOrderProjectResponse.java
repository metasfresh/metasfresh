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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class JsonWorkOrderProjectResponse
{
	@NonNull
	@JsonProperty("projectId")
	JsonMetasfreshId projectId;

	@NonNull
	@JsonProperty("value")
	String value;

	@NonNull
	@JsonProperty("name")
	String name;

	@Nullable
	@JsonProperty("projectTypeId")
	JsonMetasfreshId projectTypeId;

	@Nullable
	@JsonProperty("priceListVersionId")
	JsonMetasfreshId priceListVersionId;

	@Nullable
	@JsonProperty("currencyId")
	JsonMetasfreshId currencyId;

	@Nullable
	@JsonProperty("salesRepId")
	JsonMetasfreshId salesRepId;

	@Nullable
	@JsonProperty("description")
	String description;

	@Nullable
	@JsonProperty("dateContract")
	String dateContract;

	@Nullable
	@JsonProperty("dateFinish")
	String dateFinish;

	@Nullable
	@JsonProperty("bPartnerId")
	JsonMetasfreshId bPartnerId;

	@Nullable
	@JsonProperty("projectReferenceExt")
	String projectReferenceExt;

	@Nullable
	@JsonProperty("projectParentId")
	JsonMetasfreshId projectParentId;

	@NonNull
	@JsonProperty("orgCode")
	String orgCode;

	@Nullable
	@JsonProperty("isActive")
	Boolean isActive;

	@Nullable
	@JsonProperty("steps")
	List<JsonWorkOrderStepResponse> steps;

	@JsonCreator
	@Builder
	public JsonWorkOrderProjectResponse(
			@NonNull @JsonProperty("projectId") final JsonMetasfreshId projectId,
			@NonNull @JsonProperty("value") final String value,
			@NonNull @JsonProperty("name") final String name,
			@NonNull @JsonProperty("projectTypeId") final JsonMetasfreshId projectTypeId,
			@Nullable @JsonProperty("priceListVersionId") final JsonMetasfreshId priceListVersionId,
			@Nullable @JsonProperty("currencyId") final JsonMetasfreshId currencyId,
			@Nullable @JsonProperty("salesRepId") final JsonMetasfreshId salesRepId,
			@Nullable @JsonProperty("description") final String description,
			@Nullable @JsonProperty("dateContract") final String dateContract,
			@Nullable @JsonProperty("dateFinish") final String dateFinish,
			@Nullable @JsonProperty("bPartnerId") final JsonMetasfreshId bPartnerId,
			@Nullable @JsonProperty("projectReferenceExt") final String projectReferenceExt,
			@Nullable @JsonProperty("projectParentId") final JsonMetasfreshId projectParentId,
			@NonNull @JsonProperty("orgCode") final String orgCode,
			@Nullable @JsonProperty("isActive") final Boolean isActive,
			@Nullable @JsonProperty("steps") final List<JsonWorkOrderStepResponse> steps)
	{
		this.projectId = projectId;
		this.value = value;
		this.name = name;
		this.projectTypeId = projectTypeId;
		this.priceListVersionId = priceListVersionId;
		this.currencyId = currencyId;
		this.salesRepId = salesRepId;
		this.description = description;
		this.dateContract = dateContract;
		this.dateFinish = dateFinish;
		this.bPartnerId = bPartnerId;
		this.projectReferenceExt = projectReferenceExt;
		this.projectParentId = projectParentId;
		this.orgCode = orgCode;
		this.isActive = isActive;
		this.steps = steps;
	}
}