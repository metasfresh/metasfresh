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

import de.metas.marketing.base.sync.CampaignServiceSync;
import de.metas.marketing.base.sync.ContactPersonServiceSync;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.SyncDirection;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class CampaignSyncService
{
	private final CampaignServiceSync campaignServiceSync;
	private final ContactPersonServiceSync contactPersonServiceSync;

	public CampaignSyncService(
			@NonNull final CampaignServiceSync campaignServiceSync,
			@NonNull final ContactPersonServiceSync contactPersonServiceSync)
	{
		this.campaignServiceSync = campaignServiceSync;
		this.contactPersonServiceSync = contactPersonServiceSync;
	}

	public void syncCampaigns(
			@NonNull final PlatformId platformId,
			@NonNull final SyncDirection syncDirection)
	{
		if (SyncDirection.LOCAL_TO_REMOTE.equals(syncDirection))
		{
			campaignServiceSync.syncLocalToRemote(platformId);
		}
		else if (SyncDirection.REMOTE_TO_LOCAL.equals(syncDirection))
		{
			campaignServiceSync.syncRemoteToLocal(platformId);
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
		final Campaign campaign = campaignServiceSync.syncCampaignLocalToRemoteIfRemoteIdMissing(campaignId);

		if (SyncDirection.LOCAL_TO_REMOTE.equals(syncDirection))
		{
			contactPersonServiceSync.syncLocalToRemote(campaign);
		}
		else if (SyncDirection.REMOTE_TO_LOCAL.equals(syncDirection))
		{
			contactPersonServiceSync.syncRemoteToLocal(campaign);
		}
		else
		{
			throw new AdempiereException("Invalid sync direction: " + syncDirection);
		}
	}
}
