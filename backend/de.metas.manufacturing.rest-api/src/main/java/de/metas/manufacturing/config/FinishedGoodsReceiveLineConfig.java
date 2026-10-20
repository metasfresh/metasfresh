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
	 * Effective: setting on AND main finished good AND catch-weight product AND catch weight captured at receipt.
	 * Then every receipt is exactly one piece carrying its own scanned weight, lot and best-before date.
	 */
	boolean catchWeightScanRequired;
}
