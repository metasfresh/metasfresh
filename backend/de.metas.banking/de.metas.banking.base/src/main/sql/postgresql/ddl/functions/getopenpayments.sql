DROP FUNCTION IF EXISTS getopenpayments(numeric,
                                        numeric,
                                        timestamp WITHOUT TIME ZONE,
                                        numeric)
;

DROP VIEW IF EXISTS t_getopenpayments
;



CREATE OR REPLACE VIEW t_getopenpayments AS
SELECT NULL::numeric                     AS ad_org_id,
       NULL::numeric                     AS ad_client_id,
       NULL::numeric                     AS c_payment_id,
       NULL::numeric                     AS c_bpartner_id,
       NULL::character varying           AS DocumentNo,
       NULL::timestamp WITHOUT TIME ZONE AS paymentdate,
       NULL::timestamp WITHOUT TIME ZONE AS dateacct, -- task 09643: separate transaction date form accounting date
       NULL::numeric                     AS C_Currency_ID,
       NULL::character(3)                AS currency_code,
       NULL::numeric                     AS payAmt,
       NULL::numeric                     AS openAmt,
       NULL::numeric                     AS multiplierap,
       NULL::numeric                     AS C_ConversionType_ID,
       NULL::numeric                     AS FixedConversion_SourceCurrency_ID,
       NULL::numeric                     AS FixedConversion_Rate
;

COMMENT ON VIEW t_getopenpayments IS 'Used as return type in the SQL-function getopenpayments'
;



CREATE OR REPLACE FUNCTION getopenpayments(
    c_bpartner_id numeric, -- 1
    ad_org_id     numeric, -- 2
    date          timestamp WITHOUT TIME ZONE, -- 3
    c_payment_id  numeric -- 4
)
    RETURNS SETOF t_getopenpayments
AS
$BODY$
SELECT p.AD_Org_ID,
       p.AD_Client_ID,
       p.C_Payment_ID,
       p.C_Bpartner_ID,
       p.DocumentNo                     AS DocumentNo,
       p.DateTrx                        AS PaymentDate,
       p.DateAcct                       AS DateAcct, -- task 09643: separate transaction date form accounting date
       p.c_currency_id                  AS C_Currency_ID,
       c.ISO_Code                       AS currency_code,
       p.PayAmt                         AS payAmt,
       paymentavailable(p.C_Payment_ID) AS openAmt,
       p.MultiplierAP::numeric          AS multiplierAP,
       p.C_ConversionType_ID            AS C_ConversionType_ID,
       p.source_currency_id             AS FixedConversion_SourceCurrency_ID,
       p.currencyrate                   AS FixedConversion_Rate
FROM C_Payment_v p
         INNER JOIN C_Currency c ON (p.C_Currency_ID = c.C_Currency_ID)
WHERE (
        ($1 IS NULL AND $4 IS NULL) -- no C_BPartner_ID nor C_Payment_ID is set
        OR p.C_BPartner_ID = $1
        OR p.C_BPartner_ID IN (SELECT C_BPartnerRelation_ID FROM C_BP_Relation WHERE C_BP_Relation.C_BPartner_ID = $1 AND isPayFrom = 'Y' AND isActive = 'Y')
        OR p.C_Payment_ID = $4
    )
  AND p.IsAllocated = 'N'
  AND p.Processed = 'Y'
  AND p.C_Charge_ID IS NULL
  AND ($2 IS NULL OR $2 = 0 OR p.AD_Org_ID = $2)
  AND paymentavailable(p.C_Payment_ID) != 0
ORDER BY p.DateTrx, p.DocumentNo
    ;
$BODY$
    LANGUAGE sql STABLE
                 ROWS 1000
;

COMMENT ON FUNCTION getopenpayments(
    numeric,
    numeric,
    timestamp WITHOUT TIME ZONE,
    numeric) IS '
* Used in de.mets.paymentallocation.form.Allocation.queryPaymentTable()
* Uses the view T_GetOpenPayments as return type'
;
