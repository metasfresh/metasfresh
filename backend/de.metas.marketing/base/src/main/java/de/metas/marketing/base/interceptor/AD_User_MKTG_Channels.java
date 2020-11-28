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
import de.metas.marketing.base.model.I_AD_User_MKTG_Channels;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

@Callout(I_AD_User_MKTG_Channels.class)
@Interceptor(I_AD_User_MKTG_Channels.class)
public class AD_User_MKTG_Channels
{
	public static final AD_User_MKTG_Channels INSTANCE = new AD_User_MKTG_Channels();

	private final IMKTGChannelDao mktgChannelDao = Services.get(IMKTGChannelDao.class);

	private static final AdMessageKey MSG_CAN_NOT_REMOVE_CHANNEL = AdMessageKey.of("de.metas.marketing.base.userMarketingChannelRemovalError");

	private AD_User_MKTG_Channels()
	{
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void checkIfCanBeDeleted(@NonNull final I_AD_User_MKTG_Channels userMktgChannel)
	{
		final int count = mktgChannelDao.retrieveMarketingChannelsCountForUser(UserId.ofRepoId(userMktgChannel.getAD_User_ID()));
		if (count == 1)
		{
			throw new AdempiereException(MSG_CAN_NOT_REMOVE_CHANNEL).markAsUserValidationError();
		}
	}
}
