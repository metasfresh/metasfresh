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

package de.metas.shippingnotification.process;

import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Order;

public class C_Order_Generate_Shipment_Notification extends JavaProcess implements IProcessPrecondition
{

	public static final AdMessageKey MSG_M_Shipment_Notification_NoHarvestingYear = AdMessageKey.of("de.metas.shippingnotification.NoHarvestingYear");

	final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

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

		final OrderId orderId = context.getSingleSelectedRecordId(OrderId.class);
		final I_C_Order order = orderDAO.getById(orderId);
		if (!DocStatus.ofNullableCodeOrUnknown(order.getDocStatus()).isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only completed orders");
		}

		if (order.getHarvesting_Year_ID() <= 0)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_M_Shipment_Notification_NoHarvestingYear));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Order order = getProcessInfo().getRecord(I_C_Order.class);

		// deliveryPlanningService.generateDeliveryInstructions(selectedDeliveryPlanningsFilter);

		return MSG_OK;

	}
}
