package de.metas.server.housekeep;

import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Component;

import de.metas.security.RoleId;
import de.metas.security.UserAuthTokenRepository;
import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
public class ResetJsonReportsAuthTokenHouseKeepingTask implements IStartupHouseKeepingTask
{

	private final UserAuthTokenRepository  userAuthTokenRepo = SpringContextHolder.instance.getBean(UserAuthTokenRepository.class);

	@Override
	public void executeTask()
	{
		userAuthTokenRepo.resetAuthTokensAndSave(UserId.JSON_REPORTS, RoleId.JSON_REPORTS);
	}
}
