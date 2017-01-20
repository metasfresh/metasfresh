package de.metas.procurement.base.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;

import de.metas.procurement.base.IPMM_RfQ_DAO;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.model.X_C_RfQResponseLine;

/*
 * #%L
 * de.metas.procurement.base
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

public class PMM_RfQ_DAO implements IPMM_RfQ_DAO
{
	@Override
	public List<I_C_RfQResponseLine> retrieveActiveResponseLines(final Properties ctx, final int bpartnerId)
	{
		return retrieveActiveResponseLinesQuery(ctx)
				.addEqualsFilter(I_C_RfQResponseLine.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.list(I_C_RfQResponseLine.class);
	}

	@Override
	public List<I_C_RfQResponseLine> retrieveAllActiveResponseLines(final Properties ctx)
	{
		return retrieveActiveResponseLinesQuery(ctx)
				.create()
				.list(I_C_RfQResponseLine.class);
	}

	private IQueryBuilder<I_C_RfQResponseLine> retrieveActiveResponseLinesQuery(final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQResponseLine.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_C_RfQResponseLine.COLUMNNAME_DocStatus, X_C_RfQResponseLine.DOCSTATUS_Drafted)
				.orderBy()
				.addColumn(I_C_RfQResponseLine.COLUMNNAME_C_RfQResponse_ID)
				.addColumn(I_C_RfQResponseLine.COLUMNNAME_Line)
				.addColumn(I_C_RfQResponseLine.COLUMNNAME_C_RfQResponseLine_ID)
				.endOrderBy();
	}

	
	@Override
	public List<I_C_RfQResponseLine> retrieveResponseLines(final I_C_RfQResponse rfqResponse)
	{
		return Services.get(IRfqDAO.class).retrieveResponseLines(rfqResponse, I_C_RfQResponseLine.class);
	}

	@Override
	public List<I_C_RfQResponseLineQty> retrieveResponseLineQtys(de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine)
	{
		return Services.get(IRfqDAO.class).retrieveResponseQtys(rfqResponseLine);
	}
	
	@Override
	public I_C_RfQResponseLineQty retrieveResponseLineQty(de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine, final Date date)
	{
		return Services.get(IRfqDAO.class).retrieveResponseQty(rfqResponseLine, date);
	}

}
