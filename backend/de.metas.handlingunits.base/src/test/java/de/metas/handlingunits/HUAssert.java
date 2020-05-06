package de.metas.handlingunits;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUAssert extends AbstractAssert<HUAssert, I_M_HU>
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public HUAssert(I_M_HU actual)
	{
		super(actual, HUAssert.class);
	}

	public static HUAssert assertThat(I_M_HU actual)
	{
		return new HUAssert(actual);
	}

	public HUAssert isDestroyed()
	{
		final boolean destroyed = handlingUnitsBL.isDestroyed(actual);
		if (!destroyed)
		{
			failWithMessage("HU shall be destroyed; PI: <%s>; HU: <%s>", handlingUnitsBL.getPI(actual).getName(), actual);
		}
		return this;
	}

	public HUAssert hasStorage(@NonNull final ProductId productId, @NonNull final Quantity expectedQuantity)
	{
		final I_C_UOM uom = expectedQuantity.getUOM();

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IHUStorage huStorage = storageFactory.getStorage(actual);
		final BigDecimal qtyActual = huStorage.getQty(productId, uom);

		if (qtyActual.compareTo(expectedQuantity.toBigDecimal()) != 0)
		{
			failWithMessage("Invalid HU Storage for product: <%s>; expected <%s>, but has <%s>",
					Services.get(IProductBL.class).getProductValueAndName(productId),
					expectedQuantity,
					Quantity.of(qtyActual, uom));
		}

		return this;
	}

	public HUAssert includesHU(@NonNull final I_M_HU hu)
	{
		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(actual);
		for (I_M_HU includedHU : includedHUs)
		{
			if (includedHU.getM_HU_ID() == hu.getM_HU_ID())
			{
				return this;
			}
		}
		failWithMessage("HU shall be included; HU: <%s>", hu);
		return this;
	}

	public HUAssert isIncludedIn(@NonNull final I_M_HU hu)
	{
		final int actualParentId = handlingUnitsDAO.retrieveParentId(actual);
		if (actualParentId <= 0)
		{
			failWithMessage("Actual HU has is not included in any parent HU");
		}
		if (actualParentId != hu.getM_HU_ID())
		{
			failWithMessage("Actual HU has an invalid parent M_HU_ID=<%s>; expected parent: <%s>", actualParentId, hu);
		}

		return this;
	}

	public HUAssert isTopLevelHU()
	{
		if (!handlingUnitsBL.isTopLevel(actual))
		{
			final I_M_HU parentHU = handlingUnitsDAO.retrieveParent(actual);
			failWithMessage("Actual HU has to be a toplevel HU, but is has parent M_HU_ID=<%s>", parentHU);
		}
		return this;
	}
}
