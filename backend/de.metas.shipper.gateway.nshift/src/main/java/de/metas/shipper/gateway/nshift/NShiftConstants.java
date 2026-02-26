package de.metas.shipper.gateway.nshift;

import de.metas.shipping.ShipperGatewayId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NShiftConstants
{
	public static final ShipperGatewayId SHIPPER_GATEWAY_ID = ShipperGatewayId.ofString("nshift");
}
