-- 2026-03-08
-- gh#28565: Promotion Code Evaluation report — improve column structure
-- 1) Remove Description columns, add ValidTo dates
-- 2) Use AD_Element ColumnNames / AD_Message Values as SQL aliases for IsTranslateExcelHeaders
-- 3) Create AD_Messages for columns without existing AD_Element: ValidTo2, InvoiceGrandTotal

-- ============================================================
-- 1) AD_Message: ValidTo2 — header for second promotion code's valid-to date
-- ============================================================
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES (545641, 0, 0, 'Y', '2026-03-08 15:00', 100, '2026-03-08 15:00', 100,
        'ValidTo2', 'Gültig bis 2', 'I', 'D');

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 545641, 'Gültig bis 2', NULL,
       'N', 0, 0, '2026-03-08 15:00', 100, '2026-03-08 15:00', 100
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = 545641);

UPDATE AD_Message_Trl SET MsgText = 'Valid to 2', IsTranslated = 'Y', Updated = '2026-03-08 15:00', UpdatedBy = 100
WHERE AD_Message_ID = 545641 AND AD_Language = 'en_US';

-- ============================================================
-- 2) AD_Message: InvoiceGrandTotal — header for invoice total column
-- ============================================================
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES (545642, 0, 0, 'Y', '2026-03-08 15:00', 100, '2026-03-08 15:00', 100,
        'InvoiceGrandTotal', 'Rechnungssumme', 'I', 'D');

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 545642, 'Rechnungssumme', NULL,
       'N', 0, 0, '2026-03-08 15:00', 100, '2026-03-08 15:00', 100
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = 545642);

UPDATE AD_Message_Trl SET MsgText = 'Invoice Grand Total', IsTranslated = 'Y', Updated = '2026-03-08 15:00', UpdatedBy = 100
WHERE AD_Message_ID = 545642 AND AD_Language = 'en_US';

-- ============================================================
-- 3) Update the SQL function — remove descriptions, add ValidTo, use proper column aliases
-- ============================================================
DROP FUNCTION IF EXISTS report.report_promotion_code_evaluation(numeric, numeric, char);

CREATE OR REPLACE FUNCTION report.report_promotion_code_evaluation(
    p_C_PromotionCode_ID  numeric DEFAULT NULL,
    p_C_PromotionCode2_ID numeric DEFAULT NULL,
    p_Invoiced            char(1) DEFAULT NULL
)
    RETURNS TABLE
            (
                "C_PromotionCode_ID"  varchar,
                "ValidTo"             date,
                "C_PromotionCode2_ID" varchar,
                "ValidTo2"            date,
                "OrderDocumentNo"     varchar,
                "DateOrdered"         date,
                "CustomerNo"          varchar,
                "Name"                varchar,
                "GrandTotal"          numeric,
                "IsInvoiced"          char(1),
                "InvoiceDocumentNo"   varchar,
                "DateInvoiced"        date,
                "InvoiceGrandTotal"   numeric
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
    'gh#28565: Promotion Code Evaluation Report — column aliases use AD_Element/AD_Message keys for IsTranslateExcelHeaders';
