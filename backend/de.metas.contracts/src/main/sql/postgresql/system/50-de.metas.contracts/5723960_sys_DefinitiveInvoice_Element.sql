-- 2024-05-20T14:17:56.588Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583114,0,'DefinitiveInvoice',TO_TIMESTAMP('2024-05-20 17:17:56.385','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Definitive Invoice','Definitive Invoice',TO_TIMESTAMP('2024-05-20 17:17:56.385','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-20T14:17:56.592Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583114 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-05-20T14:18:02.984Z
UPDATE AD_Element SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-05-20 17:18:02.983','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583114
;

-- 2024-05-20T14:18:03.024Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583114,'de_DE')
;

-- Element: DefinitiveInvoice
-- 2024-05-20T14:18:09.305Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-20 17:18:09.305','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583114 AND AD_Language='en_US'
;

-- 2024-05-20T14:18:09.308Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583114,'en_US')
;

-- Element: DefinitiveInvoice
-- 2024-05-20T14:18:25.578Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Definitive Schlussabrechnung', PrintName='Definitive Schlussabrechnung',Updated=TO_TIMESTAMP('2024-05-20 17:18:25.577','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583114 AND AD_Language='de_DE'
;

-- 2024-05-20T14:18:25.590Z
UPDATE AD_Element SET Name='Definitive Schlussabrechnung', PrintName='Definitive Schlussabrechnung' WHERE AD_Element_ID=583114
;

-- 2024-05-20T14:18:27.925Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583114,'de_DE')
;

-- 2024-05-20T14:18:27.931Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583114,'de_DE')
;

-- Element: DefinitiveInvoice
-- 2024-05-20T14:18:37.213Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Definitive Schlussabrechnung', PrintName='Definitive Schlussabrechnung',Updated=TO_TIMESTAMP('2024-05-20 17:18:37.212','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583114 AND AD_Language='fr_CH'
;

-- 2024-05-20T14:18:37.215Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583114,'fr_CH')
;

-- Element: DefinitiveInvoice
-- 2024-05-20T14:19:18.362Z
UPDATE AD_Element_Trl SET Name='Definitive Schlussabrechnung', PrintName='Definitive Schlussabrechnung',Updated=TO_TIMESTAMP('2024-05-20 17:19:18.362','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583114 AND AD_Language='de_CH'
;

-- 2024-05-20T14:19:18.364Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583114,'de_CH')
;

-- Element: DefinitiveInvoice
-- 2024-05-20T14:19:20.146Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-20 17:19:20.146','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583114 AND AD_Language='de_CH'
;

-- 2024-05-20T14:19:20.148Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583114,'de_CH')
;

-- Element: DefinitiveInvoice
-- 2024-05-20T14:19:30.955Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Definitive Schlussabrechnung',Updated=TO_TIMESTAMP('2024-05-20 17:19:30.954','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583114 AND AD_Language='it_IT'
;

-- 2024-05-20T14:19:30.965Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583114,'it_IT')
;

