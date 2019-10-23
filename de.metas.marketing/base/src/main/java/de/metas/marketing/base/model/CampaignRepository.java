package de.metas.marketing.base.model;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

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

@Repository
public class CampaignRepository
{
	public Campaign getById(@NonNull final CampaignId campaignId)
	{
		final I_MKTG_Campaign campaignRecord = load(campaignId.getRepoId(), I_MKTG_Campaign.class);
		return createFromModel(campaignRecord);
	}

	public List<Campaign> getByPlatformId(@NonNull final PlatformId platform)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign.COLUMN_MKTG_Platform_ID, platform.getRepoId())
				.orderBy()
				.addColumn(I_MKTG_Campaign.COLUMN_MKTG_Campaign_ID).endOrderBy()
				.create()
				.stream()
				.map(CampaignRepository::createFromModel)
				.collect(ImmutableList.toImmutableList());
	}

	private static Campaign createFromModel(@NonNull final I_MKTG_Campaign campaignRecord)
	{
		return new Campaign(
				campaignRecord.getName(),
				campaignRecord.getRemoteRecordId(),
				PlatformId.ofRepoId(campaignRecord.getMKTG_Platform_ID()),
				CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()));
	}

	public void addContactPersonToCampaign(
			@NonNull final ContactPersonId contactPersonId,
			@NonNull final CampaignId campaignId)
	{
		final int contactPersonRepoId = contactPersonId.getRepoId();
		final int campaignRepoId = campaignId.getRepoId();

		final boolean associationAlreadyExists = Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignRepoId)
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, contactPersonRepoId)
				.create()
				.match();
		if (associationAlreadyExists)
		{
			return;
		}

		final I_MKTG_Campaign_ContactPerson newAssociation = newInstance(I_MKTG_Campaign_ContactPerson.class);
		newAssociation.setMKTG_Campaign_ID(campaignRepoId);
		newAssociation.setMKTG_ContactPerson_ID(contactPersonRepoId);
		saveRecord(newAssociation);
	}

	public Campaign saveCampaign(@NonNull final Campaign campaign)
	{
		final I_MKTG_Campaign campaignRecord = createOrUpdateRecordDontSave(campaign);
		saveRecord(campaignRecord);

		return campaign.toBuilder()
				.campaignId(CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()))
				.build();
	}

	public void saveCampaignSyncResult(@NonNull final SyncResult syncResult)
	{
		final Campaign campaign = Campaign.cast(syncResult.getSynchedDataRecord());
		final I_MKTG_Campaign campaignRecord = createOrUpdateRecordDontSave(campaign);

		if (syncResult instanceof LocalToRemoteSyncResult)
		{
			campaignRecord.setLastSyncOfLocalToRemote(SystemTime.asTimestamp());

			final LocalToRemoteSyncResult localToRemoteSyncResult = (LocalToRemoteSyncResult)syncResult;
			campaignRecord.setLastSyncStatus(localToRemoteSyncResult.getLocalToRemoteStatus().toString());
			campaignRecord.setLastSyncDetailMessage(localToRemoteSyncResult.getErrorMessage());
		}
		else if (syncResult instanceof RemoteToLocalSyncResult)
		{
			campaignRecord.setLastSyncOfRemoteToLocal(SystemTime.asTimestamp());

			final RemoteToLocalSyncResult remoteToLocalSyncResult = (RemoteToLocalSyncResult)syncResult;
			campaignRecord.setLastSyncStatus(remoteToLocalSyncResult.getRemoteToLocalStatus().toString());
			campaignRecord.setLastSyncDetailMessage(remoteToLocalSyncResult.getErrorMessage());
		}
		else
		{
			Check.fail("The given syncResult has an unexpected type of {}; syncResult={}", syncResult.getClass(), syncResult);
		}
		saveRecord(campaignRecord);
	}

	private I_MKTG_Campaign createOrUpdateRecordDontSave(@NonNull final Campaign campaign)
	{
		final I_MKTG_Campaign campaignRecord;
		if (campaign.getCampaignId() != null)
		{
			final int repoId = campaign.getCampaignId().getRepoId();
			campaignRecord = load(repoId, I_MKTG_Campaign.class);
		}
		else
		{
			campaignRecord = newInstance(I_MKTG_Campaign.class);
		}

		campaignRecord.setName(campaign.getName());
		campaignRecord.setRemoteRecordId(campaign.getRemoteId());
		campaignRecord.setMKTG_Platform_ID(campaign.getPlatformId().getRepoId());
		return campaignRecord;
	}

	public Optional<CampaignId> getDefaultNewsletterCampaignId(final int orgId)
	{
		final int defaultCampaignId = Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign.COLUMN_IsDefaultNewsletter, true)
				.addInArrayFilter(I_MKTG_Campaign.COLUMNNAME_AD_Org_ID, orgId, 0)
				.orderByDescending(I_MKTG_Campaign.COLUMNNAME_AD_Org_ID)
				.create()
				.firstId();

		return defaultCampaignId <= 0 ? Optional.empty() : Optional.of(CampaignId.ofRepoId(defaultCampaignId));
	}

	public void removeContactPersonFromCampaign(
			@NonNull final ContactPerson contactPerson,
			@NonNull final Campaign campaign)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaign.getCampaignId().getRepoId())
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, contactPerson.getContactPersonId().getRepoId())
				.create()
				.delete();
	}

	public void removeAllContactPersonsFromCampaign(final CampaignId campaignId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create()
				.delete();
	}

}
