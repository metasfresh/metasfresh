/*
 * #%L
 * marketing-activecampaign
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

package de.metas.marketing.gateway.activecampaign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.TableRecordMDC;
import de.metas.marketing.base.helper.RemoteToLocalCampaignSync;
import de.metas.marketing.base.helper.RemoteToLocalContactPersonSync;
import de.metas.marketing.base.helper.SyncUtil;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.marketing.gateway.activecampaign.restapi.RestService;
import de.metas.marketing.gateway.activecampaign.restapi.model.AddContactToList;
import de.metas.marketing.gateway.activecampaign.restapi.model.AddContactToListWrapper;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignList;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignLists;
import de.metas.marketing.gateway.activecampaign.restapi.model.Contact;
import de.metas.marketing.gateway.activecampaign.restapi.model.ContactList;
import de.metas.marketing.gateway.activecampaign.restapi.model.CreateCampaignList;
import de.metas.marketing.gateway.activecampaign.restapi.model.CreateContact;
import de.metas.marketing.gateway.activecampaign.restapi.model.Subscription;
import de.metas.marketing.gateway.activecampaign.restapi.request.ApiRequest;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.MDC;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_VERSION;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.QueryParam.LIST_ID;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ResourcePath;

public class ActiveCampaignClient implements PlatformClient
{
	@NonNull
	private final RestService restService;
	@NonNull
	private final ActiveCampaignConfig campaignConfig;

	public ActiveCampaignClient(
			@NonNull final RestService restService,
			@NonNull final ActiveCampaignConfig campaignConfig)
	{
		this.restService = restService;
		this.campaignConfig = campaignConfig;
	}

	@NonNull
	@Override
	public List<LocalToRemoteSyncResult> syncCampaignsLocalToRemote(@NonNull final List<Campaign> campaigns)
	{
		return campaigns.stream()
				//dev-note: keep only campaigns without remoteId, to avoid duplicating campaigns on remote. the api doesn't support update only create
				.filter(campaign -> Check.isBlank(campaign.getRemoteId()))
				.map(this::createCampaignList)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	@Override
	public List<LocalToRemoteSyncResult> syncContactPersonsLocalToRemote(@NonNull final Campaign campaign, @NonNull final List<ContactPerson> contactPersons)
	{
		final ImmutableList.Builder<LocalToRemoteSyncResult> syncResults = ImmutableList.builder();
		// make sure that we only send records that have a syntactically valid email and that have the correct platform-id
		final List<ContactPerson> personsWithEmail = SyncUtil.filterForRecordsWithCorrectPlatformId(
				campaignConfig.getPlatformId(),
				SyncUtil.filterForPersonsWithEmail(
						contactPersons,
						syncResults),
				syncResults);

		personsWithEmail.stream()
				.map(contactPerson -> createCampaignContactAndAddToList(campaign, contactPerson))
				.forEach(syncResults::add);

		return syncResults.build();
	}

	@NonNull
	@Override
	public List<RemoteToLocalSyncResult> syncContactPersonsRemoteToLocal(@NonNull final Campaign campaign, @NonNull final List<ContactPerson> existingContactPersons)
	{
		final List<ContactPersonRemoteUpdate> remoteContactPersons = retrieveContactsFromRemote(campaign.getRemoteId())
				.getContacts()
				.stream()
				.map(ActiveCampaignClient::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		final RemoteToLocalContactPersonSync.Request request = RemoteToLocalContactPersonSync.Request.builder()
				.platformId(campaignConfig.getPlatformId())
				.orgId(campaignConfig.getOrgId())
				.existingContactPersons(existingContactPersons)
				.remoteContactPersons(remoteContactPersons)
				.build();

		return RemoteToLocalContactPersonSync.syncRemoteContacts(request);
	}

	@NonNull
	@Override
	public List<RemoteToLocalSyncResult> syncCampaignsRemoteToLocal(final List<Campaign> existingCampaigns)
	{
		final List<CampaignRemoteUpdate> remoteCampaigns = retrieveCampaignListsFromRemote()
				.getLists()
				.stream()
				.map(ActiveCampaignClient::toCampaignUpdate)
				.collect(ImmutableList.toImmutableList());

		final RemoteToLocalCampaignSync.Request request = RemoteToLocalCampaignSync.Request.builder()
				.platformId(campaignConfig.getPlatformId())
				.orgId(campaignConfig.getOrgId())
				.existingCampaigns(existingCampaigns)
				.remoteCampaigns(remoteCampaigns)
				.build();

		return RemoteToLocalCampaignSync.syncRemoteCampaigns(request);
	}

	@NonNull
	private LocalToRemoteSyncResult createCampaignList(@NonNull final Campaign campaign)
	{
		try (final MDC.MDCCloseable campaignMDC = TableRecordMDC.putTableRecordReference(I_MKTG_Campaign.Table_Name, campaign.getCampaignId()))
		{
			final CampaignList campaignWithRemoteId = createCampaignOnRemote(campaign).getList();

			return LocalToRemoteSyncResult.inserted(campaign.toBuilder()
															.remoteId(campaignWithRemoteId.getId())
															.build());
		}
		catch (final Exception e)
		{
			return LocalToRemoteSyncResult.error(campaign, e.getMessage());
		}
	}

	@NonNull
	private LocalToRemoteSyncResult createCampaignContactAndAddToList(
			@NonNull final Campaign campaign,
			@NonNull final ContactPerson contactToCreate)
	{
		try
		{
			final Contact contactWithRemoteId = createCampaignContactOnRemote(contactToCreate).getContact();

			addContactToList(campaign.getRemoteId(), contactWithRemoteId.getId());

			return LocalToRemoteSyncResult.upserted(contactToCreate.toBuilder()
															.remoteId(contactWithRemoteId.getId())
															.build());
		}
		catch (final Exception e)
		{
			return LocalToRemoteSyncResult.error(contactToCreate, e.getMessage());
		}
	}

	@NonNull
	private CreateContact createCampaignContactOnRemote(@NonNull final ContactPerson contactPerson)
	{
		try
		{
			final CreateContact createContactRequest = CreateContact.builder()
					.contact(ActiveCampaignClient.toContact(contactPerson))
					.build();

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(createContactRequest);

			final ApiRequest request = ApiRequest.builder()
					.baseURL(campaignConfig.getBaseUrl())
					.apiKey(campaignConfig.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ResourcePath.CONTACT.getValue(),
													ResourcePath.SYNC.getValue()))
					.requestBody(requestBody)
					.build();

			return restService.performPost(request, CreateContact.class).getBody();
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private ContactList addContactToList(@NonNull final String campaignRemoteId, @NonNull final String contactRemoteId) throws JsonProcessingException
	{
		final AddContactToList addContactToList = AddContactToList.builder()
				.list(campaignRemoteId)
				.contact(contactRemoteId)
				.status(Subscription.Subscribed.getCode())
				.build();

		final AddContactToListWrapper addContactToListWrapper = AddContactToListWrapper.builder()
				.contactList(addContactToList)
				.build();

		final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.writeValueAsString(addContactToListWrapper);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.CONTACT_LISTS.getValue()))
				.requestBody(requestBody)
				.build();

		return restService.performPost(request, ContactList.class).getBody();
	}

	@NonNull
	private CreateCampaignList createCampaignOnRemote(@NonNull final Campaign campaign) throws JsonProcessingException
	{
		final CreateCampaignList createCampaignListRequest = CreateCampaignList.builder()
				.list(ActiveCampaignClient.toCampaignItem(campaign, campaignConfig))
				.build();

		final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.writeValueAsString(createCampaignListRequest);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.LISTS.getValue()))
				.requestBody(requestBody)
				.build();

		return restService.performPost(request, CreateCampaignList.class).getBody();
	}

	@NonNull
	private CampaignLists retrieveCampaignListsFromRemote()
	{
		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.LISTS.getValue()))
				.build();

		return restService.performGet(request, CampaignLists.class).getBody();
	}

	@NonNull
	private ContactList retrieveContactsFromRemote(@NonNull final String campaignListRemoteId)
	{
		final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add(LIST_ID.getValue(), campaignListRemoteId);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.CONTACTS.getValue()))
				.queryParameters(queryParams)
				.build();

		return restService.performGet(request, ContactList.class).getBody();
	}

	@NonNull
	private static CampaignRemoteUpdate toCampaignUpdate(@NonNull final CampaignList campaignItem)
	{
		return CampaignRemoteUpdate.builder()
				.remoteId(String.valueOf(campaignItem.getId()))
				.name(campaignItem.getName())
				.build();
	}

	@NonNull
	private static CampaignList toCampaignItem(@NonNull final Campaign campaign, @NonNull final ActiveCampaignConfig config)
	{
		return CampaignList.builder()
				.name(campaign.getName())
				.stringid(campaign.getName())
				.sender_url(config.getBaseUrl())
				.sender_reminder("Synced from metasfresh.")
				.build();
	}

	@NonNull
	private static ContactPersonRemoteUpdate toContactPersonUpdate(@NonNull final Contact contact)
	{
		return ContactPersonRemoteUpdate.builder()
				.remoteId(String.valueOf(contact.getId()))
				//dev-note: DeactivatedOnRemotePlatform is always NO as ActiveCampaigns doesn't have an inactivate user feature.
				.address(EmailAddress.of(contact.getEmail(), DeactivatedOnRemotePlatform.NO))
				.build();
	}

	@NonNull
	private static Contact toContact(@NonNull final ContactPerson contactPerson)
	{
		return Contact.builder()
				.email(contactPerson.getEmailAddressStringOrNull())
				.build();
	}
}
