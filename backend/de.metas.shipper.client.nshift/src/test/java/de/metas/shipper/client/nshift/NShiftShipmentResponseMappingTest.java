/*
 * #%L
 * de.metas.shipper.client.nshift
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipper.client.nshift;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponseItem;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonPackage;
import de.metas.shipper.client.nshift.json.JsonReference;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponse;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponseLabel;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guards the AWB / TrackingURL sourcing in {@code buildJsonDeliveryResponse}: label value wins (blank-treated-as-null),
 * the per-line package is the fallback, and label-to-package matching survives a blank package PkgNo via PkgCSID.
 */
class NShiftShipmentResponseMappingTest
{
	private static final int PKG_CSID = 812526418;
	private static final int TRACKING_URL_REFERENCE_KIND = 147;

	private static JsonDeliveryRequest requestWithSingleParcel()
	{
		final JsonMoney money = JsonMoney.builder().amount(BigDecimal.ONE).currencyCode("EUR").build();
		final JsonAddress address = JsonAddress.builder().companyName1("metas GmbH").city("Bonn").country("DE").zipCode("53179").street("Street").build();
		return JsonDeliveryRequest.builder()
				.pickupAddress(address)
				.deliveryAddress(address)
				.pickupDate("2026-01-01")
				.timeFrom("10:00:00")
				.timeTo("13:00:00")
				.shipperConfig(JsonShipperConfig.builder().url("u").username("u").password("p").build())
				.deliveryOrderParcel(JsonDeliveryOrderParcel.builder()
						.id("1")
						.grossWeightKg(BigDecimal.ONE)
						.packageId("1")
						.packageDimensions(JsonPackageDimensions.builder().lengthInCM(10).widthInCM(10).heightInCM(10).build())
						.contents(ImmutableList.of(JsonDeliveryOrderLineContents.builder()
								.shipmentOrderItemId("1")
								.unitPrice(money)
								.totalValue(money)
								.productName("Test Product")
								.productValue("Test Product")
								.totalWeightInKg(BigDecimal.ONE)
								.shippedQuantity(JsonQuantity.builder().value(BigDecimal.ONE).uomCode("PCE").build())
								.build()))
						.build())
				.build();
	}

	private static JsonShipmentResponse responseWith(
			@Nullable final String pkgNo,
			@Nullable final String pkgTrackingUrl,
			@Nullable final String labelPkgNo,
			@Nullable final String labelTrackingUrl)
	{
		final JsonPackage.JsonPackageBuilder pkg = JsonPackage.builder()
				.pkgCSID(PKG_CSID)
				.pkgNo(pkgNo);
		if (pkgTrackingUrl != null)
		{
			pkg.references(ImmutableList.of(JsonReference.builder().kind(TRACKING_URL_REFERENCE_KIND).value(pkgTrackingUrl).build()));
		}

		return JsonShipmentResponse.builder()
				.lines(ImmutableList.of(JsonLine.builder().number(1).pkg(pkg.build()).build()))
				.label(JsonShipmentResponseLabel.builder()
						.pkgCSID(PKG_CSID)
						.pkgNo(labelPkgNo)
						.trackingURL(labelTrackingUrl)
						.content("base64label")
						.build())
				.build();
	}

	@Test
	void labelValues_winOverPackage()
	{
		final JsonShipmentResponse response = responseWith(
				"PKG-LINE-AWB",
				"https://carrier/track?num=PKG-LINE",
				"1ZA30V130498352841",
				"https://www.ups.com/track?loc=de_DE&tracknum=1ZA30V130498352841");

		final JsonDeliveryResponse deliveryResponse = NShiftShipmentService.buildJsonDeliveryResponse(response, requestWithSingleParcel());

		final JsonDeliveryResponseItem item = deliveryResponse.getItems().get(0);
		assertThat(item.getAwb()).isEqualTo("1ZA30V130498352841");
		assertThat(item.getTrackingUrl()).isEqualTo("https://www.ups.com/track?loc=de_DE&tracknum=1ZA30V130498352841");
	}

	@Test
	void blankLabelValues_fallBackToPackage()
	{
		// Label value blank, and the package PkgNo is also blank — matching must still find the label by PkgCSID,
		// and both AWB and tracking URL fall back to the package (PkgNo + tracking-URL reference).
		final JsonShipmentResponse response = responseWith(
				"PKG-LINE-AWB",
				"https://carrier/track?num=PKG-LINE",
				"   ",
				"");

		final JsonDeliveryResponse deliveryResponse = NShiftShipmentService.buildJsonDeliveryResponse(response, requestWithSingleParcel());

		final JsonDeliveryResponseItem item = deliveryResponse.getItems().get(0);
		assertThat(item.getAwb()).isEqualTo("PKG-LINE-AWB");
		assertThat(item.getTrackingUrl()).isEqualTo("https://carrier/track?num=PKG-LINE");
	}

	@Test
	void absentLabel_fallsBackToPackage()
	{
		final JsonShipmentResponse response = JsonShipmentResponse.builder()
				.lines(ImmutableList.of(JsonLine.builder()
						.number(1)
						.pkg(JsonPackage.builder()
								.pkgCSID(PKG_CSID)
								.pkgNo("PKG-LINE-AWB")
								.references(ImmutableList.of(JsonReference.builder().kind(TRACKING_URL_REFERENCE_KIND).value("https://carrier/track?num=PKG-LINE").build()))
								.build())
						.build()))
				.build();

		final JsonDeliveryResponse deliveryResponse = NShiftShipmentService.buildJsonDeliveryResponse(response, requestWithSingleParcel());

		final JsonDeliveryResponseItem item = deliveryResponse.getItems().get(0);
		assertThat(item.getAwb()).isEqualTo("PKG-LINE-AWB");
		assertThat(item.getTrackingUrl()).isEqualTo("https://carrier/track?num=PKG-LINE");
		assertThat(item.getLabelPdfBase64()).isNull();
	}
}
