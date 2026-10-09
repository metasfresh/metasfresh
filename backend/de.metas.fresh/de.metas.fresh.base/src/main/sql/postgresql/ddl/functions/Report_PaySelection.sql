DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Report_PaySelection(numeric,
                                                                               numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Report_PaySelection(
    IN p_Record_ID     numeric,
    IN p_C_BPartner_ID numeric DEFAULT NULL
)
    RETURNS TABLE
            (
                org_name              text,
                PayDate               timestamp with time zone,
                C_PaySelectionLine_ID numeric,
                C_BPartner_ID         numeric,
                BPartner              text,
                line                  numeric,
                Description           text,
                DocDate               timestamp with time zone,
                I_DocNo               text,
                U_DocNo               text,
                NetDays               numeric,
                DiscountDays          numeric,
                DueDate               timestamp with time zone,
                DaysDue               numeric,
                DiscountDate          timestamp with time zone,
                DiscountAmt           numeric,
                PaidAmt               numeric,
                OpenAmt               numeric,
                GrandTotal            numeric,
                ISO_Code              text,
                ActualDiscountAmt     numeric,
                beforePay             numeric,
                AfterPay              numeric,
                PayAmt                numeric,
                isDraft               boolean
            )
AS
$$
SELECT COALESCE(org.name, '')                                       AS org_name,
       ps.PayDate,
       psl.C_PaySelectionLine_ID,
       bp.C_BPartner_ID,
       CASE
           WHEN LENGTH(bp.value || ' ' || bp.name) > 37
               THEN SUBSTRING(bp.value || ' ' || bp.name FOR 36) || '...'
               ELSE bp.value || ' ' || bp.name
       END                                                          AS BPartner,
       psl.line,
       dt.printname || ': ' || COALESCE(i.Documentno, p.documentno) AS Description,
       COALESCE(i.DateInvoiced, p.datetrx)                          AS DocDate,
       i.poreference                                                AS I_DocNo,
       COALESCE(i.Documentno, p.documentno)                         AS U_DocNo,

       COALESCE(pt.NetDays, 0)                                      AS NetDays,
       COALESCE(pt.DiscountDays, 0)                                 AS DiscountDays,

       CASE
           WHEN psl.original_payment_id > 0 THEN NULL::timestamp with time zone
                                            ELSE i.DueDate
       END                                                          AS DueDate,

       CASE
           WHEN psl.original_payment_id > 0 THEN NULL
                                            ELSE EXTRACT(DAY FROM TRUNC(ps.PayDate) - TRUNC(i.DueDate))
       END                                                          AS DaysDue,

       CASE
           WHEN psl.original_payment_id > 0 THEN NULL::timestamp with time zone
                                            ELSE AddDays(i.DateInvoiced, pt.DiscountDays)
       END                                                          AS DiscountDate,

       CASE
           WHEN psl.original_payment_id > 0 THEN 0
                                            ELSE ROUND(i.GrandTotal * pt.Discount / 100.0, 2)
       END                                                          AS DiscountAmt,

       CASE
           WHEN psl.original_payment_id > 0 THEN COALESCE(rp.payamt, 0)
                                            ELSE InvoicePaid(i.C_Invoice_ID, i.C_Currency_ID, 1)
       END                                                          AS PaidAmt,

       CASE
           WHEN psl.original_payment_id > 0 THEN COALESCE(rp.payamt, 0) - COALESCE(p.payamt, 0)
                                            ELSE InvoiceOpenToDate(i.C_Invoice_ID, 0, ps.PayDate)
       END                                                          AS OpenAmt,

       CASE
           WHEN psl.original_payment_id > 0 THEN COALESCE(p.payamt, 0)
                                            ELSE i.GrandTotal
       END                                                          AS GrandTotal,

       c.ISO_Code,
       COALESCE(psl.DiscountAmt, 0)                                 AS ActualDiscountAmt,
       COALESCE(psl.OpenAmt, 0)                                     AS beforePay,
       COALESCE(psl.DifferenceAmt, 0)                               AS AfterPay,
       COALESCE(psl.PayAmt, 0)                                      AS PayAmt,
       ps.processed != 'Y'                                          AS isDraft

FROM C_PaySelection ps
         LEFT JOIN AD_Org org ON org.AD_Org_ID = ps.AD_Org_ID
         LEFT JOIN C_PaySelectionLine psl ON ps.C_PaySelection_ID = psl.C_PaySelection_ID
         LEFT JOIN C_Invoice i ON psl.C_Invoice_ID = i.C_Invoice_ID
         LEFT JOIN C_Payment p ON psl.original_payment_id = p.C_Payment_ID
         LEFT JOIN C_Payment rp ON psl.C_Payment_ID = rp.C_Payment_ID
         LEFT JOIN C_PaymentTerm pt ON i.C_PaymentTerm_ID = pt.C_PaymentTerm_ID
         LEFT JOIN C_DocType dt ON COALESCE(i.C_DocType_ID, p.C_DocType_ID) = dt.C_DocType_ID
         LEFT JOIN C_Currency c ON COALESCE(i.C_Currency_ID, p.C_Currency_ID) = c.C_Currency_ID
         LEFT JOIN C_BPartner bp ON COALESCE(i.C_BPartner_ID, psl.C_BPartner_ID) = bp.C_BPartner_ID

WHERE ps.C_PaySelection_ID = p_Record_ID
  AND (p_C_BPartner_ID IS NULL OR bp.C_BPartner_ID = p_C_BPartner_ID)

ORDER BY c.ISO_Code, bp.name, i.DateInvoiced;
$$
    LANGUAGE sql
    STABLE
    PARALLEL SAFE
;
