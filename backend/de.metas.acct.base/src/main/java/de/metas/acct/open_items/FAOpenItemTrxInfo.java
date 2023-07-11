package de.metas.acct.open_items;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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

	public static boolean equals(@Nullable FAOpenItemTrxInfo o1, @Nullable FAOpenItemTrxInfo o2) {return Objects.equals(o1, o2);}
}
