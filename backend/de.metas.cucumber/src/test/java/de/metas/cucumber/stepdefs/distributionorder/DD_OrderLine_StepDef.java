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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.IdentifierIds_StepDefData;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.model.DDOrderLineHUPackingAware;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_DD_Order_MoveSchedule;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID;

@RequiredArgsConstructor
public class DD_OrderLine_StepDef
{
	@NonNull private static final Logger logger = LogManager.getLogger(DD_OrderLine_StepDef.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IHUDocumentHandlerFactory huDocumentHandlerFactory = Services.get(IHUDocumentHandlerFactory.class);
	@NonNull private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	@NonNull private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;
	@NonNull private final M_Warehouse_StepDefData warehouseData;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final IdentifierIds_StepDefData identifierIdsTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;

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

	@And("^no DD_OrderLine found for orderLine (.*)$")
	public void validate_no_DD_OrderLine_found(@NonNull final String orderLineIdentifier)
	{
		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);

		try
		{
			assertThat(queryByOrderLineId(orderLineId).count()).isZero();
		}
		catch (final Throwable throwable)
		{
			logCurrentContextExpectedNoRecords(orderLineId);
		}
	}

	@And("^after not more than (.*)s, DD_OrderLine found for orderLine (.*)$")
	public void validate_DD_OrderLines_found_for_OrderLine(
			final int timeoutSec,
			@NonNull final String orderLineIdentifier,
			@NonNull final DataTable dataTable)
	{
		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);

		final HashSet<DDOrderLineId> alreadyMatchedLineIds = new HashSet<>();
		DataTableRows.of(dataTable).forEach(row -> {
			final I_DD_OrderLine ddOrderLineRecord = StepDefUtil.tryAndWaitForItem(toDDOrderLineQuery(orderLineId, alreadyMatchedLineIds))
					.validateUsingFunction(ddOrderLine -> checkDDOrderLineMatching(ddOrderLine, row))
					.maxWaitSeconds(timeoutSec)
					.execute();

			alreadyMatchedLineIds.add(DDOrderLineId.ofRepoId(ddOrderLineRecord.getDD_OrderLine_ID()));
			row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderLineTable.putOrReplace(identifier, ddOrderLineRecord));

			row.getAsOptionalIdentifier(I_DD_OrderLine.COLUMNNAME_DD_Order_ID)
					.ifPresent(ddOrderIdentifier -> {
						final DDOrderId actualDDOrderId = DDOrderId.ofRepoId(ddOrderLineRecord.getDD_Order_ID());
						validateDDOrderId(ddOrderIdentifier, actualDDOrderId);
					});
		});
	}

	private void validateDDOrderId(@NonNull final StepDefDataIdentifier expectedDDOrderIdentifier, @Nullable final DDOrderId actualDDOrderId)
	{
		final String actualHuIdentifier = actualDDOrderId != null
				? ddOrderTable.getFirstIdentifierById(actualDDOrderId).map(StepDefDataIdentifier::getAsString).orElse("?NEW?")
				: null;

		final String description = "expectedDDOrderIdentifier=" + expectedDDOrderIdentifier + ", actualDDOrderId=" + actualDDOrderId + ", actualHuIdentifier=" + actualHuIdentifier;

		if (expectedDDOrderIdentifier.isNullPlaceholder())
		{
			assertThat(actualDDOrderId).as(description).isNull();
		}
		else
		{
			assertThat(actualDDOrderId).as(description).isNotNull();
			final DDOrderId expectedDDOrderId = ddOrderTable.getIdOptional(expectedDDOrderIdentifier).orElse(null);
			if (expectedDDOrderId == null)
			{
				ddOrderTable.put(expectedDDOrderIdentifier, ddOrderService.getById(actualDDOrderId));
			}
			else
			{
				assertThat(actualDDOrderId).as(description).isEqualTo(expectedDDOrderId);
			}
		}
	}

	private IQuery<I_DD_OrderLine> toDDOrderLineQuery(
			@NonNull final OrderLineId orderLineId,
			@NonNull final Set<DDOrderLineId> excludeDDOrderLineIds)
	{
		final IQueryBuilder<I_DD_OrderLine> queryBuilder = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.orderBy(I_DD_OrderLine.COLUMNNAME_DD_Order_ID)
				.orderBy(I_DD_OrderLine.COLUMNNAME_Line)
				.orderBy(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
				.addEqualsFilter(I_DD_OrderLine.COLUMN_C_OrderLineSO_ID, orderLineId);

		if (!excludeDDOrderLineIds.isEmpty())
		{
			queryBuilder.addNotInArrayFilter(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID, excludeDDOrderLineIds);
		}

		return queryBuilder.create();
	}

	private BooleanWithReason checkDDOrderLineMatching(@NonNull final I_DD_OrderLine actual, @NonNull final DataTableRow expected)
	{
		final BigDecimal qtyEntered = expected.getAsOptionalBigDecimal(I_DD_OrderLine.COLUMNNAME_QtyEntered).orElse(null);
		if (qtyEntered != null && qtyEntered.compareTo(actual.getQtyEntered()) != 0)
		{
			return BooleanWithReason.falseBecause("qtyEntered not matching, expected " + qtyEntered + " but got " + actual.getQtyEntered());
		}

		final StepDefDataIdentifier productIdentifier = expected.getAsOptionalIdentifier(I_DD_OrderLine.COLUMNNAME_M_Product_ID).orElse(null);
		if (productIdentifier != null)
		{
			final ProductId productIdExpected = productTable.getId(productIdentifier);
			final ProductId productIdActual = ProductId.ofRepoId(actual.getM_Product_ID());
			if (!ProductId.equals(productIdExpected, productIdActual))
			{
				return BooleanWithReason.falseBecause("M_Product_ID not matching, expected " + productIdExpected + " but got " + productIdActual);
			}
		}

		final StepDefDataIdentifier fromWarehouseIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_M_Warehouse_From_ID).orElse(null);
		if (fromWarehouseIdentifier != null)
		{
			final WarehouseId fromWarehouseIdExpected = warehouseData.getId(fromWarehouseIdentifier);
			final WarehouseId fromWarehouseIdActual = WarehouseId.ofRepoId(actual.getM_Warehouse_ID());
			if (!WarehouseId.equals(fromWarehouseIdExpected, fromWarehouseIdActual))
			{
				return BooleanWithReason.falseBecause("M_Warehouse_From_ID not matching, expected " + fromWarehouseIdExpected + " but got " + fromWarehouseIdActual);
			}
		}

		final StepDefDataIdentifier toWarehouseIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_M_Warehouse_To_ID).orElse(null);
		if (toWarehouseIdentifier != null)
		{
			final WarehouseId toWarehouseIdExpected = warehouseData.getId(toWarehouseIdentifier);
			final WarehouseId toWarehouseIdActual = WarehouseId.ofRepoId(actual.getM_WarehouseTo_ID());
			if (!WarehouseId.equals(toWarehouseIdExpected, toWarehouseIdActual))
			{
				return BooleanWithReason.falseBecause("M_Warehouse_To_ID not matching, expected " + toWarehouseIdExpected + " but got " + toWarehouseIdActual);
			}
		}

		return BooleanWithReason.TRUE;
	}

	private void logCurrentContextExpectedNoRecords(@NonNull final OrderLineId orderLineId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Validating no records found for orderLineID :").append("\n")
				.append(I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(orderLineId).append("\n");

		message.append("DD_OrderLine records:").append("\n");

		queryByOrderLineId(orderLineId)
				.stream(I_DD_OrderLine.class)
				.forEach(ddOrderLine -> message
						.append(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID).append(" : ").append(ddOrderLine.getDD_OrderLine_ID()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while validating no DD_OrderLine found for orderLineId: {}, see found records: \n{}", orderLineId, message);
	}

	@NonNull
	private IQuery<I_DD_OrderLine> queryByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMN_C_OrderLineSO_ID, orderLineId)
				.create();
	}

	@NonNull
	private OrderLineId getOrderLineIdByIdentifier(@NonNull final String identifierStr)
	{
		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString(identifierStr);

		final OrderLineId orderLineId = identifierIdsTable.getOptional(identifier, OrderLineId.class)
				.orElseGet(() -> orderLineTable.getId(identifier));
		if (orderLineId == null)
		{
			throw new AdempiereException("No orderLineId found for " + identifierStr);
		}

		return orderLineId;
	}
}
