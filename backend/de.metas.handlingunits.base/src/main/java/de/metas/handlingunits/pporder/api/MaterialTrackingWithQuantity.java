package de.metas.handlingunits.pporder.api;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Value
public class MaterialTrackingWithQuantity
{
	I_M_Material_Tracking materialTrackingRecord;

	/** It's OK to add quantities; we don't sum them up in here*/
	List<Quantity> quantities;

	public MaterialTrackingWithQuantity(@NonNull final I_M_Material_Tracking materialTrackingRecord)
	{
		this.materialTrackingRecord = materialTrackingRecord;

		this.quantities = new ArrayList<Quantity>();
	}

	public void addQuantity(Quantity quantity)
	{
		quantities.add(quantity);
	}

	public ImmutableList<Quantity> getQuantities()
	{
		return ImmutableList.copyOf(quantities);
	}
}
