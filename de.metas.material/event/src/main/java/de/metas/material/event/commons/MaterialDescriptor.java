package de.metas.material.event.commons;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

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
	public enum DateOperator
	{
		BEFORE_OR_AT,

		AFTER,

		AT_OR_AFTER,

		AT
	}

	/**
	 * @return a builder where you don't have to set all the properties.
	 */
	public static MaterialDescriptorBuilder builderForQuery()
	{
		return MaterialDescriptor.builder().complete(false);
	}

	/**
	 * @return a builder where you need to set all the properties (i.e. with complete=true).
	 */
	public static MaterialDescriptorBuilder builderForCompleteDescriptor()
	{
		return MaterialDescriptor.builder().complete(true);
	}

	boolean materialDescriptorComplete;

	@Getter
	int warehouseId;

	@Getter
	BigDecimal quantity;

	/**
	 * The projected date at which we expect this candidate's {@link #getQuantity()}.
	 */
	@Getter
	Date date;

	/**
	 * This property specifies how to interpret the date.
	 */
	@Getter
	DateOperator dateOperator;

	@Builder
	private MaterialDescriptor(
			final int warehouseId,
			final BigDecimal quantity,
			final Date date,
			final DateOperator dateOperator,
			final ProductDescriptor productDescriptor,
			final Boolean complete)
	{
		this(
				warehouseId,
				quantity,
				date,
				dateOperator,
				productDescriptor == null ? 0 : productDescriptor.getProductId(),
				productDescriptor == null ? -1 : productDescriptor.getAttributeSetInstanceId(),
				productDescriptor == null ? STORAGE_ATTRIBUTES_KEY_UNSPECIFIED : productDescriptor.getStorageAttributesKey(),
				productDescriptor == null ? false : productDescriptor.isComplete(),
				complete);
	}

	@JsonCreator
	public MaterialDescriptor(
			@JsonProperty("warehouseId") final int warehouseId,
			@JsonProperty("quantity") final BigDecimal quantity,
			@JsonProperty("date") final Date date,
			@JsonProperty("dateOperator") final DateOperator dateOperator,
			@JsonProperty("productId") final int productId,
			@JsonProperty("attributeSetInstanceId") final int attributeSetInstanceId,
			@JsonProperty("storageAttributesKey") final String storageAttributesKey,
			@JsonProperty("productDescriptorComplete") final boolean productDescriptorComplete,
			@JsonProperty("materialDescriptorComplete") final Boolean complete)
	{
		super(productDescriptorComplete, productId, storageAttributesKey, attributeSetInstanceId);

		Preconditions.checkNotNull(complete, "The given parameter complete may not be null (either true or false)");
		this.materialDescriptorComplete = complete;

		this.warehouseId = warehouseId;
		this.quantity = quantity;

		Preconditions.checkArgument(dateOperator == null || date != null,
				"Given date parameter may not be null because a not-null dateOperator=%s is given",
				dateOperator);
		this.date = date;
		this.dateOperator = dateOperator == null ? DateOperator.AT : dateOperator;

		asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor asssertMaterialDescriptorComplete()
	{
		if (materialDescriptorComplete)
		{
			Preconditions.checkArgument(super.isComplete(),
					"productDescriptor=%s needs to be complete, because complete=true", this);
			Preconditions.checkArgument(warehouseId > 0,
					"warehouseId=%s needs to be >0, because complete=true", warehouseId);
			Preconditions.checkNotNull(quantity,
					"quantity needs to be not-null, because complete=true");
			Preconditions.checkNotNull(date,
					"date needs to not-null, because complete=true");
			Preconditions.checkArgument(dateOperator == DateOperator.AT,
					"dateOperator needs to be 'AT', because complete=true");
			super.asssertCompleteness();
		}
		return this;
	}

	@Override
	@JsonProperty("materialDescriptorComplete")
	public boolean isComplete()
	{
		return materialDescriptorComplete && super.isComplete();
	}

	public MaterialDescriptor withoutQuantity()
	{
		return MaterialDescriptor.builderForQuery()
				.date(date)
				.productDescriptor(this)
				.warehouseId(warehouseId)
				.build();
	}

	public MaterialDescriptor withQuantity(@NonNull final BigDecimal quantity)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.quantity(quantity)
				.complete(this.materialDescriptorComplete)
				.date(this.date)
				.dateOperator(this.dateOperator)
				.productDescriptor(this)
				.warehouseId(this.warehouseId)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withDate(@NonNull final Date date)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.date(date)
				.dateOperator(this.dateOperator)
				.complete(this.materialDescriptorComplete)
				.productDescriptor(this)
				.warehouseId(this.warehouseId)
				.quantity(this.quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withProductDescriptor(final ProductDescriptor productDescriptor)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.productDescriptor(productDescriptor)
				.complete(this.materialDescriptorComplete)
				.date(this.date)
				.dateOperator(this.dateOperator)
				.warehouseId(this.warehouseId)
				.quantity(this.quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withWarehouseId(final int warehouseId)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.warehouseId(warehouseId)
				.complete(this.materialDescriptorComplete)
				.date(this.date)
				.dateOperator(this.dateOperator)
				.productDescriptor(this)
				.quantity(this.quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}

	public MaterialDescriptor withDateOperator(DateOperator dateOperator)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.dateOperator(dateOperator)
				.warehouseId(warehouseId)
				.complete(materialDescriptorComplete)
				.date(date)
				.productDescriptor(this)
				.quantity(quantity)
				.build();
		return result.asssertMaterialDescriptorComplete();
	}
}
