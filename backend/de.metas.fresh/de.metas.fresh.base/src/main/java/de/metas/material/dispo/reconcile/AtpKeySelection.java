package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

/**
 * <i>Which</i> reconciliation keys a run covers: the optional warehouse / product / product-category filter an
 * operator hands to {@code MD_Candidate_Reconcile_ATP} or {@code MD_Candidate_ATP_Divergence_Report}. An unset
 * field is not restricted on, so {@link #ALL} selects the whole {@code MD_Stock} population.
 * <p>
 * Kept as a value object rather than three loose nullable parameters because it travels: the write path serialises
 * it onto a {@code C_Queue_WorkPackage} in the webapi and reconstitutes it in the app server (see
 * {@code de.metas.material.dispo.reconcile.async.AtpReconciliationEnqueueService}) - a tuple could be partially
 * forgotten across that boundary.
 */
@Value
@Builder
public class AtpKeySelection
{
	/** No restriction at all: every reconciliation key in the system. */
	public static final AtpKeySelection ALL = AtpKeySelection.builder().build();

	/** {@code null} means "any warehouse". */
	@Nullable WarehouseId warehouseId;

	/** {@code null} means "any product". */
	@Nullable ProductId productId;

	/** {@code null} means "any product category". */
	@Nullable ProductCategoryId productCategoryId;
}
