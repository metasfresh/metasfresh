DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_compareWithPrevious1Year boolean, IN p_compareWithPrevious3Years boolean);

CREATE OR REPLACE FUNCTION ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_compareWithPrevious1Year boolean = FALSE, IN p_compareWithPrevious3Years boolean = FALSE)
    RETURNS TABLE
            (
                name                    text,
                value                   text,
                three_years_ago_balance numeric,
                two_years_ago_balance   numeric,
                one_year_ago_balance    numeric,
                current_balance         numeric
            )
AS
$BODY$

SELECT ev.name,
       ev.value,
       ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '3 Year'::interval, p_endDate - '3 Year'::interval) three_years_ago_balance,
       ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '2 Year'::interval, p_endDate - '2 Year'::interval) two_years_ago_balance,
       ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '1 Year'::interval, p_endDate - '1 Year'::interval) one_year_ago_balance,
       ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate, p_endDate)                                           current_balance
FROM c_elementvalue ev
WHERE TRUE
  AND ev.accounttype IN ('E', 'R')
ORDER BY ev.name
    ;

$BODY$
    LANGUAGE sql
    VOLATILE;

ALTER FUNCTION ProfitAndLossReport(timestamp, timestamp, boolean, boolean)
    OWNER TO metasfresh;

-- test: SELECT * FROM ProfitAndLossReport('1993-01-01'::Timestamp, '2992-01-01'::Timestamp);
-- test SELECT * FROM ProfitAndLossReport('2018-01-01'::Timestamp, '2018-07-01'::Timestamp);
