/*
 * #%L
 * metasfresh-material-cockpit
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

import de.metas.common.util.CoalesceUtil;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;

import static java.math.BigDecimal.ZERO;

@ToString
final class AvailableForSaleResultGroupBuilder
{
	@Getter
	private final ProductId productId;

	@Getter
	private final AttributesKey storageAttributesKey;

	@NonNull
	private BigDecimal qtyOnHandStock;

	@NonNull
	private BigDecimal qtyToBeShipped;

	final HashSet<Util.ArrayKey> includedRequestKeys = new HashSet<>();

	@Builder
	private AvailableForSaleResultGroupBuilder(
			@NonNull final ProductId productId,
			@NonNull final AttributesKey storageAttributesKey,
			@Nullable final BigDecimal qtyOnHandStock,
			@Nullable final BigDecimal qtyToBeShipped)
	{

		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;

		this.qtyOnHandStock = CoalesceUtil.coalesce(qtyOnHandStock, ZERO);
		this.qtyToBeShipped = CoalesceUtil.coalesce(qtyToBeShipped, ZERO);
	}

	public AvailableForSalesLookupBucketResult build()
	{
		return AvailableForSalesLookupBucketResult.builder()
				.productId(productId)
				.storageAttributesKey(storageAttributesKey)
				.quantities(AvailableForSalesLookupBucketResult.Quantities.builder()
						.qtyOnHandStock(qtyOnHandStock)
						.qtyToBeShipped(qtyToBeShipped)
						.build())
				.build();
	}

	public void addQty(@NonNull final AddToResultGroupRequest request)
	{
		qtyOnHandStock = qtyOnHandStock.add(CoalesceUtil.coalesce(request.getQtyOnHandStock(), ZERO));
		qtyToBeShipped = qtyToBeShipped.add(CoalesceUtil.coalesce(request.getQtyToBeShipped(), ZERO));
		includedRequestKeys.add(request.computeKey());
	}

	boolean isAlreadyIncluded(@NonNull final AddToResultGroupRequest request)
	{
		final Util.ArrayKey key = request.computeKey();
		return includedRequestKeys.contains(key);
	}

}
