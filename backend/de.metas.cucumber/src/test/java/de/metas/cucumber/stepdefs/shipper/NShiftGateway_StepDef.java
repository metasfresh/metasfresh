package de.metas.cucumber.stepdefs.shipper;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponseItem;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.inoutcandidate.CarrierService;
import de.metas.shipper.client.nshift.NShiftShipAdvisorService;
import de.metas.shipper.client.nshift.NShiftShipmentService;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Goods_Type;
import org.compiere.model.I_Carrier_Product;
import org.compiere.model.I_Carrier_Service;
import org.mockito.stubbing.Answer;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class NShiftGateway_StepDef
{
	@NonNull private final NShiftShipAdvisorService shipAdvisorServiceMock = SpringContextHolder.instance.getBean(NShiftShipAdvisorService.class);
	@NonNull private final NShiftShipmentService shipmentServiceMock = SpringContextHolder.instance.getBean(NShiftShipmentService.class);
	@NonNull private final Carrier_Product_StepDefData carrierProductTable;
	@NonNull private final Carrier_Goods_Type_StepDefData carrierGoodsTypeTable;
	@NonNull private final Carrier_Service_StepDefData carrierServiceTable;

	/**
	 * Holds the most recent {@link JsonDeliveryRequest} captured by the shipment service stub.
	 * Reset to {@code null} each time {@link #stubShipmentServiceWithSuccess()} is called.
	 */
	@Nullable
	private JsonDeliveryRequest capturedShipmentRequest = null;

	@Given("the nShift ship advisor service is stubbed to return a successful response based on the request")
	public void stubShipAdvisorServiceWithDynamicSuccess(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final CarrierProduct carrierProduct = carrierProductTable.get(row.getAsIdentifier(I_Carrier_Product.COLUMNNAME_Carrier_Product_ID));
		final CarrierGoodsType carrierGoodsType = carrierGoodsTypeTable.get(row.getAsIdentifier(I_Carrier_Goods_Type.COLUMNNAME_Carrier_Goods_Type_ID));
		final CarrierService carrierService = carrierServiceTable.get(row.getAsIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID));
		final CarrierService carrierService2 = carrierServiceTable.get(row.getAsIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID + "2"));

		when(shipAdvisorServiceMock.advise(any(JsonDeliveryAdvisorRequest.class)))
				.thenAnswer((Answer<JsonDeliveryAdvisorResponse>)invocation -> {

					final JsonDeliveryAdvisorRequest actualRequest = invocation.getArgument(0);

					return JsonDeliveryAdvisorResponse.builder()
							.requestId(actualRequest.getId())
							.shipperProduct(JsonShipperProduct.builder()
									.code(carrierProduct.getCode())
									.name(carrierProduct.getName())
									.build())
							.goodsType(JsonGoodsType.builder()
									.id(carrierGoodsType.getExternalId())
									.name(carrierGoodsType.getName())
									.build())
							.shipperProductServices(ImmutableList.of(
									JsonCarrierService.builder()
											.id(carrierService.getExternalId())
											.name(carrierService.getName())
											.build(),
									JsonCarrierService.builder()
											.id(carrierService2.getExternalId())
											.name(carrierService2.getName())
											.build()
							))
							.build();
				});
	}

	@Given("the nShift ship advisor service is stubbed to return an error response based on the request")
	public void stubShipAdvisorServiceWithDynamicError()
	{
		when(shipAdvisorServiceMock.advise(any(JsonDeliveryAdvisorRequest.class)))
				.thenAnswer((Answer<JsonDeliveryAdvisorResponse>)invocation -> {

					final JsonDeliveryAdvisorRequest actualRequest = invocation.getArgument(0);

					return JsonDeliveryAdvisorResponse.builder()
							.requestId(actualRequest.getId())
							.errorMessage("response should only contain 1 shipperProduct, pls check defined shipment rules")
							.build();
				});
	}

	@Given("the nShift shipment service is stubbed to return a successful shipment creation response")
	public void stubShipmentServiceWithSuccess()
	{
		capturedShipmentRequest = null; // reset before each stub setup so stale captures don't leak between scenarios

		when(shipmentServiceMock.createShipment(any(JsonDeliveryRequest.class)))
				.thenAnswer((Answer<JsonDeliveryResponse>)invocation -> {
					final JsonDeliveryRequest actualRequest = invocation.getArgument(0);

					// Capture the request so it can be inspected via validateCapturedNShiftShipmentRequest()
					capturedShipmentRequest = actualRequest;

					final JsonDeliveryResponse.JsonDeliveryResponseBuilder builder = JsonDeliveryResponse.builder()
							.requestId(actualRequest.getId());

					for (final JsonDeliveryOrderParcel parcel : actualRequest.getDeliveryOrderParcels())
					{
						// labelPdfBase64 holds the ASCII bytes of a base64 string (the real flow uses
						// String.getBytes() of nShift's label.content); the gateway client base64-decodes it.
						builder.item(JsonDeliveryResponseItem.builder()
								.lineId(parcel.getId())
								.awb("awb")
								.trackingUrl("trackingUrl")
								.labelPdfBase64("JVBERi0xLjAKMSAwIG9iajw8L1R5cGUvQ2F0YWxvZy9QYWdlcyAyIDAgUj4+ZW5kb2JqCjIgMCBvYmo8PC9UeXBlL1BhZ2VzL0NvdW50IDAvS2lkc1tdPj5lbmRvYmoKeHJlZgowIDMKMDAwMDAwMDAwMCA2NTUzNSBmMDAwMDAwMDAxMCAwMDAwMCBuCjAwMDAwMDAwNTYgMDAwMDAgbgp0cmFpbGVyPDwvU2l6ZSAzL1Jvb3QgMSAwIFI+PgpzdGFydHhyZWYKMTAxCiUlRU9GCg==".getBytes(StandardCharsets.US_ASCII))
								.build()
						);
					}

					return builder.build();
				});
	}

	/**
	 * Asserts the {@link JsonDeliveryRequest} captured by the {@code NShiftShipmentService} mock.
	 * Carrier product / goods type / the two services are required; address / contact / EORI /
	 * parcel columns are optional. {@code Parcel*} columns assume a single parcel.
	 */
	@And("validate the captured nShift shipment request:")
	public void validateCapturedNShiftShipmentRequest(@NonNull final DataTable dataTable)
	{
		assertThat(capturedShipmentRequest)
				.as("nShift shipment service was not called — make sure the delivery order creation scenario ran with SKIP_WP_PROCESSOR_FOR_AUTOMATION=false")
				.isNotNull();

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final SoftAssertions softly = new SoftAssertions();

		// --- carrier product (shipperProduct.code) ---
		final CarrierProduct expectedProduct = carrierProductTable.get(row.getAsIdentifier(I_Carrier_Product.COLUMNNAME_Carrier_Product_ID));
		softly.assertThat(capturedShipmentRequest.getShipperProduct().getCode())
				.as("shipperProduct.code")
				.isEqualTo(expectedProduct.getCode());

		// --- goods type (goodsType.id) ---
		final CarrierGoodsType expectedGoodsType = carrierGoodsTypeTable.get(row.getAsIdentifier(I_Carrier_Goods_Type.COLUMNNAME_Carrier_Goods_Type_ID));
		softly.assertThat(capturedShipmentRequest.getGoodsType().getId())
				.as("goodsType.id")
				.isEqualTo(expectedGoodsType.getExternalId());

		// --- services ---
		final List<String> actualServiceIds = capturedShipmentRequest.getServices()
				.stream()
				.map(JsonCarrierService::getId)
				.collect(Collectors.toList());

		final CarrierService expectedService1 = carrierServiceTable.get(row.getAsIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID));
		softly.assertThat(actualServiceIds)
				.as("services must contain Carrier_Service_ID=%s (externalId=%s)", expectedService1.getId(), expectedService1.getExternalId())
				.contains(expectedService1.getExternalId());

		final CarrierService expectedService2 = carrierServiceTable.get(row.getAsIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID + "2"));
		softly.assertThat(actualServiceIds)
				.as("services must contain Carrier_Service_ID2=%s (externalId=%s)", expectedService2.getId(), expectedService2.getExternalId())
				.contains(expectedService2.getExternalId());

		// --- optional: request-level metadata ---
		row.getAsOptionalInt("NumParcels").ifPresent(expectedNumParcels -> softly
				.assertThat(capturedShipmentRequest.getDeliveryOrderParcels().size())
				.as("number of delivery order parcels")
				.isEqualTo(expectedNumParcels));
		row.getAsOptionalString("CustomerReference").ifPresent(expected -> softly
				.assertThat(capturedShipmentRequest.getCustomerReference())
				.as("customerReference").isEqualTo(expected));
		row.getAsOptionalString("ShipperEORI").ifPresent(expected -> softly
				.assertThat(capturedShipmentRequest.getShipperEORI())
				.as("shipperEORI").isEqualTo(expected));
		row.getAsOptionalString("ReceiverEORI").ifPresent(expected -> softly
				.assertThat(capturedShipmentRequest.getReceiverEORI())
				.as("receiverEORI").isEqualTo(expected));

		// --- pickup (sender) address + contact ---
		assertAddress(softly, capturedShipmentRequest.getPickupAddress(), row, "Sender", "pickupAddress");
		assertContact(softly, capturedShipmentRequest.getPickupContact(), row, "Sender", "pickupContact");

		// --- delivery (receiver) address + contact ---
		assertAddress(softly, capturedShipmentRequest.getDeliveryAddress(), row, "Receiver", "deliveryAddress");
		assertContact(softly, capturedShipmentRequest.getDeliveryContact(), row, "Receiver", "deliveryContact");

		// --- per-parcel (single-parcel case) ---
		final boolean hasParcelExpectations = row.getAsOptionalString("ParcelGrossWeightKg").isPresent()
				|| row.getAsOptionalString("ParcelLengthCm").isPresent()
				|| row.getAsOptionalString("ParcelWidthCm").isPresent()
				|| row.getAsOptionalString("ParcelHeightCm").isPresent();
		if (hasParcelExpectations)
		{
			final List<JsonDeliveryOrderParcel> parcels = capturedShipmentRequest.getDeliveryOrderParcels();
			softly.assertThat(parcels)
					.as("Parcel-level expectations require exactly one delivery order parcel")
					.hasSize(1);
			if (parcels.size() == 1)
			{
				final JsonDeliveryOrderParcel parcel = parcels.get(0);
				row.getAsOptionalBigDecimal("ParcelGrossWeightKg").ifPresent(expected -> softly
						.assertThat(parcel.getGrossWeightKg()).as("parcel.grossWeightKg").isEqualByComparingTo(expected));
				final JsonPackageDimensions dim = parcel.getPackageDimensions();
				row.getAsOptionalInt("ParcelLengthCm").ifPresent(expected -> softly
						.assertThat(dim.getLengthInCM()).as("parcel.packageDimensions.lengthInCM").isEqualTo(expected));
				row.getAsOptionalInt("ParcelWidthCm").ifPresent(expected -> softly
						.assertThat(dim.getWidthInCM()).as("parcel.packageDimensions.widthInCM").isEqualTo(expected));
				row.getAsOptionalInt("ParcelHeightCm").ifPresent(expected -> softly
						.assertThat(dim.getHeightInCM()).as("parcel.packageDimensions.heightInCM").isEqualTo(expected));
			}
		}

		softly.assertAll();
	}

	private static void assertAddress(
			@NonNull final SoftAssertions softly,
			@Nullable final JsonAddress actual,
			@NonNull final DataTableRow row,
			@NonNull final String columnPrefix,
			@NonNull final String label)
	{
		// If any of the address columns is supplied, the address must be present.
		final boolean any = row.getAsOptionalString(columnPrefix + "CompanyName").isPresent()
				|| row.getAsOptionalString(columnPrefix + "CompanyName2").isPresent()
				|| row.getAsOptionalString(columnPrefix + "Street").isPresent()
				|| row.getAsOptionalString(columnPrefix + "AdditionalAddressInfo").isPresent()
				|| row.getAsOptionalString(columnPrefix + "HouseNo").isPresent()
				|| row.getAsOptionalString(columnPrefix + "Zip").isPresent()
				|| row.getAsOptionalString(columnPrefix + "City").isPresent()
				|| row.getAsOptionalString(columnPrefix + "CountryCode").isPresent();
		if (!any)
		{
			return;
		}
		softly.assertThat(actual).as(label).isNotNull();
		if (actual == null)
		{
			return;
		}

		row.getAsOptionalString(columnPrefix + "CompanyName").ifPresent(expected -> softly
				.assertThat(actual.getCompanyName1()).as(label + ".companyName1").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "CompanyName2").ifPresent(expected -> softly
				.assertThat(actual.getCompanyName2()).as(label + ".companyName2").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "Street").ifPresent(expected -> softly
				.assertThat(actual.getStreet()).as(label + ".street").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "AdditionalAddressInfo").ifPresent(expected -> softly
				.assertThat(actual.getAdditionalAddressInfo()).as(label + ".additionalAddressInfo").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "HouseNo").ifPresent(expected -> softly
				.assertThat(actual.getHouseNo()).as(label + ".houseNo").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "Zip").ifPresent(expected -> softly
				.assertThat(actual.getZipCode()).as(label + ".zipCode").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "City").ifPresent(expected -> softly
				.assertThat(actual.getCity()).as(label + ".city").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "CountryCode").ifPresent(expected -> softly
				.assertThat(actual.getCountry()).as(label + ".country").isEqualTo(expected));
	}

	private static void assertContact(
			@NonNull final SoftAssertions softly,
			@Nullable final JsonContact actual,
			@NonNull final DataTableRow row,
			@NonNull final String columnPrefix,
			@NonNull final String label)
	{
		final boolean any = row.getAsOptionalString(columnPrefix + "ContactName").isPresent()
				|| row.getAsOptionalString(columnPrefix + "ContactPhone").isPresent()
				|| row.getAsOptionalString(columnPrefix + "ContactEmail").isPresent();
		if (!any)
		{
			return;
		}
		softly.assertThat(actual).as(label).isNotNull();
		if (actual == null)
		{
			return;
		}

		row.getAsOptionalString(columnPrefix + "ContactName").ifPresent(expected -> softly
				.assertThat(actual.getName()).as(label + ".name").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "ContactPhone").ifPresent(expected -> softly
				.assertThat(actual.getPhone()).as(label + ".phone").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "ContactEmail").ifPresent(expected -> softly
				.assertThat(actual.getEmailAddress()).as(label + ".emailAddress").isEqualTo(expected));
	}

	@Given("the nShift shipment service is stubbed to return an error on shipment creation")
	public void stubShipmentServiceWithError()
	{
		when(shipmentServiceMock.createShipment(any(JsonDeliveryRequest.class)))
				.thenAnswer((Answer<JsonDeliveryResponse>)invocation -> {
					final JsonDeliveryRequest actualRequest = invocation.getArgument(0);

					return JsonDeliveryResponse.builder()
							.requestId(actualRequest.getId())
							.errorMessage("Error")
							.build();
				});
	}

}