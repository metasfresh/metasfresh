/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.CreditorId;
import de.metas.bpartner.DebtorId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalsystem.ExternalSystemTestHelper;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.rest_api.v2.bpartner.BPartnerEndpointService;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.JsonRequestConsolidateService;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BP_Group;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_ORG_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.BP_GROUP_RECORD_NAME;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BP_GROUP_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.EXTERNAL_SYSTEM_NAME;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that {@code debtorId} and {@code creditorId} provided in {@link JsonRequestBPartner}
 * are propagated to the {@link de.metas.bpartner.composite.BPartner} domain object by
 * {@link de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister.JsonPersisterService#syncJsonToBPartner}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class JsonPersisterServiceDebtorCreditorTest
{
	private BpartnerRestController bpartnerRestController;
	private BPartnerCompositeRepository bpartnerCompositeRepository;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier_PerTableSequence();

		final MockLogEntriesRepository logEntriesRepository = new MockLogEntriesRepository();

		bpartnerCompositeRepository = BPartnerCompositeRepository.newInstanceForUnitTesting(logEntriesRepository);

		final JsonServiceFactory jsonServiceFactory = JsonServiceFactory.newInstanceForUnitTesting(
				logEntriesRepository,
				Mockito.mock(AlbertaBPartnerCompositeService.class));

		bpartnerRestController = new BpartnerRestController(
				new BPartnerEndpointService(jsonServiceFactory),
				jsonServiceFactory,
				new JsonRequestConsolidateService());

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		bpGroupRecord.setName(BP_GROUP_RECORD_NAME);
		bpGroupRecord.setValue(BP_GROUP_RECORD_NAME);
		saveRecord(bpGroupRecord);

		// Create the org record + OrgInfo that JsonPersisterService requires
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setAD_Org_ID(AD_ORG_ID);
		saveRecord(org);
		Services.get(IOrgDAO.class).createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
				.orgId(OrgId.ofRepoId(AD_ORG_ID))
				.build());

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);

		// Register the external system so "ext-ALBERTA-*" identifiers are accepted by JsonRequestConsolidateService
		ExternalSystemTestHelper.createExternalSystemIfNotExists(ExternalSystemType.ofValue(EXTERNAL_SYSTEM_NAME));
	}

	@Test
	void createBPartner_mapsDebtorId()
	{
		// given
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setName("TestDebtorBP");
		bpartner.setGroup(BP_GROUP_RECORD_NAME);
		bpartner.setDebtorId(12345);

		final JsonResponseBPartnerUpsertItem responseItem = upsertBPartner("ext-" + EXTERNAL_SYSTEM_NAME + "-debtor1", bpartner);

		// then — response DTO must carry the value back to the caller
		assertThat(responseItem.getDebtorId()).isEqualTo(12345);
		// and the value must be persisted to the DB
		assertThat(responseItem.getMetasfreshId()).as("metasfreshId must be set on CREATED response").isNotNull();
		final BPartnerComposite result = bpartnerCompositeRepository.getById(BPartnerId.ofRepoId(responseItem.getMetasfreshId().getValue()));
		assertThat(result.getBpartner().getDebtorId()).isEqualTo(DebtorId.ofNo(12345));
	}

	@Test
	void createBPartner_mapsCreditorId()
	{
		// given
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setName("TestCreditorBP");
		bpartner.setGroup(BP_GROUP_RECORD_NAME);
		bpartner.setCreditorId(67890);

		final JsonResponseBPartnerUpsertItem responseItem = upsertBPartner("ext-" + EXTERNAL_SYSTEM_NAME + "-creditor1", bpartner);

		// then — response DTO must carry the value back to the caller
		assertThat(responseItem.getCreditorId()).isEqualTo(67890);
		// and the value must be persisted to the DB
		assertThat(responseItem.getMetasfreshId()).as("metasfreshId must be set on CREATED response").isNotNull();
		final BPartnerComposite result = bpartnerCompositeRepository.getById(BPartnerId.ofRepoId(responseItem.getMetasfreshId().getValue()));
		assertThat(result.getBpartner().getCreditorId()).isEqualTo(CreditorId.ofNo(67890));
	}

	@Test
	void createBPartner_mapsDebtorIdAndCreditorId_together()
	{
		// given
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setName("TestBothBP");
		bpartner.setGroup(BP_GROUP_RECORD_NAME);
		bpartner.setDebtorId(12345);
		bpartner.setCreditorId(67890);

		final JsonResponseBPartnerUpsertItem responseItem = upsertBPartner("ext-" + EXTERNAL_SYSTEM_NAME + "-both1", bpartner);

		// then — response DTO must carry both values back to the caller
		assertThat(responseItem.getDebtorId()).isEqualTo(12345);
		assertThat(responseItem.getCreditorId()).isEqualTo(67890);
		// and both values must be persisted to the DB
		assertThat(responseItem.getMetasfreshId()).as("metasfreshId must be set on CREATED response").isNotNull();
		final BPartnerComposite result = bpartnerCompositeRepository.getById(BPartnerId.ofRepoId(responseItem.getMetasfreshId().getValue()));
		assertThat(result.getBpartner().getDebtorId()).isEqualTo(DebtorId.ofNo(12345));
		assertThat(result.getBpartner().getCreditorId()).isEqualTo(CreditorId.ofNo(67890));
	}

	private JsonResponseBPartnerUpsertItem upsertBPartner(final String bpartnerIdentifier, final JsonRequestBPartner bpartner)
	{
		final JsonRequestBPartnerUpsert request = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItem(JsonRequestBPartnerUpsertItem.builder()
						.bpartnerIdentifier(bpartnerIdentifier)
						.bpartnerComposite(JsonRequestComposite.builder()
								.bpartner(bpartner)
								.build())
						.build())
				.build();

		final ResponseEntity<JsonResponseBPartnerCompositeUpsert> response = bpartnerRestController.createOrUpdateBPartner(request);
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonResponseBPartnerCompositeUpsertItem responseCompositeItem = response.getBody().getResponseItems().get(0);
		return responseCompositeItem.getResponseBPartnerItem();
	}
}
