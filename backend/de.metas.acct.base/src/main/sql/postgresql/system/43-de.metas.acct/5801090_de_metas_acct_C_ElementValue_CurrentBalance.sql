-- Wrapper function for C_ElementValue.CurrentBalance virtual column.
-- Delegates schema+org resolution to getC_AcctSchema_AndOrg_For_C_ElementValue
-- (defined in 5801080).

DROP FUNCTION IF EXISTS de_metas_acct.C_ElementValue_CurrentBalance(numeric);

CREATE OR REPLACE FUNCTION de_metas_acct.C_ElementValue_CurrentBalance(
    p_C_ElementValue_ID numeric)
RETURNS numeric
AS $BODY$
SELECT (de_metas_acct.acctBalanceToDate(
            p_C_ElementValue_ID,
            so.C_AcctSchema_ID,
            now()::date,
            so.AD_Org_ID
        )).Balance
  FROM getC_AcctSchema_AndOrg_For_C_ElementValue(p_C_ElementValue_ID) so
$BODY$
LANGUAGE sql STABLE;
