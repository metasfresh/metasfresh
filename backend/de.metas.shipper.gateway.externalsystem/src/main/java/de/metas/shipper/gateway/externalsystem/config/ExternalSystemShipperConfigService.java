package de.metas.shipper.gateway.externalsystem.config;

import de.metas.i18n.ExplainedOptional;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ExternalSystemShipperConfigService
{
	@NonNull private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	public ExternalSystemShipperConfig getConfig(@NonNull final ShipperId shipperId)
	{
		// TODO
		return ExternalSystemShipperConfig.builder()
				.shipperGatewayId(shipperDAO.getShipperGatewayId(shipperId).orElseThrow())
				.build();
	}

}
