/*
 * #%L
 * marketing-base
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

package de.metas.marketing.base.sync;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.ContactPersonToUpsertPage;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.PageDescriptor;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Service
public class ContactPersonSyncService
{
	@NonNull
	final PlatformClientService platformClientService;
	@NonNull
	final ContactPersonService contactPersonService;
	@NonNull
	final CampaignService campaignService;

	public ContactPersonSyncService(
			final @NonNull PlatformClientService platformClientService,
			final @NonNull ContactPersonService contactPersonService,
			final @NonNull CampaignService campaignService)
	{
		this.platformClientService = platformClientService;
		this.contactPersonService = contactPersonService;
		this.campaignService = campaignService;
	}

	public void syncLocalToRemote(@NonNull final Campaign campaign)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());

		contactPersonService.streamContacts(campaign.getCampaignId())
				.map(contactPerson -> platformClient.upsertContact(campaign, contactPerson))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(contactPersonService::saveSyncResult);
	}

	public void syncLocalToRemote(
			@NonNull final Campaign campaign,
			@NonNull final List<ContactPerson> contactsToSync)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());

		contactsToSync.stream()
				.map(contactPerson -> platformClient.upsertContact(campaign, contactPerson))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(contactPersonService::saveSyncResult);
	}

	public void syncRemoteToLocal(@NonNull final Campaign campaign)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());

		final Set<String> processedContactRemoteIds = processContactPersonToUpsertPage(platformClient, campaign);
		handleContactsNoLongerAvailableOnRemote(campaign.getCampaignId(), processedContactRemoteIds);
	}

	private void handleContactsNoLongerAvailableOnRemote(@NonNull final CampaignId campaignId, @NonNull final Set<String> allActiveRemoteContactIds)
	{
		contactPersonService.streamActivelySyncedWithRemoteId(campaignId)
				.filter(contact -> Check.isNotBlank(contact.getRemoteId()))
				.filter(contactWithRemoteId -> !allActiveRemoteContactIds.contains(contactWithRemoteId.getRemoteId()))
				.map(contactRemovedFromRemote -> contactRemovedFromRemote.toBuilder().remoteId(null).build())
				.map(RemoteToLocalSyncResult::deletedOnRemotePlatform)
				.forEach(contactPersonService::saveSyncResult);
	}

	@NonNull
	private Set<String> processContactPersonToUpsertPage(@NonNull final PlatformClient platformClient, @NonNull final Campaign campaign)
	{
		final ImmutableSet.Builder<String> contactRemoteIds = ImmutableSet.builder();

		PageDescriptor currentPageDescriptor = null;
		boolean moreContacts = true;

		while (moreContacts)
		{
			final ContactPersonToUpsertPage contactPersonToUpsertPage = platformClient.getContactPersonToUpsertPage(campaign, currentPageDescriptor);

			final Request request = Request.builder()
					.orgId(platformClient.getCampaignConfig().getOrgId())
					.platformId(platformClient.getCampaignConfig().getPlatformId())
					.campaignId(campaign.getCampaignId())
					.remoteContactPersons(contactPersonToUpsertPage.getRemoteContacts())
					.build();

			final List<RemoteToLocalSyncResult> syncResults = syncRemoteContacts(request);

			final List<ContactPerson> savedContacts = contactPersonService.saveSyncResults(syncResults);
			campaignService.addContactPersonsToCampaign(savedContacts, campaign.getCampaignId());

			contactPersonToUpsertPage.getRemoteContacts()
					.stream()
					.map(ContactPersonRemoteUpdate::getRemoteId)
					.forEach(contactRemoteIds::add);

			currentPageDescriptor = contactPersonToUpsertPage.getNext();
			moreContacts = currentPageDescriptor != null;
		}

		return contactRemoteIds.build();
	}

	@NonNull
	private ImmutableList<RemoteToLocalSyncResult> syncRemoteContacts(@NonNull final Request request)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final Map<String, ContactPerson> remoteId2ContactPerson = getContactPersonByRemoteId(request);
		final Map<String, ContactPerson> email2ContactPersons = getContactPersonByEmail(request);

		for (final ContactPersonRemoteUpdate contactPersonUpdate : request.getRemoteContactPersons())
		{
			final String receivedEmailAddress = Check.assumeNotEmpty(
					EmailAddress.getEmailAddressStringOrNull(contactPersonUpdate.getAddress()),
					"A contactPersonUpdate received from the remote API has an email address; contactPersonUpdate={}", contactPersonUpdate);

			final ContactPerson existingByEmail = email2ContactPersons.get(receivedEmailAddress);
			if (existingByEmail != null)
			{
				final ContactPerson updatedContactPerson = contactPersonUpdate.updateContactPerson(existingByEmail);
				if (Check.isEmpty(existingByEmail.getRemoteId()))
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedRemoteId(updatedContactPerson));
				}

				continue;
			}

			final ContactPerson existingByRemoteId = remoteId2ContactPerson.get(contactPersonUpdate.getRemoteId());

			if (existingByRemoteId != null)
			{
				final ContactPerson updatedContactPerson = contactPersonUpdate.updateContactPerson(existingByRemoteId);

				final boolean existingContactHasDifferentEmail = !Objects
						.equals(existingByRemoteId.getEmailAddressStringOrNull(), receivedEmailAddress);

				if (existingContactHasDifferentEmail)
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedRemoteEmail(updatedContactPerson));
				}

				final DeactivatedOnRemotePlatform deactivatedOnRemotePlatform = EmailAddress.getDeactivatedOnRemotePlatform(contactPersonUpdate.getAddress());

				final boolean existingContactHasDifferentActiveStatus = !DeactivatedOnRemotePlatform
						.equals(existingByRemoteId.getDeactivatedOnRemotePlatform(), deactivatedOnRemotePlatform);

				if (existingContactHasDifferentActiveStatus)
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedEmailBounceInfo(updatedContactPerson));
				}

				continue;
			}

			final ContactPerson newContactPerson = contactPersonUpdate.toContactPerson(request.getPlatformId(), request.getOrgId());
			syncResults.add(RemoteToLocalSyncResult.obtainedNewDataRecord(newContactPerson));
		}

		return syncResults.build();
	}

	@NonNull
	private Map<String, ContactPerson> getContactPersonByEmail(@NonNull final Request request)
	{
		final Set<String> remoteEmails = request.getRemoteContactPersons()
				.stream()
				.map(ContactPersonRemoteUpdate::getAddress)
				.map(EmailAddress::getEmailAddressStringOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return contactPersonService.retrieveByEmails(request.getCampaignId(), remoteEmails)
				.stream()
				.collect(ImmutableMap.toImmutableMap(ContactPerson::getEmailAddressStringOrNull, Function.identity()));
	}

	@NonNull
	private Map<String, ContactPerson> getContactPersonByRemoteId(@NonNull final Request request)
	{
		final Set<String> remoteIds = request.getRemoteContactPersons()
				.stream()
				.map(ContactPersonRemoteUpdate::getRemoteId)
				.collect(ImmutableSet.toImmutableSet());

		return contactPersonService.retrieveByCampaignAndRemoteIds(request.getCampaignId(), remoteIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(ContactPerson::getRemoteId, Function.identity()));
	}

	@Value
	@Builder
	private static class Request
	{
		@NonNull
		PlatformId platformId;

		@NonNull
		CampaignId campaignId;

		@NonNull
		OrgId orgId;

		@NonNull
		List<ContactPersonRemoteUpdate> remoteContactPersons;
	}
}
