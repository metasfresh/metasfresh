package de.metas.cucumber.stepdefs.shipper;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.inoutcandidate.CarrierService;
import de.metas.shipper.gateway.nshift.client.ShipAdvisorService;
import de.metas.shipper.gateway.nshift.client.ShipmentDispatchService;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class NShiftGateway_StepDef
{
	@NonNull private final ShipAdvisorService shipAdvisorServiceMock = SpringContextHolder.instance.getBean(ShipAdvisorService.class);
	@NonNull private final ShipmentDispatchService shipmentDispatchServiceMock = SpringContextHolder.instance.getBean(ShipmentDispatchService.class);
	@NonNull private final Carrier_Product_StepDefData carrierProductTable;
	@NonNull private final Carrier_Goods_Type_StepDefData carrierGoodsTypeTable;
	@NonNull private final Carrier_Service_StepDefData carrierServiceTable;

	@Nullable private JsonDeliveryAdvisorRequest capturedAdvisorRequest;

	/**
	 * Holds the most recent {@link JsonDeliveryRequest} captured by the shipment service stub.
	 * Reset to {@code null} each time either shipment stub is set up
	 * ({@link #stubShipmentServiceWithSuccess()} or {@link #stubShipmentServiceWithSuccessWithoutTrackingUrl()}).
	 */
	@Nullable private JsonDeliveryRequest capturedShipmentRequest = null;

	/**
	 * Holds every {@link JsonDeliveryRequest} captured by the shipment service stub, in call order.
	 * One entry per nShift {@code createShipment} call — i.e. one per delivery order the gateway splits the
	 * packages into. Cleared each time either shipment stub is set up
	 * ({@link #stubShipmentServiceWithSuccess()} or {@link #stubShipmentServiceWithSuccessWithoutTrackingUrl()}).
	 */
	@NonNull private final List<JsonDeliveryRequest> capturedShipmentRequests = new ArrayList<>();

	/**
	 * Stubs the nShift ship advisor service so {@code advise(...)} returns a successful response built from the
	 * DataTable row, and captures the actual request for later assertion (see {@code capturedAdvisorRequest}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Carrier_Product_ID</b>     — (required, identifier-ref) carrier product returned as the advised shipper product<br>
	 *   <b>Carrier_Goods_Type_ID</b>  — (required, identifier-ref) goods type returned in the advise response<br>
	 *   <b>Carrier_Service_ID</b>     — (optional, identifier-ref) comma-separated carrier service(s) to include in the response
	 * @cucumber.depends StepDefData: Carrier_Product_StepDefData, Carrier_Goods_Type_StepDefData, Carrier_Service_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the nShift ship advisor service is stubbed to return a successful response based on the request
	 *   | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
	 *   | cp1                | cgt1                  | cs1                |
	 * </pre>
	 */
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

	/**
	 * Stubs the nShift shipment service to return a successful shipment creation response that carries a
	 * tracking URL for every parcel. See {@link #stubShipmentServiceWithSuccessWithoutTrackingUrl()} for the
	 * tracking-absent variant.
	 */
	@Given("the nShift shipment service is stubbed to return a successful shipment creation response")
	public void stubShipmentServiceWithSuccess()
	{
		stubShipmentServiceWithSuccess(true);
	}

	/**
	 * Same successful shipment stub, but the response carries NO tracking URL — the parcel is created without one,
	 * so the shipment-notification delay gate holds the mail workpackage until a tracking URL is set later.
	 */
	@Given("the nShift shipment service is stubbed to return a successful shipment creation response without tracking url")
	public void stubShipmentServiceWithSuccessWithoutTrackingUrl()
	{
		stubShipmentServiceWithSuccess(false);
	}

	private void stubShipmentServiceWithSuccess(final boolean includeTrackingUrl)
	{
		capturedShipmentRequest = null; // reset before each stub setup so stale captures don't leak between scenarios
		capturedShipmentRequests.clear();

		when(shipmentDispatchServiceMock.createShipment(any(JsonDeliveryRequest.class)))
				.thenAnswer((Answer<JsonDeliveryResponse>)invocation -> {
					final JsonDeliveryRequest actualRequest = invocation.getArgument(0);

					// Capture the request so it can be inspected via validateCapturedNShiftShipmentRequest()
					capturedShipmentRequest = actualRequest;
					capturedShipmentRequests.add(actualRequest);

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
								.trackingUrl(includeTrackingUrl ? "trackingUrl" + (i + 1) : null)
								.labelPdfBase64("JVBERi0xLjAKMSAwIG9iajw8L1R5cGUvQ2F0YWxvZy9QYWdlcyAyIDAgUj4+ZW5kb2JqCjIgMCBvYmo8PC9UeXBlL1BhZ2VzL0NvdW50IDAvS2lkc1tdPj5lbmRvYmoKeHJlZgowIDMKMDAwMDAwMDAwMCA2NTUzNSBmMDAwMDAwMDAxMCAwMDAwMCBuCjAwMDAwMDAwNTYgMDAwMDAgbgp0cmFpbGVyPDwvU2l6ZSAzL1Jvb3QgMSAwIFI+PgpzdGFydHhyZWYKMTAxCiUlRU9GCg==".getBytes(StandardCharsets.US_ASCII))
								.build()
						);
					}

					return builder.build();
				});
	}

	/**
	 * Asserts the {@link JsonDeliveryRequest} captured by the {@code ShipmentDispatchService} mock.
	 * Carrier product / goods type / the two services are required; address / contact / EORI /
	 * {@code IsPreAdviceRequired} / parcel columns are optional. {@code Parcel*} columns assume a single parcel.
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
		row.getAsOptionalString("IsPreAdviceRequired").ifPresent(expected -> softly
				.assertThat(capturedShipmentRequest.getPreAdviceRequired())
				.as("capturedShipmentRequest.preAdviceRequired").isEqualTo(expected));

		// --- pickup (sender) address + contact ---
		assertAddress(softly, capturedShipmentRequest.getPickupAddress(), row, "Sender", "pickupAddress");
		assertContact(softly, capturedShipmentRequest.getPickupContact(), row, "Sender", "pickupContact");

		// --- delivery (receiver) address + contact ---
		assertAddress(softly, capturedShipmentRequest.getDeliveryAddress(), row, "Receiver", "deliveryAddress");
		assertContact(softly, capturedShipmentRequest.getDeliveryContact(), row, "Receiver", "deliveryContact");

		softly.assertAll();
	}

	/**
	 * Asserts the set of nShift {@code createShipment} calls when the gateway splits the shipment's packages
	 * into more than one delivery order (one call per {@code DeliveryOrderKey}, i.e. per distinct carrier).
	 * The row count MUST equal the number of captured requests; each row is matched to a request by its
	 * carrier product ({@code shipperProduct.code}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Carrier_Product_ID</b>    — (required, identifier-ref) discriminator: the request's shipperProduct<br>
	 *   <b>Carrier_Goods_Type_ID</b> — (optional, identifier-ref) the request's goodsType<br>
	 *   <b>NumParcels</b>            — (optional) expected number of parcels in that request
	 * @cucumber.depends StepDefData: Carrier_Product_StepDefData, Carrier_Goods_Type_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate the captured nShift shipment requests:
	 *   | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
	 *   | cp1                | cgt1                  | 1          |
	 *   | cp2                | cgt2                  | 1          |
	 * </pre>
	 */
	@And("validate the captured nShift shipment requests:")
	public void validateCapturedNShiftShipmentRequests(@NonNull final DataTable dataTable)
	{
		assertThat(capturedShipmentRequests)
				.as("nShift shipment service call count")
				.hasSize(dataTable.height() - 1);

		DataTableRows.of(dataTable).forEach(row -> {
			final CarrierProduct expectedProduct = carrierProductTable.get(row.getAsIdentifier(I_Carrier_Product.COLUMNNAME_Carrier_Product_ID));

			final JsonDeliveryRequest request = capturedShipmentRequests.stream()
					.filter(req -> shipperProductEquals(expectedProduct, req.getShipperProduct()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("No captured nShift shipment request with shipperProduct.code=" + expectedProduct.getCode()));

			final SoftAssertions softly = new SoftAssertions();
			row.getAsOptionalIdentifier(I_Carrier_Goods_Type.COLUMNNAME_Carrier_Goods_Type_ID)
					.map(carrierGoodsTypeTable::get)
					.ifPresent(expectedGoodsType -> softly.assertThat(goodsTypeEquals(expectedGoodsType, request.getGoodsType()))
							.as("goodsType.id for carrier product %s (expected externalId=%s, actual id=%s)",
									expectedProduct.getCode(), expectedGoodsType.getExternalId(),
									request.getGoodsType() != null ? request.getGoodsType().getId() : null)
							.isTrue());
			row.getAsOptionalInt("NumParcels").ifPresent(expectedNumParcels -> softly
					.assertThat(request.getDeliveryOrderParcels().size())
					.as("number of parcels for carrier product %s", expectedProduct.getCode())
					.isEqualTo(expectedNumParcels));
			softly.assertAll();
		});
	}

	/**
	 * Null-safe equality between an expected {@link CarrierProduct} and the captured request's
	 * {@link JsonShipperProduct}, compared on their natural key (the shipper-product {@code code}).
	 */
	private static boolean shipperProductEquals(
			@Nullable final CarrierProduct expected,
			@Nullable final JsonShipperProduct actual)
	{
		return Objects.equals(
				expected != null ? expected.getCode() : null,
				actual != null ? actual.getCode() : null);
	}

	/**
	 * Null-safe equality between an expected {@link CarrierGoodsType} and the captured request's
	 * {@link JsonGoodsType}, compared on their natural key (the goods-type external id).
	 */
	private static boolean goodsTypeEquals(
			@Nullable final CarrierGoodsType expected,
			@Nullable final JsonGoodsType actual)
	{
		return Objects.equals(
				expected != null ? expected.getExternalId() : null,
				actual != null ? actual.getId() : null);
	}

	/**
	 * Asserts address, contact, and item fields on the most-recently-captured {@link JsonDeliveryAdvisorRequest}.
	 * All columns are optional. {@code Sender*} columns target the pickup address/contact;
	 * {@code Receiver*} columns target the delivery address/contact.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>SenderCompanyName</b>              — (optional) pickup address company name<br>
	 *   <b>SenderCountryCode</b>              — (optional) pickup address country code<br>
	 *   <b>ReceiverCompanyName</b>            — (optional) delivery address company name<br>
	 *   <b>ReceiverCompanyName2</b>           — (optional) delivery address company name 2<br>
	 *   <b>ReceiverStreet</b>                 — (optional) delivery address street<br>
	 *   <b>ReceiverAdditionalAddressInfo</b>  — (optional) delivery address additional info<br>
	 *   <b>ReceiverHouseNo</b>                — (optional) delivery address house number<br>
	 *   <b>ReceiverZip</b>                    — (optional) delivery address zip code<br>
	 *   <b>ReceiverCity</b>                   — (optional) delivery address city<br>
	 *   <b>ReceiverCountryCode</b>            — (optional) delivery address country code<br>
	 *   <b>ReceiverAttention</b>              — (optional) delivery address attention<br>
	 *   <b>ReceiverContactName</b>            — (optional) delivery contact name<br>
	 *   <b>ReceiverContactPhone</b>           — (optional) delivery contact phone<br>
	 *   <b>ReceiverContactEmail</b>           — (optional) delivery contact e-mail<br>
	 *   <b>IsPreAdviceRequired</b>            — (optional) expected request.preAdviceRequired<br>
	 *   <b>grossWeightKg</b>     — (optional) expected request.grossWeightKg (parcel-level, per-unit, rounded up — request-level, taken from the first row)<br>
	 *   <b>lengthInCM</b>        — (optional) expected request.packageDimensions.lengthInCM (parcel-level — request-level, taken from the first row)<br>
	 *   <b>widthInCM</b>         — (optional) expected request.packageDimensions.widthInCM (parcel-level — request-level, taken from the first row)<br>
	 *   <b>heightInCM</b>        — (optional) expected request.packageDimensions.heightInCM (parcel-level — request-level, taken from the first row)<br>
	 *   <b>productName</b>       — (optional, per-item discriminator) matches the item by its productName<br>
	 *   <b>productValue</b>      — (optional, per-item discriminator) matches the item by its productValue<br>
	 *   <b>numberOfItems</b>     — (optional) expected item.numberOfItems<br>
	 *   <b>unitPrice</b>         — (optional) expected item.unitPrice.amount<br>
	 *   <b>totalValue</b>        — (optional) expected item.totalValue.amount<br>
	 *   <b>shippedQuantity</b>   — (optional) expected item.shippedQuantity.value<br>
	 *   <b>customsTariff</b>     — (optional) expected item.customsTariff<br>
	 *   <b>totalWeightInKg</b>   — (optional) expected item.totalWeightInKg
	 *   <p>Each DataTable row is matched to exactly one advisor-request item; the row count MUST equal the number
	 *   of items. With a single item the row is matched by index; with multiple items each row is matched by its
	 *   {@code productName} / {@code productValue} discriminator. The request-level address / contact / parcel
	 *   columns are asserted once, from the first row.
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

		final List<JsonDeliveryAdvisorRequestItem> items = capturedAdvisorRequest.getItems();
		assertThat(items).as("advisor request items").isNotEmpty();

		final List<DataTableRow> rows = DataTableRows.of(dataTable).toList();

		// One row per item: validating every item (not just the first) is the whole point — a row count
		// that disagrees with the item count would silently leave items unasserted.
		assertThat(rows)
				.as("advisor request item count (one DataTable row per item)")
				.hasSameSizeAs(items);

		final SoftAssertions softly = new SoftAssertions();

		// --- request-level fields (address / contact / single per-unit parcel) — taken from the first row ---
		final DataTableRow firstRow = rows.get(0);
		assertAddress(softly, capturedAdvisorRequest.getPickupAddress(), firstRow, "Sender", "pickupAddress");
		assertContact(softly, capturedAdvisorRequest.getPickupContact(), firstRow, "Sender", "pickupContact");
		assertAddress(softly, capturedAdvisorRequest.getDeliveryAddress(), firstRow, "Receiver", "deliveryAddress");
		assertContact(softly, capturedAdvisorRequest.getDeliveryContact(), firstRow, "Receiver", "deliveryContact");

		firstRow.getAsOptionalBigDecimal("grossWeightKg").ifPresent(expected -> softly
				.assertThat(capturedAdvisorRequest.getGrossWeightKg())
				.as("grossWeightKg")
				.isEqualByComparingTo(expected));

		firstRow.getAsOptionalInt("lengthInCM").ifPresent(expected -> softly
				.assertThat(capturedAdvisorRequest.getPackageDimensions() != null
						? capturedAdvisorRequest.getPackageDimensions().getLengthInCM() : null)
				.as("packageDimensions.lengthInCM")
				.isEqualTo(expected));

		firstRow.getAsOptionalInt("widthInCM").ifPresent(expected -> softly
				.assertThat(capturedAdvisorRequest.getPackageDimensions() != null
						? capturedAdvisorRequest.getPackageDimensions().getWidthInCM() : null)
				.as("packageDimensions.widthInCM")
				.isEqualTo(expected));

		firstRow.getAsOptionalInt("heightInCM").ifPresent(expected -> softly
				.assertThat(capturedAdvisorRequest.getPackageDimensions() != null
						? capturedAdvisorRequest.getPackageDimensions().getHeightInCM() : null)
				.as("packageDimensions.heightInCM")
				.isEqualTo(expected));

		firstRow.getAsOptionalString("IsPreAdviceRequired").ifPresent(expected -> softly
				.assertThat(capturedAdvisorRequest.getPreAdviceRequired())
				.as("capturedAdvisorRequest.preAdviceRequired")
				.isEqualTo(expected));

		// --- per-item fields: match every row to its (distinct) item and assert ---
		// matchedItemIndexes guards against two rows resolving to the same item, which would otherwise
		// leave the other item(s) silently unasserted despite the row-count == item-count check above.
		final Set<Integer> matchedItemIndexes = new HashSet<>();
		for (int i = 0; i < rows.size(); i++)
		{
			final DataTableRow row = rows.get(i);
			final int itemIndex = matchAdvisorRequestItemIndex(row, items, i, matchedItemIndexes);
			matchedItemIndexes.add(itemIndex);
			assertAdvisorRequestItem(softly, row, items.get(itemIndex));
		}

		softly.assertAll();
	}

	/**
	 * Resolves the index of the advisor-request item a DataTable row refers to. With a single item, the row is
	 * matched by index (no discriminator needed). With multiple items, the row's {@code productName} /
	 * {@code productValue} discriminator must select exactly one not-yet-matched item — zero matches, multiple
	 * matches, or a missing discriminator all fail fast so no item is left silently unasserted.
	 */
	private static int matchAdvisorRequestItemIndex(
			@NonNull final DataTableRow row,
			@NonNull final List<JsonDeliveryAdvisorRequestItem> items,
			final int rowIndex,
			@NonNull final Set<Integer> alreadyMatchedItemIndexes)
	{
		if (items.size() == 1)
		{
			return 0;
		}

		final Optional<String> productName = row.getAsOptionalString("productName");
		final Optional<String> productValue = row.getAsOptionalString("productValue");
		if (!productName.isPresent() && !productValue.isPresent())
		{
			throw new AssertionError("row[" + rowIndex + "] must carry a productName/productValue discriminator "
					+ "to match one of the " + items.size() + " advisor request items");
		}

		final List<Integer> candidates = new ArrayList<>();
		for (int i = 0; i < items.size(); i++)
		{
			if (alreadyMatchedItemIndexes.contains(i))
			{
				continue;
			}
			final JsonDeliveryAdvisorRequestItem item = items.get(i);
			if (productName.isPresent() && !productName.get().equals(item.getProductName()))
			{
				continue;
			}
			if (productValue.isPresent() && !productValue.get().equals(item.getProductValue()))
			{
				continue;
			}
			candidates.add(i);
		}

		if (candidates.size() != 1)
		{
			throw new AssertionError("expected exactly one not-yet-matched advisor request item for row["
					+ rowIndex + "] ProductName=" + productName.orElse("<any>")
					+ ", ProductValue=" + productValue.orElse("<any>") + " but found " + candidates.size());
		}

		return candidates.get(0);
	}

	private static void assertAdvisorRequestItem(
			@NonNull final SoftAssertions softly,
			@NonNull final DataTableRow row,
			@NonNull final JsonDeliveryAdvisorRequestItem item)
	{
		row.getAsOptionalInt("numberOfItems").ifPresent(expected -> softly
				.assertThat(item.getNumberOfItems())
				.as("item.numberOfItems")
				.isEqualTo(expected));

		row.getAsOptionalBigDecimal("unitPrice").ifPresent(expected -> softly
				.assertThat(item.getUnitPrice() != null
						? item.getUnitPrice().getAmount() : null)
				.as("item.unitPrice.amount")
				.isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal("totalValue").ifPresent(expected -> softly
				.assertThat(item.getTotalValue() != null
						? item.getTotalValue().getAmount() : null)
				.as("item.totalValue.amount")
				.isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal("shippedQuantity").ifPresent(expected -> softly
				.assertThat(item.getShippedQuantity() != null
						? item.getShippedQuantity().getValue() : null)
				.as("item.shippedQuantity.value")
				.isEqualByComparingTo(expected));

		row.getAsOptionalString("customsTariff").ifPresent(expected -> softly
				.assertThat(item.getCustomsTariff())
				.as("item.customsTariff")
				.isEqualTo(expected));

		row.getAsOptionalBigDecimal("totalWeightInKg").ifPresent(expected -> softly
				.assertThat(item.getTotalWeightInKg())
				.as("item.totalWeightInKg")
				.isEqualByComparingTo(expected));
	}

	/**
	 * Asserts {@code UseShippingRules} and {@code ServiceLevel} in the shipper config of the
	 * most-recently-captured {@link de.metas.common.delivery.v1.json.request.JsonDeliveryRequest}.
	 * Both columns are optional; when present they assert the corresponding additional-property
	 * values set by the nShift gateway when {@code M_Shipper.IsApiCarrierAdvise='Y'} and the
	 * carrier-advising status is not {@code Manual}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>UseShippingRules</b> — (optional) expected boolean value (true/false/null-means-unset)<br>
	 *   <b>ServiceLevel</b>     — (optional) expected service-level string, e.g. "EXPRESS"
	 * @cucumber.example
	 * <pre>
	 * And validate the captured nShift shipment request options:
	 *   | UseShippingRules | ServiceLevel |
	 *   | true             | EXPRESS      |
	 * </pre>
	 */
	@And("validate the captured nShift shipment request options:")
	public void validateCapturedNShiftShipmentRequestOptions(@NonNull final DataTable dataTable)
	{
		assertThat(capturedShipmentRequest)
				.as("nShift shipment service was not called — make sure the delivery order creation scenario ran")
				.isNotNull();

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalString("IsManual").ifPresent(expected -> {
			final String actual = capturedShipmentRequest.getShipperConfig().getAdditionalProperty("IsManual");
			if (DataTableUtil.isNullPlaceholder(expected))
			{
				softly.assertThat(actual).as("shipperConfig.IsManual should be absent").isNull();
			}
			else
			{
				softly.assertThat(actual).as("shipperConfig.IsManual").isEqualTo(expected);
			}
		});

		row.getAsOptionalString("IsSelectionRules").ifPresent(expected -> {
			final String actual = capturedShipmentRequest.getShipperConfig().getAdditionalProperty("IsSelectionRules");
			if (DataTableUtil.isNullPlaceholder(expected))
			{
				softly.assertThat(actual).as("shipperConfig.IsSelectionRules should be absent").isNull();
			}
			else
			{
				softly.assertThat(actual).as("shipperConfig.IsSelectionRules").isEqualTo(expected);
			}
		});

		row.getAsOptionalString("ServiceLevel").ifPresent(expected -> {
			final String actual = capturedShipmentRequest.getShipperConfig().getAdditionalProperty("ServiceLevel");
			if (DataTableUtil.isNullPlaceholder(expected))
			{
				softly.assertThat(actual).as("shipperConfig.ServiceLevel should be absent").isNull();
			}
			else
			{
				softly.assertThat(actual).as("shipperConfig.ServiceLevel").isEqualTo(expected);
			}
		});

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
		when(shipmentDispatchServiceMock.createShipment(any(JsonDeliveryRequest.class)))
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