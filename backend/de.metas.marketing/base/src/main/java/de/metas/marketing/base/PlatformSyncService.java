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

import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.SyncDirection;
import de.metas.marketing.base.sync.CampaignSyncService;
import de.metas.marketing.base.sync.ContactPersonSyncService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class PlatformSyncService
{
	private final CampaignSyncService campaignSyncService;
	private final ContactPersonSyncService contactPersonSyncService;

	public PlatformSyncService(
			@NonNull final CampaignSyncService campaignSyncService,
			@NonNull final ContactPersonSyncService contactPersonSyncService)
	{
		this.campaignSyncService = campaignSyncService;
		this.contactPersonSyncService = contactPersonSyncService;
	}

	public void syncCampaigns(
			@NonNull final PlatformId platformId,
			@NonNull final SyncDirection syncDirection)
	{
		if (SyncDirection.LOCAL_TO_REMOTE.equals(syncDirection))
		{
			campaignSyncService.syncLocalToRemote(platformId);
		}
		else if (SyncDirection.REMOTE_TO_LOCAL.equals(syncDirection))
		{
			campaignSyncService.syncRemoteToLocal(platformId);
		}
		else
		{
			throw new AdempiereException("Invalid sync direction: " + syncDirection);
		}
	}

	public void syncContacts(
			@NonNull final CampaignId campaignId,
			@NonNull final SyncDirection syncDirection)
	{
		final Campaign campaign = campaignSyncService.syncCampaignLocalToRemoteIfRemoteIdMissing(campaignId);

		if (SyncDirection.LOCAL_TO_REMOTE.equals(syncDirection))
		{
			contactPersonSyncService.syncLocalToRemote(campaign);
		}
		else if (SyncDirection.REMOTE_TO_LOCAL.equals(syncDirection))
		{
			contactPersonSyncService.syncRemoteToLocal(campaign);
		}
		else
		{
			throw new AdempiereException("Invalid sync direction: " + syncDirection);
		}
	}
}
