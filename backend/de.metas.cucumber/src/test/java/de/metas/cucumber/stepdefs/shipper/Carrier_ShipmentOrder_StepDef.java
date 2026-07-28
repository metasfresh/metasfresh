/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shipper;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.inout.InOutId;
import de.metas.shipping.CarrierProductId;
import de.metas.shipper.gateway.commons.model.ShipmentOrderRepository;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderItem;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcel;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Item;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/** Step definitions for {@code Carrier_ShipmentOrder} — finding, validating, and updating carrier shipment orders and their parcels. */
@RequiredArgsConstructor
public class Carrier_ShipmentOrder_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ShipmentOrderRepository shipmentOrderRepository = SpringContextHolder.instance.getBean(ShipmentOrderRepository.class);

	@NonNull private final M_InOut_StepDefData inOutTable;
	@NonNull private final Carrier_ShipmentOrder_StepDefData carrierShipmentOrderTable;
	@NonNull private final Carrier_Product_StepDefData carrierProductTable;

	/**
	 * Polls until the shipment's delivery order has been created and at least one parcel has its AWB set.
	 * Implicitly waits for {@code CreatePackagesForShipmentWorkpackageProcessor} →
	 * {@code DeliveryOrderWorkpackageProcessor} (the AWB is the last thing the chain writes).
	 *
	 * <p>When the gateway splits one shipment's packages into several delivery orders (one per carrier),
	 * the optional {@code Carrier_Product_ID} column selects the one delivery order whose carrier product
	 * matches — otherwise the first found delivery order for the shipment is used.</p>
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b>          — (required) alias to store the found delivery order under<br>
	 *   <b>M_InOut_ID</b>          — (required, identifier-ref) shipment whose delivery order(s) to poll for<br>
	 *   <b>Carrier_Product_ID</b>  — (optional, identifier-ref) disambiguator when the shipment was split into several delivery orders
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, Carrier_ShipmentOrder is found:
	 *   | Identifier | M_InOut_ID  | Carrier_Product_ID |
	 *   | cso_cp1    | inout_split | cp1                |
	 *   | cso_cp2    | inout_split | cp2                |
	 * </pre>
	 */
	@And("^after not more than (.*)s, Carrier_ShipmentOrder is found:$")
	public void findCarrierShipmentOrder(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			try
			{
				findAndStoreCarrierShipmentOrder(timeoutSec, row);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
	}

	private void findAndStoreCarrierShipmentOrder(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final InOutId inOutId = inOutTable.getId(row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID));

		final CarrierProductId expectedCarrierProductId = row.getAsOptionalIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_Product_ID)
				.map(identifier -> identifier.lookupNotNullIdIn(carrierProductTable))
				.orElse(null);

		final DeliveryOrder[] resultHolder = new DeliveryOrder[1];

		final Supplier<Boolean> foundWithAwb = () -> {
			final List<I_Carrier_ShipmentOrder_Parcel> parcels = queryParcelsOfShipment(inOutId).list();

			if (parcels.isEmpty())
			{
				return false;
			}

			final ImmutableSet<DeliveryOrderId> deliveryOrderIds = parcels.stream()
					.filter(parcel -> Check.isNotBlank(parcel.getawb()))
					.map(parcel -> DeliveryOrderId.ofRepoId(parcel.getCarrier_ShipmentOrder_ID()))
					.collect(ImmutableSet.toImmutableSet());

			if (deliveryOrderIds.isEmpty())
			{
				return false;
			}

			final DeliveryOrder match = deliveryOrderIds.stream()
					.map(shipmentOrderRepository::getById)
					.filter(order -> expectedCarrierProductId == null
							|| CarrierProductId.equals(expectedCarrierProductId, order.getCarrierProductId()))
					.findFirst()
					.orElse(null);

			if (match == null)
			{
				return false;
			}

			resultHolder[0] = match;
			return true;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, foundWithAwb);

		assertThat(resultHolder[0])
				.as("Carrier_ShipmentOrder with AWB for M_InOut_ID=%s%s was not found within %ss",
						inOutId,
						expectedCarrierProductId != null ? " and Carrier_Product_ID=" + expectedCarrierProductId : "",
						timeoutSec)
				.isNotNull();

		carrierShipmentOrderTable.put(row.getAsIdentifier(), resultHolder[0]);
	}

	/** All {@link I_Carrier_ShipmentOrder_Parcel}s reachable from the given shipment's {@link I_M_Package}s. */
	private IQuery<I_Carrier_ShipmentOrder_Parcel> queryParcelsOfShipment(@NonNull final InOutId inOutId)
	{
		return queryBL
				.createQueryBuilder(I_M_Package.class)
				.addEqualsFilter(I_M_Package.COLUMNNAME_M_InOut_ID, inOutId)
				.andCollectChildren(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_M_Package_ID, I_Carrier_ShipmentOrder_Parcel.class)
				.create();
	}

	/**
	 * Exact-set ("has only") assertion scoped by shipment: the delivery orders created for the given shipment carry
	 * EXACTLY the listed carrier products — no more, no fewer — read fresh from the DB. One row per delivery order:
	 * under selection rules the gateway creates one {@code Carrier_ShipmentOrder} per {@link I_M_Package}, so a
	 * shipment of N self-packed loose CUs has N one-parcel orders; the table lists them 1-to-1. Because the assertion
	 * re-reads the DB each poll, it also proves negatives over time — e.g. that a frozen shipment's orders keep their
	 * carrier product after a later re-advise mutates only the schedule. Polls until the async delivery-order chain
	 * has settled. Replaces the former single-order "validate ... product for shipment:" step.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Carrier_Product_ID</b> — (required, identifier-ref) expected carrier product of one delivery order
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, Carrier_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, Carrier_ShipmentOrders for M_InOut_ID inout_partial have exactly:
	 *   | Carrier_Product_ID |
	 *   | cp1                |
	 *   | cp1                |
	 * </pre>
	 */
	@And("^after not more than (.*)s, Carrier_ShipmentOrders for M_InOut_ID (.*) have exactly:$")
	public void carrierShipmentOrdersForShipmentHaveExactly(
			final int timeoutSec,
			@NonNull final String inOutIdentifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final InOutId inOutId = inOutTable.getId(inOutIdentifier);

		// One expected carrier product per delivery order the shipment should have; sorted so the comparison is a
		// multiset match (row order irrelevant).
		final List<CarrierProductId> expectedCarrierProductIds = DataTableRows.of(dataTable).stream()
				.map(row -> row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_Product_ID).lookupNotNullIdIn(carrierProductTable))
				.sorted(Comparator.nullsFirst(Comparator.comparingInt(CarrierProductId::getRepoId)))
				.collect(Collectors.toList());

		@SuppressWarnings("unchecked") final List<CarrierProductId>[] actualHolder = new List[1];

		// Poll until the async delivery-order chain has produced EXACTLY the expected carrier-product multiset,
		// re-read fresh from the DB on every try.
		final Supplier<Boolean> hasExactly = () -> {
			final ImmutableSet<DeliveryOrderId> orderIds = queryParcelsOfShipment(inOutId).list().stream()
					.map(parcel -> DeliveryOrderId.ofRepoId(parcel.getCarrier_ShipmentOrder_ID()))
					.collect(ImmutableSet.toImmutableSet());

			final List<CarrierProductId> actual = orderIds.stream()
					.map(shipmentOrderRepository::getById)
					.map(DeliveryOrder::getCarrierProductId)
					.sorted(Comparator.nullsFirst(Comparator.comparingInt(CarrierProductId::getRepoId)))
					.collect(Collectors.toList());

			actualHolder[0] = actual;
			return actual.equals(expectedCarrierProductIds);
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, hasExactly);

		assertThat(actualHolder[0])
				.as("Carrier_ShipmentOrders for M_InOut_ID=%s: the shipment's delivery-order carrier products (fresh from DB, one per order) must be EXACTLY the expected set",
						inOutId)
				.isEqualTo(expectedCarrierProductIds);
	}

	/**
	 * Validates persisted parcel records for a {@link DeliveryOrder}, one row per parcel.
	 * Each row is matched to a parcel by {@code awb} value (order-independent).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Carrier_ShipmentOrder_ID</b> — (required, identifier-ref) carrier shipment order alias<br>
	 *   <b>awb</b> — (required) air waybill number; used as the parcel discriminator<br>
	 *   <b>TrackingURL</b> — (required) expected tracking URL<br>
	 *   <b>HasPdfLabel</b> — (optional) if true, asserts the parcel has a non-empty PDF label<br>
	 * @cucumber.example
	 * <pre>
	 * And validate Carrier_ShipmentOrder_Parcels:
	 *   | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
	 *   | cso_1                    | awb1 | trackingUrl1 | true        |
	 *   | cso_1                    | awb2 | trackingUrl2 | true        |
	 * </pre>
	 */
	@And("validate Carrier_ShipmentOrder_Parcels:")
	public void validateCarrierShipmentOrderParcels(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DeliveryOrder order = carrierShipmentOrderTable.get(
					row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_ShipmentOrder_ID));

			final String expectedAwb = row.getAsString(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_awb);

			final DeliveryOrderParcel parcel = order.getDeliveryOrderParcels().stream()
					.filter(p -> expectedAwb.equals(p.getAwb()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("No parcel found with awb=" + expectedAwb
							+ " in Carrier_ShipmentOrder_ID=" + order.getId()));

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(parcel.getAwb())
					.as("parcel.awb for Carrier_ShipmentOrder_ID=%s", order.getId())
					.isEqualTo(expectedAwb);
			row.getAsOptionalString(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_TrackingURL).ifPresent(expected -> softly
					.assertThat(parcel.getTrackingUrl())
					.as("parcel.TrackingURL for Carrier_ShipmentOrder_ID=%s", order.getId())
					.isEqualTo(expected));
			if (row.getAsOptionalBoolean("HasPdfLabel").orElseFalse())
			{
				softly.assertThat(parcel.getLabelPdfBase64())
						.as("parcel.PdfLabelData for Carrier_ShipmentOrder_ID=%s", order.getId())
						.isNotEmpty();
			}
			softly.assertAll();
		});
	}

	/**
	 * Validates the persisted {@link I_Carrier_ShipmentOrder} record (loaded as a {@link DeliveryOrder}
	 * via {@code shipmentOrderRepository.getById}) against the expected column values.
	 *
	 * @cucumber.stepdef Validate a captured/persisted Carrier_ShipmentOrder.
	 * @cucumber.columns
	 *   <b>Carrier_ShipmentOrder_ID</b> — (required, identifier-ref) the shipment order to validate<br>
	 *   <b>Shipper_Name1</b>, <b>Shipper_CountryISO2Code</b> — (optional) pickup address<br>
	 *   <b>Receiver_Name1</b>, <b>Receiver_Name2</b>, <b>Receiver_StreetName1</b>, <b>Receiver_StreetName2</b>,
	 *   <b>Receiver_StreetNumber</b>, <b>Receiver_ZipCode</b>, <b>Receiver_City</b>, <b>Receiver_CountryISO2Code</b> — (optional) delivery address<br>
	 *   <b>Receiver_ContactName</b>, <b>Receiver_Phone</b>, <b>Receiver_Email</b> — (optional) delivery contact<br>
	 *   <b>CustomerReference</b> — (optional)<br>
	 *   <b>IsPreAdviceRequired</b> — (optional) expected persisted pre-advice flag as "Y"/"N"<br>
	 * @cucumber.example
	 * And validate Carrier_ShipmentOrder:
	 *   | Carrier_ShipmentOrder_ID | Receiver_City | IsPreAdviceRequired |
	 *   | cso_do                   | city          | Y                   |
	 * @see #validateCarrierShipmentOrderParcels(DataTable)
	 * @see #validateCarrierShipmentOrderItems(DataTable)
	 */
	@And("validate Carrier_ShipmentOrder:")
	public void validateCarrierShipmentOrder(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DeliveryOrder order = carrierShipmentOrderTable.get(
					row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_ShipmentOrder_ID));

			final SoftAssertions softly = new SoftAssertions();

			final Address pickup = order.getPickupAddress();
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Shipper_Name1).ifPresent(expected -> softly
					.assertThat(pickup.getCompanyName1()).as("pickupAddress.companyName1").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Shipper_CountryISO2Code).ifPresent(expected -> softly
					.assertThat(pickup.getCountry().getAlpha2()).as("pickupAddress.country.alpha2").isEqualTo(expected));

			final Address delivery = order.getDeliveryAddress();
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_Name1).ifPresent(expected -> softly
					.assertThat(delivery.getCompanyName1()).as("deliveryAddress.companyName1").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_Name2).ifPresent(expected -> softly
					.assertThat(delivery.getCompanyName2()).as("deliveryAddress.companyName2").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_StreetName1).ifPresent(expected -> softly
					.assertThat(delivery.getStreet1()).as("deliveryAddress.street1").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_StreetName2).ifPresent(expected -> softly
					.assertThat(delivery.getStreet2()).as("deliveryAddress.street2").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_StreetNumber).ifPresent(expected -> softly
					.assertThat(delivery.getHouseNo()).as("deliveryAddress.houseNo").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_ZipCode).ifPresent(expected -> softly
					.assertThat(delivery.getZipCode()).as("deliveryAddress.zipCode").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_City).ifPresent(expected -> softly
					.assertThat(delivery.getCity()).as("deliveryAddress.city").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_CountryISO2Code).ifPresent(expected -> softly
					.assertThat(delivery.getCountry().getAlpha2()).as("deliveryAddress.country.alpha2").isEqualTo(expected));

			final ContactPerson contact = order.getDeliveryContact();
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_ContactName).ifPresent(expected -> {
				softly.assertThat(contact).as("deliveryContact").isNotNull();
				if (contact != null)
				{
					softly.assertThat(contact.getName()).as("deliveryContact.name").isEqualTo(expected);
				}
			});
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_Phone).ifPresent(expected -> {
				softly.assertThat(contact).as("deliveryContact").isNotNull();
				if (contact != null)
				{
					softly.assertThat(contact.getSimplePhoneNumber()).as("deliveryContact.phone").isEqualTo(expected);
				}
			});
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_Receiver_Email).ifPresent(expected -> {
				softly.assertThat(contact).as("deliveryContact").isNotNull();
				if (contact != null)
				{
					softly.assertThat(contact.getEmailAddress()).as("deliveryContact.email").isEqualTo(expected);
				}
			});

			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_CustomerReference).ifPresent(expected -> softly
					.assertThat(order.getCustomerReference()).as("customerReference").isEqualTo(expected));

			// Asserts the PERSISTED pre-advice flag (loaded from the shipment-order record), not the emitted request.
			row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_IsPreAdviceRequired).ifPresent(expected -> softly
					.assertThat(order.getPreAdviceRequired()).as("preAdviceRequired").isEqualTo(expected));

			softly.assertAll();
		});
	}

	/**
	 * Items are matched by {@code ProductName} and optionally by {@code QtyShipped} when supplied;
	 * if neither is present the order must have exactly one item.
	 * {@code CountryOfOrigin} is an assertion-only column — not used for matching.
	 */
	@And("validate Carrier_ShipmentOrder_Items:")
	public void validateCarrierShipmentOrderItems(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DeliveryOrder order = carrierShipmentOrderTable.get(
					row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_ShipmentOrder_ID));

			final List<DeliveryOrderItem> allItems = order.getDeliveryOrderParcels().stream()
					.flatMap(p -> p.getItems().stream())
					.collect(Collectors.toList());

			final Optional<String> productNameOpt = row.getAsOptionalString(I_Carrier_ShipmentOrder_Item.COLUMNNAME_ProductName);
			final Optional<java.math.BigDecimal> qtyOpt = row.getAsOptionalBigDecimal(I_Carrier_ShipmentOrder_Item.COLUMNNAME_QtyShipped);

			final DeliveryOrderItem item;
			if (productNameOpt.isPresent() || qtyOpt.isPresent())
			{
				Stream<DeliveryOrderItem> filtered = allItems.stream();
				if (productNameOpt.isPresent())
				{
					final String expectedProductName = productNameOpt.get();
					filtered = filtered.filter(it -> expectedProductName.equals(it.getProductName()));
				}
				if (qtyOpt.isPresent())
				{
					final java.math.BigDecimal expectedQty = qtyOpt.get();
					filtered = filtered.filter(it -> expectedQty.compareTo(it.getShippedQuantity().toBigDecimal()) == 0);
				}
				final String matchKey = productNameOpt.map(n -> "productName='" + n + "'").orElse("")
						+ qtyOpt.map(q -> (productNameOpt.isPresent() ? ", " : "") + "qty=" + q).orElse("");
				item = filtered.findFirst()
						.orElseThrow(() -> new AssertionError(
								"No delivery order item with " + matchKey
										+ " in Carrier_ShipmentOrder_ID=" + order.getId()));
			}
			else
			{
				assertThat(allItems)
						.as("Expected exactly one delivery order item in Carrier_ShipmentOrder_ID=%s (supply ProductName or QtyShipped to disambiguate)", order.getId())
						.hasSize(1);
				item = allItems.get(0);
			}

			final SoftAssertions softly = new SoftAssertions();
			row.getAsOptionalString(I_Carrier_ShipmentOrder_Item.COLUMNNAME_ArticleValue).ifPresent(expected -> softly
					.assertThat(item.getProductValue()).as("productValue").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder_Item.COLUMNNAME_CustomsTariffNumber).ifPresent(expected -> softly
					.assertThat(item.getCustomsTariff()).as("customsTariff").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder_Item.COLUMNNAME_CountryOfOrigin).ifPresent(expected -> softly
					.assertThat(item.getCountryOfOrigin()).as("countryOfOrigin").isEqualTo(expected));
			row.getAsOptionalBigDecimal(I_Carrier_ShipmentOrder_Item.COLUMNNAME_QtyShipped).ifPresent(expected -> softly
					.assertThat(item.getShippedQuantity().toBigDecimal()).as("shippedQuantity").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal(I_Carrier_ShipmentOrder_Item.COLUMNNAME_Price).ifPresent(expected -> softly
					.assertThat(item.getUnitPrice().toBigDecimal()).as("unitPrice").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal(I_Carrier_ShipmentOrder_Item.COLUMNNAME_TotalPrice).ifPresent(expected -> softly
					.assertThat(item.getTotalValue().toBigDecimal()).as("totalValue").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal(I_Carrier_ShipmentOrder_Item.COLUMNNAME_TotalWeightInKg).ifPresent(expected -> softly
					.assertThat(item.getTotalWeightInKg()).as("totalWeightInKg").isEqualByComparingTo(expected));
			softly.assertAll();
		});
	}

	/**
	 * Sets (or clears) the {@code TrackingURL} on all {@link I_Carrier_ShipmentOrder_Parcel} rows
	 * that belong to the given {@link I_Carrier_ShipmentOrder}.
	 *
	 * <p>In production the carrier gateway writes the TrackingURL; this step simulates that
	 * callback so the notification-delay gate can be released in tests without a live gateway.</p>
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Carrier_ShipmentOrder_ID</b> — (required, identifier-ref) carrier shipment order whose parcels are updated<br>
	 *   <b>TrackingURL</b> — (required, null-allowed) URL to set; use {@code null} to clear<br>
	 * @cucumber.depends StepDefData: Carrier_ShipmentOrder_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update Carrier_ShipmentOrder_Parcel TrackingURL:
	 *   | Carrier_ShipmentOrder_ID | TrackingURL                    |
	 *   | cso_1                    | https://track.example.com/p123 |
	 * </pre>
	 */
	@And("update Carrier_ShipmentOrder_Parcel TrackingURL:")
	public void updateCarrierShipmentOrderParcelTrackingURL(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DeliveryOrder order = carrierShipmentOrderTable.get(
					row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_ShipmentOrder_ID));

			final String trackingURLRaw = row.getAsOptionalString(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_TrackingURL)
					.orElse(null);
			// "null" token (literal string "null" or dash) maps to Java null → clears the TrackingURL in the DB
			final String trackingURL = de.metas.cucumber.stepdefs.DataTableUtil.nullToken2Null(trackingURLRaw);

			final List<I_Carrier_ShipmentOrder_Parcel> parcels = queryBL
					.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
					.addEqualsFilter(
							I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID,
							order.getId().getRepoId())
					.create()
					.list();

			assertThat(parcels)
					.as("Parcels for Carrier_ShipmentOrder_ID=%s", order.getId())
					.isNotEmpty();

			for (final I_Carrier_ShipmentOrder_Parcel parcel : parcels)
			{
				parcel.setTrackingURL(trackingURL);
				InterfaceWrapperHelper.save(parcel);
			}
		});
	}

}
