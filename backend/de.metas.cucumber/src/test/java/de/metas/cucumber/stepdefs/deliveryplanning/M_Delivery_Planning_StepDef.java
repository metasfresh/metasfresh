/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.deliveryplanning;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Loads, deletes, updates and validates {@code M_Delivery_Planning} records, and drives the
 * close/reopen/cancel document actions.
 * <p>
 * Generating / regenerating the delivery instruction ({@code M_ShipperTransportation}) for a
 * planning is handled by {@link M_Delivery_Instruction_StepDef}, which shares the same
 * {@link M_Delivery_Planning_StepDefData} instance (injected by PicoContainer).
 */
public class M_Delivery_Planning_StepDef
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Shipper_StepDefData shipperTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_ShipperTransportation_StepDefData deliveryInstructionTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_Delivery_Planning_StepDef(
			@NonNull final M_Delivery_Planning_StepDefData deliveryPlanningTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Shipper_StepDefData shipperTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_ShipperTransportation_StepDefData deliveryInstructionTable)
	{
		this.deliveryPlanningTable = deliveryPlanningTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.productTable = productTable;
		this.bpartnerTable = bpartnerTable;
		this.shipperTable = shipperTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.warehouseTable = warehouseTable;
		this.deliveryInstructionTable = deliveryInstructionTable;
	}

	/**
	 * Waits for the async {@code M_Delivery_Planning} generation for the given order line, then loads the created
	 * records under the given aliases (in {@code M_Delivery_Planning_ID} order).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_OrderLine_ID</b> — (required, identifier-ref) the order line the plannings were generated for<br>
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) comma-separated list of aliases to store the
	 *   loaded records under, one per expected record, in ascending {@code M_Delivery_Planning_ID} order<br>
	 * @cucumber.depends StepDefData: C_OrderLine_StepDefData, M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 30s, load created M_Delivery_Planning:
	 *   | M_Delivery_Planning_ID                | C_OrderLine_ID |
	 *   | deliveryPlanning_1,deliveryPlanning_2 | orderLine      |
	 * </pre>
	 */
	@And("^after not more than (.*)s, load created M_Delivery_Planning:$")
	public void load_created_M_Delivery_Planning(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_C_OrderLine orderLine = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID).lookupNotNullIn(orderLineTable);

			final List<StepDefDataIdentifier> identifiers = row.getAsIdentifierList(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID);
			final int numberOfRecordsToLoad = identifiers.size();

			final IQueryBuilder<I_M_Delivery_Planning> queryBuilder = queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
					.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());

			final Supplier<Boolean> isDeliveryPlanningFound = () ->
			{
				final int deliveryPlanningRecords = queryBuilder.create().count();

				if (deliveryPlanningRecords > numberOfRecordsToLoad)
				{
					throw new AdempiereException("Found more M_Delivery_Planning records than expected for C_OrderLine_ID=" + orderLine.getC_OrderLine_ID())
							.appendParametersToMessage()
							.setParameter("ExpectedCount", numberOfRecordsToLoad)
							.setParameter("ActualCount", deliveryPlanningRecords);
				}

				return deliveryPlanningRecords == numberOfRecordsToLoad;
			};

			StepDefUtil.tryAndWait(timeoutSec, 1000, isDeliveryPlanningFound);

			final List<I_M_Delivery_Planning> deliveryPlannings = queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
					.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
					.orderBy(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID)
					.create()
					.list();

			for (int rowIndex = 0; rowIndex < numberOfRecordsToLoad; rowIndex++)
			{
				identifiers.get(rowIndex).putOrReplace(deliveryPlanningTable, deliveryPlannings.get(rowIndex));
			}
		});
	}

	@And("^generate (.*) additional M_Delivery_Planning records for: (.*)$")
	public void generate_additional_M_Delivery_Planning(final int noAdditionalRecords, @NonNull final String deliveryPlanningIdentifier)
	{
		final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningTable.get(deliveryPlanningIdentifier);
		assertThat(deliveryPlanning).isNotNull();

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID());
		deliveryPlanningService.createAdditionalDeliveryPlannings(deliveryPlanningId, noAdditionalRecords);
	}

	/**
	 * Deletes the given delivery planning, via {@link DeliveryPlanningService#validateDeletion(I_M_Delivery_Planning)}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning to delete<br>
	 *   <b>ErrorCode</b> — (optional) when set, the deletion is expected to fail with this {@code AdempiereException} error code
	 *   instead of succeeding<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And delete M_Delivery_Planning:
	 *   | M_Delivery_Planning_ID | ErrorCode                                                       |
	 *   | deliveryPlanning_1     | de.metas.deliveryplanning.M_Delivery_Planning_AlreadyReferenced |
	 * </pre>
	 */
	@When("delete M_Delivery_Planning:")
	public void delete_M_Delivery_Planning(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);

			final String errorCode = row.getAsOptionalString("ErrorCode").filter(Check::isNotBlank).orElse(null);
			try
			{
				deliveryPlanningService.validateDeletion(deliveryPlanning);
				if (Check.isNotBlank(errorCode))
				{
					throw new RuntimeException("Was expecting operation to fail!");
				}
				InterfaceWrapperHelper.delete(deliveryPlanning);

			}
			catch (final AdempiereException e)
			{
				assertThat(e.getErrorCode()).as("ErrorCode of %s", e).isEqualTo(errorCode);
			}
		});
	}

	/**
	 * Validates a previously created/loaded {@code M_Delivery_Planning} against the given expectations.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning to validate<br>
	 *   <b>QtyOrdered</b> — (required) expected {@code QtyOrdered}<br>
	 *   <b>QtyTotalOpen</b> — (required) expected {@code QtyTotalOpen}<br>
	 *   <b>TransportDirection</b> — (required) expected {@code TransportDirection}<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) expected product<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected business partner<br>
	 *   <b>C_Order_ID</b> — (optional, identifier-ref) expected order<br>
	 *   <b>C_OrderLine_ID</b> — (optional, identifier-ref) expected order line<br>
	 *   <b>M_Shipper_ID</b> — (optional, identifier-ref) expected shipper<br>
	 *   <b>C_BPartner_Location_ID</b> — (optional, identifier-ref) expected business partner location<br>
	 *   <b>M_Warehouse_ID</b> — (optional, identifier-ref) expected warehouse<br>
	 *   <b>ETA</b> — (optional) expected {@code ETA}<br>
	 *   <b>ETD</b> — (optional) expected {@code ETD}<br>
	 *   <b>PlannedLoadedQuantity</b> — (optional) expected {@code PlannedLoadedQuantity}<br>
	 *   <b>IsClosed</b> — (optional) expected {@code IsClosed}<br>
	 *   <b>Processed</b> — (optional) expected {@code Processed}<br>
	 *   <b>OrderStatus</b> — (optional) expected {@code OrderStatus}<br>
	 *   <b>M_ShipperTransportation_ID</b> — (optional, identifier-ref, null-allowed) expected linked delivery
	 *   instruction; a literal {@code null}/{@code -} asserts that none is linked (i.e. {@code M_ShipperTransportation_ID=0})<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_Product_StepDefData, C_BPartner_StepDefData,
	 * C_Order_StepDefData, C_OrderLine_StepDefData, M_Shipper_StepDefData, C_BPartner_Location_StepDefData,
	 * M_Warehouse_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate M_Delivery_Planning:
	 *   | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection |
	 *   | deliveryPlanning_1     | 5          | 5            | Outgoing           |
	 * </pre>
	 */
	@And("validate M_Delivery_Planning:")
	public void validate_M_Delivery_Planning(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);
			InterfaceWrapperHelper.refresh(deliveryPlanning);

			final SoftAssertions softly = new SoftAssertions();

			final BigDecimal qtyOrdered = row.getAsBigDecimal(I_M_Delivery_Planning.COLUMNNAME_QtyOrdered);
			softly.assertThat(deliveryPlanning.getQtyOrdered()).as(I_M_Delivery_Planning.COLUMNNAME_QtyOrdered).isEqualTo(qtyOrdered);

			final BigDecimal qtyTotalOpen = row.getAsBigDecimal(I_M_Delivery_Planning.COLUMNNAME_QtyTotalOpen);
			softly.assertThat(deliveryPlanning.getQtyTotalOpen()).as(I_M_Delivery_Planning.COLUMNNAME_QtyTotalOpen).isEqualTo(qtyTotalOpen);

			final String type = row.getAsString(I_M_Delivery_Planning.COLUMNNAME_TransportDirection);
			softly.assertThat(deliveryPlanning.getTransportDirection()).as(I_M_Delivery_Planning.COLUMNNAME_TransportDirection).isEqualTo(type);

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Product_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_M_Product product = id.lookupNotNullIn(productTable);
						softly.assertThat(deliveryPlanning.getM_Product_ID()).as(I_M_Delivery_Planning.COLUMNNAME_M_Product_ID).isEqualTo(product.getM_Product_ID());
					});

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_C_BPartner bPartner = id.lookupNotNullIn(bpartnerTable);
						softly.assertThat(deliveryPlanning.getC_BPartner_ID()).as(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_ID).isEqualTo(bPartner.getC_BPartner_ID());
					});

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_C_Order_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_C_Order order = id.lookupNotNullIn(orderTable);
						softly.assertThat(deliveryPlanning.getC_Order_ID()).as(I_M_Delivery_Planning.COLUMNNAME_C_Order_ID).isEqualTo(order.getC_Order_ID());
					});

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_C_OrderLine orderLine = id.lookupNotNullIn(orderLineTable);
						softly.assertThat(deliveryPlanning.getC_OrderLine_ID()).as(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID).isEqualTo(orderLine.getC_OrderLine_ID());
					});

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Shipper_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_M_Shipper shipper = id.lookupNotNullIn(shipperTable);
						softly.assertThat(deliveryPlanning.getM_Shipper_ID()).as(I_M_Delivery_Planning.COLUMNNAME_M_Shipper_ID).isEqualTo(shipper.getM_Shipper_ID());
					});

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_Location_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_C_BPartner_Location bPartnerLocation = id.lookupNotNullIn(bPartnerLocationTable);
						softly.assertThat(deliveryPlanning.getC_BPartner_Location_ID()).as(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_Location_ID).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());
					});

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Warehouse_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_M_Warehouse warehouse = id.lookupNotNullIn(warehouseTable);
						softly.assertThat(deliveryPlanning.getM_Warehouse_ID()).as(I_M_Delivery_Planning.COLUMNNAME_M_Warehouse_ID).isEqualTo(warehouse.getM_Warehouse_ID());
					});

			row.getAsOptionalLocalDateTimestamp(I_M_Delivery_Planning.COLUMNNAME_ETA)
					.ifPresent(eta -> softly.assertThat(deliveryPlanning.getETA()).as(I_M_Delivery_Planning.COLUMNNAME_ETA).isEqualTo(eta));

			row.getAsOptionalLocalDateTimestamp(I_M_Delivery_Planning.COLUMNNAME_ETD)
					.ifPresent(etd -> softly.assertThat(deliveryPlanning.getETD()).as(I_M_Delivery_Planning.COLUMNNAME_ETD).isEqualTo(etd));

			row.getAsOptionalBigDecimal(I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity)
					.ifPresent(plannedLoadedQty -> softly.assertThat(deliveryPlanning.getPlannedLoadedQuantity()).as(I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity).isEqualTo(plannedLoadedQty));

			row.getAsOptionalBoolean(I_M_Delivery_Planning.COLUMNNAME_IsClosed)
					.ifPresent(isClosed -> softly.assertThat(deliveryPlanning.isClosed()).as(I_M_Delivery_Planning.COLUMNNAME_IsClosed).isEqualTo(isClosed));

			row.getAsOptionalBoolean(I_M_Delivery_Planning.COLUMNNAME_Processed)
					.ifPresent(isProcessed -> softly.assertThat(deliveryPlanning.isProcessed()).as(I_M_Delivery_Planning.COLUMNNAME_Processed).isEqualTo(isProcessed));

			row.getAsOptionalString(I_M_Delivery_Planning.COLUMNNAME_OrderStatus)
					.filter(Check::isNotBlank)
					.ifPresent(orderStatus -> softly.assertThat(deliveryPlanning.getOrderStatus()).as(I_M_Delivery_Planning.COLUMNNAME_OrderStatus).isEqualTo(orderStatus));

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID)
					.ifPresent(id -> {
						if (id.isNullPlaceholder())
						{
							softly.assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).as(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID).isEqualTo(0);
						}
						else
						{
							final I_M_ShipperTransportation deliveryInstruction = id.lookupNotNullIn(deliveryInstructionTable);
							softly.assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).as(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID).isEqualTo(deliveryInstruction.getM_ShipperTransportation_ID());
						}
					});

			softly.assertAll();
		});
	}

	@And("^M_Delivery_Planning identified by (.*) is (closed|opened|canceled)$")
	public void delivery_Planning_action(@NonNull final String deliveryPlanningIdentifier, @NonNull final String action)
	{
		final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningTable.get(deliveryPlanningIdentifier);
		assertThat(deliveryPlanning).isNotNull();

		switch (StepDefDocAction.valueOf(action))
		{
			case closed:
				deliveryPlanningService.closeSelectedDeliveryPlannings(getQueryFilterFor(deliveryPlanningIdentifier));
				break;
			case opened:
				deliveryPlanningService.reOpenSelectedDeliveryPlannings(getQueryFilterFor(deliveryPlanningIdentifier));
				break;
			case canceled:
				deliveryPlanningService.cancelDelivery(getQueryFilterFor(deliveryPlanningIdentifier));
				break;
			default:
				throw new AdempiereException("Unsupported action for M_Delivery_Planning!")
						.appendParametersToMessage()
						.setParameter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanning.getM_Delivery_Planning_ID());
		}
	}

	/**
	 * Updates the given delivery planning.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning to update<br>
	 *   <b>M_Shipper_ID</b> — (optional, identifier-ref) new shipper to assign<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_Shipper_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update M_Delivery_Planning:
	 *   | M_Delivery_Planning_ID | M_Shipper_ID |
	 *   | deliveryPlanning_1     | shipper_DHL  |
	 * </pre>
	 */
	@And("update M_Delivery_Planning:")
	public void update_M_Delivery_Planning(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);

			row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Shipper_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_M_Shipper shipper = id.lookupNotNullIn(shipperTable);
						deliveryPlanning.setM_Shipper_ID(shipper.getM_Shipper_ID());
					});

			saveRecord(deliveryPlanning);
		});
	}

	@NonNull
	private IQueryFilter<I_M_Delivery_Planning> getQueryFilterFor(@NonNull final String deliveryPlanningIdentifier)
	{
		final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningTable.get(deliveryPlanningIdentifier);
		assertThat(deliveryPlanning).isNotNull();

		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanning.getM_Delivery_Planning_ID());
	}
}
