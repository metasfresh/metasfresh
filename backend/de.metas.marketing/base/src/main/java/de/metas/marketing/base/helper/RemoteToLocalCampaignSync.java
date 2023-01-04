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

package de.metas.marketing.base.helper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import de.metas.marketing.base.model.DataRecord;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class RemoteToLocalCampaignSync
{
	@NonNull
	public List<RemoteToLocalSyncResult> syncRemoteCampaigns(@NonNull final Request request)
	{
		final ImmutableList.Builder<RemoteToLocalSyncResult> syncResults = ImmutableList.builder();

		final ImmutableListMultimap<String, Campaign> remoteId2campaigns = Multimaps.index(request.getExistingCampaigns(), Campaign::getRemoteId);

		final HashMap<String, Collection<Campaign>> remoteId2campaignsNotYetFound = new HashMap<>(remoteId2campaigns.asMap());

		for (final CampaignRemoteUpdate campaignUpdate : request.getRemoteCampaigns())
		{
			remoteId2campaignsNotYetFound.remove(campaignUpdate.getRemoteId());

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

		remoteId2campaignsNotYetFound.entrySet()
				.stream()
				.flatMap(e -> e.getValue().stream())
				.map(p -> p.toBuilder().remoteId(null).build())
				.map(RemoteToLocalSyncResult::deletedOnRemotePlatform)
				.forEach(syncResults::add);

		return syncResults.build();
	}

	@Value
	@Builder
	public class Request
	{
		@NonNull
		PlatformId platformId;

		@NonNull
		OrgId orgId;

		@NonNull
		List<Campaign> existingCampaigns;

		@NonNull
		List<CampaignRemoteUpdate> remoteCampaigns;
	}
}
