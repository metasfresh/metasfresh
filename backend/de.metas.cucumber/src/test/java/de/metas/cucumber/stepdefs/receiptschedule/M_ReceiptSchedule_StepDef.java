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

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.order.OrderLineId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_InOut;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
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
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID;
import static org.compiere.util.Env.getCtx;

public class M_ReceiptSchedule_StepDef
{
	private static final String EMPTIES_RECEIVE = "EMPTIES RECEIVE";
	private static final String EMPTIES_RETURN = "EMPTIES RETURN";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IReceiptScheduleProducerFactory receiptScheduleProducerFactory = Services.get(IReceiptScheduleProducerFactory.class);
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);

	private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Product_StepDefData productTable;
	private final M_InOut_StepDefData inOutTable;

	private final C_Flatrate_Term_StepDefData flatrateTermTable;

	public M_ReceiptSchedule_StepDef(
			@NonNull final M_ReceiptSchedule_StepDefData receiptScheduleTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_InOut_StepDefData inOutTable,
			@NonNull final C_Flatrate_Term_StepDefData flatrateTermTable)
	{
		this.receiptScheduleTable = receiptScheduleTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.warehouseTable = warehouseTable;
		this.productTable = productTable;
		this.inOutTable = inOutTable;
		this.flatrateTermTable = flatrateTermTable;
	}

	@And("^after not more than (.*)s, M_ReceiptSchedule are found:$")
	public void there_are_receiptSchedule(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadReceiptSchedule(tableRow));

			final SoftAssertions softly = new SoftAssertions();

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
			softly.assertThat(bPartnerLocationID).isNotNull();

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer productID = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));
			softly.assertThat(productID).isNotNull();

			final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyOrdered);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);

			softly.assertThat(receiptSchedule.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			softly.assertThat(receiptSchedule.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			softly.assertThat(receiptSchedule.getC_BPartner_ID()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
			softly.assertThat(receiptSchedule.getC_BPartner_Location_ID()).isEqualTo(bPartnerLocationID);
			softly.assertThat(receiptSchedule.getM_Product_ID()).isEqualTo(productID);
			softly.assertThat(receiptSchedule.getQtyOrdered()).isEqualTo(qtyOrdered);
			softly.assertThat(receiptSchedule.getM_Warehouse_ID()).isEqualTo(warehouse.getM_Warehouse_ID());

			final BigDecimal qtyMoved = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_ReceiptSchedule.COLUMNNAME_QtyMoved);
			if (qtyMoved != null)
			{
				softly.assertThat(receiptSchedule.getQtyMoved()).isEqualTo(qtyMoved);
			}

			final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_M_ReceiptSchedule.COLUMNNAME_Processed, false);
			softly.assertThat(receiptSchedule.isProcessed()).isEqualTo(processed);

			final BigDecimal qtyOrderedTU = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_ReceiptSchedule.COLUMNNAME_QtyOrderedTU);
			if (qtyOrderedTU != null)
			{
				final de.metas.handlingunits.model.I_C_OrderLine orderLine1 = InterfaceWrapperHelper.load(receiptSchedule.getC_OrderLine_ID(), de.metas.handlingunits.model.I_C_OrderLine.class);
				softly.assertThat(orderLine1.getQtyEnteredTU()).isEqualTo(qtyOrderedTU);
			}

			final String flatrateTermIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(flatrateTermIdentifier))
			{
				final I_C_Flatrate_Term flatrateTermRecord = flatrateTermTable.get(flatrateTermIdentifier);
				softly.assertThat(receiptSchedule.getC_Flatrate_Term_ID()).as(I_M_ReceiptSchedule.COLUMNNAME_C_Flatrate_Term_ID).isEqualTo(flatrateTermRecord.getC_Flatrate_Term_ID());
			}

			softly.assertAll();

			receiptScheduleTable.putOrReplace(receiptScheduleIdentifier, receiptSchedule);
		}
	}

	@And("^there is no M_ReceiptSchedule for C_OrderLine (.*)$")
	public void validate_no_M_ReceiptSchedule_created(@NonNull final String purchaseOrderLineIdentifier)
	{
		final I_C_OrderLine purchaseOrderLine = orderLineTable.get(purchaseOrderLineIdentifier);
		final OrderLineId purchaseOrderLineId = OrderLineId.ofRepoId(purchaseOrderLine.getC_OrderLine_ID());

		validateNoReceiptScheduleCreatedForPurchaseOrderLine(purchaseOrderLineId);

		final IReceiptScheduleProducer producer = receiptScheduleProducerFactory.createProducer(I_C_OrderLine.Table_Name, false);

		final List<I_M_ReceiptSchedule> purchaseOrderReceiptSchedules = producer.createOrUpdateReceiptSchedules(purchaseOrderLine, Collections.emptyList());
		assertThat(purchaseOrderReceiptSchedules).isNull();
	}

	@And("^the M_ReceiptSchedule identified by (.*) is (closed|reactivated)$")
	public void M_ReceiptSchedule_action(@NonNull final String receiptScheduleIdentifier, @NonNull final String action)
	{
		final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case closed:
				receiptScheduleBL.close(receiptSchedule);
				break;
			case reactivated:
				receiptScheduleBL.reopen(receiptSchedule);
				break;
			default:
				throw new AdempiereException("Unhandled M_ReceiptSchedule action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	@And("^trigger (EMPTIES RECEIVE|EMPTIES RETURN) process:$")
	public void trigger_empties_process(@NonNull final String type, @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> table = dataTable.asMaps();
		for (final Map<String, String> row : table)
		{
			if (type.equals(EMPTIES_RECEIVE))
			{
				createInOutEmpties(X_M_InOut.MOVEMENTTYPE_CustomerReturns, row);
			}
			else if (type.equals(EMPTIES_RETURN))
			{
				createInOutEmpties(X_M_InOut.MOVEMENTTYPE_VendorReturns, row);
			}
			else
			{
				throw new RuntimeException("ReturnMovementType " + type + " not supported!");
			}
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

	private void validateNoReceiptScheduleCreatedForPurchaseOrderLine(@NonNull final OrderLineId orderLineId)
	{
		final I_M_ReceiptSchedule schedule = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addEqualsFilter(COLUMNNAME_C_OrderLine_ID, orderLineId.getRepoId())
				.create()
				.firstOnlyOrNull(I_M_ReceiptSchedule.class);

		Assertions.assertThat(schedule).isNull();
	}

	private void createInOutEmpties(@NonNull final String movementType, @NonNull final Map<String, String> row)
	{
		final String receiptScheduleIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(receiptScheduleIdentifier))
		{
			createDraftEmptiesForReceiptSchedule(movementType, row);
			return;
		}

		createDraftEmpties(movementType, row);
	}

	private void createDraftEmptiesForReceiptSchedule(@NonNull final String movementType, @NonNull final Map<String, String> row)
	{
		final String receiptScheduleIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		assertThat(receiptScheduleIdentifier).isNotNull();
		final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
		assertThat(receiptSchedule).isNotNull();

		final I_M_InOut emptiesInOut = huEmptiesService.createDraftEmptiesInOutFromReceiptSchedule(receiptSchedule, movementType);

		final String inOutIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		inOutTable.putOrReplace(inOutIdentifier, emptiesInOut);
	}

	private void createDraftEmpties(@NonNull final String movementType, @NonNull final Map<String, String> row)
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class);

		huEmptiesService.newReturnsInOutProducer(getCtx())
				.setMovementType(movementType)
				.setMovementDate(de.metas.common.util.time.SystemTime.asDayTimestamp())
				.fillReturnsInOutHeader(inOut);

		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bpartnerIdentifier);
		Assertions.assertThat(bPartner).isNotNull();

		inOut.setC_BPartner_ID(bPartner.getC_BPartner_ID());

		final String bpartnerLocIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLoc = bPartnerLocationTable.get(bpartnerLocIdentifier);
		Assertions.assertThat(bPartnerLoc).isNotNull();

		inOut.setC_BPartner_Location_ID(bPartnerLoc.getC_BPartner_Location_ID());

		final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);
		Assertions.assertThat(warehouse).isNotNull();

		inOut.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());

		saveRecord(inOut);

		final String inOutIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		inOutTable.putOrReplace(inOutIdentifier, inOut);
	}
}
