package de.metas.ui.web.pporder.process;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderConstants;

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

public class WEBUI_PP_Order_IssueReceipt_Launcher extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (PPOrderConstants.AD_WINDOW_ID_IssueReceipt.toInt() == context.getAD_Window_ID())
		{
			// we did already launch the IssueReceipt window
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already within the window " + PPOrderConstants.AD_WINDOW_ID_IssueReceipt);
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_PP_Order ppOrder = context.getSelectedModel(I_PP_Order.class);
		if (!X_PP_Order.DOCSTATUS_Completed.equals(ppOrder.getDocStatus()))
		{
			return ProcessPreconditionsResolution.reject("not completed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final TableRecordReference ppOrderRef = TableRecordReference.of(I_PP_Order.Table_Name, getRecord_ID());
		getResult().setRecordToOpen(ppOrderRef, PPOrderConstants.AD_WINDOW_ID_IssueReceipt.toInt(), OpenTarget.GridView);
		return MSG_OK;
	}

}
