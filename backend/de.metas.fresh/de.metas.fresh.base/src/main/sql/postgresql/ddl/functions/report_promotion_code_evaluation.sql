DROP FUNCTION IF EXISTS report.report_promotion_code_evaluation(
    p_C_PromotionCode_ID  numeric,
    p_C_PromotionCode2_ID numeric,
    p_Invoiced            char(1)
);

CREATE OR REPLACE FUNCTION report.report_promotion_code_evaluation(
    p_C_PromotionCode_ID  numeric DEFAULT NULL,
    p_C_PromotionCode2_ID numeric DEFAULT NULL,
    p_Invoiced            char(1) DEFAULT NULL
)
    RETURNS TABLE
            (
                -- Column aliases = AD_Element.ColumnName or AD_Message.Value
                -- used by IsTranslateExcelHeaders to produce translated column titles
                "C_PromotionCode_ID"  varchar,   -- AD_Element 584619: Aktionskennzeichen / Promotion Code
                "ValidTo"             date,      -- AD_Element 618: Gültig bis / Valid to
                "C_PromotionCode2_ID" varchar,   -- AD_Element 584620: Aktionskennzeichen 2 / Promotion Code 2
                "ValidTo2"            date,      -- AD_Message 545641: Gültig bis 2 / Valid to 2
                "OrderDocumentNo"     varchar,   -- AD_Element: Auftragsnr. / Order Document No
                "DateOrdered"         date,      -- AD_Element 268: Auftragsdatum
                "CustomerNo"          varchar,   -- AD_Element 1261: Kundennummer / Customer No
                "Name"                varchar,   -- AD_Element 469: Name
                "GrandTotal"          numeric,   -- AD_Element 316: Summe Gesamt / Grand Total
                "IsInvoiced"          char(1),   -- AD_Element: Fakturiert / Invoiced
                "InvoiceDocumentNo"   varchar,   -- AD_Element: Rechnungsnummer / Invoice Document No
                "DateInvoiced"        date,      -- AD_Element 267: Rechnungsdatum
                "InvoiceGrandTotal"   numeric    -- AD_Message 545642: Rechnungssumme / Invoice Grand Total
            )
AS
$$
SELECT pc1.Value                                                       AS "C_PromotionCode_ID",
       pc1.ValidTo::date                                               AS "ValidTo",
       pc2.Value                                                       AS "C_PromotionCode2_ID",
       pc2.ValidTo::date                                               AS "ValidTo2",
       o.DocumentNo                                                    AS "OrderDocumentNo",
       o.DateOrdered::date                                             AS "DateOrdered",
       bp_order.Value                                                  AS "CustomerNo",
       bp_order.Name                                                   AS "Name",
       o.GrandTotal                                                    AS "GrandTotal",
       CASE WHEN inv.C_Invoice_ID IS NOT NULL THEN 'Y' ELSE 'N' END   AS "IsInvoiced",
       inv.DocumentNo                                                  AS "InvoiceDocumentNo",
       inv.DateInvoiced::date                                          AS "DateInvoiced",
       inv.GrandTotal                                                  AS "InvoiceGrandTotal"
FROM C_Order o
         LEFT JOIN C_PromotionCode pc1 ON o.C_PromotionCode_ID = pc1.C_PromotionCode_ID
         LEFT JOIN C_PromotionCode pc2 ON o.C_PromotionCode2_ID = pc2.C_PromotionCode_ID
         JOIN C_BPartner bp_order ON o.C_BPartner_ID = bp_order.C_BPartner_ID
         LEFT JOIN LATERAL (
    SELECT DISTINCT i.C_Invoice_ID, i.DocumentNo, i.DateInvoiced, i.GrandTotal
    FROM C_OrderLine ol
             JOIN C_Invoice_Candidate ic ON ic.C_OrderLine_ID = ol.C_OrderLine_ID AND ic.IsActive = 'Y'
             JOIN C_Invoice_Line_Alloc ila ON ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ila.IsActive = 'Y'
             JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID AND il.IsActive = 'Y'
             JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.IsActive = 'Y' AND i.DocStatus IN ('CO', 'CL')
    WHERE ol.C_Order_ID = o.C_Order_ID AND ol.IsActive = 'Y'
    ORDER BY i.DateInvoiced DESC, i.C_Invoice_ID DESC
    LIMIT 1
    ) inv ON TRUE
WHERE o.IsActive = 'Y'
  AND o.IsSOTrx = 'Y'
  AND o.DocStatus IN ('CO', 'CL')
  AND (o.C_PromotionCode_ID IS NOT NULL OR o.C_PromotionCode2_ID IS NOT NULL)
  AND (p_C_PromotionCode_ID IS NULL OR o.C_PromotionCode_ID = p_C_PromotionCode_ID)
  AND (p_C_PromotionCode2_ID IS NULL OR o.C_PromotionCode2_ID = p_C_PromotionCode2_ID)
  AND (p_Invoiced IS NULL
    OR (p_Invoiced = 'Y' AND inv.C_Invoice_ID IS NOT NULL)
    OR (p_Invoiced = 'N' AND inv.C_Invoice_ID IS NULL))
ORDER BY o.DateOrdered DESC, o.DocumentNo
$$
    LANGUAGE sql STABLE;

COMMENT ON FUNCTION report.report_promotion_code_evaluation(numeric, numeric, char) IS
    'gh#28565: Promotion Code Evaluation Report — shows completed/closed orders with promotion codes and invoice status. Column aliases use AD_Element/AD_Message keys for IsTranslateExcelHeaders.';
