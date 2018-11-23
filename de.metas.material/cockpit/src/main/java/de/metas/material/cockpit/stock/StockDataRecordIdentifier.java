package de.metas.material.cockpit.stock;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Check;

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
	public static StockDataRecordIdentifier createForMaterial(
			@NonNull final MaterialDescriptor material)
	{
		final StockDataRecordIdentifier identifier = StockDataRecordIdentifier.builder()
				.productDescriptor(material)
				.warehouseId(material.getWarehouseId())
				.build();
		return identifier;
	}

	int clientId;

	int orgId;

	ProductDescriptor productDescriptor;

	int warehouseId;

	@Builder
	private StockDataRecordIdentifier(
			int clientId,
			int orgId,
			@NonNull final ProductDescriptor productDescriptor,
			int warehouseId)
	{
		productDescriptor.getStorageAttributesKey().assertNotAllOrOther();

		this.clientId = Check.assumeGreaterThanZero(clientId, "clientId");
		this.orgId = orgId;
		this.warehouseId =  Check.assumeGreaterThanZero(warehouseId, "warehouseId");
		this.productDescriptor = productDescriptor;
	}
}
