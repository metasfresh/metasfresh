/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.trace.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Set;

public class M_HU_Trace_Report_Excel extends M_HU_Trace_Report_Template
{
	@NonNull
	@Override
	protected Set<HUTraceType> getTraceTypes()
	{
		return HUTraceType.typesToReport();
	}

	@NonNull
	@Override
	protected String getSql(@NonNull final PInstanceId pinstanceId)
	{
		return " SELECT  * FROM M_HU_Trace_Report(" + pinstanceId.getRepoId() + ")";
	}

	@NonNull
	@Override
	protected List<String> getColumnHeaders()
	{
		return ImmutableList.of(
				I_M_HU_Trace.COLUMNNAME_LotNumber,
				I_M_HU_Trace.COLUMNNAME_HUTraceType,
				I_M_HU_Trace.COLUMNNAME_M_Product_ID,
				I_M_HU_Trace.COLUMNNAME_M_InOut_ID,
				I_M_HU_Trace.COLUMNNAME_PP_Order_ID,
				I_M_HU_Trace.COLUMNNAME_M_Inventory_ID,
				I_M_InOut.COLUMNNAME_MovementDate,
				I_M_HU_Trace.COLUMNNAME_Qty,
				I_M_Product.COLUMNNAME_C_UOM_ID,
				"Detail_Type",
				"Finished_Product_No",
				"Finished_Product_Name",
				"Finished_Product_Qty",
				"Finished_Product_UOM",
				"Finished_Product_Lot",
				"Vendor_Lot",
				"Finished_Product_Mhd",
				"Finished_Product_Clearance",
				"Customer_Vendor_No",
				"Customer_Vendor",
				"ShipmentQty",
				"Shipment_Note",
				"Shipment_Date",
				"Prod_Stock",
				"TraceId",
				"ReportDate"
		);
	}
}