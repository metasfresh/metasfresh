DROP VIEW IF EXISTS ad_tab_vt;
CREATE OR REPLACE VIEW ad_tab_vt AS 
SELECT
trl.ad_language
, t.ad_tab_id
, t.ad_window_id
, t.ad_table_id
, trl.name
, t.name as Name_BaseLang
, trl.description
, t.description as Description_BaseLang
, trl.help
, t.help as Help_BaseLang
, t.seqno
, t.issinglerow
, t.hastree
, t.isinfotab
, tbl.replicationtype
, tbl.tablename
, tbl.accesslevel
, tbl.issecurityenabled
, tbl.isdeleteable
, tbl.ishighvolume
, tbl.isview
, 'N'::bpchar AS hasassociation
, t.istranslationtab
, t.isreadonly
, t.ad_image_id
, t.tablevel
, t.whereclause
, t.orderbyclause
, trl.commitwarning
, t.CommitWarning as CommitWarning_BaseLang
, t.readonlylogic
, t.displaylogic
, t.ad_column_id
, t.ad_process_id
, t.issorttab
, t.isinsertrecord
, t.isadvancedtab
, t.ad_columnsortorder_id
, t.ad_columnsortyesno_id
, t.included_tab_id
, t.parent_column_id
, t.isrefreshallonactivate
, t.issearchactive
, t.defaultwhereclause
, t.issearchcollapsed
, t.isqueryonload
, t.isgridmodeonly
, t.ad_message_id
, t.ischeckparentschanged
, t.maxqueryrecords
, t.EntityType
FROM ad_tab t
JOIN ad_table tbl ON t.ad_table_id = tbl.ad_table_id
JOIN ad_tab_trl trl ON t.ad_tab_id = trl.ad_tab_id
WHERE t.isactive = 'Y'::bpchar AND tbl.isactive = 'Y'::bpchar;
