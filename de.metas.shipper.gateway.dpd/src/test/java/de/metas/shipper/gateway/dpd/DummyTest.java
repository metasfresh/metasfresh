package de.metas.shipper.gateway.dpd;

import com.dpd.common.service.types.shipmentservice._3.Address;
import com.dpd.common.service.types.shipmentservice._3.GeneralShipmentData;
import com.dpd.common.service.types.shipmentservice._3.Parcel;
import com.dpd.common.service.types.shipmentservice._3.Pickup;
import com.dpd.common.service.types.shipmentservice._3.PrintOptions;
import com.dpd.common.service.types.shipmentservice._3.ProductAndServiceData;
import com.dpd.common.service.types.shipmentservice._3.ShipmentResponse;
import com.dpd.common.service.types.shipmentservice._3.ShipmentServiceData;
import com.dpd.common.service.types.shipmentservice._3.StoreOrders;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponse;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponseType;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuth;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuthResponse;
import com.dpd.common.ws.loginservice.v2_0.types.Login;
import de.metas.shipper.gateway.dpd.model.DpdServiceType;
import de.metas.shipper.gateway.dpd.util.DpdClientUtil;
import de.metas.shipper.gateway.dpd.util.DpdSoapHeaderWithAuth;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

import static de.metas.shipper.gateway.dpd.util.DpdClientUtil.LOGIN_SERVICE_API_URL;
import static de.metas.shipper.gateway.dpd.util.DpdClientUtil.SHIPMENT_SERVICE_API_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DummyTest
{
	private static final String DELIS_ID = "sandboxdpd";
	private static final String DELIS_PASSWORD = "a";
	private static final String MESSAGE_LANGUAGE = "en_EN";

	private WebServiceTemplate webServiceTemplate;
	private com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory loginServiceOF;
	private com.dpd.common.service.types.shipmentservice._3.ObjectFactory shipmentServiceOF;

	@BeforeEach
	private void beforeEach()
	{
		webServiceTemplate = DpdClientUtil.createWebServiceTemplate();
		loginServiceOF = new com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory();
		shipmentServiceOF = new com.dpd.common.service.types.shipmentservice._3.ObjectFactory();
	}

	@Test
	void _1_testLogin()
	{

		final GetAuth getAuthValue = loginServiceOF.createGetAuth();
		getAuthValue.setDelisId(DELIS_ID);
		getAuthValue.setPassword(DELIS_PASSWORD);
		getAuthValue.setMessageLanguage(DpdConstants.DEFAULT_MESSAGE_LANGUAGE);

		final JAXBElement<GetAuth> getAuth = loginServiceOF.createGetAuth(getAuthValue);

		//noinspection unchecked
		final JAXBElement<GetAuthResponse> authenticationElement = (JAXBElement<GetAuthResponse>)webServiceTemplate.marshalSendAndReceive(LOGIN_SERVICE_API_URL, getAuth);

		final Login login = authenticationElement.getValue().getReturn();

		assertTrue(StringUtils.isNotBlank(login.getAuthToken()));
		assertTrue(StringUtils.isNotBlank(login.getDepot()));
		assertEquals(login.getDelisId(), DELIS_ID);
		assertEquals(login.getCustomerUid(), DELIS_ID);
	}

	@Test
	void _2_createOrder()
	{
		final Login login;
		{
			// Login
			final GetAuth getAuthValue = loginServiceOF.createGetAuth();
			getAuthValue.setDelisId(DELIS_ID);
			getAuthValue.setPassword(DELIS_PASSWORD);
			getAuthValue.setMessageLanguage(DpdConstants.DEFAULT_MESSAGE_LANGUAGE);

			final JAXBElement<GetAuth> getAuth = loginServiceOF.createGetAuth(getAuthValue);
			//noinspection unchecked
			final JAXBElement<GetAuthResponse> authenticationElement = (JAXBElement<GetAuthResponse>)webServiceTemplate.marshalSendAndReceive(LOGIN_SERVICE_API_URL, getAuth);
			login = authenticationElement.getValue().getReturn();

		}

		final StoreOrders storeOrders;
		{
			// Create the Delivery Order
			storeOrders = shipmentServiceOF.createStoreOrders();
			{
				// Print Options
				final PrintOptions printOptions = shipmentServiceOF.createPrintOptions();
				printOptions.setPaperFormat("A4");
				printOptions.setPrinterLanguage("PDF");
				storeOrders.setPrintOptions(printOptions);
			}
			{
				// Shipment Data 1
				final ShipmentServiceData shipmentServiceData = shipmentServiceOF.createShipmentServiceData();
				storeOrders.getOrder().add(shipmentServiceData);

				{
					// General Shipment Data
					final GeneralShipmentData generalShipmentData = shipmentServiceOF.createGeneralShipmentData();
					shipmentServiceData.setGeneralShipmentData(generalShipmentData);
					//					generalShipmentData.setMpsCustomerReferenceNumber1("Article 123"); // what is this? optional?
					//					generalShipmentData.setMpsCustomerReferenceNumber2("Order 456"); // what is this? optional?
					generalShipmentData.setIdentificationNumber("Article 456"); // what is this? optional?
					generalShipmentData.setSendingDepot("0112"); // mandatory? (taken from login)
					generalShipmentData.setProduct(DpdServiceType.DPD_CLASSIC.getCode()); // this is the DPD product
					//					generalShipmentData.setMpsWeight(500L); // optional? uom = decagram (1dag = 10g => 100dag = 1kg)

					{
						// Sender
						// the sender is hard linked with the location provided to dpd when the account was created.
						// 		it is not connected though with the pickup address! as in the pickup section there's the "CollectionRequestAddress".
						final Address sender = shipmentServiceOF.createAddress();
						generalShipmentData.setSender(sender);
						sender.setName1("metasfresh");
						sender.setName2("TheBestPessimist");
						sender.setStreet("Schröderstr.");
						sender.setHouseNo("66");
						sender.setCountry("DE");
						sender.setZipCode("10115");
						sender.setCity("Berlin, Mitte");
						//						sender.setCustomerNumber("");
						//						sender.setContact("");
						//						sender.setPhone("");
					}
					{
						// Recipient
						final Address recipient = shipmentServiceOF.createAddress();
						generalShipmentData.setRecipient(recipient);
						recipient.setName1("DPD Deutschland GmbH");
						recipient.setName2("");
						recipient.setStreet("Wailandtstr.");
						recipient.setHouseNo("1");
						recipient.setCountry("DE");
						recipient.setZipCode("63741");
						recipient.setCity("Aschaffenburg");
						//						recipient.setCustomerNumber("");
						//						recipient.setContact("");
						//						recipient.setPhone("");
					}
				}

				{
					// Parcel 1
					final Parcel parcel = shipmentServiceOF.createParcel();
					shipmentServiceData.getParcels().add(parcel);
					//					parcel.setCustomerReferenceNumber1("Article 123");    // what is this? optional?
					//					parcel.setCustomerReferenceNumber2("Order 456");    // what is this? optional?
					//					parcel.setWeight(500);    // what is this? optional? uom = decagram (1dag = 10g => 100dag = 1kg)
					parcel.setContent("Smartphones");    // what is this? optional?
				}

				{
					// ProductAndService Data
					final ProductAndServiceData productAndServiceData = shipmentServiceOF.createProductAndServiceData();
					shipmentServiceData.setProductAndServiceData(productAndServiceData);
					{
						// Shipper Product
						productAndServiceData.setOrderType("consignment"); // this is somehow related to CL
					}
					{
						// Pickup
						final Pickup pickup = shipmentServiceOF.createPickup();
						productAndServiceData.setPickup(pickup);
						pickup.setTour(0);
						pickup.setQuantity(1);
						pickup.setDate(20191029);
						pickup.setDay(2);
						pickup.setFromTime1(1300);
						pickup.setToTime1(1700);
						pickup.setExtraPickup(true);

						// Collection Request Address
						final Address pickupAddress = shipmentServiceOF.createAddress();
						pickup.setCollectionRequestAddress(pickupAddress);
						pickupAddress.setName1("metasfresh");
						pickupAddress.setName2("TheBestPessimist");
						pickupAddress.setStreet("Schröderstr.");
						pickupAddress.setHouseNo("66");
						//noinspection ConstantConditions
						pickupAddress.setState(null);
						pickupAddress.setCountry("DE");
						pickupAddress.setZipCode("10115");
						pickupAddress.setCity("Berlin, Mitte");
						pickupAddress.setCustomerNumber("");
						pickupAddress.setContact("");
						pickupAddress.setPhone("");
					}
				}
			}
		}
		{
			// Call the Shipment API
			final JAXBElement<StoreOrders> storeOrdersElement = shipmentServiceOF.createStoreOrders(storeOrders);
			//noinspection unchecked
			final JAXBElement<StoreOrdersResponse> storeOrdersResponseElement =
					(JAXBElement<StoreOrdersResponse>)webServiceTemplate.marshalSendAndReceive(SHIPMENT_SERVICE_API_URL, storeOrdersElement, new DpdSoapHeaderWithAuth(login));

			final StoreOrdersResponseType orderResult = storeOrdersResponseElement.getValue().getOrderResult();
			assertTrue(orderResult.getParcellabelsPDF().length > 2);
			assertEquals(1, orderResult.getShipmentResponses().size());
			final ShipmentResponse shipmentResponse = orderResult.getShipmentResponses().get(0);
			assertEquals("Article 456", shipmentResponse.getIdentificationNumber());
			assertTrue(shipmentResponse.getMpsId().startsWith("MPS"));

			DpdTestHelper.dumpPdfToDisk(orderResult.getParcellabelsPDF());
		}
	}
}
