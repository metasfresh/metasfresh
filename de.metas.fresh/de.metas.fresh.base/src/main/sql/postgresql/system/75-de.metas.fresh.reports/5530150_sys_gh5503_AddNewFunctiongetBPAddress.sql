-- Function: de_metas_endcustomer_fresh_reports.getBPAddress(numeric)

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getBPAddress( numeric );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getBPAddress(IN bp_id numeric)
  RETURNS TABLE
  (
    addressline1 character varying,
    addressline2 character varying,
    addressline3 character varying
  )
AS
$BODY$
SELECT

  bp.name                                                   as addressline1,
  loc.address1                                              as addressline2,
  COALESCE(loc.postal || ' ', '') || COALESCE(loc.city, '') as addressline3

FROM
  c_bpartner bp
  JOIN c_bpartner_location bpl ON bpl.c_bpartner_id = bp.c_bpartner_id AND bpl.isActive = 'Y'
  JOIN c_location loc ON bpl.c_location_id = loc.c_location_id AND loc.isActive = 'Y'
WHERE
  bp.C_BPartner_ID = bp_id AND bp.isActive = 'Y'
ORDER BY bpl.IsShipTo desc, bpl.IsBillTo desc
LIMIT 1
$BODY$
LANGUAGE sql
STABLE;
;