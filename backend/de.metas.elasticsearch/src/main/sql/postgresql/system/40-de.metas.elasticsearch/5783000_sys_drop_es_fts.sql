-- =====================================================================
-- Drop Elasticsearch FTS Application Dictionary entries and DB tables.
-- KPI-related entries (WEBUI_KPI, WEBUI_DashboardItem) are NOT touched.
-- =====================================================================

-- 1. Menu entries for FTS windows (4)
DELETE FROM AD_TreeNodeMM WHERE Node_ID IN (
  541111,  -- "Volltextsuche" (InternalName: fullTextSearch)
  541730,  -- "Volltextsuche Konfiguration" (InternalName: esFTSConfig)
  541731,  -- "FTS Modelle zur Index-Warteschlange" (InternalName: esModelsToIndex)
  541732   -- "Volltextsuche Filter" (InternalName: ftsFilter)
);
DELETE FROM AD_Menu WHERE AD_Menu_ID IN (
  541111,  -- "Volltextsuche" (InternalName: fullTextSearch)
  541730,  -- "Volltextsuche Konfiguration" (InternalName: esFTSConfig)
  541731,  -- "FTS Modelle zur Index-Warteschlange" (InternalName: esModelsToIndex)
  541732   -- "Volltextsuche Filter" (InternalName: ftsFilter)
);

-- 2. Process parameters (2)
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID IN (
  542078,  -- ColumnName: ES_DeleteIndex, Name: "Delete elasticsearch index" (Process: ES_FTS_Config_Sync)
  542080   -- ColumnName: TestSearchText, Name: "Test Search Text" (Process: ES_FTS_Filter_Test)
);

-- 3. Processes (2)
DELETE FROM AD_Process WHERE AD_Process_ID IN (
  584886,  -- Value: ES_FTS_Config_Sync, Name: "Sync to Elasticsearch"
  584887   -- Value: ES_FTS_Filter_Test, Name: "Search Test"
);

-- 4. Fields for FTS windows (EntityType = 'de.metas.elasticsearch')
DELETE FROM AD_Field WHERE EntityType = 'de.metas.elasticsearch';

-- 5. BPartner Advanced Search window (ES FTS artifact, entity type D)
DELETE FROM AD_Field WHERE AD_Tab_ID = 543530;  -- Tab of Window "Geschäftspartnersuche"
DELETE FROM AD_Tab WHERE AD_Tab_ID = 543530;     -- Tab: "Geschäftspartnersuche" (Window 541045)
DELETE FROM AD_Window WHERE AD_Window_ID = 541045; -- "Geschäftspartnersuche" (InternalName: advSearchBPartner)

-- 6. FTS Tabs (6) — children of windows 541180, 541181, 541182
DELETE FROM AD_Tab WHERE AD_Tab_ID IN (
  544162,  -- Tab in Window "Volltextsuche Konfiguration" (esFTSConfig)
  544163,  -- Tab in Window "Volltextsuche Konfiguration" (esFTSConfig)
  544164,  -- Tab in Window "FTS Modelle zur Index-Warteschlange" (esModelsToIndex)
  544165,  -- Tab in Window "FTS Modelle zur Index-Warteschlange" (esModelsToIndex)
  544166,  -- Tab in Window "Volltextsuche Filter" (ftsFilter)
  544167   -- Tab in Window "Volltextsuche Filter" (ftsFilter)
);

-- 7. FTS Windows (3)
DELETE FROM AD_Window WHERE AD_Window_ID IN (
  541180,  -- "Volltextsuche Konfiguration" (InternalName: esFTSConfig)
  541181,  -- "FTS Modelle zur Index-Warteschlange" (InternalName: esModelsToIndex)
  541182   -- "Volltextsuche Filter" (InternalName: ftsFilter)
);

-- 8. Columns for FTS tables + BPartner_Adv_Search tables (~79 columns)
DELETE FROM AD_Column WHERE AD_Table_ID IN (
  541755,  -- ES_FTS_Config
  541756,  -- ES_FTS_Config_Field
  541757,  -- ES_FTS_Config_SourceModel
  541759,  -- ES_FTS_Filter
  541760,  -- ES_FTS_Filter_JoinColumn
  541762,  -- ES_FTS_Index_Queue
  541761,  -- C_BPartner_Adv_Search
  541588   -- C_BPartner_Adv_Search_v (view)
);

-- 9. AD_Tables (6 FTS + 2 BPartner Adv Search)
DELETE FROM AD_Table WHERE AD_Table_ID IN (
  541755,  -- TableName: ES_FTS_Config
  541756,  -- TableName: ES_FTS_Config_Field
  541757,  -- TableName: ES_FTS_Config_SourceModel
  541759,  -- TableName: ES_FTS_Filter
  541760,  -- TableName: ES_FTS_Filter_JoinColumn
  541762,  -- TableName: ES_FTS_Index_Queue
  541761,  -- TableName: C_BPartner_Adv_Search
  541588   -- TableName: C_BPartner_Adv_Search_v (view)
);

-- 10. Ref_List entries (2) for ES_FTS_Index_Queue_EventType
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID IN (
  542833,  -- Value: "Update"
  542834   -- Value: "Delete"
);

-- 11. Reference (1)
DELETE FROM AD_Reference WHERE AD_Reference_ID = 541373; -- Name: "ES_FTS_Index_Queue_EventType" (List validation)

-- 12. FTS-specific Elements (6) — only those NOT used by KPI tables
DELETE FROM AD_Element WHERE AD_Element_ID IN (
  579529,  -- ColumnName: ES_FTS_Config_SourceModel_ID
  579530,  -- ColumnName: ES_FTS_Index_Queue_ID
  579532,  -- ColumnName: ES_FTS_Filter_ID
  579533,  -- ColumnName: ES_FTS_Filter_JoinColumn_ID
  579541,  -- ColumnName: ES_DocumentId
  579542   -- ColumnName: ES_FTS_Config_Field_ID
);

-- 13. FTS-specific SysConfig (2)
DELETE FROM AD_SysConfig WHERE Name IN (
  'de.metas.elasticsearch.indexer.AutoIndexModels',
  'de.metas.elasticsearch.indexer.AutoIndexModels.orders'
);
-- NOTE: keeping 'de.metas.elasticsearch.enabled' (elastic_enable) — shared with KPI

-- 14. Drop physical tables and view
DROP VIEW  IF EXISTS C_BPartner_Adv_Search_v CASCADE;
DROP TABLE IF EXISTS C_BPartner_Adv_Search CASCADE;
DROP TABLE IF EXISTS ES_FTS_Config_Field CASCADE;
DROP TABLE IF EXISTS ES_FTS_Config_SourceModel CASCADE;
DROP TABLE IF EXISTS ES_FTS_Filter_JoinColumn CASCADE;
DROP TABLE IF EXISTS ES_FTS_Filter CASCADE;
DROP TABLE IF EXISTS ES_FTS_Config CASCADE;
DROP TABLE IF EXISTS ES_FTS_Index_Queue CASCADE;
DROP TABLE IF EXISTS T_ES_FTS_Search_Result CASCADE;

-- 15. Drop sequences
DROP SEQUENCE IF EXISTS ES_FTS_Config_Field_SEQ;
DROP SEQUENCE IF EXISTS ES_FTS_Config_SourceModel_SEQ;
DROP SEQUENCE IF EXISTS ES_FTS_Config_SEQ;
DROP SEQUENCE IF EXISTS ES_FTS_Filter_JoinColumn_SEQ;
DROP SEQUENCE IF EXISTS ES_FTS_Filter_SEQ;
DROP SEQUENCE IF EXISTS ES_FTS_Index_Queue_SEQ;
