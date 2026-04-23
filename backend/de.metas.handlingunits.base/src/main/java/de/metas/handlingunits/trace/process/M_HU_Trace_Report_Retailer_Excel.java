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

/**
 * HU Trace Report for Retailers - Focus on IFS compliance and sales control
 * This report is designed for retailers and focuses on:
 * - Receipt and shipment information (no production data)
 * - Customer/recipient details
 * - Sales order and invoice references
 * - Shipper and delivery information
 * - Complete supply chain traceability
 * Used for IFS Food Safety compliance and sales control.
 */
public class M_HU_Trace_Report_Retailer_Excel extends M_HU_Trace_Report_Template
{
	@NonNull
	@Override
	protected Set<HUTraceType> getTraceTypes()
	{
		return HUTraceType.typesToReportForRetailer();
	}

	@NonNull
	@Override
	protected String getSql(@NonNull final PInstanceId pinstanceId)
	{
		return " SELECT  * FROM M_HU_Trace_Report_Retailer(" + pinstanceId.getRepoId() + ")";
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
				"DocumentDate",
				I_M_HU_Trace.COLUMNNAME_Qty,
				I_M_Product.COLUMNNAME_C_UOM_ID,
				"MHD",
				"SubProducer",
				"NetWeight",
				"GrossWeight",
				"BPValue",
				"BPName",
				I_M_InOut.COLUMNNAME_BPartnerAddress,
				"CountryName",
				"OrderDocumentNo",
				"InvoiceDocumentNo",
				"PriceActual",
				"LineNetAmt",
				"PaymentTerm",
				"ShipperName",
				"WarehouseName",
				"Prod_Stock",
				"TraceId",
				"ReportDate"
		);
	}
}
