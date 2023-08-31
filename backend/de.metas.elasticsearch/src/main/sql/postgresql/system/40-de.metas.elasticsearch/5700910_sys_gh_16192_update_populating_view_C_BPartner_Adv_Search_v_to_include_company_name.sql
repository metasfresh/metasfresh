DROP VIEW IF EXISTS C_BPartner_Adv_Search_v
;

CREATE VIEW c_bpartner_adv_search_v(c_bpartner_id, c_bpartner_location_id, c_bp_contact_id, value, externalid, iscompany, name, firstname, lastname, companyname, address1, city, postal, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, es_documentid) AS
SELECT bp.c_bpartner_id,
       bpl.c_bpartner_location_id,
       COALESCE(u.ad_user_id, '-1'::integer::numeric)                                                                                                            AS c_bp_contact_id,
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
       LEAST(bp.created, bpl.created, u.created)                                                                                                                 AS created,
       0::numeric                                                                                                                                                AS createdby,
       GREATEST(bp.updated, bpl.updated, u.updated)                                                                                                              AS updated,
       0::numeric                                                                                                                                                AS updatedby,
       (((bp.c_bpartner_id || '-'::text) || bpl.c_bpartner_location_id) || '-'::text) || COALESCE(u.ad_user_id::character varying, 'X'::character varying)::text AS es_documentid
FROM c_bpartner bp
         JOIN c_bpartner_location bpl ON bp.c_bpartner_id = bpl.c_bpartner_id AND bp.isactive = 'Y'::bpchar AND bpl.isactive = 'Y'::bpchar
         JOIN c_location l ON bpl.c_location_id = l.c_location_id AND l.isactive = 'Y'::bpchar
         JOIN ad_org o ON bp.ad_org_id = o.ad_org_id AND o.isactive = 'Y'::bpchar
         LEFT JOIN ad_user u ON u.c_bpartner_id = bp.c_bpartner_id AND (u.c_bpartner_location_id IS NULL OR u.c_bpartner_location_id = bpl.c_bpartner_location_id) AND u.isactive = 'Y'::bpchar
WHERE bp.isactive = 'Y'::bpchar
;