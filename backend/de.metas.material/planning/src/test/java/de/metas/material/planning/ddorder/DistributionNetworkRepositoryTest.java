package de.metas.material.planning.ddorder;

import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

public class DistributionNetworkRepositoryTest
{
	private DistributionNetworkRepository repository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new DistributionNetworkRepository();
	}

	private CreateDistributionNetworkRequest.CreateDistributionNetworkRequestBuilder network(final String name)
	{
		return CreateDistributionNetworkRequest.builder()
				.orgId(OrgId.ofRepoId(1000000))
				.name(name);
	}

	private CreateDistributionNetworkRequest.Line line(final int sourceWarehouseId, final int targetWarehouseId)
	{
		return CreateDistributionNetworkRequest.Line.builder()
				.sourceWarehouseId(WarehouseId.ofRepoId(sourceWarehouseId))
				.targetWarehouseId(WarehouseId.ofRepoId(targetWarehouseId))
				.shipperId(ShipperId.ofRepoId(540001))
				.build();
	}

	@Test
	public void createNetwork_withoutLines_isNotAnEmptiesNetworkByDefault()
	{
		final DistributionNetworkId networkId = repository.createNetwork(network("plain").build());

		final DistributionNetwork network = repository.getById(networkId);
		assertThat(network.getName()).isEqualTo("plain");
		assertThat(network.isEmptiesDistributionNetwork()).isFalse();
		assertThat(network.getLines()).isEmpty();
		assertThat(repository.getEmptiesDistributionNetworkIfExists()).isEmpty();
	}

	@Test
	public void createNetwork_huDestroyed_isFoundAsTheEmptiesNetwork()
	{
		final DistributionNetworkId networkId = repository.createNetwork(
				network("empties")
						.huDestroyed(true)
						.line(line(100, 200))
						.build());

		final Optional<DistributionNetwork> empties = repository.getEmptiesDistributionNetworkIfExists();
		assertThat(empties).map(DistributionNetwork::getId).contains(networkId);

		final DistributionNetworkLine line = empties.get().getLines().get(0);
		assertThat(line.getSourceWarehouseId()).isEqualTo(WarehouseId.ofRepoId(100));
		assertThat(line.getTargetWarehouseId()).isEqualTo(WarehouseId.ofRepoId(200));
		assertThat(line.getShipperId()).isEqualTo(ShipperId.ofRepoId(540001));
	}

	@Test
	public void addLine_isVisibleImmediately()
	{
		final DistributionNetworkId networkId = repository.createNetwork(network("empties").huDestroyed(true).build());
		assertThat(repository.getById(networkId).getLines()).isEmpty();

		repository.addLine(networkId, line(300, 400));

		assertThat(repository.getById(networkId).getLines())
				.extracting(DistributionNetworkLine::getSourceWarehouseId, DistributionNetworkLine::getTargetWarehouseId)
				.containsExactly(tuple(WarehouseId.ofRepoId(300), WarehouseId.ofRepoId(400)));
	}

	@Test
	public void getEmptiesDistributionNetworkIfExists_throwsOnMoreThanOne()
	{
		repository.createNetwork(network("empties1").huDestroyed(true).build());
		repository.createNetwork(network("empties2").huDestroyed(true).build());

		assertThatThrownBy(() -> repository.getEmptiesDistributionNetworkIfExists())
				.hasMessageContaining("Multiple empties distribution networks found");
	}
}
