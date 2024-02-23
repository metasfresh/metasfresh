/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inventory;

import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@Value
@Builder
public class CreateVirtualInventoryWithQtyReq
{
	@NonNull WarehouseId warehouseId;
	@NonNull OrgId orgId;
	@NonNull ClientId clientId;
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@NonNull ZonedDateTime movementDate;
	@Nullable AttributeSetInstanceId attributeSetInstanceId;
	@Nullable PickingJobId pickingJobId;
}
