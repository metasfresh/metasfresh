/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.planning.pporder;

import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.ProductBOMLineId;

import javax.annotation.Nullable;

@Value
@Builder
public class ComputeQtyRequiredRequest
{
	public ComputeQtyRequiredRequest(@Nullable final Quantity finishedGoodQty,
			@Nullable final Quantity issuedQty,
			@NonNull final ProductBOMLineId productBOMLineId)
	{
		Check.assume(finishedGoodQty != null ^ issuedQty != null, "only one of finishedGoodQty={} and issuedQty={} shall be provided", finishedGoodQty, issuedQty);
		this.finishedGoodQty = finishedGoodQty;
		this.issuedQty = issuedQty;
		this.productBOMLineId = productBOMLineId;
	}

	@Nullable
	Quantity finishedGoodQty;

	@Nullable
	Quantity issuedQty;

	@NonNull
	ProductBOMLineId productBOMLineId;
}
