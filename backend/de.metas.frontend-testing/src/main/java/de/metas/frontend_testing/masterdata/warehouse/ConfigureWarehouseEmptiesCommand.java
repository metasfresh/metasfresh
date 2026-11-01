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

import javax.annotation.Nullable;
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
		// Resolved ONCE per execute(): every warehouse carrying `empties` in the same request must end up on the
		// SAME network. Re-querying per warehouse would make the "never a second empties network" invariant depend
		// on the repository cache being invalidated between two iterations of the same transaction.
		DistributionNetworkId emptiesNetworkId = null;

		for (final Map.Entry<String, JsonWarehouseRequest> entry : requests.entrySet())
		{
			final Identifier warehouseIdentifier = Identifier.ofString(entry.getKey());
			final CreateDistributionNetworkRequest.Line line = extractLineOrNull(warehouseIdentifier, entry.getValue());
			if (line == null)
			{
				continue;
			}

			if (emptiesNetworkId == null)
			{
				emptiesNetworkId = getOrCreateEmptiesNetworkId(warehouseIdentifier, line);
			}
			else
			{
				distributionNetworkRepository.addLine(emptiesNetworkId, line);
			}
		}
	}

	@Nullable
	private CreateDistributionNetworkRequest.Line extractLineOrNull(
			@NonNull final Identifier warehouseIdentifier,
			@NonNull final JsonWarehouseRequest request)
	{
		final JsonWarehouseRequest.Empties empties = request.getEmpties();
		if (empties == null)
		{
			return null;
		}

		return CreateDistributionNetworkRequest.Line.builder()
				.sourceWarehouseId(context.getId(warehouseIdentifier, WarehouseId.class))
				.targetWarehouseId(context.getId(empties.getToWarehouse(), WarehouseId.class))
				.shipperId(context.getId(empties.getShipper(), ShipperId.class))
				.build();
	}

	/** Adds {@code line} to the client's empties network, creating that network (with the line) when there is none. */
	@NonNull
	private DistributionNetworkId getOrCreateEmptiesNetworkId(
			@NonNull final Identifier warehouseIdentifier,
			@NonNull final CreateDistributionNetworkRequest.Line line)
	{
		final DistributionNetworkId existingNetworkId = distributionNetworkRepository.getEmptiesDistributionNetworkIfExists()
				.map(DistributionNetwork::getId)
				.orElse(null);

		if (existingNetworkId != null)
		{
			distributionNetworkRepository.addLine(existingNetworkId, line);
			return existingNetworkId;
		}

		return distributionNetworkRepository.createNetwork(
				CreateDistributionNetworkRequest.builder()
						.orgId(MasterdataContext.ORG_ID)
						.name(warehouseIdentifier.toUniqueString())
						.huDestroyed(true)
						.line(line)
						.build());
	}
}
