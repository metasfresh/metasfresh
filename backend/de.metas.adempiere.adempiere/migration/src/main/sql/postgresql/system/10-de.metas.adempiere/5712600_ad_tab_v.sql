DROP VIEW IF EXISTS ad_tab_v
;

CREATE OR REPLACE VIEW ad_tab_v AS
SELECT
    (select bl.ad_language from ad_language bl where bl.isbaselanguage='Y' order by isactive desc limit 1), -- trl.ad_language
    t.ad_tab_id
     , t.Template_Tab_ID
     , t.ad_window_id
     , t.InternalName
     , t.ad_table_id
     , t.name
     , t.description
     , t.help
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
     , t.commitwarning
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
     , t.IsQueryIfNoFilters
     , t.isgridmodeonly
     , t.ad_message_id
     , t.ischeckparentschanged
     , t.maxqueryrecords
     , t.EntityType
     , t.AllowQuickInput
     , t.QuickInput_OpenButton_Caption
     , t.QuickInput_CloseButton_Caption
     , t.IncludedTabNewRecordInputMode
     , t.IsRefreshViewOnChangeEvents
     , t.IsAutodetectDefaultDateFilter
     , t.QuickInputLayout
     , t.NotFound_Message
     , t.NotFound_MessageDetail
FROM ad_tab t
         JOIN ad_table tbl ON t.ad_table_id = tbl.ad_table_id
WHERE t.isactive = 'Y'::bpchar
  AND tbl.isactive = 'Y'::bpchar
;


DROP VIEW IF EXISTS ad_tab_vt
;

CREATE OR REPLACE VIEW ad_tab_vt AS
SELECT trl.ad_language
     , t.ad_tab_id
     , t.Template_Tab_ID
     , t.ad_window_id
     , t.InternalName
     , t.ad_table_id
     , trl.name
     , t.name                           AS Name_BaseLang
     , trl.description
     , t.description                    AS Description_BaseLang
     , trl.help
     , t.help                           AS Help_BaseLang
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
     , 'N'::bpchar                      AS hasassociation
     , t.istranslationtab
     , t.isreadonly
     , t.ad_image_id
     , t.tablevel
     , t.whereclause
     , t.orderbyclause
     , trl.commitwarning
     , t.CommitWarning                  AS CommitWarning_BaseLang
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
     , t.IsQueryIfNoFilters
     , t.isgridmodeonly
     , t.ad_message_id
     , t.ischeckparentschanged
     , t.maxqueryrecords
     , t.EntityType
     , t.AllowQuickInput
     , trl.QuickInput_OpenButton_Caption
     , t.QuickInput_OpenButton_Caption  AS QuickInput_OpenButton_Caption_BaseLang
     , trl.QuickInput_CloseButton_Caption
     , t.QuickInput_CloseButton_Caption AS QuickInput_CloseButton_Caption_BaseLang
     , t.IncludedTabNewRecordInputMode
     , t.IsRefreshViewOnChangeEvents
     , t.IsAutodetectDefaultDateFilter
     , t.QuickInputLayout
     , trl.NotFound_Message
     , t.NotFound_Message AS NotFound_Message_BaseLang
     , trl.NotFound_MessageDetail
     , t.NotFound_MessageDetail AS NotFound_MessageDetail_BaseLang
FROM ad_tab t
         JOIN ad_table tbl ON t.ad_table_id = tbl.ad_table_id
         JOIN ad_tab_trl trl ON t.ad_tab_id = trl.ad_tab_id
WHERE t.isactive = 'Y'::bpchar
  AND tbl.isactive = 'Y'::bpchar
;

