DROP FUNCTION IF EXISTS getC_AcctSchema_AndOrg_For_C_ElementValue(numeric);

CREATE OR REPLACE FUNCTION getC_AcctSchema_AndOrg_For_C_ElementValue(p_C_ElementValue_ID numeric)
    RETURNS TABLE
            (
                C_AcctSchema_ID numeric,
                AD_Org_ID       numeric
            )
AS
$BODY$
SELECT COALESCE(acs_elem.C_AcctSchema_ID, acs_client.C_AcctSchema_ID),
       COALESCE(
           COALESCE(acs_elem.AD_OrgOnly_ID, acs_client.AD_OrgOnly_ID),
           (SELECT MIN(o.AD_Org_ID)
              FROM AD_Org o
             WHERE o.AD_Client_ID = ev.AD_Client_ID
               AND o.AD_Org_ID > 0
               AND o.IsActive = 'Y')
       )
  FROM C_ElementValue ev
  LEFT JOIN C_AcctSchema_Element ase
    ON ase.C_Element_ID = ev.C_Element_ID
   AND ase.ElementType = 'AC'
   AND ase.IsActive = 'Y'
  LEFT JOIN C_AcctSchema acs_elem
    ON acs_elem.C_AcctSchema_ID = ase.C_AcctSchema_ID
   AND acs_elem.IsActive = 'Y'
  LEFT JOIN AD_ClientInfo ci ON ci.AD_Client_ID = ev.AD_Client_ID AND ci.IsActive = 'Y'
  LEFT JOIN C_AcctSchema acs_client
    ON acs_client.C_AcctSchema_ID = ci.C_AcctSchema1_ID
   AND acs_client.IsActive = 'Y'
 WHERE ev.C_ElementValue_ID = p_C_ElementValue_ID
 LIMIT 1
$BODY$
    LANGUAGE sql STABLE
    COST 100;

COMMENT ON FUNCTION getC_AcctSchema_AndOrg_For_C_ElementValue(numeric) IS '
 Returns the (C_AcctSchema_ID, AD_Org_ID) pair that the given C_ElementValue posts
 against, derived purely from the data model:
   * C_AcctSchema_ID  — the schema that owns the chart-of-accounts (C_Element)
                        this account belongs to (via C_AcctSchema_Element with
                        ElementType=''AC'', IsActive=''Y'').
   * AD_Org_ID        — the schema''s AD_OrgOnly_ID when set (multi-schema clients
                        with one schema per org); falls back to the client''s
                        single non-system org for single-schema clients with
                        AD_OrgOnly_ID NULL.

 Returns no rows when no matching active schema is found (degenerate misconfig).
 Sibling of getC_AcctSchema_ID(client, org); centralises the chart-driven schema
 lookup rule for use by virtual ColumnSQL columns and reports.';
