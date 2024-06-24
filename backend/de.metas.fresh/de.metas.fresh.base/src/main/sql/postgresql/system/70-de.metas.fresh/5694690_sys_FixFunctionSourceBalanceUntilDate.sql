DROP FUNCTION IF EXISTS de_metas_acct.sourceBalanceUntilDate(p_Account_ID      numeric,
                                                             p_C_AcctSchema_ID numeric,
                                                             p_DateAcct        date,
                                                             p_AD_Org_ID       numeric)
;



CREATE OR REPLACE FUNCTION de_metas_acct.sourceBalanceUntilDate(p_Account_ID      numeric,
                                                                p_C_AcctSchema_ID numeric,
                                                                p_DateAcct        date,
                                                                p_AD_Org_ID       numeric)
    RETURNS de_metas_acct.SourceBalanceAmt
    LANGUAGE plpgsql
    STABLE
AS
$BODY$
DECLARE
    v_accountInfo   record;
    v_dateAcctStart date;
    v_row           record;
BEGIN
    SELECT ev.Value, ev.AccountType
    INTO v_accountInfo
    FROM c_elementvalue ev
    WHERE ev.c_elementvalue_id = p_Account_ID;

    IF (v_accountInfo IS NULL) THEN
        RAISE EXCEPTION 'No account found p_Account_ID=%', p_Account_ID;
    END IF;

    -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
    -- see acctBalanceToDate too
    IF (v_accountInfo.AccountType IN ('E', 'R')) THEN
        v_dateAcctStart := DATE_TRUNC('year', p_DateAcct);
    END IF;

    RAISE DEBUG 'Account=%/%, AccountType=%, DateAcct=[%, %), C_AcctSchema_ID=%, AD_Org_ID=%',
        v_accountInfo.Value, p_Account_ID, v_accountInfo.AccountType, v_dateAcctStart, p_DateAcct, p_C_AcctSchema_ID, p_AD_Org_ID;

    -- NOTE: it seems like user-defined aggregates are very slow in case of huge amount of data,
    -- so to make it fast, we first aggregate the balances per currency and then we aggregate them to a resulting source balance
    SELECT SUM(t.Balance) AS Balance
    INTO v_row
    FROM (SELECT de_metas_acct.to_SourceBalanceAmt(SUM(fa.AmtSourceDr - fa.AmtSourceCr), fa.c_currency_id) AS Balance
          FROM fact_acct fa
          WHERE fa.Account_ID = p_Account_ID
            AND fa.C_AcctSchema_ID = p_C_AcctSchema_ID
            AND fa.PostingType IN ('A', 'Y')
            AND fa.ad_org_id = p_AD_Org_ID
            AND (v_dateAcctStart IS NULL OR fa.DateAcct >= v_dateAcctStart)
            AND fa.DateAcct < p_DateAcct
          GROUP BY fa.c_currency_id
          ORDER BY fa.c_currency_id) t;

    RAISE DEBUG '... => %', v_row;

    RETURN v_row.Balance;
END;
$BODY$
;



-- | account\_id | docbasetype |
-- | :--- | :--- |
-- | 1002995 | APC |
-- | 1003355 | APC |
-- | 1002950 | ARI |
-- | 1003333 | ARI |
-- | 1002995 | API |
-- | 1002950 | ARC |
-- | 1003355 | API |
-- select distinct account_id, docbasetype from fact_acct where ad_table_id=318 and line_id is null and c_tax_id is null

-- select t.account_id, count(1) cnt
--     from (select distinct account_id, c_currency_id from fact_acct) t
-- group by t.account_id
-- having count(1) > 1
-- order by cnt desc

-- select ev.c_elementvalue_id, ev.value, ev.name, ev.accounttype, t.tablename, fa.c_acctschema_id, *
-- from fact_acct fa
-- left outer join ad_table t on t.ad_table_id=fa.ad_table_id
-- inner join c_elementvalue ev on ev.c_elementvalue_id=fa.account_id
-- where fa.account_id=1003057

-- | c\_elementvalue\_id | value | name | accounttype | tablename | c\_acctschema\_id |
-- | :--- | :--- | :--- | :--- | :--- | :--- |
-- | 1003057 | 30020 | Skonto Debitoren | R | C\_AllocationHdr | 1000000 |


/*
SELECT de_metas_acct.sourceBalanceUntilDate(
               p_Account_ID := 1003057,
               p_C_AcctSchema_ID := 1000000,
               p_DateAcct := '2022-05-01',
               p_AD_Org_ID := 1000000
           );



Account=12700/1002970, AccountType=A, DateAcct=[<NULL>, 2022-05-01), C_AcctSchema_ID=1000000, AD_Org_ID=1000000
... => (318,9204730.66,,,,,,,,)


SELECT de_metas_acct.sourceBalanceUntilDate(
               p_Account_ID := 1002970,
               p_C_AcctSchema_ID := 1000000,
               p_DateAcct := '2022-05-01',
               p_AD_Org_ID := 1000000
           );

SELECT de_metas_acct.sourceBalanceUntilDate(
               p_Account_ID := 1003057,
               p_C_AcctSchema_ID := 1000000,
               p_DateAcct := '2022-05-01',
               p_AD_Org_ID := 1000000
           );



SELECT de_metas_acct.to_SourceBalanceAmt(SUM(fa.AmtAcctDr - fa.AmtAcctCr), fa.c_currency_id) AS Balance,
       COUNT(1)
FROM fact_acct fa
WHERE TRUE
  --  AND fa.Account_ID = 1003057
  AND fa.Account_ID = 1002970
  AND fa.C_AcctSchema_ID = 1000000
  AND fa.PostingType IN ('A', 'Y')
  AND fa.ad_org_id = 1000000
  --AND (v_dateAcctStart IS NULL OR fa.DateAcct >= v_dateAcctStart)
  AND fa.DateAcct < '2022-05-01'
GROUP BY fa.c_currency_id
ORDER BY fa.c_currency_id
;

SELECT SUM(t.balance) AS balance,
       SUM(t.count)   AS count
FROM (SELECT de_metas_acct.to_SourceBalanceAmt(SUM(fa.AmtAcctDr - fa.AmtAcctCr), fa.c_currency_id) AS Balance,
             COUNT(1)                                                                              AS count
      FROM fact_acct fa
      WHERE TRUE
        --  AND fa.Account_ID = 1003057
        AND fa.Account_ID = 1002970
        AND fa.C_AcctSchema_ID = 1000000
        AND fa.PostingType IN ('A', 'Y')
        AND fa.ad_org_id = 1000000
        --AND (v_dateAcctStart IS NULL OR fa.DateAcct >= v_dateAcctStart)
        AND fa.DateAcct < '2022-05-01'
      GROUP BY fa.c_currency_id
      ORDER BY fa.c_currency_id) t
;


SELECT SUM(de_metas_acct.to_SourceBalanceAmt(fa.AmtAcctDr - fa.AmtAcctCr, fa.c_currency_id)) AS Balance,
       COUNT(1)                                                                              AS count
FROM fact_acct fa
WHERE TRUE
  --  AND fa.Account_ID = 1003057
  AND fa.Account_ID = 1002970
  AND fa.C_AcctSchema_ID = 1000000
  AND fa.PostingType IN ('A', 'Y')
  AND fa.ad_org_id = 1000000
  --AND (v_dateAcctStart IS NULL OR fa.DateAcct >= v_dateAcctStart)
  AND fa.DateAcct < '2022-05-01'
;


 */