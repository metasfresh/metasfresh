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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class JsonBudgetProjectResponse
{
	@ApiModelProperty( //
			required = true, //
			dataType = "java.lang.Integer", //
			value = "This translates to `C_Project.C_Project_ID`.")
	@NonNull
	@JsonProperty("projectId")
	JsonMetasfreshId projectId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.AD_Org_ID.Value`.")
	@NonNull
	@JsonProperty("orgCode")
	String orgCode;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.C_Currency_Id`.")
	@Nullable
	@JsonProperty("currencyId")
	JsonMetasfreshId currencyId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.Name`.")
	@NonNull
	@JsonProperty("name")
	String name;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.Value`.")
	@NonNull
	@JsonProperty("value")
	String value;

	@ApiModelProperty(value = "This translates to `C_BPartner.IsActive`.")
	@Nullable
	@JsonProperty("isActive")
	Boolean isActive;

	@ApiModelProperty(value = "This translates to `C_Project.M_PriceList_Version_ID`.")
	@Nullable
	@JsonProperty("priceListVersionId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId priceListVersionId;

	@ApiModelProperty(value = "This translates to `C_Project.Description`.")
	@Nullable
	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String description;

	@ApiModelProperty(value = "This translates to `C_Project.C_Project_Parent_ID`.")
	@Nullable
	@JsonProperty("projectParentId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId projectParentId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.C_ProjectType_ID`.")
	@NonNull
	@JsonProperty("projectTypeId")
	JsonMetasfreshId projectTypeId;

	@ApiModelProperty(value = "This translates to `C_Project.C_Project_Reference_Ext`.")
	@Nullable
	@JsonProperty("projectReferenceExt")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String projectReferenceExt;

	@ApiModelProperty(value = "This translates to `C_Project.C_BPartner_ID`.")
	@Nullable
	@JsonProperty("bpartnerId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId bpartnerId;

	@ApiModelProperty(value = "This translates to `C_Project.SalesRep_ID`.")
	@Nullable
	@JsonProperty("salesRepId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId salesRepId;

	@ApiModelProperty(value = "This translates to `C_Project.DateContract`.")
	@Nullable
	@JsonProperty("dateContract")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateContract;

	@ApiModelProperty(value = "This translates to `C_Project.DateFinish`.")
	@Nullable
	@JsonProperty("dateFinish")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateFinish;

	@NonNull
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
			@JsonProperty("bpartnerId") @Nullable final JsonMetasfreshId bpartnerId,
			@JsonProperty("salesRepId") @Nullable final JsonMetasfreshId salesRepId,
			@JsonProperty("dateContract") @Nullable final LocalDate dateContract,
			@JsonProperty("dateFinish") @Nullable final LocalDate dateFinish,
			@JsonProperty("projectResources") @Singular final List<JsonBudgetProjectResourceResponse> projectResources)
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
		this.bpartnerId = bpartnerId;
		this.salesRepId = salesRepId;
		this.dateContract = dateContract;
		this.dateFinish = dateFinish;
		this.projectResources = projectResources;
	}
}