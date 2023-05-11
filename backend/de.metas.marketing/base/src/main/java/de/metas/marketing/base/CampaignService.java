package de.metas.marketing.base;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonId;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.marketing.base.model.SyncResult;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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

@Service
public class CampaignService
{
	private final ContactPersonRepository contactPersonRepository;
	private final CampaignRepository campaignRepository;
	private final PlatformRepository platformRepository;
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public CampaignService(
			@NonNull final ContactPersonRepository contactPersonRepository,
			@NonNull final CampaignRepository campaignRepository,
			@NonNull final PlatformRepository platformRepository)
	{
		this.contactPersonRepository = contactPersonRepository;
		this.campaignRepository = campaignRepository;
		this.platformRepository = platformRepository;
	}

	public Campaign getById(@NonNull final CampaignId campaignId)
	{
		return campaignRepository.getById(campaignId);
	}

	public List<Campaign> getByPlatformId(@NonNull final PlatformId platformId)
	{
		return campaignRepository.getByPlatformId(platformId);
	}

	public PlatformId getPlatformIdByCampaignId(@NonNull final CampaignId campaignId)
	{
		return getById(campaignId).getPlatformId();
	}

	public void addContactPersonToCampaign(
			@NonNull final ContactPersonId contactPersonId,
			@NonNull final CampaignId campaignId)
	{
		campaignRepository.addContactPersonToCampaign(contactPersonId, campaignId);
	}

	public void addContactPersonsToCampaign(
			@NonNull final List<ContactPerson> contactPersons,
			@NonNull final CampaignId campaignId)
	{
		final ImmutableSet<ContactPersonId> contactPersonIds = contactPersons.stream()
				.map(contactPerson -> Check.assumeNotNull(contactPerson.getContactPersonId(), "expected contact to be saved: {}", contactPerson))
				.collect(ImmutableSet.toImmutableSet());

		campaignRepository.addContactPersonsToCampaign(contactPersonIds, campaignId);
	}

	public void addAsContactPersonsToCampaign(
			@NonNull final Stream<User> users,
			@NonNull final CampaignId campaignId,
			@Nullable final DefaultAddressType defaultAddressType)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);
		users.forEach(user -> addToCampaignIfHasMailAddressOrLocation(user, campaign, defaultAddressType));
	}

	public void removeContactPersonsFromCampaign(
			@NonNull final CampaignId campaignId)
	{
		campaignRepository.removeAllContactPersonsFromCampaign(campaignId);
	}

	public void addToCampaignIfHasEmailAddress(
			@NonNull final User user,
			@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);
		addToCampaignIfHasMailAddressOrLocation(user, campaign, null);
	}

	private void addToCampaignIfHasMailAddressOrLocation(
			@NonNull final User user,
			@NonNull final Campaign campaign,
			@Nullable final DefaultAddressType defaultAddressType)
	{

		final Platform platform = platformRepository.getById(campaign.getPlatformId());

		final boolean isRequiredMailAddress = platform.isRequiredMailAddress();
		if (isRequiredMailAddress && Check.isBlank(user.getEmailAddress()))
		{
			Loggables.addLog("Skip user because it has no email address or campaign does not require mail address; user={}", user);
			return;
		}

		final boolean isRequiredLocation = platform.isRequiredLocation() || defaultAddressType != null;
		final BPartnerLocationId addressToUse = computeAddressToUse(user.getBpartnerId(), defaultAddressType);
		if (isRequiredLocation && addressToUse == null)
		{
			final String addressTypeForMessage = defaultAddressType != null ? defaultAddressType.toString() : DefaultAddressType.BillToDefault.toString();
			Loggables.addLog("Skip user because it has no {} location and the campaign requires location; user={}", addressTypeForMessage, user);
			return;
		}

		final ContactPerson savedContactPerson = contactPersonRepository.save(ContactPerson.newForUserPlatformAndLocation(user, campaign.getPlatformId(), campaign.getOrgId(), addressToUse));
		final ContactPersonId contactPersonId = Check.assumeNotNull(savedContactPerson.getContactPersonId(), "contact shall be saved: {}", savedContactPerson);

		campaignRepository.addContactPersonToCampaign(contactPersonId, campaign.getCampaignId());
		contactPersonRepository.createUpdateConsent(savedContactPerson);
	}

	private BPartnerLocationId computeAddressToUse(
			@Nullable final BPartnerId bpartnerId,
			@Nullable final DefaultAddressType defaultAddressType)
	{
		if (bpartnerId == null)
		{
			// no address found
			return null;
		}

		// Keep as before, and consider Billing address as default
		return DefaultAddressType.ShipToDefault.equals(defaultAddressType)
				? bpartnerDAO.getShiptoDefaultLocationIdByBpartnerId(bpartnerId)
				: bpartnerDAO.getBilltoDefaultLocationIdByBpartnerId(bpartnerId);

	}

	public void removeUserFromCampaign(
			@NonNull final User user,
			@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);

		BPartnerLocationId billToDefaultLocationId = null;
		if (user.getBpartnerId() != null)
		{
			billToDefaultLocationId = bpartnerDAO.getBilltoDefaultLocationIdByBpartnerId(user.getBpartnerId());
		}
		final ContactPerson contactPerson = ContactPerson.newForUserPlatformAndLocation(user, campaign.getPlatformId(), campaign.getOrgId(), billToDefaultLocationId);
		final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);

		contactPersonRepository.revokeConsent(savedContactPerson);
		campaignRepository.removeContactPersonFromCampaign(savedContactPerson, campaign);
	}

	public void saveSyncResults(@NonNull final List<? extends SyncResult> syncResults)
	{
		for (final SyncResult syncResult : syncResults)
		{
			campaignRepository.saveCampaignSyncResult(syncResult);
		}
	}

	public Campaign saveSyncResult(@NonNull final SyncResult syncResult)
	{
		return campaignRepository.saveCampaignSyncResult(syncResult);
	}

	@NonNull
	public Stream<Campaign> streamActivelySyncedWithRemoteId(@NonNull final PlatformId platformId)
	{
		final boolean onlyWithRemoteIds = true;
		return campaignRepository.streamActiveCampaigns(platformId, onlyWithRemoteIds);
	}

	@NonNull
	public Stream<Campaign> streamCampaigns(@NonNull final PlatformId platformId)
	{
		final boolean onlyWithRemoteIds = false;
		return campaignRepository.streamActiveCampaigns(platformId, onlyWithRemoteIds);
	}

	@NonNull
	public List<Campaign> retrieveByPlatformAndRemoteIds(@NonNull final PlatformId platformId, @NonNull final Set<String> remoteIds)
	{
		return campaignRepository.retrieveByPlatformAndRemoteIds(platformId, remoteIds);
	}
}
