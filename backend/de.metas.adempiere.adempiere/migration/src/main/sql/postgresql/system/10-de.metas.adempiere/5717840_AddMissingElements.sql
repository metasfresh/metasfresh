-- 2024-02-23T08:10:34.591888600Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582977,0,'CounterAccount',TO_TIMESTAMP('2024-02-23 10:10:34.367','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Gegenkonto','Gegenkonto',TO_TIMESTAMP('2024-02-23 10:10:34.367','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-23T08:10:34.991142100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582977 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CounterAccount
-- 2024-02-23T08:10:38.614563200Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-23 10:10:38.614','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582977 AND AD_Language='de_CH'
;

-- 2024-02-23T08:10:38.620769200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582977,'de_CH') 
;

-- Element: CounterAccount
-- 2024-02-23T08:10:42.901910900Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-23 10:10:42.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582977 AND AD_Language='de_DE'
;

-- 2024-02-23T08:10:42.905541300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582977,'de_DE') 
;

-- 2024-02-23T08:10:42.907092700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582977,'de_DE') 
;

-- 2024-02-23T08:11:10.502933400Z
UPDATE AD_Element SET ColumnName='CounterPartAccount',Updated=TO_TIMESTAMP('2024-02-23 10:11:10.502','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582977
;

-- 2024-02-23T08:11:10.506124700Z
UPDATE AD_Column SET ColumnName='CounterPartAccount' WHERE AD_Element_ID=582977
;

-- 2024-02-23T08:11:10.507917Z
UPDATE AD_Process_Para SET ColumnName='CounterPartAccount' WHERE AD_Element_ID=582977
;

-- 2024-02-23T08:11:10.512152100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582977,'de_DE') 
;

-- Element: CounterPartAccount
-- 2024-02-23T08:11:16.311729200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='CounterPart Account', PrintName='CounterPart Account',Updated=TO_TIMESTAMP('2024-02-23 10:11:16.311','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582977 AND AD_Language='en_US'
;

-- 2024-02-23T08:11:16.314853400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582977,'en_US') 
;

-- 2024-02-23T08:12:48.310871700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582978,0,'Credit_Currency',TO_TIMESTAMP('2024-02-23 10:12:48.192','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Haben währung','Haben währung',TO_TIMESTAMP('2024-02-23 10:12:48.192','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-23T08:12:48.312957400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582978 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Credit_Currency
-- 2024-02-23T08:13:00.695558600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Credit Currency', PrintName='Credit Currency',Updated=TO_TIMESTAMP('2024-02-23 10:13:00.695','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582978 AND AD_Language='en_US'
;

-- 2024-02-23T08:13:00.698663300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582978,'en_US') 
;

-- Element: Credit_Currency
-- 2024-02-23T08:13:05.386616Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-23 10:13:05.386','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582978 AND AD_Language='fr_CH'
;

-- 2024-02-23T08:13:05.391388500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582978,'fr_CH') 
;

-- Element: Credit_Currency
-- 2024-02-23T08:13:07.351508900Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-23 10:13:07.35','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582978 AND AD_Language='de_CH'
;

-- 2024-02-23T08:13:07.354096100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582978,'de_CH') 
;

-- Element: Credit_Currency
-- 2024-02-23T08:13:09.213550900Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-23 10:13:09.213','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582978 AND AD_Language='de_DE'
;

-- 2024-02-23T08:13:09.218316800Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582978,'de_DE') 
;

-- 2024-02-23T08:13:09.219897700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582978,'de_DE') 
;

-- 2024-02-23T08:13:55.442088Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582979,0,'Debit_Currency',TO_TIMESTAMP('2024-02-23 10:13:55.327','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Soll währung','Soll währung',TO_TIMESTAMP('2024-02-23 10:13:55.327','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-23T08:13:55.444183200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582979 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Debit_Currency
-- 2024-02-23T08:14:00.638247200Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-23 10:14:00.637','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582979 AND AD_Language='de_DE'
;

-- 2024-02-23T08:14:00.641352600Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582979,'de_DE') 
;

-- 2024-02-23T08:14:00.642393900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582979,'de_DE') 
;

-- Element: Debit_Currency
-- 2024-02-23T08:14:22.556864Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Debit currency', PrintName='Debit currency',Updated=TO_TIMESTAMP('2024-02-23 10:14:22.556','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582979 AND AD_Language='en_US'
;

-- 2024-02-23T08:14:22.559986400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582979,'en_US') 
;

-- Element: Debit_Currency
-- 2024-02-23T08:14:24.453615300Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-23 10:14:24.453','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582979 AND AD_Language='de_CH'
;

-- 2024-02-23T08:14:24.456213400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582979,'de_CH') 
;

