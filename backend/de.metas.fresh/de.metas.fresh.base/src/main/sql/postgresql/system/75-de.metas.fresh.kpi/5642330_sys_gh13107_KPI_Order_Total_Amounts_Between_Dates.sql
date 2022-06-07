DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Order_Total_Amounts_Between_Dates (boolean,
                                                                                  date,
                                                                                  date,
                                                                                  numeric,
                                                                                  numeric)
;

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Order_Total_Amounts_Between_Dates(IN p_isSO         boolean,
                                                                                    IN p_From         date,
                                                                                    IN p_To           date,
                                                                                    IN p_AD_Client_ID numeric,
                                                                                    IN p_AD_Org_ID    numeric)
    RETURNS table
            (
                TotalOrderAmt numeric,
                IsoCode       char(3)
            )
AS
$$
SELECT COALESCE(SUM(currencyconvert(grandtotal, co.c_currency_id, cas.c_currency_id, NOW(), -1, p_AD_Client_ID, p_AD_Org_ID)), 0),
       cy.iso_code
FROM ad_clientInfo ci
         INNER JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id
         INNER JOIN c_currency cy ON cas.c_currency_id = cy.c_currency_id
         LEFT JOIN c_order co ON ci.ad_client_id = co.ad_client_id
    AND CASE WHEN p_isSO THEN co.isSoTrx = 'Y' ELSE co.isSoTrx = 'N' END
    AND co.DateOrdered >= p_From
    AND co.DateOrdered <= p_To
    AND co.DocStatus IN ('CO', 'CL') -- Completed or Closed only
    AND co.IsActive = 'Y'
    AND co.ad_org_id = p_AD_Org_ID

WHERE ci.ad_client_id = p_AD_Client_ID
  AND (cas.ad_orgonly_id IS NULL OR cas.ad_orgonly_id = p_AD_Org_ID)
GROUP BY cy.iso_code
$$
    LANGUAGE sql STABLE
;

COMMENT ON FUNCTION de_metas_fresh_kpi.KPI_Order_Total_Amounts_Between_Dates(boolean, date, date, numeric, numeric) IS 'Returns the total amount of orders, sale or purchase, between two dates (inclusive). The result is given in the currency of C_AcctSchema.C_Currency_ID'
;
