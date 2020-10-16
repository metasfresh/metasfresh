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

package de.metas.common.shipping.receiptcandidate;

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
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "Single receipt candidate; basically this tells the logistics provider to expect a particular delivery item from a vendor.\n"
		+ "It translates to a particular `M_ReceiptSchedule` record in metasfresh.")
@Value
public class JsonResponseReceiptCandidate
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

	@ApiModelProperty(position = 60,
			value = "If there is a purchase order, then this is the number of overall exportable items from that order.\n"
					+ "Useful if due to `limit`, not all items of one order are exported in one invocation.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer numberOfItemsWithSameOrderId;

	@ApiModelProperty(position = 70)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	LocalDateTime dateOrdered;

	@ApiModelProperty(position = 80, required = true)
	JsonProduct product;

	@ApiModelProperty(position = 90)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonAttributeSetInstance attributeSetInstance;

	@ApiModelProperty(position = 100, required = true,
			value = "The shipment schedule's quantity to deliver, possibly in different UOMs")
	List<JsonQuantity> quantities;

	@JsonCreator
	@Builder
	private JsonResponseReceiptCandidate(
			@JsonProperty("id") @NonNull final JsonMetasfreshId id,
			@JsonProperty("orgCode") @NonNull final String orgCode,
			@JsonProperty("orderDocumentNo") @Nullable final String orderDocumentNo,
			@JsonProperty("poReference") @Nullable final String poReference,
			@JsonProperty("dateOrdered") @Nullable final LocalDateTime dateOrdered,
			@JsonProperty("numberOfItemsWithSameOrderId") @Nullable final Integer numberOfItemsWithSameOrderId,
			@JsonProperty("product") @NonNull final JsonProduct product,
			@JsonProperty("attributeSetInstance") @Nullable final JsonAttributeSetInstance attributeSetInstance,
			@JsonProperty("quantities") @NonNull @Singular final List<JsonQuantity> quantities)
	{
		this.id = id;
		this.orgCode = orgCode;
		this.orderDocumentNo = orderDocumentNo;
		this.poReference = poReference;
		this.dateOrdered = dateOrdered;
		this.numberOfItemsWithSameOrderId = numberOfItemsWithSameOrderId;
		this.product = product;
		this.attributeSetInstance = attributeSetInstance;
		this.quantities = quantities;
	}
}
