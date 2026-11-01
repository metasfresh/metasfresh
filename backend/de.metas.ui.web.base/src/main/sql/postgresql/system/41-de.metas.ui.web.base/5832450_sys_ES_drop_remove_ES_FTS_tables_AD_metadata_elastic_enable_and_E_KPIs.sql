-- Elasticsearch removal: DB cleanup. Fully re-runnable, every statement is a no-op when its target is absent
-- (the historical ES migration scripts were deleted, so a freshly built DB never had these objects).
-- Does NOT touch the SQL-converted KPIs 1000006/1000007 or the PG-FTS objects (M_Product_FTS, C_BPartner_FTS).

-- 1) Fail loudly if an 'E' KPI outside the known delete-only set would be left behind (it would crash KPIDatasourceType.ofCode).
DO $$
DECLARE
    v_leftover text;
BEGIN
    SELECT string_agg(WEBUI_KPI_ID::text, ',' ORDER BY WEBUI_KPI_ID) INTO v_leftover
    FROM WEBUI_KPI
    WHERE KPI_DataSource_Type = 'E'
      AND WEBUI_KPI_ID NOT IN (1000000, 1000001, 1000002, 1000004, 1000005, 1000008, 1000009);
    IF v_leftover IS NOT NULL THEN
        RAISE EXCEPTION 'Elasticsearch KPI(s) with WEBUI_KPI_ID % not covered by the ES-drop cleanup; convert them to SQL or add them here', v_leftover;
    END IF;
END
$$;

-- 2) Delete the delete-only 'E' KPIs, children first (explicit ids, guarded by KPI_DataSource_Type='E')
DELETE FROM WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID IN (
    SELECT i.WEBUI_DashboardItem_ID FROM WEBUI_DashboardItem i
    WHERE i.WEBUI_KPI_ID IN (SELECT WEBUI_KPI_ID FROM WEBUI_KPI WHERE KPI_DataSource_Type = 'E' AND WEBUI_KPI_ID IN (1000000, 1000001, 1000002, 1000004, 1000005, 1000008, 1000009)));
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_KPI_ID IN (
    SELECT WEBUI_KPI_ID FROM WEBUI_KPI WHERE KPI_DataSource_Type = 'E' AND WEBUI_KPI_ID IN (1000000, 1000001, 1000002, 1000004, 1000005, 1000008, 1000009));
DELETE FROM WEBUI_KPI_Field_Trl WHERE WEBUI_KPI_Field_ID IN (
    SELECT f.WEBUI_KPI_Field_ID FROM WEBUI_KPI_Field f
    WHERE f.WEBUI_KPI_ID IN (SELECT WEBUI_KPI_ID FROM WEBUI_KPI WHERE KPI_DataSource_Type = 'E' AND WEBUI_KPI_ID IN (1000000, 1000001, 1000002, 1000004, 1000005, 1000008, 1000009)));
DELETE FROM WEBUI_KPI_Field WHERE WEBUI_KPI_ID IN (
    SELECT WEBUI_KPI_ID FROM WEBUI_KPI WHERE KPI_DataSource_Type = 'E' AND WEBUI_KPI_ID IN (1000000, 1000001, 1000002, 1000004, 1000005, 1000008, 1000009));
DELETE FROM WEBUI_KPI WHERE KPI_DataSource_Type = 'E' AND WEBUI_KPI_ID IN (1000000, 1000001, 1000002, 1000004, 1000005, 1000008, 1000009);

-- 3) KPI_DataSource_Type list entry 'E' (Elasticsearch) of reference 541339
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID IN (SELECT AD_Ref_List_ID FROM AD_Ref_List WHERE AD_Reference_ID = 541339 AND Value = 'E');
DELETE FROM AD_Ref_List WHERE AD_Reference_ID = 541339 AND Value = 'E';

-- 4) Elasticsearch sysconfigs
SELECT backup_table('AD_SysConfig', '_ES_drop');
DELETE FROM AD_SysConfig
WHERE Name IN ('elastic_enable',
               'de.metas.elasticsearch.indexer.AutoIndexModels',
               'de.metas.elasticsearch.indexer.AutoIndexModels.orders',
               'de.metas.elasticsearch.PostKpiEvents');

-- 5) Physical tables (their FKs to AD_Table/AD_Column go with them)
DROP TABLE IF EXISTS ES_FTS_Filter_JoinColumn;
DROP TABLE IF EXISTS ES_FTS_Filter;
DROP TABLE IF EXISTS ES_FTS_Config_Field;
DROP TABLE IF EXISTS ES_FTS_Config_SourceModel;
DROP TABLE IF EXISTS ES_FTS_Index_Queue;
DROP TABLE IF EXISTS ES_FTS_Config;
DROP TABLE IF EXISTS T_ES_FTS_Search_Result;

-- 6) Menu entries of the 3 ES_FTS windows (541180 541181 541182)
DELETE FROM AD_TreeNodeMM WHERE Node_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Menu_Trl WHERE AD_Menu_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Menu WHERE AD_Window_ID IN (541180, 541181, 541182);

-- 7) The windows: UI layout, fields, tabs, access, window
DELETE FROM AD_UI_ElementField WHERE AD_UI_Element_ID IN (SELECT e.AD_UI_Element_ID FROM AD_UI_Element e JOIN AD_Tab t ON t.AD_Tab_ID = e.AD_Tab_ID WHERE t.AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN (SELECT f.AD_Field_ID FROM AD_Field f JOIN AD_Tab t ON t.AD_Tab_ID = f.AD_Tab_ID WHERE t.AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_UI_Element WHERE AD_Tab_ID IN (SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_Column_ID IN (SELECT c.AD_UI_Column_ID FROM AD_UI_Column c JOIN AD_UI_Section s ON s.AD_UI_Section_ID = c.AD_UI_Section_ID JOIN AD_Tab t ON t.AD_Tab_ID = s.AD_Tab_ID WHERE t.AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_UI_Column WHERE AD_UI_Section_ID IN (SELECT s.AD_UI_Section_ID FROM AD_UI_Section s JOIN AD_Tab t ON t.AD_Tab_ID = s.AD_Tab_ID WHERE t.AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID IN (SELECT s.AD_UI_Section_ID FROM AD_UI_Section s JOIN AD_Tab t ON t.AD_Tab_ID = s.AD_Tab_ID WHERE t.AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_UI_Section WHERE AD_Tab_ID IN (SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Element_Link WHERE AD_Window_ID IN (541180, 541181, 541182)
   OR AD_Tab_ID IN (SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID IN (541180, 541181, 541182))
   OR AD_Field_ID IN (SELECT f.AD_Field_ID FROM AD_Field f JOIN AD_Tab t ON t.AD_Tab_ID = f.AD_Tab_ID WHERE t.AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (SELECT f.AD_Field_ID FROM AD_Field f JOIN AD_Tab t ON t.AD_Tab_ID = f.AD_Tab_ID WHERE t.AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Field WHERE AD_Tab_ID IN (SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Table_Process WHERE AD_Window_ID IN (541180, 541181, 541182)
   OR AD_Tab_ID IN (SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID IN (SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID IN (541180, 541181, 541182));
DELETE FROM AD_Tab WHERE AD_Window_ID IN (541180, 541181, 541182);
DELETE FROM AD_Window_Access WHERE AD_Window_ID IN (541180, 541181, 541182);
DELETE FROM AD_Window_Trl WHERE AD_Window_ID IN (541180, 541181, 541182);
DELETE FROM AD_Window WHERE AD_Window_ID IN (541180, 541181, 541182);

-- 8) Processes ES_FTS_Config_Sync / ES_FTS_Filter_Test
DELETE FROM AD_Table_Process WHERE AD_Process_ID IN (584886, 584887);
DELETE FROM AD_Process_Access WHERE AD_Process_ID IN (584886, 584887);
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID IN (SELECT AD_Process_Para_ID FROM AD_Process_Para WHERE AD_Process_ID IN (584886, 584887));
DELETE FROM AD_Process_Para WHERE AD_Process_ID IN (584886, 584887);
DELETE FROM AD_Process_Trl WHERE AD_Process_ID IN (584886, 584887);
DELETE FROM AD_Process WHERE AD_Process_ID IN (584886, 584887);

-- 9) The ES_FTS_* tables (+ T_ES_FTS_Search_Result if registered): columns, access, index definitions, sequences, table
DELETE FROM AD_Table_Process WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM AD_Index_Column WHERE AD_Index_Table_ID IN (SELECT AD_Index_Table_ID FROM AD_Index_Table WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result')));
DELETE FROM AD_Index_Table WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM AD_Column_Access WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM AD_Table_Access WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result')));
DELETE FROM AD_Column WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM AD_ChangeLog_Config WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM fix.AD_Table_ChangeLog_Config_26092025 WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM AD_Table_Trl WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result'));
DELETE FROM AD_Table WHERE TableName IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field', 'T_ES_FTS_Search_Result');
DELETE FROM AD_Sequence WHERE Name IN ('ES_FTS_Config', 'ES_FTS_Config_SourceModel', 'ES_FTS_Index_Queue', 'ES_FTS_Filter', 'ES_FTS_Filter_JoinColumn', 'ES_FTS_Config_Field');
