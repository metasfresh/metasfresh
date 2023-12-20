DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                             p_DateFrom  date,
                                                             p_DateTo    date)
;


CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                                p_DateFrom  date,
                                                                p_DateTo    date)
    RETURNS TABLE
            (
                Balance     numeric,
                BalanceYear numeric,
                taxName     varchar,
                vatcode     varchar
            )
AS
$BODY$
SELECT SUM(t.balance) AS balance, SUM(t.balanceyear) AS balanceyear, t.taxname, t.vatcode
FROM (
         SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
         FROM C_Tax_Acct ta
                  INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID IN
                                                       (ta.T_Due_Acct, ta.T_Credit_Acct))
     ) AS ev
         INNER JOIN de_metas_acct.taxaccounts_details(p_AD_Org_ID,
                                                      ev.C_ElementValue_ID,
                                                      NULL,
                                                      p_DateFrom,
                                                      p_DateTo) AS t ON TRUE
WHERE t.taxname IS NOT NULL
GROUP BY t.vatcode, t.taxname
ORDER BY vatcode
$BODY$
    LANGUAGE sql STABLE
;