DROP FUNCTION IF EXISTS sepaExportGroupedInvoicesReport(numeric)
;

CREATE OR REPLACE FUNCTION sepaExportGroupedInvoicesReport(IN p_SEPA_Export_ID numeric)

    RETURNS TABLE
            (
                VendorName        character varying,
                InvoiceDocumentNo character varying,
                InvoiceDate       timestamp without time zone,
                PayAmt            numeric,
                TotalAmt          numeric,
                IBAN              character varying,
                Currency          char(3),
                Warnings          character varying
            )
AS

$BODY$

SELECT vendor.name                 AS VendorName,
       invoice.documentno          AS InvoiceDocumentNo,
       invoice.dateinvoiced        AS InvoiceDate,
       COALESCE(ref.amt, line.amt) AS PayAmt,
       line.amt                    AS TotalAmt,
       line.iban                   AS IBAN,
       currency.iso_code           AS Currency,
       line.errormsg               AS Warnings

FROM sepa_export export
         LEFT JOIN sepa_export_line line ON export.SEPA_Export_ID = line.SEPA_Export_ID
         LEFT JOIN sepa_export_line_ref ref ON line.SEPA_Export_Line_ID = ref.SEPA_Export_Line_ID AND ref.ad_table_id = get_table_id('C_PaySelectionLine')
         LEFT JOIN c_currency currency ON currency.c_currency_id = line.c_currency_id
         LEFT JOIN c_payselectionline paySelectionLine ON paySelectionLine.c_payselectionline_id = COALESCE(ref.record_id, line.record_id)
         LEFT JOIN c_bpartner vendor ON vendor.c_bpartner_id = paySelectionLine.c_bpartner_id
         LEFT JOIN c_invoice invoice ON invoice.c_invoice_id = paySelectionLine.c_invoice_id

WHERE export.SEPA_Export_ID = p_SEPA_Export_ID

$BODY$
    LANGUAGE sql STABLE
;
