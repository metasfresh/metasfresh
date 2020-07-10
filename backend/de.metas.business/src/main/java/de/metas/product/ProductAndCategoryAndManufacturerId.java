package de.metas.product;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class ProductAndCategoryAndManufacturerId
{
	public static ProductAndCategoryAndManufacturerId of(
			final ProductId productId,
			final ProductCategoryId productCategoryId,
			final BPartnerId productManufacturerId)
	{
		return new ProductAndCategoryAndManufacturerId(productId, productCategoryId, productManufacturerId);
	}

	public static ProductAndCategoryAndManufacturerId of(
			final int productId,
			final int productCategoryId,
			final int productManufacturerId)
	{
		return ProductAndCategoryAndManufacturerId.of(
				ProductId.ofRepoId(productId),
				ProductCategoryId.ofRepoId(productCategoryId),
				BPartnerId.ofRepoIdOrNull(productManufacturerId));
	}

	ProductId productId;
	ProductCategoryId productCategoryId;
	BPartnerId productManufacturerId;

	private ProductAndCategoryAndManufacturerId(
			@NonNull final ProductId productId,
			@NonNull final ProductCategoryId productCategoryId,
			@Nullable final BPartnerId productManufacturerId)
	{
		this.productId = productId;
		this.productCategoryId = productCategoryId;
		this.productManufacturerId = productManufacturerId;
	}

}
