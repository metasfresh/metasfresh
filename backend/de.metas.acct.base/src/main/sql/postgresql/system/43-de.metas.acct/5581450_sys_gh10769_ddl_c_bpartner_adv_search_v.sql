DROP VIEW IF EXISTS C_BPartner_Adv_Search_v;
CREATE OR REPLACE VIEW C_BPartner_Adv_Search_v AS
SELECT p.c_bpartner_id,
       pl.c_bpartner_location_id,
       pl.ad_org_id,
       pl.ad_client_id,
       p.firstname,
       p.lastname,
       l.city
FROM c_bpartner p,
     c_bpartner_location pl,
     c_location l
WHERE p.c_bpartner_id=pl.c_bpartner_id
AND pl.c_location_id=l.c_location_id
AND p.isactive='Y'
AND pl.isactive='Y'
AND l.isactive='Y';