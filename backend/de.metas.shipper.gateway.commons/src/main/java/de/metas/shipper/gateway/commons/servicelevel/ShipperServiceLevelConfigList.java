package de.metas.shipper.gateway.commons.servicelevel;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.shipping.ShipperId;
import de.metas.shipper.gateway.spi.ShipperConfigRequest;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShipperServiceLevelConfigList
{
	public static final ShipperServiceLevelConfigList EMPTY = new ShipperServiceLevelConfigList(ImmutableList.of());

	@NonNull private final ImmutableList<ShipperServiceLevelConfig> list;

	private ShipperServiceLevelConfigList(@NonNull final ImmutableList<ShipperServiceLevelConfig> list) {this.list = list;}

	public static ShipperServiceLevelConfigList ofCollection(@NonNull final Collection<ShipperServiceLevelConfig> list)
	{
		return list.isEmpty() ? EMPTY : new ShipperServiceLevelConfigList(ImmutableList.copyOf(list));
	}

	public ShipperServiceLevelConfigList subsetOf(@NonNull final ShipperId shipperId)
	{
		return ofCollection(list.stream()
				.filter(config -> ShipperId.equals(shipperId, config.getShipperId()))
				.collect(Collectors.toList()));
	}

	public Optional<String> getEffectiveServiceLevel(@NonNull final ShipperConfigRequest request)
	{
		final ExternalSystemId externalSystemId = request.getExternalSystemId();

		if (externalSystemId != null)
		{
			final Optional<String> specific = list.stream()
					.filter(config -> ExternalSystemId.equals(externalSystemId, config.getExternalSystemId()))
					.map(ShipperServiceLevelConfig::getServiceLevel)
					.findFirst();
			if (specific.isPresent())
			{
				return specific;
			}
		}

		return list.stream()
				.filter(config -> config.getExternalSystemId() == null)
				.map(ShipperServiceLevelConfig::getServiceLevel)
				.findFirst();
	}
}
