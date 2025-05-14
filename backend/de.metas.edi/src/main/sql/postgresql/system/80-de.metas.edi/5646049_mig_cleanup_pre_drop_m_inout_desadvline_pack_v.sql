DROP VIEW IF EXISTS m_inout_desadvline_pack_v
;

SELECT backup_table('ad_printformatitem')
;

SELECT backup_table('ad_printformat')
;

CREATE TEMP TABLE ad_printformat_ids AS
SELECT ad_printformat_id
FROM ad_printformat
WHERE ad_table_id = 540676
;

DELETE
FROM ad_printformatitem
WHERE ad_printformat_id IN (SELECT ad_printformat_id FROM ad_printformat_ids)
;

DELETE
FROM ad_printformat
WHERE ad_printformat_id IN (SELECT ad_printformat_id FROM ad_printformat_ids)
;