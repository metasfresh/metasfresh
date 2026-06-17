package de.metas.material.cockpit.availableforsales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
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
	@NonNull ProductId productId;
	@Nullable WarehouseId warehouseId;
	@NonNull AttributesKeyPattern storageAttributesKeyPattern;
	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull Instant dateOfInterest;
	int shipmentDateLookAheadHours;
	int salesOrderLookBehindHours;

	@Builder
	@Jacksonized
	private AvailableForSalesQuery(
			@NonNull final ProductId productId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final AttributesKeyPattern storageAttributesKeyPattern,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final Instant dateOfInterest,
			final int shipmentDateLookAheadHours,
			final int salesOrderLookBehindHours)
	{
		Check.errorUnless(clientAndOrgId.getOrgId().isRegular(), "AD_Org_Id={} must be regular! M_Product_ID={}, M_Warehouse_ID={}, AttributesKey={}, dateOfInterest={}",
				clientAndOrgId.getOrgId(), productId, warehouseId, storageAttributesKeyPattern, dateOfInterest);

		this.productId = productId;
		this.warehouseId = warehouseId;
		this.storageAttributesKeyPattern = storageAttributesKeyPattern;
		this.clientAndOrgId = clientAndOrgId;
		this.dateOfInterest = dateOfInterest;
		this.shipmentDateLookAheadHours = shipmentDateLookAheadHours;
		this.salesOrderLookBehindHours = salesOrderLookBehindHours;
	}

	@JsonIgnore
	public OrgId getOrgId() {return clientAndOrgId.getOrgId();}
}
