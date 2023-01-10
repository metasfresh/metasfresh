package de.metas.marketing.gateway.cleverreach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.CampaignSyncService;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.PlatformClientFactoryRegistry;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.LocalToRemoteSyncResult.LocalToRemoteStatus;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.model.SyncDirection;
import de.metas.marketing.base.spi.PlatformClientFactory;
import de.metas.marketing.base.sync.CampaignServiceSync;
import de.metas.marketing.base.sync.ContactPersonServiceSync;
import de.metas.marketing.gateway.cleverreach.restapi.models.CreateGroupRequest;
import de.metas.marketing.gateway.cleverreach.restapi.models.Group;
import de.metas.marketing.gateway.cleverreach.restapi.models.Receiver;
import de.metas.marketing.gateway.cleverreach.restapi.models.ReceiverUpsert;
import de.metas.user.UserRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.marketing
 * %%
 * Copyright (C) 2018 metas GmbH
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

@SuppressWarnings({ "OptionalGetWithoutIsPresent", "SameParameterValue" })
public class ManualTest
{
	public static final String CONFIG_FILENAME = "./cleverreach-developer-config.json";
	private static final String MANUAL_GROUP_REMOTE_ID = "524972";

	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	final MockedStatic<CleverReachLowLevelClient> cleverReachLowLevelClientMockedStatic = Mockito.mockStatic(CleverReachLowLevelClient.class);
	final MockedStatic<SystemTime> systemTimeMockedStatic = Mockito.mockStatic(SystemTime.class);

	private CampaignSyncService campaignSyncService;
	private CleverReachLowLevelClient cleverReachLowLevelClient;
	private ContactPersonService contactPersonService;
	private CampaignService campaignService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		systemTimeMockedStatic.when(SystemTime::asInstant).thenReturn(Instant.ofEpochMilli(0));
		cleverReachLowLevelClient = Mockito.mock(CleverReachLowLevelClient.class);

		final PlatformRepository platformRepository = new PlatformRepository();
		final ContactPersonRepository contactPersonRepository = new ContactPersonRepository();
		final CampaignRepository campaignRepository = new CampaignRepository();

		final CleverReachClientFactory factory = new CleverReachClientFactory(new CleverReachConfigRepository());
		final PlatformClientFactoryRegistry registry = new PlatformClientFactoryRegistry(Optional.of(new ArrayList<>(Collections.singleton((PlatformClientFactory)factory))));
		final PlatformClientService platformClientService = new PlatformClientService(platformRepository, registry);

		contactPersonService = new ContactPersonService(contactPersonRepository, new UserRepository());
		campaignService = new CampaignService(contactPersonRepository, campaignRepository, platformRepository);

		final CampaignServiceSync campaignServiceSync = new CampaignServiceSync(campaignService, platformClientService);
		final ContactPersonServiceSync contactPersonServiceSync = new ContactPersonServiceSync(platformClientService, contactPersonService, campaignService);

		campaignSyncService = new CampaignSyncService(campaignServiceSync, contactPersonServiceSync);
	}

	@AfterEach
	public void afterEach()
	{
		cleverReachLowLevelClientMockedStatic.close();
		systemTimeMockedStatic.close();
	}

	@Test
	public void createUpdateCampaign()
	{
		final PlatformId platformId = CampaignTestUtil.createPlatformRecord("test-1");
		final CleverReachConfig config = CampaignTestUtil.createLocalConfig(platformId);

		// mock CleverReachLowLevelClient
		cleverReachLowLevelClientMockedStatic.when(() -> CleverReachLowLevelClient.createAndLogin(config)).thenReturn(cleverReachLowLevelClient);

		//
		// Add one campaign
		final String nameOfCampaignToAdd = "test-created";

		//spy
		final Group upsertGroup = readResource("/de/metas/marketing/gateway/cleverreach/campaign/UpsertGroup.json", Group.class);
		Mockito.when(cleverReachLowLevelClient.post(Mockito.eq(CreateGroupRequest.ofName(nameOfCampaignToAdd)), Mockito.any(), Mockito.eq("/groups.json/"))).thenReturn(upsertGroup);

		final Campaign addedCampaign;
		I_MKTG_Campaign campaignRecord;
		{
			final Campaign campaignToAdd = CampaignTestUtil.createCampaignRecord(nameOfCampaignToAdd, platformId);

			campaignSyncService.syncCampaigns(platformId, SyncDirection.LOCAL_TO_REMOTE);

			// Check:
			campaignRecord = InterfaceWrapperHelper.load(campaignToAdd.getCampaignId(), I_MKTG_Campaign.class);
			assertThat(campaignRecord).isNotNull();
			assertThat(campaignRecord.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.INSERTED_ON_REMOTE.name());

			addedCampaign = CampaignRepository.fromRecord(campaignRecord);
			assertThat(addedCampaign.getRemoteId()).isNotEmpty();
			assertThat(addedCampaign.getName()).isEqualTo(nameOfCampaignToAdd);
		}

		//
		// Now change its name
		final String nameOfCampaignToUpdate = "test-updated";

		//spy
		final Group upsertGroupUpdate = readResource("/de/metas/marketing/gateway/cleverreach/campaign/UpsertGroupUpdated.json", Group.class);
		Mockito.when(cleverReachLowLevelClient.post(Mockito.eq(CreateGroupRequest.ofName(nameOfCampaignToUpdate)), Mockito.any(), Mockito.eq("/groups.json/")))
				.thenReturn(upsertGroupUpdate);

		final Campaign updatedCampaign;
		{
			campaignRecord.setName(nameOfCampaignToUpdate);

			InterfaceWrapperHelper.save(campaignRecord);

			campaignSyncService.syncCampaigns(platformId, SyncDirection.LOCAL_TO_REMOTE);

			campaignRecord = InterfaceWrapperHelper.load(addedCampaign.getCampaignId(), I_MKTG_Campaign.class);

			assertThat(campaignRecord).isNotNull();
			assertThat(campaignRecord.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.UPDATED_ON_REMOTE.name());

			updatedCampaign = CampaignRepository.fromRecord(campaignRecord);
			assertThat(updatedCampaign.getRemoteId()).isEqualTo(addedCampaign.getRemoteId());
			assertThat(updatedCampaign.getName()).isEqualTo(nameOfCampaignToUpdate);
		}
	}

	@Test
	public void syncContactPersonsLocalToRemote()
	{
		final PlatformId platformId = CampaignTestUtil.createPlatformRecord("test-2");
		final CleverReachConfig config = CampaignTestUtil.createLocalConfig(platformId);

		final String campaignRemoteId = "111113";
		final Campaign campaign = CampaignTestUtil.createCampaignRecord("ManualTest.syncContactPersonsLocalToRemote", platformId, campaignRemoteId);

		final String testEmail1 = "test10@newemail.com";
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

		final String testEmail3 = "test30@newemail.com";
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

		// mock CleverReachLowLevelClient
		cleverReachLowLevelClientMockedStatic.when(() -> CleverReachLowLevelClient.createAndLogin(config)).thenReturn(cleverReachLowLevelClient);

		final Receiver receiver1 = readResource("/de/metas/marketing/gateway/cleverreach/contact/localtoremote/RemoteContact1.json", Receiver.class);
		Mockito.when(cleverReachLowLevelClient.post(Mockito.eq(ReceiverUpsert.of(contactPerson1)),
													Mockito.any(),
													Mockito.eq("/groups.json/" + campaignRemoteId + "/receivers/upsert")))
				.thenReturn(receiver1);

		final Receiver receiver2 = readResource("/de/metas/marketing/gateway/cleverreach/contact/localtoremote/RemoteContact2.json", Receiver.class);
		Mockito.when(cleverReachLowLevelClient.post(Mockito.eq(ReceiverUpsert.of(contactPerson3)),
													Mockito.any(),
													Mockito.eq("/groups.json/111113/receivers/upsert")))
				.thenReturn(receiver2);

		campaignSyncService.syncContacts(campaign.getCampaignId(), SyncDirection.LOCAL_TO_REMOTE);

		final I_MKTG_ContactPerson contactPersonRecord1 = InterfaceWrapperHelper.load(contactPerson1.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord1.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.UPSERTED_ON_REMOTE.name());
		assertThat(contactPersonRecord1.getRemoteRecordId()).isEqualTo("1");
		assertThat(contactPersonRecord1.getEMail()).isEqualTo(testEmail1);

		final I_MKTG_ContactPerson contactPersonRecord2 = InterfaceWrapperHelper.load(contactPerson2.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord2.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.ERROR.name());
		assertThat(contactPersonRecord2.getRemoteRecordId()).isNull();
		assertThat(contactPersonRecord2.getEMail()).isEqualTo(invalidTestEmail2);
		assertThat(contactPersonRecord2.getLastSyncDetailMessage()).contains("Contact person has no (valid) email address");

		final I_MKTG_ContactPerson contactPersonRecord3 = InterfaceWrapperHelper.load(contactPerson3.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord3.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.UPSERTED_ON_REMOTE.name());
		assertThat(contactPersonRecord3.getRemoteRecordId()).isEqualTo("2");
		assertThat(contactPersonRecord3.getEMail()).isEqualTo(testEmail3);

		final I_MKTG_ContactPerson contactPersonRecord4 = InterfaceWrapperHelper.load(contactPerson4.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord4.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.ERROR.name());
		assertThat(contactPersonRecord4.getRemoteRecordId()).isNull();
		assertThat(contactPersonRecord4.getEMail()).isEqualTo(invalidTestEmail4);
		assertThat(contactPersonRecord4.getLastSyncDetailMessage()).contains("Contact person has no (valid) email address");
	}

	@Test
	public void syncContactPersonsRemoteToLocal()
	{
		// given
		final PlatformId platformId = CampaignTestUtil.createPlatformRecord("test-2");
		final CleverReachConfig config = CampaignTestUtil.createLocalConfig(platformId);

		final String campaignRemoteId = "111114";
		final Campaign campaign = CampaignTestUtil.createCampaignRecord("ManualTest.syncContactPersonsRemoteToLocal", platformId, campaignRemoteId);

		final String contactEmail1 = "test10@newemail.com";
		final String contactRM1 = "1";
		final ContactPerson contactPerson1 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(contactEmail1)
				.remoteId(contactRM1)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson1.getContactPersonId(), campaign.getCampaignId());

		final String contactEmail2 = "test20@newemail.com";
		final String contactRM2 = "2";
		final ContactPerson contactPerson2 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(contactEmail2)
				.remoteId(contactRM2)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson2.getContactPersonId(), campaign.getCampaignId());

		final String contactEmail3 = "test30@newemail.com";
		final ContactPerson contactPerson3 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(contactEmail3)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson3.getContactPersonId(), campaign.getCampaignId());

		final String contactEmail4 = "test40@newemail.com";
		final String contactRM4 = "4";
		final ContactPerson contactPerson4 = CampaignTestUtil.createMKTGContactRecordBuilder()
				.platformId(platformId)
				.email(contactEmail4)
				.remoteId(contactRM4)
				.build();
		CampaignTestUtil.assignMKTGContactToCampaign(contactPerson4.getContactPersonId(), campaign.getCampaignId());

		// mock client
		cleverReachLowLevelClientMockedStatic.when(() -> CleverReachLowLevelClient.createAndLogin(config)).thenReturn(cleverReachLowLevelClient);

		final Receiver[] receivers = readResource("/de/metas/marketing/gateway/cleverreach/contact/remotetolocal/RemoteContacts.json", Receiver[].class);

		final List<Receiver> receiverList = ImmutableList.copyOf(receivers);

		Mockito.when(cleverReachLowLevelClient.get(Mockito.any(),
												   Mockito.contains("/groups.json/" + campaignRemoteId + "/receivers?pagesize=")))
				.thenReturn(receiverList);

		// when
		campaignSyncService.syncContacts(campaign.getCampaignId(), SyncDirection.REMOTE_TO_LOCAL);

		// then
		final I_MKTG_ContactPerson contactPersonRecord1 = InterfaceWrapperHelper.load(contactPerson1.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord1.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM.name());
		assertThat(contactPersonRecord1.getRemoteRecordId()).isNull();
		assertThat(contactPersonRecord1.getEMail()).isEqualTo(contactEmail1);

		final I_MKTG_ContactPerson contactPersonRecord2 = InterfaceWrapperHelper.load(contactPerson2.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord2.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_EMAIL_BOUNCE_INFO.name());
		assertThat(contactPersonRecord2.getRemoteRecordId()).isEqualTo(contactRM2);
		assertThat(contactPersonRecord2.getEMail()).isEqualTo(contactEmail2);

		final I_MKTG_ContactPerson contactPersonRecord3 = InterfaceWrapperHelper.load(contactPerson3.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord3.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_REMOTE_ID.name());
		assertThat(contactPersonRecord3.getRemoteRecordId()).isEqualTo("3");
		assertThat(contactPersonRecord3.getEMail()).isEqualTo(contactEmail3);

		final I_MKTG_ContactPerson contactPersonRecord4 = InterfaceWrapperHelper.load(contactPerson4.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord4.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_EMAIL_BOUNCE_INFO.name());
		assertThat(contactPersonRecord4.getRemoteRecordId()).isEqualTo("4");
		assertThat(contactPersonRecord4.getEMail()).isEqualTo(contactEmail4);
		assertThat(contactPersonRecord4.getDeactivatedOnRemotePlatform()).isEqualTo(DeactivatedOnRemotePlatform.YES.getCode());

		final List<ContactPerson> createContacts = contactPersonService.retrieveByCampaignAndRemoteIds(campaign.getCampaignId(), ImmutableSet.of("5"));
		assertThat(createContacts.size()).isEqualTo(1);

		final ContactPerson createdContact5 = createContacts.get(0);
		final I_MKTG_ContactPerson contactPersonRecord5 = InterfaceWrapperHelper.load(createdContact5.getContactPersonId().getRepoId(), I_MKTG_ContactPerson.class);
		assertThat(contactPersonRecord5.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_NEW_CONTACT_PERSON.name());
		assertThat(contactPersonRecord5.getRemoteRecordId()).isEqualTo("5");
		assertThat(contactPersonRecord5.getEMail()).isEqualTo("test50@newemail.com");
	}

	// @Test
	// @Disabled
	// void publishPersonsWithDifferentActivationStatus()
	// {
	// 	final String domain = "a" + System.currentTimeMillis() + ".com";
	// 	System.out.println("Using domain: " + domain);
	//
	// 	final PlatformId platformId = CampaignTestUtil.createPlatformRecord("diffActivationStatus");
	// 	final Campaign campaign = CampaignTestUtil.createCampaignRecord("diffActivationStatus", platformId, MANUAL_GROUP_REMOTE_ID);
	//
	// 	final String contactEmailInactive = "inactive@" + domain;
	// 	final ContactPerson contactPerson1 = CampaignTestUtil.createMKTGContactRecordBuilder()
	// 			.platformId(platformId)
	// 			.email(contactEmailInactive)
	// 			.deactivatedOnRemotePlatform(DeactivatedOnRemotePlatform.NO)
	// 			.build();
	// 	CampaignTestUtil.assignMKTGContactToCampaign(contactPerson1.getContactPersonId(), campaign.getCampaignId());
	//
	// 	final String contactEmailActive = "active@" + domain;
	// 	final ContactPerson contactPerson2 = CampaignTestUtil.createMKTGContactRecordBuilder()
	// 			.platformId(platformId)
	// 			.email(contactEmailActive)
	// 			.deactivatedOnRemotePlatform(DeactivatedOnRemotePlatform.YES)
	// 			.build();
	// 	CampaignTestUtil.assignMKTGContactToCampaign(contactPerson2.getContactPersonId(), campaign.getCampaignId());
	//
	// 	final String contactEmailUnknown = "unknown_status@" + domain;
	// 	final ContactPerson contactPerson3 = CampaignTestUtil.createMKTGContactRecordBuilder()
	// 			.platformId(platformId)
	// 			.email(contactEmailUnknown)
	// 			.deactivatedOnRemotePlatform(DeactivatedOnRemotePlatform.UNKNOWN)
	// 			.build();
	// 	CampaignTestUtil.assignMKTGContactToCampaign(contactPerson3.getContactPersonId(), campaign.getCampaignId());
	//
	// 	// Send them to CleverReach (first time)
	// 	{
	// 		campaignSyncService.syncContacts(campaign.getCampaignId(), SyncDirection.LOCAL_TO_REMOTE);
	// 		System.out.println("Got results (1):");
	//
	// 		contactPersonService.getByCampaignId(campaign.getCampaignId())
	// 				.forEach(System.out::println);
	// 	}
	//
	// 	// Send them to CleverReach (second time)
	// 	{
	// 		System.out.println("\n----------------------------------------------------------------------------------");
	// 		System.out.println("Sending them again...");
	//
	// 		campaignSyncService.syncContacts(campaign.getCampaignId(), SyncDirection.LOCAL_TO_REMOTE);
	// 		System.out.println("Got results (1):");
	//
	// 		contactPersonService.getByCampaignId(campaign.getCampaignId())
	// 				.forEach(System.out::println);
	// 	}
	// }
	//
	// @Test
	// @Disabled
	// void publishInactiveEmailAndThenSendActivationForm()
	// {
	// 	final String domain = "a" + System.currentTimeMillis() + ".com";
	// 	final String email = "inactive@" + domain;
	// 	System.out.println("Using email: " + email);
	//
	// 	final PlatformId platformId = CampaignTestUtil.createPlatformRecord("activationForm");
	// 	final Campaign campaign = CampaignTestUtil.createCampaignRecord("activationForm", platformId, MANUAL_GROUP_REMOTE_ID);
	//
	// 	final ContactPerson contactPerson = CampaignTestUtil.createMKTGContactRecordBuilder()
	// 			.platformId(platformId)
	// 			.email(email)
	// 			.deactivatedOnRemotePlatform(DeactivatedOnRemotePlatform.YES)
	// 			.build();
	// 	CampaignTestUtil.assignMKTGContactToCampaign(contactPerson.getContactPersonId(), campaign.getCampaignId());
	//
	// 	final ContactPerson.ContactPersonBuilder personTemplate = ContactPerson.builder().platformId(platformId);
	//
	// 	campaignSyncService.syncContacts(campaign.getCampaignId(), SyncDirection.LOCAL_TO_REMOTE);
	// 	System.out.println("Got result:");
	// 	contactPersonService.getByCampaignId(campaign.getCampaignId())
	// 			.forEach(System.out::println);
	//
	// 	while (true)
	// 	{
	// 		System.out.println("Sending activation form...");
	// 		try
	// 		{
	// 			// cleverReachClient.sendEmailActivationForm("324200", email);
	// 			System.out.println("Sent OK");
	// 			break;
	// 		}
	// 		catch (Exception ex)
	// 		{
	// 			System.out.println("Got exception while sending activation email:");
	// 			ex.printStackTrace();
	// 		}
	//
	// 		System.out.println("Sleeping 1 second...");
	// 		try
	// 		{
	// 			//noinspection BusyWait
	// 			Thread.sleep(1000);
	// 		}
	// 		catch (InterruptedException e)
	// 		{
	// 			e.printStackTrace();
	// 			break;
	// 		}
	// 	}
	// }

	@Test
	public void syncCampaignsLocalToRemote()
	{
		// given
		final PlatformId platformId = CampaignTestUtil.createPlatformRecord("test-2");
		final CleverReachConfig config = CampaignTestUtil.createLocalConfig(platformId);

		final String cmpName1 = "localCampaign1";
		final Campaign campaign1 = CampaignTestUtil.createCampaignRecord(cmpName1, platformId);
		final String cmpName2 = "localCampaign2";
		final Campaign campaign2 = CampaignTestUtil.createCampaignRecord(cmpName2, platformId);

		// mock CleverReachLowLevelClient
		cleverReachLowLevelClientMockedStatic.when(() -> CleverReachLowLevelClient.createAndLogin(config)).thenReturn(cleverReachLowLevelClient);

		final Group remoteCmp1 = readResource("/de/metas/marketing/gateway/cleverreach/campaign/localtoremote/RemoteGroup1.json", Group.class);
		Mockito.when(cleverReachLowLevelClient.post(Mockito.eq(CreateGroupRequest.ofName(campaign1.getName())), Mockito.any(), Mockito.eq("/groups.json/")))
				.thenReturn(remoteCmp1);

		final Group remoteCmp2 = readResource("/de/metas/marketing/gateway/cleverreach/campaign/localtoremote/RemoteGroup2.json", Group.class);
		Mockito.when(cleverReachLowLevelClient.post(Mockito.eq(CreateGroupRequest.ofName(remoteCmp2.getName())), Mockito.any(), Mockito.eq("/groups.json/")))
				.thenReturn(remoteCmp2);

		// when
		campaignSyncService.syncCampaigns(platformId, SyncDirection.LOCAL_TO_REMOTE);

		// then
		final I_MKTG_Campaign campaignRecord1 = InterfaceWrapperHelper.load(campaign1.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord1.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.INSERTED_ON_REMOTE.name());
		assertThat(campaignRecord1.getRemoteRecordId()).isEqualTo("1");
		assertThat(campaignRecord1.getName()).isEqualTo(campaign1.getName());

		final I_MKTG_Campaign campaignRecord2 = InterfaceWrapperHelper.load(campaign2.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord2.getLastSyncStatus()).isEqualTo(LocalToRemoteStatus.INSERTED_ON_REMOTE.name());
		assertThat(campaignRecord2.getRemoteRecordId()).isEqualTo("2");
		assertThat(campaignRecord2.getName()).isEqualTo(campaign2.getName());
	}

	@Test
	public void syncCampaignsRemoteToLocal()
	{
		// given
		final PlatformId platformId = CampaignTestUtil.createPlatformRecord("test-2");
		final CleverReachConfig config = CampaignTestUtil.createLocalConfig(platformId);

		final String cmpName1 = "localCampaign1";
		final String cmpRM1 = "1";
		final Campaign campaign1 = CampaignTestUtil.createCampaignRecord(cmpName1, platformId, cmpRM1);
		final String cmpName2 = "localCampaign2";
		final String cmpRM2 = "2";
		final Campaign campaign2 = CampaignTestUtil.createCampaignRecord(cmpName2, platformId, cmpRM2);

		// mock CleverReachLowLevelClient
		cleverReachLowLevelClientMockedStatic.when(() -> CleverReachLowLevelClient.createAndLogin(config)).thenReturn(cleverReachLowLevelClient);

		final Group[] remoteCmps = readResource("/de/metas/marketing/gateway/cleverreach/campaign/remotetolocal/RemoteGroups.json", Group[].class);
		final List<Group> remoteGroups = ImmutableList.copyOf(remoteCmps);
		Mockito.when(cleverReachLowLevelClient.get(Mockito.any(), Mockito.eq("/groups.json")))
				.thenReturn(remoteGroups);

		// when
		campaignSyncService.syncCampaigns(platformId, SyncDirection.REMOTE_TO_LOCAL);

		// then
		final I_MKTG_Campaign campaignRecord1 = InterfaceWrapperHelper.load(campaign1.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord1.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM.name());
		assertThat(campaignRecord1.getRemoteRecordId()).isNull();
		assertThat(campaignRecord1.getName()).isEqualTo(campaign1.getName());

		final I_MKTG_Campaign campaignRecord2 = InterfaceWrapperHelper.load(campaign2.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord2.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_OTHER_REMOTE_DATA.name());
		assertThat(campaignRecord2.getRemoteRecordId()).isEqualTo("2");
		assertThat(campaignRecord2.getName()).isEqualTo("localCampaign2-upd");

		final List<Campaign> remoteCampaigns = campaignService.retrieveByPlatformAndRemoteIds(platformId, ImmutableSet.of("3"));
		assertThat(remoteCampaigns.size()).isEqualTo(1);

		final Campaign createdCampaign3 = remoteCampaigns.get(0);
		final I_MKTG_Campaign campaignRecord3 = InterfaceWrapperHelper.load(createdCampaign3.getCampaignId(), I_MKTG_Campaign.class);
		assertThat(campaignRecord3.getLastSyncStatus()).isEqualTo(RemoteToLocalSyncResult.RemoteToLocalStatus.OBTAINED_NEW_CAMPAIGN.name());
		assertThat(campaignRecord3.getRemoteRecordId()).isEqualTo("3");
		assertThat(campaignRecord3.getName()).isEqualTo("remoteCampaign3");
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
