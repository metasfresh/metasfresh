DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                          p_DateFrom date,
                                                          p_DateTo date);


CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                             p_DateFrom date,
                                                             p_DateTo date)
    RETURNS TABLE
            (
                Balance           numeric,
                BalanceYear       numeric,
                taxName           varchar,
                vatcode           varchar
            )
AS
$BODY$
SELECT sum( t.balance) as balance, sum( t.balanceyear) as balanceyear, t.taxname, t.vatcode
from

    (
        select distinct vc.Account_ID as C_ElementValue_ID
        from C_Tax_Acct ta
                 inner join C_ValidCombination vc on (vc.C_ValidCombination_ID in
                                                      (ta.T_Liability_Acct, ta.T_Receivables_Acct, ta.T_Due_Acct, ta.T_Credit_Acct, ta.T_Expense_Acct, ta.t_revenue_acct,
                                                      ta.t_paydiscount_exp_acct, ta.t_paydiscount_rev_acct))
            and vc.isActive = 'Y'
        where ta.isActive = 'Y'
        ) as ev
        INNER JOIN de_metas_acct.taxaccounts_details(p_AD_Org_ID,
                                                             ev.C_ElementValue_ID,
                                                             NULL,
                                                             p_DateFrom,
                                                             p_DateTo) as t on TRUE
WHERE t.vatcode is not null
GROUP BY  t.vatcode, t.taxname
order by vatcode
$BODY$
    LANGUAGE sql STABLE;
