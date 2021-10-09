package de.metas.ui.web.order.sales.purchasePlanning.process;

import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseRowChangeRequest;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseRowId;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseView;
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

public abstract class PurchaseViewBasedProcess
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Override
	protected final PurchaseView getView()
	{
		return PurchaseView.cast(super.getView());
	}

	protected final void patchViewRow(final PurchaseRowId rowId, final PurchaseRowChangeRequest rowChangeRequest)
	{
		getView().patchViewRow(rowId, rowChangeRequest);
	}

}
