package de.metas.product;

import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Data
@Builder(toBuilder = true)
public class ProductPlanningSchema
{
	@Nullable
	private ProductPlanningSchemaId id;

	@NonNull
	private final ProductPlanningSchemaSelector selector;

	@NonNull
	private final OrgId orgId;

	@Nullable
	private final ResourceId plantId;

	@Nullable
	private final WarehouseId warehouseId;

	private final boolean attributeDependant;

	@Nullable
	private final UserId plannerId;

	@Nullable
	private final Boolean manufactured;

	private final boolean createPlan;
	private final boolean completeGeneratedDocuments;
	private final boolean pickDirectlyIfFeasible;

	@Nullable
	private final PPRoutingId routingId;

	// @Nullable
	private final DistributionNetworkId distributionNetworkId;

	@NonNull
	private final OnMaterialReceiptWithDestWarehouse onMaterialReceiptWithDestWarehouse;

	public ProductPlanningSchemaId getIdNotNull() {return Check.assumeNotNull(id, "id is set {}", this);}
}
