package de.metas.costing.methods;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_CostDetail;

@RequiredArgsConstructor
@Getter
public enum CostAmountType implements ReferenceListAwareEnum
{
	MAIN(X_M_CostDetail.M_COSTDETAIL_TYPE_Main),
	ADJUSTMENT(X_M_CostDetail.M_COSTDETAIL_TYPE_CostAdjustment),
	ALREADY_SHIPPED(X_M_CostDetail.M_COSTDETAIL_TYPE_AlreadyShipped),
	;

	@NonNull final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<CostAmountType> index = ReferenceListAwareEnums.index(values());

	public static CostAmountType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isMain() {return MAIN.equals(this);}

	public boolean isAdjustment() {return ADJUSTMENT.equals(this);}

	@SuppressWarnings("unused")
	public boolean isAlreadyShipped() {return ALREADY_SHIPPED.equals(this);}
}
