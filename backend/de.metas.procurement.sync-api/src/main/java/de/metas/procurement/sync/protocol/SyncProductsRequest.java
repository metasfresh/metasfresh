package de.metas.procurement.sync.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * #%L
 * de.metas.procurement.sync-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@XmlRootElement(name = "SyncProductRequest")
public class SyncProductsRequest
{
	public static final SyncProductsRequest of(final SyncProduct syncProduct)
	{
		final SyncProductsRequest syncProductsRequest = new SyncProductsRequest();
		syncProductsRequest.getProducts().add(syncProduct);
		return syncProductsRequest;
	}
	
	private List<SyncProduct> products = new ArrayList<>();
	
	@Override
	public String toString()
	{
		return "SyncProductsRequest [products=" + products + "]";
	}

	public List<SyncProduct> getProducts()
	{
		return products;
	}

	public void setProducts(List<SyncProduct> products)
	{
		this.products = products;
	}
}
