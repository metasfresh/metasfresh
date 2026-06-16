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
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Item;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import javax.annotation.Nullable;
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
	 * @cucumber.columns
	 *   <b>Identifier</b>          — (required) alias to store the found delivery order under<br>
	 *   <b>M_InOut_ID</b>          — (required, identifier-ref) shipment whose delivery order(s) to poll for<br>
	 *   <b>Carrier_Product_ID</b>  — (optional, identifier-ref) disambiguator when the shipment was split into several delivery orders
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

		final CarrierProductId expectedProductId = row.getAsOptionalIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_Product_ID)
				.map(identifier -> identifier.lookupNotNullIdIn(carrierProductTable))
				.orElse(null);

		final DeliveryOrder[] resultHolder = new DeliveryOrder[1];

		final Supplier<Boolean> foundWithAwb = () -> {
			final List<I_Carrier_ShipmentOrder_Parcel> parcels = queryBL
					.createQueryBuilder(I_M_Package.class)
					.addEqualsFilter(I_M_Package.COLUMNNAME_M_InOut_ID, inOutId)
					.andCollectChildren(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_M_Package_ID, I_Carrier_ShipmentOrder_Parcel.class)
					.create()
					.list();

			if (parcels.isEmpty())
			{
				return false;
			}

			final List<DeliveryOrderId> deliveryOrderIds = parcels.stream()
					.filter(p -> p.getawb() != null && !p.getawb().isEmpty())
					.map(p -> DeliveryOrderId.ofRepoId(p.getCarrier_ShipmentOrder_ID()))
					.distinct()
					.collect(Collectors.toList());

			if (deliveryOrderIds.isEmpty())
			{
				return false;
			}

			final DeliveryOrder match = deliveryOrderIds.stream()
					.map(shipmentOrderRepository::getById)
					.filter(order -> expectedProductId == null
							|| expectedProductId.equals(carrierProductIdOfCso(order.getId())))
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
						expectedProductId != null ? " and Carrier_Product_ID=" + expectedProductId : "",
						timeoutSec)
				.isNotNull();

		carrierShipmentOrderTable.put(row.getAsIdentifier(), resultHolder[0]);
	}

	@Nullable
	private CarrierProductId carrierProductIdOfCso(@NonNull final DeliveryOrderId deliveryOrderId)
	{
		final I_Carrier_ShipmentOrder cso = InterfaceWrapperHelper.load(deliveryOrderId.getRepoId(), I_Carrier_ShipmentOrder.class);
		return CarrierProductId.ofRepoIdOrNull(cso.getCarrier_Product_ID());
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

	/**
	 * Freshly reloads the {@link I_Carrier_ShipmentOrder} DB record for the given shipment and asserts
	 * its {@code Carrier_Product_ID}. Bypasses {@link Carrier_ShipmentOrder_StepDefData} so the assertion
	 * always reflects the current DB state, not the value captured when the order was first found.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b>          — (required, identifier-ref) shipment whose delivery order is checked<br>
	 *   <b>Carrier_Product_ID</b>  — (required, identifier-ref) expected carrier product
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, Carrier_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate Carrier_ShipmentOrder product for shipment:
	 *   | M_InOut_ID    | Carrier_Product_ID |
	 *   | inout_partial | cp1                |
	 * </pre>
	 */
	@And("validate Carrier_ShipmentOrder product for shipment:")
	public void validateCarrierShipmentOrderProductForShipment(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final InOutId inOutId = inOutTable.getId(row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID));
			final CarrierProductId expectedProductId = row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_Product_ID)
					.lookupNotNullIdIn(carrierProductTable);

			final I_Carrier_ShipmentOrder_Parcel parcel = queryBL
					.createQueryBuilder(I_M_Package.class)
					.addEqualsFilter(I_M_Package.COLUMNNAME_M_InOut_ID, inOutId)
					.andCollectChildren(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_M_Package_ID, I_Carrier_ShipmentOrder_Parcel.class)
					.create()
					.first();

			assertThat(parcel)
					.as("Carrier_ShipmentOrder_Parcel for M_InOut_ID=%s", inOutId)
					.isNotNull();

			final I_Carrier_ShipmentOrder cso = InterfaceWrapperHelper.load(
					parcel.getCarrier_ShipmentOrder_ID(), I_Carrier_ShipmentOrder.class);
			assertThat(cso)
					.as("Carrier_ShipmentOrder for M_InOut_ID=%s", inOutId)
					.isNotNull();

			assertThat(CarrierProductId.ofRepoIdOrNull(cso.getCarrier_Product_ID()))
					.as("Carrier_ShipmentOrder.Carrier_Product_ID for M_InOut_ID=%s", inOutId)
					.isEqualTo(expectedProductId);
		});
	}
}
