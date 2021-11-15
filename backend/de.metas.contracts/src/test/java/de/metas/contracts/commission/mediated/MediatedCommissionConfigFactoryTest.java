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

package de.metas.contracts.commission.mediated;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.mediated.repository.MediatedCommissionSettingsRepo;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class MediatedCommissionConfigFactoryTest
{
	private MediatedCommissionConfigFactory mediatedCommissionConfigFactorySpy;

	@BeforeEach
	public void beforeEach()
	{
		final MediatedCommissionSettingsRepo mediatedCommissionSettingsRepo = new MediatedCommissionSettingsRepo();
		mediatedCommissionConfigFactorySpy = Mockito.spy(new MediatedCommissionConfigFactory(mediatedCommissionSettingsRepo));
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
		final OrgId orgId = OrgId.ofRepoId(1);
		final BPartnerId vendorId = BPartnerId.ofRepoId(1);
		final BPartnerId orgBPartnerId = BPartnerId.ofRepoId(2);
		final LocalDate commissionDate = LocalDate.of(2021, 3, 18);
		final ProductId transactionProductId = ProductId.ofRepoId(104);
		final ProductId commissionProductId = ProductId.ofRepoId(103);

		final I_C_Flatrate_Term mediatedContract = contractAndComplementaryRecordsBuilder()
				.commissionProductId(commissionProductId)
				.transactionProductId(transactionProductId)
				.vendorId(vendorId)
				.orgId(orgId)
				.build();

		final CommissionConfigProvider.ConfigRequestForNewInstance requestForNewInstance = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.commissionDate(commissionDate)
				.commissionHierarchy(Hierarchy.builder().build())
				.customerBPartnerId(vendorId)
				.orgId(orgId)
				.salesProductId(transactionProductId)
				.commissionTriggerType(CommissionTriggerType.MediatedOrder)
				.salesRepBPartnerId(orgBPartnerId)
				.build();

		Mockito.doReturn(ImmutableList.of(mediatedContract))
				.when(mediatedCommissionConfigFactorySpy)
				.retrieveContracts(vendorId, orgId, commissionDate);

		//when
		final List<CommissionConfig> configs = mediatedCommissionConfigFactorySpy.createForNewCommissionInstances(requestForNewInstance);

		//then
		expect(configs).toMatchSnapshot();
	}

	@Builder(builderMethodName = "contractAndComplementaryRecordsBuilder")
	private I_C_Flatrate_Term createContractAndComplementaryRecords(
			final BPartnerId vendorId,
			final ProductId commissionProductId,
			final ProductId transactionProductId,
			final OrgId orgId)
	{
		//product
		final I_M_Product transactionProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		transactionProduct.setM_Product_ID(transactionProductId.getRepoId());
		transactionProduct.setM_Product_Category_ID(10);
		saveRecord(transactionProduct);

		//mediated commission settings
		final I_C_MediatedCommissionSettings mediatedCommissionSettings = InterfaceWrapperHelper.newInstance(I_C_MediatedCommissionSettings.class);
		mediatedCommissionSettings.setAD_Org_ID(orgId.getRepoId());
		mediatedCommissionSettings.setCommission_Product_ID(commissionProductId.getRepoId());
		mediatedCommissionSettings.setIsActive(true);
		mediatedCommissionSettings.setPointsPrecision(2);
		saveRecord(mediatedCommissionSettings);

		final I_C_MediatedCommissionSettingsLine settingsLine = InterfaceWrapperHelper.newInstance(I_C_MediatedCommissionSettingsLine.class);
		settingsLine.setC_MediatedCommissionSettings_ID(mediatedCommissionSettings.getC_MediatedCommissionSettings_ID());
		settingsLine.setAD_Org_ID(orgId.getRepoId());
		settingsLine.setPercentOfBasePoints(BigDecimal.TEN);
		settingsLine.setSeqNo(1);
		settingsLine.setIsActive(true);
		saveRecord(settingsLine);

		//contract
		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_MediatedCommissionSettings_ID(mediatedCommissionSettings.getC_MediatedCommissionSettings_ID());
		InterfaceWrapperHelper.saveRecord(conditions);

		final I_C_Flatrate_Term contract = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
		contract.setBill_BPartner_ID(vendorId.getRepoId());
		contract.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		contract.setType_Conditions(TypeConditions.MEDIATED_COMMISSION.getCode());
		contract.setM_Product_ID(commissionProductId.getRepoId());
		InterfaceWrapperHelper.saveRecord(contract);

		return contract;
	}
}
