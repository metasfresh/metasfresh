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

package de.metas.externalsystem.rabbitmqhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemTestUtil;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.organization.OrgId;
import de.metas.pricing.tax.TaxCategoryDAO;
import de.metas.process.PInstanceId;
import de.metas.user.UserGroupRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class ExportBPartnerToRabbitMQServiceTest
{
	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest_BPartner.json";

	private JsonExternalSystemRequest expectedJsonExternalSystemRequest;
	private ExportBPartnerToRabbitMQService exportBPartnerToRabbitMQService;

	@BeforeEach
	public void init() throws IOException
	{
		final ExternalSystemConfigService externalSystemConfigServiceMock = Mockito.mock(ExternalSystemConfigService.class);
		Mockito.when(externalSystemConfigServiceMock.getTraceId()).thenReturn("traceId");

		AdempiereTestHelper.get().init();

		exportBPartnerToRabbitMQService = new ExportBPartnerToRabbitMQService(new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository(), new TaxCategoryDAO()),
																			  new ExternalSystemMessageSender(new RabbitTemplate(), new Queue(QUEUE_NAME_MF_TO_ES)),
																					  new DataExportAuditLogRepository(),
																			  new DataExportAuditRepository(),
																					  externalSystemConfigServiceMock,
																					  new UserGroupRepository());

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final InputStream externalSystemIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		expectedJsonExternalSystemRequest = objectMapper.readValue(externalSystemIS, JsonExternalSystemRequest.class);
	}

	@Test
	public void given_whenToJsonExternalSystemRequest_thenReturnExternalSystemRequest()
	{
		// given
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(3);

		final I_AD_Org orgRecord = newInstance(I_AD_Org.class);
		orgRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		orgRecord.setValue("orgCode");
		saveRecord(orgRecord);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BPartner_ID(1000000);
		bpartner.setAD_Org_ID(orgRecord.getAD_Org_ID());
		saveRecord(bpartner);

		final I_ExternalSystem_Config externalSystemParentConfig = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.RabbitMQ.getCode())
				.customParentConfigId(1)
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP configRabbitMQHttp = ExternalSystemTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(externalSystemParentConfig.getExternalSystem_Config_ID())
				.isSyncBPartnerToRabbitMQ(true)
				.customChildConfigId(2)
				.value("value")
				.build();

		final TableRecordReference bPartnerRecordRef = TableRecordReference.of(I_C_BPartner.Table_Name, bpartner.getC_BPartner_ID());
		final ExternalSystemRabbitMQConfigId externalSystemRabbitMQConfigId = ExternalSystemRabbitMQConfigId.ofRepoId(configRabbitMQHttp.getExternalSystem_Config_RabbitMQ_HTTP_ID());

		// when
		final Optional<JsonExternalSystemRequest> externalSystemRequest = exportBPartnerToRabbitMQService.getExportExternalSystemRequest(
				externalSystemRabbitMQConfigId, bPartnerRecordRef, pInstanceId);

		// then
		assertThat(externalSystemRequest).isPresent();
		assertThat(externalSystemRequest.get()).isEqualTo(expectedJsonExternalSystemRequest);
	}

	private void createPrerequisites()
	{
		final I_ExternalSystem_Config externalSystemParentConfig = newInstance(I_ExternalSystem_Config.class);
		externalSystemParentConfig.setExternalSystem_Config_ID(1);
		externalSystemParentConfig.setName("ParentConfig");
		externalSystemParentConfig.setIsActive(true);
		externalSystemParentConfig.setType(ExternalSystemType.RabbitMQ.getCode());
		externalSystemParentConfig.setWriteAudit(true);
		externalSystemParentConfig.setAuditFileFolder("fileFolder");

		saveRecord(externalSystemParentConfig);

		final I_ExternalSystem_Config_RabbitMQ_HTTP externalSystemConfigRabbitMQHttp = newInstance(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
		externalSystemConfigRabbitMQHttp.setExternalSystem_Config_RabbitMQ_HTTP_ID(2);
		externalSystemConfigRabbitMQHttp.setExternalSystem_Config_ID(1);
		externalSystemConfigRabbitMQHttp.setExternalSystemValue("value");
		externalSystemConfigRabbitMQHttp.setIsActive(true);
		externalSystemConfigRabbitMQHttp.setRemoteURL("https://test");
		externalSystemConfigRabbitMQHttp.setRouting_Key("key");
		externalSystemConfigRabbitMQHttp.setAuthToken("authToken");
		externalSystemConfigRabbitMQHttp.setIsSyncBPartnersToRabbitMQ(true);

		saveRecord(externalSystemConfigRabbitMQHttp);
	}
}
