DROP FUNCTION report.fresh_account_info_report(numeric,
    numeric,
    numeric,
    numeric,
    date,
    date,
    numeric,
    varchar,
    varchar,
    varchar,
    numeric)
;

CREATE FUNCTION report.fresh_account_info_report(account_from_id       numeric,
                                                 account_to_id         numeric,
                                                 c_period_start_id     numeric,
                                                 c_period_end_id       numeric,
                                                 startdate             date,
                                                 enddate               date,
                                                 c_activity_id         numeric,
                                                 displayvoiddocuments  character varying,
                                                 showcurrencyexchange  character varying,
                                                 showonlyemptyactivity character varying,
                                                 ad_org_id             numeric)
    RETURNS TABLE
        (
        dateacct             date,
        fact_acct_id         numeric,
        bp_name              text,
        description          text,
        account2_id          text,
        a_value              text,
        amtacctdr            numeric,
        amtacctcr            numeric,
        saldo                numeric,
        param_acct_value     text,
        param_acct_name      text,
        param_end_date       date,
        param_start_date     date,
        param_activity_value text,
        param_activity_name  text,
        overallcount         bigint,
        unionorder           integer,
        docstatus            text,
        eurosaldo            numeric,
        containseur          boolean,
        ad_org_id            numeric
        )
    STABLE
    LANGUAGE sql
AS
$$
WITH AccountInfo AS
             (SELECT * FROM report.fresh_Account_Info_Report_Sub($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11))

SELECT DateAcct,
       Fact_Acct_ID,
       BP_Name,
       Description,
       Account2_ID,
       a_Value,
       AmtAcctDr,
       AmtAcctCr,
       Saldo,
       Param_Acct_Value,
       Param_Acct_Name,
       Param_End_Date,
       Param_Start_Date,
       Param_Activity_Value,
       Param_Activity_Name,
       COUNT(0) OVER () AS overallcount,
        2                AS UnionOrder,
       DocStatus,
       NULL::numeric,
        NULL::boolean,
        ad_org_id
FROM AccountInfo
WHERE Fact_Acct_ID IS NOT NULL
UNION ALL
SELECT DISTINCT NULL::date,
        NULL::numeric,
        NULL,
                'Anfangssaldo',
                NULL::text,
        NULL::text,
        NULL::numeric,
        NULL::numeric,
        CarrySaldo,
                Param_Acct_Value,
                Param_Acct_Name,
                Param_End_Date,
                Param_Start_Date,
                Param_Activity_Value,
                Param_Activity_Name,
                COUNT(0) OVER () AS overallcount,
        1,
                NULL::text,
        NULL::numeric,
        NULL::boolean,
        ad_org_id
FROM AccountInfo
UNION ALL
SELECT DISTINCT NULL::date,
        NULL::numeric,
        NULL,
                'Summe',
                NULL::text,
        NULL::text,
        AmtAcctDrEnd,
                AmtAcctCrEnd,
                CarrySaldo,
                Param_Acct_Value,
                Param_Acct_Name,
                Param_End_Date,
                Param_Start_Date,
                Param_Activity_Value,
                Param_Activity_Name,
                COUNT(0) OVER () AS overallcount,
        3,
                NULL::text,
        NULL::numeric,
        NULL::boolean,
        ad_org_id
FROM AccountInfo
UNION ALL
(SELECT DISTINCT NULL::date,
         NULL::numeric,
         NULL,
                 'Summe in EUR',
                 NULL::text,
         NULL::text,
         NULL::numeric,
         NULL::numeric,
         NULL::numeric,
         Param_Acct_Value,
                 Param_Acct_Name,
                 Param_End_Date,
                 Param_Start_Date,
                 Param_Activity_Value,
                 Param_Activity_Name,
                 COUNT(0) OVER () AS overallcount,
         4,
                 NULL::text,
         EuroSaldo,
                 containsEUR,
                 ad_org_id
 FROM AccountInfo
 WHERE containsEUR = 'Y')
ORDER BY Param_Acct_Value, UnionOrder, DateAcct,
         Fact_Acct_ID
    $$
;

ALTER FUNCTION report.fresh_account_info_report(numeric, numeric, numeric, numeric, date, date, numeric, varchar, varchar, varchar, numeric) OWNER TO metasfresh
;