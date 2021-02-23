package de.metas.marketing.base.model;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerLocationInfo;
import de.metas.bpartner.service.BPartnerLocationInfoRepository;
import de.metas.i18n.Language;
import de.metas.letter.BoilerPlateId;
import de.metas.location.LocationId;
import de.metas.marketing.base.model.ContactPerson.ContactPersonBuilder;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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
public class ContactPersonRepository
{
	private final BPartnerLocationInfoRepository bpLocationRepo;

	public ContactPersonRepository(@NonNull final BPartnerLocationInfoRepository bpLocationRepo)
	{
		this.bpLocationRepo = bpLocationRepo;
	}

	public ContactPerson save(@NonNull final ContactPerson contactPerson)
	{
		final I_MKTG_ContactPerson contactPersonRecord = createOrUpdateRecordDontSave(contactPerson);
		saveRecord(contactPersonRecord);

		return contactPerson.toBuilder()
				.contactPersonId(ContactPersonId.ofRepoId(contactPersonRecord.getMKTG_ContactPerson_ID()))
				.build();
	}

	private I_MKTG_ContactPerson createOrUpdateRecordDontSave(
			@NonNull final ContactPerson contactPerson)
	{
		final I_MKTG_ContactPerson contactPersonRecord = loadRecordIfPossible(contactPerson)
				.orElse(newInstance(I_MKTG_ContactPerson.class));

		contactPersonRecord.setAD_User_ID(UserId.toRepoIdOr(contactPerson.getUserId(), -1));
		contactPersonRecord.setC_BPartner_ID(BPartnerId.toRepoIdOr(contactPerson.getBPartnerId(), 0));

		if (contactPerson.getBPartnerId() != null)
		{
			if (contactPerson.getBpLocationId() != null)
			{
				final BPartnerLocationInfo bpLocation = bpLocationRepo.getByBPartnerLocationId(contactPerson.getBpLocationId());
				contactPersonRecord.setC_BPartner_Location_ID(bpLocation.getId().getRepoId());
				contactPersonRecord.setC_Location_ID(bpLocation.getLocationId().getRepoId());
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

		// set email stuff
		final Optional<EmailAddress> email = EmailAddress.cast(contactPerson.getAddress());

		final String emailString = email.map(EmailAddress::getValue).orElse(null);
		contactPersonRecord.setEMail(emailString);

		// set deactivated stuff
		final Boolean deactivatedBool = email.map(EmailAddress::getActiveOnRemotePlatformOrNull).orElse(null);
		final String deactivatedString = StringUtils.ofBoolean(
				deactivatedBool,
				X_MKTG_ContactPerson.DEACTIVATEDONREMOTEPLATFORM_UNKNOWN);
		contactPersonRecord.setDeactivatedOnRemotePlatform(deactivatedString);

		return contactPersonRecord;
	}

	private Optional<I_MKTG_ContactPerson> loadRecordIfPossible(
			@NonNull final ContactPerson contactPerson)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		I_MKTG_ContactPerson contactPersonRecord = null;

		if (contactPerson.getContactPersonId() != null)
		{
			final ContactPersonId contactPersonId = contactPerson.getContactPersonId();
			contactPersonRecord = load(contactPersonId.getRepoId(), I_MKTG_ContactPerson.class);
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

		if (contactPersonRecord == null && !Check.isEmpty(contactPerson.getRemoteId(), true))
		{
			contactPersonRecord = queryBL.createQueryBuilder(I_MKTG_ContactPerson.class)
					.filter(baseQueryFilter)
					.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_RemoteRecordId, contactPerson.getRemoteId())
					.create()
					.firstOnly(I_MKTG_ContactPerson.class); // might be null, that's ok
		}

		if (contactPerson.getBpLocationId() != null)
		{
			final BPartnerLocationInfo bpLocation = bpLocationRepo.getByBPartnerLocationId(contactPerson.getBpLocationId());
			final LocationId locationId = bpLocation.getLocationId();
			baseQueryFilter.addEqualsFilter(I_MKTG_ContactPerson.COLUMNNAME_C_Location_ID, locationId);
		}
		else
		{
			baseQueryFilter.addEqualsFilter(I_MKTG_ContactPerson.COLUMNNAME_C_Location_ID, contactPerson.getLocationId());
		}

		final String emailAddress = contactPerson.getEmailAddessStringOrNull();

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

	public ContactPerson saveCampaignSyncResult(@NonNull final SyncResult syncResult)
	{
		final ContactPerson contactPerson = ContactPerson.cast(syncResult.getSynchedDataRecord()).get();
		final I_MKTG_ContactPerson contactPersonRecord = createOrUpdateRecordDontSave(contactPerson);

		if (syncResult instanceof LocalToRemoteSyncResult)
		{
			contactPersonRecord.setLastSyncOfLocalToRemote(SystemTime.asTimestamp());

			final LocalToRemoteSyncResult localToRemoteSyncResult = (LocalToRemoteSyncResult)syncResult;
			contactPersonRecord.setLastSyncStatus(localToRemoteSyncResult.getLocalToRemoteStatus().toString());
			contactPersonRecord.setLastSyncDetailMessage(localToRemoteSyncResult.getErrorMessage());
		}
		else if (syncResult instanceof RemoteToLocalSyncResult)
		{
			contactPersonRecord.setLastSyncOfRemoteToLocal(de.metas.common.util.time.SystemTime.asTimestamp());

			final RemoteToLocalSyncResult remoteToLocalSyncResult = (RemoteToLocalSyncResult)syncResult;
			contactPersonRecord.setLastSyncStatus(remoteToLocalSyncResult.getRemoteToLocalStatus().toString());
			contactPersonRecord.setLastSyncDetailMessage(remoteToLocalSyncResult.getErrorMessage());
		}
		else
		{
			Check.fail("The given syncResult has an unexpected type of {}; syncResult={}", syncResult.getClass(), syncResult);
		}
		saveRecord(contactPersonRecord);
		return contactPerson
				.toBuilder()
				.contactPersonId(ContactPersonId.ofRepoId(contactPersonRecord.getMKTG_ContactPerson_ID()))
				.build();
	}

	public List<ContactPerson> getByCampaignId(@NonNull final CampaignId campaignId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId.getRepoId())
				.andCollect(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::asContactPerson)
				.collect(ImmutableList.toImmutableList());
	}

	public Set<Integer> getIdsByCampaignId(final int campaignId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
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
		ContactPerson contactPerson = asContactPerson(campaignContactPersonRecord.getMKTG_ContactPerson());
		final int boilerPlateId = campaignContactPersonRecord.getMKTG_Campaign().getAD_BoilerPlate_ID();
		if (boilerPlateId > 0)
		{
			contactPerson = contactPerson.toBuilder()
					.boilerPlateId(BoilerPlateId.ofRepoId(boilerPlateId))
					.build();
		}

		return contactPerson;
	}

	public ContactPerson asContactPerson(@NonNull final I_MKTG_ContactPerson contactPersonRecord)
	{
		final String emailDeactivated = contactPersonRecord.getDeactivatedOnRemotePlatform();

		final ContactPersonBuilder builder = ContactPerson.builder();
		if (!Check.isEmpty(contactPersonRecord.getEMail(), true))
		{
			final EmailAddress emailAddress = EmailAddress.of(
					contactPersonRecord.getEMail(),
					StringUtils.toBoolean(emailDeactivated, null));

			builder.address(emailAddress);
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(contactPersonRecord.getC_BPartner_ID());

		BPartnerLocationId bpartnerlocationId = null;
		LocationId locationId = null;
		if (contactPersonRecord.getC_BPartner_ID() > 0 && contactPersonRecord.getC_BPartner_Location_ID() > 0)
		{
			bpartnerlocationId = BPartnerLocationId.ofRepoId(bpartnerId, contactPersonRecord.getC_BPartner_Location_ID());
			locationId = LocationId.ofRepoId(contactPersonRecord.getC_BPartner_Location().getC_Location_ID());
		}
		else
		{
			bpartnerlocationId = null;
			locationId = LocationId.ofRepoIdOrNull(contactPersonRecord.getC_Location_ID());
		}

		return builder
				.userId(UserId.ofRepoId(contactPersonRecord.getAD_User_ID()))
				.bPartnerId(bpartnerId)
				.name(contactPersonRecord.getName())
				.platformId(PlatformId.ofRepoId(contactPersonRecord.getMKTG_Platform_ID()))
				.remoteId(contactPersonRecord.getRemoteRecordId())
				.contactPersonId(ContactPersonId.ofRepoId(contactPersonRecord.getMKTG_ContactPerson_ID()))
				.bpLocationId(bpartnerlocationId)
				.locationId(locationId)
				.language(Language.getLanguage(contactPersonRecord.getAD_Language()))
				.build();
	}

	public void createUpdateConsent(
			@NonNull final ContactPerson contactPerson)
	{
		final I_MKTG_Consent consentExistingRecord = getConsentRecord(contactPerson);

		final I_MKTG_Consent consent = consentExistingRecord != null ? consentExistingRecord : newInstance(I_MKTG_Consent.class);

		consent.setAD_User_ID(UserId.toRepoIdOr(contactPerson.getUserId(), -1));
		consent.setC_BPartner_ID(BPartnerId.toRepoIdOr(contactPerson.getBPartnerId(), 0));

		consent.setConsentDeclaredOn(de.metas.common.util.time.SystemTime.asTimestamp());
		consent.setMKTG_ContactPerson_ID(contactPerson.getContactPersonId().getRepoId());

		saveRecord(consent);
	}

	public void revokeConsent(
			@NonNull final ContactPerson contactPerson)
	{
		final I_MKTG_Consent consent = getConsentRecord(contactPerson);

		if (consent != null)
		{
			consent.setConsentRevokedOn(de.metas.common.util.time.SystemTime.asTimestamp());
			saveRecord(consent);
		}
	}

	private I_MKTG_Consent getConsentRecord(@NonNull final ContactPerson contactPerson)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Consent.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Consent.COLUMNNAME_ConsentRevokedOn, null)
				.addEqualsFilter(I_MKTG_Consent.COLUMNNAME_MKTG_ContactPerson_ID, contactPerson.getContactPersonId().getRepoId())
				.orderByDescending(I_MKTG_Consent.COLUMNNAME_ConsentDeclaredOn)
				.create()
				.first(I_MKTG_Consent.class);
	}

	public ContactPerson updateBPartnerLocation(final ContactPerson contactPerson, BPartnerLocationId bpLocationId)
	{
		contactPerson.toBuilder()
				.bpLocationId(bpLocationId)
				.build();

		save(contactPerson);

		return contactPerson;
	}

	public Set<ContactPerson> getByBPartnerLocationId(@NonNull final BPartnerLocationId bpLocationId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_C_BPartner_Location_ID, bpLocationId.getRepoId())
				.create()
				.stream()
				.map(this::asContactPerson)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<ContactPerson> getByUserId(@NonNull final UserId userId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_ContactPerson.COLUMN_AD_User_ID, userId.getRepoId())
				.create()
				.stream()
				.map(this::asContactPerson)
				.collect(ImmutableSet.toImmutableSet());
	}

}
