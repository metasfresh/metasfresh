package org.adempiere.inout.replenish.service;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import de.metas.common.util.time.SystemTime;
import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.CustomColNames;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.I_M_Storage;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_T_Replenish;
import org.compiere.model.MRequisitionLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IStoragePA;
import de.metas.util.Services;

public final class ReplenishForFutureQty implements IReplenishForFutureQty {

	private static final Logger logger = LogManager.getLogger(ReplenishForFutureQty.class);

	private static final String SQL_REQUISITION_LINE = //
			"SELECT rl.* " //
					+ " FROM M_RequisitionLine rl LEFT JOIN M_Requisition r ON rl.M_Requisition_ID=r.M_Requisition_ID "
					+ " WHERE " //
					+ "    r.DocStatus='CO' " //
					+ "    AND rl.M_Product_ID=? and r.M_WareHouse_ID=?";

	@Override
	public BigDecimal getQtyToOrder(final I_M_Warehouse warehouse,
			final I_T_Replenish replenishPO, final String trxName) {

		// make sure that we just use the interfaces
		final I_T_Replenish replenish = replenishPO;

		final IPOService poService = Services.get(IPOService.class);

		final Object objTimeToMarket = poService.getValue(replenish,
				CustomColNames.T_Replenish_TimeToMarket);

		final int timeToMarket = (Integer) objTimeToMarket;
		final int periodId = (Integer) poService.getValue(replenish,
				CustomColNames.T_Replenish_C_Period_ID);

		final I_C_Period period = InterfaceWrapperHelper.create(Env.getCtx(), periodId, I_C_Period.class, trxName);

		final Timestamp startDate = period.getStartDate();
		final Calendar replenishStartDate = new GregorianCalendar();
		replenishStartDate.setTime(startDate);
		replenishStartDate.add(Calendar.WEEK_OF_YEAR, -timeToMarket);

		final Timestamp endDate = period.getEndDate();
		final Calendar replenishEndDate = new GregorianCalendar();
		replenishEndDate.setTime(endDate);
		replenishEndDate.add(Calendar.WEEK_OF_YEAR, -timeToMarket);

		// now calendar contains the date at which the replenishment must start
		// in order to get the product in time.

		final Date currentdate = SystemTime.asDate();
		if (currentdate.before(replenishStartDate.getTime())) {
			logger.debug("Current date is before replenish date '"
					+ replenishStartDate.toString() + "' - returning 0");
			return new BigDecimal("0");
		}

		if (currentdate.after(replenishEndDate.getTime())) {
			logger.debug("Current date is after replenish date '"
					+ replenishEndDate.toString() + "' - returning 0");
			return new BigDecimal("0");
		}

		BigDecimal qtyAvailable = new BigDecimal("0");
		BigDecimal qtyOrdered = new BigDecimal("0");

		final IStoragePA storageService = Services.get(IStoragePA.class);

		// get the quantities that are available and the quantities that are
		// already ordered.
		for (final I_M_Storage currentStorage : storageService
				.retrieveStorages(replenish.getM_Product_ID(), trxName)) {

			if (storageService.retrieveWarehouseId(currentStorage).getRepoId() == replenish.getM_Warehouse_ID())
			{
				qtyAvailable = qtyAvailable.add(currentStorage.getQtyOnHand()
						.subtract(currentStorage.getQtyReserved()));
				qtyOrdered = qtyOrdered.add(currentStorage.getQtyOrdered());
			}
		}

		final BigDecimal currentBaseQty = qtyAvailable.add(qtyOrdered);

		// this quantity should be stocked in the warehouse at startDate,
		// if nothing is sold in between.
		BigDecimal qtyToOrder = replenish.getLevel_Max().subtract(
				currentBaseQty);

		// subtract the quantities that have already been requisitioned, but not yet ordered.
		final Collection<I_M_RequisitionLine> reqLines = retrieveRequisitionlines(replenish.getM_Product_ID(),
						replenish.getM_Warehouse_ID(), trxName);

		for (final I_M_RequisitionLine requisitionLine : reqLines) {

			if (requisitionLine.getC_OrderLine_ID() != 0) {
				qtyToOrder = qtyToOrder.subtract(requisitionLine.getQty());
			}
		}
		return qtyToOrder;
	}

	/**
	 * Loads the lines of completed {@link I_M_Requisition}s for a product and warehouse.
	 *
	 * @param productId
	 * @param warehouseId
	 * @param trxName
	 * @return the requisition lines for the given product and warehouse.
	 */
	private Collection<I_M_RequisitionLine> retrieveRequisitionlines(int productId, int warehouseId, String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SQL_REQUISITION_LINE, trxName);

		ResultSet rs = null;

		try
		{
			pstmt.setInt(1, productId);
			pstmt.setInt(2, warehouseId);

			final ArrayList<I_M_RequisitionLine> result = new ArrayList<>();

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(new MRequisitionLine(Env.getCtx(), rs, trxName));
			}
			return result;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
