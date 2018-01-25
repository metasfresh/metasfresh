package de.metas.ui.web.order.sales.purchasePlanning.view;

import de.metas.ui.web.pporder.PPOrderLineType;
import de.metas.ui.web.view.IViewRowType;

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

public enum PurchaseRowType implements IViewRowType
{
	GROUP(PPOrderLineType.MainProduct.getIconName()), //
	LINE(PPOrderLineType.BOMLine_Component.getIconName()), //
	AVAILABILITY_DETAIL(PPOrderLineType.BOMLine_ByCoProduct.getIconName());

	private final String iconName;

	PurchaseRowType(final String iconName)
	{
		this.iconName = iconName;
	}

	@Override
	public String getName()
	{
		return iconName;
	}

	@Override
	public String getIconName()
	{
		return iconName;
	}
}
