package de.metas.ui.web.order.pricingconditions.process;

import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRow;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

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

abstract class PricingConditionsViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected final PricingConditionsView getView()
	{
		return PricingConditionsView.cast(super.getView());
	}

	@Override
	protected final PricingConditionsRow getSingleSelectedRow()
	{
		return PricingConditionsRow.cast(super.getSingleSelectedRow());
	}

	public PricingConditionsRow getEditableRow()
	{
		return getView().getEditableRow();
	}

	public void patchEditableRow(final PricingConditionsRowChangeRequest request)
	{
		getView().patchEditableRow(request);
	}
}
