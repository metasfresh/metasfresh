package de.metas.procurement.sync.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * #%L
 * de.metas.fresh.procurement.webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@XmlRootElement(name = "SyncProductSuppliesRequest")
public class SyncProductSuppliesRequest
{
	public static final SyncProductSuppliesRequest of(final SyncProductSupply syncProductSupply)
	{
		if (syncProductSupply == null)
		{
			throw new NullPointerException("syncProductSupply is null");
		}

		final SyncProductSuppliesRequest request = new SyncProductSuppliesRequest();
		request.getProductSupplies().add(syncProductSupply);

		return request;
	}

	public static final SyncProductSuppliesRequest of(final Collection<SyncProductSupply> syncProductSupplies)
	{
		final SyncProductSuppliesRequest request = new SyncProductSuppliesRequest();

		if (syncProductSupplies != null)
		{
			request.getProductSupplies().addAll(syncProductSupplies);
		}

		return request;
	}

	private List<SyncProductSupply> productSupplies = new ArrayList<>();

	@Override
	public String toString()
	{
		return "SyncProductSuppliesRequest [productSupplies=" + productSupplies + "]";
	}

	public List<SyncProductSupply> getProductSupplies()
	{
		return productSupplies;
	}

	public void setProductSupplies(List<SyncProductSupply> productSupplies)
	{
		this.productSupplies = productSupplies;
	}
}
