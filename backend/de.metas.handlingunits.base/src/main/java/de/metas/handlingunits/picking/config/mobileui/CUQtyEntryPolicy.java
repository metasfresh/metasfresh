package de.metas.handlingunits.picking.config.mobileui;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * What a {@link CUQtyEntryMode} means for a pick.
 * Each query is a separate rule with its own name; several of them are answered by the same flag, which is not a claim that they must always be equal.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CUQtyEntryPolicy
{
	public static final CUQtyEntryPolicy MANUAL = new CUQtyEntryPolicy(false, false);
	public static final CUQtyEntryPolicy SCAN_EACH_CU = new CUQtyEntryPolicy(true, true);
	public static final CUQtyEntryPolicy SCAN_ONCE_ENTER_QTY = new CUQtyEntryPolicy(true, false);

	private final boolean isLabelRequired;
	private final boolean isPackIdRequired;

	public boolean isLabelRequired() {return isLabelRequired;}

	public boolean isPickWholeTUAllowed() {return !isLabelRequired;}

	public boolean isScanOnce() {return isLabelRequired && !isPackIdRequired;}

	public boolean isBookAsSingleCUInTU() {return isLabelRequired;}

	public boolean isPackIdRequired() {return isPackIdRequired;}

	/** @return true if a pick above the line's open quantity is refused whatever the profile's over-pick setting */
	public boolean isOverPickAlwaysRefused() {return isPackIdRequired;}

	/** @return true if every scanned label books exactly one CU */
	public boolean isOneCUPerLabel() {return isPackIdRequired;}

	public BigDecimal getQtyToPickCUs(final BigDecimal clientQty) {return isPackIdRequired ? BigDecimal.ONE : clientQty;}
}
