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

package de.metas.externalsystem.grssignum;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
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

public class ExportToGRSServiceTest
{
	private final static BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(1000000);
	private final static PInstanceId PINSTANCE_ID = PInstanceId.ofRepoId(3);

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";

	private JsonExternalSystemRequest expectedJsonExternalSystemRequest;
	private ExportToGRSService exportToGRSService;

	@BeforeEach
	public void init() throws IOException
	{
		final ExternalSystemConfigService externalSystemConfigServiceMock = Mockito.mock(ExternalSystemConfigService.class);
		Mockito.when(externalSystemConfigServiceMock.getTraceId()).thenReturn("traceId");

		AdempiereTestHelper.get().init();

		exportToGRSService = new ExportToGRSService(new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository()),
													new DataExportAuditRepository(),
													new DataExportAuditLogRepository(),
													new ExternalSystemMessageSender(new RabbitTemplate(), new Queue(QUEUE_NAME_MF_TO_ES)),
													externalSystemConfigServiceMock);

		createPrerequisites();

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final InputStream externalSystemIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		expectedJsonExternalSystemRequest = objectMapper.readValue(externalSystemIS, JsonExternalSystemRequest.class);
	}

	@Test
	public void givenGRSConfigId_whenToJsonExternalSystemRequest_thenReturnExternalSystemRequest()
	{
		// given
		final ExternalSystemGRSSignumConfigId externalSystemGRSSignumConfigId = createGRSSignumConfigBuilder()
				.parentConfigId(1)
				.syncBPartnersToRestEndpoint(true)
				.build();

		// when
		final Optional<JsonExternalSystemRequest> externalSystemRequest = exportToGRSService.toJsonExternalSystemRequest(
				externalSystemGRSSignumConfigId, BPARTNER_ID, PINSTANCE_ID);

		// then
		assertThat(externalSystemRequest).isPresent();
		assertThat(externalSystemRequest.get()).isEqualTo(expectedJsonExternalSystemRequest);
	}

	@Test
	public void givenSyncBPartnersToRestEndpointDisabled_whenToJsonExternalSystemRequest_thenReturnNoExternalSystemRequest()
	{
		// given
		final ExternalSystemGRSSignumConfigId externalSystemGRSSignumConfigId = createGRSSignumConfigBuilder()
				.parentConfigId(2)
				.syncBPartnersToRestEndpoint(false)
				.build();

		// when
		final Optional<JsonExternalSystemRequest> externalSystemRequest = exportToGRSService.toJsonExternalSystemRequest(
				externalSystemGRSSignumConfigId, BPARTNER_ID, PINSTANCE_ID);

		// then
		assertThat(externalSystemRequest).isNotPresent();
	}

	@Builder(builderMethodName = "createGRSSignumConfigBuilder", builderClassName = "GRSSignumConfigBuilder")
	private @NonNull ExternalSystemGRSSignumConfigId createGRSSignumConfig(
			@NonNull final Integer parentConfigId,
			final boolean syncBPartnersToRestEndpoint)
	{
		final I_ExternalSystem_Config parentConfig = newInstance(I_ExternalSystem_Config.class);
		parentConfig.setExternalSystem_Config_ID(parentConfigId);
		parentConfig.setName("ParentConfig");
		parentConfig.setIsActive(true);
		parentConfig.setType(ExternalSystemType.GRSSignum.getCode());
		parentConfig.setWriteAudit(true);
		parentConfig.setAuditFileFolder("fileFolder");

		saveRecord(parentConfig);

		final Boolean isSyncBPartnersToRestEndpoint = CoalesceUtil.coalesceNotNull(syncBPartnersToRestEndpoint, Boolean.FALSE);

		final I_ExternalSystem_Config_GRSSignum grsConfig = newInstance(I_ExternalSystem_Config_GRSSignum.class);
		grsConfig.setBaseURL("baseUrl");
		grsConfig.setExternalSystem_Config_ID(parentConfig.getExternalSystem_Config_ID());
		grsConfig.setExternalSystemValue("grsValue");
		grsConfig.setTenantId("tenantId");
		grsConfig.setCamelHttpResourceAuthKey("authKey");
		grsConfig.setAuthToken("authToken");
		grsConfig.setIsSyncBPartnersToRestEndpoint(isSyncBPartnersToRestEndpoint);
		saveRecord(grsConfig);

		return ExternalSystemGRSSignumConfigId.ofRepoId(grsConfig.getExternalSystem_Config_GRSSignum_ID());
	}

	private void createPrerequisites()
	{
		final I_AD_Org orgRecord = newInstance(I_AD_Org.class);
		orgRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		orgRecord.setValue("orgCode");
		saveRecord(orgRecord);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BPartner_ID(BPARTNER_ID.getRepoId());
		bpartner.setAD_Org_ID(orgRecord.getAD_Org_ID());
		saveRecord(bpartner);
	}
}
