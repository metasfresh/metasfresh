package de.metas.letter.service.async.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.location.Location;
import org.adempiere.location.LocationRepository;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
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
	/**
	 *
	 * Schedule the letter creation based on the given MKTG_Campaign_ContactPerson ids and the MKTG_Campaign
	 *
	 */
	public static void createWorkpackage(final List<Integer> campaignContactPersonIds)
	{
		if (campaignContactPersonIds == null || campaignContactPersonIds.isEmpty())
		{
			// no lines to process
			return;
		}

		for (final Integer campaignContactPersonId : campaignContactPersonIds)
		{
			if (campaignContactPersonId == null || campaignContactPersonId <= 0)
			{
				// should not happen
				continue;
			}
			final I_MKTG_Campaign_ContactPerson campaignContactPerson = load(campaignContactPersonId, I_MKTG_Campaign_ContactPerson.class);

			SCHEDULER.schedule(campaignContactPerson);
		}
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<I_MKTG_Campaign_ContactPerson> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<I_MKTG_Campaign_ContactPerson>(C_Letter_CreateFromMKTG_ContactPerson_Async.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final I_MKTG_Campaign_ContactPerson model)
		{
			return model != null && model.getMKTG_Campaign_ContactPerson_ID() > 0;
		};

		@Override
		protected Properties extractCtxFromItem(final I_MKTG_Campaign_ContactPerson item)
		{
			return Env.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final I_MKTG_Campaign_ContactPerson item)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final I_MKTG_Campaign_ContactPerson item)
		{
			return new TableRecordReference(I_MKTG_Campaign_ContactPerson.Table_Name, item.getMKTG_Campaign_ContactPerson_ID());
		}
	};


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
					.boilerPlateId(-1)
					.bpartnerId(contactPerson.getBPartnerId())
					.userId(contactPerson.getUserId())
					.subject(campaignContactPerson.getMKTG_Campaign().getName() + contactPerson.getName())
					.address(location.getAddress())
					.build();

			// save letter
			letter = letterRepo.save(letter);
		}

		return Result.SUCCESS;
	}

}
