package de.metas.frontend_testing.masterdata.warehouse;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.material.planning.ddorder.CreateDistributionNetworkRequest;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import java.util.Map;

/**
 * Post-pass over the {@code warehouses} section, applying {@link JsonWarehouseRequest#getEmpties()}.
 * It runs after the {@code shippers} section (the network line needs a shipper) and after every warehouse exists
 * (the empties target is named by identifier).
 * <p>
 * There can be only ONE empties distribution network per client — {@code DistributionNetworkRepository#getEmptiesDistributionNetwork()}
 * throws when it finds more than one — so this adds a LINE to the existing one instead of creating a second network,
 * and only creates the network when the client has none at all.
 */
@Builder
public class ConfigureWarehouseEmptiesCommand
{
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonWarehouseRequest> requests;

	public void execute()
	{
		requests.forEach(this::configure);
	}

	private void configure(@NonNull final String warehouseIdentifierStr, @NonNull final JsonWarehouseRequest request)
	{
		final JsonWarehouseRequest.Empties empties = request.getEmpties();
		if (empties == null)
		{
			return;
		}

		final Identifier warehouseIdentifier = Identifier.ofString(warehouseIdentifierStr);
		final CreateDistributionNetworkRequest.Line line = CreateDistributionNetworkRequest.Line.builder()
				.sourceWarehouseId(context.getId(warehouseIdentifier, WarehouseId.class))
				.targetWarehouseId(context.getId(empties.getToWarehouse(), WarehouseId.class))
				.shipperId(context.getId(empties.getShipper(), ShipperId.class))
				.build();

		final DistributionNetworkId networkId = distributionNetworkRepository.getEmptiesDistributionNetworkIfExists()
				.map(DistributionNetwork::getId)
				.orElse(null);

		if (networkId != null)
		{
			distributionNetworkRepository.addLine(networkId, line);
		}
		else
		{
			distributionNetworkRepository.createNetwork(
					CreateDistributionNetworkRequest.builder()
							.orgId(MasterdataContext.ORG_ID)
							.name(warehouseIdentifier.toUniqueString())
							.huDestroyed(true)
							.line(line)
							.build());
		}
	}
}
