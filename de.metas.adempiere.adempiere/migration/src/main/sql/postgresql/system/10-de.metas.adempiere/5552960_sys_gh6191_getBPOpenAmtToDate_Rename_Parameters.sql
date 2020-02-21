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
    v_sum numeric;
BEGIN

    SELECT INTO v_sum sum(
                              (SELECT openamt
                               FROM invoiceOpenToDate(
                                       p_C_Invoice_ID := i.C_Invoice_ID,
                                       p_c_invoicepayschedule_id := COALESCE(ips.C_InvoicePaySchedule_ID, 0::numeric),
                                       p_DateType := (
                                           CASE
                                               WHEN p_UseDateAcct = 'Y' THEN 'A'
                                                                          ELSE 'T'
                                           END
                                           ),
                                       p_Date := p_Date,
                                       p_Result_Currency_ID := p_C_Currency_ID
                                   )
                              )
                          )
    FROM C_Invoice i
             LEFT OUTER JOIN C_InvoicePaySchedule ips
                             ON i.C_Invoice_ID = ips.C_Invoice_ID
                                 AND ips.isvalid = 'Y'
                                 AND ips.isActive = 'Y'
    WHERE i.IsSOTrx = p_IsSOTrx
      AND i.DocStatus IN ('CO', 'CL')
      AND i.c_bpartner_id = p_c_bpartner_id
      AND i.AD_CLient_ID = p_AD_Client_ID
      AND (COALESCE(p_AD_Org_ID, 0) <= 0 OR i.AD_Org_ID = p_AD_Org_ID);

    RETURN v_sum;
END ;
$$
    LANGUAGE plpgsql VOLATILE;
	
	
	
	
	
	
	
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
SELECT ROW_NUMBER() OVER (ORDER BY t.Revenue DESC NULLS LAST, t.BPValue) :: INTEGER AS Rang,
       t.BPValue,
       t.Name,
       t.Revenue,
       t.OpenAmt,
       t.ISO_Code,
       (CASE
            WHEN totals.totalRevenueAmt != 0 THEN (t.Revenue * 100) / totals.totalRevenueAmt
                                             ELSE 0
        END)                                                                        AS SalesPercentOfTotal


FROM (
         SELECT accounting.C_Currency_ID,
                accounting.ISO_Code,
                bp.value AS BPValue,
                bp.Name  AS NAME,
                getCustomerRevenue(bp.C_BPartner_ID, p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo)
                         AS Revenue,

                (getBPOpenAmtToDate(
                        p_AD_Client_ID := p_AD_Client_ID,
                        p_AD_Org_ID := p_AD_Org_ID,
                        p_Date := p_DateTo,
                        p_C_BPartner_id := bp.C_BPartner_ID,
                        p_C_Currency_ID := accounting.C_Currency_ID,
                        p_UseDateAcct := 'Y',
                        p_IsSOTrx := 'Y')
                    )    AS OpenAmt
         FROM C_BPartner bp,
              (
                  SELECT C.ISO_Code, C.c_currency_id
                  FROM C_Currency C
                           JOIN C_AcctSchema acc
                                ON C.c_currency_id = acc.c_currency_id
                           JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
                  WHERE ci.AD_Client_ID = p_AD_Client_ID
              ) accounting
     ) t,
     (
         SELECT COALESCE(getTotalRevenue(p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo), 0) AS totalRevenueAmt
     ) totals


ORDER BY Rang
LIMIT (CASE
           WHEN p_Limit > 0 THEN p_Limit
                            ELSE 999999999999999
       END)
$$
    LANGUAGE SQL
    VOLATILE;
