/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package org.adempiere.ad.table.process;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.table.ChangeLogConfig;
import org.adempiere.ad.table.ChangeLogConfigRepository;
import org.adempiere.ad.table.ChangeLogEntryRepository;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

public class AD_ChangeLog_Delete_Old extends JavaProcess
{
	@NonNull private static final String DELETE_BATCH_SIZE = "org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old.deleteBatchSize";
	@NonNull private static final Logger logger = LogManager.getLogger(AD_ChangeLog_Delete_Old.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private final ChangeLogEntryRepository changeLogRepository = SpringContextHolder.instance.getBean(ChangeLogEntryRepository.class);
	@NonNull private final ChangeLogConfigRepository changeLogConfigRepository = SpringContextHolder.instance.getBean(ChangeLogConfigRepository.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final QueryLimit queryLimit = QueryLimit.ofInt(sysConfigBL.getIntValue(DELETE_BATCH_SIZE, 10000));
		final int limit = queryLimit.toInt();
		for (final ChangeLogConfig changeLogConfig : changeLogConfigRepository.retrieveChangeLogConfigList())
		{
			int deleteCount;
			do
			{
				deleteCount = trxManager.callInNewTrx(() -> changeLogRepository.deleteDirectly(changeLogConfig, queryLimit));
				Loggables.withLogger(logger, Level.DEBUG).addLog("Deleted {} change log entries for {}", deleteCount, changeLogConfig);
			}
			while (deleteCount >= limit);
		}
		return MSG_OK;
	}
}
