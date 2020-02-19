DROP FUNCTION IF EXISTS SummaryAndBalanceReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_c_activity_id NUMERIC);
DROP FUNCTION IF EXISTS SummaryAndBalanceReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC);
DROP FUNCTION IF EXISTS SummaryAndBalanceReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_ExcludeEmptyLines text);



CREATE OR REPLACE FUNCTION SummaryAndBalanceReport(p_dateFrom date,
                                                   p_dateTo date,
                                                   p_c_acctschema_id NUMERIC,
                                                   p_ad_org_id numeric,
                                                   p_account_id NUMERIC=NULL,
                                                   p_ExcludeEmptyLines text = 'Y')
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
                    ev.AccountValue::text
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
         ),
     dataExcludingEmptyLines AS
         (
             SELECT AccountValue,
                    AccountName,
                    beginningBalance,
                    debit,
                    credit,
                    endingBalance
             FROM data
             WHERE (p_ExcludeEmptyLines = 'N' OR (beginningBalance != 0 AND endingBalance != 0))
         )
SELECT AccountValue,
       AccountName,
       beginningBalance,
       debit,
       credit,
       endingBalance
FROM dataExcludingEmptyLines
ORDER BY AccountValue
$$
    LANGUAGE sql STABLE;

/*
How to run:

select * from SummaryAndBalanceReport('2018-04-01'::date,
                                          '2018-05-31'::date,
                                          1000000,
                                          1000000)
;
 */
