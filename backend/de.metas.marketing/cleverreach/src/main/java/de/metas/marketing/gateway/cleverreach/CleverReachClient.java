package de.metas.marketing.gateway.cleverreach;

import com.google.common.collect.ImmutableList;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignConfig;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.CampaignToUpsertPage;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.ContactPersonToUpsertPage;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PageDescriptor;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.marketing.gateway.cleverreach.restapi.models.CreateGroupRequest;
import de.metas.marketing.gateway.cleverreach.restapi.models.Group;
import de.metas.marketing.gateway.cleverreach.restapi.models.Receiver;
import de.metas.marketing.gateway.cleverreach.restapi.models.ReceiverUpsert;
import de.metas.marketing.gateway.cleverreach.restapi.models.SendEmailActivationFormRequest;
import de.metas.marketing.gateway.cleverreach.restapi.models.UpdateGroupRequest;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.email.EmailValidator;
import org.springframework.core.ParameterizedTypeReference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

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

	// @formatter:off
	private static final ParameterizedTypeReference<Receiver> SINGLE_RECEIVER_TYPE = new ParameterizedTypeReference<Receiver>() {}; // @formatter:on

	private final static int CLEVER_REACH_API_PAGE_SIZE = 1000;

	private final CleverReachConfig cleverReachConfig;

	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private final CleverReachLowLevelClient lowLevelClient = CleverReachLowLevelClient.createAndLogin(cleverReachConfig);

	public CleverReachClient(@NonNull final CleverReachConfig cleverReachConfig)
	{
		this.cleverReachConfig = cleverReachConfig;
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

	private List<Group> retrieveAllGroups()
	{
		final String url = "/groups.json";
		return getLowLevelClient().get(LIST_OF_GROUPS_TYPE, url);
	}

	private List<Receiver> retrieveReceivers(@NonNull final String remoteGroupId, @NonNull final CleverReachPageDescriptor pageDescriptor)
	{
		final String urlPathAndParams = String.format("/groups.json/%s/receivers?pagesize=%d&page=%d", remoteGroupId, pageDescriptor.getPageSize(), pageDescriptor.getPage());

		return getLowLevelClient()
				.get(LIST_OF_RECEIVERS_TYPE, urlPathAndParams);
	}

	@NonNull
	@Override
	public CampaignConfig getCampaignConfig()
	{
		return cleverReachConfig;
	}

	@NonNull
	@Override
	public CampaignToUpsertPage getCampaignToUpsertPage(@Nullable final PageDescriptor ignored)
	{
		final List<CampaignRemoteUpdate> remoteCampaignsToUpdate = retrieveAllGroups()
				.stream()
				.map(Group::toCampaignUpdate)
				.collect(ImmutableList.toImmutableList());

		return CampaignToUpsertPage.builder()
				.remoteCampaigns(remoteCampaignsToUpdate)
				.build();
	}

	@Override
	public ContactPersonToUpsertPage getContactPersonToUpsertPage(@NonNull final Campaign campaign, @Nullable final PageDescriptor pageDescriptor)
	{
		final CleverReachPageDescriptor cleverReachPageDescriptor = Optional.ofNullable(pageDescriptor)
				.map(CleverReachPageDescriptor::cast)
				.orElseGet(() -> CleverReachPageDescriptor.ofPageSize(CLEVER_REACH_API_PAGE_SIZE));

		final List<ContactPersonRemoteUpdate> remoteContacts = retrieveReceivers(campaign.getRemoteId(), cleverReachPageDescriptor)
				.stream()
				.map(Receiver::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		final CleverReachPageDescriptor nextPage = remoteContacts.size() >= cleverReachPageDescriptor.getPageSize()
				? cleverReachPageDescriptor.createNext()
				: null;

		return ContactPersonToUpsertPage.builder()
				.remoteContacts(remoteContacts)
				.next(nextPage)
				.build();
	}

	@Override
	public Optional<LocalToRemoteSyncResult> upsertContact(@NonNull final Campaign campaign, @NonNull final ContactPerson contactPerson)
	{
		if (!EmailValidator.isValid(contactPerson.getEmailAddressStringOrNull()))
		{
			return Optional.of(LocalToRemoteSyncResult.error(contactPerson, "Contact person has no (valid) email address"));
		}

		final ReceiverUpsert receiverUpsert = ReceiverUpsert.of(contactPerson);

		final String groupRemoteId = assumeNotEmpty(campaign.getRemoteId(), "Then given campaign needs to have a RemoteId; campagin={}", campaign);
		final String insertUrl = String.format("/groups.json/%s/receivers/upsert", groupRemoteId);

		try
		{
			final Receiver receiver = getLowLevelClient()
					.post(receiverUpsert, SINGLE_RECEIVER_TYPE, insertUrl);

			final ContactPersonRemoteUpdate remoteContactUpsert = receiver.toContactPersonUpdate();

			final ContactPerson updatedPerson = contactPerson.toBuilder()
					.remoteId(remoteContactUpsert.getRemoteId())
					.build();

			return Optional.of(LocalToRemoteSyncResult.upserted(updatedPerson));
		}
		catch (final Exception ex)
		{
			return Optional.of(LocalToRemoteSyncResult.error(contactPerson, "Unexpected result='" + ex.getMessage() + "'"));
		}
	}

	@NonNull
	@Override
	public Optional<LocalToRemoteSyncResult> upsertCampaign(final Campaign campaign)
	{
		return Optional.of(createOrUpdateGroup(campaign));
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
