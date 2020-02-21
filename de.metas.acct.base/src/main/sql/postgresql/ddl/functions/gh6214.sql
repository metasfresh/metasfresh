-- todo new function name: BusinessPartnerOpenAmountToDate ; this is used to get the invoice open amount, instead of the big heavy call to OpenItems_Report


-- todo name this Partner Account Sheet
DROP FUNCTION IF EXISTS gh6214(p_c_bpartner_id numeric, p_dateFrom date, p_dateTo date, p_ad_client_id numeric, p_ad_org_id numeric, p_isSoTrx TEXT);

CREATE OR REPLACE FUNCTION gh6214(p_c_bpartner_id numeric,
                                  p_dateFrom      date,
                                  p_dateTo        date,
                                  p_ad_client_id  numeric,
                                  p_ad_org_id     numeric = NULL,
                                  p_isSoTrx       TEXT = 'Y')
    RETURNS table
            (
                beginningBalance NUMERIC,
                amount           NUMERIC,
                endingBalance    NUMERIC,
                dateAcct         DATE,
                doctype          TEXT,
                documentno       TEXT,
                description      TEXT,
                currency         TEXT
            )
AS
$BODY$
BEGIN

    DROP TABLE IF EXISTS temp_gh6214;
    CREATE TEMPORARY TABLE temp_gh6214
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
    INTO temp_gh6214(beginningBalance,
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


    --
    -- Update the amount to be in the base currency -- todo maybe this can be merged into the insert step
    UPDATE temp_gh6214 t
    SET amount = (SELECT currencybase(t.amount, t.c_currency_id_original, t.dateacct, p_ad_client_id, t.ad_org_id));


    --
    -- Update the amount according to the document type
    -- todo (this one  uses some case statements)


    --
    -- Update the beginning and end balances with the initial "Open Invoice Amount to Date"
    UPDATE temp_gh6214
    SET beginningBalance = t.OpenInvoiceAmountToDate,
        endingBalance    = t.OpenInvoiceAmountToDate
    FROM (
             SELECT --
                    sum((openItems).openamt) OpenInvoiceAmountToDate
             FROM de_metas_endcustomer_fresh_reports.OpenItems_Report((p_dateFrom - INTERVAL '1 days')::date) openItems -- dateFrom
         ) t;


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
                 FROM temp_gh6214 t
             ),
         finalData AS
             (
                 SELECT --
                        ebs.rowid,
                        ebs.endingBalance,
                        ebs.endingBalance - ebs.currentAmount beginningBalance
                 FROM endingBalanceSum ebs
             )
    UPDATE temp_gh6214 t
    SET endingBalance    = d.endingBalance,
        beginningBalance = d.beginningBalance
    FROM finalData d
    WHERE t.rowid = d.rowid;


    --
    -- return the data
    RETURN QUERY SELECT --
                        t.beginningBalance,
                        t.amount,
                        t.endingBalance,
                        t.dateAcct,
                        NULL::TEXT      doctype, -- todo
                        t.documentno,
                        t.description,
                        orgCurrencyCode currency -- todo
                 FROM temp_gh6214 t;

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
      doctype,
      documentno,
      description,
      currency
FROM gh6214(NULL,
            '1111-1-1'::date,
            '3333-1-1'::date,
            1000000)
ORDER BY dateacct, documentno
;



--------
--------
--------
--------
--------
--------
--------

-- SELECT docbasetype, *
-- FROM c_doctype
-- WHERE docbasetype in ('ARC', 'APC')
--
-- ARC = Credit memo (receipt)
-- APC = Credit memo (payment)

--
-- --
-- -- generic group by for anything you may want
-- SELECT c_bpartner_id, count(1)
-- FROM c_invoice
-- GROUP BY c_bpartner_id
-- ORDER BY 2 DESC
-- ;
--

