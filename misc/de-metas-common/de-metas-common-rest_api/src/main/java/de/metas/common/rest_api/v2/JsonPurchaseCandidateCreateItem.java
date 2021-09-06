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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.util.CoalesceUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonPurchaseCandidateCreateItem
{
	@NonNull
	@JsonProperty("orgCode")
	String orgCode;

	@NonNull
	@JsonProperty("externalHeaderId")
	String externalHeaderId;

	@Nullable
	@JsonProperty("externalPurchaseOrderUrl")
	String externalPurchaseOrderUrl;

	@NonNull
	@JsonProperty("externalLineId")
	String externalLineId;

	@JsonProperty("isManualPrice")
	boolean isManualPrice;

	@JsonProperty("isPrepared")
	boolean isPrepared;

	@Nullable
	@JsonProperty("price")
	JsonPrice price;

	@JsonProperty("isManualDiscount")
	boolean isManualDiscount;

	@Nullable
	@JsonProperty("discount")
	BigDecimal discount;

	@Nullable
	@JsonProperty("purchaseDatePromised")
	ZonedDateTime purchaseDatePromised;

	@Nullable
	@JsonProperty("purchaseDateOrdered")
	ZonedDateTime purchaseDateOrdered;

	@ApiModelProperty("The vendor (`C_BPartner`) in question. Can be\n"
			+ "* a plain `<C_BPartner_ID>`\n"
			+ "* a plain `gln-<C_BPartner_Location.GLN>`\n"
			+ "* a plain `val-<C_BPartner.Value>`\n"
			+ "* or an External Business Key with type `BPartner` such as `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n")
	@NonNull
	@JsonProperty("vendor")
	JsonVendor vendor;

	@ApiModelProperty("The warehouse (`M_Warehouse`) in question. Can be\n"
			+ "* a plain `<M_Warehouse_ID>`\n"
			+ "* a plain `val-<M_Warehouse.Value>`\n"
			+ "* or an External Business Key with type `Warehouse` such as `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n")
	@NonNull
	@JsonProperty("warehouseIdentifier")
	String warehouseIdentifier;

	@ApiModelProperty(value = PRODUCT_IDENTIFIER_DOC)
	@NonNull
	@JsonProperty("productIdentifier")
	String productIdentifier;

	@Nullable
	@JsonProperty("attributeSetInstance")
	JsonAttributeSetInstance attributeSetInstance;

	@NonNull
	@JsonProperty("qty")
	JsonQuantity qty;

	@Builder
	@JsonCreator
	private JsonPurchaseCandidateCreateItem(
			@JsonProperty("orgCode") final @NonNull String orgCode,
			@JsonProperty("externalHeaderId") final @NonNull String externalHeaderId,
			@JsonProperty("externalPurchaseOrderUrl") final @Nullable String externalPurchaseOrderUrl,
			@JsonProperty("externalLineId") final @NonNull String externalLineId,
			@JsonProperty("isManualPrice") @Nullable final Boolean isManualPrice,
			@JsonProperty("isPrepared") final boolean isPrepared,
			@JsonProperty("price") @Nullable final JsonPrice price,
			@JsonProperty("isManualDiscount") @Nullable final Boolean isManualDiscount,
			@JsonProperty("discount") @Nullable final BigDecimal discount,
			@JsonProperty("purchaseDatePromised") @Nullable final ZonedDateTime purchaseDatePromised,
			@JsonProperty("purchaseDateOrdered") @Nullable final ZonedDateTime purchaseDateOrdered,
			@JsonProperty("vendor") final @NonNull JsonVendor vendor,
			@JsonProperty("warehouseIdentifier") final @NonNull String warehouseIdentifier,
			@JsonProperty("productIdentifier") final @NonNull String productIdentifier,
			@JsonProperty("attributeSetInstance") @Nullable final JsonAttributeSetInstance attributeSetInstance,
			@JsonProperty("qty") final @NonNull JsonQuantity qty)
	{

		this.orgCode = orgCode;
		this.externalHeaderId = externalHeaderId;
		this.externalPurchaseOrderUrl = externalPurchaseOrderUrl;
		this.externalLineId = externalLineId;
		this.isManualPrice = CoalesceUtil.coalesce(isManualPrice, false);
		this.isPrepared = isPrepared;
		this.price = price;
		this.isManualDiscount = CoalesceUtil.coalesce(isManualDiscount, false);
		this.discount = discount;
		this.purchaseDatePromised = purchaseDatePromised;
		this.purchaseDateOrdered = purchaseDateOrdered;
		this.vendor = vendor;
		this.warehouseIdentifier = warehouseIdentifier;
		this.productIdentifier = productIdentifier;
		this.attributeSetInstance = attributeSetInstance;
		this.qty = qty;
	}
}
