DROP VIEW IF EXISTS C_BPartner_Adv_Search_v
;

CREATE OR REPLACE VIEW C_BPartner_Adv_Search_v AS
SELECT p.c_bpartner_id,
       pl.c_bpartner_location_id,
       COALESCE(u.ad_user_id, -1)         AS C_BP_Contact_ID, --coalesce with -1 because mf parents cannot be null
       p.value,
       p.iscompany,
       p.name,
       COALESCE(u.firstname, p.firstname) AS firstname,
       COALESCE(u.lastname, p.lastname)   AS lastname,
       l.city,
       p.ad_client_id,
       p.ad_org_id
FROM c_bpartner p
         INNER JOIN c_bpartner_location pl ON p.c_bpartner_id = pl.c_bpartner_id AND p.isactive = 'Y' AND pl.isactive = 'Y'
         INNER JOIN c_location l ON pl.c_location_id = l.c_location_id AND l.isactive = 'Y'
         INNER JOIN ad_org o ON p.ad_org_id = o.ad_org_id AND o.isactive = 'Y'

         LEFT JOIN ad_user u ON (u.c_bpartner_id = p.c_bpartner_id
    AND (u.c_bpartner_location_id IS NULL OR u.c_bpartner_location_id = pl.c_bpartner_location_id)
    AND u.isactive = 'Y')
;
