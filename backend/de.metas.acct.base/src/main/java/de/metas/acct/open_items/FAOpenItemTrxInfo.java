package de.metas.acct.open_items;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class FAOpenItemTrxInfo
{
	@NonNull FAOpenItemTrxType trxType;
	@NonNull FAOpenItemKey key;

	public static FAOpenItemTrxInfo openItem(@NonNull FAOpenItemKey key)
	{
		return builder().trxType(FAOpenItemTrxType.OPEN_ITEM).key(key).build();
	}

	public static FAOpenItemTrxInfo clearing(@NonNull FAOpenItemKey key)
	{
		return builder().trxType(FAOpenItemTrxType.CLEARING).key(key).build();
	}
}
