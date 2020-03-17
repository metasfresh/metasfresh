DROP FUNCTION IF EXISTS ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric, IN p_startDate timestamp, IN p_endDate timestamp)
;

DROP FUNCTION IF EXISTS ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric, IN p_startDate timestamp, IN p_endDate timestamp, IN p_AD_Org_Id numeric)
;

/**
  fact_acct_summary's amtacctdr and amtacctcr are always increasing, and exactly 1 per day per organisation per posting type.
  Therefore calculating the balance for the winter sales period: 2019-12-20 -> 2020-02-01 is done by subtracting endDate_balance - the_balance_before_startDate as follows:
  (2020-02-01.debit - 2020-02-01.credit) - (2019-12-19.debit - 2019-12-19.credit)

 */
CREATE OR REPLACE FUNCTION ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric,
                                                                  IN p_startDate         timestamp,
                                                                  IN p_endDate           timestamp,
                                                                  IN p_AD_Org_Id         numeric)
    RETURNS NUMERIC
AS
$BODY$


WITH factAcctSummaryNoPeriod AS
         (
             SELECT --
                    fas.amtacctdr,
                    fas.amtacctcr,
                    fas.dateacct
             FROM c_elementvalue ev
                      INNER JOIN fact_acct_summary fas ON fas.account_id = ev.c_elementvalue_id
             WHERE TRUE
               AND ev.c_elementvalue_id = p_C_ElementValue_ID
               AND ev.accounttype IN ('E', 'R')
               AND fas.ad_org_id = p_AD_Org_ID
               -- only 'Actual' posting type is used
               AND fas.postingtype = 'A'
         ),
     factAcctSummaryInPeriod AS
         (
             SELECT --
                    fas.amtacctdr,
                    fas.amtacctcr,
                    fas.dateacct
             FROM factAcctSummaryNoPeriod fas
             WHERE TRUE
               AND fas.dateacct >= p_startDate
               AND fas.dateacct <= p_endDate
         ),
     balanceUpToStartDay AS
         (
             SELECT fas.amtacctdr - fas.amtacctcr balance
             FROM factAcctSummaryNoPeriod fas
             WHERE TRUE
               AND fas.dateacct = (SELECT max(fas.dateacct)
                                   FROM factAcctSummaryNoPeriod fas
                                   WHERE fas.dateacct < p_startDate)
             UNION ALL
             (
                 SELECT 0
             )
             LIMIT 1
         ),
     balanceUpToEndDate AS
         (
             SELECT fas.amtacctdr - fas.amtacctcr balance
             FROM factAcctSummaryInPeriod fas
             WHERE fas.dateacct = (SELECT max(fas2.dateacct) FROM factAcctSummaryInPeriod fas2)
         )

SELECT maxBal.balance - minBal.balance
FROM balanceUpToStartDay minBal,
     balanceUpToEndDate maxBal

    /**
      Why is this union needed?
      In case no `fact_acct_summary` for an account_id exists, `result_table` returns no rows. It doesn't return null (so that i can use coalesce), but returns NO rows.
         example: SELECT ProfitAndLossBalanceForAccountInPeriod(1, '1993-01-01'::Timestamp, '2992-01-01'::Timestamp, 1000000); -- this returns NO rows
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
    STABLE
;

ALTER FUNCTION ProfitAndLossBalanceForAccountInPeriod(numeric, timestamp, timestamp, numeric)
    OWNER TO metasfresh
;
