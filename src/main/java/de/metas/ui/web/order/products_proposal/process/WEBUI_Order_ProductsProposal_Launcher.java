package de.metas.ui.web.order.products_proposal.process;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.IDocument;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalViewFactory;
import de.metas.process.ProcessPreconditionsResolution;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class WEBUI_Order_ProductsProposal_Launcher extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("one and only one order shall be selected");
		}

		final I_C_Order salesOrder = context.getSelectedModel(I_C_Order.class);

		// NOTE: we allow sales and purchase orders too; see https://github.com/metasfresh/metasfresh/issues/4017

		final String docStatus = salesOrder.getDocStatus();
		if (!IDocument.STATUS_Drafted.equals(docStatus))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only draft orders are allowed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final TableRecordReference orderRef = TableRecordReference.of(getTableName(), getRecord_ID());
		getResult().setRecordToOpen(orderRef, ProductsProposalViewFactory.WINDOW_ID_STRING, OpenTarget.GridView);

		return MSG_OK;
	}

}
