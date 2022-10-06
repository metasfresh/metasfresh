package de.metas.handlingunits.inventory.draftlinescreator.aggregator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.inventory.AggregationType;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

public class SingleHUInventoryLineAggregator implements InventoryLineAggregator
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
		@NonNull HuId huId;
		@NonNull ProductId productId;
	}
}
