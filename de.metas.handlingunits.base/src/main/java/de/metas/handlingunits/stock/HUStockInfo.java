package de.metas.handlingunits.stock;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.warehouse.LocatorId;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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
public class HUStockInfo
{
	HuId huId;

	LocatorId locatorId;

	ProductId productId;

	BPartnerId bPartnerId;

	Quantity qty;

	AttributeId attributeId;

	String attributeValue;

	int huStorageRepoId;

	int huAttributeRepoId;

	private ITranslatableString huStatus;

	@Builder
	private HUStockInfo(
			@NonNull final HuId huId,
			@NonNull final LocatorId locatorId,
			@NonNull final ProductId productId,
			@Nullable final BPartnerId bPartnerId,
			@NonNull final ITranslatableString huStatus,
			@NonNull final Quantity qty,
			@Nullable AttributeId attributeId,
			@Nullable final String attributeValue,
			int huStorageRepoId,
			int huAttributeRepoId)
	{
		this.huStatus = huStatus;
		this.bPartnerId = bPartnerId;
		this.huId = huId;
		this.locatorId = locatorId;
		this.productId = productId;
		this.qty = qty;
		this.attributeId = attributeId;
		this.attributeValue = attributeValue;
		this.huStorageRepoId = Check.assumeGreaterThanZero(huStorageRepoId, "huStorageRepoId");
		this.huAttributeRepoId = huAttributeRepoId;
	}

}
