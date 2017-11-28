package de.metas.material.dispo.commons.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.commons.AttributesKey;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
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
public class AvailableStockResult
{
	@NonNull
	public static AvailableStockResult createEmptyForQuery(@NonNull final MaterialQuery query)
	{
		final ImmutableList.Builder<ResultGroup> resultBuilder = ImmutableList.builder();

		for (final int productId : query.getProductIds())
		{
			for (final AttributesKey storageAttributesKey : query.getStorageAttributesKeys())
			{
				resultBuilder.add(ResultGroup.builder()
						.productId(productId)
						.storageAttributesKey(storageAttributesKey)
						.qty(BigDecimal.ZERO)
						.build());
			}
		}

		return new AvailableStockResult(resultBuilder.build());
	}

	@NonNull
	List<ResultGroup> resultGroups;

	@ToString
	@EqualsAndHashCode
	@Getter
	public static class ResultGroup
	{
		private final int warehouseId; 
		private final int productId;
		private final AttributesKey storageAttributesKey;
		private final int bpartnerId;
		private BigDecimal qty;

		// TODO: check and set warehouseId, bpartnerId. Task: https://github.com/metasfresh/metasfresh/issues/3098
		@Builder
		public ResultGroup(
				final int warehouseId,
				final int productId,
				@NonNull final AttributesKey storageAttributesKey,
				final int bpartnerId,
				@Nullable final BigDecimal qty)
		{
			Check.assume(productId > 0, "productId > 0");

			this.warehouseId = warehouseId;
			this.productId = productId;
			this.storageAttributesKey = storageAttributesKey;
			this.bpartnerId = bpartnerId;
			this.qty = qty == null ? BigDecimal.ZERO : qty;
		}

		public boolean matches(int productIdToMatch, AttributesKey storageAttributesKeyToMatch)
		{
			if (productIdToMatch != productId)
			{
				return false;
			}

			final List<Integer> attributeValueIdsOfThisInstance = storageAttributesKey.getAttributeValueIds();
			final List<Integer> attributeValueIdsToMatch = storageAttributesKeyToMatch.getAttributeValueIds();

			return attributeValueIdsToMatch.containsAll(attributeValueIdsOfThisInstance);
		}

		public void addQty(BigDecimal qtyToAdd)
		{
			this.qty = this.qty.add(qtyToAdd);
		}
	}

	public void addQtyToMatchedGroups(
			@NonNull final BigDecimal qty,
			final int productId,
			@NonNull final AttributesKey storageAttributesKey)
	{
		for (ResultGroup group : resultGroups)
		{
			if (group.matches(productId, storageAttributesKey))
			{
				group.addQty(qty);
			}
		}
	}
}
