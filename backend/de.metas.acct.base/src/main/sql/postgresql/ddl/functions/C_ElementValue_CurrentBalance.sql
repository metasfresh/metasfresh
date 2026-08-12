-- Wrapper function for C_ElementValue.CurrentBalance virtual column.
-- Delegates schema+org resolution to getC_AcctSchema_AndOrg_For_C_ElementValue.
-- Recursively sums leaf descendants so summary accounts show the correct aggregated balance.

DROP FUNCTION IF EXISTS de_metas_acct.C_ElementValue_CurrentBalance(numeric);

CREATE OR REPLACE FUNCTION de_metas_acct.C_ElementValue_CurrentBalance(
    p_C_ElementValue_ID numeric)
RETURNS numeric
AS $BODY$
WITH RECURSIVE descendants AS (
    SELECT C_ElementValue_ID, IsSummary,
           ARRAY[C_ElementValue_ID]::numeric[] AS visited
    FROM C_ElementValue
    WHERE C_ElementValue_ID = p_C_ElementValue_ID
      AND IsActive = 'Y'

    UNION ALL

    SELECT ev.C_ElementValue_ID, ev.IsSummary,
           d.visited || ev.C_ElementValue_ID
    FROM C_ElementValue ev
    JOIN descendants d ON ev.Parent_ID = d.C_ElementValue_ID
    WHERE ev.IsActive = 'Y'
      AND NOT (ev.C_ElementValue_ID = ANY(d.visited))
)
SELECT COALESCE(SUM(
    (de_metas_acct.acctBalanceToDate(
        d.C_ElementValue_ID,
        so.C_AcctSchema_ID,
        now()::date,
        so.AD_Org_ID
    )).Balance
), 0)
FROM descendants d
JOIN getC_AcctSchema_AndOrg_For_C_ElementValue(p_C_ElementValue_ID) so ON TRUE
WHERE d.IsSummary = 'N'
$BODY$
LANGUAGE sql STABLE;
