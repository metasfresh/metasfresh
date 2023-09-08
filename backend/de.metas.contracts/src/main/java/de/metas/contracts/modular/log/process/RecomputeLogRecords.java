/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.log.process;

import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import java.util.Iterator;

public class RecomputeLogRecords extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);

	private final ModularContractService modularContractService = SpringContextHolder.instance.getBean(ModularContractService.class);

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_ModCntr_Log> logsIterator = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.filter(getProcessInfo().getQueryFilterOrElseFalse())
				.orderBy()
				.addColumn(I_ModCntr_Log.COLUMNNAME_AD_Table_ID)
				.addColumn(I_ModCntr_Log.COLUMNNAME_Record_ID)
				.endOrderBy()
				.create()
				.iterate(I_ModCntr_Log.class);

		TableRecordReference currentRecordReference = null;
		while (logsIterator.hasNext())
		{
			final I_ModCntr_Log logEntry = logsIterator.next();
			final TableRecordReference logRecordRef = TableRecordReference.of(logEntry.getAD_Table_ID(), logEntry.getRecord_ID());

			if (currentRecordReference == null)
			{
				currentRecordReference = TableRecordReference.of(logEntry.getAD_Table_ID(), logEntry.getRecord_ID());
			}

			if (!logRecordRef.equals(currentRecordReference) || !logsIterator.hasNext())
			{
				recomputeForRecord(currentRecordReference);
				currentRecordReference = logRecordRef;
			}
		}

		return MSG_OK;
	}

	private void recomputeForRecord(@NonNull final TableRecordReference tableRecordReference)
	{
		switch (tableRecordReference.getTableName())
		{
			case I_PP_Order.Table_Name -> recomputeForPPOrder(tableRecordReference);
			default -> trxManager.runInNewTrx(() -> modularContractService
					.invokeWithModelForAllContractTypes(tableRecordReference.getModel(), ModelAction.RECREATE_LOGS));
		}
	}

	private void recomputeForPPOrder(@NonNull final TableRecordReference tableRecordReference)
	{
		trxManager.runInNewTrx(() -> ppCostCollectorDAO
				.getReceiptsByOrderId(tableRecordReference.getIdAssumingTableName(I_PP_Order.Table_Name, PPOrderId::ofRepoId))
				.forEach(ppCostCollector -> modularContractService.invokeWithModelForAllContractTypes(ppCostCollector,
																									  ModelAction.RECREATE_LOGS)));
	}
}
