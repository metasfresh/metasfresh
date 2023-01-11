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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.TableRecordMDC;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignConfig;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.CampaignToUpsertPage;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.ContactPersonToUpsertPage;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PageDescriptor;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.marketing.gateway.activecampaign.restapi.RestService;
import de.metas.marketing.gateway.activecampaign.restapi.model.AddContactToList;
import de.metas.marketing.gateway.activecampaign.restapi.model.AddContactToListWrapper;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignList;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignLists;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignWrapper;
import de.metas.marketing.gateway.activecampaign.restapi.model.Contact;
import de.metas.marketing.gateway.activecampaign.restapi.model.ContactList;
import de.metas.marketing.gateway.activecampaign.restapi.model.ContactWrapper;
import de.metas.marketing.gateway.activecampaign.restapi.model.Subscription;
import de.metas.marketing.gateway.activecampaign.restapi.request.ApiRequest;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.email.EmailValidator;
import org.slf4j.MDC;
import org.springframework.util.LinkedMultiValueMap;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_VERSION;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.QueryParam.LIMIT;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.QueryParam.LIST_ID;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.QueryParam.OFFSET;

public class ActiveCampaignClient implements PlatformClient
{
	@NonNull
	private final RestService restService;
	@NonNull
	private final ActiveCampaignConfig activeCampaignConfig;

	public ActiveCampaignClient(
			@NonNull final RestService restService,
			@NonNull final ActiveCampaignConfig activeCampaignConfig)
	{
		this.restService = restService;
		this.activeCampaignConfig = activeCampaignConfig;
	}

	@NonNull
	@Override
	public CampaignConfig getCampaignConfig()
	{
		return activeCampaignConfig;
	}

	@Override
	public CampaignToUpsertPage getCampaignToUpsertPage(@Nullable final PageDescriptor pageDescriptor)
	{
		final ActiveCampaignPageDescriptor activeCampaignPageDescriptor = Optional.ofNullable(pageDescriptor)
				.map(ActiveCampaignPageDescriptor::cast)
				.orElseGet(() -> ActiveCampaignPageDescriptor.ofLimit(ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT));

		final List<CampaignRemoteUpdate> remoteCampaignsToUpdate = retrieveCampaignListsFromRemote(activeCampaignPageDescriptor)
				.stream()
				.map(ActiveCampaignClient::toCampaignUpdate)
				.collect(ImmutableList.toImmutableList());

		final int pageSize = remoteCampaignsToUpdate.size();

		final ActiveCampaignPageDescriptor nextPage = pageSize >= activeCampaignPageDescriptor.getLimit()
				? activeCampaignPageDescriptor.createNext()
				: null;

		return CampaignToUpsertPage.builder()
				.remoteCampaigns(remoteCampaignsToUpdate)
				.next(nextPage)
				.build();
	}

	@NonNull
	@Override
	public Optional<LocalToRemoteSyncResult> upsertCampaign(@NonNull final Campaign campaign)
	{
		//dev-note: keep only campaigns without remoteId, to avoid duplicating campaigns on remote. the api doesn't support update only create
		if (Check.isNotBlank(campaign.getRemoteId()))
		{
			return Optional.empty();
		}

		return Optional.of(createCampaignList(campaign));
	}

	@NonNull
	@Override
	public ContactPersonToUpsertPage getContactPersonToUpsertPage(@NonNull final Campaign campaign, @Nullable final PageDescriptor pageDescriptor)
	{
		final ActiveCampaignPageDescriptor activeCampaignPageDescriptor = Optional.ofNullable(pageDescriptor)
				.map(ActiveCampaignPageDescriptor::cast)
				.orElseGet(() -> ActiveCampaignPageDescriptor.ofLimit(ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT));

		final List<ContactPersonRemoteUpdate> remoteContacts = retrieveContactsFromRemote(campaign.getRemoteId(), activeCampaignPageDescriptor)
				.getContacts()
				.stream()
				.map(ActiveCampaignClient::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		final int pageSize = remoteContacts.size();

		final ActiveCampaignPageDescriptor nextPage = pageSize >= activeCampaignPageDescriptor.getLimit()
				? activeCampaignPageDescriptor.createNext()
				: null;

		return ContactPersonToUpsertPage.builder()
				.remoteContacts(remoteContacts)
				.next(nextPage)
				.build();
	}

	@NonNull
	@Override
	public Optional<LocalToRemoteSyncResult> upsertContact(@NonNull final Campaign campaign, @NonNull final ContactPerson contactPerson)
	{
		return Optional.of(createCampaignContactAndAddToList(campaign, contactPerson));
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
			@NonNull final ContactPerson contactToUpsert)
	{
		if (!EmailValidator.isValid(contactToUpsert.getEmailAddressStringOrNull()))
		{
			return LocalToRemoteSyncResult.error(contactToUpsert, "Contact person has no (valid) email address");
		}

		try
		{
			final Contact contactWithRemoteId = upsertContactOnRemote(contactToUpsert).getContact();

			final ContactPerson contactUpserted = contactToUpsert.toBuilder().remoteId(contactWithRemoteId.getId()).build();

			addContactToList(campaign.getRemoteId(), contactWithRemoteId.getId());

			if (Check.isBlank(contactToUpsert.getRemoteId()))
			{
				return LocalToRemoteSyncResult.upserted(contactUpserted);
			}

			return LocalToRemoteSyncResult.updated(contactUpserted);
		}
		catch (final Exception e)
		{
			return LocalToRemoteSyncResult.error(contactToUpsert, e.getMessage());
		}
	}

	@NonNull
	private ContactWrapper updateCampaignContactOnRemote(@NonNull final ContactPerson contactPerson)
	{
		try
		{
			final ApiRequest request = ApiRequestProvider.updateCampaignContactOnRemoteRequest(contactPerson, activeCampaignConfig);
			return restService.performPut(request, ContactWrapper.class).getBody();
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private ContactWrapper syncCampaignContactOnRemote(@NonNull final ContactPerson contactPerson)
	{
		try
		{
			final ApiRequest request = ApiRequestProvider.syncCampaignContactOnRemoteRequest(contactPerson, activeCampaignConfig);
			return restService.performPost(request, ContactWrapper.class).getBody();
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@VisibleForTesting
	@NonNull
	public ContactWrapper upsertContactOnRemote(@NonNull final ContactPerson contactPerson)
	{
		if (Check.isBlank(contactPerson.getRemoteId()))
		{
			return syncCampaignContactOnRemote(contactPerson);
		}

		return updateCampaignContactOnRemote(contactPerson);
	}

	@VisibleForTesting
	@NonNull
	public ContactList addContactToList(@NonNull final String campaignRemoteId, @NonNull final String contactRemoteId) throws JsonProcessingException
	{
		final ApiRequest request = ApiRequestProvider.addContactToListRequest(campaignRemoteId, contactRemoteId, activeCampaignConfig);
		return restService.performPost(request, ContactList.class).getBody();
	}

	@VisibleForTesting
	@NonNull
	public CampaignWrapper createCampaignOnRemote(@NonNull final Campaign campaign) throws JsonProcessingException
	{
		final ApiRequest request = ApiRequestProvider.createCampaignOnRemoteRequest(campaign, activeCampaignConfig);
		return restService.performPost(request, CampaignWrapper.class).getBody();
	}

	@VisibleForTesting
	@NonNull
	public List<CampaignList> retrieveCampaignListsFromRemote(@NonNull final ActiveCampaignPageDescriptor pageDescriptor)
	{
		final ApiRequest request = ApiRequestProvider.retrieveCampaignListsFromRemoteRequest(activeCampaignConfig, pageDescriptor);
		return restService.performGet(request, CampaignLists.class).getBody().getLists();
	}

	@VisibleForTesting
	@NonNull
	public ContactList retrieveContactsFromRemote(
			@NonNull final String campaignListRemoteId,
			@NonNull final ActiveCampaignPageDescriptor pageDescriptor)
	{
		final ApiRequest request = ApiRequestProvider.retrieveContactsFromRemoteRequest(activeCampaignConfig, pageDescriptor, campaignListRemoteId);
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
	private static ContactPersonRemoteUpdate toContactPersonUpdate(@NonNull final Contact contact)
	{
		return ContactPersonRemoteUpdate.builder()
				.remoteId(String.valueOf(contact.getId()))
				//dev-note: DeactivatedOnRemotePlatform is always NO as ActiveCampaigns doesn't have an inactivate user feature.
				.address(EmailAddress.of(contact.getEmail(), DeactivatedOnRemotePlatform.NO))
				.build();
	}

	private static class ApiRequestProvider
	{
		@NonNull
		private static ApiRequest createCampaignOnRemoteRequest(@NonNull final Campaign campaign, @NonNull final ActiveCampaignConfig config) throws JsonProcessingException
		{
			final CampaignWrapper createCampaignListRequest = CampaignWrapper.builder()
					.list(ApiRequestProvider.toCampaignItem(campaign, config))
					.build();

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(createCampaignListRequest);

			return ApiRequest.builder()
					.baseURL(config.getBaseUrl())
					.apiKey(config.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ActiveCampaignConstants.ResourcePath.LISTS.getValue()))
					.requestBody(requestBody)
					.build();
		}

		@NonNull
		private static ApiRequest retrieveCampaignListsFromRemoteRequest(
				@NonNull final ActiveCampaignConfig config,
				@NonNull final ActiveCampaignPageDescriptor pageDescriptor)
		{
			return ApiRequest.builder()
					.baseURL(config.getBaseUrl())
					.apiKey(config.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ActiveCampaignConstants.ResourcePath.LISTS.getValue()))
					.queryParameters(initQueryParams(pageDescriptor))
					.build();
		}

		@NonNull
		private static ApiRequest updateCampaignContactOnRemoteRequest(
				@NonNull final ContactPerson contactPerson,
				@NonNull final ActiveCampaignConfig config) throws JsonProcessingException
		{
			Check.assumeNotNull(contactPerson.getRemoteId(), "RemoteId cannot be null at this stage for contactId: {}", contactPerson.getContactPersonId());

			final ContactWrapper createContactRequest = ContactWrapper.builder()
					.contact(ApiRequestProvider.toContact(contactPerson))
					.build();

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(createContactRequest);

			return ApiRequest.builder()
					.baseURL(config.getBaseUrl())
					.apiKey(config.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ActiveCampaignConstants.ResourcePath.CONTACTS.getValue(),
													contactPerson.getRemoteId()))
					.requestBody(requestBody)
					.build();
		}

		@NonNull
		private static ApiRequest syncCampaignContactOnRemoteRequest(
				@NonNull final ContactPerson contactPerson,
				@NonNull final ActiveCampaignConfig config) throws JsonProcessingException
		{
			final ContactWrapper createContactRequest = ContactWrapper.builder()
					.contact(ApiRequestProvider.toContact(contactPerson))
					.build();

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(createContactRequest);

			return ApiRequest.builder()
					.baseURL(config.getBaseUrl())
					.apiKey(config.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ActiveCampaignConstants.ResourcePath.CONTACT.getValue(),
													ActiveCampaignConstants.ResourcePath.SYNC.getValue()))
					.requestBody(requestBody)
					.build();
		}

		@NonNull
		private static ApiRequest addContactToListRequest(
				@NonNull final String campaignRemoteId,
				@NonNull final String contactRemoteId,
				@NonNull final ActiveCampaignConfig config) throws JsonProcessingException
		{
			final AddContactToList addContactToList = AddContactToList.builder()
					.list(campaignRemoteId)
					.contact(contactRemoteId)
					.status(Subscription.Subscribed.getCode())
					.build();

			final AddContactToListWrapper addContactToListWrapper = AddContactToListWrapper.builder()
					.contactList(addContactToList)
					.build();

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(addContactToListWrapper);

			return ApiRequest.builder()
					.baseURL(config.getBaseUrl())
					.apiKey(config.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ActiveCampaignConstants.ResourcePath.CONTACT_LISTS.getValue()))
					.requestBody(requestBody)
					.build();
		}

		@NonNull
		private static ApiRequest retrieveContactsFromRemoteRequest(
				@NonNull final ActiveCampaignConfig config,
				@NonNull final ActiveCampaignPageDescriptor pageDescriptor,
				@NonNull final String campaignListRemoteId)
		{
			final LinkedMultiValueMap<String, String> queryParams = ApiRequestProvider.initQueryParams(pageDescriptor);
			queryParams.add(LIST_ID.getValue(), campaignListRemoteId);

			return ApiRequest.builder()
					.baseURL(config.getBaseUrl())
					.apiKey(config.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ActiveCampaignConstants.ResourcePath.CONTACTS.getValue()))
					.queryParameters(queryParams)
					.build();

		}

		@NonNull
		private static Contact toContact(@NonNull final ContactPerson contactPerson)
		{
			return Contact.builder()
					.email(contactPerson.getEmailAddressStringOrNull())
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
		private static LinkedMultiValueMap<String, String> initQueryParams(@NonNull final ActiveCampaignPageDescriptor pageDescriptor)
		{
			final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

			if (pageDescriptor.getLimit() > 0)
			{
				queryParams.add(LIMIT.getValue(), String.valueOf(pageDescriptor.getLimit()));
			}

			if (pageDescriptor.getOffset() > 0)
			{
				queryParams.add(OFFSET.getValue(), String.valueOf(pageDescriptor.getOffset()));
			}

			return queryParams;
		}
	}
}
