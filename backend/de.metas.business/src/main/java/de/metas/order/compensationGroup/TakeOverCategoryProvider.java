package de.metas.order.compensationGroup;

import de.metas.product.ProductCategoryId;

import java.util.Optional;

/*
 * #%L
 * de.metas.business
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

/**
 * Dependency inversion for the contract take-over record: the compensation-group repositories (in modules below
 * {@code de.metas.contracts}) need the take-over record's applies-to product category for an own compensation line
 * that has no schema line; the implementation lives in {@code de.metas.contracts}.
 */
public interface TakeOverCategoryProvider
{
	/** @return the applies-to product category of the given take-over record, empty if the record is unknown or has none */
	Optional<ProductCategoryId> getAppliesToCategory(int takeOverId);
}
