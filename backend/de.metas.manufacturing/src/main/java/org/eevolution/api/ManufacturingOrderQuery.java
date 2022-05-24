package org.eevolution.api;

import java.time.Instant;

import de.metas.dao.ValueRestriction;
import de.metas.user.UserId;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.WarehouseId;

import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.product.ResourceId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.manufacturing
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

@Value
@Builder
public class ManufacturingOrderQuery
{
	boolean onlyCompleted;
	@Nullable ResourceId plantId;
	@Nullable WarehouseId warehouseId;
	@Builder.Default
	@NonNull ValueRestriction<UserId> responsibleId = ValueRestriction.any();

	@Nullable APIExportStatus exportStatus;
	@Nullable Instant canBeExportedFrom;

	@Default
	@NonNull QueryLimit limit = QueryLimit.NO_LIMIT;
}
