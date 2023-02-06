DROP FUNCTION IF EXISTS getcreditlimitfordepartment(p_m_department_id numeric,
                                                    p_date            date)
;

CREATE OR REPLACE FUNCTION getcreditlimitfordepartment(p_m_department_id numeric,
                                                       p_date            date) RETURNS numeric
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

COMMENT ON FUNCTION getcreditlimitfordepartment(numeric, date) IS 'TEST: SELECT getcreditlimitfordepartment(2156017, now()::date);'
;

ALTER FUNCTION getcreditlimitfordepartment(numeric, date) OWNER TO metasfresh
;
