DROP FUNCTION IF EXISTS de_metas_acct.SourceBalanceAmt_Add(
    p_sourceBalanceAmt de_metas_acct.SourceBalanceAmt,
    p_AmtToAdd         numeric,
    p_Currency_ID      numeric)
;

DROP FUNCTION IF EXISTS de_metas_acct.SourceBalanceAmt_Add(
    p_sourceBalanceAmt1 de_metas_acct.SourceBalanceAmt,
    p_sourceBalanceAmt2 de_metas_acct.SourceBalanceAmt)
;

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


/*
select de_metas_acct.SourceBalanceAmt_Add(
    (101,10,null,null,null,null,null,null,null,null),
    10, 101
);

select de_metas_acct.SourceBalanceAmt_Add(
    (101,10,102,100,null,null,null,null,null,null),
    (102,9,null,null,null,null,null,null,null,null)
);
*/
