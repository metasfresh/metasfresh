/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.ebay.ApiMode;
import de.metas.externalsystem.ebay.ExternalSystemEbayConfigId;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.metasfresh.ExternalSystemMetasfreshConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay_Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Metasfresh;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6_UOM;
import de.metas.externalsystem.model.I_ExternalSystem_Config_WooCommerce;
import de.metas.externalsystem.model.X_ExternalSystem_Config;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.externalsystem.sap.ExternalSystemSAPConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.shopware6.OrderProcessingConfig;
import de.metas.externalsystem.shopware6.ProductLookup;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.tax.TaxCategoryDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import static de.metas.externalsystem.model.X_ExternalSystem_Config_Shopware6Mapping.ISINVOICEEMAILENABLED_Yes;
import static de.metas.externalsystem.other.ExternalSystemOtherConfigRepositoryTest.createExternalConfigParameterRecord;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
class ExternalSystemConfigRepoTest
{
	private ExternalSystemConfigRepo externalSystemConfigRepo;
	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		externalSystemConfigRepo = new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository(), new TaxCategoryDAO());
	}

	@Test
	void externalSystem_Config_Alberta_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Alberta)
				.build();

		final I_ExternalSystem_Config_Alberta childRecord = newInstance(I_ExternalSystem_Config_Alberta.class);
		childRecord.setApiKey("apiKey");
		childRecord.setBaseURL("baseUrl");
		childRecord.setTenant("tenant");
		childRecord.setExternalSystemValue("testAlbertaValue");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final ExternalSystemAlbertaConfigId id = ExternalSystemAlbertaConfigId.ofRepoId(childRecord.getExternalSystem_Config_Alberta_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Shopware6_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.build();

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue("testShopware6Value");
		childRecord.setFreightCost_NormalVAT_Rates("1,2");
		childRecord.setFreightCost_Reduced_VAT_Rates("3,4");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setJSONPathConstantBPartnerLocationID("JSONPathConstantBPartnerLocationID");
		childRecord.setJSONPathEmail("JSONPathEmail");
		childRecord.setJSONPathMetasfreshID("JSONPathMetasfreshID");
		childRecord.setJSONPathShopwareID("JSONPathShopwareID");
		childRecord.setM_FreightCost_NormalVAT_Product_ID(20);
		childRecord.setM_FreightCost_ReducedVAT_Product_ID(30);
		childRecord.setM_PriceList_ID(40);
		childRecord.setProductLookup(ProductLookup.ProductNumber.getCode());
		childRecord.setIsSyncAvailableForSalesToShopware6(true);
		childRecord.setPercentageOfAvailableForSalesToSync(BigDecimal.TEN);
		childRecord.setOrderProcessing(OrderProcessingConfig.INVOICE.getCode());
		saveRecord(childRecord);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_ExternalSystem_Config_Shopware6_UOM shopware6Uom = newInstance(I_ExternalSystem_Config_Shopware6_UOM.class);
		shopware6Uom.setExternalSystem_Config_Shopware6_ID(childRecord.getExternalSystem_Config_Shopware6_ID());
		shopware6Uom.setShopwareCode("shopwareCode");
		shopware6Uom.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(shopware6Uom);

		// when
		final ExternalSystemShopware6ConfigId id = ExternalSystemShopware6ConfigId.ofRepoId(childRecord.getExternalSystem_Config_Shopware6_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_RabbitMQ_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.RabbitMQ.getCode())
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = ExternalSystemTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("testRabbitMQValue")
				.isSyncBPartnerToRabbitMQ(true)
				.build();

		// when
		final ExternalSystemRabbitMQConfigId id = ExternalSystemRabbitMQConfigId.ofRepoId(childRecord.getExternalSystem_Config_RabbitMQ_HTTP_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Metasfresh_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Metasfresh)
				.build();

		final I_ExternalSystem_Config_Metasfresh childRecord = newInstance(I_ExternalSystem_Config_Metasfresh.class);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		childRecord.setFeedbackResourceURL("feedbackURL");
		childRecord.setFeedbackResourceAuthToken("feedbackAuthToke");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue("externalSystemValue");
		saveRecord(childRecord);

		// when
		final ExternalSystemMetasfreshConfigId id = ExternalSystemMetasfreshConfigId.ofRepoId(childRecord.getExternalSystem_Config_Metasfresh_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Shopware6_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.build();

		final String value = "testShopware6Value";

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setM_PriceList_ID(1);
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setProductLookup(ProductLookup.ProductNumber.getCode());
		childRecord.setOrderProcessing(OrderProcessingConfig.INVOICE.getCode());
		saveRecord(childRecord);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_ExternalSystem_Config_Shopware6_UOM shopware6Uom = newInstance(I_ExternalSystem_Config_Shopware6_UOM.class);
		shopware6Uom.setExternalSystem_Config_Shopware6_ID(childRecord.getExternalSystem_Config_Shopware6_ID());
		shopware6Uom.setShopwareCode("shopwareCode");
		shopware6Uom.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(shopware6Uom);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Shopware6, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Alberta)
				.build();

		final String value = "testAlbertaValue";

		final I_ExternalSystem_Config_Alberta childRecord = newInstance(I_ExternalSystem_Config_Alberta.class);
		childRecord.setApiKey("apiKey");
		childRecord.setBaseURL("baseUrl");
		childRecord.setTenant("tenant");
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Alberta, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Metasfresh_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Metasfresh)
				.build();

		final String value = "externalSystemValue";

		final I_ExternalSystem_Config_Metasfresh childRecord = newInstance(I_ExternalSystem_Config_Metasfresh.class);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		childRecord.setFeedbackResourceURL("feedbackURL");
		childRecord.setFeedbackResourceAuthToken("feedbackAuthToke");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue(value);
		saveRecord(childRecord);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Metasfresh, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_RabbitMQ_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.RabbitMQ.getCode())
				.build();

		final String value = "testRabbitMQValue";

		ExternalSystemTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.isSyncBPartnerToRabbitMQ(true)
				.isAutoSendWhenCreatedByUserGroup(true)
				.subjectCreatedByUserGroupId(1)
				.build();

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.RabbitMQ, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Alberta)
				.build();

		final String value = "testAlbertaValue";

		final I_ExternalSystem_Config_Alberta childRecord = newInstance(I_ExternalSystem_Config_Alberta.class);
		childRecord.setApiKey("apiKey");
		childRecord.setBaseURL("baseUrl");
		childRecord.setTenant("tenant");
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Shopware6, value);

		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Alberta)
				.build();

		final String value = "testAlbertaValue";

		final I_ExternalSystem_Config_Alberta childRecord = newInstance(I_ExternalSystem_Config_Alberta.class);
		childRecord.setApiKey("apiKey");
		childRecord.setBaseURL("baseUrl");
		childRecord.setTenant("tenant");
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());
		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.Alberta)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId().getRepoId()).isEqualTo(childRecord.getExternalSystem_Config_Alberta_ID());
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Shopware6_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.build();

		final String value = "testShopware6Value";

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setExternalSystemValue(value);
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setM_PriceList_ID(1);
		childRecord.setProductLookup(ProductLookup.ProductNumber.getCode());
		childRecord.setOrderProcessing(OrderProcessingConfig.INVOICE.getCode());
		saveRecord(childRecord);

		final I_ExternalSystem_Config_Shopware6Mapping childMappingRecord = newInstance(I_ExternalSystem_Config_Shopware6Mapping.class);
		childMappingRecord.setC_PaymentTerm_ID(10000);
		childMappingRecord.setC_DocTypeOrder_ID(10000);
		childMappingRecord.setPaymentRule("K");
		childMappingRecord.setSeqNo(10);
		childMappingRecord.setSW6_Customer_Group("testWithAnä");
		childMappingRecord.setSW6_Payment_Method("test");
		childMappingRecord.setDescription("test");
		childMappingRecord.setExternalSystem_Config_Shopware6_ID(childRecord.getExternalSystem_Config_Shopware6_ID());
		childMappingRecord.setIsInvoiceEmailEnabled(ISINVOICEEMAILENABLED_Yes);
		childMappingRecord.setBPartner_IfExists("UPDATE_MERGE");
		childMappingRecord.setBPartner_IfNotExists("FAIL");
		childMappingRecord.setBPartnerLocation_IfExists("DONT_UPDATE");
		childMappingRecord.setBPartnerLocation_IfNotExists("CREATE");
		saveRecord(childMappingRecord);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_ExternalSystem_Config_Shopware6_UOM shopware6Uom = newInstance(I_ExternalSystem_Config_Shopware6_UOM.class);
		shopware6Uom.setExternalSystem_Config_Shopware6_ID(childRecord.getExternalSystem_Config_Shopware6_ID());
		shopware6Uom.setShopwareCode("shopwareCode");
		shopware6Uom.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(shopware6Uom);

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());
		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.Shopware6)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId().getRepoId()).isEqualTo(childRecord.getExternalSystem_Config_Shopware6_ID());
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_RabbitMQ_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.RabbitMQ.getCode())
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = ExternalSystemTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("testRabbitMQValue")
				.isSyncBPartnerToRabbitMQ(true)
				.build();

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());
		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.RabbitMQ)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId().getRepoId()).isEqualTo(childRecord.getExternalSystem_Config_RabbitMQ_HTTP_ID());
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Other_Config_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Other)
				.build();

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());

		createExternalConfigParameterRecord(externalSystemParentConfigId, "name1", "value1");
		createExternalConfigParameterRecord(externalSystemParentConfigId, "name2", "value2");

		final ExternalSystemOtherConfigId otherConfigId = ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(externalSystemParentConfigId);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(otherConfigId);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Ebay_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_Ebay);
		saveRecord(parentRecord);

		final String value = "testEbayValue";

		final I_ExternalSystem_Config_Ebay childRecord = newInstance(I_ExternalSystem_Config_Ebay.class);
		childRecord.setAppId("appId");
		childRecord.setDevId("devId");
		childRecord.setCertId("certId");
		childRecord.setRefreshToken("refreshToken");
		childRecord.setAPI_Mode(ApiMode.SANDBOX.getCode());
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue(value);
		saveRecord(childRecord);

		// when
		final ExternalSystemEbayConfigId id = ExternalSystemEbayConfigId.ofRepoId(childRecord.getExternalSystem_Config_Ebay_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Ebay_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_Ebay);
		saveRecord(parentRecord);

		final String value = "testEbayValue";

		final I_ExternalSystem_Config_Ebay childRecord = newInstance(I_ExternalSystem_Config_Ebay.class);
		childRecord.setAppId("appId");
		childRecord.setDevId("devId");
		childRecord.setCertId("certId");
		childRecord.setRefreshToken("refreshToken");
		childRecord.setAPI_Mode(ApiMode.SANDBOX.getCode());
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);


		final I_ExternalSystem_Config_Ebay_Mapping childMappingRecord = newInstance(I_ExternalSystem_Config_Ebay_Mapping.class);
		childMappingRecord.setC_PaymentTerm_ID(10000);
		childMappingRecord.setC_DocTypeOrder_ID(10000);
		childMappingRecord.setPaymentRule("K");
		childMappingRecord.setSeqNo(10);
		childMappingRecord.setEBayCustomerGroup("testWithAnä");
		childMappingRecord.setEBayPaymentMethod("test");
		childMappingRecord.setDescription("test");
		childMappingRecord.setExternalSystem_Config_Ebay_ID(childRecord.getExternalSystem_Config_Ebay_ID());
		childMappingRecord.setIsInvoiceEmailEnabled(true);
		childMappingRecord.setBPartner_IfExists("UPDATE_MERGE");
		childMappingRecord.setBPartner_IfNotExists("FAIL");
		childMappingRecord.setBPartnerLocation_IfExists("DONT_UPDATE");
		childMappingRecord.setBPartnerLocation_IfNotExists("CREATE");
		saveRecord(childMappingRecord);

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());
		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.Ebay)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId().getRepoId()).isEqualTo(childRecord.getExternalSystem_Config_Ebay_ID());
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Ebay_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_Ebay);
		saveRecord(parentRecord);

		final String value = "testEbayValue";

		final I_ExternalSystem_Config_Ebay childRecord = newInstance(I_ExternalSystem_Config_Ebay.class);
		childRecord.setIsActive(true);
		childRecord.setAppId("appId");
		childRecord.setDevId("devId");
		childRecord.setCertId("certId");
		childRecord.setRefreshToken("refreshToken");
		childRecord.setAPI_Mode(ApiMode.SANDBOX.getCode());
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Ebay, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Ebay_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_Ebay);
		saveRecord(parentRecord);

		final String value = "testEbayValue";

		final I_ExternalSystem_Config_Ebay childRecord = newInstance(I_ExternalSystem_Config_Ebay.class);
		childRecord.setAppId("appId");
		childRecord.setDevId("devId");
		childRecord.setCertId("certId");
		childRecord.setRefreshToken("refreshToken");
		childRecord.setAPI_Mode(ApiMode.SANDBOX.getCode());
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Alberta, value);

		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_Woocommerce_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_WooCommerce)
				.build();

		final I_ExternalSystem_Config_WooCommerce childRecord = newInstance(I_ExternalSystem_Config_WooCommerce.class);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		childRecord.setExternalSystemValue("testWoocommerceValue");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final ExternalSystemWooCommerceConfigId id = ExternalSystemWooCommerceConfigId.ofRepoId(childRecord.getExternalSystem_Config_WooCommerce_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Woocommerce_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_WooCommerce)
				.build();

		final String value = "testWoocommerceValue";

		final I_ExternalSystem_Config_WooCommerce childRecord = newInstance(I_ExternalSystem_Config_WooCommerce.class);
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.WOO, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Woocommerce_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_WooCommerce)
				.build();

		final String value = "testWoocommerceValue";

		final I_ExternalSystem_Config_WooCommerce childRecord = newInstance(I_ExternalSystem_Config_WooCommerce.class);
		childRecord.setCamelHttpResourceAuthKey("apiKey");
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Shopware6, value);

		//then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_GRSSignum_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_GRSSignum);
		saveRecord(parentRecord);

		final I_ExternalSystem_Config_GRSSignum childRecord = ExternalSystemTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("testGRSSignumValue")
				.syncBPartnersToRestEndpoint(true)
				.build();

		// when
		final ExternalSystemGRSSignumConfigId id = ExternalSystemGRSSignumConfigId.ofRepoId(childRecord.getExternalSystem_Config_GRSSignum_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_GRSSignum_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_GRSSignum);
		saveRecord(parentRecord);

		ExternalSystemTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("testGRSSignumValue")
				.build();

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());

		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.GRSSignum)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Metasfresh_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_Metasfresh);
		saveRecord(parentRecord);

		final I_ExternalSystem_Config_Metasfresh childRecord = newInstance(I_ExternalSystem_Config_Metasfresh.class);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		childRecord.setFeedbackResourceURL("feedbackURL");
		childRecord.setFeedbackResourceAuthToken("feedbackAuthToke");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue("externalSystemValue");
		saveRecord(childRecord);

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());

		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.Metasfresh)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_GRSSignum_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_GRSSignum);

		saveRecord(parentRecord);

		final String value = "testGRSSignumValue";

		ExternalSystemTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.syncBPartnersToRestEndpoint(true)
				.build();

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.GRSSignum, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_GRSSignum_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_GRSSignum);
		saveRecord(parentRecord);

		final String value = "testGRSSignumValue";

		ExternalSystemTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.build();

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Shopware6, value);

		//then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_Shopware6_getByQuery()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.active(false)
				.build();

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setExternalSystemValue("testShopware6Value");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setM_PriceList_ID(1);
		childRecord.setIsActive(false);
		childRecord.setProductLookup(ProductLookup.ProductNumber.getCode());
		childRecord.setOrderProcessing(OrderProcessingConfig.INVOICE.getCode());
		saveRecord(childRecord);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_ExternalSystem_Config_Shopware6_UOM shopware6Uom = newInstance(I_ExternalSystem_Config_Shopware6_UOM.class);
		shopware6Uom.setExternalSystem_Config_Shopware6_ID(childRecord.getExternalSystem_Config_Shopware6_ID());
		shopware6Uom.setShopwareCode("shopwareCode");
		shopware6Uom.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(shopware6Uom);

		final ExternalSystemConfigQuery query = ExternalSystemConfigQuery.builder()
				.parentConfigId(ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID()))
				.isActive(false)
				.build();

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByQuery(ExternalSystemType.Shopware6, query)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Shopware6_store()
	{
		// given
		final I_ExternalSystem_Config initialParentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.active(false)
				.build();

		final I_ExternalSystem_Config_Shopware6 initialChildRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		initialChildRecord.setBaseURL("baseUrl");
		initialChildRecord.setClient_Secret("secret");
		initialChildRecord.setClient_Id("id");
		initialChildRecord.setJSONPathSalesRepID("/test/salesrep");
		initialChildRecord.setExternalSystemValue("testShopware6Value");
		initialChildRecord.setExternalSystem_Config_ID(initialParentRecord.getExternalSystem_Config_ID());
		initialChildRecord.setM_PriceList_ID(1);
		initialChildRecord.setProductLookup(ProductLookup.ProductNumber.getCode());
		initialChildRecord.setIsActive(false);
		initialChildRecord.setOrderProcessing(OrderProcessingConfig.INVOICE.getCode());
		saveRecord(initialChildRecord);

		final ExternalSystemParentConfig parentConfig = externalSystemConfigRepo.getById(ExternalSystemShopware6ConfigId.ofRepoId(initialChildRecord.getExternalSystem_Config_Shopware6_ID()));

		final String baseURL = "new-baseURL";
		final String clientId = "new-clientId";
		final String clientSecret = "new-clientSecret";
		final String value = "new-value";
		final PriceListId newPriceListId = PriceListId.ofRepoId(2);

		final ExternalSystemShopware6Config childConfig = ExternalSystemShopware6Config.cast(parentConfig.getChildConfig())
				.toBuilder()
				.baseUrl(baseURL)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.priceListId(newPriceListId)
				.isActive(true)
				.value(value)
				.build();

		final ExternalSystemParentConfig updatedParentConfig = parentConfig.toBuilder()
				.active(true)
				.childConfig(childConfig)
				.build();
		// when
		externalSystemConfigRepo.saveConfig(updatedParentConfig);

		// then
		final ExternalSystemParentConfig updatedChildConfig = externalSystemConfigRepo.getById(ExternalSystemShopware6ConfigId.ofRepoId(initialChildRecord.getExternalSystem_Config_Shopware6_ID()));
		assertThat(updatedChildConfig).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(updatedChildConfig);

		assertThat(updatedChildConfig.isActive()).isTrue();

		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(updatedChildConfig.getChildConfig());
		assertThat(shopware6Config.getBaseUrl()).isEqualTo(baseURL);
		assertThat(shopware6Config.getClientId()).isEqualTo(clientId);
		assertThat(shopware6Config.getClientSecret()).isEqualTo(clientSecret);
		assertThat(shopware6Config.getIsActive()).isTrue();
		assertThat(shopware6Config.getPriceListId()).isEqualTo(newPriceListId);
		assertThat(shopware6Config.getValue()).isEqualTo(value);
	}

	@Test
	void externalSystem_Config_getActiveByType_RabbitMQ()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.RabbitMQ.getCode())
				.build();

		ExternalSystemTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.build();

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.RabbitMQ);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_getActiveByType_Metasfresh()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.Metasfresh.getCode())
				.build();

		ExternalSystemTestUtil.createMetasfreshConfigBuilder()
				.value("value")
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.build();

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.Metasfresh);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_getActiveByType_NoRecord()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.RabbitMQ.getCode())
				.build();

		ExternalSystemTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.build();

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.Alberta);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void externalSystem_Config_SAP_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_SAP)
				.build();

		final String value = "testSAPValue";

		final I_ExternalSystem_Config_SAP childRecord = ExternalSystemTestUtil.createSAPConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.build();

		ExternalSystemTestUtil.createSAPContentSourceSFTPBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		ExternalSystemTestUtil.createSAPContentSourceLocalFileBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		// when
		final ExternalSystemSAPConfigId id = ExternalSystemSAPConfigId.ofRepoId(childRecord.getExternalSystem_Config_SAP_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_SAP_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_SAP)
				.build();

		final String value = "testSAPValue";

		final I_ExternalSystem_Config_SAP childRecord = ExternalSystemTestUtil.createSAPConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.build();

		ExternalSystemTestUtil.createSAPContentSourceSFTPBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		ExternalSystemTestUtil.createSAPContentSourceLocalFileBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.SAP, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_SAP_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_SAP)
				.build();

		final String value = "testSAPValue";

		ExternalSystemTestUtil.createSAPConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.build();

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Shopware6, value);

		// then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_getActiveByType_SAP()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.SAP.getCode())
				.build();

		final I_ExternalSystem_Config_SAP childRecord = ExternalSystemTestUtil.createSAPConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.build();

		ExternalSystemTestUtil.createSAPContentSourceSFTPBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		ExternalSystemTestUtil.createSAPContentSourceLocalFileBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.SAP);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_SAP_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.SAP.getCode())
				.build();

		final String value = "testSAPValue";

		final I_ExternalSystem_Config_SAP childRecord = ExternalSystemTestUtil.createSAPConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.build();

		ExternalSystemTestUtil.createSAPContentSourceSFTPBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		ExternalSystemTestUtil.createSAPContentSourceLocalFileBuilder()
				.externalSystemConfigSAPId(childRecord.getExternalSystem_Config_SAP_ID())
				.build();

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());

		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.SAP)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_SAP_getActiveByType_NoRecord()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.SAP.getCode())
				.build();

		ExternalSystemTestUtil.createSAPConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.build();

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.Alberta);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void externalSystem_LeichMehl_Config_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_LeichMehl)
				.build();

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue("LeichMehl");
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");

		saveRecord(leichMehlConfig);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(ExternalSystemLeichMehlConfigId.ofRepoId(leichMehlConfig.getExternalSystem_Config_LeichMehl_ID()));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_LeichMehl_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_LeichMehl)
				.build();

		final String value = "testLeichMehlValue";

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue(value);
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");

		saveRecord(leichMehlConfig);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.LeichUndMehl, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_LeichMehl_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_LeichMehl)
				.build();

		final String value = "testLeichMehlValue";

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue(value);
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");

		saveRecord(leichMehlConfig);

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Shopware6, value);

		//then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_LeichMehl_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_LeichMehl)
				.build();

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue("testLeichMehlValue");
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");

		saveRecord(leichMehlConfig);

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());

		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.LeichUndMehl)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_LeichMehl_getActiveByType()
	{
		// given
		final I_ExternalSystem_Config parentRecordActive = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_LeichMehl)
				.build();

		final I_ExternalSystem_Config_LeichMehl configLeichMehl = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		configLeichMehl.setExternalSystem_Config_ID(parentRecordActive.getExternalSystem_Config_ID());
		configLeichMehl.setExternalSystemValue("testLeichMehlValue");
		configLeichMehl.setProduct_BaseFolderName("productBaseFolderName");
		configLeichMehl.setTCP_PortNumber(8080);
		configLeichMehl.setTCP_Host("tcpHost");

		saveRecord(configLeichMehl);

		// given
		final I_ExternalSystem_Config parentRecordInactive = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_LeichMehl)
				.active(false)
				.build();

		final I_ExternalSystem_Config_LeichMehl configLeichMehlInactive = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		configLeichMehlInactive.setExternalSystem_Config_ID(parentRecordInactive.getExternalSystem_Config_ID());
		configLeichMehlInactive.setExternalSystemValue("testLeichMehlValueInactive");
		configLeichMehlInactive.setProduct_BaseFolderName("productBaseFolderName");
		configLeichMehlInactive.setTCP_PortNumber(8080);
		configLeichMehlInactive.setTCP_Host("tcpHost");

		saveRecord(configLeichMehlInactive);

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.LeichUndMehl);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}
}

