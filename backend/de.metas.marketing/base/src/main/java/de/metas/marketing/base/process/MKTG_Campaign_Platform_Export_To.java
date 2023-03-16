package de.metas.marketing.base.process;

import de.metas.marketing.base.PlatformSyncService;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.SyncDirection;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.Process;
import org.compiere.SpringContextHolder;

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

@Process(requiresCurrentRecordWhenCalledFromGear = false)
public class MKTG_Campaign_Platform_Export_To extends JavaProcess
{
	private final PlatformSyncService syncService = SpringContextHolder.instance.getBean(PlatformSyncService.class);

	@Param(mandatory = true, parameterName = I_MKTG_Platform.COLUMNNAME_MKTG_Platform_ID)
	private PlatformId platformId;

	@Override
	protected String doIt()
	{
		syncService.syncCampaigns(platformId, SyncDirection.LOCAL_TO_REMOTE);
		return MSG_OK;
	}
}
