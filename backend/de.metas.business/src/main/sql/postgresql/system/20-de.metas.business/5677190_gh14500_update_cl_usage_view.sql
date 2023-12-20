DROP VIEW IF EXISTS CreditLimit_Usage_V
;

CREATE OR REPLACE VIEW CreditLimit_Usage_V
AS
SELECT cl.CreditLimit_Usage_V_ID,
       cl.M_Department_ID,
       cl.Section_Group_Partner_ID,
       cl.partner_code,
       cl.M_SectionCode_ID,
       cl.CreditLimit,
       cl.creditlimit_by_department,
       cl.cl_indicator_order,
       cl.cl_indicator_delivery,
       NOW()   AS Created,
       1000000 AS CreatedBy,
       NOW()   AS Updated,
       1000000 AS UpdatedBy,
       'Y'     AS IsActive,
       1000000 AS AD_Client_ID,
       1000000 AS AD_Org_ID
FROM (WITH data AS (SELECT dsc.m_department_id,
                           groupSectionPartner.c_bpartner_id                                                                           AS section_group_partner_id,
                           sectionPartner.m_sectioncode_id,
                           COALESCE(getcreditlimitforpartner(sectionPartner.c_bpartner_id, NOW()::date), 0)                            AS cl_section_code,
                           getCreditLimitForSectionPartnerByDepartment(sectionPartner.c_bpartner_id, dsc.m_department_id, NOW()::date) AS creditlimit_by_department,
                           COALESCE(stats.so_creditused, 0)                                                                            AS so_creditused,
                           COALESCE(stats.delivery_creditused, 0)                                                                      AS delivery_creditused,
                           c.c_currency_id,
                           groupSectionPartner.ad_client_id,
                           groupSectionPartner.ad_org_id

                    FROM c_bpartner groupSectionPartner
                             JOIN c_bpartner sectionPartner ON groupSectionPartner.c_bpartner_id = sectionPartner.section_group_partner_id
                             JOIN m_department_sectioncode dsc ON sectionPartner.m_sectioncode_id = dsc.m_sectioncode_id AND dsc.isactive = 'Y'
                             JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
                             JOIN ad_clientinfo ci ON sectionPartner.ad_client_id = ci.ad_client_id
                             JOIN c_acctschema ac ON ci.c_acctschema1_id = ac.c_acctschema_id
                             JOIN c_currency c ON ac.c_currency_id = c.c_currency_id

                    WHERE groupSectionPartner.issectiongrouppartner = 'Y'),

           data_totals AS (SELECT m_department_id,
                                  section_group_partner_id,
                                  m_sectioncode_id,
                                  c_currency_id,
                                  COALESCE(SUM(data.cl_section_code), 0)           AS total_credit_limit,
                                  COALESCE(SUM(data.creditlimit_by_department), 0) AS total_credit_limit_by_department,
                                  COALESCE(SUM(data.so_creditused), 0)             AS total_so_credit_used,
                                  COALESCE(SUM(data.delivery_creditused), 0)       AS total_delivery_credit_used,
                                  CASE
                                      WHEN COALESCE(SUM(data.cl_section_code), 0) != 0 AND COALESCE(SUM(data.so_creditused), 0) != 0 THEN ROUND((SUM(data.so_creditused) / SUM(data.cl_section_code)) * 100, 3)
                                                                                                                                     ELSE 0
                                  END                                              AS so_credit_used_indicator,
                                  CASE
                                      WHEN COALESCE(SUM(data.cl_section_code), 0) != 0 AND COALESCE(SUM(data.delivery_creditused), 0) != 0 THEN ROUND((SUM(data.delivery_creditused) / SUM(data.cl_section_code)) * 100, 3)
                                                                                                                                           ELSE 0
                                  END                                              AS delivery_credit_used_indicator
                           FROM data
                           GROUP BY
                               GROUPING SETS ( (m_department_id, section_group_partner_id, m_sectioncode_id, c_currency_id),
                                               (m_department_id, section_group_partner_id, c_currency_id),
                                               (m_department_id, c_currency_id)
                               )
                           ORDER BY m_department_id, section_group_partner_id, m_sectioncode_id)

      SELECT 1000000 + ROW_NUMBER() OVER ()                         AS CreditLimit_Usage_V_ID,
             m_department_id,
             section_group_partner_id,
             (SELECT bp.value
              FROM c_bpartner bp
              WHERE bp.c_bpartner_id = dt.section_group_partner_id) AS partner_code,
             m_sectioncode_id,
             CONCAT(total_credit_limit, ' ', c.iso_code)            AS creditlimit,
             total_credit_limit_by_department                       AS creditlimit_by_department,
             CONCAT(so_credit_used_indicator, ' %')                 AS cl_indicator_order,
             CONCAT(delivery_credit_used_indicator, ' %')           AS cl_indicator_delivery
      FROM data_totals dt
               INNER JOIN c_currency c ON dt.c_currency_id = c.c_currency_id
      ORDER BY m_department_id, section_group_partner_id, m_sectioncode_id) cl
;
