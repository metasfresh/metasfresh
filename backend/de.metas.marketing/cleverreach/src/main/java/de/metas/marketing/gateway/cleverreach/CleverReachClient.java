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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.email.EmailValidator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

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

	public LocalToRemoteSyncResult deleteCampaign(@NonNull final Campaign campaign)
	{
		final String remoteId = Check.assumeNotEmpty(
				campaign.getRemoteId(),
				"The given campaign to be deleted has a remoteId; campaign={}", campaign);

		final String url = String.format("/groups.json/%s", remoteId);
		getLowLevelClient().delete(url);

		return LocalToRemoteSyncResult.deleted(campaign);
	}

	public CampaignRemoteUpdate retrieveCampaign(@NonNull final String groupId)
	{
		final String url = String.format("/groups.json/%s", groupId);
		final Group group = getLowLevelClient().get(SINGLE_GROUP_TYPE, url);
		return group.toCampaignUpdate();
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

	private List<Receiver> retrieveReceivers(@NonNull final String remoteGroupId, @NonNull final CleverReachPageDescriptor pageDescriptor)
	{
		final String urlPathAndParams = "/groups.json/{group_id}/receivers?pagesize={pagesize}&page={page}";

		return getLowLevelClient()
				.get(LIST_OF_RECEIVERS_TYPE, urlPathAndParams, remoteGroupId, pageDescriptor.getPageSize(), pageDescriptor.getPage());
	}

	public ContactPersonRemoteUpdate retrieveReceiver(@NonNull final String groupId, @NonNull final String receiverId)
	{
		final String url = String.format("/groups.json/%s/receivers/%s", groupId, receiverId);
		final Receiver receiver = getLowLevelClient().get(SINGLE_RECEIVER_TYPE, url);
		return receiver.toContactPersonUpdate();
	}

	@NonNull
	@Override
	public CampaignConfig getCampaignConfig()
	{
		return cleverReachConfig;
	}

	@Nullable
	@Override
	public PageDescriptor getCampaignPageDescriptor()
	{
		return null;
	}

	@Override
	public PageDescriptor getContactPersonPageDescriptor()
	{
		return CleverReachPageDescriptor.createNew(CLEVER_REACH_API_PAGE_SIZE);
	}

	@Override
	public Optional<CampaignRemoteUpdate> getCampaignById(@NonNull final String remoteId)
	{
		try
		{
			return Optional.of(retrieveCampaign(remoteId));
		}
		catch (final Exception ex)
		{
			if (ex.getMessage().contains(String.valueOf(HttpStatus.NOT_FOUND.value())))
			{
				return Optional.empty();
			}
			else
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}
		}
	}

	@NonNull
	@Override
	public CampaignToUpsertPage getCampaignToUpsertPage(@Nullable final PageDescriptor pageDescriptor)
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
	public Optional<ContactPersonRemoteUpdate> getContactById(@NonNull final Campaign campaign, @NonNull final String remoteId)
	{
		try
		{
			return Optional.of(retrieveReceiver(campaign.getRemoteId(), remoteId));
		}
		catch (final Exception ex)
		{
			if (ex.getMessage().contains(String.valueOf(HttpStatus.NOT_FOUND.value())))
			{
				return Optional.empty();
			}
			else
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}
		}
	}

	@Override
	public ContactPersonToUpsertPage getContactPersonToUpsertPage(@NonNull final Campaign campaign, @NonNull final PageDescriptor pageDescriptor)
	{
		final CleverReachPageDescriptor cleverReachPageDescriptor = CleverReachPageDescriptor.cast(pageDescriptor);

		final List<ContactPersonRemoteUpdate> remoteContacts = retrieveReceivers(campaign.getRemoteId(), cleverReachPageDescriptor)
				.stream()
				.map(Receiver::toContactPersonUpdate)
				.collect(ImmutableList.toImmutableList());

		if (remoteContacts.isEmpty())
		{
			return ContactPersonToUpsertPage.builder()
					.remoteContacts(remoteContacts)
					.build();
		}

		return ContactPersonToUpsertPage.builder()
				.remoteContacts(remoteContacts)
				.next(cleverReachPageDescriptor.createNext())
				.build();
	}

	@Override
	public LocalToRemoteSyncResult upsertContact(@NonNull final Campaign campaign, @NonNull final ContactPerson contactPerson)
	{
		if (!EmailValidator.isValid(contactPerson.getEmailAddressStringOrNull()))
		{
			return LocalToRemoteSyncResult.error(contactPerson, "Contact person has no (valid) email address");
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

			return LocalToRemoteSyncResult.upserted(updatedPerson);
		}
		catch (final Exception ex)
		{
			return LocalToRemoteSyncResult.error(contactPerson, "Unexpected result='" + ex.getMessage() + "'");
		}
	}

	@NonNull
	@Override
	public LocalToRemoteSyncResult upsertCampaign(final Campaign campaign)
	{
		return createOrUpdateGroup(campaign);
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
