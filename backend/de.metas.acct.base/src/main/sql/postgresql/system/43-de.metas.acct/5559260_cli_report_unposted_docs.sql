select t.tablename, u.record_id
, de_metas_acct.fact_acct_unpost(t.tablename, u.record_id)
from rv_unposted u
inner join ad_table t on t.ad_table_id=u.ad_table_id
;

