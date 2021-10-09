package de.metas.handlingunits.picking.requests;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

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

@Value
public class RemoveQtyFromHURequest
{
	Quantity qtyToRemove;
	HuId huId;
	ProductId productId;

	@Builder
	private RemoveQtyFromHURequest(
			@NonNull final Quantity qtyToRemove,
			@NonNull final HuId huId,
			@NonNull final ProductId productId)
	{
		if (qtyToRemove.signum() <= 0)
		{
			throw new AdempiereException("@Invalid@ @QtyCU@: " + qtyToRemove);
		}

		this.qtyToRemove = qtyToRemove;
		this.huId = huId;
		this.productId = productId;
	}

}
