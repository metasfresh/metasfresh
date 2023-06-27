-- getCreditLimitForSectionPartnerByDepartment

CREATE OR REPLACE FUNCTION getCreditLimitForSectionPartnerByDepartment(p_section_partner_id numeric, p_m_department_id numeric, p_date date) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(lim.amount), 0)

FROM C_BPartner sectionPartner
         INNER JOIN m_department_sectioncode depSectionCode ON sectionPartner.m_sectioncode_id = depSectionCode.m_sectioncode_id and depSectionCode.isactive='Y'
         INNER JOIN C_BPartner_CreditLimit lim ON sectionPartner.c_bpartner_id = lim.c_bpartner_id

WHERE sectionPartner.c_bpartner_id = p_section_partner_id
  AND depSectionCode.m_department_id = p_m_department_id
  AND ((lim.processed = 'Y' and lim.isactive = 'Y') OR (lim.processed = 'N' and lim.isactive = 'N'))
  AND lim.datefrom <= p_date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id
                   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= p_Date
                   AND ((lim2.processed = 'Y' and lim2.isactive = 'Y') OR (lim2.processed = 'N' and lim2.isactive = 'N'))
                   AND lim2.c_bpartner_creditlimit_id != lim.c_bpartner_creditlimit_id)

GROUP BY depSectionCode.m_department_id

$$
;

COMMENT ON FUNCTION getCreditLimitForSectionPartnerByDepartment(numeric, numeric, date) IS 'TEST: SELECT getCreditLimitForSectionPartnerByDepartment(2156017, 1000001, now()::date);'
;

ALTER FUNCTION getCreditLimitForSectionPartnerByDepartment(numeric, numeric, date) OWNER TO metasfresh
;



