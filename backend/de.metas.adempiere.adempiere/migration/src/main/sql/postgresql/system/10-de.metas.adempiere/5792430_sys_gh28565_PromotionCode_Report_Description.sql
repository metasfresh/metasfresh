-- gh#28565: Rework report function:
-- - Include Description columns for promotion codes
-- - Filter only Completed/Closed orders (DocStatus IN CO,CL)
-- - Use LATERAL subquery to avoid cartesian product from IC->ILA->IL->Invoice joins
-- - Use order GrandTotal and invoice GrandTotal instead of unreliable SplitAmt-based calculations

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
                OrderGrandTotal           numeric,
                Invoiced                  char(1),
                InvoiceDocumentNo         varchar,
                DateInvoiced              date,
                InvoiceGrandTotal         numeric
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
       o.GrandTotal                                                    AS OrderGrandTotal,
       CASE WHEN inv.C_Invoice_ID IS NOT NULL THEN 'Y' ELSE 'N' END   AS Invoiced,
       inv.DocumentNo                                                  AS InvoiceDocumentNo,
       inv.DateInvoiced::date                                          AS DateInvoiced,
       inv.GrandTotal                                                  AS InvoiceGrandTotal
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
    'gh#28565: Promotion Code Evaluation Report — shows completed/closed orders with promotion codes, their descriptions, and invoice status';
