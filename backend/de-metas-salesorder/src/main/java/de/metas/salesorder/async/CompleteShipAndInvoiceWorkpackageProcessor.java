/*
 * #%L
 * de-metas-salesorder
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

package de.metas.salesorder.async;

import ch.qos.logback.classic.Level;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.salesorder.service.AutoProcessingOrderService;
import de.metas.util.Check;
import de.metas.util.Loggables;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import static de.metas.salesorder.async.CompleteShipAndInvoiceEnqueuer.WP_PARAM_C_Order_ID;

public class CompleteShipAndInvoiceWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private final static Logger logger = LogManager.getLogger(CompleteShipAndInvoiceWorkpackageProcessor.class);

	private final AutoProcessingOrderService autoProcessingOrderService = SpringContextHolder.instance.getBean(AutoProcessingOrderService.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final OrderId orderId = getParameters().getParameterAsId(WP_PARAM_C_Order_ID, OrderId.class);
		Check.assumeNotNull(orderId, "OrderId not null");

		try
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Complete ship and invoice for orderId: {}", orderId);
			autoProcessingOrderService.completeShipAndInvoice(orderId);
		}
		catch (final Exception ex)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog("@Error@: " + ex.getLocalizedMessage());
			throw AdempiereException.wrapIfNeeded(ex);
		}

		return Result.SUCCESS;
	}
}
