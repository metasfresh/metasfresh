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

package de.metas.handlingunits.inout.impl;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
public class CreateCustomerReturnLineReq
{
	@NonNull
	OrgId orgId;

	@NonNull
	InOutId headerId;

	@NonNull
	ProductId productId;

	@Nullable
	AttributeSetInstanceId attributeSetInstanceId;

	@NonNull
	Quantity qtyEntered;

	@NonNull
	Quantity movementQty;

	@Nullable
	InOutLineId originShipmentLineId;

	@Nullable
	HUPIItemProductId hupiItemProductId;

	@Nullable
	BigDecimal qtyTU;

	@Builder
	public CreateCustomerReturnLineReq(@NonNull final OrgId orgId,
			@NonNull final InOutId headerId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyEntered,
			@NonNull final Quantity movementQty,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final InOutLineId originShipmentLineId,
			@Nullable final HUPIItemProductId hupiItemProductId,
			@Nullable final BigDecimal qtyTU)
	{
		if (!movementQty.getUomId().equals(qtyEntered.getUomId()))
		{
			throw new AdempiereException("qtyEntered and movementQty must have the same uom!");
		}

		this.orgId = orgId;
		this.headerId = headerId;
		this.productId = productId;
		this.qtyEntered = qtyEntered;
		this.movementQty = movementQty;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.originShipmentLineId = originShipmentLineId;
		this.hupiItemProductId = hupiItemProductId;
		this.qtyTU = qtyTU;
	}

	@NonNull
	public UomId getCommonUOMId()
	{
		return Quantity.getCommonUomIdOfAll(movementQty, qtyEntered);
	}
}
