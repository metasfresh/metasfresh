/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.distributionorder;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Shipper_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class DD_Order_StepDef
{
	private final DD_OrderLine_StepDefData ddOrderLineTable;
	private final DD_Order_StepDefData ddOrderTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Shipper_StepDefData shipperTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	public DD_Order_StepDef(
			@NonNull final DD_OrderLine_StepDefData ddOrderLineTable,
			@NonNull final DD_Order_StepDefData ddOrderTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Shipper_StepDefData shipperTable)
	{
		this.ddOrderLineTable = ddOrderLineTable;
		this.ddOrderTable = ddOrderTable;
		this.warehouseTable = warehouseTable;
		this.shipperTable = shipperTable;
	}

	@And("DD_Orders are found:")
	public void find_DDOrders(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findDDOrder(tableRow);
		}
	}

	@And("^the dd_order identified by (.*) is (completed|reactivated|reversed|voided|closed)$")
	public void order_action(@NonNull final String orderIdentifier, @NonNull final String action)
	{
		final I_DD_Order order = ddOrderTable.get(orderIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case completed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			case reactivated:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				break;
			case reversed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			case voided:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Void, IDocument.STATUS_Voided);
				break;
			case closed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Close, IDocument.STATUS_Closed);
				break;
			default:
				throw new AdempiereException("Unhandled DD_Order action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	@And("DD_Orders are validated:")
	public void validate_DD_Orders(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateDDOrder(tableRow);
		}
	}

	private void validateDDOrder(@NonNull final Map<String, String> tableRow)
	{
		final SoftAssertions softly = new SoftAssertions();

		final String ddOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_Order.COLUMNNAME_DD_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_Order ddOrderRecord = ddOrderTable.get(ddOrderIdentifier);
		softly.assertThat(ddOrderRecord).isNotNull();

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseIdentifier))
		{
			final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);
			softly.assertThat(warehouseRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Warehouse_ID()).isEqualTo(warehouseRecord.getM_Warehouse_ID());
		}

		final String warehouseFromIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Warehouse_From_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseFromIdentifier))
		{
			final I_M_Warehouse warehouseFromRecord = warehouseTable.get(warehouseFromIdentifier);
			softly.assertThat(warehouseFromRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Warehouse_From_ID()).isEqualTo(warehouseFromRecord.getM_Warehouse_ID());
		}

		final String warehouseToIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Warehouse_To_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseToIdentifier))
		{
			final I_M_Warehouse warehouseToRecord = warehouseTable.get(warehouseToIdentifier);
			softly.assertThat(warehouseToRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Warehouse_To_ID()).isEqualTo(warehouseToRecord.getM_Warehouse_ID());
		}

		final String shipperIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipperIdentifier))
		{
			final I_M_Shipper shipperRecord = shipperTable.get(shipperIdentifier);
			softly.assertThat(shipperRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Shipper_ID()).isEqualTo(shipperRecord.getM_Shipper_ID());
		}

		final String docStatus = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_DocStatus);
		if (Check.isNotBlank(docStatus))
		{
			softly.assertThat(ddOrderRecord.getDocStatus()).isEqualTo(docStatus);
		}

		softly.assertAll();
	}

	private void findDDOrder(@NonNull final Map<String, String> tableRow)
	{
		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_OrderLine ddOrderLineRecord = ddOrderLineTable.get(ddOrderLineIdentifier);
		assertThat(ddOrderLineRecord).isNotNull();
		
		final I_DD_Order ddOrderRecord = InterfaceWrapperHelper.load(ddOrderLineRecord.getDD_Order_ID(), I_DD_Order.class);

		final String ddOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_Order.COLUMNNAME_DD_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		ddOrderTable.putOrReplace(ddOrderIdentifier, ddOrderRecord);
	}
}
