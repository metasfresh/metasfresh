DROP FUNCTION IF EXISTS getInvoicePaymentAllocations_Report(date,
                                                            date)
;

CREATE OR REPLACE FUNCTION getInvoicePaymentAllocations_Report(
    DateInvoicedFrom date,
    DateInvoicedTo   date
)
    RETURNS TABLE
            (
                dateinvoiced           timestamp,
                InvoiceDocumentNo      varchar,
                InvoiceDocTypeName     varchar,
                Bill_BPValue           varchar,
                Bill_BPartner_Name     varchar,
                GrandTotal             numeric,
                InvoiceCurrency        varchar,
                paymentrule            char,
                PaymentDate            timestamp with time zone,
                PaymentDocumentNo      varchar,
                Payment_BPartner_Value varchar,
                Payment_BPartner_Name  varchar,
                Payamt                 numeric,
                PaymentCurrency        varchar,
                AllocatedAmt           numeric,
                discountamt            numeric,
                overunderamt           numeric,
                paymentwriteoffamt     numeric
            )
AS
$BODY$
SELECT i.dateinvoiced,
       i.documentno,
       idt.name,
       ibp.value AS bp_value,
       ibp.name  AS bp_name,
       i.grandtotal,
       invc.cursymbol,


       i.paymentrule,
       p.datetrx,
       p.documentno,
       pbp.value AS bp_value,
       pbp.name  AS Payment_BPartner,

       p.payamt,
       payc.cursymbol,
       pa.amount,
       pa.discountamt,
       pa.overunderamt,
       pa.paymentwriteoffamt


FROM c_invoice i
         JOIN C_DocType idt ON i.C_DocType_ID = idt.C_DocType_ID
         JOIN c_bpartner ibp ON i.c_bpartner_id = ibp.c_bpartner_id
         JOIN C_Currency invc ON i.c_currency_id = invc.c_currency_id
         JOIN c_allocationline pa ON i.C_Invoice_ID = pa.C_Invoice_ID
         JOIN C_Payment p ON pa.C_Payment_ID = p.C_Payment_ID
         JOIN C_Currency payc ON p.c_currency_id = payc.c_currency_id
         JOIN C_DOctype pdt ON p.C_DocType_ID = pdt.C_DocType_ID
         JOIN c_bpartner pbp ON p.c_bpartner_id = pbp.c_bpartner_id

WHERE p.docstatus IN ('CO', 'CL')
  AND i.docstatus IN ('CO', 'CL')
  AND (i.dateinvoiced::date >= DateInvoicedFrom::date)
  AND (i.dateinvoiced::date <= DateInvoicedTo::date)

    ;

$BODY$
    LANGUAGE sql
    STABLE
;


-- SELECT *
-- FROM getInvoicePaymentAllocations_Report('2023-01-01', '2025-06-25')
-- ;

