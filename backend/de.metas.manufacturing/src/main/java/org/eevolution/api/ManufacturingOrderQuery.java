package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.dao.ValueRestriction;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

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
	@NonNull @Singular ImmutableSet<ResourceId> onlyPlantOrWorkstationIds;
	@NonNull @Singular ImmutableSet<ResourceId> onlyPlantIds;
	@NonNull @Singular ImmutableSet<ResourceId> onlyWorkstationIds;
	@Nullable WarehouseId warehouseId;
	@NonNull @Builder.Default ValueRestriction<UserId> responsibleId = ValueRestriction.any();
	@Nullable Instant dateStartScheduleDay;
	@NonNull @Singular ImmutableSet<PPOrderId> onlyIds;

	@Nullable APIExportStatus exportStatus;
	@Nullable Instant canBeExportedFrom;
	@Nullable ProductBOMVersionsId bomVersionsId;
	boolean onlyDrafted;

	@NonNull @Singular ImmutableSet<PPOrderPlanningStatus> onlyPlanningStatuses;

	@NonNull @Builder.Default QueryLimit limit = QueryLimit.NO_LIMIT;
	@NonNull @Singular ImmutableList<SortingOption> sortingOptions;

	public enum SortingOption
	{
		SEQ_NO
	}
}
