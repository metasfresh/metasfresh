package de.metas.handlingunits.picking.config.mobileui;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

import static org.compiere.model.X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_MANUAL;
import static org.compiere.model.X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_EACH_CU;
import static org.compiere.model.X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_ONCE_ENTER_QTY;

/**
 * How the CU quantity is entered when picking (column {@code MobileUI_UserProfile_Picking.CUQtyEntryMode}).
 */
@AllArgsConstructor
@Getter
public enum CUQtyEntryMode implements ReferenceListAwareEnum
{
	MANUAL(CUQTYENTRYMODE_MANUAL, CUQtyEntryPolicy.MANUAL),
	SCAN_EACH_CU(CUQTYENTRYMODE_SCAN_EACH_CU, CUQtyEntryPolicy.SCAN_EACH_CU),
	SCAN_ONCE_ENTER_QTY(CUQTYENTRYMODE_SCAN_ONCE_ENTER_QTY, CUQtyEntryPolicy.SCAN_ONCE_ENTER_QTY);

	public static final CUQtyEntryMode DEFAULT = MANUAL;

	private static final ReferenceListAwareEnums.ValuesIndex<CUQtyEntryMode> index = ReferenceListAwareEnums.index(values());

	private final String code;

	/** what this mode means for a pick; ask it instead of comparing modes */
	@NonNull private final CUQtyEntryPolicy policy;

	@NonNull
	public static CUQtyEntryMode ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static CUQtyEntryMode ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
}
