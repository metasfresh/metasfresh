package de.metas.contracts.commission.commissioninstance.services.repos;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService.CommissionStagingRecords;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.testhelpers.CommissionInstanceTestRecord;
import de.metas.contracts.commission.testhelpers.CommissionInstanceTestRecord.CreateCommissionInstanceResult;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.contracts.commission.testhelpers.CommissionShareTestRecord;

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

class CommissionRecordStagingServiceTest
{
	private CommissionRecordStagingService commissionRecordStagingService;
	private ProductId commissionProduct1Id;
	private ProductId commissionProduct2Id;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		commissionRecordStagingService = new CommissionRecordStagingService();

		final I_M_Product commissionProduct1Record = newInstance(I_M_Product.class);
		saveRecord(commissionProduct1Record);
		commissionProduct1Id = ProductId.ofRepoId(commissionProduct1Record.getM_Product_ID());

		final I_M_Product commissionProduct2Record = newInstance(I_M_Product.class);
		saveRecord(commissionProduct2Record);
		commissionProduct2Id = ProductId.ofRepoId(commissionProduct2Record.getM_Product_ID());

	}

	@Test
	void retrieveRecordsForInstanceId_commission_share_ordering()
	{
		final CreateCommissionInstanceResult commissionData1 = CommissionInstanceTestRecord.builder()
				.AD_ORG_ID(OrgId.ofRepoId(10))
				.mostRecentTriggerTimestamp(1000000L)
				.pointsBase_Forecasted("10")
				.pointsBase_Invoiceable("5")
				.pointsBase_Invoiced("1")
				.commissionShareTestRecord(CommissionShareTestRecord.builder().levelHierarchy(1).flatrateTermId(FlatrateTermId.ofRepoId(20)).C_BPartner_SalesRep_ID(BPartnerId.ofRepoId(20)).commissionProductId(commissionProduct2Id).build())
				.commissionShareTestRecord(CommissionShareTestRecord.builder().levelHierarchy(0).flatrateTermId(FlatrateTermId.ofRepoId(10)).C_BPartner_SalesRep_ID(BPartnerId.ofRepoId(10)).commissionProductId(commissionProduct1Id).build())
				.build().createCommissionData();
		final CommissionInstanceId commissionInstance1Id = commissionData1.getCommissionInstanceId();

		final CreateCommissionInstanceResult commissionData2 = CommissionInstanceTestRecord.builder()
				.AD_ORG_ID(OrgId.ofRepoId(5))
				.mostRecentTriggerTimestamp(1000000L)
				.pointsBase_Forecasted("10")
				.pointsBase_Invoiceable("5")
				.pointsBase_Invoiced("1")
				.commissionShareTestRecord(CommissionShareTestRecord.builder().levelHierarchy(1).flatrateTermId(FlatrateTermId.ofRepoId(20)).C_BPartner_SalesRep_ID(BPartnerId.ofRepoId(40)).commissionProductId(commissionProduct2Id).build())
				.commissionShareTestRecord(CommissionShareTestRecord.builder().levelHierarchy(0).flatrateTermId(FlatrateTermId.ofRepoId(10)).C_BPartner_SalesRep_ID(BPartnerId.ofRepoId(30)).commissionProductId(commissionProduct1Id).build())
				.build().createCommissionData();
		final CommissionInstanceId commissionInstance2Id = commissionData2.getCommissionInstanceId();

		// invoke the method under test
		final CommissionStagingRecords result = commissionRecordStagingService.retrieveRecordsForInstanceId(ImmutableList.of(commissionInstance1Id, commissionInstance2Id));

		final ImmutableList<I_C_Commission_Share> shareRecords1 = result.getShareRecordsForInstanceRecordId(commissionInstance1Id);
		assertThat(shareRecords1)
				.extracting("LevelHierarchy", "C_BPartner_SalesRep_ID", "Commission_Product_ID")
				.containsExactly(
						tuple(0, 10, commissionProduct1Id.getRepoId()),
						tuple(1, 20, commissionProduct2Id.getRepoId()));

		final ImmutableList<I_C_Commission_Share> shareRecords2 = result.getShareRecordsForInstanceRecordId(commissionInstance2Id);
		assertThat(shareRecords2)
				.extracting("LevelHierarchy", "C_BPartner_SalesRep_ID", "Commission_Product_ID")
				.containsExactly(
						tuple(0, 30, commissionProduct1Id.getRepoId()),
						tuple(1, 40, commissionProduct2Id.getRepoId()));
	}

}
