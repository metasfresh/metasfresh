/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipping.process;

import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.util.Set;

public abstract class AddOrderLinesToShipperTransportation extends JavaProcess implements IProcessPrecondition
{
	public static final int MAX_SELECTION_SIZE = 100;

	private final PurchaseOrderToShipperTransportationService orderToShipperTransportationService = SpringContextHolder.instance.getBean(PurchaseOrderToShipperTransportationService.class);

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
	private ShipperTransportationId p_M_ShipperTransportation_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanAllowedSelected(MAX_SELECTION_SIZE))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(MAX_SELECTION_SIZE);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<OrderLineId> orderLineIds = getOrderLineIds();
		orderToShipperTransportationService.deleteShippingPackagesForOrderLines(orderLineIds, getFailureMessage());

		orderToShipperTransportationService.addOrderLinesToShipperTransportation(p_M_ShipperTransportation_ID, orderLineIds);

		return MSG_OK;
	}

	protected abstract Set<OrderLineId> getOrderLineIds();

	protected abstract AdMessageKey getFailureMessage();

}
