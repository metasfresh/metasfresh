package de.metas.marketing.gateway.cleverreach;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.logging.TableRecordMDC;
import de.metas.marketing.base.helper.RemoteToLocalCampaignSync;
import de.metas.marketing.base.helper.RemoteToLocalContactPersonSync;
import de.metas.marketing.base.helper.SyncUtil;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
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
import de.metas.util.collections.PagedIterator;
import de.metas.util.collections.PagedIterator.Page;
import de.metas.util.collections.PagedIterator.PageFetcher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.core.ParameterizedTypeReference;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

	@Override
	public List<LocalToRemoteSyncResult> syncContactPersonsLocalToRemote(
			@NonNull final Campaign campaign,
			@NonNull final List<ContactPerson> contactPersons)
	{
		final ImmutableList.Builder<LocalToRemoteSyncResult> syncResults = ImmutableList.builder();

		if (SyncUtil.filterForRecordsWithCorrectPlatformId(platformId, ImmutableList.of(campaign), syncResults).isEmpty())
		{
			return syncResults.build();
		}

		// make sure that we only send records that have a syntactically valid email and that have the correct platform-id
		final List<ContactPerson> personsWithEmail = SyncUtil.filterForRecordsWithCorrectPlatformId(
				platformId,
				SyncUtil.filterForPersonsWithEmail(
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

		final List<Campaign> campaignsWithCorrectPlatformId = SyncUtil.filterForRecordsWithCorrectPlatformId(platformId, campaigns, syncResults);

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
		final List<CampaignRemoteUpdate> campaignUpdates = retrieveAllGroups()
				.stream()
				.map(Group::toCampaignUpdate)
				.collect(ImmutableList.toImmutableList());

		final RemoteToLocalCampaignSync.Request request = RemoteToLocalCampaignSync.Request.builder()
				.platformId(platformId)
				.orgId(cleverReachConfig.getOrgId())
				.existingCampaigns(campaigns)
				.remoteCampaigns(campaignUpdates)
				.build();

		return RemoteToLocalCampaignSync.syncRemoteCampaigns(request);
	}

	@Override
	public ImmutableList<RemoteToLocalSyncResult> syncContactPersonsRemoteToLocal(
			@NonNull final Campaign campaign,
			@NonNull final List<ContactPerson> contactPersons)
	{
		final ImmutableList<ContactPersonRemoteUpdate> contactPersonUpdates = streamAllReceivers(campaign)
				.map(Receiver::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		final RemoteToLocalContactPersonSync.Request request = RemoteToLocalContactPersonSync.Request.builder()
				.platformId(platformId)
				.orgId(cleverReachConfig.getOrgId())
				.existingContactPersons(contactPersons)
				.remoteContactPersons(contactPersonUpdates)
				.build();

		return RemoteToLocalContactPersonSync.syncRemoteContacts(request);
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
