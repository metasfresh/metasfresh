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

package de.metas.contracts.commission.licensefee;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionContractBuilder;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsId;
import de.metas.contracts.commission.licensefee.repository.LicenseFeeSettingsRepository;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettingsLine;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static de.metas.testsupport.MetasfreshAssertions.assertThat;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class LicenseFeeSettingsConfigFactoryTest
{
	private LicenseFeeSettingsConfigFactory licenseFeeSettingsConfigFactorySpy;

	@BeforeEach
	public void beforeEach()
	{
		final LicenseFeeSettingsRepository licenseFeeSettingsRepository = new LicenseFeeSettingsRepository();
		licenseFeeSettingsConfigFactorySpy = Mockito.spy(new LicenseFeeSettingsConfigFactory(licenseFeeSettingsRepository));
	}

	@BeforeAll
	static void init()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
		AdempiereTestHelper.get().init();
	}

	@Test
	public void givenValidRequest_whenCreateForNewCommissionInstances_thenReturnOneCommissionConfig()
	{
		//given
		final BPartnerId salesRepId = BPartnerId.ofRepoId(2);
		final BPartnerId customerId = BPartnerId.ofRepoId(3);
		final OrgId orgId = OrgId.ofRepoId(1);
		final BPGroupId bpGroupId = BPGroupId.ofRepoId(20);
		final LocalDate commissionDate = LocalDate.of(2021, 11, 20);
		final ProductId transactionProductId = ProductId.ofRepoId(104);
		final ProductId commissionProductId = ProductId.ofRepoId(103);

		final I_C_BPartner salesRepBPartner = newInstance(I_C_BPartner.class);
		salesRepBPartner.setC_BPartner_ID(salesRepId.getRepoId());
		salesRepBPartner.setAD_Org_ID(OrgId.toRepoId(orgId));
		salesRepBPartner.setC_BP_Group_ID(BPGroupId.toRepoId(bpGroupId));
		saveRecord(salesRepBPartner);

		final I_C_Flatrate_Term licenseFeeContract = contractAndComplementaryRecordsBuilder()
				.commissionProductId(commissionProductId)
				.transactionProductId(transactionProductId)
				.contractBPartnerId(salesRepId)
				.orgId(orgId)
				.bpGroupId(BPGroupId.ofRepoId(salesRepBPartner.getC_BP_Group_ID()))
				.build();

		Mockito.doReturn(ImmutableList.of(licenseFeeContract))
				.when(licenseFeeSettingsConfigFactorySpy)
				.retrieveContracts(salesRepId, orgId, commissionDate);

		final CommissionConfigProvider.ConfigRequestForNewInstance requestForNewInstance = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.commissionDate(commissionDate)
				.commissionHierarchy(Hierarchy.builder().build())
				.customerBPartnerId(customerId)
				.orgId(orgId)
				.salesProductId(transactionProductId)
				.commissionTriggerType(CommissionTriggerType.InvoiceCandidate)
				.salesRepBPartnerId(salesRepId)
				.build();

		//when
		final List<CommissionConfig> configs = licenseFeeSettingsConfigFactorySpy.createForNewCommissionInstances(requestForNewInstance);

		//then
		assertThat(configs.size()).isEqualTo(1);
		expect(configs).toMatchSnapshot();
	}

	@Builder(builderMethodName = "contractAndComplementaryRecordsBuilder")
	private I_C_Flatrate_Term createContractAndComplementaryRecords(
			final BPartnerId contractBPartnerId,
			final ProductId commissionProductId,
			final ProductId transactionProductId,
			final OrgId orgId,
			final BPGroupId bpGroupId)
	{
		//product
		final I_M_Product transactionProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		transactionProduct.setM_Product_ID(transactionProductId.getRepoId());
		transactionProduct.setM_Product_Category_ID(10);
		saveRecord(transactionProduct);

		// license fee commission settings
		final I_C_LicenseFeeSettings licenseFeeSettings = InterfaceWrapperHelper.newInstance(I_C_LicenseFeeSettings.class);
		licenseFeeSettings.setAD_Org_ID(orgId.getRepoId());
		licenseFeeSettings.setCommission_Product_ID(commissionProductId.getRepoId());
		licenseFeeSettings.setPointsPrecision(2);
		licenseFeeSettings.setName("name");
		licenseFeeSettings.setIsActive(true);
		saveRecord(licenseFeeSettings);

		final LicenseFeeSettingsId licenseFeeSettingsId = LicenseFeeSettingsId.ofRepoId(licenseFeeSettings.getC_LicenseFeeSettings_ID());

		final I_C_LicenseFeeSettingsLine settingsLine = InterfaceWrapperHelper.newInstance(I_C_LicenseFeeSettingsLine.class);
		settingsLine.setC_LicenseFeeSettings_ID(LicenseFeeSettingsId.toRepoId(licenseFeeSettingsId));
		settingsLine.setAD_Org_ID(orgId.getRepoId());
		settingsLine.setPercentOfBasePoints(BigDecimal.TEN);
		settingsLine.setC_BP_Group_Match_ID(BPGroupId.toRepoId(bpGroupId));
		settingsLine.setSeqNo(10);
		settingsLine.setIsActive(true);
		saveRecord(settingsLine);

		return TestCommissionContractBuilder.commissionContractBuilder()
				.commissionProductId(commissionProductId)
				.contractBPartnerId(contractBPartnerId)
				.orgId(orgId)
				.licenseFeeSettingsId(licenseFeeSettingsId)
				.typeConditions(TypeConditions.LICENSE_FEE)
				.build();
	}

}
