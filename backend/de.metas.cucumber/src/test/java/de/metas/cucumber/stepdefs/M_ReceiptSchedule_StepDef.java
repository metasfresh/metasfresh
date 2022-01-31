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

package de.metas.cucumber.stepdefs;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
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

	private final StepDefData<I_M_ReceiptSchedule> receiptScheduleTable;
	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_C_OrderLine> orderLineTable;
	private final StepDefData<I_C_BPartner> bPartnerTable;
	private final StepDefData<I_C_BPartner_Location> bPartnerLocationTable;
	private final StepDefData<I_M_Warehouse> warehouseTable;
	private final StepDefData<I_M_Product> productTable;

	public M_ReceiptSchedule_StepDef(
			@NonNull final StepDefData<I_M_ReceiptSchedule> receiptScheduleTable,
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_C_OrderLine> orderLineTable,
			@NonNull final StepDefData<I_C_BPartner> bPartnerTable,
			@NonNull final StepDefData<I_C_BPartner_Location> bPartnerLocationTable,
			@NonNull final StepDefData<I_M_Warehouse> warehouseTable,
			@NonNull final StepDefData<I_M_Product> productTable)
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

			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Order order = orderTable.get(orderIdentifier);

			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

			final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);

			final String bpPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bpPartnerLocationIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyOrdered);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);

			assertThat(receiptSchedule.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			assertThat(receiptSchedule.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			assertThat(receiptSchedule.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());
			assertThat(receiptSchedule.getC_BPartner_Location_ID()).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());
			assertThat(receiptSchedule.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			assertThat(receiptSchedule.getQtyOrdered()).isEqualTo(qtyOrdered);
			assertThat(receiptSchedule.getM_Warehouse_ID()).isEqualTo(warehouse.getM_Warehouse_ID());
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
		receiptScheduleTable.put(receiptScheduleIdentifier, receiptSchedule.get());

		return true;
	}
}
