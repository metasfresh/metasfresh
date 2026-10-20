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
	 * Catch-weight scan required, effective: setting on AND main finished good AND catch-weight product AND catch weight captured at receipt.
	 */
	boolean catchWeightScanRequired;
}
