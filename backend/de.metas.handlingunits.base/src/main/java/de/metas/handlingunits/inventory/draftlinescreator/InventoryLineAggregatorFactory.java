package de.metas.handlingunits.inventory.draftlinescreator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocBaseAndSubType;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.inventory.AggregationType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class InventoryLineAggregatorFactory
{
	public InventoryLineAggregator createForDocBaseAndSubType(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		final AggregationType aggregationMode = AggregationType.getByDocTypeOrNull(docBaseAndSubType);
		Check.assumeNotNull(aggregationMode, "Unexpected docBaseAndSubType={} with no registered aggegationMode", docBaseAndSubType);

		try
		{
			return createForAggregationMode(aggregationMode);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("docBaseAndSubType", docBaseAndSubType);
		}
	}

	public InventoryLineAggregator createForAggregationMode(@NonNull final AggregationType aggregationMode)
	{
		switch (aggregationMode)
		{
			case SINGLE_HU:
				return SingleHUInventoryLineAggregator.INSTANCE;

			case MULTIPLE_HUS:
				return MultipleHUInventoryLineAggregator.INSTANCE;

			default:
				throw new AdempiereException("Unexpected aggegationMode: " + aggregationMode)
						.appendParametersToMessage();
		}
	}

	public static class SingleHUInventoryLineAggregator implements InventoryLineAggregator
	{
		@VisibleForTesting
		public static final SingleHUInventoryLineAggregator INSTANCE = new SingleHUInventoryLineAggregator();

		private SingleHUInventoryLineAggregator()
		{
		}

		@Override
		public InventoryLineAggregationKey createAggregationKey(@NonNull final HuForInventoryLine huForInventoryLine)
		{
			return new SingleHUInventoryLineInventoryLineAggregationKey(huForInventoryLine.getHuId(), huForInventoryLine.getProductId());
		}

		@Override
		public InventoryLineAggregationKey createAggregationKey(@NonNull final InventoryLine inventoryLine)
		{
			return new SingleHUInventoryLineInventoryLineAggregationKey(inventoryLine.getSingleLineHU().getHuIdNotNull(), inventoryLine.getProductId());
		}

		@Override
		public AggregationType getAggregationType()
		{
			return AggregationType.SINGLE_HU;
		}

		@Value
		public static class SingleHUInventoryLineInventoryLineAggregationKey implements InventoryLineAggregationKey
		{
			@NonNull
			HuId huId;

			@NonNull
			ProductId productId;
		}
	}

	public static class MultipleHUInventoryLineAggregator implements InventoryLineAggregator
	{
		@VisibleForTesting
		public static final MultipleHUInventoryLineAggregator INSTANCE = new MultipleHUInventoryLineAggregator();

		private MultipleHUInventoryLineAggregator()
		{
		}

		@Override
		public InventoryLineAggregationKey createAggregationKey(@NonNull final HuForInventoryLine huForInventoryLine)
		{
			final Quantity qty = CoalesceUtil.coalesce(huForInventoryLine.getQuantityCount(), huForInventoryLine.getQuantityBooked());

			final UomId uomId = qty == null ? null : qty.getUomId();
			return new MultipleHUInventoryLineInventoryLineAggregationKey(
					huForInventoryLine.getProductId(),
					uomId,
					huForInventoryLine.getStorageAttributesKey(),
					huForInventoryLine.getLocatorId());
		}

		@Override
		public InventoryLineAggregationKey createAggregationKey(@NonNull final InventoryLine inventoryLine)
		{
			final Quantity qty = CoalesceUtil.coalesce(inventoryLine.getQtyCount(), inventoryLine.getQtyBook());

			final UomId uomId = qty == null ? null : qty.getUomId();

			return new MultipleHUInventoryLineInventoryLineAggregationKey(
					inventoryLine.getProductId(),
					uomId,
					inventoryLine.getStorageAttributesKey(),
					inventoryLine.getLocatorId());
		}

		@Override
		public AggregationType getAggregationType()
		{
			return AggregationType.MULTIPLE_HUS;
		}

		@Value
		public static class MultipleHUInventoryLineInventoryLineAggregationKey implements InventoryLineAggregationKey
		{
			@NonNull
			ProductId productId;

			// needed for the case that stocking UOM has changed and there are still HUs with an old UOM
			@Nullable
			UomId uomId;

			@NonNull
			AttributesKey storageAttributesKey;

			@NonNull
			LocatorId locatorId;
		}
	}
}
