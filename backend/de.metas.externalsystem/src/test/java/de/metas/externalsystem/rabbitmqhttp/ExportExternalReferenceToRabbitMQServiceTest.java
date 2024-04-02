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

package de.metas.externalsystem.rabbitmqhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.externalreference.AlbertaExternalSystem;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
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
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.model.InterfaceWrapperHelper;
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

public class ExportExternalReferenceToRabbitMQServiceTest
{
	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest_ExternalReference.json";

	private JsonExternalSystemRequest expectedJsonExternalSystemRequest;
	private ExportExternalReferenceToRabbitMQService exportExternalReferenceToRabbitMQService;

	@BeforeEach
	public void init() throws IOException
	{
		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		final BPartnerCompositeRepository bPartnerCompositeRepository = new BPartnerCompositeRepository(partnerBL, new MockLogEntriesRepository(), new UserRoleRepository(), new BPartnerCreditLimitRepository());

		final ExternalReferenceRepository externalReferenceRepository = new ExternalReferenceRepository(Services.get(IQueryBL.class), new ExternalSystems(), new ExternalReferenceTypes());

		AdempiereTestHelper.get().init();

		final ExternalSystemConfigService externalSystemConfigServiceMock = Mockito.mock(ExternalSystemConfigService.class);
		Mockito.when(externalSystemConfigServiceMock.getTraceId()).thenReturn("traceId");

		exportExternalReferenceToRabbitMQService = new ExportExternalReferenceToRabbitMQService(
				new DataExportAuditRepository(),
				new DataExportAuditLogRepository(),
				new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository(), new TaxCategoryDAO()),
				new ExternalSystemMessageSender(new RabbitTemplate(), new Queue(QUEUE_NAME_MF_TO_ES)),
				externalReferenceRepository,
				new UserGroupRepository(),
				bPartnerCompositeRepository,
				externalSystemConfigServiceMock);

		createPrerequisites();

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final InputStream externalSystemIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		expectedJsonExternalSystemRequest = objectMapper.readValue(externalSystemIS, JsonExternalSystemRequest.class);
	}

	@Test
	public void given_whenGetExportExternalSystemRequest_thenReturnExternalSystemRequest()
	{
		// given
		final I_ExternalSystem_Config externalSystemParentConfig = ExternalSystemTestUtil.createI_ExternalSystem_ConfigBuilder()
				.type(ExternalSystemType.RabbitMQ.getCode())
				.customParentConfigId(1)
				.build();

		final I_ExternalSystem_Config_RabbitMQ_HTTP configRabbitMQHttp = ExternalSystemTestUtil.createRabbitMQConfigBuilder()
				.externalSystemConfigId(externalSystemParentConfig.getExternalSystem_Config_ID())
				.isSyncExternalReferencesToRabbitMQ(true)
				.customChildConfigId(2)
				.build();

		final I_S_ExternalReference externalReference = createExternalReferenceBuilder()
				.externalReference("ref_1")
				.externalSystem(AlbertaExternalSystem.ALBERTA.getCode())
				.type(BPLocationExternalReferenceType.BPARTNER_LOCATION.getCode())
				.recordId(3)
				.build();

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(4);
		final TableRecordReference externalRefRecordReference = TableRecordReference.of(I_S_ExternalReference.Table_Name, externalReference.getS_ExternalReference_ID());
		final ExternalSystemRabbitMQConfigId externalSystemRabbitMQConfigId = ExternalSystemRabbitMQConfigId.ofRepoId(configRabbitMQHttp.getExternalSystem_Config_RabbitMQ_HTTP_ID());

		// when
		final Optional<JsonExternalSystemRequest> externalSystemRequest = exportExternalReferenceToRabbitMQService.getExportExternalSystemRequest(externalSystemRabbitMQConfigId, externalRefRecordReference, pInstanceId);

		// then
		assertThat(externalSystemRequest).isPresent();
		assertThat(externalSystemRequest.get()).isEqualTo(expectedJsonExternalSystemRequest);
	}

	@NonNull
	@Builder(builderMethodName = "createExternalReferenceBuilder", builderClassName = "externalReferenceBuilder")
	private I_S_ExternalReference createExternalReference(
			final String externalReference,
			final String externalSystem,
			final String type,
			final int recordId)
	{
		final I_S_ExternalReference externalReferenceRecord = newInstance(I_S_ExternalReference.class);
		externalReferenceRecord.setExternalReference(externalReference);
		externalReferenceRecord.setExternalSystem(externalSystem);
		externalReferenceRecord.setType(type);
		externalReferenceRecord.setRecord_ID(recordId);
		externalReferenceRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());

		saveRecord(externalReferenceRecord);

		InterfaceWrapperHelper.setValue(externalReferenceRecord, I_S_ExternalReference.COLUMNNAME_CreatedBy, 22);

		saveRecord(externalReferenceRecord);

		return externalReferenceRecord;
	}

	private void createPrerequisites()
	{
		final I_AD_Org orgRecord = newInstance(I_AD_Org.class);
		orgRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		orgRecord.setValue("orgCode");
		saveRecord(orgRecord);
	}
}
