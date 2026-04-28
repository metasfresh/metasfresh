DROP FUNCTION IF EXISTS getopenpayments(numeric, numeric, character varying, numeric, timestamp WITHOUT TIME ZONE, numeric)
;

DROP VIEW IF EXISTS t_getopenpayments
;



CREATE OR REPLACE VIEW t_getopenpayments AS
SELECT NULL::numeric                     AS ad_org_id
     , NULL::numeric                     AS ad_client_id
     , NULL::numeric                     AS c_payment_id
     , NULL::numeric                     AS c_bpartner_id
     , NULL::character varying           AS docno
     , NULL::timestamp WITHOUT TIME ZONE AS paymentdate
     , NULL::timestamp WITHOUT TIME ZONE AS dateacct -- task 09643: separate transaction date form accounting date
     , NULL::character varying           AS doctype
     , NULL::character varying           AS bpartnername
     , NULL::character(3)                AS iso_code
     , NULL::numeric                     AS ConvertTo_Currency_ID
     , NULL::character(3)                AS ConvertTo_Currency_ISO_Code
     , NULL::numeric                     AS orig_total
     , NULL::numeric                     AS conv_total
     , NULL::numeric                     AS conv_open
     , NULL::numeric                     AS multiplierap
     , NULL::numeric                     AS C_ConversionType_ID
;

COMMENT ON VIEW t_getopenpayments IS 'Used as return type in the SQL-function getopenpayments'
;



CREATE OR REPLACE FUNCTION getopenpayments(c_bpartner_id    numeric -- 1
                                          , c_currency_id   numeric -- 2
                                          , ismulticurrency character varying -- 3
                                          , ad_org_id       numeric -- 4
                                          , date            timestamp WITHOUT TIME ZONE -- 5
                                          , c_payment_id    numeric -- 6
)
    RETURNS SETOF t_getopenpayments
AS
$BODY$
SELECT p.AD_Org_ID,
       p.AD_Client_ID,
       p.C_Payment_ID,
       p.C_Bpartner_ID,
       p.DocumentNo                                                                                                                                              AS DocNo,
       p.DateTrx,
       p.DateAcct, -- task 09643: separate transaction date form accounting date
       dt.name                                                                                                                                                   AS DocType,
       bp.name                                                                                                                                                   AS BPartnerName,
       c.ISO_Code,
       COALESCE($2, p.C_Currency_ID)                                                                                                                             AS ConvertTo_Currency_ID,
       convertToCurrency.ISO_Code                                                                                                                                AS ConvertTo_Currency_ISO_Code,
       p.PayAmt                                                                                                                                                  AS Orig_Total,
       currencyConvert(p.PayAmt, p.C_Currency_ID, COALESCE($2, p.C_Currency_ID), $5, p.C_ConversionType_ID, p.AD_Client_ID, p.AD_Org_ID)                         AS Conv_Total,
       currencyConvert(paymentAvailable(p.C_Payment_ID), p.C_Currency_ID, COALESCE($2, p.C_Currency_ID), $5, p.C_ConversionType_ID, p.AD_Client_ID, p.AD_Org_ID) AS Conv_Open,
       p.MultiplierAP::numeric,
       p.C_ConversionType_ID
FROM C_Payment_v p
         INNER JOIN C_Currency c ON (p.C_Currency_ID = c.C_Currency_ID)
         INNER JOIN C_Currency convertToCurrency ON (convertToCurrency.C_Currency_ID = COALESCE($2, p.C_Currency_ID))
         INNER JOIN C_DocType dt ON (p.C_DocType_ID = dt.C_DocType_ID)
         INNER JOIN C_BPartner bp ON (p.C_BPartner_ID = bp.C_BPartner_ID)
WHERE (
        ($1 IS NULL AND $6 IS NULL) -- no C_BPartner_ID nor C_Payment_ID is set
        OR p.C_BPartner_ID = $1
        OR p.C_BPartner_ID IN (SELECT C_BPartnerRelation_ID FROM C_BP_Relation WHERE C_BP_Relation.C_BPartner_ID = $1 AND isPayFrom = 'Y' AND isActive = 'Y')
        OR p.C_Payment_ID = $6
    )
  AND p.IsAllocated = 'N'
  AND p.Processed = 'Y'
  AND p.C_Charge_ID IS NULL
  AND ($3 = 'Y' OR p.C_Currency_ID = $2)
  AND ($4 IS NULL OR $4 = 0 OR p.AD_Org_ID = $4)
ORDER BY p.DateTrx, p.DocumentNo
    ;
$BODY$
    LANGUAGE sql STABLE
                 ROWS 1000
;

COMMENT ON FUNCTION getopenpayments(numeric, numeric, character varying, numeric, timestamp WITHOUT TIME ZONE, numeric) IS '
* Used in de.mets.paymentallocation.form.Allocation.queryPaymentTable()
* Uses the view T_GetOpenPayments as return type'
;
