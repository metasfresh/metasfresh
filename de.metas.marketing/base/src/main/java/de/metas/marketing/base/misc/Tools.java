package de.metas.marketing.base.misc;

import java.util.Iterator;

import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.compiere.model.I_AD_User;
import org.springframework.stereotype.Service;

import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.marketing.base.model.PlatformId;
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
public class Tools
{
	private final ContactPersonRepository contactPersonRepository;
	private final CampaignRepository campaignRepository;

	public Tools(@NonNull final ContactPersonRepository contactPersonRepository,
			@NonNull final CampaignRepository campaignRepository)
	{
		this.contactPersonRepository = contactPersonRepository;
		this.campaignRepository = campaignRepository;
	}

	public ContactPerson createContactPersonForAdUser(
			@NonNull final I_AD_User adUserRecord,
			@NonNull final PlatformId platformId)
	{
		return ContactPerson.builder()
				.platformId(platformId)
				.name(adUserRecord.getName())
				.adUserId(adUserRecord.getAD_User_ID())
				.address(EmailAddress.of(adUserRecord.getEMail()))
				.build();
	}

	public void addAsContactPersonsToCampaign(
			@NonNull final Iterator<I_AD_User> adUsersToAdd,
			@NonNull final Campaign campaign)
	{
		while (adUsersToAdd.hasNext())
		{
			final I_AD_User adUserToAdd = adUsersToAdd.next();
			if (Check.isEmpty(adUserToAdd.getEMail(), true))
			{
				Loggables.get().addLog("Skip AD_User because it has no email address; AD_User={}", adUserToAdd);
				continue;
			}

			final ContactPerson contactPerson = createContactPersonForAdUser(adUserToAdd, campaign.getPlatformId());
			final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);
			campaignRepository.addContactPersonToCampaign(savedContactPerson.getContactPersonId(), campaign.getCampaignId());
		}
	}

	public void addToNewsletter(@NonNull I_AD_User user, @NonNull final CampaignId campaignId)
	{
		if (Check.isEmpty(user.getEMail(), true))
		{
			Loggables.get().addLog("Skip AD_User because it has no email address; AD_User={}", user);
			return;
		}
		final Campaign campaign = campaignRepository.getById(campaignId);

		final ContactPerson contactPerson = createContactPersonForAdUser(user, campaign.getPlatformId());
		final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);

		campaignRepository.addContactPersonToCampaign(savedContactPerson.getContactPersonId(), campaign.getCampaignId());
		contactPersonRepository.createConsent(savedContactPerson);

	}

	public void removeFromNewsletter(@NonNull I_AD_User user, @NonNull final CampaignId campaignId)
	{
		final Campaign campaign = campaignRepository.getById(campaignId);

		final ContactPerson contactPerson = createContactPersonForAdUser(user, campaign.getPlatformId());
		final ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);

		contactPersonRepository.revokeConsent(savedContactPerson);
		campaignRepository.removeContactPersonFromCampaign(savedContactPerson, campaign);

	}
}
