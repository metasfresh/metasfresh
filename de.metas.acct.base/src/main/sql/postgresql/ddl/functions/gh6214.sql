DROP FUNCTION IF EXISTS BusinessPartnerAccountSheetReport(p_c_bpartner_id numeric, p_dateFrom date, p_dateTo date, p_ad_client_id numeric, p_ad_org_id numeric, p_isSoTrx TEXT);

CREATE OR REPLACE FUNCTION BusinessPartnerAccountSheetReport(p_c_bpartner_id numeric,
                                                             p_dateFrom      date,
                                                             p_dateTo        date,
                                                             p_ad_client_id  numeric,
                                                             p_ad_org_id     numeric = NULL,
                                                             p_isSoTrx       TEXT = 'Y')
    RETURNS table
            (
                dateAcct         DATE,
                DocumentType     TEXT,
                documentno       TEXT,
                beginningBalance NUMERIC,
                amount           NUMERIC,
                endingBalance    NUMERIC,
                currencyCode     TEXT,
                description      TEXT
            )
AS
$BODY$
DECLARE
    v_time timestamp;
    v_temp numeric;

BEGIN
    v_time := logDebug('start');

    DROP TABLE IF EXISTS temp_BusinessPartnerAccountSheetReport;
    CREATE TEMPORARY TABLE temp_BusinessPartnerAccountSheetReport
    (
        beginningBalance       NUMERIC,
        amount                 NUMERIC,
        endingBalance          NUMERIC,
        dateacct               date,
        description            TEXT,
        c_doctype_id           NUMERIC,
        documentno             TEXT,
        created                TIMESTAMP,
        c_currency_id_original NUMERIC,
        orgCurrencyCode        text,
        rowid                  NUMERIC,
        ad_org_id              NUMERIC
    );
    v_time := logDebug('created empty temporary table', v_time);


    --
    -- insert working data
    WITH invoicesAndPaymentsInPeriod AS
             (
                 SELECT --
                        0                                      beginningBalance,
                        i.grandtotal                           amount,
                        0                                      endingBalance,
                        i.dateacct                             dateacct,
                        COALESCE(i.poreference, i.description) description,
                        i.c_doctype_id                         c_doctype_id,
                        i.documentno                           documentno,
                        i.created                              created,
                        i.c_currency_id                        c_currency_id,
                        i.ad_org_id                            ad_org_id
                 FROM c_invoice i
                 WHERE TRUE
                   -- AND i.c_bpartner_id = p_c_bpartner_id -- todo
                   AND i.dateacct >= p_dateFrom
                   AND i.dateacct <= p_dateTo
                   AND i.issotrx = p_isSoTrx
                   AND i.docstatus IN ('CO', 'CL')
                   AND (COALESCE(p_ad_org_id, 0) <= 0 OR i.ad_org_id = p_ad_org_id)
                 UNION ALL
                 SELECT --
                        0               beginningBalance,
                        p.payamt        amount,
                        0               endingBalance,
                        p.dateacct      dateacct,
                        p.description   description,
                        p.c_doctype_id  c_doctype_id,
                        p.documentno    documentno,
                        p.created       created,
                        p.c_currency_id c_currency_id,
                        p.ad_org_id     ad_org_id
                 FROM c_payment p
                 WHERE TRUE
                   -- AND p.c_bpartner_id = p_c_bpartner_id -- todo
                   AND p.dateacct >= p_dateFrom
                   AND p.dateacct <= p_dateTo
                   AND p.isreceipt = p_isSoTrx
                   AND p.docstatus IN ('CO', 'CL')
                   AND (COALESCE(p_ad_org_id, 0) <= 0 OR p.ad_org_id = p_ad_org_id)
             )
    INSERT
    INTO temp_BusinessPartnerAccountSheetReport(beginningBalance,
                                                amount,
                                                endingBalance,
                                                dateacct,
                                                description,
                                                c_doctype_id,
                                                documentno,
                                                created,
                                                c_currency_id_original,
                                                rowid,
                                                ad_org_id,
                                                orgCurrencyCode)
    SELECT--
          i.beginningBalance,
          i.amount,
          i.endingBalance,
          i.dateacct,
          i.description,
          i.c_doctype_id,
          i.documentno,
          i.created,
          i.c_currency_id,
          row_number() OVER (),
          i.ad_org_id,
          (SELECT iso_code
           FROM c_currency c
                    INNER JOIN c_acctschema accts ON c.c_currency_id = accts.c_currency_id
                    INNER JOIN ad_clientinfo ac ON accts.c_acctschema_id = ac.c_acctschema1_id
           LIMIT 1) orgCurrencyCode
    FROM invoicesAndPaymentsInPeriod i;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('inserted invoices and payments: ' || v_temp || ' records', v_time);


    --
    -- Update the amount to be in the base currency
    UPDATE temp_BusinessPartnerAccountSheetReport t
    SET amount = (SELECT currencybase(t.amount, t.c_currency_id_original, t.dateacct, p_ad_client_id, t.ad_org_id));

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('Update amount to base currency', v_time);


    --
    -- Update the amount according to the document type
    WITH correctAmounts AS
             (
                 SELECT --
                        t.rowid,
                        (CASE
                             WHEN dt.docbasetype IN ('ARC', 'APC') THEN -1 * t.amount
                                                                   ELSE t.amount
                         END) amount
                 FROM temp_BusinessPartnerAccountSheetReport t
                          INNER JOIN c_doctype dt ON t.c_doctype_id = dt.c_doctype_id
             )
    UPDATE temp_BusinessPartnerAccountSheetReport t
    SET amount = c.amount
    FROM correctAmounts c
    WHERE c.rowid = t.rowid;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('Update amount by document type', v_time);


    --
    -- Update the beginning and end balances with the initial "Open Invoice Amount to Date"
    UPDATE temp_BusinessPartnerAccountSheetReport
    SET beginningBalance = t.OpenInvoiceAmountToDate,
        endingBalance    = t.OpenInvoiceAmountToDate
    FROM (
             SELECT --
                    sum((openItems).openamt) OpenInvoiceAmountToDate
             FROM de_metas_endcustomer_fresh_reports.OpenItems_Report((p_dateFrom - INTERVAL '1 days')::date) openItems -- dateFrom
         ) t;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('Update beginning and end balance with "Open Invoices Amount to Date"', v_time);


    --
    -- Compute rolling sum
    WITH endingBalanceSum AS
             (
                 SELECT --
                        t.rowid,
                        -- t.endingBalance
                        -- +
                        sum(t.amount) OVER ( ORDER BY t.dateacct, t.created, t.documentno ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW ) endingBalance,
                        t.amount                                                                                                             currentAmount
                 FROM temp_BusinessPartnerAccountSheetReport t
             ),
         finalData AS
             (
                 SELECT --
                        ebs.rowid,
                        ebs.endingBalance,
                        ebs.endingBalance - ebs.currentAmount beginningBalance
                 FROM endingBalanceSum ebs
             )
    UPDATE temp_BusinessPartnerAccountSheetReport t
    SET endingBalance    = d.endingBalance,
        beginningBalance = d.beginningBalance
    FROM finalData d
    WHERE t.rowid = d.rowid;

    v_time := logDebug('finished calculating rolling sum', v_time);


    --
    -- return the data
    RETURN QUERY SELECT --
                        t.dateAcct,
                        (SELECT dtt.name
                         FROM c_doctype dt
                                  INNER JOIN c_doctype_trl dtt ON dt.c_doctype_id = dtt.c_doctype_id
                         WHERE dtt.ad_language = 'en_US'
                           AND t.c_doctype_id = dt.c_doctype_id
                        )::text         doctype,
                        t.documentno,
                        t.beginningBalance,
                        t.amount,
                        t.endingBalance,
                        orgCurrencyCode currency,
                        t.description
                 FROM temp_BusinessPartnerAccountSheetReport t
                 ORDER BY t.dateacct, t.created;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE;


SELECT--
      beginningBalance,
      amount,
      endingBalance,
      beginningBalance + amount AS checkk,
      dateacct,
      DocumentType,
      documentno,
      description,
      currencyCode
FROM BusinessPartnerAccountSheetReport(NULL,
                                       '1111-1-1'::date,
                                       '3333-1-1'::date,
                                       1000000)
ORDER BY dateacct, documentno
;

/*
NO bpartner filtering


V1. perf: all updates are individual steps

[2020-02-21 13:50:52] [00000] [2020-02-21 11:50:51.378825](0s) [Δ=0s]: start
[2020-02-21 13:50:52] [00000] [2020-02-21 11:50:51.384467](0s) [Δ=0s]: created empty temporary table
[2020-02-21 13:50:52] [00000] [2020-02-21 11:50:51.976961](1s) [Δ=1s]: inserted invoices and payments: 216375 records
[2020-02-21 13:50:58] [00000] [2020-02-21 11:50:57.366161](6s) [Δ=5s]: Update amount to base currency
[2020-02-21 13:50:58] [00000] [2020-02-21 11:50:58.225999](7s) [Δ=1s]: Update amount by document type
[2020-02-21 13:52:30] [00000] [2020-02-21 11:52:29.619975](99s) [Δ=92s]: Update beginning and end balances with "Open Invoices Amount to Date"
[2020-02-21 13:52:31] [00000] [2020-02-21 11:52:31.144835](100s) [Δ=1s]: finished calculating rolling sum
Total = 100s












































*/


