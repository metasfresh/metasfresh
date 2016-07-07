package de.metas.procurement.sync.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class SyncContract extends AbstractSyncModel
{
	private Date dateFrom;
	private Date dateTo;
	private List<SyncContractLine> contractLines = new ArrayList<>();
	private String rfq_uuid; // optional

	@Override
	public String toString()
	{
		return "SyncContract ["
				+ "dateFrom=" + dateFrom + ", dateTo=" + dateTo
				+ ", rfq_uuid=" + rfq_uuid
				+ ", contractLines=" + contractLines + "]";
	}

	public Date getDateFrom()
	{
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	public Date getDateTo()
	{
		return dateTo;
	}

	public void setDateTo(Date dateTo)
	{
		this.dateTo = dateTo;
	}
	
	public void setRfq_uuid(String rfq_uuid)
	{
		this.rfq_uuid = rfq_uuid;
	}
	
	public String getRfq_uuid()
	{
		return rfq_uuid;
	}

	public List<SyncContractLine> getContractLines()
	{
		return contractLines;
	}

	public void setContractLines(List<SyncContractLine> contractLines)
	{
		this.contractLines = contractLines;
	}
}
