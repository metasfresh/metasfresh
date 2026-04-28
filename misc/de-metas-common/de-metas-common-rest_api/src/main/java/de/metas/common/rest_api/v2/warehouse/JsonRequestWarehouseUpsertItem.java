/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.common.rest_api.v2.warehouse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.EXTERNAL_VERSION_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.WAREHOUSE_IDENTIFIER_DOC;

@Value
@Builder(toBuilder = true)
@ApiModel(description = "Contains an external id and the actual warehouse to insert or update. The response will contain the given external id.")
public class JsonRequestWarehouseUpsertItem
{
	@ApiModelProperty(position = 10, value = WAREHOUSE_IDENTIFIER_DOC)
	@NonNull
	String warehouseIdentifier;

	@ApiModelProperty(position = 20, //
			value = "The version of the warehouse." + EXTERNAL_VERSION_DOC)
	@Nullable
	String externalVersion;

	@ApiModelProperty(position = 30, //
			value = "URL of the resource in the target external system.")
	@Nullable
	String externalReferenceUrl;

	@ApiModelProperty(position = 40)
	@NonNull
	JsonRequestWarehouse requestWarehouse;

	@JsonCreator
	public JsonRequestWarehouseUpsertItem(
			@NonNull @JsonProperty("warehouseIdentifier") final String warehouseIdentifier,
			@Nullable @JsonProperty("externalVersion") final String externalVersion,
			@Nullable @JsonProperty("externalReferenceUrl") final String externalReferenceUrl,
			@NonNull @JsonProperty("requestWarehouse") final JsonRequestWarehouse requestWarehouse)
	{
		this.warehouseIdentifier = warehouseIdentifier;
		this.externalVersion = externalVersion;
		this.requestWarehouse = requestWarehouse;
		this.externalReferenceUrl = externalReferenceUrl;
	}
}
