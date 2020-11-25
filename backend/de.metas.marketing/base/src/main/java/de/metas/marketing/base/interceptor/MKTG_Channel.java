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

package de.metas.marketing.base.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.marketing.base.api.IMKTGChannelDao;
import de.metas.marketing.base.model.I_MKTG_Channel;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import java.util.Set;

@Callout(I_MKTG_Channel.class)
@Interceptor(I_MKTG_Channel.class)
public class MKTG_Channel
{
	public static final MKTG_Channel INSTANCE = new MKTG_Channel();

	private final IMKTGChannelDao mktgChannelDao = Services.get(IMKTGChannelDao.class);

	private static final AdMessageKey MSG_CAN_NOT_DELETE = AdMessageKey.of("de.metas.marketing.base.marketingChannelRemovalError");

	private MKTG_Channel()
	{
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void checkIfCanBeDeleted(@NonNull final I_MKTG_Channel mktgChannel)
	{
		boolean canBeDeleted = true;
		Set<UserId> usersSet = mktgChannelDao.retrieveUsersHavingChannel(mktgChannel.getMKTG_Channel_ID());
		for (UserId userId : usersSet)
		{
			if (mktgChannelDao.retrieveMarketingChannelsCountForUser(userId) == 1)
			{
				canBeDeleted = false;
				break;
			}
		}
		if (!canBeDeleted)
		{
			throw new AdempiereException(MSG_CAN_NOT_DELETE).markAsUserValidationError();
		}

	}
}
