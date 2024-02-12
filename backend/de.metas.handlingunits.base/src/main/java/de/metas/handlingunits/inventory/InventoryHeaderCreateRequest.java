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

import de.metas.document.DocTypeId;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@Value
@Builder
public class InventoryHeaderCreateRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	ZonedDateTime movementDate;

	@NonNull
	WarehouseId warehouseId;

	@Nullable
	DocTypeId docTypeId;

	@Nullable
	ActivityId activityId;

	@Nullable
	String description;

	@Nullable
	String poReference;

	@Nullable PickingJobId pickingJobId;
}
