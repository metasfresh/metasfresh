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
}
