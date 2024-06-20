package de.metas.material.dispo.commons.repository.atp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
public class AvailableToPromiseQuery
{
	public static AvailableToPromiseQuery forMaterialDescriptor(@NonNull final MaterialDescriptor materialDescriptor)
	{
		return AvailableToPromiseQuery.builder()
				.warehouseId(materialDescriptor.getWarehouseId())
				.date(TimeUtil.asZonedDateTime(materialDescriptor.getDate()))
				.productId(materialDescriptor.getProductId())
				.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(materialDescriptor.getStorageAttributesKey()))
				.bpartner(BPartnerClassifier.specificOrNone(materialDescriptor.getCustomerId()))
				.build();
	}

	ImmutableSet<WarehouseId> warehouseIds;

	/** optional; if null, then "now" is used */
	ZonedDateTime date;

	ImmutableList<Integer> productIds;
	ImmutableList<AttributesKeyPattern> storageAttributesKeyPatterns;

	BPartnerClassifier bpartner;

	@Builder(toBuilder = true)
	private AvailableToPromiseQuery(
			@Singular final Set<WarehouseId> warehouseIds,
			@Nullable final ZonedDateTime date,
			@Singular final List<Integer> productIds,
			@Singular final List<AttributesKeyPattern> storageAttributesKeyPatterns,
			@Nullable final BPartnerClassifier bpartner)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		this.warehouseIds = warehouseIds == null || warehouseIds.isEmpty() ? ImmutableSet.of() : ImmutableSet.copyOf(warehouseIds);
		this.date = date != null ? date : SystemTime.asZonedDateTime();
		this.productIds = ImmutableList.copyOf(productIds);
		this.storageAttributesKeyPatterns = ImmutableList.copyOf(storageAttributesKeyPatterns);
		this.bpartner = bpartner != null ? bpartner : BPartnerClassifier.none();
	}

	public AvailableToPromiseQuery withDate(@NonNull final Date newDate)
	{
		return withDateTime(TimeUtil.asZonedDateTime(newDate));
	}

	public AvailableToPromiseQuery withDateTime(@NonNull final ZonedDateTime newDate)
	{
		if (Objects.equals(this.date, newDate))
		{
			return this;
		}
		return toBuilder().date(newDate).build();
	}
}
