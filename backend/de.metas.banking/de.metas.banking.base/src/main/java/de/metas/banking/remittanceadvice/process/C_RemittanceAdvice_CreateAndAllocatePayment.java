/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.banking.remittanceadvice.process;

import ch.qos.logback.classic.Level;
import de.metas.banking.service.RemittanceAdviceBankingService;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_RemittanceAdvice;
import org.slf4j.Logger;

import java.util.List;

public class C_RemittanceAdvice_CreateAndAllocatePayment extends JavaProcess
{
	private static final Logger logger = LogManager.getLogger(C_RemittanceAdvice_CreateAndAllocatePayment.class);

	private final RemittanceAdviceRepository remittanceAdviceRepo = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);
	private final RemittanceAdviceBankingService remittanceAdviceBankingService =  SpringContextHolder.instance.getBean(RemittanceAdviceBankingService.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_RemittanceAdvice> processFilter = getProcessInfo().getQueryFilterOrElse(null);
		if (processFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}
		final List<RemittanceAdvice> remittanceAdvices = remittanceAdviceRepo.getRemittanceAdvices(processFilter);

		for (final RemittanceAdvice remittanceAdvice : remittanceAdvices)
		{
			try
			{
				trxManager.runInNewTrx(() -> remittanceAdviceBankingService.runForRemittanceAdvice(remittanceAdvice));
			}
			catch (final Exception e)
			{
				Loggables.withLogger(logger, Level.WARN).addLog("*** ERROR: failed to create and allocate payments for C_RemittanceAdvice_ID={} ", RemittanceAdviceId.toRepoId(remittanceAdvice.getRemittanceAdviceId()));
				throw AdempiereException.wrapIfNeeded(e)
						.appendParametersToMessage().setParameter("C_RemittanceAdvice_ID", RemittanceAdviceId.toRepoId(remittanceAdvice.getRemittanceAdviceId()));
			}
		}

		return MSG_OK;
	}
	
}
