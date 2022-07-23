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

package de.metas.common.rest_api.v2.project.budget;

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
public class JsonBudgetProjectResponse
{
	@NonNull
	@JsonProperty("projectId")
	JsonMetasfreshId projectId;

	@NonNull
	@JsonProperty("orgCode")
	String orgCode;

	@Nullable
	@JsonProperty("currencyId")
	JsonMetasfreshId currencyId;

	@NonNull
	@JsonProperty("name")
	String name;

	@NonNull
	@JsonProperty("value")
	String value;

	@Nullable
	@JsonProperty("isActive")
	Boolean isActive;

	@Nullable
	@JsonProperty("priceListVersionId")
	JsonMetasfreshId priceListVersionId;

	@Nullable
	@JsonProperty("description")
	String description;

	@Nullable
	@JsonProperty("projectParentId")
	JsonMetasfreshId projectParentId;

	@NonNull
	@JsonProperty("projectTypeId")
	JsonMetasfreshId projectTypeId;

	@Nullable
	@JsonProperty("projectReferenceExt")
	String projectReferenceExt;

	@Nullable
	@JsonProperty("bPartnerId")
	JsonMetasfreshId bPartnerId;

	@Nullable
	@JsonProperty("salesRepId")
	JsonMetasfreshId salesRepId;

	@Nullable
	@JsonProperty("dateContract")
	String dateContract;

	@Nullable
	@JsonProperty("dateFinish")
	String dateFinish;

	@Nullable
	@JsonProperty("projectResources")
	List<JsonBudgetProjectResourceResponse> projectResources;

	@JsonCreator
	@Builder
	public JsonBudgetProjectResponse(
			@JsonProperty("projectId") @NonNull final JsonMetasfreshId projectId,
			@JsonProperty("orgCode") @NonNull final String orgCode,
			@JsonProperty("currencyId") @Nullable final JsonMetasfreshId currencyId,
			@JsonProperty("name") @NonNull final String name,
			@JsonProperty("value") @NonNull final String value,
			@JsonProperty("isActive") @Nullable final Boolean isActive,
			@JsonProperty("priceListVersionId") @Nullable final JsonMetasfreshId priceListVersionId,
			@JsonProperty("description") @Nullable final String description,
			@JsonProperty("projectParentId") @Nullable final JsonMetasfreshId projectParentId,
			@JsonProperty("projectTypeId") @NonNull final JsonMetasfreshId projectTypeId,
			@JsonProperty("projectReferenceExt") @Nullable final String projectReferenceExt,
			@JsonProperty("bPartnerId") @Nullable final JsonMetasfreshId bPartnerId,
			@JsonProperty("salesRepId") @Nullable final JsonMetasfreshId salesRepId,
			@JsonProperty("dateContract") @Nullable final String dateContract,
			@JsonProperty("dateFinish") @Nullable final String dateFinish,
			@JsonProperty("projectResources") @Nullable final List<JsonBudgetProjectResourceResponse> projectResources)
	{
		this.projectId = projectId;
		this.orgCode = orgCode;
		this.currencyId = currencyId;
		this.name = name;
		this.value = value;
		this.isActive = isActive;
		this.priceListVersionId = priceListVersionId;
		this.description = description;
		this.projectParentId = projectParentId;
		this.projectTypeId = projectTypeId;
		this.projectReferenceExt = projectReferenceExt;
		this.bPartnerId = bPartnerId;
		this.salesRepId = salesRepId;
		this.dateContract = dateContract;
		this.dateFinish = dateFinish;
		this.projectResources = projectResources;
	}
}