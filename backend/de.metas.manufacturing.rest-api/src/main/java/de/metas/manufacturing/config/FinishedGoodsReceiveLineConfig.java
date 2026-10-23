package de.metas.manufacturing.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FinishedGoodsReceiveLineConfig
{
	boolean allowReceiveToLU;
	boolean allowReceiveToTU;
	boolean skipReceiveTargetStep;
	boolean captureCatchWeight;
	/**
	 * Catch-weight scan required, effective: setting on AND main finished good AND catch-weight product.
	 * Does not depend on {@link #captureCatchWeight}: without it the scan fills lot and best-before date only.
	 * Whether a receipt is then one piece: {@link #isReceiveOnePiecePerScan()}.
	 */
	boolean catchWeightScanRequired;

	/**
	 * Each receipt is exactly one piece carrying its own scanned values: only when the weight is captured at receipt,
	 * because the scanned weight belongs to one block.
	 * <p>
	 * Without the weight at receipt the operator enters the number of blocks as in a normal receipt (CU, TU or LU) and scans
	 * the label of the last block; its lot and best-before date overwrite the defaults for the whole receipt
	 * (customer rule: the last block must be scanned, and the lot number and best-before date taken from its QR code
	 * overwrite the initially set values).
	 */
	public boolean isReceiveOnePiecePerScan()
	{
		return catchWeightScanRequired && captureCatchWeight;
	}
}
