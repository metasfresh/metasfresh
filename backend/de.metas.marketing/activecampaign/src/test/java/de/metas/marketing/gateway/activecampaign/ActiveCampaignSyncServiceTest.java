/*
 * #%L
 * marketing-activecampaign
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.marketing.gateway.activecampaign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.PlatformSyncService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.model.SyncDirection;
import de.metas.marketing.base.sync.CampaignSyncService;
import de.metas.marketing.base.sync.ContactPersonSyncService;
import de.metas.marketing.gateway.activecampaign.restapi.RestService;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignLists;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignWrapper;
import de.metas.marketing.gateway.activecampaign.restapi.model.ContactList;
import de.metas.marketing.gateway.activecampaign.restapi.model.ContactWrapper;
import de.metas.user.UserRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT;
import static org.assertj.core.api.Assertions.*;

public class ActiveCampaignSyncServiceTest
{
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	private PlatformSyncService platformSyncService;
	private ContactPersonService contactPersonService;
	private CampaignService campaignService;
	private ActiveCampaignClient activeCampaignClientSpy;

	private PlatformId platformId;

	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();

		platformId = CampaignTestUtil.createPlatformRecord("platform-ActiveCmp");
		final ActiveCampaignConfig config = CampaignTestUtil.createLocalConfig(platformId);

		final RestService restServiceMock = Mockito.mock(RestService.class);

		//spy activeCampaignClient
		activeCampaignClientSpy = Mockito.spy(new ActiveCampaignClient(restServiceMock, config));

		//contactPersonService
		final PlatformRepository platformRepository = new PlatformRepository();
		final ContactPersonRepository contactPersonRepository = new ContactPersonRepository();
		final CampaignRepository campaignRepository = new CampaignRepository();
		contactPersonService = new ContactPersonService(contactPersonRepository, new UserRepository());

		//campaignService
		campaignService = new CampaignService(contactPersonRepository, campaignRepository, platformRepository);

		//platformSyncService
		final PlatformClientService platformClientServiceMock = Mockito.mock(PlatformClientService.class);
		Mockito.when(platformClientServiceMock.createPlatformClient(platformId)).thenReturn(activeCampaignClientSpy);
		final CampaignSyncService campaignSyncService = new CampaignSyncService(campaignService, platformClientServiceMock);
		final ContactPersonSyncService contactPersonSyncService = new ContactPersonSyncService(platformClientServiceMock, contactPersonService, campaignService);
		platformSyncService = new PlatformSyncService(campaignSyncService, contactPersonSyncService);
	}

	@Test
	public void givenUnsyncedLocalCampaigns_whenSyncCampaignLocalToRemote_thenCreateOnRemote() throws JsonProcessingException
	{
		// given local campaigns
		final String cmpName1 = "localCampaign1";
		final Campaign campaign1 = CampaignTestUtil.createMKTGCampaignRecordBuilder()
				.platformId(platformId)
				.namePrefix(cmpName1)
				.build();
		final String cmpName2 = "localCampaign2";
		final Campaign campaign2 = CampaignTestUtil.createMKTGCampaignRecordBuilder()
				.platformId(platformId)
				.namePrefix(cmpName2)
				.build();

		// spy client
		final CampaignWrapper mockedCreateCampaignResponse_campaign1 = readResource("/de/metas/marketing/gateway/activecampaign/campaign/LocalToRemote_CampaignWrapper1.json", CampaignWrapper.class);
		Mockito.doReturn(mockedCreateCampaignResponse_campaign1)
				.when(activeCampaignClientSpy).createCampaignOnRemote(campaign1);

		final CampaignWrapper mockedCreateCampaignResponse_campaign2 = readResource("/de/metas/marketing/gateway/activecampaign/campaign/LocalToRemote_CampaignWrapper2.json", CampaignWrapper.class);
		Mockito.doReturn(mockedCreateCampaignResponse_campaign2)
				.when(activeCampaignClientSpy).createCampaignOnRemote(campaign2);

		// when
		platformSyncService.syncCampaigns(platformId, SyncDirection.LOCAL_TO_REMOTE);

		// then
		final I_MKTG_Campaign campaignRecord1 = InterfaceWrapperHelper.load(campaign1.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord1.getLastSyncStatus()).isEqualTo(LocalToRemoteSyncResult.LocalToRemoteStatus.INSERTED_ON_REMOTE.name());
		assertThat(campaignRecord1.getRemoteRecordId()).isEqualTo("id1");
		assertThat(campaignRecord1.getName()).isEqualTo(campaign1.getName());

		final I_MKTG_Campaign campaignRecord2 = InterfaceWrapperHelper.load(campaign2.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord2.getLastSyncStatus()).isEqualTo(LocalToRemoteSyncResult.LocalToRemoteStatus.INSERTED_ON_REMOTE.name());
		assertThat(campaignRecord2.getRemoteRecordId()).isEqualTo("id2");
		assertThat(campaignRecord2.getName()).isEqualTo(campaign2.getName());
	}

	@Test
	public void givenExistingCampaigns_whenSyncCampaignRemoteToLocal_withNewUpdatedAndDeletedCampaigns_thenSuccess()
	{
		// given local campaigns
		final String cmpName1 = "localCampaign1";
		final String cmpRMId1 = "id1";
		final Campaign campaign1 = CampaignTestUtil.createMKTGCampaignRecordBuilder()
				.platformId(platformId)
				.namePrefix(cmpName1)
				.remoteId(cmpRMId1)
				.build();
		final String cmpName2 = "localCampaign2";
		final String cmpRMId2 = "id2";
		final Campaign campaign2 = CampaignTestUtil.createMKTGCampaignRecordBuilder()
				.platformId(platformId)
				.namePrefix(cmpName2)
				.remoteId(cmpRMId2)
				.build();

		// spy client
		final CampaignLists mockedRemoteCampaignsResponse = readResource("/de/metas/marketing/gateway/activecampaign/campaign/RemoteToLocal_CampaignLists.json", CampaignLists.class);
		final ActiveCampaignPageDescriptor pageDescriptor = ActiveCampaignPageDescriptor.ofLimit(ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT);
		Mockito.doReturn(mockedRemoteCampaignsResponse.getLists())
				.when(activeCampaignClientSpy).retrieveCampaignListsFromRemote(pageDescriptor);

		// when
		platformSyncService.syncCampaigns(platformId, SyncDirection.REMOTE_TO_LOCAL);

		// then
		final I_MKTG_Campaign campaignRecord1 = InterfaceWrapperHelper.load(campaign1.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord1.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM.name());
		assertThat(campaignRecord1.getRemoteRecordId()).isNull();
		assertThat(campaignRecord1.getName()).isEqualTo(campaign1.getName());

		final I_MKTG_Campaign campaignRecord2 = InterfaceWrapperHelper.load(campaign2.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord2.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_OTHER_REMOTE_DATA.name());
		assertThat(campaignRecord2.getRemoteRecordId()).isEqualTo("id2");
		assertThat(campaignRecord2.getName()).isEqualTo("localCampaign2-updated");

		final List<Campaign> remoteCampaigns = campaignService.retrieveByPlatformAndRemoteIds(platformId, ImmutableSet.of("id3"));
		assertThat(remoteCampaigns.size()).isEqualTo(1);

		final Campaign createdCampaign3 = remoteCampaigns.get(0);
		final I_MKTG_Campaign campaignRecord3 = InterfaceWrapperHelper.load(createdCampaign3.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord3.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_NEW_CAMPAIGN.name());
		assertThat(campaignRecord3.getRemoteRecordId()).isEqualTo("id3");
		assertThat(campaignRecord3.getName()).isEqualTo("remoteCampaign3");
	}

	@Test
	public void givenUnsyncedLocalContacts_whenSyncContactsLocalToRemote_thenCreateOnRemote() throws JsonProcessingException
	{
		// given local contacts from marketing campaign
		final String campaignRemoteId = "111111";
		final Campaign campaign = CampaignTestUtil.createMKTGCampaignRecordBuilder()
				.platformId(platformId)
				.namePrefix("ActiveCampaign.syncContactPersonsLocalToRemote")
				.remoteId(campaignRemoteId)
				.build();

		final String testEmail1 = "test1@email.com";
		final ContactPerson contactPerson1 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(testEmail1)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson1.getContactPersonId(), campaign.getCampaignId());

		final String invalidTestEmail2 = "test2-invalidmail";
		final ContactPerson contactPerson2 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(invalidTestEmail2)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson2.getContactPersonId(), campaign.getCampaignId());

		final String testEmail3 = "test3@email.com";
		final ContactPerson contactPerson3 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(testEmail3)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson3.getContactPersonId(), campaign.getCampaignId());

		final String invalidTestEmail4 = "test4-invalidmail";
		final ContactPerson contactPerson4 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(invalidTestEmail4)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson4.getContactPersonId(), campaign.getCampaignId());

		// spy client
		final ContactWrapper mockedCreatedContact_contactPerson1 = readResource("/de/metas/marketing/gateway/activecampaign/contact/LocalToRemote_ContactWrapper1.json", ContactWrapper.class);
		Mockito.doReturn(mockedCreatedContact_contactPerson1)
				.when(activeCampaignClientSpy).upsertContactOnRemote(contactPerson1);

		final ContactWrapper mockedCreatedContact_contactPerson3 = readResource("/de/metas/marketing/gateway/activecampaign/contact/LocalToRemote_ContactWrapper2.json", ContactWrapper.class);
		Mockito.doReturn(mockedCreatedContact_contactPerson3)
				.when(activeCampaignClientSpy).upsertContactOnRemote(contactPerson3);

		Mockito.doReturn(null).when(activeCampaignClientSpy).addContactToList(Mockito.any(), Mockito.any());

		// when
		platformSyncService.syncContacts(campaign.getCampaignId(), SyncDirection.LOCAL_TO_REMOTE);

		// then
		final I_MKTG_ContactPerson contactPersonRecord1 = InterfaceWrapperHelper.load(contactPerson1.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord1.getLastSyncStatus()).isEqualTo(LocalToRemoteSyncResult.LocalToRemoteStatus.UPSERTED_ON_REMOTE.name());
		assertThat(contactPersonRecord1.getRemoteRecordId()).isEqualTo("45");
		assertThat(contactPersonRecord1.getEMail()).isEqualTo(testEmail1);

		final I_MKTG_ContactPerson contactPersonRecord2 = InterfaceWrapperHelper.load(contactPerson2.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord2.getLastSyncStatus()).isEqualTo(LocalToRemoteSyncResult.LocalToRemoteStatus.ERROR.name());
		assertThat(contactPersonRecord2.getRemoteRecordId()).isNull();
		assertThat(contactPersonRecord2.getEMail()).isEqualTo(invalidTestEmail2);
		assertThat(contactPersonRecord2.getLastSyncDetailMessage()).contains("Contact person has no (valid) email address");

		final I_MKTG_ContactPerson contactPersonRecord3 = InterfaceWrapperHelper.load(contactPerson3.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord3.getLastSyncStatus()).isEqualTo(LocalToRemoteSyncResult.LocalToRemoteStatus.UPSERTED_ON_REMOTE.name());
		assertThat(contactPersonRecord3.getRemoteRecordId()).isEqualTo("48");
		assertThat(contactPersonRecord3.getEMail()).isEqualTo(testEmail3);

		final I_MKTG_ContactPerson contactPersonRecord4 = InterfaceWrapperHelper.load(contactPerson4.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord4.getLastSyncStatus()).isEqualTo(LocalToRemoteSyncResult.LocalToRemoteStatus.ERROR.name());
		assertThat(contactPersonRecord4.getRemoteRecordId()).isNull();
		assertThat(contactPersonRecord4.getEMail()).isEqualTo(invalidTestEmail4);
		assertThat(contactPersonRecord4.getLastSyncDetailMessage()).contains("Contact person has no (valid) email address");
	}

	@Test
	public void givenExistingContactPersons_whenSyncContactsRemoteToLocal_withNewUpdatedAndDeletedContacts_thenSuccess()
	{
		// given
		final String campaignRemoteId = "111112";
		final Campaign campaign = CampaignTestUtil.createMKTGCampaignRecordBuilder()
				.namePrefix("ActiveCampaign.syncContactPersonsRemoteToLocal")
				.platformId(platformId)
				.remoteId(campaignRemoteId)
				.build();

		final String contactEmail1 = "test1@email.com";
		final String contactRM1 = "47";
		final ContactPerson contactPerson1 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(contactEmail1)
				.remoteId(contactRM1)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson1.getContactPersonId(), campaign.getCampaignId());

		final String contactEmail2 = "test2@email.com";
		final String contactRM2 = "48";
		final ContactPerson contactPerson2 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(contactEmail2)
				.remoteId(contactRM2)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson2.getContactPersonId(), campaign.getCampaignId());

		// spy client
		final ContactList mockedRemoteContactsResponse = readResource("/de/metas/marketing/gateway/activecampaign/contact/RemoteToLocal_ContactList.json", ContactList.class);
		final ActiveCampaignPageDescriptor pageDescriptor = ActiveCampaignPageDescriptor.ofLimit(ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT);
		Mockito.doReturn(mockedRemoteContactsResponse)
				.when(activeCampaignClientSpy).retrieveContactsFromRemote(campaignRemoteId, pageDescriptor);

		// when
		platformSyncService.syncContacts(campaign.getCampaignId(), SyncDirection.REMOTE_TO_LOCAL);

		// then
		final I_MKTG_ContactPerson contactPersonRecord1 = InterfaceWrapperHelper.load(contactPerson1.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord1.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM.name());
		assertThat(contactPersonRecord1.getRemoteRecordId()).isNull();
		assertThat(contactPersonRecord1.getEMail()).isEqualTo(contactEmail1);

		final I_MKTG_ContactPerson contactPersonRecord2 = InterfaceWrapperHelper.load(contactPerson2.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord2.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_EMAIL_BOUNCE_INFO.name());
		assertThat(contactPersonRecord2.getRemoteRecordId()).isEqualTo(contactRM2);
		assertThat(contactPersonRecord2.getEMail()).isEqualTo("test2@update.com");

		final List<ContactPerson> createContacts = contactPersonService.retrieveByCampaignAndRemoteIds(campaign.getCampaignId(), ImmutableSet.of("50"));
		assertThat(createContacts.size()).isEqualTo(1);

		final ContactPerson createdContact4 = createContacts.get(0);
		final I_MKTG_ContactPerson contactPersonRecord4 = InterfaceWrapperHelper.load(createdContact4.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord4.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_NEW_CONTACT_PERSON.name());
		assertThat(contactPersonRecord4.getRemoteRecordId()).isEqualTo("50");
		assertThat(contactPersonRecord4.getEMail()).isEqualTo("test4@email.com");
	}

	private <T> T readResource(@NonNull final String path, @NonNull final Class<T> modelClass)
	{
		final InputStream inputStream = this.getClass().getResourceAsStream(path);

		try
		{
			return objectMapper.readValue(inputStream, modelClass);
		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
