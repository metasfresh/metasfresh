WITH fa_zero_balance AS (
    SELECT t.tablename,
           fa.record_id
    FROM fact_acct fa
    inner join ad_table t on fa.ad_table_id = t.ad_table_id
    GROUP BY t.tablename, fa.record_id
    HAVING count(1) > 0
       AND sum(amtacctdr) = 0
       AND sum(amtacctcr) = 0
       AND (sum(amtsourcedr) != 0 or sum(amtsourcecr) != 0)
)
SELECT
       "de_metas_acct".fact_acct_unpost(tablename, record_id)
FROM fa_zero_balance
;

