/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;

/**
 * "Leergut Ausgabe" / "Leergut Rücknahme" on the receipt-disposition delivery-planning window: creates the empties document and opens
 * it, exactly as {@code WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base} does on window 541954.
 * <p>
 * The one shape worth naming: this action is offered with NOTHING selected as well, and then creates an empty
 * draft instead of one derived from a row - which is why it reads the selection through
 * {@link ReceiptDispositionDeliveryPlanningViewBasedProcess#getSelectedReceiptScheduleOrNull()}.
 */
abstract class ReceiptDispositionDeliveryPlanningCreateEmptiesReturnsProcess extends ReceiptDispositionDeliveryPlanningPassThroughProcess
{
	private final String returnMovementType;
	private final AdWindowId targetWindowId;

	protected ReceiptDispositionDeliveryPlanningCreateEmptiesReturnsProcess(
			@NonNull final String returnMovementType,
			@NonNull final AdWindowId targetWindowId)
	{
		Check.assumeNotEmpty(returnMovementType, "returnMovementType is not empty");

		this.returnMovementType = returnMovementType;
		this.targetWindowId = targetWindowId;
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.accept();
		}
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt()
	{
		final int emptiesInOutId = actions.createEmptiesReturns(
				getCtx(), getSelectedReceiptScheduleOrNull(), returnMovementType, targetWindowId);

		// Notify frontend that the empties document shall be opened in single document layout (not grid)
		if (emptiesInOutId > 0)
		{
			getResult().setRecordToOpen(
					TableRecordReference.of(I_M_InOut.Table_Name, emptiesInOutId), targetWindowId, OpenTarget.SingleDocument);
		}

		return MSG_OK;
	}
}
