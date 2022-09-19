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

package de.metas.cucumber.stepdefs.receiptschedule;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_Location_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_C_OrderLine_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_C_Order_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_Product_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_QtyOrdered;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_ReceiptSchedule_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Product_StepDefData productTable;

	public M_ReceiptSchedule_StepDef(
			@NonNull final M_ReceiptSchedule_StepDefData receiptScheduleTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.receiptScheduleTable = receiptScheduleTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.warehouseTable = warehouseTable;
		this.productTable = productTable;
	}

	@And("^after not more than (.*)s, M_ReceiptSchedule are found:$")
	public void there_are_receiptSchedule(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadReceiptSchedule(tableRow));

			final String receiptScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
			InterfaceWrapperHelper.refresh(receiptSchedule);

			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Order order = orderTable.get(orderIdentifier);

			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

			final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartnerRecord = bPartnerTable.get(bPartnerIdentifier);

			final String bpPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer bPartnerLocationID = bPartnerLocationTable.getOptional(bpPartnerLocationIdentifier)
					.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
					.orElseGet(() -> Integer.parseInt(bpPartnerLocationIdentifier));
			assertThat(bPartnerLocationID).isNotNull();

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer productID = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));
			assertThat(productID).isNotNull();

			final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyOrdered);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);

			assertThat(receiptSchedule.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			assertThat(receiptSchedule.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			assertThat(receiptSchedule.getC_BPartner_ID()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
			assertThat(receiptSchedule.getC_BPartner_Location_ID()).isEqualTo(bPartnerLocationID);
			assertThat(receiptSchedule.getM_Product_ID()).isEqualTo(productID);
			assertThat(receiptSchedule.getQtyOrdered()).isEqualTo(qtyOrdered);
			assertThat(receiptSchedule.getM_Warehouse_ID()).isEqualTo(warehouse.getM_Warehouse_ID());

			final BigDecimal qtyMoved = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_ReceiptSchedule.COLUMNNAME_QtyMoved);
			if (qtyMoved != null)
			{
				assertThat(receiptSchedule.getQtyMoved()).isEqualTo(qtyMoved);
			}

			final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_M_ReceiptSchedule.COLUMNNAME_Processed, false);
			assertThat(receiptSchedule.isProcessed()).isEqualTo(processed);

			final BigDecimal qtyOrderedTU = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_ReceiptSchedule.COLUMNNAME_QtyOrderedTU);
			if (qtyOrderedTU != null)
			{
				final de.metas.handlingunits.model.I_C_OrderLine orderLine1 = InterfaceWrapperHelper.load(receiptSchedule.getC_OrderLine_ID(), de.metas.handlingunits.model.I_C_OrderLine.class);
				assertThat(orderLine1.getQtyEnteredTU()).isEqualTo(qtyOrderedTU);
			}

			receiptScheduleTable.putOrReplace(receiptScheduleIdentifier, receiptSchedule);
		}
	}

	@NonNull
	private Boolean loadReceiptSchedule(@NonNull final Map<String, String> tableRow)
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine purchaseOrderLine = orderLineTable.get(orderLineIdentifier);
		assertThat(purchaseOrderLine).isNotNull();

		final Optional<I_M_ReceiptSchedule> receiptSchedule = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMN_C_OrderLine_ID, purchaseOrderLine.getC_OrderLine_ID())
				.create()
				.firstOnlyOptional(I_M_ReceiptSchedule.class);

		if (!receiptSchedule.isPresent())
		{
			return false;
		}

		final String receiptScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		receiptScheduleTable.putOrReplace(receiptScheduleIdentifier, receiptSchedule.get());

		return true;
	}
}
