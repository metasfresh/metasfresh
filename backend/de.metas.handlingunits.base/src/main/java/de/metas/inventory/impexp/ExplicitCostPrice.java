/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.inventory.impexp;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_I_Inventory;

public enum ExplicitCostPrice implements ReferenceListAwareEnum
{
	EXPLICITCOSTPRICE_Yes(X_I_Inventory.EXPLICITCOSTPRICE_Yes),
	EXPLICITCOSTPRICE_No(X_I_Inventory.EXPLICITCOSTPRICE_No),
	EXPLICITCOSTPRICE_Auto(X_I_Inventory.EXPLICITCOSTPRICE_Auto);

	@Getter
	private final String code;

	ExplicitCostPrice(@NonNull final String code)
	{
		this.code = code;
	}

	public static ExplicitCostPrice ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<ExplicitCostPrice> index = ReferenceListAwareEnums.index(values());

	public boolean isTrue()
	{
		return this == EXPLICITCOSTPRICE_Yes;
	}

	public boolean isFalse()
	{
		return this == EXPLICITCOSTPRICE_No;
	}

	public boolean isAuto()
	{
		return this == EXPLICITCOSTPRICE_Auto;
	}

}
