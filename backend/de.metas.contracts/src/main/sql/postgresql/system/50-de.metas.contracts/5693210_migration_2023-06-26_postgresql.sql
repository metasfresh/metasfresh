
update ad_table set entitytype='de.metas.contracts' where ad_table_id=542338; -- ModCntr_Log
update ad_column set entitytype='de.metas.contracts' where ad_table_id=542338; -- ModCntr_Log

update ad_table set entitytype='de.metas.contracts' where ad_table_id=542337; -- ModCntr_Type
update ad_column set entitytype='de.metas.contracts' where ad_table_id=542337; -- ModCntr_Type


update ad_window set entitytype='de.metas.contracts' where ad_window_id=541710; -- Contract Module Type
update ad_tab set entitytype='de.metas.contracts' where ad_window_id=541710; -- Contract Module Type
update ad_Field set entitytype='de.metas.contracts' where ad_tab_id=(select ad_Tab_id from ad_tab where ad_window_id=541710); -- Contract Module Type

update ad_window set entitytype='de.metas.contracts' where ad_window_id=541711; -- Contract Module Log
update ad_tab set entitytype='de.metas.contracts' where ad_window_id=541711; -- Contract Module Log
update ad_Field set entitytype='de.metas.contracts' where ad_tab_id=(select ad_Tab_id from ad_tab where ad_window_id=541711); -- Contract Module Log



-- Table: MoCntr_Log
-- 2023-06-26T05:51:57.119481200Z
UPDATE AD_Table SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2023-06-26 07:51:57.046','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542338
;

-- Window: Vertragsbaustein Log, InternalName=ModCntr_Log
-- 2023-06-26T05:58:11.443307300Z
UPDATE AD_Window SET InternalName='ModCntr_Log',Updated=TO_TIMESTAMP('2023-06-26 07:58:11.364','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541711
;

-- Window: Vertragsbaustein Typ, InternalName=ModCntr_Type
-- 2023-06-26T05:58:41.526229800Z
UPDATE AD_Window SET InternalName='ModCntr_Type',Updated=TO_TIMESTAMP('2023-06-26 07:58:41.453','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541710
;

