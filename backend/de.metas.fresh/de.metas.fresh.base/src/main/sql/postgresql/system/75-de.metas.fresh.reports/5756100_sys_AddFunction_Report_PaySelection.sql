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
SELECT (SELECT COALESCE(org.name, '')
        FROM AD_Org org

        WHERE org.ad_org_ID = ps.ad_org_ID)                         AS org_name,

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

       pt.NetDays,
       pt.DiscountDays,
       (CASE
            WHEN psl.original_payment_id > 0 THEN NULL ::timestamp with time zone
                                             ELSE i.DueDate::timestamp with time zone
        END)                                                        AS DueDate,
       (CASE
            WHEN psl.original_payment_id > 0 THEN NULL
                                             ELSE EXTRACT(DAY FROM (TRUNC(ps.PayDate) - i.DueDate))
        END)                                                        AS DaysDue,

       (CASE
            WHEN psl.original_payment_id > 0 THEN NULL ::timestamp with time zone
                                             ELSE AddDays(i.DateInvoiced::timestamp with time zone, pt.DiscountDays)
        END)                                                        AS DiscountDate,

       (CASE
            WHEN psl.original_payment_id > 0 THEN NULL
                                             ELSE ROUND(i.GrandTotal * pt.Discount / 100::numeric, 2)
        END)                                                        AS DiscountAmt,
       (CASE
            WHEN psl.original_payment_id > 0 THEN rp.payamt
                                             ELSE InvoicePaid(i.C_Invoice_ID, i.C_Currency_ID, 1::numeric)
        END)                                                        AS PaidAAmt,

       (CASE
            WHEN psl.original_payment_id > 0 THEN rp.payamt - p.payamt
                                             ELSE InvoiceOpenToDate(i.C_Invoice_ID, 0, ps.PayDate)
        END)                                                        AS OpenAmt,

       (CASE
            WHEN psl.original_payment_id > 0 THEN p.payamt
                                             ELSE i.GrandTotal
        END)                                                        AS GrandTotal,
       c.ISO_Code,
       psl.DiscountAmt                                              AS ActualDiscountAmt,
       psl.OpenAmt                                                  AS beforePay,
       psl.DifferenceAmt                                            AS AfterPay,
       psl.PayAmt                                                   AS PayAmt,
       ps.processed != 'Y'                                          AS isDraft
FROM
    -- Payselection records
    C_PaySelection ps
        LEFT OUTER JOIN C_PaySelectionLine psl ON ps.C_PaySelection_ID = psl.C_PaySelection_ID
        -- Documents
        LEFT OUTER JOIN C_Invoice i ON psl.C_Invoice_ID = i.C_Invoice_ID
        LEFT OUTER JOIN C_Payment p ON psl.original_payment_id = p.c_payment_id
        LEFT OUTER JOIN C_Payment rp ON psl.c_payment_id = rp.c_payment_id
        LEFT OUTER JOIN C_PaymentTerm pt ON i.C_PaymentTerm_ID = pt.C_PaymentTerm_ID
        LEFT OUTER JOIN C_DocType dt ON COALESCE(i.C_DocType_ID, p.c_doctype_id) = dt.C_DocType_ID
        LEFT OUTER JOIN C_Currency c ON COALESCE(i.C_Currency_ID, p.c_currency_id) = c.C_Currency_ID
        LEFT OUTER JOIN C_BPartner bp ON COALESCE(i.C_BPartner_ID, psl.c_bpartner_id) = bp.C_BPartner_ID
WHERE ps.C_PaySelection_ID = p_Record_ID
  AND (p_C_BPartner_ID IS NULL OR bp.C_BPartner_ID = p_C_BPartner_ID)

ORDER BY c.ISO_Code, bp.name, i.DateInvoiced;
$$
    LANGUAGE sql
    STABLE
;
