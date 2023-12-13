package de.metas.fresh.ordercheckup.impl;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.fresh.ordercheckup.IOrderCheckupDAO;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Order;

import java.util.List;

public class OrderCheckupDAO implements IOrderCheckupDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public List<I_C_Order_MFGWarehouse_Report> retrieveAllReports(final I_C_Order order)
	{
		return queryBL
				.createQueryBuilder(I_C_Order_MFGWarehouse_Report.class, order)
				// .addOnlyActiveRecordsFilter() // return all of them
				.addEqualsFilter(I_C_Order_MFGWarehouse_Report.COLUMN_C_Order_ID, order.getC_Order_ID())
				.orderBy()
				.addColumn(I_C_Order_MFGWarehouse_Report.COLUMN_C_Order_MFGWarehouse_Report_ID)
				.endOrderBy()
				.create()
				.list(I_C_Order_MFGWarehouse_Report.class);
	}

	@Override
	public List<I_C_Order_MFGWarehouse_ReportLine> retrieveAllReportLines(final I_C_Order_MFGWarehouse_Report report)
	{
		return queryBL
				.createQueryBuilder(I_C_Order_MFGWarehouse_ReportLine.class, report)
				// .addOnlyActiveRecordsFilter() // return all of them
				.addEqualsFilter(I_C_Order_MFGWarehouse_ReportLine.COLUMN_C_Order_MFGWarehouse_Report_ID, report.getC_Order_MFGWarehouse_Report_ID())
				.orderBy()
				.addColumn(I_C_Order_MFGWarehouse_ReportLine.COLUMN_C_Order_MFGWarehouse_ReportLine_ID)
				.endOrderBy()
				.create()
				.list(I_C_Order_MFGWarehouse_ReportLine.class);
	}

}
