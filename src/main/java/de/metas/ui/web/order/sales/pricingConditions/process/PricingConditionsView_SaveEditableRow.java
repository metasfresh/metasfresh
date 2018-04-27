package de.metas.ui.web.order.sales.pricingConditions.process;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRow;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowsSaver;

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

public class PricingConditionsView_SaveEditableRow extends PricingConditionsViewBasedProcess
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getView().hasEditableRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("view does not have an editable row");
		}

		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a single selected row");
		}

		final PricingConditionsRow row = getSingleSelectedRow();
		if (!row.isEditable())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the editable row");
		}

		if (row.getDiscountSchemaId() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("row does not have a discountSchemaId defined; saving will fail later");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final int discountSchemaBreakId = PricingConditionsRowsSaver.builder()
				.row(getEditableRow())
				.build()
				.save();

		patchEditableRow(PricingConditionsRowChangeRequest.builder()
				.discountSchemaBreakId(discountSchemaBreakId)
				.build());

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}
}
