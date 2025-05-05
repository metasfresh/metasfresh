DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report(date);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report(date, character varying);
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report;

create table de_metas_endcustomer_fresh_reports.OpenItems_Report
(
    CurrencyCode char(3)
    , DocumentNo varchar
    , DateInvoiced date
    , DateAcct date
    , NetDays numeric
    , DiscountDays numeric
    , DueDate date
    , DaysDue integer
    , DiscountDate date
    , GrandTotal numeric
    , PaidAmt numeric
    , OpenAmt numeric
    , C_Currency_ID numeric
    --
    , BPValue varchar
    , BPName varchar
    , C_BPartner_ID numeric
    --
    , IsInPaySelection boolean
    , C_Invoice_ID numeric
    , IsSOTrx char(1)
    , AD_Org_ID numeric
    , AD_Client_ID numeric
    , InvoiceCollectionType char(1)
    , Reference_Date date

    ,grandtotalconvert numeric
    ,openamtconvert numeric
    ,main_iso_code char(3)
);


CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.OpenItems_Report(IN Reference_Date date DEFAULT now(), IN switchDate character varying DEFAULT 'N')
    RETURNS SETOF de_metas_endcustomer_fresh_reports.OpenItems_Report AS
$$
SELECT
    c.iso_code,
    oi.DocumentNo,
    oi.DateInvoiced::date,
    oi.DateAcct::date,
    oi.NetDays,
    oi.DiscountDays,
    oi.DueDate::date,
    oi.DaysDue,
    oi.DiscountDate::date,
    oi.GrandTotal,
    oi.GrandTotal - oi.OpenAmt AS PaidAmt,
    oi.OpenAmt,
    oi.C_Currency_ID,
    --
    bp.Value as BPValue,
    bp.Name as BPName,
    bp.C_BPartner_ID,
    --
    -- Filtering columns
    oi.IsInPaySelection,
    oi.C_Invoice_ID,
    oi.IsSOTrx,
    oi.AD_Org_ID,
    oi.AD_Client_ID,
    oi.InvoiceCollectionType,
    $1 as Reference_Date,
    -- foreign currency
    (case when oi.main_currency != oi.C_Currency_ID
              THEN oi.GrandTotal *
                   (select currencyrate from fact_acct where record_id = oi.c_invoice_id and ad_table_id = get_table_id('C_Invoice') and isActive = 'Y' limit 1 )
              ELSE NULL
     END) AS grandtotalconvert,
    (case when oi.main_currency != oi.C_Currency_ID
              THEN oi.OpenAmt *
                   (select currencyrate from fact_acct where record_id = oi.c_invoice_id and ad_table_id = get_table_id('C_Invoice') and isActive = 'Y' limit 1 )
              ELSE NULL
     END) AS openamtconvert,

    (case when oi.main_currency != oi.C_Currency_ID
              THEN oi.main_iso_code
              ELSE NULL
     END) AS main_iso_code
FROM
    (
        SELECT
            i.AD_Org_ID,
            i.AD_Client_ID,
            i.DocumentNo,
            i.C_BPartner_ID,
            i.IsSOTrx,
            i.DateInvoiced,
            i.DateAcct,
            EXISTS (SELECT 0 FROM C_PaySelectionLine psl
                                      JOIN C_PaySelection ps ON psl.C_PaySelection_ID = ps.C_PaySelection_ID AND ps.isActive = 'Y'
                    WHERE ps.docstatus IN ('CO','CL') AND psl.C_Invoice_ID = i.C_Invoice_ID AND psl.isActive = 'Y') AS IsInPaySelection,
            COALESCE ( p.NetDays, DaysBetween( i.DueDate::timestamp with time zone, i.DateInvoiced::timestamp with time zone ) ) AS NetDays,
            p.DiscountDays,
            i.DueDate AS DueDate,
            COALESCE(
                    EXTRACT(day from (TRUNC($1) - i.DueDate)),
                    DaysBetween($1, ips.DueDate::timestamp with time zone)
                )::integer AS DaysDue,
            COALESCE( AddDays( i.DateInvoiced::timestamp with time zone, p.DiscountDays ), ips.DiscountDate ) AS DiscountDate,
            COALESCE( Round( i.GrandTotal * p.Discount / 100::numeric, 2 ), ips.DiscountAmt ) AS DiscountAmt,
            COALESCE( ips.DueAmt, i.GrandTotal ) AS GrandTotal,

            (SELECT openamt FROM invoiceOpenToDate (
                    i.C_Invoice_ID
                , COALESCE ( ips.C_InvoicePaySchedule_ID, 0::numeric )
                , (CASE WHEN $2='Y' THEN 'A' ELSE 'T' END)
                , $1 )
            )AS OpenAmt,
            i.InvoiceCollectionType,
            i.C_Currency_ID,
            i.C_Invoice_ID,
            i.MultiplierAP,
            c.C_Currency_ID as main_currency,
            c.iso_code as main_iso_code
        FROM
            C_Invoice_v i
                LEFT OUTER JOIN C_PaymentTerm p ON i.C_PaymentTerm_ID = p.C_PaymentTerm_ID AND p.isActive = 'Y'
                LEFT OUTER JOIN C_InvoicePaySchedule ips ON i.C_Invoice_ID = ips.C_Invoice_ID AND ips.isvalid = 'Y' AND ips.isActive = 'Y'

                LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID=i.ad_client_id  AND ci.isActive = 'Y'
                LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID AND acs.isActive = 'Y'
                LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID AND c.isActive = 'Y'


        WHERE true
          AND i.DocStatus IN ('CO','CL','RE') AND i.isActive = 'Y'
    )as oi
        INNER JOIN C_BPartner bp 	ON oi.C_BPartner_ID = bp.C_BPartner_ID
        INNER JOIN C_Currency c 	ON oi.C_Currency_ID = c.C_Currency_ID
WHERE true
  and oi.OpenAmt <> 0

$$
    LANGUAGE sql STABLE
;
