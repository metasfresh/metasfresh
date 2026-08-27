package org.eevolution.process;

/*
 * #%L
 * de.metas.manufacturing
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

import de.metas.costing.methods.PPOrderCostDifferenceDistributor;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;

/**
 * Discharges the WIP cost residual of a manufacturing order, offered only while it is completed and
 * not yet closed.
 */
public class PP_Order_PostCalculation extends JavaProcess implements IProcessPrecondition
{
	private final PPOrderCostDifferenceDistributor costDifferenceDistributor = SpringContextHolder.instance.getBean(PPOrderCostDifferenceDistributor.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final I_PP_Order ppOrder = context.getSelectedModel(I_PP_Order.class);
		return ProcessPreconditionsResolution.acceptIf(isEligible(ppOrder));
	}

	private static boolean isEligible(@Nullable final I_PP_Order ppOrder)
	{
		if (ppOrder == null)
		{
			return false;
		}

		// Distributing closes the order, so this also withdraws the action once the residual has been
		// discharged. After a PP_Order_UnClose the action is offered again, which is correct: the discharged
		// residual is booked into PP_Order_Cost, so a run without further activity finds nothing to discharge.
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(ppOrder.getDocStatus());
		return docStatus.isCompleted() && !docStatus.isClosed();
	}

	@Override
	protected String doIt()
	{
		final PPOrderId ppOrderId = getPPOrderId();

		costDifferenceDistributor.distribute(ppOrderId);

		return MSG_OK;
	}

	private PPOrderId getPPOrderId()
	{
		Check.assumeEquals(getTableName(), I_PP_Order.Table_Name, "TableName");
		return PPOrderId.ofRepoId(getRecord_ID());
	}
}
