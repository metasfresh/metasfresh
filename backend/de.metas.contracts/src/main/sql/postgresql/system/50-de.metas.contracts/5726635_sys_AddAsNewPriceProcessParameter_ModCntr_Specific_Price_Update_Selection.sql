

-- 2024-06-25T10:16:02.140Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583151,0,'MinValue_Old',TO_TIMESTAMP('2024-06-25 13:16:01.925','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Mindestwert Alt','Mindestwert Alt',TO_TIMESTAMP('2024-06-25 13:16:01.925','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-25T10:16:02.146Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583151 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: MinValue_Old
-- 2024-06-25T10:16:11.331Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-25 13:16:11.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583151 AND AD_Language='de_CH'
;

-- 2024-06-25T10:16:11.334Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583151,'de_CH')
;

-- Element: MinValue_Old
-- 2024-06-25T10:16:12.896Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-25 13:16:12.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583151 AND AD_Language='de_DE'
;

-- 2024-06-25T10:16:12.902Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583151,'de_DE')
;

-- 2024-06-25T10:16:12.905Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583151,'de_DE')
;

-- Element: MinValue_Old
-- 2024-06-25T10:16:38.936Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Min Value (Old)', PrintName='Min Value (Old)',Updated=TO_TIMESTAMP('2024-06-25 13:16:38.936','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583151 AND AD_Language='en_US'
;

-- 2024-06-25T10:16:38.941Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583151,'en_US')
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: MinValue_Old
-- 2024-06-25T14:13:46.855Z
UPDATE AD_Process_Para SET AD_Element_ID=583151, AD_Reference_ID=22, ColumnName='MinValue_Old', Name='Mindestwert Alt',Updated=TO_TIMESTAMP('2024-06-25 17:13:46.855','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542852
;

-- 2024-06-25T14:13:46.860Z
UPDATE AD_Process_Para_Trl trl SET Name='Mindestwert Alt' WHERE AD_Process_Para_ID=542852 AND AD_Language='de_DE'
;

-- 2024-06-25T14:13:46.863Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583151)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: C_Currency_ID
-- 2024-06-25T14:14:29.779Z
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2024-06-25 17:14:29.779','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542848
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: AsNewPrice
-- 2024-06-27T11:51:33.797Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583127,0,585401,542854,20,'AsNewPrice',TO_TIMESTAMP('2024-06-27 14:51:33.563','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,'Y','N','Y','N','Y','N','Neupreis',70,TO_TIMESTAMP('2024-06-27 14:51:33.563','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-27T11:51:33.803Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542854 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-27T11:51:33.810Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583127)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: AsNewPrice
-- 2024-06-27T11:52:36.306Z
UPDATE AD_Process_Para SET DisplayLogic='@ModCntr_Type_ID@=540024 | @ModCntr_Type_ID@=540025',Updated=TO_TIMESTAMP('2024-06-27 14:52:36.306','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542854
;

