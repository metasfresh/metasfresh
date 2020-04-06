package de.metas.ui.web.pickingV2.productsToPick.rows;

import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRowTypeIconNames;

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

public enum ProductsToPickRowType implements IViewRowType
{
	PICK_FROM_HU(ViewRowTypeIconNames.ICONNAME_PP_Order_Receive),

	UNALLOCABLE(null),

	PICK_FROM_PICKING_ORDER(ViewRowTypeIconNames.ICONNAME_PP_Order_Receive),

	ISSUE_COMPONENTS_TO_PICKING_ORDER(ViewRowTypeIconNames.ICONNAME_PP_Order_Issue);

	private final String iconName;

	ProductsToPickRowType(final String iconName)
	{
		this.iconName = iconName;
	}

	@Override
	public String getName()
	{
		return name();
	}

	@Override
	public String getIconName()
	{
		return iconName;
	}

	public boolean isPickable()
	{
		return this == PICK_FROM_HU
				|| this == ProductsToPickRowType.PICK_FROM_PICKING_ORDER;
	}
}
