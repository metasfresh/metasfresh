package de.metas.marketing.base;

import java.util.stream.Stream;

import org.adempiere.user.User;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.springframework.stereotype.Service;

import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
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

	public CampaignService(@NonNull final ContactPersonRepository contactPersonRepository,
			@NonNull final CampaignRepository campaignRepository)
	{
		this.contactPersonRepository = contactPersonRepository;
		this.campaignRepository = campaignRepository;
	}

	public void addAsContactPersonsToCampaign(
			@NonNull final Stream<User> users,
			@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);
		users.forEach(user -> addToCampaignIfHasEmailAddress0(user, campaign));
	}

	public void addToCampaignIfHasEmailAddress(
			@NonNull final User user,
			@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);
		addToCampaignIfHasEmailAddress0(user, campaign);
	}

	private void addToCampaignIfHasEmailAddress0(
			@NonNull final User user,
			@NonNull final Campaign campaign)
	{
		if (Check.isEmpty(user.getEmailAddress(), true))
		{
			Loggables.get().addLog("Skip user because it has no email address; user={}", user);
			return;
		}

		final ContactPerson contactPerson = ContactPerson.newForUserAndPlatform(user, campaign.getPlatformId());
		final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);

		campaignRepository.addContactPersonToCampaign(savedContactPerson.getContactPersonId(), campaign.getCampaignId());
		contactPersonRepository.createUpdateConsent(savedContactPerson);
	}

	public void removeFromCampaign(
			@NonNull final User user,
			@NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);

		final ContactPerson contactPerson = ContactPerson.newForUserAndPlatform(user, campaign.getPlatformId());
		final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);

		contactPersonRepository.revokeConsent(savedContactPerson);
		campaignRepository.removeContactPersonFromCampaign(savedContactPerson, campaign);
	}
}
