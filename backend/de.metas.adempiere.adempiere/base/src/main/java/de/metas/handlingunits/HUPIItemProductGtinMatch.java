/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.handlingunits;

import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

/**
 * Immutable result of a GTIN lookup against {@link de.metas.handlingunits.model.I_M_HU_PI_Item_Product}.
 * Carries the resolved product and the packing instruction item product IDs,
 * without exposing the underlying record.
 */
@Value
public class HUPIItemProductGtinMatch
{
	@NonNull ProductId productId;
	@NonNull HUPIItemProductId hupiItemProductId;

	public static HUPIItemProductGtinMatch of(
			@NonNull final ProductId productId,
			@NonNull final HUPIItemProductId hupiItemProductId)
	{
		return new HUPIItemProductGtinMatch(productId, hupiItemProductId);
	}
}
