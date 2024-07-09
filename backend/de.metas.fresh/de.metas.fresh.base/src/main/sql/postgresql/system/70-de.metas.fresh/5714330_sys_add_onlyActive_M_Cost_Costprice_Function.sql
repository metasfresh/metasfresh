-- 2023-12-21T09:47:01.788Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582875,0,'onlyActiveProducts',TO_TIMESTAMP('2023-12-21 11:47:01.547','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Nur aktive Produkte','Nur aktive Produkte',TO_TIMESTAMP('2023-12-21 11:47:01.547','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-21T09:47:01.795Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582875 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-12-21T09:51:39.994Z
UPDATE AD_Element_Trl SET Name='Only active products', PrintName='Only active products',Updated=TO_TIMESTAMP('2023-12-21 11:51:39.992','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582875 AND AD_Language='en_US'
;

-- 2023-12-21T09:51:40.028Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582875,'en_US') 
;

-- 2023-12-21T10:06:23.325Z
UPDATE AD_Element SET ColumnName='Parameter_onlyActiveProducts',Updated=TO_TIMESTAMP('2023-12-21 12:06:23.325','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582875
;

-- 2023-12-21T10:06:23.347Z
UPDATE AD_Column SET ColumnName='Parameter_onlyActiveProducts', Name='Nur aktive Produkte', Description=NULL, Help=NULL WHERE AD_Element_ID=582875
;

-- 2023-12-21T10:06:23.348Z
UPDATE AD_Process_Para SET ColumnName='Parameter_onlyActiveProducts', Name='Nur aktive Produkte', Description=NULL, Help=NULL, AD_Element_ID=582875 WHERE UPPER(ColumnName)='PARAMETER_ONLYACTIVEPRODUCTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-12-21T10:06:23.354Z
UPDATE AD_Process_Para SET ColumnName='Parameter_onlyActiveProducts', Name='Nur aktive Produkte', Description=NULL, Help=NULL WHERE AD_Element_ID=582875 AND IsCentrallyMaintained='Y'
;

-- 2023-12-21T10:06:59.766Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582875,0,584744,542763,20,'Parameter_onlyActiveProducts',TO_TIMESTAMP('2023-12-21 12:06:59.617','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','U',0,'Y','N','Y','N','N','N','Nur aktive Produkte',50,TO_TIMESTAMP('2023-12-21 12:06:59.617','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-21T10:06:59.767Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542763 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-12-21T10:08:02.035Z
UPDATE AD_Process SET SQLStatement='select * from report.M_Cost_CostPrice_Function(''@keydate@''::date, @Parameter_M_Product_ID/-1@, @Parameter_M_Warehouse_ID/-1@, ''Y'', ''@Parameter_onlyActiveProducts/N@'', ''@#AD_Language@'', @AD_Client_ID@, @AD_Org_ID@ )',Updated=TO_TIMESTAMP('2023-12-21 12:08:02.034','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2023-12-21T10:08:13.976Z
UPDATE AD_Process_Para SET DisplayLogic='1=0',Updated=TO_TIMESTAMP('2023-12-21 12:08:13.974','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542763
;

-- 2023-12-21T10:08:28.315Z
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2023-12-21 12:08:28.313','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542763
;

