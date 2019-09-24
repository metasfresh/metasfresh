package de.metas.vendor.gateway.api.order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.vendor.gateway.api
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
public class RemotePurchaseOrderCreatedItem
{
	String remotePurchaseOrderId;

	MSV3OrderResponsePackageItemPartRepoId internalItemId;

	@NonNull
	PurchaseOrderRequestItem correspondingRequestItem;

	BigDecimal confirmedOrderQuantity;

	ZonedDateTime confirmedDeliveryDateOrNull;

	@Builder
	private RemotePurchaseOrderCreatedItem(
			@NonNull final PurchaseOrderRequestItem correspondingRequestItem,
			@NonNull final BigDecimal confirmedOrderQuantity,
			@Nullable final ZonedDateTime confirmedDeliveryDateOrNull,
			final String remotePurchaseOrderId,
			final MSV3OrderResponsePackageItemPartRepoId internalItemId)
	{
		this.correspondingRequestItem = correspondingRequestItem;
		this.confirmedOrderQuantity = confirmedOrderQuantity;
		this.confirmedDeliveryDateOrNull = confirmedDeliveryDateOrNull;
		this.remotePurchaseOrderId = remotePurchaseOrderId;
		this.internalItemId = internalItemId;

		if (confirmedOrderQuantity.signum() > 0)
		{
			Check.errorIf(Check.isEmpty(remotePurchaseOrderId),
					"If any quantity was ordered remotely, then the given remotePurchaseOrderId may not be emtpy");
		}
	}

	public int getUomId()
	{
		return getCorrespondingRequestItem().getProductAndQuantity().getUomId();
	}
}
