package de.metas.handlingunits.pporder.api;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.adempiere.warehouse.LocatorId;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Value
@Builder
public class CreateIssueCandidateRequest
{
	@NonNull
	PPOrderId orderId;
	@NonNull
	PPOrderBOMLineId orderBOMLineId;

	@NonNull
	ZonedDateTime date;

	@Nullable
	LocatorId locatorId;
	@NonNull
	HuId issueFromHUId;
	@NonNull
	ProductId productId;

	@NonNull
	Quantity qtyToIssue;

	@Nullable
	PickingCandidateId pickingCandidateId;
}
