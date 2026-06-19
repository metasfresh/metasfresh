DROP TABLE IF EXISTS t_tables_to_update
;

CREATE TEMPORARY TABLE t_tables_to_update AS
SELECT ad_table_id, tablename, entitytype AS original_entitytype
FROM ad_table
WHERE TRUE
  AND tablename IN ('PP_Order_IssueSchedule')
;

SELECT *
FROM t_tables_to_update
;

UPDATE ad_table
SET entityType='de.metas.handlingunits'
WHERE ad_table_id IN (SELECT ad_table_id FROM t_tables_to_update)
;

UPDATE ad_column
SET entityType='de.metas.handlingunits'
WHERE ad_table_id IN (SELECT ad_table_id FROM t_tables_to_update)
;
