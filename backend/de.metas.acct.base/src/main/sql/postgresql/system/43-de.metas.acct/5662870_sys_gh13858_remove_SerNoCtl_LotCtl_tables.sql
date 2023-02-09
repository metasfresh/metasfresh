-- remove menu entries
DELETE
FROM ad_menu
WHERE ad_window_id IN (SELECT ad_window_id
                       FROM ad_tab
                       WHERE ad_table_id = get_table_id('M_LotCtlExclude'))
;

DELETE
FROM ad_menu
WHERE ad_window_id IN (SELECT ad_window_id
                       FROM ad_tab
                       WHERE ad_table_id = get_table_id('M_LotCtl'))
;

DELETE
FROM ad_menu
WHERE ad_window_id IN (SELECT ad_window_id
                       FROM ad_tab
                       WHERE ad_table_id = get_table_id('M_Lot'))
;

DELETE
FROM ad_menu
WHERE ad_window_id IN (SELECT ad_window_id
                       FROM ad_tab
                       WHERE ad_table_id = get_table_id('M_SerNoCtlExclude'))
;

DELETE
FROM ad_menu
WHERE ad_window_id IN (SELECT ad_window_id
                       FROM ad_tab
                       WHERE ad_table_id = get_table_id('M_SerNoCtl'))
;

-- Window: Los-Definition, InternalName=258 (Todo: Set Internal Name for UI testing)
-- 2022-11-02T10:36:15.776Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=258
;

-- 2022-11-02T10:36:15.776Z
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=258
;

-- 2022-11-02T10:36:15.780Z
DELETE FROM AD_Window WHERE AD_Window_ID=258
;

-- Table: M_LotCtlExclude
-- 2022-11-02T10:36:22.266Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=810
;

-- 2022-11-02T10:36:22.269Z
DELETE FROM AD_Table WHERE AD_Table_ID=810
;

-- Table: M_LotCtl
-- 2022-11-02T10:36:48.885Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=556
;

-- 2022-11-02T10:36:48.888Z
DELETE FROM AD_Table WHERE AD_Table_ID=556
;

-- Window: Los, InternalName=257 (Todo: Set Internal Name for UI testing)
-- 2022-11-02T10:39:51.076Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=257
;

-- 2022-11-02T10:39:51.077Z
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=257
;

-- 2022-11-02T10:39:51.081Z
DELETE FROM AD_Window WHERE AD_Window_ID=257
;

-- Table: M_Lot
-- 2022-11-02T10:39:59.365Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=557
;

-- 2022-11-02T10:39:59.368Z
DELETE FROM AD_Table WHERE AD_Table_ID=557
;

-- Window: Seriennummern-Definition, InternalName=259 (Todo: Set Internal Name for UI testing)
-- 2022-11-02T10:40:34.253Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=259
;

-- 2022-11-02T10:40:34.254Z
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=259
;

-- 2022-11-02T10:40:34.258Z
DELETE FROM AD_Window WHERE AD_Window_ID=259
;

-- Table: M_SerNoCtlExclude
-- 2022-11-02T10:40:40.471Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=811
;

-- 2022-11-02T10:40:40.474Z
DELETE FROM AD_Table WHERE AD_Table_ID=811
;

-- Table: M_SerNoCtl
-- 2022-11-02T10:40:59.176Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=555
;

-- 2022-11-02T10:40:59.179Z
DELETE FROM AD_Table WHERE AD_Table_ID=555
;

