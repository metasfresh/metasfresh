package de.metas.shipper.gateway.spi;

import de.metas.externalsystem.ExternalSystemId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ShipperConfigRequest
{
	@Nullable ExternalSystemId externalSystemId;
}
