/*
 * #%L
 * de.metas.swat.webui
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

import java.time.Instant;

public class C_Order_Generate_Shipping_Notification_ProForma extends JavaProcess implements IProcessPrecondition
{
	private static final String PARAM_PhysicalClearanceDate = "PhysicalClearanceDate";
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Param(parameterName = PARAM_PhysicalClearanceDate, mandatory = true)
	private Instant p_physicalClearanceDate;

	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final OrderId salesOrderId = context.getSingleSelectedRecordId(OrderId.class);
		if(!orderBL.isProFormaSO(salesOrderId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No proForma order");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final OrderId salesOrderId = OrderId.ofRepoId(getRecord_ID());
		//TODO

		return MSG_OK;
	}
}
