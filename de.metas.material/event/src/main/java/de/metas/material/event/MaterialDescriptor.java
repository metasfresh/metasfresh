package de.metas.material.event;

import java.math.BigDecimal;
import java.util.Date;

import com.google.common.base.Preconditions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
@Value
@AllArgsConstructor // needed for jackson
public class MaterialDescriptor
{
	/**
	 * @return a builder where you don't have to set all the properties.
	 */
	public static MaterialDescriptorBuilder builderForQuery()
	{
		return MaterialDescriptor.builder().complete(false);
	}

	/**
	 * @return a builder where you need to set all the properties.
	 */
	public static MaterialDescriptorBuilder builderForCandidateOrQuery()
	{
		return MaterialDescriptor.builder().complete(true);
	}

	boolean complete;
	
	int warehouseId;

	int productId;

	BigDecimal quantity;

	/**
	 * The projected date at which we expect this candidate's {@link #getQuantity()}.
	 */
	Date date;

	@Builder
	public MaterialDescriptor(
			final int warehouseId, 
			final int productId,
			final BigDecimal quantity, 
			final Date date, 
			final Boolean complete)
	{
		this.complete = complete == null || complete;

		this.warehouseId = warehouseId;
		this.productId = productId;
		this.quantity = quantity;
		this.date = date;

		asssertCompleteness();
	}

	private MaterialDescriptor asssertCompleteness()
	{
		if (complete)
		{
			Preconditions.checkArgument(warehouseId > 0,
					"Given parameter warehouseId=%s needs to be >0, because complete=true", warehouseId);
			Preconditions.checkArgument(productId > 0,
					"Given parameter productId=%s needs to be >0, because complete=true", productId);
			Preconditions.checkNotNull(quantity,
					"Given parameter quantity needs to be not-null, because complete=true");
			Preconditions.checkNotNull(date,
					"Given parameter date needs to not-null, because complete=true");
		}
		return this;
	}

	public MaterialDescriptor withoutQuantity()
	{
		return MaterialDescriptor.builderForQuery().date(date).productId(productId).warehouseId(warehouseId).build();
	}

	public MaterialDescriptor withQuantity(@NonNull final BigDecimal quantity)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder().complete(complete).date(date).productId(productId).warehouseId(warehouseId).quantity(quantity).build();
		return result.asssertCompleteness();
	}

	public MaterialDescriptor withDate(Date date)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder().complete(complete).date(date).productId(productId).warehouseId(warehouseId).quantity(quantity).build();
		return result.asssertCompleteness();
	}

	public MaterialDescriptor withProductId(int productId)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder().complete(complete).date(date).productId(productId).warehouseId(warehouseId).quantity(quantity).build();
		return result.asssertCompleteness();
	}

	public MaterialDescriptor withWarehouseId(int warehouseId)
	{
		final MaterialDescriptor result = MaterialDescriptor.builder().complete(complete).date(date).productId(productId).warehouseId(warehouseId).quantity(quantity).build();
		return result.asssertCompleteness();
	}
}
