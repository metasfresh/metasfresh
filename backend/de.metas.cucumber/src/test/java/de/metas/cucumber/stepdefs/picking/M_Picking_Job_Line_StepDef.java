/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.picking;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.user.UserId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.X_DD_Order;

import java.math.BigDecimal;

/**
 * Step definitions for {@code M_Picking_Job_Line} records (creating minimal picking-job lines to simulate
 * a busy picker in DD_Order replenishment tests).
 */
@RequiredArgsConstructor
public class M_Picking_Job_Line_StepDef
{
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Product_StepDefData productTable;

	/**
	 * Creates a minimal but valid {@code M_Picking_Job} + {@code M_Picking_Job_Line} linked to a shipment schedule,
	 * making the picker "busy" on that schedule's DD_Order (the busy-check matches on {@code M_ShipmentSchedule_ID}).
	 *
	 * <p>Required columns:</p>
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — schedule the picking-job line references (identifier).</li>
	 *   <li>{@code C_OrderLine_ID} — the sales-order line (identifier); provides C_Order/BPartner context.</li>
	 *   <li>{@code M_Product_ID} — product (identifier).</li>
	 *   <li>{@code QtyToPick} — quantity to pick.</li>
	 *   <li>{@code C_UOM_ID} — unit of measure repo id (int).</li>
	 * </ul>
	 *
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_Picking_Job_Line:
	 *   | M_ShipmentSchedule_ID | C_OrderLine_ID | M_Product_ID | QtyToPick | C_UOM_ID |
	 *   | shipmentSchedule      | orderLine      | product      | 5         | 1000001  |
	 * </pre>
	 */
	@And("metasfresh contains M_Picking_Job_Line:")
	public void create_M_Picking_Job_Line(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::create_M_Picking_Job_Line);
	}

	private void create_M_Picking_Job_Line(@NonNull final DataTableRow row)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(row.getAsIdentifier(I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID).getAsString());
		final I_C_OrderLine orderLine = orderLineTable.get(row.getAsIdentifier(I_M_Picking_Job_Line.COLUMNNAME_C_OrderLine_ID).getAsString());
		final I_C_Order order = InterfaceWrapperHelper.load(orderLine.getC_Order_ID(), I_C_Order.class);
		final I_M_Product product = productTable.get(row.getAsIdentifier(I_M_Picking_Job_Line.COLUMNNAME_M_Product_ID).getAsString());
		final BigDecimal qtyToPick = row.getAsBigDecimal(I_M_Picking_Job_Line.COLUMNNAME_QtyToPick);
		final int uomId = row.getAsInt(I_M_Picking_Job_Line.COLUMNNAME_C_UOM_ID);

		final int bpartnerId = order.getC_BPartner_ID();
		final int bpartnerLocationId = order.getC_BPartner_Location_ID();

		final I_M_Picking_Job pickingJob = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		pickingJob.setC_BPartner_ID(bpartnerId);
		pickingJob.setC_BPartner_Location_ID(bpartnerLocationId);
		pickingJob.setC_Order_ID(order.getC_Order_ID());
		pickingJob.setDeliveryToAddress("cucumber-picking-job");
		pickingJob.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		pickingJob.setPicking_User_ID(UserId.METASFRESH.getRepoId());
		pickingJob.setPreparationDate(TimeUtil.asTimestamp(Env.getDate(Env.getCtx())));
		pickingJob.setDeliveryDate(TimeUtil.asTimestamp(Env.getDate(Env.getCtx())));
		InterfaceWrapperHelper.saveRecord(pickingJob);

		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setM_Picking_Job_ID(pickingJob.getM_Picking_Job_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_Order_ID(order.getC_Order_ID());
		line.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		line.setC_BPartner_ID(bpartnerId);
		line.setC_BPartner_Location_ID(bpartnerLocationId);
		line.setQtyToPick(qtyToPick);
		line.setC_UOM_ID(uomId);
		line.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		InterfaceWrapperHelper.saveRecord(line);
	}
}
