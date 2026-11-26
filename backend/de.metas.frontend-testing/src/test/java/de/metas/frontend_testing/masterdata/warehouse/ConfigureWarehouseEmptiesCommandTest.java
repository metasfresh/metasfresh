package de.metas.frontend_testing.masterdata.warehouse;

import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkLine;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.shipping.ShipperId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ConfigureWarehouseEmptiesCommandTest
{
	private static final WarehouseId WH1_ID = WarehouseId.ofRepoId(100);
	private static final WarehouseId WH2_ID = WarehouseId.ofRepoId(200);
	private static final WarehouseId EMPTIES_WH_ID = WarehouseId.ofRepoId(300);
	private static final ShipperId SHIPPER_ID = ShipperId.ofRepoId(540001);

	private DistributionNetworkRepository distributionNetworkRepository;
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		distributionNetworkRepository = new DistributionNetworkRepository();

		context = new MasterdataContext();
		context.putIdentifier(Identifier.ofString("WH1"), WH1_ID);
		context.putIdentifier(Identifier.ofString("WH2"), WH2_ID);
		context.putIdentifier(Identifier.ofString("WHEMPTIES"), EMPTIES_WH_ID);
		context.putIdentifier(Identifier.ofString("SHIPPER"), SHIPPER_ID);
	}

	private static JsonWarehouseRequest emptiesTo(final String toWarehouse)
	{
		return JsonWarehouseRequest.builder()
				.empties(JsonWarehouseRequest.Empties.builder()
						.toWarehouse(Identifier.ofString(toWarehouse))
						.shipper(Identifier.ofString("SHIPPER"))
						.build())
				.build();
	}

	private void execute(final Map<String, JsonWarehouseRequest> requests)
	{
		ConfigureWarehouseEmptiesCommand.builder()
				.distributionNetworkRepository(distributionNetworkRepository)
				.context(context)
				.requests(requests)
				.build()
				.execute();
	}

	@Test
	public void twoWarehousesWithEmptiesInOneRequest_shareTheSingleEmptiesNetwork()
	{
		execute(ImmutableMap.of(
				"WH1", emptiesTo("WHEMPTIES"),
				"WH2", emptiesTo("WHEMPTIES"),
				"WHEMPTIES", JsonWarehouseRequest.builder().build()));

		// getEmptiesDistributionNetworkIfExists throws when there is more than one, so this both
		// finds the network AND asserts that only one was created
		final DistributionNetwork network = distributionNetworkRepository.getEmptiesDistributionNetworkIfExists()
				.orElseThrow(() -> new AssertionError("an empties distribution network shall have been created"));

		assertThat(network.getLines())
				.extracting(DistributionNetworkLine::getSourceWarehouseId, DistributionNetworkLine::getTargetWarehouseId, DistributionNetworkLine::getShipperId)
				.containsExactlyInAnyOrder(
						tuple(WH1_ID, EMPTIES_WH_ID, SHIPPER_ID),
						tuple(WH2_ID, EMPTIES_WH_ID, SHIPPER_ID));
	}

	@Test
	public void noWarehouseWithEmpties_createsNoNetwork()
	{
		execute(ImmutableMap.of("WH1", JsonWarehouseRequest.builder().build()));

		assertThat(distributionNetworkRepository.getEmptiesDistributionNetworkIfExists()).isEmpty();
	}
}
