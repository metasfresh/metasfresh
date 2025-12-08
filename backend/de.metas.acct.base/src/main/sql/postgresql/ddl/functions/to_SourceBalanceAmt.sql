DROP FUNCTION IF EXISTS de_metas_acct.to_SourceBalanceAmt(
    p_Balance     numeric,
    p_Currency_ID numeric)
;

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

/*
SELECT de_metas_acct.to_SourceBalanceAmt(66, null);
SELECT de_metas_acct.to_SourceBalanceAmt(66, 101);
 */