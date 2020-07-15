package de.metas.procurement.sync.protocol;

import java.math.BigDecimal;
import java.util.Date;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SyncProductSupply extends AbstractSyncModel
{
	private String bpartner_uuid;
	private String product_uuid;
	private String contractLine_uuid;
	private BigDecimal qty;
	private Date day;
	private boolean weekPlanning = false;
	private int version;

	@Override
	public String toString()
	{
		return "SyncProductSupply ["
				+ "bpartner_uuid=" + bpartner_uuid
				+ ", product_uuid=" + product_uuid
				+ ", contractLine_uuid=" + contractLine_uuid
				+ ", qty=" + qty
				+ ", day=" + day
				+ ", weekPlanning=" + weekPlanning
				+ ", version=" + version
				+ ", uuid="+getUuid()
				+ "]";
	}

	public String getBpartner_uuid()
	{
		return bpartner_uuid;
	}

	public void setBpartner_uuid(String bpartner_uuid)
	{
		this.bpartner_uuid = bpartner_uuid;
	}

	public String getProduct_uuid()
	{
		return product_uuid;
	}

	public void setProduct_uuid(String product_uuid)
	{
		this.product_uuid = product_uuid;
	}

	public String getContractLine_uuid()
	{
		return contractLine_uuid;
	}

	public void setContractLine_uuid(String contractLine_uuid)
	{
		this.contractLine_uuid = contractLine_uuid;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public void setQty(BigDecimal qty)
	{
		this.qty = qty;
	}

	public Date getDay()
	{
		return day;
	}

	public void setDay(Date day)
	{
		this.day = day;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public boolean isWeekPlanning()
	{
		return weekPlanning;
	}

	public void setWeekPlanning(boolean weekPlanning)
	{
		this.weekPlanning = weekPlanning;
	}
}
