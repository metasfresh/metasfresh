package de.metas.material.dispo.commons.repository.atp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.util.Check;
import de.metas.util.time.SystemTime;
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
public class AvailableToPromiseQuery
{
	public static AvailableToPromiseQuery forMaterialDescriptor(@NonNull final MaterialDescriptor materialDescriptor)
	{
		return AvailableToPromiseQuery.builder()
				.warehouseId(materialDescriptor.getWarehouseId())
				.date(TimeUtil.asLocalDateTime(materialDescriptor.getDate()))
				.productId(materialDescriptor.getProductId())
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.bpartnerId(materialDescriptor.getCustomerId())
				.build();
	}

	ImmutableSet<Integer> warehouseIds;

	/** optional; if null, then "now" is used */
	LocalDateTime date;

	ImmutableList<Integer> productIds;
	ImmutableList<AttributesKey> storageAttributesKeys;

	public static final int BPARTNER_ID_ANY = -1;
	public static final int BPARTNER_ID_NONE = -2;

	/** null means "none" */
	private final int bpartnerId;

	@Builder(toBuilder = true)
	private AvailableToPromiseQuery(
			@Singular final Set<Integer> warehouseIds,
			@Nullable final LocalDateTime date,
			@Singular final List<Integer> productIds,
			@Singular final List<AttributesKey> storageAttributesKeys,
			final int bpartnerId)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		this.warehouseIds = warehouseIds == null || warehouseIds.isEmpty() ? ImmutableSet.of() : ImmutableSet.copyOf(warehouseIds);
		this.date = date != null ? date : SystemTime.asLocalDateTime();
		this.productIds = ImmutableList.copyOf(productIds);
		this.storageAttributesKeys = ImmutableList.copyOf(storageAttributesKeys);

		final boolean bPartnerIdIsSpecified = bpartnerId == BPARTNER_ID_ANY
				|| bpartnerId == BPARTNER_ID_NONE
				|| bpartnerId > 0;
		if (bPartnerIdIsSpecified)
		{
			this.bpartnerId = bpartnerId;
		}
		else // default, including 0; bpartnerId was not specified on build time
		{
			this.bpartnerId = BPARTNER_ID_NONE;
		}
	}

	public AvailableToPromiseQuery withDate(@NonNull final Date newDate)
	{
		return withDateTime(TimeUtil.asLocalDateTime(newDate));
	}

	public AvailableToPromiseQuery withDateTime(@NonNull final LocalDateTime newDate)
	{
		if (Objects.equals(this.date, newDate))
		{
			return this;
		}
		return toBuilder().date(newDate).build();
	}

	public boolean isBPartnerMatching(final int bpartnerIdToMatch)
	{
		return isBPartnerMatching(bpartnerId, bpartnerIdToMatch);
	}

	public static boolean isBPartnerMatching(final int bpartnerId, final int bpartnerIdToMatch)
	{
		final boolean bpartnerIdMatchesEveryBPartnerIdToMatch = bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_ANY;
		final boolean bpartnerIdToMatchMatchesAnyBPartnerId = bpartnerIdToMatch == AvailableToPromiseQuery.BPARTNER_ID_ANY;

		return bpartnerIdMatchesEveryBPartnerIdToMatch
				|| bpartnerIdToMatchMatchesAnyBPartnerId
				|| (bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_NONE && bpartnerIdToMatch <= 0)
				|| (bpartnerId == bpartnerIdToMatch);
	}

}
