DROP FUNCTION IF EXISTS getOpenOrderAmtForSectionGroupDepartment(p_section_group_partner_id numeric,
                                                                 p_M_Department_ID          numeric)
;

CREATE FUNCTION getOpenOrderAmtForSectionGroupDepartment(p_section_group_partner_id numeric,
                                                         p_M_Department_ID          numeric) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT SUM(openView.openOrderAmt)


FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id

         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id
         LEFT JOIN C_BPartner_OpenAmounts_v openView ON sectionPartner.c_bpartner_id = openView.c_bpartner_id
WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id
  AND dep.m_department_id = p_M_Department_ID
GROUP BY dep.m_department_id

$$
;

COMMENT ON FUNCTION getOpenOrderAmtForSectionGroupDepartment (numeric, numeric) IS 'TEST: SELECT  getOpenOrderAmtForSectionGroupDepartment(2156017, 1000000);'
;