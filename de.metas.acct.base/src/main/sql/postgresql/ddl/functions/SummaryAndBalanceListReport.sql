--- backup for erpit
CREATE INDEX fas_dateacct
    ON fact_acct_summary (dateacct);
DROP INDEX fas_dateacct;
-- todo @ teo observation: it seems the query runs faster without the index fas_dateacct. (27 sec avg -> 22 sec avg => >10% perf change)
-- todo extract the index drop to own migration script
--------------------------------------


CREATE OR REPLACE FUNCTION SummaryAndBalanceListReport(p_dateFrom date,
                                                       p_dateTo date,
                                                       p_c_acctschema_id NUMERIC,
                                                       p_ad_org_id numeric,
                                                       p_account_id NUMERIC=NULL,
                                                       p_c_activity_id numeric=NULL)
    RETURNS table
            (
                AccountValue     text,
                AccountName      text,
                beginningBalance numeric,
                debit            numeric,
                credit           numeric,
                endingBalancw    numeric
            )
AS
$$
WITH filteredElementValues AS
         (
             SELECT ev.c_elementvalue_id,
                    ev.name  AccountName,
                    ev.value AccountValue
             FROM c_elementvalue ev
             WHERE TRUE
               AND (p_account_id IS NULL OR ev.c_elementvalue_id = p_account_id)
               AND (p_c_activity_id IS NULL OR ev.c_activity_id = p_c_activity_id)
             ORDER BY ev.c_elementvalue_id
         ),
     balances_test AS
         (
             SELECT --
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateFrom - INTERVAL '1 day')::date, p_ad_org_id)::de_metas_acct.BalanceAmt) begining,
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateTo - INTERVAL '1 day')::date, p_ad_org_id)::de_metas_acct.BalanceAmt)   ending,
                    ev.c_elementvalue_id,
                    ev.AccountName,
                    ev.AccountValue
             FROM filteredElementValues ev
         ),
     DATA AS
         (
             SELECT --
                    AccountValue,
                    AccountName,
                    (begining).balance                  beginningBalance,
                    (ending).debit - (begining).debit   debit,
                    (ending).credit - (begining).credit credit,
                    (ending).balance                    endingBalance
                    -- debug
--                     ,begining,
--                     ending,
--                     c_elementvalue_id
             FROM balances_test
         )
SELECT *
FROM DATA
$$
    LANGUAGE sql STABLE;
