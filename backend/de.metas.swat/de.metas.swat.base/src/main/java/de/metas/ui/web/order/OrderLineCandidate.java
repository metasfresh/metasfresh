/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.order;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.eevolution.api.ProductBOMLineId;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class OrderLineCandidate
{
	@NonNull
	OrderId orderId;

	@NonNull
	ProductId productId;

	@NonNull
	ImmutableAttributeSet attributes;

	@Nullable
	HUPIItemProductId piItemProductId;

	@NonNull
	Quantity qty;

	@Nullable
	ShipmentAllocationBestBeforePolicy bestBeforePolicy;

	@Nullable
	BPartnerId bpartnerId;

	@NonNull
	SOTrx soTrx;

	@Nullable
	GroupId compensationGroupId;

	@Nullable
	ProductBOMLineId explodedFromBOMLineId;
}
