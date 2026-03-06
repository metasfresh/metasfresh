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
                PromotionCode1            varchar,
                PromotionCode1Description varchar,
                PromotionCode2            varchar,
                PromotionCode2Description varchar,
                OrderDocumentNo           varchar,
                DateOrdered               date,
                CustomerNo                varchar,
                CustomerName              varchar,
                TotalNetAmt               numeric,
                TotalGrossAmt             numeric,
                TotalTaxAmt               numeric,
                Invoiced                  char(1),
                InvoiceDocumentNo         varchar,
                DateInvoiced              date,
                InvoiceCustomerNo         varchar,
                InvoiceCustomerName       varchar
            )
AS
$$
SELECT pc1.Value                                                       AS PromotionCode1,
       pc1.Description                                                 AS PromotionCode1Description,
       pc2.Value                                                       AS PromotionCode2,
       pc2.Description                                                 AS PromotionCode2Description,
       o.DocumentNo                                                    AS OrderDocumentNo,
       o.DateOrdered::date                                             AS DateOrdered,
       bp_order.Value                                                  AS CustomerNo,
       bp_order.Name                                                   AS CustomerName,
       COALESCE(SUM(ic.NetAmtToInvoice), 0)                           AS TotalNetAmt,
       COALESCE(SUM(ic.NetAmtToInvoice + ic.SplitAmt), 0)             AS TotalGrossAmt,
       COALESCE(SUM(ic.SplitAmt), 0)                                   AS TotalTaxAmt,
       CASE WHEN i.C_Invoice_ID IS NOT NULL THEN 'Y' ELSE 'N' END     AS Invoiced,
       i.DocumentNo                                                    AS InvoiceDocumentNo,
       i.DateInvoiced::date                                            AS DateInvoiced,
       bp_inv.Value                                                    AS InvoiceCustomerNo,
       bp_inv.Name                                                     AS InvoiceCustomerName
FROM C_Order o
         LEFT JOIN C_PromotionCode pc1 ON o.C_PromotionCode_ID = pc1.C_PromotionCode_ID
         LEFT JOIN C_PromotionCode pc2 ON o.C_PromotionCode2_ID = pc2.C_PromotionCode_ID
         JOIN C_BPartner bp_order ON o.C_BPartner_ID = bp_order.C_BPartner_ID
         LEFT JOIN C_Invoice_Candidate ic ON ic.C_Order_ID = o.C_Order_ID AND ic.IsActive = 'Y'
         LEFT JOIN C_Invoice_Line_Alloc ila ON ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ila.IsActive = 'Y'
         LEFT JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID AND il.IsActive = 'Y'
         LEFT JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.IsActive = 'Y'
         LEFT JOIN C_BPartner bp_inv ON i.C_BPartner_ID = bp_inv.C_BPartner_ID
WHERE o.IsActive = 'Y'
  AND o.IsSOTrx = 'Y'
  AND (o.C_PromotionCode_ID IS NOT NULL OR o.C_PromotionCode2_ID IS NOT NULL)
  AND (p_C_PromotionCode_ID IS NULL OR o.C_PromotionCode_ID = p_C_PromotionCode_ID)
  AND (p_C_PromotionCode2_ID IS NULL OR o.C_PromotionCode2_ID = p_C_PromotionCode2_ID)
  AND (p_Invoiced IS NULL
    OR (p_Invoiced = 'Y' AND i.C_Invoice_ID IS NOT NULL)
    OR (p_Invoiced = 'N' AND i.C_Invoice_ID IS NULL))
GROUP BY pc1.Value, pc1.Description, pc2.Value, pc2.Description, o.DocumentNo, o.DateOrdered,
         bp_order.Value, bp_order.Name,
         i.C_Invoice_ID, i.DocumentNo, i.DateInvoiced,
         bp_inv.Value, bp_inv.Name
ORDER BY o.DateOrdered DESC, o.DocumentNo
$$
    LANGUAGE sql STABLE;

COMMENT ON FUNCTION report.report_promotion_code_evaluation(numeric, numeric, char) IS
    'gh#28565: Promotion Code Evaluation Report — shows orders with promotion codes, their descriptions, net/gross/tax amounts, and invoice status';
