/*
 * #%L
 * de.metas.swat.base
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

package de.metas.order.process;

import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_Order;

import java.util.List;

public class C_Order_MarkAsFullyDelivered extends JavaProcess implements IProcessPrecondition
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final String PARAM_MarkAsFullyDelivered = "MarkAsFullyDelivered";
	@Param(parameterName = PARAM_MarkAsFullyDelivered)
	private Boolean markAsFullyDelivered;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Order order = orderBL.getById(orderId);

		if (!DocStatus.ofNullableCodeOrUnknown(order.getDocStatus()).isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Order is not completed.");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		if (markAsFullyDelivered == null)
		{
			throw new FillMandatoryException(PARAM_MarkAsFullyDelivered);
		}

		final OrderId orderId = OrderId.ofRepoId(getRecord_ID());
		final List<I_C_OrderLine> orderLines = orderBL.getLinesByOrderId(orderId);
		orderLines.forEach(this::processLine);

		return MSG_OK;
	}

	private void processLine(final I_C_OrderLine orderLine)
	{
		if (markAsFullyDelivered)
		{
			orderBL.closeLine(orderLine);
		}
		else
		{
			orderBL.reopenLine(orderLine);
		}
	}
}
