package de.metas.acct.open_items;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_Fact_Acct;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum FAOpenItemTrxType implements ReferenceListAwareEnum
{
	OPEN_ITEM(X_Fact_Acct.OI_TRXTYPE_OpenItem),
	CLEARING(X_Fact_Acct.OI_TRXTYPE_Clearing),
	;

	@Getter @NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<FAOpenItemTrxType> index = ReferenceListAwareEnums.index(values());

	public static FAOpenItemTrxType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static FAOpenItemTrxType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public boolean isClearing() {return CLEARING.equals(this);}
}
