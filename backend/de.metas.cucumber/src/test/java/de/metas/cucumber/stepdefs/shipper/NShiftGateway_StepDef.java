package de.metas.cucumber.stepdefs.shipper;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents;
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
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_Product;
import org.compiere.model.I_Carrier_Service;
import org.mockito.stubbing.Answer;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	@Nullable private JsonDeliveryAdvisorRequest capturedAdvisorRequest;

	/**
	 * Holds the most recent {@link JsonDeliveryRequest} captured by the shipment service stub.
	 * Reset to {@code null} each time {@link #stubShipmentServiceWithSuccess()} is called.
	 */
	@Nullable private JsonDeliveryRequest capturedShipmentRequest = null;

	@Given("the nShift ship advisor service is stubbed to return a successful response based on the request")
	public void stubShipAdvisorServiceWithDynamicSuccess(@NonNull final DataTable dataTable)
	{
		capturedAdvisorRequest = null;

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final CarrierProduct carrierProduct = carrierProductTable.get(row.getAsIdentifier(I_Carrier_Product.COLUMNNAME_Carrier_Product_ID));
		final CarrierGoodsType carrierGoodsType = carrierGoodsTypeTable.get(row.getAsIdentifier(I_Carrier_Goods_Type.COLUMNNAME_Carrier_Goods_Type_ID));
		final ImmutableList.Builder<JsonCarrierService> shipperProductServicesBuilder = ImmutableList.builder();
		row.getAsOptionalIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID)
				.ifPresent(identifier -> identifier.toCommaSeparatedList().stream()
						.map(carrierServiceTable::get)
						.map(NShiftGateway_StepDef::toJsonCarrierService)
						.forEach(shipperProductServicesBuilder::add));

		when(shipAdvisorServiceMock.advise(any(JsonDeliveryAdvisorRequest.class)))
				.thenAnswer((Answer<JsonDeliveryAdvisorResponse>)invocation -> {

					final JsonDeliveryAdvisorRequest actualRequest = invocation.getArgument(0);
					capturedAdvisorRequest = actualRequest;

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
							.shipperProductServices(shipperProductServicesBuilder.build())
							.build();
				});
	}

	@Given("the nShift ship advisor service is stubbed to return an error response based on the request")
	public void stubShipAdvisorServiceWithDynamicError()
	{
		capturedAdvisorRequest = null;

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

					final List<JsonDeliveryOrderParcel> deliveryOrderParcels = actualRequest.getDeliveryOrderParcels();
					for (int i = 0; i < deliveryOrderParcels.size(); i++)
					{
						// labelPdfBase64 holds the ASCII bytes of a base64 string (the real flow uses
						// String.getBytes() of nShift's label.content); the gateway client base64-decodes it.
						builder.item(JsonDeliveryResponseItem.builder()
								.lineId(deliveryOrderParcels.get(i).getId())
								.awb("awb" + (i + 1))
								.trackingUrl("trackingUrl" + (i + 1))
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
	 * {@code ParcelItem_CountryOfOrigin} asserts the country of origin of the first item in the
	 * single parcel (only evaluated when {@code NumParcels} is absent or 1).
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

		// --- services (optional — omit columns when not the focus of the test) ---
		final List<String> actualServiceIds = capturedShipmentRequest.getServices().stream()
				.map(JsonCarrierService::getId)
				.collect(Collectors.toList());
		row.getAsOptionalIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID)
				.ifPresent(identifier -> identifier.toCommaSeparatedList().stream()
						.map(carrierServiceTable::get)
						.forEach(expected -> softly.assertThat(actualServiceIds)
								.as("services must contain %s", expected.getExternalId())
								.contains(expected.getExternalId())));

		// --- optional: request-level metadata ---
		row.getAsOptionalInt("NumParcels").ifPresent(expectedNumParcels -> softly
				.assertThat(capturedShipmentRequest.getDeliveryOrderParcels().size())
				.as("number of delivery order parcels")
				.isEqualTo(expectedNumParcels));
		row.getAsOptionalString(I_Carrier_ShipmentOrder.COLUMNNAME_CustomerReference).ifPresent(expected -> softly
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

		softly.assertAll();
	}

	/**
	 * Asserts address and contact fields on the most-recently-captured {@link JsonDeliveryAdvisorRequest}.
	 * All columns are optional. {@code Sender*} columns target the pickup address/contact;
	 * {@code Receiver*} columns target the delivery address/contact.
	 * Supported columns: {@code SenderCompanyName}, {@code SenderCountryCode}, {@code SenderAttention},
	 * {@code ReceiverCompanyName}, {@code ReceiverCompanyName2}, {@code ReceiverStreet},
	 * {@code ReceiverAdditionalAddressInfo}, {@code ReceiverHouseNo}, {@code ReceiverZip},
	 * {@code ReceiverCity}, {@code ReceiverCountryCode}, {@code ReceiverAttention},
	 * {@code ReceiverContactName}, {@code ReceiverContactPhone}, {@code ReceiverContactEmail}.
	 * <p>
	 * Note: attention reflects the raw value from {@code C_BPartner_Location.Attention} as carried
	 * in the {@link JsonDeliveryAdvisorRequest} — not the post-mapping concatenation produced by
	 * {@link de.metas.shipper.client.nshift.NShiftShipAdvisorService#buildRequest}, which is
	 * verified by {@code NShiftShipAdvisorServiceTest}.
	 */
	@And("validate the captured nShift advisor request:")
	public void validateCapturedNShiftAdvisorRequest(@NonNull final DataTable dataTable)
	{
		assertThat(capturedAdvisorRequest)
				.as("nShift ship advisor service was not called")
				.isNotNull();

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final SoftAssertions softly = new SoftAssertions();

		assertAddress(softly, capturedAdvisorRequest.getPickupAddress(), row, "Sender", "pickupAddress");
		assertContact(softly, capturedAdvisorRequest.getPickupContact(), row, "Sender", "pickupContact");
		assertAddress(softly, capturedAdvisorRequest.getDeliveryAddress(), row, "Receiver", "deliveryAddress");
		assertContact(softly, capturedAdvisorRequest.getDeliveryContact(), row, "Receiver", "deliveryContact");

		softly.assertAll();
	}

	/**
	 * Asserts the {@code ServiceLevel} custom property in the {@code shipperConfig} of the most-recently-captured nShift ship advisor request.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   (inline string parameter) — expected ServiceLevel value, e.g. "EXPRESS"<br>
	 * @cucumber.example
	 * <pre>
	 * Then the last nShift ship advisor request had shipperConfig serviceLevel "EXPRESS"
	 * </pre>
	 */
	@And("the last nShift ship advisor request had shipperConfig serviceLevel {string}")
	public void assertLastAdvisorRequestShipperConfigServiceLevel(@NonNull final String expectedServiceLevel)
	{
		assertThat(capturedAdvisorRequest)
				.as("nShift ship advisor service was not called")
				.isNotNull();
		assertThat(capturedAdvisorRequest.getShipperConfig().getAdditionalProperty("ServiceLevel"))
				.as("shipperConfig.ServiceLevel")
				.isEqualTo(expectedServiceLevel);
	}

	private static void assertAddress(
			@NonNull final SoftAssertions softly,
			@Nullable final JsonAddress actual,
			@NonNull final DataTableRow row,
			@NonNull final String columnPrefix,
			@NonNull final String label)
	{
		row.getAsOptionalString(columnPrefix + "CompanyName").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getCompanyName1() : null).as(label + ".companyName1").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "CompanyName2").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getCompanyName2() : null).as(label + ".companyName2").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "Street").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getStreet() : null).as(label + ".street").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "AdditionalAddressInfo").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getAdditionalAddressInfo() : null).as(label + ".additionalAddressInfo").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "HouseNo").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getHouseNo() : null).as(label + ".houseNo").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "Zip").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getZipCode() : null).as(label + ".zipCode").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "City").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getCity() : null).as(label + ".city").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "CountryCode").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getCountry() : null).as(label + ".country").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "Attention").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getAttention() : null).as(label + ".attention").isEqualTo(expected));
	}

	private static void assertContact(
			@NonNull final SoftAssertions softly,
			@Nullable final JsonContact actual,
			@NonNull final DataTableRow row,
			@NonNull final String columnPrefix,
			@NonNull final String label)
	{
		row.getAsOptionalString(columnPrefix + "ContactName").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getName() : null).as(label + ".name").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "ContactPhone").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getPhone() : null).as(label + ".phone").isEqualTo(expected));
		row.getAsOptionalString(columnPrefix + "ContactEmail").ifPresent(expected -> softly
				.assertThat(actual != null ? actual.getEmailAddress() : null).as(label + ".emailAddress").isEqualTo(expected));
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

	private static JsonCarrierService toJsonCarrierService(@NonNull final CarrierService carrierService)
	{
		return JsonCarrierService.builder()
				.id(carrierService.getExternalId())
				.name(carrierService.getName())
				.build();
	}


	/**
	 * Validates ALL content items from ALL parcels of the captured nShift shipment request,
	 * order-independent. Each DataTable row is matched by discriminating columns
	 * ({@code ProductName}, {@code CountryOfOrigin}); all other columns are assertions.
	 * The row count MUST equal the total content count across all parcels.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>productName</b>      — (optional) discriminator<br>
	 *   <b>countryOfOrigin</b>  — (optional) discriminator + assertion<br>
	 *   <b>shippedQuantity</b>  — (optional) assertion<br>
	 *   <b>unitPrice</b>        — (optional) assertion<br>
	 *   <b>totalValue</b>       — (optional) assertion<br>
	 *   <b>totalWeightInKg</b>  — (optional) assertion<br>
	 *   <b>customsTariff</b>    — (optional) assertion
	 * @cucumber.example
	 * <pre>
	 * And validate the captured nShift shipment request contents:
	 *   | ProductName    | CountryOfOrigin | QtyShipped | UnitPrice | TotalPrice | TotalWeightInKg | CustomsTariff |
	 *   | nShift Product | IT              | 7          | 10        | 70         | 14.7            | 12345678      |
	 *   | nShift Product | DE              | 13         | 10        | 130        | 27.3            | 12345678      |
	 * </pre>
	 */
	@And("validate the captured nShift shipment request contents:")
	public void validateCapturedNShiftShipmentRequestContents(@NonNull final DataTable dataTable)
	{
		assertThat(capturedShipmentRequest)
				.as("nShift shipment service was not called").isNotNull();

		final List<JsonDeliveryOrderLineContents> allContents = capturedShipmentRequest.getDeliveryOrderParcels()
				.stream()
				.flatMap(parcel -> parcel.getContents().stream())
				.collect(Collectors.toList());

		assertThat(allContents).as("total content items").hasSize(dataTable.height() - 1);

		DataTableRows.of(dataTable).forEach(row -> {
			Stream<JsonDeliveryOrderLineContents> filtered = allContents.stream();
			final Optional<String> productName = row.getAsOptionalString("productName");
			if (productName.isPresent())
			{
				filtered = filtered.filter(it -> productName.get().equals(it.getProductName()));
			}
			final Optional<String> countryOfOrigin = row.getAsOptionalString("countryOfOrigin");
			if (countryOfOrigin.isPresent())
			{
				filtered = filtered.filter(it -> countryOfOrigin.get().equals(it.getCountryOfOrigin()));
			}

			final JsonDeliveryOrderLineContents content = filtered.findFirst()
					.orElseThrow(() -> new AssertionError(
							"No content item found for ProductName=" + productName.orElse("<any>")
									+ ", CountryOfOrigin=" + countryOfOrigin.orElse("<any>")));

			final SoftAssertions softly = new SoftAssertions();
			row.getAsOptionalBigDecimal("shippedQuantity").ifPresent(expected -> softly
					.assertThat(content.getShippedQuantity().getValue()).as("shippedQty").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal("unitPrice").ifPresent(expected -> softly
					.assertThat(content.getUnitPrice().getAmount()).as("unitPrice").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal("totalValue").ifPresent(expected -> softly
					.assertThat(content.getTotalValue().getAmount()).as("totalPrice").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal("totalWeightInKg").ifPresent(expected -> softly
					.assertThat(content.getTotalWeightInKg()).as("totalWeightInKg").isEqualByComparingTo(expected));
			row.getAsOptionalString("customsTariff").ifPresent(expected -> softly
					.assertThat(content.getCustomsTariff()).as("customsTariff").isEqualTo(expected));
			row.getAsOptionalString("countryOfOrigin").ifPresent(expected -> softly
					.assertThat(content.getCountryOfOrigin()).as("countryOfOrigin").isEqualTo(expected));
			softly.assertAll();
		});
	}


	/**
	 * Validates ALL parcels of the captured nShift shipment request, order-independent.
	 * The row count MUST equal the total parcel count.
	 * Parcels are matched by available discriminating columns.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>grossWeightKg</b>  — (optional) discriminator + assertion<br>
	 *   <b>lengthInCM</b>     — (optional) assertion<br>
	 *   <b>widthInCM</b>      — (optional) assertion<br>
	 *   <b>heightInCM</b>     — (optional) assertion
	 */
	@And("validate the captured nShift shipment request parcels:")
	public void validateCapturedNShiftShipmentRequestParcels(@NonNull final DataTable dataTable)
	{
		assertThat(capturedShipmentRequest)
				.as("nShift shipment service was not called").isNotNull();

		final List<JsonDeliveryOrderParcel> allParcels = capturedShipmentRequest.getDeliveryOrderParcels();

		assertThat(allParcels).as("total parcels").hasSize(dataTable.height() - 1);

		DataTableRows.of(dataTable).forEach(row -> {
			Stream<JsonDeliveryOrderParcel> filtered = allParcels.stream();
			final Optional<java.math.BigDecimal> grossWeightKg = row.getAsOptionalBigDecimal("grossWeightKg");
			if (grossWeightKg.isPresent())
			{
				filtered = filtered.filter(it -> grossWeightKg.get().compareTo(it.getGrossWeightKg()) == 0);
			}

			final JsonDeliveryOrderParcel parcel = filtered.findFirst()
					.orElseThrow(() -> new AssertionError(
							"No parcel found for GrossWeightKg=" + grossWeightKg.orElse(null)));

			final SoftAssertions softly = new SoftAssertions();
						grossWeightKg.ifPresent(expected -> softly
					.assertThat(parcel.getGrossWeightKg()).as("grossWeightKg").isEqualByComparingTo(expected));
			row.getAsOptionalInt("lengthInCM").ifPresent(expected -> softly
					.assertThat(parcel.getPackageDimensions().getLengthInCM()).as("lengthInCM").isEqualTo(expected));
			row.getAsOptionalInt("widthInCM").ifPresent(expected -> softly
					.assertThat(parcel.getPackageDimensions().getWidthInCM()).as("widthInCM").isEqualTo(expected));
			row.getAsOptionalInt("heightInCM").ifPresent(expected -> softly
					.assertThat(parcel.getPackageDimensions().getHeightInCM()).as("heightInCM").isEqualTo(expected));
			softly.assertAll();
		});
	}

}