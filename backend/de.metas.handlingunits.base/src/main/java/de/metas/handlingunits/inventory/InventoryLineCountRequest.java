package de.metas.handlingunits.inventory;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;

@Value
@Builder
public class InventoryLineCountRequest
{
	@Nullable HuId huId;
	@Nullable ScannedCode scannedCode;
	@NonNull Quantity qtyBook;
	@NonNull Quantity qtyCount;
	@NonNull @Builder.Default AttributeSetInstanceId asiId = AttributeSetInstanceId.NONE;
}
