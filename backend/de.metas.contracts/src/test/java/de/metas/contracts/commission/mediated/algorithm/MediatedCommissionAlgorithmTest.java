/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.mediated.algorithm;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder.MediatedOrderLineDocId;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsId;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsLineId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MediatedCommissionAlgorithmTest
{
	private MediatedCommissionAlgorithm mediatedCommissionAlgorithm;

	@BeforeEach
	public void beforeEach()
	{
		mediatedCommissionAlgorithm = new MediatedCommissionAlgorithm();
	}

	@BeforeAll
	static void init()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void givenValidRequest_whenCreateCommissionShares_thenReturnOneShare()
	{
		//given
		final CreateCommissionSharesRequest request = commissionSharesRequestBuilder().build();

		//when
		final ImmutableList<CommissionShare> commissionShare = mediatedCommissionAlgorithm.createCommissionShares(request);

		//then
		assertThat(commissionShare.size()).isEqualTo(1);

		expect(commissionShare.get(0)).toMatchSnapshot();
	}

	@Test
	public void givenInvalidContract_whenCreateCommissionShares_thenThrowException()
	{
		//given
		final CreateCommissionSharesRequest request = commissionSharesRequestBuilder()
				.invalidContract(true)
				.build();

		//when
		final AdempiereException thrown = assertThrows(
				AdempiereException.class,
				() -> mediatedCommissionAlgorithm.createCommissionShares(request));

		//then
		assertThat(thrown.getMessage()).contains("No contract available for the given vendor!");
	}

	@Test
	public void givenInvalidTriggerType_whenCreateCommissionShares_thenThrowException()
	{
		//given
		final CreateCommissionSharesRequest request = commissionSharesRequestBuilder()
				.invalidTriggerType(true)
				.build();

		//when
		final RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> mediatedCommissionAlgorithm.createCommissionShares(request));

		//then
		assertThat(thrown.getMessage()).contains("Trigger document is a mediated order!");
	}

	@Builder(builderMethodName = "commissionSharesRequestBuilder")
	private CreateCommissionSharesRequest buildRequest(
			final boolean invalidContract,
			final boolean invalidTriggerType)
	{
		final Hierarchy emptyHierarchy = Hierarchy.builder().build();

		final BPartnerId vendorId = BPartnerId.ofRepoId(1);
		final BPartnerId orgBPartnerId = BPartnerId.ofRepoId(2);

		//config & contract
		final MediatedCommissionContract mediatedCommissionContract = MediatedCommissionContract.builder()
				.contractOwnerBPartnerId(invalidContract ? orgBPartnerId : vendorId)
				.contractId(FlatrateTermId.ofRepoId(1001))
				.build();

		final MediatedCommissionConfig mediatedCommissionConfig = MediatedCommissionConfig.builder()
				.commissionProductId(ProductId.ofRepoId(2))
				.pointsPrecision(2)
				.commissionPercent(Percent.of(10))
				.mediatedCommissionSettingsLineId(MediatedCommissionSettingsLineId.ofRepoId(1, MediatedCommissionSettingsId.ofRepoId(2)))
				.mediatedCommissionContract(mediatedCommissionContract)
				.build();

		//trigger
		final CommissionTriggerData commissionTriggerData = CommissionTriggerData.builder()
				.triggerType(invalidTriggerType ? CommissionTriggerType.InvoiceCandidate : CommissionTriggerType.MediatedOrder)
				.forecastedBasePoints(CommissionPoints.ZERO)
				.invoiceableBasePoints(CommissionPoints.ZERO)
				.invoicedBasePoints(CommissionPoints.of(BigDecimal.valueOf(100)))
				.triggerDocumentDate(LocalDate.ofYearDay(2021, 200))
				.orgId(OrgId.ofRepoId(1))
				.timestamp(Instant.ofEpochSecond(19898989898L))
				.triggerDocumentId(MediatedOrderLineDocId.of(OrderLineId.ofRepoId(20)))
				.tradedCommissionPercent(Percent.ZERO)
				.build();

		final CommissionTrigger commissionTrigger = CommissionTrigger.builder()
				.orgBPartnerId(orgBPartnerId)
				.customer(Customer.of(vendorId))
				.salesRepId(orgBPartnerId)
				.commissionTriggerData(commissionTriggerData)
				.build();

		//request
		return CreateCommissionSharesRequest.builder()
				.hierarchy(emptyHierarchy)
				.startingHierarchyLevel(HierarchyLevel.ZERO)
				.config(mediatedCommissionConfig)
				.trigger(commissionTrigger)
				.build();
	}
}
