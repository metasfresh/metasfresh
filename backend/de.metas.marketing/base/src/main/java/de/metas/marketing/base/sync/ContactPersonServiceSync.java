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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.ContactPersonToUpsertPage;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContactPersonServiceSync
{
	@NonNull
	final PlatformClientService platformClientService;
	@NonNull
	final ContactPersonService contactPersonService;
	@NonNull
	final CampaignService campaignService;

	public ContactPersonServiceSync(
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

		final Iterator<I_MKTG_ContactPerson> contactIterator = contactPersonService.iterateContacts(campaign.getCampaignId());

		while (contactIterator.hasNext())
		{
			final ContactPerson contactPerson = ContactPersonRepository.toContactPerson(contactIterator.next());

			final LocalToRemoteSyncResult syncResult = platformClient.upsertContact(campaign, contactPerson);
			contactPersonService.saveSyncResults(ImmutableList.of(syncResult));
		}
	}

	public void syncRemoteToLocal(@NonNull final Campaign campaign)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());

		handleContactsNoLongerAvailableOnRemote(platformClient, campaign);

		processContactPersonToUpsertPage(platformClient, campaign);
	}

	private void handleContactsNoLongerAvailableOnRemote(@NonNull final PlatformClient platformClient, @NonNull final Campaign campaign)
	{
		final Iterator<I_MKTG_ContactPerson> contactIterator = contactPersonService.iterateContactsWithRemoteId(campaign.getCampaignId());

		while (contactIterator.hasNext())
		{
			final ContactPerson contactPerson = ContactPersonRepository.toContactPerson(contactIterator.next());

			final Optional<ContactPersonRemoteUpdate> remoteContact = platformClient.getContactById(campaign, contactPerson.getRemoteId());

			if (!remoteContact.isPresent())
			{
				final RemoteToLocalSyncResult syncResult = RemoteToLocalSyncResult.deletedOnRemotePlatform(contactPerson.toBuilder().remoteId(null).build());
				contactPersonService.saveSyncResults(ImmutableList.of(syncResult));
			}
		}
	}

	private void processContactPersonToUpsertPage(@NonNull final PlatformClient platformClient, @NonNull final Campaign campaign)
	{
		PageDescriptor currentPageDescriptor = platformClient.getContactPersonPageDescriptor();

		while (true)
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

			if (contactPersonToUpsertPage.getNext() == null)
			{
				break;
			}

			currentPageDescriptor = contactPersonToUpsertPage.getNext();
		}
	}

	@NonNull
	private ImmutableList<RemoteToLocalSyncResult> syncRemoteContacts(@NonNull final Request request)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final Map<String, ContactPersonRemoteUpdate> remoteId2RemoteContact = request.getRemoteContactPersons()
				.stream()
				.collect(Collectors.toMap(ContactPersonRemoteUpdate::getRemoteId, Function.identity()));

		final List<ContactPerson> existingContacts = contactPersonService.retrieveByCampaignAndRemoteIds(request.getCampaignId(), remoteId2RemoteContact.keySet());

		final ImmutableListMultimap<String, ContactPerson> remoteId2contactPersons = Multimaps.index(existingContacts, ContactPerson::getRemoteId);

		final List<String> remoteEmails = request.getRemoteContactPersons()
				.stream()
				.map(ContactPersonRemoteUpdate::getAddress)
				.map(EmailAddress::getEmailAddressStringOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		final List<ContactPerson> existingContactsWithEmail = contactPersonService.retrieveByEmails(request.getCampaignId(), remoteEmails);

		final ImmutableListMultimap<String, ContactPerson> email2contactPersons = Multimaps.index(existingContactsWithEmail, ContactPerson::getEmailAddressStringOrNull);

		for (final ContactPersonRemoteUpdate contactPersonUpdate : request.getRemoteContactPersons())
		{
			final String receivedEmailAddress = Check.assumeNotEmpty(
					EmailAddress.getEmailAddressStringOrNull(contactPersonUpdate.getAddress()),
					"A contactPersonUpdate received from the remote API has an email address; contactPersonUpdate={}", contactPersonUpdate);

			final ImmutableList<ContactPerson> contactPersonsByEmail = email2contactPersons.get(receivedEmailAddress);
			for (final ContactPerson contactPerson : contactPersonsByEmail)
			{
				final ContactPerson updatedContactPerson = contactPersonUpdate.updateContactPerson(contactPerson);
				if (Check.isEmpty(contactPerson.getRemoteId()))
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedRemoteId(updatedContactPerson));
					remoteId2RemoteContact.remove(updatedContactPerson.getRemoteId());
				}
			}

			final ImmutableList<ContactPerson> contactPersonsByRemoteId = remoteId2contactPersons.get(contactPersonUpdate.getRemoteId());
			for (final ContactPerson contactPerson : contactPersonsByRemoteId)
			{
				final ContactPerson updatedContactPerson = contactPersonUpdate.updateContactPerson(contactPerson);

				final boolean existingContactHasDifferentEmail = !Objects.equals(
						contactPerson.getEmailAddressStringOrNull(),
						receivedEmailAddress);
				if (existingContactHasDifferentEmail)
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedRemoteEmail(updatedContactPerson));
					remoteId2RemoteContact.remove(contactPerson.getRemoteId());
				}

				final DeactivatedOnRemotePlatform deactivatedOnRemotePlatform = EmailAddress.getDeactivatedOnRemotePlatform(contactPersonUpdate.getAddress());

				final boolean existingContactHasDifferentActiveStatus = !DeactivatedOnRemotePlatform.equals(
						contactPerson.getDeactivatedOnRemotePlatform(),
						deactivatedOnRemotePlatform);
				if (existingContactHasDifferentActiveStatus)
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedEmailBounceInfo(updatedContactPerson));
					remoteId2RemoteContact.remove(contactPerson.getRemoteId());
				}
			}
		}

		remoteId2RemoteContact.values()
				.stream()
				.map(contactPersonUpdate -> RemoteToLocalSyncResult.obtainedNewDataRecord(contactPersonUpdate.toContactPerson(request.getPlatformId(), request.getOrgId())))
				.forEach(syncResults::add);

		return syncResults.build();
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
