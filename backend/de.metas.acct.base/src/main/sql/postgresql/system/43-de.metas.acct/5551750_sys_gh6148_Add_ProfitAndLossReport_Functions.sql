DROP FUNCTION IF EXISTS ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric, IN p_startDate timestamp, IN p_endDate timestamp);

CREATE OR REPLACE FUNCTION ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric, IN p_startDate timestamp, IN p_endDate timestamp)
    RETURNS NUMERIC
AS
$BODY$

WITH periods_between_dates AS
         (
             SELECT --
                    p.c_period_id,
                    p.startdate,
                    p.enddate
             FROM c_period p
             WHERE TRUE
               AND p.startdate >= p_startDate
               AND p.enddate <= p_endDate
         ),
     fact_acct_summary_for_periods AS
         (
             -- fact_acct_summary filtered for selected period
             SELECT fas.*
             FROM periods_between_dates p
                      INNER JOIN fact_acct_summary fas ON fas.c_period_id = p.c_period_id
             WHERE fas.account_id = p_C_ElementValue_ID
         ),
     result_table AS
         (
             SELECT coalesce(sum(acctbalance(fas.account_id, fas.amtacctdr, fas.amtacctcr)), 0) balance
             FROM c_elementvalue ev
                      INNER JOIN fact_acct_summary_for_periods fas ON fas.account_id = ev.c_elementvalue_id
             WHERE TRUE
               AND ev.accounttype IN ('E', 'R')
               -- only 'Actual' posting type is used
               AND fas.postingtype = 'A'
             GROUP BY ev.value, ev.name
             ORDER BY ev.name
         )
SELECT balance
FROM result_table

/**
  Why is this union needed?
  In case no `fact_acct_summary` for an account_id exists, `result_table` returns no rows. It doesn't return null (so that i can use coalesce), but returns NO rows.
     example: SELECT ProfitAndLossBalanceForAccountInPeriod(1, '1993-01-01'::Timestamp, '2992-01-01'::Timestamp); -- this returns NO rows
  I have simply added this union in case the select returns nothing. If you have a better solution, i'm more than happy to hear it, as this feels like a hack.
 */
UNION ALL
(
    SELECT 0
)
LIMIT 1
    ;
$BODY$
    LANGUAGE SQL
    STABLE;

ALTER FUNCTION ProfitAndLossBalanceForAccountInPeriod(numeric, timestamp, timestamp)
    OWNER TO metasfresh;



DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_compareWithPrevious1Year boolean, IN p_compareWithPrevious3Years boolean);
DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp);

CREATE OR REPLACE FUNCTION ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp)
    RETURNS TABLE
            (
                name            text,
                value           text,
                isSummary       text,
                three_years_ago numeric,
                two_years_ago   numeric,
                one_year_ago    numeric,
                current_period  numeric
            )
AS
$BODY$

SELECT ev.name,
       ev.value,
       ev.issummary::text,
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

ALTER FUNCTION ProfitAndLossReport(timestamp, timestamp)
    OWNER TO metasfresh;

-- test: SELECT * FROM ProfitAndLossReport('1993-01-01'::Timestamp, '2992-01-01'::Timestamp);
-- test SELECT * FROM ProfitAndLossReport('2018-01-01'::Timestamp, '2018-07-01'::Timestamp);
