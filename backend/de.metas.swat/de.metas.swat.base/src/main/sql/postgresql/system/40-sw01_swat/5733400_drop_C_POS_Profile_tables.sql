-- Name: POS Profile
-- Action Type: W
-- Window: POS Profile(540213,de.metas.swat)
-- 2024-09-13T14:59:12.593Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540537
;

-- 2024-09-13T14:59:12.609Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540537
;

-- 2024-09-13T14:59:12.613Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540537 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Table: C_POS_Profile_Warehouse
-- Table: C_POS_Profile_Warehouse
-- 2024-09-13T14:59:46.906Z
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2024-09-13 17:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540578
;

-- Table: C_POS_Profile
-- Table: C_POS_Profile
-- 2024-09-13T14:59:50.752Z
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2024-09-13 17:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540577
;

delete from ad_field where included_tab_id=540584;

-- Window: POS Profile, InternalName=540213 (Todo: Set Internal Name for UI testing)
-- Window: POS Profile, InternalName=540213 (Todo: Set Internal Name for UI testing)
-- 2024-09-13T15:00:19.874Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540213
;

-- POS Profile -> POS Profile -> C_POS_Profile_Included_Tab
update ad_field set included_tab_id=null where ad_field_id=554073;

delete from ad_field where ad_tab_id=540584;
delete from ad_tab where ad_window_id=540213;

-- 2024-09-13T15:00:19.890Z
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540213
;

-- 2024-09-13T15:00:19.895Z
DELETE FROM AD_Window WHERE AD_Window_ID=540213
;

-- Table: C_POS_Profile_Warehouse
-- Table: C_POS_Profile_Warehouse
-- 2024-09-13T15:00:28.862Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540578
;

-- 2024-09-13T15:00:28.866Z
DELETE FROM AD_Table WHERE AD_Table_ID=540578
;

-- Table: C_POS_Profile
-- Table: C_POS_Profile
-- 2024-09-13T15:00:35.060Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540577
;

-- 2024-09-13T15:00:35.064Z
DELETE FROM AD_Table WHERE AD_Table_ID=540577
;

-- 2024-09-13T15:00:51.392Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=542417
;

-- 2024-09-13T15:00:51.408Z
DELETE FROM AD_Element WHERE AD_Element_ID=542417
;

-- 2024-09-13T15:00:54.097Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=542419
;

-- 2024-09-13T15:00:54.101Z
DELETE FROM AD_Element WHERE AD_Element_ID=542419
;

-- 2024-09-13T15:00:56.930Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=542418
;

-- 2024-09-13T15:00:56.934Z
DELETE FROM AD_Element WHERE AD_Element_ID=542418
;















delete from ad_sequence where name in ('C_POS_Profile', 'C_POS_Profile_Warehouse');
drop SEQUENCE if exists c_pos_profile_seq;
drop SEQUENCE if exists c_pos_profile_warehouse_seq;
drop table if exists c_pos_profile_warehouse;
drop table if exists c_pos_profile;

