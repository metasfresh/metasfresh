package de.metas.ui.web.order.pricingconditions.process;

import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.pricingconditions.view.OrderLinePricingConditionsViewFactory;
import de.metas.util.collections.CollectionUtils;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_SalesOrder_PricingConditionsView_Launcher extends JavaProcess implements IProcessPrecondition
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

		final DocStatus docStatus = DocStatus.ofCode(salesOrder.getDocStatus());
		if (!docStatus.isDrafted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only draft orders are allowed");
		}

		// Make sure only one line is selected
		final Set<TableRecordReference> selectedOrderLineRefs = context.getSelectedIncludedRecords();
		if (selectedOrderLineRefs.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (selectedOrderLineRefs.size() > 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final Set<TableRecordReference> salesOrderLineRefs = getSelectedIncludedRecordIds(I_C_OrderLine.class)
				.stream()
				.map(recordId -> TableRecordReference.of(I_C_OrderLine.Table_Name, recordId))
				.collect(ImmutableSet.toImmutableSet());
		CollectionUtils.singleElement(salesOrderLineRefs);

		getResult().setRecordsToOpen(salesOrderLineRefs, OrderLinePricingConditionsViewFactory.WINDOW_ID_STRING);

		return MSG_OK;
	}
}
