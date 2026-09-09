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
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;
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
	/** Shown instead of the action when the order received value with no component ever issued. */
	private static final AdMessageKey MSG_NoComponentIssued = AdMessageKey.of("org.eevolution.process.PP_Order_PostCalculation.NoComponentIssued");

	@NonNull private final PPOrderCostDifferenceDistributor costDifferenceDistributor = SpringContextHolder.instance.getBean(PPOrderCostDifferenceDistributor.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		return isEligible(context.getSelectedModel(I_PP_Order.class));
	}

	private ProcessPreconditionsResolution isEligible(@Nullable final I_PP_Order ppOrder)
	{
		if (ppOrder == null)
		{
			return ProcessPreconditionsResolution.reject();
		}

		// Distributing closes the order, so this withdraws the action once the residual is discharged. After a
		// PP_Order_UnClose it is offered again, correctly: a run without further activity finds nothing to discharge.
		// Completed and Closed are distinct statuses, so isCompleted() alone already excludes a closed order.
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(ppOrder.getDocStatus());
		if (!docStatus.isCompleted() || !costDifferenceDistributor.hasOrderCosts(ppOrder))
		{
			return ProcessPreconditionsResolution.reject();
		}

		// Refused WITH a reason, not hidden: the order does show a difference, so the user needs to be told
		// that what it shows is an un-issued receipt.
		if (!costDifferenceDistributor.hasInboundCosts(ppOrder))
		{
			return ProcessPreconditionsResolution.reject(MSG_NoComponentIssued);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		// Distributing closes the order, so it leaves the monitor only if the selection is rebuilt. The
		// Swing client knows only the coarser flag, so set both.
		getResult().setRecreateViewSelectionAfterExecution(true);
		getResult().setRefreshAllAfterExecution(true);

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
