

DROP FUNCTION IF EXISTS getTotalOrderValueForSectionGroupDepartment (p_section_group_partner_id numeric, p_M_Department_ID numeric)
;

CREATE FUNCTION getTotalOrderValueForSectionGroupDepartment(p_section_group_partner_id numeric, p_M_Department_ID numeric) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(currencyBase(o.grandtotal, o.C_Currency_ID, o.DateOrdered, o.AD_Client_ID, o.AD_Org_ID)), 0)

FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id

         LEFT JOIN C_Order o ON sectionPartner.c_bpartner_id = o.c_bpartner_id


WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id
  AND dep.m_department_id = p_m_department_ID
  AND o.docstatus IN ('CO', 'CL')
  AND o.IsSOTrx = 'Y'

GROUP BY sectionPartner.section_group_partner_id, dep.m_department_id

$$
;

COMMENT ON FUNCTION getTotalOrderValueForSectionGroupDepartment (numeric, numeric) IS 'TEST: SELECT getTotalOrderValueForSectionGroupDepartment(2156017, 1000000);'
;