/* run with p_force='Y' because
   - in a rollout, no accounting processor is running.
   - this means that we have staled records which need to be cleaned up anyways
*/
select t.tablename, u.record_id
, de_metas_acct.fact_acct_unpost(t.tablename, u.record_id, 'Y'/*p_force*/)
from rv_unposted u
inner join ad_table t on t.ad_table_id=u.ad_table_id
;

