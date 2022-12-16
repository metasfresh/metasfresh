/*
 * #%L
 * marketing-base
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

package de.metas.marketing.base.helper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class RemoteToLocalContactPersonSync
{
	@NonNull
	public ImmutableList<RemoteToLocalSyncResult> syncRemoteContacts(@NonNull final Request request)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final List<ContactPerson> personsWithEmailOrRemoteId = SyncUtil.filterForPersonsWithEmailOrRemoteId(request.getExistingContactPersons(), syncResults);
		final ImmutableList<ContactPerson> contactPersonsWithEmail = personsWithEmailOrRemoteId
				.stream()
				.filter(ContactPerson::hasEmailAddress)
				.collect(ImmutableList.toImmutableList());

		final ImmutableListMultimap<String, ContactPerson> email2contactPersons = Multimaps.index(
				contactPersonsWithEmail,
				ContactPerson::getEmailAddressStringOrNull);

		final Map<Boolean, List<ContactPerson>> contactPersonsWithAndWithoutRemoteId = personsWithEmailOrRemoteId
				.stream()
				.collect(Collectors.partitioningBy(c -> !Check.isBlank(c.getRemoteId())));

		final ImmutableSet<ContactPerson> contactPersonsWithRemoteId = ImmutableSet.copyOf(contactPersonsWithAndWithoutRemoteId.get(true));

		final ImmutableSet<ContactPerson> contactPersonsWithoutRemoteId = ImmutableSet.copyOf(contactPersonsWithAndWithoutRemoteId.get(false));

		final ImmutableListMultimap<String, ContactPerson> remoteId2contactPersons = Multimaps.index(
				contactPersonsWithRemoteId,
				ContactPerson::getRemoteId);

		final HashMap<String, Collection<ContactPerson>> remoteId2contactPersonsNotYetFound = new HashMap<>(remoteId2contactPersons.asMap());
		final HashMap<String, Collection<ContactPerson>> email2contactPersonsWithoutIdNotYetFound = new HashMap<>(Multimaps
																														  .index(contactPersonsWithoutRemoteId, ContactPerson::getEmailAddressStringOrNull)
																														  .asMap());
		for (final ContactPersonRemoteUpdate contactPersonUpdate : request.getRemoteContactPersons())
		{
			final String receivedEmailAddress = Check.assumeNotEmpty(
					EmailAddress.getEmailAddressStringOrNull(contactPersonUpdate.getAddress()),
					"A contactPersonUpdate received from the remote API has an email address; contactPersonUpdate={}", contactPersonUpdate);

			remoteId2contactPersonsNotYetFound.remove(contactPersonUpdate.getRemoteId());
			email2contactPersonsWithoutIdNotYetFound.remove(receivedEmailAddress);

			final ImmutableList<ContactPerson> contactPersonsByEmail = email2contactPersons.get(receivedEmailAddress);
			for (final ContactPerson contactPerson : contactPersonsByEmail)
			{
				final ContactPerson updatedContactPerson = contactPersonUpdate.updateContactPerson(contactPerson);
				if (Check.isEmpty(contactPerson.getRemoteId()))
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedRemoteId(updatedContactPerson));
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
				}

				final DeactivatedOnRemotePlatform deactivatedOnRemotePlatform = EmailAddress.getDeactivatedOnRemotePlatform(contactPersonUpdate.getAddress());

				final boolean existingContactHasDifferentActiveStatus = !DeactivatedOnRemotePlatform.equals(
						contactPerson.getDeactivatedOnRemotePlatform(),
						deactivatedOnRemotePlatform);
				if (existingContactHasDifferentActiveStatus)
				{
					syncResults.add(RemoteToLocalSyncResult.obtainedEmailBounceInfo(updatedContactPerson));
				}
			}

			if (!email2contactPersons.containsKey(receivedEmailAddress))
			{
				syncResults.add(RemoteToLocalSyncResult.obtainedNewDataRecord(contactPersonUpdate.toContactPerson(request.getPlatformId(), request.getOrgId())));
			}
		}

		remoteId2contactPersonsNotYetFound.entrySet()
				.stream()
				.flatMap(e -> e.getValue().stream())
				.map(p -> p.toBuilder().remoteId(null).build())
				.map(RemoteToLocalSyncResult::deletedOnRemotePlatform)
				.forEach(syncResults::add);

		email2contactPersonsWithoutIdNotYetFound.entrySet()
				.stream()
				.flatMap(e -> e.getValue().stream())
				.map(p -> p.toBuilder().remoteId(null).build())
				.map(RemoteToLocalSyncResult::notYetAddedToRemotePlatform)
				.forEach(syncResults::add);

		return syncResults.build();
	}

	@Value
	@Builder
	public class Request
	{
		@NonNull
		PlatformId platformId;

		@NonNull
		OrgId orgId;

		@NonNull
		List<ContactPerson> existingContactPersons;

		@NonNull
		List<ContactPersonRemoteUpdate> remoteContactPersons;
	}
}
