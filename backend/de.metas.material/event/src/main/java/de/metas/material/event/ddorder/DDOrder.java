package de.metas.material.event.ddorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.document.engine.DocStatus;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

/*
 * #%L
 * metasfresh-mrp
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Jacksonized
public class DDOrder
{
	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable ResourceId plantId;
	@Nullable ProductPlanningId productPlanningId;
	@NonNull Instant supplyDate;
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	@Nullable ShipperId shipperId;
	@NonNull @Singular List<DDOrderLine> lines;
	int ddOrderId;
	@NonNull DocStatus docStatus;
	@Nullable MaterialDispoGroupId materialDispoGroupId;
	boolean simulated;

	@Nullable PPOrderRef forwardPPOrderRef;

	@JsonIgnore
	public OrgId getOrgId() {return clientAndOrgId.getOrgId();}
}
