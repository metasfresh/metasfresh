package de.metas.marketing.gateway.cleverreach;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.LocalToRemoteSyncResult.LocalToRemoteStatus;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.model.RemoteToLocalSyncResult.RemoteToLocalStatus;
import de.metas.marketing.base.model.SyncResult;
import de.metas.marketing.gateway.cleverreach.restapi.models.Receiver;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

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
	private static final String MANUAL_GROUP_REMOTE_ID = "";

	private PlatformId platformId;
	private CleverReachClient cleverReachClient;

	@BeforeEach
	public void init()
	{
		final CleverReachConfig cleverReachConfig = getCleverReachConfig();
		this.platformId = cleverReachConfig.getPlatformId();
		cleverReachClient = new CleverReachClient(cleverReachConfig);
	}

	private CleverReachConfig getCleverReachConfig()
	{
		final File file = new File(CONFIG_FILENAME).getAbsoluteFile();
		try
		{
			final String configJsonString = Files.asCharSource(file, Charsets.UTF_8).read();
			final CleverReachConfig cleverReachConfig = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(configJsonString, CleverReachConfig.class);
			System.out.println("Read config from " + file + ":" + configJsonString);
			return cleverReachConfig;
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading " + file, ex);
		}
	}

	private static Condition<SyncResult> emailEqualsTo(@Nullable final String email)
	{
		return new Condition<>(
				syncResult -> Objects.equals(email, ContactPerson.cast(syncResult.getSynchedDataRecord()).map(ContactPerson::getEmailAddressStringOrNull).orElse(null)),
				"is contactperson with email=%s", email);
	}

	@Test
	@Disabled
	public void createUpdateDeleteCampaign()
	{
		// add one campaign
		final String nameOfCampaignToAdd = appendSystemTime("test-name1");
		final Campaign campaignToAdd = Campaign.builder()
				.name(nameOfCampaignToAdd)
				.platformId(platformId)
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
	@Disabled
	public void retrieveAllContactPersonsOfCampagin()
	{
		final Campaign campaign = Campaign.builder()
				.remoteId(MANUAL_GROUP_REMOTE_ID)
				.platformId(platformId).build();
		final Iterator<Receiver> contactPersons = cleverReachClient.retrieveAllReceivers(campaign);

		assertThat(contactPersons).hasNext();
	}

	@Test
	@Disabled
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
				.filteredOn(emailEqualsTo("test2-invalidmail"))
				.extracting(LocalToRemoteSyncResult::getLocalToRemoteStatus)
				.containsOnlyOnce(LocalToRemoteStatus.ERROR);

		assertThat(results)
				.filteredOn(emailEqualsTo("test4-invalidmail"))
				.extracting(LocalToRemoteSyncResult::getLocalToRemoteStatus)
				.containsOnlyOnce(LocalToRemoteStatus.ERROR);

		assertThat(results)
				.filteredOn(emailEqualsTo("test10@newemail.com"))
				.hasSize(1)
				.extracting(LocalToRemoteSyncResult::getLocalToRemoteStatus)
				.contains(LocalToRemoteStatus.UPSERTED_ON_REMOTE);

		assertThat(results)
				.filteredOn(emailEqualsTo("test30@newemail.com"))
				.hasSize(1)
				.allSatisfy(singleResult -> {
					assertThat(singleResult.getLocalToRemoteStatus()).isEqualTo(LocalToRemoteStatus.UPSERTED_ON_REMOTE);
					assertThat(ContactPerson.cast(singleResult.getSynchedDataRecord()).get().getRemoteId()).isNotEmpty();
				});
	}

	@Test
	@Disabled
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
				.filteredOn(emailEqualsTo("test1@email"))
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.containsOnlyOnce(RemoteToLocalStatus.NOT_YET_ADDED_TO_REMOTE_PLATFORM);

		assertThat(result)
				.filteredOn(emailEqualsTo("test2@email"))
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.containsOnlyOnce(RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM);

		assertThat(result)
				.filteredOn(emailEqualsTo("real-email1"))
				.hasSize(1)
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.contains(RemoteToLocalStatus.OBTAINED_REMOTE_ID);

		assertThat(result)
				.filteredOn(emailEqualsTo("real-email2"))
				.hasSize(1)
				.allSatisfy(singleResult -> {
					assertThat(singleResult.getRemoteToLocalStatus()).isEqualTo(RemoteToLocalStatus.OBTAINED_REMOTE_ID);

					final ContactPerson contactPerson = ContactPerson.cast(singleResult.getSynchedDataRecord()).get();
					assertThat(contactPerson.getRemoteId()).isNotEmpty();
				});

		assertThat(result).filteredOn(emailEqualsTo("test1@newemail.com"))
				.hasSize(1)
				.extracting(RemoteToLocalSyncResult::getRemoteToLocalStatus)
				.contains(RemoteToLocalStatus.OBTAINED_NEW_CONTACT_PERSON);

		assertThat(result)
				.filteredOn(emailEqualsTo("bounce-email1"))
				.hasSize(1)
				.allSatisfy(singleResult -> {
					assertThat(singleResult.getRemoteToLocalStatus()).isEqualTo(RemoteToLocalStatus.OBTAINED_EMAIL_BOUNCE_INFO);

					final ContactPerson contactPerson = ContactPerson.cast(singleResult.getSynchedDataRecord()).get();
					assertThat(contactPerson.getRemoteId()).isNotEmpty();

					final EmailAddress email = EmailAddress.cast(contactPerson.getAddress()).get();
					assertThat(email.getDeactivatedOnRemotePlatform()).isEqualTo(DeactivatedOnRemotePlatform.YES);
				});
	}

	@Test
	@Disabled
	void publishPersonsWithDifferentActivationStatus()
	{
		final String domain = "a" + System.currentTimeMillis() + ".com";
		System.out.println("Using domain: " + domain);

		final Campaign campaign = Campaign.builder().platformId(platformId).remoteId("524972").build();

		final ContactPerson.ContactPersonBuilder personTemplate = ContactPerson.builder().platformId(platformId);
		ImmutableList<ContactPerson> persons = ImmutableList.of(
				personTemplate.address(EmailAddress.of("inactive@" + domain, DeactivatedOnRemotePlatform.YES)).build(),
				personTemplate.address(EmailAddress.of("active@" + domain, DeactivatedOnRemotePlatform.NO)).build(),
				personTemplate.address(EmailAddress.of("unknown_status@" + domain, DeactivatedOnRemotePlatform.UNKNOWN)).build()
		);

		// Send them to CleverReach (first time)
		{
			final List<LocalToRemoteSyncResult> results = cleverReachClient.syncContactPersonsLocalToRemote(campaign, persons);
			System.out.println("Got results (1):");
			results.forEach(System.out::println);

			persons = results.stream()
					.map(LocalToRemoteSyncResult::getSynchedDataRecord)
					.map(ContactPerson.class::cast)
					.collect(ImmutableList.toImmutableList());
		}

		// Send them to CleverReach (second time)
		{
			System.out.println("\n----------------------------------------------------------------------------------");
			System.out.println("Sending them again...");

			final List<LocalToRemoteSyncResult> results = cleverReachClient.syncContactPersonsLocalToRemote(campaign, persons);
			System.out.println("Got results (1):");
			results.forEach(System.out::println);
		}
	}

	@Test
	@Disabled
	void publishInactiveEmailAndThenSendActivationForm()
	{
		final String domain = "a" + System.currentTimeMillis() + ".com";
		final String email = "inactive@" + domain;
		System.out.println("Using email: " + email);

		final Campaign campaign = Campaign.builder().platformId(platformId).remoteId("524972").build();

		final ContactPerson.ContactPersonBuilder personTemplate = ContactPerson.builder().platformId(platformId);

		final List<LocalToRemoteSyncResult> results = cleverReachClient.syncContactPersonsLocalToRemote(
				campaign,
				ImmutableList.of(personTemplate.address(EmailAddress.of(email, DeactivatedOnRemotePlatform.YES)).build()));
		System.out.println("Got result:");
		results.forEach(System.out::println);

		while (true)
		{
			System.out.println("Sending activation form...");
			try
			{
				cleverReachClient.sendEmailActivationForm("324200", email);
				System.out.println("Sent OK");
				break;
			}
			catch (Exception ex)
			{
				System.out.println("Got exception while sending activation email:");
				ex.printStackTrace();
			}

			System.out.println("Sleeping 1 second...");
			try
			{
				//noinspection BusyWait
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
}
