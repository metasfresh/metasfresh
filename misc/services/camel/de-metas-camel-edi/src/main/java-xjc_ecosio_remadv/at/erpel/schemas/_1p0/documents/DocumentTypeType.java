
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="DocumentTypeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CallOff"/&gt;
 *     &lt;enumeration value="Cheque"/&gt;
 *     &lt;enumeration value="CommercialDispute"/&gt;
 *     &lt;enumeration value="ContractReference"/&gt;
 *     &lt;enumeration value="Contrl"/&gt;
 *     &lt;enumeration value="DebitNote"/&gt;
 *     &lt;enumeration value="DebitNoteForFinancialAdjustment"/&gt;
 *     &lt;enumeration value="DeliveryForecast"/&gt;
 *     &lt;enumeration value="DeliveryJIT"/&gt;
 *     &lt;enumeration value="DispatchAdvice"/&gt;
 *     &lt;enumeration value="DocumentReference"/&gt;
 *     &lt;enumeration value="FactoredInvoice"/&gt;
 *     &lt;enumeration value="GeneralPurposeMessage"/&gt;
 *     &lt;enumeration value="InventoryReport"/&gt;
 *     &lt;enumeration value="Invoice"/&gt;
 *     &lt;enumeration value="InvoiceCreditMemo"/&gt;
 *     &lt;enumeration value="InvoiceFinalSettlement"/&gt;
 *     &lt;enumeration value="InvoiceForAdvancePayment"/&gt;
 *     &lt;enumeration value="InvoiceForPartialDelivery"/&gt;
 *     &lt;enumeration value="InvoiceList"/&gt;
 *     &lt;enumeration value="InvoiceProForma"/&gt;
 *     &lt;enumeration value="InvoiceSelfBilling"/&gt;
 *     &lt;enumeration value="InvoiceSubsequentCredit"/&gt;
 *     &lt;enumeration value="InvoiceSubsequentDebit"/&gt;
 *     &lt;enumeration value="Offer"/&gt;
 *     &lt;enumeration value="Order"/&gt;
 *     &lt;enumeration value="OrderChange"/&gt;
 *     &lt;enumeration value="OrderConfirmation"/&gt;
 *     &lt;enumeration value="PaymentOrder"/&gt;
 *     &lt;enumeration value="PreviousCallOff"/&gt;
 *     &lt;enumeration value="PriceCatalogue"/&gt;
 *     &lt;enumeration value="Project"/&gt;
 *     &lt;enumeration value="ReceiptAdvice"/&gt;
 *     &lt;enumeration value="RemittanceAdvice"/&gt;
 *     &lt;enumeration value="RequestForQuote"/&gt;
 *     &lt;enumeration value="RequestNumber"/&gt;
 *     &lt;enumeration value="ReturnDelivery"/&gt;
 *     &lt;enumeration value="SalesReport"/&gt;
 *     &lt;enumeration value="TransportInstruction"/&gt;
 *     &lt;enumeration value="TransportStatus"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DocumentTypeType")
@XmlEnum
public enum DocumentTypeType {

    @XmlEnumValue("CallOff")
    CALL_OFF("CallOff"),
    @XmlEnumValue("Cheque")
    CHEQUE("Cheque"),
    @XmlEnumValue("CommercialDispute")
    COMMERCIAL_DISPUTE("CommercialDispute"),
    @XmlEnumValue("ContractReference")
    CONTRACT_REFERENCE("ContractReference"),
    @XmlEnumValue("Contrl")
    CONTRL("Contrl"),
    @XmlEnumValue("DebitNote")
    DEBIT_NOTE("DebitNote"),
    @XmlEnumValue("DebitNoteForFinancialAdjustment")
    DEBIT_NOTE_FOR_FINANCIAL_ADJUSTMENT("DebitNoteForFinancialAdjustment"),
    @XmlEnumValue("DeliveryForecast")
    DELIVERY_FORECAST("DeliveryForecast"),
    @XmlEnumValue("DeliveryJIT")
    DELIVERY_JIT("DeliveryJIT"),
    @XmlEnumValue("DispatchAdvice")
    DISPATCH_ADVICE("DispatchAdvice"),
    @XmlEnumValue("DocumentReference")
    DOCUMENT_REFERENCE("DocumentReference"),
    @XmlEnumValue("FactoredInvoice")
    FACTORED_INVOICE("FactoredInvoice"),
    @XmlEnumValue("GeneralPurposeMessage")
    GENERAL_PURPOSE_MESSAGE("GeneralPurposeMessage"),
    @XmlEnumValue("InventoryReport")
    INVENTORY_REPORT("InventoryReport"),
    @XmlEnumValue("Invoice")
    INVOICE("Invoice"),
    @XmlEnumValue("InvoiceCreditMemo")
    INVOICE_CREDIT_MEMO("InvoiceCreditMemo"),
    @XmlEnumValue("InvoiceFinalSettlement")
    INVOICE_FINAL_SETTLEMENT("InvoiceFinalSettlement"),
    @XmlEnumValue("InvoiceForAdvancePayment")
    INVOICE_FOR_ADVANCE_PAYMENT("InvoiceForAdvancePayment"),
    @XmlEnumValue("InvoiceForPartialDelivery")
    INVOICE_FOR_PARTIAL_DELIVERY("InvoiceForPartialDelivery"),
    @XmlEnumValue("InvoiceList")
    INVOICE_LIST("InvoiceList"),
    @XmlEnumValue("InvoiceProForma")
    INVOICE_PRO_FORMA("InvoiceProForma"),
    @XmlEnumValue("InvoiceSelfBilling")
    INVOICE_SELF_BILLING("InvoiceSelfBilling"),
    @XmlEnumValue("InvoiceSubsequentCredit")
    INVOICE_SUBSEQUENT_CREDIT("InvoiceSubsequentCredit"),
    @XmlEnumValue("InvoiceSubsequentDebit")
    INVOICE_SUBSEQUENT_DEBIT("InvoiceSubsequentDebit"),
    @XmlEnumValue("Offer")
    OFFER("Offer"),
    @XmlEnumValue("Order")
    ORDER("Order"),
    @XmlEnumValue("OrderChange")
    ORDER_CHANGE("OrderChange"),
    @XmlEnumValue("OrderConfirmation")
    ORDER_CONFIRMATION("OrderConfirmation"),
    @XmlEnumValue("PaymentOrder")
    PAYMENT_ORDER("PaymentOrder"),
    @XmlEnumValue("PreviousCallOff")
    PREVIOUS_CALL_OFF("PreviousCallOff"),
    @XmlEnumValue("PriceCatalogue")
    PRICE_CATALOGUE("PriceCatalogue"),
    @XmlEnumValue("Project")
    PROJECT("Project"),
    @XmlEnumValue("ReceiptAdvice")
    RECEIPT_ADVICE("ReceiptAdvice"),
    @XmlEnumValue("RemittanceAdvice")
    REMITTANCE_ADVICE("RemittanceAdvice"),
    @XmlEnumValue("RequestForQuote")
    REQUEST_FOR_QUOTE("RequestForQuote"),
    @XmlEnumValue("RequestNumber")
    REQUEST_NUMBER("RequestNumber"),
    @XmlEnumValue("ReturnDelivery")
    RETURN_DELIVERY("ReturnDelivery"),
    @XmlEnumValue("SalesReport")
    SALES_REPORT("SalesReport"),
    @XmlEnumValue("TransportInstruction")
    TRANSPORT_INSTRUCTION("TransportInstruction"),
    @XmlEnumValue("TransportStatus")
    TRANSPORT_STATUS("TransportStatus");
    private final String value;

    DocumentTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DocumentTypeType fromValue(String v) {
        for (DocumentTypeType c: DocumentTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
