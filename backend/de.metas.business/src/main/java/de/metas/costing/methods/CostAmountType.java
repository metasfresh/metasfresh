package de.metas.costing.methods;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum CostAmountType implements ReferenceListAwareEnum
{
	MAIN("M"),
	ADJUSTMENT("A"),
	ALREADY_SHIPPED("S"),
	;

	@Getter @NonNull final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<CostAmountType> index = ReferenceListAwareEnums.index(values());

	public static CostAmountType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isMain() {return MAIN.equals(this);}

	public boolean isAdjustment() {return ADJUSTMENT.equals(this);}

	public boolean isAlreadyShipped() {return ALREADY_SHIPPED.equals(this);}
}
