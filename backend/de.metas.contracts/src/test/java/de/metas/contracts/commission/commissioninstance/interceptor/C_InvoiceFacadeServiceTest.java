package de.metas.contracts.commission.commissioninstance.interceptor;

import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_C_InvoiceLine_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_C_Invoice_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_CommissionTrigger_Type;
import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_M_Product_Order_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;

import de.metas.organization.OrgId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.business.TestInvoice;
import de.metas.business.TestInvoiceLine;
import de.metas.cache.CacheMgt;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentService;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceFactory;
import de.metas.contracts.commission.commissioninstance.interceptor.C_InvoiceFacadeService;
import de.metas.contracts.commission.commissioninstance.services.CommissionAlgorithmInvoker;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigStagingDataService;
import de.metas.contracts.commission.commissioninstance.services.CommissionHierarchyFactory;
import de.metas.contracts.commission.commissioninstance.services.CommissionInstanceRequestFactory;
import de.metas.contracts.commission.commissioninstance.services.CommissionInstanceService;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.contracts.commission.commissioninstance.services.CommissionTriggerFactory;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig.ConfigData;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfigLine;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionContract;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.model.X_C_Commission_Instance;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.IDocTypeDAO.DocTypeCreateRequest;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

class C_InvoiceFacadeServiceTest
{
	private C_InvoiceFacadeService invoiceFacadeService;
	private OrgId orgId;
	private BPartnerId customerId;
	private BPartnerId salesRepPartnerId;
	private CurrencyId currencyId;
	private ProductId salesProductId;
	private DocTypeId creditMemoDocTypeId;
	private ProductId commissionProductId;
	private I_C_UOM commissionUOMRecord;
	
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		
		SpringContextHolder.registerJUnitBean(new CurrencyRepository());

		final CommissionProductService commissionProductService = new CommissionProductService();
		final SalesInvoiceFactory salesInvoiceFactory = new SalesInvoiceFactory(commissionProductService);

		final CommissionConfigStagingDataService commissionConfigStagingDataService = new CommissionConfigStagingDataService();
		final CommissionConfigFactory commissionConfigFactory = new CommissionConfigFactory(commissionConfigStagingDataService);
		final CommissionRecordStagingService commissionInstanceRecordStagingService = new CommissionRecordStagingService();
		final CommissionInstanceRepository commissionInstanceRepository = new CommissionInstanceRepository(commissionConfigFactory, commissionInstanceRecordStagingService);
		final CommissionHierarchyFactory commissionHierarchyFactory = new CommissionHierarchyFactory();
		final CommissionTriggerFactory commissionTriggerFactory = new CommissionTriggerFactory();
		final CommissionInstanceRequestFactory commissionInstanceRequestFactory = new CommissionInstanceRequestFactory(
				commissionConfigFactory,
				commissionHierarchyFactory,
				commissionTriggerFactory);
		final CommissionAlgorithmInvoker commissionAlgorithmInvoker = new CommissionAlgorithmInvoker();
		final CommissionInstanceService commissionInstanceService = new CommissionInstanceService(commissionInstanceRequestFactory, commissionAlgorithmInvoker);

		final CommissionTriggerDocumentService commissionTriggerDocumentService = new CommissionTriggerDocumentService(commissionInstanceRepository, commissionInstanceRequestFactory, commissionAlgorithmInvoker, commissionTriggerFactory, commissionInstanceService);

		invoiceFacadeService = new C_InvoiceFacadeService(salesInvoiceFactory, commissionTriggerDocumentService);

		LogManager.setLoggerLevel("de.metas.contracts.commission", Level.DEBUG);

		orgId = AdempiereTestHelper.createOrgWithTimeZone();

		commissionUOMRecord = BusinessTestHelper.createUOM("commissionPoint");
		final I_M_Product commissionProductRecord = BusinessTestHelper.createProduct("commissionProduct", commissionUOMRecord);
		commissionProductId = ProductId.ofRepoId(commissionProductRecord.getM_Product_ID());

		final I_C_Currency currencyRecord = BusinessTestHelper.createCurrency("€");
		currencyId = CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID());

		final I_M_Product salesProductRecord = BusinessTestHelper.createProduct("salesProduct", BusinessTestHelper.createUomEach());
		salesProductRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		salesProductRecord.setM_Product_Category_ID(20);
		salesProductRecord.setIsCommissioned(true);
		saveRecord(salesProductRecord);
		salesProductId = ProductId.ofRepoId(salesProductRecord.getM_Product_ID());

		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.subtractLowerLevelCommissionFromBase(true)
				.pointsPrecision(2)
				.commissionProductId(commissionProductId)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleConfigLine").seqNo(10).percentOfBasePoints("10").build())
				.contractTestRecord(TestCommissionContract.builder().contractName("1stContract").salesRepName("salesRep").build())
				.build()
				.createConfigData();

		assertThat(configData.getName2BPartnerId()).hasSize(1); // guard
		salesRepPartnerId = configData.getName2BPartnerId().get("salesRep");

		final I_C_BPartner customerBPartner = BusinessTestHelper.createBPartner("customerBPartner");
		customerBPartner.setAD_Org_ID(OrgId.toRepoId(orgId));
		customerBPartner.setC_BP_Group_ID(10);
		customerBPartner.setC_BPartner_SalesRep_ID(salesRepPartnerId.getRepoId());
		saveRecord(customerBPartner);
		customerId = BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID());

		creditMemoDocTypeId = Services.get(IDocTypeDAO.class)
				.createDocType(DocTypeCreateRequest.builder()
						.ctx(Env.getCtx())
						.name("creditmemo")
						.docBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo)
						.build());
	}

	@Test
	void syncSalesICToCommissionInstance_creditMemo()
	{
		perform_syncSalesICToCommissionInstance_creditMemo();
	}

	private TestInvoice perform_syncSalesICToCommissionInstance_creditMemo()
	{
		// given
		final TestInvoice testInvoice = TestInvoice.builder()
				.orgId(orgId)
				.customerId(customerId)
				.docTypeId(creditMemoDocTypeId)
				.salesRepPartnerId(salesRepPartnerId)
				.currencyId(currencyId)
				.soTrx(SOTrx.SALES)
				.testInvoiceLine(TestInvoiceLine.builder()
						.productId(salesProductId)
						.build())
				.build()
				.createInvoiceRecord();
		final I_C_Invoice invoiceRecord = testInvoice.getInvoiceRecord();

		// when
		invoiceFacadeService.syncInvoiceToCommissionInstance(invoiceRecord);

		// then
		final List<I_C_Commission_Instance> commissionInstances = POJOLookupMap.get().getRecords(I_C_Commission_Instance.class);
		assertInstanceOK(testInvoice, commissionInstances);

		final List<I_C_Commission_Share> commissionShare = POJOLookupMap.get().getRecords(I_C_Commission_Share.class);
		assertThat(commissionShare)
				.hasSize(1)
				.extracting(I_C_Commission_Share.COLUMNNAME_C_Commission_Instance_ID,
						I_C_Commission_Share.COLUMNNAME_Commission_Product_ID,
						I_C_Commission_Share.COLUMNNAME_C_BPartner_SalesRep_ID)
				.contains(tuple(commissionInstances.get(0).getC_Commission_Instance_ID(),
						commissionProductId.getRepoId(),
						salesRepPartnerId.getRepoId()));

		return testInvoice;
	}

	@Test
	void syncSalesICToCommissionInstance_creditMemo_add_contract()
	{
		// given
		final TestInvoice testInvoice = perform_syncSalesICToCommissionInstance_creditMemo();

		final I_M_Product commissionProductRecord = BusinessTestHelper.createProduct("commissionProduct2", commissionUOMRecord);
		final ProductId commissionProduct2Id = ProductId.ofRepoId(commissionProductRecord.getM_Product_ID());

		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1); // guard

		TestCommissionConfig.builder()
				.orgId(orgId)
				.subtractLowerLevelCommissionFromBase(true)
				.pointsPrecision(2)
				.commissionProductId(commissionProduct2Id)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleConfigLine2ndContract").seqNo(10).percentOfBasePoints("10").build())
				.contractTestRecord(TestCommissionContract.builder().contractName("2ndContract").salesRepName("salesRep").build())
				.build()
				.createConfigData();
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(2)
				.extracting(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID)
				.contains(
						tuple(commissionProductId.getRepoId(), salesRepPartnerId.getRepoId()),
						tuple(commissionProduct2Id.getRepoId(), salesRepPartnerId.getRepoId())); // guard
		CacheMgt.get().reset(); // required - otherwise the new contract won't be returned by DAO-service

		// when
		invoiceFacadeService.syncInvoiceToCommissionInstance(testInvoice.getInvoiceRecord());

		// then
		final List<I_C_Commission_Instance> commissionInstances = POJOLookupMap.get().getRecords(I_C_Commission_Instance.class);
		assertInstanceOK(testInvoice, commissionInstances);

		final List<I_C_Commission_Share> commissionShares = POJOLookupMap.get().getRecords(I_C_Commission_Share.class);
		assertThat(commissionShares)
				.hasSize(2)
				.extracting(I_C_Commission_Share.COLUMNNAME_C_Commission_Instance_ID,
						I_C_Commission_Share.COLUMNNAME_Commission_Product_ID,
						I_C_Commission_Share.COLUMNNAME_C_BPartner_SalesRep_ID)
				.contains(
						tuple(commissionInstances.get(0).getC_Commission_Instance_ID(),
								commissionProductId.getRepoId(),
								salesRepPartnerId.getRepoId()),
						tuple(commissionInstances.get(0).getC_Commission_Instance_ID(),
								commissionProduct2Id.getRepoId(),
								salesRepPartnerId.getRepoId()));
	}

	private void assertInstanceOK(
			final TestInvoice testInvoice,
			final List<I_C_Commission_Instance> commissionInstances)
	{
		assertThat(commissionInstances)
				.hasSize(1)
				.extracting(COLUMNNAME_C_Invoice_ID,
						COLUMNNAME_C_InvoiceLine_ID,
						COLUMNNAME_Bill_BPartner_ID,
						COLUMNNAME_M_Product_Order_ID,
						COLUMNNAME_CommissionTrigger_Type)
				.contains(tuple(testInvoice.getRepoId(),
						testInvoice.getLineRepoId(0),
						customerId.getRepoId(),
						salesProductId.getRepoId(),
						X_C_Commission_Instance.COMMISSIONTRIGGER_TYPE_CustomerCreditmemo));
	}
}
