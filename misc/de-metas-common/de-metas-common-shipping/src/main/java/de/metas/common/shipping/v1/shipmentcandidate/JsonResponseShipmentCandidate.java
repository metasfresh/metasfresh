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

package de.metas.common.shipping.v1.shipmentcandidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonAttributeSetInstance;
import de.metas.common.rest_api.v1.JsonQuantity;
import de.metas.common.shipping.v1.JsonProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Single shipment candidate; basically this is a ship-TODO item. It translates to a particular `M_ShipmentSchedule` record in metasfresh.")
@Value
public class JsonResponseShipmentCandidate
{
	@Schema(required = true)
	JsonMetasfreshId id;

	@Schema(required = true)
	String orgCode;

	@Schema(description = "The the `C_Order.DocumentNo` of the shipment schedule's sales order - if any")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String orderDocumentNo;

	@Schema
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String poReference;

	@Schema
	@JsonInclude(JsonInclude.Include.NON_NULL)
	LocalDateTime dateOrdered;

	@Schema(description = "This is the number of overall exportable items that would end up in the same shipment.\n"
					+ "Useful if due to `limit`, not all items of one shipment are exported in one invocation.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer numberOfItemsForSameShipment;

	@Schema(required = true)
	JsonProduct product;

	@Schema
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonAttributeSetInstance attributeSetInstance;

	@Schema(required = true)
	JsonCustomer shipBPartner;

	@Schema
	JsonCustomer billBPartner;

	@Schema(required = true,
			description = "The shipment schedule's quantity to deliver, possibly in different UOMs")
	List<JsonQuantity> quantities;

	@Schema(required = true,
			description = "The shipment schedule's ordered quantity, possibly in different UOMs")
	List<JsonQuantity> orderedQty;

	@Schema(description = "The internal search key of the assigned shipper")
	String shipperInternalSearchKey;

	@Schema(description = "The net price of the ordered quantity")
	BigDecimal orderedQtyNetPrice;

	@Schema(description = "The net price of the quantity currently to deliver")
	@Nullable
	BigDecimal qtyToDeliverNetPrice;

	@Schema(description = "The net price of the delivered quantity ")
	BigDecimal deliveredQtyNetPrice;

	@Schema(description = "Delivery information")
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

