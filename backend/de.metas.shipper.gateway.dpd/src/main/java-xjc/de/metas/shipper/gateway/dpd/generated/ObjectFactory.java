
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.metas.shipper.gateway.dpd.generated package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _StoreOrders_QNAME = new QName("http://dpd.com/common/service/types/ShipmentService/3.2", "storeOrders");
    private static final QName _StoreOrdersResponse_QNAME = new QName("http://dpd.com/common/service/types/ShipmentService/3.2", "storeOrdersResponse");
    private static final QName _GetAuth_QNAME = new QName("http://dpd.com/common/service/types/LoginService/2.0", "getAuth");
    private static final QName _GetAuthResponse_QNAME = new QName("http://dpd.com/common/service/types/LoginService/2.0", "getAuthResponse");
    private static final QName _LoginException_QNAME = new QName("http://dpd.com/common/service/types/LoginService/2.0", "LoginException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.metas.shipper.gateway.dpd.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StoreOrders }
     * 
     * @return
     *     the new instance of {@link StoreOrders }
     */
    public StoreOrders createStoreOrders() {
        return new StoreOrders();
    }

    /**
     * Create an instance of {@link StoreOrdersResponse }
     * 
     * @return
     *     the new instance of {@link StoreOrdersResponse }
     */
    public StoreOrdersResponse createStoreOrdersResponse() {
        return new StoreOrdersResponse();
    }

    /**
     * Create an instance of {@link StoreOrdersResponseType }
     * 
     * @return
     *     the new instance of {@link StoreOrdersResponseType }
     */
    public StoreOrdersResponseType createStoreOrdersResponseType() {
        return new StoreOrdersResponseType();
    }

    /**
     * Create an instance of {@link ShipmentResponse }
     * 
     * @return
     *     the new instance of {@link ShipmentResponse }
     */
    public ShipmentResponse createShipmentResponse() {
        return new ShipmentResponse();
    }

    /**
     * Create an instance of {@link ShipmentServiceData }
     * 
     * @return
     *     the new instance of {@link ShipmentServiceData }
     */
    public ShipmentServiceData createShipmentServiceData() {
        return new ShipmentServiceData();
    }

    /**
     * Create an instance of {@link GeneralShipmentData }
     * 
     * @return
     *     the new instance of {@link GeneralShipmentData }
     */
    public GeneralShipmentData createGeneralShipmentData() {
        return new GeneralShipmentData();
    }

    /**
     * Create an instance of {@link Address }
     * 
     * @return
     *     the new instance of {@link Address }
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Parcel }
     * 
     * @return
     *     the new instance of {@link Parcel }
     */
    public Parcel createParcel() {
        return new Parcel();
    }

    /**
     * Create an instance of {@link ProductAndServiceData }
     * 
     * @return
     *     the new instance of {@link ProductAndServiceData }
     */
    public ProductAndServiceData createProductAndServiceData() {
        return new ProductAndServiceData();
    }

    /**
     * Create an instance of {@link International }
     * 
     * @return
     *     the new instance of {@link International }
     */
    public International createInternational() {
        return new International();
    }

    /**
     * Create an instance of {@link Delivery }
     * 
     * @return
     *     the new instance of {@link Delivery }
     */
    public Delivery createDelivery() {
        return new Delivery();
    }

    /**
     * Create an instance of {@link ProactiveNotification }
     * 
     * @return
     *     the new instance of {@link ProactiveNotification }
     */
    public ProactiveNotification createProactiveNotification() {
        return new ProactiveNotification();
    }

    /**
     * Create an instance of {@link Notification }
     * 
     * @return
     *     the new instance of {@link Notification }
     */
    public Notification createNotification() {
        return new Notification();
    }

    /**
     * Create an instance of {@link Cod }
     * 
     * @return
     *     the new instance of {@link Cod }
     */
    public Cod createCod() {
        return new Cod();
    }

    /**
     * Create an instance of {@link Hazardous }
     * 
     * @return
     *     the new instance of {@link Hazardous }
     */
    public Hazardous createHazardous() {
        return new Hazardous();
    }

    /**
     * Create an instance of {@link PersonalDelivery }
     * 
     * @return
     *     the new instance of {@link PersonalDelivery }
     */
    public PersonalDelivery createPersonalDelivery() {
        return new PersonalDelivery();
    }

    /**
     * Create an instance of {@link Pickup }
     * 
     * @return
     *     the new instance of {@link Pickup }
     */
    public Pickup createPickup() {
        return new Pickup();
    }

    /**
     * Create an instance of {@link ParcelInformationType }
     * 
     * @return
     *     the new instance of {@link ParcelInformationType }
     */
    public ParcelInformationType createParcelInformationType() {
        return new ParcelInformationType();
    }

    /**
     * Create an instance of {@link FaultCodeType }
     * 
     * @return
     *     the new instance of {@link FaultCodeType }
     */
    public FaultCodeType createFaultCodeType() {
        return new FaultCodeType();
    }

    /**
     * Create an instance of {@link HigherInsurance }
     * 
     * @return
     *     the new instance of {@link HigherInsurance }
     */
    public HigherInsurance createHigherInsurance() {
        return new HigherInsurance();
    }

    /**
     * Create an instance of {@link ParcelShopDelivery }
     * 
     * @return
     *     the new instance of {@link ParcelShopDelivery }
     */
    public ParcelShopDelivery createParcelShopDelivery() {
        return new ParcelShopDelivery();
    }

    /**
     * Create an instance of {@link PrintOptions }
     * 
     * @return
     *     the new instance of {@link PrintOptions }
     */
    public PrintOptions createPrintOptions() {
        return new PrintOptions();
    }

    /**
     * Create an instance of {@link Printer }
     * 
     * @return
     *     the new instance of {@link Printer }
     */
    public Printer createPrinter() {
        return new Printer();
    }

    /**
     * Create an instance of {@link Authentication }
     * 
     * @return
     *     the new instance of {@link Authentication }
     */
    public Authentication createAuthentication() {
        return new Authentication();
    }

    /**
     * Create an instance of {@link AuthenticationFault }
     * 
     * @return
     *     the new instance of {@link AuthenticationFault }
     */
    public AuthenticationFault createAuthenticationFault() {
        return new AuthenticationFault();
    }

    /**
     * Create an instance of {@link GetAuth }
     * 
     * @return
     *     the new instance of {@link GetAuth }
     */
    public GetAuth createGetAuth() {
        return new GetAuth();
    }

    /**
     * Create an instance of {@link GetAuthResponse }
     * 
     * @return
     *     the new instance of {@link GetAuthResponse }
     */
    public GetAuthResponse createGetAuthResponse() {
        return new GetAuthResponse();
    }

    /**
     * Create an instance of {@link LoginException }
     * 
     * @return
     *     the new instance of {@link LoginException }
     */
    public LoginException createLoginException() {
        return new LoginException();
    }

    /**
     * Create an instance of {@link Login }
     * 
     * @return
     *     the new instance of {@link Login }
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreOrders }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StoreOrders }{@code >}
     */
    @XmlElementDecl(namespace = "http://dpd.com/common/service/types/ShipmentService/3.2", name = "storeOrders")
    public JAXBElement<StoreOrders> createStoreOrders(StoreOrders value) {
        return new JAXBElement<>(_StoreOrders_QNAME, StoreOrders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreOrdersResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StoreOrdersResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://dpd.com/common/service/types/ShipmentService/3.2", name = "storeOrdersResponse")
    public JAXBElement<StoreOrdersResponse> createStoreOrdersResponse(StoreOrdersResponse value) {
        return new JAXBElement<>(_StoreOrdersResponse_QNAME, StoreOrdersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuth }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAuth }{@code >}
     */
    @XmlElementDecl(namespace = "http://dpd.com/common/service/types/LoginService/2.0", name = "getAuth")
    public JAXBElement<GetAuth> createGetAuth(GetAuth value) {
        return new JAXBElement<>(_GetAuth_QNAME, GetAuth.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAuthResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://dpd.com/common/service/types/LoginService/2.0", name = "getAuthResponse")
    public JAXBElement<GetAuthResponse> createGetAuthResponse(GetAuthResponse value) {
        return new JAXBElement<>(_GetAuthResponse_QNAME, GetAuthResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LoginException }{@code >}
     */
    @XmlElementDecl(namespace = "http://dpd.com/common/service/types/LoginService/2.0", name = "LoginException")
    public JAXBElement<LoginException> createLoginException(LoginException value) {
        return new JAXBElement<>(_LoginException_QNAME, LoginException.class, null, value);
    }

}
