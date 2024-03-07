
package de.metas.postfinance.jaxb;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.metas.postfinance.jaxb package. 
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

    private final static QName _ArrayOfInvoiceReport_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfInvoiceReport");
    private final static QName _InvoiceReport_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "InvoiceReport");
    private final static QName _ArrayOfDownloadFile_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfDownloadFile");
    private final static QName _DownloadFile_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "DownloadFile");
    private final static QName _ArrayOfProtocolReport_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfProtocolReport");
    private final static QName _ProtocolReport_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ProtocolReport");
    private final static QName _ArrayOfInvoice_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfInvoice");
    private final static QName _Invoice_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Invoice");
    private final static QName _ArrayOfProcessedInvoice_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfProcessedInvoice");
    private final static QName _ProcessedInvoice_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ProcessedInvoice");
    private final static QName _SearchInvoiceParameter_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "SearchInvoiceParameter");
    private final static QName _SearchInvoicesResponse_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "SearchInvoicesResponse");
    private final static QName _ArrayOfSearchInvoice_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfSearchInvoice");
    private final static QName _SearchInvoice_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "SearchInvoice");
    private final static QName _ArrayOfEBillRecipientSubscriptionStatus_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfEBillRecipientSubscriptionStatus");
    private final static QName _EBillRecipientSubscriptionStatus_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "EBillRecipientSubscriptionStatus");
    private final static QName _EBillRecipientSubscriptionStatusBulk_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "EBillRecipientSubscriptionStatusBulk");
    private final static QName _ArrayOfBillRecipient_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfBillRecipient");
    private final static QName _BillRecipient_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "BillRecipient");
    private final static QName _EBillRecipientSubscriptionInitiation_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "EBillRecipientSubscriptionInitiation");
    private final static QName _EBillRecipientSubscriptionConfirmation_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "EBillRecipientSubscriptionConfirmation");
    private final static QName _Party_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Party");
    private final static QName _Address_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Address");
    private final static QName _ArrayOfBillerReport_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ArrayOfBillerReport");
    private final static QName _BillerReport_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "BillerReport");
    private final static QName _State_QNAME = new QName("http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic", "State");
    private final static QName _SubmissionStatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic", "SubmissionStatus");
    private final static QName _EPartyType_QNAME = new QName("http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic", "EPartyType");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _ArrayOfstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
    private final static QName _GetInvoiceListBillerResponseGetInvoiceListBillerResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetInvoiceListBillerResult");
    private final static QName _GetInvoiceBillerResponseGetInvoiceBillerResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetInvoiceBillerResult");
    private final static QName _GetProcessProtocolListResponseGetProcessProtocolListResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetProcessProtocolListResult");
    private final static QName _GetProcessProtocolResponseGetProcessProtocolResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetProcessProtocolResult");
    private final static QName _GetRegistrationProtocolListResponseGetRegistrationProtocolListResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetRegistrationProtocolListResult");
    private final static QName _GetRegistrationProtocolResponseGetRegistrationProtocolResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetRegistrationProtocolResult");
    private final static QName _UploadFilesReportResponseUploadFilesReportResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "UploadFilesReportResult");
    private final static QName _SearchInvoicesResponseSearchInvoicesResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "SearchInvoicesResult");
    private final static QName _GetEBillRecipientSubscriptionStatusResponseGetEBillRecipientSubscriptionStatusResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetEBillRecipientSubscriptionStatusResult");
    private final static QName _GetEBillRecipientSubscriptionStatusBulkResponseGetEBillRecipientSubscriptionStatusBulkResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetEBillRecipientSubscriptionStatusBulkResult");
    private final static QName _InitiateEBillRecipientSubscriptionResponseInitiateEBillRecipientSubscriptionResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "InitiateEBillRecipientSubscriptionResult");
    private final static QName _ConfirmEBillRecipientSubscriptionResponseConfirmEBillRecipientSubscriptionResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "ConfirmEBillRecipientSubscriptionResult");
    private final static QName _GetBillerReportListResponseGetBillerReportListResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetBillerReportListResult");
    private final static QName _GetBillerReportResponseGetBillerReportResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetBillerReportResult");
    private final static QName _GetInvoicePayerResponseGetInvoicePayerResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetInvoicePayerResult");
    private final static QName _GetInvoiceListPayerResponseGetInvoiceListPayerResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "GetInvoiceListPayerResult");
    private final static QName _ExecutePingErrorTest_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "ErrorTest");
    private final static QName _ExecutePingExceptionTest_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "ExceptionTest");
    private final static QName _ExecutePingResponseExecutePingResult_QNAME = new QName("http://ch.swisspost.ebill.b2bservice", "ExecutePingResult");
    private final static QName _BillerReportReportType_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ReportType");
    private final static QName _AddressCompanyName_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "CompanyName");
    private final static QName _AddressFamilyName_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "FamilyName");
    private final static QName _AddressGivenName_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "GivenName");
    private final static QName _AddressAddress1_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Address1");
    private final static QName _AddressZIP_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ZIP");
    private final static QName _AddressCity_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "City");
    private final static QName _AddressCountry_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Country");
    private final static QName _BillRecipientEbillAccountID_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "EbillAccountID");
    private final static QName _BillRecipientEmailAddress_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "EmailAddress");
    private final static QName _BillRecipientUIDHR_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "UIDHR");
    private final static QName _EBillRecipientSubscriptionStatusMessage_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Message");
    private final static QName _SearchInvoiceBillerId_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "BillerId");
    private final static QName _SearchInvoiceTransactionId_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "TransactionId");
    private final static QName _SearchInvoiceEBillAccountId_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "eBillAccountId");
    private final static QName _SearchInvoiceAmount_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Amount");
    private final static QName _SearchInvoicePaymentType_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "PaymentType");
    private final static QName _SearchInvoiceESRReferenceNbr_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ESRReferenceNbr");
    private final static QName _SearchInvoiceDeliveryDate_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "DeliveryDate");
    private final static QName _SearchInvoicePaymentDueDate_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "PaymentDueDate");
    private final static QName _SearchInvoiceReasonCode_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ReasonCode");
    private final static QName _SearchInvoiceReasonText_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ReasonText");
    private final static QName _ProcessedInvoiceFileType_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "FileType");
    private final static QName _ProcessedInvoiceTransactionID_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "TransactionID");
    private final static QName _ProcessedInvoiceProcessingState_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "ProcessingState");
    private final static QName _InvoiceData_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Data");
    private final static QName _InvoiceReportBillerID_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "BillerID");
    private final static QName _DownloadFileFilename_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Filename");
    private final static QName _EBillRecipientSubscriptionConfirmationLanguage_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "Language");
    private final static QName _EBillRecipientSubscriptionInitiationSubscriptionInitiationToken_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "SubscriptionInitiationToken");
    private final static QName _EBillRecipientSubscriptionStatusBulkBillRecipients_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "BillRecipients");
    private final static QName _SearchInvoicesResponse2InvoiceList_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "InvoiceList");
    private final static QName _SearchInvoiceParameterEBillAccountID_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "eBillAccountID");
    private final static QName _SearchInvoiceParameterState_QNAME = new QName("http://swisspost_ch.ebs.ebill.b2bservice", "State");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.metas.postfinance.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInvoiceListBiller }
     * 
     */
    public GetInvoiceListBiller createGetInvoiceListBiller() {
        return new GetInvoiceListBiller();
    }

    /**
     * Create an instance of {@link GetInvoiceListBillerResponse }
     * 
     */
    public GetInvoiceListBillerResponse createGetInvoiceListBillerResponse() {
        return new GetInvoiceListBillerResponse();
    }

    /**
     * Create an instance of {@link ArrayOfInvoiceReport }
     * 
     */
    public ArrayOfInvoiceReport createArrayOfInvoiceReport() {
        return new ArrayOfInvoiceReport();
    }

    /**
     * Create an instance of {@link GetInvoiceBiller }
     * 
     */
    public GetInvoiceBiller createGetInvoiceBiller() {
        return new GetInvoiceBiller();
    }

    /**
     * Create an instance of {@link GetInvoiceBillerResponse }
     * 
     */
    public GetInvoiceBillerResponse createGetInvoiceBillerResponse() {
        return new GetInvoiceBillerResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDownloadFile }
     * 
     */
    public ArrayOfDownloadFile createArrayOfDownloadFile() {
        return new ArrayOfDownloadFile();
    }

    /**
     * Create an instance of {@link GetProcessProtocolList }
     * 
     */
    public GetProcessProtocolList createGetProcessProtocolList() {
        return new GetProcessProtocolList();
    }

    /**
     * Create an instance of {@link GetProcessProtocolListResponse }
     * 
     */
    public GetProcessProtocolListResponse createGetProcessProtocolListResponse() {
        return new GetProcessProtocolListResponse();
    }

    /**
     * Create an instance of {@link ArrayOfProtocolReport }
     * 
     */
    public ArrayOfProtocolReport createArrayOfProtocolReport() {
        return new ArrayOfProtocolReport();
    }

    /**
     * Create an instance of {@link GetProcessProtocol }
     * 
     */
    public GetProcessProtocol createGetProcessProtocol() {
        return new GetProcessProtocol();
    }

    /**
     * Create an instance of {@link GetProcessProtocolResponse }
     * 
     */
    public GetProcessProtocolResponse createGetProcessProtocolResponse() {
        return new GetProcessProtocolResponse();
    }

    /**
     * Create an instance of {@link GetRegistrationProtocolList }
     * 
     */
    public GetRegistrationProtocolList createGetRegistrationProtocolList() {
        return new GetRegistrationProtocolList();
    }

    /**
     * Create an instance of {@link GetRegistrationProtocolListResponse }
     * 
     */
    public GetRegistrationProtocolListResponse createGetRegistrationProtocolListResponse() {
        return new GetRegistrationProtocolListResponse();
    }

    /**
     * Create an instance of {@link GetRegistrationProtocol }
     * 
     */
    public GetRegistrationProtocol createGetRegistrationProtocol() {
        return new GetRegistrationProtocol();
    }

    /**
     * Create an instance of {@link GetRegistrationProtocolResponse }
     * 
     */
    public GetRegistrationProtocolResponse createGetRegistrationProtocolResponse() {
        return new GetRegistrationProtocolResponse();
    }

    /**
     * Create an instance of {@link UploadFilesReport }
     * 
     */
    public UploadFilesReport createUploadFilesReport() {
        return new UploadFilesReport();
    }

    /**
     * Create an instance of {@link ArrayOfInvoice }
     * 
     */
    public ArrayOfInvoice createArrayOfInvoice() {
        return new ArrayOfInvoice();
    }

    /**
     * Create an instance of {@link UploadFilesReportResponse }
     * 
     */
    public UploadFilesReportResponse createUploadFilesReportResponse() {
        return new UploadFilesReportResponse();
    }

    /**
     * Create an instance of {@link ArrayOfProcessedInvoice }
     * 
     */
    public ArrayOfProcessedInvoice createArrayOfProcessedInvoice() {
        return new ArrayOfProcessedInvoice();
    }

    /**
     * Create an instance of {@link SearchInvoices }
     * 
     */
    public SearchInvoices createSearchInvoices() {
        return new SearchInvoices();
    }

    /**
     * Create an instance of {@link SearchInvoiceParameter }
     * 
     */
    public SearchInvoiceParameter createSearchInvoiceParameter() {
        return new SearchInvoiceParameter();
    }

    /**
     * Create an instance of {@link SearchInvoicesResponse }
     * 
     */
    public SearchInvoicesResponse createSearchInvoicesResponse() {
        return new SearchInvoicesResponse();
    }

    /**
     * Create an instance of {@link SearchInvoicesResponse2 }
     * 
     */
    public SearchInvoicesResponse2 createSearchInvoicesResponse2() {
        return new SearchInvoicesResponse2();
    }

    /**
     * Create an instance of {@link GetEBillRecipientSubscriptionStatus }
     * 
     */
    public GetEBillRecipientSubscriptionStatus createGetEBillRecipientSubscriptionStatus() {
        return new GetEBillRecipientSubscriptionStatus();
    }

    /**
     * Create an instance of {@link GetEBillRecipientSubscriptionStatusResponse }
     * 
     */
    public GetEBillRecipientSubscriptionStatusResponse createGetEBillRecipientSubscriptionStatusResponse() {
        return new GetEBillRecipientSubscriptionStatusResponse();
    }

    /**
     * Create an instance of {@link ArrayOfEBillRecipientSubscriptionStatus }
     * 
     */
    public ArrayOfEBillRecipientSubscriptionStatus createArrayOfEBillRecipientSubscriptionStatus() {
        return new ArrayOfEBillRecipientSubscriptionStatus();
    }

    /**
     * Create an instance of {@link GetEBillRecipientSubscriptionStatusBulk }
     * 
     */
    public GetEBillRecipientSubscriptionStatusBulk createGetEBillRecipientSubscriptionStatusBulk() {
        return new GetEBillRecipientSubscriptionStatusBulk();
    }

    /**
     * Create an instance of {@link ArrayOfstring }
     * 
     */
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
    }

    /**
     * Create an instance of {@link GetEBillRecipientSubscriptionStatusBulkResponse }
     * 
     */
    public GetEBillRecipientSubscriptionStatusBulkResponse createGetEBillRecipientSubscriptionStatusBulkResponse() {
        return new GetEBillRecipientSubscriptionStatusBulkResponse();
    }

    /**
     * Create an instance of {@link EBillRecipientSubscriptionStatusBulk }
     * 
     */
    public EBillRecipientSubscriptionStatusBulk createEBillRecipientSubscriptionStatusBulk() {
        return new EBillRecipientSubscriptionStatusBulk();
    }

    /**
     * Create an instance of {@link InitiateEBillRecipientSubscription }
     * 
     */
    public InitiateEBillRecipientSubscription createInitiateEBillRecipientSubscription() {
        return new InitiateEBillRecipientSubscription();
    }

    /**
     * Create an instance of {@link InitiateEBillRecipientSubscriptionResponse }
     * 
     */
    public InitiateEBillRecipientSubscriptionResponse createInitiateEBillRecipientSubscriptionResponse() {
        return new InitiateEBillRecipientSubscriptionResponse();
    }

    /**
     * Create an instance of {@link EBillRecipientSubscriptionInitiation }
     * 
     */
    public EBillRecipientSubscriptionInitiation createEBillRecipientSubscriptionInitiation() {
        return new EBillRecipientSubscriptionInitiation();
    }

    /**
     * Create an instance of {@link ConfirmEBillRecipientSubscription }
     * 
     */
    public ConfirmEBillRecipientSubscription createConfirmEBillRecipientSubscription() {
        return new ConfirmEBillRecipientSubscription();
    }

    /**
     * Create an instance of {@link ConfirmEBillRecipientSubscriptionResponse }
     * 
     */
    public ConfirmEBillRecipientSubscriptionResponse createConfirmEBillRecipientSubscriptionResponse() {
        return new ConfirmEBillRecipientSubscriptionResponse();
    }

    /**
     * Create an instance of {@link EBillRecipientSubscriptionConfirmation }
     * 
     */
    public EBillRecipientSubscriptionConfirmation createEBillRecipientSubscriptionConfirmation() {
        return new EBillRecipientSubscriptionConfirmation();
    }

    /**
     * Create an instance of {@link GetBillerReportList }
     * 
     */
    public GetBillerReportList createGetBillerReportList() {
        return new GetBillerReportList();
    }

    /**
     * Create an instance of {@link GetBillerReportListResponse }
     * 
     */
    public GetBillerReportListResponse createGetBillerReportListResponse() {
        return new GetBillerReportListResponse();
    }

    /**
     * Create an instance of {@link ArrayOfBillerReport }
     * 
     */
    public ArrayOfBillerReport createArrayOfBillerReport() {
        return new ArrayOfBillerReport();
    }

    /**
     * Create an instance of {@link GetBillerReport }
     * 
     */
    public GetBillerReport createGetBillerReport() {
        return new GetBillerReport();
    }

    /**
     * Create an instance of {@link GetBillerReportResponse }
     * 
     */
    public GetBillerReportResponse createGetBillerReportResponse() {
        return new GetBillerReportResponse();
    }

    /**
     * Create an instance of {@link DownloadFile }
     * 
     */
    public DownloadFile createDownloadFile() {
        return new DownloadFile();
    }

    /**
     * Create an instance of {@link GetInvoicePayer }
     * 
     */
    public GetInvoicePayer createGetInvoicePayer() {
        return new GetInvoicePayer();
    }

    /**
     * Create an instance of {@link GetInvoicePayerResponse }
     * 
     */
    public GetInvoicePayerResponse createGetInvoicePayerResponse() {
        return new GetInvoicePayerResponse();
    }

    /**
     * Create an instance of {@link GetInvoiceListPayer }
     * 
     */
    public GetInvoiceListPayer createGetInvoiceListPayer() {
        return new GetInvoiceListPayer();
    }

    /**
     * Create an instance of {@link GetInvoiceListPayerResponse }
     * 
     */
    public GetInvoiceListPayerResponse createGetInvoiceListPayerResponse() {
        return new GetInvoiceListPayerResponse();
    }

    /**
     * Create an instance of {@link ExecutePing }
     * 
     */
    public ExecutePing createExecutePing() {
        return new ExecutePing();
    }

    /**
     * Create an instance of {@link ExecutePingResponse }
     * 
     */
    public ExecutePingResponse createExecutePingResponse() {
        return new ExecutePingResponse();
    }

    /**
     * Create an instance of {@link InvoiceReport }
     * 
     */
    public InvoiceReport createInvoiceReport() {
        return new InvoiceReport();
    }

    /**
     * Create an instance of {@link ProtocolReport }
     * 
     */
    public ProtocolReport createProtocolReport() {
        return new ProtocolReport();
    }

    /**
     * Create an instance of {@link Invoice }
     * 
     */
    public Invoice createInvoice() {
        return new Invoice();
    }

    /**
     * Create an instance of {@link ProcessedInvoice }
     * 
     */
    public ProcessedInvoice createProcessedInvoice() {
        return new ProcessedInvoice();
    }

    /**
     * Create an instance of {@link ArrayOfSearchInvoice }
     * 
     */
    public ArrayOfSearchInvoice createArrayOfSearchInvoice() {
        return new ArrayOfSearchInvoice();
    }

    /**
     * Create an instance of {@link SearchInvoice }
     * 
     */
    public SearchInvoice createSearchInvoice() {
        return new SearchInvoice();
    }

    /**
     * Create an instance of {@link EBillRecipientSubscriptionStatus }
     * 
     */
    public EBillRecipientSubscriptionStatus createEBillRecipientSubscriptionStatus() {
        return new EBillRecipientSubscriptionStatus();
    }

    /**
     * Create an instance of {@link ArrayOfBillRecipient }
     * 
     */
    public ArrayOfBillRecipient createArrayOfBillRecipient() {
        return new ArrayOfBillRecipient();
    }

    /**
     * Create an instance of {@link BillRecipient }
     * 
     */
    public BillRecipient createBillRecipient() {
        return new BillRecipient();
    }

    /**
     * Create an instance of {@link Party }
     * 
     */
    public Party createParty() {
        return new Party();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link BillerReport }
     * 
     */
    public BillerReport createBillerReport() {
        return new BillerReport();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfInvoiceReport")
    public JAXBElement<ArrayOfInvoiceReport> createArrayOfInvoiceReport(ArrayOfInvoiceReport value) {
        return new JAXBElement<ArrayOfInvoiceReport>(_ArrayOfInvoiceReport_QNAME, ArrayOfInvoiceReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvoiceReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InvoiceReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "InvoiceReport")
    public JAXBElement<InvoiceReport> createInvoiceReport(InvoiceReport value) {
        return new JAXBElement<InvoiceReport>(_InvoiceReport_QNAME, InvoiceReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfDownloadFile")
    public JAXBElement<ArrayOfDownloadFile> createArrayOfDownloadFile(ArrayOfDownloadFile value) {
        return new JAXBElement<ArrayOfDownloadFile>(_ArrayOfDownloadFile_QNAME, ArrayOfDownloadFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "DownloadFile")
    public JAXBElement<DownloadFile> createDownloadFile(DownloadFile value) {
        return new JAXBElement<DownloadFile>(_DownloadFile_QNAME, DownloadFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfProtocolReport")
    public JAXBElement<ArrayOfProtocolReport> createArrayOfProtocolReport(ArrayOfProtocolReport value) {
        return new JAXBElement<ArrayOfProtocolReport>(_ArrayOfProtocolReport_QNAME, ArrayOfProtocolReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ProtocolReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ProtocolReport")
    public JAXBElement<ProtocolReport> createProtocolReport(ProtocolReport value) {
        return new JAXBElement<ProtocolReport>(_ProtocolReport_QNAME, ProtocolReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfInvoice")
    public JAXBElement<ArrayOfInvoice> createArrayOfInvoice(ArrayOfInvoice value) {
        return new JAXBElement<ArrayOfInvoice>(_ArrayOfInvoice_QNAME, ArrayOfInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Invoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Invoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Invoice")
    public JAXBElement<Invoice> createInvoice(Invoice value) {
        return new JAXBElement<Invoice>(_Invoice_QNAME, Invoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProcessedInvoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfProcessedInvoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfProcessedInvoice")
    public JAXBElement<ArrayOfProcessedInvoice> createArrayOfProcessedInvoice(ArrayOfProcessedInvoice value) {
        return new JAXBElement<ArrayOfProcessedInvoice>(_ArrayOfProcessedInvoice_QNAME, ArrayOfProcessedInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessedInvoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ProcessedInvoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ProcessedInvoice")
    public JAXBElement<ProcessedInvoice> createProcessedInvoice(ProcessedInvoice value) {
        return new JAXBElement<ProcessedInvoice>(_ProcessedInvoice_QNAME, ProcessedInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchInvoiceParameter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchInvoiceParameter }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "SearchInvoiceParameter")
    public JAXBElement<SearchInvoiceParameter> createSearchInvoiceParameter(SearchInvoiceParameter value) {
        return new JAXBElement<SearchInvoiceParameter>(_SearchInvoiceParameter_QNAME, SearchInvoiceParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchInvoicesResponse2 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchInvoicesResponse2 }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "SearchInvoicesResponse")
    public JAXBElement<SearchInvoicesResponse2> createSearchInvoicesResponse(SearchInvoicesResponse2 value) {
        return new JAXBElement<SearchInvoicesResponse2>(_SearchInvoicesResponse_QNAME, SearchInvoicesResponse2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchInvoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchInvoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfSearchInvoice")
    public JAXBElement<ArrayOfSearchInvoice> createArrayOfSearchInvoice(ArrayOfSearchInvoice value) {
        return new JAXBElement<ArrayOfSearchInvoice>(_ArrayOfSearchInvoice_QNAME, ArrayOfSearchInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchInvoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchInvoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "SearchInvoice")
    public JAXBElement<SearchInvoice> createSearchInvoice(SearchInvoice value) {
        return new JAXBElement<SearchInvoice>(_SearchInvoice_QNAME, SearchInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEBillRecipientSubscriptionStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfEBillRecipientSubscriptionStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfEBillRecipientSubscriptionStatus")
    public JAXBElement<ArrayOfEBillRecipientSubscriptionStatus> createArrayOfEBillRecipientSubscriptionStatus(ArrayOfEBillRecipientSubscriptionStatus value) {
        return new JAXBElement<ArrayOfEBillRecipientSubscriptionStatus>(_ArrayOfEBillRecipientSubscriptionStatus_QNAME, ArrayOfEBillRecipientSubscriptionStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EBillRecipientSubscriptionStatus")
    public JAXBElement<EBillRecipientSubscriptionStatus> createEBillRecipientSubscriptionStatus(EBillRecipientSubscriptionStatus value) {
        return new JAXBElement<EBillRecipientSubscriptionStatus>(_EBillRecipientSubscriptionStatus_QNAME, EBillRecipientSubscriptionStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionStatusBulk }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionStatusBulk }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EBillRecipientSubscriptionStatusBulk")
    public JAXBElement<EBillRecipientSubscriptionStatusBulk> createEBillRecipientSubscriptionStatusBulk(EBillRecipientSubscriptionStatusBulk value) {
        return new JAXBElement<EBillRecipientSubscriptionStatusBulk>(_EBillRecipientSubscriptionStatusBulk_QNAME, EBillRecipientSubscriptionStatusBulk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfBillRecipient }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfBillRecipient }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfBillRecipient")
    public JAXBElement<ArrayOfBillRecipient> createArrayOfBillRecipient(ArrayOfBillRecipient value) {
        return new JAXBElement<ArrayOfBillRecipient>(_ArrayOfBillRecipient_QNAME, ArrayOfBillRecipient.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillRecipient }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillRecipient }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "BillRecipient")
    public JAXBElement<BillRecipient> createBillRecipient(BillRecipient value) {
        return new JAXBElement<BillRecipient>(_BillRecipient_QNAME, BillRecipient.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionInitiation }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionInitiation }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EBillRecipientSubscriptionInitiation")
    public JAXBElement<EBillRecipientSubscriptionInitiation> createEBillRecipientSubscriptionInitiation(EBillRecipientSubscriptionInitiation value) {
        return new JAXBElement<EBillRecipientSubscriptionInitiation>(_EBillRecipientSubscriptionInitiation_QNAME, EBillRecipientSubscriptionInitiation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionConfirmation }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionConfirmation }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EBillRecipientSubscriptionConfirmation")
    public JAXBElement<EBillRecipientSubscriptionConfirmation> createEBillRecipientSubscriptionConfirmation(EBillRecipientSubscriptionConfirmation value) {
        return new JAXBElement<EBillRecipientSubscriptionConfirmation>(_EBillRecipientSubscriptionConfirmation_QNAME, EBillRecipientSubscriptionConfirmation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Party }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Party }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Party")
    public JAXBElement<Party> createParty(Party value) {
        return new JAXBElement<Party>(_Party_QNAME, Party.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Address }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Address }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Address")
    public JAXBElement<Address> createAddress(Address value) {
        return new JAXBElement<Address>(_Address_QNAME, Address.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfBillerReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfBillerReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ArrayOfBillerReport")
    public JAXBElement<ArrayOfBillerReport> createArrayOfBillerReport(ArrayOfBillerReport value) {
        return new JAXBElement<ArrayOfBillerReport>(_ArrayOfBillerReport_QNAME, ArrayOfBillerReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillerReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillerReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "BillerReport")
    public JAXBElement<BillerReport> createBillerReport(BillerReport value) {
        return new JAXBElement<BillerReport>(_BillerReport_QNAME, BillerReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link State }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link State }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic", name = "State")
    public JAXBElement<State> createState(State value) {
        return new JAXBElement<State>(_State_QNAME, State.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmissionStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmissionStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic", name = "SubmissionStatus")
    public JAXBElement<SubmissionStatus> createSubmissionStatus(SubmissionStatus value) {
        return new JAXBElement<SubmissionStatus>(_SubmissionStatus_QNAME, SubmissionStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EPartyType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EPartyType }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic", name = "EPartyType")
    public JAXBElement<EPartyType> createEPartyType(EPartyType value) {
        return new JAXBElement<EPartyType>(_EPartyType_QNAME, EPartyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfstring")
    public JAXBElement<ArrayOfstring> createArrayOfstring(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_ArrayOfstring_QNAME, ArrayOfstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetInvoiceListBillerResult", scope = GetInvoiceListBillerResponse.class)
    public JAXBElement<ArrayOfInvoiceReport> createGetInvoiceListBillerResponseGetInvoiceListBillerResult(ArrayOfInvoiceReport value) {
        return new JAXBElement<ArrayOfInvoiceReport>(_GetInvoiceListBillerResponseGetInvoiceListBillerResult_QNAME, ArrayOfInvoiceReport.class, GetInvoiceListBillerResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetInvoiceBillerResult", scope = GetInvoiceBillerResponse.class)
    public JAXBElement<ArrayOfDownloadFile> createGetInvoiceBillerResponseGetInvoiceBillerResult(ArrayOfDownloadFile value) {
        return new JAXBElement<ArrayOfDownloadFile>(_GetInvoiceBillerResponseGetInvoiceBillerResult_QNAME, ArrayOfDownloadFile.class, GetInvoiceBillerResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetProcessProtocolListResult", scope = GetProcessProtocolListResponse.class)
    public JAXBElement<ArrayOfProtocolReport> createGetProcessProtocolListResponseGetProcessProtocolListResult(ArrayOfProtocolReport value) {
        return new JAXBElement<ArrayOfProtocolReport>(_GetProcessProtocolListResponseGetProcessProtocolListResult_QNAME, ArrayOfProtocolReport.class, GetProcessProtocolListResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetProcessProtocolResult", scope = GetProcessProtocolResponse.class)
    public JAXBElement<ArrayOfDownloadFile> createGetProcessProtocolResponseGetProcessProtocolResult(ArrayOfDownloadFile value) {
        return new JAXBElement<ArrayOfDownloadFile>(_GetProcessProtocolResponseGetProcessProtocolResult_QNAME, ArrayOfDownloadFile.class, GetProcessProtocolResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetRegistrationProtocolListResult", scope = GetRegistrationProtocolListResponse.class)
    public JAXBElement<ArrayOfProtocolReport> createGetRegistrationProtocolListResponseGetRegistrationProtocolListResult(ArrayOfProtocolReport value) {
        return new JAXBElement<ArrayOfProtocolReport>(_GetRegistrationProtocolListResponseGetRegistrationProtocolListResult_QNAME, ArrayOfProtocolReport.class, GetRegistrationProtocolListResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetRegistrationProtocolResult", scope = GetRegistrationProtocolResponse.class)
    public JAXBElement<ArrayOfDownloadFile> createGetRegistrationProtocolResponseGetRegistrationProtocolResult(ArrayOfDownloadFile value) {
        return new JAXBElement<ArrayOfDownloadFile>(_GetRegistrationProtocolResponseGetRegistrationProtocolResult_QNAME, ArrayOfDownloadFile.class, GetRegistrationProtocolResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProcessedInvoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfProcessedInvoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "UploadFilesReportResult", scope = UploadFilesReportResponse.class)
    public JAXBElement<ArrayOfProcessedInvoice> createUploadFilesReportResponseUploadFilesReportResult(ArrayOfProcessedInvoice value) {
        return new JAXBElement<ArrayOfProcessedInvoice>(_UploadFilesReportResponseUploadFilesReportResult_QNAME, ArrayOfProcessedInvoice.class, UploadFilesReportResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchInvoicesResponse2 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchInvoicesResponse2 }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "SearchInvoicesResult", scope = SearchInvoicesResponse.class)
    public JAXBElement<SearchInvoicesResponse2> createSearchInvoicesResponseSearchInvoicesResult(SearchInvoicesResponse2 value) {
        return new JAXBElement<SearchInvoicesResponse2>(_SearchInvoicesResponseSearchInvoicesResult_QNAME, SearchInvoicesResponse2 .class, SearchInvoicesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEBillRecipientSubscriptionStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfEBillRecipientSubscriptionStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetEBillRecipientSubscriptionStatusResult", scope = GetEBillRecipientSubscriptionStatusResponse.class)
    public JAXBElement<ArrayOfEBillRecipientSubscriptionStatus> createGetEBillRecipientSubscriptionStatusResponseGetEBillRecipientSubscriptionStatusResult(ArrayOfEBillRecipientSubscriptionStatus value) {
        return new JAXBElement<ArrayOfEBillRecipientSubscriptionStatus>(_GetEBillRecipientSubscriptionStatusResponseGetEBillRecipientSubscriptionStatusResult_QNAME, ArrayOfEBillRecipientSubscriptionStatus.class, GetEBillRecipientSubscriptionStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionStatusBulk }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionStatusBulk }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetEBillRecipientSubscriptionStatusBulkResult", scope = GetEBillRecipientSubscriptionStatusBulkResponse.class)
    public JAXBElement<EBillRecipientSubscriptionStatusBulk> createGetEBillRecipientSubscriptionStatusBulkResponseGetEBillRecipientSubscriptionStatusBulkResult(EBillRecipientSubscriptionStatusBulk value) {
        return new JAXBElement<EBillRecipientSubscriptionStatusBulk>(_GetEBillRecipientSubscriptionStatusBulkResponseGetEBillRecipientSubscriptionStatusBulkResult_QNAME, EBillRecipientSubscriptionStatusBulk.class, GetEBillRecipientSubscriptionStatusBulkResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionInitiation }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionInitiation }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "InitiateEBillRecipientSubscriptionResult", scope = InitiateEBillRecipientSubscriptionResponse.class)
    public JAXBElement<EBillRecipientSubscriptionInitiation> createInitiateEBillRecipientSubscriptionResponseInitiateEBillRecipientSubscriptionResult(EBillRecipientSubscriptionInitiation value) {
        return new JAXBElement<EBillRecipientSubscriptionInitiation>(_InitiateEBillRecipientSubscriptionResponseInitiateEBillRecipientSubscriptionResult_QNAME, EBillRecipientSubscriptionInitiation.class, InitiateEBillRecipientSubscriptionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionConfirmation }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionConfirmation }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "ConfirmEBillRecipientSubscriptionResult", scope = ConfirmEBillRecipientSubscriptionResponse.class)
    public JAXBElement<EBillRecipientSubscriptionConfirmation> createConfirmEBillRecipientSubscriptionResponseConfirmEBillRecipientSubscriptionResult(EBillRecipientSubscriptionConfirmation value) {
        return new JAXBElement<EBillRecipientSubscriptionConfirmation>(_ConfirmEBillRecipientSubscriptionResponseConfirmEBillRecipientSubscriptionResult_QNAME, EBillRecipientSubscriptionConfirmation.class, ConfirmEBillRecipientSubscriptionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfBillerReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfBillerReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetBillerReportListResult", scope = GetBillerReportListResponse.class)
    public JAXBElement<ArrayOfBillerReport> createGetBillerReportListResponseGetBillerReportListResult(ArrayOfBillerReport value) {
        return new JAXBElement<ArrayOfBillerReport>(_GetBillerReportListResponseGetBillerReportListResult_QNAME, ArrayOfBillerReport.class, GetBillerReportListResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetBillerReportResult", scope = GetBillerReportResponse.class)
    public JAXBElement<DownloadFile> createGetBillerReportResponseGetBillerReportResult(DownloadFile value) {
        return new JAXBElement<DownloadFile>(_GetBillerReportResponseGetBillerReportResult_QNAME, DownloadFile.class, GetBillerReportResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DownloadFile }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetInvoicePayerResult", scope = GetInvoicePayerResponse.class)
    public JAXBElement<DownloadFile> createGetInvoicePayerResponseGetInvoicePayerResult(DownloadFile value) {
        return new JAXBElement<DownloadFile>(_GetInvoicePayerResponseGetInvoicePayerResult_QNAME, DownloadFile.class, GetInvoicePayerResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "GetInvoiceListPayerResult", scope = GetInvoiceListPayerResponse.class)
    public JAXBElement<ArrayOfInvoiceReport> createGetInvoiceListPayerResponseGetInvoiceListPayerResult(ArrayOfInvoiceReport value) {
        return new JAXBElement<ArrayOfInvoiceReport>(_GetInvoiceListPayerResponseGetInvoiceListPayerResult_QNAME, ArrayOfInvoiceReport.class, GetInvoiceListPayerResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "ErrorTest", scope = ExecutePing.class)
    public JAXBElement<Boolean> createExecutePingErrorTest(Boolean value) {
        return new JAXBElement<Boolean>(_ExecutePingErrorTest_QNAME, Boolean.class, ExecutePing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "ExceptionTest", scope = ExecutePing.class)
    public JAXBElement<Boolean> createExecutePingExceptionTest(Boolean value) {
        return new JAXBElement<Boolean>(_ExecutePingExceptionTest_QNAME, Boolean.class, ExecutePing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://ch.swisspost.ebill.b2bservice", name = "ExecutePingResult", scope = ExecutePingResponse.class)
    public JAXBElement<String> createExecutePingResponseExecutePingResult(String value) {
        return new JAXBElement<String>(_ExecutePingResponseExecutePingResult_QNAME, String.class, ExecutePingResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ReportType", scope = BillerReport.class)
    public JAXBElement<String> createBillerReportReportType(String value) {
        return new JAXBElement<String>(_BillerReportReportType_QNAME, String.class, BillerReport.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "CompanyName", scope = Address.class)
    public JAXBElement<String> createAddressCompanyName(String value) {
        return new JAXBElement<String>(_AddressCompanyName_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "FamilyName", scope = Address.class)
    public JAXBElement<String> createAddressFamilyName(String value) {
        return new JAXBElement<String>(_AddressFamilyName_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "GivenName", scope = Address.class)
    public JAXBElement<String> createAddressGivenName(String value) {
        return new JAXBElement<String>(_AddressGivenName_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Address1", scope = Address.class)
    public JAXBElement<String> createAddressAddress1(String value) {
        return new JAXBElement<String>(_AddressAddress1_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ZIP", scope = Address.class)
    public JAXBElement<String> createAddressZIP(String value) {
        return new JAXBElement<String>(_AddressZIP_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "City", scope = Address.class)
    public JAXBElement<String> createAddressCity(String value) {
        return new JAXBElement<String>(_AddressCity_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Country", scope = Address.class)
    public JAXBElement<String> createAddressCountry(String value) {
        return new JAXBElement<String>(_AddressCountry_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Address }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Address }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Address", scope = Party.class)
    public JAXBElement<Address> createPartyAddress(Address value) {
        return new JAXBElement<Address>(_Address_QNAME, Address.class, Party.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EbillAccountID", scope = BillRecipient.class)
    public JAXBElement<String> createBillRecipientEbillAccountID(String value) {
        return new JAXBElement<String>(_BillRecipientEbillAccountID_QNAME, String.class, BillRecipient.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EmailAddress", scope = BillRecipient.class)
    public JAXBElement<String> createBillRecipientEmailAddress(String value) {
        return new JAXBElement<String>(_BillRecipientEmailAddress_QNAME, String.class, BillRecipient.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "UIDHR", scope = BillRecipient.class)
    public JAXBElement<String> createBillRecipientUIDHR(String value) {
        return new JAXBElement<String>(_BillRecipientUIDHR_QNAME, String.class, BillRecipient.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EbillAccountID", scope = EBillRecipientSubscriptionStatus.class)
    public JAXBElement<String> createEBillRecipientSubscriptionStatusEbillAccountID(String value) {
        return new JAXBElement<String>(_BillRecipientEbillAccountID_QNAME, String.class, EBillRecipientSubscriptionStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EmailAddress", scope = EBillRecipientSubscriptionStatus.class)
    public JAXBElement<String> createEBillRecipientSubscriptionStatusEmailAddress(String value) {
        return new JAXBElement<String>(_BillRecipientEmailAddress_QNAME, String.class, EBillRecipientSubscriptionStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "UIDHR", scope = EBillRecipientSubscriptionStatus.class)
    public JAXBElement<String> createEBillRecipientSubscriptionStatusUIDHR(String value) {
        return new JAXBElement<String>(_BillRecipientUIDHR_QNAME, String.class, EBillRecipientSubscriptionStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Message", scope = EBillRecipientSubscriptionStatus.class)
    public JAXBElement<String> createEBillRecipientSubscriptionStatusMessage(String value) {
        return new JAXBElement<String>(_EBillRecipientSubscriptionStatusMessage_QNAME, String.class, EBillRecipientSubscriptionStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "BillerId", scope = SearchInvoice.class)
    public JAXBElement<String> createSearchInvoiceBillerId(String value) {
        return new JAXBElement<String>(_SearchInvoiceBillerId_QNAME, String.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "TransactionId", scope = SearchInvoice.class)
    public JAXBElement<String> createSearchInvoiceTransactionId(String value) {
        return new JAXBElement<String>(_SearchInvoiceTransactionId_QNAME, String.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "eBillAccountId", scope = SearchInvoice.class)
    public JAXBElement<String> createSearchInvoiceEBillAccountId(String value) {
        return new JAXBElement<String>(_SearchInvoiceEBillAccountId_QNAME, String.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Amount", scope = SearchInvoice.class)
    public JAXBElement<BigDecimal> createSearchInvoiceAmount(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_SearchInvoiceAmount_QNAME, BigDecimal.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "PaymentType", scope = SearchInvoice.class)
    public JAXBElement<String> createSearchInvoicePaymentType(String value) {
        return new JAXBElement<String>(_SearchInvoicePaymentType_QNAME, String.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ESRReferenceNbr", scope = SearchInvoice.class)
    public JAXBElement<String> createSearchInvoiceESRReferenceNbr(String value) {
        return new JAXBElement<String>(_SearchInvoiceESRReferenceNbr_QNAME, String.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "DeliveryDate", scope = SearchInvoice.class)
    public JAXBElement<XMLGregorianCalendar> createSearchInvoiceDeliveryDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_SearchInvoiceDeliveryDate_QNAME, XMLGregorianCalendar.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "PaymentDueDate", scope = SearchInvoice.class)
    public JAXBElement<XMLGregorianCalendar> createSearchInvoicePaymentDueDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_SearchInvoicePaymentDueDate_QNAME, XMLGregorianCalendar.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ReasonCode", scope = SearchInvoice.class)
    public JAXBElement<String> createSearchInvoiceReasonCode(String value) {
        return new JAXBElement<String>(_SearchInvoiceReasonCode_QNAME, String.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ReasonText", scope = SearchInvoice.class)
    public JAXBElement<String> createSearchInvoiceReasonText(String value) {
        return new JAXBElement<String>(_SearchInvoiceReasonText_QNAME, String.class, SearchInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "FileType", scope = ProcessedInvoice.class)
    public JAXBElement<String> createProcessedInvoiceFileType(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceFileType_QNAME, String.class, ProcessedInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "TransactionID", scope = ProcessedInvoice.class)
    public JAXBElement<String> createProcessedInvoiceTransactionID(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceTransactionID_QNAME, String.class, ProcessedInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "ProcessingState", scope = ProcessedInvoice.class)
    public JAXBElement<String> createProcessedInvoiceProcessingState(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceProcessingState_QNAME, String.class, ProcessedInvoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "FileType", scope = Invoice.class)
    public JAXBElement<String> createInvoiceFileType(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceFileType_QNAME, String.class, Invoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "TransactionID", scope = Invoice.class)
    public JAXBElement<String> createInvoiceTransactionID(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceTransactionID_QNAME, String.class, Invoice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Data", scope = Invoice.class)
    public JAXBElement<byte[]> createInvoiceData(byte[] value) {
        return new JAXBElement<byte[]>(_InvoiceData_QNAME, byte[].class, Invoice.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "FileType", scope = ProtocolReport.class)
    public JAXBElement<String> createProtocolReportFileType(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceFileType_QNAME, String.class, ProtocolReport.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "BillerID", scope = InvoiceReport.class)
    public JAXBElement<String> createInvoiceReportBillerID(String value) {
        return new JAXBElement<String>(_InvoiceReportBillerID_QNAME, String.class, InvoiceReport.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "TransactionID", scope = InvoiceReport.class)
    public JAXBElement<String> createInvoiceReportTransactionID(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceTransactionID_QNAME, String.class, InvoiceReport.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "FileType", scope = InvoiceReport.class)
    public JAXBElement<String> createInvoiceReportFileType(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceFileType_QNAME, String.class, InvoiceReport.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Data", scope = DownloadFile.class)
    public JAXBElement<byte[]> createDownloadFileData(byte[] value) {
        return new JAXBElement<byte[]>(_InvoiceData_QNAME, byte[].class, DownloadFile.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Filename", scope = DownloadFile.class)
    public JAXBElement<String> createDownloadFileFilename(String value) {
        return new JAXBElement<String>(_DownloadFileFilename_QNAME, String.class, DownloadFile.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EbillAccountID", scope = EBillRecipientSubscriptionConfirmation.class)
    public JAXBElement<String> createEBillRecipientSubscriptionConfirmationEbillAccountID(String value) {
        return new JAXBElement<String>(_BillRecipientEbillAccountID_QNAME, String.class, EBillRecipientSubscriptionConfirmation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "EmailAddress", scope = EBillRecipientSubscriptionConfirmation.class)
    public JAXBElement<String> createEBillRecipientSubscriptionConfirmationEmailAddress(String value) {
        return new JAXBElement<String>(_BillRecipientEmailAddress_QNAME, String.class, EBillRecipientSubscriptionConfirmation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "UIDHR", scope = EBillRecipientSubscriptionConfirmation.class)
    public JAXBElement<String> createEBillRecipientSubscriptionConfirmationUIDHR(String value) {
        return new JAXBElement<String>(_BillRecipientUIDHR_QNAME, String.class, EBillRecipientSubscriptionConfirmation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Language", scope = EBillRecipientSubscriptionConfirmation.class)
    public JAXBElement<String> createEBillRecipientSubscriptionConfirmationLanguage(String value) {
        return new JAXBElement<String>(_EBillRecipientSubscriptionConfirmationLanguage_QNAME, String.class, EBillRecipientSubscriptionConfirmation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Party }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Party }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Party", scope = EBillRecipientSubscriptionConfirmation.class)
    public JAXBElement<Party> createEBillRecipientSubscriptionConfirmationParty(Party value) {
        return new JAXBElement<Party>(_Party_QNAME, Party.class, EBillRecipientSubscriptionConfirmation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Message", scope = EBillRecipientSubscriptionConfirmation.class)
    public JAXBElement<String> createEBillRecipientSubscriptionConfirmationMessage(String value) {
        return new JAXBElement<String>(_EBillRecipientSubscriptionStatusMessage_QNAME, String.class, EBillRecipientSubscriptionConfirmation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "SubscriptionInitiationToken", scope = EBillRecipientSubscriptionInitiation.class)
    public JAXBElement<String> createEBillRecipientSubscriptionInitiationSubscriptionInitiationToken(String value) {
        return new JAXBElement<String>(_EBillRecipientSubscriptionInitiationSubscriptionInitiationToken_QNAME, String.class, EBillRecipientSubscriptionInitiation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Message", scope = EBillRecipientSubscriptionInitiation.class)
    public JAXBElement<String> createEBillRecipientSubscriptionInitiationMessage(String value) {
        return new JAXBElement<String>(_EBillRecipientSubscriptionStatusMessage_QNAME, String.class, EBillRecipientSubscriptionInitiation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfBillRecipient }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfBillRecipient }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "BillRecipients", scope = EBillRecipientSubscriptionStatusBulk.class)
    public JAXBElement<ArrayOfBillRecipient> createEBillRecipientSubscriptionStatusBulkBillRecipients(ArrayOfBillRecipient value) {
        return new JAXBElement<ArrayOfBillRecipient>(_EBillRecipientSubscriptionStatusBulkBillRecipients_QNAME, ArrayOfBillRecipient.class, EBillRecipientSubscriptionStatusBulk.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "Message", scope = EBillRecipientSubscriptionStatusBulk.class)
    public JAXBElement<String> createEBillRecipientSubscriptionStatusBulkMessage(String value) {
        return new JAXBElement<String>(_EBillRecipientSubscriptionStatusMessage_QNAME, String.class, EBillRecipientSubscriptionStatusBulk.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchInvoice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchInvoice }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "InvoiceList", scope = SearchInvoicesResponse2 .class)
    public JAXBElement<ArrayOfSearchInvoice> createSearchInvoicesResponse2InvoiceList(ArrayOfSearchInvoice value) {
        return new JAXBElement<ArrayOfSearchInvoice>(_SearchInvoicesResponse2InvoiceList_QNAME, ArrayOfSearchInvoice.class, SearchInvoicesResponse2 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "TransactionID", scope = SearchInvoiceParameter.class)
    public JAXBElement<String> createSearchInvoiceParameterTransactionID(String value) {
        return new JAXBElement<String>(_ProcessedInvoiceTransactionID_QNAME, String.class, SearchInvoiceParameter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "eBillAccountID", scope = SearchInvoiceParameter.class)
    public JAXBElement<String> createSearchInvoiceParameterEBillAccountID(String value) {
        return new JAXBElement<String>(_SearchInvoiceParameterEBillAccountID_QNAME, String.class, SearchInvoiceParameter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link State }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link State }{@code >}
     */
    @XmlElementDecl(namespace = "http://swisspost_ch.ebs.ebill.b2bservice", name = "State", scope = SearchInvoiceParameter.class)
    public JAXBElement<State> createSearchInvoiceParameterState(State value) {
        return new JAXBElement<State>(_SearchInvoiceParameterState_QNAME, State.class, SearchInvoiceParameter.class, value);
    }

}
