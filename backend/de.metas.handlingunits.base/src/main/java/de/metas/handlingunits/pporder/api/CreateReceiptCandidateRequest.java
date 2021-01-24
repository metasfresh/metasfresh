package de.metas.handlingunits.pporder.api;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

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

@Data
@Builder
public final class CreateReceiptCandidateRequest
{
	@NonNull
	private final PPOrderId orderId;
	/**
	 * BY/CO-Product order BOM line
	 */
	@Nullable
	private final PPOrderBOMLineId orderBOMLineId;
	@NonNull
	private final OrgId orgId;

	@NonNull
	private final ZonedDateTime date;

	@NonNull
	private final LocatorId locatorId;

	@NonNull
	private final HuId topLevelHUId;

	@NonNull
	private final ProductId productId;

	@NonNull
	private Quantity qtyToReceive;

	@Nullable
	private final PickingCandidateId pickingCandidateId;

	private final boolean alreadyProcessed;

	public static class CreateReceiptCandidateRequestBuilder
	{
		public CreateReceiptCandidateRequestBuilder addQtyToReceive(@NonNull final Quantity qtyToAdd)
		{
			final Quantity qtyToReceiveNew = qtyToReceive != null
					? qtyToReceive = qtyToReceive.add(qtyToAdd)
					: qtyToAdd;
			return qtyToReceive(qtyToReceiveNew);
		}
	}
}
