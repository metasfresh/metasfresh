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

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.distribution.DD_NetworkDistributionLine_StepDefData;
import de.metas.distribution.ddorder.lowlevel.model.DDOrderLineHUPackingAware;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_DD_Order_MoveSchedule;
import de.metas.order.OrderLineId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID;

public class DD_OrderLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IHUDocumentHandlerFactory huDocumentHandlerFactory = Services.get(IHUDocumentHandlerFactory.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	
	private final M_Product_StepDefData productTable;
	private final DD_Order_StepDefData ddOrderTable;
	private final DD_OrderLine_StepDefData ddOrderLineTable;
	private final M_Locator_StepDefData locatorTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final DD_NetworkDistributionLine_StepDefData ddNetworkLineTable;

	public DD_OrderLine_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final DD_Order_StepDefData ddOrderTable,
			@NonNull final DD_OrderLine_StepDefData ddOrderLineTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final DD_NetworkDistributionLine_StepDefData ddNetworkLineTable)
	{
		this.productTable = productTable;
		this.ddOrderTable = ddOrderTable;
		this.ddOrderLineTable = ddOrderLineTable;
		this.locatorTable = locatorTable;
		this.orderLineTable = orderLineTable;
		this.ddNetworkLineTable = ddNetworkLineTable;
	}

	@Given("metasfresh contains DD_OrderLines:")
	public void metasfresh_contains_dd_order_lines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + ".Identifier");
			final I_M_Product product = productTable.get(productIdentifier);

			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_DD_Order order = ddOrderTable.get(orderIdentifier);

			final String locatorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Locator locator = locatorTable.get(locatorIdentifier);

			final String locatorToIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Locator locatorTo = locatorTable.get(locatorToIdentifier);

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_QtyEntered);

			final I_DD_OrderLine orderLine = newInstance(I_DD_OrderLine.class);
			orderLine.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			orderLine.setM_Product_ID(product.getM_Product_ID());
			orderLine.setQtyEntered(qtyEntered);
			orderLine.setQtyOrdered(qtyEntered);
			orderLine.setDD_Order_ID(order.getDD_Order_ID());
			orderLine.setM_Locator_ID(locator.getM_Locator_ID());
			orderLine.setM_LocatorTo_ID(locatorTo.getM_Locator_ID());
			orderLine.setIsInvoiced(false);

			saveRecord(orderLine);

			final IHUDocumentHandler handler = huDocumentHandlerFactory.createHandler(I_DD_OrderLine.Table_Name);
			handler.applyChangesFor(orderLine);

			final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(InterfaceWrapperHelper.loadOrNew(OrderLineId.ofRepoId(orderLine.getDD_OrderLine_ID()), de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine.class));
			huPackingAwareBL.setQtyTU(packingAware);

			final QtyTU qtyPacks = QtyTU.ofBigDecimal(packingAware.getQtyTU());
			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyPacks.toInt());

			saveRecord(orderLine);

			ddOrderLineTable.putOrReplace(DataTableUtil.extractRecordIdentifier(tableRow, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID), orderLine);
		}
	}

	@And("validate DD_Order_MoveSchedule")
	public void validate_move_schedule(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_DD_OrderLine orderLine = ddOrderLineTable.get(orderLineIdentifier);

			final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_DD_Order_MoveSchedule.COLUMNNAME_QtyPicked);

			final I_DD_Order_MoveSchedule moveSchedule = queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
					.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID, orderLine.getDD_OrderLine_ID())
					.orderBy(I_DD_Order_MoveSchedule.COLUMNNAME_Created)
					.create()
					.firstOnly(I_DD_Order_MoveSchedule.class);

			assertThat(moveSchedule).isNotNull();
			assertThat(moveSchedule.getQtyToPick()).isEqualTo(qtyPicked);

		}
	}

	@And("^after not more than (.*)s, DD_OrderLines are found:$")
	public void find_DDOrderLines(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findDDOrderLine(timeoutSec, tableRow);
		}
	}

	@And("DD_OrderLines are validated:")
	public void validate_DD_OrderLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateDDOrderLine(tableRow);
		}
	}

	@And("DD_OrderLines are updated:")
	public void update_DD_OrderLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateDDOrderLine(tableRow);
		}
	}

	private void updateDDOrderLine(@NonNull final Map<String, String> tableRow)
	{
		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_OrderLine ddOrderLineRecord = ddOrderLineTable.get(ddOrderLineIdentifier);
		assertThat(ddOrderLineRecord).isNotNull();

		final BigDecimal qtyOrderedAndEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyOrdered);
		if (qtyOrderedAndEntered != null)
		{
			ddOrderLineRecord.setQtyOrdered(qtyOrderedAndEntered);
			ddOrderLineRecord.setQtyEntered(qtyOrderedAndEntered);
		}

		saveRecord(ddOrderLineRecord);
	}

	private void validateDDOrderLine(@NonNull final Map<String, String> tableRow)
	{
		final SoftAssertions softly = new SoftAssertions();

		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_OrderLine ddOrderLineRecord = ddOrderLineTable.get(ddOrderLineIdentifier);
		softly.assertThat(ddOrderLineRecord).isNotNull();

		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyOrdered);
		if (qtyOrdered != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyOrdered()).isEqualByComparingTo(qtyOrdered);
		}

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyDelivered()).isEqualByComparingTo(qtyDelivered);
		}

		final Integer line = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_Line);
		if (line != null)
		{
			softly.assertThat(ddOrderLineRecord.getLine()).isEqualByComparingTo(line);
		}

		final String ddNetworkDistributionLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_DD_NetworkDistributionLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(ddNetworkDistributionLineIdentifier))
		{
			final I_DD_NetworkDistributionLine ddNetworkDistributionLineRecord = ddNetworkLineTable.get(ddNetworkDistributionLineIdentifier);
			softly.assertThat(ddOrderLineRecord.getDD_NetworkDistributionLine_ID()).isEqualTo(ddNetworkDistributionLineRecord.getDD_NetworkDistributionLine_ID());
		}

		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(productIdentifier))
		{
			final I_M_Product productRecord = productTable.get(productIdentifier);
			softly.assertThat(ddOrderLineRecord.getM_Product_ID()).isEqualTo(productRecord.getM_Product_ID());
		}

		final BigDecimal qtyReserved = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyReserved);
		if (qtyReserved != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyReserved()).isEqualByComparingTo(qtyReserved);
		}

		softly.assertAll();
	}

	private void findDDOrderLine(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String soOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine soOrderLineRecord = orderLineTable.get(soOrderLineIdentifier);
		assertThat(soOrderLineRecord).isNotNull();

		final Supplier<Optional<I_DD_OrderLine>> ddOrderLineSupplier = () -> queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID, soOrderLineRecord.getC_OrderLine_ID())
				.create()
				.firstOnlyOptional(I_DD_OrderLine.class);

		final I_DD_OrderLine ddOrderLineRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, ddOrderLineSupplier);

		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		ddOrderLineTable.putOrReplace(ddOrderLineIdentifier, ddOrderLineRecord);
	}
}
