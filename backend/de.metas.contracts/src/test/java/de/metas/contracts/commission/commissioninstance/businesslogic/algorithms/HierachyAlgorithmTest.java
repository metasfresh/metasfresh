package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.Payer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierachyAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfigId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceLineDocumentId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

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
	private final OrgId orgId = OrgId.ofRepoId(200);

	private final ProductId commissionProductId = ProductId.ofRepoId(100);

	private final Beneficiary headOfSales = Beneficiary.of(BPartnerId.ofRepoId(40));
	private final Beneficiary salesSupervisor = Beneficiary.of(BPartnerId.ofRepoId(30));
	private final Beneficiary salesRep = Beneficiary.of(BPartnerId.ofRepoId(20));
	private final BPartnerId payerId = BPartnerId.ofRepoId(1001);

	private I_C_UOM uomRecord;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uomRecord = BusinessTestHelper.createUOM("uom");
	}

	@Test
	void createInstanceShares()
	{
		createInstanceShares_performTest(InvoiceCandidateId.ofRepoId(10));
	}

	@Test
	void applyTriggerChange()
	{
		final InvoiceCandidateId invoiceCandiateId = InvoiceCandidateId.ofRepoId(10);
		final ImmutableList<CommissionShare> initialShares = createInstanceShares_performTest(invoiceCandiateId);

		final CommissionInstance instance = CommissionInstance.builder()
				.currentTriggerData(createInitialcommissionTrigger(invoiceCandiateId, salesRep).getCommissionTriggerData())
				.shares(initialShares)
				.build();

		final CommissionTriggerChange change = CommissionTriggerChange.builder()
				.instanceToUpdate(instance)
				.newCommissionTriggerData(CommissionTriggerData.builder()
												  .orgId(orgId)
												  .triggerDocumentId(new SalesInvoiceCandidateDocumentId(invoiceCandiateId))
												  .triggerType(CommissionTriggerType.InvoiceCandidate)
												  .triggerDocumentDate(LocalDate.of(2020, 03, 22))
												  .timestamp(Instant.now())
												  .forecastedBasePoints(CommissionPoints.ZERO)
												  .invoiceableBasePoints(CommissionPoints.ZERO)
												  .invoicedBasePoints(CommissionPoints.of("1110.00"))
												  .productId(commissionProductId)
												  .totalQtyInvolved(Quantity.of(BigDecimal.TEN, uomRecord))
												  .documentCurrencyId(CurrencyId.ofRepoId(1))
												  .build())
				.build();

		// invoke the method under test
		new HierachyAlgorithm().applyTriggerChangeToShares(change);

		assertThat(instance).isNotNull();
		final ImmutableList<CommissionShare> sharesAfterInvocation = instance.getShares();
		assertThat(sharesAfterInvocation).hasSize(3);

		assertThat(sharesAfterInvocation.get(0)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("111.00"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("100.00")),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("10.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("1.00")),

							tuple(CommissionState.FORECASTED, CommissionPoints.of("-100.00")),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("-10.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("110.00")));
		});

		assertThat(sharesAfterInvocation.get(1)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("99.90"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("90.00")),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("9.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.90")),

							tuple(CommissionState.FORECASTED, CommissionPoints.of("-90.00")),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("-9.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("99.00")));
		});
		assertThat(sharesAfterInvocation.get(2)).satisfies(share -> {
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("89.91"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("81.00")),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("8.10")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.81")),

							tuple(CommissionState.FORECASTED, CommissionPoints.of("-81.00")),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("-8.10")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("89.10")));
		});
	}

	/**
	 * Applies a commission trigger with negative base points.
	 */
	@Test
	void applyTriggerChange_negative()
	{
		final CommissionTriggerData triggerData = CommissionTriggerData.builder()
				.orgId(orgId)
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.timestamp(de.metas.common.util.time.SystemTime.asInstant())
				.triggerDocumentId(new SalesInvoiceLineDocumentId(InvoiceAndLineId.ofRepoId(10, 15)))
				.triggerDocumentDate(LocalDate.of(2020, 03, 22))
				.forecastedBasePoints(CommissionPoints.of("30.00"))
				.invoiceableBasePoints(CommissionPoints.of("20.00"))
				.invoicedBasePoints(CommissionPoints.of("10.00"))
				.productId(commissionProductId)
				.totalQtyInvolved(Quantity.of(BigDecimal.TEN, uomRecord))
				.documentCurrencyId(CurrencyId.ofRepoId(1))
				.build();

		final HierarchyConfig config = HierarchyConfig.builder()
				.id(HierarchyConfigId.ofRepoId(5))
				.subtractLowerLevelCommissionFromBase(true)
				.commissionProductId(commissionProductId)
				.beneficiary2HierarchyContract(salesRep, HierarchyContract.builder()
						.id(FlatrateTermId.ofRepoId(3))
						.commissionPercent(Percent.of(10))
						.pointsPrecision(2))
				.build();

		final CommissionInstance instance = CommissionInstance.builder()
				.currentTriggerData(triggerData)
				.share(CommissionShare.builder()
							   .beneficiary(salesRep)
							   .config(config)
						.soTrx(SOTrx.PURCHASE)
						.payer(Payer.of(payerId))
						.fact(CommissionFact.builder()
											 .points(CommissionPoints.of("30.00"))
								.state(CommissionState.FORECASTED)
											 .timestamp(de.metas.common.util.time.SystemTime.asInstant())
											 .build())
						.fact(CommissionFact.builder()
											 .points(CommissionPoints.of("20.00"))
								.state(CommissionState.INVOICEABLE)
											 .timestamp(de.metas.common.util.time.SystemTime.asInstant())
											 .build())
						.fact(CommissionFact.builder()
											 .points(CommissionPoints.of("10.00"))
								.state(CommissionState.INVOICED)
											 .timestamp(SystemTime.asInstant())
											 .build())
							   .level(HierarchyLevel.of(10))
							   .build())
				.build();

		// guard
		assertThat(instance.getShares().get(0)).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
				.contains(CommissionPoints.of("30.00"), CommissionPoints.of("20.00"), CommissionPoints.of("10.00"));

		final CommissionTriggerChange change = CommissionTriggerChange.builder()
				.instanceToUpdate(instance)
				.newCommissionTriggerData(CommissionTriggerData.builder()
												  .orgId(orgId)
												  .triggerDocumentId(new SalesInvoiceLineDocumentId(InvoiceAndLineId.ofRepoId(10, 15)))
												  .triggerType(CommissionTriggerType.SalesInvoice)
												  .triggerDocumentDate(LocalDate.of(2020, 03, 21))
												  .timestamp(Instant.now())
												  .forecastedBasePoints(CommissionPoints.ZERO)
												  .invoiceableBasePoints(CommissionPoints.ZERO)
												  .invoicedBasePoints(CommissionPoints.of("-1110.00")) // with our 10% commission form the config, this trls to -111
												  .productId(commissionProductId)
												  .totalQtyInvolved(Quantity.of(BigDecimal.TEN, uomRecord))
												  .documentCurrencyId(CurrencyId.ofRepoId(1))
												  .build())
				.build();

		// invoke the method under test
		new HierachyAlgorithm().applyTriggerChangeToShares(change);

		assertThat(instance).isNotNull();
		final ImmutableList<CommissionShare> sharesAfterInvocation = instance.getShares();
		assertThat(sharesAfterInvocation).hasSize(1);
		final CommissionShare share = sharesAfterInvocation.get(0);

		assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
				.contains(CommissionPoints.of("0.00"), CommissionPoints.of("0.00"), CommissionPoints.of("-111.00"));
		assertThat(share.getFacts())
				.extracting("state", "points")
				.containsExactly(
						tuple(CommissionState.FORECASTED, CommissionPoints.of("30.00")),
						tuple(CommissionState.INVOICEABLE, CommissionPoints.of("20.00")),
						tuple(CommissionState.INVOICED, CommissionPoints.of("10.00")),
						tuple(CommissionState.FORECASTED, CommissionPoints.of("-30.00")),
						tuple(CommissionState.INVOICEABLE, CommissionPoints.of("-20.00")),
						tuple(CommissionState.INVOICED, CommissionPoints.of("-121.00")));
	}

	private ImmutableList<CommissionShare> createInstanceShares_performTest(@NonNull final InvoiceCandidateId invoiceCandiateId)
	{
		final HierarchyNode headOfSalesNode = HierarchyNode.of(headOfSales);
		final HierarchyNode salesSupervisorNode = HierarchyNode.of(salesSupervisor);
		final HierarchyNode salesrepNode = HierarchyNode.of(salesRep);

		final CommissionTrigger trigger = createInitialcommissionTrigger(invoiceCandiateId, salesRep);

		final Hierarchy hierarchy = Hierarchy.builder()
				.addChildren(headOfSalesNode, ImmutableList.of(salesSupervisorNode))
				.addChildren(salesSupervisorNode, ImmutableList.of(salesrepNode))
				.addChildren(salesrepNode, ImmutableList.of(HierarchyNode.of(Beneficiary.of(trigger.getCustomer().getBPartnerId()))))
				.build();

		final HierarchyConfig config = HierarchyConfig.builder()
				.id(HierarchyConfigId.ofRepoId(5))
				.subtractLowerLevelCommissionFromBase(true)
				.commissionProductId(commissionProductId)
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

		final CreateCommissionSharesRequest request = CreateCommissionSharesRequest.builder()
				.config(config)
				.hierarchy(hierarchy)
				.trigger(trigger)
				.startingHierarchyLevel(HierarchyLevel.ZERO)
				.build();

		// invoke the method under test
		final ImmutableList<CommissionShare> shares = new HierachyAlgorithm().createCommissionShares(request);
		assertThat(shares).hasSize(3);

		assertThat(shares).allSatisfy(share -> {
			assertThat(share.getConfig()).isEqualTo(config);
		});

		assertThat(shares.get(0)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(salesRep);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(0));
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("100.00"), CommissionPoints.of("10.00"), CommissionPoints.of("1.00"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("100.00")),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("10.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("1.00")));
		});

		assertThat(shares.get(1)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(salesSupervisor);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(1));
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("90.00"), CommissionPoints.of("9.00"), CommissionPoints.of("0.90"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("90.00")/* remaining commission base=900 */),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("9.00")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.90")));
		});

		assertThat(shares.get(2)).satisfies(share -> {
			assertThat(share.getBeneficiary()).isEqualTo(headOfSales);
			assertThat(share.getLevel()).isEqualTo(HierarchyLevel.of(2));
			assertThat(share).extracting("forecastedPointsSum", "invoiceablePointsSum", "invoicedPointsSum")
					.contains(CommissionPoints.of("81.00"), CommissionPoints.of("8.10"), CommissionPoints.of("0.81"));
			assertThat(share.getFacts())
					.extracting("state", "points")
					.containsExactly(
							tuple(CommissionState.FORECASTED, CommissionPoints.of("81.00")/* remaining commission base=810 */),
							tuple(CommissionState.INVOICEABLE, CommissionPoints.of("8.10")),
							tuple(CommissionState.INVOICED, CommissionPoints.of("0.81")));
		});

		return shares;
	}

	@Test
	void createInstanceShares_multiple_settings()
	{
		createInstanceShares_performTest(InvoiceCandidateId.ofRepoId(10));
	}

	private CommissionTrigger createInitialcommissionTrigger(
			@NonNull final InvoiceCandidateId invoiceCandiateId,
			@NonNull final Beneficiary salesRep)
	{
		final CommissionTriggerData triggerData = CommissionTriggerData.builder()
				.orgId(orgId)
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.timestamp(de.metas.common.util.time.SystemTime.asInstant())
				.triggerDocumentDate(LocalDate.of(2020, 03, 22))
				.triggerDocumentId(new SalesInvoiceCandidateDocumentId(invoiceCandiateId))
				.forecastedBasePoints(CommissionPoints.of("1000.00"))
				// it's uncommon to have a trigger with points beyond "forecasted"..but still should work
				.invoiceableBasePoints(CommissionPoints.of("100.00"))
				.invoicedBasePoints(CommissionPoints.of("10.00"))
				.productId(commissionProductId)
				.totalQtyInvolved(Quantity.of(BigDecimal.TEN, uomRecord))
				.documentCurrencyId(CurrencyId.ofRepoId(1))
				.build();

		final CommissionTrigger trigger = CommissionTrigger.builder()
				.customer(Customer.of(BPartnerId.ofRepoId(10)))
				.salesRepId(salesRep.getBPartnerId())
				.orgBPartnerId(BPartnerId.ofRepoId(1001))
				.commissionTriggerData(triggerData)
				.build();
		return trigger;
	}
}
