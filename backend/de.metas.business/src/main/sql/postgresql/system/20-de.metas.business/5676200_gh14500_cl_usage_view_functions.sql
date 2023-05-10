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

-- getDeliveryCreditUsedForDepartment
CREATE OR REPLACE FUNCTION getDeliveryCreditUsedForDepartment(p_m_department_id numeric) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(stats.delivery_creditused), 0)

FROM C_BPartner sectionPartner
         INNER JOIN m_department_sectioncode depSectionCode ON sectionPartner.m_sectioncode_id = depSectionCode.m_sectioncode_id
         INNER JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
WHERE depSectionCode.m_department_id = p_M_Department_ID
GROUP BY depSectionCode.m_department_id

$$
;

COMMENT ON FUNCTION getDeliveryCreditUsedForDepartment(numeric) IS 'TEST: SELECT getDeliveryCreditUsedForDepartment(1000001);'
;

ALTER FUNCTION getDeliveryCreditUsedForDepartment(numeric) OWNER TO metasfresh
;


-- getCreditLimitForDepartment
CREATE OR REPLACE FUNCTION getCreditLimitForDepartment(p_m_department_id numeric, p_date date) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(lim.amount), 0)

FROM C_BPartner partner
         JOIN M_SectionCode sectionCode ON partner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id
         JOIN C_BPartner_CreditLimit lim ON partner.c_bpartner_id = lim.c_bpartner_id


WHERE dep.m_department_id = p_m_department_ID

  -- 2.4.13 AC14 – Increased Credit Limit in Window “Business Partner” for the section partner can be decreased again https://github.com/metasfresh/me03/issues/14325#issuecomment-1406979127
  AND ((lim.processed = 'Y' AND lim.isactive = 'Y') OR (lim.processed = 'N' AND lim.isactive = 'N'))
  AND lim.datefrom <= p_date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id
                   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= p_Date
                   AND ((lim2.processed = 'Y' AND lim2.isactive = 'Y') OR (lim2.processed = 'N' AND lim2.isactive = 'N'))
                   AND lim2.c_bpartner_creditlimit_id != lim.c_bpartner_creditlimit_id)

GROUP BY dep.m_department_id

$$
;

COMMENT ON FUNCTION getCreditLimitForDepartment(numeric, date) IS 'TEST: SELECT getCreditLimitForDepartment(2156017, now()::date);'
;

ALTER FUNCTION getCreditLimitForDepartment(numeric, date) OWNER TO metasfresh
;


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




