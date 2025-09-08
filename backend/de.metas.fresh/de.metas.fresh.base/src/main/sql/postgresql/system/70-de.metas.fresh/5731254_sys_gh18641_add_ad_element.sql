-- 2024-08-13T10:42:25.785Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583223,0,'GrossWeight',TO_TIMESTAMP('2024-08-13 13:42:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','Gewicht brutto','Gewicht brutto',TO_TIMESTAMP('2024-08-13 13:42:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-13T10:42:25.787Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583223 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product.GrossWeight
-- Column: M_Product.GrossWeight
-- 2024-08-13T10:43:09.050Z
UPDATE AD_Column SET AD_Element_ID=583223, ColumnName='GrossWeight', Description=NULL, EntityType='de.metas.product', Help=NULL, IsExcludeFromZoomTargets='Y', Name='Gewicht brutto',Updated=TO_TIMESTAMP('2024-08-13 13:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563992
;

-- 2024-08-13T10:43:09.052Z
UPDATE AD_Field SET Name='Gewicht brutto', Description=NULL, Help=NULL WHERE AD_Column_ID=563992
;

-- 2024-08-13T10:43:09.087Z
/* DDL */  select update_Column_Translation_From_AD_Element(583223) 
;

-- Element: GrossWeight
-- 2024-08-13T10:44:11.872Z
UPDATE AD_Element_Trl SET Name='Gross Weight', PrintName='Gross Weight',Updated=TO_TIMESTAMP('2024-08-13 13:44:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583223 AND AD_Language='en_US'
;

-- 2024-08-13T10:44:11.874Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583223,'en_US') 
;

-- Element: Weight
-- 2024-08-13T10:47:02.704Z
UPDATE AD_Element_Trl SET Name='Netto Gewicht', PrintName='Netto Gewicht',Updated=TO_TIMESTAMP('2024-08-13 13:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=629 AND AD_Language='de_CH'
;

-- 2024-08-13T10:47:02.706Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(629,'de_CH')
;

-- Element: Weight
-- 2024-08-13T10:47:15.615Z
UPDATE AD_Element_Trl SET Name='Net Weight', PrintName='Net Weight',Updated=TO_TIMESTAMP('2024-08-13 13:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=629 AND AD_Language='en_US'
;

-- 2024-08-13T10:47:15.617Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(629,'en_US')
;

-- Element: Weight
-- 2024-08-13T10:47:24.530Z
UPDATE AD_Element_Trl SET Name='Netto Gewicht', PrintName='Netto Gewicht',Updated=TO_TIMESTAMP('2024-08-13 13:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=629 AND AD_Language='de_DE'
;

-- 2024-08-13T10:47:24.533Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(629,'de_DE')
;

-- 2024-08-13T10:47:24.545Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(629,'de_DE')
;


-- Element: Weight
-- 2024-08-13T10:48:23.986Z
UPDATE AD_Element_Trl SET Name='Gewicht netto', PrintName='Gewicht netto',Updated=TO_TIMESTAMP('2024-08-13 13:48:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=629 AND AD_Language='de_DE'
;

-- 2024-08-13T10:48:23.988Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(629,'de_DE')
;

-- 2024-08-13T10:48:23.989Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(629,'de_DE')
;

-- Element: Weight
-- 2024-08-13T10:48:30.875Z
UPDATE AD_Element_Trl SET Name='Gewicht netto', PrintName='Gewicht netto',Updated=TO_TIMESTAMP('2024-08-13 13:48:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=629 AND AD_Language='de_CH'
;

-- 2024-08-13T10:48:30.876Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(629,'de_CH')
;

-- 2024-08-13T10:52:20.767Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PO_Name,PrintName,Updated,UpdatedBy) VALUES (0,583224,0,'GrossWeight_UOM_ID',TO_TIMESTAMP('2024-08-13 13:52:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','Brutto-Verkaufsmengeneinheit ','','Brutto-Verkaufsmengeneinheit ',TO_TIMESTAMP('2024-08-13 13:52:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-13T10:52:20.769Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583224 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product.GrossWeight_UOM_ID
-- 2024-08-13T10:53:19.533Z
UPDATE AD_Column SET AD_Element_ID=583224, ColumnName='GrossWeight_UOM_ID', Description=NULL, EntityType='de.metas.product', Help=NULL, Name='Brutto-Verkaufsmengeneinheit ',Updated=TO_TIMESTAMP('2024-08-13 13:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578971
;

-- 2024-08-13T10:53:19.535Z
UPDATE AD_Field SET Name='Brutto-Verkaufsmengeneinheit ', Description=NULL, Help=NULL WHERE AD_Column_ID=578971
;

-- 2024-08-13T10:53:19.536Z
/* DDL */  select update_Column_Translation_From_AD_Element(583224)
;



