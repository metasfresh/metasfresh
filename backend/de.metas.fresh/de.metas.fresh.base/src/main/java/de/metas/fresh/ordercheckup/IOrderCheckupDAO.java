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
import de.metas.order.OrderId;
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
	 * Computes the generation number to stamp on every {@code C_Order_MFGWarehouse_Report} created by the next
	 * {@code generateReportsIfEligible} run for the given order: one past the highest generation ever stamped on
	 * any report of that order (active or not). Reports that predate the {@code OrderCheckupGeneration} column
	 * have no generation (SQL {@code NULL}) and are ignored, so the first run after this change starts at 1.
	 */
	int retrieveNextGenerationNo(OrderId orderId);

	/**
	 * Retrieves the {@code C_Order_MFGWarehouse_Report}s (active or not) whose {@code OrderCheckupGeneration}
	 * equals the highest generation ever stamped for the given order.
	 * <p>
	 * Returns an empty list both when the order has no reports at all, and when the highest generation predates
	 * the {@code OrderCheckupGeneration} column (SQL {@code NULL}) -- legacy data that has no generation to
	 * selectively restore, so the caller falls back to a full rebuild. The NULL check happens in SQL (via the
	 * {@code MAX} aggregate, which itself ignores {@code NULL} rows and is only {@code NULL} when every row is
	 * {@code NULL}); it must never be approximated as {@code getOrderCheckupGeneration() == 0} in Java, because the
	 * generated accessor is a primitive {@code int} and reads a SQL {@code NULL} back as {@code 0}, indistinguishable
	 * from a real generation {@code 0}.
	 */
	List<I_C_Order_MFGWarehouse_Report> retrieveReportsOfMostRecentGeneration(OrderId orderId);
}
