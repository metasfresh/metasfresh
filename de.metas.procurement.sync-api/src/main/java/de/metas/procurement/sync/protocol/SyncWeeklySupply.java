package de.metas.procurement.sync.protocol;

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

public class SyncWeeklySupply extends AbstractSyncModel
{
	private String bpartner_uuid;
	private String product_uuid;
	private Date weekDay;
	private String trend;
	private int version;

	@Override
	public String toString()
	{
		return "SyncWeeklySupply ["
				+ "bpartner_uuid=" + bpartner_uuid
				+ ", product_uuid=" + product_uuid
				+ ", weekDay=" + weekDay
				+ ", trend=" + trend
				+ ", uuid=" + getUuid()
				+ ", vesion=" + version
				+ "]";
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(final int version)
	{
		this.version = version;
	}

	public String getBpartner_uuid()
	{
		return bpartner_uuid;
	}

	public void setBpartner_uuid(final String bpartner_uuid)
	{
		this.bpartner_uuid = bpartner_uuid;
	}

	public String getProduct_uuid()
	{
		return product_uuid;
	}

	public void setProduct_uuid(final String product_uuid)
	{
		this.product_uuid = product_uuid;
	}

	public Date getWeekDay()
	{
		return weekDay;
	}

	public void setWeekDay(final Date weekDay)
	{
		this.weekDay = weekDay;
	}

	public String getTrend()
	{
		return trend;
	}

	public void setTrend(final String trend)
	{
		this.trend = trend;
	}
}
