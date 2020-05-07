package de.metas.purchasecandidate;

import java.time.ZonedDateTime;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.money.CurrencyId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Groups {@link PurchaseCandidatesGroup}s. Instances are not stored on or loaded from disk,
 * but are created on the fly, according to particular sets of sales order lines or purchase candidates.
 */
@Value
@Builder
public class PurchaseDemand
{
	PurchaseDemandId id = PurchaseDemandId.createNew();

	@NonNull
	OrgId orgId;

	/** might be needed when creating new purchase candidates. */
	@NonNull
	WarehouseId warehouseId;

	@NonNull
	ProductId productId;

	@NonNull
	AttributeSetInstanceId attributeSetInstanceId;

	@NonNull
	Quantity qtyToDeliver;

	@Nullable
	CurrencyId currencyIdOrNull;

	@NonNull
	ZonedDateTime salesPreparationDate;

	@Nullable
	OrderAndLineId salesOrderAndLineIdOrNull;

	/**
	 * A demand instance might be (partially) backed by already existing purchase candidates.
	 * We need to hold their IDs to sync with them later.
	 */
	@Singular
	List<PurchaseCandidateId> existingPurchaseCandidateIds;

	public int getUOMId()
	{
		return qtyToDeliver.getUOMId();
	}
}
