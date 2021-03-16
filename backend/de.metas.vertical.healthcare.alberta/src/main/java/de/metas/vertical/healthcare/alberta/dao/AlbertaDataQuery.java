/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.dao;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.time.Instant;
import java.util.Set;

@Value
public class AlbertaDataQuery
{
	/** 
	 * Ids of products for which we need the Alberta-Data because the {@code M_Product} was updated after given the given {@code updatedAfter}.
	 */
	@NonNull
	Set<ProductId> productIds;

	/**
	 * Needed to retrieve all the Alberta-Data where the {@code M_Product} we not updated, but maybe the Alberta-Data itself.
	 */
	@NonNull
	Instant updatedAfter;

	@Builder
	public AlbertaDataQuery(@NonNull @Singular final Set<ProductId> productIds, @NonNull final Instant updatedAfter)
	{
		this.productIds = productIds;
		this.updatedAfter = updatedAfter;
	}
}
