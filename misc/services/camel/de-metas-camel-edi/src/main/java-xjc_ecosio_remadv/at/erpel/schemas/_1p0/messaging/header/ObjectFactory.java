
package at.erpel.schemas._1p0.messaging.header;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.erpel.schemas._1p0.messaging.header package. 
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

    private final static QName _ErpelBusinessDocumentHeader_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "ErpelBusinessDocumentHeader");
    private final static QName _CertificateCheck_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "CertificateCheck");
    private final static QName _InterchangeHeader_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "InterchangeHeader");
    private final static QName _MessageReceivedAt_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "MessageReceivedAt");
    private final static QName _Receiver_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "Receiver");
    private final static QName _Sender_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "Sender");
    private final static QName _SignatureCheck_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "SignatureCheck");
    private final static QName _SignatureManifestCheck_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "SignatureManifestCheck");
    private final static QName _SignatureVerificationResult_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "SignatureVerificationResult");
    private final static QName _SignatureVerification_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "SignatureVerification");
    private final static QName _SignatureVerificationOmittedErrorCode_QNAME = new QName("http://erpel.at/schemas/1p0/messaging/header", "SignatureVerificationOmittedErrorCode");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.erpel.schemas._1p0.messaging.header
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErpelBusinessDocumentHeaderType }
     * 
     */
    public ErpelBusinessDocumentHeaderType createErpelBusinessDocumentHeaderType() {
        return new ErpelBusinessDocumentHeaderType();
    }

    /**
     * Create an instance of {@link InterchangeHeaderType }
     * 
     */
    public InterchangeHeaderType createInterchangeHeaderType() {
        return new InterchangeHeaderType();
    }

    /**
     * Create an instance of {@link SignatureVerificationResultType }
     * 
     */
    public SignatureVerificationResultType createSignatureVerificationResultType() {
        return new SignatureVerificationResultType();
    }

    /**
     * Create an instance of {@link SignatureVerificationType }
     * 
     */
    public SignatureVerificationType createSignatureVerificationType() {
        return new SignatureVerificationType();
    }

    /**
     * Create an instance of {@link DateTimeType }
     * 
     */
    public DateTimeType createDateTimeType() {
        return new DateTimeType();
    }

    /**
     * Create an instance of {@link RecipientRefType }
     * 
     */
    public RecipientRefType createRecipientRefType() {
        return new RecipientRefType();
    }

    /**
     * Create an instance of {@link RecipientType }
     * 
     */
    public RecipientType createRecipientType() {
        return new RecipientType();
    }

    /**
     * Create an instance of {@link SyntaxIdentifierType }
     * 
     */
    public SyntaxIdentifierType createSyntaxIdentifierType() {
        return new SyntaxIdentifierType();
    }

    /**
     * Create an instance of {@link SenderType }
     * 
     */
    public SenderType createSenderType() {
        return new SenderType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErpelBusinessDocumentHeaderType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ErpelBusinessDocumentHeaderType }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "ErpelBusinessDocumentHeader")
    public JAXBElement<ErpelBusinessDocumentHeaderType> createErpelBusinessDocumentHeader(ErpelBusinessDocumentHeaderType value) {
        return new JAXBElement<ErpelBusinessDocumentHeaderType>(_ErpelBusinessDocumentHeader_QNAME, ErpelBusinessDocumentHeaderType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "CertificateCheck")
    public JAXBElement<Integer> createCertificateCheck(Integer value) {
        return new JAXBElement<Integer>(_CertificateCheck_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InterchangeHeaderType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InterchangeHeaderType }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "InterchangeHeader")
    public JAXBElement<InterchangeHeaderType> createInterchangeHeader(InterchangeHeaderType value) {
        return new JAXBElement<InterchangeHeaderType>(_InterchangeHeader_QNAME, InterchangeHeaderType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "MessageReceivedAt")
    public JAXBElement<XMLGregorianCalendar> createMessageReceivedAt(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_MessageReceivedAt_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "Receiver")
    public JAXBElement<String> createReceiver(String value) {
        return new JAXBElement<String>(_Receiver_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "Sender")
    public JAXBElement<String> createSender(String value) {
        return new JAXBElement<String>(_Sender_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "SignatureCheck")
    public JAXBElement<Integer> createSignatureCheck(Integer value) {
        return new JAXBElement<Integer>(_SignatureCheck_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "SignatureManifestCheck")
    public JAXBElement<Integer> createSignatureManifestCheck(Integer value) {
        return new JAXBElement<Integer>(_SignatureManifestCheck_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureVerificationResultType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignatureVerificationResultType }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "SignatureVerificationResult")
    public JAXBElement<SignatureVerificationResultType> createSignatureVerificationResult(SignatureVerificationResultType value) {
        return new JAXBElement<SignatureVerificationResultType>(_SignatureVerificationResult_QNAME, SignatureVerificationResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureVerificationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignatureVerificationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "SignatureVerification")
    public JAXBElement<SignatureVerificationType> createSignatureVerification(SignatureVerificationType value) {
        return new JAXBElement<SignatureVerificationType>(_SignatureVerification_QNAME, SignatureVerificationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://erpel.at/schemas/1p0/messaging/header", name = "SignatureVerificationOmittedErrorCode")
    public JAXBElement<String> createSignatureVerificationOmittedErrorCode(String value) {
        return new JAXBElement<String>(_SignatureVerificationOmittedErrorCode_QNAME, String.class, null, value);
    }

}
