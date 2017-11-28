package de.metas.material.dispo.commons.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class MaterialQuery
{
	public static MaterialQuery forMaterialDescriptor(@NonNull final MaterialDescriptor materialDescriptor)
	{
		return MaterialQuery.builder()
				.warehouseId(materialDescriptor.getWarehouseId())
				.date(materialDescriptor.getDate())
				.productId(materialDescriptor.getProductId())
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.build();
	}

	Set<Integer> warehouseIds;

	@NonNull
	Date date;

	List<Integer> productIds;

	List<AttributesKey> storageAttributesKeys;

	@Builder
	private MaterialQuery(
			@Singular final Set<Integer> warehouseIds,
			final Date date,
			@Singular final List<Integer> productIds,
			@Singular final List<AttributesKey> storageAttributesKeys)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		this.warehouseIds = warehouseIds == null || warehouseIds.isEmpty() ? ImmutableSet.of() : ImmutableSet.copyOf(warehouseIds);
		this.date = date != null ? date : SystemTime.asDate();
		this.productIds = ImmutableList.copyOf(productIds);
		this.storageAttributesKeys = ImmutableList.copyOf(storageAttributesKeys);
	}

	public MaterialQuery withDate(@NonNull final Date newDate)
	{
		return new MaterialQuery(warehouseIds, newDate, productIds, storageAttributesKeys);
	}
}
