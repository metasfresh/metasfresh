DROP FUNCTION IF EXISTS getSOCreditUsedForSectionGroupPartnerDepartment(p_section_group_partner_id numeric,
                                                                        p_M_Department_ID          numeric)
;


CREATE FUNCTION getSOCreditUsedForSectionGroupPartnerDepartment(p_section_group_partner_id numeric,
                                                                p_M_Department_ID          numeric) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT SUM(stats.so_creditused)

FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
         JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id

         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id
WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id
  AND dep.m_department_id = p_M_Department_ID
GROUP BY sectionPartner.section_group_partner_id, dep.m_department_id

$$
;

COMMENT ON FUNCTION getSOCreditUsedForSectionGroupPartnerDepartment (numeric, numeric) IS 'TEST: SELECT  getSOCreditUsedForSectionGroupPartnerDepartment(2156017, 1000000);'
;