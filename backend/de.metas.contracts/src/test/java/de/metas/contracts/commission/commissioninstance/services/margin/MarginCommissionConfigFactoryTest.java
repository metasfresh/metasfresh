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

package de.metas.contracts.commission.commissioninstance.services.margin;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionContractBuilder;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginId;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginRepository;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginService;
import de.metas.currency.CurrencyRepository;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class MarginCommissionConfigFactoryTest
{
	private MarginCommissionConfigFactory marginCommissionConfigFactorySpy;

	@BeforeAll
	static void init()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
		AdempiereTestHelper.get().init();
	}

	@BeforeEach
	void beforeEach()
	{
		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final MoneyService moneyService = new MoneyService(currencyRepository);
		final CustomerTradeMarginService customerTradeMarginService = new CustomerTradeMarginService(moneyService, new CustomerTradeMarginRepository());
		marginCommissionConfigFactorySpy = Mockito.spy(new MarginCommissionConfigFactory(customerTradeMarginService));
	}

	@Test
	public void givenValidRequest_whenCreateForNewCommissionInstances_thenReturnOneCommissionConfig()
	{
		//given
		final OrgId orgId = AdempiereTestHelper.createOrgWithTimeZone();
		final BPartnerId salesRepId = BPartnerId.ofRepoId(1);
		final BPartnerId customerBPartnerId = BPartnerId.ofRepoId(2);
		final LocalDate commissionDate = LocalDate.of(2021, 9, 13);

		final ProductCategoryId categoryId = BusinessTestHelper.createProductCategory("Category", null);
		
		final I_C_UOM uomPCE = BusinessTestHelper.createUomPCE();
		final ProductId transactionProductId = BusinessTestHelper.createProduct("salesProduct", uomPCE, categoryId);
		final ProductId commissionProductId = BusinessTestHelper.createProduct("comissionProduct", uomPCE, categoryId);

		final I_C_Flatrate_Term marginContract = contractAndComplementaryRecordsBuilder()
				.commissionProductId(commissionProductId)
				.salesRepId(salesRepId)
				.orgId(orgId)
				.build();

		final CommissionConfigProvider.ConfigRequestForNewInstance requestForNewInstance = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.commissionDate(commissionDate)
				.commissionHierarchy(Hierarchy.EMPTY_HIERARCHY)
				.customerBPartnerId(customerBPartnerId)
				.salesRepBPartnerId(salesRepId)
				.orgId(orgId)
				.salesProductId(transactionProductId)
				.commissionTriggerType(CommissionTriggerType.InvoiceCandidate)
				.build();

		Mockito.doReturn(ImmutableList.of(marginContract))
				.when(marginCommissionConfigFactorySpy)
				.retrieveContracts(salesRepId, orgId, commissionDate);
		//when
		final List<CommissionConfig> configs = marginCommissionConfigFactorySpy.createForNewCommissionInstances(requestForNewInstance);

		//then
		expect(configs).toMatchSnapshot();
	}

	@Builder(builderMethodName = "contractAndComplementaryRecordsBuilder")
	private I_C_Flatrate_Term createContractAndComplementaryRecords(
			final BPartnerId salesRepId,
			final ProductId commissionProductId,
			final OrgId orgId)
	{
		//margin commission settings
		final I_C_Customer_Trade_Margin margin = newInstance(I_C_Customer_Trade_Margin.class);
		margin.setAD_Org_ID(orgId.getRepoId());
		margin.setCommission_Product_ID(commissionProductId.getRepoId());
		margin.setName("name");
		margin.setPointsPrecision(2);
		margin.setIsActive(true);
		saveRecord(margin);

		final I_C_Customer_Trade_Margin_Line marginLine = newInstance(I_C_Customer_Trade_Margin_Line.class);
		marginLine.setC_Customer_Trade_Margin_ID(margin.getC_Customer_Trade_Margin_ID());
		marginLine.setAD_Org_ID(orgId.getRepoId());
		marginLine.setMargin(10);
		marginLine.setSeqNo(1);
		marginLine.setIsActive(true);
		saveRecord(marginLine);

		return TestCommissionContractBuilder.commissionContractBuilder()
				.commissionProductId(commissionProductId)
				.contractBPartnerId(salesRepId)
				.orgId(orgId)
				.marginConfigId(CustomerTradeMarginId.ofRepoId(margin.getC_Customer_Trade_Margin_ID()))
				.typeConditions(TypeConditions.MARGIN_COMMISSION)
				.build();
	}
}
