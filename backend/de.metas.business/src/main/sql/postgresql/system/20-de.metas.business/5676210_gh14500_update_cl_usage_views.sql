DROP VIEW IF EXISTS C_BPartner_Creditlimit_Usage_V
;

CREATE OR REPLACE VIEW C_BPartner_Creditlimit_Usage_V
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
(SELECT 1000000 + ROW_NUMBER() OVER ()                                                                AS c_bpartner_creditlimit_usage_v_id,
        bp.c_bpartner_id,
        bp.value                                                                                      AS partner_code,
        d.m_department_id,
        sc.m_sectioncode_id,
        getcreditlimitforpartner(bp.c_bpartner_id, NOW()::date)                   AS creditlimit,
        getCreditLimitForSectionPartnerByDepartment(bp.c_bpartner_id, d.m_department_id, NOW()::date) AS creditlimit_by_department,
        0                                                                                             AS creditlimit_by_sectioncode,
        (SELECT MAX(s.creditlimitindicator::text) AS max
         FROM c_bpartner_stats s
         WHERE s.c_bpartner_id = bp.c_bpartner_id)                                                    AS cl_indicator_order,
        (SELECT MAX(s.deliverycreditlimitindicator::text) AS max
         FROM c_bpartner_stats s
         WHERE s.c_bpartner_id = bp.c_bpartner_id)                                                    AS cl_indicator_delivery,
        bpcl.created,
        bpcl.updated,
        bpcl.createdby,
        bpcl.updatedby,
        'Y'                                                                                           AS isactive,
        bp.ad_client_id,
        bp.ad_org_id
 FROM c_bpartner bp
          LEFT JOIN m_sectioncode sc ON bp.m_sectioncode_id = sc.m_sectioncode_id
          LEFT JOIN m_department_sectioncode dsc ON bp.m_sectioncode_id = dsc.m_sectioncode_id
          INNER JOIN m_department d ON dsc.m_department_id = d.m_department_id
          INNER JOIN c_bpartner_creditlimit bpcl ON bp.c_bpartner_id = bpcl.c_bpartner_id)

UNION ALL

(SELECT 2000000 + ROW_NUMBER() OVER ()                                                                                                        AS c_bpartner_creditlimit_usage_v_id,
        NULL                                                                                                                                  AS c_bpartner_id,
        NULL                                                                                                                                  AS partner_code,
        d.m_department_id,
        NULL                                                                                                                                  AS m_sectioncode_id,
        NULL                                                                                                                                  AS creditlimit,
        getCreditLimitForDepartment(d.m_department_id, now()::date)                                                                        AS creditlimit_by_department,
        0                                                                                                                                  AS creditlimit_by_sectioncode,
        ROUND((getsocreditusedfordepartment(d.m_department_id) / getcreditlimitfordepartment(d.m_department_id, NOW()::date)) * 100, 1) || '%'       AS cl_indicator_order,
        ROUND((getdeliverycreditusedfordepartment(d.m_department_id) / getcreditlimitfordepartment(d.m_department_id, NOW()::date)) * 100, 1)  || '%' AS cl_indicator_delivery,
        d.created,
        d.updated,
        d.createdby,
        d.updatedby,
        'Y'                                                                                                                                   AS isactive,
        d.ad_client_id,
        d.ad_org_id
 FROM m_department d)

ORDER BY m_department_id
;
