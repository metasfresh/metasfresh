--
-- supposed to be run before 
-- /de.metas.acct.base/src/main/sql/postgresql/system/43-de.metas.acct/5436860_sys_09694_update_fact_acct_EndingBalance.sql 
--
ALTER FUNCTION public.acctbalancetodate(numeric, numeric, date)
SET SCHEMA de_metas_acct;

ALTER FUNCTION public.fact_acct_endingbalance(fact_acct)
SET SCHEMA de_metas_acct;

ALTER FUNCTION public.fact_acct_endingbalance_rebuildall()
SET SCHEMA de_metas_acct;

ALTER FUNCTION public.fact_acct_endingbalance_updatefortag(p_processingtag character varying)
SET SCHEMA de_metas_acct;
