package de.metas.procurement.base.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;

import de.metas.procurement.base.IPMM_RfQ_DAO;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

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
	public List<I_C_RfQResponse> retrieveActiveResponses(final Properties ctx, final int bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQResponse.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQResponse.COLUMN_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_RfQResponse.COLUMN_Processed, false) // not closed
				.orderBy()
				.addColumn(I_C_RfQResponse.COLUMNNAME_Name)
				.addColumn(I_C_RfQResponse.COLUMNNAME_C_RfQResponse_ID)
				.endOrderBy()
				//
				.create()
				.list(I_C_RfQResponse.class);
	}
	
	@Override
	public List<I_C_RfQResponseLine> retrieveResponseLines(final I_C_RfQResponse rfqResponse)
	{
		return Services.get(IRfqDAO.class).retrieveResponseLines(rfqResponse);
	}

	@Override
	public List<I_C_RfQResponseLineQty> retrieveResponseLineQtys(I_C_RfQResponseLine rfqResponseLine)
	{
		return Services.get(IRfqDAO.class).retrieveResponseQtys(rfqResponseLine);
	}
}
