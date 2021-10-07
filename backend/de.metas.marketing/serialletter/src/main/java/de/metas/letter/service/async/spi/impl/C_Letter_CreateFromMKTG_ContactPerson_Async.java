package de.metas.letter.service.async.spi.impl;

import java.util.Set;

import org.compiere.Adempiere;
import org.compiere.util.Env;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.letter.BoilerPlate;
import de.metas.letter.BoilerPlateRepository;
import de.metas.letters.model.Letter;
import de.metas.letters.model.LetterRepository;
import de.metas.location.Location;
import de.metas.location.LocationRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.util.Loggables;
import org.compiere.util.Evaluatees;

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
		final BoilerPlateRepository boilerPlateRepo = Adempiere.getBean(BoilerPlateRepository.class);

		final Set<Integer> campaignContactPersonsIDs = retrieveAllItemIds();

		for (final Integer campaignContactPersonID : campaignContactPersonsIDs)
		{
			final ContactPerson contactPerson = contactRepo.getByCampaignContactPersonId(campaignContactPersonID);
			if(contactPerson.getLocationId() == null)
			{
				Loggables.addLog(
						"contact person with id={} has no location; skipping",
						contactPerson.getContactPersonId());
				continue;
			}
			
			final Location location = locationRepo.getByLocationId(contactPerson.getLocationId());

			// create letter
			String subject = "";
			String body = "";
			if (contactPerson.getBoilerPlateId() == null)
			{
				Loggables.addLog(
						"contact person with id={} has no boilerPlate text-snippet; skipping",
						contactPerson.getContactPersonId());
				continue;
			}
			else
			{
				final BoilerPlate boilerPlate = boilerPlateRepo.getByBoilerPlateId(
						contactPerson.getBoilerPlateId(),
						contactPerson.getLanguage());

				if (boilerPlate == null)
				{
					Loggables.addLog(
							"contact person with id={} has no boilerPlate text-snippet for language={}; skipping",
							contactPerson.getContactPersonId(), contactPerson.getLanguage());
					continue;
				}
				else
				{
					subject = boilerPlate.evaluateSubject(Evaluatees.empty());
					body = boilerPlate.evaluateTextSnippet(Evaluatees.empty());
				}
			}

			final Letter letter = Letter.builder()
					.boilerPlateId(contactPerson.getBoilerPlateId())
					.bpartnerId(contactPerson.getBPartnerId())
					.bpartnerLocationId(contactPerson.getBpLocationId())
					.userId(contactPerson.getUserId())
					.address(location.getAddress())
					.adLanguage(Env.getAD_Language())
					.subject(subject)
					.body(body)
					.bodyParsed(body)
					.build();
			final Letter savedLetter = letterRepo.save(letter);
			Loggables.addLog(
					"Created and saved a letter with id={} for contact person with id={}; boilerPlateId={}; language={}",
					savedLetter.getId(), contactPerson.getContactPersonId(), contactPerson.getBoilerPlateId(), contactPerson.getLanguage());
		}
		return Result.SUCCESS;
	}
}
