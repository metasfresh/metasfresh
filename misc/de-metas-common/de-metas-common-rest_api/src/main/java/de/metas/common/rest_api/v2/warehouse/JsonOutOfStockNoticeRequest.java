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

package de.metas.common.rest_api.v2.warehouse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.common.util.CoalesceUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = JsonOutOfStockNoticeRequest.JsonOutOfStockNoticeRequestBuilder.class)
public class JsonOutOfStockNoticeRequest
{
	@Schema(
			description = "Identifier of the product in question. Can be\n"
					+ "* a plain `<M_Product_ID>`,\n"
					+ "* the M_Product.Value as `<val-M_Product.Value>`\n"
					+ "* or something like `ext-<ExternalSystemName>-<M_Product_ID.ExternalId>`")
	@NonNull
	@JsonProperty("productIdentifier")
	String productIdentifier;

	@Schema(description = "Optional; Specifies if metasfresh shall create and complete an inventory document for this out of stock notice. Default if omitted: `true`")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@NonNull
	Boolean createInventory;

	@Schema(description = "Optional; Specifies if unprocessed shipment schedules with with this request's warehouse, product ans attributes shall be closed. Default if omitted: `true`")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@NonNull
	Boolean closePendingShipmentSchedules;

	@Schema(description = "AD_Org.value")
	@Nullable
	@JsonProperty("orgCode")
	String orgCode;

	@Nullable
	@JsonProperty("attributeSetInstance")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonAttributeSetInstance attributeSetInstance;

	@Builder
	public JsonOutOfStockNoticeRequest(
			@NonNull @JsonProperty("productIdentifier") final String productIdentifier,
			@Nullable @JsonProperty("attributeSetInstance") final JsonAttributeSetInstance attributeSetInstance,
			@Nullable @JsonProperty("orgCode") final String orgCode,
			@Nullable @JsonProperty("createInventory") final Boolean createInventory,
			@Nullable @JsonProperty("closePendingShipmentSchedules") final Boolean closePendingShipmentSchedules)
	{
		this.orgCode = orgCode;
		this.productIdentifier = productIdentifier;
		this.attributeSetInstance = attributeSetInstance;
		this.createInventory = CoalesceUtil.coalesceNotNull(createInventory, Boolean.TRUE);
		this.closePendingShipmentSchedules = CoalesceUtil.coalesceNotNull(closePendingShipmentSchedules, Boolean.TRUE);
	}
}
