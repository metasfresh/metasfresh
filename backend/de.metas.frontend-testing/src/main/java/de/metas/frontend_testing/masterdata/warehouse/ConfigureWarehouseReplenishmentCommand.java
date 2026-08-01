package de.metas.frontend_testing.masterdata.warehouse;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.material.planning.ddorder.CreateDistributionNetworkRequest;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseRepository;

import java.util.Map;

/**
 * Post-pass over the {@code warehouses} section, applying {@link JsonWarehouseRequest#getReplenishment()} and
 * {@link JsonWarehouseRequest#isAutoDistributionOrder()}. It runs after the {@code shippers} section (the network line
 * needs a shipper) and after every warehouse exists (the replenishment source is named by identifier).
 */
@Builder
public class ConfigureWarehouseReplenishmentCommand
{
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonWarehouseRequest> requests;
	@NonNull private final WarehouseRepository warehouseReplenishmentRepository;

	public void execute()
	{
		requests.forEach(this::configure);
	}

	private void configure(@NonNull final String warehouseIdentifierStr, @NonNull final JsonWarehouseRequest request)
	{
		final JsonWarehouseRequest.Replenishment replenishment = request.getReplenishment();
		if (replenishment == null && !request.isAutoDistributionOrder())
		{
			return;
		}

		final Identifier warehouseIdentifier = Identifier.ofString(warehouseIdentifierStr);
		final WarehouseId warehouseId = context.getId(warehouseIdentifier, WarehouseId.class);

		final DistributionNetworkId networkId = replenishment != null
				? createNetwork(warehouseIdentifier, warehouseId, replenishment)
				: null;

		warehouseReplenishmentRepository.updateReplenishment(warehouseId, networkId, request.isAutoDistributionOrder());
	}

	private DistributionNetworkId createNetwork(
			@NonNull final Identifier warehouseIdentifier,
			@NonNull final WarehouseId warehouseId,
			@NonNull final JsonWarehouseRequest.Replenishment replenishment)
	{
		return distributionNetworkRepository.createNetwork(
				CreateDistributionNetworkRequest.builder()
						.orgId(MasterdataContext.ORG_ID)
						.name(warehouseIdentifier.toUniqueString())
						.line(CreateDistributionNetworkRequest.Line.builder()
								.targetWarehouseId(warehouseId)
								.sourceWarehouseId(context.getId(replenishment.getFromWarehouse(), WarehouseId.class))
								.shipperId(context.getId(replenishment.getShipper(), ShipperId.class))
								.build())
						.build());
	}
}
