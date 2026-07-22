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
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.leichmehl.PLUType;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedImportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import de.metas.externalsystem.model.X_ExternalSystem_Endpoint;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6_UOM;
import de.metas.externalsystem.model.I_ExternalSystem_Config_WooCommerce;
import de.metas.externalsystem.model.X_ExternalSystem_Config_LeichMehl;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.shopware6.ProductLookup;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.pricing.PriceListId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static de.metas.externalsystem.ExternalSystemType.Alberta;
import static de.metas.externalsystem.ExternalSystemType.LeichUndMehl;
import static de.metas.externalsystem.ExternalSystemType.Other;
import static de.metas.externalsystem.ExternalSystemType.RabbitMQ;
import static de.metas.externalsystem.ExternalSystemType.Shopware6;
import static de.metas.externalsystem.ExternalSystemType.WOO;
import static de.metas.externalsystem.model.X_ExternalSystem_Config_Shopware6Mapping.ISINVOICEEMAILENABLED_Yes;
import static de.metas.externalsystem.other.ExternalSystemOtherConfigRepositoryTest.createExternalConfigParameterRecord;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
class ExternalSystemConfigRepoTest
{

	private ExternalSystemConfigRepo externalSystemConfigRepo;
	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier_PerTableSequence();
		externalSystemConfigRepo = ExternalSystemConfigRepo.newInstanceForUnitTesting();
	}

	@Test
	void externalSystem_Config_Alberta_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Alberta.getValue())
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Shopware6.getValue())
				.build();

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue("testShopware6Value");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setM_PriceList_ID(1);
		childRecord.setProductLookup(ProductLookup.ProductNumber.getCode());
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(RabbitMQ.getValue())
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = ExternalSystemConfigTestUtil.createRabbitMQConfigBuilder()
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
	void externalSystem_Config_Shopware6_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Shopware6.getValue())
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
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(Shopware6, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Alberta.getValue())
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
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(Alberta, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_RabbitMQ_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(RabbitMQ.getValue())
				.build();

		final String value = "testRabbitMQValue";

		ExternalSystemConfigTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.isSyncBPartnerToRabbitMQ(true)
				.isAutoSendWhenCreatedByUserGroup(true)
				.subjectCreatedByUserGroupId(1)
				.build();

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(RabbitMQ, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Alberta.getValue())
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
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(Shopware6, value);

		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Alberta.getValue())
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
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, Alberta)
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Shopware6.getValue())
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
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, Shopware6)
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(RabbitMQ.getValue())
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = ExternalSystemConfigTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("testRabbitMQValue")
				.isSyncBPartnerToRabbitMQ(true)
				.build();

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());
		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, RabbitMQ)
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Other.getValue())
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
	void externalSystem_Config_Woocommerce_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(WOO.getValue())
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(WOO.getValue())
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(WOO.getValue())
				.build();

		final String value = "testWoocommerceValue";

		final I_ExternalSystem_Config_WooCommerce childRecord = newInstance(I_ExternalSystem_Config_WooCommerce.class);
		childRecord.setCamelHttpResourceAuthKey("apiKey");
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(Shopware6, value);

		//then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_GRSSignum_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setExternalSystem_ID(ExternalSystemTestHelper.createExternalSystemIfNotExists(ExternalSystemType.GRSSignum).getId().getRepoId());
		saveRecord(parentRecord);

		final I_ExternalSystem_Config_GRSSignum childRecord = ExternalSystemConfigTestUtil.createGrsConfigBuilder()
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
		parentRecord.setExternalSystem_ID(ExternalSystemTestHelper.createExternalSystemIfNotExists(ExternalSystemType.GRSSignum).getId().getRepoId());
		saveRecord(parentRecord);

		ExternalSystemConfigTestUtil.createGrsConfigBuilder()
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
	void externalSystem_Config_GRSSignum_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setExternalSystem_ID(ExternalSystemTestHelper.createExternalSystemIfNotExists(ExternalSystemType.GRSSignum).getId().getRepoId());

		saveRecord(parentRecord);

		final String value = "testGRSSignumValue";

		ExternalSystemConfigTestUtil.createGrsConfigBuilder()
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
		parentRecord.setExternalSystem_ID(ExternalSystemTestHelper.createExternalSystemIfNotExists(ExternalSystemType.GRSSignum).getId().getRepoId());
		saveRecord(parentRecord);

		final String value = "testGRSSignumValue";

		ExternalSystemConfigTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value(value)
				.build();

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(Shopware6, value);

		//then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_Shopware6_getByQuery()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Shopware6.getValue())
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
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByQuery(Shopware6, query)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_Shopware6_store()
	{
		// given
		final I_ExternalSystem_Config initialParentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Shopware6.getValue())
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
	void externalSystem_LeichMehl_Config_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(LeichUndMehl.getValue())
				.build();

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue("LeichMehl");
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setPluFileDestination(X_ExternalSystem_Config_LeichMehl.PLUFILEDESTINATION_Disk);
		leichMehlConfig.setPluFileLocalFolder("/serverFolder");
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");
		leichMehlConfig.setCU_TU_PLU(PLUType.CU.getCode());

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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(LeichUndMehl.getValue())
				.build();

		final String value = "testLeichMehlValue";

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue(value);
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setPluFileDestination(X_ExternalSystem_Config_LeichMehl.PLUFILEDESTINATION_TCP);
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");
		leichMehlConfig.setCU_TU_PLU(PLUType.CU.getCode());
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
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(LeichUndMehl.getValue())
				.build();

		final String value = "testLeichMehlValue";

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue(value);
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");
		leichMehlConfig.setCU_TU_PLU(PLUType.CU.getCode());

		saveRecord(leichMehlConfig);

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(Shopware6, value);

		//then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_LeichMehl_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(LeichUndMehl.getValue())
				.build();

		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		leichMehlConfig.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		leichMehlConfig.setExternalSystemValue("testLeichMehlValue");
		leichMehlConfig.setProduct_BaseFolderName("productBaseFolderName");
		leichMehlConfig.setPluFileDestination(X_ExternalSystem_Config_LeichMehl.PLUFILEDESTINATION_TCP);
		leichMehlConfig.setTCP_PortNumber(8080);
		leichMehlConfig.setTCP_Host("tcpHost");
		leichMehlConfig.setCU_TU_PLU(PLUType.CU.getCode());

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
		final I_ExternalSystem_Config parentRecordActive = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(LeichUndMehl.getValue())
				.build();

		final I_ExternalSystem_Config_LeichMehl configLeichMehl = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		configLeichMehl.setExternalSystem_Config_ID(parentRecordActive.getExternalSystem_Config_ID());
		configLeichMehl.setExternalSystemValue("testLeichMehlValue");
		configLeichMehl.setProduct_BaseFolderName("productBaseFolderName");
		configLeichMehl.setPluFileDestination(X_ExternalSystem_Config_LeichMehl.PLUFILEDESTINATION_TCP);
		configLeichMehl.setTCP_PortNumber(8080);
		configLeichMehl.setTCP_Host("tcpHost");
		configLeichMehl.setCU_TU_PLU(PLUType.CU.getCode());

		saveRecord(configLeichMehl);

		// given
		final I_ExternalSystem_Config parentRecordInactive = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(LeichUndMehl.getValue())
				.active(false)
				.build();

		final I_ExternalSystem_Config_LeichMehl configLeichMehlInactive = newInstance(I_ExternalSystem_Config_LeichMehl.class);
		configLeichMehlInactive.setExternalSystem_Config_ID(parentRecordInactive.getExternalSystem_Config_ID());
		configLeichMehlInactive.setExternalSystemValue("testLeichMehlValueInactive");
		configLeichMehlInactive.setProduct_BaseFolderName("productBaseFolderName");
		configLeichMehlInactive.setPluFileDestination(X_ExternalSystem_Config_LeichMehl.PLUFILEDESTINATION_TCP);
		configLeichMehlInactive.setTCP_PortNumber(8080);
		configLeichMehlInactive.setTCP_Host("tcpHost");
		configLeichMehlInactive.setCU_TU_PLU(PLUType.CU.getCode());

		saveRecord(configLeichMehlInactive);

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.LeichUndMehl);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_getActiveByType_RabbitMQ()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(RabbitMQ.getValue())
				.build();

		ExternalSystemConfigTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.build();

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(RabbitMQ);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void externalSystem_Config_getActiveByType_NoRecord()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(RabbitMQ.getValue())
				.build();

		ExternalSystemConfigTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.build();

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(Alberta);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void getChildByParentIdAndType_unhandledParentType_returnsEmpty()
	{
		// given: a parent config of a custom external-system type ("eddyson") that has no
		// per-parent child-config table.
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type("eddyson")
				.build();

		// when
		final Optional<IExternalSystemChildConfig> result = externalSystemConfigRepo.getChildByParentIdAndType(
				ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID()),
				ExternalSystemType.ofValue("eddyson"));

		// then: no child of that type -> empty (must NOT throw "Unsupported type", which would
		// crash the ExternalSystem_Config type-change interceptor).
		assertThat(result).isEmpty();
	}

	@Test
	void getChildByParentIdAndType_scriptedExportConversion_withChild_returnsPresent()
	{
		// Regression guard: ScriptedExportConversion DOES have a per-parent child table (modelled
		// 0..many), so the lookup must find its child rather than fall through to the empty
		// catch-all — otherwise the type-change interceptor would let a scripted-export parent be
		// re-typed and silently orphan its child rows.
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.ScriptedExportConversion.getValue())
				.build();

		final I_ExternalSystem_Endpoint endpoint = newInstance(I_ExternalSystem_Endpoint.class);
		endpoint.setValue("export-endpoint");
		saveRecord(endpoint);

		final I_ExternalSystem_Config_ScriptedExportConversion child = newInstance(I_ExternalSystem_Config_ScriptedExportConversion.class);
		child.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		child.setExternalSystem_Endpoint_ID(endpoint.getExternalSystem_Endpoint_ID());
		child.setExternalSystemValue("export-orders");
		child.setScriptIdentifier("echo");
		child.setAD_Table_ID(318);
		child.setWhereClause("1=1");
		saveRecord(child);

		// when
		final Optional<IExternalSystemChildConfig> result = externalSystemConfigRepo.getChildByParentIdAndType(
				ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID()),
				ExternalSystemType.ScriptedExportConversion);

		// then
		assertThat(result).isPresent();
	}

	@Test
	void getActiveByType_alberta_returnsValidConfig()
	{
		// Guard against the resilience filter using the wrong expected type (which would filter
		// out every valid config for that reader): a correctly-parented Alberta config must be returned.
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(Alberta.getValue())
				.build();

		final I_ExternalSystem_Config_Alberta child = newInstance(I_ExternalSystem_Config_Alberta.class);
		child.setApiKey("apiKey");
		child.setBaseURL("baseUrl");
		child.setTenant("tenant");
		child.setExternalSystemValue("alberta-value");
		child.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(child);

		// when / then
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(Alberta);
		assertThat(result)
				.extracting(config -> config.getId().getRepoId())
				.containsExactly(parentRecord.getExternalSystem_Config_ID());
	}

	@Test
	void getActiveByType_rabbitMQ_skipsConfigWithMismatchedParentType()
	{
		// Guard that the RabbitMQ reader also filters (it was missing the filter): a RabbitMQ child
		// under a wrong-typed parent must be skipped, not 500 the status endpoint.
		final I_ExternalSystem_Config eddysonParent = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type("eddyson")
				.build();
		ExternalSystemConfigTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(eddysonParent.getExternalSystem_Config_ID())
				.value("rabbit-value")
				.isSyncBPartnerToRabbitMQ(false)
				.build();

		// when / then: skipped (not returned), no throw
		assertThat(externalSystemConfigRepo.getActiveByType(RabbitMQ)).isEmpty();
	}

	@Test
	void getActiveByType_scriptedImportConversion_skipsConfigWithMismatchedParentType()
	{
		// given: a VALID scripted-import config under a scripted-import-typed parent ...
		final I_ExternalSystem_Config validParent = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.ScriptedImportConversion.getValue())
				.build();
		final I_ExternalSystem_Endpoint validEndpoint = newInstance(I_ExternalSystem_Endpoint.class);
		validEndpoint.setValue("valid-orders-endpoint");
		validEndpoint.setTransportType(X_ExternalSystem_Endpoint.TRANSPORTTYPE_HTTP);
		validEndpoint.setIsArrayFanOut(false);
		saveRecord(validEndpoint);

		final I_ExternalSystem_Config_ScriptedImportConversion validChild = newInstance(I_ExternalSystem_Config_ScriptedImportConversion.class);
		validChild.setExternalSystem_Config_ID(validParent.getExternalSystem_Config_ID());
		validChild.setExternalSystemValue("valid-orders");
		validChild.setScriptIdentifier("echo");
		validChild.setExternalSystem_Endpoint_ID(validEndpoint.getExternalSystem_Endpoint_ID());
		validChild.setAD_User_Import_ID(100);
		saveRecord(validChild);

		// ... and an INCONSISTENT scripted-import config created under a wrong-typed ("eddyson") parent.
		final I_ExternalSystem_Config eddysonParent = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type("eddyson")
				.build();
		final I_ExternalSystem_Endpoint mismatchedEndpoint = newInstance(I_ExternalSystem_Endpoint.class);
		mismatchedEndpoint.setValue("orders-endpoint");
		mismatchedEndpoint.setTransportType(X_ExternalSystem_Endpoint.TRANSPORTTYPE_HTTP);
		mismatchedEndpoint.setIsArrayFanOut(false);
		saveRecord(mismatchedEndpoint);

		final I_ExternalSystem_Config_ScriptedImportConversion mismatchedChild = newInstance(I_ExternalSystem_Config_ScriptedImportConversion.class);
		mismatchedChild.setExternalSystem_Config_ID(eddysonParent.getExternalSystem_Config_ID());
		mismatchedChild.setExternalSystemValue("ORDERS");
		mismatchedChild.setScriptIdentifier("echo");
		mismatchedChild.setExternalSystem_Endpoint_ID(mismatchedEndpoint.getExternalSystem_Endpoint_ID());
		mismatchedChild.setAD_User_Import_ID(100);
		saveRecord(mismatchedChild);

		// when
		final ImmutableList<ExternalSystemParentConfig> result = externalSystemConfigRepo.getActiveByType(ExternalSystemType.ScriptedImportConversion);

		// then: the mismatched config is skipped (not returned) and does NOT 500 the whole
		// status endpoint; only the valid config is returned.
		assertThat(result)
				.extracting(config -> config.getId().getRepoId())
				.containsExactly(validParent.getExternalSystem_Config_ID());
	}

	// The resilience filter was added to all readers; guard each of the previously-untested ones with
	// a positive test (a correctly-parented config is still returned — catches a wrong expected-type
	// argument that would filter everything out) and a mismatch test (a wrong-typed parent is skipped,
	// not a 500).

	@Test
	void getActiveByType_woo_returnsValidConfig()
	{
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(WOO.getValue())
				.build();

		final I_ExternalSystem_Config_WooCommerce child = newInstance(I_ExternalSystem_Config_WooCommerce.class);
		child.setExternalSystemValue("woo-value");
		child.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(child);

		assertThat(externalSystemConfigRepo.getActiveByType(WOO))
				.extracting(config -> config.getId().getRepoId())
				.containsExactly(parentRecord.getExternalSystem_Config_ID());
	}

	@Test
	void getActiveByType_woo_skipsConfigWithMismatchedParentType()
	{
		final I_ExternalSystem_Config eddysonParent = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type("eddyson")
				.build();

		final I_ExternalSystem_Config_WooCommerce child = newInstance(I_ExternalSystem_Config_WooCommerce.class);
		child.setExternalSystemValue("woo-value");
		child.setExternalSystem_Config_ID(eddysonParent.getExternalSystem_Config_ID());
		saveRecord(child);

		assertThat(externalSystemConfigRepo.getActiveByType(WOO)).isEmpty();
	}

	@Test
	void getActiveByType_grs_returnsValidConfig()
	{
		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.GRSSignum.getValue())
				.build();

		ExternalSystemConfigTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("grs-value")
				.syncBPartnersToRestEndpoint(true)
				.build();

		assertThat(externalSystemConfigRepo.getActiveByType(ExternalSystemType.GRSSignum))
				.extracting(config -> config.getId().getRepoId())
				.containsExactly(parentRecord.getExternalSystem_Config_ID());
	}

	@Test
	void getActiveByType_grs_skipsConfigWithMismatchedParentType()
	{
		final I_ExternalSystem_Config eddysonParent = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type("eddyson")
				.build();

		ExternalSystemConfigTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(eddysonParent.getExternalSystem_Config_ID())
				.value("grs-value")
				.syncBPartnersToRestEndpoint(true)
				.build();

		assertThat(externalSystemConfigRepo.getActiveByType(ExternalSystemType.GRSSignum)).isEmpty();
	}

	@Test
	void getActiveByType_pcm_returnsValidConfig()
	{
		// PCM's config build requires a regular (non-zero) AD_Org_ID.
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);

		final I_ExternalSystem_Config parentRecord = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.ProCareManagement.getValue())
				.build();

		final I_ExternalSystem_Config_ProCareManagement child = newInstance(I_ExternalSystem_Config_ProCareManagement.class);
		child.setAD_Org_ID(org.getAD_Org_ID());
		child.setExternalSystemValue("pcm-value");
		child.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(child);

		assertThat(externalSystemConfigRepo.getActiveByType(ExternalSystemType.ProCareManagement))
				.extracting(config -> config.getId().getRepoId())
				.containsExactly(parentRecord.getExternalSystem_Config_ID());
	}

	@Test
	void getActiveByType_pcm_skipsConfigWithMismatchedParentType()
	{
		final I_ExternalSystem_Config eddysonParent = ExternalSystemConfigTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type("eddyson")
				.build();

		// no AD_Org_ID needed: the resilience filter skips this row before the PCM config is built.
		final I_ExternalSystem_Config_ProCareManagement child = newInstance(I_ExternalSystem_Config_ProCareManagement.class);
		child.setExternalSystemValue("pcm-value");
		child.setExternalSystem_Config_ID(eddysonParent.getExternalSystem_Config_ID());
		saveRecord(child);

		assertThat(externalSystemConfigRepo.getActiveByType(ExternalSystemType.ProCareManagement)).isEmpty();
	}
}

