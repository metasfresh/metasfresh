/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemTestUtil;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.X_ExternalSystem_Config;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
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

public class ExportHUToGRSServiceTest
{
	private final static HuId HU_ID = HuId.ofRepoId(1000000);

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST_SYNC_HUS = "0_JsonExternalSystemRequest_HU.json";

	private JsonExternalSystemRequest expectedJsonExternalSystemRequest;
	private ExportHUToGRSService exportHUToGRSService;

	@BeforeEach
	public void init() throws IOException
	{
		final ExternalSystemConfigService externalSystemConfigServiceMock = Mockito.mock(ExternalSystemConfigService.class);
		Mockito.when(externalSystemConfigServiceMock.getTraceId()).thenReturn("traceId");

		AdempiereTestHelper.get().init();

		exportHUToGRSService = new ExportHUToGRSService(new DataExportAuditRepository(),
														new DataExportAuditLogRepository(),
														new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository()),
														new ExternalSystemMessageSender(new RabbitTemplate(), new Queue(QUEUE_NAME_MF_TO_ES)),
														externalSystemConfigServiceMock);
		createPrerequisites();

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final InputStream externalSystemIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST_SYNC_HUS);
		expectedJsonExternalSystemRequest = objectMapper.readValue(externalSystemIS, JsonExternalSystemRequest.class);
	}

	@Test
	public void givenGRSConfig_syncHUsOnMaterialReceipt_whenGetExportExternalSystemRequest_thenReturnExternalSystemRequest()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.customParentConfigId(1)
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.build();

		final I_ExternalSystem_Config_GRSSignum externalSystemConfigGrsSignum = ExternalSystemTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("grsValue")
				.syncHUsOnMaterialReceipt(true)
				.build();

		final ExternalSystemGRSSignumConfigId childConfigId = ExternalSystemGRSSignumConfigId.ofRepoId(externalSystemConfigGrsSignum.getExternalSystem_Config_GRSSignum_ID());

		// when
		final TableRecordReference huRecrodRef = TableRecordReference.of(I_M_HU.Table_Name, HU_ID);
		final Optional<JsonExternalSystemRequest> externalSystemRequest = exportHUToGRSService.getExportExternalSystemRequest(childConfigId, huRecrodRef, null);

		// then
		assertThat(externalSystemRequest).isPresent();
		assertThat(externalSystemRequest.get()).isEqualTo(expectedJsonExternalSystemRequest);
	}

	@Test
	public void givenGRSConfig_syncHUsOneEnabled_whenGetExportExternalSystemRequest_thenReturnExternalSystemRequest()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.customParentConfigId(1)
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.build();

		final I_ExternalSystem_Config_GRSSignum externalSystemConfigGrsSignum = ExternalSystemTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("grsValue")
				.syncHUsOnMaterialReceipt(true)
				.build();

		final ExternalSystemGRSSignumConfigId childConfigId = ExternalSystemGRSSignumConfigId.ofRepoId(externalSystemConfigGrsSignum.getExternalSystem_Config_GRSSignum_ID());

		// when
		final TableRecordReference huRecordRef = TableRecordReference.of(I_M_HU.Table_Name, HU_ID);
		final Optional<JsonExternalSystemRequest> externalSystemRequest = exportHUToGRSService.getExportExternalSystemRequest(childConfigId, huRecordRef, null);

		// then
		assertThat(externalSystemRequest).isPresent();
		assertThat(externalSystemRequest.get()).isEqualTo(expectedJsonExternalSystemRequest);
	}

	@Test
	public void givenGRSConfig_syncHUsBothDisabled_whenGetExportExternalSystemRequest_thenReturnNoExternalSystemRequest()
	{
		// given
		final I_ExternalSystem_Config parentRecord = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.customParentConfigId(1)
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.build();

		final I_ExternalSystem_Config_GRSSignum externalSystemConfigGrsSignum = ExternalSystemTestUtil.createGrsConfigBuilder()
				.externalSystemConfigId(parentRecord.getExternalSystem_Config_ID())
				.value("grsValue")
				.build();

		final ExternalSystemGRSSignumConfigId childConfigId = ExternalSystemGRSSignumConfigId.ofRepoId(externalSystemConfigGrsSignum.getExternalSystem_Config_GRSSignum_ID());

		// when
		final TableRecordReference huRecordRef = TableRecordReference.of(I_M_HU.Table_Name, HU_ID);
		final Optional<JsonExternalSystemRequest> externalSystemRequest = exportHUToGRSService.getExportExternalSystemRequest(childConfigId, huRecordRef, null);

		// then
		assertThat(externalSystemRequest).isNotPresent();
	}

	private void createPrerequisites()
	{
		final I_AD_Org orgRecord = newInstance(I_AD_Org.class);
		orgRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		orgRecord.setValue("orgCode");
		saveRecord(orgRecord);

		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setM_HU_ID(HU_ID.getRepoId());
		hu.setAD_Org_ID(orgRecord.getAD_Org_ID());
		saveRecord(hu);
	}
}
