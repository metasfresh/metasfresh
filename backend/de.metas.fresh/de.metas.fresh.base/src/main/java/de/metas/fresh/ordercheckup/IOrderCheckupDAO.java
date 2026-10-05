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
import java.util.Map;

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
	 * The order's reports (active or not), keyed by {@link OrderCheckupReportIdentity}, keeping the one with the
	 * highest {@code C_Order_MFGWarehouse_Report_ID} per identity. The reports of one generation run are written
	 * together, so that is the record the latest run produced for that identity.
	 * <p>
	 * Limitation: an identity the latest run no longer produced -- which takes the set of users in charge changing
	 * between two generations -- is still in the result, carrying the older run's record. A caller reactivating
	 * everything it finds here therefore brings a stale report back. Telling the two runs apart would take
	 * recording which records were written together.
	 */
	Map<OrderCheckupReportIdentity, I_C_Order_MFGWarehouse_Report> retrieveNewestReportPerIdentity(I_C_Order order);

	void save(I_C_Order_MFGWarehouse_Report report);
}
