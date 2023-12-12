/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.marketing.base.sync;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.CampaignToUpsertPage;
import de.metas.marketing.base.model.DataRecord;
import de.metas.marketing.base.model.PageDescriptor;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Service
public class CampaignSyncService
{
	@NonNull
	private final CampaignService campaignService;
	@NonNull
	private final PlatformClientService platformClientService;

	public CampaignSyncService(
			@NonNull final CampaignService campaignService,
			@NonNull final PlatformClientService platformClientService)
	{
		this.campaignService = campaignService;
		this.platformClientService = platformClientService;
	}

	public void syncRemoteToLocal(@NonNull final PlatformId platformId)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(platformId);

		final Set<String> processedRemoteIds = upsertCampaigns(platformClient);
		handleCampaignsNoLongerAvailableOnRemote(platformId, processedRemoteIds);
	}

	public void syncLocalToRemote(@NonNull final PlatformId platformId)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(platformId);

		campaignService.streamCampaigns(platformId)
				.map(platformClient::upsertCampaign)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(campaignService::saveSyncResult);
	}

	@NonNull
	public Campaign syncCampaignLocalToRemoteIfRemoteIdMissing(@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignService.getById(campaignId);

		if (campaign.getRemoteId() != null)
		{
			return campaign;
		}

		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());

		return platformClient.upsertCampaign(campaign)
				.map(campaignService::saveSyncResult)
				.orElseThrow(() -> new AdempiereException("Could not obtain remoteId for campaign.")
						.appendParametersToMessage()
						.setParameter("CampaignId", campaign.getCampaignId()));
	}

	private void handleCampaignsNoLongerAvailableOnRemote(@NonNull final PlatformId platformId, @NonNull final Set<String> allActiveCampaignRemoteIds)
	{
		campaignService.streamActivelySyncedWithRemoteId(platformId)
				.filter(campaign -> Check.isNotBlank(campaign.getRemoteId()))
				.filter(campaignWithRemoteId -> !allActiveCampaignRemoteIds.contains(campaignWithRemoteId.getRemoteId()))
				.map(campaignDeletedOnRemote -> campaignDeletedOnRemote.toBuilder().remoteId(null).build())
				.map(RemoteToLocalSyncResult::deletedOnRemotePlatform)
				.forEach(campaignService::saveSyncResult);
	}

	@NonNull
	private Set<String> upsertCampaigns(@NonNull final PlatformClient platformClient)
	{
		final ImmutableSet.Builder<String> remoteIds = ImmutableSet.builder();

		PageDescriptor currentPageDescriptor = null;
		boolean moreCampaigns = true;

		while (moreCampaigns)
		{
			final CampaignToUpsertPage campaignToUpsertPage = platformClient.getCampaignToUpsertPage(currentPageDescriptor);

			final Request request = Request.builder()
					.orgId(platformClient.getCampaignConfig().getOrgId())
					.platformId(platformClient.getCampaignConfig().getPlatformId())
					.remoteCampaigns(campaignToUpsertPage.getRemoteCampaigns())
					.build();

			final List<RemoteToLocalSyncResult> syncResults = processRemoteCampaigns(request);

			campaignService.saveSyncResults(syncResults);

			campaignToUpsertPage.getRemoteCampaigns()
					.stream()
					.map(CampaignRemoteUpdate::getRemoteId)
					.forEach(remoteIds::add);

			currentPageDescriptor = campaignToUpsertPage.getNext();
			moreCampaigns = currentPageDescriptor != null;
		}

		return remoteIds.build();
	}

	@NonNull
	private List<RemoteToLocalSyncResult> processRemoteCampaigns(@NonNull final Request request)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final Set<String> remoteIds = request.getRemoteCampaigns()
				.stream()
				.map(CampaignRemoteUpdate::getRemoteId)
				.collect(ImmutableSet.toImmutableSet());

		final Map<String, Campaign> remoteId2ExistingCampaigns = campaignService.retrieveByPlatformAndRemoteIds(request.getPlatformId(), remoteIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(Campaign::getRemoteId, Function.identity()));

		for (final CampaignRemoteUpdate campaignUpdate : request.getRemoteCampaigns())
		{
			final Campaign campaignToUpdate = remoteId2ExistingCampaigns.get(campaignUpdate.getRemoteId());
			if (campaignToUpdate == null)
			{
				final DataRecord newCampaign = campaignUpdate.toCampaign(request.getPlatformId(), request.getOrgId());
				syncResults.add(RemoteToLocalSyncResult.obtainedNewDataRecord(newCampaign));
			}
			else
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

		return syncResults.build();
	}

	@Value
	@Builder
	private static class Request
	{
		@NonNull
		PlatformId platformId;

		@NonNull
		OrgId orgId;

		@NonNull
		List<CampaignRemoteUpdate> remoteCampaigns;
	}
}
