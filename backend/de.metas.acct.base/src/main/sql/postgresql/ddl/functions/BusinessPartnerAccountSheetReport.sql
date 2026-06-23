DROP FUNCTION IF EXISTS BusinessPartnerAccountSheetReport(p_c_bpartner_id numeric, p_dateFrom date, p_dateTo date, p_ad_client_id numeric, p_ad_org_id numeric, p_isSoTrx TEXT, p_ad_language text);

CREATE OR REPLACE FUNCTION BusinessPartnerAccountSheetReport(p_dateFrom      date,
                                                             p_dateTo        date,
                                                             p_ad_client_id  numeric,
                                                             p_c_bpartner_id numeric = NULL,
                                                             p_ad_org_id     numeric = NULL,
                                                             p_isSoTrx       TEXT = 'Y',
                                                             p_ad_language   text = 'en_US')
    RETURNS table
            (
                c_bpartner_id    NUMERIC,
                bpartnerName     TEXT,
                dateAcct         DATE,
                DocumentType     TEXT,
                documentno       TEXT,
                beginningBalance NUMERIC,
                amount           NUMERIC,
                endingBalance    NUMERIC,
                currencyCode     TEXT,
                description      TEXT,
                referenceno      TEXT,
                created          TIMESTAMP
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
        c_bpartner_id          NUMERIC,
        bpartnerName           TEXT,
        beginningBalance       NUMERIC,
        amount                 NUMERIC,
        endingBalance          NUMERIC,
        dateacct               date,
        description            TEXT,
        referenceno            TEXT,
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
                        i.c_bpartner_id                        c_bpartner_id,
                        0                                      beginningBalance,
                        i.grandtotal                           amount,
                        0                                      endingBalance,
                        i.dateacct                             dateacct,
                        COALESCE(i.poreference, i.description) description,
                        rn.referenceno,
                        i.c_doctype_id                         c_doctype_id,
                        i.documentno                           documentno,
                        i.created                              created,
                        i.c_currency_id                        c_currency_id,
                        i.ad_org_id                            ad_org_id
                 FROM c_invoice i
                  LEFT JOIN (SELECT rn.referenceNo, rnd.Record_ID
                             FROM C_ReferenceNo_Doc rnd
                                      JOIN C_ReferenceNo rn ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
                                      JOIN AD_Table t ON t.AD_Table_ID = rnd.AD_Table_ID AND t.TableName = 'C_Invoice' AND t.isActive = 'Y'
                                      JOIN C_ReferenceNo_Type rt ON rt.C_ReferenceNo_Type_ID = rn.C_ReferenceNo_Type_ID
                                          AND rt.name = 'InvoiceReference' AND rt.isActive = 'Y'
                             WHERE rnd.isActive = 'Y') rn ON i.C_Invoice_ID = rn.Record_ID
                 WHERE TRUE
                   AND (p_c_bpartner_id IS NULL OR i.c_bpartner_id = p_c_bpartner_id)
                   AND i.dateacct >= p_dateFrom
                   AND i.dateacct <= p_dateTo
                   AND i.issotrx = p_isSoTrx
                   AND i.docstatus IN ('CO', 'CL')
                   AND (COALESCE(p_ad_org_id, 0) <= 0 OR i.ad_org_id = p_ad_org_id)
                 UNION ALL
                 SELECT --
                        p.c_bpartner_id c_bpartner_id,
                        0               beginningBalance,
                        p.payamt        amount,
                        0               endingBalance,
                        p.dateacct      dateacct,
                        p.description   description,
                        NULL as referenceno,
                        p.c_doctype_id  c_doctype_id,
                        p.documentno    documentno,
                        p.created       created,
                        p.c_currency_id c_currency_id,
                        p.ad_org_id     ad_org_id
                 FROM c_payment p
                 WHERE TRUE
                   AND (p_c_bpartner_id IS NULL OR p.c_bpartner_id = p_c_bpartner_id)
                   AND p.dateacct >= p_dateFrom
                   AND p.dateacct <= p_dateTo
                   AND p.isreceipt = p_isSoTrx
                   AND p.docstatus IN ('CO', 'CL')
                   AND (COALESCE(p_ad_org_id, 0) <= 0 OR p.ad_org_id = p_ad_org_id)
				UNION ALL
                 SELECT --
                        p.c_bpartner_id            c_bpartner_id,
                        0                          beginningBalance,
                        al.paymentwriteoffamt      amount,
                        0                          endingBalance,
                        hal.dateacct               dateacct,
                        hal.description            description,
                        NULL as referenceno,
                        -1             			   c_doctype_id,
                        hal.documentno             documentno,
                        hal.created                created,
                        hal.c_currency_id            c_currency_id,
                        hal.ad_org_id               ad_org_id
                 FROM c_payment p
                          join C_AllocationLine al on al.c_payment_id = p.c_payment_id and al.paymentwriteoffamt > 0
					  join c_allocationhdr hal on al.c_allocationhdr_id= hal.c_allocationhdr_id
                 WHERE TRUE
                   AND (p_c_bpartner_id IS NULL OR p.c_bpartner_id = p_c_bpartner_id)
                   AND hal.dateacct >= p_dateFrom
                   AND hal.dateacct <= p_dateTo
                   AND p.isreceipt = p_isSoTrx
                   AND p.docstatus IN ('CO', 'CL')
                   AND (COALESCE(p_ad_org_id, 0) <= 0 OR hal.ad_org_id = p_ad_org_id)
		 )
    INSERT
    INTO temp_BusinessPartnerAccountSheetReport(c_bpartner_id,
                                                beginningBalance,
                                                amount,
                                                endingBalance,
                                                dateacct,
                                                description,
                                                referenceno,
                                                c_doctype_id,
                                                documentno,
                                                created,
                                                c_currency_id_original,
                                                rowid,
                                                ad_org_id,
                                                targetCurrencyCode,
                                                doctype)
	SELECT--
		  i.c_bpartner_id,
		  i.beginningBalance,
		  i.amount,
		  i.endingBalance,
		  i.dateacct,
		  i.description,
		  i.referenceno,
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
		   LIMIT 1)      targetCurrencyCode,
		  (CASE
			   when i.c_doctype_id < 0 THEN
				   (select coalesce(rt.name, r.name) as doctype
					from ad_ref_list r
							 join ad_ref_list_trl rt
								  on r.ad_ref_list_id = rt.ad_ref_list_id and rt.ad_language = p_ad_language
					where ad_reference_id = 183
					  and value = 'CMA'
				   )
			   ELSE
				   (SELECT dtt.name
					FROM c_doctype dt
							 INNER JOIN c_doctype_trl dtt ON dt.c_doctype_id = dtt.c_doctype_id
					WHERE dtt.ad_language = p_ad_language
					  AND i.c_doctype_id = dt.c_doctype_id)
			  END
			  )::text AS docType
	FROM invoicesAndPaymentsInPeriod i;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('inserted invoices and payments: ' || v_temp || ' records', v_time);


    --
    -- Populate business partner name
    UPDATE temp_BusinessPartnerAccountSheetReport t
    SET bpartnerName = bp.name
    FROM c_bpartner bp
    WHERE bp.c_bpartner_id = t.c_bpartner_id;

    v_time := logDebug('populated bpartnerName', v_time);


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
                 LEFT JOIN c_doctype dt ON t.c_doctype_id = dt.c_doctype_id
             )
    UPDATE temp_BusinessPartnerAccountSheetReport t
    SET amount = c.amount
    FROM correctAmounts c
    WHERE c.rowid = t.rowid;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('Update amount by document type', v_time);


    --
    -- Update the beginning and end balances with the initial "Open Invoice Amount to Date" per business partner
    UPDATE temp_BusinessPartnerAccountSheetReport
    SET beginningBalance = sub.OpenInvoiceAmountToDate,
        endingBalance    = sub.OpenInvoiceAmountToDate
    FROM (
             SELECT t2.c_bpartner_id,
                    getBPOpenAmtToDate(p_ad_client_id,
                                       p_ad_org_id,
                                       (p_dateFrom - INTERVAL '1 days')::date,
                                       t2.c_bpartner_id,
                                       (SELECT c.c_currency_id
                                        FROM c_currency c
                                                 INNER JOIN c_acctschema accts ON c.c_currency_id = accts.c_currency_id
                                                 INNER JOIN ad_clientinfo ac ON accts.c_acctschema_id = ac.c_acctschema1_id
                                        LIMIT 1),
                                       'Y'::text,
                                       p_isSoTrx) OpenInvoiceAmountToDate
             FROM (SELECT DISTINCT c_bpartner_id FROM temp_BusinessPartnerAccountSheetReport) t2
         ) sub
    WHERE temp_BusinessPartnerAccountSheetReport.c_bpartner_id = sub.c_bpartner_id;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('Update beginning and end balance with "BP Open Invoices Amount to Date"', v_time);


    --
    -- Compute rolling sum (partitioned per business partner so each partner's balance starts fresh)
    WITH endingBalanceSum AS
             (
				SELECT --
					   t.rowid,
					   t.endingBalance
						   + sum(case
									 WHEN dt.docbasetype = 'ARR' and p_isSoTrx = 'Y' THEN -1 * t.amount
									 WHEN dt.docbasetype = 'APP' and p_isSoTrx = 'N' THEN -1 * t.amount
									 ELSE t.amount
						   end)
							 OVER ( PARTITION BY t.c_bpartner_id ORDER BY t.dateacct, t.created, t.documentno ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW ) endingBalance,
					   t.amount                                                                                                     currentAmount,
					   dt.docbasetype
				FROM temp_BusinessPartnerAccountSheetReport t
				LEFT JOIN c_doctype dt ON t.c_doctype_id = dt.c_doctype_id
             ),

         finalData AS
             (
				SELECT --
					   ebs.rowid,
					   ebs.endingBalance,
					   (ebs.endingBalance -
						(case
							 WHEN ebs.docbasetype = 'ARR' and p_isSoTrx = 'Y' THEN -1 * ebs.currentAmount
							 WHEN ebs.docbasetype = 'APP' and p_isSoTrx = 'N' THEN -1 * ebs.currentAmount
							 ELSE ebs.currentAmount
							end)
						   ) as beginningBalance
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
                        t.c_bpartner_id,
                        t.bpartnerName,
                        t.dateAcct,
                        t.doctype,
                        t.documentno,
                        t.beginningBalance,
                        t.amount,
                        t.endingBalance,
                        t.targetCurrencyCode,
                        t.description,
                        t.referenceno,
                        t.created
                 FROM temp_BusinessPartnerAccountSheetReport t
                 ORDER BY t.c_bpartner_id, t.dateacct, t.created, t.documentno;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE;

COMMENT ON FUNCTION BusinessPartnerAccountSheetReport(p_dateFrom date, p_dateTo date, p_ad_client_id numeric, p_c_bpartner_id numeric, p_ad_org_id numeric, p_isSoTrx TEXT, p_ad_language text) IS
'How to run (single partner):

SELECT*
FROM BusinessPartnerAccountSheetReport(''1111-1-1''::date,
                                       ''3333-1-1''::date,
                                       1000000,
                                       2000252)
;

How to run (all partners):

SELECT*
FROM BusinessPartnerAccountSheetReport(''1111-1-1''::date,
                                       ''3333-1-1''::date,
                                       1000000)
;
';

/*
How to run (single partner):

SELECT*
FROM BusinessPartnerAccountSheetReport('1111-1-1'::date,
                                       '3333-1-1'::date,
                                       1000000,
                                       2000252)
;

How to run (all partners):

SELECT*
FROM BusinessPartnerAccountSheetReport('1111-1-1'::date,
                                       '3333-1-1'::date,
                                       1000000)
;

*/
