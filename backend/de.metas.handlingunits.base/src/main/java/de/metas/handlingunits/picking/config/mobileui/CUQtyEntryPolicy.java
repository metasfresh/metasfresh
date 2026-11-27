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

	/** @return true if the line is counted in CUs (one booking = CUs into the open TU), also when its packing is a TU packing */
	public boolean isPickedInCUs() {return isLabelRequired;}

	/**
	 * Manual keeps the refusal text it always had (the parameters appended), so existing users see no change.
	 * In the label-required modes the picker's handheld shows the refusal in a toast of limited height: it shows only the localized message, and the parameters go to the log.
	 *
	 * @return true if a refusal shows its parameters to the picker (Manual); false in the label-required modes
	 */
	public boolean isRefusalParametersShownToPicker() {return !isLabelRequired;}

	/** @return true if a pick above the line's open quantity is refused whatever the profile's over-pick setting */
	public boolean isOverPickAlwaysRefused() {return isPackIdRequired;}

	/** @return true if every scanned label books exactly one CU */
	public boolean isOneCUPerLabel() {return isPackIdRequired;}

	public BigDecimal getQtyToPickCUs(final BigDecimal clientQty) {return isOneCUPerLabel() ? BigDecimal.ONE : clientQty;}
}
