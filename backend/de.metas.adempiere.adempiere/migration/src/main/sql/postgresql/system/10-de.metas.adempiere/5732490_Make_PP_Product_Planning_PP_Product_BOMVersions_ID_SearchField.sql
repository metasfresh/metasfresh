--
-- Script: /tmp/webui_migration_scripts_2024-08-26_8376368427608254311/5732490_migration_2024-09-05_postgresql.sql
-- User: metasfresh
-- OS user: root
--

-- Name: PP_Product_BOMVersions_
-- 2024-09-05T08:59:58.131Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541887,TO_TIMESTAMP('2024-09-05 11:59:57.942','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','PP_Product_BOMVersions_',TO_TIMESTAMP('2024-09-05 11:59:57.942','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-09-05T08:59:58.141Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541887 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Product_BOMVersions_
-- Table: PP_Product_BOMVersions
-- Key: PP_Product_BOMVersions.PP_Product_BOMVersions_ID
-- 2024-09-05T09:00:47.966Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,577840,0,541887,541911,TO_TIMESTAMP('2024-09-05 12:00:47.952','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2024-09-05 12:00:47.952','YYYY-MM-DD HH24:MI:SS.US'),100)
;

UPDATE ad_ref_table SET ad_key = 577840, ad_display = 577841 WHERE ad_reference_id = 541887;

-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- 2024-09-05T08:18:23.451Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-09-05 10:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53397
;

-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- 2024-09-05T08:19:11.258Z
UPDATE AD_Column SET AD_Reference_Value_ID=541887, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-09-05 10:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53397
;
