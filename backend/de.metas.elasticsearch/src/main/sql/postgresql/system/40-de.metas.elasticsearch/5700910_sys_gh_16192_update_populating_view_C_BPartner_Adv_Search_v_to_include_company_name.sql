DROP VIEW IF EXISTS C_BPartner_Adv_Search_v
;

CREATE OR REPLACE VIEW C_BPartner_Adv_Search_v AS
SELECT bp.c_bpartner_id,
       bpl.c_bpartner_location_id,
       COALESCE(u.ad_user_id, -1)                                                                                                            AS C_BP_Contact_ID,
       bp.value,
       (SELECT ExternalReference
        FROM S_ExternalReference
        WHERE Type = 'BPartner'
          AND ExternalSystem = 'Other'
          AND referenced_ad_table_id = 291
          AND record_id = bp.C_BPartner_ID)                                                                                                                      AS externalid,
       bp.iscompany,
       bp.name,
       COALESCE(u.firstname, bp.firstname)                                                                                                                       AS firstname,
       COALESCE(u.lastname, bp.lastname)                                                                                                                         AS lastname,
       bp.companyname																																			 AS	companyname,
       l.address1,
       l.city,
       l.postal,
       bp.ad_client_id,
       bp.ad_org_id,
       bp.isactive,
       LEAST(bp.created, bpl.created, u.created)                                                            AS created,
       0::numeric                                                                                           AS createdby,
       GREATEST(bp.updated, bpl.updated, u.updated)                                                         AS updated,
       0::numeric                                                                                           AS updatedby,
       --
       bp.c_bpartner_id || '-' || bpl.c_bpartner_location_id || '-' || COALESCE(u.ad_user_id::varchar, 'X') AS es_documentid
FROM c_bpartner bp
         INNER JOIN c_bpartner_location bpl ON bp.c_bpartner_id = bpl.c_bpartner_id AND bp.isactive = 'Y' AND bpl.isactive = 'Y'
         INNER JOIN c_location l ON bpl.c_location_id = l.c_location_id AND l.isactive = 'Y'
         INNER JOIN ad_org o ON bp.ad_org_id = o.ad_org_id AND o.isactive = 'Y'
    --
         LEFT JOIN ad_user u ON (
            u.c_bpartner_id = bp.c_bpartner_id
        AND (u.c_bpartner_location_id IS NULL OR u.c_bpartner_location_id = bpl.c_bpartner_location_id)
        AND u.isactive = 'Y')
     --
WHERE bp.isactive = 'Y'
;