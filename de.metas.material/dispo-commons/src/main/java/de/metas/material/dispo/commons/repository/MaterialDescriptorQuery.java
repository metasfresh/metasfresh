package de.metas.material.dispo.commons.repository;

import java.util.Date;

import com.google.common.base.Preconditions;

import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.StorageAttributesKey;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
@Builder
public class MaterialDescriptorQuery
{
	public enum DateOperator
	{
		BEFORE, //
		BEFORE_OR_AT, //
		AT, //
		AT_OR_AFTER, //
		AFTER
	}

	public static MaterialDescriptorQuery forDescriptor(
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		return new MaterialDescriptorQuery(
				materialDescriptor.getWarehouseId(),
				materialDescriptor.getDate(),
				DateOperator.AT,
				materialDescriptor.getProductId(),
				materialDescriptor.getStorageAttributesKey());
	}

	public static MaterialDescriptorQuery forDescriptor(
			@NonNull final MaterialDescriptor materialDescriptor,
			@NonNull final DateOperator dateOperator)
	{
		return new MaterialDescriptorQuery(
				materialDescriptor.getWarehouseId(),
				materialDescriptor.getDate(),
				dateOperator,
				materialDescriptor.getProductId(),
				materialDescriptor.getStorageAttributesKey());
	}

	/**
	 * This property specifies how to interpret the date.
	 */
	DateOperator dateOperator;

	int warehouseId;
	Date date;
	int productId;
	StorageAttributesKey storageAttributesKey;

	@Builder
	private MaterialDescriptorQuery(
			final int warehouseId,
			final Date date,
			final DateOperator dateOperator,
			final int productId,
			final StorageAttributesKey storageAttributesKey)
	{
		this.warehouseId = warehouseId > 0 ? warehouseId : -1;

		Preconditions.checkArgument(dateOperator == null || date != null,
				"Given date parameter may not be null because a not-null dateOperator=%s is given",
				dateOperator);
		this.date = date;
		this.dateOperator = dateOperator != null ? dateOperator : DateOperator.AT;

		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey != null ? storageAttributesKey : ProductDescriptor.STORAGE_ATTRIBUTES_KEY_ALL;
	}
}
