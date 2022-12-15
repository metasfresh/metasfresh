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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.TableRecordMDC;
import de.metas.marketing.base.RemoteToLocalCampaignSync;
import de.metas.marketing.base.RemoteToLocalContactPersonSync;
import de.metas.marketing.base.SyncUtil;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignList;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignLists;
import de.metas.marketing.gateway.activecampaign.restapi.model.Contact;
import de.metas.marketing.gateway.activecampaign.restapi.model.ContactList;
import de.metas.marketing.gateway.activecampaign.restapi.model.Subscription;
import de.metas.marketing.gateway.activecampaign.restapi.RestService;
import de.metas.marketing.gateway.activecampaign.restapi.request.ApiRequest;
import de.metas.marketing.gateway.activecampaign.restapi.request.CreateCampaignListResponse;
import de.metas.marketing.gateway.activecampaign.restapi.request.CreateContactResponse;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.MDC;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_VERSION;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.Endpoint;

public class ActiveCampaignClient implements PlatformClient
{
	private final RestService restService;

	private final ActiveCampaignConfig campaignConfig;
	private final PlatformId platformId;

	public ActiveCampaignClient(
			@NonNull final RestService restService,
			@NonNull final ActiveCampaignConfig campaignConfig)
	{
		this.restService = restService;
		this.campaignConfig = campaignConfig;
		this.platformId = campaignConfig.getPlatformId();
	}

	@NonNull
	@Override
	public List<LocalToRemoteSyncResult> syncCampaignsLocalToRemote(@NonNull final List<Campaign> campaigns)
	{
		return campaigns.stream()
				//dev-note: filter campaigns without remoteId, to avoid duplicating campaigns on remote. the api doesn't support update only create
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
				platformId,
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
				.map(Contact::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		final RemoteToLocalContactPersonSync.Request request = RemoteToLocalContactPersonSync.Request.builder()
				.campaignConfig(campaignConfig)
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
				.map(CampaignList::toCampaignUpdate)
				.collect(ImmutableList.toImmutableList());

		final RemoteToLocalCampaignSync.Request request = RemoteToLocalCampaignSync.Request.builder()
				.campaignConfig(campaignConfig)
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
			final CampaignList campaignWithRemoteId = createCampaignListApi(campaign).getList();

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
			@NonNull final ContactPerson contact)
	{
		try
		{
			final String response = createCampaignContact(contact);

			final JsonNode rootJsonNode = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(response, JsonNode.class);

			return handleContactUpsertErrorResponse(contact, rootJsonNode)
					.orElseGet(() -> handleContactUpsertResponse(campaign, contact, rootJsonNode));
		}
		catch (final Exception e)
		{
			return LocalToRemoteSyncResult.error(contact, "Unexpected result='" + e.getMessage() + "'");
		}
	}

	@NonNull
	private String createCampaignContact(@NonNull final ContactPerson contactPerson)
	{
		try
		{
			final Map<String, Object> body = ImmutableMap.of("contact", Contact.of(contactPerson));

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(body);

			final ApiRequest request = ApiRequest.builder()
					.baseURL(campaignConfig.getBaseUrl())
					.apiKey(campaignConfig.getApiKey())
					.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
													ACTIVE_CAMPAIGN_API_VERSION,
													Endpoint.CONTACT.getValue(),
													Endpoint.SYNC.getValue()))
					.requestBody(requestBody)
					.build();

			return restService.performPost(request, String.class).getBody();
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private Optional<LocalToRemoteSyncResult> handleContactUpsertErrorResponse(@NonNull final ContactPerson contact, @NonNull final JsonNode rootJsonNode)
	{
		final JsonNode errorsJsonNode = rootJsonNode.get("errors");

		if (errorsJsonNode == null)
		{
			return Optional.empty();
		}

		final StringBuilder errorsCollector = new StringBuilder();

		for (final JsonNode errorNode : errorsJsonNode)
		{
			final String errorMsg = errorNode.get("title").asText();

			if (Check.isBlank(errorMsg))
			{
				continue;
			}

			errorsCollector.append(errorMsg);
		}

		return Optional.of(LocalToRemoteSyncResult.error(contact, errorsCollector.toString()));
	}

	@NonNull
	private LocalToRemoteSyncResult handleContactUpsertResponse(
			@NonNull final Campaign campaign,
			@NonNull final ContactPerson contact,
			@NonNull final JsonNode rootJsonNode)
	{
		try
		{
			final Contact contactWithRemoteId = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
					.treeToValue(rootJsonNode, CreateContactResponse.class).getContact();

			addContactToList(campaign.getRemoteId(), contactWithRemoteId.getId());

			return LocalToRemoteSyncResult.upserted(contact.toBuilder()
															.remoteId(contactWithRemoteId.getId())
															.build());
		}
		catch (final Exception e)
		{
			return LocalToRemoteSyncResult.error(contact, e.getMessage());
		}
	}

	@NonNull
	private ContactList addContactToList(@NonNull final String campaignRemoteId, @NonNull final String contactRemoteId) throws JsonProcessingException
	{
		final Map<String, Object> config = ImmutableMap.of("list", campaignRemoteId,
														   "contact", contactRemoteId,
														   "status", Subscription.Subscribed.getCode());

		final Map<String, Object> body = ImmutableMap.of("contactList", config);

		final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.writeValueAsString(body);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												Endpoint.CONTACT_LISTS.getValue()))
				.requestBody(requestBody)
				.build();

		return restService.performPost(request, ContactList.class).getBody();
	}

	@NonNull
	private CreateCampaignListResponse createCampaignListApi(@NonNull final Campaign campaign) throws JsonProcessingException
	{
		final Map<String, Object> body = ImmutableMap.of("list", CampaignList.of(campaign, campaignConfig));

		final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.writeValueAsString(body);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												Endpoint.LISTS.getValue()))
				.requestBody(requestBody)
				.build();

		return restService.performPost(request, CreateCampaignListResponse.class).getBody();
	}

	@NonNull
	private CampaignLists retrieveCampaignListsFromRemote()
	{
		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												Endpoint.LISTS.getValue()))
				.build();

		return restService.performGet(request, CampaignLists.class).getBody();
	}

	@NonNull
	private ContactList retrieveContactsFromRemote(@NonNull final String campaignListRemoteId)
	{
		final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("listid", campaignListRemoteId);

		final ApiRequest request = ApiRequest.builder()
				.baseURL(campaignConfig.getBaseUrl())
				.apiKey(campaignConfig.getApiKey())
				.pathVariables(ImmutableList.of(ACTIVE_CAMPAIGN_API,
												ACTIVE_CAMPAIGN_API_VERSION,
												Endpoint.CONTACTS.getValue()))
				.queryParameters(queryParams)
				.build();

		return restService.performGet(request, ContactList.class).getBody();
	}
}
