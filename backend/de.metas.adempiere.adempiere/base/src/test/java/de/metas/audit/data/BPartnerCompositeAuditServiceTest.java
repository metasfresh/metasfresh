/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.model.DataExportAudit;
import de.metas.audit.data.model.DataExportAuditLog;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.audit.data.service.BPartnerCompositeAuditService;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class BPartnerCompositeAuditServiceTest
{
	private static final String JSON_RESPONSE_COMPOSITE = "20_JsonResponseComposite.json";
	private static final String BILL_PARTNER_VALUE = "billPartner-123";
	private static final int BPARTNER_ID = 2156425;
	private static final int LOCATION_ID = 1;
	private static final int C_BP_LOCATION_ID = 2205175;
	private static final int AD_USER_ID = 2156426;
	private static final String BPARTNER_ID_REQUEST_URI = "/api/v2/bpartner/" + BPARTNER_ID;

	private BPartnerCompositeAuditService bPartnerCompositeAuditService;
	private DataExportAuditRepository dataExportAuditRepository;
	private DataExportAuditLogRepository dataExportAuditLogRepository;
	private JsonResponseComposite jsonResponseComposite;

	@BeforeEach
	public void init() throws IOException
	{
		AdempiereTestHelper.get().init();

		dataExportAuditRepository = new DataExportAuditRepository();
		dataExportAuditLogRepository = new DataExportAuditLogRepository();

		final DataExportAuditService dataExportAuditService = new DataExportAuditService(dataExportAuditRepository, dataExportAuditLogRepository);

		bPartnerCompositeAuditService = new BPartnerCompositeAuditService(dataExportAuditService);

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final InputStream responseCompositeIS = this.getClass().getResourceAsStream(JSON_RESPONSE_COMPOSITE);
		jsonResponseComposite = objectMapper.readValue(responseCompositeIS, JsonResponseComposite.class);

		createPrerequisites();
	}

	@Test
	public void givenBPartnerRelatedRequest_whenPerformDataAuditForRequest_thenAuditBPartner()
	{
		// given
		final ExternalSystemParentConfigId externalSystemParentConfigId = ExternalSystemParentConfigId.ofRepoId(1);
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1);

		final GenericDataExportAuditRequest genericDataExportAuditRequest = GenericDataExportAuditRequest
				.builder()
				.exportedObject(jsonResponseComposite)
				.requestURI(BPARTNER_ID_REQUEST_URI)
				.pInstanceId(pInstanceId)
				.externalSystemParentConfigId(externalSystemParentConfigId)
				.build();

		// when
		bPartnerCompositeAuditService.performDataAuditForRequest(genericDataExportAuditRequest);

		// then
		assertResult(externalSystemParentConfigId, pInstanceId);
	}

	private void assertResult(
			@NonNull final ExternalSystemParentConfigId externalSystemParentConfigId,
			@NonNull final PInstanceId pInstanceId)
	{
		//validate DataExportAudit records
		final Optional<DataExportAudit> bPartnerDataExportAudit = dataExportAuditRepository.getByTableRecordReference(TableRecordReference.of(I_C_BPartner.Table_Name, BPARTNER_ID));
		assertThat(bPartnerDataExportAudit).isPresent();
		assertThat(bPartnerDataExportAudit.get().getParentId()).isNull();

		final Optional<DataExportAudit> bPartnerLocationDataExportAudit = dataExportAuditRepository.getByTableRecordReference(TableRecordReference.of(I_C_BPartner_Location.Table_Name, C_BP_LOCATION_ID));
		assertThat(bPartnerLocationDataExportAudit).isPresent();
		assertThat(bPartnerLocationDataExportAudit.get().getParentId()).isEqualTo(bPartnerDataExportAudit.get().getId());

		final Optional<DataExportAudit> locationDataExportAudit = dataExportAuditRepository.getByTableRecordReference(TableRecordReference.of(I_C_Location.Table_Name, LOCATION_ID));
		assertThat(locationDataExportAudit).isPresent();
		assertThat(locationDataExportAudit.get().getParentId()).isEqualTo(bPartnerLocationDataExportAudit.get().getId());

		final Optional<DataExportAudit> userDataExportAudit = dataExportAuditRepository.getByTableRecordReference(TableRecordReference.of(I_AD_User.Table_Name, AD_USER_ID));
		assertThat(userDataExportAudit).isPresent();
		assertThat(userDataExportAudit.get().getParentId()).isEqualTo(bPartnerDataExportAudit.get().getId());

		//validate DataExportAuditLog records
		final ImmutableList<DataExportAuditLog> bPartnerLogs = dataExportAuditLogRepository.getByDataExportAuditId(bPartnerDataExportAudit.get().getId());
		assertThat(bPartnerLogs).hasSize(1);
		assertThat(bPartnerLogs.get(0).getAction()).isEqualTo(Action.Standalone);
		assertThat(bPartnerLogs.get(0).getExternalSystemConfigId()).isEqualTo(externalSystemParentConfigId);
		assertThat(bPartnerLogs.get(0).getAdPInstanceId()).isEqualTo(pInstanceId);

		final ImmutableList<DataExportAuditLog> bPartnerLocationLogs = dataExportAuditLogRepository.getByDataExportAuditId(bPartnerLocationDataExportAudit.get().getId());
		assertThat(bPartnerLocationLogs).hasSize(1);
		assertThat(bPartnerLocationLogs.get(0).getAction()).isEqualTo(Action.AlongWithParent);
		assertThat(bPartnerLocationLogs.get(0).getExternalSystemConfigId()).isEqualTo(externalSystemParentConfigId);
		assertThat(bPartnerLocationLogs.get(0).getAdPInstanceId()).isEqualTo(pInstanceId);

		final ImmutableList<DataExportAuditLog> locationLogs = dataExportAuditLogRepository.getByDataExportAuditId(locationDataExportAudit.get().getId());
		assertThat(locationLogs).hasSize(1);
		assertThat(locationLogs.get(0).getAction()).isEqualTo(Action.AlongWithParent);
		assertThat(locationLogs.get(0).getExternalSystemConfigId()).isEqualTo(externalSystemParentConfigId);
		assertThat(locationLogs.get(0).getAdPInstanceId()).isEqualTo(pInstanceId);

		final ImmutableList<DataExportAuditLog> userLogs = dataExportAuditLogRepository.getByDataExportAuditId(userDataExportAudit.get().getId());
		assertThat(userLogs).hasSize(1);
		assertThat(userLogs.get(0).getAction()).isEqualTo(Action.AlongWithParent);
		assertThat(userLogs.get(0).getExternalSystemConfigId()).isEqualTo(externalSystemParentConfigId);
		assertThat(userLogs.get(0).getAdPInstanceId()).isEqualTo(pInstanceId);
	}

	private void createPrerequisites()
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setValue(BILL_PARTNER_VALUE);
		bpartnerRecord.setC_BP_Group_ID(1);
		bpartnerRecord.setC_BPartner_ID(BPARTNER_ID);
		saveRecord(bpartnerRecord);

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Location_ID(LOCATION_ID);
		saveRecord(locationRecord);

		final I_C_BPartner_Location bpartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		bpartnerLocationRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		bpartnerLocationRecord.setIsBillTo(true);
		bpartnerLocationRecord.setC_BPartner_Location_ID(C_BP_LOCATION_ID);
		saveRecord(bpartnerLocationRecord);
	}
}
