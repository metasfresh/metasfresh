/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.api;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@Builder(toBuilder = true)
@ToString
@Getter
@Setter(AccessLevel.PRIVATE)
public final class PPOrderRoutingProduct
{
	@Nullable
	private PPOrderRoutingProductId id;

	private final boolean subcontracting;
	@Nullable
	private Quantity qty;
	private int seqNo;
	@NonNull
	private ProductId productId;
	@Nullable
	private String specification;

	public PPOrderRoutingProduct copy() {return toBuilder().build();}

	public void setId(final PPOrderRoutingProductId id)
	{
		if (this.id == null)
		{
			this.id = id;
		}
		else
		{
			Check.assumeEquals(this.id, id);
		}
	}

}
