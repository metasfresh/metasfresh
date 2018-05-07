package de.metas.marketing.misc;

import java.util.Iterator;

import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;
import org.springframework.stereotype.Service;

import de.metas.marketing.model.Campaign;
import de.metas.marketing.model.CampaignRepository;
import de.metas.marketing.model.ContactAddress;
import de.metas.marketing.model.ContactPerson;
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
	public ContactPerson createContactPersonForAdUser(@NonNull final I_AD_User adUserRecord)
	{
		return ContactPerson.builder()
				.name(adUserRecord.getName())
				.adUserId(adUserRecord.getAD_User_ID())
				.address(ContactAddress.ofEmailAddress(adUserRecord.getEMail()))
				.build();
	}

	public void addAsContactPersonsToCampaign(
			@NonNull final Iterator<I_AD_User> adUsersToAdd,
			final int campaignId)
	{
		final Tools converters = Adempiere.getBean(Tools.class);
		final CampaignRepository campaignRepository = Adempiere.getBean(CampaignRepository.class);
		final Campaign campaign = campaignRepository.getById(campaignId);

		while (adUsersToAdd.hasNext())
		{
			final I_AD_User adUserToAdd = adUsersToAdd.next();
			if (Check.isEmpty(adUserToAdd.getEMail(), true))
			{
				Loggables.get().addLog("Skip AD_User because it has no email address; AD_User={}", adUserToAdd);
				continue;
			}

			final ContactPerson contactPerson = converters.createContactPersonForAdUser(adUserToAdd);
			campaignRepository.addContactPersonToCampaign(contactPerson, campaign);
		}
	}
}
