/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing.facades.manufacturing;

import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;

/**
 * Manufacturing Order Receipt of the processed product, aka finished good/main product
 * (in modular contracts ubiquitous language)
 */
@Value
@Builder
public class ManufacturingProcessedReceipt
{
	@NonNull PPCostCollectorId id;
	@NonNull PPOrderId manufacturingOrderId;
	@NonNull InstantAndOrgId transactionDate;
	@NonNull WarehouseId warehouseId;
	@NonNull ProductId processedProductId;
	@NonNull Quantity qtyReceived;
}
