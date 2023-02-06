
DROP FUNCTION IF EXISTS getcreditlimitforpartner(p_c_bpartner_id numeric,
                                                 p_date          date)
;

CREATE OR REPLACE FUNCTION getcreditlimitforpartner(p_c_bpartner_id numeric,
                                                    p_date          date) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(lim.amount), 0)

FROM C_BPartner partner
         JOIN C_BPartner_CreditLimit lim ON partner.c_bpartner_id = lim.c_bpartner_id


WHERE partner.c_bpartner_id = p_c_bpartner_id
  AND ((lim.processed = 'Y' AND lim.isactive = 'Y') OR (lim.processed = 'N' AND lim.isactive = 'N'))
  AND lim.datefrom <= p_date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id
                   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= p_Date
                   AND ((lim2.processed = 'Y' AND lim2.isactive = 'Y') OR (lim2.processed = 'N' AND lim2.isactive = 'N'))
                   AND lim2.c_bpartner_creditlimit_id != lim.c_bpartner_creditlimit_id)

GROUP BY partner.c_bpartner_id

$$
;

COMMENT ON FUNCTION getcreditlimitforpartner(numeric, date) IS 'TEST: SELECT getcreditlimitforpartner(2156017, now()::date);'
;

ALTER FUNCTION getcreditlimitforpartner(numeric, date) OWNER TO metasfresh
;





CREATE FUNCTION getcreditlimitfordepartment(p_m_department_id numeric,
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

COMMENT ON FUNCTION getcreditlimitfordepartment(numeric,date) IS 'TEST: SELECT getcreditlimitfordepartment(2156017, now()::date);'
;

ALTER FUNCTION getcreditlimitfordepartment(numeric, date) OWNER TO metasfresh
;


DROP VIEW IF EXISTS c_bpartner_creditlimit_usage_v
;

CREATE VIEW C_BPartner_Creditlimit_Usage_V
            (
             C_BPartner_Creditlimit_Usage_V_ID,
             c_bpartner_id,
             partner_code,
             m_department_id,
             m_sectioncode_id,
             creditlimit,
             creditlimit_by_department,
             creditlimit_by_sectioncode,
             cl_indicator_order,
             cl_indicator_delivery,
             created,
             updated,
             createdby,
             updatedby,
             isactive,
             ad_client_id,
             ad_org_id
                )
AS
SELECT bpcl.c_bpartner_creditlimit_id                                                                              AS C_BPartner_Creditlimit_Usage_V_ID,
       bp.c_bpartner_id,
       bp.value                                                                                                    AS partner_code,
       d.m_department_id,
       sc.m_sectioncode_id,
       getcreditlimitforpartner(bp.c_bpartner_id, NOW()::date)                                 AS creditlimit,
       getcreditlimitfordepartment(d.m_department_id, NOW()::date)                          AS creditlimit_by_department,
       getcreditlimitforsectioncode(bp.m_sectioncode_id, NOW()::date)                        AS creditlimit_by_sectioncode,
       (SELECT MAX(creditlimitindicator) FROM C_BPartner_Stats s WHERE s.C_BPartner_ID = bp.c_bpartner_id)         AS cl_indicator_order,
       (SELECT MAX(deliverycreditlimitindicator) FROM C_BPartner_Stats s WHERE s.C_BPartner_ID = bp.c_bpartner_id) AS cl_indicator_delivery,
       bpcl.created                                                                                                AS created,
       bpcl.updated                                                                                                AS updated,
       bpcl.createdby                                                                                              AS createdby,
       bpcl.updatedby                                                                                              AS updatedby,
       'Y'                                                                                                         AS isactive,
       bp.ad_client_id,
       bp.ad_org_id
FROM c_bpartner bp
         JOIN m_sectioncode sc ON bp.m_sectioncode_id = sc.m_sectioncode_id
         JOIN m_department_sectioncode dsc ON bp.m_sectioncode_id = dsc.m_sectioncode_id
         JOIN m_department d ON dsc.m_department_id = d.m_department_id
         JOIN c_bpartner_creditlimit bpcl ON bp.c_bpartner_id = bpcl.c_bpartner_id

ORDER BY d.m_department_id, bp.c_bpartner_id
;

ALTER TABLE C_BPartner_Creditlimit_Usage_V
    OWNER TO metasfresh
;
