-- UI Element: Available for sales -> Available for sales.Maßeinheit
-- Column: MD_Available_For_Sales.C_UOM_ID
-- 2022-06-15T17:56:18.982Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608993
;

-- 2022-06-15T17:56:18.996Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698922
;

-- Field: Available for sales -> Available for sales -> Maßeinheit
-- Column: MD_Available_For_Sales.C_UOM_ID
-- 2022-06-15T17:56:19.005Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=698922
;

-- 2022-06-15T17:56:19.010Z
DELETE FROM AD_Field WHERE AD_Field_ID=698922
;

-- 2022-06-15T17:56:19.069Z
/* DDL */ SELECT public.db_alter_table('MD_Available_For_Sales','ALTER TABLE MD_Available_For_Sales DROP COLUMN IF EXISTS C_UOM_ID')
;

-- Column: MD_Available_For_Sales.C_UOM_ID
-- 2022-06-15T17:56:19.126Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583300
;

-- 2022-06-15T17:56:19.130Z
DELETE FROM AD_Column WHERE AD_Column_ID=583300
;

-- 2022-06-15T17:59:16.194Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541461,'S',TO_TIMESTAMP('2022-06-15 20:59:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.material.cockpit.availableforsales.AvailableForSalesService.debouncer.bufferMaxSize',TO_TIMESTAMP('2022-06-15 20:59:16','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2022-06-15T17:59:56.438Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541462,'S',TO_TIMESTAMP('2022-06-15 20:59:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.material.cockpit.availableforsales.AvailableForSalesService.debouncer.delayInMillis',TO_TIMESTAMP('2022-06-15 20:59:56','YYYY-MM-DD HH24:MI:SS'),100,'5000')
;

-- 2022-06-15T18:02:01.780Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583293,541250,540699,0,TO_TIMESTAMP('2022-06-15 21:02:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2022-06-15 21:02:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:02:12.127Z
CREATE UNIQUE INDEX Product_AttributesKey_UniqueIndex ON MD_Available_For_Sales (M_Product_ID,StorageAttributesKey,AD_Org_ID)
;

-- 2022-06-15T18:02:58.219Z
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540699
;

-- 2022-06-15T18:02:58.230Z
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540699
;

-- 2022-06-15T18:03:24.282Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540701,0,542164,TO_TIMESTAMP('2022-06-15 21:03:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Product_AttributesKey_OrgId_UniqueIndex','N',TO_TIMESTAMP('2022-06-15 21:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:03:24.284Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540701 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-06-15T18:03:34.757Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583301,541251,540701,0,TO_TIMESTAMP('2022-06-15 21:03:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-06-15 21:03:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:03:42.748Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583302,541252,540701,0,TO_TIMESTAMP('2022-06-15 21:03:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2022-06-15 21:03:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:03:55.663Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583293,541253,540701,0,TO_TIMESTAMP('2022-06-15 21:03:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2022-06-15 21:03:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:04:03.452Z
CREATE UNIQUE INDEX Product_AttributesKey_OrgId_UniqueIndex ON MD_Available_For_Sales (M_Product_ID,StorageAttributesKey,AD_Org_ID)
;
