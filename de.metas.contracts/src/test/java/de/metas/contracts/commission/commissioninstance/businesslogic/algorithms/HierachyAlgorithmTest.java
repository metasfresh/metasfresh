package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateInstanceRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionState;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.lang.Percent;
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
		createInstance_performTest(InvoiceCandidateId.ofRepoId(10));
	}

	private CommissionInstance createInstance_performTest(@NonNull final InvoiceCandidateId invoiceCandiateId)
	{
		final Beneficiary headOfSales = Beneficiary.of(BPartnerId.ofRepoId(40));
		final HierarchyNode headOfSalesNode = HierarchyNode.of(headOfSales);

		final Beneficiary salesSupervisor = Beneficiary.of(BPartnerId.ofRepoId(30));
		final HierarchyNode salesSupervisorNode = HierarchyNode.of(salesSupervisor);

		final Beneficiary salesRep = Beneficiary.of(BPartnerId.ofRepoId(20));
		final HierarchyNode salesrepNode = HierarchyNode.of(salesRep);

		final Hierarchy hierarchy = Hierarchy.builder()
				.addChildren(headOfSalesNode, ImmutableList.of(salesSupervisorNode))
				.addChildren(salesSupervisorNode, ImmutableList.of(salesrepNode))
				.build();

		final CommissionTriggerData triggerData = CommissionTriggerData.builder()
				.timestamp(Instant.now())
				.invoiceCandidateId(invoiceCandiateId)
				.forecastedPoints(CommissionPoints.of("1000.00"))
				// it's uncommon to have a trigger with points beyond "forecasted"..but still should work
				.invoiceablePoints(CommissionPoints.of("100.00"))
				.invoicedPoints(CommissionPoints.of("10.00"))
				.build();

		final CommissionTrigger trigger = CommissionTrigger.builder()
				.timestamp(Instant.now())
				.customer(Customer.of(BPartnerId.ofRepoId(10)))
				.beneficiary(salesRep)
				.commissionTriggerData(triggerData)
				.build();

		final HierarchyConfig config = HierarchyConfig.builder().subtractLowerLevelCommissionFromBase(true)
				.beneficiary2HierarchyContract(headOfSales, HierarchyContract.builder()
						.id(FlatrateTermId.ofRepoId(1))
						.commissionPercent(Percent.of(10))
						.pointsPrecision(2))
				.beneficiary2HierarchyContract(salesSupervisor, HierarchyContract.builder()
						.id(FlatrateTermId.ofRepoId(2))
						.commissionPercent(Percent.of(10))
						.pointsPrecision(2))
				.beneficiary2HierarchyContract(salesRep, HierarchyContract.builder()
						.id(FlatrateTermId.ofRepoId(3))
						.commissionPercent(Percent.of(10))
						.pointsPrecision(2))
				.build();

		final CreateInstanceRequest request = CreateInstanceRequest.builder()
				.config(config)
				.hierarchy(hierarchy)
				.trigger(trigger)
				.build();

		// invoke the method under test
		final CommissionInstance instance = new HierachyAlgorithm().createInstance(request);

		assertThat(instance).isNotNull();
		assertThat(instance.getCurrentTriggerData()).isEqualTo(triggerData);
		assertThat(instance.getConfig()).isEqualTo(config);

		final ImmutableList<SalesCommissionShare> shares = instance.getShares();
		assertThat(shares).hasSize(3);

		assertThat(shares.get(0)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(salesRep);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(0));
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("100.00"), CommissionPoints.of("10.00"), CommissionPoints.of("1.00"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("100.00")),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("10.00")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("1.00")));
		});

		assertThat(shares.get(1)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(salesSupervisor);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(1));
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("90.00"), CommissionPoints.of("9.00"), CommissionPoints.of("0.90"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("90.00")/* remaining commission base=900 */),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("9.00")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("0.90")));
		});

		assertThat(shares.get(2)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(headOfSales);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(2));
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("81.00"), CommissionPoints.of("8.10"), CommissionPoints.of("0.81"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("81.00")/* remaining commission base=810 */),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("8.10")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("0.81")));
		});

		return instance;
	}

	@Test
	void applyTriggerChange()
	{
		final InvoiceCandidateId invoiceCandiateId = InvoiceCandidateId.ofRepoId(10);
		final CommissionInstance instance = createInstance_performTest(invoiceCandiateId);

		final CommissionTriggerChange change = CommissionTriggerChange.builder()
				.instanceToUpdate(instance)
				.newCommissionTriggerData(CommissionTriggerData.builder()
						.invoiceCandidateId(invoiceCandiateId)
						.timestamp(Instant.now())
						.forecastedPoints(CommissionPoints.ZERO)
						.invoiceablePoints(CommissionPoints.ZERO)
						.invoicedPoints(CommissionPoints.of("1110.00"))
						.build())
				.build();

		// invoke the method under test
		new HierachyAlgorithm().applyTriggerChangeToShares(change);

		assertThat(instance).isNotNull();
		final ImmutableList<SalesCommissionShare> shares = instance.getShares();
		assertThat(shares).hasSize(3);

		assertThat(shares.get(0)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("111.00"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("100.00")),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("10.00")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("1.00")),

							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("-100.00")),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("-10.00")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("110.00")));
		});

		assertThat(shares.get(1)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("99.90"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("90.00")),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("9.00")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("0.90")),

							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("-90.00")),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("-9.00")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("99.00")));
		});
		assertThat(shares.get(2)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("89.91"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("81.00")),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("8.10")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("0.81")),

							tuple(SalesCommissionState.FORECASTED, CommissionPoints.of("-81.00")),
							tuple(SalesCommissionState.INVOICEABLE, CommissionPoints.of("-8.10")),
							tuple(SalesCommissionState.INVOICED, CommissionPoints.of("89.10")));
		});
	}
}
