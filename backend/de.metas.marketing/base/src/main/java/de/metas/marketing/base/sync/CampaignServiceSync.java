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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.logging.TableRecordMDC;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.CampaignToUpsertPage;
import de.metas.marketing.base.model.DataRecord;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PageDescriptor;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CampaignServiceSync
{
	@NonNull
	private final CampaignService campaignService;
	@NonNull
	private final PlatformClientService platformClientService;

	public CampaignServiceSync(
			@NonNull final CampaignService campaignService,
			@NonNull final PlatformClientService platformClientService)
	{
		this.campaignService = campaignService;
		this.platformClientService = platformClientService;
	}

	public void syncRemoteToLocal(@NonNull final PlatformId platformId)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(platformId);

		handleCampaignsNoLongerAvailableOnRemote(platformClient);
		processCampaignToUpsertPage(platformClient);

	}

	public void syncLocalToRemote(@NonNull final PlatformId platformId)
	{
		final PlatformClient platformClient = platformClientService.createPlatformClient(platformId);

		final Iterator<I_MKTG_Campaign> campaignIterator = campaignService.iterateCampaigns(platformClient.getCampaignConfig().getPlatformId());

		while (campaignIterator.hasNext())
		{
			final Campaign campaign = CampaignRepository.fromRecord(campaignIterator.next());

			try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_MKTG_Campaign.Table_Name, campaign.getCampaignId()))
			{
				final LocalToRemoteSyncResult syncResult = platformClient.upsertCampaign(campaign);

				if (syncResult.getLocalToRemoteStatus().equals(LocalToRemoteSyncResult.LocalToRemoteStatus.SKIPPED))
				{
					continue;
				}

				campaignService.saveSyncResult(syncResult);
			}
		}
	}

	@NonNull
	public Campaign syncCampaignLocalToRemoteIfRemoteIdMissing(@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignService.getById(campaignId);

		if (campaign.getRemoteId() == null)
		{
			final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());
			final LocalToRemoteSyncResult syncResult = platformClient.upsertCampaign(campaign);
			return campaignService.saveSyncResult(syncResult);
		}

		return campaign;
	}

	private void handleCampaignsNoLongerAvailableOnRemote(@NonNull final PlatformClient platformClient)
	{
		final Iterator<I_MKTG_Campaign> campaignIterator = campaignService.iterateCampaignsWithRemoteId(platformClient.getCampaignConfig().getPlatformId());

		while (campaignIterator.hasNext())
		{
			final Campaign campaign = CampaignRepository.fromRecord(campaignIterator.next());

			if (Check.isBlank(campaign.getRemoteId()))
			{
				continue;
			}

			final Optional<CampaignRemoteUpdate> remoteCampaign = platformClient.getCampaignById(campaign.getRemoteId());

			if (!remoteCampaign.isPresent())
			{
				final RemoteToLocalSyncResult syncResult = RemoteToLocalSyncResult.deletedOnRemotePlatform(campaign.toBuilder().remoteId(null).build());
				campaignService.saveSyncResult(syncResult);
			}
		}
	}

	private void processCampaignToUpsertPage(@NonNull final PlatformClient platformClient)
	{
		PageDescriptor currentPageDescriptor = platformClient.getCampaignPageDescriptor();

		while (true)
		{
			final CampaignToUpsertPage campaignToUpsertPage = platformClient.getCampaignToUpsertPage(currentPageDescriptor);

			final Request request = Request.builder()
					.orgId(platformClient.getCampaignConfig().getOrgId())
					.platformId(platformClient.getCampaignConfig().getPlatformId())
					.remoteCampaigns(campaignToUpsertPage.getRemoteCampaigns())
					.build();

			final List<RemoteToLocalSyncResult> syncResults = processRemoteCampaigns(request);

			campaignService.saveSyncResults(syncResults);

			if (campaignToUpsertPage.getNext() == null)
			{
				break;
			}

			currentPageDescriptor = campaignToUpsertPage.getNext();
		}
	}

	@NonNull
	private List<RemoteToLocalSyncResult> processRemoteCampaigns(@NonNull final Request request)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final Set<String> remoteIds = request.getRemoteCampaigns()
				.stream()
				.map(CampaignRemoteUpdate::getRemoteId)
				.collect(ImmutableSet.toImmutableSet());

		final List<Campaign> existingCampaigns = campaignService.retrieveByPlatformAndRemoteIds(request.getPlatformId(), remoteIds);

		final ImmutableListMultimap<String, Campaign> remoteId2campaigns = Multimaps.index(existingCampaigns, Campaign::getRemoteId);

		for (final CampaignRemoteUpdate campaignUpdate : request.getRemoteCampaigns())
		{
			final ImmutableList<Campaign> campaignsToUpdate = remoteId2campaigns.get(campaignUpdate.getRemoteId());
			if (campaignsToUpdate.isEmpty())
			{
				final DataRecord newCampaign = campaignUpdate.toCampaign(request.getPlatformId(), request.getOrgId());
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
