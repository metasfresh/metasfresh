package de.metas.marketing.model;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.stereotype.Repository;

import de.metas.marketing.model.I_MKTG_Campaign;
import de.metas.marketing.model.I_MKTG_Campaign_ContactPerson;
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

@Repository
public class CampaignRepository
{
	private ContactPersonRepository contactPersonRepository;

	public CampaignRepository(ContactPersonRepository contactPersonRepository)
	{
		this.contactPersonRepository = contactPersonRepository;
	}

	public Campaign getById(final int campaignId)
	{
		final I_MKTG_Campaign campaignRecord = load(campaignId, I_MKTG_Campaign.class);
		return new Campaign(campaignRecord.getName(), campaignRecord.getMKTG_Campaign_ID());
	}

	public void addContactPersonToCampaign(
			@NonNull final ContactPerson contactPerson,
			@NonNull final Campaign campaign)
	{
		final ContactPerson storedContactPerson = contactPersonRepository.store(contactPerson);
		final Campaign storedCampaign = store(campaign);

		final boolean associationAlreadyExists = Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaign.getRepoId())
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, storedContactPerson.getRepoId())
				.create()
				.match();
		if (associationAlreadyExists)
		{
			return;
		}

		final I_MKTG_Campaign_ContactPerson newAssociation = newInstance(I_MKTG_Campaign_ContactPerson.class);
		newAssociation.setMKTG_Campaign_ID(storedCampaign.getRepoId());
		newAssociation.setMKTG_ContactPerson_ID(storedContactPerson.getRepoId());
		save(newAssociation);
	}

	public Campaign store(@NonNull final Campaign campaign)
	{
		final I_MKTG_Campaign campaignRecord;
		if (campaign.getRepoId() > 0)
		{
			campaignRecord = load(campaign.getRepoId(), I_MKTG_Campaign.class);
		}
		else
		{
			campaignRecord = newInstance(I_MKTG_Campaign.class);
		}

		campaignRecord.setName(campaign.getName());
		save(campaignRecord);

		return campaign.toBuilder()
				.repoId(campaignRecord.getMKTG_Campaign_ID())
				.build();

	}
}
