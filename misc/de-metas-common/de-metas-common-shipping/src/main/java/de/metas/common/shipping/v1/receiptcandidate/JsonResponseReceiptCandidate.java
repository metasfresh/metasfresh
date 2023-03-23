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

package de.metas.common.shipping.v1.receiptcandidate;

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
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Single receipt candidate; basically this tells the logistics provider to expect a particular delivery item from a vendor.\n"
		+ "It translates to a particular `M_ReceiptSchedule` record in metasfresh.")
@Value
public class JsonResponseReceiptCandidate
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

	@Schema(description = "If there is a purchase order, then this is the number of overall exportable items from that order.\n"
					+ "Useful if due to `limit`, not all items of one order are exported in one invocation.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer numberOfItemsWithSameOrderId;

	@Schema
	@JsonInclude(JsonInclude.Include.NON_NULL)
	LocalDateTime dateOrdered;

	@Schema(required = true)
	JsonProduct product;

	@Schema
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonAttributeSetInstance attributeSetInstance;

	@Schema(required = true,
			description = "The shipment schedule's quantity to deliver, possibly in different UOMs")
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
