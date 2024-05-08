package de.metas.material.event.commons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import de.metas.bpartner.BPartnerId;
import de.metas.util.NumberUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialDescriptor extends ProductDescriptor
{
	@Getter
	WarehouseId warehouseId;

	/**
	 * Optional, if set and {@code isreservedForCustomer()==true}, then the respective candidate allocated to the respective *customer*
	 * and is not available to other customers. This means that a possible ATP raise does not affect the ATP for other customers
	 */
	@Getter
	BPartnerId customerId;

	/**
	 * See {@link #customerId}
	 */
	@Getter
	boolean reservedForCustomer;

	@Getter
	BPartnerId vendorId;

	@Getter
	BigDecimal quantity;

	/**
	 * The projected date at which we expect this candidate's {@link #getQuantity()}.
	 */
	@Getter
	Instant date;

	@Builder
	private MaterialDescriptor(
			final WarehouseId warehouseId,
			final BPartnerId customerId,
			final boolean reservedForCustomer,
			final BPartnerId vendorId,
			final Instant date,
			final ProductDescriptor productDescriptor,
			final BigDecimal quantity)
	{
		this(
				warehouseId,
				customerId,
				reservedForCustomer,
				vendorId,
				quantity,
				date,
				productDescriptor == null ? 0 : productDescriptor.getProductId(),
				productDescriptor == null ? -1 : productDescriptor.getAttributeSetInstanceId(),
				productDescriptor == null ? AttributesKey.ALL : productDescriptor.getStorageAttributesKey());
	}

	@JsonCreator
	public MaterialDescriptor(
			@JsonProperty("warehouseId") final WarehouseId warehouseId,
			@JsonProperty("customerId") @Nullable final BPartnerId customerId,
			@JsonProperty("reservedForCustomer") final boolean reservedForCustomer,
			@JsonProperty("vendorId") final BPartnerId vendorId,
			@JsonProperty("quantity") final BigDecimal quantity,
			@JsonProperty("date") final Instant date,
			@JsonProperty("productId") final int productId,
			@JsonProperty("attributeSetInstanceId") final int attributeSetInstanceId,
			@JsonProperty("storageAttributesKey") final AttributesKey attributesKey)
	{
		super(productId, attributesKey, attributeSetInstanceId);

		this.warehouseId = warehouseId;

		this.customerId = customerId;
		this.reservedForCustomer = reservedForCustomer;
		this.vendorId = vendorId;

		this.quantity = NumberUtils.stripTrailingDecimalZeros(quantity);

		this.date = date;

		asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor asssertMaterialDescriptorComplete()
	{
		Preconditions.checkNotNull(warehouseId, "warehouseId needs to be set");

		// Don't enforce customer/vendor. e.g. in case of Inventory there is no customer/vendor.
		// Preconditions.checkNotNull(customerId, "customerId needs to be not-null", customerId);
		// Preconditions.checkNotNull(vendorId, "vendorId needs to be not-null", vendorId);

		Preconditions.checkNotNull(quantity, "quantity needs to be not-null");
		Preconditions.checkNotNull(date, "date needs to be not-null");

		return this;
	}

	public MaterialDescriptor withQuantity(@NonNull final BigDecimal quantity)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.quantity(quantity)
				.date(this.date)
				.productDescriptor(this)
				.warehouseId(this.warehouseId)
				.customerId(this.customerId)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withDate(@NonNull final Instant date)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.date(date)
				.productDescriptor(this)
				.warehouseId(this.warehouseId)
				.customerId(this.customerId)
				.quantity(this.quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withProductDescriptor(final ProductDescriptor productDescriptor)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.productDescriptor(productDescriptor)
				.date(this.date)
				.warehouseId(this.warehouseId)
				.customerId(this.customerId)
				.quantity(this.quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withWarehouseId(final WarehouseId warehouseId)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.warehouseId(warehouseId)
				.date(this.date)
				.productDescriptor(this)
				.customerId(this.customerId)
				.quantity(this.quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withCustomerId(@Nullable final BPartnerId customerId)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.warehouseId(this.warehouseId)
				.date(this.date)
				.productDescriptor(this)
				.customerId(customerId)
				.quantity(this.quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withStorageAttributes(
			@NonNull final AttributesKey storageAttributesKey,
			final int attributeSetInstanceId)
	{
		final ProductDescriptor newProductDescriptor = ProductDescriptor
				.forProductAndAttributes(
						getProductId(),
						storageAttributesKey,
						attributeSetInstanceId);

		return withProductDescriptor(newProductDescriptor);
	}
}
