-- Fix AD_Val_Rule 183 ("C_ElementValue of Element") so that the Parent_ID filter
-- in the window filter panel shows options even when no C_Element_ID context is available.
--
-- Old code: C_ElementValue.C_Element_ID=@C_Element_ID@
--   → throws ExpressionEvaluationException when C_Element_ID absent from filter context
-- New code: (@C_Element_ID/0@ <= 0 OR C_ElementValue.C_Element_ID=@C_Element_ID/0@)
--   → /0 sets default value 0; shows all accounts when no chart selected, filters when one is

UPDATE AD_Val_Rule
SET Code    = '(@C_Element_ID/0@ <= 0 OR C_ElementValue.C_Element_ID=@C_Element_ID/0@)',
    Updated = TO_TIMESTAMP('2026-05-11 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 183
;
