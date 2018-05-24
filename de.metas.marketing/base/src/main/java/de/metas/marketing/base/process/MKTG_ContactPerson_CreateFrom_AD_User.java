package de.metas.marketing.base.process;

import java.util.stream.Stream;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.user.User;
import org.adempiere.user.UserRepository;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;

import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

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

public class MKTG_ContactPerson_CreateFrom_AD_User extends JavaProcess
{
	@Param(mandatory = true, parameterName = I_MKTG_Campaign.COLUMNNAME_MKTG_Campaign_ID)
	private int campaignId;

	private final CampaignService tools = Adempiere.getBean(CampaignService.class);
	private final UserRepository userRepository = Adempiere.getBean(UserRepository.class);

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_AD_User> currentSelectionFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(true));

		final IQuery<I_MKTG_Campaign_ContactPerson> linkTableQuery = Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create();

		final Stream<User> usersToAdd = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.filter(currentSelectionFilter)
				.addNotInSubQueryFilter(I_AD_User.COLUMN_AD_User_ID, I_MKTG_Campaign_ContactPerson.COLUMN_AD_User_ID, linkTableQuery)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterateAndStream()
				.map(userRepository::ofRecord);

		tools.addAsContactPersonsToCampaign(
				usersToAdd,
				CampaignId.ofRepoId(campaignId));
		return MSG_OK;
	}
}
