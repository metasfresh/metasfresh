//import com.dpd.common.service.LoginService._2_0.LoginServiceProxy;
//import com.dpd.common.service.ShipmentService._3_2.ShipmentServicePublic_3_2Locator;
//import com.dpd.common.service.ShipmentService._3_2.ShipmentServicePublic_3_2_SOAPStub;
//import com.dpd.common.service.types.Authentication._2_0.AuthenticationFault;
//import com.dpd.common.service.types.LoginService._2_0.Login;
//import com.dpd.common.service.types.ShipmentService._3_2.Address;
//import com.dpd.common.service.types.ShipmentService._3_2.FaultCodeType;
//import com.dpd.common.service.types.ShipmentService._3_2.GeneralShipmentData;
//import com.dpd.common.service.types.ShipmentService._3_2.GeneralShipmentDataProduct;
//import com.dpd.common.service.types.ShipmentService._3_2.Notification;
//import com.dpd.common.service.types.ShipmentService._3_2.NotificationChannel;
//import com.dpd.common.service.types.ShipmentService._3_2.Parcel;
//import com.dpd.common.service.types.ShipmentService._3_2.ParcelInformationType;
//import com.dpd.common.service.types.ShipmentService._3_2.Pickup;
//import com.dpd.common.service.types.ShipmentService._3_2.PrintOptions;
//import com.dpd.common.service.types.ShipmentService._3_2.PrintOptionsPaperFormat;
//import com.dpd.common.service.types.ShipmentService._3_2.PrintOptionsPrinterLanguage;
//import com.dpd.common.service.types.ShipmentService._3_2.ProductAndServiceData;
//import com.dpd.common.service.types.ShipmentService._3_2.ProductAndServiceDataOrderType;
//import com.dpd.common.service.types.ShipmentService._3_2.ShipmentResponse;
//import com.dpd.common.service.types.ShipmentService._3_2.ShipmentServiceData;
//import com.dpd.common.service.types.ShipmentService._3_2.StartPosition;
//import com.dpd.common.service.types.ShipmentService._3_2.StoreOrdersResponseType;
//import com.dpd.common.service.types.shipmentservice._3.PrintOptions;
//import com.dpd.common.service.types.shipmentservice._3.ShipmentServiceData;
//import org.apache.axis.AxisFault;
//import org.apache.axis.Constants;
//import org.apache.axis.client.Stub;
//import org.apache.axis.encoding.Base64;
//import org.apache.axis.message.MessageElement;
//import org.apache.axis.message.SOAPHeaderElement;
//
//import javax.xml.soap.SOAPException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static com.oracle.jrockit.jfr.ContentType.Address;
//
//public class HURR {
//
//	public static void main(String[] args) throws SOAPException, IOException {
//		LoginServiceProxy myApiSoapClient = new LoginServiceProxy();
//		Login myLogin = new Login();
//
//		try {
//			myLogin = myApiSoapClient.getAuth("sandboxdpd", "xMmshh1", "en_EN");
//		} catch (RemoteException ex) {
//			if (ex instanceof AxisFault) {
//				if (ex instanceof AuthenticationFault) {
//					System.out.println("FAULT CODE: " + ((AuthenticationFault) ex).getErrorCode());
//					System.out.println("FAULT STRING: " + ((AuthenticationFault) ex).getErrorMessage());
//				}
//			}
//		}
//
//		// printOptions
//		PrintOptions myPrintOptions = new PrintOptions();
//		myPrintOptions.setPrinterLanguage(PrintOptionsPrinterLanguage.PDF);
//		myPrintOptions.setPaperFormat(PrintOptionsPaperFormat.A4);
//		myPrintOptions.setStartPosition(StartPosition.UPPER_LEFT);
//
//		// order (1-30)
//		ShipmentServiceData[] DPDOrderDataList = new ShipmentServiceData[1];
//		DPDOrderDataList[0] = new ShipmentServiceData();
//
//		// ----------- generalShipmentData
//		GeneralShipmentData myGeneralShipmentData = new GeneralShipmentData();
//		myGeneralShipmentData.setMpsCustomerReferenceNumber1("Article 123");
//		myGeneralShipmentData.setMpsCustomerReferenceNumber2("Order 456");
//		myGeneralShipmentData.setIdentificationNumber("Article 456");
//		myGeneralShipmentData.setSendingDepot("0112");
//		myGeneralShipmentData.setProduct(GeneralShipmentDataProduct.value1);
//		myGeneralShipmentData.setMpsWeight(Long.valueOf(500));
//
//		// ---------------------- sender
//		Address mySender = new Address();
//		mySender.setName1("metasfresh");
//		mySender.setName2("TheBestPessimist");
//		mySender.setStreet("Schröderstr.");
//		mySender.setHouseNo("66");
//		mySender.setState(null);
//		mySender.setCountry("DE");
//		mySender.setZipCode("10115");
//		mySender.setCity("Berlin, Mitte");
//		mySender.setCustomerNumber("");
//		mySender.setContact("");
//		mySender.setPhone("");
//		myGeneralShipmentData.setSender(mySender);
//
//		// ---------------------- recipient
//		Address myRecipient = new Address();
//		myRecipient.setName1("DPD Deutschland GmbH");
//		myRecipient.setName2("");
//		myRecipient.setStreet("Wailandtstr.");
//		myRecipient.setHouseNo("1");
//		myRecipient.setState(null);
//		myRecipient.setCountry("DE");
//		myRecipient.setZipCode("63741");
//		myRecipient.setCity("Aschaffenburg");
//		myRecipient.setCustomerNumber("");
//		myRecipient.setContact("");
//		myRecipient.setPhone("");
//		myGeneralShipmentData.setRecipient(myRecipient);
//
//		DPDOrderDataList[0].setGeneralShipmentData(myGeneralShipmentData);
//
//		// ----------- parcels
//		Parcel[] myParcelList = new Parcel[1];
//		Parcel myParcel = new Parcel();
//		myParcel.setCustomerReferenceNumber1("Article 123");
//		myParcel.setCustomerReferenceNumber2("Order 456");
//		myParcel.setWeight(500);
//		myParcel.setContent("Smartphones");
//		myParcelList[0] = myParcel;
//		DPDOrderDataList[0].setParcels(myParcelList);
//
//		// ----------- productAndServiceData
//		ProductAndServiceData myProductAndServiceData = new ProductAndServiceData();
//
//		myProductAndServiceData.setOrderType(ProductAndServiceDataOrderType.value1);
//
//		// ---------------------- pickup
//		Pickup myPickup = new Pickup();
//		myPickup.setTour(0);
//		myPickup.setQuantity(1);
//		myPickup.setDate(20191022);
//		myPickup.setDay(2);
//		myPickup.setFromTime1(1300);
//		myPickup.setToTime1(1700);
//		myPickup.setExtraPickup(true);
//
//		// --------------------------------- collectionrequestaddress
//		Address myCollectionRequestAddress = new Address();
//		myCollectionRequestAddress.setName1("metasfresh");
//		myCollectionRequestAddress.setName2("TheBestPessimist");
//		myCollectionRequestAddress.setStreet("Schröderstr.");
//		myCollectionRequestAddress.setHouseNo("66");
//		myCollectionRequestAddress.setState(null);
//		myCollectionRequestAddress.setCountry("DE");
//		myCollectionRequestAddress.setZipCode("10115");
//		myCollectionRequestAddress.setCity("Berlin, Mitte");
//		myCollectionRequestAddress.setCustomerNumber("");
//		myCollectionRequestAddress.setContact("");
//		myCollectionRequestAddress.setPhone("");
//		myPickup.setCollectionRequestAddress(myCollectionRequestAddress);
//
//		myProductAndServiceData.setPickup(myPickup);
//
//		// ---------------------- predict
//		Notification myNotification = new Notification();
//		myNotification.setChannel(NotificationChannel.value1);
//		myNotification.setValue("m.mustermann@dpd.de");
//		myNotification.setLanguage("DE");
//		myProductAndServiceData.setPredict(myNotification);
//
//		DPDOrderDataList[0].setProductAndServiceData(myProductAndServiceData);
//
//		// RESPONSE
//		try {
//			URL myEndpointURL = new URL("https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/");
//			ShipmentServicePublic_3_2Locator myShipmentService32Locator = new ShipmentServicePublic_3_2Locator();
//			ShipmentServicePublic_3_2_SOAPStub myShipmentService32Webservice = new ShipmentServicePublic_3_2_SOAPStub(
//					myEndpointURL, myShipmentService32Locator);
//			Stub myStub = (Stub) myShipmentService32Webservice;
//
//			SOAPHeaderElement header = new SOAPHeaderElement("http://dpd.com/common/service/types/Authentication/2.0",
//					"authentication") {
//				/**
//				 *
//				 */
//				private static final long serialVersionUID = 1L;
//
//				@Override
//				public void setAttribute(String namespace, String localName, String value) {
//					if (!Constants.ATTR_MUST_UNDERSTAND.equals(localName)) {
//						super.setAttribute(namespace, localName, value);
//					}
//				}
//			};
//
//			MessageElement myDelisIDElement = new MessageElement();
//			myDelisIDElement.setName("delisId");
//			myDelisIDElement.setValue("sandboxdpd");
//			header.addChild(myDelisIDElement);
//
//			MessageElement myAuthTokenElement = new MessageElement();
//			myAuthTokenElement.setName("authToken");
//			myAuthTokenElement.setValue(myLogin.getAuthToken());
//			header.addChild(myAuthTokenElement);
//
//			MessageElement myMessageLanguage = new MessageElement();
//			myMessageLanguage.setName("messageLanguage");
//			myMessageLanguage.setValue("de_DE");
//			header.addChild(myMessageLanguage);
//
//			header.setActor(null);
//			myStub.setHeader(header);
//
//			StoreOrdersResponseType myStoreOrdersResponse = new StoreOrdersResponseType();
//			myStoreOrdersResponse = myShipmentService32Webservice.storeOrders(myPrintOptions, DPDOrderDataList);
//
//			List<ShipmentResponse> myShipmentResponseList = new ArrayList<ShipmentResponse>();
//			myShipmentResponseList = Arrays.asList(myStoreOrdersResponse.getShipmentResponses());
//			if (myShipmentResponseList != null && myShipmentResponseList.size() > 0) {
//				for (ShipmentResponse item : myShipmentResponseList) {
//					if (item.getFaults() != null) {
//						// Error Handling
//						for (FaultCodeType myFault : item.getFaults()) {
//							System.out.println("FAULT CODE: " + myFault.getFaultCode());
//							System.out.println("FAULT STRING: " + myFault.getMessage() + "\n\n");
//						}
//					}
//					{
//						// Label PDF
//						if (myStoreOrdersResponse.getParcellabelsPDF() != null) {
//							String myBase64PDF = new String(Base64.encode(myStoreOrdersResponse.getParcellabelsPDF()));
//							System.out.println("Base64 PDF String: " + myBase64PDF + "\n\n");
//							byte[] pdf_bytes = myStoreOrdersResponse.getParcellabelsPDF();
//							FileOutputStream fos = new FileOutputStream(
//									"C://Users/.../Desktop/Test-PDF.pdf");
//							fos.write(pdf_bytes);
//							fos.close();
//						}
//
//						System.out.println("identificationNumber: " + item.getIdentificationNumber());
//						System.out.println("mpsId: " + item.getMpsId());
//						for (ParcelInformationType myParcelInformation : item.getParcelInformation()) {
//							System.out.println("parcelLabelNumber: " + myParcelInformation.getParcelLabelNumber());
//						}
//					}
//				}
//			}
//
//		} catch (RemoteException ex) {
//			System.out.println(ex.toString());
//			if (ex instanceof AxisFault) {
//				if (ex instanceof AuthenticationFault) {
//					System.out.println("FAULT CODE: " + ((AuthenticationFault) ex).getErrorCode());
//					System.out.println("FAULT STRING: " + ((AuthenticationFault) ex).getErrorMessage());
//				}
//			}
//		}
//	}
//}
