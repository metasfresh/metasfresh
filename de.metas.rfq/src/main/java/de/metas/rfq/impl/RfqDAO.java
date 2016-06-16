package de.metas.rfq.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;

import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

/*
 * #%L
 * de.metas.business
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

public class RfqDAO implements IRfqDAO
{
	@Override
	public List<I_C_RfQLine> retrieveLines(final I_C_RfQ rfq)
	{
		final List<I_C_RfQLine> lines = retrieveLinesQuery(rfq)
				.create()
				.list(I_C_RfQLine.class);

		// optimization
		for (final I_C_RfQLine line : lines)
		{
			line.setC_RfQ(rfq);
		}

		return lines;
	}

	@Override
	public int retrieveLinesCount(final I_C_RfQ rfq)
	{
		return retrieveLinesQuery(rfq)
				.create()
				.count();
	}

	private IQueryBuilder<I_C_RfQLine> retrieveLinesQuery(final I_C_RfQ rfq)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQLine.class, rfq)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQLine.COLUMNNAME_C_RfQ_ID, rfq.getC_RfQ_ID())
				.orderBy()
				.addColumn(I_C_RfQLine.COLUMNNAME_Line)
				.endOrderBy();
	}

	private IQueryBuilder<I_C_RfQLineQty> retrieveLineQtysQuery(final I_C_RfQLine line)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQLineQty.class, line)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQLineQty.COLUMNNAME_C_RfQLine_ID, line.getC_RfQLine_ID())
				.orderBy()
				.addColumn(I_C_RfQLineQty.COLUMNNAME_Qty)
				.endOrderBy();
	}

	@Override
	public List<I_C_RfQLineQty> retrieveLineQtys(final I_C_RfQLine line)
	{
		final List<I_C_RfQLineQty> lineQtys = retrieveLineQtysQuery(line)
				.create()
				.list(I_C_RfQLineQty.class);

		// optimization
		for (final I_C_RfQLineQty lineQty : lineQtys)
		{
			lineQty.setC_RfQLine(line);
		}

		return lineQtys;
	}

	@Override
	public int retrieveLineQtysCount(final I_C_RfQLine line)
	{
		return retrieveLineQtysQuery(line)
				.create()
				.count();
	}

	@Override
	public List<I_C_RfQResponse> retrieveAllResponses(final I_C_RfQ rfq)
	{
		final boolean activeOnly = false;
		final boolean completedOnly = false;
		return retrieveResponses(rfq, activeOnly, completedOnly);
	}

	@Override
	public List<I_C_RfQResponse> retrieveCompletedResponses(final I_C_RfQ rfq)
	{
		final boolean activeOnly = true;
		final boolean completedOnly = true;
		return retrieveResponses(rfq, activeOnly, completedOnly);
	}

	private List<I_C_RfQResponse> retrieveResponses(final I_C_RfQ rfq, final boolean activeOnly, final boolean completedOnly)
	{
		final IQueryBuilder<I_C_RfQResponse> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQResponse.class, rfq)
				.addEqualsFilter(I_C_RfQResponse.COLUMNNAME_C_RfQ_ID, rfq.getC_RfQ_ID())
				.orderBy()
				.addColumn(I_C_RfQResponse.COLUMNNAME_Price)
				.endOrderBy();

		if (activeOnly)
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}
		if (completedOnly)
		{
			queryBuilder.addEqualsFilter(I_C_RfQResponse.COLUMNNAME_IsComplete, true);
		}

		final List<I_C_RfQResponse> responses = queryBuilder.create().list(I_C_RfQResponse.class);

		// optimization
		for (final I_C_RfQResponse response : responses)
		{
			response.setC_RfQ(rfq);
		}

		return responses;
	}

	@Override
	public List<I_C_RfQResponseLine> retrieveResponseLines(final I_C_RfQResponse rfqResponse)
	{
		final List<I_C_RfQResponseLine> lines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQResponseLine.class, rfqResponse)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQResponseLine.COLUMNNAME_C_RfQResponse_ID, rfqResponse.getC_RfQResponse_ID())
				.orderBy()
				.addColumn(I_C_RfQResponseLine.COLUMNNAME_C_RfQResponseLine_ID)
				.endOrderBy()
				.create()
				.list(I_C_RfQResponseLine.class);

		// optimization
		for (final I_C_RfQResponseLine line : lines)
		{
			line.setC_RfQResponse(rfqResponse);
		}

		return lines;
	}

	@Override
	public List<I_C_RfQResponseLineQty> retrieveResponseQtys(final I_C_RfQLineQty rfqLineQty)
	{
		final List<I_C_RfQResponseLineQty> responseLineQtys = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQResponseLineQty.class, rfqLineQty)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQResponseLineQty.COLUMNNAME_C_RfQLineQty_ID, rfqLineQty.getC_RfQLineQty_ID())
				.orderBy()
				.addColumn(I_C_RfQResponseLineQty.COLUMNNAME_C_RfQResponseLineQty_ID)
				.endOrderBy()
				.create()
				.list(I_C_RfQResponseLineQty.class);

		// optimization
		for (final I_C_RfQResponseLineQty responseLineQty : responseLineQtys)
		{
			responseLineQty.setC_RfQLineQty(rfqLineQty);
		}

		return responseLineQtys;
	}

	@Override
	public List<I_C_RfQResponseLineQty> retrieveResponseQtys(final I_C_RfQResponseLine responseLine)
	{
		final List<I_C_RfQResponseLineQty> responseLineQtys = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQResponseLineQty.class, responseLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQResponseLineQty.COLUMNNAME_C_RfQResponseLine_ID, responseLine.getC_RfQResponseLine_ID())
				.orderBy()
				.addColumn(I_C_RfQResponseLineQty.COLUMNNAME_C_RfQResponseLineQty_ID)
				.endOrderBy()
				.create()
				.list(I_C_RfQResponseLineQty.class);

		// optimization
		for (final I_C_RfQResponseLineQty responseLineQty : responseLineQtys)
		{
			responseLineQty.setC_RfQResponseLine(responseLine);
		}

		return responseLineQtys;
	}

}
