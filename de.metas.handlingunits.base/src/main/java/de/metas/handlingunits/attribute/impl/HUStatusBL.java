package de.metas.handlingunits.attribute.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.attribute.IHUStatusBL;
import de.metas.handlingunits.model.X_M_HU;

public class HUStatusBL implements IHUStatusBL
{
	private final static List<String> HUSTATUSES_QtyOnHand = ImmutableList.of(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Picked);

	@Override
	public boolean isQtyOnHand(final String huStatus)
	{
		return HUSTATUSES_QtyOnHand.contains(huStatus);
	}

	@Override
	public List<String> getQtyOnHandStatuses()
	{
		return HUSTATUSES_QtyOnHand;
	}
}
