package de.metas.fresh.ordercheckup;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_Order;

import java.util.List;

public interface IOrderCheckupDAO extends ISingletonService
{
	/**
	 * Retrieves all (active or not) reports for given order
	 */
	List<I_C_Order_MFGWarehouse_Report> retrieveAllReports(I_C_Order order);

	/**
	 * Retrieves all (active or not) report lines for given report.
	 */
	List<I_C_Order_MFGWarehouse_ReportLine> retrieveAllReportLines(I_C_Order_MFGWarehouse_Report report);

	/**
	 * Retrieves the report (active or not) with the highest {@code C_Order_MFGWarehouse_Report_ID} per
	 * {@link OrderCheckupReportIdentity}. The reports of one generation run are written together, so that is the
	 * record the latest run produced for that identity.
	 * <p>
	 * Limitation: when a later run produced fewer identities than an earlier one -- which takes the set of users in
	 * charge changing between two generations -- the earlier run's now-orphaned identity is returned alongside the
	 * later run's records, so reactivating this set brings a stale report back. Telling the two runs apart would
	 * take recording which records were written together.
	 */
	List<I_C_Order_MFGWarehouse_Report> retrieveNewestReportPerIdentity(I_C_Order order);

	void save(I_C_Order_MFGWarehouse_Report report);
}
