/*
 * #%L
 * metasfresh-material-dispo-commons
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

package de.metas.material.cockpit.availableforsales;

import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
public final class AddToResultGroupRequest
{
	int queryNo;

	ProductId productId;

	AttributesKey storageAttributesKey;

	@Nullable
	BigDecimal qtyOnHandStock;

	@Nullable
	BigDecimal qtyToBeShipped;

	@Builder
	public AddToResultGroupRequest(
			final int queryNo,
			@NonNull final ProductId productId,
			@NonNull final AttributesKey storageAttributesKey,
			@Nullable final BigDecimal qtyOnHandStock,
			@Nullable final BigDecimal qtyToBeShipped)
	{
		this.queryNo = queryNo;
		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;

		this.qtyOnHandStock = qtyOnHandStock;
		this.qtyToBeShipped = qtyToBeShipped;
	}

	public ArrayKey computeKey()
	{
		return ArrayKey.of(productId, storageAttributesKey);
	}
}
