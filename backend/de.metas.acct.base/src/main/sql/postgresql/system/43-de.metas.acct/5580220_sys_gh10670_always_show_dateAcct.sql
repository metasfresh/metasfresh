create table backup.ad_field_bkp20210223 as select * from ad_column;

drop table if exists tmp_ad_fields_to_update;
create temporary table tmp_ad_fields_to_update as
SELECT t.tablename, c.columnname, f.displaylogic, f.isreadonly, tt.ad_window_id, tt.ad_tab_id, f.ad_field_id
FROM AD_Column c
         INNER JOIN ad_table t ON t.ad_table_id = c.ad_table_id
         INNER JOIN ad_field f ON f.ad_column_id = c.ad_column_id
         INNER JOIN ad_tab tt ON tt.ad_tab_id = f.ad_tab_id
WHERE c.columnname IN ('Posted', 'DateAcct')
  AND t.isview = 'N'
ORDER BY t.tablename, tt.ad_window_id, c.columnname;

update ad_field f set
                      DisplayLogic='@Processed/N@=''Y''',
                      IsReadonly='Y',
                      UpdatedBy=0,
                      Updated=TO_TIMESTAMP('2021-02-23 14:00:00','YYYY-MM-DD HH24:MI:SS')
from tmp_ad_fields_to_update t
where t.ad_field_id=f.ad_field_id and t.columnname='Posted';

update ad_field f set
                      DisplayLogic=null,
                      IsReadonly='N',
                      UpdatedBy=0,
                      Updated=TO_TIMESTAMP('2021-02-23 14:00:00','YYYY-MM-DD HH24:MI:SS')
from tmp_ad_fields_to_update t
where t.ad_field_id=f.ad_field_id and t.columnname='DateAcct';

-- 2021-02-23T13:33:48.458Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=561878
;

