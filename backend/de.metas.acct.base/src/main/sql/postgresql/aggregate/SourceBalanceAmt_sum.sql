DROP AGGREGATE IF EXISTS sum (de_metas_acct.SourceBalanceAmt)
;

CREATE AGGREGATE sum (de_metas_acct.SourceBalanceAmt)
    (
    SFUNC = de_metas_acct.SourceBalanceAmt_add,
    STYPE = de_metas_acct.SourceBalanceAmt
    )
;


--
-- Tests
--

/*
SELECT SUM(t.b)
FROM (SELECT (101, 10, 102, 100, NULL, NULL, NULL, NULL, NULL, NULL)::de_metas_acct.SourceBalanceAmt AS b
      UNION ALL
      SELECT (102, 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)::de_metas_acct.SourceBalanceAmt) t
;

SELECT fa.*,
       SUM(de_metas_acct.to_SourceBalanceAmt(fa.SourceBalance, fa.C_Currency_ID)) OVER (PARTITION BY fa.account_id ORDER BY fa.dateacct, fa.fact_acct_id) AS rolling_balance
FROM ( --
         SELECT '2022-05-01'::date AS dateacct, 100::numeric AS SourceBalance, 101::numeric AS C_Currency_ID, 1 AS fact_acct_id, 1 as account_id
         UNION ALL
         SELECT '2022-05-02'::date AS dateacct, 90::numeric AS SourceBalance, 101::numeric AS C_Currency_ID, 2 AS fact_acct_id, 1 as account_id
         UNION ALL
         SELECT '2022-05-02'::date AS dateacct, 33::numeric AS SourceBalance, 102::numeric AS C_Currency_ID, 3 AS fact_acct_id, 1 as account_id
         --
     ) fa
*/
