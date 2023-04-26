DROP INDEX IF EXISTS ad_window_element_uq
;

CREATE UNIQUE INDEX ad_window_element_uq ON ad_window (ad_element_id)
;

