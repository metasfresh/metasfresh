package de.metas.handlingunits.inventory.draftlinescreator.aggregator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.inventory.AggregationType;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

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
		return new SingleHUInventoryLineInventoryLineAggregationKey(huForInventoryLine.getHuId(), huForInventoryLine.getHuQRCode(), huForInventoryLine.getProductId());
	}

	@Override
	public InventoryLineAggregationKey createAggregationKey(@NonNull final InventoryLine inventoryLine)
	{
		final InventoryLineHU singleLineHU = inventoryLine.getSingleLineHU();
		return new SingleHUInventoryLineInventoryLineAggregationKey(singleLineHU.getHuId(), singleLineHU.getHuQRCode(), inventoryLine.getProductId());
	}

	@Override
	public AggregationType getAggregationType()
	{
		return AggregationType.SINGLE_HU;
	}

	@Value
	private static class SingleHUInventoryLineInventoryLineAggregationKey implements InventoryLineAggregationKey
	{
		@Nullable HuId huId;
		@Nullable HUQRCode huQRCode;
		@NonNull ProductId productId;

		SingleHUInventoryLineInventoryLineAggregationKey(
				@Nullable final HuId huId,
				@Nullable final HUQRCode huQRCode,
				@NonNull final ProductId productId)
		{
			if (huId == null && huQRCode == null)
			{
				throw new AdempiereException("At least huId or huQRCode must be set");
			}

			this.huId = huId;
			this.huQRCode = huQRCode;
			this.productId = productId;
		}
	}
}
