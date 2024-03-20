/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.model.dao;

import de.metas.handlingunits.HuId;
import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLineId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class PPMaturingCandidateV
{
	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable PPOrderCandidateId ppOrderCandidateId;
	@NonNull MaturingConfigId maturingConfigId;
	@NonNull MaturingConfigLineId maturingConfigLineId;
	@NonNull ProductBOMVersionsId productBOMVersionsId;
	@NonNull ProductId productId;
	@NonNull ProductId issueProductId;
	@NonNull WarehouseId warehouseId;
	@NonNull ProductPlanningId productPlanningId;
	@NonNull Quantity qtyRequired;
	@NonNull Instant dateStartSchedule;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@NonNull HuId issueHuId;
}
