-- getSOCreditUsedForDepartment
CREATE OR REPLACE FUNCTION getSOCreditUsedForDepartment(p_m_department_id numeric) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(stats.so_creditused),0)
FROM C_BPartner sectionPartner
         INNER JOIN m_department_sectioncode depSectionCode ON sectionPartner.m_sectioncode_id = depSectionCode.m_sectioncode_id
         INNER JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
WHERE depSectionCode.m_department_id = p_M_Department_ID
GROUP BY depSectionCode.m_department_id

$$
;

COMMENT ON FUNCTION getSOCreditUsedForDepartment(numeric) IS 'TEST: SELECT  getSOCreditUsedForDepartment(1000001);'
;

ALTER FUNCTION getSOCreditUsedForDepartment(numeric) OWNER TO metasfresh
;
