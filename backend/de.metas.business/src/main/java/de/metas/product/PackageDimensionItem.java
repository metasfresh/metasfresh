/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.product;

import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

/**
 * One line in a list of items to be packed into a TU.
 * Carries the per-unit package dimensions and the quantity of units.
 *
 * <p>Used as input to {@link PackageDimensions#ofItems(PackageDimensionCalcMethod, java.util.List)}.</p>
 */
@Value
public class PackageDimensionItem
{
	/** Per-unit dimensions (L/W/H in cm). May be {@link PackageDimensions#UNSPECIFIED}. */
	@NonNull PackageDimensions dims;

	/** Number of units being packed. */
	@NonNull Quantity qty;

	public static PackageDimensionItem of(
			@NonNull final PackageDimensions dims,
			@NonNull final Quantity qty)
	{
		return new PackageDimensionItem(dims, qty);
	}
}
