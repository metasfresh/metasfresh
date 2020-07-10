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


-- Index: x_bpartner_cockpit_search_mv_bpartner

CREATE INDEX x_bpartner_cockpit_search_mv_bpartner
  ON x_bpartner_cockpit_search_mv
  USING btree
  (c_bpartner_id);