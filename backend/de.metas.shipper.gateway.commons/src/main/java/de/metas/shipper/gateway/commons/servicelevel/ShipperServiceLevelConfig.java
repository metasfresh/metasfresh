package de.metas.shipper.gateway.commons.servicelevel;

import de.metas.externalsystem.ExternalSystemId;
import de.metas.shipping.ShipperId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Builder
@Value
public class ShipperServiceLevelConfig
{
	@NonNull ShipperServiceLevelConfigId id;
	@NonNull ShipperId shipperId;
	@NonNull SeqNo seqNo;
	@Nullable ExternalSystemId externalSystemId;
	@NonNull String serviceLevel;
}
