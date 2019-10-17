package de.metas.purchasecandidate.purchaseordercreation.localorder;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
@Builder
public class PurchaseCandidateAggregateKey
{
	public static PurchaseCandidateAggregateKey cast(@NonNull final Object obj)
	{
		return (PurchaseCandidateAggregateKey)obj;
	}

	public static PurchaseCandidateAggregateKey fromPurchaseCandidate(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		return builder()
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				.productId(purchaseCandidate.getProductId())
				.attributeSetInstanceId(purchaseCandidate.getAttributeSetInstanceId())
				.build();
	}

	@NonNull
	OrgId orgId;
	@NonNull
	WarehouseId warehouseId;
	@NonNull
	ProductId productId;
	@NonNull
	AttributeSetInstanceId attributeSetInstanceId;
}
