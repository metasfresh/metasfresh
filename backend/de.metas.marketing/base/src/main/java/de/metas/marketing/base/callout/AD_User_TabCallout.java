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

package de.metas.marketing.base.callout;

import de.metas.i18n.AdMessageKey;
import de.metas.marketing.base.api.IMKTGChannelDao;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_User;

public class AD_User_TabCallout extends TabCalloutAdapter
{
	private final IMKTGChannelDao mktgChannelDao = Services.get(IMKTGChannelDao.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final AdMessageKey MSG_CAN_NOT_REMOVE_CHANNEL = AdMessageKey.of("de.metas.marketing.base.userMarketingChannelRemovalError");
	private static final String SYS_CONFIG_MARKETING_CHANNELS_ENFORCED = "de.metas.marketing.EnforceUserHasMarketingChannels";

	@Override
	public void onSave(final ICalloutRecord calloutRecord)
	{
		final I_AD_User user = calloutRecord.getModel(I_AD_User.class);

		if (!isMarketingChannelsUseEnforced(user.getAD_Client_ID(), user.getAD_Org_ID()))
		{
			return;
		}

		if (userDAO.isSystemUser(UserId.ofRepoId(user.getAD_User_ID())))
		{
			return;
		}

		boolean canBeSaved = true;
		if (mktgChannelDao.retrieveMarketingChannelsCountForUser(UserId.ofRepoId(user.getAD_User_ID())) <= 0)
		{
			canBeSaved = false;
		}

		if (!canBeSaved)
		{
			throw new AdempiereException(MSG_CAN_NOT_REMOVE_CHANNEL).markAsUserValidationError();
		}
	}

	private boolean isMarketingChannelsUseEnforced(int clientID, int orgID)
	{
		return sysConfigBL.getBooleanValue(SYS_CONFIG_MARKETING_CHANNELS_ENFORCED, false, clientID, orgID);
	}
}
