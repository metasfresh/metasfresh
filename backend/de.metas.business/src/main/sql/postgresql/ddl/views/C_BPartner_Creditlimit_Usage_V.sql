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
SELECT *
FROM (SELECT 1000000 + ROW_NUMBER() OVER ()                                                                              AS C_BPartner_Creditlimit_Usage_V_ID,
             bp.c_bpartner_id,
             bp.value                                                                                                    AS partner_code,
             dsc.m_department_id,
             bp.m_sectioncode_id,
             concat(getcreditlimitforpartner(bp.c_bpartner_id, NOW()::date), ' ', c.iso_code::text ) AS creditlimit,
             getCreditLimitForSectionPartnerByDepartment(bp.c_bpartner_id, dsc.m_department_id, NOW()::date)             AS creditlimit_by_department,
             (SELECT MAX(creditlimitindicator) FROM C_BPartner_Stats s WHERE s.C_BPartner_ID = bp.c_bpartner_id)         AS cl_indicator_order,
             (SELECT MAX(deliverycreditlimitindicator) FROM C_BPartner_Stats s WHERE s.C_BPartner_ID = bp.c_bpartner_id) AS cl_indicator_delivery,
             dsc.created                                                                                                 AS created,
             dsc.updated                                                                                                 AS updated,
             dsc.createdby                                                                                               AS createdby,
             dsc.updatedby                                                                                               AS updatedby,
             'Y'                                                                                                         AS isactive,
             dsc.ad_client_id,
             dsc.ad_org_id
      FROM c_bpartner bp
               INNER JOIN m_department_sectioncode dsc ON bp.m_sectioncode_id = dsc.m_sectioncode_id
               INNER JOIN ad_clientinfo ci ON bp.ad_client_id = ci.ad_client_id
               INNER JOIN c_acctschema ac ON ci.c_acctschema1_id = ac.c_acctschema_id
               INNER JOIN c_currency c on ac.c_currency_id = c.c_currency_id

      UNION ALL

      (
          (SELECT 2000000 + ROW_NUMBER() OVER ()                                                                                                               AS c_bpartner_creditlimit_usage_v_id,
                  NULL                                                                                                                                         AS c_bpartner_id,
                  NULL                                                                                                                                         AS partner_code,
                  d.m_department_id,
                  NULL                                                                                                                                         AS m_sectioncode_id,
                  NULL                                                                                                                                         AS creditlimit,
                  coalesce(getCreditLimitForDepartment(d.m_department_id, NOW()::date),0)                                                                                  AS creditlimit_by_department,
                  coalesce(ROUND((getSOCreditUsedForDepartment(d.m_department_id) / getcreditlimitfordepartment(d.m_department_id, NOW()::date)) * 100, 1),0 ) || '%'       AS cl_indicator_order,
                  coalesce(ROUND((getDeliveryCreditUsedForDepartment(d.m_department_id) / getcreditlimitfordepartment(d.m_department_id, NOW()::date)) * 100, 1),0 ) || '%' AS cl_indicator_delivery,
                  d.created,
                  d.updated,
                  d.createdby,
                  d.updatedby,
                  'Y'                                                                                                                                          AS isactive,
                  d.ad_client_id,
                  d.ad_org_id
           FROM m_department d)
      )
     ) cl
ORDER BY cl.m_department_id, cl.C_BPartner_Creditlimit_Usage_V_ID, cl.c_bpartner_id
;


ALTER TABLE C_BPartner_Creditlimit_Usage_V
    OWNER TO metasfresh
;
