DROP FUNCTION IF EXISTS BusinessPartnerAccountSheetReport(p_c_bpartner_id numeric, p_dateFrom date, p_dateTo date, p_ad_client_id numeric, p_ad_org_id numeric, p_isSoTrx TEXT, p_ad_language text);

CREATE OR REPLACE FUNCTION BusinessPartnerAccountSheetReport(p_c_bpartner_id numeric,
                                                             p_dateFrom      date,
                                                             p_dateTo        date,
                                                             p_ad_client_id  numeric,
                                                             p_ad_org_id     numeric = NULL,
                                                             p_isSoTrx       TEXT = 'Y',
                                                             p_ad_language   text = 'en_US')
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
        targetCurrencyCode     text,
        rowid                  NUMERIC,
        ad_org_id              NUMERIC,
        doctype                text
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
                   AND i.c_bpartner_id = p_c_bpartner_id
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
                   AND p.c_bpartner_id = p_c_bpartner_id
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
                                                targetCurrencyCode,
                                                doctype)
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
           LIMIT 1)  targetCurrencyCode,
          (SELECT dtt.name
           FROM c_doctype dt
                    INNER JOIN c_doctype_trl dtt ON dt.c_doctype_id = dtt.c_doctype_id
           WHERE dtt.ad_language = p_ad_language
             AND i.c_doctype_id = dt.c_doctype_id
          )::text AS docType
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
    -- Update the amount according to document base type
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
             SELECT getBPOpenAmtToDate(p_ad_client_id,
                                       p_ad_org_id,
                                       (p_dateFrom - INTERVAL '1 days')::date,
                                       p_c_bpartner_id,
                                       (SELECT c.c_currency_id
                                        FROM c_currency c
                                                 INNER JOIN c_acctschema accts ON c.c_currency_id = accts.c_currency_id
                                                 INNER JOIN ad_clientinfo ac ON accts.c_acctschema_id = ac.c_acctschema1_id
                                        LIMIT 1),
                                       'Y'::text,
                                       p_isSoTrx) OpenInvoiceAmountToDate
         ) t;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('Update beginning and end balance with "BP Open Invoices Amount to Date"', v_time);


    --
    -- Compute rolling sum
    WITH endingBalanceSum AS
             (
                 SELECT --
                        t.rowid,
                        t.endingBalance
                            + sum(t.amount) OVER ( ORDER BY t.dateacct, t.created, t.documentno ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW ) endingBalance,
                        t.amount                                                                                                                   currentAmount
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
                        t.doctype,
                        t.documentno,
                        t.beginningBalance,
                        t.amount,
                        t.endingBalance,
                        t.targetCurrencyCode,
                        t.description
                 FROM temp_BusinessPartnerAccountSheetReport t
                 ORDER BY t.dateacct, t.created, t.documentno;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE;


/*
How to run:

SELECT*
FROM BusinessPartnerAccountSheetReport(2000252,
                                       '1111-1-1'::date,
                                       '3333-1-1'::date,
                                       1000000)
;

*/


