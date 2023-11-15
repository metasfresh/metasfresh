/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.order;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.stream.Stream;

@Value
public class GetOrdersQuery
{
	BPartnerId bPartnerId;
	DocTypeId docTypeTargetId;
	DocStatus docStatus;
	ImmutableSet<ProductId> productIds;
	boolean descSortByDateOrdered;
	ImmutableSet<WarehouseId> warehouseIds;
	boolean onlyActiveRecords;

	@Builder
	public GetOrdersQuery(
			@Nullable final BPartnerId bPartnerId,
			@Nullable final DocTypeId docTypeTargetId,
			@Nullable final DocStatus docStatus,
			final boolean descSortByDateOrdered,
			@Nullable @Singular final ImmutableSet<ProductId> productIds,
			@Nullable @Singular final ImmutableSet<WarehouseId> warehouseIds,
			final boolean onlyActiveRecords)
	{
		final boolean noCriteriaProvided = Stream.of(bPartnerId, docTypeTargetId, docStatus, productIds, warehouseIds)
				.allMatch(Check::isEmpty);
		
		if (noCriteriaProvided)
		{
			throw new AdempiereException("No criteria provided!");
		}
		
		this.bPartnerId = bPartnerId;
		this.docTypeTargetId = docTypeTargetId;
		this.docStatus = docStatus;
		this.descSortByDateOrdered = descSortByDateOrdered;
		this.productIds = productIds;
		this.warehouseIds = warehouseIds;
		this.onlyActiveRecords = onlyActiveRecords;
	}
}