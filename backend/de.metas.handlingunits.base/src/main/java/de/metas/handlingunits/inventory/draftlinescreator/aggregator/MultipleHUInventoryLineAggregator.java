package de.metas.handlingunits.inventory.draftlinescreator.aggregator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.inventory.AggregationType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

public class MultipleHUInventoryLineAggregator implements InventoryLineAggregator
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

		return MultipleHUInventoryLineInventoryLineAggregationKey.builder()
				.productId(huForInventoryLine.getProductId())
				.uomId(uomId)
				.storageAttributesKey(huForInventoryLine.getStorageAttributesKey())
				.locatorId(huForInventoryLine.getLocatorId())
				.build();
	}

	@Override
	public InventoryLineAggregationKey createAggregationKey(@NonNull final InventoryLine inventoryLine)
	{
		final Quantity qty = CoalesceUtil.coalesce(inventoryLine.getQtyCount(), inventoryLine.getQtyBook());
		final UomId uomId = qty == null ? null : qty.getUomId();

		return MultipleHUInventoryLineInventoryLineAggregationKey.builder()
				.productId(inventoryLine.getProductId())
				.uomId(uomId)
				.storageAttributesKey(AttributesKeys.createAttributesKeyFromASIStorageAttributes(inventoryLine.getAsiId()).orElse(AttributesKey.NONE))
				.locatorId(inventoryLine.getLocatorId())
				.build();
	}

	@Override
	public AggregationType getAggregationType()
	{
		return AggregationType.MULTIPLE_HUS;
	}

	@Value
	@Builder
	private static class MultipleHUInventoryLineInventoryLineAggregationKey implements InventoryLineAggregationKey
	{
		@NonNull ProductId productId;
		// needed for the case that stocking UOM has changed, and there are still HUs with an old UOM
		@Nullable UomId uomId;
		@NonNull AttributesKey storageAttributesKey;
		@NonNull LocatorId locatorId;
	}
}
