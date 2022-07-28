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

import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6_UOM;
import de.metas.externalsystem.model.I_ExternalSystem_Config_WooCommerce;
import de.metas.externalsystem.model.X_ExternalSystem_Config;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.externalsystem.model.X_ExternalSystem_Config_Shopware6Mapping.ISINVOICEEMAILENABLED_Yes;
import static de.metas.externalsystem.other.ExternalSystemOtherConfigRepositoryTest.createExternalConfigParameterRecord;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

class ExternalSystemConfigRepoTest
{

	private ExternalSystemConfigRepo externalSystemConfigRepo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		externalSystemConfigRepo = new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository());
	}

	@BeforeAll
	static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	void externalSystem_Config_Alberta_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Shopware6_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.build();

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue("testShopware6Value");
		childRecord.setJSONPathConstantBPartnerID("/test/bp");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setJSONPathConstantBPartnerLocationID("/test/bpl");
		childRecord.setM_PriceList_ID(1);
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_RabbitMQ_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_RabbitMQRESTAPI)
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = newInstance(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
		childRecord.setExternalSystemValue("testRabbitMQValue");
		childRecord.setRemoteURL("remoteURL");
		childRecord.setRouting_Key("routingKey");
		childRecord.setAuthToken("authToken");
		childRecord.setIsSyncBPartnersToRabbitMQ(true);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final ExternalSystemRabbitMQConfigId id = ExternalSystemRabbitMQConfigId.ofRepoId(childRecord.getExternalSystem_Config_RabbitMQ_HTTP_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Shopware6_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.build();

		final String value = "testShopware6Value";

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setJSONPathConstantBPartnerID("/test/bp");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setJSONPathConstantBPartnerLocationID("/test/bpl");
		childRecord.setM_PriceList_ID(1);
		childRecord.setExternalSystemValue(value);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_RabbitMQ_getByTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_RabbitMQRESTAPI)
				.build();

		final String value = "testRabbitMQValue";

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = newInstance(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
		childRecord.setExternalSystemValue(value);
		childRecord.setRemoteURL("remoteURL");
		childRecord.setRouting_Key("routingKey");
		childRecord.setAuthToken("authToken");
		childRecord.setIsSyncBPartnersToRabbitMQ(true);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.RabbitMQ, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Alberta_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Shopware6_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.build();

		final String value = "testShopware6Value";

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setExternalSystemValue(value);
		childRecord.setJSONPathConstantBPartnerID("/test/bp");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setJSONPathConstantBPartnerLocationID("/test/bpl");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setM_PriceList_ID(1);
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_RabbitMQ_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_RabbitMQRESTAPI)
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = newInstance(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
		childRecord.setExternalSystemValue("testRabbitMQValue");
		childRecord.setRemoteURL("remoteURL");
		childRecord.setRouting_Key("routingKey");
		childRecord.setAuthToken("authToken");
		childRecord.setIsSyncBPartnersToRabbitMQ(true);
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		saveRecord(childRecord);

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());
		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.RabbitMQ)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId().getRepoId()).isEqualTo(childRecord.getExternalSystem_Config_RabbitMQ_HTTP_ID());
		expect(result).toMatchSnapshot();
	}
	
	@Test
	void externalSystem_Other_Config_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Woocommerce_getById()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Woocommerce_getTypeAndValue()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Woocommerce_getByTypeAndValue_wrongType()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
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

		final I_ExternalSystem_Config_GRSSignum childRecord = newInstance(I_ExternalSystem_Config_GRSSignum.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue("testGRSSignumValue");
		childRecord.setCamelHttpResourceAuthKey("authKey");
		saveRecord(childRecord);

		// when
		final ExternalSystemGRSSignumConfigId id = ExternalSystemGRSSignumConfigId.ofRepoId(childRecord.getExternalSystem_Config_GRSSignum_ID());
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getById(id);

		// then
		assertThat(result).isNotNull();
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_GRSSignum_getByTypeAndParent()
	{
		// given
		final I_ExternalSystem_Config parentRecord = newInstance(I_ExternalSystem_Config.class);
		parentRecord.setName("name");
		parentRecord.setType(X_ExternalSystem_Config.TYPE_GRSSignum);
		saveRecord(parentRecord);

		final I_ExternalSystem_Config_GRSSignum childRecord = newInstance(I_ExternalSystem_Config_GRSSignum.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue("testGRSSignumValue");
		childRecord.setCamelHttpResourceAuthKey("authKey");
		saveRecord(childRecord);

		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(parentRecord.getExternalSystem_Config_ID());

		// when
		final IExternalSystemChildConfig result = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, ExternalSystemType.GRSSignum)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemChildConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect(result).toMatchSnapshot();
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

		final I_ExternalSystem_Config_GRSSignum childRecord = newInstance(I_ExternalSystem_Config_GRSSignum.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue(value);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		saveRecord(childRecord);

		// when
		final ExternalSystemParentConfig result = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.GRSSignum, value)
				.orElseThrow(() -> new RuntimeException("Something went wrong, no ExternalSystemParentConfig found!"));

		// then
		assertThat(result).isNotNull();
		expect(result).toMatchSnapshot();
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

		final I_ExternalSystem_Config_GRSSignum childRecord = newInstance(I_ExternalSystem_Config_GRSSignum.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setExternalSystemValue(value);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		saveRecord(childRecord);

		// when
		final Optional<ExternalSystemParentConfig> externalSystemParentConfig = externalSystemConfigRepo.getByTypeAndValue(ExternalSystemType.Shopware6, value);

		//then
		assertThat(externalSystemParentConfig).isEmpty();
	}

	@Test
	void externalSystem_Config_Shopware6_getByQuery()
	{
		// given
		final I_ExternalSystem_Config parentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.active(false)
				.build();

		final I_ExternalSystem_Config_Shopware6 childRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setClient_Secret("secret");
		childRecord.setClient_Id("id");
		childRecord.setJSONPathConstantBPartnerID("/test/bp");
		childRecord.setJSONPathSalesRepID("/test/salesrep");
		childRecord.setJSONPathConstantBPartnerLocationID("/test/bpl");
		childRecord.setExternalSystemValue("testShopware6Value");
		childRecord.setExternalSystem_Config_ID(parentRecord.getExternalSystem_Config_ID());
		childRecord.setM_PriceList_ID(1);
		childRecord.setIsActive(false);
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
		expect(result).toMatchSnapshot();
	}

	@Test
	void externalSystem_Config_Shopware6_store()
	{
		// given
		final I_ExternalSystem_Config initialParentRecord = createI_ExternalSystem_ConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_Shopware6)
				.active(false)
				.build();

		final I_ExternalSystem_Config_Shopware6 initialChildRecord = newInstance(I_ExternalSystem_Config_Shopware6.class);
		initialChildRecord.setBaseURL("baseUrl");
		initialChildRecord.setClient_Secret("secret");
		initialChildRecord.setClient_Id("id");
		initialChildRecord.setJSONPathConstantBPartnerID("/test/bp");
		initialChildRecord.setJSONPathSalesRepID("/test/salesrep");
		initialChildRecord.setJSONPathConstantBPartnerLocationID("/test/bpl");
		initialChildRecord.setExternalSystemValue("testShopware6Value");
		initialChildRecord.setExternalSystem_Config_ID(initialParentRecord.getExternalSystem_Config_ID());
		initialChildRecord.setM_PriceList_ID(1);
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
				.isActive(true)
				.childConfig(childConfig)
				.build();
		// when
		externalSystemConfigRepo.saveConfig(updatedParentConfig);

		// then
		final ExternalSystemParentConfig updatedChildConfig = externalSystemConfigRepo.getById(ExternalSystemShopware6ConfigId.ofRepoId(initialChildRecord.getExternalSystem_Config_Shopware6_ID()));
		assertThat(updatedChildConfig).isNotNull();
		expect(updatedChildConfig).toMatchSnapshot();

		assertThat(updatedChildConfig.getIsActive()).isTrue();

		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(updatedChildConfig.getChildConfig());
		assertThat(shopware6Config.getBaseUrl()).isEqualTo(baseURL);
		assertThat(shopware6Config.getClientId()).isEqualTo(clientId);
		assertThat(shopware6Config.getClientSecret()).isEqualTo(clientSecret);
		assertThat(shopware6Config.getIsActive()).isTrue();
		assertThat(shopware6Config.getPriceListId()).isEqualTo(newPriceListId);
		assertThat(shopware6Config.getValue()).isEqualTo(value);
	}

	@NonNull
	@Builder(builderMethodName = "createI_ExternalSystem_ConfigBuilder", builderClassName = "I_ExternalSystem_ConfigBuilder")
	private I_ExternalSystem_Config createI_ExternalSystem_Config(
			@NonNull final String type,
			@Nullable final Boolean active)
	{
		final Boolean isActive = CoalesceUtil.coalesceNotNull(active, Boolean.TRUE);

		final I_ExternalSystem_Config record = newInstance(I_ExternalSystem_Config.class);
		record.setName("name");
		record.setType(type);
		record.setIsActive(isActive);
		record.setWriteAudit(true);
		record.setAuditFileFolder("auditFileFolder");
		saveRecord(record);

		return record;
	}
}
