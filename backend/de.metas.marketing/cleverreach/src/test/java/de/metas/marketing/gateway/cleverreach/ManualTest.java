package de.metas.marketing.gateway.cleverreach;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.LocalToRemoteSyncResult.LocalToRemoteStatus;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.model.RemoteToLocalSyncResult.RemoteToLocalStatus;
import de.metas.marketing.base.model.SyncResult;
import de.metas.marketing.gateway.cleverreach.restapi.models.Receiver;
import lombok.NonNull;

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

	private static final PlatformId PLATFORM_ID = PlatformId.ofRepoId(30);
	private static final String MANUAL_GROUP_REMOTE_ID = "";
	private CleverReachClient cleverReachClient;

	@Before
	public void init()
	{
		final CleverReachConfig cleverReachConfig = CleverReachConfig.builder()
				.client_id("")
				.login("")
				.password("")
				.platformId(PLATFORM_ID)
				.build();
		cleverReachClient = new CleverReachClient(cleverReachConfig);
	}

	@Test
	@Ignore
	public void createUpdateDeleteCampagin()
	{
		// add one campaign
		final String nameOfCampaignToAdd = appendSystemTime("test-name1");
		final Campaign campaignToAdd = Campaign.builder()
				.name(nameOfCampaignToAdd)
				.platformId(PLATFORM_ID)
				.build();

		final List<LocalToRemoteSyncResult> addedCampaignResults = cleverReachClient.syncCampaignsLocalToRemote(ImmutableList.of(campaignToAdd));
		assertThat(addedCampaignResults).hasSize(1);

		final LocalToRemoteSyncResult localToRemoteSyncResult = addedCampaignResults.get(0);
		assertThat(localToRemoteSyncResult.getLocalToRemoteStatus()).isEqualTo(LocalToRemoteStatus.INSERTED_ON_REMOTE);
		assertThat(localToRemoteSyncResult.getSynchedDataRecord()).isInstanceOf(Campaign.class);

		final Campaign addedCampaign = Campaign.cast(localToRemoteSyncResult.getSynchedDataRecord());
		assertThat(addedCampaign.getRemoteId()).isNotEmpty();
		assertThat(addedCampaign.getName()).isEqualTo(nameOfCampaignToAdd);

		// now change its name
		final Campaign campaignToUpdate = addedCampaign.toBuilder().name("testcampaign-name2").remoteId(addedCampaign.getRemoteId()).build();
		final List<LocalToRemoteSyncResult> updatedCampaignResults = cleverReachClient.syncCampaignsLocalToRemote(ImmutableList.of(campaignToUpdate));
		assertThat(updatedCampaignResults).hasSize(1);
		assertThat(updatedCampaignResults.get(0).getLocalToRemoteStatus()).isEqualTo(LocalToRemoteStatus.UPDATED_ON_REMOTE);

		final Campaign updatedCampaign = Campaign.cast(updatedCampaignResults.get(0).getSynchedDataRecord());
		assertThat(updatedCampaign.getRemoteId()).isEqualTo(addedCampaign.getRemoteId());
		assertThat(updatedCampaign.getName()).isEqualTo("testcampaign-name2");

		cleverReachClient.deleteCampaign(updatedCampaign);
	}

	private static String appendSystemTime(@NonNull final String name)
	{
		return name + SystemTime.millis();
	}

	@Test
	@Ignore
	public void retrieveAllContactPersonsOfCampagin()
	{
		final Campaign campaign = Campaign.builder()
				.remoteId(MANUAL_GROUP_REMOTE_ID)
				.platformId(PLATFORM_ID).build();
		final Iterator<Receiver> contactPersons = cleverReachClient.retrieveAllReceivers(campaign);

		assertThat(contactPersons).hasNext();
	}

	@Test
	@Ignore
	public void syncContactPersonsLocalToRemote()
	{
		final Campaign campaign = Campaign.builder().remoteId(MANUAL_GROUP_REMOTE_ID).build();

		final ContactPerson newPerson1 = ContactPerson.builder().address(EmailAddress.ofString("test10@newemail.com")).build();
		final ContactPerson newPerson2 = ContactPerson.builder().address(EmailAddress.ofString("test2-invalidmail")).build();
		final ContactPerson newPerson3 = ContactPerson.builder().address(EmailAddress.ofString("test30@newemail.com")).build();
		final ContactPerson newPerson4 = ContactPerson.builder().address(EmailAddress.ofString("test4-invalidmail")).build();
		final List<LocalToRemoteSyncResult> results = cleverReachClient.syncContactPersonsLocalToRemote(
				campaign, ImmutableList.of(newPerson1, newPerson2, newPerson3, newPerson4));

		assertThat(results)
				.filteredOn(email("test2-invalidmail"))
				.extracting(LocalToRemoteSyncResult::getLocalToRemoteStatus)
				.containsOnlyOnce(LocalToRemoteStatus.ERROR);

		assertThat(results)
				.filteredOn(email("test4-invalidmail"))
				.extracting(LocalToRemoteSyncResult::getLocalToRemoteStatus)
				.containsOnlyOnce(LocalToRemoteStatus.ERROR);

		assertThat(results)
				.filteredOn(email("test10@newemail.com"))
				.hasSize(1)
				.extracting(LocalToRemoteSyncResult::getLocalToRemoteStatus)
				.contains(LocalToRemoteStatus.UPSERTED_ON_REMOTE);

		assertThat(results)
				.filteredOn(email("test30@newemail.com"))
				.hasSize(1)
				.allSatisfy(singleResult -> {
					assertThat(singleResult.getLocalToRemoteStatus()).isEqualTo(LocalToRemoteStatus.UPSERTED_ON_REMOTE);
					assertThat(ContactPerson.cast(singleResult.getSynchedDataRecord()).get().getRemoteId()).isNotEmpty();
				});
	}

	@Test
	@Ignore
	public void syncContactPersonsRemoteToLocal()
	{
		final Campaign campaign = Campaign.builder().remoteId(MANUAL_GROUP_REMOTE_ID).build();

		final ContactPerson newPerson1 = ContactPerson.builder().address(EmailAddress.ofString("test10@newemail.com")).build();
		final ContactPerson newPerson3 = ContactPerson.builder().address(EmailAddress.ofString("test30@newemail.com")).build();
		cleverReachClient.syncContactPersonsLocalToRemote(campaign, ImmutableList.of(newPerson1, newPerson3));

		final ContactPerson person1 = ContactPerson.builder().address(EmailAddress.ofString("test1@email")).build();
		final ContactPerson person2 = ContactPerson.builder().address(EmailAddress.ofString("test2@email")).remoteId("-10").build();
		final ContactPerson person3 = ContactPerson.builder().address(EmailAddress.ofString("real-email1")).build();
		final ContactPerson person4 = ContactPerson.builder().address(EmailAddress.ofString("real-email2")).build();
		final ContactPerson person5 = ContactPerson.builder().address(EmailAddress.ofString("bounce-email1")).remoteId("5").build();

		final List<RemoteToLocalSyncResult> result = cleverReachClient.syncContactPersonsRemoteToLocal(
				campaign, ImmutableList.of(person1, person2, person3, person4, person5));
		assertThat(result)
				.filteredOn(email("test1@email"))
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.containsOnlyOnce(RemoteToLocalStatus.NOT_YET_ADDED_TO_REMOTE_PLATFORM);

		assertThat(result)
				.filteredOn(email("test2@email"))
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.containsOnlyOnce(RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM);

		assertThat(result)
				.filteredOn(email("real-email1"))
				.hasSize(1)
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.contains(RemoteToLocalStatus.OBTAINED_REMOTE_ID);

		assertThat(result)
				.filteredOn(email("real-email2"))
				.hasSize(1)
				.allSatisfy(singleResult -> {
					assertThat(singleResult.getRemoteToLocalStatus()).isEqualTo(RemoteToLocalStatus.OBTAINED_REMOTE_ID);

					final ContactPerson contactPerson = ContactPerson.cast(singleResult.getSynchedDataRecord()).get();
					assertThat(contactPerson.getRemoteId()).isNotEmpty();
				});

		assertThat(result).filteredOn(email("test1@newemail.com"))
				.hasSize(1)
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.contains(RemoteToLocalStatus.OBTAINED_NEW_CONTACT_PERSON);

		assertThat(result)
				.filteredOn(email("bounce-email1"))
				.hasSize(1)
				.allSatisfy(singleResult -> {
					assertThat(singleResult.getRemoteToLocalStatus()).isEqualTo(RemoteToLocalStatus.OBTAINED_EMAIL_BOUNCE_INFO);

					final ContactPerson contactPerson = ContactPerson.cast(singleResult.getSynchedDataRecord()).get();
					assertThat(contactPerson.getRemoteId()).isNotEmpty();

					final EmailAddress email = EmailAddress.cast(contactPerson.getAddress()).get();
					assertThat(email.getDeactivatedOnRemotePlatform())
							.isNotNull()
							.isTrue();
				});
	}

	private Condition<SyncResult> email(@Nullable final String email)
	{
		final Predicate<SyncResult> p = r -> Objects.equals(
				email,
				ContactPerson
						.cast(r.getSynchedDataRecord())
						.map(ContactPerson::getEmailAddessStringOrNull)
						.orElse(null));

		return new Condition<>(
				p,
				"is contactperson with email=%s", email);
	}

}
