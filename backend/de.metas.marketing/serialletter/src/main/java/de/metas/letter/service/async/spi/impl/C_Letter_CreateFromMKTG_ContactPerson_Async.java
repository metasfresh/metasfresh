package de.metas.letter.service.async.spi.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.letter.BoilerPlate;
import de.metas.letter.BoilerPlateRepository;
import de.metas.letters.model.Letter;
import de.metas.letters.model.LetterRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;

import java.util.Set;

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
		final ContactPersonRepository contactRepo = SpringContextHolder.instance.getBean(ContactPersonRepository.class);
		final LetterRepository letterRepo = SpringContextHolder.instance.getBean(LetterRepository.class);
		final BoilerPlateRepository boilerPlateRepo = SpringContextHolder.instance.getBean(BoilerPlateRepository.class);
		final DocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(DocumentLocationBL.class);

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

			final BPartnerId bPartnerId = contactPerson.getBPartnerId();
			final BPartnerLocationId bpLocationId = contactPerson.getBpLocationId();
			final UserId userId = contactPerson.getUserId();

			final RenderedAddressAndCapturedLocation renderedAddress = documentLocationBL.computeRenderedAddress(
					DocumentLocation.builder()
							.bpartnerId(bPartnerId)
							.bpartnerLocationId(bpLocationId)
							.locationId(contactPerson.getLocationId())
							.contactId(BPartnerContactId.of(bPartnerId,userId))
							.build());

			final Letter letter = Letter.builder()
					.boilerPlateId(contactPerson.getBoilerPlateId())
					.bpartnerId(bPartnerId)
					.bpartnerLocationId(bpLocationId)
					.userId(userId)
					.address(renderedAddress.getRenderedAddress())
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
