package de.metas.mobileui.inventory_disposal;

import de.metas.i18n.ITranslatableString;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HUStorageInfo
{
	@NonNull ITranslatableString productName;
	@NonNull Quantity qty;
}
