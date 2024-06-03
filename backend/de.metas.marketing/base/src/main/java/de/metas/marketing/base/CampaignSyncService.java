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

package de.metas.marketing.base;

import com.google.common.collect.ImmutableList;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.SyncDirection;
import de.metas.marketing.base.model.SyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignSyncService
{
	private final CampaignService campaignService;
	private final ContactPersonService contactPersonService;
	private final PlatformClientService platformClientService;

	public CampaignSyncService(
			@NonNull final CampaignService campaignService,
			@NonNull final ContactPersonService contactPersonService,
			@NonNull final PlatformClientService platformClientService)
	{
		this.campaignService = campaignService;
		this.contactPersonService = contactPersonService;
		this.platformClientService = platformClientService;
	}

	public Campaign syncCampaignLocalToRemote(@NonNull final Campaign campaign)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());
		final List<LocalToRemoteSyncResult> syncResults = platformClient.syncCampaignsLocalToRemote(ImmutableList.of(campaign));
		final LocalToRemoteSyncResult syncResult = CollectionUtils.singleElement(syncResults);
		return campaignService.saveSyncResult(syncResult);
	}

	private Campaign syncCampaignLocalToRemoteIfRemoteIdMissing(@NonNull final Campaign campaign)
	{
		return campaign.getRemoteId() == null ? syncCampaignLocalToRemote(campaign) : campaign;
	}

	public void syncCampaigns(
			@NonNull final PlatformId platformId,
			@NonNull final SyncDirection syncDirection)
	{

		final PlatformClient platformClient = platformClientService.createPlatformClient(platformId);

		final List<Campaign> locallyExistingCampaigns = campaignService.getByPlatformId(platformId);
		final List<? extends SyncResult> syncResults;
		if (SyncDirection.LOCAL_TO_REMOTE.equals(syncDirection))
		{
			syncResults = platformClient.syncCampaignsLocalToRemote(locallyExistingCampaigns);
		}
		else if (SyncDirection.REMOTE_TO_LOCAL.equals(syncDirection))
		{
			syncResults = platformClient.syncCampaignsRemoteToLocal(locallyExistingCampaigns);
		}
		else
		{
			throw new AdempiereException("Invalid sync direction: " + syncDirection);
		}

		campaignService.saveSyncResults(syncResults);
	}

	public void syncContacts(
			@NonNull final CampaignId campaignId,
			@NonNull final SyncDirection syncDirection)
	{
		final List<ContactPerson> contactsToSync = contactPersonService.getByCampaignId(campaignId);
		syncContacts(campaignId, contactsToSync, syncDirection);
	}

	public void syncContacts(
			@NonNull final CampaignId campaignId,
			@NonNull final List<ContactPerson> contactsToSync,
			@NonNull SyncDirection syncDirection)
	{
		final Campaign campaign = syncCampaignLocalToRemoteIfRemoteIdMissing(campaignService.getById(campaignId));

		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());

		final List<? extends SyncResult> syncResults = syncDirection.map(new SyncDirection.CaseMapper<List<? extends SyncResult>>()
		{
			@Override
			public List<? extends SyncResult> localToRemote() {return platformClient.syncContactPersonsLocalToRemote(campaign, contactsToSync);}

			@Override
			public List<? extends SyncResult> remoteToLocal() {return platformClient.syncContactPersonsRemoteToLocal(campaign, contactsToSync);}
		});

		final List<ContactPerson> savedContacts = contactPersonService.saveSyncResults(syncResults);
		campaignService.addContactPersonsToCampaign(savedContacts, campaignId);
	}
}
