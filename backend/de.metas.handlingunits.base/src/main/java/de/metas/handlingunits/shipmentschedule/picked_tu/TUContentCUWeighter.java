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

import de.metas.quantity.Quantity;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Tells the catch weight of a CU of a TU.
 */
@FunctionalInterface
public interface TUContentCUWeighter
{
	/**
	 * @return the weight of the CU; {@code null} if the product has no catch weight or the CU has none (e.g. picked by count)
	 */
	@Nullable
	Quantity getWeight(@NonNull TUContentCU cu);
}
