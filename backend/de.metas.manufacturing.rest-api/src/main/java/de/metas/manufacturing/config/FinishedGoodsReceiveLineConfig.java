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
	 */
	boolean catchWeightScanRequired;
}
