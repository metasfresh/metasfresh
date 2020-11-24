/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.marketing.base.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_AD_User;
import de.metas.marketing.base.api.IMKTGChannelDao;
import de.metas.marketing.base.model.I_AD_User_MKTG_Channels;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;

public class MKTGChannelDao implements IMKTGChannelDao
{
	@Override
	public int retrieveMarketingChannelsCountForUser(final UserId userId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_MKTG_Channels.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, userId)
				.create()
				.count();
	}

	public ImmutableSet<UserId> retrieveUsersHavingChannel(final int marketingChannelId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_MKTG_Channels.class)
				.addEqualsFilter(I_AD_User_MKTG_Channels.COLUMNNAME_MKTG_Channel_ID, marketingChannelId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_AD_User_MKTG_Channels.COLUMNNAME_AD_User_ID, Integer.class)
				.stream()
				.map(UserId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
