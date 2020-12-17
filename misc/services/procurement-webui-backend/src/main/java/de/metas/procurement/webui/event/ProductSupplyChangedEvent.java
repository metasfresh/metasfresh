package de.metas.procurement.webui.event;

import java.math.BigDecimal;
import java.util.Date;

import com.google.common.base.MoreObjects;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;

/*
 * #%L
 * metasfresh-procurement-webui
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

public class ProductSupplyChangedEvent implements IApplicationEvent
{

	public static final ProductSupplyChangedEvent of(final ProductSupply productSupply)
	{
		return new ProductSupplyChangedEvent(productSupply);
	}

	private final String bpartner_uuid;
	private final Product product;
	private final Date day;
	private final BigDecimal qty;

	private ProductSupplyChangedEvent(final ProductSupply productSupply)
	{
		super();
		this.bpartner_uuid = productSupply.getBpartner().getUuid();
		this.product = productSupply.getProduct();
		this.day = (Date)productSupply.getDay().clone();
		this.qty = productSupply.getQty();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("bpartner_uuid", bpartner_uuid)
				.add("product", product)
				.add("day", day)
				.add("qty", qty)
				.toString();
	}

	@Override
	public String getBpartner_uuid()
	{
		return bpartner_uuid;
	}

	public Date getDay()
	{
		return (Date)day.clone(); 
	}

	public Product getProduct()
	{
		return product;
	}
	
	public BigDecimal getQty()
	{
		return qty;
	}
}
