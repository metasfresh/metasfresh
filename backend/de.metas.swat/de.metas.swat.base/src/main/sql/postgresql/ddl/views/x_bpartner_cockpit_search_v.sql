CREATE OR REPLACE VIEW x_bpartner_cockpit_search_v AS 
 SELECT bp.c_bpartner_id, btrim(x_bpartner_search_string(bp.c_bpartner_id)) AS search, btrim(x_bpartner_search_phone(bp.c_bpartner_id)) AS search_phone
   FROM c_bpartner bp
   WHERE bp.AD_client_ID!=99;

CREATE TABLE x_bpartner_cockpit_search_mv
(
  c_bpartner_id numeric(10,0),
  "search" text,
  search_location text,
  search_phone text
)
WITH (
  OIDS=FALSE
);
