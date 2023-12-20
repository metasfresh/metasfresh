package de.metas.marketing.base.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<CampaignId, Campaign> cache = CCache.<CampaignId, Campaign>builder()
			.tableName(I_MKTG_Campaign.Table_Name)
			.build();

	public Campaign getById(@NonNull final CampaignId campaignId)
	{
		return cache.getOrLoad(campaignId, this::retrieveById);
	}

	@NonNull
	private Campaign retrieveById(final @NonNull CampaignId campaignId)
	{
		final I_MKTG_Campaign campaignRecord = retrieveRecordById(campaignId);
		return fromRecord(campaignRecord);
	}

	private I_MKTG_Campaign retrieveRecordById(final @NonNull CampaignId campaignId)
	{
		return load(campaignId.getRepoId(), I_MKTG_Campaign.class);
	}

	public List<Campaign> getByPlatformId(@NonNull final PlatformId platformId)
	{
		final ImmutableSet<CampaignId> campaignIds = queryBL.createQueryBuilder(I_MKTG_Campaign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign.COLUMN_MKTG_Platform_ID, platformId.getRepoId())
				.orderBy(I_MKTG_Campaign.COLUMN_MKTG_Campaign_ID)
				.create()
				.listIds(CampaignId::ofRepoId);

		final Collection<Campaign> campaigns = cache.getAllOrLoad(campaignIds, this::retrieveByIds);
		return ImmutableList.copyOf(campaigns);
	}

	private Map<CampaignId, Campaign> retrieveByIds(final Set<CampaignId> campaignIds)
	{
		final List<I_MKTG_Campaign> records = InterfaceWrapperHelper.loadByRepoIdAwares(campaignIds, I_MKTG_Campaign.class);
		return records.stream()
				.map(CampaignRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(Campaign::getCampaignId, campaign -> campaign));
	}

	public static Campaign fromRecord(@NonNull final I_MKTG_Campaign campaignRecord)
	{
		return Campaign.builder()
				.name(campaignRecord.getName())
				.remoteId(campaignRecord.getRemoteRecordId())
				.platformId(PlatformId.ofRepoId(campaignRecord.getMKTG_Platform_ID()))
				.campaignId(CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()))
				.orgId(OrgId.ofRepoId(campaignRecord.getAD_Org_ID()))
				.build();
	}

	public void addContactPersonsToCampaign(final Set<ContactPersonId> contactPersonIds, final CampaignId campaignId)
	{
		if (contactPersonIds.isEmpty())
		{
			return;
		}

		final Campaign campaign = getById(campaignId);

		final ImmutableSet<ContactPersonId> alreadyAddedContactPersonIds = queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.addInArrayFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, contactPersonIds)
				.create()
				.stream()
				.map(record -> ContactPersonId.ofRepoId(record.getMKTG_ContactPerson_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final Set<ContactPersonId> contactPersonIdsToAdd = Sets.difference(contactPersonIds, alreadyAddedContactPersonIds);
		for(final ContactPersonId contactPersonId : contactPersonIdsToAdd)
		{
			final I_MKTG_Campaign_ContactPerson newAssociation = newInstance(I_MKTG_Campaign_ContactPerson.class);
			newAssociation.setMKTG_Campaign_ID(campaign.getCampaignId().getRepoId());
			newAssociation.setMKTG_ContactPerson_ID(contactPersonId.getRepoId());
			newAssociation.setAD_Org_ID(campaign.getOrgId().getRepoId());
			saveRecord(newAssociation);
		}
	}

	public void addContactPersonToCampaign(
			@NonNull final ContactPersonId contactPersonId,
			@NonNull final CampaignId campaignId)
	{
		addContactPersonsToCampaign(ImmutableSet.of(contactPersonId), campaignId);
	}

	public Campaign saveCampaign(@NonNull final Campaign campaign)
	{
		final I_MKTG_Campaign campaignRecord = createOrUpdateRecordDontSave(campaign);
		saveRecord(campaignRecord);

		return campaign.toBuilder()
				.campaignId(CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()))
				.build();
	}

	public Campaign saveCampaignSyncResult(@NonNull final SyncResult syncResult)
	{
		final Campaign campaign = Campaign.cast(syncResult.getSynchedDataRecord());
		final I_MKTG_Campaign campaignRecord = createOrUpdateRecordDontSave(campaign);

		if (syncResult instanceof LocalToRemoteSyncResult)
		{
			campaignRecord.setLastSyncOfLocalToRemote(de.metas.common.util.time.SystemTime.asTimestamp());

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
			throw new AdempiereException("The given syncResult has an unexpected type of " + syncResult.getClass() + "; syncResult=" + syncResult);
		}
		saveRecord(campaignRecord);

		return fromRecord(campaignRecord);
	}

	private I_MKTG_Campaign createOrUpdateRecordDontSave(@NonNull final Campaign campaign)
	{
		final I_MKTG_Campaign campaignRecord;
		if (campaign.getCampaignId() != null)
		{
			campaignRecord = retrieveRecordById(campaign.getCampaignId());
		}
		else
		{
			campaignRecord = newInstance(I_MKTG_Campaign.class);
		}

		campaignRecord.setName(campaign.getName());
		campaignRecord.setRemoteRecordId(campaign.getRemoteId());
		campaignRecord.setMKTG_Platform_ID(campaign.getPlatformId().getRepoId());
		campaignRecord.setAD_Org_ID(campaign.getOrgId().getRepoId());

		return campaignRecord;
	}

	public Optional<CampaignId> getDefaultNewsletterCampaignId(final int orgId)
	{
		final CampaignId defaultCampaignId = queryBL.createQueryBuilder(I_MKTG_Campaign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign.COLUMN_IsDefaultNewsletter, true)
				.addInArrayFilter(I_MKTG_Campaign.COLUMNNAME_AD_Org_ID, orgId, 0)
				.orderByDescending(I_MKTG_Campaign.COLUMNNAME_AD_Org_ID)
				.create()
				.firstId(CampaignId::ofRepoIdOrNull);

		return Optional.ofNullable(defaultCampaignId);
	}

	public void removeContactPersonFromCampaign(
			@NonNull final ContactPerson contactPerson,
			@NonNull final Campaign campaign)
	{
		final ContactPersonId contactPersonId = Check.assumeNotNull(contactPerson.getContactPersonId(), "contact shall be saved: {}", contactPerson);

		queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaign.getCampaignId())
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, contactPersonId.getRepoId())
				.create()
				.delete();
	}

	public void removeAllContactPersonsFromCampaign(final CampaignId campaignId)
	{
		queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create()
				.delete();
	}

	@NonNull
	public Stream<Campaign> streamActiveCampaigns(@NonNull final PlatformId platformId, final boolean onlyWithRemoteId)
	{
		//dev-note: exclude campaigns which were deleted on remote platform
		final IQueryFilter<I_MKTG_Campaign> deletedOnRemoteFilter = queryBL.createCompositeQueryFilter(I_MKTG_Campaign.class)
				.addStringNotLikeFilter(I_MKTG_Campaign.COLUMN_LastSyncStatus, RemoteToLocalSyncResult.RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM.name(), true);

		final ICompositeQueryFilter<I_MKTG_Campaign> statusFilter = queryBL.createCompositeQueryFilter(I_MKTG_Campaign.class)
				.setJoinOr()
				.addFilter(deletedOnRemoteFilter)
				.addEqualsFilter(I_MKTG_Campaign.COLUMN_LastSyncStatus, null);

		final IQueryBuilder<I_MKTG_Campaign> queryBuilder = queryBL.createQueryBuilder(I_MKTG_Campaign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign.COLUMNNAME_MKTG_Platform_ID, platformId.getRepoId())
				.filter(statusFilter);

		if (onlyWithRemoteId)
		{
			queryBuilder.addNotNull(I_MKTG_Campaign.COLUMNNAME_RemoteRecordId);
		}
		return queryBuilder
				.create()
				.iterateAndStream()
				.map(CampaignRepository::fromRecord);
	}

	@NonNull
	public List<Campaign> retrieveByPlatformAndRemoteIds(@NonNull final PlatformId platformId, @NonNull final Set<String> remoteIds)
	{
		return queryBL.createQueryBuilder(I_MKTG_Campaign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign.COLUMNNAME_MKTG_Platform_ID, platformId.getRepoId())
				.addInArrayFilter(I_MKTG_Campaign.COLUMNNAME_RemoteRecordId, remoteIds)
				.create()
				.iterateAndStream()
				.map(CampaignRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}
}
