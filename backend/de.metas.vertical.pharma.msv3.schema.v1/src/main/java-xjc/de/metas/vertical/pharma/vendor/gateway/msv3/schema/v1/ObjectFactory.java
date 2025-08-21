
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
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

    private static final QName _AuthorizationFaultInfo_QNAME = new QName("urn:msv3:v1", "AuthorizationFaultInfo");
    private static final QName _ServerFaultInfo_QNAME = new QName("urn:msv3:v1", "ServerFaultInfo");
    private static final QName _ValidationFault_QNAME = new QName("urn:msv3:v1", "ValidationFault");
    private static final QName _DenialOfServiceFault_QNAME = new QName("urn:msv3:v1", "DenialOfServiceFault");
    private static final QName _BestellstatusAbfragen_QNAME = new QName("urn:msv3:v1", "bestellstatusAbfragen");
    private static final QName _BestellstatusAbfragenResponse_QNAME = new QName("urn:msv3:v1", "bestellstatusAbfragenResponse");
    private static final QName _VertragsdatenAbfragen_QNAME = new QName("urn:msv3:v1", "vertragsdatenAbfragen");
    private static final QName _VertragsdatenAbfragenResponse_QNAME = new QName("urn:msv3:v1", "vertragsdatenAbfragenResponse");
    private static final QName _Bestellen_QNAME = new QName("urn:msv3:v1", "bestellen");
    private static final QName _BestellenResponse_QNAME = new QName("urn:msv3:v1", "bestellenResponse");
    private static final QName _RuecknahmeangebotAnfordern_QNAME = new QName("urn:msv3:v1", "ruecknahmeangebotAnfordern");
    private static final QName _RuecknahmeangebotAnfordernResponse_QNAME = new QName("urn:msv3:v1", "ruecknahmeangebotAnfordernResponse");
    private static final QName _VerbindungTesten_QNAME = new QName("urn:msv3:v1", "verbindungTesten");
    private static final QName _VerbindungTestenResponse_QNAME = new QName("urn:msv3:v1", "verbindungTestenResponse");
    private static final QName _VerfuegbarkeitAnfragen_QNAME = new QName("urn:msv3:v1", "verfuegbarkeitAnfragen");
    private static final QName _VerfuegbarkeitAnfragenBulk_QNAME = new QName("urn:msv3:v1", "verfuegbarkeitAnfragenBulk");
    private static final QName _VerfuegbarkeitAnfragenBulkResponse_QNAME = new QName("urn:msv3:v1", "verfuegbarkeitAnfragenBulkResponse");
    private static final QName _VerfuegbarkeitAnfragenResponse_QNAME = new QName("urn:msv3:v1", "verfuegbarkeitAnfragenResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AuthorizationFaultInfo }
     * 
     * @return
     *     the new instance of {@link AuthorizationFaultInfo }
     */
    public AuthorizationFaultInfo createAuthorizationFaultInfo() {
        return new AuthorizationFaultInfo();
    }

    /**
     * Create an instance of {@link ServerFaultInfo }
     * 
     * @return
     *     the new instance of {@link ServerFaultInfo }
     */
    public ServerFaultInfo createServerFaultInfo() {
        return new ServerFaultInfo();
    }

    /**
     * Create an instance of {@link ValidationFaultInfo }
     * 
     * @return
     *     the new instance of {@link ValidationFaultInfo }
     */
    public ValidationFaultInfo createValidationFaultInfo() {
        return new ValidationFaultInfo();
    }

    /**
     * Create an instance of {@link DenialOfServiceFault }
     * 
     * @return
     *     the new instance of {@link DenialOfServiceFault }
     */
    public DenialOfServiceFault createDenialOfServiceFault() {
        return new DenialOfServiceFault();
    }

    /**
     * Create an instance of {@link BestellstatusAbfragen }
     * 
     * @return
     *     the new instance of {@link BestellstatusAbfragen }
     */
    public BestellstatusAbfragen createBestellstatusAbfragen() {
        return new BestellstatusAbfragen();
    }

    /**
     * Create an instance of {@link BestellstatusAbfragenResponse }
     * 
     * @return
     *     the new instance of {@link BestellstatusAbfragenResponse }
     */
    public BestellstatusAbfragenResponse createBestellstatusAbfragenResponse() {
        return new BestellstatusAbfragenResponse();
    }

    /**
     * Create an instance of {@link VertragsdatenAbfragen }
     * 
     * @return
     *     the new instance of {@link VertragsdatenAbfragen }
     */
    public VertragsdatenAbfragen createVertragsdatenAbfragen() {
        return new VertragsdatenAbfragen();
    }

    /**
     * Create an instance of {@link VertragsdatenAbfragenResponse }
     * 
     * @return
     *     the new instance of {@link VertragsdatenAbfragenResponse }
     */
    public VertragsdatenAbfragenResponse createVertragsdatenAbfragenResponse() {
        return new VertragsdatenAbfragenResponse();
    }

    /**
     * Create an instance of {@link Bestellen }
     * 
     * @return
     *     the new instance of {@link Bestellen }
     */
    public Bestellen createBestellen() {
        return new Bestellen();
    }

    /**
     * Create an instance of {@link BestellenResponse }
     * 
     * @return
     *     the new instance of {@link BestellenResponse }
     */
    public BestellenResponse createBestellenResponse() {
        return new BestellenResponse();
    }

    /**
     * Create an instance of {@link RuecknahmeangebotAnfordern }
     * 
     * @return
     *     the new instance of {@link RuecknahmeangebotAnfordern }
     */
    public RuecknahmeangebotAnfordern createRuecknahmeangebotAnfordern() {
        return new RuecknahmeangebotAnfordern();
    }

    /**
     * Create an instance of {@link RuecknahmeangebotAnfordernResponse }
     * 
     * @return
     *     the new instance of {@link RuecknahmeangebotAnfordernResponse }
     */
    public RuecknahmeangebotAnfordernResponse createRuecknahmeangebotAnfordernResponse() {
        return new RuecknahmeangebotAnfordernResponse();
    }

    /**
     * Create an instance of {@link VerbindungTesten }
     * 
     * @return
     *     the new instance of {@link VerbindungTesten }
     */
    public VerbindungTesten createVerbindungTesten() {
        return new VerbindungTesten();
    }

    /**
     * Create an instance of {@link VerbindungTestenResponse }
     * 
     * @return
     *     the new instance of {@link VerbindungTestenResponse }
     */
    public VerbindungTestenResponse createVerbindungTestenResponse() {
        return new VerbindungTestenResponse();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitAnfragen }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitAnfragen }
     */
    public VerfuegbarkeitAnfragen createVerfuegbarkeitAnfragen() {
        return new VerfuegbarkeitAnfragen();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitAnfragenBulk }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitAnfragenBulk }
     */
    public VerfuegbarkeitAnfragenBulk createVerfuegbarkeitAnfragenBulk() {
        return new VerfuegbarkeitAnfragenBulk();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitAnfragenBulkResponse }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitAnfragenBulkResponse }
     */
    public VerfuegbarkeitAnfragenBulkResponse createVerfuegbarkeitAnfragenBulkResponse() {
        return new VerfuegbarkeitAnfragenBulkResponse();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitAnfragenResponse }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitAnfragenResponse }
     */
    public VerfuegbarkeitAnfragenResponse createVerfuegbarkeitAnfragenResponse() {
        return new VerfuegbarkeitAnfragenResponse();
    }

    /**
     * Create an instance of {@link VertragsdatenAntwort }
     * 
     * @return
     *     the new instance of {@link VertragsdatenAntwort }
     */
    public VertragsdatenAntwort createVertragsdatenAntwort() {
        return new VertragsdatenAntwort();
    }

    /**
     * Create an instance of {@link Ruecknahmeangebot }
     * 
     * @return
     *     the new instance of {@link Ruecknahmeangebot }
     */
    public Ruecknahmeangebot createRuecknahmeangebot() {
        return new Ruecknahmeangebot();
    }

    /**
     * Create an instance of {@link RuecknahmeangebotAntwort }
     * 
     * @return
     *     the new instance of {@link RuecknahmeangebotAntwort }
     */
    public RuecknahmeangebotAntwort createRuecknahmeangebotAntwort() {
        return new RuecknahmeangebotAntwort();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitsanfrageEinzelne }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitsanfrageEinzelne }
     */
    public VerfuegbarkeitsanfrageEinzelne createVerfuegbarkeitsanfrageEinzelne() {
        return new VerfuegbarkeitsanfrageEinzelne();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitsanfrageEinzelneAntwort }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitsanfrageEinzelneAntwort }
     */
    public VerfuegbarkeitsanfrageEinzelneAntwort createVerfuegbarkeitsanfrageEinzelneAntwort() {
        return new VerfuegbarkeitsanfrageEinzelneAntwort();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitsanfrageBulk }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitsanfrageBulk }
     */
    public VerfuegbarkeitsanfrageBulk createVerfuegbarkeitsanfrageBulk() {
        return new VerfuegbarkeitsanfrageBulk();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitsanfrageBulkAntwort }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitsanfrageBulkAntwort }
     */
    public VerfuegbarkeitsanfrageBulkAntwort createVerfuegbarkeitsanfrageBulkAntwort() {
        return new VerfuegbarkeitsanfrageBulkAntwort();
    }

    /**
     * Create an instance of {@link Bestellung }
     * 
     * @return
     *     the new instance of {@link Bestellung }
     */
    public Bestellung createBestellung() {
        return new Bestellung();
    }

    /**
     * Create an instance of {@link BestellungAntwort }
     * 
     * @return
     *     the new instance of {@link BestellungAntwort }
     */
    public BestellungAntwort createBestellungAntwort() {
        return new BestellungAntwort();
    }

    /**
     * Create an instance of {@link BestellstatusAntwort }
     * 
     * @return
     *     the new instance of {@link BestellstatusAntwort }
     */
    public BestellstatusAntwort createBestellstatusAntwort() {
        return new BestellstatusAntwort();
    }

    /**
     * Create an instance of {@link VertragsdatenBestellfenster }
     * 
     * @return
     *     the new instance of {@link VertragsdatenBestellfenster }
     */
    public VertragsdatenBestellfenster createVertragsdatenBestellfenster() {
        return new VertragsdatenBestellfenster();
    }

    /**
     * Create an instance of {@link VertragsdatenHauptbestellzeit }
     * 
     * @return
     *     the new instance of {@link VertragsdatenHauptbestellzeit }
     */
    public VertragsdatenHauptbestellzeit createVertragsdatenHauptbestellzeit() {
        return new VertragsdatenHauptbestellzeit();
    }

    /**
     * Create an instance of {@link VertragsdatenAuftragsartNormal }
     * 
     * @return
     *     the new instance of {@link VertragsdatenAuftragsartNormal }
     */
    public VertragsdatenAuftragsartNormal createVertragsdatenAuftragsartNormal() {
        return new VertragsdatenAuftragsartNormal();
    }

    /**
     * Create an instance of {@link VertragsdatenAuftragsart }
     * 
     * @return
     *     the new instance of {@link VertragsdatenAuftragsart }
     */
    public VertragsdatenAuftragsart createVertragsdatenAuftragsart() {
        return new VertragsdatenAuftragsart();
    }

    /**
     * Create an instance of {@link VertragsdatenAuftragsartVersand }
     * 
     * @return
     *     the new instance of {@link VertragsdatenAuftragsartVersand }
     */
    public VertragsdatenAuftragsartVersand createVertragsdatenAuftragsartVersand() {
        return new VertragsdatenAuftragsartVersand();
    }

    /**
     * Create an instance of {@link BestellungAuftrag }
     * 
     * @return
     *     the new instance of {@link BestellungAuftrag }
     */
    public BestellungAuftrag createBestellungAuftrag() {
        return new BestellungAuftrag();
    }

    /**
     * Create an instance of {@link BestellungPosition }
     * 
     * @return
     *     the new instance of {@link BestellungPosition }
     */
    public BestellungPosition createBestellungPosition() {
        return new BestellungPosition();
    }

    /**
     * Create an instance of {@link BestellungAntwortAuftrag }
     * 
     * @return
     *     the new instance of {@link BestellungAntwortAuftrag }
     */
    public BestellungAntwortAuftrag createBestellungAntwortAuftrag() {
        return new BestellungAntwortAuftrag();
    }

    /**
     * Create an instance of {@link BestellungAntwortPosition }
     * 
     * @return
     *     the new instance of {@link BestellungAntwortPosition }
     */
    public BestellungAntwortPosition createBestellungAntwortPosition() {
        return new BestellungAntwortPosition();
    }

    /**
     * Create an instance of {@link BestellungAnteil }
     * 
     * @return
     *     the new instance of {@link BestellungAnteil }
     */
    public BestellungAnteil createBestellungAnteil() {
        return new BestellungAnteil();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitsantwortArtikel }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitsantwortArtikel }
     */
    public VerfuegbarkeitsantwortArtikel createVerfuegbarkeitsantwortArtikel() {
        return new VerfuegbarkeitsantwortArtikel();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitAnteil }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitAnteil }
     */
    public VerfuegbarkeitAnteil createVerfuegbarkeitAnteil() {
        return new VerfuegbarkeitAnteil();
    }

    /**
     * Create an instance of {@link ArtikelMenge }
     * 
     * @return
     *     the new instance of {@link ArtikelMenge }
     */
    public ArtikelMenge createArtikelMenge() {
        return new ArtikelMenge();
    }

    /**
     * Create an instance of {@link VerfuegbarkeitSubstitution }
     * 
     * @return
     *     the new instance of {@link VerfuegbarkeitSubstitution }
     */
    public VerfuegbarkeitSubstitution createVerfuegbarkeitSubstitution() {
        return new VerfuegbarkeitSubstitution();
    }

    /**
     * Create an instance of {@link BestellungSubstitution }
     * 
     * @return
     *     the new instance of {@link BestellungSubstitution }
     */
    public BestellungSubstitution createBestellungSubstitution() {
        return new BestellungSubstitution();
    }

    /**
     * Create an instance of {@link Msv3FaultInfo }
     * 
     * @return
     *     the new instance of {@link Msv3FaultInfo }
     */
    public Msv3FaultInfo createMsv3FaultInfo() {
        return new Msv3FaultInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorizationFaultInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthorizationFaultInfo }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "AuthorizationFaultInfo")
    public JAXBElement<AuthorizationFaultInfo> createAuthorizationFaultInfo(AuthorizationFaultInfo value) {
        return new JAXBElement<>(_AuthorizationFaultInfo_QNAME, AuthorizationFaultInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerFaultInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ServerFaultInfo }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "ServerFaultInfo")
    public JAXBElement<ServerFaultInfo> createServerFaultInfo(ServerFaultInfo value) {
        return new JAXBElement<>(_ServerFaultInfo_QNAME, ServerFaultInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidationFaultInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidationFaultInfo }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "ValidationFault")
    public JAXBElement<ValidationFaultInfo> createValidationFault(ValidationFaultInfo value) {
        return new JAXBElement<>(_ValidationFault_QNAME, ValidationFaultInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DenialOfServiceFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DenialOfServiceFault }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "DenialOfServiceFault")
    public JAXBElement<DenialOfServiceFault> createDenialOfServiceFault(DenialOfServiceFault value) {
        return new JAXBElement<>(_DenialOfServiceFault_QNAME, DenialOfServiceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BestellstatusAbfragen }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BestellstatusAbfragen }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "bestellstatusAbfragen")
    public JAXBElement<BestellstatusAbfragen> createBestellstatusAbfragen(BestellstatusAbfragen value) {
        return new JAXBElement<>(_BestellstatusAbfragen_QNAME, BestellstatusAbfragen.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BestellstatusAbfragenResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BestellstatusAbfragenResponse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "bestellstatusAbfragenResponse")
    public JAXBElement<BestellstatusAbfragenResponse> createBestellstatusAbfragenResponse(BestellstatusAbfragenResponse value) {
        return new JAXBElement<>(_BestellstatusAbfragenResponse_QNAME, BestellstatusAbfragenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VertragsdatenAbfragen }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VertragsdatenAbfragen }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "vertragsdatenAbfragen")
    public JAXBElement<VertragsdatenAbfragen> createVertragsdatenAbfragen(VertragsdatenAbfragen value) {
        return new JAXBElement<>(_VertragsdatenAbfragen_QNAME, VertragsdatenAbfragen.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VertragsdatenAbfragenResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VertragsdatenAbfragenResponse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "vertragsdatenAbfragenResponse")
    public JAXBElement<VertragsdatenAbfragenResponse> createVertragsdatenAbfragenResponse(VertragsdatenAbfragenResponse value) {
        return new JAXBElement<>(_VertragsdatenAbfragenResponse_QNAME, VertragsdatenAbfragenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Bestellen }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Bestellen }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "bestellen")
    public JAXBElement<Bestellen> createBestellen(Bestellen value) {
        return new JAXBElement<>(_Bestellen_QNAME, Bestellen.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BestellenResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BestellenResponse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "bestellenResponse")
    public JAXBElement<BestellenResponse> createBestellenResponse(BestellenResponse value) {
        return new JAXBElement<>(_BestellenResponse_QNAME, BestellenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RuecknahmeangebotAnfordern }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RuecknahmeangebotAnfordern }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "ruecknahmeangebotAnfordern")
    public JAXBElement<RuecknahmeangebotAnfordern> createRuecknahmeangebotAnfordern(RuecknahmeangebotAnfordern value) {
        return new JAXBElement<>(_RuecknahmeangebotAnfordern_QNAME, RuecknahmeangebotAnfordern.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RuecknahmeangebotAnfordernResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RuecknahmeangebotAnfordernResponse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "ruecknahmeangebotAnfordernResponse")
    public JAXBElement<RuecknahmeangebotAnfordernResponse> createRuecknahmeangebotAnfordernResponse(RuecknahmeangebotAnfordernResponse value) {
        return new JAXBElement<>(_RuecknahmeangebotAnfordernResponse_QNAME, RuecknahmeangebotAnfordernResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerbindungTesten }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VerbindungTesten }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "verbindungTesten")
    public JAXBElement<VerbindungTesten> createVerbindungTesten(VerbindungTesten value) {
        return new JAXBElement<>(_VerbindungTesten_QNAME, VerbindungTesten.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerbindungTestenResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VerbindungTestenResponse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "verbindungTestenResponse")
    public JAXBElement<VerbindungTestenResponse> createVerbindungTestenResponse(VerbindungTestenResponse value) {
        return new JAXBElement<>(_VerbindungTestenResponse_QNAME, VerbindungTestenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragen }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragen }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "verfuegbarkeitAnfragen")
    public JAXBElement<VerfuegbarkeitAnfragen> createVerfuegbarkeitAnfragen(VerfuegbarkeitAnfragen value) {
        return new JAXBElement<>(_VerfuegbarkeitAnfragen_QNAME, VerfuegbarkeitAnfragen.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragenBulk }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragenBulk }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "verfuegbarkeitAnfragenBulk")
    public JAXBElement<VerfuegbarkeitAnfragenBulk> createVerfuegbarkeitAnfragenBulk(VerfuegbarkeitAnfragenBulk value) {
        return new JAXBElement<>(_VerfuegbarkeitAnfragenBulk_QNAME, VerfuegbarkeitAnfragenBulk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragenBulkResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragenBulkResponse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "verfuegbarkeitAnfragenBulkResponse")
    public JAXBElement<VerfuegbarkeitAnfragenBulkResponse> createVerfuegbarkeitAnfragenBulkResponse(VerfuegbarkeitAnfragenBulkResponse value) {
        return new JAXBElement<>(_VerfuegbarkeitAnfragenBulkResponse_QNAME, VerfuegbarkeitAnfragenBulkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragenResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VerfuegbarkeitAnfragenResponse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:msv3:v1", name = "verfuegbarkeitAnfragenResponse")
    public JAXBElement<VerfuegbarkeitAnfragenResponse> createVerfuegbarkeitAnfragenResponse(VerfuegbarkeitAnfragenResponse value) {
        return new JAXBElement<>(_VerfuegbarkeitAnfragenResponse_QNAME, VerfuegbarkeitAnfragenResponse.class, null, value);
    }

}
