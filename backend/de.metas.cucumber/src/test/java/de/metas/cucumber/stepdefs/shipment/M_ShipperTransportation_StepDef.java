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

package de.metas.cucumber.stepdefs.shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Package_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.deliveryplanning.DeliveryPlanningRejectionHelper;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.shipment.pickingterminal.M_ShippingPackage_StepDefData;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderId;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for M_ShipperTransportation, the transport order ("Speditionslieferung"). Allows scenarios to:
 * <ul>
 *   <li>Create a transport order and add orders / shipments / packages to it;</li>
 *   <li>Load the transport order belonging to a shipment, and assert its column values;</li>
 *   <li>Update its dates (notably {@code BLDate} and {@code ETA}) and complete it, which drives
 *       {@code M_ShipperTransportation.syncOrderDatesOnEdit} and therefore the pay-schedule due
 *       dates of the linked orders (scenarios {@code @Id:S30954_1..5}).</li>
 * </ul>
 * <p>
 * Note: these steps write through the model layer ({@code InterfaceWrapperHelper}), never through the
 * WebUI {@code Document} layer. Field editability — {@code AD_Column.IsAlwaysUpdateable} evaluated by
 * {@code DocumentReadonly} on a {@code Processed} record — is therefore out of reach here by
 * construction, and is covered instead by the Playwright spec
 * {@code transport-order-dates-editable-when-completed.spec.js}.
 */
@AllArgsConstructor
public class M_ShipperTransportation_StepDef
{
	private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	private final M_Shipper_StepDefData shipperTable;
	private final M_ShippingPackage_StepDefData shippingPackageTable;
	private final M_Package_StepDefData packageTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_BPartner_StepDefData bPartnerTable;


	private final M_InOut_StepDefData shipmentTable;
	private final C_Order_StepDefData orderTable;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	@NonNull private final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final PurchaseOrderToShipperTransportationService purchaseOrderToShipperTransportationService = SpringContextHolder.instance.getBean(PurchaseOrderToShipperTransportationService.class);
	@NonNull private final DeliveryPlanningRejectionHelper rejectionHelper;


	@And("validate M_ShipperTransportation:")
	public void validateM_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
			assertThat(deliveryInstruction).isNotNull();

			final SoftAssertions softly = new SoftAssertions();

			final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
			softly.assertThat(shipper).isNotNull();
			softly.assertThat(deliveryInstruction.getM_Shipper_ID()).as(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID).isEqualTo(shipper.getM_Shipper_ID());

			final String shipperBPIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner shipperBP = bPartnerTable.get(shipperBPIdentifier);
			softly.assertThat(shipperBP).isNotNull();
			softly.assertThat(deliveryInstruction.getShipper_BPartner_ID()).as(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID).isEqualTo(shipperBP.getC_BPartner_ID());

			final String shipperLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location shipperLocation = bPartnerLocationTable.get(shipperLocationIdentifier);
			softly.assertThat(shipperLocation).isNotNull();
			softly.assertThat(deliveryInstruction.getShipper_Location_ID()).as(I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID).isEqualTo(shipperLocation.getC_BPartner_Location_ID());

			final String deliveryLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(deliveryLocationIdentifier))
			{
				final I_C_BPartner_Location deliveryLocation = bPartnerLocationTable.get(deliveryLocationIdentifier);
				softly.assertThat(deliveryLocation).isNotNull();
				softly.assertThat(deliveryInstruction.getC_BPartner_Location_Delivery_ID()).as(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID).isEqualTo(deliveryLocation.getC_BPartner_Location_ID());
			}

			final String loadingLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(loadingLocationIdentifier))
			{
				final I_C_BPartner_Location loadingLocation = bPartnerLocationTable.get(loadingLocationIdentifier);
				softly.assertThat(loadingLocation).isNotNull();
				softly.assertThat(deliveryInstruction.getC_BPartner_Location_Loading_ID()).as(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID).isEqualTo(loadingLocation.getC_BPartner_Location_ID());
			}

			final Timestamp deliveryDate = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_ETA);
			if (deliveryDate != null)
			{
				softly.assertThat(deliveryInstruction.getETA()).as(I_M_ShipperTransportation.COLUMNNAME_ETA).isEqualTo(deliveryDate);
			}

			final String docStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_DocStatus);
			if (Check.isNotBlank(docStatus))
			{
				softly.assertThat(deliveryInstruction.getDocStatus()).as(I_M_ShipperTransportation.COLUMNNAME_DocStatus).isEqualTo(docStatus);
			}

			// DeliveredState (Task Q9): the three-state delivered indicator, e.g. "NotDelivered" / "PartlyDelivered" / "FullyDelivered".
			final String deliveredState = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_DeliveredState);
			if (Check.isNotBlank(deliveredState))
			{
				softly.assertThat(deliveryInstruction.getDeliveredState()).as(I_M_ShipperTransportation.COLUMNNAME_DeliveredState).isEqualTo(deliveredState);
			}

			softly.assertAll();
		}
	}

	@And("load Transportation Order from Shipment")
	public void load_M_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOut shipment = shipmentTable.get(shipmentIdentifier);

			final I_M_ShipperTransportation shipperTransportation = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, shipment.getM_InOut_ID())
					.andCollect(I_M_ShippingPackage.COLUMN_M_ShipperTransportation_ID)
					.orderBy(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
					.first();
			assertThat(shipperTransportation).isNotNull();
			final String shipperTransportationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			deliveryInstructionTable.putOrReplace(shipperTransportationIdentifier, shipperTransportation);
		}
	}

	/**
	 * Polling variant of {@code load Transportation Order from Shipment} — waits for the async
	 * {@code M_InOut.afterComplete → CreatePackagesForShipmentWorkpackageProcessor} chain triggered by
	 * sysconfig {@code de.metas.handlingunits.picking.addToDailyShipperTransportationOrder=true}.
	 */
	@And("^after not more than (.*)s, Transportation Order is found for Shipment:$")
	public void findTransportationOrderForShipment(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable).forEach(row -> {
			try
			{
				pollTransportationOrderForShipment(timeoutSec, row);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
	}

	private void pollTransportationOrderForShipment(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_M_InOut shipment = shipmentTable.get(row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID));

		final I_M_ShipperTransportation[] resultHolder = new I_M_ShipperTransportation[1];

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> {
			final I_M_ShipperTransportation found = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, shipment.getM_InOut_ID())
					.andCollect(I_M_ShippingPackage.COLUMN_M_ShipperTransportation_ID)
					.orderBy(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
					.first();
			if (found == null)
			{
				return false;
			}
			resultHolder[0] = found;
			return true;
		});

		assertThat(resultHolder[0])
				.as("No M_ShipperTransportation found for M_InOut_ID=%s within %ss — is sysconfig "
						+ "'de.metas.handlingunits.picking.addToDailyShipperTransportationOrder' set to true?",
						shipment.getM_InOut_ID(), timeoutSec)
				.isNotNull();

		deliveryInstructionTable.putOrReplace(row.getAsIdentifier(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID), resultHolder[0]);
	}

	/**
	 * Creates one {@code M_ShipperTransportation} (transport order / delivery instruction) record per row and
	 * stores it under its identifier. {@code TransportDirection} is <b>required</b>: the column is mandatory and
	 * has no default, so a scenario has to say which direction it means.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> (or <b>M_ShipperTransportation_ID</b>) — (required) alias to store the created record
	 *   under; either header works, the step registers {@code M_ShipperTransportation_ID} as an additional row
	 *   identifier column<br>
	 *   <b>TransportDirection</b> — (required) {@code Outgoing}, {@code Incoming} or {@code Dropship}
	 *   (see {@code X_M_ShipperTransportation.TRANSPORTDIRECTION_*})<br>
	 *   <b>M_Shipper_ID</b> — (optional, identifier-ref) the shipper; set through
	 *   {@code IShipperTransportationBL#setShipper}, which also copies the shipper's pickup time window<br>
	 *   <b>Shipper_BPartner_ID</b> — (optional, identifier-ref) the forwarder business partner<br>
	 *   <b>Shipper_Location_ID</b> — (optional, identifier-ref) the forwarder location<br>
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData, M_Shipper_StepDefData,
	 * C_BPartner_StepDefData, C_BPartner_Location_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains Transport Order
	 *   | Identifier     | M_Shipper_ID    | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
	 *   | transportOrder | shipper_freight | supplier            | supplierLocation    | Incoming           |
	 * </pre>
	 */
	@And("metasfresh contains Transport Order")
	public void add_TransportOrder(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.forEach(this::createTransportOrder);
	}

	/**
	 * Row-level worker of {@link #add_TransportOrder(DataTable)}; the column contract is documented there.
	 * Public so other step defs can create a transport order from a row they already hold.
	 */
	public void createTransportOrder(@NonNull final DataTableRow row)
	{
		final I_M_ShipperTransportation shipperTransportationRecord = newInstance(I_M_ShipperTransportation.class);

		shipperTransportationRecord.setTransportDirection(row.getAsString(I_M_ShipperTransportation.COLUMNNAME_TransportDirection));

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID)
				.map(shipperTable::getId)
				.ifPresent(id -> shipperTransportationBL.setShipper(shipperTransportationRecord,id));

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID)
				.map(bPartnerTable::getId)
				.ifPresent(id -> shipperTransportationRecord.setShipper_BPartner_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID)
				.map(bPartnerLocationTable::getId)
				.ifPresent(id -> shipperTransportationRecord.setShipper_Location_ID(id.getRepoId()));

		saveRecord(shipperTransportationRecord);

		deliveryInstructionTable.putOrReplace(row.getAsIdentifier(), shipperTransportationRecord);
	}

	@And("^metasfresh contains exactly (.*) M_ShippingPackages for transportation order: (.*)$")
	public void validateShippingPackagesForTransportationOrder(final int expectedShippingPackages, @NonNull final String transportationOrderIdentifier)
	{
		final int shipperTransportationId = deliveryInstructionTable.get(transportationOrderIdentifier)
				.getM_ShipperTransportation_ID();
		final int actualShippingPackages = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId)
				.create()
				.count();
		assertThat(actualShippingPackages).as("Number of M_ShippingPackages for M_ShipperTransportation_ID" + shipperTransportationId).isEqualTo(expectedShippingPackages);
	}

	@And("metasfresh contains M_ShippingPackage")
	public void add_M_ShippingPackage(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID)
				.forEach(this::createM_ShippingPackage);
	}


	@And("^C_Order_AddTo_M_ShipperTransportation is invoked for order (.*) and transportation order: (.*)")
	public void addOrderToShipperTransportation(@NonNull final String orderIdentifier, @NonNull final String transportationOrderIdentifier)
	{
		final OrderId orderId = OrderId.ofRepoId(orderTable.get(orderIdentifier)
				.getC_Order_ID());
		final ShipperTransportationId shipperTransportationId = ShipperTransportationId.ofRepoId(deliveryInstructionTable.get(transportationOrderIdentifier)
				.getM_ShipperTransportation_ID());

		purchaseOrderToShipperTransportationService.addPurchaseOrderToShipperTransportation(orderId, shipperTransportationId);
	}

	/**
	 * Same call as {@link #addOrderToShipperTransportation(String, String)}, but asserts the add is REFUSED —
	 * used for the direction guard in {@link PurchaseOrderToShipperTransportationService}: a purchase order is a
	 * receipt-side document and must not join a transport order whose direction has no receipt leg (Outgoing-only).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ErrorAdMessage</b> — (optional) the {@code AD_Message} the add is expected to be rejected with<br>
	 * @cucumber.example
	 * <pre>
	 * And C_Order_AddTo_M_ShipperTransportation is invoked for order order_PO and transportation order outgoingTransportOrder, and is refused:
	 *   | ErrorAdMessage                              |
	 *   | WrongTransportDirectionForPurchaseOrder      |
	 * </pre>
	 */
	@And("^C_Order_AddTo_M_ShipperTransportation is invoked for order (.*) and transportation order (.*), and is refused:$")
	public void addOrderToShipperTransportation_refused(
			@NonNull final String orderIdentifier,
			@NonNull final String transportationOrderIdentifier,
			@NonNull final DataTable dataTable)
	{
		final OrderId orderId = OrderId.ofRepoId(orderTable.get(orderIdentifier)
				.getC_Order_ID());
		final ShipperTransportationId shipperTransportationId = ShipperTransportationId.ofRepoId(deliveryInstructionTable.get(transportationOrderIdentifier)
				.getM_ShipperTransportation_ID());

		rejectionHelper.runExpectingRejectionIfAny(
				DataTableRows.of(dataTable).singleRow(),
				ImmutableSet.of(),
				() -> purchaseOrderToShipperTransportationService.addPurchaseOrderToShipperTransportation(orderId, shipperTransportationId));
	}


	public void createM_ShippingPackage(@NonNull final DataTableRow row)
	{
		final I_M_ShippingPackage shippingPackageRecord = newInstance(I_M_ShippingPackage.class);

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.map(deliveryInstructionTable::getId)
				.ifPresent(id -> shippingPackageRecord.setM_ShipperTransportation_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_C_Order_ID)
				.map(orderTable::getId)
				.ifPresent(id -> shippingPackageRecord.setC_Order_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID)
				.map(shipmentTable::getId)
				.ifPresent(id -> shippingPackageRecord.setM_InOut_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_M_Package_ID)
				.map(packageTable::getId)
				.ifPresent(id -> shippingPackageRecord.setM_Package_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_C_BPartner_Location_ID)
				.map(bPartnerLocationTable::getId)
				.ifPresent(id -> shippingPackageRecord.setC_BPartner_Location_ID(id.getRepoId()));

		saveRecord(shippingPackageRecord);

		row.getAsOptionalIdentifier()
				.ifPresent(shippingPackageIdentifier -> shippingPackageTable.putOrReplace(shippingPackageIdentifier, shippingPackageRecord));
	}

	@And("metasfresh contains M_Package")
	public void add_M_Package(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::create_M_Package);
	}

	private void create_M_Package(@NonNull final DataTableRow row)
	{
		final I_M_Package packageRecord = newInstance(I_M_Package.class);

		row.getAsOptionalIdentifier(I_M_Package.COLUMNNAME_M_Shipper_ID)
				.map(shipperTable::getId)
				.ifPresent(id -> packageRecord.setM_Shipper_ID(id.getRepoId()));
		saveRecord(packageRecord);

		row.getAsOptionalIdentifier()
				.ifPresent(packageIdentifier -> packageTable.putOrReplace(packageIdentifier, packageRecord));
	}

	/**
	 * Updates the transport order THROUGH THE MODEL LAYER on purpose: {@code saveRecord} fires the real
	 * {@code @ModelChange} interceptor {@code de.metas.shipping.model.validator.M_ShipperTransportation#syncOrderDatesOnEdit},
	 * which is what propagates ETA / BLDate onto the linked purchase orders and re-drives their pay schedules.
	 * Never replace this with a direct SQL/DB write: the propagation would no longer be exercised and the
	 * scenarios relying on it (S30954_1..S30954_3 and S30954_5 in purchaseOrderComplexPaymentTerm.feature)
	 * would keep passing with the chain broken.
	 * <p>
	 * Note this writes straight to the record and therefore bypasses the WebUI Document layer, where a
	 * completed ({@code Processed='Y'}) transport order is read-only unless the column is always-updateable.
	 * That gate is covered by the Playwright spec
	 * {@code e2e/frontend-webui/tests/spec/transport-order-dates-editable-when-completed.spec.js}.
	 * <p>
	 * On a DELIVERY instruction the same write also syncs the changed dates down onto every allocated planning.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShipperTransportation_ID</b> (or <b>Identifier</b>) — (required, identifier-ref) the record to update<br>
	 *   <b>ETD</b> — (optional) new estimated departure<br>
	 *   <b>ETA</b> — (optional) new estimated arrival<br>
	 *   <b>BLDate</b> — (optional) new bill-of-lading date<br>
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update transport order
	 *   | M_ShipperTransportation_ID | ETD                  | ETA                  |
	 *   | deliveryInstruction        | 2023-06-01T00:00:00Z | 2023-06-05T00:00:00Z |
	 * </pre>
	 * @see de.metas.shipping.model.validator.M_ShipperTransportation
	 */
	@And("update transport order")
	public void update_TransportOrder(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.forEach(this::updateTransportOrder);
	}

	private void updateTransportOrder(@NonNull final DataTableRow tableRow)
	{
		final ShipperTransportationId shipperTransportationId = tableRow.getAsIdentifier().lookupNotNullIdIn(deliveryInstructionTable);
		final I_M_ShipperTransportation record = shipperTransportationDAO.getById(shipperTransportationId);

		tableRow.getAsOptionalInstant(I_M_ShipperTransportation.COLUMNNAME_ETD)
				.ifPresent(expected -> record.setETD(Timestamp.from(expected)));

		tableRow.getAsOptionalInstant(I_M_ShipperTransportation.COLUMNNAME_ETA)
				.ifPresent(expected -> record.setETA(Timestamp.from(expected)));

		tableRow.getAsOptionalInstant(I_M_ShipperTransportation.COLUMNNAME_BLDate)
				.ifPresent(expected -> record.setBLDate(Timestamp.from(expected)));
		saveRecord(record);

		deliveryInstructionTable.putOrReplace(tableRow.getAsIdentifier(), record);
	}

	@And("^the transport order identified by (.*) is completed$")
	public void completeTransportOrder(@NonNull final String orderIdentifier)
	{
		final I_M_ShipperTransportation transportOrder = deliveryInstructionTable.get(orderIdentifier);
		transportOrder.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(transportOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	/**
	 * Evaluates the M_ShipperTransportation_ID picker of the given AddTo process (Task Q18's direction filter) -
	 * the SAME persisted {@code AD_Val_Rule.Code} the WebUI process-parameter picker evaluates, substituted
	 * through the real {@link Env#parseContext} context mechanism (never a re-implemented query) - and asserts
	 * which named transport orders it does/doesn't offer. Pass {@code "no order"} for {@code orderIdentifierOrNoOrder}
	 * to assert the "no order in context" fallback (must offer everything, unchanged).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — the transport order, from "metasfresh contains Transport Order"<br>
	 *   <b>Offered</b> — {@code true}/{@code false}, whether the picker must offer it<br>
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipperTransportation_ID picker on M_ReceiptSchedule_AddTo_M_ShipperTransportation, for order order_PO_Wrong, offers:
	 *   | Identifier             | Offered |
	 *   | outgoingTransportOrder | false   |
	 *   | incomingTransportOrder | true    |
	 * </pre>
	 */
	@Then("^the M_ShipperTransportation_ID picker on (.*), for order (.*), offers:$")
	public void picker_offers_for_order(
			@NonNull final String processValue,
			@NonNull final String orderIdentifierOrNoOrder,
			@NonNull final DataTable dataTable)
	{
		final Integer orderRepoId = "no order".equals(orderIdentifierOrNoOrder)
				? null
				: orderTable.get(orderIdentifierOrNoOrder).getC_Order_ID();

		final ImmutableSet<Integer> offeredIds = evaluatePickerOfferedIds(processValue, orderRepoId);

		final SoftAssertions softly = new SoftAssertions();
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);
			final boolean expectedOffered = DataTableUtil.extractBooleanForColumnName(row, "Offered");
			final int transportOrderId = deliveryInstructionTable.get(identifier).getM_ShipperTransportation_ID();

			softly.assertThat(offeredIds.contains(transportOrderId))
					.as("picker on %s offers %s (M_ShipperTransportation_ID=%s) for order context %s",
							processValue, identifier, transportOrderId, orderIdentifierOrNoOrder)
					.isEqualTo(expectedOffered);
		}
		softly.assertAll();
	}

	private ImmutableSet<Integer> evaluatePickerOfferedIds(@NonNull final String processValue, @Nullable final Integer orderRepoId)
	{
		final I_AD_Process process = queryBL.createQueryBuilder(I_AD_Process.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Process.COLUMNNAME_Value, processValue)
				.create()
				.firstOnlyNotNull(I_AD_Process.class);

		final I_AD_Process_Para para = queryBL.createQueryBuilder(I_AD_Process_Para.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, process.getAD_Process_ID())
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_ColumnName, I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.create()
				.firstOnlyNotNull(I_AD_Process_Para.class);

		assertThat(para.getAD_Val_Rule_ID())
				.as("the M_ShipperTransportation_ID parameter of %s is narrowed by a value rule", processValue)
				.isNotZero();

		final I_AD_Val_Rule valRule = load(para.getAD_Val_Rule_ID(), I_AD_Val_Rule.class);

		// scratch WindowNo, private to this evaluation - a copy of the shared ctx so we never leak the
		// C_Order_ID we set here into any other scenario's context
		final Properties ctx = new Properties(Env.getCtx());
		final int windowNo = 999999;
		if (orderRepoId != null)
		{
			Env.setContext(ctx, windowNo, I_C_Order.COLUMNNAME_C_Order_ID, orderRepoId);
		}
		final String whereClause = Env.parseContext(ctx, windowNo, valRule.getCode(), true);
		assertThat(whereClause).as("substituted %s WHERE clause must not be blank", valRule.getName()).isNotBlank();

		return ImmutableSet.copyOf(
				queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
						.filter(TypedSqlQueryFilter.of(whereClause))
						.create()
						.listIds());
	}

}
