/**
 *
 */
package de.metas.handlingunits.client.terminal.select.api.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Product;
import org.eevolution.model.X_M_Warehouse_Routing;

import de.metas.handlingunits.client.terminal.receiptschedule.model.IReceiptScheduleTableRow;
import de.metas.handlingunits.client.terminal.receiptschedule.model.ReceiptScheduleTableRow;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;

/**
 * @author cg
 *
 */
public class ReceiptScheduleFiltering extends AbstractFiltering
{
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends IPOSTableRow> getTableRowType()
	{
		return IReceiptScheduleTableRow.class;
	}

	@Override
	public List<I_M_Warehouse> retrieveWarehouses(final Properties ctx)
	{
		return Services.get(IWarehouseDAO.class).retrieveWarehouses(ctx, X_M_Warehouse_Routing.DOCBASETYPE_MaterialReceipt);
	}

	private ICompositeQueryFilter<I_M_ReceiptSchedule> createFilter(final Properties ctx, final int warehouseId, final int bpartnerId, final int orderId)
	{
		final ICompositeQueryFilter<I_M_ReceiptSchedule> filters = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(I_M_ReceiptSchedule.class);

		// Only active
		filters.addOnlyActiveRecordsFilter();

		// Only for context AD_Client_ID
		filters.addOnlyContextClient(ctx);

		//
		// Only not processed lines
		filters.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_Processed, false);

		// Only those which were prepared (i.e. planning HUs were generated)
		filters.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_IsHUPrepared, true);

		// filter by warehouse
		filters.addCoalesceEqualsFilter(warehouseId,
				de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_Override_ID,
				de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID);

		// filter by vendor
		if (bpartnerId > 0)
		{
			filters.addFilter(new IQueryFilter<I_M_ReceiptSchedule>()
			{

				@Override
				public boolean accept(final I_M_ReceiptSchedule schedule)
				{
					if (schedule == null)
					{
						return false;
					}
					final I_C_Order order = schedule.getC_Order();
					if (order == null)
					{
						return false;
					}

					if (order.getC_BPartner_ID() != bpartnerId)
					{
						return false;
					}

					return true;
				}
			});
			filters.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_ID, bpartnerId);
		}

		// filter by purchase order
		if (orderId > 0)
		{
			filters.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_C_Order_ID, orderId);
		}

		return filters;
	}

	@Override
	public List<IPOSTableRow> retrieveTableRows(final Properties ctx, final int warehouseId)
	{
		final int bpartnerId = -1;
		final int orderId = -1;
		final ICompositeQueryFilter<I_M_ReceiptSchedule> filters = createFilter(ctx, warehouseId, bpartnerId, orderId);

		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ReceiptSchedule.class, ctx, ITrx.TRXNAME_None)
				.filter(filters);

		// task 06570: exclude lines that are packaging materials
		queryBuilder.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_IsPackagingMaterial, false);

		// order by
		queryBuilder.orderBy()
				.addColumn(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_MovementDate)
				.addColumn(de.metas.tourplanning.model.I_M_ReceiptSchedule.COLUMNNAME_PreparationTime)
				.addColumn(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID);

		final List<I_M_ReceiptSchedule> schedules = queryBuilder.create()
				.setApplyAccessFilterRW(true)
				.list(I_M_ReceiptSchedule.class)
				.stream()

		// task 07074: exclude lines with products that have type != item (fixes this comment when i migrated to lambda)
				.filter(rs -> X_M_Product.PRODUCTTYPE_Item.equals(rs.getM_Product().getProductType()))

		.collect(Collectors.toList());

		return createTableRows(schedules);
	}

	private List<IPOSTableRow> createTableRows(final List<? extends Object> schedules)
	{
		if (schedules == null || schedules.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<IPOSTableRow> result = new ArrayList<IPOSTableRow>(schedules.size());
		for (final Object object : schedules)
		{
			Check.assumeInstanceOf(object, I_M_ReceiptSchedule.class, "object");
			final I_M_ReceiptSchedule schedule = InterfaceWrapperHelper.create(object, I_M_ReceiptSchedule.class);
			final IPOSTableRow row = new ReceiptScheduleTableRow(schedule);
			result.add(row);
		}

		return result;
	}

	public final ReceiptScheduleTableRow getReceiptScheduleTableRow(final IPOSTableRow row)
	{
		return (ReceiptScheduleTableRow)row;
	}

	@Override
	public I_M_ReceiptSchedule getReferencedObject(final IPOSTableRow row)
	{
		if (row == null)
		{
			return null;
		}

		final ReceiptScheduleTableRow rsRow = getReceiptScheduleTableRow(row);
		final I_M_ReceiptSchedule rs = rsRow.getM_ReceiptSchedule();
		return rs;
	}

	/**
	 * retrieve the selected receipt schedules
	 *
	 * @param rows
	 * @return
	 */
	private List<I_M_ReceiptSchedule> getReceiptSchedules(final Collection<IPOSTableRow> rows)
	{
		if (rows == null || rows.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<I_M_ReceiptSchedule> result = new ArrayList<I_M_ReceiptSchedule>(rows.size());
		for (final IPOSTableRow row : rows)
		{
			final ReceiptScheduleTableRow rsRow = getReceiptScheduleTableRow(row);
			final I_M_ReceiptSchedule rs = rsRow.getM_ReceiptSchedule();
			result.add(rs);
		}

		return result;
	}

	@Override
	public void processRows(final Properties ctx, final Set<IPOSTableRow> rows, final Set<I_M_HU> selectedHUs)
	{
		//
		// No rows => nothing to do
		if (rows == null || rows.isEmpty())
		{
			return;
		}

		//
		// Get receipt schedules from rows
		final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules(rows);

		final boolean storeReceipts = false;
		Services.get(IHUReceiptScheduleBL.class).processReceiptSchedules(ctx, receiptSchedules, selectedHUs, storeReceipts);
	}

	/**
	 * Closes the selected <code>rows<code> and marks the respective receipt schedules a "processed".<br>
	 * Sets QtyOrderedOverUnder in OrderLine and ReceiptSchedule.
	 * Note that by closing, the user indicates that there shall be no further receipts.
	 */
	@Override
	public void closeRows(final Set<IPOSTableRow> rows)
	{
		if (rows == null || rows.isEmpty())
		{
			return;
		}

		// Services
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		//
		// Iterate rows and close them one by one
		for (final IPOSTableRow row : rows)
		{
			final ReceiptScheduleTableRow receiptScheduleRow = getReceiptScheduleTableRow(row);
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleRow.getM_ReceiptSchedule();

			//
			// Mark the line as closed
			receiptScheduleBL.close(receiptSchedule);
		}
	}
}
