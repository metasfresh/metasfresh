package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.Date;

import de.metas.payment.sepa.api.ISEPADocumentSourceQuery;

/* package */class SEPADocumentSourceQuery implements ISEPADocumentSourceQuery
{
	private int adOrgId = -1;
	private Date dateFrom;
	private Date dateTo;
	private int adPInstanceId = -1;
	private String whereClause;

	@Override
	public int getAD_Org_ID()
	{
		return adOrgId;
	}

	@Override
	public void setAD_Org_ID(final int adOrgId)
	{
		this.adOrgId = adOrgId;
	}

	@Override
	public Date getDateFrom()
	{
		return dateFrom;
	}

	@Override
	public void setDateFrom(final Date dateFrom)
	{
		this.dateFrom = dateFrom == null ? null : (Date)dateFrom.clone();
	}

	@Override
	public Date getDateTo()
	{
		return dateTo;
	}

	@Override
	public void setDateTo(final Date dateTo)
	{
		this.dateTo = dateTo == null ? null : (Date)dateTo.clone();
	}

	@Override
	public int getAD_PInstance_ID()
	{
		return adPInstanceId;
	}

	@Override
	public void setAD_PInstance_ID(final int adPInstanceId)
	{
		this.adPInstanceId = adPInstanceId;
	}

	@Override
	public String getWhereClause()
	{
		return whereClause;
	}

	@Override
	public void setWhereClause(String whereClause)
	{
		this.whereClause = whereClause;
	}

}
