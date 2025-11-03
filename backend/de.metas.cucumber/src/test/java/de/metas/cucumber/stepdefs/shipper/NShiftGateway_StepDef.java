package de.metas.cucumber.stepdefs.shipper;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.shipper.client.nshift.NShiftShipAdvisorService;
import de.metas.shipper.client.nshift.NShiftShipmentService;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class NShiftGateway_StepDef
{

	@NonNull private final NShiftShipAdvisorService shipAdvisorServiceMock = Mockito.mock(NShiftShipAdvisorService.class);
	@NonNull private final NShiftShipmentService shipmentServiceMock = Mockito.mock(NShiftShipmentService.class);

	@Given("the nShift ship advisor service is stubbed to return a successful response based on the request")
	public void stubShipAdvisorServiceWithDynamicSuccess()
	{
		when(shipAdvisorServiceMock.advise(any(JsonDeliveryAdvisorRequest.class)))
				.thenAnswer((Answer<JsonDeliveryAdvisorResponse>)invocation -> {

					final JsonDeliveryAdvisorRequest actualRequest = invocation.getArgument(0);

					return JsonDeliveryAdvisorResponse.builder()
							.requestId(actualRequest.getId())
							.shipperProduct(JsonShipperProduct.builder()
									.code("code")
									.name("name")
									.build())
							.goodsType(JsonGoodsType.builder()
									.id("id")
									.name("name")
									.build())
							.shipperProductServices(ImmutableList.of(
									JsonCarrierService.builder()
											.id("id")
											.name("name")
											.build(),
									JsonCarrierService.builder()
											.id("id2")
											.name("name2")
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

					return JsonDeliveryResponse.builder()
							.requestId(actualRequest.getId())
							.build();
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