SELECT db_drop_functions('*.getAcct_CostElement_ID')
;

CREATE OR REPLACE FUNCTION de_metas_acct.getAcct_CostElement_ID(p_C_AcctSchema_ID numeric)
    RETURNS numeric
AS
$BODY$
WITH only_cost_elements AS (SELECT asce.m_costelement_id
                            FROM C_AcctSchema_CostElement asce
                            WHERE asce.c_acctschema_id = p_C_AcctSchema_ID
                              AND asce.isactive = 'Y')
SELECT ce.m_costelement_id
FROM c_acctschema cas
         INNER JOIN m_costelement ce ON ce.costingmethod = cas.costingmethod
WHERE TRUE
  AND cas.c_acctschema_id = p_C_AcctSchema_ID
  AND ce.isactive = 'Y'
  AND ce.costelementtype = 'M' -- Material
  AND (
    NOT EXISTS (SELECT 1 FROM only_cost_elements)
        OR EXISTS (SELECT 1 FROM only_cost_elements oce WHERE oce.m_costelement_id = ce.m_costelement_id)
    )
ORDER BY ce.m_costelement_id
LIMIT 1
$BODY$
    LANGUAGE sql STABLE
;


-- Test
/*
select c_acctschema_id, name, de_metas_acct.getAcct_CostElement_ID(c_acctschema_id) as M_CostElement_ID from c_acctschema;
 */



