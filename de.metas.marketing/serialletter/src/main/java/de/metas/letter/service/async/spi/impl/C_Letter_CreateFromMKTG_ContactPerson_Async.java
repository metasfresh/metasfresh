package de.metas.letter.service.async.spi.impl;

import java.util.Set;

import org.adempiere.location.Location;
import org.adempiere.location.LocationRepository;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.bpartner.BPartnerLocation;
import de.metas.bpartner.service.BPartnerLocationRepository;
import de.metas.letter.BoilerPlate;
import de.metas.letter.BoilerPlateRepository;
import de.metas.letters.model.Letter;
import de.metas.letters.model.LetterRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;

/*
 * #%L
 * de.metas.marketing.serialletter
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class C_Letter_CreateFromMKTG_ContactPerson_Async extends WorkpackageProcessorAdapter
{


	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		// Services
		final ContactPersonRepository contactRepo = Adempiere.getBean(ContactPersonRepository.class);
		final LetterRepository letterRepo = Adempiere.getBean(LetterRepository.class);
		final LocationRepository locationRepo = Adempiere.getBean(LocationRepository.class);
		final BPartnerLocationRepository bpLocationRepo = Adempiere.getBean(BPartnerLocationRepository.class);
		final BoilerPlateRepository  boilerPlateRepo = Adempiere.getBean(BoilerPlateRepository.class);


		final Set<Integer> campaignContactPersonsIDs = retrieveAllItemIds();

		for (final Integer campaignContactPersonID : campaignContactPersonsIDs)
		{
			final ContactPerson contactPerson = contactRepo.getByCampaignContactPersonId(campaignContactPersonID);
			final BPartnerLocation bpLocation = bpLocationRepo.getByBPartnerLocationId(contactPerson.getBpLocationId());
			final Location location = locationRepo.getByLocationId(bpLocation.getLocationId());

			// create letter
			String subject = "";
			String body = "";
			if (contactPerson.getBoilerPlateId() != null)
			{
				final BoilerPlate boilerPlate = boilerPlateRepo.getByBoilerPlateId(
						contactPerson.getBoilerPlateId(),
						contactPerson.getLanguage());

				if (boilerPlate != null)
				{
					subject = boilerPlate.getSubject();
					body = boilerPlate.getTextSnippext();
				}
			}
			Letter letter = Letter.builder()
					.boilerPlateId(contactPerson.getBoilerPlateId())
					.bpartnerId(contactPerson.getBPartnerId())
					.bpartnerLocationId(bpLocation.getId())
					.userId(contactPerson.getUserId())
					.address(location.getAddress())
					.adLanguage(Env.getAD_Language())
					.subject(subject)
					.body(body)
					.bodyParsed(body)
					.build();

			// save letter
			letter = letterRepo.save(letter);
		}

		return Result.SUCCESS;
	}

}
