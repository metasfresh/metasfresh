package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;

/**
 * The CUs of a TU that share one attribute combination (as far as it splits the shipment line), in the order the TU lists them,
 * which follows pick order. Never empty.
 */
@Value
public class TUContentGroup
{
	@NonNull ImmutableList<TUContentCU> cus;

	public TUContentGroup(@NonNull final ImmutableList<TUContentCU> cus)
	{
		Check.assume(!cus.isEmpty(), "A TU content group has at least one CU");
		// the CUs are added up (totalQty) and their UOM stands for the whole group (stockingUOM), so they must count in one UOM
		Check.assume(cus.stream().map(cu -> cu.getQty().getUomId()).distinct().count() == 1,
				"All CUs of a TU content group are in the same UOM: {}", cus);
		this.cus = cus;
	}

	public Quantity totalQty()
	{
		return cus.stream().map(TUContentCU::getQty).reduce(Quantity::add).get();
	}

	/**
	 * @return the stocking UOM of the product of the CUs: the CUs count in it (see {@link TUContentCU#ofStorage})
	 */
	public I_C_UOM stockingUOM()
	{
		return cus.get(0).getQty().getUOM();
	}

	public I_M_HU firstCU()
	{
		return cus.get(0).getCu();
	}
}

