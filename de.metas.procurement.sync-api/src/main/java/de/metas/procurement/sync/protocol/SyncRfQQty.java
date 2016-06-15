package de.metas.procurement.sync.protocol;

import java.math.BigDecimal;
import java.util.Date;

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

public class SyncRfQQty extends AbstractSyncModel
{
	private String product_uuid;
	private Date datePromised;
	private BigDecimal qtyPromised;

	@Override
	public String toString()
	{
		return "SyncRfQQty ["
				+ "product_uuid=" + product_uuid
				+ ", datePromised=" + datePromised
				+ ", qtyPromised=" + qtyPromised
				+ "]";
	}
	
	public String getProduct_uuid()
	{
		return product_uuid;
	}
	
	public void setProduct_uuid(String product_uuid)
	{
		this.product_uuid = product_uuid;
	}

	public Date getDatePromised()
	{
		return datePromised;
	}

	public void setDatePromised(final Date datePromised)
	{
		this.datePromised = datePromised;
	}

	public BigDecimal getQtyPromised()
	{
		return qtyPromised;
	}

	public void setQtyPromised(final BigDecimal qtyPromised)
	{
		this.qtyPromised = qtyPromised;
	}
}
