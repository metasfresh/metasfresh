-- Wrapper function for C_ElementValue.CurrentBalance virtual column.
-- Resolves the client's primary AcctSchema via AD_ClientInfo.C_AcctSchema1_ID
-- and delegates to acctBalanceToDate, returning only the Balance component.

DROP FUNCTION IF EXISTS de_metas_acct.C_ElementValue_CurrentBalance(numeric, numeric);

CREATE OR REPLACE FUNCTION de_metas_acct.C_ElementValue_CurrentBalance(
    p_C_ElementValue_ID numeric,
    p_AD_Org_ID numeric)
RETURNS numeric
AS $BODY$
SELECT (de_metas_acct.acctBalanceToDate(
            p_C_ElementValue_ID,
            getC_AcctSchema_ID(
                (SELECT AD_Client_ID FROM C_ElementValue WHERE C_ElementValue_ID = p_C_ElementValue_ID),
                p_AD_Org_ID
            ),
            now()::date,
            p_AD_Org_ID
        )).Balance
$BODY$
LANGUAGE sql STABLE;
