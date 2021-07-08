/*
 * #%L
 * de-metas-common-procurement
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.procurement.sync.protocol.request_to_procurementweb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class PutProductsRequest extends RequestToProcurementWeb
{
	public static PutProductsRequest of(@NonNull final SyncProduct syncProduct)
	{
		return PutProductsRequest.builder().product(syncProduct).build();
	}

	public static PutProductsRequest of(@NonNull final List<SyncProduct> syncProducts)
	{
		return PutProductsRequest.builder().products(syncProducts).build();
	}
	
	List<SyncProduct> products;

	@Builder
	@JsonCreator
	private PutProductsRequest(@JsonProperty("products") @Singular final List<SyncProduct> products)
	{
		this.products = products;
	}

	@Override
	public String toString()
	{
		return "SyncProductsRequest [products=" + products + "]";
	}
}
