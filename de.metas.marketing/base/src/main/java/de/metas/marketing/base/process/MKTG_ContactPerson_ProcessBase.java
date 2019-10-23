package de.metas.marketing.base.process;

import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * marketing-base
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
public class MKTG_ContactPerson_ProcessBase
{
	private UserRepository userRepository;
	private CampaignService campaignService;

	public MKTG_ContactPerson_ProcessBase(@NonNull UserRepository userRepository, @NonNull CampaignService campaignService)
	{
		this.userRepository = userRepository;
		this.campaignService = campaignService;
	}

	public void createContactPersonsForUser(final IQueryFilter<I_AD_User> currentSelectionFilter, final CampaignId campaignId, final DefaultAddressType defaultAddressType)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_MKTG_Campaign_ContactPerson> linkTableQuery = queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create();

		final Stream<User> usersToAdd = queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.filter(currentSelectionFilter)
				.addNotInSubQueryFilter(I_AD_User.COLUMN_AD_User_ID, I_MKTG_Campaign_ContactPerson.COLUMN_AD_User_ID, linkTableQuery)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterateAndStream()
				.map(userRepository::ofRecord);

		campaignService.addAsContactPersonsToCampaign(
				usersToAdd,
				campaignId,
				defaultAddressType);
	}

	public void createContactPersonsForPartner(final MKTG_ContactPerson_ProcessParams params)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final CampaignId campaignId = params.getCampaignId();

		if (params.isRemoveAllExistingContactsFromCampaign())
		{
			campaignService.removeContactPersonsFromCampaign(campaignId);
		}

		final IQuery<I_MKTG_Campaign_ContactPerson> linkTableQuery = queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create();

		final Stream<User> usersToAdd = queryBL
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.filter(params.getSelectionFilter())
				.andCollectChildren(I_AD_User.COLUMN_C_BPartner_ID)
				.addNotInSubQueryFilter(I_AD_User.COLUMN_AD_User_ID, I_MKTG_Campaign_ContactPerson.COLUMN_AD_User_ID, linkTableQuery)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterateAndStream()
				.map(userRepository::ofRecord);

		campaignService.addAsContactPersonsToCampaign(
				usersToAdd,
				campaignId,
				params.getAddresType());

	}
}
