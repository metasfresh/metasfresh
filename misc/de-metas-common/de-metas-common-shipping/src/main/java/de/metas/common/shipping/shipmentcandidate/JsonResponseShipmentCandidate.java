/*
 * #%L
 * de-metas-common-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.shipping.shipmentcandidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipping.JsonProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "Single shipment candidate; basically this is a ship-TODO item. It translates to a particular `M_ShipmentSchedule` record in metasfresh.")
@Value
public class JsonResponseShipmentCandidate
{
	@ApiModelProperty(position = 10, required = true)
	JsonMetasfreshId id;

	@ApiModelProperty(position = 30, required = true)
	String orgCode;

	@ApiModelProperty(position = 40,
			value = "The the `C_Order.DocumentNo` of the shipment schedule's sales order - if any")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String orderDocumentNo;

	@ApiModelProperty(position = 50)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String poReference;

	@ApiModelProperty(position = 60)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	LocalDateTime dateOrdered;

	@ApiModelProperty(position = 70,
			value = "This is the number of overall exportable items that would end up in the same shipment.\n"
					+ "Useful if due to `limit`, not all items of one shipment are exported in one invocation.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer numberOfItemsForSameShipment;

	@ApiModelProperty(position = 80, required = true)
	JsonProduct product;

	@ApiModelProperty(position = 90)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonAttributeSetInstance attributeSetInstance;

	@ApiModelProperty(position = 100, required = true)
	JsonCustomer shipBPartner;

	@ApiModelProperty(position = 105)
	JsonCustomer billBPartner;

	@ApiModelProperty(position = 110, required = true,
			value = "The shipment schedule's quantity to deliver, possibly in different UOMs")
	List<JsonQuantity> quantities;

	@ApiModelProperty(position = 120, required = true,
			value = "The shipment schedule's ordered quantity, possibly in different UOMs")
	List<JsonQuantity> orderedQty;

	@ApiModelProperty(position = 130,
			value = "The internal search key of the assigned shipper")
	String shipperInternalSearchKey;

	@ApiModelProperty(position = 140,
			value = "The net price of the ordered quantity")
	BigDecimal orderedQtyNetPrice;

	@ApiModelProperty(position = 150,
			value = "The net price of the quantity currently to deliver")
	@Nullable
	BigDecimal qtyToDeliverNetPrice;

	@ApiModelProperty(position = 160,
			value = "The net price of the delivered quantity ")
	BigDecimal deliveredQtyNetPrice;

	@ApiModelProperty(position = 170,
			value = "Delivery information")
	String deliveryInfo;

	@JsonCreator
	@Builder
	private JsonResponseShipmentCandidate(
			@JsonProperty("id") @NonNull final JsonMetasfreshId id,
			@JsonProperty("orgCode") @NonNull final String orgCode,
			@JsonProperty("orderDocumentNo") @Nullable final String orderDocumentNo,
			@JsonProperty("poReference") @Nullable final String poReference,
			@JsonProperty("dateOrdered") @Nullable final LocalDateTime dateOrdered,
			@JsonProperty("numberOfItemsForSameShipment") @Nullable final Integer numberOfItemsForSameShipment,
			@JsonProperty("product") @NonNull final JsonProduct product,
			@JsonProperty("attributeSetInstance") @Nullable final JsonAttributeSetInstance attributeSetInstance,
			@JsonProperty("shipBPartner") @NonNull final JsonCustomer shipBPartner,
			@JsonProperty("billBPartner") @Nullable final JsonCustomer billBPartner,
			@JsonProperty("quantities") @NonNull @Singular final List<JsonQuantity> quantities,
			@JsonProperty("shipperInternalSearchKey") @Nullable final String shipperInternalSearchKey,
			@JsonProperty("orderedQty") @NonNull final List<JsonQuantity> orderedQty,
			@JsonProperty("deliveredQtyNetPrice") @Nullable final BigDecimal deliveredQtyNetPrice,
			@JsonProperty("qtyToDeliverNetPrice") @Nullable final BigDecimal qtyToDeliverNetPrice,
			@JsonProperty("orderedQtyNetPrice") @Nullable final BigDecimal orderedQtyNetPrice,
			@JsonProperty("deliveryInfo") @Nullable final String deliveryInfo)
	{
		this.id = id;
		this.orgCode = orgCode;
		this.orderDocumentNo = orderDocumentNo;
		this.poReference = poReference;
		this.dateOrdered = dateOrdered;
		this.numberOfItemsForSameShipment = numberOfItemsForSameShipment;
		this.product = product;
		this.attributeSetInstance = attributeSetInstance;
		this.shipBPartner = shipBPartner;
		this.billBPartner = billBPartner;
		this.quantities = quantities;
		this.shipperInternalSearchKey = shipperInternalSearchKey;
		this.orderedQty = orderedQty;
		this.deliveredQtyNetPrice = deliveredQtyNetPrice;
		this.qtyToDeliverNetPrice = qtyToDeliverNetPrice;
		this.orderedQtyNetPrice = orderedQtyNetPrice;
		this.deliveryInfo = deliveryInfo;
	}
}

