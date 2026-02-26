package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.HuId;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonHUConsolidationJobPickingSlotContent
{
	@NonNull PickingSlotId pickingSlotId;
	@NonNull JsonDisplayableQRCode pickingSlotQRCode;
	@NonNull List<Item> items;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Item
	{
		@NonNull HuId huId;
		@NonNull String displayName;
		@NonNull String packingInfo;
		@NonNull List<ItemStorage> storages;
	}

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class ItemStorage
	{
		@NonNull String productName;
		@NonNull BigDecimal qty;
		@NonNull String uom;
	}

}
