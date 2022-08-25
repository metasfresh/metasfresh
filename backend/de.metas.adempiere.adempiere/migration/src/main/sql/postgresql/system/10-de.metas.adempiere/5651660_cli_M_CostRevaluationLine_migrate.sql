UPDATE m_costrevaluationline crl
SET c_acctschema_id=cr.c_acctschema_id,
    m_costelement_id=cr.m_costelement_id,
    m_costtype_id=(SELECT cas.m_costtype_id FROM c_acctschema cas WHERE cas.c_acctschema_id = cr.c_acctschema_id)
FROM m_costrevaluation cr
WHERE cr.m_costrevaluation_id = crl.m_costrevaluation_id
;

