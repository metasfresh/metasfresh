package de.metas.material.cockpit.stock;

import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class StockDataRecordIdentifier
{
	ClientId clientId;
	OrgId orgId;
	WarehouseId warehouseId;
	ProductId productId;
	AttributesKey storageAttributesKey;

	@Builder
	private StockDataRecordIdentifier(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			@NonNull final AttributesKey storageAttributesKey)
	{
		Check.errorUnless(orgId.isRegular(), "The given orgId may not be 'any' (*)."); // we are talking stock here. those always belong to an org

		storageAttributesKey.assertNotAllOrOther();
		this.clientId = clientId;
		this.orgId = orgId;
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;
	}
}
