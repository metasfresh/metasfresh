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

package de.metas.handlingunits.inout.returns.customer;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.QtyTU;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;

@Value
public class CreateCustomerReturnLineReq
{
	@NonNull
	InOutId headerId;

	@NonNull
	ProductId productId;

	@Nullable
	AttributeSetInstanceId attributeSetInstanceId;

	boolean warrantyCase;

	@NonNull
	Quantity qtyReturned;

	@Nullable
	InOutLineId originShipmentLineId;

	@Nullable
	HUPIItemProductId hupiItemProductId;

	@Nullable
	QtyTU qtyTU;

	@Builder
	public CreateCustomerReturnLineReq(
			@NonNull final InOutId headerId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyReturned,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			final boolean warrantyCase,
			@Nullable final InOutLineId originShipmentLineId,
			@Nullable final HUPIItemProductId hupiItemProductId,
			@Nullable final QtyTU qtyTU)
	{
		this.headerId = headerId;
		this.productId = productId;
		this.qtyReturned = qtyReturned;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.warrantyCase = warrantyCase;
		this.originShipmentLineId = originShipmentLineId;
		this.hupiItemProductId = hupiItemProductId;
		this.qtyTU = qtyTU;
	}
}
