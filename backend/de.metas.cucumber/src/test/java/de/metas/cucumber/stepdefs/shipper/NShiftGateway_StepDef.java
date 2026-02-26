package de.metas.cucumber.stepdefs.shipper;

import com.google.common.collect.ImmutableList;
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
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Goods_Type;
import org.compiere.model.I_Carrier_Product;
import org.compiere.model.I_Carrier_Service;
import org.mockito.stubbing.Answer;

import java.util.Base64;

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
		when(shipmentServiceMock.createShipment(any(JsonDeliveryRequest.class)))
				.thenAnswer((Answer<JsonDeliveryResponse>)invocation -> {
					final JsonDeliveryRequest actualRequest = invocation.getArgument(0);

					final JsonDeliveryResponse.JsonDeliveryResponseBuilder builder = JsonDeliveryResponse.builder()
							.requestId(actualRequest.getId());

					for (final JsonDeliveryOrderParcel parcel : actualRequest.getDeliveryOrderParcels())
					{
						builder.item(JsonDeliveryResponseItem.builder()
								.lineId(parcel.getId())
								.awb("awb")
								.trackingUrl("trackingUrl")
								.labelPdfBase64(Base64.getDecoder().decode("JVBERi0xLjAKMSAwIG9iajw8L1R5cGUvQ2F0YWxvZy9QYWdlcyAyIDAgUj4+ZW5kb2JqCjIgMCBvYmo8PC9UeXBlL1BhZ2VzL0NvdW50IDAvS2lkc1tdPj5lbmRvYmoKeHJlZgowIDMKMDAwMDAwMDAwMCA2NTUzNSBmMDAwMDAwMDAxMCAwMDAwMCBuCjAwMDAwMDAwNTYgMDAwMDAgbgp0cmFpbGVyPDwvU2l6ZSAzL1Jvb3QgMSAwIFI+PgpzdGFydHhyZWYKMTAxCiUlRU9GCg=="))
								.build()
						);
					}

					return builder.build();
				});
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