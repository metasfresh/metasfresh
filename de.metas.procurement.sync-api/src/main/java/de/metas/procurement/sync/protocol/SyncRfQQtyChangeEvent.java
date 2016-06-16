package de.metas.procurement.sync.protocol;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SyncRfQQtyChangeEvent
{
	private String product_uuid;
	private String rfq_qty_uuid;
	private BigDecimal qty;

	@Override
	public String toString()
	{
		return "SyncRfQQtyChangeEvent ["
				+ "product_uuid=" + product_uuid
				+ ", rfq_qty_uuid=" + rfq_qty_uuid
				+ ", qty=" + qty
				+ "]";
	}

	public String getProduct_uuid()
	{
		return product_uuid;
	}

	public void setProduct_uuid(final String product_uuid)
	{
		this.product_uuid = product_uuid;
	}

	public String getRfq_qty_uuid()
	{
		return rfq_qty_uuid;
	}

	public void setRfq_qty_uuid(final String rfq_qty_uuid)
	{
		this.rfq_qty_uuid = rfq_qty_uuid;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public void setQty(final BigDecimal qty)
	{
		this.qty = qty;
	}

}
