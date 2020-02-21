-- todo name this Partner Account Sheet
CREATE OR REPLACE FUNCTION gh6214(p_c_bpartner_id numeric,
                                  p_dateFrom date,
                                  p_dateTo date,
                                  p_ad_client_id numeric,
                                  p_ad_org_id numeric = NULL,
                                  p_isSoTrx TEXT = 'Y')
    RETURNS table
            (
                beginningBalance numeric,
                endingBalance    numeric,
                dateAcct         date,
                doctype          TEXT,
                documentno       text,
                description      text,
                amount           NUMERIC,
                currency         text
            )
AS
$BODY$

DROP TABLE IF EXISTS temp_gh6241;
    CREATE TEMPORARY TABLE temp_gh6241
    (
        beginningBalance       numeric,
        amount                 numeric,
        endingBalance          numeric,
        dateacct               date,
        description            text,
        c_doctype_id           numeric,
        documentno             text,
        created                timestamp,
        c_currency_id_original numeric,
        rowid                  numeric,
        ad_org_id              numeric
    )
    ;

    WITH invoicesAndPaymentsInPeriod AS
             (
                 SELECT --
                        0                                      beginningBalance,
                        i.grandtotal                           amount,
                        0                                      endingBalance,
                        i.dateacct                             dateacct,
                        coalesce(i.poreference, i.description) description,
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
                   AND (coalesce(p_ad_org_id, 0) <= 0 OR i.ad_org_id = p_ad_org_id)
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
                   AND (coalesce(p_ad_org_id, 0) <= 0 OR p.ad_org_id = p_ad_org_id)
             )
    INSERT
    INTO temp_gh6241(beginningBalance,
                     amount,
                     endingBalance,
                     dateacct,
                     description,
                     c_doctype_id,
                     documentno,
                     created,
                     c_currency_id_original,
                     rowid,
                     ad_org_id)
    SELECT--
          beginningBalance,
          amount,
          endingBalance,
          dateacct,
          description,
          c_doctype_id,
          documentno,
          created,
          c_currency_id,
          row_number() OVER (),
          ad_org_id
    FROM invoicesAndPaymentsInPeriod
    ;

--
-- Update the amount to be in the base currency
    UPDATE temp_gh6241
    SET amount = (SELECT currencybase(amount, c_currency_id_original, dateacct, p_ad_client_id, p_ad_org_id));
-- todo @ teo: is it correct to set conversion date to dateacct?

--
-- Update the amount according to the document type
-- todo (this one  uses some case statements)

--
-- Update the beginning and end balances with the initial "Open Invoice Amount to Date"
    UPDATE temp_gh6241
    SET beginningBalance = t.OpenInvoiceAmountToDate,
        endingBalance    = t.OpenInvoiceAmountToDate
    FROM (
             SELECT --
                    sum((openItems).openamt) OpenInvoiceAmountToDate
             FROM de_metas_endcustomer_fresh_reports.OpenItems_Report(('3111-07-07'::date - INTERVAL '1 days')::date) openItems -- dateFrom
         ) t
    ;


--
-- Compute rolling sum
    WITH endingBalanceSum AS
             (
                 SELECT --
                        t.rowid,
--t.endingBalance
-- +
                        sum(t.amount) OVER
                            (
                            ORDER BY t.dateacct, t.created, t.documentno ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                            )    endingBalance,
                        t.amount currentAmount
                 FROM temp_gh6241 t
             ),
         finalData AS
             (
                 SELECT --
                        rowid,
                        endingBalance,
                        endingBalance - currentAmount beginningBalance
                 FROM endingBalanceSum
             )
    UPDATE temp_gh6241 t
    SET endingBalance    = d.endingBalance,
        beginningBalance = d.beginningBalance
    FROM finalData d
    WHERE t.rowid = d.rowid
    ;


    SELECT NULL;

    $BODY$
    LANGUAGE SQL STABLE;


SELECT beginningBalance,
       amount,
       endingBalance,
       beginningBalance + amount AS checkk,
       rowid,
       dateacct,
       description,
       c_doctype_id,
       documentno,
       created,
       c_currency_id_original
FROM gh6214(p_c_bpartner_id,
            dateFrom,
            dateTo,
            'Y')
ORDER BY dateacct, created, documentno
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
