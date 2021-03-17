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
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.remittanceadvice.RemittanceAdviceService;
import de.metas.util.Loggables;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_RemittanceAdvice;
import org.slf4j.Logger;

import java.util.List;

public class C_RemittanceAdvice_Resolve extends JavaProcess
{
	private static final Logger logger = LogManager.getLogger(C_RemittanceAdvice_CreateAndAllocatePayment.class);

	final RemittanceAdviceRepository remittanceAdviceRepo = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);
	final RemittanceAdviceService remittanceAdviceService = SpringContextHolder.instance.getBean(RemittanceAdviceService.class);

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_RemittanceAdvice> processFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));
		if (processFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		final List<RemittanceAdvice> remittanceAdvices = remittanceAdviceRepo.getRemittanceAdvices(processFilter);

		for (final RemittanceAdvice remittanceAdvice : remittanceAdvices)
		{
			try
			{
				trxManager.runInNewTrx(() ->{
					remittanceAdviceService.resolveRemittanceAdviceLines(remittanceAdvice);
					remittanceAdviceRepo.updateRemittanceAdvice(remittanceAdvice);
				});
			}
			catch (final Exception exception)
			{
				Loggables.withLogger(logger, Level.ERROR)
						.addLog("Resolve invoice failed for remittanceAdvice: {}, reason: {}",
								remittanceAdvice.getRemittanceAdviceId(), exception.getLocalizedMessage());
			}
		}

		return MSG_OK;
	}
}
