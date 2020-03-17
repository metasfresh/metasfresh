DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_compareWithPrevious1Year boolean, IN p_compareWithPrevious3Years boolean)
;

DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp)
;

DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, p_ExcludeEmptyLines text)
;

DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_AD_Org_ID numeric, IN p_ExcludeEmptyLines text)
;

CREATE OR REPLACE FUNCTION ProfitAndLossReport(IN p_startDate         timestamp,
                                               IN p_endDate           timestamp,
                                               IN p_AD_Org_ID         numeric,
                                               IN p_ExcludeEmptyLines text = 'Y')
    RETURNS TABLE
            (
                AccountValue            text,
                AccountName             text,
                balance_three_years_ago numeric,
                balance_two_years_ago   numeric,
                balance_one_year_ago    numeric,
                balance                 numeric
            )
AS
$BODY$

WITH data AS
         (
             SELECT--
                   ev.value                                                                                                                                    AccountValue,
                   ev.name                                                                                                                                     AccountName,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '3 Year'::interval, p_endDate - '3 Year'::interval, p_AD_Org_ID) balance_three_years_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '2 Year'::interval, p_endDate - '2 Year'::interval, p_AD_Org_ID) balance_two_years_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '1 Year'::interval, p_endDate - '1 Year'::interval, p_AD_Org_ID) balance_one_year_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate, p_endDate, p_AD_Org_ID)                                           current_balance
             FROM c_elementvalue ev
             WHERE TRUE
               AND ev.accounttype IN ('E', 'R')
               AND ev.IsSummary = 'N'
         ),
     dataExcludingEmptyLines AS
         (
             SELECT --
                    AccountValue,
                    AccountName,
                    balance_three_years_ago,
                    balance_two_years_ago,
                    balance_one_year_ago,
                    current_balance
             FROM data
             WHERE (
                           p_ExcludeEmptyLines = 'N'
                           OR (
                                   current_balance != 0
                                   OR balance_one_year_ago != 0
                                   OR balance_two_years_ago != 0
                                   OR balance_three_years_ago != 0
                               )
                       )
         )
SELECT --
       AccountValue,
       AccountName,
       balance_three_years_ago,
       balance_two_years_ago,
       balance_one_year_ago,
       current_balance
FROM dataExcludingEmptyLines d
ORDER BY d.AccountValue
    ;
$BODY$
    LANGUAGE sql
    STABLE
;

-- test: SELECT * FROM ProfitAndLossReport('1993-01-01'::Timestamp, '2992-01-01'::Timestamp, 1000000);
-- test SELECT * FROM ProfitAndLossReport('2018-01-01'::Timestamp, '2018-07-01'::Timestamp, 1000000);
