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

import java.util.List;

import org.adempiere.warehouse.model.I_M_Warehouse;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.util.ISingletonService;

public interface IOrderCheckupDAO extends ISingletonService
{
	/**
	 * For the given order line, return a {@link I_PP_Product_Planning} which has either <code>IsManufactured='Y'</code> or, if there is not such, with has <code>IsTraded='Y'</code>.
	 *
	 * @return product data planning or <code>null</code>.
	 */
	I_PP_Product_Planning retrieveProductPlanningOrNull(I_C_OrderLine orderLine);

	/**
	 * For the given order line, return those warehouses that are referenced by a manufacturing_<code>PP_Product_Planning</code> record that also references the order line.
	 *
	 * @return warehouse or null
	 */
	I_M_Warehouse retrieveManufacturingWarehouseOrNull(I_C_OrderLine orderLine);

	/**
	 * Retrieves all (active or not) reports for given order
	 */
	List<I_C_Order_MFGWarehouse_Report> retrieveAllReports(I_C_Order order);

	/**
	 * Retrieves all (active or not) report lines for given report.
	 */
	List<I_C_Order_MFGWarehouse_ReportLine> retrieveAllReportLines(I_C_Order_MFGWarehouse_Report report);
}
