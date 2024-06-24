DROP FUNCTION IF EXISTS de_metas_acct.sourceBalanceUntilDate(p_Account_ID      numeric,
                                                             p_C_AcctSchema_ID numeric,
                                                             p_DateAcct        date,
                                                             p_AD_Org_ID       numeric)
;

DROP AGGREGATE IF EXISTS sum (de_metas_acct.SourceBalanceAmt)
;


DROP FUNCTION IF EXISTS de_metas_acct.SourceBalanceAmt_Add(
    p_sourceBalanceAmt de_metas_acct.SourceBalanceAmt,
    p_AmtToAdd         numeric,
    p_Currency_ID      numeric)
;

DROP FUNCTION IF EXISTS de_metas_acct.SourceBalanceAmt_Add(
    p_sourceBalanceAmt1 de_metas_acct.SourceBalanceAmt,
    p_sourceBalanceAmt2 de_metas_acct.SourceBalanceAmt)
;


DROP FUNCTION IF EXISTS de_metas_acct.to_SourceBalanceAmt(
    p_Balance     numeric,
    p_Currency_ID numeric)
;

DROP TYPE IF EXISTS de_metas_acct.SourceBalanceAmt
-- CASCADE
;

--
--
--
--
--

CREATE TYPE de_metas_acct.SourceBalanceAmt AS
(
    c_currency_id1 numeric,
    balance1       numeric,
    c_currency_id2 numeric,
    balance2       numeric,
    c_currency_id3 numeric,
    balance3       numeric,
    c_currency_id4 numeric,
    balance4       numeric,
    c_currency_id5 numeric,
    balance5       numeric
)
;

--
--
--
--
--


CREATE OR REPLACE FUNCTION de_metas_acct.to_SourceBalanceAmt(
    p_Balance     numeric,
    p_Currency_ID numeric)
    RETURNS de_metas_acct.SourceBalanceAmt
    LANGUAGE plpgsql
    STABLE
AS
$BODY$
DECLARE
    v_result de_metas_acct.SourceBalanceAmt;
BEGIN
    IF (p_Currency_ID IS NULL) THEN
        RETURN v_result;
    END IF;

    v_result.c_currency_id1 = p_Currency_ID;
    v_result.balance1 = COALESCE(p_Balance, 0);
    RETURN v_result;
END;
$BODY$
;

--
--
--
--
--


CREATE OR REPLACE FUNCTION de_metas_acct.SourceBalanceAmt_Add(
    p_sourceBalanceAmt de_metas_acct.SourceBalanceAmt,
    p_AmtToAdd         numeric,
    p_Currency_ID      numeric)
    RETURNS de_metas_acct.SourceBalanceAmt
    LANGUAGE plpgsql
    STABLE
AS
$BODY$
DECLARE
    v_result de_metas_acct.SourceBalanceAmt;
    v_added  bool := FALSE;
BEGIN
    IF (p_Currency_ID IS NULL) THEN
        RAISE EXCEPTION 'Parameter p_Currency_ID cannot be null';
    END IF;

    v_result := p_sourceBalanceAmt;

    IF (v_result.c_currency_id1 = p_Currency_ID) THEN
        v_result.balance1 := COALESCE(v_result.balance1, 0) + COALESCE(p_AmtToAdd, 0);
        v_added := TRUE;
    ELSIF (v_result.c_currency_id2 = p_Currency_ID) THEN
        v_result.balance2 := COALESCE(v_result.balance2, 0) + COALESCE(p_AmtToAdd, 0);
        v_added := TRUE;
    ELSIF (v_result.c_currency_id3 = p_Currency_ID) THEN
        v_result.balance3 := COALESCE(v_result.balance3, 0) + COALESCE(p_AmtToAdd, 0);
        v_added := TRUE;
    ELSIF (v_result.c_currency_id4 = p_Currency_ID) THEN
        v_result.balance4 := COALESCE(v_result.balance4, 0) + COALESCE(p_AmtToAdd, 0);
        v_added := TRUE;
    ELSIF (v_result.c_currency_id5 = p_Currency_ID) THEN
        v_result.balance5 := COALESCE(v_result.balance5, 0) + COALESCE(p_AmtToAdd, 0);
        v_added := TRUE;
    END IF;

    IF (NOT v_added) THEN
        IF (v_result.c_currency_id1 IS NULL) THEN
            v_result.c_currency_id1 = p_Currency_ID;
            v_result.balance1 = COALESCE(p_AmtToAdd, 0);
        ELSIF (v_result.c_currency_id2 IS NULL) THEN
            v_result.c_currency_id2 = p_Currency_ID;
            v_result.balance2 = COALESCE(p_AmtToAdd, 0);
        ELSIF (v_result.c_currency_id3 IS NULL) THEN
            v_result.c_currency_id3 = p_Currency_ID;
            v_result.balance3 = COALESCE(p_AmtToAdd, 0);
        ELSIF (v_result.c_currency_id4 IS NULL) THEN
            v_result.c_currency_id4 = p_Currency_ID;
            v_result.balance4 = COALESCE(p_AmtToAdd, 0);
        ELSIF (v_result.c_currency_id5 IS NULL) THEN
            v_result.c_currency_id5 = p_Currency_ID;
            v_result.balance5 = COALESCE(p_AmtToAdd, 0);
        ELSE
            RAISE EXCEPTION 'Found more source currencies than supported';
        END IF;
    END IF;

    RETURN v_result;
END;
$BODY$
;

--
--
--
--
--

CREATE OR REPLACE FUNCTION de_metas_acct.SourceBalanceAmt_Add(
    p_sourceBalanceAmt1 de_metas_acct.SourceBalanceAmt,
    p_sourceBalanceAmt2 de_metas_acct.SourceBalanceAmt)
    RETURNS de_metas_acct.SourceBalanceAmt
    LANGUAGE plpgsql
    STABLE
AS
$BODY$
DECLARE
    v_result de_metas_acct.SourceBalanceAmt;
BEGIN
    v_result := p_sourceBalanceAmt1;
    IF (p_sourceBalanceAmt2.c_currency_id1 IS NOT NULL) THEN
        v_result := de_metas_acct.SourceBalanceAmt_Add(v_result, p_sourceBalanceAmt2.balance1, p_sourceBalanceAmt2.c_currency_id1);
    END IF;
    IF (p_sourceBalanceAmt2.c_currency_id2 IS NOT NULL) THEN
        v_result := de_metas_acct.SourceBalanceAmt_Add(v_result, p_sourceBalanceAmt2.balance2, p_sourceBalanceAmt2.c_currency_id2);
    END IF;
    IF (p_sourceBalanceAmt2.c_currency_id3 IS NOT NULL) THEN
        v_result := de_metas_acct.SourceBalanceAmt_Add(v_result, p_sourceBalanceAmt2.balance3, p_sourceBalanceAmt2.c_currency_id3);
    END IF;
    IF (p_sourceBalanceAmt2.c_currency_id4 IS NOT NULL) THEN
        v_result := de_metas_acct.SourceBalanceAmt_Add(v_result, p_sourceBalanceAmt2.balance4, p_sourceBalanceAmt2.c_currency_id4);
    END IF;
    IF (p_sourceBalanceAmt2.c_currency_id5 IS NOT NULL) THEN
        v_result := de_metas_acct.SourceBalanceAmt_Add(v_result, p_sourceBalanceAmt2.balance5, p_sourceBalanceAmt2.c_currency_id5);
    END IF;

    RETURN v_result;
END;
$BODY$
;

--
--
--
--
--


CREATE AGGREGATE sum (de_metas_acct.SourceBalanceAmt)
    (
    SFUNC = de_metas_acct.SourceBalanceAmt_add,
    STYPE = de_metas_acct.SourceBalanceAmt
    )
;

--
--
--
--
--


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
    FROM (SELECT de_metas_acct.to_SourceBalanceAmt(SUM(fa.AmtAcctDr - fa.AmtAcctCr), fa.c_currency_id) AS Balance
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
