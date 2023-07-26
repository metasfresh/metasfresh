package de.metas.marketing.base;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.Platform;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
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

@Service
public class CampaignService
{
	private final ContactPersonRepository contactPersonRepository;
	private final CampaignRepository campaignRepository;
	private final PlatformRepository platformRepository;

	public CampaignService(@NonNull final ContactPersonRepository contactPersonRepository,
			@NonNull final CampaignRepository campaignRepository,
			@NonNull final PlatformRepository platformRepository)
	{
		this.contactPersonRepository = contactPersonRepository;
		this.campaignRepository = campaignRepository;
		this.platformRepository = platformRepository;
	}

	public void addAsContactPersonsToCampaign(
			@NonNull final Stream<User> users,
			@NonNull final CampaignId campaignId,
			@Nullable final DefaultAddressType defaultAddressType)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);
		users.forEach(user -> addToCampaignIfHasMaillAddressOrLocation(user, campaign, defaultAddressType));
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
		addToCampaignIfHasMaillAddressOrLocation(user, campaign, null);
	}

	private void addToCampaignIfHasMaillAddressOrLocation(
			@NonNull final User user,
			@NonNull final Campaign campaign,
			@Nullable final DefaultAddressType defaultAddressType)
	{

		final Platform platform = platformRepository.getById(campaign.getPlatformId());

		final boolean isRequiredMailAddres = platform.isRequiredMailAddress();

		if (isRequiredMailAddres && Check.isEmpty(user.getEmailAddress(), true))
		{
			Loggables.addLog("Skip user because it has no email address or campaign does not require mail address; user={}", user);
			return;
		}

		final boolean isRequiredLocation = platform.isRequiredLocation() || defaultAddressType != null;

		final BPartnerLocationId addressToUse = computeAddressToUse(user, defaultAddressType);

		if (isRequiredLocation && addressToUse == null )
		{
			final String addressTypeForMessage = defaultAddressType != null ? defaultAddressType.toString() : DefaultAddressType.BillToDefault.toString() ;
			Loggables.addLog("Skip user because it has no {} location and the campaign requires location; user={}", addressTypeForMessage,user);
			return;
		}

		final ContactPerson contactPerson = ContactPerson.newForUserPlatformAndLocation(user, campaign.getPlatformId(), addressToUse);
		final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);

		campaignRepository.addContactPersonToCampaign(savedContactPerson.getContactPersonId(), campaign.getCampaignId());
		contactPersonRepository.createUpdateConsent(savedContactPerson);
	}

	private BPartnerLocationId computeAddressToUse(final User user, final DefaultAddressType defaultAddressType)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final BPartnerId bpartnerId = user.getBpartnerId();

		if (bpartnerId == null)
		{
			// no address found
			return null;
		}

		final BPartnerLocationId addressToUse;

		if (DefaultAddressType.ShipToDefault.equals(defaultAddressType))
		{
			addressToUse = bpartnerDAO.getShiptoDefaultLocationIdByBpartnerId(bpartnerId);
		}
		else
		{
			// Keep as before, and consider Bill address as default
			addressToUse = bpartnerDAO.getBilltoDefaultLocationIdByBpartnerId(bpartnerId);
		}

		return addressToUse;

	}

	public void removeUserFromCampaign(
			@NonNull final User user,
			@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);

		BPartnerLocationId billToDefaultLocationId = null;
		if (user.getBpartnerId() != null)
		{
			billToDefaultLocationId = Services.get(IBPartnerDAO.class).getBilltoDefaultLocationIdByBpartnerId(user.getBpartnerId());
		}
		final ContactPerson contactPerson = ContactPerson.newForUserPlatformAndLocation(user, campaign.getPlatformId(), billToDefaultLocationId);
		final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);

		contactPersonRepository.revokeConsent(savedContactPerson);
		campaignRepository.removeContactPersonFromCampaign(savedContactPerson, campaign);
	}

}
