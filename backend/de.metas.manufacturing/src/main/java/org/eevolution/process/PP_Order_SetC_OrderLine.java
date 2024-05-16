package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;

public class PP_Order_SetC_OrderLine extends JavaProcess implements IProcessPrecondition
{
	// services
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderDAO ppOrdersRepo= Services.get(IPPOrderDAO.class);

	@Param(parameterName = I_C_OrderLine.COLUMNNAME_C_OrderLine_ID, mandatory = true)
	private int p_C_OrderLine_ID;


	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.reject();
		}


		final I_PP_Order ppOrder = ppOrderBL.getById(getPPOrderId(context.getSingleSelectedRecordId()));
		return ProcessPreconditionsResolution.acceptIf(isEligible(ppOrder));
	}

	private static boolean isEligible(@Nullable final I_PP_Order ppOrder)
	{
		if (ppOrder == null)
		{
			return false;
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(ppOrder.getDocStatus());
		return docStatus.isDraftedOrInProgress();
	}

	@Override
	protected String doIt()
	{
		final PPOrderId ppOrderId = getPPOrderId(getRecord_ID());
		final OrderLineId orderLineId = getOrderLienId();
		setC_OrderLine_ID(ppOrderId, orderLineId);

		return MSG_OK;
	}

	private PPOrderId getPPOrderId(int ppOrderId)
	{
		return PPOrderId.ofRepoId(ppOrderId);
	}

	private OrderLineId getOrderLienId()
	{
		return OrderLineId.ofRepoId(p_C_OrderLine_ID);
	}

	private void setC_OrderLine_ID(@NonNull final PPOrderId ppOrderId, @NonNull final OrderLineId orderLineId)
	{
		ppOrderBL.setC_OrderLine(ppOrderId, orderLineId);
	}
}
