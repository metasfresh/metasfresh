package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ abstract class WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base extends ReceiptScheduleBasedProcess
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.accept();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	// package-visible, non-final so a same-package unit test can substitute it; shared with the
	// receipt-logistics window's adapter, which must create the very same empties document.
	ReceiptScheduleActions actions = ReceiptScheduleActions.newInstance();

	private final String _returnMovementType;
	private final AdWindowId _targetWindowId;

	public WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base(@NonNull final String returnMovementType, @NonNull final AdWindowId targetWindowId)
	{
		Check.assumeNotEmpty(returnMovementType, "returnMovementType is not empty");

		_returnMovementType = returnMovementType;
		_targetWindowId = targetWindowId;

	}

	private String getReturnMovementType()
	{
		return _returnMovementType;
	}

	private AdWindowId getTargetWindowId()
	{
		return _targetWindowId;
	}

	@Override
	protected String doIt()
	{
		final I_M_ReceiptSchedule receiptSchedule = getProcessInfo().getRecordIfApplies(I_M_ReceiptSchedule.class, ITrx.TRXNAME_ThreadInherited).orElse(null);

		final int emptiesInOutId = actions.createEmptiesReturns(getCtx(), receiptSchedule, getReturnMovementType(), getTargetWindowId());

		//
		// Notify frontend that the empties document shall be opened in single document layout (not grid)
		if (emptiesInOutId > 0)
		{
			getResult().setRecordToOpen(TableRecordReference.of(I_M_InOut.Table_Name, emptiesInOutId), getTargetWindowId(), OpenTarget.SingleDocument);
		}

		return MSG_OK;
	}

}
