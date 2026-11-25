package de.metas.handlingunits.picking.config.mobileui;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/** What a {@link CUQtyEntryMode} means for a pick; the mobile app reads it via {@code JsonCUQtyEntryPolicy} instead of knowing the modes. */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CUQtyEntryPolicy
{
	public static final CUQtyEntryPolicy MANUAL = new CUQtyEntryPolicy(false, false);
	public static final CUQtyEntryPolicy SCAN_EACH_CU = new CUQtyEntryPolicy(true, true);
	public static final CUQtyEntryPolicy SCAN_ONCE_ENTER_QTY = new CUQtyEntryPolicy(true, false);

	private final boolean isLabelScanned;
	private final boolean isPackIdUsed;

	public boolean isLabelRequired() {return isLabelScanned;}

	public boolean isPickWholeTUAllowed() {return !isLabelScanned;}

	public boolean isScanOnce() {return isLabelScanned && !isPackIdUsed;}

	public boolean isBookedAsSingleCUInTU() {return isLabelScanned;}

	public boolean isPackIdRequired() {return isPackIdUsed;}

	/** @return true if a pick above the line's open quantity is refused whatever the profile's over-pick setting */
	public boolean isOverPickAlwaysRefused() {return isPackIdUsed;}

	public BigDecimal getQtyToPickCUs(final BigDecimal clientQty) {return isPackIdUsed ? BigDecimal.ONE : clientQty;}
}
