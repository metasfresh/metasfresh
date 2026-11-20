package de.metas.handlingunits.picking.config.mobileui;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * What a {@link CUQtyEntryMode} means for a pick. The single place that answers "does this mode do X?", so the pick code asks the policy
 * instead of comparing modes (one more mode means one more constant here, not one more {@code if} per call site).
 * <p>
 * {@link #MANUAL} is the classic entry: every question below is answered "as before this feature".
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CUQtyEntryPolicy
{
	/** The classic manual entry. */
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

	/** @return true if every pick is booked as ONE new CU inside the open box (the CU is the picked HU, not the box) */
	public boolean isBookedAsSingleCUInBox() {return isLabelScanned;}

	/** @return true if the label must carry a pack ID: it is stored on the CU and a repeated (product, lot, pack ID) in the job is refused */
	public boolean isPackIdRequired() {return isPackIdUsed;}

	/** @return the quantity of CUs to book for the given client quantity: one pack per label when a pack ID is required */
	public BigDecimal getQtyToPickCUs(final BigDecimal clientQty) {return isPackIdUsed ? BigDecimal.ONE : clientQty;}
}
