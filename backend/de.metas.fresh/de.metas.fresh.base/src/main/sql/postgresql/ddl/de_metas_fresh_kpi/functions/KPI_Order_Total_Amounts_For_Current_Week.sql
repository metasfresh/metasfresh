DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Order_Total_Amounts_For_Current_Week (boolean,
                                                                                     date,
                                                                                     date,
                                                                                     numeric,
                                                                                     numeric)
;

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Order_Total_Amounts_For_Current_Week(IN p_isSO         boolean,
                                                                                       IN p_AD_Client_ID numeric,
                                                                                       IN p_AD_Org_ID    numeric)
    RETURNS table
            (
                TotalOrderAmt numeric,
                IsoCode       char(3)
            )
AS
$$

    -- *fun* with dates:
    -- DATE_PART('dow', NOW()) - 1 -- because Mon is 1, Tue is 2, etc. we'd have to subtract 1 from the result of DATE_PART
    -- numeric * '1 day'::interval -- to get the interval in days

SELECT *
FROM de_metas_fresh_kpi.KPI_Order_Total_Amounts_Between_Dates(p_isSO,
                                                              (NOW() - (DATE_PART('dow', NOW()) - 1) * '1 day'::interval)::date,
                                                              NOW()::Date,
                                                              p_AD_Client_ID,
                                                              p_AD_Org_ID);
$$
    LANGUAGE sql STABLE
;

COMMENT ON FUNCTION de_metas_fresh_kpi.KPI_Order_Total_Amounts_For_Current_Week(boolean, numeric, numeric) IS 'Returns the total amount of orders, sale or purchase, that happened this week'
;
