DROP VIEW IF EXISTS creditlimit_usage_v
;


DROP FUNCTION IF EXISTS get_credit_limit_usage(numeric)
;

CREATE OR REPLACE FUNCTION get_credit_limit_usage(p_section_group_partner_id numeric DEFAULT NULL)
    RETURNS table
            (
                creditlimit_usage_v_id    numeric(10),
                m_department_id           numeric(10),
                section_group_partner_id  numeric(10),
                partner_code              character varying,
                m_sectioncode_id          numeric(10),
                creditlimit               character varying,
                creditlimit_by_department numeric,
                cl_indicator_order        character varying,
                cl_indicator_delivery     character varying,
                created                   timestamp WITH TIME ZONE,
                createdby                 numeric(10),
                updated                   timestamp WITH TIME ZONE,
                updatedby                 numeric(10),
                isactive                  bpchar(1),
                ad_client_id              numeric(10),
                ad_org_id                 numeric(10)
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT cl.creditlimit_usage_v_id,
               cl.m_department_id,
               cl.section_group_partner_id,
               cl.partner_code,
               cl.m_sectioncode_id,
               cl.creditlimit::character varying,
               cl.creditlimit_by_department,
               cl.cl_indicator_order ::character varying,
               cl.cl_indicator_delivery ::character varying,
               NOW()                AS created,
               1000000::numeric(10) AS createdby,
               NOW()                AS updated,
               1000000::numeric(10) AS updatedby,
               'Y'::bpchar          AS isactive,
               cl.ad_client_id      AS ad_client_id,
               cl.ad_org_id         AS ad_org_id
        FROM (WITH data AS (SELECT dsc.m_department_id,
                                   groupsectionpartner.c_bpartner_id                                                                           AS section_group_partner_id,
                                   sectionpartner.c_bpartner_id,
                                   sectionpartner.m_sectioncode_id,
                                   COALESCE(getcreditlimitforpartner(sectionpartner.c_bpartner_id, NOW()::date), 0::numeric)                   AS cl_section_code,
                                   getcreditlimitforsectionpartnerbydepartment(sectionpartner.c_bpartner_id, dsc.m_department_id, NOW()::date) AS creditlimit_by_department,
                                   COALESCE(stats.so_creditused, 0::numeric)                                                                   AS so_creditused,
                                   COALESCE(stats.delivery_creditused, 0::numeric)                                                             AS delivery_creditused,
                                   c_1.c_currency_id,
                                   groupsectionpartner.ad_client_id,
                                   groupsectionpartner.ad_org_id
                            FROM c_bpartner groupsectionpartner
                                     JOIN c_bpartner sectionpartner ON groupsectionpartner.c_bpartner_id = sectionpartner.section_group_partner_id
                                     JOIN m_department_sectioncode dsc ON sectionpartner.m_sectioncode_id = dsc.m_sectioncode_id AND dsc.isactive = 'Y'::bpchar
                                     JOIN c_bpartner_stats stats ON sectionpartner.c_bpartner_id = stats.c_bpartner_id
                                     JOIN ad_clientinfo ci ON sectionpartner.ad_client_id = ci.ad_client_id
                                     JOIN c_acctschema ac ON ci.c_acctschema1_id = ac.c_acctschema_id
                                     JOIN c_currency c_1 ON ac.c_currency_id = c_1.c_currency_id
                            WHERE TRUE
                              AND groupsectionpartner.issectiongrouppartner = 'Y'::bpchar
                              AND (groupsectionpartner.c_bpartner_id = p_section_group_partner_id OR p_section_group_partner_id IS NULL)),

                   data_totals AS (SELECT data.ad_client_id,
                                          data.ad_org_id,
                                          data.m_department_id,
                                          data.section_group_partner_id,
                                          data.m_sectioncode_id,
                                          data.c_currency_id,
                                          COALESCE(SUM(data.cl_section_code), 0::numeric)           AS total_credit_limit,
                                          COALESCE(SUM(data.creditlimit_by_department), 0::numeric) AS total_credit_limit_by_department,
                                          COALESCE(SUM(data.so_creditused), 0::numeric)             AS total_so_credit_used,
                                          COALESCE(SUM(data.delivery_creditused), 0::numeric)       AS total_delivery_credit_used,
                                          CASE
                                              WHEN COALESCE(SUM(data.cl_section_code), 0::numeric) <> 0::numeric AND COALESCE(SUM(data.so_creditused), 0::numeric) <> 0::numeric THEN ROUND(SUM(data.so_creditused) / SUM(data.cl_section_code) * 100::numeric, 3)
                                                                                                                                                                                 ELSE 0::numeric
                                          END                                                       AS so_credit_used_indicator,
                                          CASE
                                              WHEN COALESCE(SUM(data.cl_section_code), 0::numeric) <> 0::numeric AND COALESCE(SUM(data.delivery_creditused), 0::numeric) <> 0::numeric THEN ROUND(SUM(data.delivery_creditused) / SUM(data.cl_section_code) * 100::numeric, 3)
                                                                                                                                                                                       ELSE 0::numeric
                                          END                                                       AS delivery_credit_used_indicator
                                   FROM data

                                   GROUP BY GROUPING SETS ((data.ad_client_id, data.ad_org_id, data.m_department_id, data.section_group_partner_id, data.m_sectioncode_id, data.c_currency_id)
                                          , (data.ad_client_id, data.ad_org_id, data.m_department_id, data.section_group_partner_id, data.c_currency_id)
                                          , (data.ad_client_id, data.ad_org_id, data.m_department_id, data.c_currency_id))

                                   ORDER BY data.m_department_id, data.section_group_partner_id, data.m_sectioncode_id)

              SELECT (1000000 + ROW_NUMBER() OVER ())::numeric(10)          AS creditlimit_usage_v_id,
                     dt.ad_client_id,
                     dt.ad_org_id,
                     dt.m_department_id,
                     dt.section_group_partner_id,
                     (SELECT bp.value
                      FROM c_bpartner bp
                      WHERE bp.c_bpartner_id = dt.section_group_partner_id) AS partner_code,
                     dt.m_sectioncode_id,
                     CONCAT(dt.total_credit_limit, ' ', c.iso_code)         AS creditlimit,
                     dt.total_credit_limit_by_department                    AS creditlimit_by_department,
                     CONCAT(dt.so_credit_used_indicator, ' %')              AS cl_indicator_order,
                     CONCAT(dt.delivery_credit_used_indicator, ' %')        AS cl_indicator_delivery
              FROM data_totals dt
                       JOIN c_currency c ON dt.c_currency_id = c.c_currency_id
              ORDER BY dt.m_department_id, dt.section_group_partner_id, dt.m_sectioncode_id) cl;
END;
$$
;



CREATE VIEW creditlimit_usage_v AS
SELECT *
FROM get_credit_limit_usage(NULL)
;

ALTER VIEW creditlimit_usage_v
    OWNER TO metasfresh
;
