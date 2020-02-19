DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_compareWithPrevious1Year boolean, IN p_compareWithPrevious3Years boolean);
DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp);

CREATE OR REPLACE FUNCTION ProfitAndLossReport(IN p_startDate timestamp,
                                               IN p_endDate timestamp)
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

SELECT--
      ev.value                                                                                                                       AccountValue,
      ev.name                                                                                                                        AccountName,
      ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '3 Year'::interval, p_endDate - '3 Year'::interval) balance_three_years_ago,
      ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '2 Year'::interval, p_endDate - '2 Year'::interval) balance_two_years_ago,
      ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '1 Year'::interval, p_endDate - '1 Year'::interval) balance_one_year_ago,
      ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate, p_endDate)                                           current_balance
FROM c_elementvalue ev
WHERE TRUE
  AND ev.accounttype IN ('E', 'R')
  AND ev.IsSummary = 'N'
ORDER BY ev.value
    ;

$BODY$
    LANGUAGE sql
    STABLE;

ALTER FUNCTION ProfitAndLossReport(timestamp, timestamp)
    OWNER TO metasfresh;

-- test: SELECT * FROM ProfitAndLossReport('1993-01-01'::Timestamp, '2992-01-01'::Timestamp);
-- test SELECT * FROM ProfitAndLossReport('2018-01-01'::Timestamp, '2018-07-01'::Timestamp);
