/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.ddordercandidate;

import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import java.time.Instant;
import java.util.Optional;

/**
 * Supplies the ATP (available-to-promise) quantity at a given source warehouse for purposes
 * of clipping a DD order candidate when the source cannot fully cover the demand.
 *
 * <p>Implementations live in modules that own the stock read-model; the planning module itself
 * stays decoupled from {@code AvailableToPromiseRepository}. If no ATP-aware bean is wired
 * into the Spring context, {@link #UNLIMITED} is used and the factory falls back to its
 * pre-fix behavior (candidate sized to the full requested qty).
 *
 * <p>Introduced for <a href="https://github.com/metasfresh/me03/issues/28877">me03#28877</a>
 * so that when distribution cannot fulfill the whole demand, the remainder spills over to
 * manufacturing / purchasing via the leftover-quantity dispatch in
 * {@code SupplyRequiredHandler}.
 */
public interface SourceWarehouseAvailableQtyProvider
{
	/**
	 * @return the ATP at the source warehouse for the given (product, attributes, date),
	 *         or {@link Optional#empty()} to signal "unlimited — do not clip".
	 */
	@NonNull
	Optional<Quantity> availableQtyAtSource(
			@NonNull WarehouseId sourceWarehouseId,
			@NonNull ProductId productId,
			@NonNull AttributesKey storageAttributesKey,
			@NonNull Instant demandDate);

	/**
	 * No-op implementation used when no ATP source is wired. Preserves the pre-fix behavior
	 * of {@code DDOrderCandidateDataFactory} — candidates are sized to the full requested qty
	 * regardless of actual source-warehouse stock.
	 */
	SourceWarehouseAvailableQtyProvider UNLIMITED = (warehouseId, productId, attributesKey, date) -> Optional.empty();
}
