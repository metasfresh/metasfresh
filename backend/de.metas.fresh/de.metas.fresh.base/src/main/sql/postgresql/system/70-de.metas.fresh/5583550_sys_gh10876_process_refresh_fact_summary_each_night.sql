
DROP FUNCTION IF EXISTS refreshMV_Fact_Acct_Sum();
CREATE FUNCTION refreshMV_Fact_Acct_Sum()
    RETURNS boolean AS $$
BEGIN
    REFRESH MATERIALIZED VIEW MV_Fact_Acct_Sum;
    return true;
END;
$$  LANGUAGE plpgsql;
COMMENT ON FUNCTION refreshMV_Fact_Acct_Sum() IS 'This function only calls refresh on the materialized view MV_Fact_Acct_Sum. It is needed so we can use the refresh in an AD_Process which requires a returned result';
--select refreshMV_Fact_Acct_Sum();

UPDATE AD_Process SET sqlstatement='select refreshMV_Fact_Acct_Sum();', Updated='2021-03-23 18:01:30.567059 +01:00', UpdatedBy=99 WHERE AD_Process_ID=584685;
