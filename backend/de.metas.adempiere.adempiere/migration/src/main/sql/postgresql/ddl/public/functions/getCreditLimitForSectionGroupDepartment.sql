DROP FUNCTION IF EXISTS getCreditLimitForSectionGroupDepartment (p_section_group_partner_id numeric,
                                                                 p_M_Department_ID          numeric,
                                                                 p_Date                     date)
;

CREATE FUNCTION getCreditLimitForSectionGroupDepartment(p_section_group_partner_id numeric,
                                                        p_M_Department_ID          numeric,
                                                        p_Date                     date) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(lim.amount), 0)

FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
         JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id
         JOIN C_BPartner_CreditLimit lim ON sectionPartner.c_bpartner_id = lim.c_bpartner_id


WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id
  AND dep.m_department_id = p_m_department_ID
  AND ((lim.processed = 'Y' and lim.isactive = 'Y') OR (lim.processed = 'N' and lim.isactive = 'N'))
  AND lim.datefrom <= p_date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id 
				   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= p_Date
                   AND ((lim2.processed = 'Y' and lim2.isactive = 'Y') OR (lim2.processed = 'N' and lim2.isactive = 'N'))
				   AND lim2.c_bpartner_creditlimit_id != lim.c_bpartner_creditlimit_id)

GROUP BY sectionPartner.section_group_partner_id, dep.m_department_id

$$
;

COMMENT ON FUNCTION getCreditLimitForSectionGroupDepartment (numeric, numeric, date) IS 'The credit limit always comes in base currency. TEST: SELECT getCreditLimitForSectionGroupDepartment(2156017, 1000000, now()::date);'
;
