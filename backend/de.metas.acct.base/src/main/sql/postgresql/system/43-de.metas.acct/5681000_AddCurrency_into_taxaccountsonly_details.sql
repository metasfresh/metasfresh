DROP FUNCTION IF EXISTS de_metas_acct.taxaccountsonly_details(p_AD_Org_ID     numeric(10, 0),
                                                              p_Account_ID    numeric,
                                                              p_C_Vat_Code_ID numeric,
                                                              p_DateFrom      date,
                                                              p_DateTo        date)
;

CREATE OR REPLACE FUNCTION de_metas_acct.taxaccountsonly_details(p_AD_Org_ID     numeric(10, 0),
                                                                 p_Account_ID    numeric,
                                                                 p_C_Vat_Code_ID numeric,
                                                                 p_DateFrom      date,
                                                                 p_DateTo        date)
    RETURNS TABLE
            (
                Balance           numeric,
                BalanceYear       numeric,
                accountNo         varchar,
                accountName       varchar,
                taxName           varchar,
                C_Tax_ID          numeric,
                vatcode           varchar,
                C_ElementValue_ID numeric,
                param_startdate   date,
                param_enddate     date,
                param_konto       varchar,
                param_vatcode     varchar,
                param_org         varchar,
                currency          char
            )
AS
$BODY$

SELECT t.Balance,
       t.BalanceYear,
       t.accountno,
       t.accountname,
       t.taxName,
       t.C_Tax_ID,
       t.vatcode,
       t.C_ElementValue_ID,
       t.param_startdate,
       t.param_enddate,
       (CASE
            WHEN p_Account_ID IS NULL
                THEN NULL
                ELSE (SELECT value || ' - ' || name
                      FROM C_ElementValue
                      WHERE C_ElementValue_ID = p_Account_ID)
        END)      AS param_konto,
       t.param_vatcode,
       t.param_org,
       c.iso_code AS currency

FROM (
         SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
         FROM C_Tax_Acct ta
                  INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID IN
                                                       (ta.T_Due_Acct, ta.T_Credit_Acct))
         WHERE p_Account_ID IS NULL
            OR vc.Account_ID = p_Account_ID
     ) AS ev
         INNER JOIN de_metas_acct.taxaccounts_details(p_AD_Org_ID, ev.C_ElementValue_ID, p_C_Vat_Code_ID, p_DateFrom, p_DateTo) AS t ON TRUE
         INNER JOIN c_acctschema aas
                    ON aas.ad_orgonly_id = p_AD_Org_ID
         INNER JOIN c_currency C ON C.c_currency_id = aas.c_currency_id
WHERE t.taxname IS NOT NULL
ORDER BY vatcode, accountno
    ;
$BODY$
    LANGUAGE sql STABLE
;
