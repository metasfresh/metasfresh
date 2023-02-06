
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
