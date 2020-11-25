DROP INDEX IF EXISTS ad_treebar_uq;
CREATE UNIQUE INDEX ad_treebar_uq ON ad_treebar (ad_user_id, node_id);

