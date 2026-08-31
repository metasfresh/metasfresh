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

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
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
import de.metas.cucumber.stepdefs.InterfaceWrapperHelperUtils;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Loads, deletes, updates and validates {@code M_Delivery_Planning} records, and drives the
 * close/reopen/cancel document actions.
 */
@RequiredArgsConstructor
public class M_Delivery_Planning_StepDef
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;
	@NonNull private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	@NonNull private final DeliveryPlanningRejectionHelper rejectionHelper;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
	 * Deletes the given delivery planning via {@link InterfaceWrapperHelper#delete(Object)}, so the whole
	 * registered interceptor chain runs as it does in production. Deletes as a MANUAL USER ACTION by default,
	 * because only a UI action triggers the "at least one planning per order line" rule.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning to delete<br>
	 *   <b>ErrorCode</b> — (optional) when set, the deletion is expected to fail with this {@code AdempiereException} error code
	 *   instead of succeeding<br>
	 *   <b>IsUIAction</b> (or <b>OPT.IsUIAction</b>) — (optional, default {@code true}) delete as a manual user action, as the WebUI does;
	 *   set {@code false} for the programmatic path a receipt/shipment-schedule delete takes<br>
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
			final boolean uiAction = row.getAsOptionalBoolean("IsUIAction").orElseTrue();
			if (uiAction)
			{
				InterfaceWrapperHelperUtils.set_ManualUserAction(deliveryPlanning);
			}

			try
			{
				InterfaceWrapperHelper.delete(deliveryPlanning);
				if (Check.isNotBlank(errorCode))
				{
					throw new RuntimeException("Was expecting operation to fail!");
				}
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
	 *   <b>OrderStatus</b> — (optional, null-allowed) expected {@code OrderStatus}; {@code null} asserts the planning
	 *   carries none<br>
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

			row.getAsOptionalLocalDateTimestamp(I_M_Delivery_Planning.COLUMNNAME_ATA)
					.ifPresent(ata -> softly.assertThat(deliveryPlanning.getATA()).as(I_M_Delivery_Planning.COLUMNNAME_ATA).isEqualTo(ata));

			row.getAsOptionalLocalDateTimestamp(I_M_Delivery_Planning.COLUMNNAME_ATD)
					.ifPresent(atd -> softly.assertThat(deliveryPlanning.getATD()).as(I_M_Delivery_Planning.COLUMNNAME_ATD).isEqualTo(atd));

			row.getAsOptionalBigDecimal(I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity)
					.ifPresent(plannedLoadedQty -> softly.assertThat(deliveryPlanning.getPlannedLoadedQuantity()).as(I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity).isEqualTo(plannedLoadedQty));

			row.getAsOptionalBoolean(I_M_Delivery_Planning.COLUMNNAME_IsClosed)
					.ifPresent(isClosed -> softly.assertThat(deliveryPlanning.isClosed()).as(I_M_Delivery_Planning.COLUMNNAME_IsClosed).isEqualTo(isClosed));

			row.getAsOptionalBoolean(I_M_Delivery_Planning.COLUMNNAME_Processed)
					.ifPresent(isProcessed -> softly.assertThat(deliveryPlanning.isProcessed()).as(I_M_Delivery_Planning.COLUMNNAME_Processed).isEqualTo(isProcessed));

			row.getAsOptionalString(I_M_Delivery_Planning.COLUMNNAME_OrderStatus)
					.filter(Check::isNotBlank)
					.ifPresent(orderStatus -> softly.assertThat(deliveryPlanning.getOrderStatus())
							.as(I_M_Delivery_Planning.COLUMNNAME_OrderStatus)
							.isEqualTo(DataTableUtil.nullToken2Null(orderStatus)));

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

	/**
	 * Drives the close / re-open / cancel processes over the given selection, which - like the WebUI grid they are
	 * launched from - may name SEVERAL plannings, comma-separated. The selection is evaluated per row, so a
	 * rejected or skipped planning does not decide the fate of the others.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When M_Delivery_Planning identified by deliveryPlanning_1 is closed
	 * And M_Delivery_Planning identified by deliveryPlanning_1,deliveryPlanning_2 is canceled
	 * </pre>
	 */
	@And("^M_Delivery_Planning identified by (.*) is (closed|opened|canceled)$")
	public void delivery_Planning_action(@NonNull final String deliveryPlanningIdentifiers, @NonNull final String action)
	{
		final IQueryFilter<I_M_Delivery_Planning> selectionFilter = getQueryFilterFor(deliveryPlanningIdentifiers);

		switch (StepDefDocAction.valueOf(action))
		{
			case closed:
				deliveryPlanningService.closeSelectedDeliveryPlannings(selectionFilter);
				break;
			case opened:
				deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionFilter);
				break;
			case canceled:
				deliveryPlanningService.cancelDelivery(selectionFilter);
				break;
			default:
				throw new AdempiereException("Unsupported action for M_Delivery_Planning!")
						.appendParametersToMessage()
						.setParameter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIdentifiers);
		}
	}

	/**
	 * Presses {@code Close} / {@code Re-Open} on the delivery planning expecting it to be REFUSED, and asserts which
	 * rejection came back - the same {@link DeliveryPlanningService} entry points the
	 * {@code M_Delivery_Planning_Close} / {@code M_Delivery_Planning_ReOpen} processes drive.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ErrorAdMessage</b> — (optional) the {@code AD_Message} the action is expected to be rejected with<br>
	 *   <b>ErrorMessage</b> — (optional) the raw rejection text, {@code @token@}s included<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When closing M_Delivery_Planning identified by deliveryPlanning is refused:
	 *   | ErrorMessage  |
	 *   | @Closed@=@Y@  |
	 * </pre>
	 */
	@When("^(closing|reopening) M_Delivery_Planning identified by (.*) is refused:$")
	public void delivery_Planning_action_refused(
			@NonNull final String action,
			@NonNull final String deliveryPlanningIdentifier,
			@NonNull final DataTable dataTable)
	{
		final IQueryFilter<I_M_Delivery_Planning> selectionFilter = getQueryFilterFor(deliveryPlanningIdentifier);

		final Runnable deliveryPlanningAction = "closing".equals(action)
				? () -> deliveryPlanningService.closeSelectedDeliveryPlannings(selectionFilter)
				: () -> deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionFilter);

		rejectionHelper.runExpectingRejectionIfAny(DataTableRows.of(dataTable).singleRow(), ImmutableSet.of(), deliveryPlanningAction);
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

	/**
	 * Asserts that each of the given plannings carries its OWN release number, stamped from the delivery
	 * instruction it now sits on. The value is built from the instruction's {@code DocumentNo}, the planning's id
	 * and the instruction's creation MINUTE, so it is asserted by relationship - present, naming that instruction,
	 * different for every planning - rather than by literal.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning whose {@code ReleaseNo} is asserted<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstruction:
	 *   | M_Delivery_Planning_ID |
	 *   | deliveryPlanning_1     |
	 *   | deliveryPlanning_2     |
	 * </pre>
	 */
	@And("^each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation (.*):$")
	public void validate_ReleaseNo_stamped_from(
			@NonNull final String deliveryInstructionIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
		assertThat(deliveryInstruction).isNotNull();

		final Set<String> releaseNos = new LinkedHashSet<>();

		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);
			InterfaceWrapperHelper.refresh(deliveryPlanning);

			final String releaseNo = deliveryPlanning.getReleaseNo();
			assertThat(releaseNo)
					.as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, deliveryPlanning.getM_Delivery_Planning_ID())
					.isNotBlank()
					.startsWith(deliveryInstruction.getDocumentNo() + "-");

			assertThat(releaseNos.add(releaseNo))
					.as("%s %s of M_Delivery_Planning %s is not shared with another planning",
							I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, releaseNo, deliveryPlanning.getM_Delivery_Planning_ID())
					.isTrue();
		});
	}

	/**
	 * Asserts that the given plannings carry no release number - what a planning that is on no delivery
	 * instruction looks like, and therefore what makes it plannable again.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning whose {@code ReleaseNo} is asserted<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the following M_Delivery_Planning have no ReleaseNo:
	 *   | M_Delivery_Planning_ID |
	 *   | deliveryPlanning_2     |
	 * </pre>
	 */
	@And("the following M_Delivery_Planning have no ReleaseNo:")
	public void validate_no_ReleaseNo(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);
			InterfaceWrapperHelper.refresh(deliveryPlanning);

			assertThat(deliveryPlanning.getReleaseNo())
					.as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, deliveryPlanning.getM_Delivery_Planning_ID())
					.isNullOrEmpty();
		});
	}

	/**
	 * Asserts that each of the given plannings carries the delivery instruction's OWN date fields: the instruction
	 * owns the dates while the planning is allocated to it, one-way, on the initial stamp and on every later change
	 * alike. Asserted as a relationship, not as literals - the instruction's departure date is derived from the
	 * seeding order's {@code PreparationDate}, so a literal would assert the derivation instead of the sync.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning whose dates are asserted<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the following M_Delivery_Planning carry the date fields of M_ShipperTransportation deliveryInstruction:
	 *   | M_Delivery_Planning_ID |
	 *   | deliveryPlanning_1     |
	 *   | deliveryPlanning_2     |
	 * </pre>
	 */
	@And("^the following M_Delivery_Planning carry the date fields of M_ShipperTransportation (.*):$")
	public void validate_dates_taken_from_instruction(
			@NonNull final String deliveryInstructionIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
		assertThat(deliveryInstruction).isNotNull();
		InterfaceWrapperHelper.refresh(deliveryInstruction);

		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);
			InterfaceWrapperHelper.refresh(deliveryPlanning);

			final SoftAssertions softly = new SoftAssertions();

			softly.assertThat(deliveryPlanning.getETD()).as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_ETD, deliveryPlanning.getM_Delivery_Planning_ID())
					.isEqualTo(deliveryInstruction.getETD());
			softly.assertThat(deliveryPlanning.getETA()).as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_ETA, deliveryPlanning.getM_Delivery_Planning_ID())
					.isEqualTo(deliveryInstruction.getETA());
			softly.assertThat(deliveryPlanning.getATD()).as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_ATD, deliveryPlanning.getM_Delivery_Planning_ID())
					.isEqualTo(deliveryInstruction.getATD());
			softly.assertThat(deliveryPlanning.getATA()).as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_ATA, deliveryPlanning.getM_Delivery_Planning_ID())
					.isEqualTo(deliveryInstruction.getATA());
			softly.assertThat(deliveryPlanning.getLoadingTime()).as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_LoadingTime, deliveryPlanning.getM_Delivery_Planning_ID())
					.isEqualTo(deliveryInstruction.getLoadingTime());
			softly.assertThat(deliveryPlanning.getDeliveryTime()).as("%s of M_Delivery_Planning %s", I_M_Delivery_Planning.COLUMNNAME_DeliveryTime, deliveryPlanning.getM_Delivery_Planning_ID())
					.isEqualTo(deliveryInstruction.getDeliveryTime());

			softly.assertAll();
		});
	}

	/**
	 * The grid selection close / re-open / cancel receive: the plannings named in the given comma-separated
	 * identifier list, because all three are multi-row selection processes.
	 */
	@NonNull
	private IQueryFilter<I_M_Delivery_Planning> getQueryFilterFor(@NonNull final String deliveryPlanningIdentifiers)
	{
		final ImmutableList<Integer> deliveryPlanningIds = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(deliveryPlanningIdentifiers)
				.stream()
				.map(deliveryPlanningTable::get)
				.map(I_M_Delivery_Planning::getM_Delivery_Planning_ID)
				.collect(ImmutableList.toImmutableList());
		assertThat(deliveryPlanningIds).as("M_Delivery_Planning identified by %s", deliveryPlanningIdentifiers).isNotEmpty();

		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addInArrayFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds);
	}
}
