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

package de.metas.common.rest_api.v2.project;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonResponseProject.JsonResponseProjectBuilder.class)
public class JsonResponseProject
{
	@ApiModelProperty( //
			required = true, //
			dataType = "java.lang.Integer", //
			value = "This translates to `C_Project.C_Project_ID`.")
	@NonNull
	@JsonProperty("id")
	JsonMetasfreshId id;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.AD_Org_ID`.")
	@NonNull
	@JsonProperty("orgId")
	JsonMetasfreshId orgId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.C_Currency_Id.Iso_Code`.")
	@NonNull
	@JsonProperty("currencyCode")
	String currencyCode;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.Name`.")
	@NonNull
	@JsonProperty("name")
	String name;

	@ApiModelProperty(required = true, value = "This translates to `C_Project.Value`.")
	@NonNull
	@JsonProperty("value")
	String value;

	@ApiModelProperty(value = "This translates to `C_BPartner.IsActive`.")
	@JsonProperty("active")
	boolean active;

	@ApiModelProperty(value = "This translates to `C_Project.C_BPartner_Location_ID`.")
	@Nullable
	@JsonProperty("bpartnerLocationId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId bpartnerLocationId;

	@ApiModelProperty(value = "This translates to `C_Project.M_PriceList_Version_ID`.")
	@Nullable
	@JsonProperty("priceListVersionId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId priceListVersionId;

	@ApiModelProperty(value = "This translates to `C_Project.M_Warehouse_ID`.")
	@Nullable
	@JsonProperty("warehouseId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId warehouseId;

	@ApiModelProperty(value = "This translates to `C_Project.AD_User_ID`.")
	@Nullable
	@JsonProperty("contactId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId contactId;

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

	@ApiModelProperty(value = "This translates to `C_Project.C_ProjectType_ID`.")
	@Nullable
	@JsonProperty("projectTypeId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId projectTypeId;

	@ApiModelProperty(value = "This translates to `C_Project.ProjectCategory`.")
	@Nullable
	@JsonProperty("projectCategory")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String projectCategory;

	@ApiModelProperty(value = "This translates to `C_Project.R_StatusCategory_ID`.")
	@NonNull
	@JsonProperty("requestStatusCategoryId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId requestStatusCategoryId;

	@ApiModelProperty(value = "This translates to `C_Project.R_Project_Status_ID`.")
	@Nullable
	@JsonProperty("projectStatusId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId projectStatusId;

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
}
