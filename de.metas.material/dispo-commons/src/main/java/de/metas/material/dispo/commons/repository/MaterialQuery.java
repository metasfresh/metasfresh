package de.metas.material.dispo.commons.repository;

import java.util.Date;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;

import com.google.common.collect.ImmutableSet;

import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.ProductDescriptor;
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

	private final int warehouseId;
	private final Date date;
	private final Set<Integer> productIds;
	private final String storageAttributesKey;
	private final int dimensionSpecId;

	@Builder
	private MaterialQuery(
			final int warehouseId,
			final Date date,
			@Singular final Set<Integer> productIds,
			final String storageAttributesKey,
			final int dimensionSpecId)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		this.warehouseId = warehouseId > 0 ? warehouseId : -1;
		this.date = date != null ? date : SystemTime.asDate();
		this.productIds = ImmutableSet.copyOf(productIds);
		this.storageAttributesKey = storageAttributesKey != null ? storageAttributesKey : ProductDescriptor.STORAGE_ATTRIBUTES_KEY_UNSPECIFIED;
		this.dimensionSpecId = dimensionSpecId;
	}
}
