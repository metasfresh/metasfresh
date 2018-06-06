package de.metas.letter.service.async.spi.impl;

import java.util.List;

import org.adempiere.location.Location;
import org.adempiere.location.LocationRepository;
import org.adempiere.util.Services;
import org.compiere.Adempiere;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.letter.model.Letter;
import de.metas.letter.model.LetterRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;

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
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final ContactPersonRepository contactRepo = Adempiere.getBean(ContactPersonRepository.class);
		final LetterRepository letterRepo = Adempiere.getBean(LetterRepository.class);
		final LocationRepository locationRepo = Adempiere.getBean(LocationRepository.class);


		final List<I_MKTG_Campaign_ContactPerson> campaignContactPersons = queueDAO.retrieveItems(workPackage, I_MKTG_Campaign_ContactPerson.class, localTrxName);

		for (final I_MKTG_Campaign_ContactPerson campaignContactPerson : campaignContactPersons)
		{
			final ContactPerson contactPerson = contactRepo.asContactPerson(campaignContactPerson.getMKTG_ContactPerson());
			final Location location = locationRepo.getByLocationId(contactPerson.getLocationId());

			// create letter
			Letter letter = Letter.builder()
					.boilerPlateId(campaignContactPerson.getMKTG_Campaign().getAD_BoilerPlate_ID())
					.bpartnerId(contactPerson.getBPartnerId())
					.userId(contactPerson.getUserId())
					.subject(campaignContactPerson.getMKTG_Campaign().getName() + contactPerson.getName())
					.body(campaignContactPerson.getMKTG_Campaign().getName())
					.address(location.getAddress())
					.build();

			// save letter
			letter = letterRepo.save(letter);
		}

		return Result.SUCCESS;
	}

}
