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
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Item;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class Carrier_ShipmentOrder_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ShipmentOrderRepository shipmentOrderRepository = SpringContextHolder.instance.getBean(ShipmentOrderRepository.class);

	@NonNull private final M_InOut_StepDefData inOutTable;
	@NonNull private final Carrier_ShipmentOrder_StepDefData carrierShipmentOrderTable;

	/**
	 * Polls until the shipment's delivery order has been created and at least one parcel has its AWB set.
	 * Implicitly waits for {@code CreatePackagesForShipmentWorkpackageProcessor} →
	 * {@code DeliveryOrderWorkpackageProcessor} (the AWB is the last thing the chain writes).
	 */
	@And("^after not more than (.*)s, Carrier_ShipmentOrder is found:$")
	public void findCarrierShipmentOrder(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
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

			final boolean anyHasAwb = parcels.stream()
					.anyMatch(p -> p.getawb() != null && !p.getawb().isEmpty());

			if (!anyHasAwb)
			{
				return false;
			}

			final DeliveryOrderId deliveryOrderId = DeliveryOrderId.ofRepoId(parcels.get(0).getCarrier_ShipmentOrder_ID());
			resultHolder[0] = shipmentOrderRepository.getById(deliveryOrderId);
			return true;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, foundWithAwb);

		assertThat(resultHolder[0])
				.as("Carrier_ShipmentOrder with AWB for M_InOut_ID=%s was not found within %ss", inOutId, timeoutSec)
				.isNotNull();

		carrierShipmentOrderTable.put(row.getAsIdentifier(), resultHolder[0]);
	}

	@And("validate Carrier_ShipmentOrder_Parcels:")
	public void validateCarrierShipmentOrderParcels(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DeliveryOrder order = carrierShipmentOrderTable.get(
					row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_ShipmentOrder_ID));

			final List<DeliveryOrderParcel> parcels = order.getDeliveryOrderParcels();
			assertThat(parcels).as("Parcels for Carrier_ShipmentOrder_ID=%s", order.getId()).isNotEmpty();

			final String expectedAwb = row.getAsString(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_awb);
			final String expectedTrackingUrl = row.getAsString(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_TrackingURL);
			final boolean checkPdfLabel = row.getAsOptionalBoolean("HasPdfLabel").orElseFalse();

			final SoftAssertions softly = new SoftAssertions();
			for (final DeliveryOrderParcel parcel : parcels)
			{
				softly.assertThat(parcel.getAwb())
						.as("parcel.awb for Carrier_ShipmentOrder_ID=%s", order.getId())
						.isEqualTo(expectedAwb);
				softly.assertThat(parcel.getTrackingUrl())
						.as("parcel.TrackingURL for Carrier_ShipmentOrder_ID=%s", order.getId())
						.isEqualTo(expectedTrackingUrl);
				if (checkPdfLabel)
				{
					softly.assertThat(parcel.getLabelPdfBase64())
							.as("parcel.PdfLabelData for Carrier_ShipmentOrder_ID=%s", order.getId())
							.isNotEmpty();
				}
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
	 * Items are matched by {@code ProductName} when supplied; otherwise the order must have exactly one item.
	 */
	@And("validate Carrier_ShipmentOrder_Items:")
	public void validateCarrierShipmentOrderItems(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DeliveryOrder order = carrierShipmentOrderTable.get(
					row.getAsIdentifier(I_Carrier_ShipmentOrder.COLUMNNAME_Carrier_ShipmentOrder_ID));

			final List<DeliveryOrderItem> allItems = order.getDeliveryOrderParcels().stream()
					.flatMap(p -> p.getItems().stream())
					.collect(java.util.stream.Collectors.toList());

			final DeliveryOrderItem item = row.getAsOptionalString(I_Carrier_ShipmentOrder_Item.COLUMNNAME_ProductName)
					.map(expectedProductName -> allItems.stream()
							.filter(it -> expectedProductName.equals(it.getProductName()))
							.findFirst()
							.orElseThrow(() -> new AssertionError(
									"No delivery order item with productName='" + expectedProductName
											+ "' in Carrier_ShipmentOrder_ID=" + order.getId())))
					.orElseGet(() -> {
						assertThat(allItems)
								.as("Expected exactly one delivery order item in Carrier_ShipmentOrder_ID=%s (supply ProductName to disambiguate)", order.getId())
								.hasSize(1);
						return allItems.get(0);
					});

			final SoftAssertions softly = new SoftAssertions();
			row.getAsOptionalString(I_Carrier_ShipmentOrder_Item.COLUMNNAME_ArticleValue).ifPresent(expected -> softly
					.assertThat(item.getProductValue()).as("productValue").isEqualTo(expected));
			row.getAsOptionalString(I_Carrier_ShipmentOrder_Item.COLUMNNAME_CustomsTariffNumber).ifPresent(expected -> softly
					.assertThat(item.getCustomsTariff()).as("customsTariff").isEqualTo(expected));
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
}
