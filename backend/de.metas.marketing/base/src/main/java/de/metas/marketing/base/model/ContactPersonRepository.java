package de.metas.marketing.base.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.Language;
import de.metas.letter.BoilerPlateId;
import de.metas.location.LocationId;
import de.metas.marketing.base.model.ContactPerson.ContactPersonBuilder;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.STRING_LIKE_IGNORECASE;
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
public class ContactPersonRepository
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ContactPerson getById(@NonNull final ContactPersonId contactPersonId)
	{
		final I_MKTG_ContactPerson record = load(contactPersonId.getRepoId(), I_MKTG_ContactPerson.class);
		return toContactPerson(record);
	}

	public ContactPerson save(@NonNull final ContactPerson contactPerson)
	{
		final I_MKTG_ContactPerson contactPersonRecord = createOrUpdateRecordDontSave(contactPerson);
		saveRecord(contactPersonRecord);

		final ContactPersonId contactPersonId = ContactPersonId.ofRepoId(contactPersonRecord.getMKTG_ContactPerson_ID());
		return contactPerson.withContactPersonId(contactPersonId);
	}

	private I_MKTG_ContactPerson createOrUpdateRecordDontSave(
			@NonNull final ContactPerson contactPerson)
	{
		final I_MKTG_ContactPerson contactPersonRecord = loadRecordIfPossible(contactPerson)
				.orElse(newInstance(I_MKTG_ContactPerson.class));

		contactPersonRecord.setAD_User_ID(UserId.toRepoId(contactPerson.getUserId()));
		contactPersonRecord.setC_BPartner_ID(BPartnerId.toRepoId(contactPerson.getBPartnerId()));

		if (contactPerson.getBPartnerId() != null)
		{
			if (contactPerson.getBpLocationId() != null)
			{
				final BPartnerLocationAndCaptureId bpLocationId = bpartnerDAO.getBPartnerLocationAndCaptureIdInTrx(contactPerson.getBpLocationId());
				contactPersonRecord.setC_BPartner_Location_ID(bpLocationId.getBPartnerLocationRepoId());
				contactPersonRecord.setC_Location_ID(bpLocationId.getLocationCaptureRepoId());
			}
			else
			{
				contactPersonRecord.setC_BPartner_Location(null);
				contactPersonRecord.setC_Location(null);
			}
		}
		else
		{
			contactPersonRecord.setC_Location_ID(LocationId.toRepoIdOr(contactPerson.getLocationId(), 0));
		}

		contactPersonRecord.setName(contactPerson.getName());
		contactPersonRecord.setAD_Language(Language.asLanguageStringOrNull(contactPerson.getLanguage()));

		contactPersonRecord.setMKTG_Platform_ID(contactPerson.getPlatformId().getRepoId());
		contactPersonRecord.setRemoteRecordId(contactPerson.getRemoteId());

		contactPersonRecord.setEMail(contactPerson.getEmailAddressStringOrNull());
		contactPersonRecord.setDeactivatedOnRemotePlatform(contactPerson.getDeactivatedOnRemotePlatform().getCode());
		contactPersonRecord.setAD_Org_ID(contactPerson.getOrgId().getRepoId());

		return contactPersonRecord;
	}

	private Optional<I_MKTG_ContactPerson> loadRecordIfPossible(
			@NonNull final ContactPerson contactPerson)
	{
		if (contactPerson.getContactPersonId() != null)
		{
			final ContactPersonId contactPersonId = contactPerson.getContactPersonId();
			final I_MKTG_ContactPerson contactPersonRecord = load(contactPersonId.getRepoId(), I_MKTG_ContactPerson.class);
			return Optional.of(contactPersonRecord);
		}

		final ICompositeQueryFilter<I_MKTG_ContactPerson> baseQueryFilter = queryBL.createCompositeQueryFilter(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_MKTG_Platform_ID, contactPerson.getPlatformId().getRepoId());

		final UserId userId = contactPerson.getUserId();
		if (userId != null)
		{
			baseQueryFilter.addEqualsFilter(I_MKTG_ContactPerson.COLUMNNAME_AD_User_ID, userId.getRepoId());
		}

		I_MKTG_ContactPerson contactPersonRecord = null;
		if (!Check.isBlank(contactPerson.getRemoteId()))
		{
			contactPersonRecord = queryBL.createQueryBuilder(I_MKTG_ContactPerson.class)
					.filter(baseQueryFilter)
					.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_RemoteRecordId, contactPerson.getRemoteId())
					.create()
					.firstOnly(I_MKTG_ContactPerson.class); // might be null, that's ok
		}

		if (contactPerson.getBpLocationId() != null)
		{
			final BPartnerLocationAndCaptureId bpLocation = bpartnerDAO.getBPartnerLocationAndCaptureIdInTrx(contactPerson.getBpLocationId());
			final LocationId locationId = bpLocation.getLocationCaptureId();
			baseQueryFilter.addEqualsFilter(I_MKTG_ContactPerson.COLUMNNAME_C_Location_ID, locationId);
		}
		else
		{
			baseQueryFilter.addEqualsFilter(I_MKTG_ContactPerson.COLUMNNAME_C_Location_ID, contactPerson.getLocationId());
		}

		final String emailAddress = contactPerson.getEmailAddressStringOrNull();

		if (contactPersonRecord == null && emailAddress != null)
		{
			// if it's still null, then see if there is a contact with a matching email
			contactPersonRecord = queryBL.createQueryBuilder(I_MKTG_ContactPerson.class)
					.filter(baseQueryFilter)
					.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_EMail, emailAddress)
					.orderBy()
					.addColumn(I_MKTG_ContactPerson.COLUMN_MKTG_ContactPerson_ID).endOrderBy()
					.create()
					.first();
		}
		return Optional.ofNullable(contactPersonRecord);
	}

	public ContactPerson saveSyncResult(@NonNull final SyncResult syncResult)
	{
		final ContactPerson contactPerson = ContactPerson.cast(syncResult.getSynchedDataRecord())
				.orElseThrow(() -> new AdempiereException("Expected to have a contact person: " + syncResult));
		final I_MKTG_ContactPerson contactPersonRecord = createOrUpdateRecordDontSave(contactPerson);

		if (syncResult instanceof LocalToRemoteSyncResult)
		{
			final LocalToRemoteSyncResult localToRemoteSyncResult = (LocalToRemoteSyncResult)syncResult;

			contactPersonRecord.setLastSyncOfLocalToRemote(SystemTime.asTimestamp());
			contactPersonRecord.setLastSyncStatus(localToRemoteSyncResult.getLocalToRemoteStatus().toString());
			contactPersonRecord.setLastSyncDetailMessage(localToRemoteSyncResult.getErrorMessage());
		}
		else if (syncResult instanceof RemoteToLocalSyncResult)
		{
			final RemoteToLocalSyncResult remoteToLocalSyncResult = (RemoteToLocalSyncResult)syncResult;

			contactPersonRecord.setLastSyncOfRemoteToLocal(SystemTime.asTimestamp());
			contactPersonRecord.setLastSyncStatus(remoteToLocalSyncResult.getRemoteToLocalStatus().toString());
			contactPersonRecord.setLastSyncDetailMessage(remoteToLocalSyncResult.getErrorMessage());
		}
		else
		{
			throw new AdempiereException("The given syncResult has an unexpected type of " + syncResult.getClass() + "; syncResult=" + syncResult);
		}

		saveRecord(contactPersonRecord);

		return toContactPerson(contactPersonRecord);
	}

	public List<ContactPerson> getByCampaignId(@NonNull final CampaignId campaignId)
	{
		return queryBL
				.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId.getRepoId())
				.andCollect(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ContactPersonRepository::toContactPerson)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean isEmailInUse(@NonNull final String email)
	{
		return queryBL
				.createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_MKTG_ContactPerson.COLUMN_EMail, STRING_LIKE_IGNORECASE, email)
				.create()
				.anyMatch();
	}

	public Set<Integer> getIdsByCampaignId(final int campaignId)
	{
		return queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterateAndStream()
				.map(I_MKTG_Campaign_ContactPerson::getMKTG_Campaign_ContactPerson_ID)
				.collect(ImmutableSet.toImmutableSet());
	}

	public ContactPerson getByCampaignContactPersonId(final int campaignContactPersonID)
	{
		final I_MKTG_Campaign_ContactPerson campaignContactPersonRecord = load(campaignContactPersonID, I_MKTG_Campaign_ContactPerson.class);
		ContactPerson contactPerson = toContactPerson(campaignContactPersonRecord.getMKTG_ContactPerson());
		final int boilerPlateId = campaignContactPersonRecord.getMKTG_Campaign().getAD_BoilerPlate_ID();
		if (boilerPlateId > 0)
		{
			contactPerson = contactPerson.toBuilder()
					.boilerPlateId(BoilerPlateId.ofRepoId(boilerPlateId))
					.build();
		}

		return contactPerson;
	}

	public static ContactPerson toContactPerson(@NonNull final I_MKTG_ContactPerson contactPersonRecord)
	{
		final ContactPersonBuilder builder = ContactPerson.builder();

		final String email = StringUtils.trimBlankToNull(contactPersonRecord.getEMail());
		if (email != null)
		{
			final DeactivatedOnRemotePlatform deactivatedOnRemotePlatform = DeactivatedOnRemotePlatform.ofCode(contactPersonRecord.getDeactivatedOnRemotePlatform());
			builder.address(EmailAddress.of(email, deactivatedOnRemotePlatform));
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(contactPersonRecord.getC_BPartner_ID());
		final BPartnerLocationId bpartnerlocationId = BPartnerLocationId.ofRepoIdOrNull(bpartnerId, contactPersonRecord.getC_BPartner_Location_ID());
		final LocationId locationId;
		if (bpartnerlocationId != null)
		{
			locationId = LocationId.ofRepoId(contactPersonRecord.getC_BPartner_Location().getC_Location_ID());
		}
		else
		{
			locationId = LocationId.ofRepoIdOrNull(contactPersonRecord.getC_Location_ID());
		}

		return builder
				.userId(UserId.ofRepoIdOrNull(contactPersonRecord.getAD_User_ID()))
				.bPartnerId(bpartnerId)
				.name(contactPersonRecord.getName())
				.platformId(PlatformId.ofRepoId(contactPersonRecord.getMKTG_Platform_ID()))
				.remoteId(StringUtils.trimBlankToNull(contactPersonRecord.getRemoteRecordId()))
				.contactPersonId(ContactPersonId.ofRepoId(contactPersonRecord.getMKTG_ContactPerson_ID()))
				.bpLocationId(bpartnerlocationId)
				.locationId(locationId)
				.language(Language.getLanguage(contactPersonRecord.getAD_Language()))
				.orgId(OrgId.ofRepoId(contactPersonRecord.getAD_Org_ID()))
				.build();
	}

	public void createUpdateConsent(
			@NonNull final ContactPerson contactPerson)
	{
		final ContactPersonId contactPersonId = Check.assumeNotNull(contactPerson.getContactPersonId(), "contact is saved: {}", contactPerson);
		final I_MKTG_Consent consentExistingRecord = getConsentRecord(contactPersonId);

		final I_MKTG_Consent consent = consentExistingRecord != null ? consentExistingRecord : newInstance(I_MKTG_Consent.class);

		consent.setAD_User_ID(UserId.toRepoIdOr(contactPerson.getUserId(), -1));
		consent.setC_BPartner_ID(BPartnerId.toRepoIdOr(contactPerson.getBPartnerId(), 0));

		consent.setConsentDeclaredOn(SystemTime.asTimestamp());
		consent.setMKTG_ContactPerson_ID(contactPersonId.getRepoId());

		saveRecord(consent);
	}

	public void revokeConsent(
			@NonNull final ContactPerson contactPerson)
	{
		final ContactPersonId contactPersonId = Check.assumeNotNull(contactPerson.getContactPersonId(), "contact is saved: {}", contactPerson);
		final I_MKTG_Consent consent = getConsentRecord(contactPersonId);

		if (consent != null)
		{
			consent.setConsentRevokedOn(SystemTime.asTimestamp());
			saveRecord(consent);
		}
	}

	private I_MKTG_Consent getConsentRecord(@NonNull final ContactPersonId contactPersonId)
	{
		return queryBL.createQueryBuilder(I_MKTG_Consent.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Consent.COLUMNNAME_ConsentRevokedOn, null)
				.addEqualsFilter(I_MKTG_Consent.COLUMNNAME_MKTG_ContactPerson_ID, contactPersonId.getRepoId())
				.orderByDescending(I_MKTG_Consent.COLUMNNAME_ConsentDeclaredOn)
				.create()
				.first(I_MKTG_Consent.class);
	}

	public void updateBPartnerLocation(final ContactPerson contactPerson, BPartnerLocationId bpLocationId)
	{
		contactPerson.toBuilder()
				.bpLocationId(bpLocationId)
				.build();

		save(contactPerson);
	}

	public Set<ContactPerson> getByBPartnerLocationId(@NonNull final BPartnerLocationId bpLocationId)
	{
		return queryBL
				.createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_C_BPartner_Location_ID, bpLocationId.getRepoId())
				.create()
				.stream()
				.map(ContactPersonRepository::toContactPerson)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<ContactPerson> getByUserId(@NonNull final UserId userId)
	{
		return queryBL
				.createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_AD_User_ID, userId.getRepoId())
				.create()
				.stream()
				.map(ContactPersonRepository::toContactPerson)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public Stream<ContactPerson> streamActiveContacts(@NonNull final CampaignId campaignId, final boolean onlyWithRemoteId)
	{
		//dev-note: exclude contacts which were deleted on remote platform
		final IQueryFilter<I_MKTG_ContactPerson> deletedOnRemoteFilter = queryBL.createCompositeQueryFilter(I_MKTG_ContactPerson.class)
				.addStringNotLikeFilter(I_MKTG_ContactPerson.COLUMN_LastSyncStatus, RemoteToLocalSyncResult.RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM.name(), true);

		final ICompositeQueryFilter<I_MKTG_ContactPerson> statusFilter = queryBL.createCompositeQueryFilter(I_MKTG_ContactPerson.class)
				.setJoinOr()
				.addFilter(deletedOnRemoteFilter)
				.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_LastSyncStatus, null);

		final IQueryBuilder<I_MKTG_ContactPerson> queryBuilder = queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_Campaign_ID, campaignId.getRepoId())
				.andCollect(I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_ContactPerson_ID, I_MKTG_ContactPerson.class)
				.filter(statusFilter);

		if(onlyWithRemoteId)
		{
			queryBuilder.addNotNull(I_MKTG_ContactPerson.COLUMNNAME_RemoteRecordId);
		}

		return queryBuilder.create()
				.iterateAndStream()
				.map(ContactPersonRepository::toContactPerson);
	}

	@NonNull
	public List<ContactPerson> retrieveByCampaignAndRemoteIds(@NonNull final CampaignId campaignId, @NonNull final Set<String> remoteIds)
	{
		if(remoteIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_Campaign_ID, campaignId.getRepoId())
				.andCollect(I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_ContactPerson_ID, I_MKTG_ContactPerson.class)
				.addInArrayFilter(I_MKTG_ContactPerson.COLUMNNAME_RemoteRecordId, remoteIds)
				.create()
				.stream()
				.map(ContactPersonRepository::toContactPerson)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<ContactPerson> retrieveByEmails(@NonNull final CampaignId campaignId, @NonNull final Collection<String> emails)
	{
		return queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_MKTG_ContactPerson.COLUMNNAME_EMail, emails)
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_Campaign_ID, campaignId.getRepoId())
				.andCollect(I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_ContactPerson_ID, I_MKTG_ContactPerson.class)
				.create()
				.stream()
				.map(ContactPersonRepository::toContactPerson)
				.collect(ImmutableList.toImmutableList());
	}
}
