-- Helper: returns (C_AcctSchema_ID, AD_Org_ID) for a C_ElementValue, derived purely
-- from the data model. Used by C_ElementValue.CurrentBalance virtual column and any
-- future virtual column / report that needs to bind an account to its (schema, org).

DROP FUNCTION IF EXISTS getC_AcctSchema_AndOrg_For_C_ElementValue(numeric);

CREATE OR REPLACE FUNCTION getC_AcctSchema_AndOrg_For_C_ElementValue(p_C_ElementValue_ID numeric)
    RETURNS TABLE
            (
                C_AcctSchema_ID numeric,
                AD_Org_ID       numeric
            )
AS
$BODY$
SELECT acs.C_AcctSchema_ID,
       COALESCE(
           acs.AD_OrgOnly_ID,
           (SELECT MIN(o.AD_Org_ID)
              FROM AD_Org o
             WHERE o.AD_Client_ID = ev.AD_Client_ID
               AND o.AD_Org_ID > 0
               AND o.IsActive = 'Y')
       )
  FROM C_ElementValue ev
  JOIN C_AcctSchema_Element ase
    ON ase.C_Element_ID = ev.C_Element_ID
   AND ase.ElementType = 'AC'
   AND ase.IsActive = 'Y'
  JOIN C_AcctSchema acs
    ON acs.C_AcctSchema_ID = ase.C_AcctSchema_ID
   AND acs.IsActive = 'Y'
 WHERE ev.C_ElementValue_ID = p_C_ElementValue_ID
 LIMIT 1
$BODY$
    LANGUAGE sql STABLE
    COST 100;
