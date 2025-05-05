DROP FUNCTION IF EXISTS getOpenBalanceForSectionGroupDepartment (p_section_group_partner_id numeric,
                                                                 p_M_Department_ID          numeric)
;

CREATE FUNCTION getOpenBalanceForSectionGroupDepartment(p_section_group_partner_id numeric,
                                                        p_M_Department_ID          numeric) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(stats.openitems), 0)

FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
         JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id


WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id
  AND dep.m_department_id = p_m_department_ID

GROUP BY sectionPartner.section_group_partner_id, dep.m_department_id

$$
;

COMMENT ON FUNCTION getOpenBalanceForSectionGroupDepartment (numeric, numeric) IS 'TEST: SELECT getOpenBalanceForSectionGroupDepartment(2156017, 1000000);'
;

