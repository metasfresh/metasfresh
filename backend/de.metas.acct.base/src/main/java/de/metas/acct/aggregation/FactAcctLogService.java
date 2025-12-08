/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.acct.aggregation;

import de.metas.acct.aggregation.legacy.FactAcctLogProcessResult;
import de.metas.acct.aggregation.legacy.impl.LegacyFactAcctLogProcessor;
import de.metas.util.Services;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

@Service
public class FactAcctLogService
{
	private static final String SYSCONFIG_IsUseLegacyProcessor = "FactAcctLogService.useLegacyProcessor";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public FactAcctLogProcessResult processAll(final QueryLimit limit)
	{
		return trxManager.callInNewTrx(() -> processAllInTrx(limit));
	}

	private FactAcctLogProcessResult processAllInTrx(final QueryLimit limit)
	{
		if (isUseLegacyProcessor())
		{
			return new LegacyFactAcctLogProcessor().processAll(limit);
		}
		else
		{
			final int count = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, "SELECT de_metas_acct.fact_acct_log_process(?)", limit.toInt());
			return FactAcctLogProcessResult.builder()
					.iterations(1)
					.processedLogRecordsCount(count)
					.build();
		}
	}

	private boolean isUseLegacyProcessor()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_IsUseLegacyProcessor, true); // default to true for backwards compatibility
	}
}