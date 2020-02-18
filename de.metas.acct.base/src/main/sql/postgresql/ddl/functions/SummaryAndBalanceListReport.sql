DROP FUNCTION IF EXISTS SummaryAndBalanceListReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_c_activity_id NUMERIC);
DROP FUNCTION IF EXISTS SummaryAndBalanceListReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC);



CREATE OR REPLACE FUNCTION SummaryAndBalanceListReport(p_dateFrom date,
                                                       p_dateTo date,
                                                       p_c_acctschema_id NUMERIC,
                                                       p_ad_org_id numeric,
                                                       p_account_id NUMERIC=NULL)
    RETURNS table
            (
                AccountValue     text,
                AccountName      text,
                beginningBalance numeric,
                debit            numeric,
                credit           numeric,
                endingBalance    numeric
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
             ORDER BY ev.c_elementvalue_id
         ),
     balances AS
         (
             SELECT --
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateFrom - INTERVAL '1 day')::date, p_ad_org_id)::de_metas_acct.BalanceAmt) begining,
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateTo)::date, p_ad_org_id)::de_metas_acct.BalanceAmt)                      ending,
                    ev.c_elementvalue_id,
                    ev.AccountName,
                    ev.AccountValue
             FROM filteredElementValues ev
         ),
     data AS
         (
             SELECT --
                    AccountValue,
                    AccountName,
                    (begining).balance                  beginningBalance,
                    (ending).debit - (begining).debit   debit,
                    (ending).credit - (begining).credit credit,
                    (ending).balance                    endingBalance
             FROM balances
         )
SELECT *
FROM data
$$
    LANGUAGE sql STABLE;

/*
How to run:

select * from SummaryAndBalanceListReport('2018-04-01'::date,
                                          '2018-05-31'::date,
                                          1000000,
                                          1000000)
;
 */
