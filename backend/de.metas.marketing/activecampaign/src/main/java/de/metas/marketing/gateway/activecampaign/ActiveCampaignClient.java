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
import org.slf4j.MDC;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_VERSION;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.QueryParam.LIMIT;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.QueryParam.LIST_ID;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.QueryParam.OFFSET;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ResourcePath;

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

	@NonNull
	@Override
	public PageDescriptor getCampaignPageDescriptor()
	{
		return ActiveCampaignPageDescriptor.createNew(ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT);
	}

	@NonNull
	@Override
	public PageDescriptor getContactPersonPageDescriptor()
	{
		return ActiveCampaignPageDescriptor.createNew(ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT);
	}

	@Override
	@NonNull
	public Optional<CampaignRemoteUpdate> getCampaignById(@NonNull final String remoteId)
	{
		try
		{
			return Optional.of(retrieveCampaignListFromRemote(remoteId))
					.map(CampaignWrapper::getList)
					.map(ActiveCampaignClient::toCampaignUpdate);
		}
		catch (final HttpClientErrorException.NotFound notFoundEx)
		{
			return Optional.empty();
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	@Override
	public CampaignToUpsertPage getCampaignToUpsertPage(@NonNull final PageDescriptor pageDescriptor)
	{
		final ActiveCampaignPageDescriptor activeCampaignPageDescriptor = ActiveCampaignPageDescriptor.cast(pageDescriptor);

		final List<CampaignRemoteUpdate> remoteCampaignsToUpdate = retrieveCampaignListsFromRemote(activeCampaignPageDescriptor)
				.stream()
				.map(ActiveCampaignClient::toCampaignUpdate)
				.collect(ImmutableList.toImmutableList());

		final int pageSize = remoteCampaignsToUpdate.size();

		if (pageSize < activeCampaignPageDescriptor.getLimit())
		{
			return CampaignToUpsertPage.builder()
					.remoteCampaigns(remoteCampaignsToUpdate)
					.build();
		}

		return CampaignToUpsertPage.builder()
				.remoteCampaigns(remoteCampaignsToUpdate)
				.next(activeCampaignPageDescriptor.createNext(pageSize))
				.build();
	}

	@NonNull
	@Override
	public LocalToRemoteSyncResult upsertCampaign(@NonNull final Campaign campaign)
	{
		//dev-note: keep only campaigns without remoteId, to avoid duplicating campaigns on remote. the api doesn't support update only create
		if (Check.isNotBlank(campaign.getRemoteId()))
		{
			return LocalToRemoteSyncResult.skipped(campaign);
		}

		return createCampaignList(campaign);
	}

	@NonNull
	@Override
	public Optional<ContactPersonRemoteUpdate> getContactById(@NonNull final Campaign campaign_NOT_USED, @NonNull final String remoteId)
	{
		try
		{
			return Optional.of(retrieveContactFromRemote(remoteId))
					.map(ContactWrapper::getContact)
					.map(ActiveCampaignClient::toContactPersonUpdate);
		}
		catch (final HttpClientErrorException.NotFound notFoundEx)
		{
			return Optional.empty();
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	@NonNull
	@Override
	public ContactPersonToUpsertPage getContactPersonToUpsertPage(@NonNull final Campaign campaign, @NonNull final PageDescriptor pageDescriptor)
	{
		final ActiveCampaignPageDescriptor activeCampaignPageDescriptor = ActiveCampaignPageDescriptor.cast(pageDescriptor);

		final List<ContactPersonRemoteUpdate> remoteContacts = retrieveContactsFromRemote(campaign.getRemoteId(), activeCampaignPageDescriptor)
				.getContacts()
				.stream()
				.map(ActiveCampaignClient::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		if (remoteContacts.size() < activeCampaignPageDescriptor.getLimit())
		{
			return ContactPersonToUpsertPage.builder()
					.remoteContacts(remoteContacts)
					.build();
		}

		final int pageSize = remoteContacts.size();
		final boolean hasMoreContacts = pageSize <= activeCampaignPageDescriptor.getLimit();

		if (!hasMoreContacts)
		{
			return ContactPersonToUpsertPage.builder()
					.remoteContacts(remoteContacts)
					.build();
		}

		return ContactPersonToUpsertPage.builder()
				.remoteContacts(remoteContacts)
				.next(activeCampaignPageDescriptor.createNext(pageSize))
				.build();
	}

	@NonNull
	@Override
	public LocalToRemoteSyncResult upsertContact(@NonNull final Campaign campaign, @NonNull final ContactPerson contactPerson)
	{
		return createCampaignContactAndAddToList(campaign, contactPerson);
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
	private ContactWrapper createCampaignContactOnRemote(@NonNull final ContactPerson contactPerson)
	{
		try
		{
			final ContactWrapper createContactRequest = ContactWrapper.builder()
					.contact(ActiveCampaignClient.toContact(contactPerson))
					.build();

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(createContactRequest);

			final ApiRequest request = ApiRequest.builder()
					.baseURL(activeCampaignConfig.getBaseUrl())
					.apiKey(activeCampaignConfig.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													ResourcePath.CONTACT.getValue(),
													ResourcePath.SYNC.getValue()))
					.requestBody(requestBody)
					.build();

			return restService.performPost(request, ContactWrapper.class).getBody();
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
				.baseURL(activeCampaignConfig.getBaseUrl())
				.apiKey(activeCampaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.CONTACT_LISTS.getValue()))
				.requestBody(requestBody)
				.build();

		return restService.performPost(request, ContactList.class).getBody();
	}

	@NonNull
	private CampaignWrapper createCampaignOnRemote(@NonNull final Campaign campaign) throws JsonProcessingException
	{
		final CampaignWrapper createCampaignListRequest = CampaignWrapper.builder()
				.list(ActiveCampaignClient.toCampaignItem(campaign, activeCampaignConfig))
				.build();

		final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.writeValueAsString(createCampaignListRequest);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(activeCampaignConfig.getBaseUrl())
				.apiKey(activeCampaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.LISTS.getValue()))
				.requestBody(requestBody)
				.build();

		return restService.performPost(request, CampaignWrapper.class).getBody();
	}

	@NonNull
	private List<CampaignList> retrieveCampaignListsFromRemote(@NonNull final ActiveCampaignPageDescriptor pageDescriptor)
	{
		final ApiRequest request = ApiRequest.builder()
				.baseURL(activeCampaignConfig.getBaseUrl())
				.apiKey(activeCampaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.LISTS.getValue()))
				.queryParameters(buildQueryParams(pageDescriptor))
				.build();

		return restService.performGet(request, CampaignLists.class).getBody().getLists();
	}

	@NonNull
	private CampaignWrapper retrieveCampaignListFromRemote(@NonNull final String remoteId)
	{
		final ApiRequest request = ApiRequest.builder()
				.baseURL(activeCampaignConfig.getBaseUrl())
				.apiKey(activeCampaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.LISTS.getValue(),
												remoteId))
				.build();

		return restService.performGet(request, CampaignWrapper.class).getBody();
	}

	@NonNull
	private ContactList retrieveContactsFromRemote(
			@NonNull final String campaignListRemoteId,
			@NonNull final ActiveCampaignPageDescriptor pageDescriptor)
	{
		final LinkedMultiValueMap<String, String> queryParams = Optional.ofNullable(pageDescriptor)
				.map(ActiveCampaignClient::buildQueryParams)
				.orElse(new LinkedMultiValueMap<>());

		queryParams.add(LIST_ID.getValue(), campaignListRemoteId);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(activeCampaignConfig.getBaseUrl())
				.apiKey(activeCampaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.CONTACTS.getValue()))
				.queryParameters(queryParams)
				.build();

		return restService.performGet(request, ContactList.class).getBody();
	}

	@NonNull
	private ContactWrapper retrieveContactFromRemote(@NonNull final String contactRemoteId)
	{
		final ApiRequest request = ApiRequest.builder()
				.baseURL(activeCampaignConfig.getBaseUrl())
				.apiKey(activeCampaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												ResourcePath.CONTACTS.getValue(),
												contactRemoteId))
				.build();

		return restService.performGet(request, ContactWrapper.class).getBody();
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

	@NonNull
	private static LinkedMultiValueMap<String, String> buildQueryParams(@NonNull final ActiveCampaignPageDescriptor pageDescriptor)
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
