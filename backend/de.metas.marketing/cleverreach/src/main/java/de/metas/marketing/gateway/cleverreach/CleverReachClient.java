package de.metas.marketing.gateway.cleverreach;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.logging.TableRecordMDC;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.DataRecord;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.marketing.gateway.cleverreach.restapi.models.CreateGroupRequest;
import de.metas.marketing.gateway.cleverreach.restapi.models.Group;
import de.metas.marketing.gateway.cleverreach.restapi.models.Receiver;
import de.metas.marketing.gateway.cleverreach.restapi.models.ReceiverUpsert;
import de.metas.marketing.gateway.cleverreach.restapi.models.SendEmailActivationFormRequest;
import de.metas.marketing.gateway.cleverreach.restapi.models.UpdateGroupRequest;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.collections.PagedIterator;
import de.metas.util.collections.PagedIterator.Page;
import de.metas.util.collections.PagedIterator.PageFetcher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.email.EmailValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.core.ParameterizedTypeReference;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static de.metas.util.Check.assumeNotEmpty;

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

public class CleverReachClient implements PlatformClient
{
	// @formatter:off
	private static final ParameterizedTypeReference<List<Object>> HETEROGENOUS_LIST = new ParameterizedTypeReference<List<Object>>() {}; // @formatter:on

	// @formatter:off
	private static final ParameterizedTypeReference<Object> SINGLE_HETEROGENOUS_TYPE = new ParameterizedTypeReference<Object>() {}; // @formatter:on

	// @formatter:off
	private static final ParameterizedTypeReference<List<Group>> LIST_OF_GROUPS_TYPE = new ParameterizedTypeReference<List<Group>>() {}; // @formatter:on

	// @formatter:off
	private static final ParameterizedTypeReference<Group> SINGLE_GROUP_TYPE = new ParameterizedTypeReference<Group>() {}; // @formatter:on

	// @formatter:off
	private static final ParameterizedTypeReference<List<Receiver>> LIST_OF_RECEIVERS_TYPE = new ParameterizedTypeReference<List<Receiver>>() {}; // @formatter:on

	private final CleverReachConfig cleverReachConfig;

	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private final CleverReachLowLevelClient lowLevelClient = CleverReachLowLevelClient.createAndLogin(cleverReachConfig);

	private final PlatformId platformId;

	public CleverReachClient(@NonNull final CleverReachConfig cleverReachConfig)
	{
		this.cleverReachConfig = cleverReachConfig;
		this.platformId = cleverReachConfig.getPlatformId();
	}

	private Group createGroup(@NonNull final Campaign campaign)
	{
		return getLowLevelClient()
				.post(
						CreateGroupRequest.ofName(campaign.getName()),
						SINGLE_GROUP_TYPE,
						"/groups.json/");
	}

	private void updateGroup(@NonNull final Campaign campaign)
	{
		final String url = String.format("/groups.json/%s", campaign.getRemoteId());
		getLowLevelClient().put(
				UpdateGroupRequest.ofName(campaign.getName()),
				SINGLE_GROUP_TYPE,
				url);
	}

	public LocalToRemoteSyncResult deleteCampaign(@NonNull final Campaign campaign)
	{
		final String remoteId = Check.assumeNotEmpty(
				campaign.getRemoteId(),
				"The given campaign to be deleted has a remoteId; campaign={}", campaign);

		final String url = String.format("/groups.json/%s", remoteId);
		getLowLevelClient().delete(url);

		return LocalToRemoteSyncResult.deleted(campaign);
	}

	public Campaign retrieveCampaign(@NonNull final String groupId)
	{
		final String url = String.format("/groups.json/%s", groupId);
		final Group group = getLowLevelClient().get(SINGLE_GROUP_TYPE, url);
		return group.toCampaign();
	}

	public List<Campaign> retrieveAllCampaigns()
	{
		final List<Group> groups = retrieveAllGroups();

		return groups.stream()
				.map(Group::toCampaign)
				.collect(ImmutableList.toImmutableList());
	}

	private List<Group> retrieveAllGroups()
	{
		final String url = "/groups.json";
		return getLowLevelClient().get(LIST_OF_GROUPS_TYPE, url);
	}

	@VisibleForTesting
	Stream<Receiver> streamAllReceivers(@NonNull final Campaign campaign)
	{
		final String remoteGroupId = Check.assumeNotNull(campaign.getRemoteId(), "campaign's remoteId is set: {}", campaign);
		final PageFetcher<Receiver> pageFetcher = createReceiversPageFetcher(remoteGroupId);

		final PagedIterator<Receiver> iterator = PagedIterator.<Receiver>builder()
				.pageSize(1000) // according to https://rest.cleverreach.com/explorer/v3/#!/groups-v3/list_groups_get, the maximum page size is 5000
				.pageFetcher(pageFetcher)
				.build();

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
	}

	private PageFetcher<Receiver> createReceiversPageFetcher(@NonNull final String remoteGroupId)
	{
		final String urlPathAndParams = "/groups.json/{group_id}/receivers?pagesize={pagesize}&page={page}";

		return (firstRow, pageSize) -> {
			final int zeroBasedPageNo = firstRow / Check.assumeGreaterThanZero(pageSize, "currentPageSize");
			final List<Receiver> receivers = getLowLevelClient()
					.get(
							LIST_OF_RECEIVERS_TYPE,
							urlPathAndParams,
							remoteGroupId, pageSize, zeroBasedPageNo);

			if (receivers.isEmpty())
			{
				return null;
			}
			return Page.ofRows(receivers);
		};
	}

	/**
	 * Note: if something is sorted out by this method, then that's probably some bug.
	 */
	private <T extends DataRecord> List<T> filterForRecordsWithCorrectPlatformId(
			@NonNull final List<T> records,
			@NonNull final ImmutableList.Builder<LocalToRemoteSyncResult> resultToAddErrorsTo)
	{
		final String errorMessage = StringUtils.formatMessage("Data record's platformId={} does not match this client's platFormId={}", platformId);

		final Map<Boolean, List<T>> okAndNotOkDataRecords = records
				.stream()
				.collect(Collectors.partitioningBy(record -> PlatformId.equals(record.getPlatformId(), platformId)));

		okAndNotOkDataRecords.get(false)
				.stream()
				.map(record -> LocalToRemoteSyncResult.error(record, errorMessage))
				.forEach(resultToAddErrorsTo::add);

		return okAndNotOkDataRecords.get(true);
	}

	/**
	 * @param resultToAddErrorsTo to each contactPerson without an email, and error result is added to this list builder.
	 * @return the persons that do have a non-empty email
	 */
	private static List<ContactPerson> filterForPersonsWithEmail(
			@NonNull final List<ContactPerson> contactPersons,
			@NonNull final ImmutableList.Builder<LocalToRemoteSyncResult> resultToAddErrorsTo)
	{
		final Map<Boolean, List<ContactPerson>> personsWithAndWithoutEmail = contactPersons
				.stream()
				.collect(Collectors.partitioningBy(contactPerson -> EmailValidator.isValid(contactPerson.getEmailAddressStringOrNull())));

		personsWithAndWithoutEmail.get(false)
				.stream()
				.map(contactPerson -> LocalToRemoteSyncResult.error(contactPerson, "Contact person has no (valid) email address"))
				.forEach(resultToAddErrorsTo::add);

		return personsWithAndWithoutEmail.get(true);
	}

	@Override
	public List<LocalToRemoteSyncResult> syncContactPersonsLocalToRemote(
			@NonNull final Campaign campaign,
			@NonNull final List<ContactPerson> contactPersons)
	{
		final ImmutableList.Builder<LocalToRemoteSyncResult> syncResults = ImmutableList.builder();

		if (filterForRecordsWithCorrectPlatformId(ImmutableList.of(campaign), syncResults).isEmpty())
		{
			return syncResults.build();
		}

		// make sure that we only send records that have a syntactically valid email and that have the correct platform-id
		final List<ContactPerson> personsWithEmail = filterForRecordsWithCorrectPlatformId(
				filterForPersonsWithEmail(
						contactPersons,
						syncResults),
				syncResults);

		final ImmutableListMultimap<String, ContactPerson> email2contactPersons = Multimaps.index(personsWithEmail, ContactPerson::getEmailAddressStringOrNull);

		final HashMap<String, Collection<ContactPerson>> email2contactPersonsWithoutErrorResponse = new HashMap<>(email2contactPersons.asMap());

		if (personsWithEmail.isEmpty())
		{
			return syncResults.build();
		}

		final ImmutableList<ReceiverUpsert> receiversUpserts = personsWithEmail
				.stream()
				.map(ReceiverUpsert::of)
				.collect(ImmutableList.toImmutableList());

		final String groupRemoteId = assumeNotEmpty(campaign.getRemoteId(), "Then given campaign needs to have a RemoteId; campagin={}", campaign);
		final String insertUrl = String.format("/groups.json/%s/receivers/upsert", groupRemoteId);

		final List<Object> results = getLowLevelClient()
				.post(receiversUpserts,
						HETEROGENOUS_LIST,
						insertUrl);

		Check.errorUnless(results.size() == personsWithEmail.size(),
				"The number of results needs to be the same as the number of contacts which we send; number of results={}; number of contacts that we send={}",
				results.size(), personsWithEmail.size());
		for (int i = 0; i < results.size(); i++)
		{
			final Object resultObj = results.get(i);
			if (isNotBlankString(resultObj))
			{
				final String resultStr = (String)resultObj;
				final Optional<InvalidEmail> invalidEmailInfo = createInvalidEmailInfo(resultStr);

				invalidEmailInfo.ifPresent(info -> {
					syncResults.addAll(createErrorResults(email2contactPersons, info));
					email2contactPersonsWithoutErrorResponse.remove(info.getEmail());
				});
			}
			else if (resultObj instanceof Map)
			{
				final Map<?, ?> resultMap = (Map<?, ?>)resultObj;
				if (resultMap.containsKey("id"))
				{
					final String resultRemoteId = String.valueOf(resultMap.get("id"));

					final ContactPerson person = personsWithEmail.get(i);
					final ContactPerson updatedPerson = person.toBuilder()
							.remoteId(resultRemoteId)
							.build();

					syncResults.add(LocalToRemoteSyncResult.upserted(updatedPerson));
				}
			}
			else
			{
				final ContactPerson person = personsWithEmail.get(i);
				syncResults.add(LocalToRemoteSyncResult.error(person, "Unexpected result='" + resultObj + "'"));
			}
		}

		return syncResults.build();
	}

	private static boolean isNotBlankString(@Nullable final Object resultObj)
	{
		if (resultObj instanceof String)
		{
			return Check.isNotBlank((String)resultObj);
		}
		else
		{
			return false;
		}
	}

	@Override
	public List<LocalToRemoteSyncResult> syncCampaignsLocalToRemote(@NonNull final List<Campaign> campaigns)
	{
		final ImmutableList.Builder<LocalToRemoteSyncResult> syncResults = ImmutableList.builder();

		final List<Campaign> campaignsWithCorrectPlatformId = filterForRecordsWithCorrectPlatformId(campaigns, syncResults);

		for (final Campaign campaign : campaignsWithCorrectPlatformId)
		{
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_MKTG_Campaign.Table_Name, campaign.getCampaignId()))
			{
				syncResults.add(createOrUpdateGroup(campaign));
			}
		}
		return syncResults.build();
	}

	@Override
	public List<RemoteToLocalSyncResult> syncCampaignsRemoteToLocal(@NonNull final List<Campaign> campaigns)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final ImmutableListMultimap<String, Campaign> remoteId2campaigns = Multimaps.index(campaigns, Campaign::getRemoteId);

		final HashMap<String, Collection<Campaign>> remoteId2campaignsNotYetFound = new HashMap<>(remoteId2campaigns.asMap());

		final List<CampaignRemoteUpdate> campaignUpdates = retrieveAllGroups()
				.stream()
				.map(Group::toCampaignUpdate)
				.collect(ImmutableList.toImmutableList());

		for (final CampaignRemoteUpdate campaignUpdate : campaignUpdates)
		{
			remoteId2campaignsNotYetFound.remove(campaignUpdate.getRemoteId());

			final ImmutableList<Campaign> campaignsToUpdate = remoteId2campaigns.get(campaignUpdate.getRemoteId());
			if (campaignsToUpdate.isEmpty())
			{
				final DataRecord newCampaign = campaignUpdate.toCampaign(platformId);
				syncResults.add(RemoteToLocalSyncResult.obtainedNewDataRecord(newCampaign));
			}
			else
			{
				for (final Campaign campaignToUpdate : campaignsToUpdate)
				{
					final Campaign updatedCampaign = campaignUpdate.update(campaignToUpdate);
					if (Objects.equals(updatedCampaign, campaignToUpdate))
					{
						syncResults.add(RemoteToLocalSyncResult.noChanges(updatedCampaign));
					}
					else
					{
						syncResults.add(RemoteToLocalSyncResult.obtainedOtherRemoteData(updatedCampaign));
					}
				}
			}
		}

		remoteId2campaignsNotYetFound.entrySet()
				.stream()
				.flatMap(e -> e.getValue().stream())
				.map(p -> p.toBuilder().remoteId(null).build())
				.map(RemoteToLocalSyncResult::deletedOnRemotePlatform)
				.forEach(syncResults::add);

		return syncResults.build();
	}

	@Override
	public ImmutableList<RemoteToLocalSyncResult> syncContactPersonsRemoteToLocal(
			@NonNull final Campaign campaign,
			@NonNull final List<ContactPerson> contactPersons)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final List<ContactPerson> personsWithEmailOrRemoteId = filterForPersonsWithEmailOrRemoteId(contactPersons, syncResults);
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

		final ImmutableList<ContactPersonRemoteUpdate> contactPersonUpdates = streamAllReceivers(campaign)
				.map(Receiver::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		for (final ContactPersonRemoteUpdate contactPersonUpdate : contactPersonUpdates)
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
				syncResults.add(RemoteToLocalSyncResult.obtainedNewDataRecord(contactPersonUpdate.toContactPerson(platformId)));
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

	private static List<ContactPerson> filterForPersonsWithEmailOrRemoteId(
			@NonNull final List<ContactPerson> contactPersons,
			@NonNull final Builder<RemoteToLocalSyncResult> syncResultsToAddErrorsTo)
	{
		final Map<Boolean, List<ContactPerson>> personsWithAndWithoutEmailOrRemoteId = contactPersons
				.stream()
				.collect(Collectors.partitioningBy(ContactPerson::hasEmailAddress));

		personsWithAndWithoutEmailOrRemoteId.get(false)
				.stream()
				.map(contactPerson -> RemoteToLocalSyncResult.error(contactPerson, "contact person has no email address"))
				.forEach(syncResultsToAddErrorsTo::add);

		return personsWithAndWithoutEmailOrRemoteId.get(true);
	}

	private static ImmutableList<LocalToRemoteSyncResult> createErrorResults(
			@NonNull final ImmutableListMultimap<String, ContactPerson> email2contactPersons,
			@NonNull final InvalidEmail invalidEmailInfo)
	{
		final String errorMsg = invalidEmailInfo.getErrorMessage();
		final String invalidAddress = invalidEmailInfo.getEmail();

		return email2contactPersons
				.get(invalidAddress)
				.stream()
				.map(p -> LocalToRemoteSyncResult.error(p, errorMsg))
				.collect(ImmutableList.toImmutableList());
	}

	private static Optional<InvalidEmail> createInvalidEmailInfo(final String singleResult)
	{
		final Pattern regExpInvalidAddress = Pattern.compile(".*invalid address *'(.*)'.*");
		final Pattern regExpDuplicateAddress = Pattern.compile(".*duplicate address *'(.*).*'");
		final Optional<InvalidEmail> invalidEmailIfno = createInvalidEmailInfo(regExpInvalidAddress, singleResult);
		if (invalidEmailIfno.isPresent())
		{
			return invalidEmailIfno;
		}

		return createInvalidEmailInfo(regExpDuplicateAddress, singleResult);
	}

	private static Optional<InvalidEmail> createInvalidEmailInfo(final Pattern regExpInvalidAddress, final String reponseString)
	{
		final Matcher invalidAddressMatcher = regExpInvalidAddress.matcher(reponseString);
		if (invalidAddressMatcher.matches())
		{
			final String errorMsg = invalidAddressMatcher.group();
			final String invalidAddress = invalidAddressMatcher.group(1);
			return Optional.of(new InvalidEmail(invalidAddress, errorMsg));
		}
		return Optional.empty();
	}

	@Value
	private static class InvalidEmail
	{
		String email;
		String errorMessage;
	}

	private LocalToRemoteSyncResult createOrUpdateGroup(@NonNull final Campaign campaign)
	{
		if (Check.isEmpty(campaign.getRemoteId()))
		{
			final Group createdGroup = createGroup(campaign);
			final Campaign campaignWithRemoteId = campaign
					.toBuilder()
					.remoteId(Integer.toString(createdGroup.getId()))
					.build();
			return LocalToRemoteSyncResult.inserted(campaignWithRemoteId);
		}

		updateGroup(campaign);
		return LocalToRemoteSyncResult.updated(campaign);
	}

	@Override
	public void sendEmailActivationForm(
			@NonNull final String formId,
			@NonNull final String email)
	{
		final String url = "/forms/" + formId + "/send/activate";
		final SendEmailActivationFormRequest body = SendEmailActivationFormRequest.builder()
				.email(email)
				.doidata(SendEmailActivationFormRequest.DoubleOptInData.builder()
						.user_ip("1.2.3.4")
						.referer("metasfresh")
						.user_agent("metasfresh")
						.build())
				.build();

		getLowLevelClient().post(body, SINGLE_HETEROGENOUS_TYPE, url);
		// returns the email as string in case of success
	}

}
