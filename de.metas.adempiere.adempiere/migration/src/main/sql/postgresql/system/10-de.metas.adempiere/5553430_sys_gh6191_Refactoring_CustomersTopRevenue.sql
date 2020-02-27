DROP FUNCTION IF EXISTS getBPOpenAmtToDate(numeric, numeric, date, numeric, numeric, text, text);

CREATE OR REPLACE FUNCTION getBPOpenAmtToDate(p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_Date date,
                                              p_C_BPartner_id numeric,
                                              p_C_Currency_ID numeric,
                                              p_UseDateAcct text = 'Y',
                                              p_IsSOTrx text = 'Y')
    RETURNS numeric
AS
$$
DECLARE
    v_sum       numeric;
    v_orgNotSet boolean;
    v_dateType  text;
BEGIN
    v_orgNotSet := COALESCE(p_AD_Org_ID, 0) <= 0;

    v_dateType := CASE
                      WHEN p_UseDateAcct = 'Y' THEN 'A'
                                               ELSE 'T'
                  END;

    WITH t AS
             (SELECT i.C_Invoice_ID,
                     ips.C_InvoicePaySchedule_ID
              FROM C_Invoice i
                       LEFT OUTER JOIN C_InvoicePaySchedule ips
                                       ON i.C_Invoice_ID = ips.C_Invoice_ID
                                           AND ips.isvalid = 'Y'
                                           AND ips.isActive = 'Y'
              WHERE TRUE
                AND i.IsSOTrx = p_IsSOTrx
                AND (CASE
                         WHEN p_UseDateAcct = 'Y' THEN i.dateacct
                                                  ELSE i.dateinvoiced
                     END) <= p_Date
                AND i.DocStatus IN ('CO', 'CL')
                AND i.c_bpartner_id = p_c_bpartner_id
                AND i.AD_CLient_ID = p_AD_Client_ID
                AND (v_orgNotSet OR i.AD_Org_ID = p_AD_Org_ID))

    SELECT INTO v_sum sum(
                              (SELECT openamt
                               FROM invoiceOpenToDate(
                                       p_C_Invoice_ID := t.C_Invoice_ID,
                                       p_c_invoicepayschedule_id := COALESCE(t.C_InvoicePaySchedule_ID, 0::numeric),
                                       p_DateType := v_dateType,
                                       p_Date := p_Date,
                                       p_Result_Currency_ID := p_C_Currency_ID
                                   )
                              )
                          )


    FROM t;


    RETURN coalesce(v_sum, 0);
END ;
$$
    LANGUAGE plpgsql VOLATILE;



COMMENT ON FUNCTION getBPOpenAmtToDate(numeric, numeric, date, numeric, numeric, text, text) IS
    '  TEST
    SELECT value,
           C_Bpartner_ID,
           getBPOpenAmtToDate(1000000,
                              1000000,
                              ''9999-01-01'' :: date,
                              C_BPartner_ID,
                              318,
                               ''Y'',
                               ''Y'')

     FROM C_Bpartner ; ';

/* TEST:

SELECT value,
       C_Bpartner_ID,
       getBPOpenAmtToDate(1000000,
                          1000000,
                          '9999-01-01' :: date,
                          C_BPartner_ID,
                          318,
                           'Y',
                           'Y')

 FROM C_Bpartner ;

*/











DROP FUNCTION IF EXISTS getCustomerRevenue(numeric, numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getCustomerRevenue(p_C_BPartner_ID numeric,
                                              p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_DateFrom date,
                                              p_DateTo date)
    RETURNS numeric
AS
$$

WITH t AS (
    SELECT i.DateInvoiced,
           i.C_Currency_ID,
           i.C_ConversionType_ID,
           CASE
               WHEN dt.DocBaseType IN ('ARC', 'APC') -- subtract credit memos
                   THEN -1
                   ELSE
                   1
           END
               AS multiplier,

           CASE
               WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo
                                          ELSE il.LineNetAmt
           END AS amt

    FROM C_InvoiceLine il
             JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
             JOIN C_DocTYpe dt ON i.C_DocType_ID = dt.C_DocType_ID

    WHERE p_C_BPartner_ID = i.C_Bpartner_ID
      AND il.IsActive = 'Y'
      AND i.IsActive = 'Y'
      AND i.isSOTrx = 'Y'
      AND i.DocStatus IN ('CO', 'CL')
      AND i.AD_Client_ID = p_AD_Client_ID
      AND i.AD_Org_ID = p_AD_Org_ID
      AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
      AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo)),

     accounting AS (
         SELECT C.c_currency_id
         FROM C_Currency C
                  JOIN C_AcctSchema acc
                       ON C.c_currency_id = acc.c_currency_id
                  JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
         WHERE ci.AD_Client_ID = p_AD_Client_ID
     )


SELECT sum(
                   t.multiplier *
                   currencyconvert(
                           p_amount := t.amt,
                           p_curfrom_id := t.C_Currency_ID,
                           p_curto_ID := accounting.c_currency_id,
                           p_convdate := t.DateInvoiced,
                           p_conversiontype_id := t.c_conversiontype_id,
                           p_client_id := p_AD_Client_ID,
                           p_org_id := p_AD_Org_ID)
           ) AS Revenue


FROM t,
     accounting;
$$
    LANGUAGE SQL STABLE;



COMMENT ON FUNCTION getCustomerRevenue(numeric, numeric, numeric, date, date) IS
    '   TEST :
     SELECT C_BPartner_ID,
            getCustomerRevenue(C_BPartner_ID,
                               1000000,
                               1000000,
                               NULL,
                               NULL)

     FROM C_BPartner; ';

/* TEST:

 SELECT C_BPartner_ID,
            getCustomerRevenue(C_BPartner_ID,
                               1000000,
                               1000000,
                               NULL,
                               NULL)

     FROM C_BPartner ;

*/













DROP FUNCTION IF EXISTS getTotalRevenue(numeric, numeric, numeric, date, date);


DROP FUNCTION IF EXISTS getTotalRevenue(numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getTotalRevenue (p_AD_Client_ID numeric,
                                            p_AD_Org_ID numeric,
                                            p_DateFrom date,
                                            p_DateTo date)
    RETURNS numeric
AS
$$


WITH t AS (
    SELECT il.M_Product_ID,
           i.DateInvoiced,
           i.C_Currency_ID,
           i.C_ConversionType_ID,
           CASE
               WHEN dt.DocBaseType IN ('ARC', 'APC') -- subtract credit memos
                   THEN -1
                   ELSE
                   1
           END
               AS multiplier,

           CASE
               WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo
                                          ELSE il.LineNetAmt
           END AS amt


    FROM C_InvoiceLine il
             JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
             JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID

    WHERE i.isSOTrx = 'Y'
      AND il.IsActive = 'Y'
      AND i.IsActive = 'Y'
      AND i.DocStatus IN ('CO', 'CL')
      AND i.AD_Client_ID = p_AD_Client_ID
      AND i.AD_Org_ID = p_AD_Org_ID
      AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
      AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo))
        ,
     accounting AS (
         SELECT C.c_currency_id
         FROM C_Currency C
                  JOIN C_AcctSchema acc
                       ON C.c_currency_id = acc.c_currency_id
                  JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
         WHERE ci.AD_Client_ID = p_AD_Client_ID
     )


SELECT sum(
                   t.multiplier *
                   currencyconvert(
                           p_amount := t.amt,
                           p_curfrom_id := t.C_Currency_ID,
                           p_curto_ID := accounting.c_currency_id,
                           p_convdate := t.DateInvoiced,
                           p_conversiontype_id := t.c_conversiontype_id,
                           p_client_id := p_AD_Client_ID,
                           p_org_id := p_AD_Org_ID))

FROM t,
     accounting;


$$
    LANGUAGE SQL STABLE;

COMMENT ON FUNCTION
    getTotalRevenue
    (NUMERIC, NUMERIC, date, date)
    IS
        ' TEST :
        SELECT *
        FROM getTotalRevenue(1000000 :: numeric,
                             1000000 ::numeric,
                              NULL :: date,
                              NULL :: date) ; ';

/* TEST:

 SELECT *
        FROM getTotalRevenue(1000000 :: numeric,
                             1000000 ::numeric,
                              NULL :: date,
                              NULL :: date) ;

*/














DROP FUNCTION IF EXISTS CustomersTopRevenue(NUMERIC, NUMERIC, DATE, DATE, INTEGER);

CREATE OR REPLACE FUNCTION CustomersTopRevenue(p_AD_Client_ID NUMERIC,
                                               p_AD_Org_ID NUMERIC,
                                               p_DateFrom DATE,
                                               p_DateTo DATE,
                                               p_Limit INTEGER)

    RETURNS TABLE
            (
                Rang                INTEGER,
                BPValue             CHARACTER VARYING,
                Name                CHARACTER VARYING,
                Revenue             NUMERIC,
                OpenAmt             NUMERIC,
                ISO_Code            CHARACTER(3),
                SalesPercentOfTotal NUMERIC
            )

AS
$$


WITH totals AS
         (
             SELECT COALESCE(getTotalRevenue(p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo), 0) AS totalRevenueAmt
         ),

     accounting AS (
         SELECT C.c_currency_id, C.ISO_Code
         FROM C_Currency C
                  JOIN C_AcctSchema acc
                       ON C.c_currency_id = acc.c_currency_id
                  JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
         WHERE ci.AD_Client_ID = p_AD_Client_ID
     ),

     t AS
         (
             SELECT bp.value AS BPValue,
                    bp.Name  AS NAME,
                    getCustomerRevenue(bp.C_BPartner_ID, p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo)
                             AS Revenue,

                    getBPOpenAmtToDate(p_AD_Client_ID := p_AD_Client_ID,
                                       p_AD_Org_ID := p_AD_Org_ID,
                                       p_Date := p_DateTo,
                                       p_C_BPartner_id := bp.C_BPartner_ID,
                                       p_C_Currency_ID := accounting.c_currency_id,
                                       p_UseDateAcct := 'Y',
                                       p_IsSOTrx := 'Y')
                             AS OpenAmt
             FROM C_BPartner bp,
                  accounting
             WHERE bp.isCustomer = 'Y'
         )


SELECT ROW_NUMBER() OVER (ORDER BY t.Revenue DESC NULLS LAST, t.BPValue) :: INTEGER AS Rang,
       t.BPValue,
       t.Name,
       t.Revenue,
       t.OpenAmt,
       accounting.ISO_Code,
       round(
               (CASE
                    WHEN totals.totalRevenueAmt != 0 THEN (t.Revenue * 100) / totals.totalRevenueAmt
                                                     ELSE 0
                END)
           , 2)                                                                     AS SalesPercentOfTotal


FROM t,
     totals,
     accounting


ORDER BY Rang
LIMIT (CASE
           WHEN p_Limit > 0 THEN p_Limit
                            ELSE 999999999999999
       END);

$$
    LANGUAGE SQL VOLATILE;



COMMENT ON FUNCTION CustomersTopRevenue(NUMERIC, NUMERIC, DATE, DATE, INTEGER) IS
    '  TEST
    SELECT *
    FROM CustomersTopRevenue
        (p_AD_Client_ID := 1000000,
         p_AD_Org_ID := 1000000,
         p_DateFrom := ''2020-01-01'',
         p_DateTo := ''2020-05-05'',
         p_Limit := -1); ';


/* TEST:

 SELECT *
    FROM CustomersTopRevenue
        (p_AD_Client_ID := 1000000,
         p_AD_Org_ID := 1000000,
         p_DateFrom := '2020-01-01',
         p_DateTo := '2020-05-05',
         p_Limit := -1);

*/






