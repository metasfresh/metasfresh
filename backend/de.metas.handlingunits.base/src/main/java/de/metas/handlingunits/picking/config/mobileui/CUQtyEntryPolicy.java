package de.metas.handlingunits.picking.config.mobileui;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * What a {@link CUQtyEntryMode} means for a pick. The single place that answers "does this mode do X?", so the pick code asks the policy
 * instead of comparing modes (one more mode means one more constant here, not one more {@code if} per call site).
 * <p>
 * {@link #MANUAL} enters the quantity by hand: every question below is answered with "no label, no pack ID".
 * <p>
 * The policy is the single owner of these rules: the mobile app does not repeat them, it reads the policy published on the picking line.
 * Mobile app counterpart: {@code misc/services/mobile-webui/mobile-webui-frontend/src/apps/picking/cuQtyEntry/cuQtyEntryPolicy.js} reads the {@code JsonCUQtyEntryPolicy} published on each picking line.
 * When you add or change a query here that screens need, change {@code JsonCUQtyEntryPolicy} and that JS file together.
 * <p>
 * Several queries answer from the same flag on purpose: each names one decision of the pick code (intention-revealing interface).
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CUQtyEntryPolicy
{
	/** The quantity is entered by hand. */
	public static final CUQtyEntryPolicy MANUAL = new CUQtyEntryPolicy(false, false);
	/** One pack label per CU: the label carries a pack ID and one scan books exactly one CU. */
	public static final CUQtyEntryPolicy SCAN_EACH_CU = new CUQtyEntryPolicy(true, true);
	/** One pack label, then the quantity is typed: the label's pack ID is ignored. */
	public static final CUQtyEntryPolicy SCAN_ONCE_ENTER_QTY = new CUQtyEntryPolicy(true, false);

	private final boolean isLabelScanned;
	private final boolean isPackIdUsed;

	/**
	 * @return true if a pick needs an open box (TU) and a CU label scan: the label supplies lot / best-before, the picked HU is the booked
	 * CU inside the box (also the QR code owner), and a whole source TU is never picked at once
	 */
	public boolean isLabelRequired() {return isLabelScanned;}

	/** @return false: the booked CU goes into the open box, never a whole TU */
	public boolean isPickWholeTUAllowed() {return !isLabelScanned;}

	/** @return true if one label is scanned and the quantity is typed: the label's pack ID is ignored */
	public boolean isScanOnce() {return isLabelScanned && !isPackIdUsed;}

	/** @return true if every pick is booked as ONE CU inside the open box (the CU is the picked HU, not the box) */
	public boolean isBookedAsSingleCUInBox() {return isLabelScanned;}

	/** @return true if the label must carry a pack ID: it is stored on the CU and a repeated (product, lot, pack ID) in the job is refused */
	public boolean isPackIdRequired() {return isPackIdUsed;}

	/**
	 * @return true if a pick above the line's open quantity is refused whatever the profile's over-pick setting: one scan books one pack and the
	 * scan loop has no over-pick confirmation prompt, so once the line is done a further scan must book nothing
	 */
	public boolean isOverPickAlwaysRefused() {return isPackIdUsed;}

	/** @return the quantity of CUs to book for the given client quantity: one CU per label when a pack ID is required */
	public BigDecimal getQtyToPickCUs(final BigDecimal clientQty) {return isPackIdUsed ? BigDecimal.ONE : clientQty;}
}
