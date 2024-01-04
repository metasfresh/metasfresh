package de.metas.material.cockpit.availableforsales;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

/*
 * #%L
 * metasfresh-available-for-sales
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

@Value
public class AvailableForSalesQuery
{
	@NonNull
	ProductId productId;

	@Nullable
	WarehouseId warehouseId;

	@NonNull
	AttributesKeyPattern storageAttributesKeyPattern;

	@NonNull
	OrgId orgId;

	@NonNull
	Instant dateOfInterest;

	int shipmentDateLookAheadHours;

	int salesOrderLookBehindHours;

	@Builder
	public AvailableForSalesQuery(
			@NonNull final ProductId productId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final AttributesKeyPattern storageAttributesKeyPattern,
			@NonNull final OrgId orgId,
			@NonNull final Instant dateOfInterest,
			final int shipmentDateLookAheadHours,
			final int salesOrderLookBehindHours)
	{
		Check.errorUnless(orgId.isRegular(), "AD_Org_Id={} must be regular! M_Product_ID={}, M_Warehouse_ID={}, AttributesKey={}, dateOfInterest={}",
						  OrgId.toRepoId(orgId), ProductId.toRepoId(productId), WarehouseId.toRepoId(warehouseId), storageAttributesKeyPattern, dateOfInterest);

		this.productId = productId;
		this.warehouseId = warehouseId;
		this.storageAttributesKeyPattern = storageAttributesKeyPattern;
		this.orgId = orgId;
		this.dateOfInterest = dateOfInterest;
		this.shipmentDateLookAheadHours = shipmentDateLookAheadHours;
		this.salesOrderLookBehindHours = salesOrderLookBehindHours;
	}
}
