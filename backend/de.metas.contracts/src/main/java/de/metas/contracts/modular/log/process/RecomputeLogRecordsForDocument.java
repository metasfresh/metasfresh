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

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.modular.log.LogsRecomputationService;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

public class RecomputeLogRecordsForDocument extends JavaProcess
{
	private final LogsRecomputationService recomputeLogsService = SpringContextHolder.instance.getBean(LogsRecomputationService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final String tableName = getTableName();

		if (tableName == null)
		{
			throw new AdempiereException("RecomputeLogRecordsForDocument was not properly setup! TableName is missing!");
		}

		switch (tableName)
		{
			case I_C_Invoice.Table_Name -> recomputeLogsService.recomputeForInvoice(getProcessInfo().getQueryFilterOrElseFalse());
			case I_C_Order.Table_Name -> recomputeLogsService.recomputeForOrder(getProcessInfo().getQueryFilterOrElseFalse());
			case I_M_InOut.Table_Name -> recomputeLogsService.recomputeForInOut(getProcessInfo().getQueryFilterOrElseFalse());
			case I_C_Flatrate_Term.Table_Name -> recomputeLogsService.recomputeForFlatrate(getProcessInfo().getQueryFilterOrElseFalse());
			case I_M_Inventory.Table_Name -> recomputeLogsService.recomputeForInventory(getProcessInfo().getQueryFilterOrElseFalse());
			case I_PP_Cost_Collector.Table_Name -> recomputeLogsService.recomputeForCostCollector(getProcessInfo().getQueryFilterOrElseFalse());
			case I_PP_Order.Table_Name -> recomputeLogsService.recomputeForPPOrder(getProcessInfo().getQueryFilterOrElseFalse());
			case I_M_Shipping_Notification.Table_Name -> recomputeLogsService.recomputeForShippingNotification(getProcessInfo().getQueryFilterOrElseFalse());
			case I_I_ModCntr_Log.Table_Name -> {/*Should never recompute import logs;*/}
			default -> throw new AdempiereException("Process is not supported for table name=" + tableName);
		}

		return MSG_OK;
	}
}
