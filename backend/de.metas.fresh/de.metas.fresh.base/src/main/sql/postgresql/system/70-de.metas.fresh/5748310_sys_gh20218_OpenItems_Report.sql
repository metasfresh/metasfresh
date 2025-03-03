DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report
(
    Reference_Date  date,
    switchDate      character varying,
    p_C_BPartner_ID numeric(10)
)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.OpenItems_Report(p_reference_date date DEFAULT NOW(),
                                                                    p_switchdate     character varying DEFAULT 'N'::character varying,
                                                                    p_c_bpartner_id  numeric DEFAULT NULL::numeric)
    RETURNS TABLE
            (
                currencycode          char(3),
                documentno            varchar,
                dateinvoiced          date,
                dateacct              date,
                netdays               numeric,
                discountdays          numeric,
                duedate               date,
                daysdue               integer,
                discountdate          date,
                grandtotal            numeric,
                paidamt               numeric,
                openamt               numeric,
                c_currency_id         numeric,
                bpvalue               varchar,
                bpname                varchar,
                ExternalID            character varying,
                c_bpartner_id         numeric,
                isinpayselection      boolean,
                c_invoice_id          numeric,
                issotrx               char,
                ad_org_id             numeric,
                ad_client_id          numeric,
                invoicecollectiontype char,
                reference_date        date,
                grandtotalconvert     numeric,
                openamtconvert        numeric,
                main_iso_code         char(3)
            )
AS
$$
SELECT c.iso_code,
       oi.DocumentNo,
       oi.DateInvoiced::date,
       oi.DateAcct::date,
       oi.NetDays,
       oi.DiscountDays,
       oi.DueDate::date,
       oi.DaysDue,
       oi.DiscountDate::date,
       oi.GrandTotal,
       oi.GrandTotal - oi.OpenAmt                    AS PaidAmt,
       oi.OpenAmt,
       oi.C_Currency_ID,
       --
       bp.Value                                      AS BPValue,
       bp.Name                                       AS BPName,
       report.getPartnerExternalID(bp.C_BPartner_ID) AS ExternalID,
       bp.C_BPartner_ID,
       --
       -- Filtering columns
       oi.IsInPaySelection,
       oi.C_Invoice_ID,
       oi.IsSOTrx,
       oi.AD_Org_ID,
       oi.AD_Client_ID,
       oi.InvoiceCollectionType,
       p_reference_date                              AS Reference_Date,
       -- foreign currency
       (CASE
            WHEN oi.main_currency != oi.C_Currency_ID
                THEN oi.GrandTotal *
                     (SELECT currencyrate FROM fact_acct WHERE record_id = oi.c_invoice_id AND ad_table_id = get_table_id('C_Invoice') LIMIT 1)
        END)                                         AS grandtotalconvert,
       (CASE
            WHEN oi.main_currency != oi.C_Currency_ID
                THEN oi.OpenAmt *
                     (SELECT currencyrate FROM fact_acct WHERE record_id = oi.c_invoice_id AND ad_table_id = get_table_id('C_Invoice') LIMIT 1)
        END)                                         AS openamtconvert,

       (CASE
            WHEN oi.main_currency != oi.C_Currency_ID
                THEN oi.main_iso_code
        END)                                         AS main_iso_code
FROM (SELECT i.AD_Org_ID,
             i.AD_Client_ID,
             i.DocumentNo,
             i.C_BPartner_ID,
             i.IsSOTrx,
             i.DateInvoiced,
             i.DateAcct,
             EXISTS (SELECT 0
                     FROM C_PaySelectionLine psl
                              JOIN C_PaySelection ps ON psl.C_PaySelection_ID = ps.C_PaySelection_ID
                     WHERE ps.docstatus IN ('CO', 'CL')
                       AND psl.C_Invoice_ID = i.C_Invoice_ID)                                                                AS IsInPaySelection,
             COALESCE(p.NetDays, DaysBetween(i.DueDate::timestamp with time zone, i.DateInvoiced::timestamp with time zone)) AS NetDays,
             p.DiscountDays,
             i.DueDate                                                                                                       AS DueDate,
             COALESCE(
                     EXTRACT(DAY FROM (TRUNC(p_reference_date) - i.DueDate)),
                     DaysBetween(p_reference_date, ips.DueDate::timestamp with time zone)
             )::integer                                                                                                      AS DaysDue,
             COALESCE(AddDays(i.DateInvoiced::timestamp with time zone, p.DiscountDays), ips.DiscountDate)                   AS DiscountDate,
             COALESCE(ROUND(i.GrandTotal * p.Discount / 100::numeric, 2), ips.DiscountAmt)                                   AS DiscountAmt,
             COALESCE(ips.DueAmt, i.GrandTotal)                                                                              AS GrandTotal,

             (SELECT openamt
              FROM invoiceOpenToDate(
                      i.C_Invoice_ID
                  , COALESCE(ips.C_InvoicePaySchedule_ID, 0::numeric)
                  , (CASE WHEN p_switchdate = 'Y' THEN 'A' ELSE 'T' END)
                  , p_reference_date))                                                                                       AS OpenAmt,
             i.InvoiceCollectionType,
             i.C_Currency_ID,
             i.C_Invoice_ID,
             i.MultiplierAP,
             c.C_Currency_ID                                                                                                 AS main_currency,
             c.iso_code                                                                                                      AS main_iso_code
      FROM C_Invoice_v i
               LEFT OUTER JOIN C_PaymentTerm p ON i.C_PaymentTerm_ID = p.C_PaymentTerm_ID
               LEFT OUTER JOIN C_InvoicePaySchedule ips ON i.C_Invoice_ID = ips.C_Invoice_ID AND ips.isvalid = 'Y'

               LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID = i.ad_client_id
               LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
               LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID = c.C_Currency_ID


      WHERE TRUE
        AND i.DocStatus IN ('CO', 'CL', 'RE')
        AND (p_c_bpartner_id IS NULL OR i.C_BPartner_ID = p_c_bpartner_id)
         --
     ) AS oi
         INNER JOIN C_BPartner bp ON oi.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_Currency c ON oi.C_Currency_ID = c.C_Currency_ID
WHERE TRUE
  AND oi.OpenAmt <> 0
$$
    LANGUAGE sql STABLE
;

