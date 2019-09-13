package de.metas.contracts.commission.algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.CommissionInstance;
import de.metas.contracts.commission.CommissionPoints;
import de.metas.contracts.commission.CommissionShare;
import de.metas.contracts.commission.CommissionTrigger;
import de.metas.contracts.commission.CommissionTriggerChange;
import de.metas.contracts.commission.CommissionTriggerId;
import de.metas.contracts.commission.CreateInstanceRequest;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.CommissionState;
import de.metas.contracts.commission.hierarchy.Hierarchy;
import de.metas.contracts.commission.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.hierarchy.HierarchyNode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

class HierachyAlgorithmTest
{

	@Test
	void createInstance()
	{
		createInstance_performTest(new CommissionTriggerId());
	}

	private CommissionInstance createInstance_performTest(@NonNull final CommissionTriggerId commissionTriggerId)
	{
		final Beneficiary headOfSales = new Beneficiary(BPartnerId.ofRepoId(40));
		final HierarchyNode headOfSalesNode = HierarchyNode.of(headOfSales);

		final Beneficiary salesSupervisor = new Beneficiary(BPartnerId.ofRepoId(30));
		final HierarchyNode salesSupervisorNode = HierarchyNode.of(salesSupervisor);

		final Beneficiary salesRep = new Beneficiary(BPartnerId.ofRepoId(20));
		final HierarchyNode salesrepNode = HierarchyNode.of(salesRep);

		final Hierarchy hierarchy = Hierarchy.builder()
				.addChildren(headOfSalesNode, ImmutableList.of(salesSupervisorNode))
				.addChildren(salesSupervisorNode, ImmutableList.of(salesrepNode))
				.build();

		final CommissionTrigger trigger = CommissionTrigger.builder()
				.id(commissionTriggerId)
				.timestamp(Instant.now())
				.customer(new Customer(BPartnerId.ofRepoId(10)))
				.beneficiary(salesRep)
				.forecastedPoints(CommissionPoints.of("1000.00"))

				// it's uncommon to have a trigger with points beyond "forecasted"..but still should work
				.pointsToInvoice(CommissionPoints.of("100.00"))
				.invoicedPoints(CommissionPoints.of("10.00"))
				.build();

		final HierarchyConfig config = new HierarchyConfig();

		final CreateInstanceRequest request = CreateInstanceRequest.builder()
				.config(config)
				.hierarchy(hierarchy)
				.trigger(trigger)
				.build();

		// invoke the method under test
		final CommissionInstance instance = new HierachyAlgorithm().createInstance(request);

		assertThat(instance).isNotNull();
		assertThat(instance.getTrigger()).isEqualTo(trigger);
		assertThat(instance.getConfig()).isEqualTo(config);

		final ImmutableList<CommissionShare> shares = instance.getShares();
		assertThat(shares).hasSize(3);

		assertThat(shares.get(0)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(salesRep);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(0));
			assertThat(share).extracting("forecastedPointsSum", "pointsToInvoiceSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("100.00"), CommissionPoints.of("10.00"), CommissionPoints.of("1.00"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("100.00")),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("10.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("1.00")));
		});

		assertThat(shares.get(1)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(salesSupervisor);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(1));
			assertThat(share).extracting("forecastedPointsSum", "pointsToInvoiceSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("90.00"), CommissionPoints.of("9.00"), CommissionPoints.of("0.90"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("90.00")/* remaining commission base=900 */),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("9.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.90")));
		});

		assertThat(shares.get(2)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(headOfSales);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(2));
			assertThat(share).extracting("forecastedPointsSum", "pointsToInvoiceSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("81.00"), CommissionPoints.of("8.10"), CommissionPoints.of("0.81"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("81.00")/* remaining commission base=810 */),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("8.10")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.81")));
		});

		return instance;
	}

	@Test
	void applyTriggerChange()
	{
		final CommissionTriggerId commissionTriggerId = new CommissionTriggerId();

		final CommissionInstance instance = createInstance_performTest(commissionTriggerId);

		final CommissionTriggerChange change = CommissionTriggerChange.builder()
				.oldCommissionInstance(instance)
				.timestamp(Instant.now())
				.forecastedPoints(CommissionPoints.ZERO)
				.pointsToInvoice(CommissionPoints.ZERO)
				.invoicedPoints(CommissionPoints.of("1110.00"))
				.build();

		// invoke the method under test
		new HierachyAlgorithm().applyTriggerChange(change);

		assertThat(instance).isNotNull();
		final ImmutableList<CommissionShare> shares = instance.getShares();
		assertThat(shares).hasSize(3);

		assertThat(shares.get(0)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "pointsToInvoiceSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("111.00"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("100.00")),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("10.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("1.00")),

							tuple(CommissionState.FORECASTED, CommissionPoints.of("-100.00")),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("-10.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("110.00")));
		});

		assertThat(shares.get(1)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "pointsToInvoiceSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("99.90"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("90.00")),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("9.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.90")),

							tuple(CommissionState.FORECASTED, CommissionPoints.of("-90.00")),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("-9.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("99.00")));
		});
		assertThat(shares.get(2)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "pointsToInvoiceSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("89.91"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("81.00")),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("8.10")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.81")),

							tuple(CommissionState.FORECASTED, CommissionPoints.of("-81.00")),
							tuple(CommissionState.TO_INVOICE, CommissionPoints.of("-8.10")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("89.10")));
		});
	}
}
