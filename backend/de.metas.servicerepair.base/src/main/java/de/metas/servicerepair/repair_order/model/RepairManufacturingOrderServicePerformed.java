/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.repair_order.model;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

/**
 * Service performed in manufacturing/repair order.
 * This service will be imported to repair project when the repair order is done.
 * <p>
 * Latter on might be included on quotation and/or sales order,
 * but that depends on other rules (warranty case etc).
 */
@Value
@Builder
public class RepairManufacturingOrderServicePerformed
{
	@NonNull PPOrderId repairOrderId;
	@NonNull ProductId serviceId;
	@NonNull Quantity qty;
}
