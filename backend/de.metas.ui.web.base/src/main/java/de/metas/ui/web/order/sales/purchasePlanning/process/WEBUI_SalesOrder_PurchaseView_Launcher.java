package de.metas.ui.web.order.sales.purchasePlanning.process;

import java.util.Set;

import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.sales.purchasePlanning.view.SalesOrder2PurchaseViewFactory;

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

public class WEBUI_SalesOrder_PurchaseView_Launcher
		extends JavaProcess
		implements IProcessPrecondition
{

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final I_C_Order salesOrder = orderDAO.getById(OrderId.ofRepoId(context.getSingleSelectedRecordId()), I_C_Order.class);
		if (salesOrder == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only existing records that were saved at least once are allowed");
		}
		if (!salesOrder.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only sales orders are allowed");
		}

		final DocStatus docStatus = DocStatus.ofCode(salesOrder.getDocStatus());
		if (!docStatus.isDrafted())
		{
			return ProcessPreconditionsResolution
					.rejectWithInternalReason("only draft orders are allowed");
		}

		// At least one sales order line shall be selected
		final Set<TableRecordReference> selectedOrderLineRefs = //
				context.getSelectedIncludedRecords();
		if (selectedOrderLineRefs.isEmpty())
		{
			return ProcessPreconditionsResolution
					.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final Set<TableRecordReference> salesOrderLineRefs = //
				getSelectedIncludedRecordIds(I_C_OrderLine.class)
						.stream()
						.map(recordId -> TableRecordReference.of(
								I_C_OrderLine.Table_Name,
								recordId))
						.collect(ImmutableSet.toImmutableSet());

		if (salesOrderLineRefs.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		getResult().setRecordsToOpen(
				salesOrderLineRefs,
				SalesOrder2PurchaseViewFactory.WINDOW_ID_STRING);

		return MSG_OK;
	}

}
