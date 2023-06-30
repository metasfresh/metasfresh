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

package de.metas.contracts.commission.licensefee.algorithm;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
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
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.Builder;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
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

public class LicenseFeeAlgorithmTest
{
	private LicenseFeeAlgorithm licenseFeeAlgorithm;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		licenseFeeAlgorithm = new LicenseFeeAlgorithm();
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
		final ImmutableList<CommissionShare> commissionShare = licenseFeeAlgorithm.createCommissionShares(request);

		//then
		assertThat(commissionShare.size()).isEqualTo(1);

		expect(commissionShare.get(0)).toMatchSnapshot();
	}

	@Test
	public void givenSalesRepWithNoContract_whenCreateCommissionShares_thenThrowException()
	{
		//given
		final CreateCommissionSharesRequest request = commissionSharesRequestBuilder()
				.invalidContract(true)
				.build();

		//when
		final RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> licenseFeeAlgorithm.createCommissionShares(request));

		//then
		assertThat(thrown.getMessage()).contains("No contract available for the given salesRep!");
	}

	@Builder(builderMethodName = "commissionSharesRequestBuilder")
	private CreateCommissionSharesRequest buildRequest(final boolean invalidContract)
	{
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uom");

		final Hierarchy emptyHierarchy = Hierarchy.builder().build();

		final BPartnerId salesRepId = BPartnerId.ofRepoId(1);
		final BPartnerId orgBPartnerId = BPartnerId.ofRepoId(2);
		final BPartnerId randomBPartnerIdForContractFail = BPartnerId.ofRepoId(3);

		//config & contract
		final LicenseFeeContract licenseFeeContract = LicenseFeeContract.builder()
				.id(FlatrateTermId.ofRepoId(1001))
				.contractOwnerBPartnerId(invalidContract ? randomBPartnerIdForContractFail : salesRepId)
				.build();

		final LicenseFeeSettingsId licenseFeeSettingsId = LicenseFeeSettingsId.ofRepoId(1);

		final LicenseFeeConfig licenseFeeConfig = LicenseFeeConfig.builder()
				.licenseFeeContract(licenseFeeContract)
				.commissionProductId(ProductId.ofRepoId(2))
				.commissionPercent(Percent.of(10))
				.pointsPrecision(2)
				.licenseFeeSettingsLineId(LicenseFeeSettingsLineId.ofRepoId(licenseFeeSettingsId, 2))
				.build();

		//trigger
		final CommissionTriggerData commissionTriggerData = CommissionTriggerData.builder()
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.forecastedBasePoints(CommissionPoints.of(BigDecimal.valueOf(100)))
				.invoiceableBasePoints(CommissionPoints.ZERO)
				.invoicedBasePoints(CommissionPoints.ZERO)
				.triggerDocumentDate(LocalDate.ofYearDay(2021, 200))
				.orgId(OrgId.MAIN)
				.timestamp(Instant.ofEpochSecond(19898989898L))
				.triggerDocumentId(new SalesInvoiceCandidateDocumentId(InvoiceCandidateId.ofRepoId(1)))
				.productId(ProductId.ofRepoId(1))
				.totalQtyInvolved(Quantity.of(BigDecimal.TEN, uomRecord))
				.documentCurrencyId(CurrencyId.ofRepoId(1))
				.build();

		final CommissionTrigger commissionTrigger = CommissionTrigger.builder()
				.orgBPartnerId(orgBPartnerId)
				.customer(Customer.of(BPartnerId.ofRepoId(1001)))
				.salesRepId(salesRepId)
				.commissionTriggerData(commissionTriggerData)
				.build();

		//request
		return CreateCommissionSharesRequest.builder()
				.hierarchy(emptyHierarchy)
				.startingHierarchyLevel(HierarchyLevel.ZERO)
				.config(licenseFeeConfig)
				.trigger(commissionTrigger)
				.build();
	}
}
