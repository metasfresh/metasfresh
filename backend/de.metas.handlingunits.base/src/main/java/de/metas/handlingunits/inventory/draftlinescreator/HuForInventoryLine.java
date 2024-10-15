package de.metas.handlingunits.inventory.draftlinescreator;

import javax.annotation.Nullable;

import de.metas.handlingunits.qrcodes.model.HUQRCode;
import org.adempiere.warehouse.LocatorId;

import de.metas.handlingunits.HuId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
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

/** Instances are created by a {@link HUsForInventoryStrategy} and can be turned into drafted inventory lines. */
@Value
public class HuForInventoryLine
{
	@NonNull
	OrgId orgId;

	@Nullable HuId huId;
	@Nullable HUQRCode huQRCode;

	@NonNull
	Quantity quantityBooked;

	@Nullable
	Quantity quantityCount;

	@NonNull
	ProductId productId;

	@NonNull
	AttributesKey storageAttributesKey;

	@NonNull
	LocatorId locatorId;

	boolean markAsCounted;

	@Builder
	private HuForInventoryLine(
			@NonNull final OrgId orgId,
			@Nullable final HuId huId,
			@Nullable final HUQRCode huQRCode,
			@NonNull final Quantity quantityBooked,
			@Nullable final Quantity quantityCount,
			@NonNull final ProductId productId,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final LocatorId locatorId,
			boolean markAsCounted)
	{
		Quantity.getCommonUomIdOfAll(quantityBooked, quantityCount); // make sure we use the same UOM

		this.orgId = orgId;
		this.huId = huId;
		this.huQRCode = huQRCode;
		this.quantityBooked = quantityBooked;
		this.quantityCount = quantityCount;

		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;
		this.locatorId = locatorId;

		this.markAsCounted = markAsCounted;
	}
}
