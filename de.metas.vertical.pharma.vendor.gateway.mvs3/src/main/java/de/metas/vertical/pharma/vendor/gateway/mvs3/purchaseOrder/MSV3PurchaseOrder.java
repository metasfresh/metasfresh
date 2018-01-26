package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class MSV3PurchaseOrder
{
	public static MSV3PurchaseOrder ofDataRecord(@NonNull final I_MSV3_PurchaseOrder dataRecord)
	{
		return new MSV3PurchaseOrder(
				dataRecord.getSupportId(),
				dataRecord.getC_Order_ID());
	}

	int supportId;
	int orderId;

	@Builder
	private MSV3PurchaseOrder(
			final int supportId,
			final int orderId)
	{
		this.supportId = supportId;
		this.orderId = orderId;
	}
}
