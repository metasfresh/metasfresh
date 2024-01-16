UPDATE c_bpartner_location bpl
SET ad_org_id=bp.ad_org_id, updated='2021-09-29 06:33', updatedby=99
FROM c_bpartner bp
WHERE bp.c_bpartner_id = bpl.c_bpartner_id
  AND bpl.ad_org_id != bp.ad_org_id
;

UPDATE c_location l
SET ad_org_id=bpl.ad_org_id, updated='2021-09-29 06:34', updatedby=99
FROM c_bpartner_location bpl
WHERE bpl.c_location_id = l.c_location_id
  AND bpl.ad_org_id != l.ad_org_id
;

